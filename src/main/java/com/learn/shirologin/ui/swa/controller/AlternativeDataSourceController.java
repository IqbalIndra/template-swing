/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.learn.shirologin.ui.swa.controller;

import com.learn.shirologin.model.AlternativeDataSource;
import com.learn.shirologin.service.AlternativeDataSourceService;
import com.learn.shirologin.ui.base.controller.AbstractPanelController;
import com.learn.shirologin.ui.swa.model.AlternativeDataSourceTableModel;
import com.learn.shirologin.ui.swa.view.AlternativeDataSourceTablePaginationPanel;
import com.learn.shirologin.ui.user.model.UserPaginationComboBoxModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;

/**
 *
 * @author KBDSI-IQBAL
 */
@RequiredArgsConstructor
@Controller
@Slf4j
public class AlternativeDataSourceController extends AbstractPanelController{
    private final AlternativeDataSourceTablePaginationPanel alternativeDataSourceTablePaginationPanel;
    private final AlternativeDataSourceTableModel alternativeDataSourceTableModel;
    private final UserPaginationComboBoxModel userPaginationComboBoxModel;
    private final AlternativeDataSourceService alternativeDataSourceService;
    private Page<AlternativeDataSource> pageAlternativeDataSource;
    
    @PostConstruct
    private void prepareListeners(){
        registerAction(alternativeDataSourceTablePaginationPanel.getBtnFirst(), (e) -> showFirstData());
        registerAction(alternativeDataSourceTablePaginationPanel.getBtnLast(), (e) -> showLastData());
        registerAction(alternativeDataSourceTablePaginationPanel.getBtnNext(), (e) -> showNextData());
        registerAction(alternativeDataSourceTablePaginationPanel.getBtnPrevious(), (e) -> showPreviousData());
        registerAction(alternativeDataSourceTablePaginationPanel.getCbxPagePerSize(), this::showDataPerPageSize);

        registerMouseListener(alternativeDataSourceTablePaginationPanel.getTableAlternativeDataSource(), onClickedTableUser());
    }



    private void showFirstData() {
        if(!pageAlternativeDataSource.isFirst()){
            loadEntities(PageRequest.of(0, pageAlternativeDataSource.getSize()));
        }
    }

    private void showLastData() {
        if(!pageAlternativeDataSource.isLast()){
            loadEntities(PageRequest.of(pageAlternativeDataSource.getTotalPages()-1, pageAlternativeDataSource.getSize()));
        }
    }

    private void showNextData() {
        if(pageAlternativeDataSource.hasNext()){
            loadEntities(pageAlternativeDataSource.nextPageable());
        }
    }

    private void showPreviousData() {
        if(pageAlternativeDataSource.hasPrevious()){
            loadEntities(pageAlternativeDataSource.previousPageable());
        }
    }


    private void loadEntities(Pageable pageable) {
        pageAlternativeDataSource = alternativeDataSourceService.getAllAlternativeDataSource(pageable);
        alternativeDataSourceTableModel.clearAll();
        alternativeDataSourceTableModel.addDatas(pageAlternativeDataSource.getContent());
        showVisibleButtonPagination(pageAlternativeDataSource);
    }

    @Override
    public JPanel prepareAndGetPanel() {
        Pageable pageable = PageRequest.of(0, 10);
        loadEntities(pageable);
        loadPaginationComboBox();
        return alternativeDataSourceTablePaginationPanel;
    }

    private void showVisibleButtonPagination(Page<AlternativeDataSource> pageAlternativeDataSource) {
        alternativeDataSourceTablePaginationPanel.getBtnPrevious().setEnabled(pageAlternativeDataSource.hasPrevious());
        alternativeDataSourceTablePaginationPanel.getBtnNext().setEnabled(pageAlternativeDataSource.hasNext());
        alternativeDataSourceTablePaginationPanel.getBtnFirst().setEnabled((!pageAlternativeDataSource.isFirst()));
        alternativeDataSourceTablePaginationPanel.getBtnLast().setEnabled((!pageAlternativeDataSource.isLast()));
    }

    private void loadPaginationComboBox() {
        userPaginationComboBoxModel.removeAllElements();
        userPaginationComboBoxModel.addElements(Arrays.asList(new Integer[] {10,20,30,50,100}));
    }

    private void showDataPerPageSize(ActionEvent e) {
        Integer pageSize = userPaginationComboBoxModel.getSelectedItem();
        if(!ObjectUtils.isEmpty(pageAlternativeDataSource) || !pageAlternativeDataSource.isEmpty()){
            Pageable pageable = PageRequest.of(pageAlternativeDataSource.getNumber(), pageSize);
            loadEntities(pageable);
        }
    }





    private MouseListener onClickedTableUser() {
        return new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                JTable table =(JTable) e.getSource();
                Point point = e.getPoint();
                int row = table.rowAtPoint(point);

                if (e.getClickCount() == 2 && table.getSelectedRow() != -1 && row != -1) {
                    AlternativeDataSource alternativeDataSource = alternativeDataSourceTableModel.getDataByRow(row);
                    log.info("Get Data Selected from table : ${}", alternativeDataSource);
                }
            }
        };
    }



}
