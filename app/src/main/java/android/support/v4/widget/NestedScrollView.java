/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Canvas
 *  android.graphics.Rect
 *  android.os.Bundle
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.util.AttributeSet
 *  android.util.Log
 *  android.util.TypedValue
 *  android.view.FocusFinder
 *  android.view.KeyEvent
 *  android.view.MotionEvent
 *  android.view.VelocityTracker
 *  android.view.View
 *  android.view.View$BaseSavedState
 *  android.view.View$MeasureSpec
 *  android.view.ViewConfiguration
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewGroup$MarginLayoutParams
 *  android.view.ViewParent
 *  android.view.accessibility.AccessibilityEvent
 *  android.view.animation.AnimationUtils
 *  android.widget.FrameLayout
 *  android.widget.FrameLayout$LayoutParams
 *  android.widget.ScrollView
 */
package android.support.v4.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.view.AccessibilityDelegateCompat;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.NestedScrollingChild;
import android.support.v4.view.NestedScrollingChildHelper;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.NestedScrollingParentHelper;
import android.support.v4.view.ScrollingView;
import android.support.v4.view.VelocityTrackerCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.accessibility.AccessibilityEventCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v4.view.accessibility.AccessibilityRecordCompat;
import android.support.v4.widget.EdgeEffectCompat;
import android.support.v4.widget.ScrollerCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.FocusFinder;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import java.util.ArrayList;

