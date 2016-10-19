package com.thesis.aurique.squiz.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.thesis.aurique.squiz.R;
import com.thesis.aurique.squiz.model.Questions;
import com.thesis.aurique.squiz.view.activity.QuestionMainActivity;


public class QuestionWelcomeFragment extends Fragment implements View.OnClickListener{

    QuestionMainActivity questionActivity;
    private String set;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         super.onCreateView(inflater, container, savedInstanceState);

        final View view = inflater.inflate(R.layout.question_welcome,container,false);
        questionActivity = ((QuestionMainActivity) getActivity());



        ((RadioGroup) view.findViewById(R.id.category)).setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                view.findViewById(R.id.proceed).setEnabled(true);
                RadioButton r = (RadioButton) view.findViewById(group.getCheckedRadioButtonId());
                set = r.getText().toString();
            }
        });

        questionActivity.db.child("question").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getChildrenCount() == 0){

                    ((RadioGroup) view.findViewById(R.id.category)).setVisibility(View.GONE);
                    ((Button) view.findViewById(R.id.proceed)).setVisibility(View.GONE);
                    ((TextView) view.findViewById(R.id.labelwelcome)).setText("No Questions yet. Please add!");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        ((Button) view.findViewById(R.id.proceed)).setOnClickListener(this);


        return  view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.proceed :
                questionActivity.changeFragment(QuestionFragment.newInstance(set),"QuestionTag");
                break;

        }
    }
}
