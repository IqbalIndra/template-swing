/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.learn.shirologin.ui.dashboard.view;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.util.function.IntConsumer;
import javax.annotation.PostConstruct;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTree;
import javax.swing.tree.TreeSelectionModel;
import lombok.Getter;
import net.miginfocom.swing.MigLayout;
import org.springframework.stereotype.Component;

/**
 *
 * @author KBDSI-IQBAL
 */
@Component
@Getter
public class DashboardPanel extends JPanel{
   private JTabbedPane tabbedPaneLeft;
   private JTabbedPane tabbedPaneRight;
   private JPanel panelRight;

   private JSplitPane splitPanel;
   private JTree treeMenu;
    
    @PostConstruct
    private void preparePanel(){
        setPanelUp();
        initComponents();
    }

    private void setPanelUp() {
        setLayout(new MigLayout("fill"));
    }

    private void initComponents() {
        treeMenu = new JTree();
        treeMenu.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        
        tabbedPaneLeft = new JTabbedPane();
        tabbedPaneLeft.setMinimumSize(new Dimension(200, 300));
        tabbedPaneLeft.addTab("Menu",treeMenu);
        tabbedPaneLeft.putClientProperty("JTabbedPane.tabClosable", false);
        tabbedPaneLeft.putClientProperty("JTabbedPane.tabCloseCallback", (IntConsumer) tabIndex -> {
            tabbedPaneLeft.remove(tabIndex);
        });
        
        tabbedPaneRight = new JTabbedPane();
        tabbedPaneRight.putClientProperty("JTabbedPane.tabClosable", true);
        tabbedPaneRight.putClientProperty("JTabbedPane.tabCloseCallback", (IntConsumer) tabIndex -> {
            tabbedPaneRight.remove(tabIndex);
        });
        
        
        splitPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, tabbedPaneLeft,tabbedPaneRight);
        
        add(splitPanel,"dock center");
    }

    private JPanel createComponentPanelRight() {
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new CardLayout());
        return jPanel;
    }
}
