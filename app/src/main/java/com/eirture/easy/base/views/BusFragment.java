package com.eirture.easy.base.views;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eirture.easy.base.bus.SingleBus;
import com.eirture.easy.base.bus.SingleBus_;

/**
 * Created by eirture on 16-12-6.
 */

public abstract class BusFragment extends BaseFragment {
    protected SingleBus bus;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bus = SingleBus_.getInstance_(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        bus.register(this);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        bus.unregister(this);
        super.onDestroyView();
    }

}
