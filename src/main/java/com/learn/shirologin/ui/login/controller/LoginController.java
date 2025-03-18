/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.learn.shirologin.ui.login.controller;


import com.learn.shirologin.service.UserInfoService;
import com.learn.shirologin.ui.base.controller.AbstractFrameController;
import com.learn.shirologin.ui.dashboard.controller.DashboardController;
import com.learn.shirologin.ui.login.model.LoginEntity;
import com.learn.shirologin.ui.login.view.LoginPanel;
import com.learn.shirologin.ui.main.view.MainFrame;
import java.awt.Dimension;
import java.awt.Toolkit;

import com.learn.shirologin.util.ConstantParams;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;

/**
 *
 * @author KBDSI-IQBAL
 */
@Controller
@Slf4j
@AllArgsConstructor
public class LoginController extends AbstractFrameController{
    private final LoginPanel loginPanel;
    private final MainFrame mainFrame;
    private final UserInfoService userInfoService;
    private final DashboardController dashboardController;
  
    @Override
    public void prepareAndOpenFrame() {
        registerAction(loginPanel.getButtonLogin(), (e) -> submitLogin());
        registerAction(loginPanel.getButtonCancel(), (e) -> cancelLogin());
        
        mainFrame.showViewCardPanel(ConstantParams.LOGIN_PANEL);
    }

    private void submitLogin() {
        
        LoginEntity loginEntity = loginPanel.getRequestFromForm();
        try {
            userInfoService.login(loginEntity.getUsername(), loginEntity.getPassword());
            Subject subject = SecurityUtils.getSubject();
            log.info("Authenticated : {}" , subject.isAuthenticated());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new AuthenticationException(e.getMessage());
        }
        loginPanel.clearForm();
        dashboardController.prepareAndOpenFrame();
        
    }

    private void cancelLogin() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        log.info("Authenticated : {}" , subject.isAuthenticated());
    }
    
}
