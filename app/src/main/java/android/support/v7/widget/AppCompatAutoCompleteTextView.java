/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.drawable.Drawable
 *  android.util.AttributeSet
 *  android.view.View
 *  android.widget.AutoCompleteTextView
 *  android.widget.TextView
 */
package android.support.v7.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v4.view.TintableBackgroundView;
import android.support.v7.appcompat.R;
import android.support.v7.widget.AppCompatBackgroundHelper;
import android.support.v7.widget.AppCompatDrawableManager;
import android.support.v7.widget.AppCompatTextHelper;
import android.support.v7.widget.TintContextWrapper;
import android.support.v7.widget.TintTypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

public class AppCompatAutoCompleteTextView
extends AutoCompleteTextView
implements TintableBackgroundView {
    private static final int[] TINT_ATTRS = new int[]{16843126};
    private AppCompatBackgroundHelper mBackgroundTintHelper;
    private AppCompatDrawableManager mDrawableManager = AppCompatDrawableManager.get();
    private AppCompatTextHelper mTextHelper;

    public AppCompatAutoCompleteTextView(Context context) {
        this(context, null);
    }

    public AppCompatAutoCompleteTextView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.autoCompleteTextViewStyle);
    }

    public AppCompatAutoCompleteTextView(Context object, AttributeSet attributeSet, int n2) {
        super(TintContextWrapper.wrap((Context)object), attributeSet, n2);
        object = TintTypedArray.obtainStyledAttributes(this.getContext(), attributeSet, TINT_ATTRS, n2, 0);
        if (((TintTypedArray)object).hasValue(0)) {
            this.setDropDownBackgroundDrawable(((TintTypedArray)object).getDrawable(0));
        }
        ((TintTypedArray)object).recycle();
        this.mBackgroundTintHelper = new AppCompatBackgroundHelper((View)this, this.mDrawableManager);
        this.mBackgroundTintHelper.loadFromAttributes(attributeSet, n2);
        this.mTextHelper = AppCompatTextHelper.create((TextView)this);
        this.mTextHelper.loadFromAttributes(attributeSet, n2);
        this.mTextHelper.applyCompoundDrawablesTints();
    }

    protected void drawableStateChanged() {
        super.drawableStateChanged();
        if (this.mBackgroundTintHelper != null) {
            this.mBackgroundTintHelper.applySupportBackgroundTint();
        }
        if (this.mTextHelper != null) {
            this.mTextHelper.applyCompoundDrawablesTints();
        }
    }

    @Override
    @Nullable
    public ColorStateList getSupportBackgroundTintList() {
        if (this.mBackgroundTintHelper != null) {
            return this.mBackgroundTintHelper.getSupportBackgroundTintList();
        }
        return null;
    }

    @Override
    @Nullable
    public PorterDuff.Mode getSupportBackgroundTintMode() {
        if (this.mBackgroundTintHelper != null) {
            return this.mBackgroundTintHelper.getSupportBackgroundTintMode();
        }
        return null;
    }

    public void setBackgroundDrawable(Drawable drawable2) {
        super.setBackgroundDrawable(drawable2);
        if (this.mBackgroundTintHelper != null) {
            this.mBackgroundTintHelper.onSetBackgroundDrawable(drawable2);
        }
    }

    public void setBackgroundResource(@DrawableRes int n2) {
        super.setBackgroundResource(n2);
        if (this.mBackgroundTintHelper != null) {
            this.mBackgroundTintHelper.onSetBackgroundResource(n2);
        }
    }

    public void setDropDownBackgroundResource(@DrawableRes int n2) {
        if (this.mDrawableManager != null) {
            this.setDropDownBackgroundDrawable(this.mDrawableManager.getDrawable(this.getContext(), n2));
            return;
        }
        super.setDropDownBackgroundResource(n2);
    }

    @Override
    public void setSupportBackgroundTintList(@Nullable ColorStateList colorStateList) {
        if (this.mBackgroundTintHelper != null) {
            this.mBackgroundTintHelper.setSupportBackgroundTintList(colorStateList);
        }
    }

    @Override
    public void setSupportBackgroundTintMode(@Nullable PorterDuff.Mode mode) {
        if (this.mBackgroundTintHelper != null) {
            this.mBackgroundTintHelper.setSupportBackgroundTintMode(mode);
        }
    }

    public void setTextAppearance(Context context, int n2) {
        super.setTextAppearance(context, n2);
        if (this.mTextHelper != null) {
            this.mTextHelper.onSetTextAppearance(context, n2);
        }
    }
}

