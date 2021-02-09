package com.example.myapplication.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class EquipaAdapter extends RecyclerView.Adapter<EquipaAdapter.EquipaViewHolder> {

    private Context mContext;
    private List<Equipa_item> mEquipaItem;

    public EquipaAdapter(Context mContext, List<Equipa_item> mEquipaItem) {
        this.mContext = mContext;
        this.mEquipaItem = mEquipaItem;
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

        pintaClassificao(holder,position);

        TextView textView = holder.nameTextView;
        textView.setText(equipaItem.getNome());

        TextView textView2 = holder.pontosTextView;
        textView2.setText(equipaItem.getPontos() + "");

        TextView textView3 = holder.posicaoTextView;
        textView3.setText(equipaItem.getPosicao() + ".");


        ImageView imageView = holder.logoImagem;
        Picasso.get().load(equipaItem.getLogo()).into(imageView);

        TextView textView5 = holder.numJogosTextView;
        textView5.setText(equipaItem.getNum_jogos() + "");

        TextView textView6 = holder.golosMarcadosTextView;
        textView6.setText(equipaItem.getGolos_marcados() + "");

        TextView textView7 = holder.golosSofridosTextView;
        textView7.setText(equipaItem.getGolos_sofridos() + "");
    }

    private void pintaClassificao(@NonNull EquipaViewHolder holder, int position){
        switch (position){
            case 0:
                holder.itemView.setBackgroundColor(Color.rgb(161,251,204));
                break;
            case 1:
                holder.itemView.setBackgroundColor(Color.rgb(220,251,235));
                break;
            case 2:
                holder.itemView.setBackgroundColor(Color.rgb(220,251,235));
                break;
            case 3:
                holder.itemView.setBackgroundColor(Color.rgb(220,251,235));
                break;
            case 4:
                holder.itemView.setBackgroundColor(Color.rgb(254,251,36));
                break;
            case 5:
                holder.itemView.setBackgroundColor(Color.rgb(255,254,184));
                break;
            case 15:
                holder.itemView.setBackgroundColor(Color.rgb(251,192,192));
                break;
            case 16:
                holder.itemView.setBackgroundColor(Color.rgb(255,111,111));
                break;
            case 17:
                holder.itemView.setBackgroundColor(Color.rgb(255,111,111));
                break;
            default:
                holder.itemView.setBackgroundColor(Color.rgb(255,255,255));
        }
    }

    @Override
    public int getItemCount() {
        return mEquipaItem.size();
    }


    public class EquipaViewHolder extends RecyclerView.ViewHolder{
        public TextView nameTextView;
        public TextView pontosTextView;
        public TextView posicaoTextView;
        public ImageView logoImagem;
        public TextView numJogosTextView;
        public TextView golosMarcadosTextView;
        public TextView golosSofridosTextView;

        public EquipaViewHolder(View itemView){
            super(itemView);

            nameTextView = itemView.findViewById(R.id.equipa_nome);
            pontosTextView = itemView.findViewById(R.id.equipa_pontos);
            posicaoTextView = itemView.findViewById(R.id.equipa_posicao);
            logoImagem = itemView.findViewById(R.id.equipa_logo2);
            numJogosTextView = itemView.findViewById(R.id.equipa_numJogos);
            golosMarcadosTextView = itemView.findViewById(R.id.equipa_golosMarcados);
            golosSofridosTextView = itemView.findViewById(R.id.equipa_golosSofridos);
        }
    }

}

