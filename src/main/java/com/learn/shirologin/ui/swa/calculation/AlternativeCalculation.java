package com.learn.shirologin.ui.swa.calculation;

import com.learn.shirologin.model.CriteriaItem;
import com.learn.shirologin.model.CriteriaType;
import com.learn.shirologin.model.SubCriteriaItem;
import com.learn.shirologin.ui.swa.model.AlternativeDetailTableModel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AlternativeCalculation {
    private final AlternativeDetailTableModel alternativeDetailTableModel;
    private final SubCriteriaConvention subCriteriaConvention;

    public void tryToConvention(List<CriteriaItem> criteria){
        List<List<Object>> data = alternativeDetailTableModel.getDatas();
        for (int i = 0; i < data.size(); i++) {
                int columnCriteria = 0;
            for (int j = 2; j < data.get(i).size(); j++) {

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

                if(selected.isPresent()){
                    alternativeDetailTableModel.setValueAt(selected.get().getWeight()
                            .toString(),i,j);
                }
            }

        }
    }

    public void tryToNormalization(List<CriteriaItem> criteria){
        List<List<Object>> data = alternativeDetailTableModel.getDatas();
        for (int i = 0; i < data.size(); i++) {
            int columnCriteria = 0;
            for (int j = 2; j < data.get(i).size(); j++) {

                CriteriaItem criteriaItem = criteria.get(columnCriteria);
                Double variable = Double.parseDouble((String)data.get(i).get(j)) ;

                Double total = 0d;
                if(criteriaItem.getType().equals(CriteriaType.BENEFIT)){
                    total = alternativeDetailTableModel.getMaxDataByColumn(j);
                }else{
                    total = alternativeDetailTableModel.getMinDataByColumn(j);
                }

                double result = variable / total;

                alternativeDetailTableModel.setValueAt(Double.toString(result),i,j);
                columnCriteria++;
            }

        }
    }

}
