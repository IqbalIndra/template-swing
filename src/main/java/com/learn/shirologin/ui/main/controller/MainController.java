/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.learn.shirologin.ui.main.controller;

import com.learn.shirologin.ui.base.controller.AbstractFrameController;
import com.learn.shirologin.ui.dashboard.view.DashboardPanel;
import com.learn.shirologin.ui.login.controller.LoginController;
import com.learn.shirologin.ui.login.view.LoginPanel;
import com.learn.shirologin.ui.main.view.MainFrame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;

/**
 *
 * @author KBDSI-IQBAL
 */
@Controller
@AllArgsConstructor
@Slf4j
public class MainController extends AbstractFrameController{
    private final MainFrame mainFrame;
    private final LoginController loginController;
    private final org.apache.shiro.mgt.SecurityManager securityManager;
    
    @Override
    public void prepareAndOpenFrame() {
        registerWindowAction(mainFrame, onWindowClose());
        initStaticSecurityManager();
       
        loginController.prepareAndOpenFrame();
        
        mainFrame.pack();
        mainFrame.setVisible(true);
    }
    
    private void initStaticSecurityManager() {
        SecurityUtils.setSecurityManager(securityManager);
    }

    private WindowListener onWindowClose() {
       return new WindowAdapter() {
           @Override
           public void windowClosing(WindowEvent e) {
               Subject subject = SecurityUtils.getSubject();

               int answer = JOptionPane.showConfirmDialog(
                       mainFrame,"Are sure to close?","Confirmation",
                       JOptionPane.YES_NO_OPTION);

               switch (answer) {
                   case JOptionPane.YES_OPTION:
                       if(subject.isAuthenticated())
                           subject.logout();

                       System.exit(0);
                       break;

                   case JOptionPane.NO_OPTION:
                       break;
               }
           }
            
       };
    }
    
}
