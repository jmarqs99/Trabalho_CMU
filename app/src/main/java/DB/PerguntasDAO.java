package DB;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PerguntasDAO {

    @Insert
    public void addPergunta(Perguntas pergunta);

    @Delete
    public void deletePergunta(Perguntas pergunta);

    @Query("Select * FROM perguntas WHERE id LIKE :ID")
    public LiveData<List<Perguntas>> getPergunta(String ID);

    @Query("SELECT SUM(pontos) FROM perguntas WHERE acertou=1")
    public int getConta();

    @Query("SELECT * FROM perguntas")
    public List<Perguntas> getPerguntas();



}
