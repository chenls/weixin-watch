/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.util.AttributeSet
 *  android.view.MotionEvent
 *  android.view.VelocityTracker
 *  android.view.View
 *  android.view.View$BaseSavedState
 *  android.view.ViewConfiguration
 *  android.view.ViewGroup
 */
package android.support.design.widget;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.design.R;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.MathUtils;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.NestedScrollingChild;
import android.support.v4.view.VelocityTrackerCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;

public class BottomSheetBehavior<V extends View>
extends CoordinatorLayout.Behavior<V> {
    private static final float HIDE_FRICTION = 0.1f;
    private static final float HIDE_THRESHOLD = 0.5f;
    public static final int STATE_COLLAPSED = 4;
    public static final int STATE_DRAGGING = 1;
    public static final int STATE_EXPANDED = 3;
    public static final int STATE_HIDDEN = 5;
    public static final int STATE_SETTLING = 2;
    private int mActivePointerId;
    private BottomSheetCallback mCallback;
    private final ViewDragHelper.Callback mDragCallback = new ViewDragHelper.Callback(){

        @Override
        public int clampViewPositionHorizontal(View view, int n2, int n3) {
            return view.getLeft();
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        public int clampViewPositionVertical(View view, int n2, int n3) {
            int n4 = BottomSheetBehavior.this.mMinOffset;
            if (BottomSheetBehavior.this.mHideable) {
                n3 = BottomSheetBehavior.this.mParentHeight;
                return MathUtils.constrain(n2, n4, n3);
            }
            n3 = BottomSheetBehavior.this.mMaxOffset;
            return MathUtils.constrain(n2, n4, n3);
        }

        @Override
        public int getViewVerticalDragRange(View view) {
            if (BottomSheetBehavior.this.mHideable) {
                return BottomSheetBehavior.this.mParentHeight - BottomSheetBehavior.this.mMinOffset;
            }
            return BottomSheetBehavior.this.mMaxOffset - BottomSheetBehavior.this.mMinOffset;
        }

        @Override
        public void onViewDragStateChanged(int n2) {
            if (n2 == 1) {
                BottomSheetBehavior.this.setStateInternal(1);
            }
        }

        @Override
        public void onViewPositionChanged(View view, int n2, int n3, int n4, int n5) {
            BottomSheetBehavior.this.dispatchOnSlide(n3);
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public void onViewReleased(View view, float f2, float f3) {
            int n2;
            int n3;
            if (f3 < 0.0f) {
                n3 = BottomSheetBehavior.this.mMinOffset;
                n2 = 3;
            } else if (BottomSheetBehavior.this.mHideable && BottomSheetBehavior.this.shouldHide(view, f3)) {
                n3 = BottomSheetBehavior.this.mParentHeight;
                n2 = 5;
            } else if (f3 == 0.0f) {
                n2 = view.getTop();
                if (Math.abs(n2 - BottomSheetBehavior.this.mMinOffset) < Math.abs(n2 - BottomSheetBehavior.this.mMaxOffset)) {
                    n3 = BottomSheetBehavior.this.mMinOffset;
                    n2 = 3;
                } else {
                    n3 = BottomSheetBehavior.this.mMaxOffset;
                    n2 = 4;
                }
            } else {
                n3 = BottomSheetBehavior.this.mMaxOffset;
                n2 = 4;
            }
            if (BottomSheetBehavior.this.mViewDragHelper.settleCapturedViewAt(view.getLeft(), n3)) {
                BottomSheetBehavior.this.setStateInternal(2);
                ViewCompat.postOnAnimation(view, new SettleRunnable(view, n2));
                return;
            }
            BottomSheetBehavior.this.setStateInternal(n2);
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        public boolean tryCaptureView(View view, int n2) {
            View view2;
            boolean bl2 = true;
            if (BottomSheetBehavior.this.mState == 1) {
                return false;
            }
            if (BottomSheetBehavior.this.mTouchingScrollingChild) return false;
            if (BottomSheetBehavior.this.mState == 3 && BottomSheetBehavior.this.mActivePointerId == n2 && (view2 = (View)BottomSheetBehavior.this.mNestedScrollingChildRef.get()) != null) {
                if (ViewCompat.canScrollVertically(view2, -1)) return false;
            }
            if (BottomSheetBehavior.this.mViewRef == null) return false;
            if (BottomSheetBehavior.this.mViewRef.get() != view) return false;
            return bl2;
        }
    };
    private boolean mHideable;
    private boolean mIgnoreEvents;
    private int mInitialY;
    private int mLastNestedScrollDy;
    private int mMaxOffset;
    private float mMaximumVelocity;
    private int mMinOffset;
    private boolean mNestedScrolled;
    private WeakReference<View> mNestedScrollingChildRef;
    private int mParentHeight;
    private int mPeekHeight;
    private int mState = 4;
    private boolean mTouchingScrollingChild;
    private VelocityTracker mVelocityTracker;
    private ViewDragHelper mViewDragHelper;
    private WeakReference<V> mViewRef;

    public BottomSheetBehavior() {
    }

    public BottomSheetBehavior(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        attributeSet = context.obtainStyledAttributes(attributeSet, R.styleable.BottomSheetBehavior_Params);
        this.setPeekHeight(attributeSet.getDimensionPixelSize(R.styleable.BottomSheetBehavior_Params_behavior_peekHeight, 0));
        this.setHideable(attributeSet.getBoolean(R.styleable.BottomSheetBehavior_Params_behavior_hideable, false));
        attributeSet.recycle();
        this.mMaximumVelocity = ViewConfiguration.get((Context)context).getScaledMaximumFlingVelocity();
    }

    private void dispatchOnSlide(int n2) {
        View view;
        block3: {
            block2: {
                view = (View)this.mViewRef.get();
                if (view == null || this.mCallback == null) break block2;
                if (n2 <= this.mMaxOffset) break block3;
                this.mCallback.onSlide(view, (float)(this.mMaxOffset - n2) / (float)this.mPeekHeight);
            }
            return;
        }
        this.mCallback.onSlide(view, (float)(this.mMaxOffset - n2) / (float)(this.mMaxOffset - this.mMinOffset));
    }

    private View findScrollingChild(View view) {
        if (view instanceof NestedScrollingChild) {
            return view;
        }
        if (view instanceof ViewGroup) {
            view = (ViewGroup)view;
            int n2 = view.getChildCount();
            for (int i2 = 0; i2 < n2; ++i2) {
                View view2 = this.findScrollingChild(view.getChildAt(i2));
                if (view2 == null) continue;
                return view2;
            }
        }
        return null;
    }

    public static <V extends View> BottomSheetBehavior<V> from(V object) {
        if (!((object = object.getLayoutParams()) instanceof CoordinatorLayout.LayoutParams)) {
            throw new IllegalArgumentException("The view is not a child of CoordinatorLayout");
        }
        if (!((object = ((CoordinatorLayout.LayoutParams)((Object)object)).getBehavior()) instanceof BottomSheetBehavior)) {
            throw new IllegalArgumentException("The view is not associated with BottomSheetBehavior");
        }
        return (BottomSheetBehavior)object;
    }

    private float getYVelocity() {
        this.mVelocityTracker.computeCurrentVelocity(1000, this.mMaximumVelocity);
        return VelocityTrackerCompat.getYVelocity(this.mVelocityTracker, this.mActivePointerId);
    }

    private void reset() {
        this.mActivePointerId = -1;
        if (this.mVelocityTracker != null) {
            this.mVelocityTracker.recycle();
            this.mVelocityTracker = null;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void setStateInternal(int n2) {
        View view;
        block3: {
            block2: {
                if (this.mState == n2) break block2;
                this.mState = n2;
                view = (View)this.mViewRef.get();
                if (view != null && this.mCallback != null) break block3;
            }
            return;
        }
        this.mCallback.onStateChanged(view, n2);
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean shouldHide(View view, float f2) {
        return view.getTop() >= this.mMaxOffset && Math.abs((float)view.getTop() + 0.1f * f2 - (float)this.mMaxOffset) / (float)this.mPeekHeight > 0.5f;
    }

    public final int getPeekHeight() {
        return this.mPeekHeight;
    }

    public final int getState() {
        return this.mState;
    }

    public boolean isHideable() {
        return this.mHideable;
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    @Override
    public boolean onInterceptTouchEvent(CoordinatorLayout coordinatorLayout, V object, MotionEvent motionEvent) {
        void var3_4;
        boolean bl2 = true;
        if (!object.isShown()) {
            return false;
        }
        int n2 = MotionEventCompat.getActionMasked((MotionEvent)var3_4);
        if (n2 == 0) {
            this.reset();
        }
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
        this.mVelocityTracker.addMovement((MotionEvent)var3_4);
        switch (n2) {
            case 1: 
            case 3: {
                this.mTouchingScrollingChild = false;
                this.mActivePointerId = -1;
                if (!this.mIgnoreEvents) break;
                this.mIgnoreEvents = false;
                return false;
            }
            case 0: {
                int n3 = (int)var3_4.getX();
                this.mInitialY = (int)var3_4.getY();
                View view = (View)this.mNestedScrollingChildRef.get();
                if (view != null && coordinatorLayout.isPointInChildBounds(view, n3, this.mInitialY)) {
                    this.mActivePointerId = var3_4.getPointerId(var3_4.getActionIndex());
                    this.mTouchingScrollingChild = true;
                }
                boolean bl3 = this.mActivePointerId == -1 && !coordinatorLayout.isPointInChildBounds((View)object, n3, this.mInitialY);
                this.mIgnoreEvents = bl3;
            }
        }
        if (!this.mIgnoreEvents && this.mViewDragHelper.shouldInterceptTouchEvent((MotionEvent)var3_4)) {
            return true;
        }
        View view = (View)this.mNestedScrollingChildRef.get();
        if (n2 != 2) return false;
        if (view == null) return false;
        if (this.mIgnoreEvents) return false;
        if (this.mState == 1) return false;
        if (coordinatorLayout.isPointInChildBounds(view, (int)var3_4.getX(), (int)var3_4.getY())) return false;
        if (!(Math.abs((float)this.mInitialY - var3_4.getY()) > (float)this.mViewDragHelper.getTouchSlop())) return false;
        return bl2;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public boolean onLayoutChild(CoordinatorLayout coordinatorLayout, V v2, int n2) {
        if (this.mState != 1 && this.mState != 2) {
            if (ViewCompat.getFitsSystemWindows((View)coordinatorLayout) && !ViewCompat.getFitsSystemWindows(v2)) {
                ViewCompat.setFitsSystemWindows(v2, true);
            }
            coordinatorLayout.onLayoutChild((View)v2, n2);
        }
        this.mParentHeight = coordinatorLayout.getHeight();
        this.mMinOffset = Math.max(0, this.mParentHeight - v2.getHeight());
        this.mMaxOffset = Math.max(this.mParentHeight - this.mPeekHeight, this.mMinOffset);
        if (this.mState == 3) {
            ViewCompat.offsetTopAndBottom(v2, this.mMinOffset);
        } else if (this.mHideable && this.mState == 5) {
            ViewCompat.offsetTopAndBottom(v2, this.mParentHeight);
        } else if (this.mState == 4) {
            ViewCompat.offsetTopAndBottom(v2, this.mMaxOffset);
        }
        if (this.mViewDragHelper == null) {
            this.mViewDragHelper = ViewDragHelper.create(coordinatorLayout, this.mDragCallback);
        }
        this.mViewRef = new WeakReference<V>(v2);
        this.mNestedScrollingChildRef = new WeakReference<View>(this.findScrollingChild((View)v2));
        return true;
    }

    @Override
    public boolean onNestedPreFling(CoordinatorLayout coordinatorLayout, V v2, View view, float f2, float f3) {
        return view == this.mNestedScrollingChildRef.get() && (this.mState != 3 || super.onNestedPreFling(coordinatorLayout, v2, view, f2, f3));
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, V v2, View view, int n2, int n3, int[] nArray) {
        if (view != (View)this.mNestedScrollingChildRef.get()) {
            return;
        }
        n2 = v2.getTop();
        int n4 = n2 - n3;
        if (n3 > 0) {
            if (n4 < this.mMinOffset) {
                nArray[1] = n2 - this.mMinOffset;
                ViewCompat.offsetTopAndBottom(v2, -nArray[1]);
                this.setStateInternal(3);
            } else {
                nArray[1] = n3;
                ViewCompat.offsetTopAndBottom(v2, -n3);
                this.setStateInternal(1);
            }
        } else if (n3 < 0 && !ViewCompat.canScrollVertically(view, -1)) {
            if (n4 <= this.mMaxOffset || this.mHideable) {
                nArray[1] = n3;
                ViewCompat.offsetTopAndBottom(v2, -n3);
                this.setStateInternal(1);
            } else {
                nArray[1] = n2 - this.mMaxOffset;
                ViewCompat.offsetTopAndBottom(v2, -nArray[1]);
                this.setStateInternal(4);
            }
        }
        this.dispatchOnSlide(v2.getTop());
        this.mLastNestedScrollDy = n3;
        this.mNestedScrolled = true;
    }

    @Override
    public void onRestoreInstanceState(CoordinatorLayout coordinatorLayout, V v2, Parcelable object) {
        object = (SavedState)((Object)object);
        super.onRestoreInstanceState(coordinatorLayout, v2, object.getSuperState());
        if (object.state == 1 || object.state == 2) {
            this.mState = 4;
            return;
        }
        this.mState = object.state;
    }

    @Override
    public Parcelable onSaveInstanceState(CoordinatorLayout coordinatorLayout, V v2) {
        return new SavedState(super.onSaveInstanceState(coordinatorLayout, v2), this.mState);
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, V v2, View view, View view2, int n2) {
        boolean bl2 = false;
        this.mLastNestedScrollDy = 0;
        this.mNestedScrolled = false;
        if ((n2 & 2) != 0) {
            bl2 = true;
        }
        return bl2;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, V v2, View view) {
        int n2;
        int n3;
        if (v2.getTop() == this.mMinOffset) {
            this.setStateInternal(3);
            return;
        }
        if (view != this.mNestedScrollingChildRef.get() || !this.mNestedScrolled) return;
        if (this.mLastNestedScrollDy > 0) {
            n3 = this.mMinOffset;
            n2 = 3;
        } else if (this.mHideable && this.shouldHide((View)v2, this.getYVelocity())) {
            n3 = this.mParentHeight;
            n2 = 5;
        } else if (this.mLastNestedScrollDy == 0) {
            n2 = v2.getTop();
            if (Math.abs(n2 - this.mMinOffset) < Math.abs(n2 - this.mMaxOffset)) {
                n3 = this.mMinOffset;
                n2 = 3;
            } else {
                n3 = this.mMaxOffset;
                n2 = 4;
            }
        } else {
            n3 = this.mMaxOffset;
            n2 = 4;
        }
        if (this.mViewDragHelper.smoothSlideViewTo((View)v2, v2.getLeft(), n3)) {
            this.setStateInternal(2);
            ViewCompat.postOnAnimation(v2, new SettleRunnable((View)v2, n2));
        } else {
            this.setStateInternal(n2);
        }
        this.mNestedScrolled = false;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public boolean onTouchEvent(CoordinatorLayout coordinatorLayout, V v2, MotionEvent motionEvent) {
        boolean bl2;
        boolean bl3 = true;
        if (!v2.isShown()) {
            return false;
        }
        int n2 = MotionEventCompat.getActionMasked(motionEvent);
        if (this.mState == 1) {
            bl2 = bl3;
            if (n2 == 0) return bl2;
        }
        this.mViewDragHelper.processTouchEvent(motionEvent);
        if (n2 == 0) {
            this.reset();
        }
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
        this.mVelocityTracker.addMovement(motionEvent);
        bl2 = bl3;
        if (n2 != 2) return bl2;
        bl2 = bl3;
        if (this.mIgnoreEvents) return bl2;
        bl2 = bl3;
        if (!(Math.abs((float)this.mInitialY - motionEvent.getY()) > (float)this.mViewDragHelper.getTouchSlop())) return bl2;
        this.mViewDragHelper.captureChildView((View)v2, motionEvent.getPointerId(motionEvent.getActionIndex()));
        return true;
    }

    public void setBottomSheetCallback(BottomSheetCallback bottomSheetCallback) {
        this.mCallback = bottomSheetCallback;
    }

    public void setHideable(boolean bl2) {
        this.mHideable = bl2;
    }

    public final void setPeekHeight(int n2) {
        this.mPeekHeight = Math.max(0, n2);
        this.mMaxOffset = this.mParentHeight - n2;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final void setState(int n2) {
        int n3;
        if (n2 == this.mState) return;
        if (this.mViewRef == null) {
            if (n2 != 4 && n2 != 3 && (!this.mHideable || n2 != 5)) return;
            this.mState = n2;
            return;
        }
        View view = (View)this.mViewRef.get();
        if (view == null) return;
        if (n2 == 4) {
            n3 = this.mMaxOffset;
        } else if (n2 == 3) {
            n3 = this.mMinOffset;
        } else {
            if (!this.mHideable || n2 != 5) throw new IllegalArgumentException("Illegal state argument: " + n2);
            n3 = this.mParentHeight;
        }
        this.setStateInternal(2);
        if (!this.mViewDragHelper.smoothSlideViewTo(view, view.getLeft(), n3)) {
            return;
        }
        ViewCompat.postOnAnimation(view, new SettleRunnable(view, n2));
    }

    public static abstract class BottomSheetCallback {
        public abstract void onSlide(@NonNull View var1, float var2);

        public abstract void onStateChanged(@NonNull View var1, int var2);
    }

    protected static class SavedState
    extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>(){

            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            public SavedState[] newArray(int n2) {
                return new SavedState[n2];
            }
        };
        final int state;

        public SavedState(Parcel parcel) {
            super(parcel);
            this.state = parcel.readInt();
        }

        public SavedState(Parcelable parcelable, int n2) {
            super(parcelable);
            this.state = n2;
        }

        public void writeToParcel(Parcel parcel, int n2) {
            super.writeToParcel(parcel, n2);
            parcel.writeInt(this.state);
        }
    }

    private class SettleRunnable
    implements Runnable {
        private final int mTargetState;
        private final View mView;

        SettleRunnable(View view, int n2) {
            this.mView = view;
            this.mTargetState = n2;
        }

        @Override
        public void run() {
            if (BottomSheetBehavior.this.mViewDragHelper != null && BottomSheetBehavior.this.mViewDragHelper.continueSettling(true)) {
                ViewCompat.postOnAnimation(this.mView, this);
                return;
            }
            BottomSheetBehavior.this.setStateInternal(this.mTargetState);
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface State {
    }
}

