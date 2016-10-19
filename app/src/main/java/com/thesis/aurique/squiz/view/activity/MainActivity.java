package com.thesis.aurique.squiz.view.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;

import com.thesis.aurique.squiz.R;
import com.thesis.aurique.squiz.view.fragment.LoginFragment;

public class MainActivity extends BaseActivity {

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);

        if(auth.getCurrentUser() == null) { // no user currently logged in
            changeFragment(new LoginFragment());
        } else { // if already logged in redirect to question fragment
           toQuestionActivity();
        }

    }

    public void toQuestionActivity(){
        Intent i = new Intent(MainActivity.this,QuestionMainActivity.class);
        startActivity(i);
    }

    public void changeFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.mainFragment,fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
        System.exit(0);
    }

    public void showProgress(){
        progressDialog = ProgressDialog.show(this,"Initializing","Please wait...",true);
    }

    public void hideProgress() {
        progressDialog.dismiss();
    }
}
