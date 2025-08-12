/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.learn.shirologin.ui.swa.controller;

import com.learn.shirologin.model.AlternativeDataSource;
import com.learn.shirologin.model.CriteriaItem;
import com.learn.shirologin.model.RatingMatch;
import com.learn.shirologin.service.AlternativeDataSourceService;
import com.learn.shirologin.service.CriteriaService;
import com.learn.shirologin.ui.base.controller.AbstractPanelController;
import com.learn.shirologin.ui.swa.calculation.AlternativeCalculation;
import com.learn.shirologin.ui.swa.model.*;
import com.learn.shirologin.ui.swa.view.AlternativeDataSourceTablePaginationPanel;
import com.learn.shirologin.ui.swa.view.modal.AlternativeDataSourceFormBtnPanel;
import com.learn.shirologin.ui.swa.view.modal.AlternativeDataSourceFormDialog;
import com.learn.shirologin.ui.swa.view.modal.AlternativeDataSourceFormPanel;
import com.learn.shirologin.ui.user.model.UserPaginationComboBoxModel;
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
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.time.Year;
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
    private final CriteriaService criteriaService;
    private final AlternativeCalculation alternativeCalculation;
    private final IOFile ioFile;
    private Page<AlternativeDataSource> pageAlternativeDataSource;
    private final TahunAjaranComboBoxModel tahunAjaranComboBoxModel;
    private final KelasComboBoxModel kelasComboBoxModel;
    private final JurusanComboBoxModel jurusanComboBoxModel;
    private final CriteriaComboBoxModel criteriaComboBoxModel;
    
    @PostConstruct
    private void prepareListeners(){
        AlternativeDataSourceFormPanel alternativeDataSourceFormPanel = alternativeDataSourceFormDialog.getAlternativeDataSourceFormPanel();
        AlternativeDataSourceFormBtnPanel alternativeDataSourceFormBtnPanel = alternativeDataSourceFormDialog.getAlternativeDataSourceFormBtnPanel();

        registerAction(alternativeDataSourceTablePaginationPanel.getBtnFirst(), (e) -> showFirstData());
        registerAction(alternativeDataSourceTablePaginationPanel.getBtnLast(), (e) -> showLastData());
        registerAction(alternativeDataSourceTablePaginationPanel.getBtnNext(), (e) -> showNextData());
        registerAction(alternativeDataSourceTablePaginationPanel.getBtnPrevious(), (e) -> showPreviousData());
        registerAction(alternativeDataSourceTablePaginationPanel.getCbxPagePerSize(), this::showDataPerPageSize);
        registerAction(alternativeDataSourceTablePaginationPanel.getBtnNew(), (e) -> showNewData());
        registerAction(alternativeDataSourceTablePaginationPanel.getBtnDelete(), this::deleteSelectedData);
        registerAction(alternativeDataSourceFormPanel.getBtnUploadDataSource(), (e)-> uploadDataSource());
        registerAction(alternativeDataSourceFormBtnPanel.getSaveBtn(), this::saveAlternativeDataSource);
        registerAction(alternativeDataSourceFormBtnPanel.getCancelBtn(), (e) -> cancelAlternativeDataSource());
        registerAction(alternativeDataSourceFormPanel.getLoadBtn(), (e) -> fillToTable());
        registerAction(alternativeDataSourceFormPanel.getLoadConventionBtn(), (e) -> tryConvention());
        registerAction(alternativeDataSourceFormPanel.getLoadNormalizationBtn(), (e) -> tryNormalization());
        registerAction(alternativeDataSourceFormPanel.getLoadRankBtn(), (e) -> tryRankMatch());

        registerMouseListener(alternativeDataSourceTablePaginationPanel.getTableAlternativeDataSource(), onClickedTableUser());
    }

    private void tryRankMatch() {
        AlternativeDataSourceFormPanel alternativeDataSourceFormPanel = alternativeDataSourceFormDialog.getAlternativeDataSourceFormPanel();
        AlternativeNormalizationTableModel alternativeNormalizationTableModel = alternativeDataSourceFormPanel.getALternativeNormalizationTableModel();
        AlternativeRatingMatchTableModel alternativeRatingMatchTableModel = alternativeDataSourceFormPanel.getAlternativeRatingMatchTableModel();


        List<RatingMatch> dataRanking = alternativeCalculation.tryToRating(criteriaComboBoxModel.getItemsSelected());
        alternativeRatingMatchTableModel.clearAll();
        alternativeRatingMatchTableModel.addDatas(dataRanking);
        alternativeDataSourceFormPanel.getTableAlternativeRankMatch().setModel(alternativeRatingMatchTableModel);

        AlternativeDataSourceFormPanel panel = alternativeDataSourceFormDialog.getAlternativeDataSourceFormPanel();
        panel.getJTabbedPane().setEnabledAt(panel.getJTabbedPane().getTabCount()-1, true);
        panel.getJTabbedPane().setSelectedIndex(panel.getJTabbedPane().getTabCount()-1);
    }

    private void fillToTable() {
        AlternativeDataSourceFormPanel alternativeDataSourceFormPanel = alternativeDataSourceFormDialog.getAlternativeDataSourceFormPanel();
        AlternativeDetailTableModel alternativeDetailTableModel = alternativeDataSourceFormPanel.getAlternativeDetailTableModel();
        AlternativeDataSource alternativeDataSource = alternativeDataSourceFormPanel.getEntityFromForm();

        alternativeDetailTableModel.clearAll();
        loadAlternativeDetailData(alternativeDetailTableModel,alternativeDataSource.getFileSource());
        alternativeDataSourceFormPanel.getTableAlternativeDetail().setModel(alternativeDetailTableModel);

        alternativeDataSourceFormPanel.getJTabbedPane().setEnabledAt(1,true);
        alternativeDataSourceFormPanel.getJTabbedPane().setSelectedIndex(1);
    }

    private void tryConvention() {
        AlternativeDataSourceFormPanel alternativeDataSourceFormPanel = alternativeDataSourceFormDialog.getAlternativeDataSourceFormPanel();
        AlternativeDetailTableModel alternativeDetailTableModel = alternativeDataSourceFormPanel.getAlternativeDetailTableModel();
        AlternativeConventionTableModel alternativeConventionTableModel = alternativeDataSourceFormPanel.getAlternativeConventionTableModel();

        List<List<Object>> dataConvention = alternativeCalculation.tryToConvention(criteriaComboBoxModel.getItemsSelected());
        alternativeConventionTableModel.clearAll();
        alternativeConventionTableModel.addDatas(dataConvention);
        alternativeConventionTableModel.setColumnLabel(alternativeDetailTableModel.getColumnLabels());
        alternativeDataSourceFormPanel.getTableAlternativeConvention().setModel(alternativeConventionTableModel);

        AlternativeDataSourceFormPanel panel = alternativeDataSourceFormDialog.getAlternativeDataSourceFormPanel();
        panel.getJTabbedPane().setEnabledAt(2,true);
        panel.getJTabbedPane().setSelectedIndex(2);
    }

    private void tryNormalization() {
        AlternativeDataSourceFormPanel alternativeDataSourceFormPanel = alternativeDataSourceFormDialog.getAlternativeDataSourceFormPanel();
        AlternativeConventionTableModel alternativeConventionTableModel = alternativeDataSourceFormPanel.getAlternativeConventionTableModel();
        AlternativeNormalizationTableModel alternativeNormalizationTableModel = alternativeDataSourceFormPanel.getALternativeNormalizationTableModel();


        List<List<Object>> dataNormalization = alternativeCalculation.tryToNormalization(criteriaComboBoxModel.getItemsSelected());
        alternativeNormalizationTableModel.clearAll();
        alternativeNormalizationTableModel.addDatas(dataNormalization);
        alternativeNormalizationTableModel.setColumnLabel(alternativeConventionTableModel.getColumnLabels());
        alternativeDataSourceFormPanel.getTableAlternativeNormalization().setModel(alternativeNormalizationTableModel);

        AlternativeDataSourceFormPanel panel = alternativeDataSourceFormDialog.getAlternativeDataSourceFormPanel();
        panel.getJTabbedPane().setEnabledAt(3, true);
        panel.getJTabbedPane().setSelectedIndex(3);
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
        alternativeDataSourceFormDialog.getAlternativeDataSourceFormBtnPanel().getSaveBtn().setText("Save");
        alternativeDataSourceFormDialog.setLocationRelativeTo(alternativeDataSourceTablePaginationPanel);
        alternativeDataSourceFormDialog.getAlternativeDataSourceFormPanel().getJTabbedPane().setSelectedIndex(0);
        alternativeDataSourceFormDialog.getAlternativeDataSourceFormPanel().getJTabbedPane().setEnabledAt(1,false);
        alternativeDataSourceFormDialog.getAlternativeDataSourceFormPanel().getJTabbedPane().setEnabledAt(2,false);
        alternativeDataSourceFormDialog.getAlternativeDataSourceFormPanel().getJTabbedPane().setEnabledAt(3,false);
        alternativeDataSourceFormDialog.getAlternativeDataSourceFormPanel().getJTabbedPane().setEnabledAt(4,false);
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
        }

    }

    private void loadAlternativeDetailData(AlternativeDetailTableModel alternativeDetailTableModel, File fileSource) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileSource));
            String firstLine = br.readLine().trim();
            String[] columnsName = firstLine.split(",");

            for(int i = 2; i<columnsName.length; i++){
                columnsName[i] = String.format("C%d",i-1);
            }

            alternativeDetailTableModel.setColumnLabel(columnsName);

            // get lines from txt file
            Object[] tableLines = br.lines().toArray();

            // extratct data from lines
            // set data to jtable model
            for(int i = 0; i < tableLines.length; i++)
            {
                String line = tableLines[i].toString().trim();
                Object[] dataRow = line.split(",");
                alternativeDetailTableModel.addData(Arrays.asList(dataRow));
            }

        } catch (Exception ex) {
            log.error("Error Populate data from file : {}",ex.getMessage());
        }
    }

    private void cancelAlternativeDataSource() {
        alternativeDataSourceFormDialog.getAlternativeDataSourceFormPanel().clearForm();
        alternativeDataSourceFormDialog.getAlternativeDataSourceFormBtnPanel().getSaveBtn().setText("Save");
        alternativeDataSourceFormDialog.dispose();
    }

    private void showViewData(AlternativeDataSource alternativeDataSource){
        AlternativeDataSourceFormPanel alternativeDataSourceFormPanel = alternativeDataSourceFormDialog.getAlternativeDataSourceFormPanel();

        JButton jButton = alternativeDataSourceFormDialog.getAlternativeDataSourceFormBtnPanel().getSaveBtn();
        jButton.setText("Edit");

        alternativeDataSourceFormPanel.setEntityToForm(alternativeDataSource);

        AlternativeDetailTableModel alternativeDetailTableModel = alternativeDataSourceFormPanel.getAlternativeDetailTableModel();
        loadAlternativeDetailData(alternativeDetailTableModel,alternativeDataSource.getFileSource());
        alternativeDataSourceFormPanel.getJTabbedPane().setSelectedIndex(0);

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
        loadCriteriaComboBox();
        return alternativeDataSourceTablePaginationPanel;
    }

    private void loadCriteriaComboBox() {
        criteriaComboBoxModel.clear();
        List<CriteriaItem> criteriaItems = criteriaService.findCriteriaItem();
        criteriaComboBoxModel.addElements(criteriaItems);
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
