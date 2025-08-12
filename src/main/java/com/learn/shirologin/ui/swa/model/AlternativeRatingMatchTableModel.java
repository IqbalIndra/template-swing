package com.learn.shirologin.ui.swa.model;

import com.learn.shirologin.model.RatingMatch;
import com.learn.shirologin.model.UserInfo;
import com.learn.shirologin.ui.base.model.DefaultTableModel;
import lombok.Setter;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AlternativeRatingMatchTableModel extends DefaultTableModel<RatingMatch> {

    private static final int ALTERNATIVE_NAME_INDEX = 0;
    private static final int VALUE_INDEX = 1;
    private static final int RANK_INDEX = 2;

    @Override
    public String[] getColumnLabels() {
        return new String[]{
                "Nama",
                "Nilai Akhir",
                "Ranking"
        };
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        RatingMatch ratingMatch = datas.get(rowIndex);
        switch(columnIndex) {
            case ALTERNATIVE_NAME_INDEX:
                return ratingMatch.getAlternativeName();
            case VALUE_INDEX:
                return ratingMatch.getValue();
            case RANK_INDEX:
                return ratingMatch.getRank();
            default :
                return Strings.EMPTY;
        }
    }
}
