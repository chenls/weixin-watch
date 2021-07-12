/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.RippleDrawable
 *  android.util.AttributeSet
 *  android.util.TypedValue
 *  android.widget.ImageButton
 */
package ticwear.design.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ImageButton;
import ticwear.design.R;
import ticwear.design.drawable.ArcDrawable;

public class PrimaryButton
extends ImageButton {
    public PrimaryButton(Context context) {
        this(context, null);
    }

    public PrimaryButton(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.tic_primaryButtonStyle);
    }

    public PrimaryButton(Context context, AttributeSet attributeSet, int n2) {
        this(context, attributeSet, n2, R.style.Widget_Ticwear_PrimaryButton);
    }

    public PrimaryButton(Context context, AttributeSet attributeSet, int n2, int n3) {
        super(context, attributeSet, n2, n3);
    }

    public void setBackgroundDrawable(Drawable drawable2) {
        TypedValue typedValue = new TypedValue();
        this.getContext().getTheme().resolveAttribute(16843820, typedValue, true);
        typedValue = ColorStateList.valueOf((int)typedValue.data);
        drawable2 = new ArcDrawable(drawable2);
        super.setBackgroundDrawable((Drawable)new RippleDrawable((ColorStateList)typedValue, drawable2, drawable2));
    }
}

