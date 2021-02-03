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
    public void addPergunta(Pergunta pergunta);

    @Delete
    public void deletePergunta(Pergunta pergunta);

    @Query("Select * FROM Pergunta WHERE id LIKE :ID")
    public LiveData<List<Pergunta>> getPergunta(String ID);

    @Query("SELECT SUM(pontos) FROM Pergunta WHERE acertou=1")
    public int getConta();

    @Query("SELECT * FROM Pergunta")
    public List<Pergunta> getPerguntas();


    @Query("DELETE FROM Pergunta")
    public void removerPerguntas();



}
