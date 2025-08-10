package com.learn.shirologin.ui.base.combobox;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CheckItem {
    private String text;
    private boolean selected;

    @Override
    public String toString(){
        return text;
    }

}
