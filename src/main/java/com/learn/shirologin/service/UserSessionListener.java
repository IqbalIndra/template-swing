/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.learn.shirologin.service;

import com.learn.shirologin.ui.login.controller.LoginController;
import com.learn.shirologin.util.Windows;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListenerAdapter;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.*;

/**
 *
 * @author KBDSI-IQBAL
 */
@Slf4j
@RequiredArgsConstructor
public class UserSessionListener extends SessionListenerAdapter{
    private final LoginController loginController;

    @Override
    public void onExpiration(Session session) {
        log.info("Session Expired . {}",session.getId());

        JOptionPane.showMessageDialog(null, "Login Expired. Please login !", "Session",JOptionPane.ERROR_MESSAGE);
        Windows.closeAllDialogs();
        loginController.viewToLoginPanel();
    }

    @Override
    public void onStart(Session session) {
        log.info("Session Started . {}",session.getId());
    }

    @Override
    public void onStop(Session session) {
        log.info("Session stop . {}",session.getId());
    }
    
}
