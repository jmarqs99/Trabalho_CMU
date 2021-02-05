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

        TextView textView3 = holder.dadosTextView;
        TextView textView4 = holder.minutosTextView;

        switch (jogoItem.getStatus_code()){
            case 0:
                textView3.setText(jogoItem.getData_inicio().substring(11,16));
                break;
            case 1:
                textView3.setText(jogoItem.getGolos_casa() + " - " + jogoItem.getGolos_fora());
                textView4.setText(jogoItem.getMinuto() + "'");
                textView4.setVisibility(View.VISIBLE);
                break;
            case 3:
                textView3.setText(jogoItem.getGolos_casa() + " - " + jogoItem.getGolos_fora());
                break;
            case 11:
                textView3.setText(jogoItem.getGolos_casa() + " - " + jogoItem.getGolos_fora());
                textView4.setText(jogoItem.getMinuto() + "'");
                textView4.setVisibility(View.VISIBLE);
                break;
            case 12:
                textView3.setText(jogoItem.getGolos_casa() + " - " + jogoItem.getGolos_fora());
                textView4.setText(jogoItem.getMinuto() + "'");
                textView4.setVisibility(View.VISIBLE);
                break;
            case 13:
                textView3.setText(jogoItem.getGolos_casa() + " - " + jogoItem.getGolos_fora());
                textView4.setText(jogoItem.getMinuto() + "'");
                textView4.setVisibility(View.VISIBLE);
                break;
            case 14:
                textView3.setText(jogoItem.getGolos_casa() + " - " + jogoItem.getGolos_fora());
                textView4.setText(jogoItem.getMinuto() + "'");
                textView4.setVisibility(View.VISIBLE);
                break;
            case 31:
                textView3.setText(jogoItem.getGolos_casa() + " - " + jogoItem.getGolos_fora());
                break;
            case 32:
                textView3.setText(jogoItem.getGolos_casa() + " - " + jogoItem.getGolos_fora());
                break;
            default:
                textView3.setText("N/A");
        }

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
        public TextView dadosTextView;
        public TextView minutosTextView;

        public JogoViewHolder(View itemView){
            super(itemView);
            equipaCasaTextView = itemView.findViewById(R.id.equipa_casa);
            equipaForaTextView = itemView.findViewById(R.id.equipa_fora);
            logoCasaImagem = itemView.findViewById(R.id.equipa_logo_casa);
            logoForaImagem = itemView.findViewById(R.id.equipa_logo_fora);
            dadosTextView = itemView.findViewById(R.id.dados);
            minutosTextView = itemView.findViewById(R.id.minutos);
        }
    }
}
