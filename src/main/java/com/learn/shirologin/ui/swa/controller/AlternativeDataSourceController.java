/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.learn.shirologin.ui.swa.controller;

import com.learn.shirologin.model.AlternativeDataSource;
import com.learn.shirologin.model.UserInfo;
import com.learn.shirologin.service.AlternativeDataSourceService;
import com.learn.shirologin.ui.base.controller.AbstractPanelController;
import com.learn.shirologin.ui.swa.model.AlternativeDataSourceTableModel;
import com.learn.shirologin.ui.swa.model.JurusanComboBoxModel;
import com.learn.shirologin.ui.swa.model.KelasComboBoxModel;
import com.learn.shirologin.ui.swa.model.TahunAjaranComboBoxModel;
import com.learn.shirologin.ui.swa.view.AlternativeDataSourceTablePaginationPanel;
import com.learn.shirologin.ui.swa.view.modal.AlternativeDataSourceFormDialog;
import com.learn.shirologin.ui.swa.view.modal.AlternativeDataSourceFormPanel;
import com.learn.shirologin.ui.user.model.UserPaginationComboBoxModel;
import com.learn.shirologin.ui.user.view.modal.UserInfoFormPanel;
import com.learn.shirologin.util.IOFile;
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
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.time.Year;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author KBDSI-IQBAL
 */
@RequiredArgsConstructor
@Controller
@Slf4j
public class AlternativeDataSourceController extends AbstractPanelController{
    private final AlternativeDataSourceTablePaginationPanel alternativeDataSourceTablePaginationPanel;
    private final AlternativeDataSourceFormDialog alternativeDataSourceFormDialog;
    private final AlternativeDataSourceTableModel alternativeDataSourceTableModel;
    private final UserPaginationComboBoxModel userPaginationComboBoxModel;
    private final AlternativeDataSourceService alternativeDataSourceService;
    private final IOFile ioFile;
    private Page<AlternativeDataSource> pageAlternativeDataSource;
    private final TahunAjaranComboBoxModel tahunAjaranComboBoxModel;
    private final KelasComboBoxModel kelasComboBoxModel;
    private final JurusanComboBoxModel jurusanComboBoxModel;
    
    @PostConstruct
    private void prepareListeners(){
        AlternativeDataSourceFormPanel alternativeDataSourceFormPanel = alternativeDataSourceFormDialog.getAlternativeDataSourceFormPanel();

        registerAction(alternativeDataSourceTablePaginationPanel.getBtnFirst(), (e) -> showFirstData());
        registerAction(alternativeDataSourceTablePaginationPanel.getBtnLast(), (e) -> showLastData());
        registerAction(alternativeDataSourceTablePaginationPanel.getBtnNext(), (e) -> showNextData());
        registerAction(alternativeDataSourceTablePaginationPanel.getBtnPrevious(), (e) -> showPreviousData());
        registerAction(alternativeDataSourceTablePaginationPanel.getCbxPagePerSize(), this::showDataPerPageSize);
        registerAction(alternativeDataSourceTablePaginationPanel.getBtnNew(), (e) -> showNewData());
        registerAction(alternativeDataSourceTablePaginationPanel.getBtnDelete(), this::deleteSelectedData);
        registerAction(alternativeDataSourceFormPanel.getBtnUploadDataSource(), (e)-> uploadDataSource());
        registerAction(alternativeDataSourceFormPanel.getSaveBtn(), this::saveAlternativeDataSource);
        registerAction(alternativeDataSourceFormPanel.getCancelBtn(), (e) -> cancelAlternativeDataSource());

        registerMouseListener(alternativeDataSourceTablePaginationPanel.getTableAlternativeDataSource(), onClickedTableUser());
    }

