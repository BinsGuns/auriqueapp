package com.thesis.aurique.squiz.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.thesis.aurique.squiz.R;
import com.thesis.aurique.squiz.view.activity.QuestionMainActivity;


public class QuestionWelcomeFragment extends Fragment implements View.OnClickListener{

    QuestionMainActivity questionActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.question_welcome,container,false);
        questionActivity = ((QuestionMainActivity) getActivity());


        ((Button) view.findViewById(R.id.proceed)).setOnClickListener(this);

        return  view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.proceed :
                questionActivity.changeFragment(new QuestionFragment());
                break;

        }
    }
}
