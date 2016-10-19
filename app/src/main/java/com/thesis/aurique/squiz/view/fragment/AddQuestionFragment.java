package com.thesis.aurique.squiz.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.TextViewCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.thesis.aurique.squiz.R;
import com.thesis.aurique.squiz.model.Questions;
import com.thesis.aurique.squiz.view.activity.QuestionMainActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class AddQuestionFragment extends Fragment  {

    private View view;
    private List<String> listInputs;
    private Questions question;
    private QuestionMainActivity questionMainActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         super.onCreateView(inflater, container, savedInstanceState);

        view = inflater.inflate(R.layout.add_question_fragment,container,false);
        questionMainActivity = ((QuestionMainActivity) getActivity());

        ((Button) view.findViewById(R.id.addQuestion)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listInputs = new ArrayList<>();
                checkInputs(view);
                onAddQuestion();
            }
        });
        return  view;

    }

    private void onAddQuestion(){

        if(listInputs.size() == 7){ // all fields have value

            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            question = new Questions();

            question.uId = questionMainActivity.auth.getCurrentUser().getUid();
            question.question = listInputs.get(0);
            question.question_dt_added = dateFormat.format((date));
            question.options = question.toListOption(listInputs.get(1),listInputs.get(2),listInputs.get(3),listInputs.get(4));
            question.answer = listInputs.get((6));
            question.category = listInputs.get(5);

            questionMainActivity.db.child("question").push().setValue(question);


            questionMainActivity.showSnackBar("Successfully adding question !");

            clearField();



        }
    }

    private void clearField(){

        ((EditText) view.findViewById(R.id.question)).getText().clear();
        ((EditText) view.findViewById(R.id.choiceone)).getText().clear();
        ((EditText) view.findViewById(R.id.choicetwo)).getText().clear();
        ((EditText) view.findViewById(R.id.choicethree)).getText().clear();
        ((EditText) view.findViewById(R.id.choicefour)).getText().clear();
        ((TextView) view.findViewById(R.id.errorMessage)).setText("");
        ((RadioGroup) view.findViewById(R.id.categoryquestion)).check(0);
        ((RadioGroup) view.findViewById(R.id.questionAnswer)).check(0);

    }

    private void checkInputs(View v) {
        int countChild = ((ViewGroup) v).getChildCount();
        if(countChild > 0){
            for (int x = 0; x < countChild; x++) {
                View childView = ((ViewGroup) v).getChildAt(x);
                if (childView instanceof ViewGroup) {
                    checkInputs(childView);
                } else if (childView instanceof EditText) {
                    String input = ((EditText) childView).getText().toString();
                    if (input.isEmpty()) {
                        ((EditText) childView).setHint("This field is required");
                    } else {
                        listInputs.add(((EditText) childView).getText().toString());
                    }
                } else if (childView instanceof RadioButton) {
                    RadioGroup r = ((RadioGroup) childView.getParent());
                    if (r.getCheckedRadioButtonId() == -1) {
                        ((TextView) view.findViewById(R.id.errorMessage)).setText("Required for category or answer");
                    } else {
                        int id = r.getCheckedRadioButtonId();
                        String buttonVal = ((RadioButton) view.findViewById(id)).getText().toString();
                        parseRadioButtonText(buttonVal);
                    }
                    break;
                }
            }
        }

    }

    private void parseRadioButtonText(String value){
        switch (value){
            case "Choice one" :
                listInputs.add(((EditText) view.findViewById(R.id.choiceone)).getText().toString());
                break;
            case "Choice two" :
                listInputs.add(((EditText) view.findViewById(R.id.choicetwo)).getText().toString());
                break;
            case "Choice three" :
                listInputs.add(((EditText) view.findViewById(R.id.choicethree)).getText().toString());
                break;
            case "Choice four" :
                listInputs.add(((EditText) view.findViewById(R.id.choicefour)).getText().toString());
                break;
            default:
                listInputs.add(value);
        }
    }
}
