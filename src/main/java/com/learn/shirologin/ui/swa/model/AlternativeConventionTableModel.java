package com.learn.shirologin.ui.swa.model;

import com.learn.shirologin.ui.base.model.DefaultTableModel;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AlternativeConventionTableModel extends DefaultTableModel<List<Object>> {
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

    public Double getMaxDataByColumn(int columnIndex){
        return datas.stream()
                .filter(row -> row.size() > columnIndex) // avoid IndexOutOfBounds
                .map(row -> (String) row.get(columnIndex))
                .mapToDouble(Double::parseDouble) // convert String to Integer if needed
                .max()
                .orElse(0d);
    }

    public Double getMinDataByColumn(int columnIndex){
        return datas.stream()
                .filter(row -> row.size() > columnIndex) // avoid IndexOutOfBounds
                .map(row -> (String) row.get(columnIndex))
                .mapToDouble(Double::parseDouble) // convert String to Integer if needed
                .min()
                .orElse(0d);
    }
}
