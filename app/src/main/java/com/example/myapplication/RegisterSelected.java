package com.example.myapplication;

import android.widget.TextView;

public interface RegisterSelected {

    public void OnRegisterSelected(String email, String password, TextView errorMessage);
    public void OnBackToLoginPageSelected();
}
