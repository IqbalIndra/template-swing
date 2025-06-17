/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.learn.shirologin.ui.user.view;

import com.learn.shirologin.ui.user.model.UserPaginationComboBoxModel;
import com.learn.shirologin.ui.user.model.UserTableModel;
import javax.annotation.PostConstruct;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.miginfocom.swing.MigLayout;
import org.springframework.stereotype.Component;

/**
 *
 * @author KBDSI-IQBAL
 */
@Component
@Getter
@RequiredArgsConstructor
public class UserTablePaginationPanel extends JPanel{
    private JButton btnLast;
    private JButton btnNext;
    private JComboBox<Integer> cbxPagePerSize;
    private JButton btnPrevious;
    private JButton btnFirst;
    private JButton btnNew;
    private JButton btnDelete;
    private JButton btnViewReport;
    private JTable tableUser;
    private final UserTableModel userTableModel;
    private final UserPaginationComboBoxModel userPaginationComboBoxModel;
    
    @PostConstruct
    private void preparePanel(){
        setPanel();
        initComponent();
    }

    private void setPanel() {
        MigLayout migLayout = new MigLayout("fill");
        setLayout(migLayout);
    }

    private JPanel createPanelPagination() {
        JPanel panel = new JPanel();
        MigLayout migLayout = new MigLayout("wrap 4","[grow][][][]");
        panel.setLayout(migLayout);
        
        JPanel panelLeft = createPanelLeftPagination();
        panel.add(panelLeft,"left");
        panel.add(btnNew,"right");
        panel.add(btnDelete,"right");
        panel.add(btnViewReport,"right");

        return panel;
    }

    private JScrollPane createTable() {
        tableUser = new JTable(userTableModel);
        tableUser.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableUser.setFillsViewportHeight(true);
        
        JScrollPane paneWithTable = new JScrollPane(tableUser);
        return paneWithTable;
    }

    private JPanel createPanelLeftPagination() {
        JPanel panel = new JPanel();
        btnLast = new JButton("Last");
        btnNext = new JButton(">");
        cbxPagePerSize = new JComboBox(userPaginationComboBoxModel);
        
        btnPrevious = new JButton("<");
        btnFirst = new JButton("First");
        btnNew = new JButton("New");
        btnDelete = new JButton("Delete");
        btnViewReport = new JButton("View Report");

        panel.add(btnLast);
        panel.add(btnNext);
        panel.add(cbxPagePerSize);
        panel.add(btnPrevious);
        panel.add(btnFirst);
        
        return panel;
    }

    private void initComponent() {
        JPanel panelPagination = createPanelPagination();
       
        add(panelPagination, "dock north");
        add(createTable(), "dock center");
    }
    
}
