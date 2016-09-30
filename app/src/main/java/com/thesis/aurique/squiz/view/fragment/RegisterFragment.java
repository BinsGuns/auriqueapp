package com.thesis.aurique.squiz.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.thesis.aurique.squiz.R;
import com.thesis.aurique.squiz.view.activity.MainActivity;
import com.thesis.aurique.squiz.view.activity.QuestionMainActivity;

public class RegisterFragment extends Fragment {

    private MainActivity mainActivity;
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.register_fragment,container,false);
        mainActivity = ((MainActivity) getActivity());
        Button registerButton = (Button) view.findViewById(R.id.registerButton);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRegister();
            }
        });

        return view;
    }

    private void onRegister(){
        EditText emailText = (EditText) view.findViewById(R.id.email);
        EditText passwordText = (EditText) view.findViewById(R.id.password);
        mainActivity.showProgress();
        mainActivity.auth.createUserWithEmailAndPassword(emailText.getText().toString().trim(),passwordText.getText().toString().trim())
        .addOnCompleteListener(mainActivity, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                mainActivity.hideProgress();
                if (!task.isSuccessful()) {
                    ((TextView) view.findViewById(R.id.errorMessage)).setText(task.getException().getLocalizedMessage());
                } else{
                    mainActivity.toQuestionActivity();
                }
            }
        });
    }
}
