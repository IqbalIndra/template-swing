/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.learn.shirologin.domain.menu.service;

import com.learn.shirologin.domain.menu.model.TreeMenu;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.tree.DefaultMutableTreeNode;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

/**
 *
 * @author KBDSI-IQBAL
 */
@Service
public class TreeMenuServiceImpl implements TreeMenuService{

    @Override
    public DefaultMutableTreeNode createNodes() {
        List<TreeMenu> treeMenus = new ArrayList<>();

        treeMenus.add(new TreeMenu(
                "C001",
                "User",
                "",
                "com.learn.shirologin.ui.user.controller.UserController"
        ));
        treeMenus.add(new TreeMenu(
                "C002",
                "SWA",
                "",
                "null"
        ));

        treeMenus.add(new TreeMenu(
                "GC001",
                "Kriteria",
                "C002",
                "com.learn.shirologin.ui.swa.controller.CriteriaController"
        ));
        treeMenus.add(new TreeMenu(
                "GC002",
                "Sub Kriteria",
                "C002",
                "com.learn.shirologin.ui.swa.controller.SubCriteriaController"
        ));
        treeMenus.add(new TreeMenu(
                "GC003",
                "Perhitungan Peringkat",
                "C002",
                "com.learn.shirologin.ui.swa.controller.AlternativeDataSourceController"
        ));
        DefaultMutableTreeNode root = new DefaultMutableTreeNode();
        Map<String,DefaultMutableTreeNode> treeMap = new HashMap<>();

        for(TreeMenu menu : treeMenus){
            treeMap.put(menu.getCode(), new DefaultMutableTreeNode(menu, true));
        }

        for(TreeMenu menu : treeMenus){
            if(ObjectUtils.isEmpty(menu.getParentCode()) || menu.getParentCode().isEmpty()){
                DefaultMutableTreeNode child = treeMap.get(menu.getCode());
                root.add(child);
            }
            else {
                DefaultMutableTreeNode parent = treeMap.get(menu.getParentCode());
                if(!ObjectUtils.isEmpty(parent))
                    parent.add(treeMap.get(menu.getCode()));
            }
        }

        return root;
    }
    
}
