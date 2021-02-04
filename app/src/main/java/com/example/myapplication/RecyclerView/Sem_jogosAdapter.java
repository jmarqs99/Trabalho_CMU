package com.example.myapplication.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.List;

public class Sem_jogosAdapter extends RecyclerView.Adapter<Sem_jogosAdapter.SemJogosViewHolder> {

    private Context mContext;
    private List<Sem_jogos_item> items;

    public Sem_jogosAdapter(Context mContext, List<Sem_jogos_item> items) {
        this.mContext = mContext;
        this.items = items;
    }

    @NonNull
    @Override
    public Sem_jogosAdapter.SemJogosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View semJogosView = inflater.inflate(R.layout.sem_jogos,parent,false);

        return new Sem_jogosAdapter.SemJogosViewHolder(semJogosView);
    }

    @Override
    public void onBindViewHolder(@NonNull Sem_jogosAdapter.SemJogosViewHolder holder, int position) {
        Sem_jogos_item jogoItem = items.get(position);

        TextView textView = holder.texto;
        textView.setText(jogoItem.getMensagem());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class SemJogosViewHolder extends RecyclerView.ViewHolder{
        public TextView texto;

        public SemJogosViewHolder(View itemView){
            super(itemView);
            texto = itemView.findViewById(R.id.SemJogostextview);
        }
    }
}
