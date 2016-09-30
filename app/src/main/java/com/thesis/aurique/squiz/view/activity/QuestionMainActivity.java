package com.thesis.aurique.squiz.view.activity;


import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import com.thesis.aurique.squiz.view.fragment.QuestionWelcomeFragment;

import java.util.ArrayList;
import java.util.List;


public class QuestionMainActivity extends BaseActivity implements View.OnClickListener{

    private QuestionActivityBinding questionFragmentBinding;
    private User u;
    private DrawerLayout drawerLayout;
    public List<Questions> listQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);

        questionFragmentBinding = DataBindingUtil.setContentView(this,R.layout.question_activity);
        drawerLayout = questionFragmentBinding.drawer;
        ListView listView = questionFragmentBinding.listItem;

        checkUser();
        List<String> menuList =  initializeList();

        listView.setAdapter(new ArrayAdapter<>(this,R.layout.item_nav_layout,menuList));

        questionFragmentBinding.navButton.setOnClickListener(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedItem(((TextView) view).getText().toString());
            }
        });

        changeFragment(new QuestionWelcomeFragment());


        db.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.d("","added");
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                listQuestion = new ArrayList<>();
                for(DataSnapshot d : dataSnapshot.getChildren()){
                    Questions q =  d.getValue(Questions.class);
                    listQuestion.add(q);
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
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

        if(u.uId.equals("rjUbEYoKUDfJB357099YDBLgNnH3")){ // uid of admin that can add questions
            menuList.add("Add Question");
        }
        // add more menu here for more functionality
            menuList.add("My Profile");
            menuList.add("Log out");

        return menuList;
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
                changeFragment(new AddQuestionFragment());
                break;
        }
    }

    public void changeFragment(Fragment fragment){
        drawerLayout.closeDrawers();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.questionMainWrapper,fragment);
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
        fm.popBackStack();
    }




}
