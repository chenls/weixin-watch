/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.util.Log
 *  android.view.View
 */
package ticwear.design.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import ticwear.design.widget.AppBarLayout;
import ticwear.design.widget.CoordinatorLayout;
import ticwear.design.widget.MathUtils;
import ticwear.design.widget.TicklableRecyclerView;

public class TicklableRecyclerViewBehavior
extends AppBarLayout.ScrollingViewBehavior {
    static final String TAG = "TicklableRVBehavior";
    private View hostView;
    private boolean scrolling = false;

    public TicklableRecyclerViewBehavior() {
    }

    public TicklableRecyclerViewBehavior(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    private boolean offsetValidation(int n2, int n3) {
        if (Math.abs(n2) < Math.abs(n3)) {
            Log.w((String)TAG, (String)"total offset should not smaller than raw offset");
            this.setRawTopAndBottomOffset(0);
            return false;
        }
        if (!MathUtils.sameSign(n2, n3)) {
            Log.w((String)TAG, (String)"total offset has different sign than raw offset");
            this.setRawTopAndBottomOffset(0);
            return false;
        }
        return true;
    }

    /*
     * Enabled aggressive block sorting
     */
    private int reduceRemovableOffset(int n2) {
        int n3;
        int n4 = this.getRawTopAndBottomOffset();
        if (n4 == 0 || !this.offsetValidation(n3 = this.getTopAndBottomOffset(), n4)) {
            return n2;
        }
        boolean bl2 = MathUtils.sameSign(n2, n3);
        n3 = Math.abs(n3) - Math.abs(n2);
        if (bl2 && n3 >= 0) {
            n4 = Math.max(0, n4 - n3);
            this.setRawTopAndBottomOffset(n4);
            return n2 - n4;
        }
        if (!bl2) {
            this.setRawTopAndBottomOffset(0);
            return n2;
        }
        return n2 - n4;
    }

    private boolean setOffsetForFocusListView(TicklableRecyclerView ticklableRecyclerView, int n2) {
        n2 = ticklableRecyclerView.updateScrollOffset(this.reduceRemovableOffset(n2));
        this.setRawTopAndBottomOffset(this.getRawTopAndBottomOffset() + n2);
        return true;
    }

    public int getRawTopAndBottomOffset() {
        return super.getTopAndBottomOffset();
    }

    @Override
    public int getTopAndBottomOffset() {
        if (this.hostView instanceof TicklableRecyclerView) {
            TicklableRecyclerView ticklableRecyclerView = (TicklableRecyclerView)this.hostView;
            return this.getRawTopAndBottomOffset() + ticklableRecyclerView.getScrollOffset();
        }
        return super.getTopAndBottomOffset();
    }

    @Override
    public boolean onLayoutChild(CoordinatorLayout coordinatorLayout, View view, int n2) {
        this.hostView = view;
        return super.onLayoutChild(coordinatorLayout, view, n2);
    }

    @Override
    public boolean requestInterceptPreScroll(CoordinatorLayout coordinatorLayout) {
        if (this.hostView instanceof TicklableRecyclerView && ((TicklableRecyclerView)this.hostView).interceptPreScroll()) {
            return true;
        }
        return super.requestInterceptPreScroll(coordinatorLayout);
    }

    public boolean setRawTopAndBottomOffset(int n2) {
        return super.setTopAndBottomOffset(n2);
    }

    @Override
    public boolean setTopAndBottomOffset(int n2) {
        boolean bl2;
        if (this.scrolling || this.hostView == null) {
            return false;
        }
        this.scrolling = true;
        boolean bl3 = bl2 = false;
        if (this.hostView instanceof TicklableRecyclerView) {
            TicklableRecyclerView ticklableRecyclerView = (TicklableRecyclerView)this.hostView;
            bl3 = bl2;
            if (ticklableRecyclerView.useScrollAsOffset()) {
                bl3 = this.setOffsetForFocusListView(ticklableRecyclerView, n2);
            }
        }
        bl2 = bl3;
        if (!bl3) {
            bl2 = this.setRawTopAndBottomOffset(n2);
        }
        this.scrolling = false;
        return bl2;
    }
}

