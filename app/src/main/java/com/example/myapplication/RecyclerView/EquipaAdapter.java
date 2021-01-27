package com.example.myapplication.RecyclerView;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.List;

public class EquipaAdapter extends RecyclerView.Adapter<EquipaAdapter.EquipaViewHolder> {

    private Context mContext;
    private List<Equipa_item> mEquipaItem;

    public EquipaAdapter(Context mContext, List<Equipa_item> mEquipaItem) {
        this.mContext = mContext;
        this.mEquipaItem = mEquipaItem;
        Log.d("TAMANHO DAS EQUIPAS" , mEquipaItem.size() + "");
    }

    @NonNull
    @Override
    public EquipaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View equipaView = inflater.inflate(R.layout.item_equipa,parent,false);

        return new EquipaViewHolder(equipaView);
    }

    @Override
    public void onBindViewHolder(@NonNull EquipaViewHolder holder, int position) {
        Equipa_item equipaItem = mEquipaItem.get(position);

        TextView textView = holder.nameTextView;
        textView.setText(equipaItem.getNome());

        TextView textView2 = holder.pontosTextView;
        textView2.setText(equipaItem.getPontos() + "");

    }

    @Override
    public int getItemCount() {
        return mEquipaItem.size();
    }


    public class EquipaViewHolder extends RecyclerView.ViewHolder{
        public TextView nameTextView;
        public TextView pontosTextView;

        public EquipaViewHolder(View itemView){
            super(itemView);

            nameTextView = itemView.findViewById(R.id.equipa_nome);
            pontosTextView = itemView.findViewById(R.id.equipa_pontos);
        }
    }

}

