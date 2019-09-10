package com.vktrhrsny.clndrx.select_adapter;

import com.vktrhrsny.clndrx.vp.pojo.DayItem;


public class SelectItem extends DayItem {
    private boolean isSelected = false;


    public SelectItem(DayItem entity,boolean isSelected) {
        super(entity.getMemoId(),entity.getMessage(),entity.getEmotIcon(), entity.getDate());
        this.isSelected = isSelected;
    }


    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}

