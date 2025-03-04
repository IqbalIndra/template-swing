/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.learn.shirologin.ui.user.view.modal;

import javax.annotation.PostConstruct;
import javax.swing.JButton;
import javax.swing.JPanel;
import lombok.Getter;
import org.springframework.stereotype.Component;

/**
 *
 * @author KBDSI-IQBAL
 */
@Component
@Getter
public class UserInfoFormBtnPanel extends JPanel{
    private JButton saveBtn;
    private JButton cancelBtn;
    
    @PostConstruct
    private void preparePanel(){
        initComponents();
    }

    private void initComponents() {
        saveBtn = new JButton("Save");
        cancelBtn = new JButton("Cancel");
        add(saveBtn);
        add(cancelBtn);
    }
}
