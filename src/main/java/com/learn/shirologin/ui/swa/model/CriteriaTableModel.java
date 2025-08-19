package com.learn.shirologin.ui.swa.model;

import com.learn.shirologin.model.Criteria;
import com.learn.shirologin.model.RatingMatch;
import com.learn.shirologin.ui.base.model.DefaultTableModel;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;

@Component
public class CriteriaTableModel extends DefaultTableModel<Criteria> {

    private static final int NAME_INDEX = 0;
    private static final int TYPE_INDEX = 1;
    private static final int WEIGHT_INDEX = 2;

    @Override
    public String[] getColumnLabels() {
        return new String[]{
                "Nama",
                "Tipe",
                "Bobot"
        };
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Criteria criteria = datas.get(rowIndex);
        switch(columnIndex) {
            case NAME_INDEX:
                return criteria.getName();
            case TYPE_INDEX:
                return criteria.getType().getName();
            case WEIGHT_INDEX:
                return criteria.getWeight();
            default :
                return Strings.EMPTY;
        }
    }
}
