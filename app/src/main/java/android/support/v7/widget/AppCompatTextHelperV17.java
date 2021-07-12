/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.drawable.Drawable
 *  android.util.AttributeSet
 *  android.widget.TextView
 */
package android.support.v7.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatDrawableManager;
import android.support.v7.widget.AppCompatTextHelper;
import android.support.v7.widget.TintInfo;
import android.util.AttributeSet;
import android.widget.TextView;

class AppCompatTextHelperV17
extends AppCompatTextHelper {
    private static final int[] VIEW_ATTRS_v17 = new int[]{16843666, 16843667};
    private TintInfo mDrawableEndTint;
    private TintInfo mDrawableStartTint;

    AppCompatTextHelperV17(TextView textView) {
        super(textView);
    }

    @Override
    void applyCompoundDrawablesTints() {
        super.applyCompoundDrawablesTints();
        if (this.mDrawableStartTint != null || this.mDrawableEndTint != null) {
            Drawable[] drawableArray = this.mView.getCompoundDrawablesRelative();
            this.applyCompoundDrawableTint(drawableArray[0], this.mDrawableStartTint);
            this.applyCompoundDrawableTint(drawableArray[2], this.mDrawableEndTint);
        }
    }

    @Override
    void loadFromAttributes(AttributeSet attributeSet, int n2) {
        super.loadFromAttributes(attributeSet, n2);
        Context context = this.mView.getContext();
        AppCompatDrawableManager appCompatDrawableManager = AppCompatDrawableManager.get();
        attributeSet = context.obtainStyledAttributes(attributeSet, VIEW_ATTRS_v17, n2, 0);
        if (attributeSet.hasValue(0)) {
            this.mDrawableStartTint = AppCompatTextHelperV17.createTintInfo(context, appCompatDrawableManager, attributeSet.getResourceId(0, 0));
        }
        if (attributeSet.hasValue(1)) {
            this.mDrawableEndTint = AppCompatTextHelperV17.createTintInfo(context, appCompatDrawableManager, attributeSet.getResourceId(1, 0));
        }
        attributeSet.recycle();
    }
}

