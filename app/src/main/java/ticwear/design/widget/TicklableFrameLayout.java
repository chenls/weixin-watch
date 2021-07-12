/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.view.MotionEvent
 *  android.widget.FrameLayout
 */
package ticwear.design.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import com.mobvoi.ticwear.view.SidePanelEventDispatcher;

public class TicklableFrameLayout
extends FrameLayout
implements SidePanelEventDispatcher {
    private SidePanelEventDispatcher mSidePanelEventDispatcher;

    public TicklableFrameLayout(Context context) {
        super(context);
    }

    public TicklableFrameLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public TicklableFrameLayout(Context context, AttributeSet attributeSet, int n2) {
        this(context, attributeSet, n2, 0);
    }

    public TicklableFrameLayout(Context context, AttributeSet attributeSet, int n2, int n3) {
        super(context, attributeSet, n2, n3);
    }

    @Override
    public boolean dispatchTouchSidePanelEvent(MotionEvent motionEvent, @NonNull SidePanelEventDispatcher.SuperCallback superCallback) {
        return this.mSidePanelEventDispatcher != null && this.mSidePanelEventDispatcher.dispatchTouchSidePanelEvent(motionEvent, superCallback);
    }

    public void setSidePanelEventDispatcher(SidePanelEventDispatcher sidePanelEventDispatcher) {
        this.mSidePanelEventDispatcher = sidePanelEventDispatcher;
    }
}

