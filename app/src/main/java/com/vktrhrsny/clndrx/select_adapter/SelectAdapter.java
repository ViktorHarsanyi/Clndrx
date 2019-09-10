package com.vktrhrsny.clndrx.select_adapter;

import android.annotation.SuppressLint;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vktrhrsny.clndrx.R;
import com.vktrhrsny.clndrx.util.Conv;
import com.vktrhrsny.clndrx.vp.pojo.DayItem;
import com.vktrhrsny.clndrx.vp.pojo.MemoryEntity;

import java.util.ArrayList;
import java.util.List;

public class SelectAdapter  extends RecyclerView.Adapter implements SelectableVH.OnItemSelectedListener {

        private List<SelectItem> mValues;
        private List<MemoryEntity> originalEntities;
        private boolean isMultiSelect;
        private SelectableVH.OnItemSelectedListener listener;


        public SelectAdapter(SelectableVH.OnItemSelectedListener listener, boolean isMultiSelectionEnabled) {
            this.listener = listener;
            this.isMultiSelect = isMultiSelectionEnabled;

        }

        public void setValues(List<MemoryEntity> memoryEntities){
            List<SelectItem> tempVal = new ArrayList<>();
            originalEntities = memoryEntities;
            for(MemoryEntity entity:memoryEntities){
                tempVal.add(new SelectItem(new DayItem(entity.getMemoryID(),entity.getEntry(),entity.getEmotIcon(),Conv.stringToDate(entity.getDate())),false));
            }
            mValues=tempVal;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public SelectableVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.checked_text_item, parent, false);

            return new SelectableVH(itemView, this);
        }


        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {

            SelectableVH holder = (SelectableVH) viewHolder;
            SelectItem selectableItem = mValues.get(position);
            holder.imageView.setImageResource(Conv.getEmotIconImageResource(selectableItem.getEmotIcon()));
            holder.textView.setText(selectableItem.getMessage());

            //setIcon(holder.textView,name);
            if (isMultiSelect) {
                TypedValue value = new TypedValue();
                holder.textView.getContext().getTheme().resolveAttribute(android.R.attr.listChoiceIndicatorMultiple, value, true);
                int checkMarkDrawableResId = value.resourceId;
                holder.textView.setCheckMarkDrawable(checkMarkDrawableResId);
            } else {
                TypedValue value = new TypedValue();
                holder.textView.getContext().getTheme().resolveAttribute(android.R.attr.listChoiceIndicatorSingle, value, true);
                int checkMarkDrawableResId = value.resourceId;
                holder.textView.setCheckMarkDrawable(checkMarkDrawableResId);
            }

            holder.selectItem = selectableItem;
            holder.setChecked(holder.selectItem.isSelected());
        }

        @Override
        public int getItemCount() {
            if(mValues!=null)return mValues.size();
            else return 0;
        }

        public List<MemoryEntity> getSelectedItems() {

            List<MemoryEntity> selectedItems = new ArrayList<>();
            if(mValues!=null){
            for (SelectItem item : mValues) {
                if (item.isSelected()) {
                    selectedItems.add(originalEntities.get(mValues.indexOf(item)));
                }
            }
            }
            return selectedItems;
        }

        private void resetSelectedItems(){
            for(SelectItem item:mValues) {
                if(item.isSelected())
                    item.setSelected(false);
            }
            notifyDataSetChanged();
        }

        @Override
        public int getItemViewType(int position) {
            if(isMultiSelect){
                return SelectableVH.MULTI_SELECTION;
            }
            else{
                return SelectableVH.SINGLE_SELECTION;
            }
        }

        @Override
        public void onItemSelected(SelectItem item) {
            if (!isMultiSelect) {

                for (SelectItem selectableItem : mValues) {
                    if (selectableItem.getMemoId()!=(item.getMemoId())
                            && selectableItem.isSelected()) {
                        resetSelectedItems();

                    } else if (selectableItem.getMemoId()==(item.getMemoId())
                            && item.isSelected()) {

                        selectableItem.setSelected(true);
                    }
                }
                notifyDataSetChanged();
            }

            listener.onItemSelected(item);
        }


    }

