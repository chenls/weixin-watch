/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Bitmap
 *  android.graphics.Bitmap$Config
 *  android.graphics.Canvas
 *  android.graphics.ColorFilter
 *  android.graphics.Paint
 *  android.graphics.Point
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.PorterDuffColorFilter
 *  android.graphics.drawable.BitmapDrawable
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.TransitionDrawable
 *  android.os.AsyncTask
 *  android.view.Display
 *  android.view.View
 *  android.view.Window
 */
package ticwear.design.utils.blur;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.view.Display;
import android.view.View;
import android.view.Window;
import ticwear.design.utils.blur.BlurFactor;
import ticwear.design.utils.blur.FastBlur;

public class BlurBehind {
    private int mAnimationDuration;
    private final BlurFactor mBlurFactor = new BlurFactor();
    private final Context mContext;

    private BlurBehind(Context context) {
        this.mContext = context;
        this.mAnimationDuration = 0;
    }

    public static BlurBehind from(Context context) {
        return new BlurBehind(context);
    }

    private Bitmap prepareSource(View view) {
        if (view == null) {
            return null;
        }
        Bitmap bitmap = Bitmap.createBitmap((int)view.getWidth(), (int)view.getHeight(), (Bitmap.Config)Bitmap.Config.RGB_565);
        view.draw(new Canvas(bitmap));
        return bitmap;
    }

    public BlurBehind animate(int n2) {
        this.mAnimationDuration = n2;
        return this;
    }

