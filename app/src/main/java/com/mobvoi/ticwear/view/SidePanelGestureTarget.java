/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.view.MotionEvent
 */
package com.mobvoi.ticwear.view;

import android.view.MotionEvent;

public interface SidePanelGestureTarget {
    public boolean onDoubleTapSidePanel(MotionEvent var1);

    public boolean onFlingSidePanel(MotionEvent var1, MotionEvent var2, float var3, float var4);

    public boolean onLongPressSidePanel(MotionEvent var1);

    public boolean onScrollSidePanel(MotionEvent var1, MotionEvent var2, float var3, float var4);

    public boolean onSingleTapSidePanel(MotionEvent var1);
}

