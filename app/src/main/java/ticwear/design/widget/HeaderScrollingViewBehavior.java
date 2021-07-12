/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.View$MeasureSpec
 */
package ticwear.design.widget;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import java.util.List;
import ticwear.design.widget.CoordinatorLayout;
import ticwear.design.widget.ViewOffsetBehavior;

abstract class HeaderScrollingViewBehavior
extends ViewOffsetBehavior<View> {
    public HeaderScrollingViewBehavior() {
    }

    public HeaderScrollingViewBehavior(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    abstract View findFirstDependency(List<View> var1);

    int getScrollRange(View view) {
        return view.getMeasuredHeight();
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public boolean onMeasureChild(CoordinatorLayout coordinatorLayout, View view, int n2, int n3, int n4, int n5) {
        int n6 = view.getLayoutParams().height;
        if (n6 == -1 || n6 == -2) {
            View view2 = coordinatorLayout.getDependencies(view);
            if (view2.isEmpty()) {
                return false;
            }
            if ((view2 = this.findFirstDependency((List<View>)view2)) != null && ViewCompat.isLaidOut(view2)) {
                int n7;
                if (ViewCompat.getFitsSystemWindows(view2)) {
                    ViewCompat.setFitsSystemWindows(view, true);
                }
                n4 = n7 = View.MeasureSpec.getSize((int)n4);
                if (n7 == 0) {
                    n4 = coordinatorLayout.getHeight();
                }
                int n8 = view2.getMeasuredHeight();
                int n9 = this.getScrollRange(view2);
                n7 = n6 == -1 ? 0x40000000 : Integer.MIN_VALUE;
                coordinatorLayout.onMeasureChild(view, n2, n3, View.MeasureSpec.makeMeasureSpec((int)(n4 - n8 + n9), (int)n7), n5);
                return true;
            }
        }
        return false;
    }
}

