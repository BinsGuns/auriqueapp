package com.thesis.aurique.squiz.view.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.thesis.aurique.squiz.R;

import java.util.HashMap;

/**
 * Created by Vince on 10/19/2016.
 */
public class FinishActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.finish_layout);
        findViewById(R.id.done).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        db.child("scores").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
              Object s =  dataSnapshot.getValue(Object.class);
              Long score = (Long) ((HashMap) s).get(auth.getCurrentUser().getUid());

                ((TextView) findViewById(R.id.score)).setText("Your final score is "+score);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this,QuestionMainActivity.class);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();

    }

    @Override
    public void onStop() {
        super.onStop();
        SharedPreferences.Editor e =  sharedPreferences.edit();
        e.clear();
        e.commit();
    }
}
