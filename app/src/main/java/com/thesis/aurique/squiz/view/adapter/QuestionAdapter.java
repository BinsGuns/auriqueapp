package com.thesis.aurique.squiz.view.adapter;

import android.content.Context;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;


import com.google.firebase.database.DataSnapshot;
import com.thesis.aurique.squiz.model.Questions;
import com.thesis.aurique.squiz.view.fragment.QuestionDetailedFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vince on 10/16/2016.
 */
public class QuestionAdapter extends FragmentPagerAdapter {


    private DataSnapshot dataSnapshot;
    private List<Questions> questionsList;
    private String set;


    public QuestionAdapter(FragmentManager fm,Context context, DataSnapshot dataSnapshot,String set) {
        super(fm);
        this.set = set;

        this.dataSnapshot = dataSnapshot;

    }



    @Override
    public Fragment getItem(int position) {

        return QuestionDetailedFragment.newInstance(position,questionsList,getCount());
    }



    @Override
    public int getCount() {

        questionsList = new ArrayList<>();

        for(DataSnapshot d : dataSnapshot.getChildren()){
           Questions q = d.getValue(Questions.class);
            if(q.category.replaceAll("\\s+","").equals(set.replaceAll("\\s+",""))){
                questionsList.add(q);
            }
        }

        return questionsList.size();
    }
}