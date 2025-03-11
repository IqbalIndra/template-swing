/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.learn.shirologin.ui.base.controller;

import java.awt.Component;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.WindowListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionListener;

/**
 *
 * @author KBDSI-IQBAL
 */
public abstract class AbstractFrameController {
    public abstract void prepareAndOpenFrame();
    
    protected void registerAction(Component component, ActionListener actionListener){
        if(component instanceof JButton) {
            ((JButton) (component)).addActionListener(actionListener);
        }
    }
    
    protected void registerWindowAction(JFrame frame, WindowListener windowListener){
        frame.addWindowListener(windowListener);
    }
    
    protected void registerTreeAction(JTree tree,TreeSelectionListener treeSelectionListener ){
        tree.addTreeSelectionListener(treeSelectionListener);
    }
    
    protected void registerMouseListener(Component component, MouseListener listener){
        if(component instanceof JTree) {
            ((JTree) (component)).addMouseListener(listener);
        }else if(component instanceof JTable){
            ((JTable) (component)).addMouseListener(listener);
        }
    }
    
    
}
