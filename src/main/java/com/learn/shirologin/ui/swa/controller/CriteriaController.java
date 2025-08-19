/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.learn.shirologin.ui.swa.controller;

import com.learn.shirologin.common.validation.ValidationError;
import com.learn.shirologin.model.Criteria;
import com.learn.shirologin.model.CriteriaType;
import com.learn.shirologin.service.CriteriaService;
import com.learn.shirologin.ui.base.controller.AbstractPanelController;
import com.learn.shirologin.ui.swa.model.CriteriaTableModel;
import com.learn.shirologin.ui.swa.model.CriteriaTypeComboBoxModel;
import com.learn.shirologin.ui.swa.validation.CriteriaValidator;
import com.learn.shirologin.ui.swa.view.CriteriaTablePaginationPanel;
import com.learn.shirologin.ui.swa.view.modal.CriteriaFormBtnPanel;
import com.learn.shirologin.ui.swa.view.modal.CriteriaFormDialog;
import com.learn.shirologin.ui.swa.view.modal.CriteriaFormPanel;
import com.learn.shirologin.ui.user.model.UserPaginationComboBoxModel;
import com.learn.shirologin.util.Notifications;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 *
 * @author KBDSI-IQBAL
 */
@RequiredArgsConstructor
@Controller
@Slf4j
public class CriteriaController extends AbstractPanelController{
    private final CriteriaTablePaginationPanel criteriaTablePaginationPanel;
    private final CriteriaFormDialog criteriaFormDialog;
    private final CriteriaTableModel criteriaTableModel;
    private final CriteriaTypeComboBoxModel criteriaTypeComboBoxModel;
    private final CriteriaService criteriaService;
    private final CriteriaValidator criteriaValidator;
    private Page<Criteria> pageCriteria;
    
    @PostConstruct
    private void prepareListeners(){
        CriteriaFormBtnPanel criteriaFormBtnPanel = criteriaFormDialog.getCriteriaFormBtnPanel();

        registerAction(criteriaTablePaginationPanel.getBtnFirst(), (e) -> showFirstData());
        registerAction(criteriaTablePaginationPanel.getBtnLast(), (e) -> showLastData());
        registerAction(criteriaTablePaginationPanel.getBtnNext(), (e) -> showNextData());
        registerAction(criteriaTablePaginationPanel.getBtnPrevious(), (e) -> showPreviousData());
        registerAction(criteriaTablePaginationPanel.getBtnNew(), (e) -> showNewData());
        registerAction(criteriaTablePaginationPanel.getBtnDelete(), this::deleteSelectedData);
        registerAction(criteriaTablePaginationPanel.getCbxPagePerSize(), this::showDataPerPageSize);
        registerAction(criteriaFormBtnPanel.getSaveBtn(), this::saveCriteria);
        registerAction(criteriaFormBtnPanel.getCancelBtn(), (e) -> cancelSaveCriteria());
        registerMouseListener(criteriaTablePaginationPanel.getTableCriteria(), onClickedTable());
    }


    private void deleteSelectedData(ActionEvent e) {
        int index = criteriaTablePaginationPanel.getTableCriteria().getSelectedRow();
        if(index == -1){
            JOptionPane.showMessageDialog(criteriaTablePaginationPanel,
                    "Please selected data !","Warning",JOptionPane.WARNING_MESSAGE);
            return;
        }

        int choose = JOptionPane.showConfirmDialog(criteriaTablePaginationPanel,"Are you sure want to delete this data?","Warning", JOptionPane.OK_CANCEL_OPTION);
        if(choose == JOptionPane.OK_OPTION){
            Criteria dataByRow = criteriaTableModel.getDataByRow(index);
            criteriaService.delete(dataByRow);
            criteriaTableModel.removeData(index);
        }

    }


    private void showFirstData() {
        if(!pageCriteria.isFirst()){
            loadEntities(PageRequest.of(0, pageCriteria.getSize()));
        }
    }

    private void showLastData() {
        if(!pageCriteria.isLast()){
            loadEntities(PageRequest.of(pageCriteria.getTotalPages()-1, pageCriteria.getSize()));
        }
    }

    private void showNextData() {
        if(pageCriteria.hasNext()){
            loadEntities(pageCriteria.nextPageable());
        }
    }

    private void showPreviousData() {
        if(pageCriteria.hasPrevious()){
            loadEntities(pageCriteria.previousPageable());
        }
    }

