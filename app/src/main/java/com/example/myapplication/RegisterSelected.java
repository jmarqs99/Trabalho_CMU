package com.example.myapplication;

import android.widget.TextView;

public interface RegisterSelected {

    /**
     * Função que irá registar um utilizador na app
     * @param email o email do utilizador
     * @param password a password do utilizador
     * @param errorMessage uma mensagem de erro
     */
    public void OnRegisterSelected(String email, String password, TextView errorMessage);

    /**
     * Função que irá levar o utilizador para a página de login
     */
    public void OnBackToLoginPageSelected();
}
