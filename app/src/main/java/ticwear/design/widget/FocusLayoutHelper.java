/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.view.GestureDetector
 *  android.view.GestureDetector$OnGestureListener
 *  android.view.GestureDetector$SimpleOnGestureListener
 *  android.view.MotionEvent
 *  android.view.View
 */
package ticwear.design.widget;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import ticwear.design.widget.TicklableRecyclerView;
import ticwear.design.widget.ViewPropertiesHelper;

class FocusLayoutHelper {
    private final GestureDetector mGestureDetector;
    private final RecyclerView.LayoutManager mLayoutManager;
    private final TicklableRecyclerView mTicklableRecyclerView;

    FocusLayoutHelper(@NonNull TicklableRecyclerView ticklableRecyclerView, @NonNull RecyclerView.LayoutManager object) {
        this.mTicklableRecyclerView = ticklableRecyclerView;
        this.mLayoutManager = object;
        object = new OnGestureListener();
        this.mGestureDetector = new GestureDetector(ticklableRecyclerView.getContext(), (GestureDetector.OnGestureListener)object);
    }

    private int getCenterYPos() {
        return ViewPropertiesHelper.getCenterYPos((View)this.mTicklableRecyclerView);
    }

    private View getChildAt(int n2) {
        return this.mLayoutManager.getChildAt(n2);
    }

    private int getChildCount() {
        return this.mLayoutManager.getChildCount();
    }

    public void destroy() {
        for (int i2 = 0; i2 < this.getChildCount(); ++i2) {
            this.getChildAt(i2).clearFocus();
        }
    }

    public boolean dispatchTouchSidePanelEvent(MotionEvent motionEvent) {
        this.mTicklableRecyclerView.onTouchEvent(motionEvent);
        this.mGestureDetector.onTouchEvent(motionEvent);
        return true;
    }

    int findCenterViewIndex() {
        int n2 = this.getChildCount();
        int n3 = -1;
        int n4 = Integer.MAX_VALUE;
        int n5 = this.getCenterYPos();
        for (int i2 = 0; i2 < n2; ++i2) {
            View view = this.getChildAt(i2);
            int n6 = Math.abs(n5 - (this.mTicklableRecyclerView.getTop() + ViewPropertiesHelper.getCenterYPos(view)));
            int n7 = n4;
            if (n6 < n4) {
                n7 = n6;
                n3 = i2;
            }
            n4 = n7;
        }
        if (n3 == -1) {
            throw new IllegalStateException("Can't find central view.");
        }
        return n3;
    }

    int getCentralItemHeight() {
        if (this.getChildCount() > 0) {
            return this.getChildAt(this.findCenterViewIndex()).getHeight();
        }
        return 0;
    }

    int getVerticalPadding() {
        if (this.getChildCount() > 0) {
            return (ViewPropertiesHelper.getAdjustedHeight((View)this.mTicklableRecyclerView) - this.getCentralItemHeight()) / 2;
        }
        return 0;
    }

    public boolean interceptPreScroll() {
        boolean bl2 = false;
        View view = this.getChildAt(0);
        boolean bl3 = bl2;
        if (view != null) {
            bl3 = bl2;
            if (this.mTicklableRecyclerView.getChildAdapterPosition(view) <= 0) {
                bl3 = bl2;
                if (view.getTop() >= this.mTicklableRecyclerView.getPaddingTop()) {
                    bl3 = true;
                }
            }
        }
        return bl3;
    }

    void onScrollStateChanged(int n2) {
        View view;
        block3: {
            block2: {
                if (this.getChildCount() <= 0) break block2;
                view = this.getChildAt(this.findCenterViewIndex());
                if (n2 != 0) break block3;
                view.requestFocus();
            }
            return;
        }
        view.clearFocus();
    }

    private class OnGestureListener
    extends GestureDetector.SimpleOnGestureListener {
        private static final long SAFE_PRESS_DELAY = 60L;
        private final Runnable mConfirmPressRunnable = new Runnable(){

            @Override
            public void run() {
                OnGestureListener.this.confirmPress();
            }
        };
        private float mHotspotX;
        private float mHotspotY;
        private boolean mPressConfirmed;
        private View mTargetView;

        private OnGestureListener() {
        }

        private void cancelPressIfNeed() {
            this.stopPressConfirm(this.mTargetView);
            if (this.mTargetView != null) {
                this.mTargetView.setPressed(false);
                this.mTargetView = null;
                this.mPressConfirmed = false;
            }
        }

        private void confirmPress() {
            if (this.mTargetView != null) {
                this.mPressConfirmed = true;
                this.startPressIfPossible(this.mTargetView, this.mHotspotX, this.mHotspotY);
            }
        }

        private void startPressIfPossible(View view, float f2, float f3) {
            this.stopPressConfirm(view);
            if (view != null) {
                view.drawableHotspotChanged(f2, f3);
                view.setPressed(true);
            }
        }

        private void stopPressConfirm(View view) {
            if (view != null) {
                view.removeCallbacks(this.mConfirmPressRunnable);
            }
        }

        public boolean onDoubleTap(MotionEvent motionEvent) {
            this.cancelPressIfNeed();
            return false;
        }

        public boolean onDown(MotionEvent motionEvent) {
            this.mPressConfirmed = false;
            if (FocusLayoutHelper.this.getChildCount() > 0) {
                int n2 = FocusLayoutHelper.this.findCenterViewIndex();
                View view = FocusLayoutHelper.this.getChildAt(n2);
                this.mHotspotX = motionEvent.getX() - view.getX();
                this.mHotspotY = motionEvent.getY() - view.getY();
                this.mTargetView = view;
                this.mTargetView.postDelayed(this.mConfirmPressRunnable, 60L);
            }
            return false;
        }

        public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f2, float f3) {
            this.cancelPressIfNeed();
            return false;
        }

        public void onLongPress(MotionEvent motionEvent) {
            this.cancelPressIfNeed();
        }

        public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f2, float f3) {
            this.cancelPressIfNeed();
            if (FocusLayoutHelper.this.mLayoutManager.canScrollVertically()) {
                int n2 = Math.round(f2);
                int n3 = Math.round(f3);
                FocusLayoutHelper.this.mTicklableRecyclerView.scrollBySkipNestedScroll(n2, n3);
            }
            return false;
        }

        public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
            if (this.mTargetView != null && this.mPressConfirmed) {
                this.mTargetView.performClick();
            }
            this.cancelPressIfNeed();
            return false;
        }

        public boolean onSingleTapUp(MotionEvent motionEvent) {
            this.stopPressConfirm(this.mTargetView);
            return false;
        }
    }
}

