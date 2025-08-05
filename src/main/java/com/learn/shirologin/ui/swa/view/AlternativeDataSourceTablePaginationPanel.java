/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.learn.shirologin.ui.swa.view;

import com.learn.shirologin.ui.swa.model.AlternativeDataSourceTableModel;
import com.learn.shirologin.ui.user.model.UserPaginationComboBoxModel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.miginfocom.swing.MigLayout;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;

/**
 *
 * @author KBDSI-IQBAL
 */
@Component
@Getter
@RequiredArgsConstructor
public class AlternativeDataSourceTablePaginationPanel extends JPanel{
    private JButton btnLast;
    private JButton btnNext;
    private JComboBox<Integer> cbxPagePerSize;
    private JButton btnPrevious;
    private JButton btnFirst;
    private JButton btnNew;
    private JButton btnDelete;
    private JButton btnViewReport;
    private JTable tableAlternativeDataSource;
    private final AlternativeDataSourceTableModel alternativeDataSourceTableModel;
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
        tableAlternativeDataSource = new JTable(alternativeDataSourceTableModel);
        tableAlternativeDataSource.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableAlternativeDataSource.setFillsViewportHeight(true);
        
        JScrollPane paneWithTable = new JScrollPane(tableAlternativeDataSource);
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
