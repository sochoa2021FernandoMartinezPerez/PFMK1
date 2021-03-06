package net.iesseveroochoa.fernandomartinezperez.pfmk1.database;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.*;
import androidx.sqlite.db.SupportSQLiteDatabase;

import net.iesseveroochoa.fernandomartinezperez.pfmk1.model.Personaje;


import java.util.concurrent.*;

@Database(entities = {Personaje.class}, version = 1)
public abstract class PersonajeRoomDatabase extends RoomDatabase {

    public abstract PersonajeDAO personajeDAO();

    private static volatile PersonajeRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static PersonajeRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (PersonajeRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            PersonajeRoomDatabase.class,
                            "PFMK1_Personajes")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            Log.i("DB", "OnCreate"); //TODO Borrar
            super.onCreate(db);

            databaseWriteExecutor.execute(() -> {
                PersonajeDAO mDao = INSTANCE.personajeDAO();
                Personaje personaje;
            });
        }


        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            Log.i("DB", "OnOpen"); //TODO Borrar
            super.onOpen(db);
        }
    };
}
