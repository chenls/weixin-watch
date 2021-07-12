/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.graphics.drawable.Drawable
 *  android.util.AttributeSet
 *  android.widget.ImageView
 */
package android.support.v7.widget;

import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.appcompat.R;
import android.support.v7.widget.AppCompatDrawableManager;
import android.support.v7.widget.DrawableUtils;
import android.support.v7.widget.TintTypedArray;
import android.util.AttributeSet;
import android.widget.ImageView;

public class AppCompatImageHelper {
    private final AppCompatDrawableManager mDrawableManager;
    private final ImageView mView;

    public AppCompatImageHelper(ImageView imageView, AppCompatDrawableManager appCompatDrawableManager) {
        this.mView = imageView;
        this.mDrawableManager = appCompatDrawableManager;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void loadFromAttributes(AttributeSet object, int n2) {
        object = TintTypedArray.obtainStyledAttributes(this.mView.getContext(), (AttributeSet)object, R.styleable.AppCompatImageView, n2, 0);
        try {
            Drawable drawable2 = ((TintTypedArray)object).getDrawableIfKnown(R.styleable.AppCompatImageView_android_src);
            if (drawable2 != null) {
                this.mView.setImageDrawable(drawable2);
            }
            if ((n2 = ((TintTypedArray)object).getResourceId(R.styleable.AppCompatImageView_srcCompat, -1)) != -1 && (drawable2 = this.mDrawableManager.getDrawable(this.mView.getContext(), n2)) != null) {
                this.mView.setImageDrawable(drawable2);
            }
            if ((drawable2 = this.mView.getDrawable()) == null) return;
            DrawableUtils.fixDrawable(drawable2);
            return;
        }
        finally {
            ((TintTypedArray)object).recycle();
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setImageResource(int n2) {
        if (n2 == 0) {
            this.mView.setImageDrawable(null);
            return;
        }
        Drawable drawable2 = this.mDrawableManager != null ? this.mDrawableManager.getDrawable(this.mView.getContext(), n2) : ContextCompat.getDrawable(this.mView.getContext(), n2);
        if (drawable2 != null) {
            DrawableUtils.fixDrawable(drawable2);
        }
        this.mView.setImageDrawable(drawable2);
    }
}

