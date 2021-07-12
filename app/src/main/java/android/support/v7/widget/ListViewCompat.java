/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Canvas
 *  android.graphics.Rect
 *  android.graphics.drawable.Drawable
 *  android.util.AttributeSet
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.widget.AbsListView
 *  android.widget.ListAdapter
 *  android.widget.ListView
 */
package android.support.v7.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.graphics.drawable.DrawableWrapper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.ListView;
import java.lang.reflect.Field;

public class ListViewCompat
extends ListView {
    public static final int INVALID_POSITION = -1;
    public static final int NO_POSITION = -1;
    private static final int[] STATE_SET_NOTHING = new int[]{0};
    private Field mIsChildViewEnabled;
    protected int mMotionPosition;
    int mSelectionBottomPadding = 0;
    int mSelectionLeftPadding = 0;
    int mSelectionRightPadding = 0;
    int mSelectionTopPadding = 0;
    private GateKeeperDrawable mSelector;
    final Rect mSelectorRect = new Rect();

    public ListViewCompat(Context context) {
        this(context, null);
    }

    public ListViewCompat(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ListViewCompat(Context context, AttributeSet attributeSet, int n2) {
        super(context, attributeSet, n2);
        try {
            this.mIsChildViewEnabled = AbsListView.class.getDeclaredField("mIsChildViewEnabled");
            this.mIsChildViewEnabled.setAccessible(true);
            return;
        }
        catch (NoSuchFieldException noSuchFieldException) {
            noSuchFieldException.printStackTrace();
            return;
        }
    }

    protected void dispatchDraw(Canvas canvas) {
        this.drawSelectorCompat(canvas);
        super.dispatchDraw(canvas);
    }

    protected void drawSelectorCompat(Canvas canvas) {
        Drawable drawable2;
        if (!this.mSelectorRect.isEmpty() && (drawable2 = this.getSelector()) != null) {
            drawable2.setBounds(this.mSelectorRect);
            drawable2.draw(canvas);
        }
    }

    protected void drawableStateChanged() {
        super.drawableStateChanged();
        this.setSelectorEnabled(true);
        this.updateSelectorStateCompat();
    }

    /*
     * Enabled aggressive block sorting
     */
    public int lookForSelectablePosition(int n2, boolean bl2) {
        ListAdapter listAdapter = this.getAdapter();
        if (listAdapter == null || this.isInTouchMode()) return -1;
        int n3 = listAdapter.getCount();
        if (!this.getAdapter().areAllItemsEnabled()) {
            int n4;
            if (bl2) {
                n2 = Math.max(0, n2);
                while (true) {
                    n4 = n2;
                    if (n2 < n3) {
                        n4 = n2;
                        if (!listAdapter.isEnabled(n2)) {
                            ++n2;
                            continue;
                        }
                    }
                    break;
                }
            } else {
                n2 = Math.min(n2, n3 - 1);
                while (true) {
                    n4 = n2;
                    if (n2 < 0) break;
                    n4 = n2;
                    if (listAdapter.isEnabled(n2)) break;
                    --n2;
                }
            }
            if (n4 < 0 || n4 >= n3) return -1;
            return n4;
        }
        if (n2 >= 0 && n2 < n3) return n2;
        return -1;
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public int measureHeightOfChildrenCompat(int n2, int n3, int n4, int n5, int n6) {
        n3 = this.getListPaddingTop();
        n4 = this.getListPaddingBottom();
        this.getListPaddingLeft();
        this.getListPaddingRight();
        int n7 = this.getDividerHeight();
        Drawable drawable2 = this.getDivider();
        ListAdapter listAdapter = this.getAdapter();
        if (listAdapter == null) {
            n3 += n4;
            return n3;
        }
        n4 = n3 + n4;
        if (n7 <= 0 || drawable2 == null) {
            n7 = 0;
        }
        n3 = 0;
        drawable2 = null;
        int n8 = 0;
        int n9 = listAdapter.getCount();
        int n10 = 0;
        while (n10 < n9) {
            int n11 = listAdapter.getItemViewType(n10);
            int n12 = n8;
            if (n11 != n8) {
                drawable2 = null;
                n12 = n11;
            }
            View view = listAdapter.getView(n10, (View)drawable2, (ViewGroup)this);
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            drawable2 = layoutParams;
            if (layoutParams == null) {
                drawable2 = this.generateDefaultLayoutParams();
                view.setLayoutParams((ViewGroup.LayoutParams)drawable2);
            }
            n8 = drawable2.height > 0 ? View.MeasureSpec.makeMeasureSpec((int)drawable2.height, (int)0x40000000) : View.MeasureSpec.makeMeasureSpec((int)0, (int)0);
            view.measure(n2, n8);
            view.forceLayout();
            n8 = n4;
            if (n10 > 0) {
                n8 = n4 + n7;
            }
            if ((n4 = n8 + view.getMeasuredHeight()) >= n5) {
                if (n6 < 0) return n5;
                if (n10 <= n6) return n5;
                if (n3 <= 0) return n5;
                if (n4 != n5) return n3;
                return n5;
            }
            n8 = n3;
            if (n6 >= 0) {
                n8 = n3;
                if (n10 >= n6) {
                    n8 = n4;
                }
            }
            ++n10;
            drawable2 = view;
            n3 = n8;
            n8 = n12;
        }
        return n4;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean onTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            default: {
                return super.onTouchEvent(motionEvent);
            }
            case 0: 
        }
        this.mMotionPosition = this.pointToPosition((int)motionEvent.getX(), (int)motionEvent.getY());
        return super.onTouchEvent(motionEvent);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected void positionSelectorCompat(int n2, View object) {
        Rect rect = this.mSelectorRect;
        rect.set(object.getLeft(), object.getTop(), object.getRight(), object.getBottom());
        rect.left -= this.mSelectionLeftPadding;
        rect.top -= this.mSelectionTopPadding;
        rect.right += this.mSelectionRightPadding;
        rect.bottom += this.mSelectionBottomPadding;
        try {
            boolean bl2 = this.mIsChildViewEnabled.getBoolean((Object)this);
            if (object.isEnabled() != bl2) {
                object = this.mIsChildViewEnabled;
                bl2 = !bl2;
                ((Field)object).set((Object)this, bl2);
                if (n2 != -1) {
                    this.refreshDrawableState();
                }
            }
            return;
        }
        catch (IllegalAccessException illegalAccessException) {
            illegalAccessException.printStackTrace();
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void positionSelectorLikeFocusCompat(int n2, View view) {
        boolean bl2 = true;
        Drawable drawable2 = this.getSelector();
        boolean bl3 = drawable2 != null && n2 != -1;
        if (bl3) {
            drawable2.setVisible(false, false);
        }
        this.positionSelectorCompat(n2, view);
        if (bl3) {
            view = this.mSelectorRect;
            float f2 = view.exactCenterX();
            float f3 = view.exactCenterY();
            if (this.getVisibility() != 0) {
                bl2 = false;
            }
            drawable2.setVisible(bl2, false);
            DrawableCompat.setHotspot(drawable2, f2, f3);
        }
    }

    protected void positionSelectorLikeTouchCompat(int n2, View view, float f2, float f3) {
        this.positionSelectorLikeFocusCompat(n2, view);
        view = this.getSelector();
        if (view != null && n2 != -1) {
            DrawableCompat.setHotspot((Drawable)view, f2, f3);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setSelector(Drawable drawable2) {
        GateKeeperDrawable gateKeeperDrawable = drawable2 != null ? new GateKeeperDrawable(drawable2) : null;
        this.mSelector = gateKeeperDrawable;
        super.setSelector((Drawable)this.mSelector);
        gateKeeperDrawable = new Rect();
        if (drawable2 != null) {
            drawable2.getPadding((Rect)gateKeeperDrawable);
        }
        this.mSelectionLeftPadding = ((Rect)gateKeeperDrawable).left;
        this.mSelectionTopPadding = ((Rect)gateKeeperDrawable).top;
        this.mSelectionRightPadding = ((Rect)gateKeeperDrawable).right;
        this.mSelectionBottomPadding = ((Rect)gateKeeperDrawable).bottom;
    }

    protected void setSelectorEnabled(boolean bl2) {
        if (this.mSelector != null) {
            this.mSelector.setEnabled(bl2);
        }
    }

    protected boolean shouldShowSelectorCompat() {
        return this.touchModeDrawsInPressedStateCompat() && this.isPressed();
    }

    protected boolean touchModeDrawsInPressedStateCompat() {
        return false;
    }

    protected void updateSelectorStateCompat() {
        Drawable drawable2 = this.getSelector();
        if (drawable2 != null && this.shouldShowSelectorCompat()) {
            drawable2.setState(this.getDrawableState());
        }
    }

    private static class GateKeeperDrawable
    extends DrawableWrapper {
        private boolean mEnabled = true;

        public GateKeeperDrawable(Drawable drawable2) {
            super(drawable2);
        }

        @Override
        public void draw(Canvas canvas) {
            if (this.mEnabled) {
                super.draw(canvas);
            }
        }

        void setEnabled(boolean bl2) {
            this.mEnabled = bl2;
        }

        @Override
        public void setHotspot(float f2, float f3) {
            if (this.mEnabled) {
                super.setHotspot(f2, f3);
            }
        }

        @Override
        public void setHotspotBounds(int n2, int n3, int n4, int n5) {
            if (this.mEnabled) {
                super.setHotspotBounds(n2, n3, n4, n5);
            }
        }

        @Override
        public boolean setState(int[] nArray) {
            if (this.mEnabled) {
                return super.setState(nArray);
            }
            return false;
        }

        @Override
        public boolean setVisible(boolean bl2, boolean bl3) {
            if (this.mEnabled) {
                return super.setVisible(bl2, bl3);
            }
            return false;
        }
    }
}

