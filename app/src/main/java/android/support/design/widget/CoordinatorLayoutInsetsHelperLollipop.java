/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.view.View
 */
package android.support.design.widget;

import android.support.design.widget.CoordinatorLayoutInsetsHelper;
import android.support.v4.view.OnApplyWindowInsetsListener;
import android.support.v4.view.ViewCompat;
import android.view.View;

class CoordinatorLayoutInsetsHelperLollipop
implements CoordinatorLayoutInsetsHelper {
    CoordinatorLayoutInsetsHelperLollipop() {
    }

    @Override
    public void setupForWindowInsets(View view, OnApplyWindowInsetsListener onApplyWindowInsetsListener) {
        if (ViewCompat.getFitsSystemWindows(view)) {
            ViewCompat.setOnApplyWindowInsetsListener(view, onApplyWindowInsetsListener);
            view.setSystemUiVisibility(1280);
        }
    }
}

