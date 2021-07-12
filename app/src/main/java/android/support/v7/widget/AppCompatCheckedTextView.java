/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.widget.CheckedTextView
 *  android.widget.TextView
 */
package android.support.v7.widget;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.v7.widget.AppCompatDrawableManager;
import android.support.v7.widget.AppCompatTextHelper;
import android.support.v7.widget.TintContextWrapper;
import android.support.v7.widget.TintTypedArray;
import android.util.AttributeSet;
import android.widget.CheckedTextView;
import android.widget.TextView;

public class AppCompatCheckedTextView
extends CheckedTextView {
    private static final int[] TINT_ATTRS = new int[]{0x1010108};
    private AppCompatDrawableManager mDrawableManager;
    private AppCompatTextHelper mTextHelper = AppCompatTextHelper.create((TextView)this);

    public AppCompatCheckedTextView(Context context) {
        this(context, null);
    }

    public AppCompatCheckedTextView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16843720);
    }

    public AppCompatCheckedTextView(Context object, AttributeSet attributeSet, int n2) {
        super(TintContextWrapper.wrap((Context)object), attributeSet, n2);
        this.mTextHelper.loadFromAttributes(attributeSet, n2);
        this.mTextHelper.applyCompoundDrawablesTints();
        this.mDrawableManager = AppCompatDrawableManager.get();
        object = TintTypedArray.obtainStyledAttributes(this.getContext(), attributeSet, TINT_ATTRS, n2, 0);
        this.setCheckMarkDrawable(((TintTypedArray)object).getDrawable(0));
        ((TintTypedArray)object).recycle();
    }

    protected void drawableStateChanged() {
        super.drawableStateChanged();
        if (this.mTextHelper != null) {
            this.mTextHelper.applyCompoundDrawablesTints();
        }
    }

    public void setCheckMarkDrawable(@DrawableRes int n2) {
        if (this.mDrawableManager != null) {
            this.setCheckMarkDrawable(this.mDrawableManager.getDrawable(this.getContext(), n2));
            return;
        }
        super.setCheckMarkDrawable(n2);
    }

    public void setTextAppearance(Context context, int n2) {
        super.setTextAppearance(context, n2);
        if (this.mTextHelper != null) {
            this.mTextHelper.onSetTextAppearance(context, n2);
        }
    }
}

