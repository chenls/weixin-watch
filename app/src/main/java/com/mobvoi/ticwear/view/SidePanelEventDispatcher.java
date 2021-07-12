/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.view.MotionEvent
 */
package com.mobvoi.ticwear.view;

import android.support.annotation.NonNull;
import android.view.MotionEvent;

public interface SidePanelEventDispatcher {
    public boolean dispatchTouchSidePanelEvent(MotionEvent var1, @NonNull SuperCallback var2);

    public static interface SuperCallback {
        public boolean superDispatchTouchSidePanelEvent(MotionEvent var1);
    }
}

