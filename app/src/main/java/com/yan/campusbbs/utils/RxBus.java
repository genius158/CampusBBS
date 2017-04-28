package com.yan.campusbbs.utils;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

/**
 * Created by yan on 2016/12/7.
 */

public final class RxBus {
    private Subject<Object> bus;

    @Inject
    public RxBus() {
        bus = PublishSubject.create().toSerialized();
    }

    public void post(Object event) {
        bus.toSerialized().onNext(event);
    }

    public <T> Observable<T> getEvent(Class<T> eventType) {
        return bus.toSerialized().ofType(eventType);
    }

}