public class NestedScrollView
extends FrameLayout
implements NestedScrollingParent,
NestedScrollingChild,
ScrollingView {
    private static final AccessibilityDelegate ACCESSIBILITY_DELEGATE = new AccessibilityDelegate();
    static final int ANIMATED_SCROLL_GAP = 250;
    private static final int INVALID_POINTER = -1;
    static final float MAX_SCROLL_FACTOR = 0.5f;
    private static final int[] SCROLLVIEW_STYLEABLE = new int[]{16843130};
    private static final String TAG = "NestedScrollView";
    private int mActivePointerId = -1;
    private final NestedScrollingChildHelper mChildHelper;
    private View mChildToScrollTo = null;
    private EdgeEffectCompat mEdgeGlowBottom;
    private EdgeEffectCompat mEdgeGlowTop;
    private boolean mFillViewport;
    private boolean mIsBeingDragged = false;
    private boolean mIsLaidOut = false;
    private boolean mIsLayoutDirty = true;
    private int mLastMotionY;
    private long mLastScroll;
    private int mMaximumVelocity;
    private int mMinimumVelocity;
    private int mNestedYOffset;
    private OnScrollChangeListener mOnScrollChangeListener;
    private final NestedScrollingParentHelper mParentHelper;
    private SavedState mSavedState;
    private final int[] mScrollConsumed;
    private final int[] mScrollOffset;
    private ScrollerCompat mScroller;
    private boolean mSmoothScrollingEnabled = true;
    private final Rect mTempRect = new Rect();
    private int mTouchSlop;
    private VelocityTracker mVelocityTracker;
    private float mVerticalScrollFactor;

    public NestedScrollView(Context context) {
        this(context, null);
    }

    public NestedScrollView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public NestedScrollView(Context context, AttributeSet attributeSet, int n2) {
        super(context, attributeSet, n2);
        this.mScrollOffset = new int[2];
        this.mScrollConsumed = new int[2];
        this.initScrollView();
        context = context.obtainStyledAttributes(attributeSet, SCROLLVIEW_STYLEABLE, n2, 0);
        this.setFillViewport(context.getBoolean(0, false));
        context.recycle();
        this.mParentHelper = new NestedScrollingParentHelper((ViewGroup)this);
        this.mChildHelper = new NestedScrollingChildHelper((View)this);
        this.setNestedScrollingEnabled(true);
        ViewCompat.setAccessibilityDelegate((View)this, ACCESSIBILITY_DELEGATE);
    }

    private boolean canScroll() {
        boolean bl2 = false;
        View view = this.getChildAt(0);
        boolean bl3 = bl2;
        if (view != null) {
            int n2 = view.getHeight();
            bl3 = bl2;
            if (this.getHeight() < this.getPaddingTop() + n2 + this.getPaddingBottom()) {
                bl3 = true;
            }
        }
        return bl3;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static int clamp(int n2, int n3, int n4) {
        if (n3 >= n4) return 0;
        if (n2 < 0) {
            return 0;
        }
        int n5 = n2;
        if (n3 + n2 <= n4) return n5;
        return n4 - n3;
    }

    private void doScrollY(int n2) {
        block3: {
            block2: {
                if (n2 == 0) break block2;
                if (!this.mSmoothScrollingEnabled) break block3;
                this.smoothScrollBy(0, n2);
            }
            return;
        }
        this.scrollBy(0, n2);
    }

    private void endDrag() {
        this.mIsBeingDragged = false;
        this.recycleVelocityTracker();
        this.stopNestedScroll();
        if (this.mEdgeGlowTop != null) {
            this.mEdgeGlowTop.onRelease();
            this.mEdgeGlowBottom.onRelease();
        }
    }

    private void ensureGlows() {
        if (this.getOverScrollMode() != 2) {
            if (this.mEdgeGlowTop == null) {
                Context context = this.getContext();
                this.mEdgeGlowTop = new EdgeEffectCompat(context);
                this.mEdgeGlowBottom = new EdgeEffectCompat(context);
            }
            return;
        }
        this.mEdgeGlowTop = null;
        this.mEdgeGlowBottom = null;
    }

    /*
     * Enabled aggressive block sorting
     */
    private View findFocusableViewInBounds(boolean bl2, int n2, int n3) {
        ArrayList arrayList = this.getFocusables(2);
        View view = null;
        boolean bl3 = false;
        int n4 = arrayList.size();
        int n5 = 0;
        while (true) {
            boolean bl4;
            View view2;
            block7: {
                boolean bl5;
                int n6;
                View view3;
                block9: {
                    int n7;
                    block8: {
                        if (n5 >= n4) {
                            return view;
                        }
                        view3 = (View)arrayList.get(n5);
                        n6 = view3.getTop();
                        n7 = view3.getBottom();
                        view2 = view;
                        bl4 = bl3;
                        if (n2 >= n7) break block7;
                        view2 = view;
                        bl4 = bl3;
                        if (n6 >= n3) break block7;
                        bl5 = n2 < n6 && n7 < n3;
                        if (view != null) break block8;
                        view2 = view3;
                        bl4 = bl5;
                        break block7;
                    }
                    n6 = bl2 && n6 < view.getTop() || !bl2 && n7 > view.getBottom() ? 1 : 0;
                    if (!bl3) break block9;
                    view2 = view;
                    bl4 = bl3;
                    if (bl5) {
                        view2 = view;
                        bl4 = bl3;
                        if (n6 != 0) {
                            view2 = view3;
                            bl4 = bl3;
                        }
                    }
                    break block7;
                }
                if (bl5) {
                    view2 = view3;
                    bl4 = true;
                } else {
                    view2 = view;
                    bl4 = bl3;
                    if (n6 != 0) {
                        view2 = view3;
                        bl4 = bl3;
                    }
                }
            }
            ++n5;
            view = view2;
            bl3 = bl4;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void flingWithNestedDispatch(int n2) {
        int n3 = this.getScrollY();
        boolean bl2 = !(n3 <= 0 && n2 <= 0 || n3 >= this.getScrollRange() && n2 >= 0);
        if (!this.dispatchNestedPreFling(0.0f, n2)) {
            this.dispatchNestedFling(0.0f, n2, bl2);
            if (bl2) {
                this.fling(n2);
            }
        }
    }

    private float getVerticalScrollFactorCompat() {
        if (this.mVerticalScrollFactor == 0.0f) {
            TypedValue typedValue = new TypedValue();
            Context context = this.getContext();
            if (!context.getTheme().resolveAttribute(16842829, typedValue, true)) {
                throw new IllegalStateException("Expected theme to define listPreferredItemHeight.");
            }
            this.mVerticalScrollFactor = typedValue.getDimension(context.getResources().getDisplayMetrics());
        }
        return this.mVerticalScrollFactor;
    }

    private boolean inChild(int n2, int n3) {
        boolean bl2;
        boolean bl3 = bl2 = false;
        if (this.getChildCount() > 0) {
            int n4 = this.getScrollY();
            View view = this.getChildAt(0);
            bl3 = bl2;
            if (n3 >= view.getTop() - n4) {
                bl3 = bl2;
                if (n3 < view.getBottom() - n4) {
                    bl3 = bl2;
                    if (n2 >= view.getLeft()) {
                        bl3 = bl2;
                        if (n2 < view.getRight()) {
                            bl3 = true;
                        }
                    }
                }
            }
        }
        return bl3;
    }

    private void initOrResetVelocityTracker() {
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
            return;
        }
        this.mVelocityTracker.clear();
    }

    private void initScrollView() {
        this.mScroller = ScrollerCompat.create(this.getContext(), null);
        this.setFocusable(true);
        this.setDescendantFocusability(262144);
        this.setWillNotDraw(false);
        ViewConfiguration viewConfiguration = ViewConfiguration.get((Context)this.getContext());
        this.mTouchSlop = viewConfiguration.getScaledTouchSlop();
        this.mMinimumVelocity = viewConfiguration.getScaledMinimumFlingVelocity();
        this.mMaximumVelocity = viewConfiguration.getScaledMaximumFlingVelocity();
    }

    private void initVelocityTrackerIfNotExists() {
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
    }

    private boolean isOffScreen(View view) {
        boolean bl2 = false;
        if (!this.isWithinDeltaOfScreen(view, 0, this.getHeight())) {
            bl2 = true;
        }
        return bl2;
    }

    /*
     * Enabled aggressive block sorting
     */
    private static boolean isViewDescendantOf(View view, View view2) {
        return view == view2 || (view = view.getParent()) instanceof ViewGroup && NestedScrollView.isViewDescendantOf(view, view2);
    }

    private boolean isWithinDeltaOfScreen(View view, int n2, int n3) {
        view.getDrawingRect(this.mTempRect);
        this.offsetDescendantRectToMyCoords(view, this.mTempRect);
        return this.mTempRect.bottom + n2 >= this.getScrollY() && this.mTempRect.top - n2 <= this.getScrollY() + n3;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void onSecondaryPointerUp(MotionEvent motionEvent) {
        int n2 = (motionEvent.getAction() & 0xFF00) >> 8;
        if (motionEvent.getPointerId(n2) == this.mActivePointerId) {
            n2 = n2 == 0 ? 1 : 0;
            this.mLastMotionY = (int)motionEvent.getY(n2);
            this.mActivePointerId = motionEvent.getPointerId(n2);
            if (this.mVelocityTracker != null) {
                this.mVelocityTracker.clear();
            }
        }
    }

    private void recycleVelocityTracker() {
        if (this.mVelocityTracker != null) {
            this.mVelocityTracker.recycle();
            this.mVelocityTracker = null;
        }
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    private boolean scrollAndFocus(int n2, int n3, int n4) {
        void var8_11;
        View view;
        boolean bl2 = true;
        int n5 = this.getHeight();
        int n6 = this.getScrollY();
        n5 = n6 + n5;
        boolean bl3 = n2 == 33;
        View view2 = view = this.findFocusableViewInBounds(bl3, n3, n4);
        if (view == null) {
            NestedScrollView nestedScrollView = this;
        }
        if (n3 >= n6 && n4 <= n5) {
            bl3 = false;
        } else {
            n3 = bl3 ? (n3 -= n6) : n4 - n5;
            this.doScrollY(n3);
            bl3 = bl2;
        }
        if (var8_11 != this.findFocus()) {
            var8_11.requestFocus(n2);
        }
        return bl3;
    }

    private void scrollToChild(View view) {
        view.getDrawingRect(this.mTempRect);
        this.offsetDescendantRectToMyCoords(view, this.mTempRect);
        int n2 = this.computeScrollDeltaToGetChildRectOnScreen(this.mTempRect);
        if (n2 != 0) {
            this.scrollBy(0, n2);
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean scrollToChildRect(Rect rect, boolean bl2) {
        int n2 = this.computeScrollDeltaToGetChildRectOnScreen(rect);
        if (n2 == 0) return false;
        boolean bl3 = true;
        if (!bl3) return bl3;
        if (bl2) {
            this.scrollBy(0, n2);
            return bl3;
        }
        this.smoothScrollBy(0, n2);
        return bl3;
    }

    public void addView(View view) {
        if (this.getChildCount() > 0) {
            throw new IllegalStateException("ScrollView can host only one direct child");
        }
        super.addView(view);
    }

    public void addView(View view, int n2) {
        if (this.getChildCount() > 0) {
            throw new IllegalStateException("ScrollView can host only one direct child");
        }
        super.addView(view, n2);
    }

    public void addView(View view, int n2, ViewGroup.LayoutParams layoutParams) {
        if (this.getChildCount() > 0) {
            throw new IllegalStateException("ScrollView can host only one direct child");
        }
        super.addView(view, n2, layoutParams);
    }

    public void addView(View view, ViewGroup.LayoutParams layoutParams) {
        if (this.getChildCount() > 0) {
            throw new IllegalStateException("ScrollView can host only one direct child");
        }
        super.addView(view, layoutParams);
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean arrowScroll(int n2) {
        View view;
        boolean bl2 = false;
        View view2 = view = this.findFocus();
        if (view == this) {
            view2 = null;
        }
        view = FocusFinder.getInstance().findNextFocus((ViewGroup)this, view2, n2);
        int n3 = this.getMaxScrollAmount();
        if (view != null && this.isWithinDeltaOfScreen(view, n3, this.getHeight())) {
            view.getDrawingRect(this.mTempRect);
            this.offsetDescendantRectToMyCoords(view, this.mTempRect);
            this.doScrollY(this.computeScrollDeltaToGetChildRectOnScreen(this.mTempRect));
            view.requestFocus(n2);
        } else {
            int n4;
            int n5 = n3;
            if (n2 == 33 && this.getScrollY() < n5) {
                n4 = this.getScrollY();
            } else {
                n4 = n5;
                if (n2 == 130) {
                    n4 = n5;
                    if (this.getChildCount() > 0) {
                        int n6 = this.getChildAt(0).getBottom();
                        int n7 = this.getScrollY() + this.getHeight() - this.getPaddingBottom();
                        n4 = n5;
                        if (n6 - n7 < n3) {
                            n4 = n6 - n7;
                        }
                    }
                }
            }
            if (n4 == 0) return bl2;
            if (n2 != 130) {
                n4 = -n4;
            }
            this.doScrollY(n4);
        }
        if (view2 == null) return true;
        if (!view2.isFocused()) return true;
        if (!this.isOffScreen(view2)) return true;
        n2 = this.getDescendantFocusability();
        this.setDescendantFocusability(131072);
        this.requestFocus();
        this.setDescendantFocusability(n2);
        return true;
    }

    @Override
    public int computeHorizontalScrollExtent() {
        return super.computeHorizontalScrollExtent();
    }

    @Override
    public int computeHorizontalScrollOffset() {
        return super.computeHorizontalScrollOffset();
    }

    @Override
    public int computeHorizontalScrollRange() {
        return super.computeHorizontalScrollRange();
    }

    /*
     * Enabled aggressive block sorting
     */
    public void computeScroll() {
        boolean bl2 = true;
        if (!this.mScroller.computeScrollOffset()) return;
        int n2 = this.getScrollX();
        int n3 = this.getScrollY();
        int n4 = this.mScroller.getCurrX();
        int n5 = this.mScroller.getCurrY();
        if (n2 == n4 && n3 == n5) return;
        int n6 = this.getScrollRange();
        int n7 = this.getOverScrollMode();
        boolean bl3 = bl2;
        if (n7 != 0) {
            bl3 = n7 == 1 && n6 > 0 ? bl2 : false;
        }
        this.overScrollByCompat(n4 - n2, n5 - n3, n2, n3, 0, n6, 0, 0, false);
        if (!bl3) return;
        this.ensureGlows();
        if (n5 <= 0 && n3 > 0) {
            this.mEdgeGlowTop.onAbsorb((int)this.mScroller.getCurrVelocity());
            return;
        } else {
            if (n5 < n6 || n3 >= n6) return;
            this.mEdgeGlowBottom.onAbsorb((int)this.mScroller.getCurrVelocity());
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    protected int computeScrollDeltaToGetChildRectOnScreen(Rect rect) {
        if (this.getChildCount() == 0) {
            return 0;
        }
        int n2 = this.getHeight();
        int n3 = this.getScrollY();
        int n4 = n3 + n2;
        int n5 = this.getVerticalFadingEdgeLength();
        int n6 = n3;
        if (rect.top > 0) {
            n6 = n3 + n5;
        }
        n3 = n4;
        if (rect.bottom < this.getChildAt(0).getHeight()) {
            n3 = n4 - n5;
        }
        if (rect.bottom > n3 && rect.top > n6) {
            if (rect.height() > n2) {
                n6 = 0 + (rect.top - n6);
                return Math.min(n6, this.getChildAt(0).getBottom() - n3);
            }
            n6 = 0 + (rect.bottom - n3);
            return Math.min(n6, this.getChildAt(0).getBottom() - n3);
        }
        if (rect.top >= n6) return 0;
        if (rect.bottom >= n3) return 0;
        if (rect.height() > n2) {
            n3 = 0 - (n3 - rect.bottom);
            return Math.max(n3, -this.getScrollY());
        }
        n3 = 0 - (n6 - rect.top);
        return Math.max(n3, -this.getScrollY());
    }

    @Override
    public int computeVerticalScrollExtent() {
        return super.computeVerticalScrollExtent();
    }

    @Override
    public int computeVerticalScrollOffset() {
        return Math.max(0, super.computeVerticalScrollOffset());
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public int computeVerticalScrollRange() {
        int n2 = this.getChildCount();
        int n3 = this.getHeight() - this.getPaddingBottom() - this.getPaddingTop();
        if (n2 == 0) {
            return n3;
        }
        n2 = this.getChildAt(0).getBottom();
        int n4 = this.getScrollY();
        int n5 = Math.max(0, n2 - n3);
        if (n4 < 0) {
            return n2 - n4;
        }
        n3 = n2;
        if (n4 <= n5) return n3;
        return n2 + (n4 - n5);
    }

    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        return super.dispatchKeyEvent(keyEvent) || this.executeKeyEvent(keyEvent);
    }

    @Override
    public boolean dispatchNestedFling(float f2, float f3, boolean bl2) {
        return this.mChildHelper.dispatchNestedFling(f2, f3, bl2);
    }

    @Override
    public boolean dispatchNestedPreFling(float f2, float f3) {
        return this.mChildHelper.dispatchNestedPreFling(f2, f3);
    }

    @Override
    public boolean dispatchNestedPreScroll(int n2, int n3, int[] nArray, int[] nArray2) {
        return this.mChildHelper.dispatchNestedPreScroll(n2, n3, nArray, nArray2);
    }

    @Override
    public boolean dispatchNestedScroll(int n2, int n3, int n4, int n5, int[] nArray) {
        return this.mChildHelper.dispatchNestedScroll(n2, n3, n4, n5, nArray);
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (this.mEdgeGlowTop != null) {
            int n2;
            int n3;
            int n4;
            int n5 = this.getScrollY();
            if (!this.mEdgeGlowTop.isFinished()) {
                n4 = canvas.save();
                n3 = this.getWidth();
                n2 = this.getPaddingLeft();
                int n6 = this.getPaddingRight();
                canvas.translate((float)this.getPaddingLeft(), (float)Math.min(0, n5));
                this.mEdgeGlowTop.setSize(n3 - n2 - n6, this.getHeight());
                if (this.mEdgeGlowTop.draw(canvas)) {
                    ViewCompat.postInvalidateOnAnimation((View)this);
                }
                canvas.restoreToCount(n4);
            }
            if (!this.mEdgeGlowBottom.isFinished()) {
                n4 = canvas.save();
                n3 = this.getWidth() - this.getPaddingLeft() - this.getPaddingRight();
                n2 = this.getHeight();
                canvas.translate((float)(-n3 + this.getPaddingLeft()), (float)(Math.max(this.getScrollRange(), n5) + n2));
                canvas.rotate(180.0f, (float)n3, 0.0f);
                this.mEdgeGlowBottom.setSize(n3, n2);
                if (this.mEdgeGlowBottom.draw(canvas)) {
                    ViewCompat.postInvalidateOnAnimation((View)this);
                }
                canvas.restoreToCount(n4);
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean executeKeyEvent(KeyEvent keyEvent) {
        boolean bl2 = false;
        this.mTempRect.setEmpty();
        if (!this.canScroll()) {
            boolean bl3 = bl2;
            if (!this.isFocused()) return bl3;
            bl3 = bl2;
            if (keyEvent.getKeyCode() == 4) return bl3;
            View view = this.findFocus();
            keyEvent = view;
            if (view == this) {
                keyEvent = null;
            }
            keyEvent = FocusFinder.getInstance().findNextFocus((ViewGroup)this, (View)keyEvent, 130);
            bl3 = bl2;
            if (keyEvent == null) return bl3;
            bl3 = bl2;
            if (keyEvent == this) return bl3;
            bl3 = bl2;
            if (!keyEvent.requestFocus(130)) return bl3;
            return true;
        }
        boolean bl4 = bl2 = false;
        if (keyEvent.getAction() != 0) return bl4;
        switch (keyEvent.getKeyCode()) {
            default: {
                return bl2;
            }
            case 19: {
                if (keyEvent.isAltPressed()) return this.fullScroll(33);
                return this.arrowScroll(33);
            }
            case 20: {
                if (keyEvent.isAltPressed()) return this.fullScroll(130);
                return this.arrowScroll(130);
            }
            case 62: 
        }
        int n2 = keyEvent.isShiftPressed() ? 33 : 130;
        this.pageScroll(n2);
        return bl2;
    }

    public void fling(int n2) {
        if (this.getChildCount() > 0) {
            int n3 = this.getHeight() - this.getPaddingBottom() - this.getPaddingTop();
            int n4 = this.getChildAt(0).getHeight();
            this.mScroller.fling(this.getScrollX(), this.getScrollY(), 0, n2, 0, 0, 0, Math.max(0, n4 - n3), 0, n3 / 2);
            ViewCompat.postInvalidateOnAnimation((View)this);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean fullScroll(int n2) {
        int n3 = n2 == 130 ? 1 : 0;
        int n4 = this.getHeight();
        this.mTempRect.top = 0;
        this.mTempRect.bottom = n4;
        if (n3 != 0 && (n3 = this.getChildCount()) > 0) {
            View view = this.getChildAt(n3 - 1);
            this.mTempRect.bottom = view.getBottom() + this.getPaddingBottom();
            this.mTempRect.top = this.mTempRect.bottom - n4;
        }
        return this.scrollAndFocus(n2, this.mTempRect.top, this.mTempRect.bottom);
    }

    protected float getBottomFadingEdgeStrength() {
        if (this.getChildCount() == 0) {
            return 0.0f;
        }
        int n2 = this.getVerticalFadingEdgeLength();
        int n3 = this.getHeight();
        int n4 = this.getPaddingBottom();
        n3 = this.getChildAt(0).getBottom() - this.getScrollY() - (n3 - n4);
        if (n3 < n2) {
            return (float)n3 / (float)n2;
        }
        return 1.0f;
    }

    public int getMaxScrollAmount() {
        return (int)(0.5f * (float)this.getHeight());
    }

    @Override
    public int getNestedScrollAxes() {
        return this.mParentHelper.getNestedScrollAxes();
    }

    int getScrollRange() {
        int n2 = 0;
        if (this.getChildCount() > 0) {
            n2 = Math.max(0, this.getChildAt(0).getHeight() - (this.getHeight() - this.getPaddingBottom() - this.getPaddingTop()));
        }
        return n2;
    }

    protected float getTopFadingEdgeStrength() {
        if (this.getChildCount() == 0) {
            return 0.0f;
        }
        int n2 = this.getVerticalFadingEdgeLength();
        int n3 = this.getScrollY();
        if (n3 < n2) {
            return (float)n3 / (float)n2;
        }
        return 1.0f;
    }

    @Override
    public boolean hasNestedScrollingParent() {
        return this.mChildHelper.hasNestedScrollingParent();
    }

    public boolean isFillViewport() {
        return this.mFillViewport;
    }

    @Override
    public boolean isNestedScrollingEnabled() {
        return this.mChildHelper.isNestedScrollingEnabled();
    }

    public boolean isSmoothScrollingEnabled() {
        return this.mSmoothScrollingEnabled;
    }

    protected void measureChild(View view, int n2, int n3) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        view.measure(NestedScrollView.getChildMeasureSpec((int)n2, (int)(this.getPaddingLeft() + this.getPaddingRight()), (int)layoutParams.width), View.MeasureSpec.makeMeasureSpec((int)0, (int)0));
    }

    protected void measureChildWithMargins(View view, int n2, int n3, int n4, int n5) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams)view.getLayoutParams();
        view.measure(NestedScrollView.getChildMeasureSpec((int)n2, (int)(this.getPaddingLeft() + this.getPaddingRight() + marginLayoutParams.leftMargin + marginLayoutParams.rightMargin + n3), (int)marginLayoutParams.width), View.MeasureSpec.makeMeasureSpec((int)(marginLayoutParams.topMargin + marginLayoutParams.bottomMargin), (int)0));
    }

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mIsLaidOut = false;
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean onGenericMotionEvent(MotionEvent motionEvent) {
        if ((motionEvent.getSource() & 2) == 0) return false;
        switch (motionEvent.getAction()) {
            default: {
                return false;
            }
            case 8: {
                float f2;
                if (this.mIsBeingDragged || (f2 = MotionEventCompat.getAxisValue(motionEvent, 9)) == 0.0f) return false;
                int n2 = (int)(this.getVerticalScrollFactorCompat() * f2);
                int n3 = this.getScrollRange();
                int n4 = this.getScrollY();
                int n5 = n4 - n2;
                if (n5 < 0) {
                    n2 = 0;
                } else {
                    n2 = n5;
                    if (n5 > n3) {
                        n2 = n3;
                    }
                }
                if (n2 == n4) return false;
                super.scrollTo(this.getScrollX(), n2);
                return true;
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        boolean bl2 = false;
        int n2 = motionEvent.getAction();
        if (n2 == 2 && this.mIsBeingDragged) {
            return true;
        }
        switch (n2 & 0xFF) {
            case 2: {
                n2 = this.mActivePointerId;
                if (n2 == -1) return this.mIsBeingDragged;
                int n3 = motionEvent.findPointerIndex(n2);
                if (n3 == -1) {
                    Log.e((String)TAG, (String)("Invalid pointerId=" + n2 + " in onInterceptTouchEvent"));
                    return this.mIsBeingDragged;
                }
                n2 = (int)motionEvent.getY(n3);
                if (Math.abs(n2 - this.mLastMotionY) <= this.mTouchSlop) return this.mIsBeingDragged;
                if ((this.getNestedScrollAxes() & 2) != 0) return this.mIsBeingDragged;
                this.mIsBeingDragged = true;
                this.mLastMotionY = n2;
                this.initVelocityTrackerIfNotExists();
                this.mVelocityTracker.addMovement(motionEvent);
                this.mNestedYOffset = 0;
                motionEvent = this.getParent();
                if (motionEvent == null) return this.mIsBeingDragged;
                motionEvent.requestDisallowInterceptTouchEvent(true);
                return this.mIsBeingDragged;
            }
            case 0: {
                n2 = (int)motionEvent.getY();
                if (!this.inChild((int)motionEvent.getX(), n2)) {
                    this.mIsBeingDragged = false;
                    this.recycleVelocityTracker();
                    return this.mIsBeingDragged;
                }
                this.mLastMotionY = n2;
                this.mActivePointerId = motionEvent.getPointerId(0);
                this.initOrResetVelocityTracker();
                this.mVelocityTracker.addMovement(motionEvent);
                this.mScroller.computeScrollOffset();
                if (!this.mScroller.isFinished()) {
                    bl2 = true;
                }
                this.mIsBeingDragged = bl2;
                this.startNestedScroll(2);
                return this.mIsBeingDragged;
            }
            case 1: 
            case 3: {
                this.mIsBeingDragged = false;
                this.mActivePointerId = -1;
                this.recycleVelocityTracker();
                if (this.mScroller.springBack(this.getScrollX(), this.getScrollY(), 0, 0, 0, this.getScrollRange())) {
                    ViewCompat.postInvalidateOnAnimation((View)this);
                }
                this.stopNestedScroll();
                return this.mIsBeingDragged;
            }
            case 6: {
                this.onSecondaryPointerUp(motionEvent);
                return this.mIsBeingDragged;
            }
        }
        return this.mIsBeingDragged;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void onLayout(boolean bl2, int n2, int n3, int n4, int n5) {
        super.onLayout(bl2, n2, n3, n4, n5);
        this.mIsLayoutDirty = false;
        if (this.mChildToScrollTo != null && NestedScrollView.isViewDescendantOf(this.mChildToScrollTo, (View)this)) {
            this.scrollToChild(this.mChildToScrollTo);
        }
        this.mChildToScrollTo = null;
        if (!this.mIsLaidOut) {
            if (this.mSavedState != null) {
                this.scrollTo(this.getScrollX(), this.mSavedState.scrollPosition);
                this.mSavedState = null;
            }
            n2 = this.getChildCount() > 0 ? this.getChildAt(0).getMeasuredHeight() : 0;
            n2 = Math.max(0, n2 - (n5 - n3 - this.getPaddingBottom() - this.getPaddingTop()));
            if (this.getScrollY() > n2) {
                this.scrollTo(this.getScrollX(), n2);
            } else if (this.getScrollY() < 0) {
                this.scrollTo(this.getScrollX(), 0);
            }
        }
        this.scrollTo(this.getScrollX(), this.getScrollY());
        this.mIsLaidOut = true;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void onMeasure(int n2, int n3) {
        View view;
        block3: {
            block2: {
                super.onMeasure(n2, n3);
                if (!this.mFillViewport || View.MeasureSpec.getMode((int)n3) == 0 || this.getChildCount() <= 0) break block2;
                view = this.getChildAt(0);
                n3 = this.getMeasuredHeight();
                if (view.getMeasuredHeight() < n3) break block3;
            }
            return;
        }
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams)view.getLayoutParams();
        view.measure(NestedScrollView.getChildMeasureSpec((int)n2, (int)(this.getPaddingLeft() + this.getPaddingRight()), (int)layoutParams.width), View.MeasureSpec.makeMeasureSpec((int)(n3 - this.getPaddingTop() - this.getPaddingBottom()), (int)0x40000000));
    }

    @Override
    public boolean onNestedFling(View view, float f2, float f3, boolean bl2) {
        if (!bl2) {
            this.flingWithNestedDispatch((int)f3);
            return true;
        }
        return false;
    }

    @Override
    public boolean onNestedPreFling(View view, float f2, float f3) {
        return this.dispatchNestedPreFling(f2, f3);
    }

    @Override
    public void onNestedPreScroll(View view, int n2, int n3, int[] nArray) {
        this.dispatchNestedPreScroll(n2, n3, nArray, null);
    }

    @Override
    public void onNestedScroll(View view, int n2, int n3, int n4, int n5) {
        n2 = this.getScrollY();
        this.scrollBy(0, n5);
        n2 = this.getScrollY() - n2;
        this.dispatchNestedScroll(0, n2, 0, n5 - n2, null);
    }

    @Override
    public void onNestedScrollAccepted(View view, View view2, int n2) {
        this.mParentHelper.onNestedScrollAccepted(view, view2, n2);
        this.startNestedScroll(2);
    }

    protected void onOverScrolled(int n2, int n3, boolean bl2, boolean bl3) {
        super.scrollTo(n2, n3);
    }

    /*
     * Enabled aggressive block sorting
     */
    protected boolean onRequestFocusInDescendants(int n2, Rect rect) {
        int n3;
        if (n2 == 2) {
            n3 = 130;
        } else {
            n3 = n2;
            if (n2 == 1) {
                n3 = 33;
            }
        }
        View view = rect == null ? FocusFinder.getInstance().findNextFocus((ViewGroup)this, null, n3) : FocusFinder.getInstance().findNextFocusFromRect((ViewGroup)this, rect, n3);
        if (view == null || this.isOffScreen(view)) {
            return false;
        }
        return view.requestFocus(n3, rect);
    }

    protected void onRestoreInstanceState(Parcelable object) {
        if (!(object instanceof SavedState)) {
            super.onRestoreInstanceState(object);
            return;
        }
        object = (SavedState)((Object)object);
        super.onRestoreInstanceState(object.getSuperState());
        this.mSavedState = object;
        this.requestLayout();
    }

    protected Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.scrollPosition = this.getScrollY();
        return savedState;
    }

    protected void onScrollChanged(int n2, int n3, int n4, int n5) {
        super.onScrollChanged(n2, n3, n4, n5);
        if (this.mOnScrollChangeListener != null) {
            this.mOnScrollChangeListener.onScrollChange(this, n2, n3, n4, n5);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void onSizeChanged(int n2, int n3, int n4, int n5) {
        super.onSizeChanged(n2, n3, n4, n5);
        View view = this.findFocus();
        if (view == null || this == view || !this.isWithinDeltaOfScreen(view, 0, n5)) {
            return;
        }
        view.getDrawingRect(this.mTempRect);
        this.offsetDescendantRectToMyCoords(view, this.mTempRect);
        this.doScrollY(this.computeScrollDeltaToGetChildRectOnScreen(this.mTempRect));
    }

    @Override
    public boolean onStartNestedScroll(View view, View view2, int n2) {
        return (n2 & 2) != 0;
    }

    @Override
    public void onStopNestedScroll(View view) {
        this.mParentHelper.onStopNestedScroll(view);
        this.stopNestedScroll();
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean onTouchEvent(MotionEvent motionEvent) {
        this.initVelocityTrackerIfNotExists();
        MotionEvent motionEvent2 = MotionEvent.obtain((MotionEvent)motionEvent);
        int n2 = MotionEventCompat.getActionMasked(motionEvent);
        if (n2 == 0) {
            this.mNestedYOffset = 0;
        }
        motionEvent2.offsetLocation(0.0f, (float)this.mNestedYOffset);
        switch (n2) {
            case 0: {
                ViewParent viewParent;
                if (this.getChildCount() == 0) {
                    return false;
                }
                boolean bl2 = !this.mScroller.isFinished();
                this.mIsBeingDragged = bl2;
                if (bl2 && (viewParent = this.getParent()) != null) {
                    viewParent.requestDisallowInterceptTouchEvent(true);
                }
                if (!this.mScroller.isFinished()) {
                    this.mScroller.abortAnimation();
                }
                this.mLastMotionY = (int)motionEvent.getY();
                this.mActivePointerId = motionEvent.getPointerId(0);
                this.startNestedScroll(2);
                break;
            }
            case 2: {
                int n3;
                int n4 = motionEvent.findPointerIndex(this.mActivePointerId);
                if (n4 == -1) {
                    Log.e((String)TAG, (String)("Invalid pointerId=" + this.mActivePointerId + " in onTouchEvent"));
                    break;
                }
                int n5 = (int)motionEvent.getY(n4);
                int n6 = n2 = this.mLastMotionY - n5;
                if (this.dispatchNestedPreScroll(0, n2, this.mScrollConsumed, this.mScrollOffset)) {
                    n6 = n2 - this.mScrollConsumed[1];
                    motionEvent2.offsetLocation(0.0f, (float)this.mScrollOffset[1]);
                    this.mNestedYOffset += this.mScrollOffset[1];
                }
                n2 = n6;
                if (!this.mIsBeingDragged) {
                    n2 = n6;
                    if (Math.abs(n6) > this.mTouchSlop) {
                        ViewParent viewParent = this.getParent();
                        if (viewParent != null) {
                            viewParent.requestDisallowInterceptTouchEvent(true);
                        }
                        this.mIsBeingDragged = true;
                        n2 = n6 > 0 ? n6 - this.mTouchSlop : n6 + this.mTouchSlop;
                    }
                }
                if (!this.mIsBeingDragged) break;
                this.mLastMotionY = n5 - this.mScrollOffset[1];
                int n7 = this.getScrollY();
                n5 = this.getScrollRange();
                n6 = this.getOverScrollMode();
                n6 = n6 == 0 || n6 == 1 && n5 > 0 ? 1 : 0;
                if (this.overScrollByCompat(0, n2, 0, this.getScrollY(), 0, n5, 0, 0, true) && !this.hasNestedScrollingParent()) {
                    this.mVelocityTracker.clear();
                }
                if (this.dispatchNestedScroll(0, n3 = this.getScrollY() - n7, 0, n2 - n3, this.mScrollOffset)) {
                    this.mLastMotionY -= this.mScrollOffset[1];
                    motionEvent2.offsetLocation(0.0f, (float)this.mScrollOffset[1]);
                    this.mNestedYOffset += this.mScrollOffset[1];
                    break;
                }
                if (n6 == 0) break;
                this.ensureGlows();
                n6 = n7 + n2;
                if (n6 < 0) {
                    this.mEdgeGlowTop.onPull((float)n2 / (float)this.getHeight(), motionEvent.getX(n4) / (float)this.getWidth());
                    if (!this.mEdgeGlowBottom.isFinished()) {
                        this.mEdgeGlowBottom.onRelease();
                    }
                } else if (n6 > n5) {
                    this.mEdgeGlowBottom.onPull((float)n2 / (float)this.getHeight(), 1.0f - motionEvent.getX(n4) / (float)this.getWidth());
                    if (!this.mEdgeGlowTop.isFinished()) {
                        this.mEdgeGlowTop.onRelease();
                    }
                }
                if (this.mEdgeGlowTop == null || this.mEdgeGlowTop.isFinished() && this.mEdgeGlowBottom.isFinished()) break;
                ViewCompat.postInvalidateOnAnimation((View)this);
                break;
            }
            case 1: {
                if (this.mIsBeingDragged) {
                    motionEvent = this.mVelocityTracker;
                    motionEvent.computeCurrentVelocity(1000, (float)this.mMaximumVelocity);
                    n2 = (int)VelocityTrackerCompat.getYVelocity((VelocityTracker)motionEvent, this.mActivePointerId);
                    if (Math.abs(n2) > this.mMinimumVelocity) {
                        this.flingWithNestedDispatch(-n2);
                    } else if (this.mScroller.springBack(this.getScrollX(), this.getScrollY(), 0, 0, 0, this.getScrollRange())) {
                        ViewCompat.postInvalidateOnAnimation((View)this);
                    }
                }
                this.mActivePointerId = -1;
                this.endDrag();
                break;
            }
            case 3: {
                if (this.mIsBeingDragged && this.getChildCount() > 0 && this.mScroller.springBack(this.getScrollX(), this.getScrollY(), 0, 0, 0, this.getScrollRange())) {
                    ViewCompat.postInvalidateOnAnimation((View)this);
                }
                this.mActivePointerId = -1;
                this.endDrag();
                break;
            }
            case 5: {
                n2 = MotionEventCompat.getActionIndex(motionEvent);
                this.mLastMotionY = (int)motionEvent.getY(n2);
                this.mActivePointerId = motionEvent.getPointerId(n2);
                break;
            }
            case 6: {
                this.onSecondaryPointerUp(motionEvent);
                this.mLastMotionY = (int)motionEvent.getY(motionEvent.findPointerIndex(this.mActivePointerId));
                break;
            }
        }
        if (this.mVelocityTracker != null) {
            this.mVelocityTracker.addMovement(motionEvent2);
        }
        motionEvent2.recycle();
        return true;
    }

    /*
     * Enabled aggressive block sorting
     */
    boolean overScrollByCompat(int n2, int n3, int n4, int n5, int n6, int n7, int n8, int n9, boolean bl2) {
        int n10 = this.getOverScrollMode();
        boolean bl3 = this.computeHorizontalScrollRange() > this.computeHorizontalScrollExtent();
        boolean bl4 = this.computeVerticalScrollRange() > this.computeVerticalScrollExtent();
        bl3 = n10 == 0 || n10 == 1 && bl3;
        bl4 = n10 == 0 || n10 == 1 && bl4;
        n4 += n2;
        if (!bl3) {
            n8 = 0;
        }
        n5 += n3;
        if (!bl4) {
            n9 = 0;
        }
        n3 = -n8;
        n2 = n8 + n6;
        n6 = -n9;
        n7 = n9 + n7;
        bl2 = false;
        if (n4 > n2) {
            bl2 = true;
        } else {
            n2 = n4;
            if (n4 < n3) {
                n2 = n3;
                bl2 = true;
            }
        }
        boolean bl5 = false;
        if (n5 > n7) {
            n3 = n7;
            bl5 = true;
        } else {
            n3 = n5;
            if (n5 < n6) {
                n3 = n6;
                bl5 = true;
            }
        }
        if (bl5) {
            this.mScroller.springBack(n2, n3, 0, 0, 0, this.getScrollRange());
        }
        this.onOverScrolled(n2, n3, bl2, bl5);
        return bl2 || bl5;
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean pageScroll(int n2) {
        int n3 = n2 == 130 ? 1 : 0;
        int n4 = this.getHeight();
        if (n3 != 0) {
            View view;
            this.mTempRect.top = this.getScrollY() + n4;
            n3 = this.getChildCount();
            if (n3 > 0 && this.mTempRect.top + n4 > (view = this.getChildAt(n3 - 1)).getBottom()) {
                this.mTempRect.top = view.getBottom() - n4;
            }
        } else {
            this.mTempRect.top = this.getScrollY() - n4;
            if (this.mTempRect.top < 0) {
                this.mTempRect.top = 0;
            }
        }
        this.mTempRect.bottom = this.mTempRect.top + n4;
        return this.scrollAndFocus(n2, this.mTempRect.top, this.mTempRect.bottom);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void requestChildFocus(View view, View view2) {
        if (!this.mIsLayoutDirty) {
            this.scrollToChild(view2);
        } else {
            this.mChildToScrollTo = view2;
        }
        super.requestChildFocus(view, view2);
    }

    public boolean requestChildRectangleOnScreen(View view, Rect rect, boolean bl2) {
        rect.offset(view.getLeft() - view.getScrollX(), view.getTop() - view.getScrollY());
        return this.scrollToChildRect(rect, bl2);
    }

    public void requestDisallowInterceptTouchEvent(boolean bl2) {
        if (bl2) {
            this.recycleVelocityTracker();
        }
        super.requestDisallowInterceptTouchEvent(bl2);
    }

    public void requestLayout() {
        this.mIsLayoutDirty = true;
        super.requestLayout();
    }

    public void scrollTo(int n2, int n3) {
        if (this.getChildCount() > 0) {
            View view = this.getChildAt(0);
            n2 = NestedScrollView.clamp(n2, this.getWidth() - this.getPaddingRight() - this.getPaddingLeft(), view.getWidth());
            n3 = NestedScrollView.clamp(n3, this.getHeight() - this.getPaddingBottom() - this.getPaddingTop(), view.getHeight());
            if (n2 != this.getScrollX() || n3 != this.getScrollY()) {
                super.scrollTo(n2, n3);
            }
        }
    }

    public void setFillViewport(boolean bl2) {
        if (bl2 != this.mFillViewport) {
            this.mFillViewport = bl2;
            this.requestLayout();
        }
    }

    @Override
    public void setNestedScrollingEnabled(boolean bl2) {
        this.mChildHelper.setNestedScrollingEnabled(bl2);
    }

    public void setOnScrollChangeListener(OnScrollChangeListener onScrollChangeListener) {
        this.mOnScrollChangeListener = onScrollChangeListener;
    }

    public void setSmoothScrollingEnabled(boolean bl2) {
        this.mSmoothScrollingEnabled = bl2;
    }

    public boolean shouldDelayChildPressedState() {
        return true;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final void smoothScrollBy(int n2, int n3) {
        if (this.getChildCount() == 0) {
            return;
        }
        if (AnimationUtils.currentAnimationTimeMillis() - this.mLastScroll > 250L) {
            n2 = this.getHeight();
            int n4 = this.getPaddingBottom();
            int n5 = this.getPaddingTop();
            n4 = Math.max(0, this.getChildAt(0).getHeight() - (n2 - n4 - n5));
            n2 = this.getScrollY();
            n3 = Math.max(0, Math.min(n2 + n3, n4));
            this.mScroller.startScroll(this.getScrollX(), n2, 0, n3 - n2);
            ViewCompat.postInvalidateOnAnimation((View)this);
        } else {
            if (!this.mScroller.isFinished()) {
                this.mScroller.abortAnimation();
            }
            this.scrollBy(n2, n3);
        }
        this.mLastScroll = AnimationUtils.currentAnimationTimeMillis();
    }

    public final void smoothScrollTo(int n2, int n3) {
        this.smoothScrollBy(n2 - this.getScrollX(), n3 - this.getScrollY());
    }

    @Override
    public boolean startNestedScroll(int n2) {
        return this.mChildHelper.startNestedScroll(n2);
    }

    @Override
    public void stopNestedScroll() {
        this.mChildHelper.stopNestedScroll();
    }

    static class AccessibilityDelegate
    extends AccessibilityDelegateCompat {
        AccessibilityDelegate() {
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public void onInitializeAccessibilityEvent(View object, AccessibilityEvent object2) {
            super.onInitializeAccessibilityEvent((View)object, (AccessibilityEvent)object2);
            object = (NestedScrollView)object;
            object2.setClassName((CharSequence)ScrollView.class.getName());
            object2 = AccessibilityEventCompat.asRecord((AccessibilityEvent)object2);
            boolean bl2 = ((NestedScrollView)object).getScrollRange() > 0;
            ((AccessibilityRecordCompat)object2).setScrollable(bl2);
            ((AccessibilityRecordCompat)object2).setScrollX(object.getScrollX());
            ((AccessibilityRecordCompat)object2).setScrollY(object.getScrollY());
            ((AccessibilityRecordCompat)object2).setMaxScrollX(object.getScrollX());
            ((AccessibilityRecordCompat)object2).setMaxScrollY(((NestedScrollView)object).getScrollRange());
        }

        @Override
        public void onInitializeAccessibilityNodeInfo(View object, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            int n2;
            super.onInitializeAccessibilityNodeInfo((View)object, accessibilityNodeInfoCompat);
            object = (NestedScrollView)object;
            accessibilityNodeInfoCompat.setClassName(ScrollView.class.getName());
            if (object.isEnabled() && (n2 = ((NestedScrollView)object).getScrollRange()) > 0) {
                accessibilityNodeInfoCompat.setScrollable(true);
                if (object.getScrollY() > 0) {
                    accessibilityNodeInfoCompat.addAction(8192);
                }
                if (object.getScrollY() < n2) {
                    accessibilityNodeInfoCompat.addAction(4096);
                }
            }
        }

        @Override
        public boolean performAccessibilityAction(View object, int n2, Bundle bundle) {
            if (super.performAccessibilityAction((View)object, n2, bundle)) {
                return true;
            }
            if (!(object = (NestedScrollView)object).isEnabled()) {
                return false;
            }
            switch (n2) {
                default: {
                    return false;
                }
                case 4096: {
                    n2 = object.getHeight();
                    int n3 = object.getPaddingBottom();
                    int n4 = object.getPaddingTop();
                    n2 = Math.min(object.getScrollY() + (n2 - n3 - n4), ((NestedScrollView)object).getScrollRange());
                    if (n2 != object.getScrollY()) {
                        ((NestedScrollView)object).smoothScrollTo(0, n2);
                        return true;
                    }
                    return false;
                }
                case 8192: 
            }
            n2 = object.getHeight();
            int n5 = object.getPaddingBottom();
            int n6 = object.getPaddingTop();
            n2 = Math.max(object.getScrollY() - (n2 - n5 - n6), 0);
            if (n2 != object.getScrollY()) {
                ((NestedScrollView)object).smoothScrollTo(0, n2);
                return true;
            }
            return false;
        }
    }

    public static interface OnScrollChangeListener {
        public void onScrollChange(NestedScrollView var1, int var2, int var3, int var4, int var5);
    }

    static class SavedState
    extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>(){

            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            public SavedState[] newArray(int n2) {
                return new SavedState[n2];
            }
        };
        public int scrollPosition;

        SavedState(Parcel parcel) {
            super(parcel);
            this.scrollPosition = parcel.readInt();
        }

        SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        public String toString() {
            return "HorizontalScrollView.SavedState{" + Integer.toHexString(System.identityHashCode((Object)this)) + " scrollPosition=" + this.scrollPosition + "}";
        }

        public void writeToParcel(Parcel parcel, int n2) {
            super.writeToParcel(parcel, n2);
            parcel.writeInt(this.scrollPosition);
        }
    }
}

