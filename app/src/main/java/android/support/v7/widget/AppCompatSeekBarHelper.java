/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.graphics.drawable.Drawable
 *  android.util.AttributeSet
 *  android.widget.ProgressBar
 *  android.widget.SeekBar
 */
package android.support.v7.widget;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatDrawableManager;
import android.support.v7.widget.AppCompatProgressBarHelper;
import android.support.v7.widget.TintTypedArray;
import android.util.AttributeSet;
import android.widget.ProgressBar;
import android.widget.SeekBar;

class AppCompatSeekBarHelper
extends AppCompatProgressBarHelper {
    private static final int[] TINT_ATTRS = new int[]{16843074};
    private final SeekBar mView;

    AppCompatSeekBarHelper(SeekBar seekBar, AppCompatDrawableManager appCompatDrawableManager) {
        super((ProgressBar)seekBar, appCompatDrawableManager);
        this.mView = seekBar;
    }

    @Override
    void loadFromAttributes(AttributeSet object, int n2) {
        super.loadFromAttributes((AttributeSet)object, n2);
        object = TintTypedArray.obtainStyledAttributes(this.mView.getContext(), (AttributeSet)object, TINT_ATTRS, n2, 0);
        Drawable drawable2 = ((TintTypedArray)object).getDrawableIfKnown(0);
        if (drawable2 != null) {
            this.mView.setThumb(drawable2);
        }
        ((TintTypedArray)object).recycle();
    }
}

