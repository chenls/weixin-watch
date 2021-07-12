/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.content.Context
 *  android.database.DataSetObserver
 *  android.graphics.Point
 *  android.graphics.Rect
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.os.SystemClock
 *  android.util.AttributeSet
 *  android.util.Log
 *  android.util.SparseIntArray
 *  android.view.KeyEvent
 *  android.view.MotionEvent
 *  android.view.VelocityTracker
 *  android.view.View
 *  android.view.View$BaseSavedState
 *  android.view.View$MeasureSpec
 *  android.view.View$OnApplyWindowInsetsListener
 *  android.view.ViewConfiguration
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewGroup$MarginLayoutParams
 *  android.view.ViewParent
 *  android.view.WindowInsets
 *  android.view.animation.DecelerateInterpolator
 *  android.view.animation.Interpolator
 *  android.widget.ScrollView
 *  android.widget.Scroller
 */
package android.support.wearable.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;
import android.support.v4.util.SimpleArrayMap;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewConfigurationCompat;
import android.support.wearable.view.BackgroundController;
import android.support.wearable.view.CardScrollView;
import android.support.wearable.view.GridPagerAdapter;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowInsets;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.ScrollView;
import android.widget.Scroller;

@TargetApi(value=20)
public class GridViewPager
extends ViewGroup {
    private static final int CLOSE_ENOUGH = 2;
    private static final boolean DEBUG_ADAPTER = false;
    private static final boolean DEBUG_LAYOUT = false;
    private static final boolean DEBUG_LIFECYCLE = false;
    private static final boolean DEBUG_LISTENERS = false;
    private static final boolean DEBUG_POPULATE = false;
    private static final boolean DEBUG_ROUND = false;
    private static final boolean DEBUG_SCROLLING = false;
    private static final boolean DEBUG_SETTLING = false;
    private static final boolean DEBUG_TOUCH = false;
    private static final boolean DEBUG_TOUCHSLOP = false;
    private static final int DEFAULT_OFFSCREEN_PAGES = 1;
    private static final int[] LAYOUT_ATTRS = new int[]{16842931};
    private static final int MIN_ACCURATE_VELOCITY = 200;
    private static final int MIN_DISTANCE_FOR_FLING_DP = 40;
    private static final int NO_POINTER = -1;
    private static final Interpolator OVERSCROLL_INTERPOLATOR = new DragFrictionInterpolator();
    private static final int SCROLL_AXIS_X = 0;
    private static final int SCROLL_AXIS_Y = 1;
    public static final int SCROLL_STATE_CONTENT_SETTLING = 3;
    public static final int SCROLL_STATE_DRAGGING = 1;
    public static final int SCROLL_STATE_IDLE = 0;
    public static final int SCROLL_STATE_SETTLING = 2;
    private static final int SLIDE_ANIMATION_DURATION_NORMAL_MS = 300;
    private static final Interpolator SLIDE_INTERPOLATOR = new DecelerateInterpolator(2.5f);
    private static final String TAG = "GridViewPager";
    private int mActivePointerId = -1;
    private GridPagerAdapter mAdapter;
    private OnAdapterChangeListener mAdapterChangeListener;
    private boolean mAdapterChangeNotificationPending;
    private BackgroundController mBackgroundController;
    private boolean mCalledSuper;
    private final int mCloseEnough;
    private int mColMargin;
    private boolean mConsumeInsets = true;
    private final Point mCurItem;
    private boolean mDatasetChangePending;
    private boolean mDelayPopulate;
    private final Runnable mEndScrollRunnable = new Runnable(){

        @Override
        public void run() {
            GridViewPager.this.setScrollState(0);
            GridViewPager.this.populate();
        }
    };
    private int mExpectedCurrentColumnCount;
    private int mExpectedRowCount;
    private boolean mFirstLayout = true;
    private int mGestureInitialScrollY;
    private float mGestureInitialX;
    private float mGestureInitialY;
    private boolean mInLayout;
    private boolean mIsAbleToDrag;
    private boolean mIsBeingDragged;
    private final SimpleArrayMap<Point, ItemInfo> mItems;
    private final int mMinFlingDistance;
    private final int mMinFlingVelocity;
    private final int mMinUsableVelocity;
    private PagerObserver mObserver;
    private int mOffscreenPageCount = 1;
    private GridPagerAdapter mOldAdapter;
    private View.OnApplyWindowInsetsListener mOnApplyWindowInsetsListener;
    private OnPageChangeListener mOnPageChangeListener;
    private float mPointerLastX;
    private float mPointerLastY;
    private final Rect mPopulatedPageBounds;
    private final Rect mPopulatedPages;
    private final SimpleArrayMap<Point, ItemInfo> mRecycledItems;
    private Parcelable mRestoredAdapterState;
    private ClassLoader mRestoredClassLoader;
    private Point mRestoredCurItem;
    private int mRowMargin;
    private SparseIntArray mRowScrollX;
    private int mScrollAxis;
    private int mScrollState = 0;
    private final Scroller mScroller;
    private View mScrollingContent;
    private int mSlideAnimationDurationMs = 300;
    private final Point mTempPoint1;
    private final int mTouchSlop;
    private final int mTouchSlopSquared;
    private VelocityTracker mVelocityTracker = null;
    private WindowInsets mWindowInsets;

    public GridViewPager(Context context) {
        this(context, null, 0);
    }

    public GridViewPager(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public GridViewPager(Context context, AttributeSet attributeSet, int n2) {
        super(context, attributeSet, n2);
        attributeSet = ViewConfiguration.get((Context)this.getContext());
        float f2 = context.getResources().getDisplayMetrics().density;
        this.mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop((ViewConfiguration)attributeSet);
        this.mTouchSlopSquared = this.mTouchSlop * this.mTouchSlop;
        this.mMinFlingVelocity = attributeSet.getScaledMinimumFlingVelocity();
        this.mMinFlingDistance = (int)(40.0f * f2);
        this.mMinUsableVelocity = (int)(200.0f * f2);
        this.mCloseEnough = (int)(2.0f * f2);
        this.mCurItem = new Point();
        this.mItems = new SimpleArrayMap();
        this.mRecycledItems = new SimpleArrayMap();
        this.mPopulatedPages = new Rect();
        this.mPopulatedPageBounds = new Rect();
        this.mScroller = new Scroller(context, SLIDE_INTERPOLATOR, true);
        this.mTempPoint1 = new Point();
        this.setOverScrollMode(1);
        this.mRowScrollX = new SparseIntArray();
        this.mBackgroundController = new BackgroundController();
        this.mBackgroundController.attachTo((View)this);
    }

    private void adapterChanged(GridPagerAdapter gridPagerAdapter, GridPagerAdapter gridPagerAdapter2) {
        if (this.mAdapterChangeListener != null) {
            this.mAdapterChangeListener.onAdapterChanged(gridPagerAdapter, gridPagerAdapter2);
        }
        if (this.mBackgroundController != null) {
            this.mBackgroundController.onAdapterChanged(gridPagerAdapter, gridPagerAdapter2);
        }
    }

    private ItemInfo addNewItem(int n2, int n3) {
        ItemInfo itemInfo;
        Point point = new Point(n2, n3);
        ItemInfo itemInfo2 = itemInfo = this.mRecycledItems.remove(point);
        if (itemInfo == null) {
            itemInfo2 = new ItemInfo();
            itemInfo2.object = this.mAdapter.instantiateItem(this, n3, n2);
            itemInfo2.positionX = n2;
            itemInfo2.positionY = n3;
        }
        point.set(n2, n3);
        itemInfo2.positionX = n2;
        itemInfo2.positionY = n3;
        this.mItems.put(point, itemInfo2);
        return itemInfo2;
    }

    private void cancelDragGesture() {
        this.cancelPendingInputEvents();
        long l2 = SystemClock.uptimeMillis();
        MotionEvent motionEvent = MotionEvent.obtain((long)l2, (long)l2, (int)3, (float)0.0f, (float)0.0f, (int)0);
        motionEvent.setSource(4098);
        this.dispatchTouchEvent(motionEvent);
        motionEvent.recycle();
    }

    /*
     * Enabled aggressive block sorting
     */
    private void completeScroll(boolean bl2) {
        boolean bl3 = this.mScrollState == 2;
        if (bl3) {
            this.mScroller.abortAnimation();
            int n2 = this.getRowScrollX(this.mCurItem.y);
            int n3 = this.getScrollY();
            int n4 = this.mScroller.getCurrX();
            int n5 = this.mScroller.getCurrY();
            if (n2 != n4 || n3 != n5) {
                this.scrollTo(n4, n5);
            }
        }
        this.mScrollingContent = null;
        this.mDelayPopulate = false;
        if (bl3) {
            if (!bl2) {
                this.mEndScrollRunnable.run();
                return;
            }
            ViewCompat.postOnAnimation((View)this, this.mEndScrollRunnable);
        }
    }

    private int computePageLeft(int n2) {
        return (this.getContentWidth() + this.mColMargin) * n2 + this.getPaddingLeft();
    }

    private int computePageTop(int n2) {
        return (this.getContentHeight() + this.mRowMargin) * n2 + this.getPaddingTop();
    }

    /*
     * Enabled aggressive block sorting
     */
    private void dataSetChanged() {
        int n2;
        this.mExpectedRowCount = n2 = this.mAdapter.getRowCount();
        Point point = new Point(this.mCurItem);
        boolean bl2 = false;
        SimpleArrayMap<Point, ItemInfo> simpleArrayMap = new SimpleArrayMap<Point, ItemInfo>();
        for (int i2 = this.mItems.size() - 1; i2 >= 0; --i2) {
            boolean bl3;
            Point point2 = this.mItems.keyAt(i2);
            ItemInfo itemInfo = this.mItems.valueAt(i2);
            Point point3 = this.mAdapter.getItemPosition(itemInfo.object);
            this.mAdapter.applyItemPosition(itemInfo.object, point3);
            if (point3 == GridPagerAdapter.POSITION_UNCHANGED) {
                simpleArrayMap.put(point2, itemInfo);
                bl3 = bl2;
            } else if (point3 == GridPagerAdapter.POSITION_NONE) {
                boolean bl4 = bl2;
                if (!bl2) {
                    this.mAdapter.startUpdate(this);
                    bl4 = true;
                }
                this.mAdapter.destroyItem(this, itemInfo.positionY, itemInfo.positionX, itemInfo.object);
                bl3 = bl4;
                if (this.mCurItem.equals(itemInfo.positionX, itemInfo.positionY)) {
                    point.y = GridViewPager.limit(this.mCurItem.y, 0, Math.max(0, n2 - 1));
                    if (point.y < n2) {
                        point.x = GridViewPager.limit(this.mCurItem.x, 0, this.mAdapter.getColumnCount(point.y) - 1);
                        bl3 = bl4;
                    } else {
                        point.x = 0;
                        bl3 = bl4;
                    }
                }
            } else {
                bl3 = bl2;
                if (!point3.equals(itemInfo.positionX, itemInfo.positionY)) {
                    if (this.mCurItem.equals(itemInfo.positionX, itemInfo.positionY)) {
                        point.set(point3.x, point3.y);
                    }
                    itemInfo.positionX = point3.x;
                    itemInfo.positionY = point3.y;
                    simpleArrayMap.put(new Point(point3), itemInfo);
                    bl3 = bl2;
                }
            }
            bl2 = bl3;
        }
        this.mItems.clear();
        this.mItems.putAll(simpleArrayMap);
        if (bl2) {
            this.mAdapter.finishUpdate(this);
        }
        this.mExpectedCurrentColumnCount = this.mExpectedRowCount > 0 ? this.mAdapter.getColumnCount(point.y) : 0;
        this.dispatchOnDataSetChanged();
        this.setCurrentItemInternal(point.y, point.x, false, true);
        this.requestLayout();
    }

    private static String debugIndent(int n2) {
        StringBuilder stringBuilder = new StringBuilder((n2 * 2 + 3) * 2);
        for (int i2 = 0; i2 < n2 * 2 + 3; ++i2) {
            stringBuilder.append(' ').append(' ');
        }
        return stringBuilder.toString();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private int determineTargetPage(int n2, int n3, float f2, int n4, int n5, int n6, int n7) {
        n2 = n6;
        if (Math.abs(n6) < this.mMinUsableVelocity) {
            n2 = (int)Math.copySign(n6, n7);
        }
        float f3 = 0.5f / Math.max(Math.abs(0.5f - f2), 0.001f);
        if (Math.abs(n7) > this.mMinFlingDistance && (float)Math.abs(n2) + f3 * 100.0f > (float)this.mMinFlingVelocity) {
            if (n2 > 0) {
                n2 = n3;
                return GridViewPager.limit(n2, n4, n5);
            }
            n2 = n3 + 1;
            return GridViewPager.limit(n2, n4, n5);
        }
        n2 = Math.round((float)n3 + f2);
        return GridViewPager.limit(n2, n4, n5);
    }

    private void dispatchOnDataSetChanged() {
        if (this.mAdapterChangeListener != null) {
            this.mAdapterChangeListener.onDataSetChanged();
        }
        if (this.mBackgroundController != null) {
            this.mBackgroundController.onDataSetChanged();
        }
    }

    private void endDrag() {
        this.mIsBeingDragged = false;
        this.mIsAbleToDrag = false;
        if (this.mVelocityTracker != null) {
            this.mVelocityTracker.recycle();
            this.mVelocityTracker = null;
        }
    }

    /*
     * Handled duff style switch with additional control
     * Enabled aggressive block sorting
     */
    private boolean executeKeyEvent(KeyEvent keyEvent) {
        boolean bl2;
        boolean bl3 = bl2 = false;
        int n2 = 0;
        block8: do {
            switch (n2 == 0 ? keyEvent.getKeyCode() : n2) {
                default: {
                    bl3 = bl2;
                    n2 = 61;
                    continue block8;
                }
                case 21: {
                    bl3 = this.pageLeft();
                    n2 = 61;
                    continue block8;
                }
                case 22: {
                    bl3 = this.pageRight();
                    n2 = 61;
                    continue block8;
                }
                case 19: {
                    bl3 = this.pageUp();
                    n2 = 61;
                    continue block8;
                }
                case 20: {
                    bl3 = this.pageDown();
                }
                case 61: {
                    return bl3;
                }
                case 62: 
            }
            break;
        } while (true);
        this.debug();
        return true;
    }

    private View getChildForInfo(ItemInfo itemInfo) {
        if (itemInfo.object != null) {
            int n2 = this.getChildCount();
            for (int i2 = 0; i2 < n2; ++i2) {
                View view = this.getChildAt(i2);
                if (!this.mAdapter.isViewFromObject(view, itemInfo.object)) continue;
                return view;
            }
        }
        return null;
    }

    private int getContentHeight() {
        return this.getMeasuredHeight() - (this.getPaddingTop() + this.getPaddingBottom());
    }

    private int getContentWidth() {
        return this.getMeasuredWidth() - (this.getPaddingLeft() + this.getPaddingRight());
    }

    private int getRowScrollX(int n2) {
        return this.mRowScrollX.get(n2, 0);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private int getScrollableDistance(View view, int n2) {
        int n3 = 0;
        if (view instanceof CardScrollView) {
            return ((CardScrollView)view).getAvailableScrollDelta(n2);
        }
        if (!(view instanceof ScrollView)) return n3;
        return this.getScrollableDistance((ScrollView)view, n2);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private int getScrollableDistance(ScrollView scrollView, int n2) {
        int n3;
        int n4 = n3 = 0;
        if (scrollView.getChildCount() <= 0) return n4;
        View view = scrollView.getChildAt(0);
        int n5 = scrollView.getHeight();
        int n6 = view.getHeight();
        n4 = n3;
        if (n6 <= n5) return n4;
        if (n2 > 0) {
            return Math.min(n6 - n5 - scrollView.getScrollY(), 0);
        }
        n4 = n3;
        if (n2 >= 0) return n4;
        return -scrollView.getScrollY();
    }

    private float getXIndex(float f2) {
        int n2 = this.getContentWidth() + this.mColMargin;
        if (n2 == 0) {
            Log.e((String)TAG, (String)"getXIndex() called with zero width.");
            return 0.0f;
        }
        return f2 / (float)n2;
    }

    private float getYIndex(float f2) {
        int n2 = this.getContentHeight() + this.mRowMargin;
        if (n2 == 0) {
            Log.e((String)TAG, (String)"getYIndex() called with zero height.");
            return 0.0f;
        }
        return f2 / (float)n2;
    }

    private boolean handlePointerDown(MotionEvent motionEvent) {
        if (this.mIsBeingDragged) {
            return false;
        }
        this.mActivePointerId = MotionEventCompat.getPointerId(motionEvent, 0);
        this.mGestureInitialX = motionEvent.getX();
        this.mGestureInitialY = motionEvent.getY();
        this.mGestureInitialScrollY = this.getScrollY();
        this.mPointerLastX = this.mGestureInitialX;
        this.mPointerLastY = this.mGestureInitialY;
        this.mIsAbleToDrag = true;
        this.mVelocityTracker = VelocityTracker.obtain();
        this.mVelocityTracker.addMovement(motionEvent);
        this.mScroller.computeScrollOffset();
        if ((this.mScrollState == 2 || this.mScrollState == 3) && this.mScrollAxis == 0 && Math.abs(this.mScroller.getFinalX() - this.mScroller.getCurrX()) > this.mCloseEnough || this.mScrollAxis == 1 && Math.abs(this.mScroller.getFinalY() - this.mScroller.getCurrY()) > this.mCloseEnough) {
            this.mScroller.abortAnimation();
            this.mDelayPopulate = false;
            this.populate();
            this.mIsBeingDragged = true;
            this.requestParentDisallowInterceptTouchEvent(true);
            this.setScrollState(1);
            return false;
        }
        this.completeScroll(false);
        this.mIsBeingDragged = false;
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean handlePointerMove(MotionEvent motionEvent) {
        int n2 = this.mActivePointerId;
        if (n2 == -1) {
            return false;
        }
        if ((n2 = motionEvent.findPointerIndex(n2)) == -1) {
            return this.mIsBeingDragged;
        }
        float f2 = MotionEventCompat.getX(motionEvent, n2);
        float f3 = MotionEventCompat.getY(motionEvent, n2);
        float f4 = f2 - this.mPointerLastX;
        float f5 = Math.abs(f4);
        float f6 = f3 - this.mPointerLastY;
        float f7 = Math.abs(f6);
        if (this.mIsBeingDragged) {
            // empty if block
        }
        if (!this.mIsBeingDragged && f5 * f5 + f7 * f7 > (float)this.mTouchSlopSquared) {
            this.mIsBeingDragged = true;
            this.requestParentDisallowInterceptTouchEvent(true);
            this.setScrollState(1);
            this.mScrollAxis = f7 >= f5 ? 1 : 0;
            if (f7 > 0.0f && f5 > 0.0f) {
                double d2 = Math.sqrt(f5 * f5 + f7 * f7);
                d2 = Math.acos((double)f5 / d2);
                f5 = (float)(Math.sin(d2) * (double)this.mTouchSlop);
                f7 = (float)(Math.cos(d2) * (double)this.mTouchSlop);
            } else if (f7 == 0.0f) {
                f7 = this.mTouchSlop;
                f5 = 0.0f;
            } else {
                f7 = 0.0f;
                f5 = this.mTouchSlop;
            }
            f7 = f4 > 0.0f ? this.mPointerLastX + f7 : this.mPointerLastX - f7;
            this.mPointerLastX = f7;
            f5 = f6 > 0.0f ? this.mPointerLastY + f5 : this.mPointerLastY - f5;
            this.mPointerLastY = f5;
        }
        if (this.mIsBeingDragged && this.performDrag(f5 = this.mScrollAxis == 0 ? f2 : this.mPointerLastX, f7 = this.mScrollAxis == 1 ? f3 : this.mPointerLastY)) {
            ViewCompat.postInvalidateOnAnimation((View)this);
        }
        this.mVelocityTracker.addMovement(motionEvent);
        return this.mIsBeingDragged;
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean handlePointerUp(MotionEvent motionEvent) {
        int n2;
        if (!this.mIsBeingDragged || this.mExpectedRowCount == 0) {
            this.mActivePointerId = -1;
            this.endDrag();
            return false;
        }
        VelocityTracker velocityTracker = this.mVelocityTracker;
        velocityTracker.addMovement(motionEvent);
        velocityTracker.computeCurrentVelocity(1000);
        int n3 = motionEvent.findPointerIndex(this.mActivePointerId);
        int n4 = this.mCurItem.x;
        int n5 = this.mCurItem.y;
        int n6 = 0;
        ItemInfo itemInfo = this.infoForCurrentScrollPosition();
        switch (this.mScrollAxis) {
            default: {
                n3 = n4;
                n2 = n5;
                break;
            }
            case 0: {
                n3 = (int)(motionEvent.getRawX() - this.mGestureInitialX);
                n6 = (int)velocityTracker.getXVelocity(this.mActivePointerId);
                n2 = itemInfo.positionX;
                float f2 = this.getXIndex(this.getRowScrollX(itemInfo.positionY) - this.computePageLeft(itemInfo.positionX));
                n3 = this.determineTargetPage(this.mCurItem.x, n2, f2, this.mPopulatedPages.left, this.mPopulatedPages.right, n6, n3);
                n2 = n5;
                break;
            }
            case 1: {
                motionEvent.getX(n3);
                n6 = this.mGestureInitialScrollY;
                n3 = this.getScrollY();
                int n7 = (int)velocityTracker.getYVelocity(this.mActivePointerId);
                n2 = itemInfo.positionY;
                float f3 = this.getYIndex(this.getScrollY() - this.computePageTop(itemInfo.positionY));
                if (f3 == 0.0f) {
                    motionEvent = this.getChildForInfo(this.infoForCurrentScrollPosition());
                    int n8 = this.getScrollableDistance((View)motionEvent, -n7);
                    n6 = n7;
                    n2 = n5;
                    n3 = n4;
                    if (n8 == 0) break;
                    this.mScrollingContent = motionEvent;
                    n6 = n7;
                    n2 = n5;
                    n3 = n4;
                    if (Math.abs(n7) < Math.abs(this.mMinFlingVelocity)) break;
                    this.flingContent(0, n8, 0, -n7);
                    this.endDrag();
                    n6 = n7;
                    n2 = n5;
                    n3 = n4;
                    break;
                }
                n2 = this.determineTargetPage(this.mCurItem.y, n2, f3, this.mPopulatedPages.top, this.mPopulatedPages.bottom, n7, n6 - n3);
                n6 = n7;
                n3 = n4;
            }
        }
        if (this.mScrollState != 3) {
            this.mDelayPopulate = true;
            if (n2 != this.mCurItem.y) {
                n3 = this.mAdapter.getCurrentColumnForRow(n2, this.mCurItem.x);
            }
            this.setCurrentItemInternal(n2, n3, true, true, n6);
        }
        this.mActivePointerId = -1;
        this.endDrag();
        return false;
    }

    private static boolean inRange(int n2, int n3, int n4) {
        return n2 >= n3 && n2 <= n4;
    }

    private ItemInfo infoForChild(View view) {
        for (int i2 = 0; i2 < this.mItems.size(); ++i2) {
            ItemInfo itemInfo = this.mItems.valueAt(i2);
            if (itemInfo == null || !this.mAdapter.isViewFromObject(view, itemInfo.object)) continue;
            return itemInfo;
        }
        return null;
    }

    private ItemInfo infoForCurrentScrollPosition() {
        return this.infoForScrollPosition(this.getRowScrollX((int)this.getYIndex(this.getScrollY())), this.getScrollY());
    }

    private ItemInfo infoForPosition(int n2, int n3) {
        this.mTempPoint1.set(n2, n3);
        return this.mItems.get(this.mTempPoint1);
    }

    private ItemInfo infoForPosition(Point point) {
        return this.mItems.get(point);
    }

    private ItemInfo infoForScrollPosition(int n2, int n3) {
        ItemInfo itemInfo;
        n3 = (int)this.getYIndex(n3);
        n2 = (int)this.getXIndex(n2);
        ItemInfo itemInfo2 = itemInfo = this.infoForPosition(n2, n3);
        if (itemInfo == null) {
            itemInfo2 = new ItemInfo();
            itemInfo2.positionX = n2;
            itemInfo2.positionY = n3;
        }
        return itemInfo2;
    }

    private static float limit(float f2, float f3, float f4) {
        if (f2 < f3) {
            return f3;
        }
        if (f2 > f4) {
            return f4;
        }
        return f2;
    }

    private static float limit(float f2, int n2) {
        if (n2 > 0) {
            return Math.max(0.0f, Math.min(f2, (float)n2));
        }
        return Math.min(0.0f, Math.max(f2, (float)n2));
    }

    private static int limit(int n2, int n3, int n4) {
        if (n2 < n3) {
            return n3;
        }
        if (n2 > n4) {
            return n4;
        }
        return n2;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void onSecondaryPointerUp(MotionEvent motionEvent) {
        int n2 = MotionEventCompat.getActionIndex(motionEvent);
        if (MotionEventCompat.getPointerId(motionEvent, n2) == this.mActivePointerId) {
            n2 = n2 == 0 ? 1 : 0;
            this.mPointerLastX = MotionEventCompat.getX(motionEvent, n2);
            this.mPointerLastY = MotionEventCompat.getY(motionEvent, n2);
            this.mActivePointerId = MotionEventCompat.getPointerId(motionEvent, n2);
            if (this.mVelocityTracker != null) {
                this.mVelocityTracker.clear();
            }
        }
    }

    private boolean pageDown() {
        if (this.mAdapter != null && this.mCurItem.y < this.mAdapter.getRowCount() - 1) {
            this.setCurrentItem(this.mCurItem.x, this.mCurItem.y + 1, true);
            return true;
        }
        return false;
    }

    private boolean pageLeft() {
        if (this.mCurItem.x > 0) {
            this.setCurrentItem(this.mCurItem.x - 1, this.mCurItem.y, true);
            return true;
        }
        return false;
    }

    private boolean pageRight() {
        if (this.mAdapter != null && this.mCurItem.x < this.mAdapter.getColumnCount(this.mCurItem.y) - 1) {
            this.setCurrentItem(this.mCurItem.x + 1, this.mCurItem.y, true);
            return true;
        }
        return false;
    }

    private boolean pageScrolled(int n2, int n3) {
        boolean bl2 = false;
        if (this.mItems.size() == 0) {
            this.mCalledSuper = false;
            this.onPageScrolled(0, 0, 0.0f, 0.0f, 0, 0);
            if (!this.mCalledSuper) {
                throw new IllegalStateException("onPageScrolled did not call superclass implementation");
            }
        } else {
            ItemInfo itemInfo = this.infoForCurrentScrollPosition();
            int n4 = this.computePageLeft(itemInfo.positionX);
            int n5 = this.computePageTop(itemInfo.positionY);
            n2 = this.getPaddingLeft() + n2 - n4;
            n3 = this.getPaddingTop() + n3 - n5;
            float f2 = this.getXIndex(n2);
            float f3 = this.getYIndex(n3);
            this.mCalledSuper = false;
            this.onPageScrolled(itemInfo.positionX, itemInfo.positionY, f2, f3, n2, n3);
            if (!this.mCalledSuper) {
                throw new IllegalStateException("onPageScrolled did not call superclass implementation");
            }
            bl2 = true;
        }
        return bl2;
    }

    private boolean pageUp() {
        if (this.mCurItem.y > 0) {
            this.setCurrentItem(this.mCurItem.x, this.mCurItem.y - 1, true);
            return true;
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean performDrag(float f2, float f3) {
        float f4;
        float f5;
        float f6;
        block8: {
            int n2;
            int n3;
            int n4;
            int n5;
            float f7;
            block9: {
                float f8;
                int n6;
                f7 = this.mPointerLastX - f2;
                float f9 = this.mPointerLastY - f3;
                this.mPointerLastX = f2;
                this.mPointerLastY = f3;
                Rect rect = this.mPopulatedPages;
                n5 = this.computePageLeft(rect.left) - this.getPaddingLeft();
                n4 = this.computePageLeft(rect.right) - this.getPaddingLeft();
                n3 = this.computePageTop(rect.top) - this.getPaddingTop();
                n2 = this.computePageTop(rect.bottom) - this.getPaddingTop();
                f6 = this.getRowScrollX(this.mCurItem.y);
                f5 = this.getScrollY();
                f2 = f9;
                f4 = f5;
                if (this.mScrollAxis == 1) {
                    n6 = this.getContentHeight() + this.mRowMargin;
                    f2 = f9 < 0.0f ? -(f5 % (float)n6) : ((float)n6 - f5 % (float)n6) % (float)n6;
                    n6 = 0;
                    f8 = f9;
                    f3 = f5;
                    if (Math.abs(f2) <= Math.abs(f9)) {
                        f8 = f9 - f2;
                        f3 = f5 + f2;
                        n6 = 1;
                    }
                    f2 = f8;
                    f4 = f3;
                    if (n6 != 0) {
                        rect = this.getChildForInfo(this.infoForScrollPosition((int)f6, (int)f3));
                        f2 = f8;
                        f4 = f3;
                        if (rect != null) {
                            f9 = GridViewPager.limit(f8, this.getScrollableDistance((View)rect, (int)Math.signum(f8)));
                            rect.scrollBy(0, (int)f9);
                            f2 = f8 - f9;
                            this.mPointerLastY += f9 - (float)((int)f9);
                            f4 = f3;
                        }
                    }
                }
                n6 = (int)((float)((int)f7) + f6);
                int n7 = (int)((float)((int)f2) + f4);
                n6 = n6 < n5 || n6 > n4 || n7 < n3 || n7 > n2 ? 1 : 0;
                f3 = f7;
                f5 = f2;
                if (n6 == 0) break block8;
                n7 = this.getOverScrollMode();
                n6 = this.mScrollAxis == 0 && n5 < n4 || this.mScrollAxis == 1 && n3 < n2 ? 1 : 0;
                if (n7 != 0 && (n6 == 0 || n7 != 1)) break block9;
                f3 = f6 > (float)n4 ? f6 - (float)n4 : (f6 < (float)n5 ? f6 - (float)n5 : 0.0f);
                f8 = f4 > (float)n2 ? f4 - (float)n2 : (f4 < (float)n3 ? f4 - (float)n3 : 0.0f);
                f9 = f7;
                if (Math.abs(f3) > 0.0f) {
                    f9 = f7;
                    if (Math.signum(f3) == Math.signum(f7)) {
                        f9 = f7 * OVERSCROLL_INTERPOLATOR.getInterpolation(1.0f - Math.abs(f3) / (float)this.getContentWidth());
                    }
                }
                f3 = f9;
                f5 = f2;
                if (Math.abs(f8) > 0.0f) {
                    f3 = f9;
                    f5 = f2;
                    if (Math.signum(f8) == Math.signum(f2)) {
                        f5 = f2 * OVERSCROLL_INTERPOLATOR.getInterpolation(1.0f - Math.abs(f8) / (float)this.getContentHeight());
                        f3 = f9;
                    }
                }
                break block8;
            }
            f3 = GridViewPager.limit(f7, (float)n5 - f6, (float)n4 - f6);
            f5 = GridViewPager.limit(f2, (float)n3 - f4, (float)n2 - f4);
        }
        f2 = f6 + f3;
        f3 = f4 + f5;
        this.mPointerLastX += f2 - (float)((int)f2);
        this.mPointerLastY += f3 - (float)((int)f3);
        this.scrollTo((int)f2, (int)f3);
        this.pageScrolled((int)f2, (int)f3);
        return true;
    }

    private boolean pointInRange(int n2, int n3) {
        boolean bl2;
        boolean bl3 = bl2 = false;
        if (GridViewPager.inRange(n3, 0, this.mExpectedRowCount - 1)) {
            bl3 = bl2;
            if (GridViewPager.inRange(n2, 0, this.mAdapter.getColumnCount(n3) - 1)) {
                bl3 = true;
            }
        }
        return bl3;
    }

    private boolean pointInRange(Point point) {
        return this.pointInRange(point.x, point.y);
    }

    private void populate() {
        if (this.mAdapter != null && this.mAdapter.getRowCount() > 0) {
            this.populate(this.mCurItem.x, this.mCurItem.y);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void populate(int n2, int n3) {
        block15: {
            block14: {
                Point point = new Point();
                if (this.mCurItem.x != n2 || this.mCurItem.y != n3) {
                    point.set(this.mCurItem.x, this.mCurItem.y);
                    this.mCurItem.set(n2, n3);
                }
                if (this.mDelayPopulate || this.getWindowToken() == null) break block14;
                this.mAdapter.startUpdate(this);
                this.mPopulatedPageBounds.setEmpty();
                int n4 = this.mAdapter.getRowCount();
                if (this.mExpectedRowCount != n4) {
                    throw new IllegalStateException("Adapter row count changed without a call to notifyDataSetChanged()");
                }
                int n5 = this.mAdapter.getColumnCount(n3);
                if (n5 < 1) {
                    throw new IllegalStateException("All rows must have at least 1 column");
                }
                this.mExpectedRowCount = n4;
                this.mExpectedCurrentColumnCount = n5;
                int n6 = Math.max(1, this.mOffscreenPageCount);
                int n7 = Math.max(0, n3 - n6);
                n4 = Math.min(n4 - 1, n3 + n6);
                int n8 = Math.max(0, n2 - n6);
                n6 = Math.min(n5 - 1, n2 + n6);
                for (n5 = this.mItems.size() - 1; n5 >= 0; --n5) {
                    int n9;
                    ItemInfo itemInfo = this.mItems.valueAt(n5);
                    if (itemInfo.positionY != n3 ? itemInfo.positionX == (n9 = this.mAdapter.getCurrentColumnForRow(itemInfo.positionY, this.mCurItem.x)) && itemInfo.positionY >= n7 && itemInfo.positionY <= n4 : itemInfo.positionX >= n8 && itemInfo.positionX <= n6) continue;
                    Point point2 = this.mItems.keyAt(n5);
                    this.mItems.removeAt(n5);
                    point2.set(itemInfo.positionX, itemInfo.positionY);
                    this.mRecycledItems.put(point2, itemInfo);
                }
                this.mTempPoint1.y = n3;
                this.mTempPoint1.x = n8;
                while (this.mTempPoint1.x <= n6) {
                    if (!this.mItems.containsKey(this.mTempPoint1)) {
                        this.addNewItem(this.mTempPoint1.x, this.mTempPoint1.y);
                    }
                    Point point3 = this.mTempPoint1;
                    ++point3.x;
                }
                this.mTempPoint1.y = n7;
                while (this.mTempPoint1.y <= n4) {
                    this.mTempPoint1.x = this.mAdapter.getCurrentColumnForRow(this.mTempPoint1.y, n2);
                    if (!this.mItems.containsKey(this.mTempPoint1)) {
                        this.addNewItem(this.mTempPoint1.x, this.mTempPoint1.y);
                    }
                    if (this.mTempPoint1.y != this.mCurItem.y) {
                        this.setRowScrollX(this.mTempPoint1.y, this.computePageLeft(this.mTempPoint1.x) - this.getPaddingLeft());
                    }
                    Point point4 = this.mTempPoint1;
                    ++point4.y;
                }
                for (n2 = this.mRecycledItems.size() - 1; n2 >= 0; --n2) {
                    ItemInfo itemInfo = this.mRecycledItems.removeAt(n2);
                    this.mAdapter.destroyItem(this, itemInfo.positionY, itemInfo.positionX, itemInfo.object);
                }
                this.mRecycledItems.clear();
                this.mAdapter.finishUpdate(this);
                this.mPopulatedPages.set(n8, n7, n6, n4);
                this.mPopulatedPageBounds.set(this.computePageLeft(n8) - this.getPaddingLeft(), this.computePageTop(n7) - this.getPaddingTop(), this.computePageLeft(n6 + 1) - this.getPaddingRight(), this.computePageTop(n4 + 1) + this.getPaddingBottom());
                if (this.mAdapterChangeNotificationPending) {
                    this.mAdapterChangeNotificationPending = false;
                    this.adapterChanged(this.mOldAdapter, this.mAdapter);
                    this.mOldAdapter = null;
                }
                if (this.mDatasetChangePending) break block15;
            }
            return;
        }
        this.mDatasetChangePending = false;
        this.dispatchOnDataSetChanged();
    }

    /*
     * Enabled aggressive block sorting
     */
    private void recomputeScrollPosition(int n2, int n3, int n4, int n5, int n6, int n7, int n8, int n9) {
        if (n3 > 0 && n5 > 0) {
            int n10 = this.getPaddingLeft();
            int n11 = this.getPaddingRight();
            int n12 = this.getPaddingLeft();
            int n13 = this.getPaddingRight();
            int n14 = this.getPaddingTop();
            int n15 = this.getPaddingBottom();
            int n16 = this.getPaddingTop();
            int n17 = this.getPaddingBottom();
            float f2 = (float)this.getRowScrollX(this.mCurItem.y) / (float)(n3 - n12 - n13 + n7);
            n2 = (int)((float)(n2 - n10 - n11 + n6) * f2);
            f2 = (float)this.getScrollY() / (float)(n5 - n16 - n17 + n9);
            n3 = (int)((float)(n4 - n14 - n15 + n8) * f2);
            this.scrollTo(n2, n3);
            if (this.mScroller.isFinished()) return;
            ItemInfo itemInfo = this.infoForPosition(this.mCurItem);
            n4 = this.computePageLeft(itemInfo.positionX);
            n5 = this.getPaddingLeft();
            n6 = this.computePageTop(itemInfo.positionY);
            n7 = this.getPaddingTop();
            n8 = this.mScroller.getDuration();
            n9 = this.mScroller.timePassed();
            this.mScroller.startScroll(n2, n3, n4 - n5, n6 - n7, n8 - n9);
            return;
        }
        ItemInfo itemInfo = this.infoForPosition(this.mCurItem);
        if (itemInfo == null) return;
        n2 = this.computePageLeft(itemInfo.positionX) - this.getPaddingLeft();
        n3 = this.computePageTop(itemInfo.positionY) - this.getPaddingTop();
        if (n2 == this.getRowScrollX(itemInfo.positionY) && n3 == this.getScrollY()) return;
        this.completeScroll(false);
        this.scrollTo(n2, n3);
    }

    private void requestParentDisallowInterceptTouchEvent(boolean bl2) {
        ViewParent viewParent = this.getParent();
        if (viewParent != null) {
            viewParent.requestDisallowInterceptTouchEvent(bl2);
        }
    }

    private void scrollCurrentRowTo(int n2) {
        this.scrollRowTo(this.mCurItem.y, n2);
    }

    private void scrollRowTo(int n2, int n3) {
        if (this.getRowScrollX(n2) == n3) {
            return;
        }
        int n4 = this.getChildCount();
        int n5 = this.getRowScrollX(n2);
        for (int i2 = 0; i2 < n4; ++i2) {
            View view = this.getChildAt(i2);
            ItemInfo itemInfo = this.infoForChild(view);
            if (itemInfo == null || itemInfo.positionY != n2) continue;
            view.offsetLeftAndRight(-(n3 - n5));
            this.postInvalidateOnAnimation();
        }
        this.setRowScrollX(n2, n3);
    }

    private static String scrollStateToString(int n2) {
        switch (n2) {
            default: {
                return "";
            }
            case 1: {
                return "DRAGGING";
            }
            case 0: {
                return "IDLE";
            }
            case 2: {
                return "SETTLING";
            }
            case 3: 
        }
        return "CONTENT_SETTLING";
    }

    private void scrollToItem(int n2, int n3, boolean bl2, int n4, boolean bl3) {
        ItemInfo itemInfo = this.infoForPosition(n2, n3);
        int n5 = 0;
        int n6 = 0;
        if (itemInfo != null) {
            n5 = this.computePageLeft(itemInfo.positionX) - this.getPaddingLeft();
            n6 = this.computePageTop(itemInfo.positionY) - this.getPaddingTop();
        }
        this.mAdapter.setCurrentColumnForRow(n3, n2);
        if (bl3) {
            if (this.mOnPageChangeListener != null) {
                this.mOnPageChangeListener.onPageSelected(n3, n2);
            }
            if (this.mBackgroundController != null) {
                this.mBackgroundController.onPageSelected(n3, n2);
            }
        }
        if (bl2) {
            this.smoothScrollTo(n5, n6, n4);
            return;
        }
        this.completeScroll(false);
        this.scrollTo(n5, n6);
        this.pageScrolled(n5, n6);
    }

    private void setRowScrollX(int n2, int n3) {
        this.mRowScrollX.put(n2, n3);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void setScrollState(int n2) {
        block5: {
            block4: {
                if (this.mScrollState == n2) break block4;
                this.mScrollState = n2;
                if (this.mOnPageChangeListener != null) {
                    this.mOnPageChangeListener.onPageScrollStateChanged(n2);
                }
                if (this.mBackgroundController != null) break block5;
            }
            return;
        }
        this.mBackgroundController.onPageScrollStateChanged(n2);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void addView(View view, int n2, ViewGroup.LayoutParams object) {
        this.infoForChild(view);
        Object object2 = object;
        if (!this.checkLayoutParams((ViewGroup.LayoutParams)object)) {
            object2 = this.generateLayoutParams((ViewGroup.LayoutParams)object);
        }
        LayoutParams layoutParams = (LayoutParams)((Object)object2);
        if (this.mInLayout) {
            layoutParams.needsMeasure = true;
            this.addViewInLayout(view, n2, (ViewGroup.LayoutParams)object2);
        } else {
            super.addView(view, n2, object2);
        }
        if (this.mWindowInsets != null) {
            view.onApplyWindowInsets(this.mWindowInsets);
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean canScrollHorizontally(int n2) {
        boolean bl2 = true;
        if (this.getVisibility() != 0) return false;
        if (this.mAdapter == null) return false;
        if (this.mItems.isEmpty()) {
            return false;
        }
        int n3 = this.getRowScrollX(this.mCurItem.y);
        int n4 = this.mExpectedCurrentColumnCount;
        if (n2 > 0) {
            if (this.getPaddingLeft() + n3 < this.computePageLeft(n4 - 1)) return bl2;
            return false;
        }
        if (n3 > 0) return bl2;
        return false;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean canScrollVertically(int n2) {
        boolean bl2 = true;
        if (this.getVisibility() != 0) return false;
        if (this.mAdapter == null) return false;
        if (this.mItems.isEmpty()) {
            return false;
        }
        int n3 = this.getScrollY();
        int n4 = this.mExpectedRowCount;
        if (n2 > 0) {
            if (this.getPaddingTop() + n3 < this.computePageTop(n4 - 1)) return bl2;
            return false;
        }
        if (n3 > 0) return bl2;
        return false;
    }

    protected boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams && super.checkLayoutParams(layoutParams);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void computeScroll() {
        if (!this.mScroller.isFinished() && this.mScroller.computeScrollOffset()) {
            if (this.mScrollState == 3) {
                if (this.mScrollingContent == null) {
                    this.mScroller.abortAnimation();
                } else {
                    int n2 = this.mScroller.getCurrX();
                    int n3 = this.mScroller.getCurrY();
                    this.mScrollingContent.scrollTo(n2, n3);
                }
            } else {
                int n4 = this.getRowScrollX(this.mCurItem.y);
                int n5 = this.getScrollY();
                int n6 = this.mScroller.getCurrX();
                int n7 = this.mScroller.getCurrY();
                if (n4 != n6 || n5 != n7) {
                    this.scrollTo(n6, n7);
                    if (!this.pageScrolled(n6, n7)) {
                        this.mScroller.abortAnimation();
                        this.scrollTo(0, 0);
                    }
                }
            }
            ViewCompat.postInvalidateOnAnimation((View)this);
            return;
        }
        this.completeScroll(true);
    }

    public void debug() {
        this.debug(0);
    }

    protected void debug(int n2) {
        super.debug(n2);
        String string2 = GridViewPager.debugIndent(n2);
        Log.d((String)"View", (String)(string2 + "mCurItem={" + this.mCurItem + "}"));
        string2 = GridViewPager.debugIndent(n2);
        Log.d((String)"View", (String)(string2 + "mAdapter={" + this.mAdapter + "}"));
        string2 = GridViewPager.debugIndent(n2);
        Log.d((String)"View", (String)(string2 + "mRowCount=" + this.mExpectedRowCount));
        string2 = GridViewPager.debugIndent(n2);
        Log.d((String)"View", (String)(string2 + "mCurrentColumnCount=" + this.mExpectedCurrentColumnCount));
        int n3 = this.mItems.size();
        if (n3 != 0) {
            string2 = GridViewPager.debugIndent(n2);
            Log.d((String)"View", (String)(string2 + "mItems={"));
        }
        for (int i2 = 0; i2 < n3; ++i2) {
            string2 = GridViewPager.debugIndent(n2 + 1);
            Log.d((String)"View", (String)(string2 + this.mItems.keyAt(i2) + " => " + this.mItems.valueAt(i2)));
        }
        if (n3 != 0) {
            string2 = GridViewPager.debugIndent(n2);
            Log.d((String)"View", (String)(string2 + "}"));
        }
    }

    public WindowInsets dispatchApplyWindowInsets(WindowInsets windowInsets) {
        WindowInsets windowInsets2 = this.onApplyWindowInsets(windowInsets);
        if (this.mOnApplyWindowInsetsListener != null) {
            this.mOnApplyWindowInsetsListener.onApplyWindowInsets((View)this, windowInsets2);
        }
        windowInsets = windowInsets2;
        if (this.mConsumeInsets) {
            windowInsets = windowInsets2.consumeSystemWindowInsets();
        }
        return windowInsets;
    }

    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        return super.dispatchKeyEvent(keyEvent) || this.executeKeyEvent(keyEvent);
    }

    /*
     * Enabled aggressive block sorting
     */
    void flingContent(int n2, int n3, int n4, int n5) {
        int n6;
        int n7;
        if (this.mScrollingContent == null) {
            return;
        }
        if (n4 == 0 && n5 == 0) {
            this.completeScroll(false);
            this.setScrollState(0);
            return;
        }
        int n8 = this.mScrollingContent.getScrollX();
        int n9 = this.mScrollingContent.getScrollY();
        this.setScrollState(3);
        if (n4 > 0) {
            n7 = n8;
            n6 = n8 + n2;
            n2 = n7;
        } else {
            n2 = n8 + n2;
            n6 = n8;
        }
        if (n5 > 0) {
            n7 = n9;
            n3 = n9 + n3;
        } else {
            n7 = n9 + n3;
            n3 = n9;
        }
        this.mScroller.fling(n8, n9, n4, n5, n2, n6, n7, n3);
        ViewCompat.postInvalidateOnAnimation((View)this);
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

    public GridPagerAdapter getAdapter() {
        return this.mAdapter;
    }

    public Point getCurrentItem() {
        return new Point(this.mCurItem);
    }

    public int getOffscreenPageCount() {
        return this.mOffscreenPageCount;
    }

    public int getPageColumnMargin() {
        return this.mColMargin;
    }

    public int getPageRowMargin() {
        return this.mRowMargin;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void measureChild(View view, LayoutParams layoutParams) {
        int n2 = this.getContentWidth();
        int n3 = this.getContentHeight();
        int n4 = layoutParams.width == -2 ? 0 : 0x40000000;
        int n5 = layoutParams.height == -2 ? 0 : 0x40000000;
        n4 = View.MeasureSpec.makeMeasureSpec((int)n2, (int)n4);
        n5 = View.MeasureSpec.makeMeasureSpec((int)n3, (int)n5);
        view.measure(GridViewPager.getChildMeasureSpec((int)n4, (int)(layoutParams.leftMargin + layoutParams.rightMargin), (int)layoutParams.width), GridViewPager.getChildMeasureSpec((int)n5, (int)(layoutParams.topMargin + layoutParams.bottomMargin), (int)layoutParams.height));
    }

    public WindowInsets onApplyWindowInsets(WindowInsets windowInsets) {
        int n2 = this.getChildCount();
        for (int i2 = 0; i2 < n2; ++i2) {
            this.getChildAt(i2).dispatchApplyWindowInsets(windowInsets);
        }
        this.mWindowInsets = windowInsets;
        return windowInsets;
    }

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mFirstLayout = true;
        this.getParent().requestFitSystemWindows();
    }

    protected void onDetachedFromWindow() {
        this.removeCallbacks(this.mEndScrollRunnable);
        super.onDetachedFromWindow();
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        boolean bl2 = true;
        int n2 = motionEvent.getAction() & 0xFF;
        if (n2 == 3 || n2 == 1) {
            this.mIsBeingDragged = false;
            this.mIsAbleToDrag = false;
            this.mActivePointerId = -1;
            if (this.mVelocityTracker == null) return false;
            this.mVelocityTracker.recycle();
            this.mVelocityTracker = null;
            return false;
        }
        if (n2 != 0) {
            if (this.mIsBeingDragged) return bl2;
            if (!this.mIsAbleToDrag) {
                return false;
            }
        }
        switch (n2) {
            case 0: {
                this.handlePointerDown(motionEvent);
                return this.mIsBeingDragged;
            }
            case 2: {
                this.handlePointerMove(motionEvent);
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
        n3 = this.getChildCount();
        for (n2 = 0; n2 < n3; ++n2) {
            View view = this.getChildAt(n2);
            LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
            if (layoutParams == null) {
                Log.w((String)TAG, (String)("Got null layout params for child: " + view));
                continue;
            }
            ItemInfo itemInfo = this.infoForChild(view);
            if (itemInfo == null) {
                Log.w((String)TAG, (String)("Unknown child view, not claimed by adapter: " + view));
                continue;
            }
            if (layoutParams.needsMeasure) {
                layoutParams.needsMeasure = false;
                this.measureChild(view, layoutParams);
            }
            n5 = this.computePageLeft(itemInfo.positionX);
            n4 = this.computePageTop(itemInfo.positionY);
            n5 = n5 - this.getRowScrollX(itemInfo.positionY) + layoutParams.leftMargin;
            view.layout(n5, n4 += layoutParams.topMargin, view.getMeasuredWidth() + n5, view.getMeasuredHeight() + n4);
        }
        if (this.mFirstLayout && !this.mItems.isEmpty()) {
            this.scrollToItem(this.mCurItem.x, this.mCurItem.y, false, 0, false);
        }
        this.mFirstLayout = false;
    }

    protected void onMeasure(int n2, int n3) {
        this.setMeasuredDimension(GridViewPager.getDefaultSize((int)0, (int)n2), GridViewPager.getDefaultSize((int)0, (int)n3));
        this.mInLayout = true;
        this.populate();
        this.mInLayout = false;
        n3 = this.getChildCount();
        for (n2 = 0; n2 < n3; ++n2) {
            LayoutParams layoutParams;
            View view = this.getChildAt(n2);
            if (view.getVisibility() == 8 || (layoutParams = (LayoutParams)view.getLayoutParams()) == null) continue;
            this.measureChild(view, layoutParams);
        }
    }

    public void onPageScrolled(int n2, int n3, float f2, float f3, int n4, int n5) {
        this.mCalledSuper = true;
        if (this.mOnPageChangeListener != null) {
            this.mOnPageChangeListener.onPageScrolled(n3, n2, f3, f2, n5, n4);
        }
        if (this.mBackgroundController != null) {
            this.mBackgroundController.onPageScrolled(n3, n2, f3, f2, n5, n4);
        }
    }

    public void onRestoreInstanceState(Parcelable object) {
        if (!(object instanceof SavedState)) {
            super.onRestoreInstanceState(object);
            return;
        }
        object = (SavedState)((Object)object);
        super.onRestoreInstanceState(object.getSuperState());
        if (this.pointInRange(object.currentX, object.currentY)) {
            this.mRestoredCurItem = new Point(object.currentX, object.currentY);
            return;
        }
        this.mCurItem.set(0, 0);
        this.scrollTo(0, 0);
    }

    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.currentX = this.mCurItem.x;
        savedState.currentY = this.mCurItem.y;
        return savedState;
    }

    protected void onSizeChanged(int n2, int n3, int n4, int n5) {
        super.onSizeChanged(n2, n3, n4, n5);
        if (!this.mItems.isEmpty()) {
            this.recomputeScrollPosition(n2, n4, n3, n5, this.mColMargin, this.mColMargin, this.mRowMargin, this.mRowMargin);
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (this.mAdapter == null) {
            return false;
        }
        int n2 = motionEvent.getAction();
        switch (n2 & 0xFF) {
            default: {
                Log.e((String)TAG, (String)("Unknown action type: " + n2));
                return true;
            }
            case 0: {
                this.handlePointerDown(motionEvent);
                return true;
            }
            case 2: {
                this.handlePointerMove(motionEvent);
                return true;
            }
            case 1: 
            case 3: {
                this.handlePointerUp(motionEvent);
                return true;
            }
            case 6: 
        }
        this.onSecondaryPointerUp(motionEvent);
        return true;
    }

    void pageBackgroundChanged(int n2, int n3) {
        if (this.mBackgroundController != null) {
            this.mBackgroundController.onPageBackgroundChanged(n2, n3);
        }
    }

    public void removeView(View view) {
        this.infoForChild(view);
        if (this.mInLayout) {
            this.removeViewInLayout(view);
            return;
        }
        super.removeView(view);
    }

    public void requestFitSystemWindows() {
    }

    void rowBackgroundChanged(int n2) {
        if (this.mBackgroundController != null) {
            this.mBackgroundController.onRowBackgroundChanged(n2);
        }
    }

    public void scrollTo(int n2, int n3) {
        int n4 = n2;
        if (this.mScrollState == 2) {
            n4 = n2;
            if (this.mScrollAxis == 1) {
                n4 = this.getRowScrollX(this.mCurItem.y);
            }
        }
        super.scrollTo(0, n3);
        this.scrollCurrentRowTo(n4);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setAdapter(GridPagerAdapter gridPagerAdapter) {
        Object object;
        block11: {
            block12: {
                if (this.mAdapter != null) {
                    this.mAdapter.unregisterDataSetObserver(this.mObserver);
                    this.mAdapter.setOnBackgroundChangeListener(null);
                    this.mAdapter.startUpdate(this);
                    for (int i2 = 0; i2 < this.mItems.size(); ++i2) {
                        object = this.mItems.valueAt(i2);
                        this.mAdapter.destroyItem(this, ((ItemInfo)object).positionY, ((ItemInfo)object).positionX, ((ItemInfo)object).object);
                    }
                    this.mAdapter.finishUpdate(this);
                    this.mItems.clear();
                    this.removeAllViews();
                    this.scrollTo(0, 0);
                    this.mRowScrollX.clear();
                }
                object = this.mAdapter;
                this.mCurItem.set(0, 0);
                this.mAdapter = gridPagerAdapter;
                this.mExpectedRowCount = 0;
                this.mExpectedCurrentColumnCount = 0;
                if (this.mAdapter == null) break block12;
                if (this.mObserver == null) {
                    this.mObserver = new PagerObserver();
                }
                this.mAdapter.registerDataSetObserver(this.mObserver);
                this.mAdapter.setOnBackgroundChangeListener(this.mBackgroundController);
                this.mDelayPopulate = false;
                boolean bl2 = this.mFirstLayout;
                this.mFirstLayout = true;
                this.mExpectedRowCount = this.mAdapter.getRowCount();
                if (this.mExpectedRowCount > 0) {
                    this.mCurItem.set(0, 0);
                    this.mExpectedCurrentColumnCount = this.mAdapter.getColumnCount(this.mCurItem.y);
                }
                if (this.mRestoredCurItem != null) {
                    this.mAdapter.restoreState(this.mRestoredAdapterState, this.mRestoredClassLoader);
                    this.setCurrentItemInternal(this.mRestoredCurItem.y, this.mRestoredCurItem.x, false, true);
                    this.mRestoredCurItem = null;
                    this.mRestoredAdapterState = null;
                    this.mRestoredClassLoader = null;
                    break block11;
                } else if (!bl2) {
                    this.populate();
                    break block11;
                } else {
                    this.requestLayout();
                }
                break block11;
            }
            if (this.mIsBeingDragged) {
                this.cancelDragGesture();
            }
        }
        if (object == gridPagerAdapter) {
            this.mAdapterChangeNotificationPending = false;
            this.mOldAdapter = null;
            return;
        }
        if (gridPagerAdapter == null) {
            this.mAdapterChangeNotificationPending = false;
            this.adapterChanged((GridPagerAdapter)object, gridPagerAdapter);
            this.mOldAdapter = null;
            return;
        }
        this.mAdapterChangeNotificationPending = true;
        this.mOldAdapter = object;
    }

    public void setConsumeWindowInsets(boolean bl2) {
        this.mConsumeInsets = bl2;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setCurrentItem(int n2, int n3) {
        this.mDelayPopulate = false;
        boolean bl2 = !this.mFirstLayout;
        this.setCurrentItemInternal(n2, n3, bl2, false);
    }

    public void setCurrentItem(int n2, int n3, boolean bl2) {
        this.mDelayPopulate = false;
        this.setCurrentItemInternal(n2, n3, bl2, false);
    }

    void setCurrentItemInternal(int n2, int n3, boolean bl2, boolean bl3) {
        this.setCurrentItemInternal(n2, n3, bl2, bl3, 0);
    }

    /*
     * Enabled aggressive block sorting
     */
    void setCurrentItemInternal(int n2, int n3, boolean bl2, boolean bl3, int n4) {
        if (this.mAdapter == null || this.mAdapter.getRowCount() <= 0 || !bl3 && this.mCurItem.equals(n3, n2) && this.mItems.size() != 0) {
            return;
        }
        if ((n3 = GridViewPager.limit(n3, 0, this.mAdapter.getColumnCount(n2 = GridViewPager.limit(n2, 0, this.mAdapter.getRowCount() - 1)) - 1)) != this.mCurItem.x) {
            this.mScrollAxis = 0;
            bl3 = true;
        } else if (n2 != this.mCurItem.y) {
            this.mScrollAxis = 1;
            bl3 = true;
        } else {
            bl3 = false;
        }
        if (!this.mFirstLayout) {
            this.populate(n3, n2);
            this.scrollToItem(n3, n2, bl2, n4, bl3);
            return;
        }
        this.mCurItem.set(0, 0);
        this.mAdapter.setCurrentColumnForRow(n2, n3);
        if (bl3) {
            if (this.mOnPageChangeListener != null) {
                this.mOnPageChangeListener.onPageSelected(n2, n3);
            }
            if (this.mBackgroundController != null) {
                this.mBackgroundController.onPageSelected(n2, n3);
            }
        }
        this.requestLayout();
    }

    public void setOffscreenPageCount(int n2) {
        int n3 = n2;
        if (n2 < 1) {
            Log.w((String)TAG, (String)("Requested offscreen page limit " + n2 + " too small; defaulting to " + 1));
            n3 = 1;
        }
        if (n3 != this.mOffscreenPageCount) {
            this.mOffscreenPageCount = n3;
            this.populate();
        }
    }

    public void setOnAdapterChangeListener(OnAdapterChangeListener onAdapterChangeListener) {
        this.mAdapterChangeListener = onAdapterChangeListener;
        if (onAdapterChangeListener != null && this.mAdapter != null && !this.mAdapterChangeNotificationPending) {
            onAdapterChangeListener.onAdapterChanged(null, this.mAdapter);
        }
    }

    public void setOnApplyWindowInsetsListener(View.OnApplyWindowInsetsListener onApplyWindowInsetsListener) {
        this.mOnApplyWindowInsetsListener = onApplyWindowInsetsListener;
    }

    public void setOnPageChangeListener(OnPageChangeListener onPageChangeListener) {
        this.mOnPageChangeListener = onPageChangeListener;
    }

    public void setPageMargins(int n2, int n3) {
        int n4 = this.mRowMargin;
        this.mRowMargin = n2;
        n2 = this.mColMargin;
        this.mColMargin = n3;
        n3 = this.getWidth();
        int n5 = this.getHeight();
        if (!this.mFirstLayout && !this.mItems.isEmpty()) {
            this.recomputeScrollPosition(n3, n3, n5, n5, this.mColMargin, n2, this.mRowMargin, n4);
            this.requestLayout();
        }
    }

    public void setSlideAnimationDuration(int n2) {
        this.mSlideAnimationDurationMs = n2;
    }

    void smoothScrollTo(int n2, int n3) {
        this.smoothScrollTo(n2, n3, 0);
    }

    void smoothScrollTo(int n2, int n3, int n4) {
        if (this.getChildCount() == 0) {
            return;
        }
        n4 = this.getRowScrollX(this.mCurItem.y);
        int n5 = this.getScrollY();
        if ((n2 -= n4) == 0 && (n3 -= n5) == 0) {
            this.completeScroll(false);
            this.populate();
            this.setScrollState(0);
            return;
        }
        this.setScrollState(2);
        int n6 = this.mSlideAnimationDurationMs;
        this.mScroller.startScroll(n4, n5, n2, n3, n6);
        ViewCompat.postInvalidateOnAnimation((View)this);
    }

    private static final class DragFrictionInterpolator
    implements Interpolator {
        private static final float DEFAULT_FALLOFF = 4.0f;
        private final float falloffRate;

        public DragFrictionInterpolator() {
            this(4.0f);
        }

        public DragFrictionInterpolator(float f2) {
            this.falloffRate = f2;
        }

        public float getInterpolation(float f2) {
            double d2 = Math.exp(2.0f * f2 * this.falloffRate);
            return (float)((d2 - 1.0) / (1.0 + d2)) * (1.0f / this.falloffRate);
        }
    }

    static class ItemInfo {
        Object object;
        int positionX;
        int positionY;

        ItemInfo() {
        }

        public String toString() {
            return this.positionX + "," + this.positionY + " => " + this.object;
        }
    }

    public static class LayoutParams
    extends ViewGroup.MarginLayoutParams {
        public int gravity;
        public boolean needsMeasure;

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

    public static interface OnAdapterChangeListener {
        public void onAdapterChanged(GridPagerAdapter var1, GridPagerAdapter var2);

        public void onDataSetChanged();
    }

    public static interface OnPageChangeListener {
        public void onPageScrollStateChanged(int var1);

        public void onPageScrolled(int var1, int var2, float var3, float var4, int var5, int var6);

        public void onPageSelected(int var1, int var2);
    }

    private class PagerObserver
    extends DataSetObserver {
        private PagerObserver() {
        }

        public void onChanged() {
            GridViewPager.this.dataSetChanged();
        }

        public void onInvalidated() {
            GridViewPager.this.dataSetChanged();
        }
    }

    private static class SavedState
    extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>(){

            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            public SavedState[] newArray(int n2) {
                return new SavedState[n2];
            }
        };
        int currentX;
        int currentY;

        private SavedState(Parcel parcel) {
            super(parcel);
            this.currentX = parcel.readInt();
            this.currentY = parcel.readInt();
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        public void writeToParcel(Parcel parcel, int n2) {
            super.writeToParcel(parcel, n2);
            parcel.writeInt(this.currentX);
            parcel.writeInt(this.currentY);
        }
    }
}

