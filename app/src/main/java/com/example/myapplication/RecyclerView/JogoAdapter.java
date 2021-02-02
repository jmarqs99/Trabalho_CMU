package com.example.myapplication.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class JogoAdapter extends RecyclerView.Adapter<JogoAdapter.JogoViewHolder> {

    private Context mContext;
    private List<Jogo_item> mJogoItem;

    public JogoAdapter(Context mContext, List<Jogo_item> mJogoItem) {
        this.mContext = mContext;
        this.mJogoItem = mJogoItem;
    }

    @NonNull
    @Override
    public JogoAdapter.JogoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View jogoView = inflater.inflate(R.layout.item_jogo,parent,false);

        return new JogoAdapter.JogoViewHolder(jogoView);
    }

    @Override
    public void onBindViewHolder(@NonNull JogoViewHolder holder, int position) {
        Jogo_item jogoItem = mJogoItem.get(position);

        TextView textView = holder.equipaCasaTextView;
        textView.setText(jogoItem.getEquipa_casa());

        TextView textView1 = holder.equipaForaTextView;
        textView1.setText(jogoItem.getEquipa_fora());

        ImageView logoCasa = holder.logoCasaImagem;
        Picasso.get().load(jogoItem.getLogo_casa()).into(logoCasa);

        ImageView logoFora = holder.logoForaImagem;
        Picasso.get().load(jogoItem.getLogo_fora()).into(logoFora);

        TextView textView2 = holder.estadoTextView;
        textView2.setText(jogoItem.getEstado());

        TextView textView3 = holder.golosCasaTextView;
        textView3.setText(jogoItem.getGolos_casa() + "");

        TextView textView4 = holder.golosForaTextView;
        textView4.setText(jogoItem.getGolos_fora() + "");
    }

    @Override
    public int getItemCount() {
        return mJogoItem.size();
    }

    public class JogoViewHolder extends RecyclerView.ViewHolder{
        public TextView equipaCasaTextView;
        public TextView equipaForaTextView;
        public ImageView logoCasaImagem;
        public ImageView logoForaImagem;
        public TextView estadoTextView;
        public TextView golosCasaTextView;
        public TextView golosForaTextView;

        public JogoViewHolder(View itemView){
            super(itemView);
            equipaCasaTextView = itemView.findViewById(R.id.equipa_casa);
            equipaForaTextView = itemView.findViewById(R.id.equipa_fora);
            logoCasaImagem = itemView.findViewById(R.id.equipa_logo_casa);
            logoForaImagem = itemView.findViewById(R.id.equipa_logo_fora);
            estadoTextView = itemView.findViewById(R.id.estado_jogo);
            golosCasaTextView = itemView.findViewById(R.id.golos_casa);
            golosForaTextView = itemView.findViewById(R.id.golos_fora);

        }
    }
}
