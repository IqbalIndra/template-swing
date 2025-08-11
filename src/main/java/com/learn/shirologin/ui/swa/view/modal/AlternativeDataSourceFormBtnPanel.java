/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.learn.shirologin.ui.swa.view.modal;

import lombok.Getter;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;

/**
 *
 * @author KBDSI-IQBAL
 */
@Component
@Getter
public class AlternativeDataSourceFormBtnPanel extends JPanel{
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
