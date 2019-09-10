package com.vktrhrsny.clndrx.vp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.vktrhrsny.clndrx.R;
import com.vktrhrsny.clndrx.util.Conv;
import com.vktrhrsny.clndrx.vp.pojo.DayItem;
import com.vktrhrsny.clndrx.vp.pojo.MemoryEntity;

import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class RecyAdapter extends RecyclerView.Adapter<RecyAdapter.ViewHolder> {


    private List<DayItem> data;
    private Context context;
    private Calendar today;
    private OnItemClick clickListener;
    private List<MemoryEntity> events;
    void setOnItemClickListener(OnItemClick listener) {
        clickListener = listener;
    }

    public void setData(Calendar today) {

        this.today = today;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {


        ImageView imageView,eventImage;
        TextView textView;
        FrameLayout container;



        ViewHolder(View view) {
            super(view);
            container = view.findViewById(R.id.ll);
            imageView = view.findViewById(R.id.image_view);
            textView = view.findViewById(R.id.text_view);
            eventImage = view.findViewById(R.id.event);
        }
    }

    RecyAdapter(Context context) {
        this.context = context;

    }

    void setAdapterData( Calendar today, List<DayItem> data, @Nullable List<MemoryEntity> events){
        this.data = data;
        this.today = today;
        if(events!=null)
            this.events = events;
    }

    @NonNull
    @Override
    public RecyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recy_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final Date wDate = data.get(position).getDate();
        Calendar wCal = Calendar.getInstance();
        wCal.setTime(wDate);
        int dayValue = wCal.get(Calendar.DAY_OF_MONTH);
        int displayMonth = wCal.get(Calendar.MONTH) + 1;
        int displayYear = wCal.get(Calendar.YEAR);
        int currentMonth = today.get(Calendar.MONTH) + 1;
        int currentYear = today.get(Calendar.YEAR);
        int day = today.get(Calendar.DAY_OF_MONTH);

        if(displayMonth == currentMonth && displayYear == currentYear){
            if(day==dayValue)
            holder.container.setBackgroundResource(R.drawable.ic_ufo_dash_current);
            else
            holder.container.setBackgroundResource(R.drawable.ic_ufo_dash_current_month);
        }else{
            holder.container.setBackgroundResource(R.drawable.ic_ufo_dash);
        }
        String model="No Entry";


        Calendar eventCalendar = Calendar.getInstance();
        if(events!=null) {
            for (MemoryEntity memo : events) {
                if(memo.getDate()!=null)
                eventCalendar.setTime(Conv.stringToDate(memo.getDate()));
                else eventCalendar.setTime(eventCalendar.getTime());
                if (dayValue == eventCalendar.get(Calendar.DAY_OF_MONTH) && displayMonth == eventCalendar.get(Calendar.MONTH) + 1
                        && displayYear == eventCalendar.get(Calendar.YEAR)) {
                    holder.eventImage.setImageResource(R.drawable.ic_mail_outline_black_24dp);
                    model = memo.getEntry();
                }
            }
        }
        final String wModel = model;
        if(clickListener!=null)
            holder.itemView.setOnClickListener(v -> clickListener.onItemClicked(v, wDate, wModel));

        holder.imageView.setImageResource(R.drawable.ic_ufo_dash);

        holder.textView.setText(format(wCal.get(Calendar.DAY_OF_WEEK))+"\n"+dayValue);

    }

    private DayItem getItem(int position) {
        return data.get(position);
    }

    private Context getContext() {
        return context;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    private String format(int day){
        String sday;
        switch (day){
            case 1:sday=getContext().getResources().getString(R.string.sun);
            break;
            case 2:sday=getContext().getResources().getString(R.string.mon);
            break;
            case 3:sday=getContext().getResources().getString(R.string.tue);
            break;
            case 4:sday=getContext().getResources().getString(R.string.wed);
            break;
            case 5:sday=getContext().getResources().getString(R.string.thu);
            break;
            case 6:sday=getContext().getResources().getString(R.string.fri);
            break;
            case 7:sday=getContext().getResources().getString(R.string.sat);
            break;
            default:sday="";
        }
        return sday;
    }

void setMemoData(List<MemoryEntity> memoData){
        events = memoData;
        notifyDataSetChanged();
}
interface OnItemClick {
    void onItemClicked(View view, Date date, String data);
}
}