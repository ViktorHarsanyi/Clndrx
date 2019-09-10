package com.vktrhrsny.clndrx;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vktrhrsny.clndrx.select_adapter.SelectAdapter;
import com.vktrhrsny.clndrx.sql.MemoVM;
import com.vktrhrsny.clndrx.util.Conv;
import com.vktrhrsny.clndrx.vp.MoodChart;
import com.vktrhrsny.clndrx.vp.pojo.MemoryEntity;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Locale;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;


public class ReadFrag extends Fragment {
    private TextView dateTextView;
    private SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH);
    private MemoVM vm;
    private CompositeDisposable compositeDisposable;
    private Date wDate;
    private SelectAdapter selectAdapter;
    private MoodChart moodChart;

    public ReadFrag() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vm = ViewModelProviders.of(this).get(MemoVM.class);
        compositeDisposable = new CompositeDisposable();

            compositeDisposable.add(
                    EventBus.getInstance().toObservable()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(date -> {
                        wDate = date;
                        print();
                    }));
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.read_frag, container, false);
        Button delete = view.findViewById(R.id.delete);
        RecyclerView recyclerView = view.findViewById(R.id.selectable_recy);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        moodChart = view.findViewById(R.id.moodRead);
        selectAdapter = new SelectAdapter(item ->{
                Toast.makeText(getContext(), "selected: "+item.getMemoId(), Toast.LENGTH_SHORT).show();},true);
        recyclerView.setAdapter(selectAdapter);

        delete.setOnClickListener(listener ->{
            for (MemoryEntity entity:selectAdapter.getSelectedItems())
            compositeDisposable.add(vm.deleteIt(entity));
            print();
        });
        dateTextView = view.findViewById(R.id.dateTv);
        print();
        return view;
    }

    @Override
    public void onStop(){
        super.onStop();
        compositeDisposable.clear();
    }

    private void print(){
        if(wDate==null)
          wDate = new Date();

        dateTextView.setText(formatter.format(wDate));
        compositeDisposable.add(vm.getSpecificMemo(Conv.dateToString(wDate))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(memoryEntities -> {
                    selectAdapter.setValues(memoryEntities);
                    moodChart.setData(memoryEntities);
                },throwable -> Log.e("Error","issue"+ throwable.toString(),throwable))
        );
    }

}
