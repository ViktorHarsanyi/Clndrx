package com.vktrhrsny.clndrx.sql;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.vktrhrsny.clndrx.vp.pojo.MemoryEntity;

import java.util.Date;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;

public class MemoVM extends AndroidViewModel {
        private Repo repo;
        private Flowable<List<MemoryEntity>> memoList;
    public MemoVM(@NonNull Application application) {
        super(application);

        repo = new Repo(application);
        memoList = repo.getMemoList();

    }

    public Flowable<List<MemoryEntity>> getMemoList() {
        return memoList;
    }

    public Flowable<List<MemoryEntity>> getSpecificMemo(String date){
        return repo.getSpecificMemo(date);
    }

    public Disposable deleteIt(MemoryEntity memoryEntity){return repo.deleteIt(memoryEntity);}

    public Disposable deleteThem(List<MemoryEntity> memoryEntities){return repo.deleteThem(memoryEntities);}

    public Disposable insert(MemoryEntity memoryEntity){
       return repo.insert(memoryEntity);
    }
}
