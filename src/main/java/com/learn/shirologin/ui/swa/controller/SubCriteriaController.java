/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.learn.shirologin.ui.swa.controller;

import com.learn.shirologin.common.validation.ValidationError;
import com.learn.shirologin.model.OperatorType;
import com.learn.shirologin.model.SubCriteria;
import com.learn.shirologin.service.CriteriaService;
import com.learn.shirologin.service.SubCriteriaService;
import com.learn.shirologin.ui.base.controller.AbstractPanelController;
import com.learn.shirologin.ui.swa.model.CriteriaSingleComboBoxModel;
import com.learn.shirologin.ui.swa.model.OperatorTypeComboBoxModel;
import com.learn.shirologin.ui.swa.model.SubCriteriaTableModel;
import com.learn.shirologin.ui.swa.validation.SubCriteriaValidator;
import com.learn.shirologin.ui.swa.view.SubCriteriaTablePaginationPanel;
import com.learn.shirologin.ui.swa.view.modal.SubCriteriaFormBtnPanel;
import com.learn.shirologin.ui.swa.view.modal.SubCriteriaFormDialog;
import com.learn.shirologin.ui.swa.view.modal.SubCriteriaFormPanel;
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
public class SubCriteriaController extends AbstractPanelController{
    private final SubCriteriaTablePaginationPanel subCriteriaTablePaginationPanel;
    private final SubCriteriaFormDialog subCriteriaFormDialog;
    private final SubCriteriaTableModel subCriteriaTableModel;
    private final CriteriaSingleComboBoxModel criteriaSingleComboBoxModel;
    private final SubCriteriaService subCriteriaService;
    private final CriteriaService criteriaService;
    private final SubCriteriaValidator subCriteriaValidator;
    private Page<SubCriteria> pageSubCriteria;
    private final OperatorTypeComboBoxModel operatorTypeComboBoxModel;

    @PostConstruct
    private void prepareListeners(){
        SubCriteriaFormBtnPanel subcriteriaFormBtnPanel = subCriteriaFormDialog.getSubcriteriaFormBtnPanel();
        SubCriteriaFormPanel subCriteriaFormPanel = subCriteriaFormDialog.getSubCriteriaFormPanel();

        registerAction(subCriteriaTablePaginationPanel.getBtnFirst(), (e) -> showFirstData());
        registerAction(subCriteriaTablePaginationPanel.getBtnLast(), (e) -> showLastData());
        registerAction(subCriteriaTablePaginationPanel.getBtnNext(), (e) -> showNextData());
        registerAction(subCriteriaTablePaginationPanel.getBtnPrevious(), (e) -> showPreviousData());
        registerAction(subCriteriaTablePaginationPanel.getBtnNew(), (e) -> showNewData());
        registerAction(subCriteriaTablePaginationPanel.getBtnDelete(), this::deleteSelectedData);
        registerAction(subCriteriaTablePaginationPanel.getCbxPagePerSize(), this::showDataPerPageSize);
        registerAction(subcriteriaFormBtnPanel.getSaveBtn(), this::saveSubCriteria);
        registerAction(subCriteriaFormPanel.getOperatorTypeComboBox(), this::handleOperatorWeight);
        registerAction(subcriteriaFormBtnPanel.getCancelBtn(), (e) -> cancelSaveSubCriteria());
        registerMouseListener(subCriteriaTablePaginationPanel.getSubTableCriteria(), onClickedTable());
    }

    private void handleOperatorWeight(ActionEvent actionEvent) {
        SubCriteriaFormPanel subCriteriaFormPanel = subCriteriaFormDialog.getSubCriteriaFormPanel();
        subCriteriaFormPanel.setHandleOperatorType();
    }


    private void deleteSelectedData(ActionEvent e) {
        int index = subCriteriaTablePaginationPanel.getSubTableCriteria().getSelectedRow();
        if(index == -1){
            JOptionPane.showMessageDialog(subCriteriaTablePaginationPanel,
                    "Please selected data !","Warning",JOptionPane.WARNING_MESSAGE);
            return;
        }

        int choose = JOptionPane.showConfirmDialog(subCriteriaTablePaginationPanel,"Are you sure want to delete this data?","Warning", JOptionPane.OK_CANCEL_OPTION);
        if(choose == JOptionPane.OK_OPTION){
            SubCriteria dataByRow = subCriteriaTableModel.getDataByRow(index);
            subCriteriaService.delete(dataByRow);
            subCriteriaTableModel.removeData(index);
        }

    }


    private void showFirstData() {
        if(!pageSubCriteria.isFirst()){
            loadEntities(PageRequest.of(0, pageSubCriteria.getSize()));
        }
    }

    private void showLastData() {
        if(!pageSubCriteria.isLast()){
            loadEntities(PageRequest.of(pageSubCriteria.getTotalPages()-1, pageSubCriteria.getSize()));
        }
    }

    private void showNextData() {
        if(pageSubCriteria.hasNext()){
            loadEntities(pageSubCriteria.nextPageable());
        }
    }

    private void showPreviousData() {
        if(pageSubCriteria.hasPrevious()){
            loadEntities(pageSubCriteria.previousPageable());
        }
    }

