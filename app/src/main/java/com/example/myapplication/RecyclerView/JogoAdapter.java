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

        // Verifica o código de estado do jogo e faz mudanças no layout consoante a resposta
        switch (jogoItem.getStatus_code()){
            // Jogo ainda não comecou
            case 0:
                // Mostra a hora do jogo
                textView3.setText(jogoItem.getData_inicio().substring(11,16));
                break;
            // Jogo em progresso
            case 1:
                // Mostra o resultado e os minutos
                textView3.setText(jogoItem.getGolos_casa() + " - " + jogoItem.getGolos_fora());
                textView4.setText(jogoItem.getMinuto() + "'");
                textView4.setVisibility(View.VISIBLE);
                break;
            // Jogo terminou
            case 3:
                // Mostra resultado final
                textView3.setText(jogoItem.getGolos_casa() + " - " + jogoItem.getGolos_fora());
                break;
            // Jogo em intervalo
            case 11:
                // Mostra o resultado e coloca os minutos como INT
                textView3.setText(jogoItem.getGolos_casa() + " - " + jogoItem.getGolos_fora());
                textView4.setText("INT");
                textView4.setVisibility(View.VISIBLE);
                break;
            // Jogo em prolongamento
            case 12:
                // Mostra resultado e os minutos
                textView3.setText(jogoItem.getGolos_casa() + " - " + jogoItem.getGolos_fora());
                textView4.setText(jogoItem.getMinuto() + "'");
                textView4.setVisibility(View.VISIBLE);
                break;
            // Penaltis
            case 13:
                // Mostra resultado e que o jogo esta em PEN
                textView3.setText(jogoItem.getGolos_casa() + " - " + jogoItem.getGolos_fora());
                textView4.setText("PEN");
                textView4.setVisibility(View.VISIBLE);
                break;
            // Intervalo prolongamento
            case 14:
                // Mostra o resultado e coloca os minutos como INT
                textView3.setText(jogoItem.getGolos_casa() + " - " + jogoItem.getGolos_fora());
                textView4.setText("INT");
                textView4.setVisibility(View.VISIBLE);
                break;
            // Jogo terminado após penaltis
            case 31:
                // Mostra o resultado
                textView3.setText(jogoItem.getGolos_casa() + " - " + jogoItem.getGolos_fora());
                break;
            // Jogo terminado após prolongamento
            case 32:
                // Mostra o resultado
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
