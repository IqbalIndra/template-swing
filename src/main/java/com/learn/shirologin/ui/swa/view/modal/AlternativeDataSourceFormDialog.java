/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.learn.shirologin.ui.swa.view.modal;

import com.learn.shirologin.ui.user.view.modal.UserInfoFormBtnPanel;
import com.learn.shirologin.ui.user.view.modal.UserInfoFormPanel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.miginfocom.swing.MigLayout;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 *
 * @author KBDSI-IQBAL
 */
@Getter
@AllArgsConstructor
@Component
public class AlternativeDataSourceFormDialog extends JDialog{
    private final AlternativeDataSourceFormPanel alternativeDataSourceFormPanel;
    private final AlternativeDataSourceFormBtnPanel alternativeDataSourceFormBtnPanel;
    
    @PostConstruct
    private void prepareDialog(){
        setDialogUp();
        initComponents();
        pack();
    }

    private void setDialogUp() {
        setTitle("Alternative Data Source");
        setLocationRelativeTo(null);
        setResizable(false);
        setModal(true);
    }

    private void initComponents() {
        add(alternativeDataSourceFormPanel,BorderLayout.CENTER);
        add(alternativeDataSourceFormBtnPanel, BorderLayout.SOUTH);
        this.addWindowListener(onDialogClose());
    }

    private WindowListener onDialogClose() {
        return new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        };
    }


}
