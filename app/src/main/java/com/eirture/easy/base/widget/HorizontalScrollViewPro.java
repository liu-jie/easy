package com.eirture.easy.base.widget;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;

/**
 * Created by eirture on 16-12-21.
 */

public class HorizontalScrollViewPro extends HorizontalScrollView {

    public interface ScrollViewListener {
        void onScrollChanged(int l, int t, int oldl, int oldt);
    }

    private ScrollViewListener scrollViewListener;

    public HorizontalScrollViewPro(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HorizontalScrollViewPro(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public HorizontalScrollViewPro(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (scrollViewListener != null) {
            scrollViewListener.onScrollChanged(l, t, oldl, oldt);
        }
    }

    public void addOnScrollChangedListener(ScrollViewListener listener) {
        this.scrollViewListener = listener;
    }
}
