/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.learn.shirologin.ui.user.controller;

import com.learn.shirologin.model.Role;
import com.learn.shirologin.model.UserInfo;
import com.learn.shirologin.service.UserInfoService;
import com.learn.shirologin.ui.base.controller.AbstractPanelController;
import com.learn.shirologin.ui.user.model.UserPaginationComboBoxModel;
import com.learn.shirologin.ui.user.model.UserRoleComboBoxModel;
import com.learn.shirologin.ui.user.model.UserTableModel;
import com.learn.shirologin.ui.user.view.UserTablePaginationPanel;
import com.learn.shirologin.ui.user.view.modal.UserInfoFormBtnPanel;
import com.learn.shirologin.ui.user.view.modal.UserInfoFormDialog;
import com.learn.shirologin.ui.user.view.modal.UserInfoFormPanel;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.swing.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.fill.JRSwapFileVirtualizer;
import net.sf.jasperreports.view.JasperViewer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;

/**
 *
 * @author KBDSI-IQBAL
 */
@RequiredArgsConstructor
@Controller
@Slf4j
public class UserController extends AbstractPanelController{
    private final UserTablePaginationPanel userTablePaginationPanel;
    private final UserInfoFormDialog userInfoFormDialog;
    private final UserTableModel userTableModel;
    private final UserPaginationComboBoxModel userPaginationComboBoxModel;
    private final UserRoleComboBoxModel userRoleComboBoxModel;
    private final UserInfoService userInfoService;
    private final JasperReport userInfoReport;
    private final JRSwapFileVirtualizer swapFileVirtualizer;
    private Page<UserInfo> pageUserInfo;
    
    @PostConstruct
    private void prepareListeners(){
        UserInfoFormBtnPanel userInfoFormBtnPanel = userInfoFormDialog.getUserInfoFormBtnPanel();

        registerAction(userTablePaginationPanel.getBtnFirst(), (e) -> showFirstData());
        registerAction(userTablePaginationPanel.getBtnLast(), (e) -> showLastData());
        registerAction(userTablePaginationPanel.getBtnNext(), (e) -> showNextData());
        registerAction(userTablePaginationPanel.getBtnPrevious(), (e) -> showPreviousData());
        registerAction(userTablePaginationPanel.getBtnNew(), (e) -> showNewData());
        registerAction(userTablePaginationPanel.getBtnDelete(), this::deleteSelectedData);
        registerAction(userTablePaginationPanel.getBtnViewReport(), this::viewReport);
        registerAction(userTablePaginationPanel.getCbxPagePerSize(), this::showDataPerPageSize);
        registerAction(userInfoFormBtnPanel.getSaveBtn(), this::saveUserInfo);
        registerAction(userInfoFormBtnPanel.getCancelBtn(), (e) -> cancelSaveUserInfo());
        registerMouseListener(userTablePaginationPanel.getTableUser(), onClickedTableUser());
    }

    private void viewReport(ActionEvent actionEvent) {
        List<UserInfo> userInfos = userTableModel.getDatas();
        JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(userInfos);

        // Add parameters
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(JRParameter.REPORT_VIRTUALIZER, swapFileVirtualizer);

        JasperPrint jasperPrint = null;
        try {
            jasperPrint = JasperFillManager.fillReport(userInfoReport, parameters,
                    jrBeanCollectionDataSource);
        } catch (JRException e) {
            log.error("Error when fill report : {}", e.getMessage());
        }

        JasperViewer.viewReport(jasperPrint, false);
    }

    private void deleteSelectedData(ActionEvent e) {
        int index = userTablePaginationPanel.getTableUser().getSelectedRow();
        if(index == -1){
            JOptionPane.showMessageDialog(userTablePaginationPanel,
                    "Please selected data !","Warning",JOptionPane.WARNING_MESSAGE);
            return;
        }

        int choose = JOptionPane.showConfirmDialog(userTablePaginationPanel,"Are you sure want to delete this data?","Warning", JOptionPane.OK_CANCEL_OPTION);
        if(choose == JOptionPane.OK_OPTION){
            UserInfo dataByRow = userTableModel.getDataByRow(index);
            userInfoService.delete(dataByRow);
            userTableModel.removeData(index);
        }

    }


    private void showFirstData() {
        if(!pageUserInfo.isFirst()){
            loadEntities(PageRequest.of(0, pageUserInfo.getSize()));
        }
    }

    private void showLastData() {
        if(!pageUserInfo.isLast()){
            loadEntities(PageRequest.of(pageUserInfo.getTotalPages()-1, pageUserInfo.getSize()));
        }
    }

