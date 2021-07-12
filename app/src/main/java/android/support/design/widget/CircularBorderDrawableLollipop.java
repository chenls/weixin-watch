/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.graphics.Outline
 */
package android.support.design.widget;

import android.graphics.Outline;
import android.support.design.widget.CircularBorderDrawable;

class CircularBorderDrawableLollipop
extends CircularBorderDrawable {
    CircularBorderDrawableLollipop() {
    }

    public void getOutline(Outline outline) {
        this.copyBounds(this.mRect);
        outline.setOval(this.mRect);
    }
}

