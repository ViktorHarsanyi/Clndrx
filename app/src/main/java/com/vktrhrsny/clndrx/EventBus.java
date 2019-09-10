package com.vktrhrsny.clndrx;

import androidx.annotation.NonNull;
import java.util.Date;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

class EventBus {
    private static EventBus instance = null;
    private PublishSubject<Date> subject = PublishSubject.create();

    private EventBus(){}

    static EventBus getInstance(){
        if (instance == null) {
            instance = new EventBus();
        }
        return instance;
    }
    Observable<Date> toObservable(){
        return subject;
    }

    void publish(@NonNull Date eventDate){
        subject.onNext(eventDate);
    }


}