    private void showNewData() {
        subCriteriaFormDialog.getSubCriteriaFormPanel().clearForm();
        subCriteriaFormDialog.getSubCriteriaFormPanel().enabledComponent(true);
        subCriteriaFormDialog.getSubCriteriaFormPanel().setHandleOperatorType();
        subCriteriaFormDialog.getSubcriteriaFormBtnPanel().getSaveBtn().setText("Save");
        subCriteriaFormDialog.setLocationRelativeTo(subCriteriaTablePaginationPanel);
        subCriteriaFormDialog.pack();
        subCriteriaFormDialog.setVisible(true);
    }

    private void loadEntities(Pageable pageable) {
        pageSubCriteria = subCriteriaService.findByPagination(pageable);
        subCriteriaTableModel.clearAll();
        subCriteriaTableModel.addDatas(pageSubCriteria.getContent());
        showVisibleButtonPagination(pageSubCriteria);
    }

    @Override
    public JPanel prepareAndGetPanel() {
        Pageable pageable = PageRequest.of(0, 10);
        loadEntities(pageable);
        loadOperatorTypeComboBox();
        loadCriteriaComboBox();
        return subCriteriaTablePaginationPanel;
    }

    private void showVisibleButtonPagination(Page<SubCriteria> pageSubCriteria) {
        subCriteriaTablePaginationPanel.getBtnPrevious().setEnabled(pageSubCriteria.hasPrevious());
        subCriteriaTablePaginationPanel.getBtnNext().setEnabled(pageSubCriteria.hasNext());
        subCriteriaTablePaginationPanel.getBtnFirst().setEnabled((!pageSubCriteria.isFirst()));
        subCriteriaTablePaginationPanel.getBtnLast().setEnabled((!pageSubCriteria.isLast()));
    }


    private void showDataPerPageSize(ActionEvent e) {
        Integer pageSize = (Integer) subCriteriaTablePaginationPanel.getCbxPagePerSize().getSelectedItem();
        if(pageSubCriteria != null){
            Pageable pageable = PageRequest.of(pageSubCriteria.getNumber(), pageSize);
            loadEntities(pageable);
        }
    }

    private void loadCriteriaComboBox() {
        criteriaSingleComboBoxModel.removeAllElements();
        criteriaSingleComboBoxModel.addElements(
                criteriaService.findAll()
        );
    }

    private void loadOperatorTypeComboBox(){
        operatorTypeComboBoxModel.clear();
        operatorTypeComboBoxModel.addElements(
                Arrays.stream(OperatorType.values())
                        .map(OperatorType::getSymbol)
                        .collect(Collectors.toList())
        );
    }

    private void saveSubCriteria(ActionEvent e) {
        JButton button = (JButton) e.getSource();

        if(!button.getText().equalsIgnoreCase("save")){
            subCriteriaFormDialog.getSubCriteriaFormPanel().enabledComponent(true);
            button.setText("Save");
            return;
        }

        SubCriteriaFormPanel subCriteriaFormPanel = subCriteriaFormDialog.getSubCriteriaFormPanel();
        SubCriteria subCriteria = subCriteriaFormPanel.getEntityFromForm();
        Optional<ValidationError> validator = subCriteriaValidator.validate(subCriteria);

        if(validator.isPresent()){
            Notifications.showFormValidationAlert(subCriteriaFormPanel, "Error",validator.get().getMessage());
        }else{
            int confirm = JOptionPane.showConfirmDialog(subCriteriaFormDialog,"Are you want to save this data ?","Confirm",JOptionPane.OK_CANCEL_OPTION);
            if(confirm == JOptionPane.OK_OPTION){

                if(!ObjectUtils.isEmpty(subCriteria.getId())){
                    subCriteriaService.update(subCriteria);
                    subCriteriaTableModel.updateData(subCriteriaTablePaginationPanel.getSubTableCriteria().getSelectedRow(), subCriteria);
                }else{
                    subCriteriaService.save(subCriteria);
                    subCriteriaTableModel.addData(subCriteria);
                }

                cancelSaveSubCriteria();
            }
        }
    }

    private void cancelSaveSubCriteria() {
        subCriteriaFormDialog.getSubCriteriaFormPanel().clearForm();
        subCriteriaFormDialog.getSubcriteriaFormBtnPanel().getSaveBtn().setText("Save");
        subCriteriaFormDialog.dispose();
    }

    private void showViewData(SubCriteria info){
        JButton jButton = subCriteriaFormDialog.getSubcriteriaFormBtnPanel().getSaveBtn();
        jButton.setText("Edit");

        subCriteriaFormDialog.getSubCriteriaFormPanel().setEntityToForm(info);
        subCriteriaFormDialog.setLocationRelativeTo(subCriteriaTablePaginationPanel);
        subCriteriaFormDialog.setVisible(true);
    }

    private MouseListener onClickedTable() {
        return new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                JTable table =(JTable) e.getSource();
                Point point = e.getPoint();
                int row = table.rowAtPoint(point);

                if (e.getClickCount() == 2 && table.getSelectedRow() != -1 && row != -1) {
                    SubCriteria info = subCriteriaTableModel.getDataByRow(row);
                    showViewData(info);
                }
            }
        };
    }
}
