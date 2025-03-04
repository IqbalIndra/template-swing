/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.learn.shirologin.ui.base.model;

import java.util.List;
import javax.swing.DefaultComboBoxModel;

/**
 *
 * @author KBDSI-IQBAL
 */
public class DefaultAbstractComboBoxModel<T> extends DefaultComboBoxModel<T>{

    @Override
    public T getSelectedItem() {
        return (T) super.getSelectedItem(); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void addElements(List<T> elements){
        elements.forEach(this::addElement);
    }
    
    public void clear(){
        removeAllElements();
    }
    
    
}
