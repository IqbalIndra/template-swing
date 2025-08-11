/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.learn.shirologin.ui.swa.view.modal;

import com.learn.shirologin.model.AlternativeDataSource;
import com.learn.shirologin.ui.base.combobox.CheckedCombobox;
import com.learn.shirologin.ui.swa.model.*;
import com.learn.shirologin.util.Borders;
import com.learn.shirologin.util.IOFile;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.miginfocom.swing.MigLayout;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.util.Optional;

/**
 *
 * @author KBDSI-IQBAL
 */
@Component
@RequiredArgsConstructor
public class AlternativeDataSourceFormPanel extends JPanel{
    private final TahunAjaranComboBoxModel tahunAjaranComboBoxModel;
    private final JurusanComboBoxModel jurusanComboBoxModel;
    private final KelasComboBoxModel kelasComboBoxModel;
    private final CriteriaComboBoxModel criteriaComboBoxModel;
    private final IOFile ioFile;
    private JLabel kodeLbl;
    private JLabel tahunAjaranLbl;
    private JLabel jurusanLbl;
    private JLabel kelasLbl;
    private JLabel dataSourceLbl;
    private JLabel criteriaLbl;
    @Getter
    private JLabel dataSourceFilenameLbl;
    private JTextField kodeTxt;
    private JComboBox tahunAjaranComboBox;
    private JComboBox jurusanComboBox;
    private JComboBox kelasComboBox;
    private JComboBox criteriaCombobox;
    @Getter
    private JButton btnUploadDataSource;
    @Getter
    private JFileChooser jFileChooser;
    private AlternativeDataSource entity;
    @Getter
    private JButton loadBtn;
    @Getter
    private JButton loadConventionBtn;
    @Getter
    private JButton loadNormalizationBtn;
    @Getter
    private JButton loadRankBtn;
    @Getter
    private JTable tableAlternativeDetail;
    @Getter
    private JTable tableAlternativeConvention;
    @Getter
    private JTable tableAlternativeNormalization;
    @Getter
    private JTable tableAlternativeRankMatch;
    @Getter
    private JTabbedPane jTabbedPane;
    @Getter
    private final AlternativeDetailTableModel alternativeDetailTableModel;
    @Getter
    private final AlternativeConventionTableModel alternativeConventionTableModel;
    @Getter
    private final AlternativeNormalizationTableModel aLternativeNormalizationTableModel;


    @PostConstruct
    private void preparePanel(){
        setPanelUp();
        initComponents();
    }

    private void initComponents() {
        kodeLbl = new JLabel("Kode:");
        tahunAjaranLbl = new JLabel("Tahun Ajaran:");
        jurusanLbl = new JLabel("Jurusan:");
        kelasLbl = new JLabel("Kelas:");
        criteriaLbl = new JLabel("Criteria:");
        dataSourceLbl = new JLabel("");
        dataSourceFilenameLbl = new JLabel("");
        btnUploadDataSource = new JButton("...");
        kodeTxt = new JTextField(50);
        tahunAjaranComboBox = new JComboBox<>(tahunAjaranComboBoxModel);
        jurusanComboBox = new JComboBox<>(jurusanComboBoxModel);
        kelasComboBox = new JComboBox<>(kelasComboBoxModel);
        criteriaCombobox = new CheckedCombobox(criteriaComboBoxModel);
        jFileChooser = new JFileChooser();
        jFileChooser.setFileFilter(new FileNameExtensionFilter("Comma Separated Values (*.csv)", "csv"));
        loadBtn = new JButton("Lanjut");
        loadConventionBtn = new JButton("Lanjut ke Convention");
        loadNormalizationBtn = new JButton("Lanjut ke Normalization");
        loadRankBtn = new JButton("Hasil");

        jTabbedPane = new JTabbedPane();
        jTabbedPane.putClientProperty("JTabbedPane.tabClosable", false);
        jTabbedPane.addTab("Tab 1",getDetailAlternativePanel());
        jTabbedPane.addTab("Tab 2", getAlternativePanel());
        jTabbedPane.addTab("Tab 3", getAlternativeConventionPanel());
        jTabbedPane.addTab("Tab 4", getAlternativeNormalizationPanel());
        jTabbedPane.addTab("Tab 5", getAlternativeRankMatchPanel());


        add(jTabbedPane);
    }

    private void setPanelUp() {
        setBorder(Borders.createEmptyBorder());
        setLayout(new MigLayout("fill"));
    }
    
    public AlternativeDataSource getEntityFromForm(){
        Optional<AlternativeDataSource> optionalEntity = Optional.ofNullable(entity);
        if(!optionalEntity.isPresent()){
            entity = AlternativeDataSource.of().build();
        }

        entity.setCode(kodeTxt.getText());
        entity.setMajor(jurusanComboBoxModel.getSelectedItem());
        entity.setSchoolYear(tahunAjaranComboBoxModel.getSelectedItem());
        entity.setClassRoom(kelasComboBoxModel.getSelectedItem());
        entity.setFileSource(jFileChooser.getSelectedFile());
        entity.setFilename(jFileChooser.getSelectedFile().getName());


        return entity;
    }

