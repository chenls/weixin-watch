/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.view.MotionEvent
 */
package ticwear.design.widget;

import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import ticwear.design.widget.TicklableRecyclerView;

public interface TicklableLayoutManager {
    public boolean dispatchTouchEvent(MotionEvent var1);

    public boolean dispatchTouchSidePanelEvent(MotionEvent var1);

    public int getScrollOffset();

    public boolean interceptPreScroll();

    public void setTicklableRecyclerView(TicklableRecyclerView var1);

    public int updateScrollOffset(int var1);

    public boolean useScrollAsOffset();

    public boolean validAdapter(RecyclerView.Adapter var1);
}

