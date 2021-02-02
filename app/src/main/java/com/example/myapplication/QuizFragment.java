package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import DB.PerguntasDB;

public class QuizFragment extends Fragment {

    PerguntasDB dataBase;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.quizs_fragment, container, false);

        return v;
    }
}