    public void clearForm(){
        this.entity = null;

        kodeTxt.setText(Strings.EMPTY);
        jurusanComboBox.setSelectedIndex(0);
        tahunAjaranComboBox.setSelectedIndex(0);
        kelasComboBox.setSelectedIndex(0);
        dataSourceFilenameLbl.setText("Tidak ada file");
    }

    public void enabledComponent(boolean enabled){
        kodeTxt.setEditable(enabled);
        kodeTxt.setEnabled(enabled);
        jurusanComboBox.setEnabled(enabled);
        tahunAjaranComboBox.setEnabled(enabled);
        kelasComboBox.setEnabled(enabled);
        btnUploadDataSource.setEnabled(enabled);
    }

    public void setEntityToForm(AlternativeDataSource alternativeDataSource){
        this.entity = alternativeDataSource;

        kodeTxt.setText(entity.getCode());
        jurusanComboBox.setSelectedItem(entity.getMajor());
        kelasComboBox.setSelectedItem(entity.getClassRoom());
        tahunAjaranComboBox.setSelectedItem(entity.getSchoolYear());
        dataSourceFilenameLbl.setText(entity.getFilename());

        File file = ioFile.getUploadFile(alternativeDataSource.getFilename());
        jFileChooser.setSelectedFile(file);

        enabledComponent(false);
    }


    private JPanel getAlternativePanel(){
        JPanel panel = new JPanel();
        panel.setLayout(new MigLayout("wrap 1","[grow]"));
        panel.add(createTableAlternative(), "grow,wrap");
        panel.add(loadConventionBtn, "align center, wrap");
        return panel;
    }

    private JPanel getAlternativeConventionPanel(){
        JPanel panel = new JPanel();
        panel.setLayout(new MigLayout("wrap 1","[grow]"));
        panel.add(createTableAlternativeConvention(), "grow,wrap");
        panel.add(loadNormalizationBtn, "align center, wrap");
        return panel;
    }

    private JPanel getAlternativeNormalizationPanel(){
        JPanel panel = new JPanel();
        panel.setLayout(new MigLayout("wrap 1","[grow]"));
        panel.add(createTableAlternativeNormalization(), "grow,wrap");
        panel.add(loadRankBtn, "align center, wrap");
        return panel;
    }

    private JPanel getAlternativeRankMatchPanel(){
        JPanel panel = new JPanel();
        panel.setLayout(new MigLayout("wrap 1","[grow]"));
        panel.add(createTableAlternativeRankMatch(), "grow");
        return panel;
    }

    private JPanel getDetailAlternativePanel(){
        JPanel panel = new JPanel();
        panel.setLayout(new MigLayout("wrap 2","[] [grow]","[] [grow]"));

        panel.add(kodeLbl,"split 2,sg a");
        panel.add(kodeTxt,"pushx,growx,wrap");
        panel.add(tahunAjaranLbl,"split 2,sg a");
        panel.add(tahunAjaranComboBox,"pushx,growx,wrap");
        panel.add(jurusanLbl,"split 2, sg a");
        panel.add(jurusanComboBox,"pushx,growx,wrap");
        panel.add(kelasLbl,"split 2, sg a");
        panel.add(kelasComboBox,"pushx,growx,wrap");
        panel.add(dataSourceLbl,"split 3, sg a");
        panel.add(dataSourceFilenameLbl,"pushx,growx");
        panel.add(btnUploadDataSource, "wrap 10");
        panel.add(criteriaLbl, "split 2, sg a");
        panel.add(criteriaCombobox, "pushx,growx,wrap");
        panel.add(loadBtn, "align center,wrap");

        return panel;
    }

    private JScrollPane createTableAlternative() {
        tableAlternativeDetail = new JTable();
        tableAlternativeDetail.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableAlternativeDetail.setFillsViewportHeight(true);

        return new JScrollPane(tableAlternativeDetail);
    }

    private JScrollPane createTableAlternativeConvention() {
        tableAlternativeConvention = new JTable();
        tableAlternativeConvention.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableAlternativeConvention.setFillsViewportHeight(true);

        return new JScrollPane(tableAlternativeConvention);
    }

    private JScrollPane createTableAlternativeNormalization() {
        tableAlternativeNormalization = new JTable();
        tableAlternativeNormalization.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableAlternativeNormalization.setFillsViewportHeight(true);

        return new JScrollPane(tableAlternativeNormalization);
    }

    private JScrollPane createTableAlternativeRankMatch() {
        tableAlternativeRankMatch = new JTable();
        tableAlternativeRankMatch.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableAlternativeRankMatch.setFillsViewportHeight(true);

        return new JScrollPane(tableAlternativeRankMatch);
    }
}
