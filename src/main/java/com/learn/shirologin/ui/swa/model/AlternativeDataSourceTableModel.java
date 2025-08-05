package com.learn.shirologin.ui.swa.model;

import com.learn.shirologin.model.AlternativeDataSource;
import com.learn.shirologin.model.UserInfo;
import com.learn.shirologin.ui.base.model.DefaultTableModel;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AlternativeDataSourceTableModel extends DefaultTableModel<AlternativeDataSource> {

    private static final int CODE_INDEX = 0;
    private static final int MAJOR_INDEX = 2;
    private static final int SCHOOL_YEAR_INDEX = 1;
    private static final int CLASSROOM_INDEX = 3;
    private static final int STATUS_INDEX = 4;

    @Override
    public String[] getColumnLabels() {
        return new String[]{
                "Kode",
                "Tahun Ajaran",
                "Jurusan",
                "Kelas",
                "Status"
        };
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        AlternativeDataSource alternativeDataSource = datas.get(rowIndex);
        switch(columnIndex) {
            case CODE_INDEX :
                return alternativeDataSource.getCode();
            case SCHOOL_YEAR_INDEX :
                return alternativeDataSource.getSchoolYear();
            case MAJOR_INDEX :
                return alternativeDataSource.getMajor();
            case CLASSROOM_INDEX :
                return alternativeDataSource.getClassRoom();
            case STATUS_INDEX :
                return alternativeDataSource.getStatus();
            default :
                return Strings.EMPTY;
        }
    }
}
