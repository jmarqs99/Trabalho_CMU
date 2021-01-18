package DB;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Perguntas.class}, version = 1)
public abstract class PerguntasDB extends RoomDatabase {

    public abstract PerguntasDAO perguntasDAO();

    private static PerguntasDB INSTANCE;

    private static final Object sLock = new Object();

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            
        }
    };

    public static PerguntasDB getInstance(Context context){
        synchronized (sLock){
            if(INSTANCE == null){
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        PerguntasDB.class, "database-name")
                        .addMigrations(MIGRATION_1_2).build();
            }
            return INSTANCE;
        }
    }

}
