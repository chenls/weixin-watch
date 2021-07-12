/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.animation.Interpolator
 */
package android.support.v4.view;

import android.view.View;
import android.view.animation.Interpolator;

class ViewPropertyAnimatorCompatJellybeanMr2 {
    ViewPropertyAnimatorCompatJellybeanMr2() {
    }

    public static Interpolator getInterpolator(View view) {
        return (Interpolator)view.animate().getInterpolator();
    }
}

