/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.learn.shirologin.ui.base.model;

import com.learn.shirologin.model.CriteriaItem;
import com.learn.shirologin.ui.base.combobox.CheckItem;

import javax.swing.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 *
 * @author KBDSI-IQBAL
 */
public class DefaultAbstractCheckedComboBoxModel<E extends CheckItem> extends DefaultComboBoxModel<E>{

    @Override
    public E getSelectedItem() {
        return null;
    }
    
    public void addElements(List<E> elements){
        elements.forEach(this::addElement);
    }
    
    public void clear(){
        removeAllElements();
    }

    public List<E> getItemsSelected() {
        return IntStream.range(0,this.getSize())
                .mapToObj(this::getElementAt)
                .filter(CheckItem::isSelected)
                .collect(Collectors.toList());
    }

    public void setItemsSelected(List<E> items){
        Set<E> unique = new HashSet<>(items);
        IntStream.range(0, this.getSize())
                .mapToObj(this::getElementAt)
                .filter(unique::contains)
                .forEach(e -> e.setSelected(true));
    }
}
