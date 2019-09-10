package com.vktrhrsny.clndrx.sql;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.vktrhrsny.clndrx.vp.pojo.MemoryEntity;


import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;


@Dao
public interface MemoryDao {

        @Query("SELECT * FROM memories")
        Flowable<List<MemoryEntity>> getMemoList();

        @Insert
        Completable addData(MemoryEntity memoryEntity);

        @Query("SELECT * FROM memories WHERE memo_date =:memoDate")
        Flowable<List<MemoryEntity>> getMemo(String memoDate);

        @Delete
        Completable deleteAll(List<MemoryEntity> memoryEntities);

        @Delete
        Completable deleteIt(MemoryEntity entity);

}
