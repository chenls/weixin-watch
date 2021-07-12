/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.graphics.drawable.Drawable
 */
package android.support.wearable.view;

import android.annotation.TargetApi;
import android.graphics.drawable.Drawable;

@TargetApi(value=20)
public interface GridPageOptions {
    public Drawable getBackground();

    public void setBackgroundListener(BackgroundListener var1);

    public static interface BackgroundListener {
        public void notifyBackgroundChanged();
    }
}

