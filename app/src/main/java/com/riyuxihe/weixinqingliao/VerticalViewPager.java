/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources$NotFoundException
 *  android.database.DataSetObserver
 *  android.graphics.Canvas
 *  android.graphics.Rect
 *  android.graphics.drawable.Drawable
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.os.SystemClock
 *  android.util.AttributeSet
 *  android.util.Log
 *  android.view.FocusFinder
 *  android.view.KeyEvent
 *  android.view.MotionEvent
 *  android.view.SoundEffectConstants
 *  android.view.VelocityTracker
 *  android.view.View
 *  android.view.View$BaseSavedState
 *  android.view.View$MeasureSpec
 *  android.view.ViewConfiguration
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewParent
 *  android.view.accessibility.AccessibilityEvent
 *  android.view.animation.Interpolator
 *  android.widget.Scroller
 */
package com.riyuxihe.weixinqingliao;

import android.content.Context;
import android.content.res.Resources;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;
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

public class VerticalViewPager
extends ViewGroup {
    private static final int CLOSE_ENOUGH = 2;
    private static final Comparator<ItemInfo> COMPARATOR;
    private static final boolean DEBUG = false;
    private static final int DEFAULT_GUTTER_SIZE = 16;
    private static final int DEFAULT_OFFSCREEN_PAGES = 1;
    private static final int DRAW_ORDER_DEFAULT = 0;
    private static final int DRAW_ORDER_FORWARD = 1;
    private static final int DRAW_ORDER_REVERSE = 2;
    private static final int INVALID_POINTER = -1;
    private static final int[] LAYOUT_ATTRS;
    private static final int MAX_SETTLE_DURATION = 600;
    private static final int MIN_DISTANCE_FOR_FLING = 25;
    private static final int MIN_FLING_VELOCITY = 400;
    public static final int SCROLL_STATE_DRAGGING = 1;
    public static final int SCROLL_STATE_IDLE = 0;
    public static final int SCROLL_STATE_SETTLING = 2;
    private static final String TAG = "ViewPager";
    private static final boolean USE_CACHE = false;
    private static final Interpolator sInterpolator;
    private static final ViewPositionComparator sPositionComparator;
    private int mActivePointerId = -1;
    private PagerAdapter mAdapter;
    private OnAdapterChangeListener mAdapterChangeListener;
    private EdgeEffectCompat mBottomEdge;
    private boolean mCalledSuper;
    private int mChildHeightMeasureSpec;
    private int mChildWidthMeasureSpec;
    private int mCloseEnough;
    private int mCurItem;
    private int mDecorChildCount;
    private int mDefaultGutterSize;
    private int mDrawingOrder;
    private ArrayList<View> mDrawingOrderedChildren;
    private final Runnable mEndScrollRunnable;
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
    private final ArrayList<ItemInfo> mItems = new ArrayList();
    private float mLastMotionX;
    private float mLastMotionY;
    private float mLastOffset;
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

    static {
        LAYOUT_ATTRS = new int[]{16842931};
        COMPARATOR = new Comparator<ItemInfo>(){

            @Override
            public int compare(ItemInfo itemInfo, ItemInfo itemInfo2) {
                return itemInfo.position - itemInfo2.position;
            }
        };
        sInterpolator = new Interpolator(){

            public float getInterpolation(float f2) {
                return (f2 -= 1.0f) * f2 * f2 * f2 * f2 + 1.0f;
            }
        };
        sPositionComparator = new ViewPositionComparator();
    }

    public VerticalViewPager(Context context) {
        super(context);
        this.mLastOffset = Float.MAX_VALUE;
        this.mEndScrollRunnable = new Runnable(){

            @Override
            public void run() {
                VerticalViewPager.this.setScrollState(0);
                VerticalViewPager.this.populate();
            }
        };
        this.initViewPager();
    }

    public VerticalViewPager(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mLastOffset = Float.MAX_VALUE;
        this.mEndScrollRunnable = new /* invalid duplicate definition of identical inner class */;
        this.initViewPager();
    }

    /*
     * Enabled aggressive block sorting
     */
    private void calculatePageOffsets(ItemInfo itemInfo, int n2, ItemInfo itemInfo2) {
        int n3;
        float f2;
        float f3;
        int n4;
        float f4;
        int n5;
        int n6;
        block12: {
            block15: {
                block14: {
                    block13: {
                        n6 = this.mAdapter.getCount();
                        n5 = this.getClientHeight();
                        f4 = n5 > 0 ? (float)this.mPageMargin / (float)n5 : 0.0f;
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

    /*
     * Enabled aggressive block sorting
     */
    private void completeScroll(boolean bl2) {
        int n2;
        int n3;
        int n4 = this.mScrollState == 2 ? 1 : 0;
        if (n4 != 0) {
            this.setScrollingCacheEnabled(false);
            this.mScroller.abortAnimation();
            n3 = this.getScrollX();
            n2 = this.getScrollY();
            int n5 = this.mScroller.getCurrX();
            int n6 = this.mScroller.getCurrY();
            if (n3 != n5 || n2 != n6) {
                this.scrollTo(n5, n6);
            }
        }
        this.mPopulatePending = false;
        n2 = 0;
        n3 = n4;
        for (n4 = n2; n4 < this.mItems.size(); ++n4) {
            ItemInfo itemInfo = this.mItems.get(n4);
            if (!itemInfo.scrolling) continue;
            n3 = 1;
            itemInfo.scrolling = false;
        }
        if (n3 != 0) {
            if (!bl2) {
                this.mEndScrollRunnable.run();
                return;
            }
            ViewCompat.postOnAnimation((View)this, this.mEndScrollRunnable);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private int determineTargetPage(int n2, float f2, int n3, int n4) {
        if (Math.abs(n4) > this.mFlingDistance && Math.abs(n3) > this.mMinimumVelocity) {
            if (n3 <= 0) {
                ++n2;
            }
        } else {
            float f3 = n2 >= this.mCurItem ? 0.4f : 0.6f;
            n2 = (int)((float)n2 + f2 + f3);
        }
        n3 = n2;
        if (this.mItems.size() <= 0) return n3;
        ItemInfo itemInfo = this.mItems.get(0);
        ItemInfo itemInfo2 = this.mItems.get(this.mItems.size() - 1);
        return Math.max(itemInfo.position, Math.min(n2, itemInfo2.position));
    }

    /*
     * Enabled aggressive block sorting
     */
    private void enableLayers(boolean bl2) {
        int n2 = this.getChildCount();
        int n3 = 0;
        while (n3 < n2) {
            int n4 = bl2 ? 2 : 0;
            ViewCompat.setLayerType(this.getChildAt(n3), n4, null);
            ++n3;
        }
        return;
    }

    private void endDrag() {
        this.mIsBeingDragged = false;
        this.mIsUnableToDrag = false;
        if (this.mVelocityTracker != null) {
            this.mVelocityTracker.recycle();
            this.mVelocityTracker = null;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private Rect getChildRectInPagerCoordinates(Rect rect, View view) {
        Rect rect2 = rect;
        if (rect == null) {
            rect2 = new Rect();
        }
        if (view == null) {
            rect2.set(0, 0, 0, 0);
            return rect2;
        } else {
            rect2.left = view.getLeft();
            rect2.right = view.getRight();
            rect2.top = view.getTop();
            rect2.bottom = view.getBottom();
            for (rect = view.getParent(); rect instanceof ViewGroup && rect != this; rect2.left += rect.getLeft(), rect2.right += rect.getRight(), rect2.top += rect.getTop(), rect2.bottom += rect.getBottom(), rect = rect.getParent()) {
                rect = (ViewGroup)rect;
            }
        }
        return rect2;
    }

    private int getClientHeight() {
        return this.getMeasuredHeight() - this.getPaddingTop() - this.getPaddingBottom();
    }

    /*
     * Enabled aggressive block sorting
     */
    private ItemInfo infoForCurrentScrollPosition() {
        float f2 = 0.0f;
        int n2 = this.getClientHeight();
        float f3 = n2 > 0 ? (float)this.getScrollY() / (float)n2 : 0.0f;
        if (n2 > 0) {
            f2 = (float)this.mPageMargin / (float)n2;
        }
        int n3 = -1;
        float f4 = 0.0f;
        float f5 = 0.0f;
        boolean bl2 = true;
        ItemInfo itemInfo = null;
        n2 = 0;
        while (true) {
            ItemInfo itemInfo2 = itemInfo;
            if (n2 >= this.mItems.size()) return itemInfo2;
            itemInfo2 = this.mItems.get(n2);
            int n4 = n2;
            ItemInfo itemInfo3 = itemInfo2;
            if (!bl2) {
                n4 = n2;
                itemInfo3 = itemInfo2;
                if (itemInfo2.position != n3 + 1) {
                    itemInfo3 = this.mTempItem;
                    itemInfo3.offset = f4 + f5 + f2;
                    itemInfo3.position = n3 + 1;
                    itemInfo3.heightFactor = this.mAdapter.getPageWidth(itemInfo3.position);
                    n4 = n2 - 1;
                }
            }
            f4 = itemInfo3.offset;
            f5 = itemInfo3.heightFactor;
            if (!bl2) {
                itemInfo2 = itemInfo;
                if (!(f3 >= f4)) return itemInfo2;
            }
            if (f3 < f5 + f4 + f2) return itemInfo3;
            if (n4 == this.mItems.size() - 1) {
                return itemInfo3;
            }
            bl2 = false;
            n3 = itemInfo3.position;
            f5 = itemInfo3.heightFactor;
            n2 = n4 + 1;
            itemInfo = itemInfo3;
        }
    }

    private boolean isGutterDrag(float f2, float f3) {
        return f2 < (float)this.mGutterSize && f3 > 0.0f || f2 > (float)(this.getHeight() - this.mGutterSize) && f3 < 0.0f;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void onSecondaryPointerUp(MotionEvent motionEvent) {
        int n2 = MotionEventCompat.getActionIndex(motionEvent);
        if (MotionEventCompat.getPointerId(motionEvent, n2) == this.mActivePointerId) {
            n2 = n2 == 0 ? 1 : 0;
            this.mLastMotionY = MotionEventCompat.getY(motionEvent, n2);
            this.mActivePointerId = MotionEventCompat.getPointerId(motionEvent, n2);
            if (this.mVelocityTracker != null) {
                this.mVelocityTracker.clear();
            }
        }
    }

    private boolean pageScrolled(int n2) {
        boolean bl2 = false;
        if (this.mItems.size() == 0) {
            this.mCalledSuper = false;
            this.onPageScrolled(0, 0.0f, 0);
            if (!this.mCalledSuper) {
                throw new IllegalStateException("onPageScrolled did not call superclass implementation");
            }
        } else {
            ItemInfo itemInfo = this.infoForCurrentScrollPosition();
            int n3 = this.getClientHeight();
            int n4 = this.mPageMargin;
            float f2 = (float)this.mPageMargin / (float)n3;
            int n5 = itemInfo.position;
            f2 = ((float)n2 / (float)n3 - itemInfo.offset) / (itemInfo.heightFactor + f2);
            n2 = (int)((float)(n3 + n4) * f2);
            this.mCalledSuper = false;
            this.onPageScrolled(n5, f2, n2);
            if (!this.mCalledSuper) {
                throw new IllegalStateException("onPageScrolled did not call superclass implementation");
            }
            bl2 = true;
        }
        return bl2;
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean performDrag(float f2) {
        boolean bl2 = false;
        boolean bl3 = false;
        boolean bl4 = false;
        float f3 = this.mLastMotionY;
        this.mLastMotionY = f2;
        float f4 = (float)this.getScrollY() + (f3 - f2);
        int n2 = this.getClientHeight();
        f2 = (float)n2 * this.mFirstOffset;
        f3 = (float)n2 * this.mLastOffset;
        boolean bl5 = true;
        boolean bl6 = true;
        ItemInfo itemInfo = this.mItems.get(0);
        ItemInfo itemInfo2 = this.mItems.get(this.mItems.size() - 1);
        if (itemInfo.position != 0) {
            bl5 = false;
            f2 = itemInfo.offset * (float)n2;
        }
        if (itemInfo2.position != this.mAdapter.getCount() - 1) {
            bl6 = false;
            f3 = itemInfo2.offset * (float)n2;
        }
        if (f4 < f2) {
            if (bl5) {
                bl4 = this.mTopEdge.onPull(Math.abs(f2 - f4) / (float)n2);
            }
        } else {
            bl4 = bl2;
            f2 = f4;
            if (f4 > f3) {
                bl4 = bl3;
                if (bl6) {
                    bl4 = this.mBottomEdge.onPull(Math.abs(f4 - f3) / (float)n2);
                }
                f2 = f3;
            }
        }
        this.mLastMotionX += f2 - (float)((int)f2);
        this.scrollTo(this.getScrollX(), (int)f2);
        this.pageScrolled((int)f2);
        return bl4;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void recomputeScrollPosition(int n2, int n3, int n4, int n5) {
        if (n3 > 0 && !this.mItems.isEmpty()) {
            int n6 = this.getPaddingTop();
            int n7 = this.getPaddingBottom();
            int n8 = this.getPaddingTop();
            int n9 = this.getPaddingBottom();
            float f2 = (float)this.getScrollY() / (float)(n3 - n8 - n9 + n5);
            n3 = (int)((float)(n2 - n6 - n7 + n4) * f2);
            this.scrollTo(this.getScrollX(), n3);
            if (this.mScroller.isFinished()) return;
            n4 = this.mScroller.getDuration();
            n5 = this.mScroller.timePassed();
            ItemInfo itemInfo = this.infoForPosition(this.mCurItem);
            this.mScroller.startScroll(0, n3, 0, (int)(itemInfo.offset * (float)n2), n4 - n5);
            return;
        }
        ItemInfo itemInfo = this.infoForPosition(this.mCurItem);
        float f3 = itemInfo != null ? Math.min(itemInfo.offset, this.mLastOffset) : 0.0f;
        if ((n2 = (int)((float)(n2 - this.getPaddingTop() - this.getPaddingBottom()) * f3)) == this.getScrollY()) return;
        this.completeScroll(false);
        this.scrollTo(this.getScrollX(), n2);
    }

    private void removeNonDecorViews() {
        int n2 = 0;
        while (n2 < this.getChildCount()) {
            int n3 = n2;
            if (!((LayoutParams)this.getChildAt((int)n2).getLayoutParams()).isDecor) {
                this.removeViewAt(n2);
                n3 = n2 - 1;
            }
            n2 = n3 + 1;
        }
    }

    private void requestParentDisallowInterceptTouchEvent(boolean bl2) {
        ViewParent viewParent = this.getParent();
        if (viewParent != null) {
            viewParent.requestDisallowInterceptTouchEvent(bl2);
        }
    }

    private void scrollToItem(int n2, boolean bl2, int n3, boolean bl3) {
        ItemInfo itemInfo = this.infoForPosition(n2);
        int n4 = 0;
        if (itemInfo != null) {
            n4 = (int)((float)this.getClientHeight() * Math.max(this.mFirstOffset, Math.min(itemInfo.offset, this.mLastOffset)));
        }
        if (bl2) {
            this.smoothScrollTo(0, n4, n3);
            if (bl3 && this.mOnPageChangeListener != null) {
                this.mOnPageChangeListener.onPageSelected(n2);
            }
            if (bl3 && this.mInternalPageChangeListener != null) {
                this.mInternalPageChangeListener.onPageSelected(n2);
            }
            return;
        }
        if (bl3 && this.mOnPageChangeListener != null) {
            this.mOnPageChangeListener.onPageSelected(n2);
        }
        if (bl3 && this.mInternalPageChangeListener != null) {
            this.mInternalPageChangeListener.onPageSelected(n2);
        }
        this.completeScroll(false);
        this.scrollTo(0, n4);
        this.pageScrolled(n4);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void setScrollState(int n2) {
        block5: {
            block4: {
                if (this.mScrollState == n2) break block4;
                this.mScrollState = n2;
                if (this.mPageTransformer != null) {
                    boolean bl2 = n2 != 0;
                    this.enableLayers(bl2);
                }
                if (this.mOnPageChangeListener != null) break block5;
            }
            return;
        }
        this.mOnPageChangeListener.onPageScrollStateChanged(n2);
    }

    private void setScrollingCacheEnabled(boolean bl2) {
        if (this.mScrollingCacheEnabled != bl2) {
            this.mScrollingCacheEnabled = bl2;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void sortChildDrawingOrder() {
        if (this.mDrawingOrder != 0) {
            if (this.mDrawingOrderedChildren == null) {
                this.mDrawingOrderedChildren = new ArrayList();
            } else {
                this.mDrawingOrderedChildren.clear();
            }
            int n2 = this.getChildCount();
            for (int i2 = 0; i2 < n2; ++i2) {
                View view = this.getChildAt(i2);
                this.mDrawingOrderedChildren.add(view);
            }
            Collections.sort(this.mDrawingOrderedChildren, sPositionComparator);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void addFocusables(ArrayList<View> arrayList, int n2, int n3) {
        int n4 = arrayList.size();
        int n5 = this.getDescendantFocusability();
        if (n5 != 393216) {
            for (int i2 = 0; i2 < this.getChildCount(); ++i2) {
                ItemInfo itemInfo;
                View view = this.getChildAt(i2);
                if (view.getVisibility() != 0 || (itemInfo = this.infoForChild(view)) == null || itemInfo.position != this.mCurItem) continue;
                view.addFocusables(arrayList, n2, n3);
            }
        }
        if (n5 == 262144 && n4 != arrayList.size() || !this.isFocusable() || (n3 & 1) == 1 && this.isInTouchMode() && !this.isFocusableInTouchMode() || arrayList == null) {
            return;
        }
        arrayList.add((View)this);
    }

    ItemInfo addNewItem(int n2, int n3) {
        ItemInfo itemInfo = new ItemInfo();
        itemInfo.position = n2;
        itemInfo.object = this.mAdapter.instantiateItem(this, n2);
        itemInfo.heightFactor = this.mAdapter.getPageWidth(n2);
        if (n3 < 0 || n3 >= this.mItems.size()) {
            this.mItems.add(itemInfo);
            return itemInfo;
        }
        this.mItems.add(n3, itemInfo);
        return itemInfo;
    }

    public void addTouchables(ArrayList<View> arrayList) {
        for (int i2 = 0; i2 < this.getChildCount(); ++i2) {
            ItemInfo itemInfo;
            View view = this.getChildAt(i2);
            if (view.getVisibility() != 0 || (itemInfo = this.infoForChild(view)) == null || itemInfo.position != this.mCurItem) continue;
            view.addTouchables(arrayList);
        }
    }

    public void addView(View view, int n2, ViewGroup.LayoutParams layoutParams) {
        ViewGroup.LayoutParams layoutParams2 = layoutParams;
        if (!this.checkLayoutParams(layoutParams)) {
            layoutParams2 = this.generateLayoutParams(layoutParams);
        }
        layoutParams = (LayoutParams)layoutParams2;
        layoutParams.isDecor |= view instanceof Decor;
        if (this.mInLayout) {
            if (layoutParams != null && layoutParams.isDecor) {
                throw new IllegalStateException("Cannot add pager decor view during layout");
            }
            layoutParams.needsMeasure = true;
            this.addViewInLayout(view, n2, layoutParams2);
            return;
        }
        super.addView(view, n2, layoutParams2);
    }

    /*
     * Unable to fully structure code
     */
    public boolean arrowScroll(int var1_1) {
        block13: {
            block7: {
                block12: {
                    block8: {
                        block9: {
                            var6_2 = this.findFocus();
                            if (var6_2 == this) {
                                var5_3 = null;
lbl4:
                                // 4 sources

                                while (true) {
                                    var4_6 = false;
                                    var6_2 = FocusFinder.getInstance().findNextFocus((ViewGroup)this, var5_3, var1_1);
                                    if (var6_2 == null || var6_2 == var5_3) break block7;
                                    if (var1_1 != 33) break block8;
                                    var2_4 = this.getChildRectInPagerCoordinates((Rect)this.mTempRect, (View)var6_2).top;
                                    var3_5 = this.getChildRectInPagerCoordinates((Rect)this.mTempRect, (View)var5_3).top;
                                    if (var5_3 != null && var2_4 >= var3_5) {
                                        var4_6 = this.pageUp();
lbl13:
                                        // 8 sources

                                        while (true) {
                                            if (var4_6) {
                                                this.playSoundEffect(SoundEffectConstants.getContantForFocusDirection((int)var1_1));
                                            }
                                            return var4_6;
                                        }
                                    }
                                    break block9;
                                    break;
                                }
                            }
                            var5_3 = var6_2;
                            if (var6_2 == null) ** GOTO lbl4
                            var3_5 = 0;
                            var5_3 = var6_2.getParent();
                            while (true) {
                                block11: {
                                    block10: {
                                        var2_4 = var3_5;
                                        if (!(var5_3 instanceof ViewGroup)) break block10;
                                        if (var5_3 != this) break block11;
                                        var2_4 = 1;
                                    }
                                    var5_3 = var6_2;
                                    if (var2_4 != 0) ** GOTO lbl4
                                    var7_7 = new StringBuilder();
                                    var7_7.append(var6_2.getClass().getSimpleName());
                                    var5_3 = var6_2.getParent();
                                    while (var5_3 instanceof ViewGroup) {
                                        var7_7.append(" => ").append(var5_3.getClass().getSimpleName());
                                        var5_3 = var5_3.getParent();
                                    }
                                    break;
                                }
                                var5_3 = var5_3.getParent();
                            }
                            Log.e((String)"ViewPager", (String)("arrowScroll tried to find focus based on non-child current focused view " + var7_7.toString()));
                            var5_3 = null;
                            ** while (true)
                        }
                        var4_6 = var6_2.requestFocus();
                        ** GOTO lbl13
                    }
                    if (var1_1 != 130) ** GOTO lbl13
                    var2_4 = this.getChildRectInPagerCoordinates((Rect)this.mTempRect, (View)var6_2).bottom;
                    var3_5 = this.getChildRectInPagerCoordinates((Rect)this.mTempRect, (View)var5_3).bottom;
                    if (var5_3 == null || var2_4 > var3_5) break block12;
                    var4_6 = this.pageDown();
                    ** GOTO lbl13
                }
                var4_6 = var6_2.requestFocus();
                ** GOTO lbl13
            }
            if (var1_1 != 33 && var1_1 != 1) break block13;
            var4_6 = this.pageUp();
            ** GOTO lbl13
        }
        if (var1_1 != 130 && var1_1 != 2) ** GOTO lbl13
        var4_6 = this.pageDown();
        ** while (true)
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean beginFakeDrag() {
        if (this.mIsBeingDragged) {
            return false;
        }
        this.mFakeDragging = true;
        this.setScrollState(1);
        this.mLastMotionY = 0.0f;
        this.mInitialMotionY = 0.0f;
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        } else {
            this.mVelocityTracker.clear();
        }
        long l2 = SystemClock.uptimeMillis();
        MotionEvent motionEvent = MotionEvent.obtain((long)l2, (long)l2, (int)0, (float)0.0f, (float)0.0f, (int)0);
        this.mVelocityTracker.addMovement(motionEvent);
        motionEvent.recycle();
        this.mFakeDragBeginTime = l2;
        return true;
    }

    protected boolean canScroll(View view, boolean bl2, int n2, int n3, int n4) {
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup)view;
            int n5 = view.getScrollX();
            int n6 = view.getScrollY();
            for (int i2 = viewGroup.getChildCount() - 1; i2 >= 0; --i2) {
                View view2 = viewGroup.getChildAt(i2);
                if (n4 + n6 < view2.getTop() || n4 + n6 >= view2.getBottom() || n3 + n5 < view2.getLeft() || n3 + n5 >= view2.getRight() || !this.canScroll(view2, true, n2, n3 + n5 - view2.getLeft(), n4 + n6 - view2.getTop())) continue;
                return true;
            }
        }
        return bl2 && ViewCompat.canScrollVertically(view, -n2);
    }

    protected boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams && super.checkLayoutParams(layoutParams);
    }

    public void computeScroll() {
        if (!this.mScroller.isFinished() && this.mScroller.computeScrollOffset()) {
            int n2 = this.getScrollX();
            int n3 = this.getScrollY();
            int n4 = this.mScroller.getCurrX();
            int n5 = this.mScroller.getCurrY();
            if (n2 != n4 || n3 != n5) {
                this.scrollTo(n4, n5);
                if (!this.pageScrolled(n5)) {
                    this.mScroller.abortAnimation();
                    this.scrollTo(n4, 0);
                }
            }
            ViewCompat.postInvalidateOnAnimation((View)this);
            return;
        }
        this.completeScroll(true);
    }

    /*
     * Enabled aggressive block sorting
     */
    void dataSetChanged() {
        int n2;
        this.mExpectedAdapterCount = n2 = this.mAdapter.getCount();
        int n3 = this.mItems.size() < this.mOffscreenPageLimit * 2 + 1 && this.mItems.size() < n2 ? 1 : 0;
        int n4 = this.mCurItem;
        int n5 = 0;
        int n6 = 0;
        while (n6 < this.mItems.size()) {
            int n7;
            int n8;
            int n9;
            ItemInfo itemInfo = this.mItems.get(n6);
            int n10 = this.mAdapter.getItemPosition(itemInfo.object);
            if (n10 == -1) {
                n9 = n4;
                n8 = n5;
                n7 = n6;
            } else if (n10 == -2) {
                this.mItems.remove(n6);
                n10 = n6 - 1;
                n6 = n5;
                if (n5 == 0) {
                    this.mAdapter.startUpdate(this);
                    n6 = 1;
                }
                this.mAdapter.destroyItem(this, itemInfo.position, itemInfo.object);
                n3 = 1;
                n7 = n10;
                n8 = n6;
                n9 = n4;
                if (this.mCurItem == itemInfo.position) {
                    n9 = Math.max(0, Math.min(this.mCurItem, n2 - 1));
                    n3 = 1;
                    n7 = n10;
                    n8 = n6;
                }
            } else {
                n7 = n6;
                n8 = n5;
                n9 = n4;
                if (itemInfo.position != n10) {
                    if (itemInfo.position == this.mCurItem) {
                        n4 = n10;
                    }
                    itemInfo.position = n10;
                    n3 = 1;
                    n7 = n6;
                    n8 = n5;
                    n9 = n4;
                }
            }
            n6 = n7 + 1;
            n5 = n8;
            n4 = n9;
        }
        if (n5 != 0) {
            this.mAdapter.finishUpdate(this);
        }
        Collections.sort(this.mItems, COMPARATOR);
        if (n3 != 0) {
            n5 = this.getChildCount();
            for (n3 = 0; n3 < n5; ++n3) {
                LayoutParams layoutParams = (LayoutParams)this.getChildAt(n3).getLayoutParams();
                if (layoutParams.isDecor) continue;
                layoutParams.heightFactor = 0.0f;
            }
            this.setCurrentItemInternal(n4, false, true);
            this.requestLayout();
        }
    }

    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        return super.dispatchKeyEvent(keyEvent) || this.executeKeyEvent(keyEvent);
    }

    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        if (accessibilityEvent.getEventType() == 4096) {
            return super.dispatchPopulateAccessibilityEvent(accessibilityEvent);
        }
        int n2 = this.getChildCount();
        for (int i2 = 0; i2 < n2; ++i2) {
            ItemInfo itemInfo;
            View view = this.getChildAt(i2);
            if (view.getVisibility() != 0 || (itemInfo = this.infoForChild(view)) == null || itemInfo.position != this.mCurItem || !view.dispatchPopulateAccessibilityEvent(accessibilityEvent)) continue;
            return true;
        }
        return false;
    }

    float distanceInfluenceForSnapDuration(float f2) {
        return (float)Math.sin((float)((double)(f2 - 0.5f) * 0.4712389167638204));
    }

    /*
     * Enabled aggressive block sorting
     */
    public void draw(Canvas canvas) {
        super.draw(canvas);
        int n2 = 0;
        int n3 = 0;
        int n4 = ViewCompat.getOverScrollMode((View)this);
        if (n4 == 0 || n4 == 1 && this.mAdapter != null && this.mAdapter.getCount() > 1) {
            int n5;
            if (!this.mTopEdge.isFinished()) {
                n2 = canvas.save();
                n3 = this.getHeight();
                n4 = this.getWidth();
                n5 = this.getPaddingLeft();
                int n6 = this.getPaddingRight();
                canvas.translate((float)this.getPaddingLeft(), this.mFirstOffset * (float)n3);
                this.mTopEdge.setSize(n4 - n5 - n6, n3);
                n3 = 0 | this.mTopEdge.draw(canvas);
                canvas.restoreToCount(n2);
            }
            n2 = n3;
            if (!this.mBottomEdge.isFinished()) {
                n4 = canvas.save();
                n2 = this.getHeight();
                n5 = this.getWidth() - this.getPaddingLeft() - this.getPaddingRight();
                canvas.rotate(180.0f);
                canvas.translate((float)(-n5 - this.getPaddingLeft()), -(this.mLastOffset + 1.0f) * (float)n2);
                this.mBottomEdge.setSize(n5, n2);
                n2 = n3 | this.mBottomEdge.draw(canvas);
                canvas.restoreToCount(n4);
            }
        } else {
            this.mTopEdge.finish();
            this.mBottomEdge.finish();
        }
        if (n2 != 0) {
            ViewCompat.postInvalidateOnAnimation((View)this);
        }
    }

    protected void drawableStateChanged() {
        super.drawableStateChanged();
        Drawable drawable2 = this.mMarginDrawable;
        if (drawable2 != null && drawable2.isStateful()) {
            drawable2.setState(this.getDrawableState());
        }
    }

    public void endFakeDrag() {
        if (!this.mFakeDragging) {
            throw new IllegalStateException("No fake drag in progress. Call beginFakeDrag first.");
        }
        Object object = this.mVelocityTracker;
        object.computeCurrentVelocity(1000, (float)this.mMaximumVelocity);
        int n2 = (int)VelocityTrackerCompat.getYVelocity(object, this.mActivePointerId);
        this.mPopulatePending = true;
        int n3 = this.getClientHeight();
        int n4 = this.getScrollY();
        object = this.infoForCurrentScrollPosition();
        this.setCurrentItemInternal(this.determineTargetPage(object.position, ((float)n4 / (float)n3 - object.offset) / object.heightFactor, n2, (int)(this.mLastMotionY - this.mInitialMotionY)), true, true, n2);
        this.endDrag();
        this.mFakeDragging = false;
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean executeKeyEvent(KeyEvent keyEvent) {
        if (keyEvent.getAction() != 0) return false;
        switch (keyEvent.getKeyCode()) {
            default: {
                return false;
            }
            case 21: {
                return this.arrowScroll(17);
            }
            case 22: {
                return this.arrowScroll(66);
            }
            case 61: {
                if (Build.VERSION.SDK_INT < 11) return false;
                if (KeyEventCompat.hasNoModifiers(keyEvent)) {
                    return this.arrowScroll(2);
                }
                if (!KeyEventCompat.hasModifiers(keyEvent, 1)) return false;
                return this.arrowScroll(1);
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void fakeDragBy(float f2) {
        if (!this.mFakeDragging) {
            throw new IllegalStateException("No fake drag in progress. Call beginFakeDrag first.");
        }
        this.mLastMotionY += f2;
        float f3 = (float)this.getScrollY() - f2;
        int n2 = this.getClientHeight();
        f2 = (float)n2 * this.mFirstOffset;
        float f4 = (float)n2 * this.mLastOffset;
        ItemInfo itemInfo = this.mItems.get(0);
        ItemInfo itemInfo2 = this.mItems.get(this.mItems.size() - 1);
        if (itemInfo.position != 0) {
            f2 = itemInfo.offset * (float)n2;
        }
        if (itemInfo2.position != this.mAdapter.getCount() - 1) {
            f4 = itemInfo2.offset * (float)n2;
        }
        if (!(f3 < f2)) {
            f2 = f3;
            if (f3 > f4) {
                f2 = f4;
            }
        }
        this.mLastMotionY += f2 - (float)((int)f2);
        this.scrollTo(this.getScrollX(), (int)f2);
        this.pageScrolled((int)f2);
        long l2 = SystemClock.uptimeMillis();
        itemInfo = MotionEvent.obtain((long)this.mFakeDragBeginTime, (long)l2, (int)2, (float)0.0f, (float)this.mLastMotionY, (int)0);
        this.mVelocityTracker.addMovement((MotionEvent)itemInfo);
        itemInfo.recycle();
    }

    protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams();
    }

    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(this.getContext(), attributeSet);
    }

    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return this.generateDefaultLayoutParams();
    }

    public PagerAdapter getAdapter() {
        return this.mAdapter;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    protected int getChildDrawingOrder(int n2, int n3) {
        if (this.mDrawingOrder == 2) {
            n2 = n2 - 1 - n3;
            return ((LayoutParams)this.mDrawingOrderedChildren.get((int)n2).getLayoutParams()).childIndex;
        }
        n2 = n3;
        return ((LayoutParams)this.mDrawingOrderedChildren.get((int)n2).getLayoutParams()).childIndex;
    }

    public int getCurrentItem() {
        return this.mCurItem;
    }

    public int getOffscreenPageLimit() {
        return this.mOffscreenPageLimit;
    }

    public int getPageMargin() {
        return this.mPageMargin;
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

    ItemInfo infoForChild(View view) {
        for (int i2 = 0; i2 < this.mItems.size(); ++i2) {
            ItemInfo itemInfo = this.mItems.get(i2);
            if (!this.mAdapter.isViewFromObject(view, itemInfo.object)) continue;
            return itemInfo;
        }
        return null;
    }

    ItemInfo infoForPosition(int n2) {
        for (int i2 = 0; i2 < this.mItems.size(); ++i2) {
            ItemInfo itemInfo = this.mItems.get(i2);
            if (itemInfo.position != n2) continue;
            return itemInfo;
        }
        return null;
    }

    void initViewPager() {
        this.setWillNotDraw(false);
        this.setDescendantFocusability(262144);
        this.setFocusable(true);
        Context context = this.getContext();
        this.mScroller = new Scroller(context, sInterpolator);
        ViewConfiguration viewConfiguration = ViewConfiguration.get((Context)context);
        float f2 = context.getResources().getDisplayMetrics().density;
        this.mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(viewConfiguration);
        this.mMinimumVelocity = (int)(400.0f * f2);
        this.mMaximumVelocity = viewConfiguration.getScaledMaximumFlingVelocity();
        this.mTopEdge = new EdgeEffectCompat(context);
        this.mBottomEdge = new EdgeEffectCompat(context);
        this.mFlingDistance = (int)(25.0f * f2);
        this.mCloseEnough = (int)(2.0f * f2);
        this.mDefaultGutterSize = (int)(16.0f * f2);
        ViewCompat.setAccessibilityDelegate((View)this, new MyAccessibilityDelegate());
        if (ViewCompat.getImportantForAccessibility((View)this) == 0) {
            ViewCompat.setImportantForAccessibility((View)this, 1);
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean internalCanScrollVertically(int n2) {
        boolean bl2 = true;
        boolean bl3 = true;
        if (this.mAdapter == null) {
            return false;
        }
        int n3 = this.getClientHeight();
        int n4 = this.getScrollY();
        if (n2 < 0) {
            if (n4 <= (int)((float)n3 * this.mFirstOffset)) return false;
            return bl3;
        }
        if (n2 <= 0) return false;
        if (n4 >= (int)((float)n3 * this.mLastOffset)) return false;
        return bl2;
    }

    public boolean isFakeDragging() {
        return this.mFakeDragging;
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mFirstLayout = true;
    }

    protected void onDetachedFromWindow() {
        this.removeCallbacks(this.mEndScrollRunnable);
        super.onDetachedFromWindow();
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.mPageMargin > 0 && this.mMarginDrawable != null && this.mItems.size() > 0 && this.mAdapter != null) {
            int n2 = this.getScrollY();
            int n3 = this.getHeight();
            float f2 = (float)this.mPageMargin / (float)n3;
            int n4 = 0;
            Object object = this.mItems.get(0);
            float f3 = ((ItemInfo)object).offset;
            int n5 = this.mItems.size();
            int n6 = this.mItems.get((int)(n5 - 1)).position;
            for (int i2 = ((ItemInfo)object).position; i2 < n6; ++i2) {
                float f4;
                while (i2 > ((ItemInfo)object).position && n4 < n5) {
                    object = this.mItems;
                    object = (ItemInfo)((ArrayList)object).get(++n4);
                }
                if (i2 == ((ItemInfo)object).position) {
                    f4 = (((ItemInfo)object).offset + ((ItemInfo)object).heightFactor) * (float)n3;
                    f3 = ((ItemInfo)object).offset + ((ItemInfo)object).heightFactor + f2;
                } else {
                    float f5 = this.mAdapter.getPageWidth(i2);
                    f4 = (f3 + f5) * (float)n3;
                    f3 += f5 + f2;
                }
                if ((float)this.mPageMargin + f4 > (float)n2) {
                    this.mMarginDrawable.setBounds(this.mLeftPageBounds, (int)f4, this.mRightPageBounds, (int)((float)this.mPageMargin + f4 + 0.5f));
                    this.mMarginDrawable.draw(canvas);
                }
                if (f4 > (float)(n2 + n3)) break;
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        int n2 = motionEvent.getAction() & 0xFF;
        if (n2 == 3 || n2 == 1) {
            this.mIsBeingDragged = false;
            this.mIsUnableToDrag = false;
            this.mActivePointerId = -1;
            if (this.mVelocityTracker != null) {
                this.mVelocityTracker.recycle();
                this.mVelocityTracker = null;
            }
            return false;
        }
        if (n2 != 0) {
            if (this.mIsBeingDragged) {
                return true;
            }
            if (this.mIsUnableToDrag) {
                return false;
            }
        }
        switch (n2) {
            case 2: {
                n2 = this.mActivePointerId;
                if (n2 == -1) break;
                n2 = MotionEventCompat.findPointerIndex(motionEvent, n2);
                float f2 = MotionEventCompat.getY(motionEvent, n2);
                float f3 = f2 - this.mLastMotionY;
                float f4 = Math.abs(f3);
                float f5 = MotionEventCompat.getX(motionEvent, n2);
                float f6 = Math.abs(f5 - this.mInitialMotionX);
                if (f3 != 0.0f && !this.isGutterDrag(this.mLastMotionY, f3) && this.canScroll((View)this, false, (int)f3, (int)f5, (int)f2)) {
                    this.mLastMotionX = f5;
                    this.mLastMotionY = f2;
                    this.mIsUnableToDrag = true;
                    return false;
                }
                if (f4 > (float)this.mTouchSlop && 0.5f * f4 > f6) {
                    this.mIsBeingDragged = true;
                    this.requestParentDisallowInterceptTouchEvent(true);
                    this.setScrollState(1);
                    f3 = f3 > 0.0f ? this.mInitialMotionY + (float)this.mTouchSlop : this.mInitialMotionY - (float)this.mTouchSlop;
                    this.mLastMotionY = f3;
                    this.mLastMotionX = f5;
                    this.setScrollingCacheEnabled(true);
                } else if (f6 > (float)this.mTouchSlop) {
                    this.mIsUnableToDrag = true;
                }
                if (!this.mIsBeingDragged || !this.performDrag(f2)) break;
                ViewCompat.postInvalidateOnAnimation((View)this);
                break;
            }
            case 0: {
                float f7;
                this.mInitialMotionX = f7 = motionEvent.getX();
                this.mLastMotionX = f7;
                this.mInitialMotionY = f7 = motionEvent.getY();
                this.mLastMotionY = f7;
                this.mActivePointerId = MotionEventCompat.getPointerId(motionEvent, 0);
                this.mIsUnableToDrag = false;
                this.mScroller.computeScrollOffset();
                if (this.mScrollState == 2 && Math.abs(this.mScroller.getFinalY() - this.mScroller.getCurrY()) > this.mCloseEnough) {
                    this.mScroller.abortAnimation();
                    this.mPopulatePending = false;
                    this.populate();
                    this.mIsBeingDragged = true;
                    this.requestParentDisallowInterceptTouchEvent(true);
                    this.setScrollState(1);
                    break;
                }
                this.completeScroll(false);
                this.mIsBeingDragged = false;
                break;
            }
            case 6: {
                this.onSecondaryPointerUp(motionEvent);
                break;
            }
        }
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
        this.mVelocityTracker.addMovement(motionEvent);
        return this.mIsBeingDragged;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void onLayout(boolean bl2, int n2, int n3, int n4, int n5) {
        LayoutParams layoutParams;
        int n6;
        View view;
        int n7 = this.getChildCount();
        int n8 = n4 - n2;
        int n9 = n5 - n3;
        n3 = this.getPaddingLeft();
        n2 = this.getPaddingTop();
        int n10 = this.getPaddingRight();
        n5 = this.getPaddingBottom();
        int n11 = this.getScrollY();
        int n12 = 0;
        for (int i2 = 0; i2 < n7; ++i2) {
            view = this.getChildAt(i2);
            int n13 = n12;
            int n14 = n5;
            n6 = n3;
            int n15 = n10;
            n4 = n2;
            if (view.getVisibility() != 8) {
                layoutParams = (LayoutParams)view.getLayoutParams();
                n13 = n12;
                n14 = n5;
                n6 = n3;
                n15 = n10;
                n4 = n2;
                if (layoutParams.isDecor) {
                    n4 = layoutParams.gravity;
                    n15 = layoutParams.gravity;
                    switch (n4 & 7) {
                        default: {
                            n4 = n3;
                            n6 = n3;
                            break;
                        }
                        case 3: {
                            n4 = n3;
                            n6 = n3 + view.getMeasuredWidth();
                            break;
                        }
                        case 1: {
                            n4 = Math.max((n8 - view.getMeasuredWidth()) / 2, n3);
                            n6 = n3;
                            break;
                        }
                        case 5: {
                            n4 = n8 - n10 - view.getMeasuredWidth();
                            n10 += view.getMeasuredWidth();
                            n6 = n3;
                        }
                    }
                    switch (n15 & 0x70) {
                        default: {
                            n3 = n2;
                            break;
                        }
                        case 48: {
                            n3 = n2;
                            n2 += view.getMeasuredHeight();
                            break;
                        }
                        case 16: {
                            n3 = Math.max((n9 - view.getMeasuredHeight()) / 2, n2);
                            break;
                        }
                        case 80: {
                            n3 = n9 - n5 - view.getMeasuredHeight();
                            n5 += view.getMeasuredHeight();
                        }
                    }
                    view.layout(n4, n3 += n11, view.getMeasuredWidth() + n4, view.getMeasuredHeight() + n3);
                    n13 = n12 + 1;
                    n4 = n2;
                    n15 = n10;
                    n14 = n5;
                }
            }
            n12 = n13;
            n5 = n14;
            n3 = n6;
            n10 = n15;
            n2 = n4;
        }
        n5 = n9 - n2 - n5;
        for (n4 = 0; n4 < n7; ++n4) {
            ItemInfo itemInfo;
            view = this.getChildAt(n4);
            if (view.getVisibility() == 8) continue;
            layoutParams = (LayoutParams)view.getLayoutParams();
            if (layoutParams.isDecor || (itemInfo = this.infoForChild(view)) == null) continue;
            n6 = n2 + (int)((float)n5 * itemInfo.offset);
            if (layoutParams.needsMeasure) {
                layoutParams.needsMeasure = false;
                view.measure(View.MeasureSpec.makeMeasureSpec((int)(n8 - n3 - n10), (int)0x40000000), View.MeasureSpec.makeMeasureSpec((int)((int)((float)n5 * layoutParams.heightFactor)), (int)0x40000000));
            }
            view.layout(n3, n6, view.getMeasuredWidth() + n3, view.getMeasuredHeight() + n6);
        }
        this.mLeftPageBounds = n3;
        this.mRightPageBounds = n8 - n10;
        this.mDecorChildCount = n12;
        if (this.mFirstLayout) {
            this.scrollToItem(this.mCurItem, false, 0, false);
        }
        this.mFirstLayout = false;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void onMeasure(int n2, int n3) {
        LayoutParams layoutParams;
        int n4;
        int n5;
        View view;
        this.setMeasuredDimension(VerticalViewPager.getDefaultSize((int)0, (int)n2), VerticalViewPager.getDefaultSize((int)0, (int)n3));
        n2 = this.getMeasuredHeight();
        this.mGutterSize = Math.min(n2 / 10, this.mDefaultGutterSize);
        n3 = this.getMeasuredWidth() - this.getPaddingLeft() - this.getPaddingRight();
        n2 = n2 - this.getPaddingTop() - this.getPaddingBottom();
        int n6 = this.getChildCount();
        for (int i2 = 0; i2 < n6; ++i2) {
            view = this.getChildAt(i2);
            n5 = n2;
            n4 = n3;
            if (view.getVisibility() != 8) {
                layoutParams = (LayoutParams)view.getLayoutParams();
                n5 = n2;
                n4 = n3;
                if (layoutParams != null) {
                    n5 = n2;
                    n4 = n3;
                    if (layoutParams.isDecor) {
                        int n7;
                        n4 = layoutParams.gravity & 7;
                        int n8 = layoutParams.gravity & 0x70;
                        int n9 = Integer.MIN_VALUE;
                        n5 = Integer.MIN_VALUE;
                        n8 = n8 == 48 || n8 == 80 ? 1 : 0;
                        boolean bl2 = n4 == 3 || n4 == 5;
                        if (n8 != 0) {
                            n4 = 0x40000000;
                        } else {
                            n4 = n9;
                            if (bl2) {
                                n5 = 0x40000000;
                                n4 = n9;
                            }
                        }
                        int n10 = n3;
                        n9 = n2;
                        int n11 = n10;
                        if (layoutParams.width != -2) {
                            n4 = n7 = 0x40000000;
                            n11 = n10;
                            if (layoutParams.width != -1) {
                                n11 = layoutParams.width;
                                n4 = n7;
                            }
                        }
                        n10 = n9;
                        if (layoutParams.height != -2) {
                            n5 = n7 = 0x40000000;
                            n10 = n9;
                            if (layoutParams.height != -1) {
                                n10 = layoutParams.height;
                                n5 = n7;
                            }
                        }
                        view.measure(View.MeasureSpec.makeMeasureSpec((int)n11, (int)n4), View.MeasureSpec.makeMeasureSpec((int)n10, (int)n5));
                        if (n8 != 0) {
                            n5 = n2 - view.getMeasuredHeight();
                            n4 = n3;
                        } else {
                            n5 = n2;
                            n4 = n3;
                            if (bl2) {
                                n4 = n3 - view.getMeasuredWidth();
                                n5 = n2;
                            }
                        }
                    }
                }
            }
            n2 = n5;
            n3 = n4;
        }
        this.mChildWidthMeasureSpec = View.MeasureSpec.makeMeasureSpec((int)n3, (int)0x40000000);
        this.mChildHeightMeasureSpec = View.MeasureSpec.makeMeasureSpec((int)n2, (int)0x40000000);
        this.mInLayout = true;
        this.populate();
        this.mInLayout = false;
        n5 = this.getChildCount();
        n3 = 0;
        while (n3 < n5) {
            view = this.getChildAt(n3);
            if (!(view.getVisibility() == 8 || (layoutParams = (LayoutParams)view.getLayoutParams()) != null && layoutParams.isDecor)) {
                n4 = View.MeasureSpec.makeMeasureSpec((int)((int)((float)n2 * layoutParams.heightFactor)), (int)0x40000000);
                view.measure(this.mChildWidthMeasureSpec, n4);
            }
            ++n3;
        }
        return;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void onPageScrolled(int n2, float f2, int n3) {
        View view;
        int n4;
        if (this.mDecorChildCount > 0) {
            int n5 = this.getScrollY();
            n4 = this.getPaddingTop();
            int n6 = this.getPaddingBottom();
            int n7 = this.getHeight();
            int n8 = this.getChildCount();
            for (int i2 = 0; i2 < n8; ++i2) {
                int n9;
                int n10;
                view = this.getChildAt(i2);
                LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
                if (!layoutParams.isDecor) {
                    n10 = n4;
                    n9 = n6;
                } else {
                    switch (layoutParams.gravity & 0x70) {
                        default: {
                            n10 = n4;
                            break;
                        }
                        case 48: {
                            n10 = n4;
                            n4 += view.getHeight();
                            break;
                        }
                        case 16: {
                            n10 = Math.max((n7 - view.getMeasuredHeight()) / 2, n4);
                            break;
                        }
                        case 80: {
                            n10 = n7 - n6 - view.getMeasuredHeight();
                            n6 += view.getMeasuredHeight();
                        }
                    }
                    int n11 = n10 + n5 - view.getTop();
                    n9 = n6;
                    n10 = n4;
                    if (n11 != 0) {
                        view.offsetTopAndBottom(n11);
                        n9 = n6;
                        n10 = n4;
                    }
                }
                n6 = n9;
                n4 = n10;
            }
        }
        if (this.mOnPageChangeListener != null) {
            this.mOnPageChangeListener.onPageScrolled(n2, f2, n3);
        }
        if (this.mInternalPageChangeListener != null) {
            this.mInternalPageChangeListener.onPageScrolled(n2, f2, n3);
        }
        if (this.mPageTransformer != null) {
            n3 = this.getScrollY();
            n4 = this.getChildCount();
            for (n2 = 0; n2 < n4; ++n2) {
                view = this.getChildAt(n2);
                if (((LayoutParams)view.getLayoutParams()).isDecor) continue;
                f2 = (float)(view.getTop() - n3) / (float)this.getClientHeight();
                this.mPageTransformer.transformPage(view, f2);
            }
        }
        this.mCalledSuper = true;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected boolean onRequestFocusInDescendants(int n2, Rect rect) {
        int n3;
        int n4;
        int n5 = this.getChildCount();
        if ((n2 & 2) != 0) {
            n4 = 0;
            n3 = 1;
        } else {
            n4 = n5 - 1;
            n3 = -1;
            n5 = -1;
        }
        while (n4 != n5) {
            ItemInfo itemInfo;
            View view = this.getChildAt(n4);
            if (view.getVisibility() == 0 && (itemInfo = this.infoForChild(view)) != null && itemInfo.position == this.mCurItem && view.requestFocus(n2, rect)) {
                return true;
            }
            n4 += n3;
        }
        return false;
    }

    public void onRestoreInstanceState(Parcelable object) {
        if (!(object instanceof SavedState)) {
            super.onRestoreInstanceState(object);
            return;
        }
        object = (SavedState)((Object)object);
        super.onRestoreInstanceState(object.getSuperState());
        if (this.mAdapter != null) {
            this.mAdapter.restoreState(object.adapterState, object.loader);
            this.setCurrentItemInternal(object.position, false, true);
            return;
        }
        this.mRestoredCurItem = object.position;
        this.mRestoredAdapterState = object.adapterState;
        this.mRestoredClassLoader = object.loader;
    }

    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.position = this.mCurItem;
        if (this.mAdapter != null) {
            savedState.adapterState = this.mAdapter.saveState();
        }
        return savedState;
    }

    protected void onSizeChanged(int n2, int n3, int n4, int n5) {
        super.onSizeChanged(n2, n3, n4, n5);
        if (n3 != n5) {
            this.recomputeScrollPosition(n3, n5, this.mPageMargin, this.mPageMargin);
        }
    }

    /*
     * Handled duff style switch with additional control
     * Enabled aggressive block sorting
     */
    public boolean onTouchEvent(MotionEvent motionEvent) {
        int n2;
        if (this.mFakeDragging) {
            return true;
        }
        if (motionEvent.getAction() == 0 && motionEvent.getEdgeFlags() != 0) {
            return false;
        }
        if (this.mAdapter == null || this.mAdapter.getCount() == 0) {
            return false;
        }
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
        this.mVelocityTracker.addMovement(motionEvent);
        int n3 = motionEvent.getAction();
        int n4 = n2 = 0;
        int n5 = Integer.MIN_VALUE;
        block9: do {
            switch (n5 == Integer.MIN_VALUE ? n3 & 0xFF : n5) {
                default: {
                    n4 = n2;
                    break;
                }
                case 0: {
                    float f2;
                    this.mScroller.abortAnimation();
                    this.mPopulatePending = false;
                    this.populate();
                    this.mInitialMotionX = f2 = motionEvent.getX();
                    this.mLastMotionX = f2;
                    this.mInitialMotionY = f2 = motionEvent.getY();
                    this.mLastMotionY = f2;
                    this.mActivePointerId = MotionEventCompat.getPointerId(motionEvent, 0);
                    n4 = n2;
                    break;
                }
                case 2: {
                    if (!this.mIsBeingDragged) {
                        n4 = MotionEventCompat.findPointerIndex(motionEvent, this.mActivePointerId);
                        float f3 = MotionEventCompat.getY(motionEvent, n4);
                        float f4 = Math.abs(f3 - this.mLastMotionY);
                        float f5 = MotionEventCompat.getX(motionEvent, n4);
                        float f6 = Math.abs(f5 - this.mLastMotionX);
                        if (f4 > (float)this.mTouchSlop && f4 > f6) {
                            this.mIsBeingDragged = true;
                            this.requestParentDisallowInterceptTouchEvent(true);
                            f3 = f3 - this.mInitialMotionY > 0.0f ? this.mInitialMotionY + (float)this.mTouchSlop : this.mInitialMotionY - (float)this.mTouchSlop;
                            this.mLastMotionY = f3;
                            this.mLastMotionX = f5;
                            this.setScrollState(1);
                            this.setScrollingCacheEnabled(true);
                            ViewParent viewParent = this.getParent();
                            if (viewParent != null) {
                                viewParent.requestDisallowInterceptTouchEvent(true);
                            }
                        }
                    }
                    n4 = n2;
                    n5 = 4;
                    if (!this.mIsBeingDragged) continue block9;
                    n4 = 0 | this.performDrag(MotionEventCompat.getY(motionEvent, MotionEventCompat.findPointerIndex(motionEvent, this.mActivePointerId)));
                    break;
                }
                case 1: {
                    n4 = n2;
                    n5 = 4;
                    if (!this.mIsBeingDragged) continue block9;
                    VelocityTracker velocityTracker = this.mVelocityTracker;
                    velocityTracker.computeCurrentVelocity(1000, (float)this.mMaximumVelocity);
                    n4 = (int)VelocityTrackerCompat.getYVelocity(velocityTracker, this.mActivePointerId);
                    this.mPopulatePending = true;
                    n2 = this.getClientHeight();
                    n3 = this.getScrollY();
                    ItemInfo itemInfo = this.infoForCurrentScrollPosition();
                    this.setCurrentItemInternal(this.determineTargetPage(itemInfo.position, ((float)n3 / (float)n2 - itemInfo.offset) / itemInfo.heightFactor, n4, (int)(MotionEventCompat.getY(motionEvent, MotionEventCompat.findPointerIndex(motionEvent, this.mActivePointerId)) - this.mInitialMotionY)), true, true, n4);
                    this.mActivePointerId = -1;
                    this.endDrag();
                    n4 = this.mTopEdge.onRelease() | this.mBottomEdge.onRelease();
                    break;
                }
                case 3: {
                    n4 = n2;
                    n5 = 4;
                    if (!this.mIsBeingDragged) continue block9;
                    this.scrollToItem(this.mCurItem, true, 0, false);
                    this.mActivePointerId = -1;
                    this.endDrag();
                    n4 = this.mTopEdge.onRelease() | this.mBottomEdge.onRelease();
                    break;
                }
                case 5: {
                    n4 = MotionEventCompat.getActionIndex(motionEvent);
                    this.mLastMotionY = MotionEventCompat.getY(motionEvent, n4);
                    this.mActivePointerId = MotionEventCompat.getPointerId(motionEvent, n4);
                    n4 = n2;
                }
                case 4: {
                    break;
                }
                case 6: {
                    this.onSecondaryPointerUp(motionEvent);
                    this.mLastMotionY = MotionEventCompat.getY(motionEvent, MotionEventCompat.findPointerIndex(motionEvent, this.mActivePointerId));
                    n4 = n2;
                }
            }
            break;
        } while (true);
        if (n4 != 0) {
            ViewCompat.postInvalidateOnAnimation((View)this);
        }
        return true;
    }

    boolean pageDown() {
        if (this.mAdapter != null && this.mCurItem < this.mAdapter.getCount() - 1) {
            this.setCurrentItem(this.mCurItem + 1, true);
            return true;
        }
        return false;
    }

    boolean pageUp() {
        if (this.mCurItem > 0) {
            this.setCurrentItem(this.mCurItem - 1, true);
            return true;
        }
        return false;
    }

    void populate() {
        this.populate(this.mCurItem);
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void populate(int n2) {
        void var14_56;
        void var14_25;
        ItemInfo itemInfo;
        int n3;
        int n4;
        Object object;
        block49: {
            int n5;
            block50: {
                float f2;
                void var14_17;
                int n6;
                int n7;
                float f3;
                ItemInfo itemInfo2;
                object = null;
                n4 = 2;
                if (this.mCurItem != n2) {
                    n3 = this.mCurItem < n2 ? 130 : 33;
                    object = this.infoForPosition(this.mCurItem);
                    this.mCurItem = n2;
                    n4 = n3;
                }
                if (this.mAdapter == null) {
                    this.sortChildDrawingOrder();
                    return;
                }
                if (this.mPopulatePending) {
                    this.sortChildDrawingOrder();
                    return;
                }
                if (this.getWindowToken() == null) return;
                this.mAdapter.startUpdate(this);
                n2 = this.mOffscreenPageLimit;
                int n8 = Math.max(0, this.mCurItem - n2);
                int n9 = this.mAdapter.getCount();
                int n10 = Math.min(n9 - 1, this.mCurItem + n2);
                if (n9 != this.mExpectedAdapterCount) {
                    void var14_9;
                    try {
                        String string2 = this.getResources().getResourceName(this.getId());
                        throw new IllegalStateException("The application's PagerAdapter changed the adapter's contents without calling PagerAdapter#notifyDataSetChanged! Expected adapter item count: " + this.mExpectedAdapterCount + ", found: " + n9 + " Pager id: " + (String)var14_9 + " Pager class: " + ((Object)((Object)this)).getClass() + " Problematic adapter: " + this.mAdapter.getClass());
                    }
                    catch (Resources.NotFoundException notFoundException) {
                        String string3 = Integer.toHexString(this.getId());
                        throw new IllegalStateException("The application's PagerAdapter changed the adapter's contents without calling PagerAdapter#notifyDataSetChanged! Expected adapter item count: " + this.mExpectedAdapterCount + ", found: " + n9 + " Pager id: " + (String)var14_9 + " Pager class: " + ((Object)((Object)this)).getClass() + " Problematic adapter: " + this.mAdapter.getClass());
                    }
                }
                itemInfo = null;
                n2 = 0;
                while (true) {
                    block54: {
                        void var14_15;
                        block53: {
                            ItemInfo itemInfo3 = itemInfo;
                            if (n2 >= this.mItems.size()) break block53;
                            itemInfo2 = this.mItems.get(n2);
                            if (itemInfo2.position < this.mCurItem) break block54;
                            ItemInfo itemInfo4 = itemInfo;
                            if (itemInfo2.position == this.mCurItem) {
                                ItemInfo itemInfo5 = itemInfo2;
                            }
                        }
                        itemInfo = var14_15;
                        if (var14_15 == null) {
                            itemInfo = var14_15;
                            if (n9 > 0) {
                                itemInfo = this.addNewItem(this.mCurItem, n2);
                            }
                        }
                        if (itemInfo == null) break block49;
                        f3 = 0.0f;
                        n7 = n2 - 1;
                        if (n7 >= 0) {
                            ItemInfo itemInfo6 = this.mItems.get(n7);
                            break;
                        } else {
                            Object var14_28 = null;
                            break;
                        }
                    }
                    ++n2;
                }
                float f4 = (n6 = this.getClientHeight()) <= 0 ? 0.0f : 2.0f - itemInfo.heightFactor + (float)this.getPaddingLeft() / (float)n6;
                int n11 = this.mCurItem - 1;
                itemInfo2 = var14_17;
                n5 = n2;
                while (true) {
                    void var14_32;
                    block51: {
                        block56: {
                            block57: {
                                block55: {
                                    if (n11 < 0) break block55;
                                    if (!(f3 >= f4) || n11 >= n8) break block56;
                                    if (itemInfo2 != null) break block57;
                                }
                                f3 = itemInfo.heightFactor;
                                n11 = n5 + 1;
                                if (f3 < 2.0f) {
                                    void var14_20;
                                    if (n11 < this.mItems.size()) {
                                        ItemInfo itemInfo7 = this.mItems.get(n11);
                                    } else {
                                        Object var14_40 = null;
                                    }
                                    f4 = n6 <= 0 ? 0.0f : (float)this.getPaddingRight() / (float)n6 + 2.0f;
                                    itemInfo2 = var14_20;
                                    break;
                                }
                                break block50;
                            }
                            n2 = n5;
                            f2 = f3;
                            ItemInfo itemInfo8 = itemInfo2;
                            n3 = n7;
                            if (n11 != itemInfo2.position) break block51;
                            n2 = n5;
                            f2 = f3;
                            ItemInfo itemInfo9 = itemInfo2;
                            n3 = n7;
                            if (!itemInfo2.scrolling) {
                                this.mItems.remove(n7);
                                this.mAdapter.destroyItem(this, n11, itemInfo2.object);
                                n3 = n7 - 1;
                                n2 = n5 - 1;
                                if (n3 >= 0) {
                                    ItemInfo itemInfo10 = this.mItems.get(n3);
                                    f2 = f3;
                                    break block51;
                                } else {
                                    Object var14_33 = null;
                                    f2 = f3;
                                }
                            }
                            break block51;
                        }
                        if (itemInfo2 != null && n11 == itemInfo2.position) {
                            f2 = f3 + itemInfo2.heightFactor;
                            n3 = n7 - 1;
                            if (n3 >= 0) {
                                ItemInfo itemInfo11 = this.mItems.get(n3);
                            } else {
                                Object var14_36 = null;
                            }
                            n2 = n5;
                        } else {
                            f2 = f3 + this.addNewItem((int)n11, (int)(n7 + 1)).heightFactor;
                            n2 = n5 + 1;
                            if (n7 >= 0) {
                                ItemInfo itemInfo12 = this.mItems.get(n7);
                            } else {
                                Object var14_39 = null;
                            }
                            n3 = n7;
                        }
                    }
                    --n11;
                    n5 = n2;
                    f3 = f2;
                    itemInfo2 = var14_32;
                    n7 = n3;
                }
                for (n3 = this.mCurItem + 1; n3 < n9; ++n3) {
                    void var14_44;
                    block52: {
                        block58: {
                            if (!(f3 >= f4) || n3 <= n10) break block58;
                            if (itemInfo2 == null) break;
                            f2 = f3;
                            ItemInfo itemInfo13 = itemInfo2;
                            n2 = n11;
                            if (n3 != itemInfo2.position) break block52;
                            f2 = f3;
                            ItemInfo itemInfo14 = itemInfo2;
                            n2 = n11;
                            if (!itemInfo2.scrolling) {
                                this.mItems.remove(n11);
                                this.mAdapter.destroyItem(this, n3, itemInfo2.object);
                                if (n11 < this.mItems.size()) {
                                    ItemInfo itemInfo15 = this.mItems.get(n11);
                                    n2 = n11;
                                    f2 = f3;
                                    break block52;
                                } else {
                                    Object var14_45 = null;
                                    f2 = f3;
                                    n2 = n11;
                                }
                            }
                            break block52;
                        }
                        if (itemInfo2 != null && n3 == itemInfo2.position) {
                            f2 = f3 + itemInfo2.heightFactor;
                            n2 = n11 + 1;
                            if (n2 < this.mItems.size()) {
                                ItemInfo itemInfo16 = this.mItems.get(n2);
                            } else {
                                Object var14_48 = null;
                            }
                        } else {
                            ItemInfo itemInfo17 = this.addNewItem(n3, n11);
                            n2 = n11 + 1;
                            f2 = f3 + itemInfo17.heightFactor;
                            if (n2 < this.mItems.size()) {
                                ItemInfo itemInfo18 = this.mItems.get(n2);
                            } else {
                                Object var14_52 = null;
                            }
                        }
                    }
                    f3 = f2;
                    itemInfo2 = var14_44;
                    n11 = n2;
                }
            }
            this.calculatePageOffsets(itemInfo, n5, (ItemInfo)object);
        }
        object = this.mAdapter;
        n2 = this.mCurItem;
        if (itemInfo != null) {
            Object object2 = itemInfo.object;
        } else {
            Object var14_53 = null;
        }
        ((PagerAdapter)object).setPrimaryItem(this, n2, (Object)var14_25);
        this.mAdapter.finishUpdate(this);
        n3 = this.getChildCount();
        for (n2 = 0; n2 < n3; ++n2) {
            object = this.getChildAt(n2);
            LayoutParams layoutParams = (LayoutParams)object.getLayoutParams();
            layoutParams.childIndex = n2;
            if (layoutParams.isDecor || layoutParams.heightFactor != 0.0f || (object = this.infoForChild((View)object)) == null) continue;
            layoutParams.heightFactor = ((ItemInfo)object).heightFactor;
            layoutParams.position = ((ItemInfo)object).position;
        }
        this.sortChildDrawingOrder();
        if (!this.hasFocus()) return;
        View view = this.findFocus();
        if (view != null) {
            ItemInfo itemInfo19 = this.infoForAnyChild(view);
        } else {
            Object var14_59 = null;
        }
        if (var14_56 != null) {
            if (var14_56.position == this.mCurItem) return;
        }
        n2 = 0;
        while (n2 < this.getChildCount()) {
            View view2 = this.getChildAt(n2);
            object = this.infoForChild(view2);
            if (object != null && ((ItemInfo)object).position == this.mCurItem) {
                if (view2.requestFocus(n4)) return;
            }
            ++n2;
        }
    }

    public void removeView(View view) {
        if (this.mInLayout) {
            this.removeViewInLayout(view);
            return;
        }
        super.removeView(view);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setAdapter(PagerAdapter pagerAdapter) {
        Object object;
        if (this.mAdapter != null) {
            this.mAdapter.unregisterDataSetObserver(this.mObserver);
            this.mAdapter.startUpdate(this);
            for (int i2 = 0; i2 < this.mItems.size(); ++i2) {
                object = this.mItems.get(i2);
                this.mAdapter.destroyItem(this, ((ItemInfo)object).position, ((ItemInfo)object).object);
            }
            this.mAdapter.finishUpdate(this);
            this.mItems.clear();
            this.removeNonDecorViews();
            this.mCurItem = 0;
            this.scrollTo(0, 0);
        }
        object = this.mAdapter;
        this.mAdapter = pagerAdapter;
        this.mExpectedAdapterCount = 0;
        if (this.mAdapter != null) {
            if (this.mObserver == null) {
                this.mObserver = new PagerObserver();
            }
            this.mAdapter.registerDataSetObserver(this.mObserver);
            this.mPopulatePending = false;
            boolean bl2 = this.mFirstLayout;
            this.mFirstLayout = true;
            this.mExpectedAdapterCount = this.mAdapter.getCount();
            if (this.mRestoredCurItem >= 0) {
                this.mAdapter.restoreState(this.mRestoredAdapterState, this.mRestoredClassLoader);
                this.setCurrentItemInternal(this.mRestoredCurItem, false, true);
                this.mRestoredCurItem = -1;
                this.mRestoredAdapterState = null;
                this.mRestoredClassLoader = null;
            } else if (!bl2) {
                this.populate();
            } else {
                this.requestLayout();
            }
        }
        if (this.mAdapterChangeListener != null && object != pagerAdapter) {
            this.mAdapterChangeListener.onAdapterChanged((PagerAdapter)object, pagerAdapter);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    void setChildrenDrawingOrderEnabledCompat(boolean bl2) {
        if (Build.VERSION.SDK_INT < 7) return;
        if (this.mSetChildrenDrawingOrderEnabled == null) {
            try {
                this.mSetChildrenDrawingOrderEnabled = ViewGroup.class.getDeclaredMethod("setChildrenDrawingOrderEnabled", Boolean.TYPE);
            }
            catch (NoSuchMethodException noSuchMethodException) {
                Log.e((String)TAG, (String)"Can't find setChildrenDrawingOrderEnabled", (Throwable)noSuchMethodException);
            }
        }
        try {
            this.mSetChildrenDrawingOrderEnabled.invoke((Object)this, bl2);
            return;
        }
        catch (Exception exception) {
            Log.e((String)TAG, (String)"Error changing children drawing order", (Throwable)exception);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setCurrentItem(int n2) {
        this.mPopulatePending = false;
        boolean bl2 = !this.mFirstLayout;
        this.setCurrentItemInternal(n2, bl2, false);
    }

    public void setCurrentItem(int n2, boolean bl2) {
        this.mPopulatePending = false;
        this.setCurrentItemInternal(n2, bl2, false);
    }

    void setCurrentItemInternal(int n2, boolean bl2, boolean bl3) {
        this.setCurrentItemInternal(n2, bl2, bl3, 0);
    }

    /*
     * Enabled aggressive block sorting
     */
    void setCurrentItemInternal(int n2, boolean bl2, boolean bl3, int n3) {
        int n4;
        boolean bl4 = true;
        if (this.mAdapter == null || this.mAdapter.getCount() <= 0) {
            this.setScrollingCacheEnabled(false);
            return;
        }
        if (!bl3 && this.mCurItem == n2 && this.mItems.size() != 0) {
            this.setScrollingCacheEnabled(false);
            return;
        }
        if (n2 < 0) {
            n4 = 0;
        } else {
            n4 = n2;
            if (n2 >= this.mAdapter.getCount()) {
                n4 = this.mAdapter.getCount() - 1;
            }
        }
        if (n4 > this.mCurItem + (n2 = this.mOffscreenPageLimit) || n4 < this.mCurItem - n2) {
            for (n2 = 0; n2 < this.mItems.size(); ++n2) {
                this.mItems.get((int)n2).scrolling = true;
            }
        }
        bl3 = this.mCurItem != n4 ? bl4 : false;
        if (!this.mFirstLayout) {
            this.populate(n4);
            this.scrollToItem(n4, bl2, n3, bl3);
            return;
        }
        this.mCurItem = n4;
        if (bl3 && this.mOnPageChangeListener != null) {
            this.mOnPageChangeListener.onPageSelected(n4);
        }
        if (bl3 && this.mInternalPageChangeListener != null) {
            this.mInternalPageChangeListener.onPageSelected(n4);
        }
        this.requestLayout();
    }

    ViewPager.OnPageChangeListener setInternalPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener) {
        ViewPager.OnPageChangeListener onPageChangeListener2 = this.mInternalPageChangeListener;
        this.mInternalPageChangeListener = onPageChangeListener;
        return onPageChangeListener2;
    }

    public void setOffscreenPageLimit(int n2) {
        int n3 = n2;
        if (n2 < 1) {
            Log.w((String)TAG, (String)("Requested offscreen page limit " + n2 + " too small; defaulting to " + 1));
            n3 = 1;
        }
        if (n3 != this.mOffscreenPageLimit) {
            this.mOffscreenPageLimit = n3;
            this.populate();
        }
    }

    void setOnAdapterChangeListener(OnAdapterChangeListener onAdapterChangeListener) {
        this.mAdapterChangeListener = onAdapterChangeListener;
    }

    public void setOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener) {
        this.mOnPageChangeListener = onPageChangeListener;
    }

    public void setPageMargin(int n2) {
        int n3 = this.mPageMargin;
        this.mPageMargin = n2;
        int n4 = this.getHeight();
        this.recomputeScrollPosition(n4, n4, n2, n3);
        this.requestLayout();
    }

    public void setPageMarginDrawable(int n2) {
        this.setPageMarginDrawable(this.getContext().getResources().getDrawable(n2));
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setPageMarginDrawable(Drawable drawable2) {
        this.mMarginDrawable = drawable2;
        if (drawable2 != null) {
            this.refreshDrawableState();
        }
        boolean bl2 = drawable2 == null;
        this.setWillNotDraw(bl2);
        this.invalidate();
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setPageTransformer(boolean bl2, ViewPager.PageTransformer pageTransformer) {
        int n2 = 1;
        if (Build.VERSION.SDK_INT < 11) return;
        boolean bl3 = pageTransformer != null;
        boolean bl4 = this.mPageTransformer != null;
        boolean bl5 = bl3 != bl4;
        this.mPageTransformer = pageTransformer;
        this.setChildrenDrawingOrderEnabledCompat(bl3);
        if (bl3) {
            if (bl2) {
                n2 = 2;
            }
            this.mDrawingOrder = n2;
        } else {
            this.mDrawingOrder = 0;
        }
        if (bl5) {
            this.populate();
        }
    }

    void smoothScrollTo(int n2, int n3) {
        this.smoothScrollTo(n2, n3, 0);
    }

    /*
     * Enabled aggressive block sorting
     */
    void smoothScrollTo(int n2, int n3, int n4) {
        if (this.getChildCount() == 0) {
            this.setScrollingCacheEnabled(false);
            return;
        }
        int n5 = this.getScrollX();
        int n6 = this.getScrollY();
        int n7 = n2 - n5;
        if (n7 == 0 && (n3 -= n6) == 0) {
            this.completeScroll(false);
            this.populate();
            this.setScrollState(0);
            return;
        }
        this.setScrollingCacheEnabled(true);
        this.setScrollState(2);
        n2 = this.getClientHeight();
        int n8 = n2 / 2;
        float f2 = Math.min(1.0f, 1.0f * (float)Math.abs(n7) / (float)n2);
        float f3 = n8;
        float f4 = n8;
        f2 = this.distanceInfluenceForSnapDuration(f2);
        n4 = Math.abs(n4);
        if (n4 > 0) {
            n2 = Math.round(1000.0f * Math.abs((f3 + f4 * f2) / (float)n4)) * 4;
        } else {
            f3 = n2;
            f4 = this.mAdapter.getPageWidth(this.mCurItem);
            n2 = (int)((1.0f + (float)Math.abs(n7) / ((float)this.mPageMargin + f3 * f4)) * 100.0f);
        }
        n2 = Math.min(n2, 600);
        this.mScroller.startScroll(n5, n6, n7, n3, n2);
        ViewCompat.postInvalidateOnAnimation((View)this);
    }

    protected boolean verifyDrawable(Drawable drawable2) {
        return super.verifyDrawable(drawable2) || drawable2 == this.mMarginDrawable;
    }

    static interface Decor {
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

    public static class LayoutParams
    extends ViewGroup.LayoutParams {
        int childIndex;
        public int gravity;
        float heightFactor = 0.0f;
        public boolean isDecor;
        boolean needsMeasure;
        int position;

        public LayoutParams() {
            super(-1, -1);
        }

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            context = context.obtainStyledAttributes(attributeSet, LAYOUT_ATTRS);
            this.gravity = context.getInteger(0, 48);
            context.recycle();
        }
    }

    class MyAccessibilityDelegate
    extends AccessibilityDelegateCompat {
        MyAccessibilityDelegate() {
        }

        private boolean canScroll() {
            return VerticalViewPager.this.mAdapter != null && VerticalViewPager.this.mAdapter.getCount() > 1;
        }

        @Override
        public void onInitializeAccessibilityEvent(View object, AccessibilityEvent accessibilityEvent) {
            super.onInitializeAccessibilityEvent((View)object, accessibilityEvent);
            accessibilityEvent.setClassName((CharSequence)ViewPager.class.getName());
            object = AccessibilityRecordCompat.obtain();
            ((AccessibilityRecordCompat)object).setScrollable(this.canScroll());
            if (accessibilityEvent.getEventType() == 4096 && VerticalViewPager.this.mAdapter != null) {
                ((AccessibilityRecordCompat)object).setItemCount(VerticalViewPager.this.mAdapter.getCount());
                ((AccessibilityRecordCompat)object).setFromIndex(VerticalViewPager.this.mCurItem);
                ((AccessibilityRecordCompat)object).setToIndex(VerticalViewPager.this.mCurItem);
            }
        }

        @Override
        public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat);
            accessibilityNodeInfoCompat.setClassName(ViewPager.class.getName());
            accessibilityNodeInfoCompat.setScrollable(this.canScroll());
            if (VerticalViewPager.this.internalCanScrollVertically(1)) {
                accessibilityNodeInfoCompat.addAction(4096);
            }
            if (VerticalViewPager.this.internalCanScrollVertically(-1)) {
                accessibilityNodeInfoCompat.addAction(8192);
            }
        }

        @Override
        public boolean performAccessibilityAction(View view, int n2, Bundle bundle) {
            if (super.performAccessibilityAction(view, n2, bundle)) {
                return true;
            }
            switch (n2) {
                default: {
                    return false;
                }
                case 4096: {
                    if (VerticalViewPager.this.internalCanScrollVertically(1)) {
                        VerticalViewPager.this.setCurrentItem(VerticalViewPager.this.mCurItem + 1);
                        return true;
                    }
                    return false;
                }
                case 8192: 
            }
            if (VerticalViewPager.this.internalCanScrollVertically(-1)) {
                VerticalViewPager.this.setCurrentItem(VerticalViewPager.this.mCurItem - 1);
                return true;
            }
            return false;
        }
    }

    static interface OnAdapterChangeListener {
        public void onAdapterChanged(PagerAdapter var1, PagerAdapter var2);
    }

    private class PagerObserver
    extends DataSetObserver {
        private PagerObserver() {
        }

        public void onChanged() {
            VerticalViewPager.this.dataSetChanged();
        }

        public void onInvalidated() {
            VerticalViewPager.this.dataSetChanged();
        }
    }

    public static class SavedState
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
        Parcelable adapterState;
        ClassLoader loader;
        int position;

        SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel);
            ClassLoader classLoader2 = classLoader;
            if (classLoader == null) {
                classLoader2 = ((Object)((Object)this)).getClass().getClassLoader();
            }
            this.position = parcel.readInt();
            this.adapterState = parcel.readParcelable(classLoader2);
            this.loader = classLoader2;
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        public String toString() {
            return "FragmentPager.SavedState{" + Integer.toHexString(System.identityHashCode((Object)this)) + " position=" + this.position + "}";
        }

        public void writeToParcel(Parcel parcel, int n2) {
            super.writeToParcel(parcel, n2);
            parcel.writeInt(this.position);
            parcel.writeParcelable(this.adapterState, n2);
        }
    }

    static class ViewPositionComparator
    implements Comparator<View> {
        ViewPositionComparator() {
        }

        @Override
        public int compare(View object, View object2) {
            object = (LayoutParams)object.getLayoutParams();
            object2 = (LayoutParams)object2.getLayoutParams();
            if (object.isDecor != object2.isDecor) {
                if (object.isDecor) {
                    return 1;
                }
                return -1;
            }
            return object.position - object2.position;
        }
    }
}

