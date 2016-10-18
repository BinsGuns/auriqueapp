package com.thesis.aurique.squiz.view.activity;


import android.app.ProgressDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.thesis.aurique.squiz.R;
import com.thesis.aurique.squiz.databinding.QuestionActivityBinding;
import com.thesis.aurique.squiz.model.Questions;
import com.thesis.aurique.squiz.model.User;
import com.thesis.aurique.squiz.view.fragment.AddQuestionFragment;
import com.thesis.aurique.squiz.view.fragment.QuestionFragment;
import com.thesis.aurique.squiz.view.fragment.QuestionWelcomeFragment;

import java.util.ArrayList;
import java.util.List;


public class QuestionMainActivity extends BaseActivity implements View.OnClickListener{

    private QuestionActivityBinding questionFragmentBinding;
    private User u;
    private DrawerLayout drawerLayout;
    public List<Questions> listQuestion;
    public ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

        questionFragmentBinding = DataBindingUtil.setContentView(this, R.layout.question_activity);
        drawerLayout = questionFragmentBinding.drawer;
        ListView listView = questionFragmentBinding.listItem;

        checkUser();
        List<String> menuList = initializeList();

        listView.setAdapter(new ArrayAdapter<>(this, R.layout.item_nav_layout, menuList));

        questionFragmentBinding.navButton.setOnClickListener(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedItem(((TextView) view).getText().toString());
            }
        });

        changeFragment(new QuestionWelcomeFragment(),"QuestionWelcomeTag");

    }

    private void checkUser() {
        FirebaseUser user = auth.getCurrentUser();
        u = new User();
        u.uId = user.getUid();
        u.email = user.getEmail();
        u.username = user.getDisplayName();
    }

    private List<String> initializeList(){
        List<String> menuList = new ArrayList<>();

        if(u.uId.equals("BDRXsRfGxhOa8Xmaci3hzSx4K0C2")){ // uid of admin that can add questions
            menuList.add("Add Question");
        }
        // add more menu here for more functionality
            menuList.add("My Profile");
            menuList.add("Log out");

        return menuList;
    }


    public void showSnackBar(String message){
        Snackbar snackbar  = Snackbar.make(findViewById(android.R.id.content),message,Snackbar.LENGTH_LONG);
        snackbar.show();
    }


    private void selectedItem(String val){
        switch(val){
            case "Log out":
                auth.signOut();
                drawerLayout.closeDrawers();
                Intent i = new Intent(this,MainActivity.class);
                startActivity(i);
                break;
            case "Add Question":
                drawerLayout.closeDrawers();
                changeFragment(new AddQuestionFragment(),"AddQuestionTag");
                break;
        }
    }

    public void changeFragment(Fragment fragment,String tag){
        drawerLayout.closeDrawers();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.questionMainWrapper,fragment,tag);
        fragmentTransaction.commit();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.navButton :
                    openDrawer();
                break;
            case R.id.proceed :

                break;
        }
    }


    private void openDrawer(){
        drawerLayout.openDrawer(Gravity.LEFT);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        FragmentManager fm = getSupportFragmentManager();

        if(fm.findFragmentByTag("QuestionTag") instanceof  QuestionFragment){
            QuestionFragment frag = ((QuestionFragment) fm.findFragmentByTag("QuestionTag"));
            ViewPager v = frag.viewPager;
            if(v.getCurrentItem() == 0) {

                super.onBackPressed();
                finish();
            } else {

                v.setCurrentItem(v.getCurrentItem()-1);
            }
        } else {

            finish();
        }

    }




}
