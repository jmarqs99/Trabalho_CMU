package com.example.myapplication;

public class User {

    private String email;
    private String pergunta;
    private String resposta;
    private Boolean acertou;
    private int pontos;

    public User(String email) {
        this.email = email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPergunta(String pergunta) {
        this.pergunta = pergunta;
    }

    public void setResposta(String resposta) {
        this.resposta = resposta;
    }

    public void setAcertou(Boolean acertou) {
        this.acertou = acertou;
    }

    public void setPontos(int pontos) {
        this.pontos = pontos;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPergunta() {
        return this.pergunta;
    }
    public String getResposta() {
        return this.resposta;
    }
    public int getPontos() {
        return this.pontos;
    }
}
