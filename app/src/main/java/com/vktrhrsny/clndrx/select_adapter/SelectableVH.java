package com.vktrhrsny.clndrx.select_adapter;

import android.view.View;
import android.widget.CheckedTextView;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.vktrhrsny.clndrx.R;

public class SelectableVH  extends RecyclerView.ViewHolder {

    static final int MULTI_SELECTION = 2;
    static final int SINGLE_SELECTION = 1;
    CheckedTextView textView;
    ImageView imageView;
    SelectItem selectItem;
    private OnItemSelectedListener itemSelectedListener;


    SelectableVH(View view, OnItemSelectedListener listener) {
        super(view);
        itemSelectedListener = listener;
        textView = view.findViewById(R.id.checked_text);
        imageView = view.findViewById(R.id.emoticon);
        textView.setOnClickListener(view1 -> {
        setChecked(!selectItem.isSelected());
        itemSelectedListener.onItemSelected(selectItem);

        });
    }

    void setChecked(boolean value) {

        selectItem.setSelected(value);
        textView.setChecked(value);
    }

    public interface OnItemSelectedListener {

        void onItemSelected(SelectItem item);
    }

}

