/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.learn.shirologin.ui.user.view;

import com.learn.shirologin.model.UserInfo;
import com.learn.shirologin.service.UserInfoService;
import com.learn.shirologin.ui.user.model.UserTableModel;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import lombok.Getter;
import net.miginfocom.swing.MigLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author KBDSI-IQBAL
 */
@Component
@Getter
public class UserPanel extends JPanel{
    private JButton btnLast;
    private JButton btnNext;
    private JComboBox<String> cbxPagePerSize;
    private JButton btnPrevious;
    private JButton btnFirst;
    private JButton btnNew;
    private JTable tableUser;
    
    @Autowired private UserInfoService userInfoService;
    @Autowired private UserTableModel userTableModel;
    
    @PostConstruct
    private void preparePanel(){
        setPanel();
    }

    private void setPanel() {
        MigLayout migLayout = new MigLayout("debug, fill");
        setLayout(migLayout);
        JPanel panelPagination = createPanelPagination();
       
        add(panelPagination, "dock north");
        add(createTable(), "dock center");
        
    }

    private JPanel createPanelPagination() {
        JPanel panel = new JPanel();
        MigLayout migLayout = new MigLayout("wrap 2","[grow][]");
        panel.setLayout(migLayout);
        
        JPanel panelLeft = createPanelLeftPagination();
        panel.add(panelLeft,"left");
        panel.add(btnNew,"right");
       
        return panel;
    }
    
    private JPanel createPanelTable(){
        JPanel panel = new JPanel();
        panel.setLayout(new MigLayout("fill"));
        JScrollPane scrollPane = createTable();
        panel.add(scrollPane,"dock center");
        
       return panel;
    }

    private JScrollPane createTable() {
        tableUser = new JTable();
        tableUser.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableUser.setFillsViewportHeight(true);
        
        List<UserInfo> userInfos = userInfoService.getAllUserInfo();
        
        userTableModel.addDatas(userInfos);
        tableUser.setModel(userTableModel);
        
        JScrollPane paneWithTable = new JScrollPane(tableUser);
        return paneWithTable;
    }

    private JPanel createPanelLeftPagination() {
        JPanel panel = new JPanel();
        btnLast = new JButton("Last");
        btnNext = new JButton("Next");
        cbxPagePerSize = new JComboBox<>();
        btnPrevious = new JButton("Previous");
        btnFirst = new JButton("First");
        btnNew = new JButton("New");
        
        panel.add(btnLast);
        panel.add(btnNext);
        panel.add(cbxPagePerSize);
        panel.add(btnPrevious);
        panel.add(btnFirst);
        
        return panel;
    }
    
}
