package com.vktrhrsny.clndrx.vp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.vktrhrsny.clndrx.R;

import java.util.List;

public class VPAdapter extends RecyclerView.Adapter<VPAdapter.ViewHolder> {

    private List<String> data;
    private LayoutInflater inflater;
    private ViewPager2 vp;

    private int[] colors = new int[]
            {android.R.color.holo_blue_bright,android.R.color.holo_green_light,
            android.R.color.holo_orange_light,android.R.color.holo_red_light};

    public VPAdapter(Context context,List<String> data, ViewPager2 viewPager2){
        this.inflater = LayoutInflater.from(context);
        this.data = data;
        vp = viewPager2;
    }

    @NonNull
    @Override
    public VPAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.vp_item_layout,parent,false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull VPAdapter.ViewHolder holder, int position) {
        String screen = data.get(position);
        holder.textView.setText(screen);
        holder.relativeLayout.setBackgroundResource(colors[position]);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        RelativeLayout relativeLayout;
        Button button;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tvTitle);
            relativeLayout = itemView.findViewById(R.id.container);
            button = itemView.findViewById(R.id.btnToggle);

            button.setOnClickListener(v -> {
                if(vp.getOrientation() == ViewPager2.ORIENTATION_VERTICAL)
                    vp.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
                else{
                    vp.setOrientation(ViewPager2.ORIENTATION_VERTICAL);
                }
            });
        }
    }
}
