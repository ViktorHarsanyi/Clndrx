package com.vktrhrsny.clndrx.vp;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.vktrhrsny.clndrx.ReadFrag;
import com.vktrhrsny.clndrx.WriteFrag;

import java.util.ArrayList;



public class FragPagerAdapter extends FragmentStateAdapter {

    private ArrayList<Fragment> arrayList = new ArrayList<>();


    public FragPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
        arrayList.add(new CalendarFrag());
        arrayList.add(new WriteFrag());
        arrayList.add(new ReadFrag());
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        return arrayList.get(position);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }



}