    private void uploadDataSource() {
        JFileChooser fileChooser = alternativeDataSourceFormDialog.getAlternativeDataSourceFormPanel().getJFileChooser();
        int returnValue = fileChooser.showOpenDialog(alternativeDataSourceFormDialog);
        JLabel labelUpload = alternativeDataSourceFormDialog.getAlternativeDataSourceFormPanel().getDataSourceFilenameLbl();

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            labelUpload.setText("File terpilih: " + selectedFile.getName());
        } else {
            labelUpload.setText("Tidak Ada file.");
        }
    }

    private void deleteSelectedData(ActionEvent actionEvent) {
        int index = alternativeDataSourceTablePaginationPanel.getTableAlternativeDataSource().getSelectedRow();
        if(index == -1){
            JOptionPane.showMessageDialog(alternativeDataSourceTablePaginationPanel,
                    "Please selected data !","Warning",JOptionPane.WARNING_MESSAGE);
            return;
        }

        int choose = JOptionPane.showConfirmDialog(alternativeDataSourceTablePaginationPanel,"Are you sure want to delete this data?","Warning", JOptionPane.OK_CANCEL_OPTION);
        if(choose == JOptionPane.OK_OPTION){
            AlternativeDataSource dataByRow = alternativeDataSourceTableModel.getDataByRow(index);
            alternativeDataSourceService.delete(dataByRow);
            alternativeDataSourceTableModel.removeData(index);
        }
    }

    private void showNewData() {
        alternativeDataSourceFormDialog.getAlternativeDataSourceFormPanel().clearForm();
        alternativeDataSourceFormDialog.getAlternativeDataSourceFormPanel().enabledComponent(true);
        alternativeDataSourceFormDialog.getAlternativeDataSourceFormPanel().getSaveBtn().setText("Save");
        alternativeDataSourceFormDialog.setLocationRelativeTo(alternativeDataSourceTablePaginationPanel);
        alternativeDataSourceFormDialog.pack();
        alternativeDataSourceFormDialog.setVisible(true);
    }

    private void saveAlternativeDataSource(ActionEvent e) {
        JButton button = (JButton) e.getSource();

        if(!button.getText().equalsIgnoreCase("save")){
            alternativeDataSourceFormDialog.getAlternativeDataSourceFormPanel().enabledComponent(true);
            button.setText("Save");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(alternativeDataSourceFormDialog,"Are you want to save this data ?","Confirm",JOptionPane.OK_CANCEL_OPTION);
        if(confirm == JOptionPane.OK_OPTION){
            AlternativeDataSourceFormPanel alternativeDataSourceFormPanel = alternativeDataSourceFormDialog.getAlternativeDataSourceFormPanel();
            AlternativeDataSource alternativeDataSource = alternativeDataSourceFormPanel.getEntityFromForm();

            if(!ObjectUtils.isEmpty(alternativeDataSource.getId())){
                alternativeDataSourceService.update(alternativeDataSource);
                alternativeDataSourceTableModel.updateData(
                        alternativeDataSourceTablePaginationPanel
                                .getTableAlternativeDataSource()
                                .getSelectedRow(),
                        alternativeDataSource);
            }else{
                alternativeDataSourceService.save(alternativeDataSource);
                alternativeDataSourceTableModel.addData(alternativeDataSource);
            }

            //upload file immediately after save or update data to DB
            ioFile.createUploadFile(alternativeDataSource.getFileSource(),
                    alternativeDataSource.getFilename());

            cancelAlternativeDataSource();
        }

    }

    private void cancelAlternativeDataSource() {
        alternativeDataSourceFormDialog.getAlternativeDataSourceFormPanel().clearForm();
        alternativeDataSourceFormDialog.getAlternativeDataSourceFormPanel().getSaveBtn().setText("Save");
        alternativeDataSourceFormDialog.dispose();
    }

    private void showViewData(AlternativeDataSource alternativeDataSource){
        JButton jButton = alternativeDataSourceFormDialog.getAlternativeDataSourceFormPanel().getSaveBtn();
        jButton.setText("Edit");

        alternativeDataSourceFormDialog.getAlternativeDataSourceFormPanel().setEntityToForm(alternativeDataSource);
        alternativeDataSourceFormDialog.setLocationRelativeTo(alternativeDataSourceTablePaginationPanel);
        alternativeDataSourceFormDialog.pack();
        alternativeDataSourceFormDialog.setVisible(true);
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
        loadTahunAjaranComboBox();
        loadKelasComboBox();
        loadJurusanComboBox();
        return alternativeDataSourceTablePaginationPanel;
    }

    private void loadJurusanComboBox() {
        jurusanComboBoxModel.clear();
        jurusanComboBoxModel.addElements(Arrays.asList("TKJ","TI","Adm.Perkantoran"));
    }

    private void loadKelasComboBox() {
        kelasComboBoxModel.clear();
        kelasComboBoxModel.addElements(Arrays.asList("X","XI","XII"));
    }

    private void loadTahunAjaranComboBox() {
        tahunAjaranComboBoxModel.clear();
        int year = Year.now().getValue();
        int end = year + 5;
        while(year++ < end)
            tahunAjaranComboBoxModel.addElement(year-1+"/"+year);
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
                    showViewData(alternativeDataSource);
                }
            }
        };
    }
}
