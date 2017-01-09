package com.eirture.easy.base.utils.glide;

import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.ViewPropertyAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.SizeReadyCallback;

public class GlideHelper {

    private static final ViewPropertyAnimation.Animator ANIMATOR =
            new ViewPropertyAnimation.Animator() {
                @Override
                public void animate(View view) {
                    view.setAlpha(0f);
                    view.animate().alpha(1f);
                }
            };

    private GlideHelper() {
    }

    public static void loadResource(@DrawableRes int drawableId, @NonNull ImageView image) {
        DisplayMetrics metrics = image.getResources().getDisplayMetrics();
        final int displayWidth = metrics.widthPixels;
        final int displayHeight = metrics.heightPixels;

        Glide.with(image.getContext())
                .load(drawableId)
                .asBitmap()
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(new BitmapImageViewTarget(image) {
                    @Override
                    public void getSize(final SizeReadyCallback cb) {
                        // We don'data want to load very big images on devices with small screens.
                        // This will help Glide correctly choose images scale when reading them.
                        super.getSize(new SizeReadyCallback() {
                            @Override
                            public void onSizeReady(int width, int height) {
                                cb.onSizeReady(displayWidth / 2, displayHeight / 2);
                            }
                        });
                    }
                });
    }

    public static void loadPath(@NonNull final String path, @NonNull final ImageView image,
                                @NonNull final ImageLoadingListener listener) {
        Glide.with(image.getContext())
                .load(path)
                .asBitmap()
                .dontAnimate()
                .placeholder(image.getDrawable())
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .listener(new GlideDrawableListener() {
                    @Override
                    public void onSuccess(String url) {
                        if (url.equals(path)) {
                            if (listener != null) {
                                listener.onLoaded();
                            }
                        }
                    }

                    @Override
                    public void onFail(String url) {
                        if (listener != null) {
                            listener.onFailed();
                        }
                    }
                })
                .into(new GlideImageTarget(image));
    }

    public static void loadPicture(String path, @NonNull ImageView imageView) {

        Glide.with(imageView.getContext())
                .load(path)
                .asBitmap()
//                .placeholder(R.drawable.ic_default_picture)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(new GlideImageTarget(imageView));
    }

    public interface ImageLoadingListener {
        void onLoaded();

        void onFailed();
    }

}
