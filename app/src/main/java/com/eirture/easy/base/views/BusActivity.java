package com.eirture.easy.base.views;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.eirture.easy.base.bus.SingleBus;
import com.eirture.easy.base.bus.SingleBus_;

/**
 * Created by eirture on 16-12-6.
 */

public abstract class BusActivity extends BaseActivity {
    protected SingleBus bus;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bus = SingleBus_.getInstance_(this);
        bus.register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bus.unregister(this);
    }
}
