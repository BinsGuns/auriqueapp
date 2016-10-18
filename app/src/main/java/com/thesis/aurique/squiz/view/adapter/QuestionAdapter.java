package com.thesis.aurique.squiz.view.adapter;

import android.content.Context;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


import com.google.firebase.database.DataSnapshot;
import com.thesis.aurique.squiz.view.fragment.QuestionDetailedFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vince on 10/16/2016.
 */
public class QuestionAdapter extends FragmentPagerAdapter {


    private DataSnapshot dataSnapshot;
    private List<Fragment> fragmentList;

    private DataSnapshot data;
    public QuestionAdapter(FragmentManager fm,Context context, DataSnapshot dataSnapshot) {
        super(fm);
        fragmentList = new ArrayList<>();
        this.dataSnapshot = dataSnapshot;

    }



    @Override
    public Fragment getItem(int position) {

        return QuestionDetailedFragment.newInstance(position,dataSnapshot,getCount());
    }



    @Override
    public int getCount() {
        return (int)dataSnapshot.getChildrenCount();
    }
}