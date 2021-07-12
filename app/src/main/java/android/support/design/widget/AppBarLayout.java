/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.View$BaseSavedState
 *  android.view.View$MeasureSpec
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewGroup$MarginLayoutParams
 *  android.view.animation.AnimationUtils
 *  android.view.animation.Interpolator
 *  android.widget.LinearLayout
 *  android.widget.LinearLayout$LayoutParams
 */
package android.support.design.widget;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.R;
import android.support.design.widget.AnimationUtils;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.HeaderBehavior;
import android.support.design.widget.HeaderScrollingViewBehavior;
import android.support.design.widget.MathUtils;
import android.support.design.widget.ThemeUtils;
import android.support.design.widget.ValueAnimatorCompat;
import android.support.design.widget.ViewUtils;
import android.support.v4.os.ParcelableCompat;
import android.support.v4.os.ParcelableCompatCreatorCallbacks;
import android.support.v4.view.OnApplyWindowInsetsListener;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.WindowInsetsCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

@CoordinatorLayout.DefaultBehavior(value=Behavior.class)
public class AppBarLayout
extends LinearLayout {
    private static final int INVALID_SCROLL_RANGE = -1;
    private static final int PENDING_ACTION_ANIMATE_ENABLED = 4;
    private static final int PENDING_ACTION_COLLAPSED = 2;
    private static final int PENDING_ACTION_EXPANDED = 1;
    private static final int PENDING_ACTION_NONE = 0;
    private int mDownPreScrollRange = -1;
    private int mDownScrollRange = -1;
    boolean mHaveChildWithInterpolator;
    private WindowInsetsCompat mLastInsets;
    private final List<OnOffsetChangedListener> mListeners;
    private int mPendingAction = 0;
    private float mTargetElevation;
    private int mTotalScrollRange = -1;

    public AppBarLayout(Context context) {
        this(context, null);
    }

    public AppBarLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.setOrientation(1);
        ThemeUtils.checkAppCompatTheme(context);
        context = context.obtainStyledAttributes(attributeSet, R.styleable.AppBarLayout, 0, R.style.Widget_Design_AppBarLayout);
        this.mTargetElevation = context.getDimensionPixelSize(R.styleable.AppBarLayout_elevation, 0);
        this.setBackgroundDrawable(context.getDrawable(R.styleable.AppBarLayout_android_background));
        if (context.hasValue(R.styleable.AppBarLayout_expanded)) {
            this.setExpanded(context.getBoolean(R.styleable.AppBarLayout_expanded, false));
        }
        context.recycle();
        ViewUtils.setBoundsViewOutlineProvider((View)this);
        this.mListeners = new ArrayList<OnOffsetChangedListener>();
        ViewCompat.setElevation((View)this, this.mTargetElevation);
        ViewCompat.setOnApplyWindowInsetsListener((View)this, new OnApplyWindowInsetsListener(){

            @Override
            public WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat) {
                return AppBarLayout.this.onWindowInsetChanged(windowInsetsCompat);
            }
        });
    }

    /*
     * Enabled aggressive block sorting
     */
    private int getDownNestedPreScrollRange() {
        int n2;
        if (this.mDownPreScrollRange != -1) {
            return this.mDownPreScrollRange;
        }
        int n3 = 0;
        for (int i2 = this.getChildCount() - 1; i2 >= 0; --i2) {
            View view = this.getChildAt(i2);
            LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
            n2 = view.getMeasuredHeight();
            int n4 = layoutParams.mScrollFlags;
            if ((n4 & 5) == 5) {
                n3 += layoutParams.topMargin + layoutParams.bottomMargin;
                n2 = (n4 & 8) != 0 ? n3 + ViewCompat.getMinimumHeight(view) : ((n4 & 2) != 0 ? n3 + (n2 - ViewCompat.getMinimumHeight(view)) : n3 + n2);
            } else {
                n2 = n3;
                if (n3 > 0) break;
            }
            n3 = n2;
        }
        this.mDownPreScrollRange = n2 = Math.max(0, n3);
        return n2;
    }

    private int getDownNestedScrollRange() {
        if (this.mDownScrollRange != -1) {
            return this.mDownScrollRange;
        }
        int n2 = 0;
        int n3 = 0;
        int n4 = this.getChildCount();
        while (true) {
            block6: {
                int n5;
                block5: {
                    n5 = n2;
                    if (n3 >= n4) break block5;
                    View view = this.getChildAt(n3);
                    LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
                    int n6 = view.getMeasuredHeight();
                    int n7 = layoutParams.topMargin;
                    int n8 = layoutParams.bottomMargin;
                    int n9 = layoutParams.mScrollFlags;
                    n5 = n2;
                    if ((n9 & 1) == 0) break block5;
                    n2 += n6 + (n7 + n8);
                    if ((n9 & 2) == 0) break block6;
                    n5 = n2 - (ViewCompat.getMinimumHeight(view) + this.getTopInset());
                }
                this.mDownScrollRange = n2 = Math.max(0, n5);
                return n2;
            }
            ++n3;
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

    private WindowInsetsCompat onWindowInsetChanged(WindowInsetsCompat windowInsetsCompat) {
        WindowInsetsCompat windowInsetsCompat2 = null;
        if (ViewCompat.getFitsSystemWindows((View)this)) {
            windowInsetsCompat2 = windowInsetsCompat;
        }
        if (windowInsetsCompat2 != this.mLastInsets) {
            this.mLastInsets = windowInsetsCompat2;
            this.invalidateScrollRanges();
        }
        return windowInsetsCompat;
    }

    private void resetPendingAction() {
        this.mPendingAction = 0;
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
        return new LayoutParams(this.getContext(), attributeSet);
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

    final int getMinimumHeightForVisibleOverlappingContent() {
        int n2 = this.getTopInset();
        int n3 = ViewCompat.getMinimumHeight((View)this);
        if (n3 != 0) {
            return n3 * 2 + n2;
        }
        n3 = this.getChildCount();
        if (n3 >= 1) {
            return ViewCompat.getMinimumHeight(this.getChildAt(n3 - 1)) * 2 + n2;
        }
        return 0;
    }

    public float getTargetElevation() {
        return this.mTargetElevation;
    }

    public final int getTotalScrollRange() {
        if (this.mTotalScrollRange != -1) {
            return this.mTotalScrollRange;
        }
        int n2 = 0;
        int n3 = 0;
        int n4 = this.getChildCount();
        while (true) {
            block6: {
                int n5;
                block5: {
                    n5 = n2;
                    if (n3 >= n4) break block5;
                    View view = this.getChildAt(n3);
                    LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
                    int n6 = view.getMeasuredHeight();
                    int n7 = layoutParams.mScrollFlags;
                    n5 = n2;
                    if ((n7 & 1) == 0) break block5;
                    n2 += layoutParams.topMargin + n6 + layoutParams.bottomMargin;
                    if ((n7 & 2) == 0) break block6;
                    n5 = n2 - ViewCompat.getMinimumHeight(view);
                }
                this.mTotalScrollRange = n2 = Math.max(0, n5 - this.getTopInset());
                return n2;
            }
            ++n3;
        }
    }

    protected void onLayout(boolean bl2, int n2, int n3, int n4, int n5) {
        super.onLayout(bl2, n2, n3, n4, n5);
        this.invalidateScrollRanges();
        this.mHaveChildWithInterpolator = false;
        n2 = 0;
        n3 = this.getChildCount();
        while (true) {
            block4: {
                block3: {
                    if (n2 >= n3) break block3;
                    if (((LayoutParams)this.getChildAt(n2).getLayoutParams()).getScrollInterpolator() == null) break block4;
                    this.mHaveChildWithInterpolator = true;
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
        private ValueAnimatorCompat mAnimator;
        private WeakReference<View> mLastNestedScrollingChildRef;
        private int mOffsetDelta;
        private int mOffsetToChildIndexOnLayout = -1;
        private boolean mOffsetToChildIndexOnLayoutIsMinHeight;
        private float mOffsetToChildIndexOnLayoutPerc;
        private DragCallback mOnDragCallback;
        private boolean mSkipNestedPreScroll;
        private boolean mWasNestedFlung;

        public Behavior() {
        }

        public Behavior(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        /*
         * Enabled aggressive block sorting
         */
        private void animateOffsetTo(final CoordinatorLayout coordinatorLayout, final AppBarLayout appBarLayout, int n2) {
            int n3 = this.getTopBottomOffsetForScrollingSibling();
            if (n3 == n2) {
                if (this.mAnimator != null && this.mAnimator.isRunning()) {
                    this.mAnimator.cancel();
                }
                return;
            }
            if (this.mAnimator == null) {
                this.mAnimator = ViewUtils.createAnimator();
                this.mAnimator.setInterpolator(AnimationUtils.DECELERATE_INTERPOLATOR);
                this.mAnimator.setUpdateListener(new ValueAnimatorCompat.AnimatorUpdateListener(){

                    @Override
                    public void onAnimationUpdate(ValueAnimatorCompat valueAnimatorCompat) {
                        Behavior.this.setHeaderTopBottomOffset(coordinatorLayout, appBarLayout, valueAnimatorCompat.getAnimatedIntValue());
                    }
                });
            } else {
                this.mAnimator.cancel();
            }
            float f2 = (float)Math.abs(n3 - n2) / coordinatorLayout.getResources().getDisplayMetrics().density;
            this.mAnimator.setDuration(Math.round(1000.0f * f2 / 300.0f));
            this.mAnimator.setIntValues(n3, n2);
            this.mAnimator.start();
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
        int getScrollRangeForDragFling(AppBarLayout appBarLayout) {
            return appBarLayout.getTotalScrollRange();
        }

        @Override
        int getTopBottomOffsetForScrollingSibling() {
            return this.getTopAndBottomOffset() + this.mOffsetDelta;
        }

        @Override
        void onFlingFinished(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout) {
            this.snapToChildIfNeeded(coordinatorLayout, appBarLayout);
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
            this.setTopAndBottomOffset(MathUtils.constrain(this.getTopAndBottomOffset(), -appBarLayout.getTotalScrollRange(), 0));
            this.dispatchOffsetUpdates(appBarLayout);
            return bl2;
        }

        @Override
        public boolean onMeasureChild(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, int n2, int n3, int n4, int n5) {
            if (((CoordinatorLayout.LayoutParams)appBarLayout.getLayoutParams()).height == -2) {
                coordinatorLayout.onMeasureChild((View)appBarLayout, n2, n3, View.MeasureSpec.makeMeasureSpec((int)0, (int)0), n5);
                return true;
            }
            return super.onMeasureChild(coordinatorLayout, appBarLayout, n2, n3, n4, n5);
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public boolean onNestedFling(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, View view, float f2, float f3, boolean bl2) {
            boolean bl3 = false;
            if (!bl2) {
                bl2 = this.fling(coordinatorLayout, appBarLayout, -appBarLayout.getTotalScrollRange(), 0, -f3);
            } else if (f3 < 0.0f) {
                int n2 = -appBarLayout.getTotalScrollRange() + appBarLayout.getDownNestedPreScrollRange();
                bl2 = bl3;
                if (this.getTopBottomOffsetForScrollingSibling() < n2) {
                    this.animateOffsetTo(coordinatorLayout, appBarLayout, n2);
                    bl2 = true;
                }
            } else {
                int n3 = -appBarLayout.getUpNestedPreScrollRange();
                bl2 = bl3;
                if (this.getTopBottomOffsetForScrollingSibling() > n3) {
                    this.animateOffsetTo(coordinatorLayout, appBarLayout, n3);
                    bl2 = true;
                }
            }
            this.mWasNestedFlung = bl2;
            return bl2;
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, View view, int n2, int n3, int[] nArray) {
            if (n3 != 0 && !this.mSkipNestedPreScroll) {
                int n4;
                if (n3 < 0) {
                    n2 = -appBarLayout.getTotalScrollRange();
                    n4 = n2 + appBarLayout.getDownNestedPreScrollRange();
                } else {
                    n2 = -appBarLayout.getUpNestedPreScrollRange();
                    n4 = 0;
                }
                nArray[1] = this.scroll(coordinatorLayout, appBarLayout, n3, n2, n4);
            }
        }

        @Override
        public void onNestedScroll(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, View view, int n2, int n3, int n4, int n5) {
            if (n5 < 0) {
                this.scroll(coordinatorLayout, appBarLayout, n5, -appBarLayout.getDownNestedScrollRange(), 0);
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
        public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, View view, View view2, int n2) {
            boolean bl2 = (n2 & 2) != 0 && appBarLayout.hasScrollableChildren() && coordinatorLayout.getHeight() - view.getHeight() <= appBarLayout.getHeight();
            if (bl2 && this.mAnimator != null) {
                this.mAnimator.cancel();
            }
            this.mLastNestedScrollingChildRef = null;
            return bl2;
        }

        @Override
        public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, View view) {
            if (!this.mWasNestedFlung) {
                this.snapToChildIfNeeded(coordinatorLayout, appBarLayout);
            }
            this.mSkipNestedPreScroll = false;
            this.mWasNestedFlung = false;
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
            int n5 = this.getTopBottomOffsetForScrollingSibling();
            int n6 = 0;
            if (n3 != 0 && n5 >= n3 && n5 <= n4) {
                n3 = MathUtils.constrain(n2, n3, n4);
                n2 = n6;
                if (n5 == n3) return n2;
                n2 = appBarLayout.hasChildWithInterpolator() ? this.interpolateOffset(appBarLayout, n3) : n3;
                boolean bl2 = this.setTopAndBottomOffset(n2);
                n4 = n5 - n3;
                this.mOffsetDelta = n3 - n2;
                if (!bl2 && appBarLayout.hasChildWithInterpolator()) {
                    coordinatorLayout.dispatchDependentViewsChanged((View)appBarLayout);
                }
                this.dispatchOffsetUpdates(appBarLayout);
                return n4;
            }
            this.mOffsetDelta = 0;
            return 0;
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
        static final int FLAG_QUICK_RETURN = 5;
        static final int FLAG_SNAP = 17;
        public static final int SCROLL_FLAG_ENTER_ALWAYS = 4;
        public static final int SCROLL_FLAG_ENTER_ALWAYS_COLLAPSED = 8;
        public static final int SCROLL_FLAG_EXIT_UNTIL_COLLAPSED = 2;
        public static final int SCROLL_FLAG_SCROLL = 1;
        public static final int SCROLL_FLAG_SNAP = 16;
        int mScrollFlags = 1;
        Interpolator mScrollInterpolator;

        public LayoutParams(int n2, int n3) {
            super(n2, n3);
        }

        public LayoutParams(int n2, int n3, float f2) {
            super(n2, n3, f2);
        }

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            attributeSet = context.obtainStyledAttributes(attributeSet, R.styleable.AppBarLayout_LayoutParams);
            this.mScrollFlags = attributeSet.getInt(R.styleable.AppBarLayout_LayoutParams_layout_scrollFlags, 0);
            if (attributeSet.hasValue(R.styleable.AppBarLayout_LayoutParams_layout_scrollInterpolator)) {
                this.mScrollInterpolator = android.view.animation.AnimationUtils.loadInterpolator((Context)context, (int)attributeSet.getResourceId(R.styleable.AppBarLayout_LayoutParams_layout_scrollInterpolator, 0));
            }
            attributeSet.recycle();
        }

        public LayoutParams(LayoutParams layoutParams) {
            super((LinearLayout.LayoutParams)layoutParams);
            this.mScrollFlags = layoutParams.mScrollFlags;
            this.mScrollInterpolator = layoutParams.mScrollInterpolator;
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

        public int getScrollFlags() {
            return this.mScrollFlags;
        }

        public Interpolator getScrollInterpolator() {
            return this.mScrollInterpolator;
        }

        public void setScrollFlags(int n2) {
            this.mScrollFlags = n2;
        }

        public void setScrollInterpolator(Interpolator interpolator2) {
            this.mScrollInterpolator = interpolator2;
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
        public ScrollingViewBehavior() {
        }

        public ScrollingViewBehavior(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            context = context.obtainStyledAttributes(attributeSet, R.styleable.ScrollingViewBehavior_Params);
            this.setOverlayTop(context.getDimensionPixelSize(R.styleable.ScrollingViewBehavior_Params_behavior_overlapTop, 0));
            context.recycle();
        }

        private static int getAppBarLayoutOffset(AppBarLayout object) {
            if ((object = ((CoordinatorLayout.LayoutParams)object.getLayoutParams()).getBehavior()) instanceof Behavior) {
                return ((Behavior)object).getTopBottomOffsetForScrollingSibling();
            }
            return 0;
        }

        private void offsetChildAsNeeded(CoordinatorLayout object, View view, View view2) {
            object = ((CoordinatorLayout.LayoutParams)view2.getLayoutParams()).getBehavior();
            if (object instanceof Behavior) {
                object = (Behavior)object;
                ((Behavior)object).getTopBottomOffsetForScrollingSibling();
                ViewCompat.offsetTopAndBottom(view, view2.getBottom() - view.getTop() + ((Behavior)object).mOffsetDelta + this.getVerticalLayoutGap() - this.getOverlapPixelsForOffset(view2));
            }
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

        /*
         * Enabled aggressive block sorting
         */
        @Override
        float getOverlapRatioForOffset(View object) {
            int n2;
            int n3;
            block3: {
                block2: {
                    if (!(object instanceof AppBarLayout)) break block2;
                    object = (AppBarLayout)((Object)object);
                    n3 = ((AppBarLayout)((Object)object)).getTotalScrollRange();
                    int n4 = ((AppBarLayout)((Object)object)).getDownNestedPreScrollRange();
                    n2 = ScrollingViewBehavior.getAppBarLayoutOffset((AppBarLayout)((Object)object));
                    if ((n4 == 0 || n3 + n2 > n4) && (n3 -= n4) != 0) break block3;
                }
                return 0.0f;
            }
            return 1.0f + (float)n2 / (float)n3;
        }

        @Override
        int getScrollRange(View view) {
            if (view instanceof AppBarLayout) {
                return ((AppBarLayout)view).getTotalScrollRange();
            }
            return super.getScrollRange(view);
        }

        @Override
        public boolean layoutDependsOn(CoordinatorLayout coordinatorLayout, View view, View view2) {
            return view2 instanceof AppBarLayout;
        }

        @Override
        public boolean onDependentViewChanged(CoordinatorLayout coordinatorLayout, View view, View view2) {
            this.offsetChildAsNeeded(coordinatorLayout, view, view2);
            return false;
        }
    }
}

