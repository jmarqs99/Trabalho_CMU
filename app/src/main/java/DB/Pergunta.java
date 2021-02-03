package DB;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.List;

@Entity
public class Pergunta {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String pergunta;

    @TypeConverters(Converters.class)
    public List<String> opcoes;

    @ColumnInfo(defaultValue = "")
    public String respostaUser;

    public String respostaCorreta;
    
    public Boolean acertou;

    public int pontos;
}
