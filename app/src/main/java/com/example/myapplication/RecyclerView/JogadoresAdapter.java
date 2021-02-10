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

public class JogadoresAdapter extends RecyclerView.Adapter<JogadoresAdapter.JogadoresViewHolder> {

    private Context mContext;
    private List<Jogadores_item> mJogadorItem;

    public JogadoresAdapter(Context mContext, List<Jogadores_item> mJogadorItem) {
        this.mContext = mContext;
        this.mJogadorItem = mJogadorItem;
    }

    @NonNull
    @Override
    public JogadoresViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View jogadorView = inflater.inflate(R.layout.item_jogador,parent,false);

        return new JogadoresAdapter.JogadoresViewHolder(jogadorView);
    }

    @Override
    public void onBindViewHolder(@NonNull JogadoresViewHolder holder, int position) {
        Jogadores_item jogadorItem = mJogadorItem.get(position);

        // Caso verfique a condição irá colocar a cor de fundo cinzento
        if(position % 2 != 0 ){
            holder.itemView.setBackgroundColor(Color.rgb(223,226,227));
        }

        TextView textView = holder.posicaoTextView;
        textView.setText(position + 1  + ".");

        TextView textView2 = holder.emailTextView;
        textView2.setText(jogadorItem.getEmail());

        TextView textView3 = holder.pontosTextView;
        textView3.setText(jogadorItem.getPontos() + "");
    }

    @Override
    public int getItemCount() {
        return mJogadorItem.size();
    }

    public class JogadoresViewHolder extends RecyclerView.ViewHolder{
        public TextView posicaoTextView;
        public TextView emailTextView;
        public TextView pontosTextView;

        public JogadoresViewHolder(View itemView){
            super(itemView);
            posicaoTextView = itemView.findViewById(R.id.posicao);
            emailTextView = itemView.findViewById(R.id.email);
            pontosTextView = itemView.findViewById(R.id.pontos);
        }
    }
}
