/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.learn.shirologin.ui.swa.view.modal;

import com.learn.shirologin.model.Criteria;
import com.learn.shirologin.model.CriteriaType;
import com.learn.shirologin.model.OperatorType;
import com.learn.shirologin.model.SubCriteria;
import com.learn.shirologin.ui.swa.model.CriteriaSingleComboBoxModel;
import com.learn.shirologin.ui.swa.model.CriteriaTypeComboBoxModel;
import com.learn.shirologin.ui.swa.model.OperatorTypeComboBoxModel;
import com.learn.shirologin.util.Borders;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.miginfocom.swing.MigLayout;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.text.NumberFormat;

/**
 *
 * @author KBDSI-IQBAL
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class SubCriteriaFormPanel extends JPanel{
    private final CriteriaSingleComboBoxModel criteriaSingleComboBoxModel;
    private final OperatorTypeComboBoxModel operatorTypeComboBoxModel;
    private JLabel nameLbl;
    private JLabel criteriaNameLbl;
    private JLabel operatorLbl;
    private JLabel weightLbl;
    private JLabel minLbl;
    private JLabel maxLbl;
    private JTextField nameTxt;
    private JFormattedTextField weightTxt;
    private JFormattedTextField minTxt;
    private JFormattedTextField maxTxt;
    private JComboBox criteriaComboBox;
    @Getter
    private JComboBox operatorTypeComboBox;
    private SubCriteria entity;

    
    @PostConstruct
    private void preparePanel(){
        setPanelUp();
        initComponents();
    }

    private void initComponents() {
        nameLbl = new JLabel("Nama:");
        criteriaNameLbl = new JLabel("Kriteria:");
        weightLbl = new JLabel("Bobot:");
        operatorLbl = new JLabel("Operator:");
        minLbl = new JLabel("Minimal Bobot:");
        maxLbl = new JLabel("Maksimal Bobot:");
        nameTxt = new JTextField(20);

        NumberFormat decimalFormat = NumberFormat.getNumberInstance();
        decimalFormat.setMinimumFractionDigits(2);
        decimalFormat.setMaximumFractionDigits(2);
        decimalFormat.setGroupingUsed(false);

        weightTxt = new JFormattedTextField(decimalFormat);
        weightTxt.setColumns(20);
        minTxt = new JFormattedTextField(decimalFormat);
        minTxt.setColumns(20);
        maxTxt = new JFormattedTextField(decimalFormat);
        maxTxt.setColumns(20);

        criteriaComboBox = new JComboBox<>(criteriaSingleComboBoxModel);
        operatorTypeComboBox = new JComboBox<>(operatorTypeComboBoxModel);

        add(criteriaNameLbl,"left");
        add(criteriaComboBox,"pushx,growx");
        add(nameLbl,"left");
        add(nameTxt,"pushx,growx");
        add(operatorLbl,"left");
        add(operatorTypeComboBox,"pushx,growx");
        add(weightLbl,"left");
        add(weightTxt,"pushx,growx");
        add(minLbl,"left");
        add(minTxt,"pushx,growx");
        add(maxLbl,"left");
        add(maxTxt,"pushx,growx");
    }

    private void setPanelUp() {
        setBorder(Borders.createEmptyBorder());
        setLayout(new MigLayout("wrap 2","[]10[]"));
    }
    
    public SubCriteria getEntityFromForm(){
        if(entity == null){
            entity = SubCriteria.of().build();
        }

        entity.setName(nameTxt.getText());
        entity.setCriteria(criteriaSingleComboBoxModel.getSelectedItem());
        entity.setOperator(OperatorType.valueOfType(operatorTypeComboBoxModel.getSelectedItem()));
        entity.setWeight(Double.valueOf(weightTxt.getText()));
        entity.setMinValue(Double.valueOf(minTxt.getText()));
        entity.setMaxValue(Double.valueOf(maxTxt.getText()));

        return entity;
    }

    public void clearForm(){
        this.entity = null;

        nameTxt.setText(Strings.EMPTY);
        weightTxt.setText(Strings.EMPTY);
        minTxt.setText(Strings.EMPTY);
        maxTxt.setText(Strings.EMPTY);
        criteriaComboBox.setSelectedIndex(0);
        operatorTypeComboBox.setSelectedIndex(0);
    }

    public void enabledComponent(boolean enabled){
        nameTxt.setEditable(enabled);
        nameTxt.setEnabled(enabled);
        weightTxt.setEditable(enabled);
        weightTxt.setEnabled(enabled);
        minTxt.setEditable(enabled);
        minTxt.setEnabled(enabled);
        maxTxt.setEditable(enabled);
        maxTxt.setEnabled(enabled);
        criteriaComboBox.setEnabled(enabled);
        operatorTypeComboBox.setEnabled(enabled);
    }

    public void setEntityToForm(SubCriteria info){
        this.entity = info;

        nameTxt.setText(entity.getName());
        weightTxt.setValue(entity.getWeight());
        minTxt.setValue(entity.getMinValue());
        maxTxt.setValue(entity.getMaxValue());
        criteriaSingleComboBoxModel.setSelectedItem(entity.getCriteria());
        operatorTypeComboBoxModel.setSelectedItem(entity.getOperator().getSymbol());

        enabledComponent(false);
    }

    public void setHandleOperatorType(){
        String operator = operatorTypeComboBoxModel.getSelectedItem();
        OperatorType type = OperatorType.valueOfType(operator);

        if(OperatorType.LESS_THAN.equals(type) || OperatorType.EQUALS.equals(type)){
            minTxt.setValue(0d);
            minTxt.setEditable(false);
            minTxt.setEnabled(false);
            maxTxt.setEditable(true);
            maxTxt.setEnabled(true);
        }else if(OperatorType.GREATER_THAN.equals(type)){
            maxTxt.setValue(0d);
            maxTxt.setEditable(false);
            maxTxt.setEnabled(false);
            minTxt.setEditable(true);
            minTxt.setEnabled(true);
        }else{
            minTxt.setEditable(true);
            minTxt.setEnabled(true);
            maxTxt.setEditable(true);
            maxTxt.setEnabled(true);
        }
    }
}
