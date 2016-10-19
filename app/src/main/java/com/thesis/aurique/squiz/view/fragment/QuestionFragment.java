package com.thesis.aurique.squiz.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.thesis.aurique.squiz.R;

import com.thesis.aurique.squiz.model.Questions;
import com.thesis.aurique.squiz.view.activity.QuestionMainActivity;
import com.thesis.aurique.squiz.view.adapter.QuestionAdapter;


public class QuestionFragment extends Fragment {

    private QuestionMainActivity questionMainActivity;
    private View view;
    public ViewPager viewPager;
    private PagerAdapter pagerAdapter;
    private static String mSet;

    public static QuestionFragment newInstance(String set){
        QuestionFragment q = new QuestionFragment();
        mSet = set;
        return q;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.question_fragment,container,false);
        questionMainActivity = ((QuestionMainActivity) getActivity());


        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        pagerAdapter = null;

        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        questionMainActivity.db.child("question").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


            if(pagerAdapter == null) {
                pagerAdapter = new QuestionAdapter(getFragmentManager(), questionMainActivity.context, dataSnapshot,mSet);
                viewPager.setAdapter(pagerAdapter);

            }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        return view;
    }

}
