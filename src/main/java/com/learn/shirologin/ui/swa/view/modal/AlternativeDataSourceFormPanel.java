/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.learn.shirologin.ui.swa.view.modal;

import com.learn.shirologin.model.AlternativeDataSource;
import com.learn.shirologin.model.StatusAlternative;
import com.learn.shirologin.model.UserInfo;
import com.learn.shirologin.ui.base.combobox.CheckedCombobox;
import com.learn.shirologin.ui.swa.model.*;
import com.learn.shirologin.ui.user.model.UserRoleComboBoxModel;
import com.learn.shirologin.util.Borders;
import com.learn.shirologin.util.IOFile;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
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
    private JButton saveBtn;
    @Getter
    private JButton cancelBtn;
    @Getter
    private JButton normalizeBtn;
    @Getter
    private JPanel panelAlternative;
    @Getter
    private JTable tableAlternativeDetail;
    @Getter
    private final AlternativeDetailTableModel alternativeDetailTableModel;

    
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

        saveBtn = new JButton("Save");
        cancelBtn = new JButton("Cancel");
        normalizeBtn = new JButton("Normalize");

        panelAlternative = getAlternativePanel();

        add(kodeLbl,"split 2,sg a");
        add(kodeTxt,"pushx,growx,wrap");
        add(tahunAjaranLbl,"split 2,sg a");
        add(tahunAjaranComboBox,"pushx,growx,wrap");
        add(jurusanLbl,"split 2, sg a");
        add(jurusanComboBox,"pushx,growx,wrap");
        add(kelasLbl,"split 2, sg a");
        add(kelasComboBox,"pushx,growx,wrap");
        add(dataSourceLbl,"split 3, sg a");
        add(dataSourceFilenameLbl,"pushx,growx");
        add(btnUploadDataSource, "wrap 10");
        add(criteriaLbl, "split 2, sg a");
        add(criteriaCombobox, "pushx,growx,wrap");
        add(saveBtn, "split 3, align center");
        add(cancelBtn, "align center");
        add(normalizeBtn, "align center, wrap");
        add(panelAlternative,"span 2, push , grow, wrap");
    }

    private void setPanelUp() {
        setBorder(Borders.createEmptyBorder());
        setLayout(new MigLayout("wrap 2","[] [grow]","[] [grow]"));
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

    public void showAlternativeDetailTable(AlternativeDetailTableModel alternativeDetailTableModel){
        tableAlternativeDetail.setModel(alternativeDetailTableModel);
        panelAlternative.setVisible(true);
    }

    private JPanel getAlternativePanel(){
        JPanel panel = new JPanel();
        panel.setLayout(new MigLayout("wrap 1","[grow]"));
        panel.add(createTable(), "grow");
        panel.setVisible(false);
        return panel;
    }

    private JScrollPane createTable() {
        tableAlternativeDetail = new JTable();
        tableAlternativeDetail.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableAlternativeDetail.setFillsViewportHeight(true);

        JScrollPane paneWithTable = new JScrollPane(tableAlternativeDetail);
        return paneWithTable;
    }
}
