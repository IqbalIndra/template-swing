package com.learn.shirologin.ui.swa.calculation;

import com.learn.shirologin.model.*;
import com.learn.shirologin.ui.swa.model.AlternativeConventionTableModel;
import com.learn.shirologin.ui.swa.model.AlternativeDetailTableModel;
import com.learn.shirologin.ui.swa.model.AlternativeNormalizationTableModel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.PriorityQueue;

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
        for (List<Object> datum : data) {
            int columnCriteria = 0;
            List<Object> rows = new ArrayList<>();
            for (int j = 0; j < datum.size(); j++) {
                if (j < 2) {
                    rows.add(datum.get(j));
                    continue;
                }

                List<SubCriteriaItem> subCriteriaItems = criteria.get(columnCriteria++).getSubCriteriaItems();
                Double variable = Double.parseDouble((String) datum.get(j));

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
        for (List<Object> datum : data) {
            int columnCriteria = 0;
            List<Object> rows = new ArrayList<>();
            for (int j = 0; j < datum.size(); j++) {
                if (j < 2) {
                    rows.add(datum.get(j));
                    continue;
                }

                CriteriaItem criteriaItem = criteria.get(columnCriteria);
                Double variable = Double.parseDouble((String) datum.get(j));

                Double total = 0d;
                if (criteriaItem.getType().equals(CriteriaType.BENEFIT)) {
                    total = alternativeConventionTableModel.getMaxDataByColumn(j);
                } else {
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

    public List<RatingMatch> tryToRating(List<CriteriaItem> criterias){
        List<List<Object>> datas = alternativeNormalizationTableModel.getDatas();
        List<RatingMatch> result = new ArrayList<>();
        PriorityQueue<RatingMatch> queue = new PriorityQueue<>((o1, o2) ->
                Double.compare(o2.getValue(), o1.getValue()));

        for(List<Object> data : datas){
            int columnCriteria = 0;
            double value = 0d;
            RatingMatch ratingMatch = new RatingMatch();
            ratingMatch.setAlternativeName((String) data.get(1));

            for(int j = 2; j<data.size(); j++){
                CriteriaItem criteria = criterias.get(columnCriteria++);
                value+= (Double) data.get(j) * criteria.getWeight();
            }
            ratingMatch.setValue(value);

            if(queue.size() < 10){
                queue.add(ratingMatch);
            }else if (value > queue.peek().getValue()) {
                queue.poll();
                queue.add(ratingMatch);
            }
        }

        System.out.println("Size Data : "+queue.size());

        int i =0;
        while(!queue.isEmpty()){
            RatingMatch ratingMatch = queue.poll();
            ratingMatch.setRank(i+1);
            result.add(ratingMatch);
            i++;
        }

        return result;
    }

}
