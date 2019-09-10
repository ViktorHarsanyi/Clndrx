package com.vktrhrsny.clndrx.vp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vktrhrsny.clndrx.MainActivity;
import com.vktrhrsny.clndrx.R;
import com.vktrhrsny.clndrx.sql.MemoVM;
import com.vktrhrsny.clndrx.vp.pojo.DayItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;


public class CalendarFrag extends Fragment {

    private static final int MAX_CALENDAR_COLUMN = 42;
    private SimpleDateFormat formatter = new SimpleDateFormat("MMMM yyyy", Locale.ENGLISH);
    private Calendar cal = Calendar.getInstance(Locale.ENGLISH);
    private TextView indicator;
    private CompositeDisposable compositeDisposable;
    private RecyAdapter adapter;
    public CalendarFrag() {

    }

    @SuppressLint("CheckResult")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MemoVM vm = ViewModelProviders.of(this).get(MemoVM.class);

        compositeDisposable = new CompositeDisposable();

        compositeDisposable.add(vm.getMemoList()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(memoryEntities -> {
                        adapter.setMemoData(memoryEntities);
                }));

        adapter = new RecyAdapter(getContext());
        adapter.setOnItemClickListener((view, date, data) -> {
            if(getActivity() instanceof MainActivity)
                ((MainActivity) getActivity()).showDialog(date);

        });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.vp_frag, container, false);

        RecyclerView recy = view.findViewById(R.id.recy);
        Button bF = view.findViewById(R.id.monthF);
        Button bB = view.findViewById(R.id.monthB);
        indicator = view.findViewById(R.id.indicatorTv);

        recy.setLayoutManager(new GridLayoutManager(getActivity(),7));
        recy.setAdapter(adapter);
        initRecyAdapter();


        bF.setOnClickListener(bf -> {
            cal.add(Calendar.MONTH,1);
            initRecyAdapter();
        });
        bB.setOnClickListener(bb ->{
            cal.add(Calendar.MONTH,-1);
            initRecyAdapter();
        });

        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        compositeDisposable.clear();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    private void initRecyAdapter(){
        List<DayItem> dayValueInCells = new ArrayList<>();
        Calendar clone = (Calendar)cal.clone();
        clone.set(Calendar.DAY_OF_MONTH, 1);
        int firstDOM = clone.get(Calendar.DAY_OF_WEEK) - 1;
        clone.add(Calendar.DAY_OF_MONTH, -firstDOM);
        while(dayValueInCells.size() < MAX_CALENDAR_COLUMN){
            dayValueInCells.add(new DayItem(clone.get(Calendar.DAY_OF_MONTH),clone.getTime()));
            clone.add(Calendar.DAY_OF_MONTH, 1);
        }
        String stringDate = formatter.format(cal.getTime());
        indicator.setText(stringDate);
        adapter.setAdapterData(cal,dayValueInCells, null);
        adapter.notifyDataSetChanged();
    }

}
