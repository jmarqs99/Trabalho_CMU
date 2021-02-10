package com.example.myapplication.RecyclerView;

public class Sem_jogos_item {

    /**
     * Mensagem a ser mostrada
     */
    private String mensagem;

    /**
     * Construtor de Sem_jogos_item
     * @param mensagem mensagem a ser mostrada
     */
    public Sem_jogos_item(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
}
