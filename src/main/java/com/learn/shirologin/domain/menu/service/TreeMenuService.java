/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.learn.shirologin.domain.menu.service;

import com.learn.shirologin.domain.menu.model.TreeMenu;
import java.util.List;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author KBDSI-IQBAL
 */
public interface TreeMenuService {
    void createNodes(DefaultMutableTreeNode root);
}
