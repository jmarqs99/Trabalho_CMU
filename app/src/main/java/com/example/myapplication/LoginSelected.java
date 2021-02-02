package com.example.myapplication;

import android.widget.TextView;

public interface LoginSelected {

    public void onSelected(String mail, String pass, TextView errorMessage);

    public void onLoginWithGoogleSelected();

    public void onRegisterButtonSelected();

}
