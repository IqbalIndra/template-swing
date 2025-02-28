/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.learn.shirologin.domain.menu.service;

import com.learn.shirologin.domain.menu.model.TreeMenu;
import java.util.ArrayList;
import java.util.List;
import javax.swing.tree.DefaultMutableTreeNode;
import org.springframework.stereotype.Service;

/**
 *
 * @author KBDSI-IQBAL
 */
@Service
public class TreeMenuServiceImpl implements TreeMenuService{

    @Override
    public void createNodes(DefaultMutableTreeNode root) {
        List<TreeMenu> treeMenus = new ArrayList<>();
        treeMenus.add(new TreeMenu("C001", "Dashboard", "P001","com.learn.shirologin.ui.dashboard.view.DashboardPanel"));
        treeMenus.add(new TreeMenu("C002", "Login", "P001","com.learn.shirologin.ui.login.view.LoginPanel"));
        treeMenus.add(new TreeMenu("C003", "User", "P001","com.learn.shirologin.ui.user.view.UserPanel"));
        
        TreeMenu treeMenuFirst = treeMenus.get(0);
        String parentCode = treeMenuFirst.getCode();
        DefaultMutableTreeNode parent = new DefaultMutableTreeNode(treeMenuFirst.getParentCode(),true);
        for (TreeMenu treeMenu : treeMenus) {
            if(!treeMenu.getParentCode().equalsIgnoreCase(parentCode)){
                parent = new DefaultMutableTreeNode(treeMenu.getParentCode(),true);
                root.add(parent);
                parentCode = treeMenu.getParentCode();
            }
            parent.add(new DefaultMutableTreeNode(treeMenu));
        }
        
    }
    
}
