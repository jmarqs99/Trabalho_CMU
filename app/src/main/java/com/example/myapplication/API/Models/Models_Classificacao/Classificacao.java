package com.example.myapplication.API.Models.Models_Classificacao;

public class Classificacao {

    private query query;
    private data data;

    public query getQuery() {
        return query;
    }

    public void setQuery(query query) {
        this.query = query;
    }

    public data getData() {
        return data;
    }

    public void setData(data data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Classificacao{" +
                "query=" + query +
                ", data=" + data.toString() +
                '}';
    }
}
