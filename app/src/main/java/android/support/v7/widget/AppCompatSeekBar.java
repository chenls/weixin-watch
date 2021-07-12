/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.widget.SeekBar
 */
package android.support.v7.widget;

import android.content.Context;
import android.support.v7.appcompat.R;
import android.support.v7.widget.AppCompatDrawableManager;
import android.support.v7.widget.AppCompatSeekBarHelper;
import android.util.AttributeSet;
import android.widget.SeekBar;

public class AppCompatSeekBar
extends SeekBar {
    private AppCompatSeekBarHelper mAppCompatSeekBarHelper;
    private AppCompatDrawableManager mDrawableManager = AppCompatDrawableManager.get();

    public AppCompatSeekBar(Context context) {
        this(context, null);
    }

    public AppCompatSeekBar(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.seekBarStyle);
    }

    public AppCompatSeekBar(Context context, AttributeSet attributeSet, int n2) {
        super(context, attributeSet, n2);
        this.mAppCompatSeekBarHelper = new AppCompatSeekBarHelper(this, this.mDrawableManager);
        this.mAppCompatSeekBarHelper.loadFromAttributes(attributeSet, n2);
    }
}

