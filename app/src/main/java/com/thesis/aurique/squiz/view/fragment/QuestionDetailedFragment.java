package com.thesis.aurique.squiz.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thesis.aurique.squiz.R;

/**
 * Created by Vince on 10/17/2016.
 */
public class QuestionDetailedFragment extends Fragment {



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.question_detailed_fragment_layout,container,false);

        return  view;
    }
}
