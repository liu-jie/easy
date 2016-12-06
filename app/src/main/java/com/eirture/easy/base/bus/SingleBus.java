package com.eirture.easy.base.bus;

import com.squareup.otto.Bus;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EBean;

/**
 * Created by eirture on 16-12-6.
 */
@EBean(scope = EBean.Scope.Singleton)
public class SingleBus {

    private Bus bus;

    @AfterInject
    void init() {
        bus = new Bus();
    }

    public void register(Object o) {
        bus.register(o);
    }

    public void unregister(Object o) {
        bus.unregister(o);
    }

    public <T extends Event> void post(T e) {
        bus.post(e);
    }

}
