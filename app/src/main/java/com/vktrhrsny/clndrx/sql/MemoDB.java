package com.vktrhrsny.clndrx.sql;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.vktrhrsny.clndrx.vp.pojo.MemoryEntity;

@Database(entities = MemoryEntity.class,version=3)
@TypeConverters({DateTC.class})
public abstract class MemoDB extends RoomDatabase {

    private static volatile MemoDB INSTANCE = null;


   static MemoDB getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (MemoDB.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MemoDB.class, "memoDB")
                            .addCallback(dbCallBack)
                            .fallbackToDestructiveMigration()
                            .build();


                }
            }
        }
        return INSTANCE;
    }


    private static RoomDatabase.Callback dbCallBack =
            new RoomDatabase.Callback(){

                @Override
                public void onCreate (@NonNull SupportSQLiteDatabase db){
                    super.onCreate(db);
                    //new PopulateDbAsync(INSTANCE).execute();
                }
            };

    public abstract MemoryDao getMemoDAO();

}
