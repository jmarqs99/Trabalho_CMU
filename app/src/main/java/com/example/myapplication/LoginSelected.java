package com.example.myapplication;

import android.widget.TextView;

public interface LoginSelected {

    /**
     * Função que irá permitir ao utilizador fazer login na applicação
     *
     * @param mail         o email do utilizador
     * @param pass         a password do utilizador
     * @param errorMessage mensagem de erro
     */
    public void onSelected(String mail, String pass, TextView errorMessage);

    /**
     * Função que irá permitir ao utilizador fazer login através da sua conta Google
     */
    public void onLoginWithGoogleSelected();

    /**
     * Função que irá levar o utilizador para a página de registo de conta
     */
    public void onRegisterButtonSelected();

}
