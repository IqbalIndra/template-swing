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
import java.awt.event.ActionEvent;
import java.util.Arrays;
import javax.annotation.PostConstruct;
import javax.swing.JPanel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    private Page<UserInfo> pageUserInfo;
    
    @PostConstruct
    private void prepareListeners(){
        UserInfoFormBtnPanel userInfoFormBtnPanel = userInfoFormDialog.getUserInfoFormBtnPanel();
        
        registerAction(userTablePaginationPanel.getBtnFirst(), (e) -> showFirstData());
        registerAction(userTablePaginationPanel.getBtnLast(), (e) -> showLastData());
        registerAction(userTablePaginationPanel.getBtnNext(), (e) -> showNextData());
        registerAction(userTablePaginationPanel.getBtnPrevious(), (e) -> showPreviousData());
        registerAction(userTablePaginationPanel.getBtnNew(), (e) -> showNewData());
        registerAction(userTablePaginationPanel.getCbxPagePerSize(), (e) -> showDataPerPageSize(e));
        registerAction(userInfoFormBtnPanel.getSaveBtn(), (e) -> saveUserInfo(e));
        registerAction(userInfoFormBtnPanel.getCancelBtn(), (e) -> cancelSaveUserInfo());
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
        userInfoFormDialog.setVisible(true);
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
        UserInfoFormPanel userInfoFormPanel = userInfoFormDialog.getUserInfoFormPanel();
        UserInfo userInfo = userInfoFormPanel.getEntityFromForm();
        
        userInfoService.save(userInfo);
        userTableModel.addData(userInfo);
        cancelSaveUserInfo();
    }

    private void cancelSaveUserInfo() {
        userInfoFormDialog.getUserInfoFormPanel().clearForm();
        userInfoFormDialog.dispose();
    }
    
}
