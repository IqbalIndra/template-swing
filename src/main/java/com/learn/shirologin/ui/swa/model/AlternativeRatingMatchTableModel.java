package com.learn.shirologin.ui.swa.model;

import com.learn.shirologin.ui.base.model.DefaultTableModel;
import lombok.Setter;

import java.util.List;

public class AlternativeRatingMatchTableModel extends DefaultTableModel<List<Object>> {

    @Setter
    private String[] columnLabel;

    @Override
    public String[] getColumnLabels() {
        return columnLabel;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
         List<Object> dataByRow = getDataByRow(rowIndex);
        return dataByRow.get(columnIndex);
    }
}
