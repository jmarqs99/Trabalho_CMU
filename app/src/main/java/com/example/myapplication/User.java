package com.example.myapplication;

public class User {

    private String email;

    private int pontos;

    public User(String email) {
        this.email = email;
    }
    
    public void setPontos(int pontos) {
        this.pontos = pontos;
    }

    public String getEmail() {
        return this.email;
    }

    public int getPontos() {
        return this.pontos;
    }
}