    private void showNextData() {
        if(pageUserInfo.hasNext()){
            loadEntities(pageUserInfo.nextPageable());
        }
    }

    private void showPreviousData() {
        if(pageUserInfo.hasPrevious()){
            loadEntities(pageUserInfo.previousPageable());
        }
    }

    private void showNewData() {
        userInfoFormDialog.getUserInfoFormPanel().clearForm();
        userInfoFormDialog.getUserInfoFormPanel().enabledComponent(true);
        userInfoFormDialog.getUserInfoFormBtnPanel().getSaveBtn().setText("Save");
        userInfoFormDialog.setVisible(true);
        userInfoFormDialog.setLocationRelativeTo(userTablePaginationPanel);
    }

    private void loadEntities(Pageable pageable) {
        pageUserInfo = userInfoService.getAllUserInfo(pageable);
        userTableModel.clearAll();
        userTableModel.addDatas(pageUserInfo.getContent());
        showVisibleButtonPagination(pageUserInfo);
    }

    @Override
    public JPanel prepareAndGetPanel() {
        Pageable pageable = PageRequest.of(0, 10);
        loadEntities(pageable);
        loadPaginationComboBox();
        loadRoleComboBox();
        return userTablePaginationPanel;
    }

    private void showVisibleButtonPagination(Page<UserInfo> pageUserInfo) {
        userTablePaginationPanel.getBtnPrevious().setEnabled(pageUserInfo.hasPrevious());
        userTablePaginationPanel.getBtnNext().setEnabled(pageUserInfo.hasNext());
        userTablePaginationPanel.getBtnFirst().setEnabled((!pageUserInfo.isFirst()));
        userTablePaginationPanel.getBtnLast().setEnabled((!pageUserInfo.isLast()));
    }

    private void loadPaginationComboBox() {
        userPaginationComboBoxModel.removeAllElements();
        userPaginationComboBoxModel.addElements(Arrays.asList(new Integer[] {10,20,30,50,100}));
    }

    private void showDataPerPageSize(ActionEvent e) {
        Integer pageSize = userPaginationComboBoxModel.getSelectedItem();
        if(!ObjectUtils.isEmpty(pageUserInfo) || !pageUserInfo.isEmpty()){
            Pageable pageable = PageRequest.of(pageUserInfo.getNumber(), pageSize);
            loadEntities(pageable);
        }
    }

    private void loadRoleComboBox() {
        userRoleComboBoxModel.removeAllElements();
        userRoleComboBoxModel.addElements(Arrays.asList(Role.values()));
    }

    private void saveUserInfo(ActionEvent e) {
        JButton button = (JButton) e.getSource();

        if(!button.getText().equalsIgnoreCase("save")){
            userInfoFormDialog.getUserInfoFormPanel().enabledComponent(true);
            button.setText("Save");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(userInfoFormDialog,"Are you want to save this data ?","Confirm",JOptionPane.OK_CANCEL_OPTION);
        if(confirm == JOptionPane.OK_OPTION){
            UserInfoFormPanel userInfoFormPanel = userInfoFormDialog.getUserInfoFormPanel();
            UserInfo userInfo = userInfoFormPanel.getEntityFromForm();

            if(!ObjectUtils.isEmpty(userInfo.getId())){
                userInfoService.update(userInfo);
                userTableModel.updateData(userTablePaginationPanel.getTableUser().getSelectedRow(), userInfo);
            }else{
                userInfoService.save(userInfo);
                userTableModel.addData(userInfo);
            }

            cancelSaveUserInfo();
        }

    }

    private void cancelSaveUserInfo() {
        userInfoFormDialog.getUserInfoFormPanel().clearForm();
        userInfoFormDialog.getUserInfoFormBtnPanel().getSaveBtn().setText("Save");
        userInfoFormDialog.dispose();
    }

    private void showViewData(UserInfo info){
        JButton jButton = userInfoFormDialog.getUserInfoFormBtnPanel().getSaveBtn();
        jButton.setText("Edit");

        userInfoFormDialog.getUserInfoFormPanel().setEntityToForm(info);
        userInfoFormDialog.setLocationRelativeTo(userTablePaginationPanel);
        userInfoFormDialog.setVisible(true);
    }

    private MouseListener onClickedTableUser() {
        return new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                JTable table =(JTable) e.getSource();
                Point point = e.getPoint();
                int row = table.rowAtPoint(point);

                if (e.getClickCount() == 2 && table.getSelectedRow() != -1 && row != -1) {
                    UserInfo info = userTableModel.getDataByRow(row);
                    showViewData(info);
                }
            }
        };
    }



}
