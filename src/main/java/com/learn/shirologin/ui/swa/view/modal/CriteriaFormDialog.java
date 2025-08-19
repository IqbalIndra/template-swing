/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.learn.shirologin.ui.swa.view.modal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.awt.*;

/**
 *
 * @author KBDSI-IQBAL
 */
@Getter
@AllArgsConstructor
@Component
public class CriteriaFormDialog extends JDialog{
    private final CriteriaFormPanel criteriaFormPanel;
    private final CriteriaFormBtnPanel criteriaFormBtnPanel;
    
    @PostConstruct
    private void prepareDialog(){
        setDialogUp();
        initComponents();
        pack();
    }

    private void setDialogUp() {
        setTitle("Criteria");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setModal(true);
    }

    private void initComponents() {
        add(criteriaFormPanel, BorderLayout.CENTER);
        add(criteriaFormBtnPanel, BorderLayout.SOUTH);
    }
}
