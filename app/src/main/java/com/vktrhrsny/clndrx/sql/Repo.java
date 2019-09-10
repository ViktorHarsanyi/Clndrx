package com.vktrhrsny.clndrx.sql;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;
import com.vktrhrsny.clndrx.vp.pojo.MemoryEntity;
import java.lang.ref.WeakReference;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;

import io.reactivex.schedulers.Schedulers;

public class Repo {
    private MemoryDao dao;
    private Flowable<List<MemoryEntity>> memoList;
    private WeakReference<Context> contextWeakReference;
    private MemoDB db;
    Repo(Application app){
        contextWeakReference = new WeakReference<>(app.getApplicationContext());
        db = MemoDB.getDatabase(app.getApplicationContext());
        dao = db.getMemoDAO();
        memoList = db.getMemoDAO().getMemoList();
    }

    Flowable<List<MemoryEntity>> getMemoList() {
        return memoList;
    }

    Flowable<List<MemoryEntity>> getSpecificMemo(String date){
        return dao.getMemo(date);
    }

    Disposable deleteIt(MemoryEntity entity){
        return dao.deleteIt(entity).subscribeOn(Schedulers.io())
                .subscribe(Toast.makeText(contextWeakReference.get(), "Deleted: "+entity.getMemoryID(), Toast.LENGTH_SHORT)::show, e -> {
                            Toast.makeText(contextWeakReference.get(), "Del_Error: " + e.toString(), Toast.LENGTH_SHORT).show();});
    }

    Disposable deleteThem(List<MemoryEntity> memoryEntities){
        return dao.deleteAll(memoryEntities).subscribeOn(Schedulers.io()).subscribe();
}

    Disposable insert(MemoryEntity entity){
        return dao.addData(entity).subscribeOn(Schedulers.io()).subscribe(Toast.makeText(contextWeakReference.get(), "Success Insert", Toast.LENGTH_SHORT)::show, e -> {
            Toast.makeText(contextWeakReference.get(), "Error: " + e.toString(), Toast.LENGTH_SHORT).show();
        });
    }

}
