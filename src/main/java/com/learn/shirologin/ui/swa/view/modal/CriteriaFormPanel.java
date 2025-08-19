/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.learn.shirologin.ui.swa.view.modal;

import com.learn.shirologin.model.Criteria;
import com.learn.shirologin.model.CriteriaType;
import com.learn.shirologin.model.UserInfo;
import com.learn.shirologin.ui.swa.model.CriteriaTypeComboBoxModel;
import com.learn.shirologin.util.Borders;
import lombok.RequiredArgsConstructor;
import net.miginfocom.swing.MigLayout;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.math.RoundingMode;
import java.text.NumberFormat;

/**
 *
 * @author KBDSI-IQBAL
 */
@Component
@RequiredArgsConstructor
public class CriteriaFormPanel extends JPanel{
    private final CriteriaTypeComboBoxModel criteriaTypeComboBoxModel;
    private JLabel nameLbl;
    private JLabel typeLbl;
    private JLabel weightLbl;
    private JTextField nameTxt;
    private JFormattedTextField weightTxt;
    private JComboBox criteriaTypeComboBox;
    private Criteria entity;

    
    @PostConstruct
    private void preparePanel(){
        setPanelUp();
        initComponents();
    }

    private void initComponents() {
        nameLbl = new JLabel("Nama:");
        typeLbl = new JLabel("Tipe:");
        weightLbl = new JLabel("Bobot:");
        nameTxt = new JTextField(20);

        NumberFormat decimalFormat = NumberFormat.getNumberInstance();
        decimalFormat.setMinimumFractionDigits(2);
        decimalFormat.setMaximumFractionDigits(2);
        decimalFormat.setGroupingUsed(false);

        weightTxt = new JFormattedTextField(decimalFormat);
        weightTxt.setColumns(20);

        criteriaTypeComboBox = new JComboBox<>(criteriaTypeComboBoxModel);
        
        add(nameLbl,"left");
        add(nameTxt,"pushx,growx");
        add(typeLbl,"left");
        add(criteriaTypeComboBox,"pushx,growx");
        add(weightLbl,"left");
        add(weightTxt,"pushx,growx");
    }

    private void setPanelUp() {
        setBorder(Borders.createEmptyBorder());
        setLayout(new MigLayout("wrap 2","[]10[]"));
    }
    
    public Criteria getEntityFromForm(){
        if(entity == null){
            entity = Criteria.of().build();
        }

        entity.setName(nameTxt.getText());
        entity.setType(CriteriaType.valueOfType(criteriaTypeComboBoxModel.getSelectedItem()));
        entity.setWeight(Double.valueOf(weightTxt.getText()));

        return entity;
    }

    public void clearForm(){
        this.entity = null;

        nameTxt.setText(Strings.EMPTY);
        weightTxt.setText(Strings.EMPTY);
        criteriaTypeComboBox.setSelectedIndex(0);
    }

    public void enabledComponent(boolean enabled){
        nameTxt.setEditable(enabled);
        nameTxt.setEnabled(enabled);
        weightTxt.setEditable(enabled);
        weightTxt.setEnabled(enabled);
        criteriaTypeComboBox.setEnabled(enabled);
    }

    public void setEntityToForm(Criteria info){
        this.entity = info;

        nameTxt.setText(entity.getName());
        weightTxt.setValue(entity.getWeight());
        criteriaTypeComboBoxModel.setSelectedItem(entity.getType().getName());


        enabledComponent(false);
    }
}
