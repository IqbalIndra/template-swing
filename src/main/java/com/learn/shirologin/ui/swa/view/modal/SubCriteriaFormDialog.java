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
public class SubCriteriaFormDialog extends JDialog{
    private final SubCriteriaFormPanel subCriteriaFormPanel;
    private final SubCriteriaFormBtnPanel SubcriteriaFormBtnPanel;
    
    @PostConstruct
    private void prepareDialog(){
        setDialogUp();
        initComponents();
        pack();
    }

    private void setDialogUp() {
        setTitle("Sub Kriteria");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setModal(true);
    }

    private void initComponents() {
        add(subCriteriaFormPanel, BorderLayout.CENTER);
        add(SubcriteriaFormBtnPanel, BorderLayout.SOUTH);
    }
}
