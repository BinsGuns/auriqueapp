package com.thesis.aurique.squiz.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.thesis.aurique.squiz.databinding.LoginFragmentBinding;

import com.thesis.aurique.squiz.model.User;
import com.thesis.aurique.squiz.view.activity.MainActivity;
import com.thesis.aurique.squiz.view.activity.QuestionMainActivity;

public class LoginFragment extends Fragment{

    private Button loginButton,registerButton;
    private MainActivity mainActivity;
    public LoginFragmentBinding loginFragmentBinding;
    private User user;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         super.onCreateView(inflater, container, savedInstanceState);
        loginFragmentBinding = LoginFragmentBinding.inflate(inflater,container,false);
        user = new User();
        loginFragmentBinding.setUser(user);

        loginButton = loginFragmentBinding.loginButton;
        registerButton = loginFragmentBinding.registerButton;

        mainActivity = ((MainActivity) getActivity());
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLogin();
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterFragment registerFragment = new RegisterFragment();
                mainActivity.changeFragment(registerFragment);
            }
        });

        return loginFragmentBinding.getRoot();
    }


    private void onLogin(){
        String email = loginFragmentBinding.email.getText().toString().trim();
        String password = loginFragmentBinding.password.getText().toString().trim();
        mainActivity.showProgress();
        mainActivity.auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        mainActivity.hideProgress();
                        if (!task.isSuccessful()) {
                            if(task.getException().getLocalizedMessage().contains("no user")){
                                loginFragmentBinding.errorMessage.setText("Invalid username or password");
                            } else {
                                loginFragmentBinding.errorMessage.setText(task.getException().getLocalizedMessage());
                            }
                        } else {
                            mainActivity.toQuestionActivity();
                        }
                    }
                });


        }

}
