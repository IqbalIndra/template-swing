/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.learn.shirologin.ui.base.controller;

import java.awt.Component;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

/**
 *
 * @author KBDSI-IQBAL
 */
public abstract class AbstractPanelController {
    public abstract JPanel prepareAndGetPanel();
    
    protected void registerAction(Component component, ActionListener actionListener){
        if(component instanceof JButton) {
            ((JButton) (component)).addActionListener(actionListener);
        }else if(component instanceof JComboBox) {
            ((JComboBox) (component)).addActionListener(actionListener);
        }
    }
    
}
