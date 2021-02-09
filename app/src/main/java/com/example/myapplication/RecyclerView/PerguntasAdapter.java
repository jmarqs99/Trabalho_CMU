package com.example.myapplication.RecyclerView;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.List;

import DB.Pergunta;

public class PerguntasAdapter extends RecyclerView.Adapter<PerguntasAdapter.PerguntaViewHolder> {

    private Context mContext;
    private List<Pergunta> mPerguntaItem;

    public PerguntasAdapter(Context mContext, List<Pergunta> mPerguntaItem) {
        this.mContext = mContext;
        this.mPerguntaItem = mPerguntaItem;
    }

    @NonNull
    @Override
    public PerguntasAdapter.PerguntaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View perguntaView = inflater.inflate(R.layout.item_pergunta,parent,false);

        return new PerguntasAdapter.PerguntaViewHolder(perguntaView);
    }

    @Override
    public void onBindViewHolder(@NonNull PerguntasAdapter.PerguntaViewHolder holder, int position) {
        Pergunta perguntas_itemItem = mPerguntaItem.get(position);

        if(position % 2 == 0 ){
            holder.itemView.setBackgroundColor(Color.rgb(223,226,227));
        }

        TextView textView = holder.perguntaTextView;
        textView.setText(perguntas_itemItem.pergunta);

        TextView textView1 = holder.resposta_corretaTextView;
        textView1.setText(perguntas_itemItem.respostaCorreta);

        TextView textView2 = holder.resposta_userTextView;
        textView2.setText(perguntas_itemItem.respostaUser);

        TextView textView3 = holder.pontos_totalTextView;
        textView3.setText(perguntas_itemItem.pontos + "");

        if(textView1.getText().equals(textView2.getText())){
            textView2.setTextColor(Color.rgb(55,199,113));
        }else{
            textView2.setTextColor(Color.rgb(255,0,0));
        }

    }

    @Override
    public int getItemCount() {
        return mPerguntaItem.size();
    }

    public class PerguntaViewHolder extends RecyclerView.ViewHolder{
        public TextView perguntaTextView;
        public TextView resposta_corretaTextView;
        public TextView resposta_userTextView;
        public TextView pontos_totalTextView;

        public PerguntaViewHolder(View itemView){
            super(itemView);
            perguntaTextView = itemView.findViewById(R.id.pergunta);
            resposta_corretaTextView = itemView.findViewById(R.id.resposta_correta);
            resposta_userTextView = itemView.findViewById(R.id.resposta_user);
            pontos_totalTextView = itemView.findViewById(R.id.pontosPergunta);
        }
    }
}
