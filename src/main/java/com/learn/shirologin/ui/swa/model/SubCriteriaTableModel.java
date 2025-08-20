package com.learn.shirologin.ui.swa.model;

import com.learn.shirologin.model.SubCriteria;
import com.learn.shirologin.ui.base.model.DefaultTableModel;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;

@Component
public class SubCriteriaTableModel extends DefaultTableModel<SubCriteria> {

    private static final int CRITERIA_INDEX = 0;
    private static final int NAME_INDEX = 1;
    private static final int OPERATOR_INDEX = 2;
    private static final int WEIGHT_INDEX = 3;
    private static final int MINIMAL_INDEX = 4;
    private static final int MAXIMAL_INDEX = 5;

    @Override
    public String[] getColumnLabels() {
        return new String[]{
                "Kriteria",
                "Nama",
                "Operator",
                "Bobot",
                "Minimal",
                "Maksimal"
        };
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        SubCriteria subCriteria = datas.get(rowIndex);
        switch(columnIndex) {
            case CRITERIA_INDEX:
                return subCriteria.getCriteria().getName();
            case NAME_INDEX:
                return subCriteria.getName();
            case OPERATOR_INDEX:
                return subCriteria.getOperator().getSymbol();
            case WEIGHT_INDEX:
                return subCriteria.getWeight();
            case MINIMAL_INDEX:
                return subCriteria.getMinValue();
            case MAXIMAL_INDEX:
                return subCriteria.getMaxValue();
            default :
                return Strings.EMPTY;
        }
    }
}
