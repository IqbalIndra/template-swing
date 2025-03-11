/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.learn.shirologin.ui.base.controller;

import java.awt.Component;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTree;

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
    
    protected void registerMouseListener(Component component, MouseListener listener){
        if(component instanceof JTree) {
            ((JTree) (component)).addMouseListener(listener);
        }else if(component instanceof JTable){
            ((JTable) (component)).addMouseListener(listener);
        }
    }
    
}
