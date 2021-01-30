package DB;

import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Perguntas {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String pergunta;

    public String[] opcoes;

    @ColumnInfo(defaultValue = "")
    public String respostaUser;

    public String respostaCorreta;
    
    public Boolean acertou;

    public int pontos;
}
