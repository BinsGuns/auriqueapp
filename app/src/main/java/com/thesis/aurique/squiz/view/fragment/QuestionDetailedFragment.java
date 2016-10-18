package com.thesis.aurique.squiz.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.thesis.aurique.squiz.R;
import com.thesis.aurique.squiz.model.Questions;
import com.thesis.aurique.squiz.view.activity.FinishActivity;
import com.thesis.aurique.squiz.view.activity.QuestionMainActivity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Vince on 10/18/2016.
 */
public class QuestionDetailedFragment extends Fragment {

    private  static DataSnapshot mData;
    private  static int mPosition,mTotal;
    private DatabaseReference db;
    private QuestionMainActivity mainAct;
    private int pos,score;
    private Questions question;
    private RadioGroup optionRg;


    public static QuestionDetailedFragment newInstance(int position, DataSnapshot data,int total){

        mData = data;
        mTotal = total;
        mPosition = position;
        QuestionDetailedFragment q = new QuestionDetailedFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("position",position);

        q.setArguments(bundle);

        return q;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = ((QuestionMainActivity) getActivity()).db;

        pos =   getArguments().getInt("position");
        mainAct = ((QuestionMainActivity) getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.question_detailed_layout,container,false);
        int count = 0;
        for(DataSnapshot d : mData.getChildren()){
            if(pos == count){
                 question = d.getValue(Questions.class);

                ((TextView) view.findViewById(R.id.questionText)).setText(question.question);
                ((TextView) view.findViewById(R.id.label)).setText("Question no. "+ (pos+1) + " out of "+mTotal);

                optionRg = (RadioGroup) view.findViewById(R.id.option);

                optionRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        view.findViewById(R.id.answerButton).setEnabled(true);
                    }
                });

                for(int i = 0 ; i < optionRg.getChildCount() ; i++) {
                    String o =   question.options.get(i);
                    ((RadioButton) optionRg.getChildAt(i)).setText(o);

                }

                break;
            }

            count++;
        }

       QuestionFragment qFrag = ((QuestionFragment) getFragmentManager().findFragmentByTag("QuestionTag"));

       final ViewPager viewPager = qFrag.viewPager;



        view.findViewById(R.id.answerButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(pos+1 == mTotal) {
                    Intent i = new Intent(mainAct, FinishActivity.class);
                    startActivity(i);

                }

                RadioButton r = (RadioButton) view.findViewById(optionRg.getCheckedRadioButtonId());

                boolean isCorrect = checkAnswer(question.answer,r.getText().toString());

                if(isCorrect) {

                    Map<String, Object> map = new HashMap<>();
                    score++;
                //    HashMap<String, Integer> h = new HashMap<>();

                 //   h.put("user_score",1 ); // key value

                    map.put( mainAct.auth.getCurrentUser().getUid(),score );

                    db.child("scores").updateChildren(map);

                    mainAct.showSnackBar("Correct answer!");
                } else {

                    mainAct.showSnackBar("Wrong answer!");
                }
                viewPager.setCurrentItem(pos+1);
            }
        });

        db.child("scores").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        return  view;

    }

    private boolean checkAnswer(String correctAnswer , String selectedAnswer) {
        if(correctAnswer.equals(selectedAnswer)){
            return true;
        } else {
            return false;
        }
    }
}
