package com.example.myapplication;

import android.app.IntentService;
import android.content.Intent;

import androidx.annotation.Nullable;

public class NewQuestionsService extends IntentService {
    public NewQuestionsService() {
        super("NewQuestionsService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

    }
}
