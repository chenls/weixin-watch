/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.animation.TimeInterpolator
 *  android.animation.ValueAnimator
 *  android.animation.ValueAnimator$AnimatorUpdateListener
 *  android.content.Context
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.util.AttributeSet
 *  android.util.Log
 *  android.util.TypedValue
 *  android.view.View
 *  android.view.View$BaseSavedState
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewGroup$MarginLayoutParams
 *  android.view.ViewOutlineProvider
 *  android.view.animation.AnimationUtils
 *  android.view.animation.Interpolator
 *  android.widget.LinearLayout
 *  android.widget.LinearLayout$LayoutParams
 */
package ticwear.design.widget;

import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.os.ParcelableCompat;
import android.support.v4.os.ParcelableCompatCreatorCallbacks;
import android.support.v4.view.OnApplyWindowInsetsListener;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.WindowInsetsCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import ticwear.design.DesignConfig;
import ticwear.design.R;
import ticwear.design.widget.AnimationUtils;
import ticwear.design.widget.CoordinatorLayout;
import ticwear.design.widget.HeaderBehavior;
import ticwear.design.widget.HeaderScrollingViewBehavior;
import ticwear.design.widget.MathUtils;
import ticwear.design.widget.ScrollViewFlingChecker;

@CoordinatorLayout.DefaultBehavior(value=Behavior.class)
public class AppBarLayout
extends LinearLayout {
    private static final int INVALID_SCROLL_RANGE = -1;
    private static final int INVALID_VIEW_HEIGHT = -1;
    private static final int PENDING_ACTION_ANIMATE_ENABLED = 4;
    private static final int PENDING_ACTION_COLLAPSED = 2;
    private static final int PENDING_ACTION_EXPANDED = 1;
    private static final int PENDING_ACTION_NONE = 0;
    private static final String TAG = "ABL";
    private int mDownPreScrollRange = -1;
    private int mDownScrollRange = -1;
    boolean mHaveChildWithInterpolator;
    boolean mHaveChildWithResistance;
    private WindowInsetsCompat mLastInsets;
    private final List<OnOffsetChangedListener> mListeners;
    private int mPendingAction = 0;
    private boolean mShouldConsumePreScroll = false;
    private float mTargetElevation;
    private int mTotalScrollRange = -1;

    public AppBarLayout(Context context) {
        this(context, null);
    }

    public AppBarLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.setOrientation(1);
        context = context.obtainStyledAttributes(attributeSet, R.styleable.AppBarLayout, 0, R.style.Widget_Ticwear_AppBarLayout);
        this.mTargetElevation = context.getDimensionPixelSize(R.styleable.AppBarLayout_android_elevation, 0);
        this.setBackgroundDrawable(context.getDrawable(R.styleable.AppBarLayout_android_background));
        if (context.hasValue(R.styleable.AppBarLayout_tic_expanded)) {
            this.setExpanded(context.getBoolean(R.styleable.AppBarLayout_tic_expanded, false));
        }
        context.recycle();
        this.setOutlineProvider(ViewOutlineProvider.BOUNDS);
        this.mListeners = new ArrayList<OnOffsetChangedListener>();
        ViewCompat.setElevation((View)this, this.mTargetElevation);
        ViewCompat.setOnApplyWindowInsetsListener((View)this, new OnApplyWindowInsetsListener(){

            @Override
            public WindowInsetsCompat onApplyWindowInsets(View object, WindowInsetsCompat windowInsetsCompat) {
                object = windowInsetsCompat;
                if (AppBarLayout.this.isShown()) {
                    AppBarLayout.this.setWindowInsets(windowInsetsCompat);
                    object = windowInsetsCompat.consumeSystemWindowInsets();
                }
                return object;
            }
        });
    }

    private int getDownNestedPreScrollRange() {
        return this.getDownNestedPreScrollRange(false);
    }

    /*
     * Enabled aggressive block sorting
     */
    private int getDownNestedPreScrollRange(boolean bl2) {
        if (bl2 != this.mShouldConsumePreScroll) {
            this.mDownPreScrollRange = -1;
            this.mShouldConsumePreScroll = bl2;
        }
        if (this.mDownPreScrollRange != -1) {
            return this.mDownPreScrollRange;
        }
        int n2 = 0;
        int n3 = this.getBottom();
        int n4 = 1;
        int n5 = this.getChildCount() - 1;
        while (true) {
            block9: {
                block10: {
                    int n6;
                    block7: {
                        block8: {
                            n6 = n4;
                            if (n5 < 0) break block7;
                            View view = this.getChildAt(n5);
                            LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
                            int n7 = layoutParams.mScrollFlags;
                            if (!bl2 && (n7 & 5) != 5) break block8;
                            n6 = this.getTop() + view.getTop() - layoutParams.topMargin;
                            n2 = (n7 & 8) != 0 ? (n2 += n3 - (this.getTop() + view.getBottom() - ViewCompat.getMinimumHeight(view))) : (n2 += n3 - n6);
                            n3 = n6;
                            break block9;
                        }
                        if (n2 <= 0) break block10;
                        n6 = 0;
                    }
                    n4 = n2;
                    if (n6 != 0) {
                        n4 = n2 + (n3 - this.getTop());
                    }
                    this.mDownPreScrollRange = n4;
                    return n4;
                }
                n4 = 0;
            }
            --n5;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private int getDownNestedScrollRange() {
        if (this.mDownScrollRange != -1) {
            return this.mDownScrollRange;
        }
        int n2 = 0;
        int n3 = this.getTop();
        int n4 = 1;
        int n5 = 0;
        int n6 = this.getChildCount();
        while (true) {
            block5: {
                int n7;
                int n8;
                int n9;
                block3: {
                    block4: {
                        n9 = n4;
                        n8 = n3;
                        n7 = n2;
                        if (n5 >= n6) break block3;
                        View view = this.getChildAt(n5);
                        LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
                        n7 = this.getTop() + view.getBottom();
                        n9 = layoutParams.mScrollFlags;
                        if ((n9 & 1) == 0) break block4;
                        n2 += n7 - n3;
                        n3 = n7;
                        if ((n9 & 2) == 0) break block5;
                        n7 = n2 - (ViewCompat.getMinimumHeight(view) + this.getTopInset());
                        n9 = 0;
                        n8 = n3;
                        break block3;
                    }
                    n9 = 0;
                    n8 = n3;
                    n7 = n2;
                }
                n3 = n7;
                if (n9 != 0) {
                    n3 = n7 + (this.getBottom() - n8);
                }
                this.mDownScrollRange = n3 = Math.max(0, n3);
                return n3;
            }
            ++n5;
        }
    }

    private int getPendingAction() {
        return this.mPendingAction;
    }

    private int getTopInset() {
        if (this.mLastInsets != null) {
            return this.mLastInsets.getSystemWindowInsetTop();
        }
        return 0;
    }

    private int getUpNestedPreScrollRange() {
        return this.getTotalScrollRange();
    }

    private boolean hasChildWithInterpolator() {
        return this.mHaveChildWithInterpolator;
    }

    private boolean hasScrollableChildren() {
        return this.getTotalScrollRange() != 0;
    }

    private void invalidateScrollRanges() {
        this.mTotalScrollRange = -1;
        this.mDownPreScrollRange = -1;
        this.mDownScrollRange = -1;
    }

    private static boolean overScrollBounceEnabled(LayoutParams layoutParams) {
        return (layoutParams.getScrollFlags() & 0x21) == 33;
    }

    private void resetPendingAction() {
        this.mPendingAction = 0;
    }

    private void setWindowInsets(WindowInsetsCompat windowInsetsCompat) {
        this.mTotalScrollRange = -1;
        this.mLastInsets = windowInsetsCompat;
        int n2 = 0;
        int n3 = this.getChildCount();
        while (n2 < n3 && !(windowInsetsCompat = ViewCompat.dispatchApplyWindowInsets(this.getChildAt(n2), windowInsetsCompat)).isConsumed()) {
            ++n2;
        }
        return;
    }

    public void addOnOffsetChangedListener(OnOffsetChangedListener onOffsetChangedListener) {
        if (onOffsetChangedListener != null && !this.mListeners.contains(onOffsetChangedListener)) {
            this.mListeners.add(onOffsetChangedListener);
        }
    }

    protected boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams;
    }

    protected LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-1, -2);
    }

    public LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(this.getContext(), attributeSet, this.isInEditMode());
    }

    protected LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        if (layoutParams instanceof LinearLayout.LayoutParams) {
            return new LayoutParams((LinearLayout.LayoutParams)layoutParams);
        }
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            return new LayoutParams((ViewGroup.MarginLayoutParams)layoutParams);
        }
        return new LayoutParams(layoutParams);
    }

    /*
     * Enabled aggressive block sorting
     */
    final int getMinimumHeightForVisibleOverlappingContent() {
        int n2 = 0;
        int n3 = this.mLastInsets != null ? this.mLastInsets.getSystemWindowInsetTop() : 0;
        int n4 = ViewCompat.getMinimumHeight((View)this);
        if (n4 != 0) {
            return n4 * 2 + n3;
        }
        n4 = this.getChildCount();
        if (n4 < 1) return n2;
        return ViewCompat.getMinimumHeight(this.getChildAt(n4 - 1)) * 2 + n3;
    }

    public float getTargetElevation() {
        return this.mTargetElevation;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final int getTotalScrollRange() {
        if (this.mTotalScrollRange != -1) {
            return this.mTotalScrollRange;
        }
        int n2 = 0;
        int n3 = this.getTop();
        int n4 = 1;
        int n5 = 0;
        int n6 = this.getChildCount();
        while (true) {
            block5: {
                int n7;
                int n8;
                int n9;
                block3: {
                    block4: {
                        n9 = n4;
                        n8 = n3;
                        n7 = n2;
                        if (n5 >= n6) break block3;
                        View view = this.getChildAt(n5);
                        LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
                        n7 = this.getTop() + view.getBottom();
                        n9 = layoutParams.mScrollFlags;
                        if ((n9 & 1) == 0) break block4;
                        n2 += n7 - n3;
                        n3 = n7;
                        if ((n9 & 2) == 0) break block5;
                        n7 = n2 - ViewCompat.getMinimumHeight(view);
                        n9 = 0;
                        n8 = n3;
                        break block3;
                    }
                    n9 = 0;
                    n8 = n3;
                    n7 = n2;
                }
                n3 = n7;
                if (n9 != 0) {
                    n3 = n7 + (this.getBottom() - n8);
                }
                this.mTotalScrollRange = n3 = Math.max(0, n3 - this.getTopInset());
                return n3;
            }
            ++n5;
        }
    }

    public boolean hasChildWithResistance() {
        return this.mHaveChildWithResistance;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void onLayout(boolean bl2, int n2, int n3, int n4, int n5) {
        super.onLayout(bl2, n2, n3, n4, n5);
        this.invalidateScrollRanges();
        this.mHaveChildWithInterpolator = false;
        this.mHaveChildWithResistance = false;
        n2 = 0;
        n3 = this.getChildCount();
        while (true) {
            block5: {
                block3: {
                    float f2;
                    LayoutParams layoutParams;
                    block4: {
                        if (n2 >= n3) break block3;
                        layoutParams = (LayoutParams)this.getChildAt(n2).getLayoutParams();
                        Interpolator interpolator2 = layoutParams.getScrollInterpolator();
                        f2 = layoutParams.getScrollResistanceFactor();
                        if (interpolator2 == null) break block4;
                        this.mHaveChildWithInterpolator = true;
                        if (this.mHaveChildWithResistance) break block3;
                    }
                    if (!(f2 > 0.0f) || !AppBarLayout.overScrollBounceEnabled(layoutParams)) break block5;
                    this.mHaveChildWithResistance = true;
                    if (!this.mHaveChildWithInterpolator) break block5;
                }
                return;
            }
            ++n2;
        }
    }

    protected void onMeasure(int n2, int n3) {
        super.onMeasure(n2, n3);
        this.invalidateScrollRanges();
    }

    public void removeOnOffsetChangedListener(OnOffsetChangedListener onOffsetChangedListener) {
        if (onOffsetChangedListener != null) {
            this.mListeners.remove(onOffsetChangedListener);
        }
    }

    public void setExpanded(boolean bl2) {
        this.setExpanded(bl2, ViewCompat.isLaidOut((View)this));
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setExpanded(boolean bl2, boolean bl3) {
        int n2 = bl2 ? 1 : 2;
        int n3 = bl3 ? 4 : 0;
        this.mPendingAction = n3 | n2;
        this.requestLayout();
    }

    public void setOrientation(int n2) {
        if (n2 != 1) {
            throw new IllegalArgumentException("AppBarLayout is always vertical and does not support horizontal orientation");
        }
        super.setOrientation(n2);
    }

    public void setTargetElevation(float f2) {
        this.mTargetElevation = f2;
    }

    public static class Behavior
    extends HeaderBehavior<AppBarLayout> {
        private static final int ANIMATE_OFFSET_DIPS_PER_SECOND = 300;
        private static final int INVALID_POSITION = -1;
        private ValueAnimator mAnimator;
        private ScrollViewFlingChecker mAppBarFlingChecker;
        private WeakReference<View> mLastNestedScrollingChildRef;
        private int mOffsetDelta;
        private int mOffsetToChildIndexOnLayout = -1;
        private boolean mOffsetToChildIndexOnLayoutIsMinHeight;
        private float mOffsetToChildIndexOnLayoutPerc;
        private DragCallback mOnDragCallback;
        private int mOverScrollDelta;
        int mOverScrollOriginalHeight = -1;
        private boolean mSiblingOverScrollDown;
        private boolean mSkipNestedPreScroll;
        private boolean mWasFlung;

        public Behavior() {
        }

        public Behavior(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        private void animateOffsetTo(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, int n2) {
            this.animateOffsetTo(coordinatorLayout, appBarLayout, this.getTopBottomOffsetForScrollingSibling(), n2);
        }

        /*
         * Enabled aggressive block sorting
         */
        private void animateOffsetTo(final CoordinatorLayout coordinatorLayout, final AppBarLayout appBarLayout, int n2, int n3) {
            if (n2 == n3) {
                if (this.mAnimator != null && this.mAnimator.isRunning()) {
                    this.mAnimator.cancel();
                }
                return;
            }
            if (this.mAnimator == null) {
                this.mAnimator = new ValueAnimator();
                this.mAnimator.setInterpolator((TimeInterpolator)AnimationUtils.DECELERATE_INTERPOLATOR);
                this.mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(){

                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        Behavior.this.setHeaderTopBottomOffset(coordinatorLayout, appBarLayout, (Integer)valueAnimator.getAnimatedValue());
                    }
                });
            } else {
                this.mAnimator.cancel();
            }
            float f2 = (float)Math.abs(n2 - n3) / coordinatorLayout.getResources().getDisplayMetrics().density;
            this.mAnimator.setDuration((long)Math.round(1000.0f * f2 / 300.0f));
            this.mAnimator.setIntValues(new int[]{n2, n3});
            this.mAnimator.start();
        }

        private boolean consumePreScroll(CoordinatorLayout coordinatorLayout, View object) {
            if ((object = object.getLayoutParams()) instanceof CoordinatorLayout.LayoutParams && (object = ((CoordinatorLayout.LayoutParams)((Object)object)).getBehavior()) != null) {
                return ((CoordinatorLayout.Behavior)object).requestInterceptPreScroll(coordinatorLayout);
            }
            return false;
        }

        private void dispatchOffsetUpdates(AppBarLayout appBarLayout) {
            List list = appBarLayout.mListeners;
            int n2 = list.size();
            for (int i2 = 0; i2 < n2; ++i2) {
                OnOffsetChangedListener onOffsetChangedListener = (OnOffsetChangedListener)list.get(i2);
                if (onOffsetChangedListener == null) continue;
                onOffsetChangedListener.onOffsetChanged(appBarLayout, this.getTopAndBottomOffset());
            }
        }

        private View getChildOnOffset(AppBarLayout appBarLayout, int n2) {
            int n3 = appBarLayout.getChildCount();
            for (int i2 = 0; i2 < n3; ++i2) {
                View view = appBarLayout.getChildAt(i2);
                if (view.getTop() > -n2 || view.getBottom() < -n2) continue;
                return view;
            }
            return null;
        }

        private int interpolateOffset(AppBarLayout appBarLayout, int n2) {
            int n3 = Math.abs(n2);
            int n4 = 0;
            int n5 = appBarLayout.getChildCount();
            while (true) {
                block10: {
                    int n6;
                    block9: {
                        n6 = n2;
                        if (n4 >= n5) break block9;
                        View view = appBarLayout.getChildAt(n4);
                        LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
                        Interpolator interpolator2 = layoutParams.getScrollInterpolator();
                        if (n3 < view.getTop() || n3 > view.getBottom()) break block10;
                        n6 = n2;
                        if (interpolator2 != null) {
                            n6 = 0;
                            n5 = layoutParams.getScrollFlags();
                            if ((n5 & 1) != 0) {
                                n6 = n4 = 0 + (view.getHeight() + layoutParams.topMargin + layoutParams.bottomMargin);
                                if ((n5 & 2) != 0) {
                                    n6 = n4 - ViewCompat.getMinimumHeight(view);
                                }
                            }
                            n4 = n6;
                            if (ViewCompat.getFitsSystemWindows(view)) {
                                n4 = n6 - appBarLayout.getTopInset();
                            }
                            n6 = n2;
                            if (n4 > 0) {
                                n6 = view.getTop();
                                n6 = Math.round((float)n4 * interpolator2.getInterpolation((float)(n3 - n6) / (float)n4));
                                n6 = Integer.signum(n2) * (view.getTop() + n6);
                            }
                        }
                    }
                    return n6;
                }
                ++n4;
            }
        }

        private int resistanceSizeChange(AppBarLayout appBarLayout, int n2) {
            int n3;
            float f2;
            LayoutParams layoutParams;
            int n4;
            if (n2 <= 0) {
                return n2;
            }
            float f3 = 0.0f;
            float f4 = 0.0f;
            int n5 = 0;
            int n6 = appBarLayout.getChildCount();
            for (n4 = 0; n4 < n6; ++n4) {
                layoutParams = (LayoutParams)appBarLayout.getChildAt(n4).getLayoutParams();
                f2 = layoutParams.getScrollResistanceFactor();
                n3 = layoutParams.getScrollOffsetLimit();
                f4 += f2;
                f3 = Math.max(f2, f3);
                n5 = Math.max(n3, n5);
            }
            n6 = Math.min(n5, n2);
            n4 = 0;
            n3 = appBarLayout.getChildCount();
            for (n2 = 0; n2 < n3; ++n2) {
                layoutParams = appBarLayout.getChildAt(n2);
                LayoutParams layoutParams2 = (LayoutParams)layoutParams.getLayoutParams();
                n5 = n4;
                if (AppBarLayout.overScrollBounceEnabled(layoutParams2)) {
                    f2 = f3 * layoutParams2.getScrollResistanceFactor() / f4;
                    int n7 = (int)((float)n6 * f2);
                    n5 = n4 + n7;
                    layoutParams2.height = layoutParams2.mOverScrollOriginalHeight + n7;
                    layoutParams.setLayoutParams((ViewGroup.LayoutParams)layoutParams2);
                }
                n4 = n5;
            }
            layoutParams = appBarLayout.getLayoutParams();
            ((ViewGroup.LayoutParams)layoutParams).height = this.mOverScrollOriginalHeight + n4;
            appBarLayout.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
            return n4;
        }

        /*
         * Enabled aggressive block sorting
         */
        private void snapToChildIfNeeded(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout) {
            LayoutParams layoutParams;
            int n2 = this.getTopBottomOffsetForScrollingSibling();
            View view = this.getChildOnOffset(appBarLayout, n2);
            if (view != null && ((layoutParams = (LayoutParams)view.getLayoutParams()).getScrollFlags() & 0x11) == 17) {
                int n3;
                int n4 = -view.getTop();
                int n5 = n3 = -view.getBottom();
                if ((layoutParams.getScrollFlags() & 2) == 2) {
                    n5 = n3 + ViewCompat.getMinimumHeight(view);
                }
                if (n2 >= (n5 + n4) / 2) {
                    n5 = n4;
                }
                this.animateOffsetTo(coordinatorLayout, appBarLayout, MathUtils.constrain(n5, -appBarLayout.getTotalScrollRange(), 0));
            }
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        boolean canDragView(AppBarLayout appBarLayout) {
            boolean bl2 = true;
            if (this.mOnDragCallback != null) {
                return this.mOnDragCallback.canDrag(appBarLayout);
            }
            boolean bl3 = bl2;
            if (this.mLastNestedScrollingChildRef == null) return bl3;
            appBarLayout = (View)this.mLastNestedScrollingChildRef.get();
            if (appBarLayout == null) return false;
            if (!appBarLayout.isShown()) return false;
            bl3 = bl2;
            if (!ViewCompat.canScrollVertically((View)appBarLayout, -1)) return bl3;
            return false;
        }

        @Override
        int getMaxDragOffset(AppBarLayout appBarLayout) {
            return -appBarLayout.getDownNestedScrollRange();
        }

        @Override
        int getOverScrollOffset() {
            return this.mOverScrollDelta;
        }

        @Override
        int getScrollRangeForDragFling(AppBarLayout appBarLayout) {
            return appBarLayout.getTotalScrollRange();
        }

        @Override
        int getTopBottomOffsetForScrollingSibling() {
            return this.getTopAndBottomOffset() + this.mOffsetDelta;
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public boolean onLayoutChild(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, int n2) {
            boolean bl2;
            block6: {
                block7: {
                    int n3;
                    block8: {
                        bl2 = super.onLayoutChild(coordinatorLayout, appBarLayout, n2);
                        n3 = appBarLayout.getPendingAction();
                        if (n3 == 0) break block7;
                        n2 = (n3 & 4) != 0 ? 1 : 0;
                        if ((n3 & 2) == 0) break block8;
                        n3 = -appBarLayout.getUpNestedPreScrollRange();
                        if (n2 != 0) {
                            this.animateOffsetTo(coordinatorLayout, appBarLayout, n3);
                            break block6;
                        } else {
                            this.setHeaderTopBottomOffset(coordinatorLayout, appBarLayout, n3);
                        }
                        break block6;
                    }
                    if ((n3 & 1) != 0) {
                        if (n2 != 0) {
                            this.animateOffsetTo(coordinatorLayout, appBarLayout, 0);
                            break block6;
                        } else {
                            this.setHeaderTopBottomOffset(coordinatorLayout, appBarLayout, 0);
                        }
                    }
                    break block6;
                }
                if (this.mOffsetToChildIndexOnLayout >= 0) {
                    coordinatorLayout = appBarLayout.getChildAt(this.mOffsetToChildIndexOnLayout);
                    n2 = -coordinatorLayout.getBottom();
                    n2 = this.mOffsetToChildIndexOnLayoutIsMinHeight ? (n2 += ViewCompat.getMinimumHeight((View)coordinatorLayout)) : (n2 += Math.round((float)coordinatorLayout.getHeight() * this.mOffsetToChildIndexOnLayoutPerc));
                    this.setTopAndBottomOffset(n2);
                }
            }
            appBarLayout.resetPendingAction();
            this.mOffsetToChildIndexOnLayout = -1;
            this.dispatchOffsetUpdates(appBarLayout);
            return bl2;
        }

        @Override
        public boolean onNestedFling(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, View view, float f2, float f3, boolean bl2) {
            block3: {
                block2: {
                    boolean bl3 = false;
                    if (!bl2) break block2;
                    bl2 = bl3;
                    if (!this.consumePreScroll(coordinatorLayout, view)) break block3;
                }
                bl2 = this.fling(coordinatorLayout, appBarLayout, -appBarLayout.getTotalScrollRange(), 0, -f3);
            }
            this.mWasFlung = bl2;
            return bl2;
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public boolean onNestedPreFling(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, View view, float f2, float f3) {
            if (f3 < 0.0f) {
                int n2 = -appBarLayout.getTotalScrollRange() + appBarLayout.getDownNestedPreScrollRange(this.consumePreScroll(coordinatorLayout, view));
                if (this.getTopBottomOffsetForScrollingSibling() >= n2) return false;
                this.animateOffsetTo(coordinatorLayout, appBarLayout, n2);
                return false;
            }
            int n3 = -appBarLayout.getUpNestedPreScrollRange();
            if (this.getTopBottomOffsetForScrollingSibling() <= n3) return false;
            this.animateOffsetTo(coordinatorLayout, appBarLayout, n3);
            return false;
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, View view, int n2, int n3, int[] nArray) {
            n2 = this.mSkipNestedPreScroll && n3 < 0 ? 1 : 0;
            if (n3 != 0 && n2 == 0) {
                int n4;
                if (n3 < 0) {
                    n2 = -appBarLayout.getTotalScrollRange();
                    n4 = n2 + appBarLayout.getDownNestedPreScrollRange(this.consumePreScroll(coordinatorLayout, view));
                } else {
                    n2 = -appBarLayout.getUpNestedPreScrollRange();
                    n4 = 0;
                }
                nArray[1] = this.scroll(coordinatorLayout, appBarLayout, n3, n2, n4);
            }
        }

        @Override
        public void onNestedScroll(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, View view, int n2, int n3, int n4, int n5, int[] nArray) {
            if (n5 < 0) {
                this.mSiblingOverScrollDown = true;
                nArray[1] = this.scroll(coordinatorLayout, appBarLayout, n5, -appBarLayout.getDownNestedScrollRange(), 0);
                this.mSkipNestedPreScroll = true;
                return;
            }
            this.mSkipNestedPreScroll = false;
        }

        @Override
        public void onRestoreInstanceState(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, Parcelable object) {
            if (object instanceof SavedState) {
                object = (SavedState)((Object)object);
                super.onRestoreInstanceState(coordinatorLayout, appBarLayout, object.getSuperState());
                this.mOffsetToChildIndexOnLayout = object.firstVisibleChildIndex;
                this.mOffsetToChildIndexOnLayoutPerc = object.firstVisibileChildPercentageShown;
                this.mOffsetToChildIndexOnLayoutIsMinHeight = object.firstVisibileChildAtMinimumHeight;
                return;
            }
            super.onRestoreInstanceState(coordinatorLayout, appBarLayout, (Parcelable)object);
            this.mOffsetToChildIndexOnLayout = -1;
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public Parcelable onSaveInstanceState(CoordinatorLayout coordinatorLayout, AppBarLayout object) {
            Parcelable parcelable = super.onSaveInstanceState(coordinatorLayout, object);
            int n2 = this.getTopAndBottomOffset();
            int n3 = 0;
            int n4 = object.getChildCount();
            while (n3 < n4) {
                coordinatorLayout = object.getChildAt(n3);
                int n5 = coordinatorLayout.getBottom() + n2;
                if (coordinatorLayout.getTop() + n2 <= 0 && n5 >= 0) {
                    SavedState savedState = new SavedState(parcelable);
                    savedState.firstVisibleChildIndex = n3;
                    boolean bl2 = n5 == ViewCompat.getMinimumHeight((View)coordinatorLayout);
                    savedState.firstVisibileChildAtMinimumHeight = bl2;
                    savedState.firstVisibileChildPercentageShown = (float)n5 / (float)coordinatorLayout.getHeight();
                    return savedState;
                }
                ++n3;
            }
            return parcelable;
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, View object, View view, int n2) {
            int n3;
            boolean bl2 = (n3 & 2) != 0 && appBarLayout.hasScrollableChildren() && coordinatorLayout.getHeight() - object.getHeight() <= appBarLayout.getHeight();
            if (bl2 && this.mAnimator != null) {
                this.mAnimator.cancel();
            } else {
                int n4 = appBarLayout.getChildCount();
                for (n3 = 0; n3 < n4; ++n3) {
                    coordinatorLayout = appBarLayout.getChildAt(n3);
                    LayoutParams layoutParams = (LayoutParams)coordinatorLayout.getLayoutParams();
                    if (!AppBarLayout.overScrollBounceEnabled(layoutParams) || layoutParams.mOverScrollOriginalHeight != -1) continue;
                    layoutParams.mOverScrollOriginalHeight = coordinatorLayout.getMeasuredHeight();
                }
                if (appBarLayout.hasChildWithResistance() && this.mOverScrollOriginalHeight == -1) {
                    this.mOverScrollOriginalHeight = appBarLayout.getMeasuredHeight();
                }
            }
            this.mLastNestedScrollingChildRef = null;
            this.mSiblingOverScrollDown = false;
            this.mOverScrollDelta = 0;
            return bl2;
        }

        @Override
        public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, View view) {
            if (!this.snapToZeroOffsetIfNeeded(coordinatorLayout, appBarLayout) && !this.mWasFlung) {
                this.snapToChildIfNeeded(coordinatorLayout, appBarLayout);
            }
            this.mSkipNestedPreScroll = false;
            this.mWasFlung = false;
            this.mLastNestedScrollingChildRef = new WeakReference<View>(view);
        }

        public void setDragCallback(@Nullable DragCallback dragCallback) {
            this.mOnDragCallback = dragCallback;
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        int setHeaderTopBottomOffset(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, int n2, int n3, int n4) {
            boolean bl2 = true;
            int n5 = this.getTopBottomOffsetForScrollingSibling();
            int n6 = 0;
            int n7 = 0;
            int n8 = MathUtils.constrain(n2, n3, n4);
            boolean bl3 = n2 > 0 && n8 >= 0;
            if (bl3 = bl3 && this.mSiblingOverScrollDown && appBarLayout.hasChildWithResistance() ? bl2 : false) {
                this.resistanceSizeChange(appBarLayout, n2);
                n3 = -(n2 - this.mOverScrollDelta);
                this.mOverScrollDelta = n2 - n8;
                return n3;
            }
            n2 = n7;
            if (n3 == 0) return n2;
            n2 = n7;
            if (n5 < n3) return n2;
            n2 = n7;
            if (n5 > n4) return n2;
            n2 = n6;
            if (n5 != n8) {
                n2 = appBarLayout.hasChildWithInterpolator() ? this.interpolateOffset(appBarLayout, n8) : n8;
                boolean bl4 = this.setTopAndBottomOffset(n2);
                n3 = n5 - n8;
                this.mOffsetDelta = n8 - n2;
                if (!bl4 && appBarLayout.hasChildWithInterpolator()) {
                    coordinatorLayout.dispatchDependentViewsChanged((View)appBarLayout);
                }
                this.dispatchOffsetUpdates(appBarLayout);
                n2 = n3;
            }
            this.mOverScrollDelta = 0;
            return n2;
        }

        @Override
        boolean snapToZeroOffsetIfNeeded(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout) {
            boolean bl2 = false;
            if (this.needSnapToZero()) {
                int n2;
                int n3 = n2 = 0;
                if (appBarLayout.hasChildWithResistance()) {
                    n3 = n2;
                    if (this.mOverScrollOriginalHeight != -1) {
                        n3 = appBarLayout.getMeasuredHeight() - this.mOverScrollOriginalHeight;
                    }
                }
                this.animateOffsetTo(coordinatorLayout, appBarLayout, n3, 0);
                bl2 = true;
            }
            return bl2;
        }

        public static abstract class DragCallback {
            public abstract boolean canDrag(@NonNull AppBarLayout var1);
        }

        protected static class SavedState
        extends View.BaseSavedState {
            public static final Parcelable.Creator<SavedState> CREATOR = ParcelableCompat.newCreator(new ParcelableCompatCreatorCallbacks<SavedState>(){

                @Override
                public SavedState createFromParcel(Parcel parcel, ClassLoader classLoader) {
                    return new SavedState(parcel, classLoader);
                }

                public SavedState[] newArray(int n2) {
                    return new SavedState[n2];
                }
            });
            boolean firstVisibileChildAtMinimumHeight;
            float firstVisibileChildPercentageShown;
            int firstVisibleChildIndex;

            /*
             * Enabled aggressive block sorting
             */
            public SavedState(Parcel parcel, ClassLoader classLoader) {
                super(parcel);
                this.firstVisibleChildIndex = parcel.readInt();
                this.firstVisibileChildPercentageShown = parcel.readFloat();
                boolean bl2 = parcel.readByte() != 0;
                this.firstVisibileChildAtMinimumHeight = bl2;
            }

            public SavedState(Parcelable parcelable) {
                super(parcelable);
            }

            /*
             * Enabled aggressive block sorting
             */
            public void writeToParcel(Parcel parcel, int n2) {
                super.writeToParcel(parcel, n2);
                parcel.writeInt(this.firstVisibleChildIndex);
                parcel.writeFloat(this.firstVisibileChildPercentageShown);
                n2 = this.firstVisibileChildAtMinimumHeight ? 1 : 0;
                parcel.writeByte((byte)n2);
            }
        }
    }

    public static class LayoutParams
    extends LinearLayout.LayoutParams {
        static final int FLAG_OVERSCROLL = 33;
        static final int FLAG_QUICK_RETURN = 5;
        static final int FLAG_SNAP = 17;
        public static final int SCROLL_FLAG_ENTER_ALWAYS = 4;
        public static final int SCROLL_FLAG_ENTER_ALWAYS_COLLAPSED = 8;
        public static final int SCROLL_FLAG_EXIT_UNTIL_COLLAPSED = 2;
        public static final int SCROLL_FLAG_OVER_SCROLL_BOUNCE = 32;
        public static final int SCROLL_FLAG_SCROLL = 1;
        public static final int SCROLL_FLAG_SNAP = 16;
        int mOverScrollOriginalHeight = -1;
        int mScrollFlags = 1;
        Interpolator mScrollInterpolator;
        int mScrollOffsetLimit;
        float mScrollResistanceFactor;

        public LayoutParams(int n2, int n3) {
            super(n2, n3);
        }

        public LayoutParams(int n2, int n3, float f2) {
            super(n2, n3, f2);
        }

        /*
         * Enabled aggressive block sorting
         */
        public LayoutParams(Context context, AttributeSet attributeSet, boolean bl2) {
            super(context, attributeSet);
            int n2;
            float f2;
            attributeSet = context.obtainStyledAttributes(attributeSet, R.styleable.AppBarLayout_LayoutParams);
            this.mScrollFlags = attributeSet.getInt(R.styleable.AppBarLayout_LayoutParams_tic_layout_scrollFlags, 0);
            if (attributeSet.hasValue(R.styleable.AppBarLayout_LayoutParams_tic_layout_scrollInterpolator)) {
                this.mScrollInterpolator = android.view.animation.AnimationUtils.loadInterpolator((Context)context, (int)attributeSet.getResourceId(R.styleable.AppBarLayout_LayoutParams_tic_layout_scrollInterpolator, 0));
            }
            if (bl2) {
                f2 = 0.0f;
                n2 = Integer.MAX_VALUE;
            } else {
                TypedValue typedValue = new TypedValue();
                context.getResources().getValue(R.integer.design_factor_over_scroll_bounce, typedValue, true);
                f2 = typedValue.getFloat();
                n2 = context.getResources().getDimensionPixelOffset(R.dimen.design_over_scroll_limit);
            }
            this.mScrollResistanceFactor = MathUtils.constrain(attributeSet.getFloat(R.styleable.AppBarLayout_LayoutParams_tic_layout_scrollResistanceFactor, f2), 0.0f, 1.0f);
            this.mScrollOffsetLimit = attributeSet.getDimensionPixelOffset(R.styleable.AppBarLayout_LayoutParams_tic_layout_overScrollLimit, n2);
            attributeSet.recycle();
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public LayoutParams(ViewGroup.MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
        }

        public LayoutParams(LinearLayout.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public LayoutParams(LayoutParams layoutParams) {
            super((LinearLayout.LayoutParams)layoutParams);
            this.mScrollFlags = layoutParams.mScrollFlags;
            this.mScrollInterpolator = layoutParams.mScrollInterpolator;
            this.mScrollResistanceFactor = layoutParams.mScrollResistanceFactor;
            this.mScrollOffsetLimit = layoutParams.mScrollOffsetLimit;
        }

        public int getScrollFlags() {
            return this.mScrollFlags;
        }

        public Interpolator getScrollInterpolator() {
            return this.mScrollInterpolator;
        }

        public int getScrollOffsetLimit() {
            return this.mScrollOffsetLimit;
        }

        public float getScrollResistanceFactor() {
            return this.mScrollResistanceFactor;
        }

        public void setScrollFlags(int n2) {
            this.mScrollFlags = n2;
        }

        public void setScrollInterpolator(Interpolator interpolator2) {
            this.mScrollInterpolator = interpolator2;
        }

        public void setScrollOffsetLimit(int n2) {
            this.mScrollOffsetLimit = MathUtils.constrain(n2, 0, 1);
        }

        public void setScrollResistanceFactor(float f2) {
            this.mScrollResistanceFactor = MathUtils.constrain(f2, 0.0f, 1.0f);
        }

        @Retention(value=RetentionPolicy.SOURCE)
        public static @interface ScrollFlags {
        }
    }

    public static interface OnOffsetChangedListener {
        public void onOffsetChanged(AppBarLayout var1, int var2);
    }

    public static class ScrollingViewBehavior
    extends HeaderScrollingViewBehavior {
        private static final int INVALID_PADDING = Integer.MIN_VALUE;
        private static final String TAG = "ABL.ScrollingVB";
        private int mAdditionalOffset;
        private int mOriginalPaddingBottom;
        private int mOriginalPaddingTop = Integer.MIN_VALUE;
        private int mOverlayTop;
        private View mScrollingView;

        public ScrollingViewBehavior() {
            this.mOriginalPaddingBottom = Integer.MIN_VALUE;
        }

        public ScrollingViewBehavior(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            this.mOriginalPaddingBottom = Integer.MIN_VALUE;
            context = context.obtainStyledAttributes(attributeSet, R.styleable.ScrollingViewBehavior_Params);
            this.mOverlayTop = context.getDimensionPixelSize(R.styleable.ScrollingViewBehavior_Params_tic_behavior_overlapTop, 0);
            context.recycle();
        }

        /*
         * Enabled aggressive block sorting
         */
        private View findScrollingView(View object) {
            if (object.canScrollVertically(1) || object.canScrollVertically(-1)) {
                return object;
            }
            if (object instanceof ViewGroup) {
                View view;
                ViewGroup viewGroup = (ViewGroup)object;
                object = null;
                ArrayList<View> arrayList = new ArrayList<View>(1);
                arrayList.clear();
                int n2 = viewGroup.getChildCount();
                for (int i2 = 0; i2 < n2; ++i2) {
                    view = viewGroup.getChildAt(i2);
                    if (!view.isShown()) continue;
                    arrayList.add(view);
                    if (object != null && view.getHeight() < object.getHeight()) {
                        arrayList.remove(view);
                        continue;
                    }
                    object = view;
                }
                object = arrayList.iterator();
                while (object.hasNext()) {
                    view = this.findScrollingView((View)object.next());
                    if (view == null) continue;
                    return view;
                }
            }
            return null;
        }

        private int getOverlapForOffset(View object, int n2) {
            if (this.mOverlayTop != 0 && object instanceof AppBarLayout) {
                object = (AppBarLayout)((Object)object);
                int n3 = ((AppBarLayout)((Object)object)).getTotalScrollRange();
                int n4 = ((AppBarLayout)((Object)object)).getDownNestedPreScrollRange();
                if (n4 != 0 && n3 + n2 <= n4) {
                    return 0;
                }
                if ((n3 -= n4) != 0) {
                    return MathUtils.constrain(Math.round((1.0f + (float)n2 / (float)n3) * (float)this.mOverlayTop), 0, this.mOverlayTop);
                }
            }
            return this.mOverlayTop;
        }

        /*
         * Enabled aggressive block sorting
         */
        private void updateChildOffset(CoordinatorLayout coordinatorLayout, View view, int n2) {
            this.setTopAndBottomOffset(n2);
            if (n2 != 0) {
                int n3;
                int n4;
                int n5 = view.getPaddingLeft();
                int n6 = view.getPaddingTop();
                int n7 = view.getPaddingRight();
                int n8 = view.getPaddingBottom();
                boolean bl2 = this.mScrollingView != null && this.mScrollingView.canScrollVertically(n2);
                boolean bl3 = view.getBottom() > coordinatorLayout.getHeight() || view.getTop() < 0;
                boolean bl4 = n6 != this.mOriginalPaddingTop || n8 != this.mOriginalPaddingBottom;
                boolean bl5 = bl4 || !bl2 && bl3;
                if (DesignConfig.DEBUG_COORDINATOR) {
                    Log.v((String)TAG, (String)("update child offset to " + n2 + ", child canScroll " + bl2 + ", overScreen " + bl3 + ", alreadyAdd " + bl4 + ", needAdd " + bl5 + ", child " + view));
                }
                if (bl5 && n2 < 0) {
                    if (this.mOriginalPaddingTop == Integer.MIN_VALUE) {
                        n4 = -n2;
                        n3 = n8;
                    } else {
                        n4 = this.mOriginalPaddingTop - n2;
                        n3 = n8;
                    }
                } else {
                    n3 = n8;
                    n4 = n6;
                    if (bl5) {
                        n3 = n8;
                        n4 = n6;
                        if (n2 > 0) {
                            if (this.mOriginalPaddingBottom != Integer.MIN_VALUE) {
                                n2 = this.mOriginalPaddingBottom + n2;
                            }
                            n3 = n2;
                            n4 = n6;
                        }
                    }
                }
                if (bl5) {
                    view.setPadding(n5, n4, n7, n3);
                }
            }
        }

        private void updateOffset(CoordinatorLayout coordinatorLayout, View view) {
            List<View> list = coordinatorLayout.getDependencies(view);
            boolean bl2 = false;
            int n2 = 0;
            int n3 = list.size();
            while (true) {
                block6: {
                    boolean bl3;
                    block5: {
                        bl3 = bl2;
                        if (n2 >= n3) break block5;
                        if (!this.updateOffset(coordinatorLayout, view, list.get(n2))) break block6;
                        bl3 = true;
                    }
                    if (!bl3) {
                        this.updateChildOffset(coordinatorLayout, view, this.mAdditionalOffset);
                    }
                    return;
                }
                ++n2;
            }
        }

        /*
         * Enabled aggressive block sorting
         */
        private boolean updateOffset(CoordinatorLayout coordinatorLayout, View view, View view2) {
            CoordinatorLayout.Behavior behavior = ((CoordinatorLayout.LayoutParams)view2.getLayoutParams()).getBehavior();
            if (!(behavior instanceof Behavior)) {
                return false;
            }
            int n2 = ((Behavior)behavior).getTopBottomOffsetForScrollingSibling();
            int n3 = view2.getHeight();
            int n4 = this.getOverlapForOffset(view2, n2);
            n2 = view2.getVisibility() == 8 ? this.mAdditionalOffset : n3 + n2 - n4 + this.mAdditionalOffset;
            this.updateChildOffset(coordinatorLayout, view, n2);
            return true;
        }

        @Override
        View findFirstDependency(List<View> list) {
            int n2 = list.size();
            for (int i2 = 0; i2 < n2; ++i2) {
                View view = list.get(i2);
                if (!(view instanceof AppBarLayout)) continue;
                return view;
            }
            return null;
        }

        public int getOverlayTop() {
            return this.mOverlayTop;
        }

        @Override
        int getScrollRange(View view) {
            if (view instanceof AppBarLayout) {
                return ((AppBarLayout)view).getTotalScrollRange();
            }
            return super.getScrollRange(view);
        }

        public View getScrollingView() {
            return this.mScrollingView;
        }

        @Override
        public boolean layoutDependsOn(CoordinatorLayout coordinatorLayout, View view, View view2) {
            return view2 instanceof AppBarLayout;
        }

        @Override
        public boolean onDependentViewChanged(CoordinatorLayout coordinatorLayout, View view, View view2) {
            this.updateOffset(coordinatorLayout, view, view2);
            return false;
        }

        @Override
        public boolean onLayoutChild(CoordinatorLayout coordinatorLayout, View view, int n2) {
            super.onLayoutChild(coordinatorLayout, view, n2);
            if (this.mScrollingView == null) {
                this.mScrollingView = this.findScrollingView(view);
            }
            if (this.mOriginalPaddingTop == Integer.MIN_VALUE) {
                this.mOriginalPaddingTop = view.getPaddingTop();
            }
            if (this.mOriginalPaddingBottom == Integer.MIN_VALUE) {
                this.mOriginalPaddingBottom = view.getPaddingBottom();
            }
            this.updateOffset(coordinatorLayout, view);
            return true;
        }

        public void setOverlayTop(int n2) {
            this.mOverlayTop = n2;
        }

        void setScrollOffset(CoordinatorLayout coordinatorLayout, View view, int n2) {
            this.mAdditionalOffset = n2;
            this.updateOffset(coordinatorLayout, view);
        }
    }
}

