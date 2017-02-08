package com.yan.campusbbs.util;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

/**
 * Created by yan on 2016/12/7.
 */

public final class RxBus {
    private static RxBus instance;

    private Subject<Object> bus;

    public static RxBus getInstance() {
        if (instance == null) {
            instance = new RxBus();
        }
        return instance;
    }

    private RxBus() {
        if (bus == null) {
            synchronized (RxBus.class) {
                if (bus == null) {
                    bus = PublishSubject.create().toSerialized();
                }
            }
        }
    }

    public void post(Object event) {
        bus.toSerialized().onNext(event);
    }

    public <T> Observable<T> getEvent(Class<T> eventType) {
        return bus.toSerialized().ofType(eventType);
    }


}