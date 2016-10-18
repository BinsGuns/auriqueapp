package com.thesis.aurique.squiz.view.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.google.firebase.database.DataSnapshot;

import com.thesis.aurique.squiz.model.Questions;

import java.util.List;

/**
 * Created by Vince on 10/16/2016.
 */
public class QuestionAdapter extends PagerAdapter {


    private DataSnapshot dataSnapshot;
    private Context context;

    public QuestionAdapter(Context context, DataSnapshot dataSnapshot) {
        this.context = context;
        this.dataSnapshot = dataSnapshot;

    }




    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        int iterator = 0;
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setLayoutParams(new ViewGroup.LayoutParams(ViewPager.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));

        for(DataSnapshot d : dataSnapshot.getChildren()) {
            Questions q = d.getValue(Questions.class);

            if(iterator == position) {

                TextView tv = new TextView(context);
                TextView tv1 = new TextView(context);
                tv.setText(q.question);
                tv1.setText("TESTTT");

                layout.addView(tv);
                layout.addView(tv1);
                break;
            }

            iterator++;

        }
        container.addView(layout);
        return container;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


    @Override
    public int getCount() {
        return (int)dataSnapshot.getChildrenCount();
    }
}
