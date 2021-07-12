/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.widget.ImageButton
 */
package android.support.design.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageButton;

class VisibilityAwareImageButton
extends ImageButton {
    private int mUserSetVisibility = this.getVisibility();

    public VisibilityAwareImageButton(Context context) {
        this(context, null);
    }

    public VisibilityAwareImageButton(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public VisibilityAwareImageButton(Context context, AttributeSet attributeSet, int n2) {
        super(context, attributeSet, n2);
    }

    final int getUserSetVisibility() {
        return this.mUserSetVisibility;
    }

    final void internalSetVisibility(int n2, boolean bl2) {
        super.setVisibility(n2);
        if (bl2) {
            this.mUserSetVisibility = n2;
        }
    }

    public void setVisibility(int n2) {
        this.internalSetVisibility(n2, true);
    }
}