    public BlurBehindExecutor capture(@Nullable View view) {
        return new BlurBehindExecutor(this.mContext, this.prepareSource(view), this.mBlurFactor, this.mAnimationDuration);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public BlurBehindExecutor capture(@Nullable Window window) {
        if (window != null) {
            window = window.getDecorView();
            return this.capture((View)window);
        }
        window = null;
        return this.capture((View)window);
    }

    public BlurBehind color(int n2) {
        this.mBlurFactor.color = n2;
        return this;
    }

    public BlurBehind radius(int n2) {
        this.mBlurFactor.radius = n2;
        return this;
    }

    public BlurBehind sampling(int n2) {
        this.mBlurFactor.sampling = n2;
        return this;
    }

    public static class BlurBehindExecutor {
        private final int mAnimationDuration;
        private final BlurFactor mBlurFactor;
        private final Context mContext;
        private Runnable mPostSetBackgroundRunnable;
        private Runnable mPreSetBackgroundRunnable;
        private final Bitmap mSource;

        private BlurBehindExecutor(Context context, Bitmap bitmap, BlurFactor blurFactor, int n2) {
            this.mContext = context;
            this.mSource = bitmap;
            this.mBlurFactor = blurFactor;
            this.mAnimationDuration = n2;
        }

        /*
         * Enabled aggressive block sorting
         */
        private TransitionDrawable changeBackground(View view, Drawable drawable2, int n2) {
            Drawable drawable3;
            if (n2 <= 0) {
                view.setBackground(drawable2);
                return null;
            }
            Object object = drawable3 = view.getBackground();
            if (drawable3 instanceof TransitionDrawable) {
                object = (TransitionDrawable)drawable3;
                object = object.getNumberOfLayers() < 1 ? null : object.getDrawable(object.getNumberOfLayers() - 1);
            }
            drawable2 = new TransitionDrawable(new Drawable[]{object, drawable2});
            view.setBackground(drawable2);
            drawable2.startTransition(n2);
            return drawable2;
        }

        private BlurBehindFuture start(final ViewGetter viewGetter) {
            if (viewGetter == null) {
                return new BlurBehindFuture(null);
            }
            return new BlurBehindFuture(new BlurBehindTask(this.mBlurFactor, new OnBlurFinishedCallback(){

                @Override
                public void onBlurFinished(Bitmap bitmap) {
                    if (BlurBehindExecutor.this.mPreSetBackgroundRunnable != null) {
                        BlurBehindExecutor.this.mPreSetBackgroundRunnable.run();
                    }
                    bitmap = new BitmapDrawable(BlurBehindExecutor.this.mContext.getResources(), bitmap);
                    BlurBehindExecutor.this.changeBackground(viewGetter.get(), (Drawable)bitmap, BlurBehindExecutor.this.mAnimationDuration);
                    if (BlurBehindExecutor.this.mPostSetBackgroundRunnable != null) {
                        BlurBehindExecutor.this.mPostSetBackgroundRunnable.run();
                    }
                }
            }).execute(new Bitmap[]{this.mSource}));
        }

        public BlurBehindFuture into(final View view) {
            if (view == null) {
                return new BlurBehindFuture(null);
            }
            this.mBlurFactor.width = view.getMeasuredWidth();
            this.mBlurFactor.height = view.getMeasuredHeight();
            return this.start(new ViewGetter(){

                @Override
                public View get() {
                    return view;
                }
            });
        }

        public BlurBehindFuture into(final Window window) {
            if (window == null) {
                return new BlurBehindFuture(null);
            }
            Display display = window.getWindowManager().getDefaultDisplay();
            Point point = new Point();
            display.getSize(point);
            this.mBlurFactor.width = point.x;
            this.mBlurFactor.height = point.y;
            return this.start(new ViewGetter(){

                @Override
                public View get() {
                    return window.getDecorView();
                }
            });
        }

        public BlurBehindExecutor postSetBackground(Runnable runnable) {
            this.mPostSetBackgroundRunnable = runnable;
            return this;
        }

        public BlurBehindExecutor preSetBackground(Runnable runnable) {
            this.mPreSetBackgroundRunnable = runnable;
            return this;
        }

        private static interface ViewGetter {
            public View get();
        }
    }

    public static class BlurBehindFuture {
        private AsyncTask mBlurBehindTask;

        private BlurBehindFuture(AsyncTask asyncTask) {
            this.mBlurBehindTask = asyncTask;
        }

        public void cancel() {
            if (this.mBlurBehindTask != null) {
                this.mBlurBehindTask.cancel(true);
                this.mBlurBehindTask = null;
            }
        }
    }

    private static class BlurBehindTask
    extends AsyncTask<Bitmap, Void, Bitmap> {
        private final BlurFactor mBlurFactor;
        private OnBlurFinishedCallback mOnBlurFinishedCallback;

        public BlurBehindTask(BlurFactor blurFactor, OnBlurFinishedCallback onBlurFinishedCallback) {
            this.mBlurFactor = blurFactor;
            this.mOnBlurFinishedCallback = onBlurFinishedCallback;
        }

        /*
         * Enabled aggressive block sorting
         */
        protected Bitmap doInBackground(Bitmap ... bitmap) {
            int n2;
            int n3;
            block3: {
                block2: {
                    if (((Bitmap)bitmap).length == 0) break block2;
                    bitmap = bitmap[0];
                    n3 = this.mBlurFactor.width / this.mBlurFactor.sampling;
                    n2 = this.mBlurFactor.height / this.mBlurFactor.sampling;
                    if (n3 != 0 && n2 != 0) break block3;
                }
                return null;
            }
            Bitmap bitmap2 = Bitmap.createBitmap((int)n3, (int)n2, (Bitmap.Config)Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap2);
            canvas.scale(1.0f / (float)this.mBlurFactor.sampling, 1.0f / (float)this.mBlurFactor.sampling);
            Paint paint = new Paint();
            paint.setFlags(3);
            paint.setColorFilter((ColorFilter)new PorterDuffColorFilter(this.mBlurFactor.color, PorterDuff.Mode.SRC_ATOP));
            canvas.drawBitmap(bitmap, 0.0f, 0.0f, paint);
            return FastBlur.doBlur(bitmap2, this.mBlurFactor.radius, true);
        }

        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute((Object)bitmap);
            if (this.mOnBlurFinishedCallback != null) {
                this.mOnBlurFinishedCallback.onBlurFinished(bitmap);
            }
        }
    }

    public static interface OnBlurFinishedCallback {
        public void onBlurFinished(Bitmap var1);
    }
}