    private void showNewData() {
        criteriaFormDialog.getCriteriaFormPanel().clearForm();
        criteriaFormDialog.getCriteriaFormPanel().enabledComponent(true);
        criteriaFormDialog.getCriteriaFormBtnPanel().getSaveBtn().setText("Save");
        criteriaFormDialog.setLocationRelativeTo(criteriaTablePaginationPanel);
        criteriaFormDialog.pack();
        criteriaFormDialog.setVisible(true);
    }

    private void loadEntities(Pageable pageable) {
        pageCriteria = criteriaService.findByPagination(pageable);
        criteriaTableModel.clearAll();
        criteriaTableModel.addDatas(pageCriteria.getContent());
        showVisibleButtonPagination(pageCriteria);
    }

    @Override
    public JPanel prepareAndGetPanel() {
        Pageable pageable = PageRequest.of(0, 10);
        loadEntities(pageable);
        loadCriteriaTypeComboBox();
        return criteriaTablePaginationPanel;
    }

    private void showVisibleButtonPagination(Page<Criteria> pageCriteria) {
        criteriaTablePaginationPanel.getBtnPrevious().setEnabled(pageCriteria.hasPrevious());
        criteriaTablePaginationPanel.getBtnNext().setEnabled(pageCriteria.hasNext());
        criteriaTablePaginationPanel.getBtnFirst().setEnabled((!pageCriteria.isFirst()));
        criteriaTablePaginationPanel.getBtnLast().setEnabled((!pageCriteria.isLast()));
    }


    private void showDataPerPageSize(ActionEvent e) {
        Integer pageSize = (Integer) criteriaTablePaginationPanel.getCbxPagePerSize().getSelectedItem();
        if(pageCriteria != null){
            Pageable pageable = PageRequest.of(pageCriteria.getNumber(), pageSize);
            loadEntities(pageable);
        }
    }

    private void loadCriteriaTypeComboBox() {
        criteriaTypeComboBoxModel.removeAllElements();
        criteriaTypeComboBoxModel.addElements(
                Arrays.stream(CriteriaType.values())
                        .map(CriteriaType::getName)
                        .collect(Collectors.toList())
        );
    }

    private void saveCriteria(ActionEvent e) {
        JButton button = (JButton) e.getSource();

        if(!button.getText().equalsIgnoreCase("save")){
            criteriaFormDialog.getCriteriaFormPanel().enabledComponent(true);
            button.setText("Save");
            return;
        }

        CriteriaFormPanel criteriaFormPanel = criteriaFormDialog.getCriteriaFormPanel();
        Criteria criteria = criteriaFormPanel.getEntityFromForm();
        Optional<ValidationError> validator = criteriaValidator.validate(criteria);

        if(validator.isPresent()){
            Notifications.showFormValidationAlert(criteriaFormPanel, "Error",validator.get().getMessage());
        }else{
            int confirm = JOptionPane.showConfirmDialog(criteriaFormDialog,"Are you want to save this data ?","Confirm",JOptionPane.OK_CANCEL_OPTION);
            if(confirm == JOptionPane.OK_OPTION){


                if(!ObjectUtils.isEmpty(criteria.getId())){
                    criteriaService.update(criteria);
                    criteriaTableModel.updateData(criteriaTablePaginationPanel.getTableCriteria().getSelectedRow(), criteria);
                }else{
                    criteriaService.save(criteria);
                    criteriaTableModel.addData(criteria);
                }

                cancelSaveCriteria();
            }
        }
    }

    private void cancelSaveCriteria() {
        criteriaFormDialog.getCriteriaFormPanel().clearForm();
        criteriaFormDialog.getCriteriaFormBtnPanel().getSaveBtn().setText("Save");
        criteriaFormDialog.dispose();
    }

    private void showViewData(Criteria info){
        JButton jButton = criteriaFormDialog.getCriteriaFormBtnPanel().getSaveBtn();
        jButton.setText("Edit");

        criteriaFormDialog.getCriteriaFormPanel().setEntityToForm(info);
        criteriaFormDialog.setLocationRelativeTo(criteriaTablePaginationPanel);
        criteriaFormDialog.setVisible(true);
    }

    private MouseListener onClickedTable() {
        return new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                JTable table =(JTable) e.getSource();
                Point point = e.getPoint();
                int row = table.rowAtPoint(point);

                if (e.getClickCount() == 2 && table.getSelectedRow() != -1 && row != -1) {
                    Criteria info = criteriaTableModel.getDataByRow(row);
                    showViewData(info);
                }
            }
        };
    }
}
