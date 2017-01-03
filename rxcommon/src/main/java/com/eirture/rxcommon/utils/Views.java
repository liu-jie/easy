package com.eirture.rxcommon.utils;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by eirture on 16-12-4.
 */

public class Views {

    @SuppressWarnings("unchecked")
    public static <T extends View> T find(View parent, int viewId) {
        return (T) parent.findViewById(viewId);
    }

    @SuppressWarnings("unchecked")
    public static <T extends View> T find(Activity activity, int viewId) {
        return (T) activity.findViewById(viewId);
    }

    @SuppressWarnings("unchecked")
    public static <T extends View> T inflate(Context context, int layoutId) {
        return (T) LayoutInflater.from(context).inflate(layoutId, null, false);
    }

    public static <T extends View> T inflate(View root, int layoutId) {
        return inflateInternal(root, layoutId, false);
    }

    public static <T extends View> T inflateAndAttach(View root, int layoutId) {
        return inflateInternal(root, layoutId, true);
    }

    @SuppressWarnings("unchecked")
    private static <T extends View> T inflateInternal(View root, int layoutId, boolean attach) {
        if (root == null) throw new NullPointerException("Root view cannot be null");
        return (T) LayoutInflater.from(root.getContext()).inflate(layoutId, (ViewGroup) root, attach);
    }

    private Views() {
        //工具类，不能被实例化
    }

}
