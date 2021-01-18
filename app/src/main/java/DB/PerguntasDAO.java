package DB;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface PerguntasDAO {

    @Insert
    public void addPergunta(Perguntas pergunta);

    @Delete
    public void deletePergunta(Perguntas pergunta);

    @Query("Select * FROM perguntas")
    public void getPergunta(Perguntas pergunta);

    @Query("Select * FROM perguntas WHERE id LIKE :ID")
    public void getPerguntabyID(String ID);

}
