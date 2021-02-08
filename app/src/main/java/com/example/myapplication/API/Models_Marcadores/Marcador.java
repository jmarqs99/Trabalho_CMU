package com.example.myapplication.API.Models_Marcadores;

import java.util.List;

public class Marcador {

    private query query;

    private List<data> data;

    public query getQuery() {
        return query;
    }

    public void setQuery(query query) {
        this.query = query;
    }

    public List<com.example.myapplication.API.Models_Marcadores.data> getData() {
        return data;
    }

    public void setData(List<com.example.myapplication.API.Models_Marcadores.data> data) {
        this.data = data;
    }
}
