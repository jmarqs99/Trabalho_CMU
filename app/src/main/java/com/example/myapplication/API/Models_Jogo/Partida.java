package com.example.myapplication.API.Models_Jogo;

import java.util.List;

public class Partida {

    private query query;

    private List<jogo> data;

    public query getQuery() {
        return query;
    }

    public void setQuery(query query) {
        this.query = query;
    }

    public List<jogo> getData() {
        return data;
    }

    public void setData(List<jogo> data) {
        this.data = data;
    }
}
