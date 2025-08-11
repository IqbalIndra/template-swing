package com.learn.shirologin.ui.swa.calculation;

import com.learn.shirologin.model.CriteriaItem;
import com.learn.shirologin.model.CriteriaType;
import com.learn.shirologin.model.SubCriteriaItem;
import com.learn.shirologin.ui.swa.model.AlternativeConventionTableModel;
import com.learn.shirologin.ui.swa.model.AlternativeDetailTableModel;
import com.learn.shirologin.ui.swa.model.AlternativeNormalizationTableModel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AlternativeCalculation {
    private final AlternativeDetailTableModel alternativeDetailTableModel;
    private final AlternativeConventionTableModel alternativeConventionTableModel;
    private final AlternativeNormalizationTableModel alternativeNormalizationTableModel;
    private final SubCriteriaConvention subCriteriaConvention;

    public List<List<Object>> tryToConvention(List<CriteriaItem> criteria){
        List<List<Object>> result = new ArrayList<>();
        List<List<Object>> data = alternativeDetailTableModel.getDatas();
        for (int i = 0; i < data.size(); i++) {
                int columnCriteria = 0;
                List<Object> rows = new ArrayList<>();
            for (int j = 0; j < data.get(i).size(); j++) {
                if(j < 2){
                    rows.add(data.get(i).get(j));
                    continue;
                }

                List<SubCriteriaItem> subCriteriaItems = criteria.get(columnCriteria++).getSubCriteriaItems();
                Double variable = Double.parseDouble((String)data.get(i).get(j)) ;

                Optional<SubCriteriaItem> selected = subCriteriaItems.stream()
                        .filter(s -> subCriteriaConvention.check(
                                s.getOperator().getSymbol(),
                                variable,
                                s.getMinValue(),
                                s.getMaxValue()
                        ))
                        .findFirst();

                selected.ifPresent(subCriteriaItem -> rows.add(subCriteriaItem.getWeight().toString()));
            }
            result.add(rows);

        }
        return result;
    }

    public List<List<Object>> tryToNormalization(List<CriteriaItem> criteria){
        List<List<Object>> result = new ArrayList<>();
        List<List<Object>> data = alternativeConventionTableModel.getDatas();
        for (int i = 0; i < data.size(); i++) {
            int columnCriteria = 0;
            List<Object> rows = new ArrayList<>();
            for (int j = 0; j < data.get(i).size(); j++) {
                if(j < 2){
                    rows.add(data.get(i).get(j));
                    continue;
                }

                CriteriaItem criteriaItem = criteria.get(columnCriteria);
                Double variable = Double.parseDouble((String)data.get(i).get(j)) ;

                Double total = 0d;
                if(criteriaItem.getType().equals(CriteriaType.BENEFIT)){
                    total = alternativeConventionTableModel.getMaxDataByColumn(j);
                }else{
                    total = alternativeConventionTableModel.getMinDataByColumn(j);
                }

                double value = variable / total;
                rows.add(value);
                columnCriteria++;
            }
            result.add(rows);
        }
        return result;
    }

}
