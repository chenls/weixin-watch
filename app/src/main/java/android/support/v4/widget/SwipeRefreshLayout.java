/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.os.Build$VERSION
 *  android.util.AttributeSet
 *  android.util.DisplayMetrics
 *  android.util.Log
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.ViewConfiguration
 *  android.view.ViewGroup
 *  android.view.animation.Animation
 *  android.view.animation.Animation$AnimationListener
 *  android.view.animation.DecelerateInterpolator
 *  android.view.animation.Interpolator
 *  android.view.animation.Transformation
 *  android.widget.AbsListView
 */
package android.support.v4.widget;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.NestedScrollingChild;
import android.support.v4.view.NestedScrollingChildHelper;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.NestedScrollingParentHelper;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.CircleImageView;
import android.support.v4.widget.MaterialProgressDrawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.Transformation;
import android.widget.AbsListView;

public class SwipeRefreshLayout
extends ViewGroup
implements NestedScrollingParent,
NestedScrollingChild {
    private static final int ALPHA_ANIMATION_DURATION = 300;
    private static final int ANIMATE_TO_START_DURATION = 200;
    private static final int ANIMATE_TO_TRIGGER_DURATION = 200;
    private static final int CIRCLE_BG_LIGHT = -328966;
    @VisibleForTesting
    static final int CIRCLE_DIAMETER = 40;
    @VisibleForTesting
    static final int CIRCLE_DIAMETER_LARGE = 56;
    private static final float DECELERATE_INTERPOLATION_FACTOR = 2.0f;
    public static final int DEFAULT = 1;
    private static final int DEFAULT_CIRCLE_TARGET = 64;
    private static final float DRAG_RATE = 0.5f;
    private static final int INVALID_POINTER = -1;
    public static final int LARGE = 0;
    private static final int[] LAYOUT_ATTRS;
    private static final String LOG_TAG;
    private static final int MAX_ALPHA = 255;
    private static final float MAX_PROGRESS_ANGLE = 0.8f;
    private static final int SCALE_DOWN_DURATION = 150;
    private static final int STARTING_PROGRESS_ALPHA = 76;
    private int mActivePointerId = -1;
    private Animation mAlphaMaxAnimation;
    private Animation mAlphaStartAnimation;
    private final Animation mAnimateToCorrectPosition;
    private final Animation mAnimateToStartPosition;
    private OnChildScrollUpCallback mChildScrollUpCallback;
    private int mCircleDiameter;
    CircleImageView mCircleView;
    private int mCircleViewIndex = -1;
    int mCurrentTargetOffsetTop;
    private final DecelerateInterpolator mDecelerateInterpolator;
    protected int mFrom;
    private float mInitialDownY;
    private float mInitialMotionY;
    private boolean mIsBeingDragged;
    OnRefreshListener mListener;
    private int mMediumAnimationDuration;
    private boolean mNestedScrollInProgress;
    private final NestedScrollingChildHelper mNestedScrollingChildHelper;
    private final NestedScrollingParentHelper mNestedScrollingParentHelper;
    boolean mNotify;
    protected int mOriginalOffsetTop;
    private final int[] mParentOffsetInWindow;
    private final int[] mParentScrollConsumed = new int[2];
    MaterialProgressDrawable mProgress;
    private Animation.AnimationListener mRefreshListener;
    boolean mRefreshing = false;
    private boolean mReturningToStart;
    boolean mScale;
    private Animation mScaleAnimation;
    private Animation mScaleDownAnimation;
    private Animation mScaleDownToStartAnimation;
    float mSpinnerFinalOffset;
    float mStartingScale;
    private View mTarget;
    private float mTotalDragDistance = -1.0f;
    private float mTotalUnconsumed;
    private int mTouchSlop;
    boolean mUsingCustomStart;

    static {
        LOG_TAG = SwipeRefreshLayout.class.getSimpleName();
        LAYOUT_ATTRS = new int[]{0x101000E};
    }

    public SwipeRefreshLayout(Context context) {
        this(context, null);
    }

    public SwipeRefreshLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        int n2;
        this.mParentOffsetInWindow = new int[2];
        this.mRefreshListener = new Animation.AnimationListener(){

            public void onAnimationEnd(Animation animation) {
                if (SwipeRefreshLayout.this.mRefreshing) {
                    SwipeRefreshLayout.this.mProgress.setAlpha(255);
                    SwipeRefreshLayout.this.mProgress.start();
                    if (SwipeRefreshLayout.this.mNotify && SwipeRefreshLayout.this.mListener != null) {
                        SwipeRefreshLayout.this.mListener.onRefresh();
                    }
                    SwipeRefreshLayout.this.mCurrentTargetOffsetTop = SwipeRefreshLayout.this.mCircleView.getTop();
                    return;
                }
                SwipeRefreshLayout.this.reset();
            }

            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationStart(Animation animation) {
            }
        };
        this.mAnimateToCorrectPosition = new Animation(){

            /*
             * Enabled aggressive block sorting
             */
            public void applyTransformation(float f2, Transformation transformation) {
                int n2 = !SwipeRefreshLayout.this.mUsingCustomStart ? (int)(SwipeRefreshLayout.this.mSpinnerFinalOffset - (float)Math.abs(SwipeRefreshLayout.this.mOriginalOffsetTop)) : (int)SwipeRefreshLayout.this.mSpinnerFinalOffset;
                int n3 = SwipeRefreshLayout.this.mFrom;
                n2 = (int)((float)(n2 - SwipeRefreshLayout.this.mFrom) * f2);
                int n4 = SwipeRefreshLayout.this.mCircleView.getTop();
                SwipeRefreshLayout.this.setTargetOffsetTopAndBottom(n3 + n2 - n4, false);
                SwipeRefreshLayout.this.mProgress.setArrowScale(1.0f - f2);
            }
        };
        this.mAnimateToStartPosition = new Animation(){

            public void applyTransformation(float f2, Transformation transformation) {
                SwipeRefreshLayout.this.moveToStart(f2);
            }
        };
        this.mTouchSlop = ViewConfiguration.get((Context)context).getScaledTouchSlop();
        this.mMediumAnimationDuration = this.getResources().getInteger(0x10E0001);
        this.setWillNotDraw(false);
        this.mDecelerateInterpolator = new DecelerateInterpolator(2.0f);
        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        this.mCircleDiameter = (int)(40.0f * displayMetrics.density);
        this.createProgressView();
        ViewCompat.setChildrenDrawingOrderEnabled(this, true);
        this.mTotalDragDistance = this.mSpinnerFinalOffset = 64.0f * displayMetrics.density;
        this.mNestedScrollingParentHelper = new NestedScrollingParentHelper(this);
        this.mNestedScrollingChildHelper = new NestedScrollingChildHelper((View)this);
        this.setNestedScrollingEnabled(true);
        this.mCurrentTargetOffsetTop = n2 = -this.mCircleDiameter;
        this.mOriginalOffsetTop = n2;
        this.moveToStart(1.0f);
        context = context.obtainStyledAttributes(attributeSet, LAYOUT_ATTRS);
        this.setEnabled(context.getBoolean(0, true));
        context.recycle();
    }

    private void animateOffsetToCorrectPosition(int n2, Animation.AnimationListener animationListener) {
        this.mFrom = n2;
        this.mAnimateToCorrectPosition.reset();
        this.mAnimateToCorrectPosition.setDuration(200L);
        this.mAnimateToCorrectPosition.setInterpolator((Interpolator)this.mDecelerateInterpolator);
        if (animationListener != null) {
            this.mCircleView.setAnimationListener(animationListener);
        }
        this.mCircleView.clearAnimation();
        this.mCircleView.startAnimation(this.mAnimateToCorrectPosition);
    }

    private void animateOffsetToStartPosition(int n2, Animation.AnimationListener animationListener) {
        if (this.mScale) {
            this.startScaleDownReturnToStartAnimation(n2, animationListener);
            return;
        }
        this.mFrom = n2;
        this.mAnimateToStartPosition.reset();
        this.mAnimateToStartPosition.setDuration(200L);
        this.mAnimateToStartPosition.setInterpolator((Interpolator)this.mDecelerateInterpolator);
        if (animationListener != null) {
            this.mCircleView.setAnimationListener(animationListener);
        }
        this.mCircleView.clearAnimation();
        this.mCircleView.startAnimation(this.mAnimateToStartPosition);
    }

    private void createProgressView() {
        this.mCircleView = new CircleImageView(this.getContext(), -328966);
        this.mProgress = new MaterialProgressDrawable(this.getContext(), (View)this);
        this.mProgress.setBackgroundColor(-328966);
        this.mCircleView.setImageDrawable(this.mProgress);
        this.mCircleView.setVisibility(8);
        this.addView((View)this.mCircleView);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private void ensureTarget() {
        if (this.mTarget != null) return;
        int n2 = 0;
        while (n2 < this.getChildCount()) {
            View view = this.getChildAt(n2);
            if (!view.equals((Object)this.mCircleView)) {
                this.mTarget = view;
                return;
            }
            ++n2;
        }
    }

    private void finishSpinner(float f2) {
        if (f2 > this.mTotalDragDistance) {
            this.setRefreshing(true, true);
            return;
        }
        this.mRefreshing = false;
        this.mProgress.setStartEndTrim(0.0f, 0.0f);
        Animation.AnimationListener animationListener = null;
        if (!this.mScale) {
            animationListener = new Animation.AnimationListener(){

                public void onAnimationEnd(Animation animation) {
                    if (!SwipeRefreshLayout.this.mScale) {
                        SwipeRefreshLayout.this.startScaleDownAnimation(null);
                    }
                }

                public void onAnimationRepeat(Animation animation) {
                }

                public void onAnimationStart(Animation animation) {
                }
            };
        }
        this.animateOffsetToStartPosition(this.mCurrentTargetOffsetTop, animationListener);
        this.mProgress.showArrow(false);
    }

    private boolean isAlphaUsedForScale() {
        return Build.VERSION.SDK_INT < 11;
    }

    private boolean isAnimationRunning(Animation animation) {
        return animation != null && animation.hasStarted() && !animation.hasEnded();
    }

    /*
     * Enabled aggressive block sorting
     */
    private void moveSpinner(float f2) {
        this.mProgress.showArrow(true);
        float f3 = Math.min(1.0f, Math.abs(f2 / this.mTotalDragDistance));
        float f4 = (float)Math.max((double)f3 - 0.4, 0.0) * 5.0f / 3.0f;
        float f5 = Math.abs(f2);
        float f6 = this.mTotalDragDistance;
        float f7 = this.mUsingCustomStart ? this.mSpinnerFinalOffset - (float)this.mOriginalOffsetTop : this.mSpinnerFinalOffset;
        f5 = Math.max(0.0f, Math.min(f5 - f6, 2.0f * f7) / f7);
        f5 = (float)((double)(f5 / 4.0f) - Math.pow(f5 / 4.0f, 2.0)) * 2.0f;
        int n2 = this.mOriginalOffsetTop;
        int n3 = (int)(f7 * f3 + f7 * f5 * 2.0f);
        if (this.mCircleView.getVisibility() != 0) {
            this.mCircleView.setVisibility(0);
        }
        if (!this.mScale) {
            ViewCompat.setScaleX((View)this.mCircleView, 1.0f);
            ViewCompat.setScaleY((View)this.mCircleView, 1.0f);
        }
        if (this.mScale) {
            this.setAnimationProgress(Math.min(1.0f, f2 / this.mTotalDragDistance));
        }
        if (f2 < this.mTotalDragDistance) {
            if (this.mProgress.getAlpha() > 76 && !this.isAnimationRunning(this.mAlphaStartAnimation)) {
                this.startProgressAlphaStartAnimation();
            }
        } else if (this.mProgress.getAlpha() < 255 && !this.isAnimationRunning(this.mAlphaMaxAnimation)) {
            this.startProgressAlphaMaxAnimation();
        }
        this.mProgress.setStartEndTrim(0.0f, Math.min(0.8f, f4 * 0.8f));
        this.mProgress.setArrowScale(Math.min(1.0f, f4));
        this.mProgress.setProgressRotation((-0.25f + 0.4f * f4 + 2.0f * f5) * 0.5f);
        this.setTargetOffsetTopAndBottom(n2 + n3 - this.mCurrentTargetOffsetTop, true);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void onSecondaryPointerUp(MotionEvent motionEvent) {
        int n2 = MotionEventCompat.getActionIndex(motionEvent);
        if (motionEvent.getPointerId(n2) == this.mActivePointerId) {
            n2 = n2 == 0 ? 1 : 0;
            this.mActivePointerId = motionEvent.getPointerId(n2);
        }
    }

    private void setColorViewAlpha(int n2) {
        this.mCircleView.getBackground().setAlpha(n2);
        this.mProgress.setAlpha(n2);
    }

    private void setRefreshing(boolean bl2, boolean bl3) {
        block3: {
            block2: {
                if (this.mRefreshing == bl2) break block2;
                this.mNotify = bl3;
                this.ensureTarget();
                this.mRefreshing = bl2;
                if (!this.mRefreshing) break block3;
                this.animateOffsetToCorrectPosition(this.mCurrentTargetOffsetTop, this.mRefreshListener);
            }
            return;
        }
        this.startScaleDownAnimation(this.mRefreshListener);
    }

    private Animation startAlphaAnimation(final int n2, final int n3) {
        if (this.mScale && this.isAlphaUsedForScale()) {
            return null;
        }
        Animation animation = new Animation(){

            public void applyTransformation(float f2, Transformation transformation) {
                SwipeRefreshLayout.this.mProgress.setAlpha((int)((float)n2 + (float)(n3 - n2) * f2));
            }
        };
        animation.setDuration(300L);
        this.mCircleView.setAnimationListener(null);
        this.mCircleView.clearAnimation();
        this.mCircleView.startAnimation(animation);
        return animation;
    }

    private void startDragging(float f2) {
        if (f2 - this.mInitialDownY > (float)this.mTouchSlop && !this.mIsBeingDragged) {
            this.mInitialMotionY = this.mInitialDownY + (float)this.mTouchSlop;
            this.mIsBeingDragged = true;
            this.mProgress.setAlpha(76);
        }
    }

    private void startProgressAlphaMaxAnimation() {
        this.mAlphaMaxAnimation = this.startAlphaAnimation(this.mProgress.getAlpha(), 255);
    }

    private void startProgressAlphaStartAnimation() {
        this.mAlphaStartAnimation = this.startAlphaAnimation(this.mProgress.getAlpha(), 76);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void startScaleDownReturnToStartAnimation(int n2, Animation.AnimationListener animationListener) {
        this.mFrom = n2;
        this.mStartingScale = this.isAlphaUsedForScale() ? (float)this.mProgress.getAlpha() : ViewCompat.getScaleX((View)this.mCircleView);
        this.mScaleDownToStartAnimation = new Animation(){

            public void applyTransformation(float f2, Transformation transformation) {
                float f3 = SwipeRefreshLayout.this.mStartingScale;
                float f4 = -SwipeRefreshLayout.this.mStartingScale;
                SwipeRefreshLayout.this.setAnimationProgress(f3 + f4 * f2);
                SwipeRefreshLayout.this.moveToStart(f2);
            }
        };
        this.mScaleDownToStartAnimation.setDuration(150L);
        if (animationListener != null) {
            this.mCircleView.setAnimationListener(animationListener);
        }
        this.mCircleView.clearAnimation();
        this.mCircleView.startAnimation(this.mScaleDownToStartAnimation);
    }

    private void startScaleUpAnimation(Animation.AnimationListener animationListener) {
        this.mCircleView.setVisibility(0);
        if (Build.VERSION.SDK_INT >= 11) {
            this.mProgress.setAlpha(255);
        }
        this.mScaleAnimation = new Animation(){

            public void applyTransformation(float f2, Transformation transformation) {
                SwipeRefreshLayout.this.setAnimationProgress(f2);
            }
        };
        this.mScaleAnimation.setDuration((long)this.mMediumAnimationDuration);
        if (animationListener != null) {
            this.mCircleView.setAnimationListener(animationListener);
        }
        this.mCircleView.clearAnimation();
        this.mCircleView.startAnimation(this.mScaleAnimation);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean canChildScrollUp() {
        boolean bl2 = true;
        boolean bl3 = false;
        if (this.mChildScrollUpCallback != null) {
            return this.mChildScrollUpCallback.canChildScrollUp(this, this.mTarget);
        }
        if (Build.VERSION.SDK_INT >= 14) return ViewCompat.canScrollVertically(this.mTarget, -1);
        if (this.mTarget instanceof AbsListView) {
            AbsListView absListView = (AbsListView)this.mTarget;
            if (absListView.getChildCount() <= 0) return false;
            bl3 = bl2;
            if (absListView.getFirstVisiblePosition() > 0) return bl3;
            bl3 = bl2;
            if (absListView.getChildAt(0).getTop() < absListView.getPaddingTop()) return bl3;
            return false;
        }
        if (ViewCompat.canScrollVertically(this.mTarget, -1)) return true;
        if (this.mTarget.getScrollY() <= 0) return bl3;
        return true;
    }

    @Override
    public boolean dispatchNestedFling(float f2, float f3, boolean bl2) {
        return this.mNestedScrollingChildHelper.dispatchNestedFling(f2, f3, bl2);
    }

    @Override
    public boolean dispatchNestedPreFling(float f2, float f3) {
        return this.mNestedScrollingChildHelper.dispatchNestedPreFling(f2, f3);
    }

    @Override
    public boolean dispatchNestedPreScroll(int n2, int n3, int[] nArray, int[] nArray2) {
        return this.mNestedScrollingChildHelper.dispatchNestedPreScroll(n2, n3, nArray, nArray2);
    }

    @Override
    public boolean dispatchNestedScroll(int n2, int n3, int n4, int n5, int[] nArray) {
        return this.mNestedScrollingChildHelper.dispatchNestedScroll(n2, n3, n4, n5, nArray);
    }

    /*
     * Enabled aggressive block sorting
     */
    protected int getChildDrawingOrder(int n2, int n3) {
        block5: {
            block4: {
                if (this.mCircleViewIndex < 0) break block4;
                if (n3 == n2 - 1) {
                    return this.mCircleViewIndex;
                }
                if (n3 >= this.mCircleViewIndex) break block5;
            }
            return n3;
        }
        return n3 + 1;
    }

    @Override
    public int getNestedScrollAxes() {
        return this.mNestedScrollingParentHelper.getNestedScrollAxes();
    }

    public int getProgressCircleDiameter() {
        return this.mCircleDiameter;
    }

    @Override
    public boolean hasNestedScrollingParent() {
        return this.mNestedScrollingChildHelper.hasNestedScrollingParent();
    }

    @Override
    public boolean isNestedScrollingEnabled() {
        return this.mNestedScrollingChildHelper.isNestedScrollingEnabled();
    }

    public boolean isRefreshing() {
        return this.mRefreshing;
    }

    void moveToStart(float f2) {
        this.setTargetOffsetTopAndBottom(this.mFrom + (int)((float)(this.mOriginalOffsetTop - this.mFrom) * f2) - this.mCircleView.getTop(), false);
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.reset();
    }

    /*
     * Unable to fully structure code
     */
    public boolean onInterceptTouchEvent(MotionEvent var1_1) {
        this.ensureTarget();
        var2_2 = MotionEventCompat.getActionMasked(var1_1);
        if (this.mReturningToStart && var2_2 == 0) {
            this.mReturningToStart = false;
        }
        if (!this.isEnabled() || this.mReturningToStart || this.canChildScrollUp() || this.mRefreshing || this.mNestedScrollInProgress) lbl-1000:
        // 3 sources

        {
            return false;
        }
        switch (var2_2) lbl-1000:
        // 4 sources

        {
            default: lbl-1000:
            // 2 sources

            {
                return this.mIsBeingDragged;
            }
            case 0: {
                this.setTargetOffsetTopAndBottom(this.mOriginalOffsetTop - this.mCircleView.getTop(), true);
                this.mActivePointerId = var1_1.getPointerId(0);
                this.mIsBeingDragged = false;
                var2_2 = var1_1.findPointerIndex(this.mActivePointerId);
                if (var2_2 < 0) ** GOTO lbl-1000
                this.mInitialDownY = var1_1.getY(var2_2);
                ** GOTO lbl-1000
            }
            case 2: {
                if (this.mActivePointerId == -1) {
                    Log.e((String)SwipeRefreshLayout.LOG_TAG, (String)"Got ACTION_MOVE event but don't have an active pointer id.");
                    return false;
                }
                if ((var2_2 = var1_1.findPointerIndex(this.mActivePointerId)) < 0) ** continue;
                this.startDragging(var1_1.getY(var2_2));
                ** GOTO lbl-1000
            }
            case 6: {
                this.onSecondaryPointerUp(var1_1);
                ** GOTO lbl-1000
            }
            case 1: 
            case 3: 
        }
        this.mIsBeingDragged = false;
        this.mActivePointerId = -1;
        ** while (true)
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void onLayout(boolean bl2, int n2, int n3, int n4, int n5) {
        block5: {
            block4: {
                n2 = this.getMeasuredWidth();
                n3 = this.getMeasuredHeight();
                if (this.getChildCount() == 0) break block4;
                if (this.mTarget == null) {
                    this.ensureTarget();
                }
                if (this.mTarget != null) break block5;
            }
            return;
        }
        View view = this.mTarget;
        n4 = this.getPaddingLeft();
        n5 = this.getPaddingTop();
        view.layout(n4, n5, n4 + (n2 - this.getPaddingLeft() - this.getPaddingRight()), n5 + (n3 - this.getPaddingTop() - this.getPaddingBottom()));
        n3 = this.mCircleView.getMeasuredWidth();
        n4 = this.mCircleView.getMeasuredHeight();
        this.mCircleView.layout(n2 / 2 - n3 / 2, this.mCurrentTargetOffsetTop, n2 / 2 + n3 / 2, this.mCurrentTargetOffsetTop + n4);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void onMeasure(int n2, int n3) {
        super.onMeasure(n2, n3);
        if (this.mTarget == null) {
            this.ensureTarget();
        }
        if (this.mTarget != null) {
            this.mTarget.measure(View.MeasureSpec.makeMeasureSpec((int)(this.getMeasuredWidth() - this.getPaddingLeft() - this.getPaddingRight()), (int)0x40000000), View.MeasureSpec.makeMeasureSpec((int)(this.getMeasuredHeight() - this.getPaddingTop() - this.getPaddingBottom()), (int)0x40000000));
            this.mCircleView.measure(View.MeasureSpec.makeMeasureSpec((int)this.mCircleDiameter, (int)0x40000000), View.MeasureSpec.makeMeasureSpec((int)this.mCircleDiameter, (int)0x40000000));
            this.mCircleViewIndex = -1;
            for (n2 = 0; n2 < this.getChildCount(); ++n2) {
                if (this.getChildAt(n2) != this.mCircleView) continue;
                this.mCircleViewIndex = n2;
                return;
            }
        }
    }

    @Override
    public boolean onNestedFling(View view, float f2, float f3, boolean bl2) {
        return this.dispatchNestedFling(f2, f3, bl2);
    }

    @Override
    public boolean onNestedPreFling(View view, float f2, float f3) {
        return this.dispatchNestedPreFling(f2, f3);
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    @Override
    public void onNestedPreScroll(View object, int n2, int n3, int[] nArray) {
        int[] nArray2;
        void var2_3;
        void var4_5;
        void var3_4;
        if (var3_4 > 0 && this.mTotalUnconsumed > 0.0f) {
            if ((float)var3_4 > this.mTotalUnconsumed) {
                var4_5[1] = var3_4 - (int)this.mTotalUnconsumed;
                this.mTotalUnconsumed = 0.0f;
            } else {
                this.mTotalUnconsumed -= (float)var3_4;
                var4_5[1] = var3_4;
            }
            this.moveSpinner(this.mTotalUnconsumed);
        }
        if (this.mUsingCustomStart && var3_4 > 0 && this.mTotalUnconsumed == 0.0f && Math.abs((int)(var3_4 - var4_5[1])) > 0) {
            this.mCircleView.setVisibility(8);
        }
        if (this.dispatchNestedPreScroll((int)(var2_3 - var4_5[0]), (int)(var3_4 - var4_5[1]), nArray2 = this.mParentScrollConsumed, null)) {
            var4_5[0] = var4_5[0] + nArray2[0];
            var4_5[1] = var4_5[1] + nArray2[1];
        }
    }

    @Override
    public void onNestedScroll(View view, int n2, int n3, int n4, int n5) {
        this.dispatchNestedScroll(n2, n3, n4, n5, this.mParentOffsetInWindow);
        n2 = n5 + this.mParentOffsetInWindow[1];
        if (n2 < 0 && !this.canChildScrollUp()) {
            this.mTotalUnconsumed += (float)Math.abs(n2);
            this.moveSpinner(this.mTotalUnconsumed);
        }
    }

    @Override
    public void onNestedScrollAccepted(View view, View view2, int n2) {
        this.mNestedScrollingParentHelper.onNestedScrollAccepted(view, view2, n2);
        this.startNestedScroll(n2 & 2);
        this.mTotalUnconsumed = 0.0f;
        this.mNestedScrollInProgress = true;
    }

    @Override
    public boolean onStartNestedScroll(View view, View view2, int n2) {
        return this.isEnabled() && !this.mReturningToStart && !this.mRefreshing && (n2 & 2) != 0;
    }

    @Override
    public void onStopNestedScroll(View view) {
        this.mNestedScrollingParentHelper.onStopNestedScroll(view);
        this.mNestedScrollInProgress = false;
        if (this.mTotalUnconsumed > 0.0f) {
            this.finishSpinner(this.mTotalUnconsumed);
            this.mTotalUnconsumed = 0.0f;
        }
        this.stopNestedScroll();
    }

    /*
     * Unable to fully structure code
     */
    public boolean onTouchEvent(MotionEvent var1_1) {
        var4_2 = MotionEventCompat.getActionMasked(var1_1);
        if (this.mReturningToStart && var4_2 == 0) {
            this.mReturningToStart = false;
        }
        if (!this.isEnabled() || this.mReturningToStart || this.canChildScrollUp() || this.mRefreshing || this.mNestedScrollInProgress) lbl-1000:
        // 3 sources

        {
            return false;
        }
        switch (var4_2) {
            case 3: {
                ** GOTO lbl-1000
            }
lbl10:
            // 6 sources

            default: {
                return true;
            }
            case 0: {
                this.mActivePointerId = var1_1.getPointerId(0);
                this.mIsBeingDragged = false;
                ** GOTO lbl10
            }
            case 2: {
                var4_2 = var1_1.findPointerIndex(this.mActivePointerId);
                if (var4_2 < 0) {
                    Log.e((String)SwipeRefreshLayout.LOG_TAG, (String)"Got ACTION_MOVE event but have an invalid active pointer id.");
                    return false;
                }
                var2_3 = var1_1.getY(var4_2);
                this.startDragging(var2_3);
                if (!this.mIsBeingDragged) ** GOTO lbl10
                if ((var2_3 = (var2_3 - this.mInitialMotionY) * 0.5f) > 0.0f) ** break;
                ** continue;
                this.moveSpinner(var2_3);
                ** GOTO lbl10
            }
            case 5: {
                var4_2 = MotionEventCompat.getActionIndex(var1_1);
                if (var4_2 < 0) {
                    Log.e((String)SwipeRefreshLayout.LOG_TAG, (String)"Got ACTION_POINTER_DOWN event but have an invalid action index.");
                    return false;
                }
                this.mActivePointerId = var1_1.getPointerId(var4_2);
                ** GOTO lbl10
            }
            case 6: {
                this.onSecondaryPointerUp(var1_1);
                ** GOTO lbl10
            }
            case 1: 
        }
        var4_2 = var1_1.findPointerIndex(this.mActivePointerId);
        if (var4_2 < 0) {
            Log.e((String)SwipeRefreshLayout.LOG_TAG, (String)"Got ACTION_UP event but don't have an active pointer id.");
            return false;
        }
        if (this.mIsBeingDragged) {
            var2_4 = var1_1.getY(var4_2);
            var3_5 = this.mInitialMotionY;
            this.mIsBeingDragged = false;
            this.finishSpinner((var2_4 - var3_5) * 0.5f);
        }
        this.mActivePointerId = -1;
        return false;
    }

    public void requestDisallowInterceptTouchEvent(boolean bl2) {
        if (Build.VERSION.SDK_INT < 21 && this.mTarget instanceof AbsListView || this.mTarget != null && !ViewCompat.isNestedScrollingEnabled(this.mTarget)) {
            return;
        }
        super.requestDisallowInterceptTouchEvent(bl2);
    }

    /*
     * Enabled aggressive block sorting
     */
    void reset() {
        this.mCircleView.clearAnimation();
        this.mProgress.stop();
        this.mCircleView.setVisibility(8);
        this.setColorViewAlpha(255);
        if (this.mScale) {
            this.setAnimationProgress(0.0f);
        } else {
            this.setTargetOffsetTopAndBottom(this.mOriginalOffsetTop - this.mCurrentTargetOffsetTop, true);
        }
        this.mCurrentTargetOffsetTop = this.mCircleView.getTop();
    }

    void setAnimationProgress(float f2) {
        if (this.isAlphaUsedForScale()) {
            this.setColorViewAlpha((int)(255.0f * f2));
            return;
        }
        ViewCompat.setScaleX((View)this.mCircleView, f2);
        ViewCompat.setScaleY((View)this.mCircleView, f2);
    }

    @Deprecated
    public void setColorScheme(int ... nArray) {
        this.setColorSchemeResources(nArray);
    }

    public void setColorSchemeColors(int ... nArray) {
        this.ensureTarget();
        this.mProgress.setColorSchemeColors(nArray);
    }

    public void setColorSchemeResources(int ... nArray) {
        Resources resources = this.getResources();
        int[] nArray2 = new int[nArray.length];
        for (int i2 = 0; i2 < nArray.length; ++i2) {
            nArray2[i2] = resources.getColor(nArray[i2]);
        }
        this.setColorSchemeColors(nArray2);
    }

    public void setDistanceToTriggerSync(int n2) {
        this.mTotalDragDistance = n2;
    }

    public void setEnabled(boolean bl2) {
        super.setEnabled(bl2);
        if (!bl2) {
            this.reset();
        }
    }

    @Override
    public void setNestedScrollingEnabled(boolean bl2) {
        this.mNestedScrollingChildHelper.setNestedScrollingEnabled(bl2);
    }

    public void setOnChildScrollUpCallback(@Nullable OnChildScrollUpCallback onChildScrollUpCallback) {
        this.mChildScrollUpCallback = onChildScrollUpCallback;
    }

    public void setOnRefreshListener(OnRefreshListener onRefreshListener) {
        this.mListener = onRefreshListener;
    }

    @Deprecated
    public void setProgressBackgroundColor(int n2) {
        this.setProgressBackgroundColorSchemeResource(n2);
    }

    public void setProgressBackgroundColorSchemeColor(@ColorInt int n2) {
        this.mCircleView.setBackgroundColor(n2);
        this.mProgress.setBackgroundColor(n2);
    }

    public void setProgressBackgroundColorSchemeResource(@ColorRes int n2) {
        this.setProgressBackgroundColorSchemeColor(this.getResources().getColor(n2));
    }

    public void setProgressViewEndTarget(boolean bl2, int n2) {
        this.mSpinnerFinalOffset = n2;
        this.mScale = bl2;
        this.mCircleView.invalidate();
    }

    public void setProgressViewOffset(boolean bl2, int n2, int n3) {
        this.mScale = bl2;
        this.mOriginalOffsetTop = n2;
        this.mSpinnerFinalOffset = n3;
        this.mUsingCustomStart = true;
        this.reset();
        this.mRefreshing = false;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setRefreshing(boolean bl2) {
        if (bl2 && this.mRefreshing != bl2) {
            this.mRefreshing = bl2;
            int n2 = !this.mUsingCustomStart ? (int)(this.mSpinnerFinalOffset + (float)this.mOriginalOffsetTop) : (int)this.mSpinnerFinalOffset;
            this.setTargetOffsetTopAndBottom(n2 - this.mCurrentTargetOffsetTop, true);
            this.mNotify = false;
            this.startScaleUpAnimation(this.mRefreshListener);
            return;
        }
        this.setRefreshing(bl2, false);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setSize(int n2) {
        if (n2 != 0 && n2 != 1) {
            return;
        }
        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        this.mCircleDiameter = n2 == 0 ? (int)(56.0f * displayMetrics.density) : (int)(40.0f * displayMetrics.density);
        this.mCircleView.setImageDrawable(null);
        this.mProgress.updateSizes(n2);
        this.mCircleView.setImageDrawable(this.mProgress);
    }

    void setTargetOffsetTopAndBottom(int n2, boolean bl2) {
        this.mCircleView.bringToFront();
        ViewCompat.offsetTopAndBottom((View)this.mCircleView, n2);
        this.mCurrentTargetOffsetTop = this.mCircleView.getTop();
        if (bl2 && Build.VERSION.SDK_INT < 11) {
            this.invalidate();
        }
    }

    @Override
    public boolean startNestedScroll(int n2) {
        return this.mNestedScrollingChildHelper.startNestedScroll(n2);
    }

    void startScaleDownAnimation(Animation.AnimationListener animationListener) {
        this.mScaleDownAnimation = new Animation(){

            public void applyTransformation(float f2, Transformation transformation) {
                SwipeRefreshLayout.this.setAnimationProgress(1.0f - f2);
            }
        };
        this.mScaleDownAnimation.setDuration(150L);
        this.mCircleView.setAnimationListener(animationListener);
        this.mCircleView.clearAnimation();
        this.mCircleView.startAnimation(this.mScaleDownAnimation);
    }

    @Override
    public void stopNestedScroll() {
        this.mNestedScrollingChildHelper.stopNestedScroll();
    }

    public static interface OnChildScrollUpCallback {
        public boolean canChildScrollUp(SwipeRefreshLayout var1, @Nullable View var2);
    }

    public static interface OnRefreshListener {
        public void onRefresh();
    }
}

