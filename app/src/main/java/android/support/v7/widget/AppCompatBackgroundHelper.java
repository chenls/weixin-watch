/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.res.ColorStateList
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.GradientDrawable
 *  android.os.Build$VERSION
 *  android.util.AttributeSet
 *  android.view.View
 */
package android.support.v7.widget;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v7.appcompat.R;
import android.support.v7.widget.AppCompatDrawableManager;
import android.support.v7.widget.DrawableUtils;
import android.support.v7.widget.TintInfo;
import android.util.AttributeSet;
import android.view.View;

class AppCompatBackgroundHelper {
    private TintInfo mBackgroundTint;
    private final AppCompatDrawableManager mDrawableManager;
    private TintInfo mInternalBackgroundTint;
    private TintInfo mTmpInfo;
    private final View mView;

    AppCompatBackgroundHelper(View view, AppCompatDrawableManager appCompatDrawableManager) {
        this.mView = view;
        this.mDrawableManager = appCompatDrawableManager;
    }

    private void compatTintDrawableUsingFrameworkTint(@NonNull Drawable drawable2) {
        if (this.mTmpInfo == null) {
            this.mTmpInfo = new TintInfo();
        }
        TintInfo tintInfo = this.mTmpInfo;
        tintInfo.clear();
        ColorStateList colorStateList = ViewCompat.getBackgroundTintList(this.mView);
        if (colorStateList != null) {
            tintInfo.mHasTintList = true;
            tintInfo.mTintList = colorStateList;
        }
        if ((colorStateList = ViewCompat.getBackgroundTintMode(this.mView)) != null) {
            tintInfo.mHasTintMode = true;
            tintInfo.mTintMode = colorStateList;
        }
        if (tintInfo.mHasTintList || tintInfo.mHasTintMode) {
            AppCompatDrawableManager.tintDrawable(drawable2, tintInfo, this.mView.getDrawableState());
        }
    }

    private boolean shouldCompatTintUsingFrameworkTint(@NonNull Drawable drawable2) {
        return Build.VERSION.SDK_INT == 21 && drawable2 instanceof GradientDrawable;
    }

    /*
     * Enabled aggressive block sorting
     */
    void applySupportBackgroundTint() {
        Drawable drawable2 = this.mView.getBackground();
        if (drawable2 == null) return;
        if (this.mBackgroundTint != null) {
            AppCompatDrawableManager.tintDrawable(drawable2, this.mBackgroundTint, this.mView.getDrawableState());
            return;
        } else {
            if (this.mInternalBackgroundTint != null) {
                AppCompatDrawableManager.tintDrawable(drawable2, this.mInternalBackgroundTint, this.mView.getDrawableState());
                return;
            }
            if (!this.shouldCompatTintUsingFrameworkTint(drawable2)) return;
            this.compatTintDrawableUsingFrameworkTint(drawable2);
            return;
        }
    }

    ColorStateList getSupportBackgroundTintList() {
        if (this.mBackgroundTint != null) {
            return this.mBackgroundTint.mTintList;
        }
        return null;
    }

    PorterDuff.Mode getSupportBackgroundTintMode() {
        if (this.mBackgroundTint != null) {
            return this.mBackgroundTint.mTintMode;
        }
        return null;
    }

    void loadFromAttributes(AttributeSet attributeSet, int n2) {
        attributeSet = this.mView.getContext().obtainStyledAttributes(attributeSet, R.styleable.ViewBackgroundHelper, n2, 0);
        try {
            ColorStateList colorStateList;
            if (attributeSet.hasValue(R.styleable.ViewBackgroundHelper_android_background) && (colorStateList = this.mDrawableManager.getTintList(this.mView.getContext(), attributeSet.getResourceId(R.styleable.ViewBackgroundHelper_android_background, -1))) != null) {
                this.setInternalBackgroundTint(colorStateList);
            }
            if (attributeSet.hasValue(R.styleable.ViewBackgroundHelper_backgroundTint)) {
                ViewCompat.setBackgroundTintList(this.mView, attributeSet.getColorStateList(R.styleable.ViewBackgroundHelper_backgroundTint));
            }
            if (attributeSet.hasValue(R.styleable.ViewBackgroundHelper_backgroundTintMode)) {
                ViewCompat.setBackgroundTintMode(this.mView, DrawableUtils.parseTintMode(attributeSet.getInt(R.styleable.ViewBackgroundHelper_backgroundTintMode, -1), null));
            }
            return;
        }
        finally {
            attributeSet.recycle();
        }
    }

    void onSetBackgroundDrawable(Drawable drawable2) {
        this.setInternalBackgroundTint(null);
    }

    /*
     * Enabled aggressive block sorting
     */
    void onSetBackgroundResource(int n2) {
        ColorStateList colorStateList = this.mDrawableManager != null ? this.mDrawableManager.getTintList(this.mView.getContext(), n2) : null;
        this.setInternalBackgroundTint(colorStateList);
    }

    /*
     * Enabled aggressive block sorting
     */
    void setInternalBackgroundTint(ColorStateList colorStateList) {
        if (colorStateList != null) {
            if (this.mInternalBackgroundTint == null) {
                this.mInternalBackgroundTint = new TintInfo();
            }
            this.mInternalBackgroundTint.mTintList = colorStateList;
            this.mInternalBackgroundTint.mHasTintList = true;
        } else {
            this.mInternalBackgroundTint = null;
        }
        this.applySupportBackgroundTint();
    }

    void setSupportBackgroundTintList(ColorStateList colorStateList) {
        if (this.mBackgroundTint == null) {
            this.mBackgroundTint = new TintInfo();
        }
        this.mBackgroundTint.mTintList = colorStateList;
        this.mBackgroundTint.mHasTintList = true;
        this.applySupportBackgroundTint();
    }

    void setSupportBackgroundTintMode(PorterDuff.Mode mode) {
        if (this.mBackgroundTint == null) {
            this.mBackgroundTint = new TintInfo();
        }
        this.mBackgroundTint.mTintMode = mode;
        this.mBackgroundTint.mHasTintMode = true;
        this.applySupportBackgroundTint();
    }
}

