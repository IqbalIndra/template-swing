package com.learn.shirologin.model;

import com.learn.shirologin.ui.base.combobox.CheckItem;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder(builderMethodName = "of")
public class CriteriaItem extends CheckItem {
    private Long id;
    private CriteriaType type;
    private Double weight;
    private List<SubCriteriaItem> subCriteriaItems;
}
