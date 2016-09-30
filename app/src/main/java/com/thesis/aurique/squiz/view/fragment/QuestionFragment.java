package com.thesis.aurique.squiz.view.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.thesis.aurique.squiz.R;
import com.thesis.aurique.squiz.databinding.QuestionFragmentBinding;
import com.thesis.aurique.squiz.view.activity.QuestionMainActivity;


public class QuestionFragment extends Fragment {

    private QuestionMainActivity questionMainActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        QuestionFragmentBinding questionFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.question_fragment,container,false);
        questionMainActivity = ((QuestionMainActivity) getActivity());

        questionMainActivity.db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return questionFragmentBinding.getRoot();
    }
}
