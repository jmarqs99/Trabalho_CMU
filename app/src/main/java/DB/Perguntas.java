package DB;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Perguntas {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String pergunta;

    public String resposta;

    public Boolean acertou;

    public int pontos;
}
