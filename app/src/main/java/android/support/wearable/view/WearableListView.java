/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.Animator$AnimatorListener
 *  android.animation.AnimatorSet
 *  android.animation.ObjectAnimator
 *  android.annotation.TargetApi
 *  android.content.Context
 *  android.graphics.PointF
 *  android.os.Handler
 *  android.util.AttributeSet
 *  android.util.DisplayMetrics
 *  android.util.Log
 *  android.util.Property
 *  android.view.KeyEvent
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.View$OnLayoutChangeListener
 *  android.view.ViewConfiguration
 *  android.widget.Scroller
 */
package android.support.wearable.view;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.PointF;
import android.os.Handler;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.support.wearable.view.SimpleAnimatorListener;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Property;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Scroller;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@TargetApi(value=20)
public class WearableListView
extends RecyclerView {
    private static final float BOTTOM_TAP_REGION_PERCENTAGE = 0.33f;
    private static final long CENTERING_ANIMATION_DURATION_MS = 150L;
    private static final long FLIP_ANIMATION_DURATION_MS = 150L;
    private static final String TAG = "WearableListView";
    private static final int THIRD = 3;
    private static final float TOP_TAP_REGION_PERCENTAGE = 0.33f;
    private boolean mCanClick = true;
    private ClickListener mClickListener;
    private boolean mGestureDirectionLocked;
    private boolean mGestureNavigationEnabled = true;
    private boolean mGreedyTouchMode;
    private int mInitialOffset = 0;
    private int mLastScrollChange;
    private final int[] mLocation;
    private final int mMaxFlingVelocity;
    private boolean mMaximizeSingleItem;
    private final int mMinFlingVelocity;
    private Runnable mNotifyChildrenPostLayoutRunnable;
    private final OnChangeObserver mObserver;
    private final List<OnCentralPositionChangedListener> mOnCentralPositionChangedListeners;
    private final List<OnScrollListener> mOnScrollListeners;
    private OnOverScrollListener mOverScrollListener;
    private boolean mPossibleVerticalSwipe;
    private final Runnable mPressedRunnable;
    private View mPressedView = null;
    private int mPreviousCentral = 0;
    private final Runnable mReleasedRunnable;
    private Animator mScrollAnimator;
    private Scroller mScroller;
    private SetScrollVerticallyProperty mSetScrollVerticallyProperty = new SetScrollVerticallyProperty();
    private float mStartFirstTop;
    private float mStartX;
    private float mStartY;
    private int mTapPositionX;
    private int mTapPositionY;
    private final float[] mTapRegions;
    private final int mTouchSlop;

    public WearableListView(Context context) {
        this(context, null);
    }

    public WearableListView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public WearableListView(Context context, AttributeSet attributeSet, int n2) {
        super(context, attributeSet, n2);
        this.mOnScrollListeners = new ArrayList<OnScrollListener>();
        this.mOnCentralPositionChangedListeners = new ArrayList<OnCentralPositionChangedListener>();
        this.mTapRegions = new float[2];
        this.mLocation = new int[2];
        this.mPressedRunnable = new Runnable(){

            @Override
            public void run() {
                if (WearableListView.this.getChildCount() > 0) {
                    WearableListView.access$002(WearableListView.this, WearableListView.this.getChildAt(WearableListView.this.findCenterViewIndex()));
                    WearableListView.this.mPressedView.setPressed(true);
                    return;
                }
                Log.w((String)WearableListView.TAG, (String)"mPressedRunnable: the children were removed, skipping.");
            }
        };
        this.mReleasedRunnable = new Runnable(){

            @Override
            public void run() {
                WearableListView.this.releasePressedItem();
            }
        };
        this.mNotifyChildrenPostLayoutRunnable = new Runnable(){

            @Override
            public void run() {
                WearableListView.this.notifyChildrenAboutProximity(false);
            }
        };
        this.mObserver = new OnChangeObserver();
        this.setHasFixedSize(true);
        this.setOverScrollMode(2);
        this.setLayoutManager(new LayoutManager());
        this.setOnScrollListener(new RecyclerView.OnScrollListener(){

            @Override
            public void onScrollStateChanged(RecyclerView object, int n2) {
                if (n2 == 0 && WearableListView.this.getChildCount() > 0) {
                    WearableListView.this.handleTouchUp(null, n2);
                }
                object = WearableListView.this.mOnScrollListeners.iterator();
                while (object.hasNext()) {
                    ((OnScrollListener)object.next()).onScrollStateChanged(n2);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int n2, int n3) {
                WearableListView.this.onScroll(n3);
            }
        });
        context = ViewConfiguration.get((Context)context);
        this.mTouchSlop = context.getScaledTouchSlop();
        this.mMinFlingVelocity = context.getScaledMinimumFlingVelocity();
        this.mMaxFlingVelocity = context.getScaledMaximumFlingVelocity();
    }

    static /* synthetic */ View access$002(WearableListView wearableListView, View view) {
        wearableListView.mPressedView = view;
        return view;
    }

    static /* synthetic */ int access$1200(WearableListView wearableListView) {
        return wearableListView.mInitialOffset;
    }

    static /* synthetic */ boolean access$902(WearableListView wearableListView, boolean bl2) {
        wearableListView.mCanClick = bl2;
        return bl2;
    }

    private void animateToMiddle(int n2, int n3) {
        if (n2 == n3) {
            throw new IllegalArgumentException("newCenterIndex must be different from oldCenterIndex");
        }
        ArrayList<Animator> arrayList = new ArrayList<Animator>();
        View view = this.getChildAt(n2);
        this.startScrollAnimation(arrayList, this.getCentralViewTop() - view.getTop(), 150L);
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean checkForTap(MotionEvent object) {
        block8: {
            block7: {
                if (!this.isEnabled()) break block7;
                float f2 = object.getRawY();
                int n2 = this.findCenterViewIndex();
                RecyclerView.ViewHolder viewHolder = this.getChildViewHolder(this.getChildAt(n2));
                this.computeTapRegions(this.mTapRegions);
                if (f2 > this.mTapRegions[0] && f2 < this.mTapRegions[1]) {
                    if (this.mClickListener != null) {
                        this.mClickListener.onClick((ViewHolder)viewHolder);
                    }
                    return true;
                }
                if (n2 > 0 && f2 <= this.mTapRegions[0]) {
                    this.animateToMiddle(n2 - 1, n2);
                    return true;
                }
                if (n2 < this.getChildCount() - 1 && f2 >= this.mTapRegions[1]) {
                    this.animateToMiddle(n2 + 1, n2);
                    return true;
                }
                if (n2 == 0 && f2 <= this.mTapRegions[0] && this.mClickListener != null) break block8;
            }
            return false;
        }
        this.mClickListener.onTopEmptyRegionClick();
        return true;
    }

    private void computeTapRegions(float[] fArray) {
        int[] nArray = this.mLocation;
        this.mLocation[1] = 0;
        nArray[0] = 0;
        this.getLocationOnScreen(this.mLocation);
        int n2 = this.mLocation[1];
        int n3 = this.getHeight();
        fArray[0] = (float)n2 + (float)n3 * 0.33f;
        fArray[1] = (float)n2 + (float)n3 * 0.66999996f;
    }

    private int findCenterViewIndex() {
        int n2 = this.getChildCount();
        int n3 = -1;
        int n4 = Integer.MAX_VALUE;
        int n5 = WearableListView.getCenterYPos((View)this);
        for (int i2 = 0; i2 < n2; ++i2) {
            View view = this.getChildAt(i2);
            int n6 = Math.abs(n5 - (this.getTop() + WearableListView.getCenterYPos(view)));
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

    private int getAdjustedHeight() {
        return WearableListView.getAdjustedHeight((View)this);
    }

    private static int getAdjustedHeight(View view) {
        return view.getHeight() - view.getPaddingBottom() - view.getPaddingTop();
    }

    private static int getCenterYPos(View view) {
        return view.getTop() + view.getPaddingTop() + WearableListView.getAdjustedHeight(view) / 2;
    }

    private int getItemHeight() {
        return this.getAdjustedHeight() / 3 + 1;
    }

    private int getTopViewMaxTop() {
        return this.getHeight() / 2;
    }

    private boolean handlePossibleVerticalSwipe(MotionEvent motionEvent) {
        float f2;
        if (this.mGestureDirectionLocked) {
            return this.mPossibleVerticalSwipe;
        }
        float f3 = Math.abs(this.mStartX - motionEvent.getX());
        if (f3 * f3 + (f2 = Math.abs(this.mStartY - motionEvent.getY())) * f2 > (float)(this.mTouchSlop * this.mTouchSlop)) {
            if (f3 > f2) {
                this.mPossibleVerticalSwipe = false;
            }
            this.mGestureDirectionLocked = true;
        }
        return this.mPossibleVerticalSwipe;
    }

    private void handleTouchDown(MotionEvent motionEvent) {
        if (this.mCanClick) {
            this.mTapPositionX = (int)motionEvent.getX();
            this.mTapPositionY = (int)motionEvent.getY();
            float f2 = motionEvent.getRawY();
            this.computeTapRegions(this.mTapRegions);
            if (f2 > this.mTapRegions[0] && f2 < this.mTapRegions[1] && this.getChildAt(this.findCenterViewIndex()) instanceof OnCenterProximityListener && (motionEvent = this.getHandler()) != null) {
                motionEvent.removeCallbacks(this.mReleasedRunnable);
                motionEvent.postDelayed(this.mPressedRunnable, (long)ViewConfiguration.getTapTimeout());
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void handleTouchUp(MotionEvent motionEvent, int n2) {
        if (this.mCanClick && motionEvent != null && this.checkForTap(motionEvent)) {
            motionEvent = this.getHandler();
            if (motionEvent == null) return;
            motionEvent.postDelayed(this.mReleasedRunnable, (long)ViewConfiguration.getTapTimeout());
            return;
        }
        if (n2 != 0) return;
        if (this.isOverScrolling()) {
            this.mOverScrollListener.onOverScroll();
            return;
        }
        this.animateToCenter();
    }

    private boolean isOverScrolling() {
        boolean bl2;
        boolean bl3 = bl2 = false;
        if (this.getChildCount() > 0) {
            bl3 = bl2;
            if (this.mStartFirstTop <= (float)this.getCentralViewTop()) {
                bl3 = bl2;
                if (this.getChildAt(0).getTop() >= this.getTopViewMaxTop()) {
                    bl3 = bl2;
                    if (this.mOverScrollListener != null) {
                        bl3 = true;
                    }
                }
            }
        }
        return bl3;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void notifyChildrenAboutProximity(boolean bl2) {
        int n2;
        Object object;
        block8: {
            block7: {
                object = (LayoutManager)this.getLayoutManager();
                int n3 = ((RecyclerView.LayoutManager)object).getChildCount();
                if (n3 == 0) break block7;
                int n4 = ((LayoutManager)object).findCenterViewIndex();
                for (n2 = 0; n2 < n3; ++n2) {
                    RecyclerView.ViewHolder viewHolder = this.getChildViewHolder(((RecyclerView.LayoutManager)object).getChildAt(n2));
                    boolean bl3 = n2 == n4;
                    ((ViewHolder)viewHolder).onCenterProximity(bl3, bl2);
                }
                n2 = this.getChildViewHolder(this.getChildAt(n4)).getPosition();
                if (n2 != this.mPreviousCentral) break block8;
            }
            return;
        }
        object = this.mOnScrollListeners.iterator();
        while (object.hasNext()) {
            ((OnScrollListener)object.next()).onCentralPositionChanged(n2);
        }
        object = this.mOnCentralPositionChangedListeners.iterator();
        while (true) {
            if (!object.hasNext()) {
                this.mPreviousCentral = n2;
                return;
            }
            object.next().onCentralPositionChanged(n2);
        }
    }

    private void onScroll(int n2) {
        Iterator<OnScrollListener> iterator = this.mOnScrollListeners.iterator();
        while (iterator.hasNext()) {
            iterator.next().onScroll(n2);
        }
        this.notifyChildrenAboutProximity(true);
    }

    private void releasePressedItem() {
        Handler handler;
        if (this.mPressedView != null) {
            this.mPressedView.setPressed(false);
            this.mPressedView = null;
        }
        if ((handler = this.getHandler()) != null) {
            handler.removeCallbacks(this.mPressedRunnable);
        }
    }

    private void setScrollVertically(int n2) {
        this.scrollBy(0, n2 - this.mLastScrollChange);
        this.mLastScrollChange = n2;
    }

    private void startScrollAnimation(int n2, long l2, long l3, Animator.AnimatorListener animatorListener) {
        this.startScrollAnimation(null, n2, l2, l3, animatorListener);
    }

    private void startScrollAnimation(List<Animator> list, int n2, long l2) {
        this.startScrollAnimation(list, n2, l2, 0L);
    }

    private void startScrollAnimation(List<Animator> list, int n2, long l2, long l3) {
        this.startScrollAnimation(list, n2, l2, l3, null);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void startScrollAnimation(List<Animator> list, int n2, long l2, long l3, Animator.AnimatorListener animatorListener) {
        if (this.mScrollAnimator != null) {
            this.mScrollAnimator.cancel();
        }
        this.mLastScrollChange = 0;
        ObjectAnimator objectAnimator = ObjectAnimator.ofInt((Object)this, (Property)this.mSetScrollVerticallyProperty, (int[])new int[]{0, -n2});
        if (list != null) {
            list.add((Animator)objectAnimator);
            objectAnimator = new AnimatorSet();
            objectAnimator.playTogether(list);
            this.mScrollAnimator = objectAnimator;
        } else {
            this.mScrollAnimator = objectAnimator;
        }
        this.mScrollAnimator.setDuration(l2);
        if (animatorListener != null) {
            this.mScrollAnimator.addListener(animatorListener);
        }
        if (l3 > 0L) {
            this.mScrollAnimator.setStartDelay(l3);
        }
        this.mScrollAnimator.start();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean tapCenterView() {
        boolean bl2 = true;
        if (!this.isEnabled()) return false;
        if (this.getVisibility() != 0) return false;
        if (this.getChildCount() < 1) {
            return false;
        }
        View view = this.getChildAt(this.findCenterViewIndex());
        RecyclerView.ViewHolder viewHolder = this.getChildViewHolder(view);
        if (view.performClick()) return bl2;
        if (this.mClickListener == null) return false;
        this.mClickListener.onClick((ViewHolder)viewHolder);
        return true;
    }

    public void addOnCentralPositionChangedListener(OnCentralPositionChangedListener onCentralPositionChangedListener) {
        this.mOnCentralPositionChangedListeners.add(onCentralPositionChangedListener);
    }

    public void addOnScrollListener(OnScrollListener onScrollListener) {
        this.mOnScrollListeners.add(onScrollListener);
    }

    public void animateToCenter() {
        if (this.getChildCount() == 0) {
            return;
        }
        View view = this.getChildAt(this.findCenterViewIndex());
        this.startScrollAnimation(this.getCentralViewTop() - view.getTop(), 150L, 0L, new SimpleAnimatorListener(){

            @Override
            public void onAnimationEnd(Animator animator2) {
                if (!this.wasCanceled()) {
                    WearableListView.access$902(WearableListView.this, true);
                }
            }
        });
    }

    public void animateToInitialPosition(final Runnable runnable) {
        View view = this.getChildAt(0);
        this.startScrollAnimation(this.getCentralViewTop() + this.mInitialOffset - view.getTop(), 150L, 0L, new SimpleAnimatorListener(){

            @Override
            public void onAnimationEnd(Animator animator2) {
                if (runnable != null) {
                    runnable.run();
                }
            }
        });
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public boolean fling(int n2, int n3) {
        if (this.getChildCount() == 0) {
            return false;
        }
        int n4 = this.getChildPosition(this.getChildAt(this.findCenterViewIndex()));
        if (n4 == 0 && n3 < 0 || n4 == this.getAdapter().getItemCount() - 1 && n3 > 0) {
            return super.fling(n2, n3);
        }
        if (Math.abs(n3) < this.mMinFlingVelocity) {
            return false;
        }
        int n5 = Math.max(Math.min(n3, this.mMaxFlingVelocity), -this.mMaxFlingVelocity);
        if (this.mScroller == null) {
            this.mScroller = new Scroller(this.getContext(), null, true);
        }
        this.mScroller.fling(0, 0, 0, n5, Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE);
        n2 = n3 = this.mScroller.getFinalY() / (this.getPaddingTop() + this.getAdjustedHeight() / 2);
        if (n3 == 0) {
            n2 = n5 > 0 ? 1 : -1;
        }
        this.smoothScrollToPosition(Math.max(0, Math.min(this.getAdapter().getItemCount() - 1, n4 + n2)));
        return true;
    }

    @Override
    public int getBaseline() {
        if (this.getChildCount() == 0) {
            return super.getBaseline();
        }
        int n2 = this.getChildAt(this.findCenterViewIndex()).getBaseline();
        if (n2 == -1) {
            return super.getBaseline();
        }
        return this.getCentralViewTop() + n2;
    }

    public int getCentralViewTop() {
        return this.getPaddingTop() + this.getItemHeight();
    }

    @Override
    public ViewHolder getChildViewHolder(View view) {
        return (ViewHolder)super.getChildViewHolder(view);
    }

    public boolean getMaximizeSingleItem() {
        return this.mMaximizeSingleItem;
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean isAtTop() {
        return this.getChildCount() == 0 || this.getChildAdapterPosition(this.getChildAt(this.findCenterViewIndex())) == 0 && this.getScrollState() == 0;
    }

    public boolean isGestureNavigationEnabled() {
        return this.mGestureNavigationEnabled;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mObserver.setListView(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        this.mObserver.setListView(null);
        super.onDetachedFromWindow();
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        if (!this.isEnabled()) {
            return false;
        }
        if (this.mGreedyTouchMode && this.getChildCount() > 0) {
            int n2 = motionEvent.getActionMasked();
            if (n2 == 0) {
                this.mStartX = motionEvent.getX();
                this.mStartY = motionEvent.getY();
                float f2 = this.getChildCount() > 0 ? (float)this.getChildAt(0).getTop() : 0.0f;
                this.mStartFirstTop = f2;
                this.mPossibleVerticalSwipe = true;
                this.mGestureDirectionLocked = false;
            } else if (n2 == 2 && this.mPossibleVerticalSwipe) {
                this.handlePossibleVerticalSwipe(motionEvent);
            }
            this.getParent().requestDisallowInterceptTouchEvent(this.mPossibleVerticalSwipe);
        }
        return super.onInterceptTouchEvent(motionEvent);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean onKeyDown(int n2, KeyEvent keyEvent) {
        if (!this.mGestureNavigationEnabled) return super.onKeyDown(n2, keyEvent);
        switch (n2) {
            default: {
                return super.onKeyDown(n2, keyEvent);
            }
            case 260: {
                this.fling(0, -this.mMinFlingVelocity);
                return true;
            }
            case 261: {
                this.fling(0, this.mMinFlingVelocity);
                return true;
            }
            case 262: {
                return this.tapCenterView();
            }
            case 263: 
        }
        return false;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        boolean bl2;
        if (!this.isEnabled()) {
            return false;
        }
        int n2 = this.getScrollState();
        boolean bl3 = bl2 = super.onTouchEvent(motionEvent);
        if (this.getChildCount() <= 0) return bl3;
        int n3 = motionEvent.getActionMasked();
        if (n3 == 0) {
            this.handleTouchDown(motionEvent);
            return bl2;
        }
        if (n3 == 1) {
            this.handleTouchUp(motionEvent, n2);
            this.getParent().requestDisallowInterceptTouchEvent(false);
            return bl2;
        }
        if (n3 == 2) {
            if (Math.abs(this.mTapPositionX - (int)motionEvent.getX()) >= this.mTouchSlop || Math.abs(this.mTapPositionY - (int)motionEvent.getY()) >= this.mTouchSlop) {
                this.releasePressedItem();
                this.mCanClick = false;
            }
            bl3 = this.handlePossibleVerticalSwipe(motionEvent);
            this.getParent().requestDisallowInterceptTouchEvent(this.mPossibleVerticalSwipe);
            return bl2 | bl3;
        }
        bl3 = bl2;
        if (n3 != 3) return bl3;
        this.getParent().requestDisallowInterceptTouchEvent(false);
        this.mCanClick = true;
        return bl2;
    }

    public void removeOnCentralPositionChangedListener(OnCentralPositionChangedListener onCentralPositionChangedListener) {
        this.mOnCentralPositionChangedListeners.remove(onCentralPositionChangedListener);
    }

    public void removeOnScrollListener(OnScrollListener onScrollListener) {
        this.mOnScrollListeners.remove(onScrollListener);
    }

    public void resetLayoutManager() {
        this.setLayoutManager(new LayoutManager());
    }

    @Override
    public void setAdapter(RecyclerView.Adapter adapter) {
        this.mObserver.setAdapter(adapter);
        super.setAdapter(adapter);
    }

    public void setClickListener(ClickListener clickListener) {
        this.mClickListener = clickListener;
    }

    public void setEnableGestureNavigation(boolean bl2) {
        this.mGestureNavigationEnabled = bl2;
    }

    public void setGreedyTouchMode(boolean bl2) {
        this.mGreedyTouchMode = bl2;
    }

    public void setInitialOffset(int n2) {
        this.mInitialOffset = n2;
    }

    public void setMaximizeSingleItem(boolean bl2) {
        this.mMaximizeSingleItem = bl2;
    }

    public void setOverScrollListener(OnOverScrollListener onOverScrollListener) {
        this.mOverScrollListener = onOverScrollListener;
    }

    public void smoothScrollToPosition(int n2, RecyclerView.SmoothScroller smoothScroller) {
        LayoutManager layoutManager = (LayoutManager)this.getLayoutManager();
        layoutManager.setCustomSmoothScroller(smoothScroller);
        this.smoothScrollToPosition(n2);
        layoutManager.clearCustomSmoothScroller();
    }

    public static abstract class Adapter
    extends RecyclerView.Adapter<ViewHolder> {
    }

    public static interface ClickListener {
        public void onClick(ViewHolder var1);

        public void onTopEmptyRegionClick();
    }

    private class LayoutManager
    extends RecyclerView.LayoutManager {
        private int mAbsoluteScroll;
        private RecyclerView.SmoothScroller mDefaultSmoothScroller;
        private int mFirstPosition;
        private boolean mPushFirstHigher;
        private RecyclerView.SmoothScroller mSmoothScroller;
        private boolean mUseOldViewTop = true;
        private boolean mWasZoomedIn = false;

        private LayoutManager() {
        }

        private int findCenterViewIndex() {
            int n2 = this.getChildCount();
            int n3 = -1;
            int n4 = Integer.MAX_VALUE;
            int n5 = WearableListView.getCenterYPos((View)WearableListView.this);
            for (int i2 = 0; i2 < n2; ++i2) {
                View view = WearableListView.this.getLayoutManager().getChildAt(i2);
                int n6 = Math.abs(n5 - (WearableListView.this.getTop() + WearableListView.getCenterYPos(view)));
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

        private void measureThirdView(View view) {
            this.measureView(view, (int)(1.0f + (float)this.getHeight() / 3.0f));
        }

        private void measureView(View view, int n2) {
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams)view.getLayoutParams();
            view.measure(LayoutManager.getChildMeasureSpec(this.getWidth(), this.getPaddingLeft() + this.getPaddingRight() + layoutParams.leftMargin + layoutParams.rightMargin, layoutParams.width, this.canScrollHorizontally()), LayoutManager.getChildMeasureSpec(this.getHeight(), this.getPaddingTop() + this.getPaddingBottom() + layoutParams.topMargin + layoutParams.bottomMargin, n2, this.canScrollVertically()));
        }

        private void measureZoomView(View view) {
            this.measureView(view, this.getHeight());
        }

        /*
         * Enabled aggressive block sorting
         */
        private void performLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state, int n2, int n3) {
            this.detachAndScrapAttachedViews(recycler);
            if (WearableListView.this.mMaximizeSingleItem && state.getItemCount() == 1) {
                this.performLayoutOneChild(recycler, n2);
                this.mWasZoomedIn = true;
            } else {
                this.performLayoutMultipleChildren(recycler, state, n2, n3);
                this.mWasZoomedIn = false;
            }
            if (this.getChildCount() > 0) {
                WearableListView.this.post(WearableListView.this.mNotifyChildrenPostLayoutRunnable);
            }
        }

        private void performLayoutMultipleChildren(RecyclerView.Recycler recycler, RecyclerView.State state, int n2, int n3) {
            int n4 = this.getPaddingLeft();
            int n5 = this.getWidth();
            int n6 = this.getPaddingRight();
            int n7 = state.getItemCount();
            int n8 = 0;
            int n9 = n3;
            n3 = n8;
            while (this.getFirstPosition() + n3 < n7 && n9 < n2) {
                state = recycler.getViewForPosition(this.getFirstPosition() + n3);
                this.addView((View)state, n3);
                this.measureThirdView((View)state);
                n8 = n9 + WearableListView.this.getItemHeight();
                state.layout(n4, n9, n5 - n6, n8);
                ++n3;
                n9 = n8;
            }
            return;
        }

        private void performLayoutOneChild(RecyclerView.Recycler recycler, int n2) {
            int n3 = this.getWidth();
            int n4 = this.getPaddingRight();
            recycler = recycler.getViewForPosition(this.getFirstPosition());
            this.addView((View)recycler, 0);
            this.measureZoomView((View)recycler);
            recycler.layout(this.getPaddingLeft(), this.getPaddingTop(), n3 - n4, n2);
        }

        /*
         * Enabled aggressive block sorting
         */
        private void recycleViewsOutOfBounds(RecyclerView.Recycler recycler) {
            int n2;
            int n3 = this.getChildCount();
            int n4 = this.getWidth();
            int n5 = this.getHeight();
            int n6 = 0;
            int n7 = 0;
            int n8 = 0;
            for (n2 = 0; n2 < n3; ++n2) {
                int n9;
                int n10;
                int n11;
                block11: {
                    block10: {
                        View view = this.getChildAt(n2);
                        if (view.hasFocus()) break block10;
                        n11 = n7;
                        n10 = n6;
                        n9 = n8;
                        if (view.getRight() < 0) break block11;
                        n11 = n7;
                        n10 = n6;
                        n9 = n8;
                        if (view.getLeft() > n4) break block11;
                        n11 = n7;
                        n10 = n6;
                        n9 = n8;
                        if (view.getBottom() < 0) break block11;
                        n11 = n7;
                        n10 = n6;
                        n9 = n8;
                        if (view.getTop() > n5) break block11;
                    }
                    n8 = n6;
                    if (n6 == 0) {
                        n7 = n2;
                        n8 = 1;
                    }
                    n9 = n2;
                    n10 = n8;
                    n11 = n7;
                }
                n7 = n11;
                n6 = n10;
                n8 = n9;
            }
            for (n2 = n3 - 1; n2 > n8; --n2) {
                this.removeAndRecycleViewAt(n2, recycler);
            }
            for (n2 = n7 - 1; n2 >= 0; --n2) {
                this.removeAndRecycleViewAt(n2, recycler);
            }
            if (this.getChildCount() == 0) {
                this.mFirstPosition = 0;
                return;
            } else {
                if (n7 <= 0) return;
                this.mPushFirstHigher = true;
                this.mFirstPosition += n7;
                return;
            }
        }

        private void setAbsoluteScroll(int n2) {
            this.mAbsoluteScroll = n2;
            Iterator iterator = WearableListView.this.mOnScrollListeners.iterator();
            while (iterator.hasNext()) {
                ((OnScrollListener)iterator.next()).onAbsoluteScrollChange(this.mAbsoluteScroll);
            }
        }

        @Override
        public boolean canScrollVertically() {
            return this.getItemCount() != 1 || !this.mWasZoomedIn;
        }

        public void clearCustomSmoothScroller() {
            this.mSmoothScroller = null;
        }

        @Override
        public RecyclerView.LayoutParams generateDefaultLayoutParams() {
            return new RecyclerView.LayoutParams(-1, -2);
        }

        public RecyclerView.SmoothScroller getDefaultSmoothScroller(RecyclerView recyclerView) {
            if (this.mDefaultSmoothScroller == null) {
                this.mDefaultSmoothScroller = new SmoothScroller(recyclerView.getContext(), this);
            }
            return this.mDefaultSmoothScroller;
        }

        public int getFirstPosition() {
            return this.mFirstPosition;
        }

        @Override
        public void onAdapterChanged(RecyclerView.Adapter adapter, RecyclerView.Adapter adapter2) {
            this.removeAllViews();
        }

        /*
         * Unable to fully structure code
         */
        @Override
        public void onLayoutChildren(RecyclerView.Recycler var1_1, RecyclerView.State var2_2) {
            block14: {
                block15: {
                    block13: {
                        var9_3 = this.getHeight();
                        var10_4 = this.getPaddingBottom();
                        var6_5 = WearableListView.this.getCentralViewTop() + WearableListView.access$1200(WearableListView.this);
                        if (!this.mUseOldViewTop || this.getChildCount() <= 0) break block15;
                        var8_6 = this.findCenterViewIndex();
                        var4_7 = this.getPosition(this.getChildAt(var8_6));
                        var5_8 = var8_6;
                        var3_9 = var4_7;
                        if (var4_7 != -1) ** GOTO lbl22
                        var7_10 = 0;
                        var11_11 = this.getChildCount();
                        while (true) {
                            block19: {
                                block18: {
                                    block17: {
                                        block16: {
                                            if (var8_6 + var7_10 < var11_11) break block16;
                                            var5_8 = var8_6;
                                            var3_9 = var4_7;
                                            if (var8_6 - var7_10 < 0) break block17;
                                        }
                                        if ((var12_12 = this.getChildAt(var8_6 + var7_10)) == null) break block18;
                                        var4_7 = var3_9 = this.getPosition(var12_12);
                                        if (var3_9 == -1) break block18;
                                        var5_8 = var8_6 + var7_10;
                                    }
lbl23:
                                    // 2 sources

                                    while (var3_9 == -1) {
                                        var4_7 = this.getChildAt(0).getTop();
                                        var5_8 = var2_2.getItemCount();
                                        while (true) {
                                            var3_9 = var4_7;
                                            if (this.mFirstPosition >= var5_8) {
                                                var3_9 = var4_7;
                                                if (this.mFirstPosition > 0) {
                                                    --this.mFirstPosition;
                                                    continue;
                                                }
                                            }
                                            break block13;
                                            break;
                                        }
                                    }
                                    break;
                                }
                                if ((var12_12 = this.getChildAt(var8_6 - var7_10)) == null) break block19;
                                var4_7 = var3_9 = this.getPosition(var12_12);
                                if (var3_9 == -1) break block19;
                                var5_8 = var8_6 - var7_10;
                                ** GOTO lbl23
                            }
                            ++var7_10;
                        }
                        var4_7 = var6_5;
                        var6_5 = var3_9;
                        if (!this.mWasZoomedIn) {
                            var4_7 = this.getChildAt(var5_8).getTop();
                            var6_5 = var3_9;
                        }
                        while (var4_7 > this.getPaddingTop() && var6_5 > 0) {
                            --var6_5;
                            var4_7 -= WearableListView.access$1300(WearableListView.this);
                        }
                        var3_9 = var4_7;
                        if (var6_5 == 0) {
                            var3_9 = var4_7;
                            if (var4_7 > WearableListView.this.getCentralViewTop()) {
                                var3_9 = WearableListView.this.getCentralViewTop();
                            }
                        }
                        this.mFirstPosition = var6_5;
                    }
lbl60:
                    // 3 sources

                    while (true) {
                        this.performLayoutChildren(var1_1, var2_2, var9_3 - var10_4, var3_9);
                        if (this.getChildCount() == 0) {
                            this.setAbsoluteScroll(0);
lbl64:
                            // 2 sources

                            while (true) {
                                this.mUseOldViewTop = true;
                                this.mPushFirstHigher = false;
                                return;
                            }
                        }
                        break block14;
                        break;
                    }
                }
                var3_9 = var6_5;
                if (!this.mPushFirstHigher) ** GOTO lbl60
                var3_9 = WearableListView.this.getCentralViewTop() - WearableListView.access$1300(WearableListView.this);
                ** while (true)
            }
            var1_1 = this.getChildAt(this.findCenterViewIndex());
            this.setAbsoluteScroll(var1_1.getTop() - WearableListView.this.getCentralViewTop() + this.getPosition((View)var1_1) * WearableListView.access$1300(WearableListView.this));
            ** while (true)
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public void scrollToPosition(int n2) {
            this.mUseOldViewTop = false;
            if (n2 > 0) {
                this.mFirstPosition = n2 - 1;
                this.mPushFirstHigher = true;
            } else {
                this.mFirstPosition = n2;
                this.mPushFirstHigher = false;
            }
            this.requestLayout();
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public int scrollVerticallyBy(int n2, RecyclerView.Recycler recycler, RecyclerView.State state) {
            int n3;
            block9: {
                if (this.getChildCount() == 0) {
                    return 0;
                }
                int n4 = 0;
                int n5 = 0;
                n3 = 0;
                int n6 = this.getPaddingLeft();
                int n7 = this.getWidth() - this.getPaddingRight();
                if (n2 < 0) {
                    n5 = n3;
                    while (true) {
                        n3 = n5;
                        if (n5 <= n2) break block9;
                        state = this.getChildAt(0);
                        if (this.getFirstPosition() <= 0) break;
                        n3 = Math.min(n5 - n2, Math.max(-state.getTop(), 0));
                        n5 -= n3;
                        this.offsetChildrenVertical(n3);
                        n3 = n5;
                        if (this.getFirstPosition() > 0) {
                            n3 = n5;
                            if (n5 > n2) {
                                --this.mFirstPosition;
                                View view = recycler.getViewForPosition(this.getFirstPosition());
                                this.addView(view, 0);
                                this.measureThirdView(view);
                                n3 = state.getTop();
                                view.layout(n6, n3 - WearableListView.this.getItemHeight(), n7, n3);
                                continue;
                            }
                        }
                        break block9;
                        break;
                    }
                    this.mPushFirstHigher = false;
                    n3 = WearableListView.this.mOverScrollListener != null ? this.getHeight() : WearableListView.this.getTopViewMaxTop();
                    n2 = Math.min(-n2 + n5, n3 - state.getTop());
                    n3 = n5 - n2;
                    this.offsetChildrenVertical(n2);
                } else {
                    n3 = n4;
                    if (n2 > 0) {
                        View view;
                        n4 = this.getHeight();
                        while (true) {
                            n3 = n5;
                            if (n5 >= n2) break block9;
                            view = this.getChildAt(this.getChildCount() - 1);
                            if (state.getItemCount() <= this.mFirstPosition + this.getChildCount()) break;
                            n3 = -Math.min(n2 - n5, Math.max(view.getBottom() - n4, 0));
                            n5 -= n3;
                            this.offsetChildrenVertical(n3);
                            n3 = n5;
                            if (n5 < n2) {
                                view = recycler.getViewForPosition(this.mFirstPosition + this.getChildCount());
                                n3 = this.getChildAt(this.getChildCount() - 1).getBottom();
                                this.addView(view);
                                this.measureThirdView(view);
                                view.layout(n6, n3, n7, n3 + WearableListView.this.getItemHeight());
                                continue;
                            }
                            break block9;
                            break;
                        }
                        n2 = Math.max(-n2 + n5, this.getHeight() / 2 - view.getBottom());
                        n3 = n5 - n2;
                        this.offsetChildrenVertical(n2);
                    }
                }
            }
            this.recycleViewsOutOfBounds(recycler);
            this.setAbsoluteScroll(this.mAbsoluteScroll + n3);
            return n3;
        }

        public void setCustomSmoothScroller(RecyclerView.SmoothScroller smoothScroller) {
            this.mSmoothScroller = smoothScroller;
        }

        @Override
        public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State object, int n2) {
            RecyclerView.SmoothScroller smoothScroller = this.mSmoothScroller;
            object = smoothScroller;
            if (smoothScroller == null) {
                object = this.getDefaultSmoothScroller(recyclerView);
            }
            ((RecyclerView.SmoothScroller)object).setTargetPosition(n2);
            this.startSmoothScroll((RecyclerView.SmoothScroller)object);
        }
    }

    public static interface OnCenterProximityListener {
        public void onCenterPosition(boolean var1);

        public void onNonCenterPosition(boolean var1);
    }

    public static interface OnCentralPositionChangedListener {
        public void onCentralPositionChanged(int var1);
    }

    private static class OnChangeObserver
    extends RecyclerView.AdapterDataObserver
    implements View.OnLayoutChangeListener {
        private RecyclerView.Adapter mAdapter;
        private boolean mIsListeningToLayoutChange;
        private boolean mIsObservingAdapter;
        private WeakReference<WearableListView> mListView;

        private OnChangeObserver() {
        }

        private void startDataObserving() {
            if (this.mAdapter != null) {
                this.mAdapter.registerAdapterDataObserver(this);
                this.mIsObservingAdapter = true;
            }
        }

        /*
         * Enabled aggressive block sorting
         */
        private void startOnLayoutChangeListening() {
            WearableListView wearableListView = this.mListView == null ? null : (WearableListView)this.mListView.get();
            if (!this.mIsListeningToLayoutChange && wearableListView != null) {
                wearableListView.addOnLayoutChangeListener(this);
                this.mIsListeningToLayoutChange = true;
            }
        }

        private void stopDataObserving() {
            this.stopOnLayoutChangeListening();
            if (this.mIsObservingAdapter) {
                this.mAdapter.unregisterAdapterDataObserver(this);
                this.mIsObservingAdapter = false;
            }
        }

        /*
         * Enabled aggressive block sorting
         */
        private void stopOnLayoutChangeListening() {
            if (this.mIsListeningToLayoutChange) {
                WearableListView wearableListView = this.mListView == null ? null : (WearableListView)this.mListView.get();
                if (wearableListView != null) {
                    wearableListView.removeOnLayoutChangeListener(this);
                }
                this.mIsListeningToLayoutChange = false;
            }
        }

        @Override
        public void onChanged() {
            this.startOnLayoutChangeListening();
        }

        /*
         * Enabled aggressive block sorting
         */
        public void onLayoutChange(View object, int n2, int n3, int n4, int n5, int n6, int n7, int n8, int n9) {
            block3: {
                block2: {
                    object = (WearableListView)this.mListView.get();
                    if (object == null) break block2;
                    this.stopOnLayoutChangeListening();
                    if (object.getChildCount() > 0) break block3;
                }
                return;
            }
            ((WearableListView)object).animateToCenter();
        }

        public void setAdapter(RecyclerView.Adapter adapter) {
            this.stopDataObserving();
            this.mAdapter = adapter;
            this.startDataObserving();
        }

        public void setListView(WearableListView wearableListView) {
            this.stopOnLayoutChangeListening();
            this.mListView = new WeakReference<WearableListView>(wearableListView);
        }
    }

    public static interface OnOverScrollListener {
        public void onOverScroll();
    }

    public static interface OnScrollListener {
        @Deprecated
        public void onAbsoluteScrollChange(int var1);

        public void onCentralPositionChanged(int var1);

        public void onScroll(int var1);

        public void onScrollStateChanged(int var1);
    }

    private class SetScrollVerticallyProperty
    extends Property<WearableListView, Integer> {
        public SetScrollVerticallyProperty() {
            super(Integer.class, "scrollVertically");
        }

        public Integer get(WearableListView wearableListView) {
            return wearableListView.mLastScrollChange;
        }

        public void set(WearableListView wearableListView, Integer n2) {
            wearableListView.setScrollVertically(n2);
        }
    }

    private static class SmoothScroller
    extends LinearSmoothScroller {
        private static final float MILLISECONDS_PER_INCH = 100.0f;
        private final LayoutManager mLayoutManager;

        public SmoothScroller(Context context, LayoutManager layoutManager) {
            super(context);
            this.mLayoutManager = layoutManager;
        }

        @Override
        public int calculateDtToFit(int n2, int n3, int n4, int n5, int n6) {
            return (n4 + n5) / 2 - (n2 + n3) / 2;
        }

        @Override
        protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
            return 100.0f / (float)displayMetrics.densityDpi;
        }

        @Override
        public PointF computeScrollVectorForPosition(int n2) {
            if (n2 < this.mLayoutManager.getFirstPosition()) {
                return new PointF(0.0f, -1.0f);
            }
            return new PointF(0.0f, 1.0f);
        }

        @Override
        protected void onStart() {
            super.onStart();
        }
    }

    public static class ViewHolder
    extends RecyclerView.ViewHolder {
        public ViewHolder(View view) {
            super(view);
        }

        protected void onCenterProximity(boolean bl2, boolean bl3) {
            if (!(this.itemView instanceof OnCenterProximityListener)) {
                return;
            }
            OnCenterProximityListener onCenterProximityListener = (OnCenterProximityListener)this.itemView;
            if (bl2) {
                onCenterProximityListener.onCenterPosition(bl3);
                return;
            }
            onCenterProximityListener.onNonCenterPosition(bl3);
        }
    }
}

