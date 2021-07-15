package com.riyuxihe.weixinqingliao;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;
//import android.support.v4.media.TransportMediator;
import android.support.v4.os.ParcelableCompat;
import android.support.v4.os.ParcelableCompatCreatorCallbacks;
import android.support.v4.view.AccessibilityDelegateCompat;
import android.support.v4.view.KeyEventCompat;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.VelocityTrackerCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewConfigurationCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v4.view.accessibility.AccessibilityRecordCompat;
import android.support.v4.widget.EdgeEffectCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.FocusFinder;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SoundEffectConstants;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.animation.Interpolator;
import android.widget.Scroller;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class VerticalViewPager extends ViewGroup {
    private static final int CLOSE_ENOUGH = 2;
    private static final Comparator<ItemInfo> COMPARATOR = new Comparator<ItemInfo>() {
        public int compare(ItemInfo lhs, ItemInfo rhs) {
            return lhs.position - rhs.position;
        }
    };
    private static final boolean DEBUG = false;
    private static final int DEFAULT_GUTTER_SIZE = 16;
    private static final int DEFAULT_OFFSCREEN_PAGES = 1;
    private static final int DRAW_ORDER_DEFAULT = 0;
    private static final int DRAW_ORDER_FORWARD = 1;
    private static final int DRAW_ORDER_REVERSE = 2;
    private static final int INVALID_POINTER = -1;
    /* access modifiers changed from: private */
    public static final int[] LAYOUT_ATTRS = {16842931};
    private static final int MAX_SETTLE_DURATION = 600;
    private static final int MIN_DISTANCE_FOR_FLING = 25;
    private static final int MIN_FLING_VELOCITY = 400;
    public static final int SCROLL_STATE_DRAGGING = 1;
    public static final int SCROLL_STATE_IDLE = 0;
    public static final int SCROLL_STATE_SETTLING = 2;
    private static final String TAG = "ViewPager";
    private static final boolean USE_CACHE = false;
    private static final Interpolator sInterpolator = new Interpolator() {
        public float getInterpolation(float t) {
            float t2 = t - 1.0f;
            return (t2 * t2 * t2 * t2 * t2) + 1.0f;
        }
    };
    private static final ViewPositionComparator sPositionComparator = new ViewPositionComparator();
    private int mActivePointerId = -1;
    /* access modifiers changed from: private */
    public PagerAdapter mAdapter;
    private OnAdapterChangeListener mAdapterChangeListener;
    private EdgeEffectCompat mBottomEdge;
    private boolean mCalledSuper;
    private int mChildHeightMeasureSpec;
    private int mChildWidthMeasureSpec;
    private int mCloseEnough;
    /* access modifiers changed from: private */
    public int mCurItem;
    private int mDecorChildCount;
    private int mDefaultGutterSize;
    private int mDrawingOrder;
    private ArrayList<View> mDrawingOrderedChildren;
    private final Runnable mEndScrollRunnable = new Runnable() {
        public void run() {
            VerticalViewPager.this.setScrollState(0);
            VerticalViewPager.this.populate();
        }
    };
    private int mExpectedAdapterCount;
    private long mFakeDragBeginTime;
    private boolean mFakeDragging;
    private boolean mFirstLayout = true;
    private float mFirstOffset = -3.4028235E38f;
    private int mFlingDistance;
    private int mGutterSize;
    private boolean mIgnoreGutter;
    private boolean mInLayout;
    private float mInitialMotionX;
    private float mInitialMotionY;
    private ViewPager.OnPageChangeListener mInternalPageChangeListener;
    private boolean mIsBeingDragged;
    private boolean mIsUnableToDrag;
    private final ArrayList<ItemInfo> mItems = new ArrayList<>();
    private float mLastMotionX;
    private float mLastMotionY;
    private float mLastOffset = Float.MAX_VALUE;
    private int mLeftPageBounds;
    private Drawable mMarginDrawable;
    private int mMaximumVelocity;
    private int mMinimumVelocity;
    private boolean mNeedCalculatePageOffsets = false;
    private PagerObserver mObserver;
    private int mOffscreenPageLimit = 1;
    private ViewPager.OnPageChangeListener mOnPageChangeListener;
    private int mPageMargin;
    private ViewPager.PageTransformer mPageTransformer;
    private boolean mPopulatePending;
    private Parcelable mRestoredAdapterState = null;
    private ClassLoader mRestoredClassLoader = null;
    private int mRestoredCurItem = -1;
    private int mRightPageBounds;
    private int mScrollState = 0;
    private Scroller mScroller;
    private boolean mScrollingCacheEnabled;
    private Method mSetChildrenDrawingOrderEnabled;
    private final ItemInfo mTempItem = new ItemInfo();
    private final Rect mTempRect = new Rect();
    private EdgeEffectCompat mTopEdge;
    private int mTouchSlop;
    private VelocityTracker mVelocityTracker;

    interface Decor {
    }

    interface OnAdapterChangeListener {
        void onAdapterChanged(PagerAdapter pagerAdapter, PagerAdapter pagerAdapter2);
    }

    static class ItemInfo {
        float heightFactor;
        Object object;
        float offset;
        int position;
        boolean scrolling;

        ItemInfo() {
        }
    }

    public VerticalViewPager(Context context) {
        super(context);
        initViewPager();
    }

    public VerticalViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViewPager();
    }

    /* access modifiers changed from: package-private */
    public void initViewPager() {
        setWillNotDraw(false);
        setDescendantFocusability(262144);
        setFocusable(true);
        Context context = getContext();
        this.mScroller = new Scroller(context, sInterpolator);
        ViewConfiguration configuration = ViewConfiguration.get(context);
        float density = context.getResources().getDisplayMetrics().density;
        this.mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(configuration);
        this.mMinimumVelocity = (int) (400.0f * density);
        this.mMaximumVelocity = configuration.getScaledMaximumFlingVelocity();
        this.mTopEdge = new EdgeEffectCompat(context);
        this.mBottomEdge = new EdgeEffectCompat(context);
        this.mFlingDistance = (int) (25.0f * density);
        this.mCloseEnough = (int) (2.0f * density);
        this.mDefaultGutterSize = (int) (16.0f * density);
        ViewCompat.setAccessibilityDelegate(this, new MyAccessibilityDelegate());
        if (ViewCompat.getImportantForAccessibility(this) == 0) {
            ViewCompat.setImportantForAccessibility(this, 1);
        }
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        removeCallbacks(this.mEndScrollRunnable);
        super.onDetachedFromWindow();
    }

    /* access modifiers changed from: private */
    public void setScrollState(int newState) {
        if (this.mScrollState != newState) {
            this.mScrollState = newState;
            if (this.mPageTransformer != null) {
                enableLayers(newState != 0);
            }
            if (this.mOnPageChangeListener != null) {
                this.mOnPageChangeListener.onPageScrollStateChanged(newState);
            }
        }
    }

    public void setAdapter(PagerAdapter adapter) {
        if (this.mAdapter != null) {
            this.mAdapter.unregisterDataSetObserver(this.mObserver);
            this.mAdapter.startUpdate(this);
            for (int i = 0; i < this.mItems.size(); i++) {
                ItemInfo ii = this.mItems.get(i);
                this.mAdapter.destroyItem(this, ii.position, ii.object);
            }
            this.mAdapter.finishUpdate(this);
            this.mItems.clear();
            removeNonDecorViews();
            this.mCurItem = 0;
            scrollTo(0, 0);
        }
        PagerAdapter oldAdapter = this.mAdapter;
        this.mAdapter = adapter;
        this.mExpectedAdapterCount = 0;
        if (this.mAdapter != null) {
            if (this.mObserver == null) {
                this.mObserver = new PagerObserver();
            }
            this.mAdapter.registerDataSetObserver(this.mObserver);
            this.mPopulatePending = false;
            boolean wasFirstLayout = this.mFirstLayout;
            this.mFirstLayout = true;
            this.mExpectedAdapterCount = this.mAdapter.getCount();
            if (this.mRestoredCurItem >= 0) {
                this.mAdapter.restoreState(this.mRestoredAdapterState, this.mRestoredClassLoader);
                setCurrentItemInternal(this.mRestoredCurItem, false, true);
                this.mRestoredCurItem = -1;
                this.mRestoredAdapterState = null;
                this.mRestoredClassLoader = null;
            } else if (!wasFirstLayout) {
                populate();
            } else {
                requestLayout();
            }
        }
        if (this.mAdapterChangeListener != null && oldAdapter != adapter) {
            this.mAdapterChangeListener.onAdapterChanged(oldAdapter, adapter);
        }
    }

    private void removeNonDecorViews() {
        int i = 0;
        while (i < getChildCount()) {
            if (!((LayoutParams) getChildAt(i).getLayoutParams()).isDecor) {
                removeViewAt(i);
                i--;
            }
            i++;
        }
    }

    public PagerAdapter getAdapter() {
        return this.mAdapter;
    }

    /* access modifiers changed from: package-private */
    public void setOnAdapterChangeListener(OnAdapterChangeListener listener) {
        this.mAdapterChangeListener = listener;
    }

    private int getClientHeight() {
        return (getMeasuredHeight() - getPaddingTop()) - getPaddingBottom();
    }

    public void setCurrentItem(int item) {
        boolean z;
        this.mPopulatePending = false;
        z = !this.mFirstLayout;
        setCurrentItemInternal(item, z, false);
    }

    public void setCurrentItem(int item, boolean smoothScroll) {
        this.mPopulatePending = false;
        setCurrentItemInternal(item, smoothScroll, false);
    }

    public int getCurrentItem() {
        return this.mCurItem;
    }

    /* access modifiers changed from: package-private */
    public void setCurrentItemInternal(int item, boolean smoothScroll, boolean always) {
        setCurrentItemInternal(item, smoothScroll, always, 0);
    }

    /* access modifiers changed from: package-private */
    public void setCurrentItemInternal(int item, boolean smoothScroll, boolean always, int velocity) {
        boolean dispatchSelected = true;
        if (this.mAdapter == null || this.mAdapter.getCount() <= 0) {
            setScrollingCacheEnabled(false);
        } else if (always || this.mCurItem != item || this.mItems.size() == 0) {
            if (item < 0) {
                item = 0;
            } else if (item >= this.mAdapter.getCount()) {
                item = this.mAdapter.getCount() - 1;
            }
            int pageLimit = this.mOffscreenPageLimit;
            if (item > this.mCurItem + pageLimit || item < this.mCurItem - pageLimit) {
                for (int i = 0; i < this.mItems.size(); i++) {
                    this.mItems.get(i).scrolling = true;
                }
            }
            if (this.mCurItem == item) {
                dispatchSelected = false;
            }
            if (this.mFirstLayout) {
                this.mCurItem = item;
                if (dispatchSelected && this.mOnPageChangeListener != null) {
                    this.mOnPageChangeListener.onPageSelected(item);
                }
                if (dispatchSelected && this.mInternalPageChangeListener != null) {
                    this.mInternalPageChangeListener.onPageSelected(item);
                }
                requestLayout();
                return;
            }
            populate(item);
            scrollToItem(item, smoothScroll, velocity, dispatchSelected);
        } else {
            setScrollingCacheEnabled(false);
        }
    }

    private void scrollToItem(int item, boolean smoothScroll, int velocity, boolean dispatchSelected) {
        ItemInfo curInfo = infoForPosition(item);
        int destY = 0;
        if (curInfo != null) {
            destY = (int) (((float) getClientHeight()) * Math.max(this.mFirstOffset, Math.min(curInfo.offset, this.mLastOffset)));
        }
        if (smoothScroll) {
            smoothScrollTo(0, destY, velocity);
            if (dispatchSelected && this.mOnPageChangeListener != null) {
                this.mOnPageChangeListener.onPageSelected(item);
            }
            if (dispatchSelected && this.mInternalPageChangeListener != null) {
                this.mInternalPageChangeListener.onPageSelected(item);
                return;
            }
            return;
        }
        if (dispatchSelected && this.mOnPageChangeListener != null) {
            this.mOnPageChangeListener.onPageSelected(item);
        }
        if (dispatchSelected && this.mInternalPageChangeListener != null) {
            this.mInternalPageChangeListener.onPageSelected(item);
        }
        completeScroll(false);
        scrollTo(0, destY);
        pageScrolled(destY);
    }

    public void setOnPageChangeListener(ViewPager.OnPageChangeListener listener) {
        this.mOnPageChangeListener = listener;
    }

    public void setPageTransformer(boolean reverseDrawingOrder, ViewPager.PageTransformer transformer) {
        boolean z;
        int i = 1;
        if (Build.VERSION.SDK_INT >= 11) {
            boolean hasTransformer = transformer != null;
            z = this.mPageTransformer != null;
            boolean needsPopulate = hasTransformer != z;
            this.mPageTransformer = transformer;
            setChildrenDrawingOrderEnabledCompat(hasTransformer);
            if (hasTransformer) {
                if (reverseDrawingOrder) {
                    i = 2;
                }
                this.mDrawingOrder = i;
            } else {
                this.mDrawingOrder = 0;
            }
            if (needsPopulate) {
                populate();
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void setChildrenDrawingOrderEnabledCompat(boolean enable) {
        if (Build.VERSION.SDK_INT >= 7) {
            if (this.mSetChildrenDrawingOrderEnabled == null) {
                Class<ViewGroup> cls = ViewGroup.class;
                try {
                    this.mSetChildrenDrawingOrderEnabled = cls.getDeclaredMethod("setChildrenDrawingOrderEnabled", Boolean.TYPE);
                } catch (NoSuchMethodException e) {
                    Log.e(TAG, "Can't find setChildrenDrawingOrderEnabled", e);
                }
            }
            try {
                this.mSetChildrenDrawingOrderEnabled.invoke(this, Boolean.valueOf(enable));
            } catch (Exception e2) {
                Log.e(TAG, "Error changing children drawing order", e2);
            }
        }
    }

    /* access modifiers changed from: protected */
    public int getChildDrawingOrder(int childCount, int i) {
        int index;
        if (this.mDrawingOrder == 2) {
            index = (childCount - 1) - i;
        } else {
            index = i;
        }
        return ((LayoutParams) this.mDrawingOrderedChildren.get(index).getLayoutParams()).childIndex;
    }

    /* access modifiers changed from: package-private */
    public ViewPager.OnPageChangeListener setInternalPageChangeListener(ViewPager.OnPageChangeListener listener) {
        ViewPager.OnPageChangeListener oldListener = this.mInternalPageChangeListener;
        this.mInternalPageChangeListener = listener;
        return oldListener;
    }

    public int getOffscreenPageLimit() {
        return this.mOffscreenPageLimit;
    }

    public void setOffscreenPageLimit(int limit) {
        if (limit < 1) {
            Log.w(TAG, "Requested offscreen page limit " + limit + " too small; defaulting to " + 1);
            limit = 1;
        }
        if (limit != this.mOffscreenPageLimit) {
            this.mOffscreenPageLimit = limit;
            populate();
        }
    }

    public void setPageMargin(int marginPixels) {
        int oldMargin = this.mPageMargin;
        this.mPageMargin = marginPixels;
        int height = getHeight();
        recomputeScrollPosition(height, height, marginPixels, oldMargin);
        requestLayout();
    }

    public int getPageMargin() {
        return this.mPageMargin;
    }

    public void setPageMarginDrawable(Drawable d) {
        this.mMarginDrawable = d;
        if (d != null) {
            refreshDrawableState();
        }
        setWillNotDraw(d == null);
        invalidate();
    }

    public void setPageMarginDrawable(int resId) {
        setPageMarginDrawable(getContext().getResources().getDrawable(resId));
    }

    /* access modifiers changed from: protected */
    public boolean verifyDrawable(Drawable who) {
        return super.verifyDrawable(who) || who == this.mMarginDrawable;
    }

    /* access modifiers changed from: protected */
    public void drawableStateChanged() {
        super.drawableStateChanged();
        Drawable d = this.mMarginDrawable;
        if (d != null && d.isStateful()) {
            d.setState(getDrawableState());
        }
    }

    /* access modifiers changed from: package-private */
    public float distanceInfluenceForSnapDuration(float f) {
        return (float) Math.sin((float) (((double) (f - 0.5f)) * 0.4712389167638204d));
    }

    /* access modifiers changed from: package-private */
    public void smoothScrollTo(int x, int y) {
        smoothScrollTo(x, y, 0);
    }

    /* access modifiers changed from: package-private */
    public void smoothScrollTo(int x, int y, int velocity) {
        int duration;
        if (getChildCount() == 0) {
            setScrollingCacheEnabled(false);
            return;
        }
        int sx = getScrollX();
        int sy = getScrollY();
        int dx = x - sx;
        int dy = y - sy;
        if (dx == 0 && dy == 0) {
            completeScroll(false);
            populate();
            setScrollState(0);
            return;
        }
        setScrollingCacheEnabled(true);
        setScrollState(2);
        int height = getClientHeight();
        int halfHeight = height / 2;
        float distance = ((float) halfHeight) + (((float) halfHeight) * distanceInfluenceForSnapDuration(Math.min(1.0f, (1.0f * ((float) Math.abs(dx))) / ((float) height))));
        int velocity2 = Math.abs(velocity);
        if (velocity2 > 0) {
            duration = Math.round(1000.0f * Math.abs(distance / ((float) velocity2))) * 4;
        } else {
            duration = (int) ((1.0f + (((float) Math.abs(dx)) / (((float) this.mPageMargin) + (((float) height) * this.mAdapter.getPageWidth(this.mCurItem))))) * 100.0f);
        }
        this.mScroller.startScroll(sx, sy, dx, dy, Math.min(duration, MAX_SETTLE_DURATION));
        ViewCompat.postInvalidateOnAnimation(this);
    }

    /* access modifiers changed from: package-private */
    public ItemInfo addNewItem(int position, int index) {
        ItemInfo ii = new ItemInfo();
        ii.position = position;
        ii.object = this.mAdapter.instantiateItem(this, position);
        ii.heightFactor = this.mAdapter.getPageWidth(position);
        if (index < 0 || index >= this.mItems.size()) {
            this.mItems.add(ii);
        } else {
            this.mItems.add(index, ii);
        }
        return ii;
    }

    /* access modifiers changed from: package-private */
    public void dataSetChanged() {
        boolean needPopulate;
        int adapterCount = this.mAdapter.getCount();
        this.mExpectedAdapterCount = adapterCount;
        needPopulate = this.mItems.size() < (this.mOffscreenPageLimit * 2) + 1 && this.mItems.size() < adapterCount;
        int newCurrItem = this.mCurItem;
        boolean isUpdating = false;
        int i = 0;
        while (i < this.mItems.size()) {
            ItemInfo ii = this.mItems.get(i);
            int newPos = this.mAdapter.getItemPosition(ii.object);
            if (newPos != -1) {
                if (newPos == -2) {
                    this.mItems.remove(i);
                    i--;
                    if (!isUpdating) {
                        this.mAdapter.startUpdate(this);
                        isUpdating = true;
                    }
                    this.mAdapter.destroyItem(this, ii.position, ii.object);
                    needPopulate = true;
                    if (this.mCurItem == ii.position) {
                        newCurrItem = Math.max(0, Math.min(this.mCurItem, adapterCount - 1));
                        needPopulate = true;
                    }
                } else if (ii.position != newPos) {
                    if (ii.position == this.mCurItem) {
                        newCurrItem = newPos;
                    }
                    ii.position = newPos;
                    needPopulate = true;
                }
            }
            i++;
        }
        if (isUpdating) {
            this.mAdapter.finishUpdate(this);
        }
        Collections.sort(this.mItems, COMPARATOR);
        if (needPopulate) {
            int childCount = getChildCount();
            for (int i2 = 0; i2 < childCount; i2++) {
                LayoutParams lp = (LayoutParams) getChildAt(i2).getLayoutParams();
                if (!lp.isDecor) {
                    lp.heightFactor = 0.0f;
                }
            }
            setCurrentItemInternal(newCurrItem, false, true);
            requestLayout();
        }
    }

    /* access modifiers changed from: package-private */
    public void populate() {
        populate(this.mCurItem);
    }

    /* access modifiers changed from: package-private */
    public void populate(int newCurrentItem) {
        ItemInfo ii;
        float topHeightNeeded;
        float bottomHeightNeeded;
        String resName;
        ItemInfo oldCurInfo = null;
        int focusDirection = 2;
        if (this.mCurItem != newCurrentItem) {
            focusDirection = 33;
            oldCurInfo = infoForPosition(this.mCurItem);
            this.mCurItem = newCurrentItem;
        }
        if (this.mAdapter == null) {
            sortChildDrawingOrder();
        } else if (this.mPopulatePending) {
            sortChildDrawingOrder();
        } else if (getWindowToken() != null) {
            this.mAdapter.startUpdate(this);
            int pageLimit = this.mOffscreenPageLimit;
            int startPos = Math.max(0, this.mCurItem - pageLimit);
            int N = this.mAdapter.getCount();
            int endPos = Math.min(N - 1, this.mCurItem + pageLimit);
            if (N != this.mExpectedAdapterCount) {
                try {
                    resName = getResources().getResourceName(getId());
                } catch (Resources.NotFoundException e) {
                    resName = Integer.toHexString(getId());
                }
                throw new IllegalStateException("The application's PagerAdapter changed the adapter's contents without calling PagerAdapter#notifyDataSetChanged! Expected adapter item count: " + this.mExpectedAdapterCount + ", found: " + N + " Pager id: " + resName + " Pager class: " + getClass() + " Problematic adapter: " + this.mAdapter.getClass());
            }
            ItemInfo curItem = null;
            int curIndex = 0;
            while (true) {
                if (curIndex >= this.mItems.size()) {
                    break;
                }
                ItemInfo ii2 = this.mItems.get(curIndex);
                if (ii2.position >= this.mCurItem) {
                    if (ii2.position == this.mCurItem) {
                        curItem = ii2;
                    }
                } else {
                    curIndex++;
                }
            }
            if (curItem == null && N > 0) {
                curItem = addNewItem(this.mCurItem, curIndex);
            }
            if (curItem != null) {
                float extraHeightTop = 0.0f;
                int itemIndex = curIndex - 1;
                ItemInfo ii3 = itemIndex >= 0 ? this.mItems.get(itemIndex) : null;
                int clientHeight = getClientHeight();
                if (clientHeight <= 0) {
                    topHeightNeeded = 0.0f;
                } else {
                    topHeightNeeded = (2.0f - curItem.heightFactor) + (((float) getPaddingLeft()) / ((float) clientHeight));
                }
                for (int pos = this.mCurItem - 1; pos >= 0; pos--) {
                    if (extraHeightTop < topHeightNeeded || pos >= startPos) {
                        if (ii3 == null || pos != ii3.position) {
                            extraHeightTop += addNewItem(pos, itemIndex + 1).heightFactor;
                            curIndex++;
                            ii3 = itemIndex >= 0 ? this.mItems.get(itemIndex) : null;
                        } else {
                            extraHeightTop += ii3.heightFactor;
                            itemIndex--;
                            ii3 = itemIndex >= 0 ? this.mItems.get(itemIndex) : null;
                        }
                    } else if (ii3 == null) {
                        break;
                    } else {
                        if (pos == ii3.position && !ii3.scrolling) {
                            this.mItems.remove(itemIndex);
                            this.mAdapter.destroyItem(this, pos, ii3.object);
                            itemIndex--;
                            curIndex--;
                            if (itemIndex >= 0) {
                                ii3 = this.mItems.get(itemIndex);
                            } else {
                                ii3 = null;
                            }
                        }
                    }
                }
                float extraHeightBottom = curItem.heightFactor;
                int itemIndex2 = curIndex + 1;
                if (extraHeightBottom < 2.0f) {
                    ItemInfo ii4 = itemIndex2 < this.mItems.size() ? this.mItems.get(itemIndex2) : null;
                    if (clientHeight <= 0) {
                        bottomHeightNeeded = 0.0f;
                    } else {
                        bottomHeightNeeded = (((float) getPaddingRight()) / ((float) clientHeight)) + 2.0f;
                    }
                    for (int pos2 = this.mCurItem + 1; pos2 < N; pos2++) {
                        if (extraHeightBottom < bottomHeightNeeded || pos2 <= endPos) {
                            if (ii4 == null || pos2 != ii4.position) {
                                itemIndex2++;
                                extraHeightBottom += addNewItem(pos2, itemIndex2).heightFactor;
                                ii4 = itemIndex2 < this.mItems.size() ? this.mItems.get(itemIndex2) : null;
                            } else {
                                extraHeightBottom += ii4.heightFactor;
                                itemIndex2++;
                                ii4 = itemIndex2 < this.mItems.size() ? this.mItems.get(itemIndex2) : null;
                            }
                        } else if (ii4 == null) {
                            break;
                        } else {
                            if (pos2 == ii4.position && !ii4.scrolling) {
                                this.mItems.remove(itemIndex2);
                                this.mAdapter.destroyItem(this, pos2, ii4.object);
                                if (itemIndex2 < this.mItems.size()) {
                                    ii4 = this.mItems.get(itemIndex2);
                                } else {
                                    ii4 = null;
                                }
                            }
                        }
                    }
                }
                calculatePageOffsets(curItem, curIndex, oldCurInfo);
            }
            this.mAdapter.setPrimaryItem(this, this.mCurItem, curItem != null ? curItem.object : null);
            this.mAdapter.finishUpdate(this);
            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                View child = getChildAt(i);
                LayoutParams lp = (LayoutParams) child.getLayoutParams();
                lp.childIndex = i;
                if (!lp.isDecor && lp.heightFactor == 0.0f && (ii = infoForChild(child)) != null) {
                    lp.heightFactor = ii.heightFactor;
                    lp.position = ii.position;
                }
            }
            sortChildDrawingOrder();
            if (hasFocus()) {
                View currentFocused = findFocus();
                ItemInfo ii5 = currentFocused != null ? infoForAnyChild(currentFocused) : null;
                if (ii5 == null || ii5.position != this.mCurItem) {
                    int i2 = 0;
                    while (i2 < getChildCount()) {
                        View child2 = getChildAt(i2);
                        ItemInfo ii6 = infoForChild(child2);
                        if (ii6 == null || ii6.position != this.mCurItem || !child2.requestFocus(focusDirection)) {
                            i2++;
                        } else {
                            return;
                        }
                    }
                }
            }
        }
    }

    private void sortChildDrawingOrder() {
        if (this.mDrawingOrder != 0) {
            if (this.mDrawingOrderedChildren == null) {
                this.mDrawingOrderedChildren = new ArrayList<>();
            } else {
                this.mDrawingOrderedChildren.clear();
            }
            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                this.mDrawingOrderedChildren.add(getChildAt(i));
            }
            Collections.sort(this.mDrawingOrderedChildren, sPositionComparator);
        }
    }

    private void calculatePageOffsets(ItemInfo itemInfo, int n2, ItemInfo itemInfo2) {
        int n3;
        float f2;
        float f3;
        int n4;
        float f4;
        int n5;
        int n6;
        block12:
        {
            block15:
            {
                block14:
                {
                    block13:
                    {
                        n6 = this.mAdapter.getCount();
                        n5 = this.getClientHeight();
                        f4 = n5 > 0 ? (float) this.mPageMargin / (float) n5 : 0.0f;
                        if (itemInfo2 == null) break block12;
                        n5 = itemInfo2.position;
                        if (n5 >= itemInfo.position) break block13;
                        n4 = 0;
                        f3 = itemInfo2.offset + itemInfo2.heightFactor + f4;
                        ++n5;
                        break block14;
                    }
                    if (n5 <= itemInfo.position) break block12;
                    n4 = this.mItems.size() - 1;
                    f3 = itemInfo2.offset;
                    --n5;
                    break block15;
                }
                while (n5 <= itemInfo.position && n4 < this.mItems.size()) {
                    itemInfo2 = this.mItems.get(n4);
                    while (true) {
                        f2 = f3;
                        n3 = n5;
                        if (n5 <= itemInfo2.position) break;
                        f2 = f3;
                        n3 = n5;
                        if (n4 >= this.mItems.size() - 1) break;
                        itemInfo2 = this.mItems.get(++n4);
                    }
                    while (n3 < itemInfo2.position) {
                        f2 += this.mAdapter.getPageWidth(n3) + f4;
                        ++n3;
                    }
                    itemInfo2.offset = f2;
                    f3 = f2 + (itemInfo2.heightFactor + f4);
                    n5 = n3 + 1;
                }
                break block12;
            }
            while (n5 >= itemInfo.position && n4 >= 0) {
                itemInfo2 = this.mItems.get(n4);
                while (true) {
                    f2 = f3;
                    n3 = n5;
                    if (n5 >= itemInfo2.position) break;
                    f2 = f3;
                    n3 = n5;
                    if (n4 <= 0) break;
                    itemInfo2 = this.mItems.get(--n4);
                }
                while (n3 > itemInfo2.position) {
                    f2 -= this.mAdapter.getPageWidth(n3) + f4;
                    --n3;
                }
                itemInfo2.offset = f3 = f2 - (itemInfo2.heightFactor + f4);
                n5 = n3 - 1;
            }
        }
        n3 = this.mItems.size();
        f2 = itemInfo.offset;
        n5 = itemInfo.position - 1;
        f3 = itemInfo.position == 0 ? itemInfo.offset : -3.4028235E38f;
        this.mFirstOffset = f3;
        f3 = itemInfo.position == n6 - 1 ? itemInfo.offset + itemInfo.heightFactor - 1.0f : Float.MAX_VALUE;
        this.mLastOffset = f3;
        f3 = f2;
        for (n4 = n2 - 1; n4 >= 0; --n4, --n5) {
            itemInfo2 = this.mItems.get(n4);
            while (n5 > itemInfo2.position) {
                f3 -= this.mAdapter.getPageWidth(n5) + f4;
                --n5;
            }
            itemInfo2.offset = f3 -= itemInfo2.heightFactor + f4;
            if (itemInfo2.position != 0) continue;
            this.mFirstOffset = f3;
        }
        f3 = itemInfo.offset + itemInfo.heightFactor + f4;
        n5 = itemInfo.position + 1;
        n4 = n2 + 1;
        n2 = n5;
        n5 = n4;
        while (true) {
            if (n5 >= n3) {
                this.mNeedCalculatePageOffsets = false;
                return;
            }
            itemInfo = this.mItems.get(n5);
            while (n2 < itemInfo.position) {
                f3 += this.mAdapter.getPageWidth(n2) + f4;
                ++n2;
            }
            if (itemInfo.position == n6 - 1) {
                this.mLastOffset = itemInfo.heightFactor + f3 - 1.0f;
            }
            itemInfo.offset = f3;
            f3 += itemInfo.heightFactor + f4;
            ++n5;
            ++n2;
        }
    }

    public static class SavedState extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = ParcelableCompat.newCreator(new ParcelableCompatCreatorCallbacks<SavedState>() {
            public SavedState createFromParcel(Parcel in, ClassLoader loader) {
                return new SavedState(in, loader);
            }

            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        });
        Parcelable adapterState;
        ClassLoader loader;
        int position;

        public SavedState(Parcelable superState) {
            super(superState);
        }

        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(this.position);
            out.writeParcelable(this.adapterState, flags);
        }

        public String toString() {
            return "FragmentPager.SavedState{" + Integer.toHexString(System.identityHashCode(this)) + " position=" + this.position + "}";
        }

        SavedState(Parcel in, ClassLoader loader2) {
            super(in);
            loader2 = loader2 == null ? getClass().getClassLoader() : loader2;
            this.position = in.readInt();
            this.adapterState = in.readParcelable(loader2);
            this.loader = loader2;
        }
    }

    public Parcelable onSaveInstanceState() {
        SavedState ss = new SavedState(super.onSaveInstanceState());
        ss.position = this.mCurItem;
        if (this.mAdapter != null) {
            ss.adapterState = this.mAdapter.saveState();
        }
        return ss;
    }

    public void onRestoreInstanceState(Parcelable state) {
        if (!(state instanceof SavedState)) {
            super.onRestoreInstanceState(state);
            return;
        }
        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        if (this.mAdapter != null) {
            this.mAdapter.restoreState(ss.adapterState, ss.loader);
            setCurrentItemInternal(ss.position, false, true);
            return;
        }
        this.mRestoredCurItem = ss.position;
        this.mRestoredAdapterState = ss.adapterState;
        this.mRestoredClassLoader = ss.loader;
    }

    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        if (!checkLayoutParams(params)) {
            params = generateLayoutParams(params);
        }
        LayoutParams lp = (LayoutParams) params;
        lp.isDecor |= child instanceof Decor;
        if (!this.mInLayout) {
            super.addView(child, index, params);
        } else if (lp == null || !lp.isDecor) {
            lp.needsMeasure = true;
            addViewInLayout(child, index, params);
        } else {
            throw new IllegalStateException("Cannot add pager decor view during layout");
        }
    }

    public void removeView(View view) {
        if (this.mInLayout) {
            removeViewInLayout(view);
        } else {
            super.removeView(view);
        }
    }

    /* access modifiers changed from: package-private */
    public ItemInfo infoForChild(View child) {
        for (int i = 0; i < this.mItems.size(); i++) {
            ItemInfo ii = this.mItems.get(i);
            if (this.mAdapter.isViewFromObject(child, ii.object)) {
                return ii;
            }
        }
        return null;
    }

    ItemInfo infoForAnyChild(View view) {
        ViewParent viewParent;
        while ((viewParent = view.getParent()) != this) {
            if (viewParent == null || !(viewParent instanceof View)) {
                return null;
            }
            view = (View)viewParent;
        }
        return this.infoForChild(view);
    }


    /* access modifiers changed from: package-private */
    public ItemInfo infoForPosition(int position) {
        for (int i = 0; i < this.mItems.size(); i++) {
            ItemInfo ii = this.mItems.get(i);
            if (ii.position == position) {
                return ii;
            }
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mFirstLayout = true;
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        LayoutParams lp;
        LayoutParams lp2;
        setMeasuredDimension(getDefaultSize(0, widthMeasureSpec), getDefaultSize(0, heightMeasureSpec));
        int measuredHeight = getMeasuredHeight();
        this.mGutterSize = Math.min(measuredHeight / 10, this.mDefaultGutterSize);
        int childWidthSize = (getMeasuredWidth() - getPaddingLeft()) - getPaddingRight();
        int childHeightSize = (measuredHeight - getPaddingTop()) - getPaddingBottom();
        int size = getChildCount();
        for (int i = 0; i < size; i++) {
            View child = getChildAt(i);
            if (!(child.getVisibility() == 8 || (lp2 = (LayoutParams) child.getLayoutParams()) == null || !lp2.isDecor)) {
                int hgrav = lp2.gravity & 7;
                int vgrav = lp2.gravity & 112;
                int widthMode = Integer.MIN_VALUE;
                int heightMode = Integer.MIN_VALUE;
                boolean consumeVertical = vgrav == 48 || vgrav == 80;
                boolean consumeHorizontal = hgrav == 3 || hgrav == 5;
                if (consumeVertical) {
                    widthMode = 1073741824;
                } else if (consumeHorizontal) {
                    heightMode = 1073741824;
                }
                int widthSize = childWidthSize;
                int heightSize = childHeightSize;
                if (lp2.width != -2) {
                    widthMode = 1073741824;
                    if (lp2.width != -1) {
                        widthSize = lp2.width;
                    }
                }
                if (lp2.height != -2) {
                    heightMode = 1073741824;
                    if (lp2.height != -1) {
                        heightSize = lp2.height;
                    }
                }
                child.measure(View.MeasureSpec.makeMeasureSpec(widthSize, widthMode), View.MeasureSpec.makeMeasureSpec(heightSize, heightMode));
                if (consumeVertical) {
                    childHeightSize -= child.getMeasuredHeight();
                } else if (consumeHorizontal) {
                    childWidthSize -= child.getMeasuredWidth();
                }
            }
        }
        this.mChildWidthMeasureSpec = View.MeasureSpec.makeMeasureSpec(childWidthSize, 1073741824);
        this.mChildHeightMeasureSpec = View.MeasureSpec.makeMeasureSpec(childHeightSize, 1073741824);
        this.mInLayout = true;
        populate();
        this.mInLayout = false;
        int size2 = getChildCount();
        for (int i2 = 0; i2 < size2; i2++) {
            View child2 = getChildAt(i2);
            if (child2.getVisibility() != 8 && ((lp = (LayoutParams) child2.getLayoutParams()) == null || !lp.isDecor)) {
                child2.measure(this.mChildWidthMeasureSpec, View.MeasureSpec.makeMeasureSpec((int) (((float) childHeightSize) * lp.heightFactor), 1073741824));
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (h != oldh) {
            recomputeScrollPosition(h, oldh, this.mPageMargin, this.mPageMargin);
        }
    }

    private void recomputeScrollPosition(int height, int oldHeight, int margin, int oldMargin) {
        if (oldHeight <= 0 || this.mItems.isEmpty()) {
            ItemInfo ii = infoForPosition(this.mCurItem);
            int scrollPos = (int) (((float) ((height - getPaddingTop()) - getPaddingBottom())) * (ii != null ? Math.min(ii.offset, this.mLastOffset) : 0.0f));
            if (scrollPos != getScrollY()) {
                completeScroll(false);
                scrollTo(getScrollX(), scrollPos);
                return;
            }
            return;
        }
        int newOffsetPixels = (int) (((float) (((height - getPaddingTop()) - getPaddingBottom()) + margin)) * (((float) getScrollY()) / ((float) (((oldHeight - getPaddingTop()) - getPaddingBottom()) + oldMargin))));
        scrollTo(getScrollX(), newOffsetPixels);
        if (!this.mScroller.isFinished()) {
            this.mScroller.startScroll(0, newOffsetPixels, 0, (int) (infoForPosition(this.mCurItem).offset * ((float) height)), this.mScroller.getDuration() - this.mScroller.timePassed());
        }
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean changed, int l, int t, int r, int b) {
        ItemInfo ii;
        int childLeft;
        int childTop;
        int count = getChildCount();
        int width = r - l;
        int height = b - t;
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();
        int scrollY = getScrollY();
        int decorCount = 0;
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() != 8) {
                LayoutParams lp = (LayoutParams) child.getLayoutParams();
                if (lp.isDecor) {
                    int hgrav = lp.gravity & 7;
                    int vgrav = lp.gravity & 112;
                    switch (hgrav) {
                        case 1:
                            childLeft = Math.max((width - child.getMeasuredWidth()) / 2, paddingLeft);
                            break;
                        case 3:
                            childLeft = paddingLeft;
                            paddingLeft += child.getMeasuredWidth();
                            break;
                        case 5:
                            childLeft = (width - paddingRight) - child.getMeasuredWidth();
                            paddingRight += child.getMeasuredWidth();
                            break;
                        default:
                            childLeft = paddingLeft;
                            break;
                    }
                    switch (vgrav) {
                        case 16:
                            childTop = Math.max((height - child.getMeasuredHeight()) / 2, paddingTop);
                            break;
                        case 48:
                            childTop = paddingTop;
                            paddingTop += child.getMeasuredHeight();
                            break;
                        case 80:
                            childTop = (height - paddingBottom) - child.getMeasuredHeight();
                            paddingBottom += child.getMeasuredHeight();
                            break;
                        default:
                            childTop = paddingTop;
                            break;
                    }
                    int childTop2 = childTop + scrollY;
                    child.layout(childLeft, childTop2, child.getMeasuredWidth() + childLeft, child.getMeasuredHeight() + childTop2);
                    decorCount++;
                }
            }
        }
        int childHeight = (height - paddingTop) - paddingBottom;
        for (int i2 = 0; i2 < count; i2++) {
            View child2 = getChildAt(i2);
            if (child2.getVisibility() != 8) {
                LayoutParams lp2 = (LayoutParams) child2.getLayoutParams();
                if (!lp2.isDecor && (ii = infoForChild(child2)) != null) {
                    int childLeft2 = paddingLeft;
                    int childTop3 = paddingTop + ((int) (((float) childHeight) * ii.offset));
                    if (lp2.needsMeasure) {
                        lp2.needsMeasure = false;
                        child2.measure(View.MeasureSpec.makeMeasureSpec((width - paddingLeft) - paddingRight, 1073741824), View.MeasureSpec.makeMeasureSpec((int) (((float) childHeight) * lp2.heightFactor), 1073741824));
                    }
                    child2.layout(childLeft2, childTop3, child2.getMeasuredWidth() + childLeft2, child2.getMeasuredHeight() + childTop3);
                }
            }
        }
        this.mLeftPageBounds = paddingLeft;
        this.mRightPageBounds = width - paddingRight;
        this.mDecorChildCount = decorCount;
        if (this.mFirstLayout) {
            scrollToItem(this.mCurItem, false, 0, false);
        }
        this.mFirstLayout = false;
    }

    public void computeScroll() {
        if (this.mScroller.isFinished() || !this.mScroller.computeScrollOffset()) {
            completeScroll(true);
            return;
        }
        int oldX = getScrollX();
        int oldY = getScrollY();
        int x = this.mScroller.getCurrX();
        int y = this.mScroller.getCurrY();
        if (!(oldX == x && oldY == y)) {
            scrollTo(x, y);
            if (!pageScrolled(y)) {
                this.mScroller.abortAnimation();
                scrollTo(x, 0);
            }
        }
        ViewCompat.postInvalidateOnAnimation(this);
    }

    private boolean pageScrolled(int ypos) {
        if (this.mItems.size() == 0) {
            this.mCalledSuper = false;
            onPageScrolled(0, 0.0f, 0);
            if (this.mCalledSuper) {
                return false;
            }
            throw new IllegalStateException("onPageScrolled did not call superclass implementation");
        }
        ItemInfo ii = infoForCurrentScrollPosition();
        int height = getClientHeight();
        int heightWithMargin = height + this.mPageMargin;
        float marginOffset = ((float) this.mPageMargin) / ((float) height);
        int currentPage = ii.position;
        float pageOffset = ((((float) ypos) / ((float) height)) - ii.offset) / (ii.heightFactor + marginOffset);
        this.mCalledSuper = false;
        onPageScrolled(currentPage, pageOffset, (int) (((float) heightWithMargin) * pageOffset));
        if (this.mCalledSuper) {
            return true;
        }
        throw new IllegalStateException("onPageScrolled did not call superclass implementation");
    }

    /* access modifiers changed from: protected */
    public void onPageScrolled(int position, float offset, int offsetPixels) {
        int childTop;
        if (this.mDecorChildCount > 0) {
            int scrollY = getScrollY();
            int paddingTop = getPaddingTop();
            int paddingBottom = getPaddingBottom();
            int height = getHeight();
            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                View child = getChildAt(i);
                LayoutParams lp = (LayoutParams) child.getLayoutParams();
                if (lp.isDecor) {
                    switch (lp.gravity & 112) {
                        case 16:
                            childTop = Math.max((height - child.getMeasuredHeight()) / 2, paddingTop);
                            break;
                        case 48:
                            childTop = paddingTop;
                            paddingTop += child.getHeight();
                            break;
                        case 80:
                            childTop = (height - paddingBottom) - child.getMeasuredHeight();
                            paddingBottom += child.getMeasuredHeight();
                            break;
                        default:
                            childTop = paddingTop;
                            break;
                    }
                    int childOffset = (childTop + scrollY) - child.getTop();
                    if (childOffset != 0) {
                        child.offsetTopAndBottom(childOffset);
                    }
                }
            }
        }
        if (this.mOnPageChangeListener != null) {
            this.mOnPageChangeListener.onPageScrolled(position, offset, offsetPixels);
        }
        if (this.mInternalPageChangeListener != null) {
            this.mInternalPageChangeListener.onPageScrolled(position, offset, offsetPixels);
        }
        if (this.mPageTransformer != null) {
            int scrollY2 = getScrollY();
            int childCount2 = getChildCount();
            for (int i2 = 0; i2 < childCount2; i2++) {
                View child2 = getChildAt(i2);
                if (!((LayoutParams) child2.getLayoutParams()).isDecor) {
                    this.mPageTransformer.transformPage(child2, ((float) (child2.getTop() - scrollY2)) / ((float) getClientHeight()));
                }
            }
        }
        this.mCalledSuper = true;
    }

    private void completeScroll(boolean postEvents) {
        boolean needPopulate;
        needPopulate = this.mScrollState == 2;
        if (needPopulate) {
            setScrollingCacheEnabled(false);
            this.mScroller.abortAnimation();
            int oldX = getScrollX();
            int oldY = getScrollY();
            int x = this.mScroller.getCurrX();
            int y = this.mScroller.getCurrY();
            if (!(oldX == x && oldY == y)) {
                scrollTo(x, y);
            }
        }
        this.mPopulatePending = false;
        for (int i = 0; i < this.mItems.size(); i++) {
            ItemInfo ii = this.mItems.get(i);
            if (ii.scrolling) {
                needPopulate = true;
                ii.scrolling = false;
            }
        }
        if (!needPopulate) {
            return;
        }
        if (postEvents) {
            ViewCompat.postOnAnimation(this, this.mEndScrollRunnable);
        } else {
            this.mEndScrollRunnable.run();
        }
    }

    private boolean isGutterDrag(float y, float dy) {
        return (y < ((float) this.mGutterSize) && dy > 0.0f) || (y > ((float) (getHeight() - this.mGutterSize)) && dy < 0.0f);
    }

    private void enableLayers(boolean enable) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            ViewCompat.setLayerType(getChildAt(i), enable ? 2 : 0, null);
        }
    }

    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int action = ev.getAction() & 255;
        if (action == 3 || action == 1) {
            this.mIsBeingDragged = false;
            this.mIsUnableToDrag = false;
            this.mActivePointerId = -1;
            if (this.mVelocityTracker != null) {
                this.mVelocityTracker.recycle();
                this.mVelocityTracker = null;
            }
            return false;
        }
        if (action != 0) {
            if (this.mIsBeingDragged) {
                return true;
            }
            if (this.mIsUnableToDrag) {
                return false;
            }
        }
        switch (action) {
            case 0:
                float x = ev.getX();
                this.mInitialMotionX = x;
                this.mLastMotionX = x;
                float y = ev.getY();
                this.mInitialMotionY = y;
                this.mLastMotionY = y;
                this.mActivePointerId = MotionEventCompat.getPointerId(ev, 0);
                this.mIsUnableToDrag = false;
                this.mScroller.computeScrollOffset();
                if (this.mScrollState == 2 && Math.abs(this.mScroller.getFinalY() - this.mScroller.getCurrY()) > this.mCloseEnough) {
                    this.mScroller.abortAnimation();
                    this.mPopulatePending = false;
                    populate();
                    this.mIsBeingDragged = true;
                    requestParentDisallowInterceptTouchEvent(true);
                    setScrollState(1);
                    break;
                } else {
                    completeScroll(false);
                    this.mIsBeingDragged = false;
                    break;
                }
            case 2:
                int activePointerId = this.mActivePointerId;
                if (activePointerId != -1) {
                    int pointerIndex = MotionEventCompat.findPointerIndex(ev, activePointerId);
                    float y2 = MotionEventCompat.getY(ev, pointerIndex);
                    float dy = y2 - this.mLastMotionY;
                    float yDiff = Math.abs(dy);
                    float x2 = MotionEventCompat.getX(ev, pointerIndex);
                    float xDiff = Math.abs(x2 - this.mInitialMotionX);
                    if (dy != 0.0f && !isGutterDrag(this.mLastMotionY, dy)) {
                        if (canScroll(this, false, (int) dy, (int) x2, (int) y2)) {
                            this.mLastMotionX = x2;
                            this.mLastMotionY = y2;
                            this.mIsUnableToDrag = true;
                            return false;
                        }
                    }
                    if (yDiff > ((float) this.mTouchSlop) && 0.5f * yDiff > xDiff) {
                        this.mIsBeingDragged = true;
                        requestParentDisallowInterceptTouchEvent(true);
                        setScrollState(1);
                        this.mLastMotionY = dy > 0.0f ? this.mInitialMotionY + ((float) this.mTouchSlop) : this.mInitialMotionY - ((float) this.mTouchSlop);
                        this.mLastMotionX = x2;
                        setScrollingCacheEnabled(true);
                    } else if (xDiff > ((float) this.mTouchSlop)) {
                        this.mIsUnableToDrag = true;
                    }
                    if (this.mIsBeingDragged && performDrag(y2)) {
                        ViewCompat.postInvalidateOnAnimation(this);
                        break;
                    }
                }
                break;
            case 6:
                onSecondaryPointerUp(ev);
                break;
        }
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
        this.mVelocityTracker.addMovement(ev);
        return this.mIsBeingDragged;
    }

    public boolean onTouchEvent(MotionEvent ev) {
        float f;
        if (this.mFakeDragging) {
            return true;
        }
        if ((ev.getAction() == 0 && ev.getEdgeFlags() != 0) || this.mAdapter == null || this.mAdapter.getCount() == 0) {
            return false;
        }
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
        this.mVelocityTracker.addMovement(ev);
        boolean needsInvalidate = false;
        switch (ev.getAction() & 255) {
            case 0:
                this.mScroller.abortAnimation();
                this.mPopulatePending = false;
                populate();
                float x = ev.getX();
                this.mInitialMotionX = x;
                this.mLastMotionX = x;
                float y = ev.getY();
                this.mInitialMotionY = y;
                this.mLastMotionY = y;
                this.mActivePointerId = MotionEventCompat.getPointerId(ev, 0);
                break;
            case 1:
                if (this.mIsBeingDragged) {
                    VelocityTracker velocityTracker = this.mVelocityTracker;
                    velocityTracker.computeCurrentVelocity(1000, (float) this.mMaximumVelocity);
                    int initialVelocity = (int) VelocityTrackerCompat.getYVelocity(velocityTracker, this.mActivePointerId);
                    this.mPopulatePending = true;
                    int height = getClientHeight();
                    int scrollY = getScrollY();
                    ItemInfo ii = infoForCurrentScrollPosition();
                    setCurrentItemInternal(determineTargetPage(ii.position, ((((float) scrollY) / ((float) height)) - ii.offset) / ii.heightFactor, initialVelocity, (int) (MotionEventCompat.getY(ev, MotionEventCompat.findPointerIndex(ev, this.mActivePointerId)) - this.mInitialMotionY)), true, true, initialVelocity);
                    this.mActivePointerId = -1;
                    endDrag();
                    needsInvalidate = this.mTopEdge.onRelease() | this.mBottomEdge.onRelease();
                    break;
                }
                break;
            case 2:
                if (!this.mIsBeingDragged) {
                    int pointerIndex = MotionEventCompat.findPointerIndex(ev, this.mActivePointerId);
                    float y2 = MotionEventCompat.getY(ev, pointerIndex);
                    float yDiff = Math.abs(y2 - this.mLastMotionY);
                    float x2 = MotionEventCompat.getX(ev, pointerIndex);
                    float xDiff = Math.abs(x2 - this.mLastMotionX);
                    if (yDiff > ((float) this.mTouchSlop) && yDiff > xDiff) {
                        this.mIsBeingDragged = true;
                        requestParentDisallowInterceptTouchEvent(true);
                        if (y2 - this.mInitialMotionY > 0.0f) {
                            f = this.mInitialMotionY + ((float) this.mTouchSlop);
                        } else {
                            f = this.mInitialMotionY - ((float) this.mTouchSlop);
                        }
                        this.mLastMotionY = f;
                        this.mLastMotionX = x2;
                        setScrollState(1);
                        setScrollingCacheEnabled(true);
                        ViewParent parent = getParent();
                        if (parent != null) {
                            parent.requestDisallowInterceptTouchEvent(true);
                        }
                    }
                }
                if (this.mIsBeingDragged) {
                    needsInvalidate = false | performDrag(MotionEventCompat.getY(ev, MotionEventCompat.findPointerIndex(ev, this.mActivePointerId)));
                    break;
                }
                break;
            case 3:
                if (this.mIsBeingDragged) {
                    scrollToItem(this.mCurItem, true, 0, false);
                    this.mActivePointerId = -1;
                    endDrag();
                    needsInvalidate = this.mTopEdge.onRelease() | this.mBottomEdge.onRelease();
                    break;
                }
                break;
            case 5:
                int index = MotionEventCompat.getActionIndex(ev);
                this.mLastMotionY = MotionEventCompat.getY(ev, index);
                this.mActivePointerId = MotionEventCompat.getPointerId(ev, index);
                break;
            case 6:
                onSecondaryPointerUp(ev);
                this.mLastMotionY = MotionEventCompat.getY(ev, MotionEventCompat.findPointerIndex(ev, this.mActivePointerId));
                break;
        }
        if (needsInvalidate) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
        return true;
    }

    private void requestParentDisallowInterceptTouchEvent(boolean disallowIntercept) {
        ViewParent parent = getParent();
        if (parent != null) {
            parent.requestDisallowInterceptTouchEvent(disallowIntercept);
        }
    }

    private boolean performDrag(float y) {
        boolean needsInvalidate = false;
        float deltaY = this.mLastMotionY - y;
        this.mLastMotionY = y;
        float scrollY = ((float) getScrollY()) + deltaY;
        int height = getClientHeight();
        float topBound = ((float) height) * this.mFirstOffset;
        float bottomBound = ((float) height) * this.mLastOffset;
        boolean topAbsolute = true;
        boolean bottomAbsolute = true;
        ItemInfo firstItem = this.mItems.get(0);
        ItemInfo lastItem = this.mItems.get(this.mItems.size() - 1);
        if (firstItem.position != 0) {
            topAbsolute = false;
            topBound = firstItem.offset * ((float) height);
        }
        if (lastItem.position != this.mAdapter.getCount() - 1) {
            bottomAbsolute = false;
            bottomBound = lastItem.offset * ((float) height);
        }
        if (scrollY < topBound) {
            if (topAbsolute) {
                needsInvalidate = this.mTopEdge.onPull(Math.abs(topBound - scrollY) / ((float) height));
            }
            scrollY = topBound;
        } else if (scrollY > bottomBound) {
            if (bottomAbsolute) {
                needsInvalidate = this.mBottomEdge.onPull(Math.abs(scrollY - bottomBound) / ((float) height));
            }
            scrollY = bottomBound;
        }
        this.mLastMotionX += scrollY - ((float) ((int) scrollY));
        scrollTo(getScrollX(), (int) scrollY);
        pageScrolled((int) scrollY);
        return needsInvalidate;
    }

    private ItemInfo infoForCurrentScrollPosition() {
        float scrollOffset;
        float marginOffset = 0.0f;
        int height = getClientHeight();
        if (height > 0) {
            scrollOffset = ((float) getScrollY()) / ((float) height);
        } else {
            scrollOffset = 0.0f;
        }
        if (height > 0) {
            marginOffset = ((float) this.mPageMargin) / ((float) height);
        }
        int lastPos = -1;
        float lastOffset = 0.0f;
        float lastHeight = 0.0f;
        boolean first = true;
        ItemInfo lastItem = null;
        int i = 0;
        while (i < this.mItems.size()) {
            ItemInfo ii = this.mItems.get(i);
            if (!first && ii.position != lastPos + 1) {
                ii = this.mTempItem;
                ii.offset = lastOffset + lastHeight + marginOffset;
                ii.position = lastPos + 1;
                ii.heightFactor = this.mAdapter.getPageWidth(ii.position);
                i--;
            }
            float offset = ii.offset;
            float topBound = offset;
            float bottomBound = ii.heightFactor + offset + marginOffset;
            if (!first && scrollOffset < topBound) {
                return lastItem;
            }
            if (scrollOffset < bottomBound || i == this.mItems.size() - 1) {
                return ii;
            }
            first = false;
            lastPos = ii.position;
            lastOffset = offset;
            lastHeight = ii.heightFactor;
            lastItem = ii;
            i++;
        }
        return lastItem;
    }

    private int determineTargetPage(int currentPage, float pageOffset, int velocity, int deltaY) {
        int targetPage;
        if (Math.abs(deltaY) <= this.mFlingDistance || Math.abs(velocity) <= this.mMinimumVelocity) {
            targetPage = (int) (((float) currentPage) + pageOffset + (currentPage >= this.mCurItem ? 0.4f : 0.6f));
        } else {
            targetPage = velocity > 0 ? currentPage : currentPage + 1;
        }
        if (this.mItems.size() > 0) {
            return Math.max(this.mItems.get(0).position, Math.min(targetPage, this.mItems.get(this.mItems.size() - 1).position));
        }
        return targetPage;
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
        boolean needsInvalidate = false;
        int overScrollMode = ViewCompat.getOverScrollMode(this);
        if (overScrollMode == 0 || (overScrollMode == 1 && this.mAdapter != null && this.mAdapter.getCount() > 1)) {
            if (!this.mTopEdge.isFinished()) {
                int restoreCount = canvas.save();
                int height = getHeight();
                int width = (getWidth() - getPaddingLeft()) - getPaddingRight();
                canvas.translate((float) getPaddingLeft(), this.mFirstOffset * ((float) height));
                this.mTopEdge.setSize(width, height);
                needsInvalidate = false | this.mTopEdge.draw(canvas);
                canvas.restoreToCount(restoreCount);
            }
            if (!this.mBottomEdge.isFinished()) {
                int restoreCount2 = canvas.save();
                int height2 = getHeight();
                int width2 = (getWidth() - getPaddingLeft()) - getPaddingRight();
                canvas.rotate(180.0f);
                canvas.translate((float) ((-width2) - getPaddingLeft()), (-(this.mLastOffset + 1.0f)) * ((float) height2));
                this.mBottomEdge.setSize(width2, height2);
                needsInvalidate |= this.mBottomEdge.draw(canvas);
                canvas.restoreToCount(restoreCount2);
            }
        } else {
            this.mTopEdge.finish();
            this.mBottomEdge.finish();
        }
        if (needsInvalidate) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        float drawAt;
        super.onDraw(canvas);
        if (this.mPageMargin > 0 && this.mMarginDrawable != null && this.mItems.size() > 0 && this.mAdapter != null) {
            int scrollY = getScrollY();
            int height = getHeight();
            float marginOffset = ((float) this.mPageMargin) / ((float) height);
            int itemIndex = 0;
            ItemInfo ii = this.mItems.get(0);
            float offset = ii.offset;
            int itemCount = this.mItems.size();
            int firstPos = ii.position;
            int lastPos = this.mItems.get(itemCount - 1).position;
            int pos = firstPos;
            while (pos < lastPos) {
                while (pos > ii.position && itemIndex < itemCount) {
                    itemIndex++;
                    ii = this.mItems.get(itemIndex);
                }
                if (pos == ii.position) {
                    drawAt = (ii.offset + ii.heightFactor) * ((float) height);
                    offset = ii.offset + ii.heightFactor + marginOffset;
                } else {
                    float heightFactor = this.mAdapter.getPageWidth(pos);
                    drawAt = (offset + heightFactor) * ((float) height);
                    offset += heightFactor + marginOffset;
                }
                if (((float) this.mPageMargin) + drawAt > ((float) scrollY)) {
                    this.mMarginDrawable.setBounds(this.mLeftPageBounds, (int) drawAt, this.mRightPageBounds, (int) (((float) this.mPageMargin) + drawAt + 0.5f));
                    this.mMarginDrawable.draw(canvas);
                }
                if (drawAt <= ((float) (scrollY + height))) {
                    pos++;
                } else {
                    return;
                }
            }
        }
    }

    public boolean beginFakeDrag() {
        if (this.mIsBeingDragged) {
            return false;
        }
        this.mFakeDragging = true;
        setScrollState(1);
        this.mLastMotionY = 0.0f;
        this.mInitialMotionY = 0.0f;
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        } else {
            this.mVelocityTracker.clear();
        }
        long time = SystemClock.uptimeMillis();
        MotionEvent ev = MotionEvent.obtain(time, time, 0, 0.0f, 0.0f, 0);
        this.mVelocityTracker.addMovement(ev);
        ev.recycle();
        this.mFakeDragBeginTime = time;
        return true;
    }

    public void endFakeDrag() {
        if (!this.mFakeDragging) {
            throw new IllegalStateException("No fake drag in progress. Call beginFakeDrag first.");
        }
        VelocityTracker velocityTracker = this.mVelocityTracker;
        velocityTracker.computeCurrentVelocity(1000, (float) this.mMaximumVelocity);
        int initialVelocity = (int) VelocityTrackerCompat.getYVelocity(velocityTracker, this.mActivePointerId);
        this.mPopulatePending = true;
        int height = getClientHeight();
        int scrollY = getScrollY();
        ItemInfo ii = infoForCurrentScrollPosition();
        setCurrentItemInternal(determineTargetPage(ii.position, ((((float) scrollY) / ((float) height)) - ii.offset) / ii.heightFactor, initialVelocity, (int) (this.mLastMotionY - this.mInitialMotionY)), true, true, initialVelocity);
        endDrag();
        this.mFakeDragging = false;
    }

    public void fakeDragBy(float yOffset) {
        if (!this.mFakeDragging) {
            throw new IllegalStateException("No fake drag in progress. Call beginFakeDrag first.");
        }
        this.mLastMotionY += yOffset;
        float scrollY = ((float) getScrollY()) - yOffset;
        int height = getClientHeight();
        float topBound = ((float) height) * this.mFirstOffset;
        float bottomBound = ((float) height) * this.mLastOffset;
        ItemInfo firstItem = this.mItems.get(0);
        ItemInfo lastItem = this.mItems.get(this.mItems.size() - 1);
        if (firstItem.position != 0) {
            topBound = firstItem.offset * ((float) height);
        }
        if (lastItem.position != this.mAdapter.getCount() - 1) {
            bottomBound = lastItem.offset * ((float) height);
        }
        if (scrollY < topBound) {
            scrollY = topBound;
        } else if (scrollY > bottomBound) {
            scrollY = bottomBound;
        }
        this.mLastMotionY += scrollY - ((float) ((int) scrollY));
        scrollTo(getScrollX(), (int) scrollY);
        pageScrolled((int) scrollY);
        MotionEvent ev = MotionEvent.obtain(this.mFakeDragBeginTime, SystemClock.uptimeMillis(), 2, 0.0f, this.mLastMotionY, 0);
        this.mVelocityTracker.addMovement(ev);
        ev.recycle();
    }

    public boolean isFakeDragging() {
        return this.mFakeDragging;
    }

    private void onSecondaryPointerUp(MotionEvent ev) {
        int pointerIndex = MotionEventCompat.getActionIndex(ev);
        if (MotionEventCompat.getPointerId(ev, pointerIndex) == this.mActivePointerId) {
            int newPointerIndex = pointerIndex == 0 ? 1 : 0;
            this.mLastMotionY = MotionEventCompat.getY(ev, newPointerIndex);
            this.mActivePointerId = MotionEventCompat.getPointerId(ev, newPointerIndex);
            if (this.mVelocityTracker != null) {
                this.mVelocityTracker.clear();
            }
        }
    }

    private void endDrag() {
        this.mIsBeingDragged = false;
        this.mIsUnableToDrag = false;
        if (this.mVelocityTracker != null) {
            this.mVelocityTracker.recycle();
            this.mVelocityTracker = null;
        }
    }

    private void setScrollingCacheEnabled(boolean enabled) {
        if (this.mScrollingCacheEnabled != enabled) {
            this.mScrollingCacheEnabled = enabled;
        }
    }

    public boolean internalCanScrollVertically(int direction) {
        boolean z = true;
        if (this.mAdapter == null) {
            return false;
        }
        int height = getClientHeight();
        int scrollY = getScrollY();
        if (direction < 0) {
            if (scrollY <= ((int) (((float) height) * this.mFirstOffset))) {
                z = false;
            }
            return z;
        } else if (direction <= 0) {
            return false;
        } else {
            if (scrollY >= ((int) (((float) height) * this.mLastOffset))) {
                z = false;
            }
            return z;
        }
    }

    /* access modifiers changed from: protected */
    public boolean canScroll(View v, boolean checkV, int dy, int x, int y) {
        if (v instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) v;
            int scrollX = v.getScrollX();
            int scrollY = v.getScrollY();
            for (int i = group.getChildCount() - 1; i >= 0; i--) {
                View child = group.getChildAt(i);
                if (y + scrollY >= child.getTop() && y + scrollY < child.getBottom() && x + scrollX >= child.getLeft() && x + scrollX < child.getRight()) {
                    if (canScroll(child, true, dy, (x + scrollX) - child.getLeft(), (y + scrollY) - child.getTop())) {
                        return true;
                    }
                }
            }
        }
        return checkV && ViewCompat.canScrollVertically(v, -dy);
    }

    public boolean dispatchKeyEvent(KeyEvent event) {
        return super.dispatchKeyEvent(event) || executeKeyEvent(event);
    }

    public boolean executeKeyEvent(KeyEvent event) {
        if (event.getAction() != 0) {
            return false;
        }
        switch (event.getKeyCode()) {
            case 21:
                return arrowScroll(17);
            case 22:
                return arrowScroll(66);
            case 61:
                if (Build.VERSION.SDK_INT < 11) {
                    return false;
                }
                if (KeyEventCompat.hasNoModifiers(event)) {
                    return arrowScroll(2);
                }
                if (KeyEventCompat.hasModifiers(event, 1)) {
                    return arrowScroll(1);
                }
                return false;
            default:
                return false;
        }
    }

    public boolean arrowScroll(int direction) {
        View currentFocused = findFocus();
        if (currentFocused == this) {
            currentFocused = null;
        } else if (currentFocused != null) {
            boolean isChild = false;
            ViewParent parent = currentFocused.getParent();
            while (true) {
                if (!(parent instanceof ViewGroup)) {
                    break;
                } else if (parent == this) {
                    isChild = true;
                    break;
                } else {
                    parent = parent.getParent();
                }
            }
            if (!isChild) {
                StringBuilder sb = new StringBuilder();
                sb.append(currentFocused.getClass().getSimpleName());
                for (ViewParent parent2 = currentFocused.getParent(); parent2 instanceof ViewGroup; parent2 = parent2.getParent()) {
                    sb.append(" => ").append(parent2.getClass().getSimpleName());
                }
                Log.e(TAG, "arrowScroll tried to find focus based on non-child current focused view " + sb.toString());
                currentFocused = null;
            }
        }
        boolean handled = false;
        View nextFocused = FocusFinder.getInstance().findNextFocus(this, currentFocused, direction);
        if (nextFocused == null || nextFocused == currentFocused) {
            if (direction == 33 || direction == 1) {
                handled = pageUp();
            } else if (direction == 130 || direction == 2) {
                handled = pageDown();
            }
        } else if (direction == 33) {
            handled = (currentFocused == null || getChildRectInPagerCoordinates(this.mTempRect, nextFocused).top < getChildRectInPagerCoordinates(this.mTempRect, currentFocused).top) ? nextFocused.requestFocus() : pageUp();
        } else if (direction == 130) {
            handled = (currentFocused == null || getChildRectInPagerCoordinates(this.mTempRect, nextFocused).bottom > getChildRectInPagerCoordinates(this.mTempRect, currentFocused).bottom) ? nextFocused.requestFocus() : pageDown();
        }
        if (handled) {
            playSoundEffect(SoundEffectConstants.getContantForFocusDirection(direction));
        }
        return handled;
    }

    private Rect getChildRectInPagerCoordinates(Rect outRect, View child) {
        if (outRect == null) {
            outRect = new Rect();
        }
        if (child == null) {
            outRect.set(0, 0, 0, 0);
        } else {
            outRect.left = child.getLeft();
            outRect.right = child.getRight();
            outRect.top = child.getTop();
            outRect.bottom = child.getBottom();
            ViewParent parent = child.getParent();
            while ((parent instanceof ViewGroup) && parent != this) {
                ViewGroup group = (ViewGroup) parent;
                outRect.left += group.getLeft();
                outRect.right += group.getRight();
                outRect.top += group.getTop();
                outRect.bottom += group.getBottom();
                parent = group.getParent();
            }
        }
        return outRect;
    }

    /* access modifiers changed from: package-private */
    public boolean pageUp() {
        if (this.mCurItem <= 0) {
            return false;
        }
        setCurrentItem(this.mCurItem - 1, true);
        return true;
    }

    /* access modifiers changed from: package-private */
    public boolean pageDown() {
        if (this.mAdapter == null || this.mCurItem >= this.mAdapter.getCount() - 1) {
            return false;
        }
        setCurrentItem(this.mCurItem + 1, true);
        return true;
    }

    public void addFocusables(ArrayList<View> views, int direction, int focusableMode) {
        ItemInfo ii;
        int focusableCount = views.size();
        int descendantFocusability = getDescendantFocusability();
        if (descendantFocusability != 393216) {
            for (int i = 0; i < getChildCount(); i++) {
                View child = getChildAt(i);
                if (child.getVisibility() == 0 && (ii = infoForChild(child)) != null && ii.position == this.mCurItem) {
                    child.addFocusables(views, direction, focusableMode);
                }
            }
        }
        if ((descendantFocusability == 262144 && focusableCount != views.size()) || !isFocusable()) {
            return;
        }
        if (((focusableMode & 1) != 1 || !isInTouchMode() || isFocusableInTouchMode()) && views != null) {
            views.add(this);
        }
    }

    public void addTouchables(ArrayList<View> views) {
        ItemInfo ii;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (child.getVisibility() == 0 && (ii = infoForChild(child)) != null && ii.position == this.mCurItem) {
                child.addTouchables(views);
            }
        }
    }

    /* access modifiers changed from: protected */
    public boolean onRequestFocusInDescendants(int direction, Rect previouslyFocusedRect) {
        int index;
        int increment;
        int end;
        ItemInfo ii;
        int count = getChildCount();
        if ((direction & 2) != 0) {
            index = 0;
            increment = 1;
            end = count;
        } else {
            index = count - 1;
            increment = -1;
            end = -1;
        }
        for (int i = index; i != end; i += increment) {
            View child = getChildAt(i);
            if (child.getVisibility() == 0 && (ii = infoForChild(child)) != null && ii.position == this.mCurItem && child.requestFocus(direction, previouslyFocusedRect)) {
                return true;
            }
        }
        return false;
    }

    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent event) {
        ItemInfo ii;
        if (event.getEventType() == 4096) {
            return super.dispatchPopulateAccessibilityEvent(event);
        }
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() == 0 && (ii = infoForChild(child)) != null && ii.position == this.mCurItem && child.dispatchPopulateAccessibilityEvent(event)) {
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams();
    }

    /* access modifiers changed from: protected */
    public ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return generateDefaultLayoutParams();
    }

    /* access modifiers changed from: protected */
    public boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return (p instanceof LayoutParams) && super.checkLayoutParams(p);
    }

    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    class MyAccessibilityDelegate extends AccessibilityDelegateCompat {
        MyAccessibilityDelegate() {
        }

        public void onInitializeAccessibilityEvent(View host, AccessibilityEvent event) {
            super.onInitializeAccessibilityEvent(host, event);
            event.setClassName(ViewPager.class.getName());
            AccessibilityRecordCompat recordCompat = AccessibilityRecordCompat.obtain();
            recordCompat.setScrollable(canScroll());
            if (event.getEventType() == 4096 && VerticalViewPager.this.mAdapter != null) {
                recordCompat.setItemCount(VerticalViewPager.this.mAdapter.getCount());
                recordCompat.setFromIndex(VerticalViewPager.this.mCurItem);
                recordCompat.setToIndex(VerticalViewPager.this.mCurItem);
            }
        }

        public void onInitializeAccessibilityNodeInfo(View host, AccessibilityNodeInfoCompat info) {
            super.onInitializeAccessibilityNodeInfo(host, info);
            info.setClassName(ViewPager.class.getName());
            info.setScrollable(canScroll());
            if (VerticalViewPager.this.internalCanScrollVertically(1)) {
                info.addAction(4096);
            }
            if (VerticalViewPager.this.internalCanScrollVertically(-1)) {
                info.addAction(8192);
            }
        }

        public boolean performAccessibilityAction(View host, int action, Bundle args) {
            if (super.performAccessibilityAction(host, action, args)) {
                return true;
            }
            switch (action) {
                case 4096:
                    if (!VerticalViewPager.this.internalCanScrollVertically(1)) {
                        return false;
                    }
                    VerticalViewPager.this.setCurrentItem(VerticalViewPager.this.mCurItem + 1);
                    return true;
                case 8192:
                    if (!VerticalViewPager.this.internalCanScrollVertically(-1)) {
                        return false;
                    }
                    VerticalViewPager.this.setCurrentItem(VerticalViewPager.this.mCurItem - 1);
                    return true;
                default:
                    return false;
            }
        }

        private boolean canScroll() {
            return VerticalViewPager.this.mAdapter != null && VerticalViewPager.this.mAdapter.getCount() > 1;
        }
    }

    private class PagerObserver extends DataSetObserver {
        private PagerObserver() {
        }

        public void onChanged() {
            VerticalViewPager.this.dataSetChanged();
        }

        public void onInvalidated() {
            VerticalViewPager.this.dataSetChanged();
        }
    }

    public static class LayoutParams extends ViewGroup.LayoutParams {
        int childIndex;
        public int gravity;
        float heightFactor = 0.0f;
        public boolean isDecor;
        boolean needsMeasure;
        int position;

        public LayoutParams() {
            super(-1, -1);
        }

        public LayoutParams(Context context, AttributeSet attrs) {
            super(context, attrs);
            TypedArray a = context.obtainStyledAttributes(attrs, VerticalViewPager.LAYOUT_ATTRS);
            this.gravity = a.getInteger(0, 48);
            a.recycle();
        }
    }

    static class ViewPositionComparator implements Comparator<View> {
        ViewPositionComparator() {
        }

        public int compare(View lhs, View rhs) {
            LayoutParams llp = (LayoutParams) lhs.getLayoutParams();
            LayoutParams rlp = (LayoutParams) rhs.getLayoutParams();
            if (llp.isDecor != rlp.isDecor) {
                return llp.isDecor ? 1 : -1;
            }
            return llp.position - rlp.position;
        }
    }
}
