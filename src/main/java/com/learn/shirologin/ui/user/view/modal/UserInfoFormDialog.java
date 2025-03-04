/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.learn.shirologin.ui.user.view.modal;

import java.awt.BorderLayout;
import javax.annotation.PostConstruct;
import javax.swing.JDialog;
import javax.swing.WindowConstants;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Component;

/**
 *
 * @author KBDSI-IQBAL
 */
@Getter
@AllArgsConstructor
@Component
public class UserInfoFormDialog extends JDialog{
    private final UserInfoFormPanel userInfoFormPanel;
    private final UserInfoFormBtnPanel userInfoFormBtnPanel;
    
    @PostConstruct
    private void prepareDialog(){
        setDialogUp();
        initComponents();
        pack();
    }

    private void setDialogUp() {
        setTitle("User Info");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setModal(true);
    }

    private void initComponents() {
        add(userInfoFormPanel, BorderLayout.CENTER);
        add(userInfoFormBtnPanel, BorderLayout.SOUTH);
    }
}
