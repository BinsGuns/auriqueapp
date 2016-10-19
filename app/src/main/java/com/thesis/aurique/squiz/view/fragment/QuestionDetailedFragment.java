package com.thesis.aurique.squiz.view.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
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
import java.util.List;
import java.util.Map;

/**
 * Created by Vince on 10/18/2016.
 */
public class QuestionDetailedFragment extends Fragment {

    private  static DataSnapshot mData;
    private  static int mPosition,mTotal;
    private DatabaseReference db;
    private QuestionMainActivity mainAct;
    private int pos;
    private RadioGroup optionRg;


    private static List<Questions> questionsList;


    public static QuestionDetailedFragment newInstance(int position, List<Questions> data,int total){
        questionsList = data;

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

        mainAct.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        mainAct.findViewById(R.id.navButton).setVisibility(View.GONE);


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.question_detailed_layout,container,false);

        final Questions question = questionsList.get(pos);

        ((TextView) view.findViewById(R.id.questionText)).setText(question.question);
        ((TextView) view.findViewById(R.id.label)).setText("Question no. " + (pos + 1) + " out of " + mTotal);

        optionRg = (RadioGroup) view.findViewById(R.id.option);

        optionRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                view.findViewById(R.id.answerButton).setEnabled(true);
            }
        });

        for (int i = 0; i < optionRg.getChildCount(); i++) {
            String o = question.options.get(i);
            ((RadioButton) optionRg.getChildAt(i)).setText(o);

        }

       QuestionFragment qFrag = ((QuestionFragment) getFragmentManager().findFragmentByTag("QuestionTag"));

       final ViewPager viewPager = qFrag.viewPager;


        view.findViewById(R.id.answerButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                RadioButton r = (RadioButton) view.findViewById(optionRg.getCheckedRadioButtonId());

                boolean isCorrect = checkAnswer(question.answer,r.getText().toString());


                if(isCorrect) {

                    Map<String, Object> map = new HashMap<>();

                    int s = mainAct.sharedPreferences.getInt("score",0);
                    s++;
                    map.put( mainAct.auth.getCurrentUser().getUid(),s );
                    SharedPreferences.Editor e = mainAct.sharedPreferences.edit();
                    e.putInt("score",s);
                    e.commit();

                    db.child("scores").updateChildren(map);

                    mainAct.showSnackBar("Correct answer!");
                } else {

                    int wrong_count =  mainAct.sharedPreferences.getInt("wrong_count",0);
                    wrong_count ++;
                    SharedPreferences.Editor e = mainAct.sharedPreferences.edit();
                    e.putInt("wrong_count",wrong_count);
                    e.commit();

                    mainAct.sharedPreferences.getInt("wrong_count",0);

                    mainAct.showSnackBar("Wrong answer!");
                }

                if(pos+1 == mTotal) {

                    int wrong_count =  mainAct.sharedPreferences.getInt("wrong_count",0);

                    if(wrong_count == mTotal){
                        Map<String, Object> map = new HashMap<>();
                        map.put( mainAct.auth.getCurrentUser().getUid(),0 );
                        db.child("scores").updateChildren(map);
                    }

                    Intent i = new Intent(mainAct, FinishActivity.class);
                    startActivity(i);
                }



                viewPager.setCurrentItem(pos+1);
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
