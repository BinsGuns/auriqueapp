package com.thesis.aurique.squiz.view.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.thesis.aurique.squiz.R;
import com.thesis.aurique.squiz.model.Questions;

public class BaseActivity extends AppCompatActivity {

    public FirebaseAuth auth;
    public  FirebaseAuth.AuthStateListener authStateListener;
    public FirebaseUser user;
    public DatabaseReference db;
    public Context context;
    public SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        auth = FirebaseAuth.getInstance();
        context = getApplicationContext();
        db = FirebaseDatabase.getInstance().getReference();

        sharedPreferences = this.getSharedPreferences("SquizPreferences",Context.MODE_PRIVATE);


    }

    @Override
    public void onStart() {
        super.onStart();
      //  auth.addAuthStateListener(authStateListener); // add state listener on app start
    }


    @Override
    public void onStop() {
        super.onStop();


//        if (authStateListener != null) {
//            auth.removeAuthStateListener(authStateListener); // remove state listener when app stop
//        }
    }
}
