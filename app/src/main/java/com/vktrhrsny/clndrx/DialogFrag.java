package com.vktrhrsny.clndrx;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class DialogFrag extends DialogFragment {

    private EventCoordinator coordinator;
    private SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM", Locale.ENGLISH);

    static DialogFrag instantiate(Date date){
        DialogFrag dialogFrag = new DialogFrag();
        Bundle bundle = new Bundle();
        bundle.putSerializable("date",date);
        dialogFrag.setArguments(bundle);
        return dialogFrag;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_frag,container);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button read = view.findViewById(R.id.read_button);
        Date argDate = (Date)Objects.requireNonNull(getArguments()).getSerializable("date");
        TextView date = view.findViewById(R.id.dateIndicator);
        date.setText(formatter.format(argDate));
        read.setOnClickListener(listener ->{
            if(getActivity() instanceof MainActivity)
                ((MainActivity)getActivity()).scrollTo(2);
            this.dismiss();
        });

        Button add = view.findViewById(R.id.add_button);
        add.setOnClickListener(listener ->{
            coordinator.passMemo(argDate);
            if(getActivity() instanceof MainActivity)
                ((MainActivity)getActivity()).scrollTo(1);
            this.dismiss();

        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            coordinator = (EventCoordinator) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException("Error: "+e);
        }
    }

    public interface EventCoordinator{
        void passMemo(Date date);
    }


}
