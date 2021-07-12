/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.content.Context
 *  android.os.Bundle
 *  android.util.AttributeSet
 *  android.util.Log
 *  android.view.MotionEvent
 */
package ticwear.design.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import com.mobvoi.ticwear.view.SidePanelEventDispatcher;
import ticwear.design.R;
import ticwear.design.widget.CoordinatorLayout;
import ticwear.design.widget.TicklableLayoutManager;
import ticwear.design.widget.TicklableRecyclerViewBehavior;

@CoordinatorLayout.DefaultBehavior(value=TicklableRecyclerViewBehavior.class)
@TargetApi(value=20)
public class TicklableRecyclerView
extends RecyclerView
implements SidePanelEventDispatcher {
    static final String TAG = "TicklableRV";
    private boolean mSkipNestedScroll;
    @Nullable
    private TicklableLayoutManager mTicklableLayoutManager;

    public TicklableRecyclerView(Context context) {
        this(context, null);
    }

    public TicklableRecyclerView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public TicklableRecyclerView(Context context, AttributeSet attributeSet, int n2) {
        this(context, attributeSet, n2, 0);
    }

    public TicklableRecyclerView(Context context, AttributeSet attributeSet, int n2, int n3) {
        super(context, attributeSet, n2);
        this.setHasFixedSize(true);
        this.setOverScrollMode(2);
        this.mSkipNestedScroll = false;
        if (!this.isInEditMode() && this.getItemAnimator() != null) {
            long l2 = (long)context.getResources().getInteger(R.integer.design_anim_list_item_state_change) / 4L;
            this.getItemAnimator().setMoveDuration(l2);
        }
    }

    @Override
    public boolean dispatchNestedFling(float f2, float f3, boolean bl2) {
        return !this.mSkipNestedScroll && super.dispatchNestedFling(f2, f3, bl2);
    }

    @Override
    public boolean dispatchNestedPreFling(float f2, float f3) {
        return !this.mSkipNestedScroll && super.dispatchNestedPreFling(f2, f3);
    }

    public boolean dispatchNestedPrePerformAccessibilityAction(int n2, Bundle bundle) {
        return !this.mSkipNestedScroll && super.dispatchNestedPrePerformAccessibilityAction(n2, bundle);
    }

    @Override
    public boolean dispatchNestedPreScroll(int n2, int n3, int[] nArray, int[] nArray2) {
        return !this.mSkipNestedScroll && super.dispatchNestedPreScroll(n2, n3, nArray, nArray2);
    }

    @Override
    public boolean dispatchNestedScroll(int n2, int n3, int n4, int n5, int[] nArray) {
        return !this.mSkipNestedScroll && super.dispatchNestedScroll(n2, n3, n4, n5, nArray);
    }

    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        return this.mTicklableLayoutManager != null && this.mTicklableLayoutManager.dispatchTouchEvent(motionEvent) || super.dispatchTouchEvent(motionEvent);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public boolean dispatchTouchSidePanelEvent(MotionEvent motionEvent, @NonNull SidePanelEventDispatcher.SuperCallback superCallback) {
        boolean bl2 = false;
        if (this.mTicklableLayoutManager == null) {
            if (this.dispatchTouchEvent(motionEvent)) return true;
            if (!superCallback.superDispatchTouchSidePanelEvent(motionEvent)) return bl2;
            return true;
        }
        if (this.mTicklableLayoutManager.dispatchTouchSidePanelEvent(motionEvent)) return true;
        if (!superCallback.superDispatchTouchSidePanelEvent(motionEvent)) return bl2;
        return true;
    }

    public int getScrollOffset() {
        if (this.mTicklableLayoutManager != null) {
            return this.mTicklableLayoutManager.getScrollOffset();
        }
        return 0;
    }

    public boolean interceptPreScroll() {
        return this.mTicklableLayoutManager != null && this.mTicklableLayoutManager.interceptPreScroll();
    }

    public void scrollBySkipNestedScroll(int n2, int n3) {
        this.mSkipNestedScroll = true;
        this.scrollBy(n2, n3);
        this.mSkipNestedScroll = false;
    }

    @Override
    public void setAdapter(RecyclerView.Adapter adapter) {
        if (this.mTicklableLayoutManager == null || this.mTicklableLayoutManager.validAdapter(adapter)) {
            super.setAdapter(adapter);
            return;
        }
        throw new IllegalArgumentException("Adapter is invalid for current TicklableLayoutManager.");
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        super.setLayoutManager(layoutManager);
        if (this.isInEditMode() || this.mTicklableLayoutManager == layoutManager) {
            return;
        }
        if (this.mTicklableLayoutManager != null) {
            this.mTicklableLayoutManager.setTicklableRecyclerView(null);
        }
        if (!(layoutManager instanceof TicklableLayoutManager)) {
            Log.w((String)TAG, (String)"To let TicklableRecyclerView support complex tickle events, let LayoutManager implements TicklableLayoutManager.");
            this.mTicklableLayoutManager = null;
            return;
        }
        if (((TicklableLayoutManager)((Object)layoutManager)).validAdapter(this.getAdapter())) {
            this.mTicklableLayoutManager = (TicklableLayoutManager)((Object)layoutManager);
            this.mTicklableLayoutManager.setTicklableRecyclerView(this);
            return;
        }
        Log.w((String)TAG, (String)"To let TicklableRecyclerView support complex tickle events, make sure your Adapter is compat with TicklableLayoutManager.");
        this.mTicklableLayoutManager = null;
    }

    public int updateScrollOffset(int n2) {
        int n3 = n2;
        if (this.mTicklableLayoutManager != null) {
            n3 = this.mTicklableLayoutManager.updateScrollOffset(n2);
        }
        return n3;
    }

    public boolean useScrollAsOffset() {
        return this.mTicklableLayoutManager != null && this.mTicklableLayoutManager.useScrollAsOffset();
    }
}

