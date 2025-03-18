/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.learn.shirologin.ui.dashboard.controller;

import com.learn.shirologin.domain.menu.model.TreeMenu;
import com.learn.shirologin.domain.menu.service.TreeMenuService;
import com.learn.shirologin.ui.base.controller.AbstractFrameController;
import com.learn.shirologin.ui.dashboard.view.DashboardPanel;
import com.learn.shirologin.ui.main.view.MainFrame;
import com.learn.shirologin.util.ApplicationContextHolder;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.lang.reflect.Method;
import java.util.Optional;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import com.learn.shirologin.util.ConstantParams;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

/**
 *
 * @author KBDSI-IQBAL
 */
@Controller
@AllArgsConstructor
@Slf4j
public class DashboardController extends AbstractFrameController{
    private final MainFrame mainFrame;
    private final DashboardPanel dashboardPanel;
    private final TreeMenuService treeMenuService;

    @Override
    public void prepareAndOpenFrame() {
        registerTreeAction(dashboardPanel.getTreeMenu(), (e) -> showMenu());
        registerMouseListener(dashboardPanel.getTreeMenu(), releaseTreeSelected(dashboardPanel.getTreeMenu()));
        loadTreeMenu();
        mainFrame.showViewCardPanel(ConstantParams.DASHBOARD_PANEL);
    }

    private void loadTreeMenu() {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("#");
        treeMenuService.createNodes(root);
        JTree jTree = dashboardPanel.getTreeMenu();
        jTree.setModel(new DefaultTreeModel(root));
    }

    private void showMenu() {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode)
                       dashboardPanel.getTreeMenu().getLastSelectedPathComponent();
        
        Optional<DefaultMutableTreeNode> nodeOptional = Optional.ofNullable(node);
        if(!nodeOptional.isPresent())
            return;
        
        if (node.isLeaf()) {
            Object nodeInfo = node.getUserObject();
            TreeMenu menu = (TreeMenu) nodeInfo;
            
            try {
                JTabbedPane tabbedPane = dashboardPanel.getTabbedPaneRight();
                int indexTab = getTabbedIndex(tabbedPane , menu.getName());
                
                if(indexTab > -1){
                    tabbedPane.setSelectedIndex(indexTab);
                }else{
                    Class classe = Class.forName(menu.getPath());
                    Object obj = ApplicationContextHolder.getBean(classe);
                    Method method = classe.getDeclaredMethod("prepareAndGetPanel", (Class[]) null);
                    JPanel panel = (JPanel) method.invoke(obj, (Object[]) null);

                    tabbedPane.addTab(menu.getName(), panel);
                    tabbedPane.setSelectedIndex(tabbedPane.getTabCount()-1);
                }
                   
            } catch (Exception ex) {
                log.error("Error Get Class Not Found =>{}",ex.getMessage());
            }
           
        }
    }

    private int getTabbedIndex(JTabbedPane tabbedPane, String name) {
        for(int i=0; i<tabbedPane.getTabCount(); i++){
            if(tabbedPane.getTitleAt(i).equalsIgnoreCase(name))
                return i;
        }
        return -1;
    }

    private MouseListener releaseTreeSelected(final JTree treeMenu) {
        return new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(treeMenu.getRowForLocation(e.getX(),e.getY()) == -1) {
                    treeMenu.clearSelection();
                }
            }

        };
    }
}
