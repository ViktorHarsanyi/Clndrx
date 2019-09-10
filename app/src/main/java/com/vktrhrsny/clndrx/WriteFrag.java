package com.vktrhrsny.clndrx;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.vktrhrsny.clndrx.sql.MemoVM;
import com.vktrhrsny.clndrx.util.Conv;
import com.vktrhrsny.clndrx.vp.pojo.MemoryEntity;

import java.util.Date;
import java.util.Objects;

import io.reactivex.disposables.CompositeDisposable;

public class WriteFrag extends Fragment {

    private CompositeDisposable compositeDisposable;
    private MemoVM vm;
    private int emoticonCode = 1;

    public WriteFrag() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        compositeDisposable = new CompositeDisposable();
        vm = ViewModelProviders.of(this).get(MemoVM.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.write_frag, container, false);
        EditText editText = view.findViewById(R.id.edit);
        Button button = view.findViewById(R.id.write_confirm);
        button.setOnClickListener(listener ->{
            String memo = editText.getText().toString();
            editText.getText().clear();
            InputMethodManager inputMethodManager = (InputMethodManager) Objects.requireNonNull(getContext()).getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
            Date wDate = new Date();

            if(getActivity() instanceof MainActivity)
                wDate = ((MainActivity) getActivity()).getDate();

            EventBus.getInstance().publish(wDate);
            MemoryEntity memory = new MemoryEntity();
            memory.setDate(Conv.dateToString(wDate));
            memory.setEntry(memo);
            memory.setEmotIcon(emoticonCode);
            compositeDisposable.add(vm.insert(memory));

        });

        ImageView vs = view.findViewById(R.id.vs);vs.setOnClickListener(listener->{emoticonCode=5; editText.setBackground(vs.getDrawable());});
        ImageView s = view.findViewById(R.id.s); s.setOnClickListener(listener->{emoticonCode=4; editText.setBackground(s.getDrawable());});
        ImageView n = view.findViewById(R.id.n); n.setOnClickListener(listener->{emoticonCode=3;editText.setBackground(n.getDrawable()); });
        ImageView h = view.findViewById(R.id.h); h.setOnClickListener(listener->{emoticonCode=2; editText.setBackground(h.getDrawable());});
        ImageView vh = view.findViewById(R.id.vh); vh.setOnClickListener(listener->{emoticonCode=1;editText.setBackground(vh.getDrawable()); });

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    @Override
    public void onStop(){
        super.onStop();
        compositeDisposable.clear();
    }


}
