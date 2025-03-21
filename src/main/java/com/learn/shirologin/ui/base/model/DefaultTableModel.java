/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.learn.shirologin.ui.base.model;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author KBDSI-IQBAL
 */
public abstract class DefaultTableModel<T> extends AbstractTableModel{
    protected List<T> datas = new ArrayList<>();
    
    public abstract String[] getColumnLabels();

    @Override
    public int getRowCount() {
        return datas.size();
    }

    @Override
    public int getColumnCount() {
        return getColumnLabels().length;
    }

    @Override
    public String getColumnName(int column) {
        return getColumnLabels()[column];
    }
    
    public void addData(T data){
        datas.add(data);
        fireTableDataChanged();
    }
    
    public void addDatas(List<T> datas){
        this.datas.addAll(datas);
        fireTableDataChanged();
    }

    public List<T> getDatas(){
        return datas;
    }
    
    public T getDataByRow(int row){
        return datas.get(row);
    }
    
    public void updateData(int row, T data){
        datas.set(row, data);
        fireTableDataChanged();
    }
    
    public void removeData(int row){
        datas.remove(row);
        fireTableDataChanged();
    }
    
    public void clearAll(){
        datas.clear();
        fireTableDataChanged();
    }
    
}
