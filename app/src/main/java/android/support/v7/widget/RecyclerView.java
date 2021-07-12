/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.database.Observable
 *  android.graphics.Canvas
 *  android.graphics.PointF
 *  android.graphics.Rect
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.os.SystemClock
 *  android.util.AttributeSet
 *  android.util.Log
 *  android.util.SparseArray
 *  android.util.SparseIntArray
 *  android.util.TypedValue
 *  android.view.FocusFinder
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
 *  android.view.accessibility.AccessibilityManager
 *  android.view.animation.Interpolator
 */
package android.support.v7.widget;

import android.content.Context;
import android.database.Observable;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.v4.os.TraceCompat;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.NestedScrollingChild;
import android.support.v4.view.NestedScrollingChildHelper;
import android.support.v4.view.ScrollingView;
import android.support.v4.view.VelocityTrackerCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewConfigurationCompat;
import android.support.v4.view.accessibility.AccessibilityEventCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v4.view.accessibility.AccessibilityRecordCompat;
import android.support.v4.widget.EdgeEffectCompat;
import android.support.v4.widget.ScrollerCompat;
import android.support.v7.recyclerview.R;
import android.support.v7.widget.AdapterHelper;
import android.support.v7.widget.ChildHelper;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerViewAccessibilityDelegate;
import android.support.v7.widget.ViewInfoStore;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.util.TypedValue;
import android.view.FocusFinder;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.animation.Interpolator;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RecyclerView
extends ViewGroup
implements ScrollingView,
NestedScrollingChild {
    static final boolean ALLOW_SIZE_IN_UNSPECIFIED_SPEC;
    private static final boolean DEBUG = false;
    static final boolean DISPATCH_TEMP_DETACH = false;
    private static final boolean FORCE_INVALIDATE_DISPLAY_LIST;
    public static final int HORIZONTAL = 0;
    private static final int INVALID_POINTER = -1;
    public static final int INVALID_TYPE = -1;
    private static final Class<?>[] LAYOUT_MANAGER_CONSTRUCTOR_SIGNATURE;
    private static final int MAX_SCROLL_DURATION = 2000;
    private static final int[] NESTED_SCROLLING_ATTRS;
    public static final long NO_ID = -1L;
    public static final int NO_POSITION = -1;
    public static final int SCROLL_STATE_DRAGGING = 1;
    public static final int SCROLL_STATE_IDLE = 0;
    public static final int SCROLL_STATE_SETTLING = 2;
    private static final String TAG = "RecyclerView";
    public static final int TOUCH_SLOP_DEFAULT = 0;
    public static final int TOUCH_SLOP_PAGING = 1;
    private static final String TRACE_BIND_VIEW_TAG = "RV OnBindView";
    private static final String TRACE_CREATE_VIEW_TAG = "RV CreateView";
    private static final String TRACE_HANDLE_ADAPTER_UPDATES_TAG = "RV PartialInvalidate";
    private static final String TRACE_ON_DATA_SET_CHANGE_LAYOUT_TAG = "RV FullInvalidate";
    private static final String TRACE_ON_LAYOUT_TAG = "RV OnLayout";
    private static final String TRACE_SCROLL_TAG = "RV Scroll";
    public static final int VERTICAL = 1;
    private static final Interpolator sQuinticInterpolator;
    private RecyclerViewAccessibilityDelegate mAccessibilityDelegate;
    private final AccessibilityManager mAccessibilityManager;
    private OnItemTouchListener mActiveOnItemTouchListener;
    private Adapter mAdapter;
    AdapterHelper mAdapterHelper;
    private boolean mAdapterUpdateDuringMeasure;
    private EdgeEffectCompat mBottomGlow;
    private ChildDrawingOrderCallback mChildDrawingOrderCallback;
    ChildHelper mChildHelper;
    private boolean mClipToPadding;
    private boolean mDataSetHasChangedAfterLayout = false;
    private int mEatRequestLayout = 0;
    private int mEatenAccessibilityChangeFlags;
    private boolean mFirstLayoutComplete;
    private boolean mHasFixedSize;
    private boolean mIgnoreMotionEventTillDown;
    private int mInitialTouchX;
    private int mInitialTouchY;
    private boolean mIsAttached;
    ItemAnimator mItemAnimator;
    private ItemAnimator.ItemAnimatorListener mItemAnimatorListener;
    private Runnable mItemAnimatorRunner;
    private final ArrayList<ItemDecoration> mItemDecorations;
    boolean mItemsAddedOrRemoved = false;
    boolean mItemsChanged = false;
    private int mLastTouchX;
    private int mLastTouchY;
    @VisibleForTesting
    LayoutManager mLayout;
    private boolean mLayoutFrozen;
    private int mLayoutOrScrollCounter = 0;
    private boolean mLayoutRequestEaten;
    private EdgeEffectCompat mLeftGlow;
    private final int mMaxFlingVelocity;
    private final int mMinFlingVelocity;
    private final int[] mMinMaxLayoutPositions;
    private final int[] mNestedOffsets;
    private final RecyclerViewDataObserver mObserver = new RecyclerViewDataObserver();
    private List<OnChildAttachStateChangeListener> mOnChildAttachStateListeners;
    private final ArrayList<OnItemTouchListener> mOnItemTouchListeners;
    private SavedState mPendingSavedState;
    private final boolean mPostUpdatesOnAnimation;
    private boolean mPostedAnimatorRunner = false;
    final Recycler mRecycler = new Recycler();
    private RecyclerListener mRecyclerListener;
    private EdgeEffectCompat mRightGlow;
    private final int[] mScrollConsumed;
    private float mScrollFactor;
    private OnScrollListener mScrollListener;
    private List<OnScrollListener> mScrollListeners;
    private final int[] mScrollOffset;
    private int mScrollPointerId = -1;
    private int mScrollState = 0;
    private NestedScrollingChildHelper mScrollingChildHelper;
    final State mState;
    private final Rect mTempRect;
    private EdgeEffectCompat mTopGlow;
    private int mTouchSlop;
    private final Runnable mUpdateChildViewsRunnable;
    private VelocityTracker mVelocityTracker;
    private final ViewFlinger mViewFlinger;
    private final ViewInfoStore.ProcessCallback mViewInfoProcessCallback;
    final ViewInfoStore mViewInfoStore = new ViewInfoStore();

    /*
     * Enabled aggressive block sorting
     */
    static {
        NESTED_SCROLLING_ATTRS = new int[]{16843830};
        boolean bl2 = Build.VERSION.SDK_INT == 18 || Build.VERSION.SDK_INT == 19 || Build.VERSION.SDK_INT == 20;
        FORCE_INVALIDATE_DISPLAY_LIST = bl2;
        bl2 = Build.VERSION.SDK_INT >= 23;
        ALLOW_SIZE_IN_UNSPECIFIED_SPEC = bl2;
        LAYOUT_MANAGER_CONSTRUCTOR_SIGNATURE = new Class[]{Context.class, AttributeSet.class, Integer.TYPE, Integer.TYPE};
        sQuinticInterpolator = new Interpolator(){

            public float getInterpolation(float f2) {
                return (f2 -= 1.0f) * f2 * f2 * f2 * f2 + 1.0f;
            }
        };
    }

    public RecyclerView(Context context) {
        this(context, null);
    }

    public RecyclerView(Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    /*
     * Enabled aggressive block sorting
     */
    public RecyclerView(Context context, @Nullable AttributeSet attributeSet, int n2) {
        super(context, attributeSet, n2);
        boolean bl2;
        this.mUpdateChildViewsRunnable = new Runnable(){

            @Override
            public void run() {
                if (!RecyclerView.this.mFirstLayoutComplete || RecyclerView.this.isLayoutRequested()) {
                    return;
                }
                if (RecyclerView.this.mLayoutFrozen) {
                    RecyclerView.access$302(RecyclerView.this, true);
                    return;
                }
                RecyclerView.this.consumePendingUpdateOperations();
            }
        };
        this.mTempRect = new Rect();
        this.mItemDecorations = new ArrayList();
        this.mOnItemTouchListeners = new ArrayList();
        this.mItemAnimator = new DefaultItemAnimator();
        this.mScrollFactor = Float.MIN_VALUE;
        this.mViewFlinger = new ViewFlinger();
        this.mState = new State();
        this.mItemAnimatorListener = new ItemAnimatorRestoreListener();
        this.mMinMaxLayoutPositions = new int[2];
        this.mScrollOffset = new int[2];
        this.mScrollConsumed = new int[2];
        this.mNestedOffsets = new int[2];
        this.mItemAnimatorRunner = new Runnable(){

            @Override
            public void run() {
                if (RecyclerView.this.mItemAnimator != null) {
                    RecyclerView.this.mItemAnimator.runPendingAnimations();
                }
                RecyclerView.access$602(RecyclerView.this, false);
            }
        };
        this.mViewInfoProcessCallback = new ViewInfoStore.ProcessCallback(){

            @Override
            public void processAppeared(ViewHolder viewHolder, ItemAnimator.ItemHolderInfo itemHolderInfo, ItemAnimator.ItemHolderInfo itemHolderInfo2) {
                RecyclerView.this.animateAppearance(viewHolder, itemHolderInfo, itemHolderInfo2);
            }

            @Override
            public void processDisappeared(ViewHolder viewHolder, @NonNull ItemAnimator.ItemHolderInfo itemHolderInfo, @Nullable ItemAnimator.ItemHolderInfo itemHolderInfo2) {
                RecyclerView.this.mRecycler.unscrapView(viewHolder);
                RecyclerView.this.animateDisappearance(viewHolder, itemHolderInfo, itemHolderInfo2);
            }

            /*
             * Enabled aggressive block sorting
             */
            @Override
            public void processPersistent(ViewHolder viewHolder, @NonNull ItemAnimator.ItemHolderInfo itemHolderInfo, @NonNull ItemAnimator.ItemHolderInfo itemHolderInfo2) {
                viewHolder.setIsRecyclable(false);
                if (RecyclerView.this.mDataSetHasChangedAfterLayout) {
                    if (!RecyclerView.this.mItemAnimator.animateChange(viewHolder, viewHolder, itemHolderInfo, itemHolderInfo2)) return;
                    RecyclerView.this.postAnimationRunner();
                    return;
                }
                if (!RecyclerView.this.mItemAnimator.animatePersistence(viewHolder, itemHolderInfo, itemHolderInfo2)) return;
                RecyclerView.this.postAnimationRunner();
            }

            @Override
            public void unused(ViewHolder viewHolder) {
                RecyclerView.this.mLayout.removeAndRecycleView(viewHolder.itemView, RecyclerView.this.mRecycler);
            }
        };
        this.setScrollContainer(true);
        this.setFocusableInTouchMode(true);
        boolean bl3 = Build.VERSION.SDK_INT >= 16;
        this.mPostUpdatesOnAnimation = bl3;
        ViewConfiguration viewConfiguration = ViewConfiguration.get((Context)context);
        this.mTouchSlop = viewConfiguration.getScaledTouchSlop();
        this.mMinFlingVelocity = viewConfiguration.getScaledMinimumFlingVelocity();
        this.mMaxFlingVelocity = viewConfiguration.getScaledMaximumFlingVelocity();
        bl3 = ViewCompat.getOverScrollMode((View)this) == 2;
        this.setWillNotDraw(bl3);
        this.mItemAnimator.setListener(this.mItemAnimatorListener);
        this.initAdapterManager();
        this.initChildrenHelper();
        if (ViewCompat.getImportantForAccessibility((View)this) == 0) {
            ViewCompat.setImportantForAccessibility((View)this, 1);
        }
        this.mAccessibilityManager = (AccessibilityManager)this.getContext().getSystemService("accessibility");
        this.setAccessibilityDelegateCompat(new RecyclerViewAccessibilityDelegate(this));
        bl3 = bl2 = true;
        if (attributeSet != null) {
            viewConfiguration = context.obtainStyledAttributes(attributeSet, R.styleable.RecyclerView, n2, 0);
            String string2 = viewConfiguration.getString(R.styleable.RecyclerView_layoutManager);
            viewConfiguration.recycle();
            this.createLayoutManager(context, string2, attributeSet, n2, 0);
            bl3 = bl2;
            if (Build.VERSION.SDK_INT >= 21) {
                context = context.obtainStyledAttributes(attributeSet, NESTED_SCROLLING_ATTRS, n2, 0);
                bl3 = context.getBoolean(0, true);
                context.recycle();
            }
        }
        this.setNestedScrollingEnabled(bl3);
    }

    static /* synthetic */ boolean access$302(RecyclerView recyclerView, boolean bl2) {
        recyclerView.mLayoutRequestEaten = bl2;
        return bl2;
    }

    static /* synthetic */ boolean access$4502(RecyclerView recyclerView, boolean bl2) {
        recyclerView.mAdapterUpdateDuringMeasure = bl2;
        return bl2;
    }

    static /* synthetic */ boolean access$602(RecyclerView recyclerView, boolean bl2) {
        recyclerView.mPostedAnimatorRunner = bl2;
        return bl2;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void addAnimatingView(ViewHolder viewHolder) {
        View view = viewHolder.itemView;
        boolean bl2 = view.getParent() == this;
        this.mRecycler.unscrapView(this.getChildViewHolder(view));
        if (viewHolder.isTmpDetached()) {
            this.mChildHelper.attachViewToParent(view, -1, view.getLayoutParams(), true);
            return;
        }
        if (!bl2) {
            this.mChildHelper.addView(view, true);
            return;
        }
        this.mChildHelper.hide(view);
    }

    private void animateAppearance(@NonNull ViewHolder viewHolder, @Nullable ItemAnimator.ItemHolderInfo itemHolderInfo, @NonNull ItemAnimator.ItemHolderInfo itemHolderInfo2) {
        viewHolder.setIsRecyclable(false);
        if (this.mItemAnimator.animateAppearance(viewHolder, itemHolderInfo, itemHolderInfo2)) {
            this.postAnimationRunner();
        }
    }

    private void animateChange(@NonNull ViewHolder viewHolder, @NonNull ViewHolder viewHolder2, @NonNull ItemAnimator.ItemHolderInfo itemHolderInfo, @NonNull ItemAnimator.ItemHolderInfo itemHolderInfo2, boolean bl2, boolean bl3) {
        viewHolder.setIsRecyclable(false);
        if (bl2) {
            this.addAnimatingView(viewHolder);
        }
        if (viewHolder != viewHolder2) {
            if (bl3) {
                this.addAnimatingView(viewHolder2);
            }
            viewHolder.mShadowedHolder = viewHolder2;
            this.addAnimatingView(viewHolder);
            this.mRecycler.unscrapView(viewHolder);
            viewHolder2.setIsRecyclable(false);
            viewHolder2.mShadowingHolder = viewHolder;
        }
        if (this.mItemAnimator.animateChange(viewHolder, viewHolder2, itemHolderInfo, itemHolderInfo2)) {
            this.postAnimationRunner();
        }
    }

    private void animateDisappearance(@NonNull ViewHolder viewHolder, @NonNull ItemAnimator.ItemHolderInfo itemHolderInfo, @Nullable ItemAnimator.ItemHolderInfo itemHolderInfo2) {
        this.addAnimatingView(viewHolder);
        viewHolder.setIsRecyclable(false);
        if (this.mItemAnimator.animateDisappearance(viewHolder, itemHolderInfo, itemHolderInfo2)) {
            this.postAnimationRunner();
        }
    }

    private boolean canReuseUpdatedViewHolder(ViewHolder viewHolder) {
        return this.mItemAnimator == null || this.mItemAnimator.canReuseUpdatedViewHolder(viewHolder, viewHolder.getUnmodifiedPayloads());
    }

    private void cancelTouch() {
        this.resetTouch();
        this.setScrollState(0);
    }

    private void considerReleasingGlowsOnScroll(int n2, int n3) {
        boolean bl2;
        boolean bl3 = bl2 = false;
        if (this.mLeftGlow != null) {
            bl3 = bl2;
            if (!this.mLeftGlow.isFinished()) {
                bl3 = bl2;
                if (n2 > 0) {
                    bl3 = this.mLeftGlow.onRelease();
                }
            }
        }
        bl2 = bl3;
        if (this.mRightGlow != null) {
            bl2 = bl3;
            if (!this.mRightGlow.isFinished()) {
                bl2 = bl3;
                if (n2 < 0) {
                    bl2 = bl3 | this.mRightGlow.onRelease();
                }
            }
        }
        bl3 = bl2;
        if (this.mTopGlow != null) {
            bl3 = bl2;
            if (!this.mTopGlow.isFinished()) {
                bl3 = bl2;
                if (n3 > 0) {
                    bl3 = bl2 | this.mTopGlow.onRelease();
                }
            }
        }
        bl2 = bl3;
        if (this.mBottomGlow != null) {
            bl2 = bl3;
            if (!this.mBottomGlow.isFinished()) {
                bl2 = bl3;
                if (n3 < 0) {
                    bl2 = bl3 | this.mBottomGlow.onRelease();
                }
            }
        }
        if (bl2) {
            ViewCompat.postInvalidateOnAnimation((View)this);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void consumePendingUpdateOperations() {
        block9: {
            block8: {
                if (!this.mFirstLayoutComplete) break block8;
                if (this.mDataSetHasChangedAfterLayout) {
                    TraceCompat.beginSection(TRACE_ON_DATA_SET_CHANGE_LAYOUT_TAG);
                    this.dispatchLayout();
                    TraceCompat.endSection();
                    return;
                }
                if (!this.mAdapterHelper.hasPendingUpdates()) break block8;
                if (this.mAdapterHelper.hasAnyUpdateTypes(4) && !this.mAdapterHelper.hasAnyUpdateTypes(11)) {
                    TraceCompat.beginSection(TRACE_HANDLE_ADAPTER_UPDATES_TAG);
                    this.eatRequestLayout();
                    this.mAdapterHelper.preProcess();
                    if (!this.mLayoutRequestEaten) {
                        if (this.hasUpdatedView()) {
                            this.dispatchLayout();
                        } else {
                            this.mAdapterHelper.consumePostponedUpdates();
                        }
                    }
                    this.resumeRequestLayout(true);
                    TraceCompat.endSection();
                    return;
                }
                if (this.mAdapterHelper.hasPendingUpdates()) break block9;
            }
            return;
        }
        TraceCompat.beginSection(TRACE_ON_DATA_SET_CHANGE_LAYOUT_TAG);
        this.dispatchLayout();
        TraceCompat.endSection();
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void createLayoutManager(Context var1_1, String var2_8, AttributeSet var3_9, int var4_10, int var5_11) {
        if (var2_8 != null && (var2_8 = var2_8.trim()).length() != 0) {
            block13: {
                block15: {
                    var7_12 = this.getFullClassName((Context)var1_1, (String)var2_8);
                    try {
                        if (!this.isInEditMode()) break block13;
                        var2_8 = this.getClass().getClassLoader();
lbl6:
                        // 2 sources

                        while (true) {
                            var8_13 = var2_8.loadClass(var7_12).asSubclass(LayoutManager.class);
                            var2_8 = null;
                            try {
                                var6_14 = var8_13.getConstructor(RecyclerView.LAYOUT_MANAGER_CONSTRUCTOR_SIGNATURE);
                            }
                            catch (NoSuchMethodException var6_15) {
                                try {
                                    var1_1 = var8_13.getConstructor(new Class[0]);
                                    break block15;
                                }
                                catch (NoSuchMethodException var1_2) {
                                    var1_2.initCause(var6_15);
                                    throw new IllegalStateException(var3_9.getPositionDescription() + ": Error creating LayoutManager " + var7_12, var1_2);
                                }
                            }
                            break;
                        }
                    }
                    catch (ClassNotFoundException var1_3) {
                        throw new IllegalStateException(var3_9.getPositionDescription() + ": Unable to find LayoutManager " + var7_12, var1_3);
                    }
                    catch (InvocationTargetException var1_4) {
                        throw new IllegalStateException(var3_9.getPositionDescription() + ": Could not instantiate the LayoutManager: " + var7_12, var1_4);
                    }
                    catch (InstantiationException var1_5) {
                        throw new IllegalStateException(var3_9.getPositionDescription() + ": Could not instantiate the LayoutManager: " + var7_12, var1_5);
                    }
                    catch (IllegalAccessException var1_6) {
                        throw new IllegalStateException(var3_9.getPositionDescription() + ": Cannot access non-public constructor " + var7_12, var1_6);
                    }
                    catch (ClassCastException var1_7) {
                        throw new IllegalStateException(var3_9.getPositionDescription() + ": Class is not a LayoutManager " + var7_12, var1_7);
                    }
                    var2_8 = new Object[]{var1_1, var3_9, var4_10, var5_11};
                    var1_1 = var6_14;
                }
                var1_1.setAccessible(true);
                this.setLayoutManager((LayoutManager)var1_1.newInstance((Object[])var2_8));
                return;
            }
            var2_8 = var1_1.getClassLoader();
            ** continue;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean didChildRangeChange(int n2, int n3) {
        boolean bl2 = false;
        if (this.mChildHelper.getChildCount() == 0) {
            if (n2 != 0) return true;
            if (n3 == 0) return bl2;
            return true;
        }
        this.findMinMaxChildLayoutPositions(this.mMinMaxLayoutPositions);
        if (this.mMinMaxLayoutPositions[0] != n2) return true;
        if (this.mMinMaxLayoutPositions[1] == n3) return bl2;
        return true;
    }

    private void dispatchChildAttached(View view) {
        ViewHolder viewHolder = RecyclerView.getChildViewHolderInt(view);
        this.onChildAttachedToWindow(view);
        if (this.mAdapter != null && viewHolder != null) {
            this.mAdapter.onViewAttachedToWindow(viewHolder);
        }
        if (this.mOnChildAttachStateListeners != null) {
            for (int i2 = this.mOnChildAttachStateListeners.size() - 1; i2 >= 0; --i2) {
                this.mOnChildAttachStateListeners.get(i2).onChildViewAttachedToWindow(view);
            }
        }
    }

    private void dispatchChildDetached(View view) {
        ViewHolder viewHolder = RecyclerView.getChildViewHolderInt(view);
        this.onChildDetachedFromWindow(view);
        if (this.mAdapter != null && viewHolder != null) {
            this.mAdapter.onViewDetachedFromWindow(viewHolder);
        }
        if (this.mOnChildAttachStateListeners != null) {
            for (int i2 = this.mOnChildAttachStateListeners.size() - 1; i2 >= 0; --i2) {
                this.mOnChildAttachStateListeners.get(i2).onChildViewDetachedFromWindow(view);
            }
        }
    }

    private void dispatchContentChangedIfNecessary() {
        int n2 = this.mEatenAccessibilityChangeFlags;
        this.mEatenAccessibilityChangeFlags = 0;
        if (n2 != 0 && this.isAccessibilityEnabled()) {
            AccessibilityEvent accessibilityEvent = AccessibilityEvent.obtain();
            accessibilityEvent.setEventType(2048);
            AccessibilityEventCompat.setContentChangeTypes(accessibilityEvent, n2);
            this.sendAccessibilityEventUnchecked(accessibilityEvent);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void dispatchLayoutStep1() {
        ItemAnimator.ItemHolderInfo itemHolderInfo;
        int n2;
        int n3;
        this.mState.assertLayoutStep(1);
        State.access$2202(this.mState, false);
        this.eatRequestLayout();
        this.mViewInfoStore.clear();
        this.onEnterLayoutOrScroll();
        this.processAdapterUpdatesAndSetAnimationFlags();
        Object object = this.mState;
        boolean bl2 = this.mState.mRunSimpleAnimations && this.mItemsChanged;
        State.access$2702((State)object, bl2);
        this.mItemsChanged = false;
        this.mItemsAddedOrRemoved = false;
        State.access$2402(this.mState, this.mState.mRunPredictiveAnimations);
        this.mState.mItemCount = this.mAdapter.getItemCount();
        this.findMinMaxChildLayoutPositions(this.mMinMaxLayoutPositions);
        if (this.mState.mRunSimpleAnimations) {
            n3 = this.mChildHelper.getChildCount();
            for (n2 = 0; n2 < n3; ++n2) {
                object = RecyclerView.getChildViewHolderInt(this.mChildHelper.getChildAt(n2));
                if (((ViewHolder)object).shouldIgnore() || ((ViewHolder)object).isInvalid() && !this.mAdapter.hasStableIds()) continue;
                itemHolderInfo = this.mItemAnimator.recordPreLayoutInformation(this.mState, (ViewHolder)object, ItemAnimator.buildAdapterChangeFlagsForAnimations((ViewHolder)object), ((ViewHolder)object).getUnmodifiedPayloads());
                this.mViewInfoStore.addToPreLayout((ViewHolder)object, itemHolderInfo);
                if (!this.mState.mTrackOldChangeHolders || !((ViewHolder)object).isUpdated() || ((ViewHolder)object).isRemoved() || ((ViewHolder)object).shouldIgnore() || ((ViewHolder)object).isInvalid()) continue;
                long l2 = this.getChangedHolderKey((ViewHolder)object);
                this.mViewInfoStore.addToOldChangeHolders(l2, (ViewHolder)object);
            }
        }
        if (!this.mState.mRunPredictiveAnimations) {
            this.clearOldPositions();
        } else {
            this.saveOldPositions();
            bl2 = this.mState.mStructureChanged;
            State.access$1802(this.mState, false);
            this.mLayout.onLayoutChildren(this.mRecycler, this.mState);
            State.access$1802(this.mState, bl2);
            for (n2 = 0; n2 < this.mChildHelper.getChildCount(); ++n2) {
                object = RecyclerView.getChildViewHolderInt(this.mChildHelper.getChildAt(n2));
                if (((ViewHolder)object).shouldIgnore() || this.mViewInfoStore.isInPreLayout((ViewHolder)object)) continue;
                int n4 = ItemAnimator.buildAdapterChangeFlagsForAnimations((ViewHolder)object);
                bl2 = ((ViewHolder)object).hasAnyOfTheFlags(8192);
                n3 = n4;
                if (!bl2) {
                    n3 = n4 | 0x1000;
                }
                itemHolderInfo = this.mItemAnimator.recordPreLayoutInformation(this.mState, (ViewHolder)object, n3, ((ViewHolder)object).getUnmodifiedPayloads());
                if (bl2) {
                    this.recordAnimationInfoIfBouncedHiddenView((ViewHolder)object, itemHolderInfo);
                    continue;
                }
                this.mViewInfoStore.addToAppearedInPreLayoutHolders((ViewHolder)object, itemHolderInfo);
            }
            this.clearOldPositions();
        }
        this.onExitLayoutOrScroll();
        this.resumeRequestLayout(false);
        State.access$2102(this.mState, 2);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void dispatchLayoutStep2() {
        this.eatRequestLayout();
        this.onEnterLayoutOrScroll();
        this.mState.assertLayoutStep(6);
        this.mAdapterHelper.consumeUpdatesInOnePass();
        this.mState.mItemCount = this.mAdapter.getItemCount();
        State.access$1702(this.mState, 0);
        State.access$2402(this.mState, false);
        this.mLayout.onLayoutChildren(this.mRecycler, this.mState);
        State.access$1802(this.mState, false);
        this.mPendingSavedState = null;
        State state = this.mState;
        boolean bl2 = this.mState.mRunSimpleAnimations && this.mItemAnimator != null;
        State.access$2502(state, bl2);
        State.access$2102(this.mState, 4);
        this.onExitLayoutOrScroll();
        this.resumeRequestLayout(false);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void dispatchLayoutStep3() {
        this.mState.assertLayoutStep(4);
        this.eatRequestLayout();
        this.onEnterLayoutOrScroll();
        State.access$2102(this.mState, 1);
        if (this.mState.mRunSimpleAnimations) {
            for (int i2 = this.mChildHelper.getChildCount() - 1; i2 >= 0; --i2) {
                ViewHolder viewHolder = RecyclerView.getChildViewHolderInt(this.mChildHelper.getChildAt(i2));
                if (viewHolder.shouldIgnore()) continue;
                long l2 = this.getChangedHolderKey(viewHolder);
                ItemAnimator.ItemHolderInfo itemHolderInfo = this.mItemAnimator.recordPostLayoutInformation(this.mState, viewHolder);
                ViewHolder viewHolder2 = this.mViewInfoStore.getFromOldChangeHolders(l2);
                if (viewHolder2 != null && !viewHolder2.shouldIgnore()) {
                    boolean bl2 = this.mViewInfoStore.isDisappearing(viewHolder2);
                    boolean bl3 = this.mViewInfoStore.isDisappearing(viewHolder);
                    if (bl2 && viewHolder2 == viewHolder) {
                        this.mViewInfoStore.addToPostLayout(viewHolder, itemHolderInfo);
                        continue;
                    }
                    ItemAnimator.ItemHolderInfo itemHolderInfo2 = this.mViewInfoStore.popFromPreLayout(viewHolder2);
                    this.mViewInfoStore.addToPostLayout(viewHolder, itemHolderInfo);
                    itemHolderInfo = this.mViewInfoStore.popFromPostLayout(viewHolder);
                    if (itemHolderInfo2 == null) {
                        this.handleMissingPreInfoForChangeError(l2, viewHolder, viewHolder2);
                        continue;
                    }
                    this.animateChange(viewHolder2, viewHolder, itemHolderInfo2, itemHolderInfo, bl2, bl3);
                    continue;
                }
                this.mViewInfoStore.addToPostLayout(viewHolder, itemHolderInfo);
            }
            this.mViewInfoStore.process(this.mViewInfoProcessCallback);
        }
        this.mLayout.removeAndRecycleScrapInt(this.mRecycler);
        State.access$2802(this.mState, this.mState.mItemCount);
        this.mDataSetHasChangedAfterLayout = false;
        State.access$2502(this.mState, false);
        State.access$2302(this.mState, false);
        LayoutManager.access$2602(this.mLayout, false);
        if (this.mRecycler.mChangedScrap != null) {
            this.mRecycler.mChangedScrap.clear();
        }
        this.onExitLayoutOrScroll();
        this.resumeRequestLayout(false);
        this.mViewInfoStore.clear();
        if (this.didChildRangeChange(this.mMinMaxLayoutPositions[0], this.mMinMaxLayoutPositions[1])) {
            this.dispatchOnScrolled(0, 0);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean dispatchOnItemTouch(MotionEvent motionEvent) {
        int n2 = motionEvent.getAction();
        if (this.mActiveOnItemTouchListener != null) {
            if (n2 != 0) {
                this.mActiveOnItemTouchListener.onTouchEvent(this, motionEvent);
                if (n2 != 3 && n2 != 1) return true;
                this.mActiveOnItemTouchListener = null;
                return true;
            }
            this.mActiveOnItemTouchListener = null;
        }
        if (n2 == 0) return false;
        int n3 = this.mOnItemTouchListeners.size();
        for (n2 = 0; n2 < n3; ++n2) {
            OnItemTouchListener onItemTouchListener = this.mOnItemTouchListeners.get(n2);
            if (!onItemTouchListener.onInterceptTouchEvent(this, motionEvent)) continue;
            this.mActiveOnItemTouchListener = onItemTouchListener;
            return true;
        }
        return false;
    }

    private boolean dispatchOnItemTouchIntercept(MotionEvent motionEvent) {
        int n2 = motionEvent.getAction();
        if (n2 == 3 || n2 == 0) {
            this.mActiveOnItemTouchListener = null;
        }
        int n3 = this.mOnItemTouchListeners.size();
        for (int i2 = 0; i2 < n3; ++i2) {
            OnItemTouchListener onItemTouchListener = this.mOnItemTouchListeners.get(i2);
            if (!onItemTouchListener.onInterceptTouchEvent(this, motionEvent) || n2 == 3) continue;
            this.mActiveOnItemTouchListener = onItemTouchListener;
            return true;
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void findMinMaxChildLayoutPositions(int[] nArray) {
        int n2 = this.mChildHelper.getChildCount();
        if (n2 == 0) {
            nArray[0] = 0;
            nArray[1] = 0;
            return;
        }
        int n3 = Integer.MAX_VALUE;
        int n4 = Integer.MIN_VALUE;
        int n5 = 0;
        while (true) {
            int n6;
            if (n5 >= n2) {
                nArray[0] = n3;
                nArray[1] = n4;
                return;
            }
            ViewHolder viewHolder = RecyclerView.getChildViewHolderInt(this.mChildHelper.getChildAt(n5));
            if (viewHolder.shouldIgnore()) {
                n6 = n3;
                n3 = n4;
            } else {
                int n7 = viewHolder.getLayoutPosition();
                int n8 = n3;
                if (n7 < n3) {
                    n8 = n7;
                }
                n3 = n4;
                n6 = n8;
                if (n7 > n4) {
                    n3 = n7;
                    n6 = n8;
                }
            }
            ++n5;
            n4 = n3;
            n3 = n6;
        }
    }

    private int getAdapterPositionFor(ViewHolder viewHolder) {
        if (viewHolder.hasAnyOfTheFlags(524) || !viewHolder.isBound()) {
            return -1;
        }
        return this.mAdapterHelper.applyPendingUpdatesToPosition(viewHolder.mPosition);
    }

    static ViewHolder getChildViewHolderInt(View view) {
        if (view == null) {
            return null;
        }
        return ((LayoutParams)view.getLayoutParams()).mViewHolder;
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    private String getFullClassName(Context object, String string2) {
        void var1_3;
        void var2_5;
        if (var2_5.charAt(0) == '.') {
            String string3 = object.getPackageName() + (String)var2_5;
            return var1_3;
        } else {
            void var1_4 = var2_5;
            if (var2_5.contains(".")) return var1_3;
            return RecyclerView.class.getPackage().getName() + '.' + (String)var2_5;
        }
    }

    private float getScrollFactor() {
        block3: {
            block2: {
                if (this.mScrollFactor != Float.MIN_VALUE) break block2;
                TypedValue typedValue = new TypedValue();
                if (!this.getContext().getTheme().resolveAttribute(16842829, typedValue, true)) break block3;
                this.mScrollFactor = typedValue.getDimension(this.getContext().getResources().getDisplayMetrics());
            }
            return this.mScrollFactor;
        }
        return 0.0f;
    }

    private NestedScrollingChildHelper getScrollingChildHelper() {
        if (this.mScrollingChildHelper == null) {
            this.mScrollingChildHelper = new NestedScrollingChildHelper((View)this);
        }
        return this.mScrollingChildHelper;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void handleMissingPreInfoForChangeError(long l2, ViewHolder viewHolder, ViewHolder viewHolder2) {
        ViewHolder viewHolder3;
        int n2 = this.mChildHelper.getChildCount();
        int n3 = 0;
        while (true) {
            if (n3 >= n2) {
                Log.e((String)TAG, (String)("Problem while matching changed view holders with the newones. The pre-layout information for the change holder " + viewHolder2 + " cannot be found but it is necessary for " + viewHolder));
                return;
            }
            viewHolder3 = RecyclerView.getChildViewHolderInt(this.mChildHelper.getChildAt(n3));
            if (viewHolder3 != viewHolder && this.getChangedHolderKey(viewHolder3) == l2) {
                if (this.mAdapter == null || !this.mAdapter.hasStableIds()) break;
                throw new IllegalStateException("Two different ViewHolders have the same stable ID. Stable IDs in your adapter MUST BE unique and SHOULD NOT change.\n ViewHolder 1:" + viewHolder3 + " \n View Holder 2:" + viewHolder);
            }
            ++n3;
        }
        throw new IllegalStateException("Two different ViewHolders have the same change ID. This might happen due to inconsistent Adapter update events or if the LayoutManager lays out the same View multiple times.\n ViewHolder 1:" + viewHolder3 + " \n View Holder 2:" + viewHolder);
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean hasUpdatedView() {
        int n2 = this.mChildHelper.getChildCount();
        int n3 = 0;
        while (n3 < n2) {
            ViewHolder viewHolder = RecyclerView.getChildViewHolderInt(this.mChildHelper.getChildAt(n3));
            if (viewHolder != null && !viewHolder.shouldIgnore() && viewHolder.isUpdated()) {
                return true;
            }
            ++n3;
        }
        return false;
    }

    private void initChildrenHelper() {
        this.mChildHelper = new ChildHelper(new ChildHelper.Callback(){

            @Override
            public void addView(View view, int n2) {
                RecyclerView.this.addView(view, n2);
                RecyclerView.this.dispatchChildAttached(view);
            }

            @Override
            public void attachViewToParent(View view, int n2, ViewGroup.LayoutParams layoutParams) {
                ViewHolder viewHolder = RecyclerView.getChildViewHolderInt(view);
                if (viewHolder != null) {
                    if (!viewHolder.isTmpDetached() && !viewHolder.shouldIgnore()) {
                        throw new IllegalArgumentException("Called attach on a child which is not detached: " + viewHolder);
                    }
                    viewHolder.clearTmpDetachFlag();
                }
                RecyclerView.this.attachViewToParent(view, n2, layoutParams);
            }

            @Override
            public void detachViewFromParent(int n2) {
                Object object = this.getChildAt(n2);
                if (object != null && (object = RecyclerView.getChildViewHolderInt((View)object)) != null) {
                    if (((ViewHolder)object).isTmpDetached() && !((ViewHolder)object).shouldIgnore()) {
                        throw new IllegalArgumentException("called detach on an already detached child " + object);
                    }
                    ((ViewHolder)object).addFlags(256);
                }
                RecyclerView.this.detachViewFromParent(n2);
            }

            @Override
            public View getChildAt(int n2) {
                return RecyclerView.this.getChildAt(n2);
            }

            @Override
            public int getChildCount() {
                return RecyclerView.this.getChildCount();
            }

            @Override
            public ViewHolder getChildViewHolder(View view) {
                return RecyclerView.getChildViewHolderInt(view);
            }

            @Override
            public int indexOfChild(View view) {
                return RecyclerView.this.indexOfChild(view);
            }

            @Override
            public void onEnteredHiddenState(View object) {
                if ((object = RecyclerView.getChildViewHolderInt(object)) != null) {
                    ((ViewHolder)object).onEnteredHiddenState();
                }
            }

            @Override
            public void onLeftHiddenState(View object) {
                if ((object = RecyclerView.getChildViewHolderInt(object)) != null) {
                    ((ViewHolder)object).onLeftHiddenState();
                }
            }

            @Override
            public void removeAllViews() {
                int n2 = this.getChildCount();
                for (int i2 = 0; i2 < n2; ++i2) {
                    RecyclerView.this.dispatchChildDetached(this.getChildAt(i2));
                }
                RecyclerView.this.removeAllViews();
            }

            @Override
            public void removeViewAt(int n2) {
                View view = RecyclerView.this.getChildAt(n2);
                if (view != null) {
                    RecyclerView.this.dispatchChildDetached(view);
                }
                RecyclerView.this.removeViewAt(n2);
            }
        });
    }

    private void jumpToPositionForSmoothScroller(int n2) {
        if (this.mLayout == null) {
            return;
        }
        this.mLayout.scrollToPosition(n2);
        this.awakenScrollBars();
    }

    private void onEnterLayoutOrScroll() {
        ++this.mLayoutOrScrollCounter;
    }

    private void onExitLayoutOrScroll() {
        --this.mLayoutOrScrollCounter;
        if (this.mLayoutOrScrollCounter < 1) {
            this.mLayoutOrScrollCounter = 0;
            this.dispatchContentChangedIfNecessary();
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void onPointerUp(MotionEvent motionEvent) {
        int n2 = MotionEventCompat.getActionIndex(motionEvent);
        if (MotionEventCompat.getPointerId(motionEvent, n2) == this.mScrollPointerId) {
            int n3;
            n2 = n2 == 0 ? 1 : 0;
            this.mScrollPointerId = MotionEventCompat.getPointerId(motionEvent, n2);
            this.mLastTouchX = n3 = (int)(MotionEventCompat.getX(motionEvent, n2) + 0.5f);
            this.mInitialTouchX = n3;
            this.mLastTouchY = n2 = (int)(MotionEventCompat.getY(motionEvent, n2) + 0.5f);
            this.mInitialTouchY = n2;
        }
    }

    private void postAnimationRunner() {
        if (!this.mPostedAnimatorRunner && this.mIsAttached) {
            ViewCompat.postOnAnimation((View)this, this.mItemAnimatorRunner);
            this.mPostedAnimatorRunner = true;
        }
    }

    private boolean predictiveItemAnimationsEnabled() {
        return this.mItemAnimator != null && this.mLayout.supportsPredictiveItemAnimations();
    }

    /*
     * Enabled aggressive block sorting
     */
    private void processAdapterUpdatesAndSetAnimationFlags() {
        boolean bl2 = true;
        if (this.mDataSetHasChangedAfterLayout) {
            this.mAdapterHelper.reset();
            this.markKnownViewsInvalid();
            this.mLayout.onItemsChanged(this);
        }
        if (this.predictiveItemAnimationsEnabled()) {
            this.mAdapterHelper.preProcess();
        } else {
            this.mAdapterHelper.consumeUpdatesInOnePass();
        }
        boolean bl3 = this.mItemsAddedOrRemoved || this.mItemsChanged;
        State state = this.mState;
        boolean bl4 = !(!this.mFirstLayoutComplete || this.mItemAnimator == null || !this.mDataSetHasChangedAfterLayout && !bl3 && !this.mLayout.mRequestedSimpleAnimations || this.mDataSetHasChangedAfterLayout && !this.mAdapter.hasStableIds());
        State.access$2502(state, bl4);
        state = this.mState;
        bl4 = this.mState.mRunSimpleAnimations && bl3 && !this.mDataSetHasChangedAfterLayout && this.predictiveItemAnimationsEnabled() ? bl2 : false;
        State.access$2302(state, bl4);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void pullGlows(float f2, float f3, float f4, float f5) {
        boolean bl2;
        boolean bl3 = false;
        if (f3 < 0.0f) {
            this.ensureLeftGlow();
            bl2 = bl3;
            if (this.mLeftGlow.onPull(-f3 / (float)this.getWidth(), 1.0f - f4 / (float)this.getHeight())) {
                bl2 = true;
            }
        } else {
            bl2 = bl3;
            if (f3 > 0.0f) {
                this.ensureRightGlow();
                bl2 = bl3;
                if (this.mRightGlow.onPull(f3 / (float)this.getWidth(), f4 / (float)this.getHeight())) {
                    bl2 = true;
                }
            }
        }
        if (f5 < 0.0f) {
            this.ensureTopGlow();
            bl3 = bl2;
            if (this.mTopGlow.onPull(-f5 / (float)this.getHeight(), f2 / (float)this.getWidth())) {
                bl3 = true;
            }
        } else {
            bl3 = bl2;
            if (f5 > 0.0f) {
                this.ensureBottomGlow();
                bl3 = bl2;
                if (this.mBottomGlow.onPull(f5 / (float)this.getHeight(), 1.0f - f2 / (float)this.getWidth())) {
                    bl3 = true;
                }
            }
        }
        if (bl3 || f3 != 0.0f || f5 != 0.0f) {
            ViewCompat.postInvalidateOnAnimation((View)this);
        }
    }

    private void recordAnimationInfoIfBouncedHiddenView(ViewHolder viewHolder, ItemAnimator.ItemHolderInfo itemHolderInfo) {
        viewHolder.setFlags(0, 8192);
        if (this.mState.mTrackOldChangeHolders && viewHolder.isUpdated() && !viewHolder.isRemoved() && !viewHolder.shouldIgnore()) {
            long l2 = this.getChangedHolderKey(viewHolder);
            this.mViewInfoStore.addToOldChangeHolders(l2, viewHolder);
        }
        this.mViewInfoStore.addToPreLayout(viewHolder, itemHolderInfo);
    }

    private void releaseGlows() {
        boolean bl2 = false;
        if (this.mLeftGlow != null) {
            bl2 = this.mLeftGlow.onRelease();
        }
        boolean bl3 = bl2;
        if (this.mTopGlow != null) {
            bl3 = bl2 | this.mTopGlow.onRelease();
        }
        bl2 = bl3;
        if (this.mRightGlow != null) {
            bl2 = bl3 | this.mRightGlow.onRelease();
        }
        bl3 = bl2;
        if (this.mBottomGlow != null) {
            bl3 = bl2 | this.mBottomGlow.onRelease();
        }
        if (bl3) {
            ViewCompat.postInvalidateOnAnimation((View)this);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean removeAnimatingView(View object) {
        this.eatRequestLayout();
        boolean bl2 = this.mChildHelper.removeViewIfHidden((View)object);
        if (bl2) {
            ViewHolder viewHolder = RecyclerView.getChildViewHolderInt(object);
            this.mRecycler.unscrapView(viewHolder);
            this.mRecycler.recycleViewHolderInternal(viewHolder);
        }
        boolean bl3 = !bl2;
        this.resumeRequestLayout(bl3);
        return bl2;
    }

    private void repositionShadowingViews() {
        int n2 = this.mChildHelper.getChildCount();
        for (int i2 = 0; i2 < n2; ++i2) {
            View view = this.mChildHelper.getChildAt(i2);
            ViewHolder viewHolder = this.getChildViewHolder(view);
            if (viewHolder == null || viewHolder.mShadowingHolder == null) continue;
            viewHolder = viewHolder.mShadowingHolder.itemView;
            int n3 = view.getLeft();
            int n4 = view.getTop();
            if (n3 == viewHolder.getLeft() && n4 == viewHolder.getTop()) continue;
            viewHolder.layout(n3, n4, viewHolder.getWidth() + n3, viewHolder.getHeight() + n4);
        }
    }

    private void resetTouch() {
        if (this.mVelocityTracker != null) {
            this.mVelocityTracker.clear();
        }
        this.stopNestedScroll();
        this.releaseGlows();
    }

    private void setAdapterInternal(Adapter adapter, boolean bl2, boolean bl3) {
        if (this.mAdapter != null) {
            this.mAdapter.unregisterAdapterDataObserver(this.mObserver);
            this.mAdapter.onDetachedFromRecyclerView(this);
        }
        if (!bl2 || bl3) {
            if (this.mItemAnimator != null) {
                this.mItemAnimator.endAnimations();
            }
            if (this.mLayout != null) {
                this.mLayout.removeAndRecycleAllViews(this.mRecycler);
                this.mLayout.removeAndRecycleScrapInt(this.mRecycler);
            }
            this.mRecycler.clear();
        }
        this.mAdapterHelper.reset();
        Adapter adapter2 = this.mAdapter;
        this.mAdapter = adapter;
        if (adapter != null) {
            adapter.registerAdapterDataObserver(this.mObserver);
            adapter.onAttachedToRecyclerView(this);
        }
        if (this.mLayout != null) {
            this.mLayout.onAdapterChanged(adapter2, this.mAdapter);
        }
        this.mRecycler.onAdapterChanged(adapter2, this.mAdapter, bl2);
        State.access$1802(this.mState, true);
        this.markKnownViewsInvalid();
    }

    private void setDataSetChangedAfterLayout() {
        if (this.mDataSetHasChangedAfterLayout) {
            return;
        }
        this.mDataSetHasChangedAfterLayout = true;
        int n2 = this.mChildHelper.getUnfilteredChildCount();
        for (int i2 = 0; i2 < n2; ++i2) {
            ViewHolder viewHolder = RecyclerView.getChildViewHolderInt(this.mChildHelper.getUnfilteredChildAt(i2));
            if (viewHolder == null || viewHolder.shouldIgnore()) continue;
            viewHolder.addFlags(512);
        }
        this.mRecycler.setAdapterPositionsAsUnknown();
    }

    private void setScrollState(int n2) {
        if (n2 == this.mScrollState) {
            return;
        }
        this.mScrollState = n2;
        if (n2 != 2) {
            this.stopScrollersInternal();
        }
        this.dispatchOnScrollStateChanged(n2);
    }

    private void stopScrollersInternal() {
        this.mViewFlinger.stop();
        if (this.mLayout != null) {
            this.mLayout.stopSmoothScroller();
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    void absorbGlows(int n2, int n3) {
        if (n2 < 0) {
            this.ensureLeftGlow();
            this.mLeftGlow.onAbsorb(-n2);
        } else if (n2 > 0) {
            this.ensureRightGlow();
            this.mRightGlow.onAbsorb(n2);
        }
        if (n3 < 0) {
            this.ensureTopGlow();
            this.mTopGlow.onAbsorb(-n3);
        } else if (n3 > 0) {
            this.ensureBottomGlow();
            this.mBottomGlow.onAbsorb(n3);
        }
        if (n2 != 0 || n3 != 0) {
            ViewCompat.postInvalidateOnAnimation((View)this);
        }
    }

    public void addFocusables(ArrayList<View> arrayList, int n2, int n3) {
        if (this.mLayout == null || !this.mLayout.onAddFocusables(this, arrayList, n2, n3)) {
            super.addFocusables(arrayList, n2, n3);
        }
    }

    public void addItemDecoration(ItemDecoration itemDecoration) {
        this.addItemDecoration(itemDecoration, -1);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void addItemDecoration(ItemDecoration itemDecoration, int n2) {
        if (this.mLayout != null) {
            this.mLayout.assertNotInLayoutOrScroll("Cannot add item decoration during a scroll  or layout");
        }
        if (this.mItemDecorations.isEmpty()) {
            this.setWillNotDraw(false);
        }
        if (n2 < 0) {
            this.mItemDecorations.add(itemDecoration);
        } else {
            this.mItemDecorations.add(n2, itemDecoration);
        }
        this.markItemDecorInsetsDirty();
        this.requestLayout();
    }

    public void addOnChildAttachStateChangeListener(OnChildAttachStateChangeListener onChildAttachStateChangeListener) {
        if (this.mOnChildAttachStateListeners == null) {
            this.mOnChildAttachStateListeners = new ArrayList<OnChildAttachStateChangeListener>();
        }
        this.mOnChildAttachStateListeners.add(onChildAttachStateChangeListener);
    }

    public void addOnItemTouchListener(OnItemTouchListener onItemTouchListener) {
        this.mOnItemTouchListeners.add(onItemTouchListener);
    }

    public void addOnScrollListener(OnScrollListener onScrollListener) {
        if (this.mScrollListeners == null) {
            this.mScrollListeners = new ArrayList<OnScrollListener>();
        }
        this.mScrollListeners.add(onScrollListener);
    }

    void assertInLayoutOrScroll(String string2) {
        if (!this.isComputingLayout()) {
            if (string2 == null) {
                throw new IllegalStateException("Cannot call this method unless RecyclerView is computing a layout or scrolling");
            }
            throw new IllegalStateException(string2);
        }
    }

    void assertNotInLayoutOrScroll(String string2) {
        if (this.isComputingLayout()) {
            if (string2 == null) {
                throw new IllegalStateException("Cannot call this method while RecyclerView is computing a layout or scrolling");
            }
            throw new IllegalStateException(string2);
        }
    }

    protected boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams && this.mLayout.checkLayoutParams((LayoutParams)layoutParams);
    }

    void clearOldPositions() {
        int n2 = this.mChildHelper.getUnfilteredChildCount();
        for (int i2 = 0; i2 < n2; ++i2) {
            ViewHolder viewHolder = RecyclerView.getChildViewHolderInt(this.mChildHelper.getUnfilteredChildAt(i2));
            if (viewHolder.shouldIgnore()) continue;
            viewHolder.clearOldPosition();
        }
        this.mRecycler.clearOldPositions();
    }

    public void clearOnChildAttachStateChangeListeners() {
        if (this.mOnChildAttachStateListeners != null) {
            this.mOnChildAttachStateListeners.clear();
        }
    }

    public void clearOnScrollListeners() {
        if (this.mScrollListeners != null) {
            this.mScrollListeners.clear();
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public int computeHorizontalScrollExtent() {
        if (this.mLayout == null || !this.mLayout.canScrollHorizontally()) {
            return 0;
        }
        return this.mLayout.computeHorizontalScrollExtent(this.mState);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public int computeHorizontalScrollOffset() {
        if (this.mLayout == null || !this.mLayout.canScrollHorizontally()) {
            return 0;
        }
        return this.mLayout.computeHorizontalScrollOffset(this.mState);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public int computeHorizontalScrollRange() {
        if (this.mLayout == null || !this.mLayout.canScrollHorizontally()) {
            return 0;
        }
        return this.mLayout.computeHorizontalScrollRange(this.mState);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public int computeVerticalScrollExtent() {
        if (this.mLayout == null || !this.mLayout.canScrollVertically()) {
            return 0;
        }
        return this.mLayout.computeVerticalScrollExtent(this.mState);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public int computeVerticalScrollOffset() {
        if (this.mLayout == null || !this.mLayout.canScrollVertically()) {
            return 0;
        }
        return this.mLayout.computeVerticalScrollOffset(this.mState);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public int computeVerticalScrollRange() {
        if (this.mLayout == null || !this.mLayout.canScrollVertically()) {
            return 0;
        }
        return this.mLayout.computeVerticalScrollRange(this.mState);
    }

    void defaultOnMeasure(int n2, int n3) {
        this.setMeasuredDimension(LayoutManager.chooseSize(n2, this.getPaddingLeft() + this.getPaddingRight(), ViewCompat.getMinimumWidth((View)this)), LayoutManager.chooseSize(n3, this.getPaddingTop() + this.getPaddingBottom(), ViewCompat.getMinimumHeight((View)this)));
    }

    /*
     * Enabled aggressive block sorting
     */
    void dispatchLayout() {
        if (this.mAdapter == null) {
            Log.e((String)TAG, (String)"No adapter attached; skipping layout");
            return;
        }
        if (this.mLayout == null) {
            Log.e((String)TAG, (String)"No layout manager attached; skipping layout");
            return;
        }
        State.access$2202(this.mState, false);
        if (this.mState.mLayoutStep == 1) {
            this.dispatchLayoutStep1();
            this.mLayout.setExactMeasureSpecsFrom(this);
            this.dispatchLayoutStep2();
        } else if (this.mAdapterHelper.hasUpdates() || this.mLayout.getWidth() != this.getWidth() || this.mLayout.getHeight() != this.getHeight()) {
            this.mLayout.setExactMeasureSpecsFrom(this);
            this.dispatchLayoutStep2();
        } else {
            this.mLayout.setExactMeasureSpecsFrom(this);
        }
        this.dispatchLayoutStep3();
    }

    @Override
    public boolean dispatchNestedFling(float f2, float f3, boolean bl2) {
        return this.getScrollingChildHelper().dispatchNestedFling(f2, f3, bl2);
    }

    @Override
    public boolean dispatchNestedPreFling(float f2, float f3) {
        return this.getScrollingChildHelper().dispatchNestedPreFling(f2, f3);
    }

    @Override
    public boolean dispatchNestedPreScroll(int n2, int n3, int[] nArray, int[] nArray2) {
        return this.getScrollingChildHelper().dispatchNestedPreScroll(n2, n3, nArray, nArray2);
    }

    @Override
    public boolean dispatchNestedScroll(int n2, int n3, int n4, int n5, int[] nArray) {
        return this.getScrollingChildHelper().dispatchNestedScroll(n2, n3, n4, n5, nArray);
    }

    void dispatchOnScrollStateChanged(int n2) {
        if (this.mLayout != null) {
            this.mLayout.onScrollStateChanged(n2);
        }
        this.onScrollStateChanged(n2);
        if (this.mScrollListener != null) {
            this.mScrollListener.onScrollStateChanged(this, n2);
        }
        if (this.mScrollListeners != null) {
            for (int i2 = this.mScrollListeners.size() - 1; i2 >= 0; --i2) {
                this.mScrollListeners.get(i2).onScrollStateChanged(this, n2);
            }
        }
    }

    void dispatchOnScrolled(int n2, int n3) {
        int n4 = this.getScrollX();
        int n5 = this.getScrollY();
        this.onScrollChanged(n4, n5, n4, n5);
        this.onScrolled(n2, n3);
        if (this.mScrollListener != null) {
            this.mScrollListener.onScrolled(this, n2, n3);
        }
        if (this.mScrollListeners != null) {
            for (n4 = this.mScrollListeners.size() - 1; n4 >= 0; --n4) {
                this.mScrollListeners.get(n4).onScrolled(this, n2, n3);
            }
        }
    }

    protected void dispatchRestoreInstanceState(SparseArray<Parcelable> sparseArray) {
        this.dispatchThawSelfOnly(sparseArray);
    }

    protected void dispatchSaveInstanceState(SparseArray<Parcelable> sparseArray) {
        this.dispatchFreezeSelfOnly(sparseArray);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void draw(Canvas canvas) {
        int n2;
        int n3;
        int n4 = 1;
        super.draw(canvas);
        int n5 = this.mItemDecorations.size();
        for (n3 = 0; n3 < n5; ++n3) {
            this.mItemDecorations.get(n3).onDrawOver(canvas, this, this.mState);
        }
        n5 = n3 = 0;
        if (this.mLeftGlow != null) {
            n5 = n3;
            if (!this.mLeftGlow.isFinished()) {
                n2 = canvas.save();
                n3 = this.mClipToPadding ? this.getPaddingBottom() : 0;
                canvas.rotate(270.0f);
                canvas.translate((float)(-this.getHeight() + n3), 0.0f);
                n5 = this.mLeftGlow != null && this.mLeftGlow.draw(canvas) ? 1 : 0;
                canvas.restoreToCount(n2);
            }
        }
        n3 = n5;
        if (this.mTopGlow != null) {
            n3 = n5;
            if (!this.mTopGlow.isFinished()) {
                n2 = canvas.save();
                if (this.mClipToPadding) {
                    canvas.translate((float)this.getPaddingLeft(), (float)this.getPaddingTop());
                }
                n3 = this.mTopGlow != null && this.mTopGlow.draw(canvas) ? 1 : 0;
                n3 = n5 | n3;
                canvas.restoreToCount(n2);
            }
        }
        n5 = n3;
        if (this.mRightGlow != null) {
            n5 = n3;
            if (!this.mRightGlow.isFinished()) {
                n2 = canvas.save();
                int n6 = this.getWidth();
                n5 = this.mClipToPadding ? this.getPaddingTop() : 0;
                canvas.rotate(90.0f);
                canvas.translate((float)(-n5), (float)(-n6));
                n5 = this.mRightGlow != null && this.mRightGlow.draw(canvas) ? 1 : 0;
                n5 = n3 | n5;
                canvas.restoreToCount(n2);
            }
        }
        n3 = n5;
        if (this.mBottomGlow != null) {
            n3 = n5;
            if (!this.mBottomGlow.isFinished()) {
                n2 = canvas.save();
                canvas.rotate(180.0f);
                if (this.mClipToPadding) {
                    canvas.translate((float)(-this.getWidth() + this.getPaddingRight()), (float)(-this.getHeight() + this.getPaddingBottom()));
                } else {
                    canvas.translate((float)(-this.getWidth()), (float)(-this.getHeight()));
                }
                n3 = this.mBottomGlow != null && this.mBottomGlow.draw(canvas) ? n4 : 0;
                n3 = n5 | n3;
                canvas.restoreToCount(n2);
            }
        }
        n5 = n3;
        if (n3 == 0) {
            n5 = n3;
            if (this.mItemAnimator != null) {
                n5 = n3;
                if (this.mItemDecorations.size() > 0) {
                    n5 = n3;
                    if (this.mItemAnimator.isRunning()) {
                        n5 = 1;
                    }
                }
            }
        }
        if (n5 != 0) {
            ViewCompat.postInvalidateOnAnimation((View)this);
        }
    }

    public boolean drawChild(Canvas canvas, View view, long l2) {
        return super.drawChild(canvas, view, l2);
    }

    void eatRequestLayout() {
        ++this.mEatRequestLayout;
        if (this.mEatRequestLayout == 1 && !this.mLayoutFrozen) {
            this.mLayoutRequestEaten = false;
        }
    }

    void ensureBottomGlow() {
        if (this.mBottomGlow != null) {
            return;
        }
        this.mBottomGlow = new EdgeEffectCompat(this.getContext());
        if (this.mClipToPadding) {
            this.mBottomGlow.setSize(this.getMeasuredWidth() - this.getPaddingLeft() - this.getPaddingRight(), this.getMeasuredHeight() - this.getPaddingTop() - this.getPaddingBottom());
            return;
        }
        this.mBottomGlow.setSize(this.getMeasuredWidth(), this.getMeasuredHeight());
    }

    void ensureLeftGlow() {
        if (this.mLeftGlow != null) {
            return;
        }
        this.mLeftGlow = new EdgeEffectCompat(this.getContext());
        if (this.mClipToPadding) {
            this.mLeftGlow.setSize(this.getMeasuredHeight() - this.getPaddingTop() - this.getPaddingBottom(), this.getMeasuredWidth() - this.getPaddingLeft() - this.getPaddingRight());
            return;
        }
        this.mLeftGlow.setSize(this.getMeasuredHeight(), this.getMeasuredWidth());
    }

    void ensureRightGlow() {
        if (this.mRightGlow != null) {
            return;
        }
        this.mRightGlow = new EdgeEffectCompat(this.getContext());
        if (this.mClipToPadding) {
            this.mRightGlow.setSize(this.getMeasuredHeight() - this.getPaddingTop() - this.getPaddingBottom(), this.getMeasuredWidth() - this.getPaddingLeft() - this.getPaddingRight());
            return;
        }
        this.mRightGlow.setSize(this.getMeasuredHeight(), this.getMeasuredWidth());
    }

    void ensureTopGlow() {
        if (this.mTopGlow != null) {
            return;
        }
        this.mTopGlow = new EdgeEffectCompat(this.getContext());
        if (this.mClipToPadding) {
            this.mTopGlow.setSize(this.getMeasuredWidth() - this.getPaddingLeft() - this.getPaddingRight(), this.getMeasuredHeight() - this.getPaddingTop() - this.getPaddingBottom());
            return;
        }
        this.mTopGlow.setSize(this.getMeasuredWidth(), this.getMeasuredHeight());
    }

    public View findChildViewUnder(float f2, float f3) {
        for (int i2 = this.mChildHelper.getChildCount() - 1; i2 >= 0; --i2) {
            View view = this.mChildHelper.getChildAt(i2);
            float f4 = ViewCompat.getTranslationX(view);
            float f5 = ViewCompat.getTranslationY(view);
            if (!(f2 >= (float)view.getLeft() + f4) || !(f2 <= (float)view.getRight() + f4) || !(f3 >= (float)view.getTop() + f5) || !(f3 <= (float)view.getBottom() + f5)) continue;
            return view;
        }
        return null;
    }

    @Nullable
    public View findContainingItemView(View view) {
        ViewParent viewParent = view.getParent();
        View view2 = view;
        view = viewParent;
        while (view != null && view != this && view instanceof View) {
            view2 = view;
            view = view2.getParent();
        }
        if (view == this) {
            return view2;
        }
        return null;
    }

    @Nullable
    public ViewHolder findContainingViewHolder(View view) {
        if ((view = this.findContainingItemView(view)) == null) {
            return null;
        }
        return this.getChildViewHolder(view);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public ViewHolder findViewHolderForAdapterPosition(int n2) {
        if (this.mDataSetHasChangedAfterLayout) {
            return null;
        }
        int n3 = this.mChildHelper.getUnfilteredChildCount();
        int n4 = 0;
        while (n4 < n3) {
            ViewHolder viewHolder = RecyclerView.getChildViewHolderInt(this.mChildHelper.getUnfilteredChildAt(n4));
            if (viewHolder != null && !viewHolder.isRemoved()) {
                ViewHolder viewHolder2 = viewHolder;
                if (this.getAdapterPositionFor(viewHolder) == n2) return viewHolder2;
            }
            ++n4;
        }
        return null;
    }

    public ViewHolder findViewHolderForItemId(long l2) {
        int n2 = this.mChildHelper.getUnfilteredChildCount();
        for (int i2 = 0; i2 < n2; ++i2) {
            ViewHolder viewHolder = RecyclerView.getChildViewHolderInt(this.mChildHelper.getUnfilteredChildAt(i2));
            if (viewHolder == null || viewHolder.getItemId() != l2) continue;
            return viewHolder;
        }
        return null;
    }

    public ViewHolder findViewHolderForLayoutPosition(int n2) {
        return this.findViewHolderForPosition(n2, false);
    }

    @Deprecated
    public ViewHolder findViewHolderForPosition(int n2) {
        return this.findViewHolderForPosition(n2, false);
    }

    /*
     * Enabled aggressive block sorting
     */
    ViewHolder findViewHolderForPosition(int n2, boolean bl2) {
        int n3 = this.mChildHelper.getUnfilteredChildCount();
        int n4 = 0;
        while (n4 < n3) {
            ViewHolder viewHolder = RecyclerView.getChildViewHolderInt(this.mChildHelper.getUnfilteredChildAt(n4));
            if (viewHolder != null && !viewHolder.isRemoved() && (bl2 ? viewHolder.mPosition == n2 : viewHolder.getLayoutPosition() == n2)) {
                return viewHolder;
            }
            ++n4;
        }
        return null;
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean fling(int n2, int n3) {
        int n4;
        boolean bl2;
        boolean bl3;
        block14: {
            block13: {
                block12: {
                    block11: {
                        if (this.mLayout == null) {
                            Log.e((String)TAG, (String)"Cannot fling without a LayoutManager set. Call setLayoutManager with a non-null argument.");
                            return false;
                        }
                        if (this.mLayoutFrozen) return false;
                        bl3 = this.mLayout.canScrollHorizontally();
                        bl2 = this.mLayout.canScrollVertically();
                        if (!bl3) break block11;
                        n4 = n2;
                        if (Math.abs(n2) >= this.mMinFlingVelocity) break block12;
                    }
                    n4 = 0;
                }
                if (!bl2) break block13;
                n2 = n3;
                if (Math.abs(n3) >= this.mMinFlingVelocity) break block14;
            }
            n2 = 0;
        }
        if (n4 == 0 && n2 == 0 || this.dispatchNestedPreFling(n4, n2)) return false;
        bl3 = bl3 || bl2;
        this.dispatchNestedFling(n4, n2, bl3);
        if (!bl3) return false;
        n3 = Math.max(-this.mMaxFlingVelocity, Math.min(n4, this.mMaxFlingVelocity));
        n2 = Math.max(-this.mMaxFlingVelocity, Math.min(n2, this.mMaxFlingVelocity));
        this.mViewFlinger.fling(n3, n2);
        return true;
    }

    public View focusSearch(View view, int n2) {
        View view2;
        View view3 = this.mLayout.onInterceptFocusSearch(view, n2);
        if (view3 != null) {
            return view3;
        }
        view3 = view2 = FocusFinder.getInstance().findNextFocus((ViewGroup)this, view, n2);
        if (view2 == null) {
            view3 = view2;
            if (this.mAdapter != null) {
                view3 = view2;
                if (this.mLayout != null) {
                    view3 = view2;
                    if (!this.isComputingLayout()) {
                        view3 = view2;
                        if (!this.mLayoutFrozen) {
                            this.eatRequestLayout();
                            view3 = this.mLayout.onFocusSearchFailed(view, n2, this.mRecycler, this.mState);
                            this.resumeRequestLayout(false);
                        }
                    }
                }
            }
        }
        if (view3 != null) {
            return view3;
        }
        return super.focusSearch(view, n2);
    }

    protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
        if (this.mLayout == null) {
            throw new IllegalStateException("RecyclerView has no LayoutManager");
        }
        return this.mLayout.generateDefaultLayoutParams();
    }

    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        if (this.mLayout == null) {
            throw new IllegalStateException("RecyclerView has no LayoutManager");
        }
        return this.mLayout.generateLayoutParams(this.getContext(), attributeSet);
    }

    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        if (this.mLayout == null) {
            throw new IllegalStateException("RecyclerView has no LayoutManager");
        }
        return this.mLayout.generateLayoutParams(layoutParams);
    }

    public Adapter getAdapter() {
        return this.mAdapter;
    }

    public int getBaseline() {
        if (this.mLayout != null) {
            return this.mLayout.getBaseline();
        }
        return super.getBaseline();
    }

    long getChangedHolderKey(ViewHolder viewHolder) {
        if (this.mAdapter.hasStableIds()) {
            return viewHolder.getItemId();
        }
        return viewHolder.mPosition;
    }

    public int getChildAdapterPosition(View object) {
        if ((object = RecyclerView.getChildViewHolderInt((View)object)) != null) {
            return ((ViewHolder)object).getAdapterPosition();
        }
        return -1;
    }

    protected int getChildDrawingOrder(int n2, int n3) {
        if (this.mChildDrawingOrderCallback == null) {
            return super.getChildDrawingOrder(n2, n3);
        }
        return this.mChildDrawingOrderCallback.onGetChildDrawingOrder(n2, n3);
    }

    /*
     * Enabled aggressive block sorting
     */
    public long getChildItemId(View object) {
        if (this.mAdapter == null || !this.mAdapter.hasStableIds() || (object = RecyclerView.getChildViewHolderInt((View)object)) == null) {
            return -1L;
        }
        return ((ViewHolder)object).getItemId();
    }

    public int getChildLayoutPosition(View object) {
        if ((object = RecyclerView.getChildViewHolderInt((View)object)) != null) {
            return ((ViewHolder)object).getLayoutPosition();
        }
        return -1;
    }

    @Deprecated
    public int getChildPosition(View view) {
        return this.getChildAdapterPosition(view);
    }

    public ViewHolder getChildViewHolder(View view) {
        ViewParent viewParent = view.getParent();
        if (viewParent != null && viewParent != this) {
            throw new IllegalArgumentException("View " + view + " is not a direct child of " + this);
        }
        return RecyclerView.getChildViewHolderInt(view);
    }

    public RecyclerViewAccessibilityDelegate getCompatAccessibilityDelegate() {
        return this.mAccessibilityDelegate;
    }

    public ItemAnimator getItemAnimator() {
        return this.mItemAnimator;
    }

    Rect getItemDecorInsetsForChild(View view) {
        LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
        if (!layoutParams.mInsetsDirty) {
            return layoutParams.mDecorInsets;
        }
        Rect rect = layoutParams.mDecorInsets;
        rect.set(0, 0, 0, 0);
        int n2 = this.mItemDecorations.size();
        for (int i2 = 0; i2 < n2; ++i2) {
            this.mTempRect.set(0, 0, 0, 0);
            this.mItemDecorations.get(i2).getItemOffsets(this.mTempRect, view, this, this.mState);
            rect.left += this.mTempRect.left;
            rect.top += this.mTempRect.top;
            rect.right += this.mTempRect.right;
            rect.bottom += this.mTempRect.bottom;
        }
        layoutParams.mInsetsDirty = false;
        return rect;
    }

    public LayoutManager getLayoutManager() {
        return this.mLayout;
    }

    public int getMaxFlingVelocity() {
        return this.mMaxFlingVelocity;
    }

    public int getMinFlingVelocity() {
        return this.mMinFlingVelocity;
    }

    public RecycledViewPool getRecycledViewPool() {
        return this.mRecycler.getRecycledViewPool();
    }

    public int getScrollState() {
        return this.mScrollState;
    }

    public boolean hasFixedSize() {
        return this.mHasFixedSize;
    }

    @Override
    public boolean hasNestedScrollingParent() {
        return this.getScrollingChildHelper().hasNestedScrollingParent();
    }

    public boolean hasPendingAdapterUpdates() {
        return !this.mFirstLayoutComplete || this.mDataSetHasChangedAfterLayout || this.mAdapterHelper.hasPendingUpdates();
    }

    void initAdapterManager() {
        this.mAdapterHelper = new AdapterHelper(new AdapterHelper.Callback(){

            void dispatchUpdate(AdapterHelper.UpdateOp updateOp) {
                switch (updateOp.cmd) {
                    default: {
                        return;
                    }
                    case 1: {
                        RecyclerView.this.mLayout.onItemsAdded(RecyclerView.this, updateOp.positionStart, updateOp.itemCount);
                        return;
                    }
                    case 2: {
                        RecyclerView.this.mLayout.onItemsRemoved(RecyclerView.this, updateOp.positionStart, updateOp.itemCount);
                        return;
                    }
                    case 4: {
                        RecyclerView.this.mLayout.onItemsUpdated(RecyclerView.this, updateOp.positionStart, updateOp.itemCount, updateOp.payload);
                        return;
                    }
                    case 8: 
                }
                RecyclerView.this.mLayout.onItemsMoved(RecyclerView.this, updateOp.positionStart, updateOp.itemCount, 1);
            }

            /*
             * Enabled force condition propagation
             * Lifted jumps to return sites
             */
            @Override
            public ViewHolder findViewHolder(int n2) {
                ViewHolder viewHolder = RecyclerView.this.findViewHolderForPosition(n2, true);
                if (viewHolder == null) {
                    return null;
                }
                ViewHolder viewHolder2 = viewHolder;
                if (!RecyclerView.this.mChildHelper.isHidden(viewHolder.itemView)) return viewHolder2;
                return null;
            }

            @Override
            public void markViewHoldersUpdated(int n2, int n3, Object object) {
                RecyclerView.this.viewRangeUpdate(n2, n3, object);
                RecyclerView.this.mItemsChanged = true;
            }

            @Override
            public void offsetPositionsForAdd(int n2, int n3) {
                RecyclerView.this.offsetPositionRecordsForInsert(n2, n3);
                RecyclerView.this.mItemsAddedOrRemoved = true;
            }

            @Override
            public void offsetPositionsForMove(int n2, int n3) {
                RecyclerView.this.offsetPositionRecordsForMove(n2, n3);
                RecyclerView.this.mItemsAddedOrRemoved = true;
            }

            @Override
            public void offsetPositionsForRemovingInvisible(int n2, int n3) {
                RecyclerView.this.offsetPositionRecordsForRemove(n2, n3, true);
                RecyclerView.this.mItemsAddedOrRemoved = true;
                State.access$1712(RecyclerView.this.mState, n3);
            }

            @Override
            public void offsetPositionsForRemovingLaidOutOrNewView(int n2, int n3) {
                RecyclerView.this.offsetPositionRecordsForRemove(n2, n3, false);
                RecyclerView.this.mItemsAddedOrRemoved = true;
            }

            @Override
            public void onDispatchFirstPass(AdapterHelper.UpdateOp updateOp) {
                this.dispatchUpdate(updateOp);
            }

            @Override
            public void onDispatchSecondPass(AdapterHelper.UpdateOp updateOp) {
                this.dispatchUpdate(updateOp);
            }
        });
    }

    void invalidateGlows() {
        this.mBottomGlow = null;
        this.mTopGlow = null;
        this.mRightGlow = null;
        this.mLeftGlow = null;
    }

    public void invalidateItemDecorations() {
        if (this.mItemDecorations.size() == 0) {
            return;
        }
        if (this.mLayout != null) {
            this.mLayout.assertNotInLayoutOrScroll("Cannot invalidate item decorations during a scroll or layout");
        }
        this.markItemDecorInsetsDirty();
        this.requestLayout();
    }

    boolean isAccessibilityEnabled() {
        return this.mAccessibilityManager != null && this.mAccessibilityManager.isEnabled();
    }

    public boolean isAnimating() {
        return this.mItemAnimator != null && this.mItemAnimator.isRunning();
    }

    public boolean isAttachedToWindow() {
        return this.mIsAttached;
    }

    public boolean isComputingLayout() {
        return this.mLayoutOrScrollCounter > 0;
    }

    public boolean isLayoutFrozen() {
        return this.mLayoutFrozen;
    }

    @Override
    public boolean isNestedScrollingEnabled() {
        return this.getScrollingChildHelper().isNestedScrollingEnabled();
    }

    void markItemDecorInsetsDirty() {
        int n2 = this.mChildHelper.getUnfilteredChildCount();
        for (int i2 = 0; i2 < n2; ++i2) {
            ((LayoutParams)this.mChildHelper.getUnfilteredChildAt((int)i2).getLayoutParams()).mInsetsDirty = true;
        }
        this.mRecycler.markItemDecorInsetsDirty();
    }

    void markKnownViewsInvalid() {
        int n2 = this.mChildHelper.getUnfilteredChildCount();
        for (int i2 = 0; i2 < n2; ++i2) {
            ViewHolder viewHolder = RecyclerView.getChildViewHolderInt(this.mChildHelper.getUnfilteredChildAt(i2));
            if (viewHolder == null || viewHolder.shouldIgnore()) continue;
            viewHolder.addFlags(6);
        }
        this.markItemDecorInsetsDirty();
        this.mRecycler.markKnownViewsInvalid();
    }

    public void offsetChildrenHorizontal(int n2) {
        int n3 = this.mChildHelper.getChildCount();
        for (int i2 = 0; i2 < n3; ++i2) {
            this.mChildHelper.getChildAt(i2).offsetLeftAndRight(n2);
        }
    }

    public void offsetChildrenVertical(int n2) {
        int n3 = this.mChildHelper.getChildCount();
        for (int i2 = 0; i2 < n3; ++i2) {
            this.mChildHelper.getChildAt(i2).offsetTopAndBottom(n2);
        }
    }

    void offsetPositionRecordsForInsert(int n2, int n3) {
        int n4 = this.mChildHelper.getUnfilteredChildCount();
        for (int i2 = 0; i2 < n4; ++i2) {
            ViewHolder viewHolder = RecyclerView.getChildViewHolderInt(this.mChildHelper.getUnfilteredChildAt(i2));
            if (viewHolder == null || viewHolder.shouldIgnore() || viewHolder.mPosition < n2) continue;
            viewHolder.offsetPosition(n3, false);
            State.access$1802(this.mState, true);
        }
        this.mRecycler.offsetPositionRecordsForInsert(n2, n3);
        this.requestLayout();
    }

    /*
     * Enabled aggressive block sorting
     */
    void offsetPositionRecordsForMove(int n2, int n3) {
        int n4;
        int n5;
        int n6;
        int n7 = this.mChildHelper.getUnfilteredChildCount();
        if (n2 < n3) {
            n6 = n2;
            n5 = n3;
            n4 = -1;
        } else {
            n6 = n3;
            n5 = n2;
            n4 = 1;
        }
        int n8 = 0;
        while (true) {
            if (n8 >= n7) {
                this.mRecycler.offsetPositionRecordsForMove(n2, n3);
                this.requestLayout();
                return;
            }
            ViewHolder viewHolder = RecyclerView.getChildViewHolderInt(this.mChildHelper.getUnfilteredChildAt(n8));
            if (viewHolder != null && viewHolder.mPosition >= n6 && viewHolder.mPosition <= n5) {
                if (viewHolder.mPosition == n2) {
                    viewHolder.offsetPosition(n3 - n2, false);
                } else {
                    viewHolder.offsetPosition(n4, false);
                }
                State.access$1802(this.mState, true);
            }
            ++n8;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    void offsetPositionRecordsForRemove(int n2, int n3, boolean bl2) {
        int n4 = this.mChildHelper.getUnfilteredChildCount();
        int n5 = 0;
        while (true) {
            if (n5 >= n4) {
                this.mRecycler.offsetPositionRecordsForRemove(n2, n3, bl2);
                this.requestLayout();
                return;
            }
            ViewHolder viewHolder = RecyclerView.getChildViewHolderInt(this.mChildHelper.getUnfilteredChildAt(n5));
            if (viewHolder != null && !viewHolder.shouldIgnore()) {
                if (viewHolder.mPosition >= n2 + n3) {
                    viewHolder.offsetPosition(-n3, bl2);
                    State.access$1802(this.mState, true);
                } else if (viewHolder.mPosition >= n2) {
                    viewHolder.flagRemovedAndOffsetPosition(n2 - 1, -n3, bl2);
                    State.access$1802(this.mState, true);
                }
            }
            ++n5;
        }
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mLayoutOrScrollCounter = 0;
        this.mIsAttached = true;
        this.mFirstLayoutComplete = false;
        if (this.mLayout != null) {
            this.mLayout.dispatchAttachedToWindow(this);
        }
        this.mPostedAnimatorRunner = false;
    }

    public void onChildAttachedToWindow(View view) {
    }

    public void onChildDetachedFromWindow(View view) {
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.mItemAnimator != null) {
            this.mItemAnimator.endAnimations();
        }
        this.mFirstLayoutComplete = false;
        this.stopScroll();
        this.mIsAttached = false;
        if (this.mLayout != null) {
            this.mLayout.dispatchDetachedFromWindow(this, this.mRecycler);
        }
        this.removeCallbacks(this.mItemAnimatorRunner);
        this.mViewInfoStore.onDetach();
    }

    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int n2 = this.mItemDecorations.size();
        for (int i2 = 0; i2 < n2; ++i2) {
            this.mItemDecorations.get(i2).onDraw(canvas, this, this.mState);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean onGenericMotionEvent(MotionEvent motionEvent) {
        float f2;
        float f3;
        block3: {
            block2: {
                if (this.mLayout == null || this.mLayoutFrozen || (MotionEventCompat.getSource(motionEvent) & 2) == 0 || motionEvent.getAction() != 8) break block2;
                f3 = this.mLayout.canScrollVertically() ? -MotionEventCompat.getAxisValue(motionEvent, 9) : 0.0f;
                f2 = this.mLayout.canScrollHorizontally() ? MotionEventCompat.getAxisValue(motionEvent, 10) : 0.0f;
                if (f3 != 0.0f || f2 != 0.0f) break block3;
            }
            return false;
        }
        float f4 = this.getScrollFactor();
        this.scrollByInternal((int)(f2 * f4), (int)(f3 * f4), motionEvent);
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean onInterceptTouchEvent(MotionEvent object) {
        if (this.mLayoutFrozen) {
            return false;
        }
        if (this.dispatchOnItemTouchIntercept((MotionEvent)object)) {
            this.cancelTouch();
            return true;
        }
        if (this.mLayout == null) {
            return false;
        }
        boolean bl2 = this.mLayout.canScrollHorizontally();
        boolean bl3 = this.mLayout.canScrollVertically();
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
        this.mVelocityTracker.addMovement(object);
        int n2 = MotionEventCompat.getActionMasked(object);
        int n3 = MotionEventCompat.getActionIndex(object);
        switch (n2) {
            case 0: {
                if (this.mIgnoreMotionEventTillDown) {
                    this.mIgnoreMotionEventTillDown = false;
                }
                this.mScrollPointerId = MotionEventCompat.getPointerId(object, 0);
                this.mLastTouchX = n3 = (int)(object.getX() + 0.5f);
                this.mInitialTouchX = n3;
                this.mLastTouchY = n3 = (int)(object.getY() + 0.5f);
                this.mInitialTouchY = n3;
                if (this.mScrollState == 2) {
                    this.getParent().requestDisallowInterceptTouchEvent(true);
                    this.setScrollState(1);
                }
                int[] nArray = this.mNestedOffsets;
                this.mNestedOffsets[1] = 0;
                nArray[0] = 0;
                n3 = 0;
                if (bl2) {
                    n3 = 0 | 1;
                }
                n2 = n3;
                if (bl3) {
                    n2 = n3 | 2;
                }
                this.startNestedScroll(n2);
                break;
            }
            case 5: {
                this.mScrollPointerId = MotionEventCompat.getPointerId(object, n3);
                this.mLastTouchX = n2 = (int)(MotionEventCompat.getX(object, n3) + 0.5f);
                this.mInitialTouchX = n2;
                this.mLastTouchY = n3 = (int)(MotionEventCompat.getY(object, n3) + 0.5f);
                this.mInitialTouchY = n3;
                break;
            }
            case 2: {
                n2 = MotionEventCompat.findPointerIndex(object, this.mScrollPointerId);
                if (n2 < 0) {
                    Log.e((String)TAG, (String)("Error processing scroll; pointer index for id " + this.mScrollPointerId + " not found. Did any MotionEvents get skipped?"));
                    return false;
                }
                n3 = (int)(MotionEventCompat.getX(object, n2) + 0.5f);
                n2 = (int)(MotionEventCompat.getY(object, n2) + 0.5f);
                if (this.mScrollState == 1) break;
                int n4 = n3 - this.mInitialTouchX;
                int n5 = n2 - this.mInitialTouchY;
                n3 = n2 = 0;
                if (bl2) {
                    n3 = n2;
                    if (Math.abs(n4) > this.mTouchSlop) {
                        n2 = this.mInitialTouchX;
                        int n6 = this.mTouchSlop;
                        n3 = n4 < 0 ? -1 : 1;
                        this.mLastTouchX = n3 * n6 + n2;
                        n3 = 1;
                    }
                }
                n2 = n3;
                if (bl3) {
                    n2 = n3;
                    if (Math.abs(n5) > this.mTouchSlop) {
                        n2 = this.mInitialTouchY;
                        n4 = this.mTouchSlop;
                        n3 = n5 < 0 ? -1 : 1;
                        this.mLastTouchY = n3 * n4 + n2;
                        n2 = 1;
                    }
                }
                if (n2 == 0) break;
                this.setScrollState(1);
                break;
            }
            case 6: {
                this.onPointerUp((MotionEvent)object);
                break;
            }
            case 1: {
                this.mVelocityTracker.clear();
                this.stopNestedScroll();
                break;
            }
            case 3: {
                this.cancelTouch();
            }
        }
        return this.mScrollState == 1;
    }

    protected void onLayout(boolean bl2, int n2, int n3, int n4, int n5) {
        TraceCompat.beginSection(TRACE_ON_LAYOUT_TAG);
        this.dispatchLayout();
        TraceCompat.endSection();
        this.mFirstLayoutComplete = true;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void onMeasure(int n2, int n3) {
        boolean bl2 = false;
        if (this.mLayout == null) {
            this.defaultOnMeasure(n2, n3);
            return;
        }
        if (this.mLayout.mAutoMeasure) {
            int n4 = View.MeasureSpec.getMode((int)n2);
            int n5 = View.MeasureSpec.getMode((int)n3);
            boolean bl3 = bl2;
            if (n4 == 0x40000000) {
                bl3 = bl2;
                if (n5 == 0x40000000) {
                    bl3 = true;
                }
            }
            this.mLayout.onMeasure(this.mRecycler, this.mState, n2, n3);
            if (bl3 || this.mAdapter == null) return;
            if (this.mState.mLayoutStep == 1) {
                this.dispatchLayoutStep1();
            }
            this.mLayout.setMeasureSpecs(n2, n3);
            State.access$2202(this.mState, true);
            this.dispatchLayoutStep2();
            this.mLayout.setMeasuredDimensionFromChildren(n2, n3);
            if (!this.mLayout.shouldMeasureTwice()) return;
            this.mLayout.setMeasureSpecs(View.MeasureSpec.makeMeasureSpec((int)this.getMeasuredWidth(), (int)0x40000000), View.MeasureSpec.makeMeasureSpec((int)this.getMeasuredHeight(), (int)0x40000000));
            State.access$2202(this.mState, true);
            this.dispatchLayoutStep2();
            this.mLayout.setMeasuredDimensionFromChildren(n2, n3);
            return;
        }
        if (this.mHasFixedSize) {
            this.mLayout.onMeasure(this.mRecycler, this.mState, n2, n3);
            return;
        }
        if (this.mAdapterUpdateDuringMeasure) {
            this.eatRequestLayout();
            this.processAdapterUpdatesAndSetAnimationFlags();
            if (this.mState.mRunPredictiveAnimations) {
                State.access$2402(this.mState, true);
            } else {
                this.mAdapterHelper.consumeUpdatesInOnePass();
                State.access$2402(this.mState, false);
            }
            this.mAdapterUpdateDuringMeasure = false;
            this.resumeRequestLayout(false);
        }
        this.mState.mItemCount = this.mAdapter != null ? this.mAdapter.getItemCount() : 0;
        this.eatRequestLayout();
        this.mLayout.onMeasure(this.mRecycler, this.mState, n2, n3);
        this.resumeRequestLayout(false);
        State.access$2402(this.mState, false);
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        } else {
            this.mPendingSavedState = (SavedState)parcelable;
            super.onRestoreInstanceState(this.mPendingSavedState.getSuperState());
            if (this.mLayout == null || this.mPendingSavedState.mLayoutState == null) return;
            this.mLayout.onRestoreInstanceState(this.mPendingSavedState.mLayoutState);
            return;
        }
    }

    protected Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        if (this.mPendingSavedState != null) {
            savedState.copyFrom(this.mPendingSavedState);
            return savedState;
        }
        if (this.mLayout != null) {
            savedState.mLayoutState = this.mLayout.onSaveInstanceState();
            return savedState;
        }
        savedState.mLayoutState = null;
        return savedState;
    }

    public void onScrollStateChanged(int n2) {
    }

    public void onScrolled(int n2, int n3) {
    }

    protected void onSizeChanged(int n2, int n3, int n4, int n5) {
        super.onSizeChanged(n2, n3, n4, n5);
        if (n2 != n4 || n3 != n5) {
            this.invalidateGlows();
        }
    }

    /*
     * Handled duff style switch with additional control
     * Enabled aggressive block sorting
     */
    public boolean onTouchEvent(MotionEvent object) {
        if (this.mLayoutFrozen || this.mIgnoreMotionEventTillDown) {
            return false;
        }
        if (this.dispatchOnItemTouch((MotionEvent)object)) {
            this.cancelTouch();
            return true;
        }
        if (this.mLayout == null) {
            return false;
        }
        boolean bl2 = this.mLayout.canScrollHorizontally();
        boolean bl3 = this.mLayout.canScrollVertically();
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
        int n2 = 0;
        MotionEvent motionEvent = MotionEvent.obtain((MotionEvent)object);
        int n3 = MotionEventCompat.getActionMasked(object);
        int n4 = MotionEventCompat.getActionIndex(object);
        if (n3 == 0) {
            int[] nArray = this.mNestedOffsets;
            this.mNestedOffsets[1] = 0;
            nArray[0] = 0;
        }
        motionEvent.offsetLocation((float)this.mNestedOffsets[0], (float)this.mNestedOffsets[1]);
        int n5 = n2;
        int n6 = Integer.MIN_VALUE;
        block9: do {
            switch (n6 == Integer.MIN_VALUE ? n3 : n6) {
                default: {
                    n5 = n2;
                    break;
                }
                case 0: {
                    this.mScrollPointerId = MotionEventCompat.getPointerId(object, 0);
                    this.mLastTouchX = n5 = (int)(object.getX() + 0.5f);
                    this.mInitialTouchX = n5;
                    this.mLastTouchY = n5 = (int)(object.getY() + 0.5f);
                    this.mInitialTouchY = n5;
                    n5 = 0;
                    if (bl2) {
                        n5 = 0 | 1;
                    }
                    n4 = n5;
                    if (bl3) {
                        n4 = n5 | 2;
                    }
                    this.startNestedScroll(n4);
                    n5 = n2;
                    break;
                }
                case 5: {
                    this.mScrollPointerId = MotionEventCompat.getPointerId(object, n4);
                    this.mLastTouchX = n5 = (int)(MotionEventCompat.getX(object, n4) + 0.5f);
                    this.mInitialTouchX = n5;
                    this.mLastTouchY = n5 = (int)(MotionEventCompat.getY(object, n4) + 0.5f);
                    this.mInitialTouchY = n5;
                    n5 = n2;
                    break;
                }
                case 2: {
                    n5 = MotionEventCompat.findPointerIndex(object, this.mScrollPointerId);
                    if (n5 < 0) {
                        Log.e((String)TAG, (String)("Error processing scroll; pointer index for id " + this.mScrollPointerId + " not found. Did any MotionEvents get skipped?"));
                        return false;
                    }
                    int n7 = (int)(MotionEventCompat.getX(object, n5) + 0.5f);
                    int n8 = (int)(MotionEventCompat.getY(object, n5) + 0.5f);
                    int n9 = this.mLastTouchX - n7;
                    n3 = this.mLastTouchY - n8;
                    n4 = n9;
                    n5 = n3;
                    if (this.dispatchNestedPreScroll(n9, n3, this.mScrollConsumed, this.mScrollOffset)) {
                        n4 = n9 - this.mScrollConsumed[0];
                        n5 = n3 - this.mScrollConsumed[1];
                        motionEvent.offsetLocation((float)this.mScrollOffset[0], (float)this.mScrollOffset[1]);
                        int[] nArray = this.mNestedOffsets;
                        nArray[0] = nArray[0] + this.mScrollOffset[0];
                        int[] nArray2 = this.mNestedOffsets;
                        nArray2[1] = nArray2[1] + this.mScrollOffset[1];
                    }
                    int n10 = n4;
                    n9 = n5;
                    if (this.mScrollState != 1) {
                        n10 = 0;
                        n3 = n4;
                        n9 = n10;
                        if (bl2) {
                            n3 = n4;
                            n9 = n10;
                            if (Math.abs(n4) > this.mTouchSlop) {
                                n3 = n4 > 0 ? n4 - this.mTouchSlop : n4 + this.mTouchSlop;
                                n9 = 1;
                            }
                        }
                        n4 = n5;
                        int n11 = n9;
                        if (bl3) {
                            n4 = n5;
                            n11 = n9;
                            if (Math.abs(n5) > this.mTouchSlop) {
                                n4 = n5 > 0 ? n5 - this.mTouchSlop : n5 + this.mTouchSlop;
                                n11 = 1;
                            }
                        }
                        n10 = n3;
                        n9 = n4;
                        if (n11 != 0) {
                            this.setScrollState(1);
                            n9 = n4;
                            n10 = n3;
                        }
                    }
                    n5 = n2;
                    n6 = 4;
                    if (this.mScrollState != 1) continue block9;
                    this.mLastTouchX = n7 - this.mScrollOffset[0];
                    this.mLastTouchY = n8 - this.mScrollOffset[1];
                    if (!bl2) {
                        n10 = 0;
                    }
                    if (!bl3) {
                        n9 = 0;
                    }
                    n5 = n2;
                    n6 = 4;
                    if (!this.scrollByInternal(n10, n9, motionEvent)) continue block9;
                    this.getParent().requestDisallowInterceptTouchEvent(true);
                    n5 = n2;
                    break;
                }
                case 6: {
                    this.onPointerUp((MotionEvent)object);
                    n5 = n2;
                    break;
                }
                case 1: {
                    this.mVelocityTracker.addMovement(motionEvent);
                    n5 = 1;
                    this.mVelocityTracker.computeCurrentVelocity(1000, (float)this.mMaxFlingVelocity);
                    float f2 = bl2 ? -VelocityTrackerCompat.getXVelocity(this.mVelocityTracker, this.mScrollPointerId) : 0.0f;
                    float f3 = bl3 ? -VelocityTrackerCompat.getYVelocity(this.mVelocityTracker, this.mScrollPointerId) : 0.0f;
                    if (f2 == 0.0f && f3 == 0.0f || !this.fling((int)f2, (int)f3)) {
                        this.setScrollState(0);
                    }
                    this.resetTouch();
                }
                case 4: {
                    break;
                }
                case 3: {
                    this.cancelTouch();
                    n5 = n2;
                }
            }
            break;
        } while (true);
        if (n5 == 0) {
            this.mVelocityTracker.addMovement(motionEvent);
        }
        motionEvent.recycle();
        return true;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void removeDetachedView(View view, boolean bl2) {
        ViewHolder viewHolder = RecyclerView.getChildViewHolderInt(view);
        if (viewHolder != null) {
            if (viewHolder.isTmpDetached()) {
                viewHolder.clearTmpDetachFlag();
            } else if (!viewHolder.shouldIgnore()) {
                throw new IllegalArgumentException("Called removeDetachedView with a view which is not flagged as tmp detached." + viewHolder);
            }
        }
        this.dispatchChildDetached(view);
        super.removeDetachedView(view, bl2);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void removeItemDecoration(ItemDecoration itemDecoration) {
        if (this.mLayout != null) {
            this.mLayout.assertNotInLayoutOrScroll("Cannot remove item decoration during a scroll  or layout");
        }
        this.mItemDecorations.remove(itemDecoration);
        if (this.mItemDecorations.isEmpty()) {
            boolean bl2 = ViewCompat.getOverScrollMode((View)this) == 2;
            this.setWillNotDraw(bl2);
        }
        this.markItemDecorInsetsDirty();
        this.requestLayout();
    }

    public void removeOnChildAttachStateChangeListener(OnChildAttachStateChangeListener onChildAttachStateChangeListener) {
        if (this.mOnChildAttachStateListeners == null) {
            return;
        }
        this.mOnChildAttachStateListeners.remove(onChildAttachStateChangeListener);
    }

    public void removeOnItemTouchListener(OnItemTouchListener onItemTouchListener) {
        this.mOnItemTouchListeners.remove(onItemTouchListener);
        if (this.mActiveOnItemTouchListener == onItemTouchListener) {
            this.mActiveOnItemTouchListener = null;
        }
    }

    public void removeOnScrollListener(OnScrollListener onScrollListener) {
        if (this.mScrollListeners != null) {
            this.mScrollListeners.remove(onScrollListener);
        }
    }

    public void requestChildFocus(View view, View view2) {
        boolean bl2 = false;
        if (!this.mLayout.onRequestChildFocus(this, this.mState, view, view2) && view2 != null) {
            this.mTempRect.set(0, 0, view2.getWidth(), view2.getHeight());
            Object object = view2.getLayoutParams();
            if (object instanceof LayoutParams) {
                object = (LayoutParams)((Object)object);
                if (!object.mInsetsDirty) {
                    object = object.mDecorInsets;
                    Rect rect = this.mTempRect;
                    rect.left -= object.left;
                    rect = this.mTempRect;
                    rect.right += object.right;
                    rect = this.mTempRect;
                    rect.top -= object.top;
                    rect = this.mTempRect;
                    rect.bottom += object.bottom;
                }
            }
            this.offsetDescendantRectToMyCoords(view2, this.mTempRect);
            this.offsetRectIntoDescendantCoords(view, this.mTempRect);
            object = this.mTempRect;
            if (!this.mFirstLayoutComplete) {
                bl2 = true;
            }
            this.requestChildRectangleOnScreen(view, (Rect)object, bl2);
        }
        super.requestChildFocus(view, view2);
    }

    public boolean requestChildRectangleOnScreen(View view, Rect rect, boolean bl2) {
        return this.mLayout.requestChildRectangleOnScreen(this, view, rect, bl2);
    }

    public void requestDisallowInterceptTouchEvent(boolean bl2) {
        int n2 = this.mOnItemTouchListeners.size();
        for (int i2 = 0; i2 < n2; ++i2) {
            this.mOnItemTouchListeners.get(i2).onRequestDisallowInterceptTouchEvent(bl2);
        }
        super.requestDisallowInterceptTouchEvent(bl2);
    }

    public void requestLayout() {
        if (this.mEatRequestLayout == 0 && !this.mLayoutFrozen) {
            super.requestLayout();
            return;
        }
        this.mLayoutRequestEaten = true;
    }

    void resumeRequestLayout(boolean bl2) {
        if (this.mEatRequestLayout < 1) {
            this.mEatRequestLayout = 1;
        }
        if (!bl2) {
            this.mLayoutRequestEaten = false;
        }
        if (this.mEatRequestLayout == 1) {
            if (bl2 && this.mLayoutRequestEaten && !this.mLayoutFrozen && this.mLayout != null && this.mAdapter != null) {
                this.dispatchLayout();
            }
            if (!this.mLayoutFrozen) {
                this.mLayoutRequestEaten = false;
            }
        }
        --this.mEatRequestLayout;
    }

    void saveOldPositions() {
        int n2 = this.mChildHelper.getUnfilteredChildCount();
        for (int i2 = 0; i2 < n2; ++i2) {
            ViewHolder viewHolder = RecyclerView.getChildViewHolderInt(this.mChildHelper.getUnfilteredChildAt(i2));
            if (viewHolder.shouldIgnore()) continue;
            viewHolder.saveOldPosition();
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void scrollBy(int n2, int n3) {
        if (this.mLayout == null) {
            Log.e((String)TAG, (String)"Cannot scroll without a LayoutManager set. Call setLayoutManager with a non-null argument.");
            return;
        }
        if (this.mLayoutFrozen) return;
        boolean bl2 = this.mLayout.canScrollHorizontally();
        boolean bl3 = this.mLayout.canScrollVertically();
        if (!bl2 && !bl3) return;
        if (bl2) {
        } else {
            n2 = 0;
        }
        if (!bl3) {
            n3 = 0;
        }
        this.scrollByInternal(n2, n3, null);
    }

    /*
     * Enabled aggressive block sorting
     */
    boolean scrollByInternal(int n2, int n3, MotionEvent object) {
        int n4 = 0;
        int n5 = 0;
        int n6 = 0;
        int n7 = 0;
        int n8 = 0;
        int n9 = 0;
        int n10 = 0;
        int n11 = 0;
        this.consumePendingUpdateOperations();
        if (this.mAdapter != null) {
            this.eatRequestLayout();
            this.onEnterLayoutOrScroll();
            TraceCompat.beginSection(TRACE_SCROLL_TAG);
            n8 = n9;
            n4 = n5;
            if (n2 != 0) {
                n8 = this.mLayout.scrollHorizontallyBy(n2, this.mRecycler, this.mState);
                n4 = n2 - n8;
            }
            n10 = n11;
            n6 = n7;
            if (n3 != 0) {
                n10 = this.mLayout.scrollVerticallyBy(n3, this.mRecycler, this.mState);
                n6 = n3 - n10;
            }
            TraceCompat.endSection();
            this.repositionShadowingViews();
            this.onExitLayoutOrScroll();
            this.resumeRequestLayout(false);
        }
        if (!this.mItemDecorations.isEmpty()) {
            this.invalidate();
        }
        if (this.dispatchNestedScroll(n8, n10, n4, n6, this.mScrollOffset)) {
            this.mLastTouchX -= this.mScrollOffset[0];
            this.mLastTouchY -= this.mScrollOffset[1];
            if (object != null) {
                object.offsetLocation((float)this.mScrollOffset[0], (float)this.mScrollOffset[1]);
            }
            int[] nArray = this.mNestedOffsets;
            nArray[0] = nArray[0] + this.mScrollOffset[0];
            int[] nArray2 = this.mNestedOffsets;
            nArray2[1] = nArray2[1] + this.mScrollOffset[1];
        } else if (ViewCompat.getOverScrollMode((View)this) != 2) {
            if (object != null) {
                this.pullGlows(object.getX(), n4, object.getY(), n6);
            }
            this.considerReleasingGlowsOnScroll(n2, n3);
        }
        if (n8 != 0 || n10 != 0) {
            this.dispatchOnScrolled(n8, n10);
        }
        if (!this.awakenScrollBars()) {
            this.invalidate();
        }
        return n8 != 0 || n10 != 0;
    }

    public void scrollTo(int n2, int n3) {
        Log.w((String)TAG, (String)"RecyclerView does not support scrolling to an absolute position. Use scrollToPosition instead");
    }

    public void scrollToPosition(int n2) {
        if (this.mLayoutFrozen) {
            return;
        }
        this.stopScroll();
        if (this.mLayout == null) {
            Log.e((String)TAG, (String)"Cannot scroll to position a LayoutManager set. Call setLayoutManager with a non-null argument.");
            return;
        }
        this.mLayout.scrollToPosition(n2);
        this.awakenScrollBars();
    }

    public void sendAccessibilityEventUnchecked(AccessibilityEvent accessibilityEvent) {
        if (this.shouldDeferAccessibilityEvent(accessibilityEvent)) {
            return;
        }
        super.sendAccessibilityEventUnchecked(accessibilityEvent);
    }

    public void setAccessibilityDelegateCompat(RecyclerViewAccessibilityDelegate recyclerViewAccessibilityDelegate) {
        this.mAccessibilityDelegate = recyclerViewAccessibilityDelegate;
        ViewCompat.setAccessibilityDelegate((View)this, this.mAccessibilityDelegate);
    }

    public void setAdapter(Adapter adapter) {
        this.setLayoutFrozen(false);
        this.setAdapterInternal(adapter, false, true);
        this.requestLayout();
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setChildDrawingOrderCallback(ChildDrawingOrderCallback childDrawingOrderCallback) {
        if (childDrawingOrderCallback == this.mChildDrawingOrderCallback) {
            return;
        }
        this.mChildDrawingOrderCallback = childDrawingOrderCallback;
        boolean bl2 = this.mChildDrawingOrderCallback != null;
        this.setChildrenDrawingOrderEnabled(bl2);
    }

    public void setClipToPadding(boolean bl2) {
        if (bl2 != this.mClipToPadding) {
            this.invalidateGlows();
        }
        this.mClipToPadding = bl2;
        super.setClipToPadding(bl2);
        if (this.mFirstLayoutComplete) {
            this.requestLayout();
        }
    }

    public void setHasFixedSize(boolean bl2) {
        this.mHasFixedSize = bl2;
    }

    public void setItemAnimator(ItemAnimator itemAnimator) {
        if (this.mItemAnimator != null) {
            this.mItemAnimator.endAnimations();
            this.mItemAnimator.setListener(null);
        }
        this.mItemAnimator = itemAnimator;
        if (this.mItemAnimator != null) {
            this.mItemAnimator.setListener(this.mItemAnimatorListener);
        }
    }

    public void setItemViewCacheSize(int n2) {
        this.mRecycler.setViewCacheSize(n2);
    }

    public void setLayoutFrozen(boolean bl2) {
        block5: {
            block4: {
                if (bl2 == this.mLayoutFrozen) break block4;
                this.assertNotInLayoutOrScroll("Do not setLayoutFrozen in layout or scroll");
                if (bl2) break block5;
                this.mLayoutFrozen = false;
                if (this.mLayoutRequestEaten && this.mLayout != null && this.mAdapter != null) {
                    this.requestLayout();
                }
                this.mLayoutRequestEaten = false;
            }
            return;
        }
        long l2 = SystemClock.uptimeMillis();
        this.onTouchEvent(MotionEvent.obtain((long)l2, (long)l2, (int)3, (float)0.0f, (float)0.0f, (int)0));
        this.mLayoutFrozen = true;
        this.mIgnoreMotionEventTillDown = true;
        this.stopScroll();
    }

    public void setLayoutManager(LayoutManager layoutManager) {
        if (layoutManager == this.mLayout) {
            return;
        }
        this.stopScroll();
        if (this.mLayout != null) {
            if (this.mIsAttached) {
                this.mLayout.dispatchDetachedFromWindow(this, this.mRecycler);
            }
            this.mLayout.setRecyclerView(null);
        }
        this.mRecycler.clear();
        this.mChildHelper.removeAllViewsUnfiltered();
        this.mLayout = layoutManager;
        if (layoutManager != null) {
            if (layoutManager.mRecyclerView != null) {
                throw new IllegalArgumentException("LayoutManager " + layoutManager + " is already attached to a RecyclerView: " + layoutManager.mRecyclerView);
            }
            this.mLayout.setRecyclerView(this);
            if (this.mIsAttached) {
                this.mLayout.dispatchAttachedToWindow(this);
            }
        }
        this.requestLayout();
    }

    @Override
    public void setNestedScrollingEnabled(boolean bl2) {
        this.getScrollingChildHelper().setNestedScrollingEnabled(bl2);
    }

    @Deprecated
    public void setOnScrollListener(OnScrollListener onScrollListener) {
        this.mScrollListener = onScrollListener;
    }

    public void setRecycledViewPool(RecycledViewPool recycledViewPool) {
        this.mRecycler.setRecycledViewPool(recycledViewPool);
    }

    public void setRecyclerListener(RecyclerListener recyclerListener) {
        this.mRecyclerListener = recyclerListener;
    }

    public void setScrollingTouchSlop(int n2) {
        ViewConfiguration viewConfiguration = ViewConfiguration.get((Context)this.getContext());
        switch (n2) {
            default: {
                Log.w((String)TAG, (String)("setScrollingTouchSlop(): bad argument constant " + n2 + "; using default value"));
            }
            case 0: {
                this.mTouchSlop = viewConfiguration.getScaledTouchSlop();
                return;
            }
            case 1: 
        }
        this.mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(viewConfiguration);
    }

    public void setViewCacheExtension(ViewCacheExtension viewCacheExtension) {
        this.mRecycler.setViewCacheExtension(viewCacheExtension);
    }

    boolean shouldDeferAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        if (this.isComputingLayout()) {
            int n2 = 0;
            if (accessibilityEvent != null) {
                n2 = AccessibilityEventCompat.getContentChangeTypes(accessibilityEvent);
            }
            int n3 = n2;
            if (n2 == 0) {
                n3 = 0;
            }
            this.mEatenAccessibilityChangeFlags |= n3;
            return true;
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void smoothScrollBy(int n2, int n3) {
        if (this.mLayout == null) {
            Log.e((String)TAG, (String)"Cannot smooth scroll without a LayoutManager set. Call setLayoutManager with a non-null argument.");
            return;
        } else {
            if (this.mLayoutFrozen) return;
            if (!this.mLayout.canScrollHorizontally()) {
                n2 = 0;
            }
            if (!this.mLayout.canScrollVertically()) {
                n3 = 0;
            }
            if (n2 == 0 && n3 == 0) return;
            this.mViewFlinger.smoothScrollBy(n2, n3);
            return;
        }
    }

    public void smoothScrollToPosition(int n2) {
        if (this.mLayoutFrozen) {
            return;
        }
        if (this.mLayout == null) {
            Log.e((String)TAG, (String)"Cannot smooth scroll without a LayoutManager set. Call setLayoutManager with a non-null argument.");
            return;
        }
        this.mLayout.smoothScrollToPosition(this, this.mState, n2);
    }

    @Override
    public boolean startNestedScroll(int n2) {
        return this.getScrollingChildHelper().startNestedScroll(n2);
    }

    @Override
    public void stopNestedScroll() {
        this.getScrollingChildHelper().stopNestedScroll();
    }

    public void stopScroll() {
        this.setScrollState(0);
        this.stopScrollersInternal();
    }

    public void swapAdapter(Adapter adapter, boolean bl2) {
        this.setLayoutFrozen(false);
        this.setAdapterInternal(adapter, true, bl2);
        this.setDataSetChangedAfterLayout();
        this.requestLayout();
    }

    /*
     * Enabled aggressive block sorting
     */
    void viewRangeUpdate(int n2, int n3, Object object) {
        int n4 = this.mChildHelper.getUnfilteredChildCount();
        int n5 = 0;
        while (true) {
            if (n5 >= n4) {
                this.mRecycler.viewRangeUpdate(n2, n3);
                return;
            }
            View view = this.mChildHelper.getUnfilteredChildAt(n5);
            ViewHolder viewHolder = RecyclerView.getChildViewHolderInt(view);
            if (viewHolder != null && !viewHolder.shouldIgnore() && viewHolder.mPosition >= n2 && viewHolder.mPosition < n2 + n3) {
                viewHolder.addFlags(2);
                viewHolder.addChangePayload(object);
                ((LayoutParams)view.getLayoutParams()).mInsetsDirty = true;
            }
            ++n5;
        }
    }

    public static abstract class Adapter<VH extends ViewHolder> {
        private boolean mHasStableIds = false;
        private final AdapterDataObservable mObservable = new AdapterDataObservable();

        public final void bindViewHolder(VH VH, int n2) {
            ((ViewHolder)VH).mPosition = n2;
            if (this.hasStableIds()) {
                ((ViewHolder)VH).mItemId = this.getItemId(n2);
            }
            ((ViewHolder)VH).setFlags(1, 519);
            TraceCompat.beginSection(RecyclerView.TRACE_BIND_VIEW_TAG);
            this.onBindViewHolder(VH, n2, ((ViewHolder)VH).getUnmodifiedPayloads());
            ((ViewHolder)VH).clearPayload();
            TraceCompat.endSection();
        }

        public final VH createViewHolder(ViewGroup object, int n2) {
            TraceCompat.beginSection(RecyclerView.TRACE_CREATE_VIEW_TAG);
            object = this.onCreateViewHolder((ViewGroup)object, n2);
            object.mItemViewType = n2;
            TraceCompat.endSection();
            return (VH)object;
        }

        public abstract int getItemCount();

        public long getItemId(int n2) {
            return -1L;
        }

        public int getItemViewType(int n2) {
            return 0;
        }

        public final boolean hasObservers() {
            return this.mObservable.hasObservers();
        }

        public final boolean hasStableIds() {
            return this.mHasStableIds;
        }

        public final void notifyDataSetChanged() {
            this.mObservable.notifyChanged();
        }

        public final void notifyItemChanged(int n2) {
            this.mObservable.notifyItemRangeChanged(n2, 1);
        }

        public final void notifyItemChanged(int n2, Object object) {
            this.mObservable.notifyItemRangeChanged(n2, 1, object);
        }

        public final void notifyItemInserted(int n2) {
            this.mObservable.notifyItemRangeInserted(n2, 1);
        }

        public final void notifyItemMoved(int n2, int n3) {
            this.mObservable.notifyItemMoved(n2, n3);
        }

        public final void notifyItemRangeChanged(int n2, int n3) {
            this.mObservable.notifyItemRangeChanged(n2, n3);
        }

        public final void notifyItemRangeChanged(int n2, int n3, Object object) {
            this.mObservable.notifyItemRangeChanged(n2, n3, object);
        }

        public final void notifyItemRangeInserted(int n2, int n3) {
            this.mObservable.notifyItemRangeInserted(n2, n3);
        }

        public final void notifyItemRangeRemoved(int n2, int n3) {
            this.mObservable.notifyItemRangeRemoved(n2, n3);
        }

        public final void notifyItemRemoved(int n2) {
            this.mObservable.notifyItemRangeRemoved(n2, 1);
        }

        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        }

        public abstract void onBindViewHolder(VH var1, int var2);

        public void onBindViewHolder(VH VH, int n2, List<Object> list) {
            this.onBindViewHolder(VH, n2);
        }

        public abstract VH onCreateViewHolder(ViewGroup var1, int var2);

        public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        }

        public boolean onFailedToRecycleView(VH VH) {
            return false;
        }

        public void onViewAttachedToWindow(VH VH) {
        }

        public void onViewDetachedFromWindow(VH VH) {
        }

        public void onViewRecycled(VH VH) {
        }

        public void registerAdapterDataObserver(AdapterDataObserver adapterDataObserver) {
            this.mObservable.registerObserver(adapterDataObserver);
        }

        public void setHasStableIds(boolean bl2) {
            if (this.hasObservers()) {
                throw new IllegalStateException("Cannot change whether this adapter has stable IDs while the adapter has registered observers.");
            }
            this.mHasStableIds = bl2;
        }

        public void unregisterAdapterDataObserver(AdapterDataObserver adapterDataObserver) {
            this.mObservable.unregisterObserver(adapterDataObserver);
        }
    }

    static class AdapterDataObservable
    extends Observable<AdapterDataObserver> {
        AdapterDataObservable() {
        }

        public boolean hasObservers() {
            return !this.mObservers.isEmpty();
        }

        public void notifyChanged() {
            for (int i2 = this.mObservers.size() - 1; i2 >= 0; --i2) {
                ((AdapterDataObserver)this.mObservers.get(i2)).onChanged();
            }
        }

        public void notifyItemMoved(int n2, int n3) {
            for (int i2 = this.mObservers.size() - 1; i2 >= 0; --i2) {
                ((AdapterDataObserver)this.mObservers.get(i2)).onItemRangeMoved(n2, n3, 1);
            }
        }

        public void notifyItemRangeChanged(int n2, int n3) {
            this.notifyItemRangeChanged(n2, n3, null);
        }

        public void notifyItemRangeChanged(int n2, int n3, Object object) {
            for (int i2 = this.mObservers.size() - 1; i2 >= 0; --i2) {
                ((AdapterDataObserver)this.mObservers.get(i2)).onItemRangeChanged(n2, n3, object);
            }
        }

        public void notifyItemRangeInserted(int n2, int n3) {
            for (int i2 = this.mObservers.size() - 1; i2 >= 0; --i2) {
                ((AdapterDataObserver)this.mObservers.get(i2)).onItemRangeInserted(n2, n3);
            }
        }

        public void notifyItemRangeRemoved(int n2, int n3) {
            for (int i2 = this.mObservers.size() - 1; i2 >= 0; --i2) {
                ((AdapterDataObserver)this.mObservers.get(i2)).onItemRangeRemoved(n2, n3);
            }
        }
    }

    public static abstract class AdapterDataObserver {
        public void onChanged() {
        }

        public void onItemRangeChanged(int n2, int n3) {
        }

        public void onItemRangeChanged(int n2, int n3, Object object) {
            this.onItemRangeChanged(n2, n3);
        }

        public void onItemRangeInserted(int n2, int n3) {
        }

        public void onItemRangeMoved(int n2, int n3, int n4) {
        }

        public void onItemRangeRemoved(int n2, int n3) {
        }
    }

    public static interface ChildDrawingOrderCallback {
        public int onGetChildDrawingOrder(int var1, int var2);
    }

    public static abstract class ItemAnimator {
        public static final int FLAG_APPEARED_IN_PRE_LAYOUT = 4096;
        public static final int FLAG_CHANGED = 2;
        public static final int FLAG_INVALIDATED = 4;
        public static final int FLAG_MOVED = 2048;
        public static final int FLAG_REMOVED = 8;
        private long mAddDuration = 120L;
        private long mChangeDuration = 250L;
        private ArrayList<ItemAnimatorFinishedListener> mFinishedListeners = new ArrayList();
        private ItemAnimatorListener mListener = null;
        private long mMoveDuration = 250L;
        private long mRemoveDuration = 120L;

        static int buildAdapterChangeFlagsForAnimations(ViewHolder viewHolder) {
            int n2 = viewHolder.mFlags & 0xE;
            if (viewHolder.isInvalid()) {
                return 4;
            }
            int n3 = n2;
            if ((n2 & 4) == 0) {
                int n4 = viewHolder.getOldPosition();
                int n5 = viewHolder.getAdapterPosition();
                n3 = n2;
                if (n4 != -1) {
                    n3 = n2;
                    if (n5 != -1) {
                        n3 = n2;
                        if (n4 != n5) {
                            n3 = n2 | 0x800;
                        }
                    }
                }
            }
            return n3;
        }

        public abstract boolean animateAppearance(@NonNull ViewHolder var1, @Nullable ItemHolderInfo var2, @NonNull ItemHolderInfo var3);

        public abstract boolean animateChange(@NonNull ViewHolder var1, @NonNull ViewHolder var2, @NonNull ItemHolderInfo var3, @NonNull ItemHolderInfo var4);

        public abstract boolean animateDisappearance(@NonNull ViewHolder var1, @NonNull ItemHolderInfo var2, @Nullable ItemHolderInfo var3);

        public abstract boolean animatePersistence(@NonNull ViewHolder var1, @NonNull ItemHolderInfo var2, @NonNull ItemHolderInfo var3);

        public boolean canReuseUpdatedViewHolder(@NonNull ViewHolder viewHolder) {
            return true;
        }

        public boolean canReuseUpdatedViewHolder(@NonNull ViewHolder viewHolder, @NonNull List<Object> list) {
            return this.canReuseUpdatedViewHolder(viewHolder);
        }

        public final void dispatchAnimationFinished(ViewHolder viewHolder) {
            this.onAnimationFinished(viewHolder);
            if (this.mListener != null) {
                this.mListener.onAnimationFinished(viewHolder);
            }
        }

        public final void dispatchAnimationStarted(ViewHolder viewHolder) {
            this.onAnimationStarted(viewHolder);
        }

        public final void dispatchAnimationsFinished() {
            int n2 = this.mFinishedListeners.size();
            for (int i2 = 0; i2 < n2; ++i2) {
                this.mFinishedListeners.get(i2).onAnimationsFinished();
            }
            this.mFinishedListeners.clear();
        }

        public abstract void endAnimation(ViewHolder var1);

        public abstract void endAnimations();

        public long getAddDuration() {
            return this.mAddDuration;
        }

        public long getChangeDuration() {
            return this.mChangeDuration;
        }

        public long getMoveDuration() {
            return this.mMoveDuration;
        }

        public long getRemoveDuration() {
            return this.mRemoveDuration;
        }

        public abstract boolean isRunning();

        public final boolean isRunning(ItemAnimatorFinishedListener itemAnimatorFinishedListener) {
            boolean bl2;
            block3: {
                block2: {
                    bl2 = this.isRunning();
                    if (itemAnimatorFinishedListener == null) break block2;
                    if (bl2) break block3;
                    itemAnimatorFinishedListener.onAnimationsFinished();
                }
                return bl2;
            }
            this.mFinishedListeners.add(itemAnimatorFinishedListener);
            return bl2;
        }

        public ItemHolderInfo obtainHolderInfo() {
            return new ItemHolderInfo();
        }

        public void onAnimationFinished(ViewHolder viewHolder) {
        }

        public void onAnimationStarted(ViewHolder viewHolder) {
        }

        @NonNull
        public ItemHolderInfo recordPostLayoutInformation(@NonNull State state, @NonNull ViewHolder viewHolder) {
            return this.obtainHolderInfo().setFrom(viewHolder);
        }

        @NonNull
        public ItemHolderInfo recordPreLayoutInformation(@NonNull State state, @NonNull ViewHolder viewHolder, int n2, @NonNull List<Object> list) {
            return this.obtainHolderInfo().setFrom(viewHolder);
        }

        public abstract void runPendingAnimations();

        public void setAddDuration(long l2) {
            this.mAddDuration = l2;
        }

        public void setChangeDuration(long l2) {
            this.mChangeDuration = l2;
        }

        void setListener(ItemAnimatorListener itemAnimatorListener) {
            this.mListener = itemAnimatorListener;
        }

        public void setMoveDuration(long l2) {
            this.mMoveDuration = l2;
        }

        public void setRemoveDuration(long l2) {
            this.mRemoveDuration = l2;
        }

        @Retention(value=RetentionPolicy.SOURCE)
        public static @interface AdapterChanges {
        }

        public static interface ItemAnimatorFinishedListener {
            public void onAnimationsFinished();
        }

        static interface ItemAnimatorListener {
            public void onAnimationFinished(ViewHolder var1);
        }

        public static class ItemHolderInfo {
            public int bottom;
            public int changeFlags;
            public int left;
            public int right;
            public int top;

            public ItemHolderInfo setFrom(ViewHolder viewHolder) {
                return this.setFrom(viewHolder, 0);
            }

            public ItemHolderInfo setFrom(ViewHolder viewHolder, int n2) {
                viewHolder = viewHolder.itemView;
                this.left = viewHolder.getLeft();
                this.top = viewHolder.getTop();
                this.right = viewHolder.getRight();
                this.bottom = viewHolder.getBottom();
                return this;
            }
        }
    }

    private class ItemAnimatorRestoreListener
    implements ItemAnimator.ItemAnimatorListener {
        private ItemAnimatorRestoreListener() {
        }

        @Override
        public void onAnimationFinished(ViewHolder viewHolder) {
            viewHolder.setIsRecyclable(true);
            if (viewHolder.mShadowedHolder != null && viewHolder.mShadowingHolder == null) {
                viewHolder.mShadowedHolder = null;
            }
            viewHolder.mShadowingHolder = null;
            if (!viewHolder.shouldBeKeptAsChild() && !RecyclerView.this.removeAnimatingView(viewHolder.itemView) && viewHolder.isTmpDetached()) {
                RecyclerView.this.removeDetachedView(viewHolder.itemView, false);
            }
        }
    }

    public static abstract class ItemDecoration {
        @Deprecated
        public void getItemOffsets(Rect rect, int n2, RecyclerView recyclerView) {
            rect.set(0, 0, 0, 0);
        }

        public void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, State state) {
            this.getItemOffsets(rect, ((LayoutParams)view.getLayoutParams()).getViewLayoutPosition(), recyclerView);
        }

        @Deprecated
        public void onDraw(Canvas canvas, RecyclerView recyclerView) {
        }

        public void onDraw(Canvas canvas, RecyclerView recyclerView, State state) {
            this.onDraw(canvas, recyclerView);
        }

        @Deprecated
        public void onDrawOver(Canvas canvas, RecyclerView recyclerView) {
        }

        public void onDrawOver(Canvas canvas, RecyclerView recyclerView, State state) {
            this.onDrawOver(canvas, recyclerView);
        }
    }

    public static abstract class LayoutManager {
        private boolean mAutoMeasure = false;
        ChildHelper mChildHelper;
        private int mHeight;
        private int mHeightMode;
        boolean mIsAttachedToWindow = false;
        private boolean mMeasurementCacheEnabled = true;
        RecyclerView mRecyclerView;
        private boolean mRequestedSimpleAnimations = false;
        @Nullable
        SmoothScroller mSmoothScroller;
        private int mWidth;
        private int mWidthMode;

        static /* synthetic */ boolean access$2602(LayoutManager layoutManager, boolean bl2) {
            layoutManager.mRequestedSimpleAnimations = bl2;
            return bl2;
        }

        /*
         * Enabled aggressive block sorting
         */
        private void addViewInt(View view, int n2, boolean bl2) {
            ViewHolder viewHolder = RecyclerView.getChildViewHolderInt(view);
            if (bl2 || viewHolder.isRemoved()) {
                this.mRecyclerView.mViewInfoStore.addToDisappearedInLayout(viewHolder);
            } else {
                this.mRecyclerView.mViewInfoStore.removeFromDisappearedInLayout(viewHolder);
            }
            LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
            if (viewHolder.wasReturnedFromScrap() || viewHolder.isScrap()) {
                if (viewHolder.isScrap()) {
                    viewHolder.unScrap();
                } else {
                    viewHolder.clearReturnedFromScrapFlag();
                }
                this.mChildHelper.attachViewToParent(view, n2, view.getLayoutParams(), false);
            } else if (view.getParent() == this.mRecyclerView) {
                int n3 = this.mChildHelper.indexOfChild(view);
                int n4 = n2;
                if (n2 == -1) {
                    n4 = this.mChildHelper.getChildCount();
                }
                if (n3 == -1) {
                    throw new IllegalStateException("Added View has RecyclerView as parent but view is not a real child. Unfiltered index:" + this.mRecyclerView.indexOfChild(view));
                }
                if (n3 != n4) {
                    this.mRecyclerView.mLayout.moveView(n3, n4);
                }
            } else {
                this.mChildHelper.addView(view, n2, false);
                layoutParams.mInsetsDirty = true;
                if (this.mSmoothScroller != null && this.mSmoothScroller.isRunning()) {
                    this.mSmoothScroller.onChildAttachedToWindow(view);
                }
            }
            if (layoutParams.mPendingInvalidate) {
                viewHolder.itemView.invalidate();
                layoutParams.mPendingInvalidate = false;
            }
        }

        public static int chooseSize(int n2, int n3, int n4) {
            int n5;
            int n6 = View.MeasureSpec.getMode((int)n2);
            n2 = n5 = View.MeasureSpec.getSize((int)n2);
            switch (n6) {
                default: {
                    n2 = Math.max(n3, n4);
                }
                case 0x40000000: {
                    return n2;
                }
                case -2147483648: 
            }
            return Math.min(n5, Math.max(n3, n4));
        }

        private void detachViewInternal(int n2, View view) {
            this.mChildHelper.detachViewFromParent(n2);
        }

        /*
         * Enabled aggressive block sorting
         */
        public static int getChildMeasureSpec(int n2, int n3, int n4, int n5, boolean bl2) {
            int n6 = Math.max(0, n2 - n4);
            n4 = 0;
            n2 = 0;
            if (bl2) {
                if (n5 >= 0) {
                    n4 = n5;
                    n2 = 0x40000000;
                    return View.MeasureSpec.makeMeasureSpec((int)n4, (int)n2);
                }
                if (n5 != -1) {
                    if (n5 != -2) return View.MeasureSpec.makeMeasureSpec((int)n4, (int)n2);
                    n4 = 0;
                    n2 = 0;
                    return View.MeasureSpec.makeMeasureSpec((int)n4, (int)n2);
                }
                switch (n3) {
                    default: {
                        return View.MeasureSpec.makeMeasureSpec((int)n4, (int)n2);
                    }
                    case -2147483648: 
                    case 0x40000000: {
                        n4 = n6;
                        n2 = n3;
                        return View.MeasureSpec.makeMeasureSpec((int)n4, (int)n2);
                    }
                    case 0: 
                }
                n4 = 0;
                n2 = 0;
                return View.MeasureSpec.makeMeasureSpec((int)n4, (int)n2);
            }
            if (n5 >= 0) {
                n4 = n5;
                n2 = 0x40000000;
                return View.MeasureSpec.makeMeasureSpec((int)n4, (int)n2);
            }
            if (n5 == -1) {
                n4 = n6;
                n2 = n3;
                return View.MeasureSpec.makeMeasureSpec((int)n4, (int)n2);
            }
            if (n5 != -2) return View.MeasureSpec.makeMeasureSpec((int)n4, (int)n2);
            n4 = n6;
            if (n3 != Integer.MIN_VALUE && n3 != 0x40000000) {
                n2 = 0;
                return View.MeasureSpec.makeMeasureSpec((int)n4, (int)n2);
            }
            n2 = Integer.MIN_VALUE;
            return View.MeasureSpec.makeMeasureSpec((int)n4, (int)n2);
        }

        /*
         * Enabled aggressive block sorting
         */
        @Deprecated
        public static int getChildMeasureSpec(int n2, int n3, int n4, boolean bl2) {
            int n5 = Math.max(0, n2 - n3);
            n3 = 0;
            n2 = 0;
            if (bl2) {
                if (n4 >= 0) {
                    n3 = n4;
                    n2 = 0x40000000;
                    return View.MeasureSpec.makeMeasureSpec((int)n3, (int)n2);
                }
                n3 = 0;
                n2 = 0;
                return View.MeasureSpec.makeMeasureSpec((int)n3, (int)n2);
            }
            if (n4 >= 0) {
                n3 = n4;
                n2 = 0x40000000;
                return View.MeasureSpec.makeMeasureSpec((int)n3, (int)n2);
            }
            if (n4 == -1) {
                n3 = n5;
                n2 = 0x40000000;
                return View.MeasureSpec.makeMeasureSpec((int)n3, (int)n2);
            }
            if (n4 != -2) return View.MeasureSpec.makeMeasureSpec((int)n3, (int)n2);
            n3 = n5;
            n2 = Integer.MIN_VALUE;
            return View.MeasureSpec.makeMeasureSpec((int)n3, (int)n2);
        }

        public static Properties getProperties(Context context, AttributeSet attributeSet, int n2, int n3) {
            Properties properties = new Properties();
            context = context.obtainStyledAttributes(attributeSet, R.styleable.RecyclerView, n2, n3);
            properties.orientation = context.getInt(R.styleable.RecyclerView_android_orientation, 1);
            properties.spanCount = context.getInt(R.styleable.RecyclerView_spanCount, 1);
            properties.reverseLayout = context.getBoolean(R.styleable.RecyclerView_reverseLayout, false);
            properties.stackFromEnd = context.getBoolean(R.styleable.RecyclerView_stackFromEnd, false);
            context.recycle();
            return properties;
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        private static boolean isMeasurementUpToDate(int n2, int n3, int n4) {
            boolean bl2 = true;
            int n5 = View.MeasureSpec.getMode((int)n3);
            n3 = View.MeasureSpec.getSize((int)n3);
            if (n4 > 0 && n2 != n4) {
                return false;
            }
            boolean bl3 = bl2;
            switch (n5) {
                case 0: {
                    return bl3;
                }
                default: {
                    return false;
                }
                case -2147483648: {
                    bl3 = bl2;
                    if (n3 >= n2) return bl3;
                    return false;
                }
                case 0x40000000: 
            }
            bl3 = bl2;
            if (n3 == n2) return bl3;
            return false;
        }

        private void onSmoothScrollerStopped(SmoothScroller smoothScroller) {
            if (this.mSmoothScroller == smoothScroller) {
                this.mSmoothScroller = null;
            }
        }

        private void scrapOrRecycleView(Recycler recycler, int n2, View view) {
            ViewHolder viewHolder = RecyclerView.getChildViewHolderInt(view);
            if (viewHolder.shouldIgnore()) {
                return;
            }
            if (viewHolder.isInvalid() && !viewHolder.isRemoved() && !this.mRecyclerView.mAdapter.hasStableIds()) {
                this.removeViewAt(n2);
                recycler.recycleViewHolderInternal(viewHolder);
                return;
            }
            this.detachViewAt(n2);
            recycler.scrapView(view);
            this.mRecyclerView.mViewInfoStore.onViewDetached(viewHolder);
        }

        public void addDisappearingView(View view) {
            this.addDisappearingView(view, -1);
        }

        public void addDisappearingView(View view, int n2) {
            this.addViewInt(view, n2, true);
        }

        public void addView(View view) {
            this.addView(view, -1);
        }

        public void addView(View view, int n2) {
            this.addViewInt(view, n2, false);
        }

        public void assertInLayoutOrScroll(String string2) {
            if (this.mRecyclerView != null) {
                this.mRecyclerView.assertInLayoutOrScroll(string2);
            }
        }

        public void assertNotInLayoutOrScroll(String string2) {
            if (this.mRecyclerView != null) {
                this.mRecyclerView.assertNotInLayoutOrScroll(string2);
            }
        }

        public void attachView(View view) {
            this.attachView(view, -1);
        }

        public void attachView(View view, int n2) {
            this.attachView(view, n2, (LayoutParams)view.getLayoutParams());
        }

        /*
         * Enabled aggressive block sorting
         */
        public void attachView(View view, int n2, LayoutParams layoutParams) {
            ViewHolder viewHolder = RecyclerView.getChildViewHolderInt(view);
            if (viewHolder.isRemoved()) {
                this.mRecyclerView.mViewInfoStore.addToDisappearedInLayout(viewHolder);
            } else {
                this.mRecyclerView.mViewInfoStore.removeFromDisappearedInLayout(viewHolder);
            }
            this.mChildHelper.attachViewToParent(view, n2, (ViewGroup.LayoutParams)layoutParams, viewHolder.isRemoved());
        }

        public void calculateItemDecorationsForChild(View view, Rect rect) {
            if (this.mRecyclerView == null) {
                rect.set(0, 0, 0, 0);
                return;
            }
            rect.set(this.mRecyclerView.getItemDecorInsetsForChild(view));
        }

        public boolean canScrollHorizontally() {
            return false;
        }

        public boolean canScrollVertically() {
            return false;
        }

        public boolean checkLayoutParams(LayoutParams layoutParams) {
            return layoutParams != null;
        }

        public int computeHorizontalScrollExtent(State state) {
            return 0;
        }

        public int computeHorizontalScrollOffset(State state) {
            return 0;
        }

        public int computeHorizontalScrollRange(State state) {
            return 0;
        }

        public int computeVerticalScrollExtent(State state) {
            return 0;
        }

        public int computeVerticalScrollOffset(State state) {
            return 0;
        }

        public int computeVerticalScrollRange(State state) {
            return 0;
        }

        public void detachAndScrapAttachedViews(Recycler recycler) {
            for (int i2 = this.getChildCount() - 1; i2 >= 0; --i2) {
                this.scrapOrRecycleView(recycler, i2, this.getChildAt(i2));
            }
        }

        public void detachAndScrapView(View view, Recycler recycler) {
            this.scrapOrRecycleView(recycler, this.mChildHelper.indexOfChild(view), view);
        }

        public void detachAndScrapViewAt(int n2, Recycler recycler) {
            this.scrapOrRecycleView(recycler, n2, this.getChildAt(n2));
        }

        public void detachView(View view) {
            int n2 = this.mChildHelper.indexOfChild(view);
            if (n2 >= 0) {
                this.detachViewInternal(n2, view);
            }
        }

        public void detachViewAt(int n2) {
            this.detachViewInternal(n2, this.getChildAt(n2));
        }

        void dispatchAttachedToWindow(RecyclerView recyclerView) {
            this.mIsAttachedToWindow = true;
            this.onAttachedToWindow(recyclerView);
        }

        void dispatchDetachedFromWindow(RecyclerView recyclerView, Recycler recycler) {
            this.mIsAttachedToWindow = false;
            this.onDetachedFromWindow(recyclerView, recycler);
        }

        public void endAnimation(View view) {
            if (this.mRecyclerView.mItemAnimator != null) {
                this.mRecyclerView.mItemAnimator.endAnimation(RecyclerView.getChildViewHolderInt(view));
            }
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Nullable
        public View findContainingItemView(View view) {
            if (this.mRecyclerView == null) {
                return null;
            }
            View view2 = this.mRecyclerView.findContainingItemView(view);
            if (view2 == null) {
                return null;
            }
            view = view2;
            if (!this.mChildHelper.isHidden(view2)) return view;
            return null;
        }

        /*
         * Enabled aggressive block sorting
         */
        public View findViewByPosition(int n2) {
            int n3 = this.getChildCount();
            int n4 = 0;
            while (n4 < n3) {
                View view = this.getChildAt(n4);
                ViewHolder viewHolder = RecyclerView.getChildViewHolderInt(view);
                if (!(viewHolder == null || viewHolder.getLayoutPosition() != n2 || viewHolder.shouldIgnore() || !this.mRecyclerView.mState.isPreLayout() && viewHolder.isRemoved())) {
                    return view;
                }
                ++n4;
            }
            return null;
        }

        public abstract LayoutParams generateDefaultLayoutParams();

        public LayoutParams generateLayoutParams(Context context, AttributeSet attributeSet) {
            return new LayoutParams(context, attributeSet);
        }

        public LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
            if (layoutParams instanceof LayoutParams) {
                return new LayoutParams((LayoutParams)layoutParams);
            }
            if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
                return new LayoutParams((ViewGroup.MarginLayoutParams)layoutParams);
            }
            return new LayoutParams(layoutParams);
        }

        public int getBaseline() {
            return -1;
        }

        public int getBottomDecorationHeight(View view) {
            return ((LayoutParams)view.getLayoutParams()).mDecorInsets.bottom;
        }

        public View getChildAt(int n2) {
            if (this.mChildHelper != null) {
                return this.mChildHelper.getChildAt(n2);
            }
            return null;
        }

        public int getChildCount() {
            if (this.mChildHelper != null) {
                return this.mChildHelper.getChildCount();
            }
            return 0;
        }

        public boolean getClipToPadding() {
            return this.mRecyclerView != null && this.mRecyclerView.mClipToPadding;
        }

        /*
         * Enabled aggressive block sorting
         */
        public int getColumnCountForAccessibility(Recycler recycler, State state) {
            if (this.mRecyclerView == null || this.mRecyclerView.mAdapter == null || !this.canScrollHorizontally()) {
                return 1;
            }
            return this.mRecyclerView.mAdapter.getItemCount();
        }

        public int getDecoratedBottom(View view) {
            return view.getBottom() + this.getBottomDecorationHeight(view);
        }

        public int getDecoratedLeft(View view) {
            return view.getLeft() - this.getLeftDecorationWidth(view);
        }

        public int getDecoratedMeasuredHeight(View view) {
            Rect rect = ((LayoutParams)view.getLayoutParams()).mDecorInsets;
            return view.getMeasuredHeight() + rect.top + rect.bottom;
        }

        public int getDecoratedMeasuredWidth(View view) {
            Rect rect = ((LayoutParams)view.getLayoutParams()).mDecorInsets;
            return view.getMeasuredWidth() + rect.left + rect.right;
        }

        public int getDecoratedRight(View view) {
            return view.getRight() + this.getRightDecorationWidth(view);
        }

        public int getDecoratedTop(View view) {
            return view.getTop() - this.getTopDecorationHeight(view);
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        public View getFocusedChild() {
            if (this.mRecyclerView == null) {
                return null;
            }
            View view = this.mRecyclerView.getFocusedChild();
            if (view == null) return null;
            View view2 = view;
            if (!this.mChildHelper.isHidden(view)) return view2;
            return null;
        }

        public int getHeight() {
            return this.mHeight;
        }

        public int getHeightMode() {
            return this.mHeightMode;
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        public int getItemCount() {
            if (this.mRecyclerView == null) return 0;
            Adapter adapter = this.mRecyclerView.getAdapter();
            if (adapter == null) return 0;
            return adapter.getItemCount();
        }

        public int getItemViewType(View view) {
            return RecyclerView.getChildViewHolderInt(view).getItemViewType();
        }

        public int getLayoutDirection() {
            return ViewCompat.getLayoutDirection((View)this.mRecyclerView);
        }

        public int getLeftDecorationWidth(View view) {
            return ((LayoutParams)view.getLayoutParams()).mDecorInsets.left;
        }

        public int getMinimumHeight() {
            return ViewCompat.getMinimumHeight((View)this.mRecyclerView);
        }

        public int getMinimumWidth() {
            return ViewCompat.getMinimumWidth((View)this.mRecyclerView);
        }

        public int getPaddingBottom() {
            if (this.mRecyclerView != null) {
                return this.mRecyclerView.getPaddingBottom();
            }
            return 0;
        }

        public int getPaddingEnd() {
            if (this.mRecyclerView != null) {
                return ViewCompat.getPaddingEnd((View)this.mRecyclerView);
            }
            return 0;
        }

        public int getPaddingLeft() {
            if (this.mRecyclerView != null) {
                return this.mRecyclerView.getPaddingLeft();
            }
            return 0;
        }

        public int getPaddingRight() {
            if (this.mRecyclerView != null) {
                return this.mRecyclerView.getPaddingRight();
            }
            return 0;
        }

        public int getPaddingStart() {
            if (this.mRecyclerView != null) {
                return ViewCompat.getPaddingStart((View)this.mRecyclerView);
            }
            return 0;
        }

        public int getPaddingTop() {
            if (this.mRecyclerView != null) {
                return this.mRecyclerView.getPaddingTop();
            }
            return 0;
        }

        public int getPosition(View view) {
            return ((LayoutParams)view.getLayoutParams()).getViewLayoutPosition();
        }

        public int getRightDecorationWidth(View view) {
            return ((LayoutParams)view.getLayoutParams()).mDecorInsets.right;
        }

        /*
         * Enabled aggressive block sorting
         */
        public int getRowCountForAccessibility(Recycler recycler, State state) {
            if (this.mRecyclerView == null || this.mRecyclerView.mAdapter == null || !this.canScrollVertically()) {
                return 1;
            }
            return this.mRecyclerView.mAdapter.getItemCount();
        }

        public int getSelectionModeForAccessibility(Recycler recycler, State state) {
            return 0;
        }

        public int getTopDecorationHeight(View view) {
            return ((LayoutParams)view.getLayoutParams()).mDecorInsets.top;
        }

        public int getWidth() {
            return this.mWidth;
        }

        public int getWidthMode() {
            return this.mWidthMode;
        }

        boolean hasFlexibleChildInBothOrientations() {
            int n2 = this.getChildCount();
            for (int i2 = 0; i2 < n2; ++i2) {
                ViewGroup.LayoutParams layoutParams = this.getChildAt(i2).getLayoutParams();
                if (layoutParams.width >= 0 || layoutParams.height >= 0) continue;
                return true;
            }
            return false;
        }

        public boolean hasFocus() {
            return this.mRecyclerView != null && this.mRecyclerView.hasFocus();
        }

        public void ignoreView(View object) {
            if (object.getParent() != this.mRecyclerView || this.mRecyclerView.indexOfChild((View)object) == -1) {
                throw new IllegalArgumentException("View should be fully attached to be ignored");
            }
            object = RecyclerView.getChildViewHolderInt((View)object);
            ((ViewHolder)object).addFlags(128);
            this.mRecyclerView.mViewInfoStore.removeViewHolder((ViewHolder)object);
        }

        public boolean isAttachedToWindow() {
            return this.mIsAttachedToWindow;
        }

        public boolean isAutoMeasureEnabled() {
            return this.mAutoMeasure;
        }

        public boolean isFocused() {
            return this.mRecyclerView != null && this.mRecyclerView.isFocused();
        }

        public boolean isLayoutHierarchical(Recycler recycler, State state) {
            return false;
        }

        public boolean isMeasurementCacheEnabled() {
            return this.mMeasurementCacheEnabled;
        }

        public boolean isSmoothScrolling() {
            return this.mSmoothScroller != null && this.mSmoothScroller.isRunning();
        }

        public void layoutDecorated(View view, int n2, int n3, int n4, int n5) {
            Rect rect = ((LayoutParams)view.getLayoutParams()).mDecorInsets;
            view.layout(rect.left + n2, rect.top + n3, n4 - rect.right, n5 - rect.bottom);
        }

        public void measureChild(View view, int n2, int n3) {
            LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
            Rect rect = this.mRecyclerView.getItemDecorInsetsForChild(view);
            int n4 = rect.left;
            int n5 = rect.right;
            int n6 = rect.top;
            int n7 = rect.bottom;
            n2 = LayoutManager.getChildMeasureSpec(this.getWidth(), this.getWidthMode(), this.getPaddingLeft() + this.getPaddingRight() + (n2 + (n4 + n5)), layoutParams.width, this.canScrollHorizontally());
            if (this.shouldMeasureChild(view, n2, n3 = LayoutManager.getChildMeasureSpec(this.getHeight(), this.getHeightMode(), this.getPaddingTop() + this.getPaddingBottom() + (n3 + (n6 + n7)), layoutParams.height, this.canScrollVertically()), layoutParams)) {
                view.measure(n2, n3);
            }
        }

        public void measureChildWithMargins(View view, int n2, int n3) {
            LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
            Rect rect = this.mRecyclerView.getItemDecorInsetsForChild(view);
            int n4 = rect.left;
            int n5 = rect.right;
            int n6 = rect.top;
            int n7 = rect.bottom;
            n2 = LayoutManager.getChildMeasureSpec(this.getWidth(), this.getWidthMode(), this.getPaddingLeft() + this.getPaddingRight() + layoutParams.leftMargin + layoutParams.rightMargin + (n2 + (n4 + n5)), layoutParams.width, this.canScrollHorizontally());
            if (this.shouldMeasureChild(view, n2, n3 = LayoutManager.getChildMeasureSpec(this.getHeight(), this.getHeightMode(), this.getPaddingTop() + this.getPaddingBottom() + layoutParams.topMargin + layoutParams.bottomMargin + (n3 + (n6 + n7)), layoutParams.height, this.canScrollVertically()), layoutParams)) {
                view.measure(n2, n3);
            }
        }

        public void moveView(int n2, int n3) {
            View view = this.getChildAt(n2);
            if (view == null) {
                throw new IllegalArgumentException("Cannot move a child from non-existing index:" + n2);
            }
            this.detachViewAt(n2);
            this.attachView(view, n3);
        }

        public void offsetChildrenHorizontal(int n2) {
            if (this.mRecyclerView != null) {
                this.mRecyclerView.offsetChildrenHorizontal(n2);
            }
        }

        public void offsetChildrenVertical(int n2) {
            if (this.mRecyclerView != null) {
                this.mRecyclerView.offsetChildrenVertical(n2);
            }
        }

        public void onAdapterChanged(Adapter adapter, Adapter adapter2) {
        }

        public boolean onAddFocusables(RecyclerView recyclerView, ArrayList<View> arrayList, int n2, int n3) {
            return false;
        }

        @CallSuper
        public void onAttachedToWindow(RecyclerView recyclerView) {
        }

        @Deprecated
        public void onDetachedFromWindow(RecyclerView recyclerView) {
        }

        @CallSuper
        public void onDetachedFromWindow(RecyclerView recyclerView, Recycler recycler) {
            this.onDetachedFromWindow(recyclerView);
        }

        @Nullable
        public View onFocusSearchFailed(View view, int n2, Recycler recycler, State state) {
            return null;
        }

        /*
         * Enabled aggressive block sorting
         */
        public void onInitializeAccessibilityEvent(Recycler object, State state, AccessibilityEvent accessibilityEvent) {
            block7: {
                block6: {
                    boolean bl2 = true;
                    object = AccessibilityEventCompat.asRecord(accessibilityEvent);
                    if (this.mRecyclerView == null || object == null) break block6;
                    boolean bl3 = bl2;
                    if (!ViewCompat.canScrollVertically((View)this.mRecyclerView, 1)) {
                        bl3 = bl2;
                        if (!ViewCompat.canScrollVertically((View)this.mRecyclerView, -1)) {
                            bl3 = bl2;
                            if (!ViewCompat.canScrollHorizontally((View)this.mRecyclerView, -1)) {
                                bl3 = ViewCompat.canScrollHorizontally((View)this.mRecyclerView, 1) ? bl2 : false;
                            }
                        }
                    }
                    ((AccessibilityRecordCompat)object).setScrollable(bl3);
                    if (this.mRecyclerView.mAdapter != null) break block7;
                }
                return;
            }
            ((AccessibilityRecordCompat)object).setItemCount(this.mRecyclerView.mAdapter.getItemCount());
        }

        public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
            this.onInitializeAccessibilityEvent(this.mRecyclerView.mRecycler, this.mRecyclerView.mState, accessibilityEvent);
        }

        void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            this.onInitializeAccessibilityNodeInfo(this.mRecyclerView.mRecycler, this.mRecyclerView.mState, accessibilityNodeInfoCompat);
        }

        public void onInitializeAccessibilityNodeInfo(Recycler recycler, State state, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            if (ViewCompat.canScrollVertically((View)this.mRecyclerView, -1) || ViewCompat.canScrollHorizontally((View)this.mRecyclerView, -1)) {
                accessibilityNodeInfoCompat.addAction(8192);
                accessibilityNodeInfoCompat.setScrollable(true);
            }
            if (ViewCompat.canScrollVertically((View)this.mRecyclerView, 1) || ViewCompat.canScrollHorizontally((View)this.mRecyclerView, 1)) {
                accessibilityNodeInfoCompat.addAction(4096);
                accessibilityNodeInfoCompat.setScrollable(true);
            }
            accessibilityNodeInfoCompat.setCollectionInfo(AccessibilityNodeInfoCompat.CollectionInfoCompat.obtain(this.getRowCountForAccessibility(recycler, state), this.getColumnCountForAccessibility(recycler, state), this.isLayoutHierarchical(recycler, state), this.getSelectionModeForAccessibility(recycler, state)));
        }

        /*
         * Enabled aggressive block sorting
         */
        public void onInitializeAccessibilityNodeInfoForItem(Recycler recycler, State state, View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            int n2 = this.canScrollVertically() ? this.getPosition(view) : 0;
            int n3 = this.canScrollHorizontally() ? this.getPosition(view) : 0;
            accessibilityNodeInfoCompat.setCollectionItemInfo(AccessibilityNodeInfoCompat.CollectionItemInfoCompat.obtain(n2, 1, n3, 1, false, false));
        }

        void onInitializeAccessibilityNodeInfoForItem(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            ViewHolder viewHolder = RecyclerView.getChildViewHolderInt(view);
            if (viewHolder != null && !viewHolder.isRemoved() && !this.mChildHelper.isHidden(viewHolder.itemView)) {
                this.onInitializeAccessibilityNodeInfoForItem(this.mRecyclerView.mRecycler, this.mRecyclerView.mState, view, accessibilityNodeInfoCompat);
            }
        }

        public View onInterceptFocusSearch(View view, int n2) {
            return null;
        }

        public void onItemsAdded(RecyclerView recyclerView, int n2, int n3) {
        }

        public void onItemsChanged(RecyclerView recyclerView) {
        }

        public void onItemsMoved(RecyclerView recyclerView, int n2, int n3, int n4) {
        }

        public void onItemsRemoved(RecyclerView recyclerView, int n2, int n3) {
        }

        public void onItemsUpdated(RecyclerView recyclerView, int n2, int n3) {
        }

        public void onItemsUpdated(RecyclerView recyclerView, int n2, int n3, Object object) {
            this.onItemsUpdated(recyclerView, n2, n3);
        }

        public void onLayoutChildren(Recycler recycler, State state) {
            Log.e((String)"RecyclerView", (String)"You must override onLayoutChildren(Recycler recycler, State state) ");
        }

        public void onMeasure(Recycler recycler, State state, int n2, int n3) {
            this.mRecyclerView.defaultOnMeasure(n2, n3);
        }

        public boolean onRequestChildFocus(RecyclerView recyclerView, State state, View view, View view2) {
            return this.onRequestChildFocus(recyclerView, view, view2);
        }

        @Deprecated
        public boolean onRequestChildFocus(RecyclerView recyclerView, View view, View view2) {
            return this.isSmoothScrolling() || recyclerView.isComputingLayout();
        }

        public void onRestoreInstanceState(Parcelable parcelable) {
        }

        public Parcelable onSaveInstanceState() {
            return null;
        }

        public void onScrollStateChanged(int n2) {
        }

        boolean performAccessibilityAction(int n2, Bundle bundle) {
            return this.performAccessibilityAction(this.mRecyclerView.mRecycler, this.mRecyclerView.mState, n2, bundle);
        }

        /*
         * Enabled aggressive block sorting
         */
        public boolean performAccessibilityAction(Recycler recycler, State state, int n2, Bundle bundle) {
            int n3;
            block10: {
                block9: {
                    if (this.mRecyclerView == null) break block9;
                    int n4 = 0;
                    int n5 = 0;
                    int n6 = 0;
                    n3 = 0;
                    switch (n2) {
                        default: {
                            n2 = n6;
                            break;
                        }
                        case 8192: {
                            n6 = n4;
                            if (ViewCompat.canScrollVertically((View)this.mRecyclerView, -1)) {
                                n6 = -(this.getHeight() - this.getPaddingTop() - this.getPaddingBottom());
                            }
                            n2 = n6;
                            if (!ViewCompat.canScrollHorizontally((View)this.mRecyclerView, -1)) break;
                            n3 = -(this.getWidth() - this.getPaddingLeft() - this.getPaddingRight());
                            n2 = n6;
                            break;
                        }
                        case 4096: {
                            n6 = n5;
                            if (ViewCompat.canScrollVertically((View)this.mRecyclerView, 1)) {
                                n6 = this.getHeight() - this.getPaddingTop() - this.getPaddingBottom();
                            }
                            n2 = n6;
                            if (!ViewCompat.canScrollHorizontally((View)this.mRecyclerView, 1)) break;
                            n3 = this.getWidth() - this.getPaddingLeft() - this.getPaddingRight();
                            n2 = n6;
                        }
                    }
                    if (n2 != 0 || n3 != 0) break block10;
                }
                return false;
            }
            this.mRecyclerView.scrollBy(n3, n2);
            return true;
        }

        public boolean performAccessibilityActionForItem(Recycler recycler, State state, View view, int n2, Bundle bundle) {
            return false;
        }

        boolean performAccessibilityActionForItem(View view, int n2, Bundle bundle) {
            return this.performAccessibilityActionForItem(this.mRecyclerView.mRecycler, this.mRecyclerView.mState, view, n2, bundle);
        }

        public void postOnAnimation(Runnable runnable) {
            if (this.mRecyclerView != null) {
                ViewCompat.postOnAnimation((View)this.mRecyclerView, runnable);
            }
        }

        public void removeAllViews() {
            for (int i2 = this.getChildCount() - 1; i2 >= 0; --i2) {
                this.mChildHelper.removeViewAt(i2);
            }
        }

        public void removeAndRecycleAllViews(Recycler recycler) {
            for (int i2 = this.getChildCount() - 1; i2 >= 0; --i2) {
                if (RecyclerView.getChildViewHolderInt(this.getChildAt(i2)).shouldIgnore()) continue;
                this.removeAndRecycleViewAt(i2, recycler);
            }
        }

        /*
         * Enabled aggressive block sorting
         */
        void removeAndRecycleScrapInt(Recycler recycler) {
            int n2 = recycler.getScrapCount();
            for (int i2 = n2 - 1; i2 >= 0; --i2) {
                View view = recycler.getScrapViewAt(i2);
                ViewHolder viewHolder = RecyclerView.getChildViewHolderInt(view);
                if (viewHolder.shouldIgnore()) continue;
                viewHolder.setIsRecyclable(false);
                if (viewHolder.isTmpDetached()) {
                    this.mRecyclerView.removeDetachedView(view, false);
                }
                if (this.mRecyclerView.mItemAnimator != null) {
                    this.mRecyclerView.mItemAnimator.endAnimation(viewHolder);
                }
                viewHolder.setIsRecyclable(true);
                recycler.quickRecycleScrapView(view);
            }
            recycler.clearScrap();
            if (n2 > 0) {
                this.mRecyclerView.invalidate();
            }
        }

        public void removeAndRecycleView(View view, Recycler recycler) {
            this.removeView(view);
            recycler.recycleView(view);
        }

        public void removeAndRecycleViewAt(int n2, Recycler recycler) {
            View view = this.getChildAt(n2);
            this.removeViewAt(n2);
            recycler.recycleView(view);
        }

        public boolean removeCallbacks(Runnable runnable) {
            if (this.mRecyclerView != null) {
                return this.mRecyclerView.removeCallbacks(runnable);
            }
            return false;
        }

        public void removeDetachedView(View view) {
            this.mRecyclerView.removeDetachedView(view, false);
        }

        public void removeView(View view) {
            this.mChildHelper.removeView(view);
        }

        public void removeViewAt(int n2) {
            if (this.getChildAt(n2) != null) {
                this.mChildHelper.removeViewAt(n2);
            }
        }

        /*
         * Enabled aggressive block sorting
         */
        public boolean requestChildRectangleOnScreen(RecyclerView recyclerView, View view, Rect rect, boolean bl2) {
            int n2 = this.getPaddingLeft();
            int n3 = this.getPaddingTop();
            int n4 = this.getWidth() - this.getPaddingRight();
            int n5 = this.getHeight();
            int n6 = this.getPaddingBottom();
            int n7 = view.getLeft() + rect.left - view.getScrollX();
            int n8 = view.getTop() + rect.top - view.getScrollY();
            int n9 = n7 + rect.width();
            int n10 = rect.height();
            int n11 = Math.min(0, n7 - n2);
            int n12 = Math.min(0, n8 - n3);
            int n13 = Math.max(0, n9 - n4);
            n5 = Math.max(0, n8 + n10 - (n5 - n6));
            if (this.getLayoutDirection() == 1) {
                n11 = n13 != 0 ? n13 : Math.max(n11, n9 - n4);
            } else if (n11 == 0) {
                n11 = Math.min(n7 - n2, n13);
            }
            if (n12 == 0) {
                n12 = Math.min(n8 - n3, n5);
            }
            if (n11 == 0) {
                if (n12 == 0) return false;
            }
            if (bl2) {
                recyclerView.scrollBy(n11, n12);
                return true;
            }
            recyclerView.smoothScrollBy(n11, n12);
            return true;
        }

        public void requestLayout() {
            if (this.mRecyclerView != null) {
                this.mRecyclerView.requestLayout();
            }
        }

        public void requestSimpleAnimationsInNextLayout() {
            this.mRequestedSimpleAnimations = true;
        }

        public int scrollHorizontallyBy(int n2, Recycler recycler, State state) {
            return 0;
        }

        public void scrollToPosition(int n2) {
        }

        public int scrollVerticallyBy(int n2, Recycler recycler, State state) {
            return 0;
        }

        public void setAutoMeasureEnabled(boolean bl2) {
            this.mAutoMeasure = bl2;
        }

        void setExactMeasureSpecsFrom(RecyclerView recyclerView) {
            this.setMeasureSpecs(View.MeasureSpec.makeMeasureSpec((int)recyclerView.getWidth(), (int)0x40000000), View.MeasureSpec.makeMeasureSpec((int)recyclerView.getHeight(), (int)0x40000000));
        }

        void setMeasureSpecs(int n2, int n3) {
            this.mWidth = View.MeasureSpec.getSize((int)n2);
            this.mWidthMode = View.MeasureSpec.getMode((int)n2);
            if (this.mWidthMode == 0 && !ALLOW_SIZE_IN_UNSPECIFIED_SPEC) {
                this.mWidth = 0;
            }
            this.mHeight = View.MeasureSpec.getSize((int)n3);
            this.mHeightMode = View.MeasureSpec.getMode((int)n3);
            if (this.mHeightMode == 0 && !ALLOW_SIZE_IN_UNSPECIFIED_SPEC) {
                this.mHeight = 0;
            }
        }

        public void setMeasuredDimension(int n2, int n3) {
            this.mRecyclerView.setMeasuredDimension(n2, n3);
        }

        public void setMeasuredDimension(Rect rect, int n2, int n3) {
            int n4 = rect.width();
            int n5 = this.getPaddingLeft();
            int n6 = this.getPaddingRight();
            int n7 = rect.height();
            int n8 = this.getPaddingTop();
            int n9 = this.getPaddingBottom();
            this.setMeasuredDimension(LayoutManager.chooseSize(n2, n4 + n5 + n6, this.getMinimumWidth()), LayoutManager.chooseSize(n3, n7 + n8 + n9, this.getMinimumHeight()));
        }

        void setMeasuredDimensionFromChildren(int n2, int n3) {
            int n4 = this.getChildCount();
            if (n4 == 0) {
                this.mRecyclerView.defaultOnMeasure(n2, n3);
                return;
            }
            int n5 = Integer.MAX_VALUE;
            int n6 = Integer.MAX_VALUE;
            int n7 = Integer.MIN_VALUE;
            int n8 = Integer.MIN_VALUE;
            for (int i2 = 0; i2 < n4; ++i2) {
                View view = this.getChildAt(i2);
                LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
                int n9 = this.getDecoratedLeft(view) - layoutParams.leftMargin;
                int n10 = this.getDecoratedRight(view) + layoutParams.rightMargin;
                int n11 = this.getDecoratedTop(view) - layoutParams.topMargin;
                int n12 = this.getDecoratedBottom(view) + layoutParams.bottomMargin;
                int n13 = n5;
                if (n9 < n5) {
                    n13 = n9;
                }
                n5 = n7;
                if (n10 > n7) {
                    n5 = n10;
                }
                n10 = n6;
                if (n11 < n6) {
                    n10 = n11;
                }
                n6 = n8;
                if (n12 > n8) {
                    n6 = n12;
                }
                n7 = n5;
                n8 = n6;
                n5 = n13;
                n6 = n10;
            }
            this.mRecyclerView.mTempRect.set(n5, n6, n7, n8);
            this.setMeasuredDimension(this.mRecyclerView.mTempRect, n2, n3);
        }

        public void setMeasurementCacheEnabled(boolean bl2) {
            this.mMeasurementCacheEnabled = bl2;
        }

        /*
         * Enabled aggressive block sorting
         */
        void setRecyclerView(RecyclerView recyclerView) {
            if (recyclerView == null) {
                this.mRecyclerView = null;
                this.mChildHelper = null;
                this.mWidth = 0;
                this.mHeight = 0;
            } else {
                this.mRecyclerView = recyclerView;
                this.mChildHelper = recyclerView.mChildHelper;
                this.mWidth = recyclerView.getWidth();
                this.mHeight = recyclerView.getHeight();
            }
            this.mWidthMode = 0x40000000;
            this.mHeightMode = 0x40000000;
        }

        boolean shouldMeasureChild(View view, int n2, int n3, LayoutParams layoutParams) {
            return view.isLayoutRequested() || !this.mMeasurementCacheEnabled || !LayoutManager.isMeasurementUpToDate(view.getWidth(), n2, layoutParams.width) || !LayoutManager.isMeasurementUpToDate(view.getHeight(), n3, layoutParams.height);
        }

        boolean shouldMeasureTwice() {
            return false;
        }

        boolean shouldReMeasureChild(View view, int n2, int n3, LayoutParams layoutParams) {
            return !this.mMeasurementCacheEnabled || !LayoutManager.isMeasurementUpToDate(view.getMeasuredWidth(), n2, layoutParams.width) || !LayoutManager.isMeasurementUpToDate(view.getMeasuredHeight(), n3, layoutParams.height);
        }

        public void smoothScrollToPosition(RecyclerView recyclerView, State state, int n2) {
            Log.e((String)"RecyclerView", (String)"You must override smoothScrollToPosition to support smooth scrolling");
        }

        public void startSmoothScroll(SmoothScroller smoothScroller) {
            if (this.mSmoothScroller != null && smoothScroller != this.mSmoothScroller && this.mSmoothScroller.isRunning()) {
                this.mSmoothScroller.stop();
            }
            this.mSmoothScroller = smoothScroller;
            this.mSmoothScroller.start(this.mRecyclerView, this);
        }

        public void stopIgnoringView(View object) {
            object = RecyclerView.getChildViewHolderInt((View)object);
            ((ViewHolder)object).stopIgnoring();
            ((ViewHolder)object).resetInternal();
            ((ViewHolder)object).addFlags(4);
        }

        void stopSmoothScroller() {
            if (this.mSmoothScroller != null) {
                this.mSmoothScroller.stop();
            }
        }

        public boolean supportsPredictiveItemAnimations() {
            return false;
        }

        public static class Properties {
            public int orientation;
            public boolean reverseLayout;
            public int spanCount;
            public boolean stackFromEnd;
        }
    }

    public static class LayoutParams
    extends ViewGroup.MarginLayoutParams {
        final Rect mDecorInsets = new Rect();
        boolean mInsetsDirty = true;
        boolean mPendingInvalidate = false;
        ViewHolder mViewHolder;

        public LayoutParams(int n2, int n3) {
            super(n2, n3);
        }

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        public LayoutParams(LayoutParams layoutParams) {
            super((ViewGroup.LayoutParams)layoutParams);
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public LayoutParams(ViewGroup.MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
        }

        public int getViewAdapterPosition() {
            return this.mViewHolder.getAdapterPosition();
        }

        public int getViewLayoutPosition() {
            return this.mViewHolder.getLayoutPosition();
        }

        public int getViewPosition() {
            return this.mViewHolder.getPosition();
        }

        public boolean isItemChanged() {
            return this.mViewHolder.isUpdated();
        }

        public boolean isItemRemoved() {
            return this.mViewHolder.isRemoved();
        }

        public boolean isViewInvalid() {
            return this.mViewHolder.isInvalid();
        }

        public boolean viewNeedsUpdate() {
            return this.mViewHolder.needsUpdate();
        }
    }

    public static interface OnChildAttachStateChangeListener {
        public void onChildViewAttachedToWindow(View var1);

        public void onChildViewDetachedFromWindow(View var1);
    }

    public static interface OnItemTouchListener {
        public boolean onInterceptTouchEvent(RecyclerView var1, MotionEvent var2);

        public void onRequestDisallowInterceptTouchEvent(boolean var1);

        public void onTouchEvent(RecyclerView var1, MotionEvent var2);
    }

    public static abstract class OnScrollListener {
        public void onScrollStateChanged(RecyclerView recyclerView, int n2) {
        }

        public void onScrolled(RecyclerView recyclerView, int n2, int n3) {
        }
    }

    public static class RecycledViewPool {
        private static final int DEFAULT_MAX_SCRAP = 5;
        private int mAttachCount = 0;
        private SparseIntArray mMaxScrap;
        private SparseArray<ArrayList<ViewHolder>> mScrap = new SparseArray();

        public RecycledViewPool() {
            this.mMaxScrap = new SparseIntArray();
        }

        private ArrayList<ViewHolder> getScrapHeapForType(int n2) {
            ArrayList arrayList;
            ArrayList arrayList2 = arrayList = (ArrayList)this.mScrap.get(n2);
            if (arrayList == null) {
                arrayList = new ArrayList();
                this.mScrap.put(n2, arrayList);
                arrayList2 = arrayList;
                if (this.mMaxScrap.indexOfKey(n2) < 0) {
                    this.mMaxScrap.put(n2, 5);
                    arrayList2 = arrayList;
                }
            }
            return arrayList2;
        }

        void attach(Adapter adapter) {
            ++this.mAttachCount;
        }

        public void clear() {
            this.mScrap.clear();
        }

        void detach() {
            --this.mAttachCount;
        }

        public ViewHolder getRecycledView(int n2) {
            ArrayList arrayList = (ArrayList)this.mScrap.get(n2);
            if (arrayList != null && !arrayList.isEmpty()) {
                n2 = arrayList.size() - 1;
                ViewHolder viewHolder = (ViewHolder)arrayList.get(n2);
                arrayList.remove(n2);
                return viewHolder;
            }
            return null;
        }

        void onAdapterChanged(Adapter adapter, Adapter adapter2, boolean bl2) {
            if (adapter != null) {
                this.detach();
            }
            if (!bl2 && this.mAttachCount == 0) {
                this.clear();
            }
            if (adapter2 != null) {
                this.attach(adapter2);
            }
        }

        public void putRecycledView(ViewHolder viewHolder) {
            int n2 = viewHolder.getItemViewType();
            ArrayList<ViewHolder> arrayList = this.getScrapHeapForType(n2);
            if (this.mMaxScrap.get(n2) <= arrayList.size()) {
                return;
            }
            viewHolder.resetInternal();
            arrayList.add(viewHolder);
        }

        public void setMaxRecycledViews(int n2, int n3) {
            this.mMaxScrap.put(n2, n3);
            ArrayList arrayList = (ArrayList)this.mScrap.get(n2);
            if (arrayList != null) {
                while (arrayList.size() > n3) {
                    arrayList.remove(arrayList.size() - 1);
                }
            }
        }

        int size() {
            int n2 = 0;
            for (int i2 = 0; i2 < this.mScrap.size(); ++i2) {
                ArrayList arrayList = (ArrayList)this.mScrap.valueAt(i2);
                int n3 = n2;
                if (arrayList != null) {
                    n3 = n2 + arrayList.size();
                }
                n2 = n3;
            }
            return n2;
        }
    }

    public final class Recycler {
        private static final int DEFAULT_CACHE_SIZE = 2;
        final ArrayList<ViewHolder> mAttachedScrap = new ArrayList();
        final ArrayList<ViewHolder> mCachedViews = new ArrayList();
        private ArrayList<ViewHolder> mChangedScrap = null;
        private RecycledViewPool mRecyclerPool;
        private final List<ViewHolder> mUnmodifiableAttachedScrap = Collections.unmodifiableList(this.mAttachedScrap);
        private ViewCacheExtension mViewCacheExtension;
        private int mViewCacheMax = 2;

        private void attachAccessibilityDelegate(View view) {
            if (RecyclerView.this.isAccessibilityEnabled()) {
                if (ViewCompat.getImportantForAccessibility(view) == 0) {
                    ViewCompat.setImportantForAccessibility(view, 1);
                }
                if (!ViewCompat.hasAccessibilityDelegate(view)) {
                    ViewCompat.setAccessibilityDelegate(view, RecyclerView.this.mAccessibilityDelegate.getItemDelegate());
                }
            }
        }

        private void invalidateDisplayListInt(ViewHolder viewHolder) {
            if (viewHolder.itemView instanceof ViewGroup) {
                this.invalidateDisplayListInt((ViewGroup)viewHolder.itemView, false);
            }
        }

        private void invalidateDisplayListInt(ViewGroup viewGroup, boolean bl2) {
            int n2;
            for (n2 = viewGroup.getChildCount() - 1; n2 >= 0; --n2) {
                View view = viewGroup.getChildAt(n2);
                if (!(view instanceof ViewGroup)) continue;
                this.invalidateDisplayListInt((ViewGroup)view, true);
            }
            if (!bl2) {
                return;
            }
            if (viewGroup.getVisibility() == 4) {
                viewGroup.setVisibility(0);
                viewGroup.setVisibility(4);
                return;
            }
            n2 = viewGroup.getVisibility();
            viewGroup.setVisibility(4);
            viewGroup.setVisibility(n2);
        }

        void addViewHolderToRecycledViewPool(ViewHolder viewHolder) {
            ViewCompat.setAccessibilityDelegate(viewHolder.itemView, null);
            this.dispatchViewRecycled(viewHolder);
            viewHolder.mOwnerRecyclerView = null;
            this.getRecycledViewPool().putRecycledView(viewHolder);
        }

        /*
         * WARNING - void declaration
         * Enabled aggressive block sorting
         */
        public void bindViewToPosition(View object, int n2) {
            ViewGroup.LayoutParams layoutParams;
            void var2_7;
            boolean bl2 = true;
            ViewHolder viewHolder = RecyclerView.getChildViewHolderInt(object);
            if (viewHolder == null) {
                throw new IllegalArgumentException("The view does not have a ViewHolder. You cannot pass arbitrary views to this method, they should be created by the Adapter");
            }
            int n3 = RecyclerView.this.mAdapterHelper.findPositionOffset((int)var2_7);
            if (n3 < 0 || n3 >= RecyclerView.this.mAdapter.getItemCount()) {
                throw new IndexOutOfBoundsException("Inconsistency detected. Invalid item position " + (int)var2_7 + "(offset:" + n3 + ")." + "state:" + RecyclerView.this.mState.getItemCount());
            }
            viewHolder.mOwnerRecyclerView = RecyclerView.this;
            RecyclerView.this.mAdapter.bindViewHolder(viewHolder, n3);
            this.attachAccessibilityDelegate((View)object);
            if (RecyclerView.this.mState.isPreLayout()) {
                viewHolder.mPreLayoutPosition = var2_7;
            }
            if ((layoutParams = viewHolder.itemView.getLayoutParams()) == null) {
                LayoutParams layoutParams2 = (LayoutParams)RecyclerView.this.generateDefaultLayoutParams();
                viewHolder.itemView.setLayoutParams((ViewGroup.LayoutParams)layoutParams2);
            } else if (!RecyclerView.this.checkLayoutParams(layoutParams)) {
                LayoutParams layoutParams3 = (LayoutParams)RecyclerView.this.generateLayoutParams(layoutParams);
                viewHolder.itemView.setLayoutParams((ViewGroup.LayoutParams)layoutParams3);
            } else {
                LayoutParams layoutParams4 = (LayoutParams)layoutParams;
            }
            var1_4.mInsetsDirty = true;
            var1_4.mViewHolder = viewHolder;
            if (viewHolder.itemView.getParent() != null) {
                bl2 = false;
            }
            var1_4.mPendingInvalidate = bl2;
        }

        public void clear() {
            this.mAttachedScrap.clear();
            this.recycleAndClearCachedViews();
        }

        void clearOldPositions() {
            int n2;
            int n3 = this.mCachedViews.size();
            for (n2 = 0; n2 < n3; ++n2) {
                this.mCachedViews.get(n2).clearOldPosition();
            }
            n3 = this.mAttachedScrap.size();
            for (n2 = 0; n2 < n3; ++n2) {
                this.mAttachedScrap.get(n2).clearOldPosition();
            }
            if (this.mChangedScrap != null) {
                n3 = this.mChangedScrap.size();
                for (n2 = 0; n2 < n3; ++n2) {
                    this.mChangedScrap.get(n2).clearOldPosition();
                }
            }
        }

        void clearScrap() {
            this.mAttachedScrap.clear();
            if (this.mChangedScrap != null) {
                this.mChangedScrap.clear();
            }
        }

        public int convertPreLayoutPositionToPostLayout(int n2) {
            if (n2 < 0 || n2 >= RecyclerView.this.mState.getItemCount()) {
                throw new IndexOutOfBoundsException("invalid position " + n2 + ". State " + "item count is " + RecyclerView.this.mState.getItemCount());
            }
            if (!RecyclerView.this.mState.isPreLayout()) {
                return n2;
            }
            return RecyclerView.this.mAdapterHelper.findPositionOffset(n2);
        }

        void dispatchViewRecycled(ViewHolder viewHolder) {
            if (RecyclerView.this.mRecyclerListener != null) {
                RecyclerView.this.mRecyclerListener.onViewRecycled(viewHolder);
            }
            if (RecyclerView.this.mAdapter != null) {
                RecyclerView.this.mAdapter.onViewRecycled(viewHolder);
            }
            if (RecyclerView.this.mState != null) {
                RecyclerView.this.mViewInfoStore.removeViewHolder(viewHolder);
            }
        }

        ViewHolder getChangedScrapViewForPosition(int n2) {
            ViewHolder viewHolder;
            int n3;
            if (this.mChangedScrap == null || (n3 = this.mChangedScrap.size()) == 0) {
                return null;
            }
            for (int i2 = 0; i2 < n3; ++i2) {
                viewHolder = this.mChangedScrap.get(i2);
                if (viewHolder.wasReturnedFromScrap() || viewHolder.getLayoutPosition() != n2) continue;
                viewHolder.addFlags(32);
                return viewHolder;
            }
            if (RecyclerView.this.mAdapter.hasStableIds() && (n2 = RecyclerView.this.mAdapterHelper.findPositionOffset(n2)) > 0 && n2 < RecyclerView.this.mAdapter.getItemCount()) {
                long l2 = RecyclerView.this.mAdapter.getItemId(n2);
                for (n2 = 0; n2 < n3; ++n2) {
                    viewHolder = this.mChangedScrap.get(n2);
                    if (viewHolder.wasReturnedFromScrap() || viewHolder.getItemId() != l2) continue;
                    viewHolder.addFlags(32);
                    return viewHolder;
                }
            }
            return null;
        }

        RecycledViewPool getRecycledViewPool() {
            if (this.mRecyclerPool == null) {
                this.mRecyclerPool = new RecycledViewPool();
            }
            return this.mRecyclerPool;
        }

        int getScrapCount() {
            return this.mAttachedScrap.size();
        }

        public List<ViewHolder> getScrapList() {
            return this.mUnmodifiableAttachedScrap;
        }

        View getScrapViewAt(int n2) {
            return this.mAttachedScrap.get((int)n2).itemView;
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        ViewHolder getScrapViewForId(long l2, int n2, boolean bl2) {
            ViewHolder viewHolder;
            ViewHolder viewHolder2;
            int n3;
            for (n3 = this.mAttachedScrap.size() - 1; n3 >= 0; --n3) {
                viewHolder2 = this.mAttachedScrap.get(n3);
                if (viewHolder2.getItemId() != l2 || viewHolder2.wasReturnedFromScrap()) continue;
                if (n2 == viewHolder2.getItemViewType()) {
                    viewHolder2.addFlags(32);
                    viewHolder = viewHolder2;
                    if (!viewHolder2.isRemoved()) return viewHolder;
                    viewHolder = viewHolder2;
                    if (RecyclerView.this.mState.isPreLayout()) return viewHolder;
                    viewHolder2.setFlags(2, 14);
                    return viewHolder2;
                }
                if (bl2) continue;
                this.mAttachedScrap.remove(n3);
                RecyclerView.this.removeDetachedView(viewHolder2.itemView, false);
                this.quickRecycleScrapView(viewHolder2.itemView);
            }
            n3 = this.mCachedViews.size() - 1;
            while (n3 >= 0) {
                viewHolder2 = this.mCachedViews.get(n3);
                if (viewHolder2.getItemId() == l2) {
                    if (n2 == viewHolder2.getItemViewType()) {
                        viewHolder = viewHolder2;
                        if (bl2) return viewHolder;
                        this.mCachedViews.remove(n3);
                        return viewHolder2;
                    }
                    if (!bl2) {
                        this.recycleCachedViewAt(n3);
                    }
                }
                --n3;
            }
            return null;
        }

        /*
         * Enabled aggressive block sorting
         * Lifted jumps to return sites
         */
        ViewHolder getScrapViewForPosition(int n2, int n3, boolean bl2) {
            ViewHolder viewHolder;
            int n4;
            block7: {
                ViewHolder viewHolder2;
                int n5 = this.mAttachedScrap.size();
                n4 = 0;
                while (true) {
                    block9: {
                        block8: {
                            if (n4 >= n5) break block8;
                            viewHolder = this.mAttachedScrap.get(n4);
                            if (viewHolder.wasReturnedFromScrap() || viewHolder.getLayoutPosition() != n2 || viewHolder.isInvalid() || !RecyclerView.this.mState.mInPreLayout && viewHolder.isRemoved()) break block9;
                            if (n3 == -1 || viewHolder.getItemViewType() == n3) {
                                viewHolder.addFlags(32);
                                return viewHolder;
                            }
                            Log.e((String)RecyclerView.TAG, (String)("Scrap view for position " + n2 + " isn't dirty but has" + " wrong view type! (found " + viewHolder.getItemViewType() + " but expected " + n3 + ")"));
                        }
                        if (!bl2 && (viewHolder = RecyclerView.this.mChildHelper.findHiddenNonRemovedView(n2, n3)) != null) {
                            viewHolder2 = RecyclerView.getChildViewHolderInt((View)viewHolder);
                            RecyclerView.this.mChildHelper.unhide((View)viewHolder);
                            n2 = RecyclerView.this.mChildHelper.indexOfChild((View)viewHolder);
                            if (n2 != -1) break;
                            throw new IllegalStateException("layout index should not be -1 after unhiding a view:" + viewHolder2);
                        }
                        break block7;
                    }
                    ++n4;
                }
                RecyclerView.this.mChildHelper.detachViewFromParent(n2);
                this.scrapView((View)viewHolder);
                viewHolder2.addFlags(8224);
                return viewHolder2;
            }
            n4 = this.mCachedViews.size();
            n3 = 0;
            while (n3 < n4) {
                ViewHolder viewHolder3 = this.mCachedViews.get(n3);
                if (!viewHolder3.isInvalid() && viewHolder3.getLayoutPosition() == n2) {
                    viewHolder = viewHolder3;
                    if (bl2) return viewHolder;
                    this.mCachedViews.remove(n3);
                    return viewHolder3;
                }
                ++n3;
            }
            return null;
        }

        public View getViewForPosition(int n2) {
            return this.getViewForPosition(n2, false);
        }

        /*
         * WARNING - void declaration
         * Enabled aggressive block sorting
         */
        View getViewForPosition(int n2, boolean bl2) {
            if (n2 < 0 || n2 >= RecyclerView.this.mState.getItemCount()) {
                throw new IndexOutOfBoundsException("Invalid item position " + n2 + "(" + n2 + "). Item count:" + RecyclerView.this.mState.getItemCount());
            }
            int n3 = 0;
            Object object = null;
            if (RecyclerView.this.mState.isPreLayout()) {
                object = this.getChangedScrapViewForPosition(n2);
                n3 = object != null ? 1 : 0;
            }
            int n4 = n3;
            Object object2 = object;
            if (object == null) {
                object = this.getScrapViewForPosition(n2, -1, bl2);
                n4 = n3;
                object2 = object;
                if (object != null) {
                    if (!this.validateViewHolderForOffsetPosition((ViewHolder)object)) {
                        if (!bl2) {
                            ((ViewHolder)object).addFlags(4);
                            if (((ViewHolder)object).isScrap()) {
                                RecyclerView.this.removeDetachedView(((ViewHolder)object).itemView, false);
                                ((ViewHolder)object).unScrap();
                            } else if (((ViewHolder)object).wasReturnedFromScrap()) {
                                ((ViewHolder)object).clearReturnedFromScrapFlag();
                            }
                            this.recycleViewHolderInternal((ViewHolder)object);
                        }
                        object2 = null;
                        n4 = n3;
                    } else {
                        n4 = 1;
                        object2 = object;
                    }
                }
            }
            int n5 = n4;
            object = object2;
            if (object2 == null) {
                void var9_14;
                n5 = RecyclerView.this.mAdapterHelper.findPositionOffset(n2);
                if (n5 < 0 || n5 >= RecyclerView.this.mAdapter.getItemCount()) {
                    throw new IndexOutOfBoundsException("Inconsistency detected. Invalid item position " + n2 + "(offset:" + n5 + ")." + "state:" + RecyclerView.this.mState.getItemCount());
                }
                int n6 = RecyclerView.this.mAdapter.getItemViewType(n5);
                n3 = n4;
                object = object2;
                if (RecyclerView.this.mAdapter.hasStableIds()) {
                    object2 = this.getScrapViewForId(RecyclerView.this.mAdapter.getItemId(n5), n6, bl2);
                    n3 = n4;
                    object = object2;
                    if (object2 != null) {
                        ((ViewHolder)object2).mPosition = n5;
                        n3 = 1;
                        object = object2;
                    }
                }
                object2 = object;
                if (object == null) {
                    object2 = object;
                    if (this.mViewCacheExtension != null) {
                        View view = this.mViewCacheExtension.getViewForPositionAndType(this, n2, n6);
                        object2 = object;
                        if (view != null) {
                            object = RecyclerView.this.getChildViewHolder(view);
                            if (object == null) {
                                throw new IllegalArgumentException("getViewForPositionAndType returned a view which does not have a ViewHolder");
                            }
                            object2 = object;
                            if (((ViewHolder)object).shouldIgnore()) {
                                throw new IllegalArgumentException("getViewForPositionAndType returned a view that is ignored. You must call stopIgnoring before returning this view.");
                            }
                        }
                    }
                }
                Object object3 = object2;
                if (object2 == null) {
                    Object object4 = object2 = this.getRecycledViewPool().getRecycledView(n6);
                    if (object2 != null) {
                        ((ViewHolder)object2).resetInternal();
                        Object object5 = object2;
                        if (FORCE_INVALIDATE_DISPLAY_LIST) {
                            this.invalidateDisplayListInt((ViewHolder)object2);
                            Object object6 = object2;
                        }
                    }
                }
                n5 = n3;
                object = var9_14;
                if (var9_14 == null) {
                    object = RecyclerView.this.mAdapter.createViewHolder(RecyclerView.this, n6);
                    n5 = n3;
                }
            }
            if (n5 != 0 && !RecyclerView.this.mState.isPreLayout() && ((ViewHolder)object).hasAnyOfTheFlags(8192)) {
                ((ViewHolder)object).setFlags(0, 8192);
                if (RecyclerView.this.mState.mRunSimpleAnimations) {
                    n4 = ItemAnimator.buildAdapterChangeFlagsForAnimations((ViewHolder)object);
                    object2 = RecyclerView.this.mItemAnimator.recordPreLayoutInformation(RecyclerView.this.mState, (ViewHolder)object, n4 | 0x1000, ((ViewHolder)object).getUnmodifiedPayloads());
                    RecyclerView.this.recordAnimationInfoIfBouncedHiddenView((ViewHolder)object, (ItemAnimator.ItemHolderInfo)object2);
                }
            }
            n4 = 0;
            if (RecyclerView.this.mState.isPreLayout() && ((ViewHolder)object).isBound()) {
                ((ViewHolder)object).mPreLayoutPosition = n2;
            } else if (!((ViewHolder)object).isBound() || ((ViewHolder)object).needsUpdate() || ((ViewHolder)object).isInvalid()) {
                n4 = RecyclerView.this.mAdapterHelper.findPositionOffset(n2);
                ((ViewHolder)object).mOwnerRecyclerView = RecyclerView.this;
                RecyclerView.this.mAdapter.bindViewHolder(object, n4);
                this.attachAccessibilityDelegate(((ViewHolder)object).itemView);
                n4 = n3 = 1;
                if (RecyclerView.this.mState.isPreLayout()) {
                    ((ViewHolder)object).mPreLayoutPosition = n2;
                    n4 = n3;
                }
            }
            if ((object2 = ((ViewHolder)object).itemView.getLayoutParams()) == null) {
                object2 = (LayoutParams)RecyclerView.this.generateDefaultLayoutParams();
                ((ViewHolder)object).itemView.setLayoutParams((ViewGroup.LayoutParams)object2);
            } else if (!RecyclerView.this.checkLayoutParams((ViewGroup.LayoutParams)object2)) {
                object2 = (LayoutParams)RecyclerView.this.generateLayoutParams((ViewGroup.LayoutParams)object2);
                ((ViewHolder)object).itemView.setLayoutParams((ViewGroup.LayoutParams)object2);
            } else {
                object2 = (LayoutParams)((Object)object2);
            }
            ((LayoutParams)object2).mViewHolder = object;
            bl2 = n5 != 0 && n4 != 0;
            ((LayoutParams)object2).mPendingInvalidate = bl2;
            return ((ViewHolder)object).itemView;
        }

        void markItemDecorInsetsDirty() {
            int n2 = this.mCachedViews.size();
            for (int i2 = 0; i2 < n2; ++i2) {
                LayoutParams layoutParams = (LayoutParams)this.mCachedViews.get((int)i2).itemView.getLayoutParams();
                if (layoutParams == null) continue;
                layoutParams.mInsetsDirty = true;
            }
        }

        void markKnownViewsInvalid() {
            if (RecyclerView.this.mAdapter != null && RecyclerView.this.mAdapter.hasStableIds()) {
                int n2 = this.mCachedViews.size();
                for (int i2 = 0; i2 < n2; ++i2) {
                    ViewHolder viewHolder = this.mCachedViews.get(i2);
                    if (viewHolder == null) continue;
                    viewHolder.addFlags(6);
                    viewHolder.addChangePayload(null);
                }
            } else {
                this.recycleAndClearCachedViews();
            }
        }

        void offsetPositionRecordsForInsert(int n2, int n3) {
            int n4 = this.mCachedViews.size();
            for (int i2 = 0; i2 < n4; ++i2) {
                ViewHolder viewHolder = this.mCachedViews.get(i2);
                if (viewHolder == null || viewHolder.mPosition < n2) continue;
                viewHolder.offsetPosition(n3, true);
            }
        }

        /*
         * Enabled aggressive block sorting
         */
        void offsetPositionRecordsForMove(int n2, int n3) {
            int n4;
            int n5;
            int n6;
            if (n2 < n3) {
                n6 = n2;
                n5 = n3;
                n4 = -1;
            } else {
                n6 = n3;
                n5 = n2;
                n4 = 1;
            }
            int n7 = this.mCachedViews.size();
            int n8 = 0;
            while (n8 < n7) {
                ViewHolder viewHolder = this.mCachedViews.get(n8);
                if (viewHolder != null && viewHolder.mPosition >= n6 && viewHolder.mPosition <= n5) {
                    if (viewHolder.mPosition == n2) {
                        viewHolder.offsetPosition(n3 - n2, false);
                    } else {
                        viewHolder.offsetPosition(n4, false);
                    }
                }
                ++n8;
            }
            return;
        }

        /*
         * Enabled aggressive block sorting
         */
        void offsetPositionRecordsForRemove(int n2, int n3, boolean bl2) {
            int n4 = this.mCachedViews.size() - 1;
            while (n4 >= 0) {
                ViewHolder viewHolder = this.mCachedViews.get(n4);
                if (viewHolder != null) {
                    if (viewHolder.mPosition >= n2 + n3) {
                        viewHolder.offsetPosition(-n3, bl2);
                    } else if (viewHolder.mPosition >= n2) {
                        viewHolder.addFlags(8);
                        this.recycleCachedViewAt(n4);
                    }
                }
                --n4;
            }
            return;
        }

        void onAdapterChanged(Adapter adapter, Adapter adapter2, boolean bl2) {
            this.clear();
            this.getRecycledViewPool().onAdapterChanged(adapter, adapter2, bl2);
        }

        void quickRecycleScrapView(View object) {
            object = RecyclerView.getChildViewHolderInt((View)object);
            ViewHolder.access$5002((ViewHolder)object, null);
            ViewHolder.access$5102((ViewHolder)object, false);
            ((ViewHolder)object).clearReturnedFromScrapFlag();
            this.recycleViewHolderInternal((ViewHolder)object);
        }

        void recycleAndClearCachedViews() {
            for (int i2 = this.mCachedViews.size() - 1; i2 >= 0; --i2) {
                this.recycleCachedViewAt(i2);
            }
            this.mCachedViews.clear();
        }

        void recycleCachedViewAt(int n2) {
            this.addViewHolderToRecycledViewPool(this.mCachedViews.get(n2));
            this.mCachedViews.remove(n2);
        }

        /*
         * Enabled aggressive block sorting
         */
        public void recycleView(View view) {
            ViewHolder viewHolder = RecyclerView.getChildViewHolderInt(view);
            if (viewHolder.isTmpDetached()) {
                RecyclerView.this.removeDetachedView(view, false);
            }
            if (viewHolder.isScrap()) {
                viewHolder.unScrap();
            } else if (viewHolder.wasReturnedFromScrap()) {
                viewHolder.clearReturnedFromScrapFlag();
            }
            this.recycleViewHolderInternal(viewHolder);
        }

        /*
         * Enabled aggressive block sorting
         */
        void recycleViewHolderInternal(ViewHolder viewHolder) {
            boolean bl2;
            int n2;
            boolean bl3;
            block12: {
                boolean bl4;
                int n3;
                int n4;
                block11: {
                    bl3 = true;
                    if (viewHolder.isScrap() || viewHolder.itemView.getParent() != null) {
                        StringBuilder stringBuilder = new StringBuilder().append("Scrapped or attached views may not be recycled. isScrap:").append(viewHolder.isScrap()).append(" isAttached:");
                        if (viewHolder.itemView.getParent() != null) {
                            throw new IllegalArgumentException(stringBuilder.append(bl3).toString());
                        }
                        bl3 = false;
                        throw new IllegalArgumentException(stringBuilder.append(bl3).toString());
                    }
                    if (viewHolder.isTmpDetached()) {
                        throw new IllegalArgumentException("Tmp detached view should be removed from RecyclerView before it can be recycled: " + viewHolder);
                    }
                    if (viewHolder.shouldIgnore()) {
                        throw new IllegalArgumentException("Trying to recycle an ignored view holder. You should first call stopIgnoringView(view) before calling recycle.");
                    }
                    bl3 = viewHolder.doesTransientStatePreventRecycling();
                    n4 = RecyclerView.this.mAdapter != null && bl3 && RecyclerView.this.mAdapter.onFailedToRecycleView(viewHolder) ? 1 : 0;
                    n2 = 0;
                    n3 = 0;
                    bl4 = false;
                    if (n4 != 0) break block11;
                    bl2 = bl4;
                    if (!viewHolder.isRecyclable()) break block12;
                }
                n4 = n3;
                if (!viewHolder.hasAnyOfTheFlags(14)) {
                    n2 = this.mCachedViews.size();
                    if (n2 == this.mViewCacheMax && n2 > 0) {
                        this.recycleCachedViewAt(0);
                    }
                    n4 = n3;
                    if (n2 < this.mViewCacheMax) {
                        this.mCachedViews.add(viewHolder);
                        n4 = 1;
                    }
                }
                n2 = n4;
                bl2 = bl4;
                if (n4 == 0) {
                    this.addViewHolderToRecycledViewPool(viewHolder);
                    bl2 = true;
                    n2 = n4;
                }
            }
            RecyclerView.this.mViewInfoStore.removeViewHolder(viewHolder);
            if (n2 != 0) return;
            if (bl2) return;
            if (!bl3) return;
            viewHolder.mOwnerRecyclerView = null;
        }

        void recycleViewInternal(View view) {
            this.recycleViewHolderInternal(RecyclerView.getChildViewHolderInt(view));
        }

        void scrapView(View object) {
            if (((ViewHolder)(object = RecyclerView.getChildViewHolderInt((View)object))).hasAnyOfTheFlags(12) || !((ViewHolder)object).isUpdated() || RecyclerView.this.canReuseUpdatedViewHolder((ViewHolder)object)) {
                if (((ViewHolder)object).isInvalid() && !((ViewHolder)object).isRemoved() && !RecyclerView.this.mAdapter.hasStableIds()) {
                    throw new IllegalArgumentException("Called scrap view with an invalid view. Invalid views cannot be reused from scrap, they should rebound from recycler pool.");
                }
                ((ViewHolder)object).setScrapContainer(this, false);
                this.mAttachedScrap.add((ViewHolder)object);
                return;
            }
            if (this.mChangedScrap == null) {
                this.mChangedScrap = new ArrayList();
            }
            ((ViewHolder)object).setScrapContainer(this, true);
            this.mChangedScrap.add((ViewHolder)object);
        }

        void setAdapterPositionsAsUnknown() {
            int n2 = this.mCachedViews.size();
            for (int i2 = 0; i2 < n2; ++i2) {
                ViewHolder viewHolder = this.mCachedViews.get(i2);
                if (viewHolder == null) continue;
                viewHolder.addFlags(512);
            }
        }

        void setRecycledViewPool(RecycledViewPool recycledViewPool) {
            if (this.mRecyclerPool != null) {
                this.mRecyclerPool.detach();
            }
            this.mRecyclerPool = recycledViewPool;
            if (recycledViewPool != null) {
                this.mRecyclerPool.attach(RecyclerView.this.getAdapter());
            }
        }

        void setViewCacheExtension(ViewCacheExtension viewCacheExtension) {
            this.mViewCacheExtension = viewCacheExtension;
        }

        public void setViewCacheSize(int n2) {
            this.mViewCacheMax = n2;
            for (int i2 = this.mCachedViews.size() - 1; i2 >= 0 && this.mCachedViews.size() > n2; --i2) {
                this.recycleCachedViewAt(i2);
            }
        }

        /*
         * Enabled aggressive block sorting
         */
        void unscrapView(ViewHolder viewHolder) {
            if (viewHolder.mInChangeScrap) {
                this.mChangedScrap.remove(viewHolder);
            } else {
                this.mAttachedScrap.remove(viewHolder);
            }
            ViewHolder.access$5002(viewHolder, null);
            ViewHolder.access$5102(viewHolder, false);
            viewHolder.clearReturnedFromScrapFlag();
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        boolean validateViewHolderForOffsetPosition(ViewHolder viewHolder) {
            boolean bl2 = true;
            if (viewHolder.isRemoved()) {
                return RecyclerView.this.mState.isPreLayout();
            }
            if (viewHolder.mPosition < 0) throw new IndexOutOfBoundsException("Inconsistency detected. Invalid view holder adapter position" + viewHolder);
            if (viewHolder.mPosition >= RecyclerView.this.mAdapter.getItemCount()) {
                throw new IndexOutOfBoundsException("Inconsistency detected. Invalid view holder adapter position" + viewHolder);
            }
            if (!RecyclerView.this.mState.isPreLayout() && RecyclerView.this.mAdapter.getItemViewType(viewHolder.mPosition) != viewHolder.getItemViewType()) {
                return false;
            }
            boolean bl3 = bl2;
            if (!RecyclerView.this.mAdapter.hasStableIds()) return bl3;
            bl3 = bl2;
            if (viewHolder.getItemId() == RecyclerView.this.mAdapter.getItemId(viewHolder.mPosition)) return bl3;
            return false;
        }

        /*
         * Enabled aggressive block sorting
         */
        void viewRangeUpdate(int n2, int n3) {
            int n4 = this.mCachedViews.size() - 1;
            while (n4 >= 0) {
                int n5;
                ViewHolder viewHolder = this.mCachedViews.get(n4);
                if (viewHolder != null && (n5 = viewHolder.getLayoutPosition()) >= n2 && n5 < n2 + n3) {
                    viewHolder.addFlags(2);
                    this.recycleCachedViewAt(n4);
                }
                --n4;
            }
            return;
        }
    }

    public static interface RecyclerListener {
        public void onViewRecycled(ViewHolder var1);
    }

    private class RecyclerViewDataObserver
    extends AdapterDataObserver {
        private RecyclerViewDataObserver() {
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public void onChanged() {
            RecyclerView.this.assertNotInLayoutOrScroll(null);
            if (RecyclerView.this.mAdapter.hasStableIds()) {
                State.access$1802(RecyclerView.this.mState, true);
                RecyclerView.this.setDataSetChangedAfterLayout();
            } else {
                State.access$1802(RecyclerView.this.mState, true);
                RecyclerView.this.setDataSetChangedAfterLayout();
            }
            if (!RecyclerView.this.mAdapterHelper.hasPendingUpdates()) {
                RecyclerView.this.requestLayout();
            }
        }

        @Override
        public void onItemRangeChanged(int n2, int n3, Object object) {
            RecyclerView.this.assertNotInLayoutOrScroll(null);
            if (RecyclerView.this.mAdapterHelper.onItemRangeChanged(n2, n3, object)) {
                this.triggerUpdateProcessor();
            }
        }

        @Override
        public void onItemRangeInserted(int n2, int n3) {
            RecyclerView.this.assertNotInLayoutOrScroll(null);
            if (RecyclerView.this.mAdapterHelper.onItemRangeInserted(n2, n3)) {
                this.triggerUpdateProcessor();
            }
        }

        @Override
        public void onItemRangeMoved(int n2, int n3, int n4) {
            RecyclerView.this.assertNotInLayoutOrScroll(null);
            if (RecyclerView.this.mAdapterHelper.onItemRangeMoved(n2, n3, n4)) {
                this.triggerUpdateProcessor();
            }
        }

        @Override
        public void onItemRangeRemoved(int n2, int n3) {
            RecyclerView.this.assertNotInLayoutOrScroll(null);
            if (RecyclerView.this.mAdapterHelper.onItemRangeRemoved(n2, n3)) {
                this.triggerUpdateProcessor();
            }
        }

        void triggerUpdateProcessor() {
            if (RecyclerView.this.mPostUpdatesOnAnimation && RecyclerView.this.mHasFixedSize && RecyclerView.this.mIsAttached) {
                ViewCompat.postOnAnimation((View)RecyclerView.this, RecyclerView.this.mUpdateChildViewsRunnable);
                return;
            }
            RecyclerView.access$4502(RecyclerView.this, true);
            RecyclerView.this.requestLayout();
        }
    }

    public static class SavedState
    extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>(){

            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            public SavedState[] newArray(int n2) {
                return new SavedState[n2];
            }
        };
        Parcelable mLayoutState;

        SavedState(Parcel parcel) {
            super(parcel);
            this.mLayoutState = parcel.readParcelable(LayoutManager.class.getClassLoader());
        }

        SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        private void copyFrom(SavedState savedState) {
            this.mLayoutState = savedState.mLayoutState;
        }

        public void writeToParcel(Parcel parcel, int n2) {
            super.writeToParcel(parcel, n2);
            parcel.writeParcelable(this.mLayoutState, 0);
        }
    }

    public static class SimpleOnItemTouchListener
    implements OnItemTouchListener {
        @Override
        public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
            return false;
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean bl2) {
        }

        @Override
        public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
        }
    }

    public static abstract class SmoothScroller {
        private LayoutManager mLayoutManager;
        private boolean mPendingInitialRun;
        private RecyclerView mRecyclerView;
        private final Action mRecyclingAction = new Action(0, 0);
        private boolean mRunning;
        private int mTargetPosition = -1;
        private View mTargetView;

        /*
         * Enabled aggressive block sorting
         */
        private void onAnimation(int n2, int n3) {
            RecyclerView recyclerView = this.mRecyclerView;
            if (!this.mRunning || this.mTargetPosition == -1 || recyclerView == null) {
                this.stop();
            }
            this.mPendingInitialRun = false;
            if (this.mTargetView != null) {
                if (this.getChildPosition(this.mTargetView) == this.mTargetPosition) {
                    this.onTargetFound(this.mTargetView, recyclerView.mState, this.mRecyclingAction);
                    this.mRecyclingAction.runIfNecessary(recyclerView);
                    this.stop();
                } else {
                    Log.e((String)RecyclerView.TAG, (String)"Passed over target position while smooth scrolling.");
                    this.mTargetView = null;
                }
            }
            if (this.mRunning) {
                this.onSeekTargetStep(n2, n3, recyclerView.mState, this.mRecyclingAction);
                boolean bl2 = this.mRecyclingAction.hasJumpTarget();
                this.mRecyclingAction.runIfNecessary(recyclerView);
                if (bl2) {
                    if (!this.mRunning) {
                        this.stop();
                        return;
                    }
                    this.mPendingInitialRun = true;
                    recyclerView.mViewFlinger.postOnAnimation();
                }
            }
        }

        public View findViewByPosition(int n2) {
            return this.mRecyclerView.mLayout.findViewByPosition(n2);
        }

        public int getChildCount() {
            return this.mRecyclerView.mLayout.getChildCount();
        }

        public int getChildPosition(View view) {
            return this.mRecyclerView.getChildLayoutPosition(view);
        }

        @Nullable
        public LayoutManager getLayoutManager() {
            return this.mLayoutManager;
        }

        public int getTargetPosition() {
            return this.mTargetPosition;
        }

        @Deprecated
        public void instantScrollToPosition(int n2) {
            this.mRecyclerView.scrollToPosition(n2);
        }

        public boolean isPendingInitialRun() {
            return this.mPendingInitialRun;
        }

        public boolean isRunning() {
            return this.mRunning;
        }

        protected void normalize(PointF pointF) {
            double d2 = Math.sqrt(pointF.x * pointF.x + pointF.y * pointF.y);
            pointF.x = (float)((double)pointF.x / d2);
            pointF.y = (float)((double)pointF.y / d2);
        }

        protected void onChildAttachedToWindow(View view) {
            if (this.getChildPosition(view) == this.getTargetPosition()) {
                this.mTargetView = view;
            }
        }

        protected abstract void onSeekTargetStep(int var1, int var2, State var3, Action var4);

        protected abstract void onStart();

        protected abstract void onStop();

        protected abstract void onTargetFound(View var1, State var2, Action var3);

        public void setTargetPosition(int n2) {
            this.mTargetPosition = n2;
        }

        void start(RecyclerView recyclerView, LayoutManager layoutManager) {
            this.mRecyclerView = recyclerView;
            this.mLayoutManager = layoutManager;
            if (this.mTargetPosition == -1) {
                throw new IllegalArgumentException("Invalid target position");
            }
            State.access$5802(this.mRecyclerView.mState, this.mTargetPosition);
            this.mRunning = true;
            this.mPendingInitialRun = true;
            this.mTargetView = this.findViewByPosition(this.getTargetPosition());
            this.onStart();
            this.mRecyclerView.mViewFlinger.postOnAnimation();
        }

        protected final void stop() {
            if (!this.mRunning) {
                return;
            }
            this.onStop();
            State.access$5802(this.mRecyclerView.mState, -1);
            this.mTargetView = null;
            this.mTargetPosition = -1;
            this.mPendingInitialRun = false;
            this.mRunning = false;
            this.mLayoutManager.onSmoothScrollerStopped(this);
            this.mLayoutManager = null;
            this.mRecyclerView = null;
        }

        public static class Action {
            public static final int UNDEFINED_DURATION = Integer.MIN_VALUE;
            private boolean changed = false;
            private int consecutiveUpdates = 0;
            private int mDuration;
            private int mDx;
            private int mDy;
            private Interpolator mInterpolator;
            private int mJumpToPosition = -1;

            public Action(int n2, int n3) {
                this(n2, n3, Integer.MIN_VALUE, null);
            }

            public Action(int n2, int n3, int n4) {
                this(n2, n3, n4, null);
            }

            public Action(int n2, int n3, int n4, Interpolator interpolator2) {
                this.mDx = n2;
                this.mDy = n3;
                this.mDuration = n4;
                this.mInterpolator = interpolator2;
            }

            /*
             * Enabled aggressive block sorting
             */
            private void runIfNecessary(RecyclerView recyclerView) {
                if (this.mJumpToPosition >= 0) {
                    int n2 = this.mJumpToPosition;
                    this.mJumpToPosition = -1;
                    recyclerView.jumpToPositionForSmoothScroller(n2);
                    this.changed = false;
                    return;
                }
                if (!this.changed) {
                    this.consecutiveUpdates = 0;
                    return;
                }
                this.validate();
                if (this.mInterpolator == null) {
                    if (this.mDuration == Integer.MIN_VALUE) {
                        recyclerView.mViewFlinger.smoothScrollBy(this.mDx, this.mDy);
                    } else {
                        recyclerView.mViewFlinger.smoothScrollBy(this.mDx, this.mDy, this.mDuration);
                    }
                } else {
                    recyclerView.mViewFlinger.smoothScrollBy(this.mDx, this.mDy, this.mDuration, this.mInterpolator);
                }
                ++this.consecutiveUpdates;
                if (this.consecutiveUpdates > 10) {
                    Log.e((String)RecyclerView.TAG, (String)"Smooth Scroll action is being updated too frequently. Make sure you are not changing it unless necessary");
                }
                this.changed = false;
            }

            private void validate() {
                if (this.mInterpolator != null && this.mDuration < 1) {
                    throw new IllegalStateException("If you provide an interpolator, you must set a positive duration");
                }
                if (this.mDuration < 1) {
                    throw new IllegalStateException("Scroll duration must be a positive number");
                }
            }

            public int getDuration() {
                return this.mDuration;
            }

            public int getDx() {
                return this.mDx;
            }

            public int getDy() {
                return this.mDy;
            }

            public Interpolator getInterpolator() {
                return this.mInterpolator;
            }

            boolean hasJumpTarget() {
                return this.mJumpToPosition >= 0;
            }

            public void jumpTo(int n2) {
                this.mJumpToPosition = n2;
            }

            public void setDuration(int n2) {
                this.changed = true;
                this.mDuration = n2;
            }

            public void setDx(int n2) {
                this.changed = true;
                this.mDx = n2;
            }

            public void setDy(int n2) {
                this.changed = true;
                this.mDy = n2;
            }

            public void setInterpolator(Interpolator interpolator2) {
                this.changed = true;
                this.mInterpolator = interpolator2;
            }

            public void update(int n2, int n3, int n4, Interpolator interpolator2) {
                this.mDx = n2;
                this.mDy = n3;
                this.mDuration = n4;
                this.mInterpolator = interpolator2;
                this.changed = true;
            }
        }
    }

    public static class State {
        static final int STEP_ANIMATIONS = 4;
        static final int STEP_LAYOUT = 2;
        static final int STEP_START = 1;
        private SparseArray<Object> mData;
        private int mDeletedInvisibleItemCountSincePreviousLayout = 0;
        private boolean mInPreLayout = false;
        private boolean mIsMeasuring = false;
        int mItemCount = 0;
        private int mLayoutStep = 1;
        private int mPreviousLayoutItemCount = 0;
        private boolean mRunPredictiveAnimations = false;
        private boolean mRunSimpleAnimations = false;
        private boolean mStructureChanged = false;
        private int mTargetPosition = -1;
        private boolean mTrackOldChangeHolders = false;

        static /* synthetic */ int access$1702(State state, int n2) {
            state.mDeletedInvisibleItemCountSincePreviousLayout = n2;
            return n2;
        }

        static /* synthetic */ int access$1712(State state, int n2) {
            state.mDeletedInvisibleItemCountSincePreviousLayout = n2 = state.mDeletedInvisibleItemCountSincePreviousLayout + n2;
            return n2;
        }

        static /* synthetic */ boolean access$1802(State state, boolean bl2) {
            state.mStructureChanged = bl2;
            return bl2;
        }

        static /* synthetic */ int access$2102(State state, int n2) {
            state.mLayoutStep = n2;
            return n2;
        }

        static /* synthetic */ boolean access$2202(State state, boolean bl2) {
            state.mIsMeasuring = bl2;
            return bl2;
        }

        static /* synthetic */ boolean access$2302(State state, boolean bl2) {
            state.mRunPredictiveAnimations = bl2;
            return bl2;
        }

        static /* synthetic */ boolean access$2402(State state, boolean bl2) {
            state.mInPreLayout = bl2;
            return bl2;
        }

        static /* synthetic */ boolean access$2502(State state, boolean bl2) {
            state.mRunSimpleAnimations = bl2;
            return bl2;
        }

        static /* synthetic */ boolean access$2702(State state, boolean bl2) {
            state.mTrackOldChangeHolders = bl2;
            return bl2;
        }

        static /* synthetic */ int access$2802(State state, int n2) {
            state.mPreviousLayoutItemCount = n2;
            return n2;
        }

        static /* synthetic */ int access$5802(State state, int n2) {
            state.mTargetPosition = n2;
            return n2;
        }

        void assertLayoutStep(int n2) {
            if ((this.mLayoutStep & n2) == 0) {
                throw new IllegalStateException("Layout state should be one of " + Integer.toBinaryString(n2) + " but it is " + Integer.toBinaryString(this.mLayoutStep));
            }
        }

        public boolean didStructureChange() {
            return this.mStructureChanged;
        }

        public <T> T get(int n2) {
            if (this.mData == null) {
                return null;
            }
            return (T)this.mData.get(n2);
        }

        public int getItemCount() {
            if (this.mInPreLayout) {
                return this.mPreviousLayoutItemCount - this.mDeletedInvisibleItemCountSincePreviousLayout;
            }
            return this.mItemCount;
        }

        public int getTargetScrollPosition() {
            return this.mTargetPosition;
        }

        public boolean hasTargetScrollPosition() {
            return this.mTargetPosition != -1;
        }

        public boolean isMeasuring() {
            return this.mIsMeasuring;
        }

        public boolean isPreLayout() {
            return this.mInPreLayout;
        }

        public void put(int n2, Object object) {
            if (this.mData == null) {
                this.mData = new SparseArray();
            }
            this.mData.put(n2, object);
        }

        public void remove(int n2) {
            if (this.mData == null) {
                return;
            }
            this.mData.remove(n2);
        }

        State reset() {
            this.mTargetPosition = -1;
            if (this.mData != null) {
                this.mData.clear();
            }
            this.mItemCount = 0;
            this.mStructureChanged = false;
            this.mIsMeasuring = false;
            return this;
        }

        public String toString() {
            return "State{mTargetPosition=" + this.mTargetPosition + ", mData=" + this.mData + ", mItemCount=" + this.mItemCount + ", mPreviousLayoutItemCount=" + this.mPreviousLayoutItemCount + ", mDeletedInvisibleItemCountSincePreviousLayout=" + this.mDeletedInvisibleItemCountSincePreviousLayout + ", mStructureChanged=" + this.mStructureChanged + ", mInPreLayout=" + this.mInPreLayout + ", mRunSimpleAnimations=" + this.mRunSimpleAnimations + ", mRunPredictiveAnimations=" + this.mRunPredictiveAnimations + '}';
        }

        public boolean willRunPredictiveAnimations() {
            return this.mRunPredictiveAnimations;
        }

        public boolean willRunSimpleAnimations() {
            return this.mRunSimpleAnimations;
        }
    }

    public static abstract class ViewCacheExtension {
        public abstract View getViewForPositionAndType(Recycler var1, int var2, int var3);
    }

    private class ViewFlinger
    implements Runnable {
        private boolean mEatRunOnAnimationRequest = false;
        private Interpolator mInterpolator = RecyclerView.access$3000();
        private int mLastFlingX;
        private int mLastFlingY;
        private boolean mReSchedulePostAnimationCallback = false;
        private ScrollerCompat mScroller;

        public ViewFlinger() {
            this.mScroller = ScrollerCompat.create(RecyclerView.this.getContext(), sQuinticInterpolator);
        }

        /*
         * Enabled aggressive block sorting
         */
        private int computeScrollDuration(int n2, int n3, int n4, int n5) {
            int n6;
            int n7 = Math.abs(n2);
            boolean bl2 = n7 > (n6 = Math.abs(n3));
            n4 = (int)Math.sqrt(n4 * n4 + n5 * n5);
            n3 = (int)Math.sqrt(n2 * n2 + n3 * n3);
            n2 = bl2 ? RecyclerView.this.getWidth() : RecyclerView.this.getHeight();
            n5 = n2 / 2;
            float f2 = Math.min(1.0f, 1.0f * (float)n3 / (float)n2);
            float f3 = n5;
            float f4 = n5;
            f2 = this.distanceInfluenceForSnapDuration(f2);
            if (n4 > 0) {
                n2 = Math.round(1000.0f * Math.abs((f3 + f4 * f2) / (float)n4)) * 4;
                return Math.min(n2, 2000);
            }
            n3 = bl2 ? n7 : n6;
            n2 = (int)(((float)n3 / (float)n2 + 1.0f) * 300.0f);
            return Math.min(n2, 2000);
        }

        private void disableRunOnAnimationRequests() {
            this.mReSchedulePostAnimationCallback = false;
            this.mEatRunOnAnimationRequest = true;
        }

        private float distanceInfluenceForSnapDuration(float f2) {
            return (float)Math.sin((float)((double)(f2 - 0.5f) * 0.4712389167638204));
        }

        private void enableRunOnAnimationRequests() {
            this.mEatRunOnAnimationRequest = false;
            if (this.mReSchedulePostAnimationCallback) {
                this.postOnAnimation();
            }
        }

        public void fling(int n2, int n3) {
            RecyclerView.this.setScrollState(2);
            this.mLastFlingY = 0;
            this.mLastFlingX = 0;
            this.mScroller.fling(0, 0, n2, n3, Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE);
            this.postOnAnimation();
        }

        void postOnAnimation() {
            if (this.mEatRunOnAnimationRequest) {
                this.mReSchedulePostAnimationCallback = true;
                return;
            }
            RecyclerView.this.removeCallbacks(this);
            ViewCompat.postOnAnimation((View)RecyclerView.this, this);
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public void run() {
            if (RecyclerView.this.mLayout == null) {
                this.stop();
                return;
            }
            this.disableRunOnAnimationRequests();
            RecyclerView.this.consumePendingUpdateOperations();
            ScrollerCompat scrollerCompat = this.mScroller;
            SmoothScroller smoothScroller = RecyclerView.this.mLayout.mSmoothScroller;
            if (scrollerCompat.computeScrollOffset()) {
                int n2 = scrollerCompat.getCurrX();
                int n3 = scrollerCompat.getCurrY();
                int n4 = n2 - this.mLastFlingX;
                int n5 = n3 - this.mLastFlingY;
                int n6 = 0;
                int n7 = 0;
                int n8 = 0;
                int n9 = 0;
                this.mLastFlingX = n2;
                this.mLastFlingY = n3;
                int n10 = 0;
                int n11 = 0;
                int n12 = 0;
                int n13 = 0;
                if (RecyclerView.this.mAdapter != null) {
                    RecyclerView.this.eatRequestLayout();
                    RecyclerView.this.onEnterLayoutOrScroll();
                    TraceCompat.beginSection(RecyclerView.TRACE_SCROLL_TAG);
                    if (n4 != 0) {
                        n7 = RecyclerView.this.mLayout.scrollHorizontallyBy(n4, RecyclerView.this.mRecycler, RecyclerView.this.mState);
                        n11 = n4 - n7;
                    }
                    if (n5 != 0) {
                        n9 = RecyclerView.this.mLayout.scrollVerticallyBy(n5, RecyclerView.this.mRecycler, RecyclerView.this.mState);
                        n13 = n5 - n9;
                    }
                    TraceCompat.endSection();
                    RecyclerView.this.repositionShadowingViews();
                    RecyclerView.this.onExitLayoutOrScroll();
                    RecyclerView.this.resumeRequestLayout(false);
                    n6 = n7;
                    n10 = n11;
                    n12 = n13;
                    n8 = n9;
                    if (smoothScroller != null) {
                        n6 = n7;
                        n10 = n11;
                        n12 = n13;
                        n8 = n9;
                        if (!smoothScroller.isPendingInitialRun()) {
                            n6 = n7;
                            n10 = n11;
                            n12 = n13;
                            n8 = n9;
                            if (smoothScroller.isRunning()) {
                                n6 = RecyclerView.this.mState.getItemCount();
                                if (n6 == 0) {
                                    smoothScroller.stop();
                                    n8 = n9;
                                    n12 = n13;
                                    n10 = n11;
                                    n6 = n7;
                                } else if (smoothScroller.getTargetPosition() >= n6) {
                                    smoothScroller.setTargetPosition(n6 - 1);
                                    smoothScroller.onAnimation(n4 - n11, n5 - n13);
                                    n6 = n7;
                                    n10 = n11;
                                    n12 = n13;
                                    n8 = n9;
                                } else {
                                    smoothScroller.onAnimation(n4 - n11, n5 - n13);
                                    n6 = n7;
                                    n10 = n11;
                                    n12 = n13;
                                    n8 = n9;
                                }
                            }
                        }
                    }
                }
                if (!RecyclerView.this.mItemDecorations.isEmpty()) {
                    RecyclerView.this.invalidate();
                }
                if (ViewCompat.getOverScrollMode((View)RecyclerView.this) != 2) {
                    RecyclerView.this.considerReleasingGlowsOnScroll(n4, n5);
                }
                if (n10 != 0 || n12 != 0) {
                    n13 = (int)scrollerCompat.getCurrVelocity();
                    n7 = 0;
                    if (n10 != n2) {
                        n7 = n10 < 0 ? -n13 : (n10 > 0 ? n13 : 0);
                    }
                    n11 = 0;
                    if (n12 != n3) {
                        n11 = n12 < 0 ? -n13 : (n12 > 0 ? n13 : 0);
                    }
                    if (ViewCompat.getOverScrollMode((View)RecyclerView.this) != 2) {
                        RecyclerView.this.absorbGlows(n7, n11);
                    }
                    if (!(n7 == 0 && n10 != n2 && scrollerCompat.getFinalX() != 0 || n11 == 0 && n12 != n3 && scrollerCompat.getFinalY() != 0)) {
                        scrollerCompat.abortAnimation();
                    }
                }
                if (n6 != 0 || n8 != 0) {
                    RecyclerView.this.dispatchOnScrolled(n6, n8);
                }
                if (!RecyclerView.this.awakenScrollBars()) {
                    RecyclerView.this.invalidate();
                }
                n7 = n5 != 0 && RecyclerView.this.mLayout.canScrollVertically() && n8 == n5 ? 1 : 0;
                n11 = n4 != 0 && RecyclerView.this.mLayout.canScrollHorizontally() && n6 == n4 ? 1 : 0;
                n7 = n4 == 0 && n5 == 0 || n11 != 0 || n7 != 0 ? 1 : 0;
                if (scrollerCompat.isFinished() || n7 == 0) {
                    RecyclerView.this.setScrollState(0);
                } else {
                    this.postOnAnimation();
                }
            }
            if (smoothScroller != null) {
                if (smoothScroller.isPendingInitialRun()) {
                    smoothScroller.onAnimation(0, 0);
                }
                if (!this.mReSchedulePostAnimationCallback) {
                    smoothScroller.stop();
                }
            }
            this.enableRunOnAnimationRequests();
        }

        public void smoothScrollBy(int n2, int n3) {
            this.smoothScrollBy(n2, n3, 0, 0);
        }

        public void smoothScrollBy(int n2, int n3, int n4) {
            this.smoothScrollBy(n2, n3, n4, sQuinticInterpolator);
        }

        public void smoothScrollBy(int n2, int n3, int n4, int n5) {
            this.smoothScrollBy(n2, n3, this.computeScrollDuration(n2, n3, n4, n5));
        }

        public void smoothScrollBy(int n2, int n3, int n4, Interpolator interpolator2) {
            if (this.mInterpolator != interpolator2) {
                this.mInterpolator = interpolator2;
                this.mScroller = ScrollerCompat.create(RecyclerView.this.getContext(), interpolator2);
            }
            RecyclerView.this.setScrollState(2);
            this.mLastFlingY = 0;
            this.mLastFlingX = 0;
            this.mScroller.startScroll(0, 0, n2, n3, n4);
            this.postOnAnimation();
        }

        public void stop() {
            RecyclerView.this.removeCallbacks(this);
            this.mScroller.abortAnimation();
        }
    }

    public static abstract class ViewHolder {
        static final int FLAG_ADAPTER_FULLUPDATE = 1024;
        static final int FLAG_ADAPTER_POSITION_UNKNOWN = 512;
        static final int FLAG_APPEARED_IN_PRE_LAYOUT = 4096;
        static final int FLAG_BOUNCED_FROM_HIDDEN_LIST = 8192;
        static final int FLAG_BOUND = 1;
        static final int FLAG_IGNORE = 128;
        static final int FLAG_INVALID = 4;
        static final int FLAG_MOVED = 2048;
        static final int FLAG_NOT_RECYCLABLE = 16;
        static final int FLAG_REMOVED = 8;
        static final int FLAG_RETURNED_FROM_SCRAP = 32;
        static final int FLAG_TMP_DETACHED = 256;
        static final int FLAG_UPDATE = 2;
        private static final List<Object> FULLUPDATE_PAYLOADS = Collections.EMPTY_LIST;
        public final View itemView;
        private int mFlags;
        private boolean mInChangeScrap = false;
        private int mIsRecyclableCount = 0;
        long mItemId = -1L;
        int mItemViewType = -1;
        int mOldPosition = -1;
        RecyclerView mOwnerRecyclerView;
        List<Object> mPayloads = null;
        int mPosition = -1;
        int mPreLayoutPosition = -1;
        private Recycler mScrapContainer = null;
        ViewHolder mShadowedHolder = null;
        ViewHolder mShadowingHolder = null;
        List<Object> mUnmodifiedPayloads = null;
        private int mWasImportantForAccessibilityBeforeHidden = 0;

        public ViewHolder(View view) {
            if (view == null) {
                throw new IllegalArgumentException("itemView may not be null");
            }
            this.itemView = view;
        }

        static /* synthetic */ Recycler access$5002(ViewHolder viewHolder, Recycler recycler) {
            viewHolder.mScrapContainer = recycler;
            return recycler;
        }

        static /* synthetic */ boolean access$5102(ViewHolder viewHolder, boolean bl2) {
            viewHolder.mInChangeScrap = bl2;
            return bl2;
        }

        private void createPayloadsIfNeeded() {
            if (this.mPayloads == null) {
                this.mPayloads = new ArrayList<Object>();
                this.mUnmodifiedPayloads = Collections.unmodifiableList(this.mPayloads);
            }
        }

        private boolean doesTransientStatePreventRecycling() {
            return (this.mFlags & 0x10) == 0 && ViewCompat.hasTransientState(this.itemView);
        }

        private void onEnteredHiddenState() {
            this.mWasImportantForAccessibilityBeforeHidden = ViewCompat.getImportantForAccessibility(this.itemView);
            ViewCompat.setImportantForAccessibility(this.itemView, 4);
        }

        private void onLeftHiddenState() {
            ViewCompat.setImportantForAccessibility(this.itemView, this.mWasImportantForAccessibilityBeforeHidden);
            this.mWasImportantForAccessibilityBeforeHidden = 0;
        }

        private boolean shouldBeKeptAsChild() {
            return (this.mFlags & 0x10) != 0;
        }

        /*
         * Enabled aggressive block sorting
         */
        void addChangePayload(Object object) {
            if (object == null) {
                this.addFlags(1024);
                return;
            } else {
                if ((this.mFlags & 0x400) != 0) return;
                this.createPayloadsIfNeeded();
                this.mPayloads.add(object);
                return;
            }
        }

        void addFlags(int n2) {
            this.mFlags |= n2;
        }

        void clearOldPosition() {
            this.mOldPosition = -1;
            this.mPreLayoutPosition = -1;
        }

        void clearPayload() {
            if (this.mPayloads != null) {
                this.mPayloads.clear();
            }
            this.mFlags &= 0xFFFFFBFF;
        }

        void clearReturnedFromScrapFlag() {
            this.mFlags &= 0xFFFFFFDF;
        }

        void clearTmpDetachFlag() {
            this.mFlags &= 0xFFFFFEFF;
        }

        void flagRemovedAndOffsetPosition(int n2, int n3, boolean bl2) {
            this.addFlags(8);
            this.offsetPosition(n3, bl2);
            this.mPosition = n2;
        }

        public final int getAdapterPosition() {
            if (this.mOwnerRecyclerView == null) {
                return -1;
            }
            return this.mOwnerRecyclerView.getAdapterPositionFor(this);
        }

        public final long getItemId() {
            return this.mItemId;
        }

        public final int getItemViewType() {
            return this.mItemViewType;
        }

        public final int getLayoutPosition() {
            if (this.mPreLayoutPosition == -1) {
                return this.mPosition;
            }
            return this.mPreLayoutPosition;
        }

        public final int getOldPosition() {
            return this.mOldPosition;
        }

        @Deprecated
        public final int getPosition() {
            if (this.mPreLayoutPosition == -1) {
                return this.mPosition;
            }
            return this.mPreLayoutPosition;
        }

        List<Object> getUnmodifiedPayloads() {
            if ((this.mFlags & 0x400) == 0) {
                if (this.mPayloads == null || this.mPayloads.size() == 0) {
                    return FULLUPDATE_PAYLOADS;
                }
                return this.mUnmodifiedPayloads;
            }
            return FULLUPDATE_PAYLOADS;
        }

        boolean hasAnyOfTheFlags(int n2) {
            return (this.mFlags & n2) != 0;
        }

        boolean isAdapterPositionUnknown() {
            return (this.mFlags & 0x200) != 0 || this.isInvalid();
        }

        boolean isBound() {
            return (this.mFlags & 1) != 0;
        }

        boolean isInvalid() {
            return (this.mFlags & 4) != 0;
        }

        public final boolean isRecyclable() {
            return (this.mFlags & 0x10) == 0 && !ViewCompat.hasTransientState(this.itemView);
        }

        boolean isRemoved() {
            return (this.mFlags & 8) != 0;
        }

        boolean isScrap() {
            return this.mScrapContainer != null;
        }

        boolean isTmpDetached() {
            return (this.mFlags & 0x100) != 0;
        }

        boolean isUpdated() {
            return (this.mFlags & 2) != 0;
        }

        boolean needsUpdate() {
            return (this.mFlags & 2) != 0;
        }

        void offsetPosition(int n2, boolean bl2) {
            if (this.mOldPosition == -1) {
                this.mOldPosition = this.mPosition;
            }
            if (this.mPreLayoutPosition == -1) {
                this.mPreLayoutPosition = this.mPosition;
            }
            if (bl2) {
                this.mPreLayoutPosition += n2;
            }
            this.mPosition += n2;
            if (this.itemView.getLayoutParams() != null) {
                ((LayoutParams)this.itemView.getLayoutParams()).mInsetsDirty = true;
            }
        }

        void resetInternal() {
            this.mFlags = 0;
            this.mPosition = -1;
            this.mOldPosition = -1;
            this.mItemId = -1L;
            this.mPreLayoutPosition = -1;
            this.mIsRecyclableCount = 0;
            this.mShadowedHolder = null;
            this.mShadowingHolder = null;
            this.clearPayload();
            this.mWasImportantForAccessibilityBeforeHidden = 0;
        }

        void saveOldPosition() {
            if (this.mOldPosition == -1) {
                this.mOldPosition = this.mPosition;
            }
        }

        void setFlags(int n2, int n3) {
            this.mFlags = this.mFlags & ~n3 | n2 & n3;
        }

        /*
         * Enabled aggressive block sorting
         */
        public final void setIsRecyclable(boolean bl2) {
            int n2 = bl2 ? this.mIsRecyclableCount - 1 : this.mIsRecyclableCount + 1;
            this.mIsRecyclableCount = n2;
            if (this.mIsRecyclableCount < 0) {
                this.mIsRecyclableCount = 0;
                Log.e((String)"View", (String)("isRecyclable decremented below 0: unmatched pair of setIsRecyable() calls for " + this));
                return;
            } else {
                if (!bl2 && this.mIsRecyclableCount == 1) {
                    this.mFlags |= 0x10;
                    return;
                }
                if (!bl2 || this.mIsRecyclableCount != 0) return;
                this.mFlags &= 0xFFFFFFEF;
                return;
            }
        }

        void setScrapContainer(Recycler recycler, boolean bl2) {
            this.mScrapContainer = recycler;
            this.mInChangeScrap = bl2;
        }

        boolean shouldIgnore() {
            return (this.mFlags & 0x80) != 0;
        }

        void stopIgnoring() {
            this.mFlags &= 0xFFFFFF7F;
        }

        /*
         * Enabled aggressive block sorting
         */
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder("ViewHolder{" + Integer.toHexString(this.hashCode()) + " position=" + this.mPosition + " id=" + this.mItemId + ", oldPos=" + this.mOldPosition + ", pLpos:" + this.mPreLayoutPosition);
            if (this.isScrap()) {
                StringBuilder stringBuilder2 = stringBuilder.append(" scrap ");
                String string2 = this.mInChangeScrap ? "[changeScrap]" : "[attachedScrap]";
                stringBuilder2.append(string2);
            }
            if (this.isInvalid()) {
                stringBuilder.append(" invalid");
            }
            if (!this.isBound()) {
                stringBuilder.append(" unbound");
            }
            if (this.needsUpdate()) {
                stringBuilder.append(" update");
            }
            if (this.isRemoved()) {
                stringBuilder.append(" removed");
            }
            if (this.shouldIgnore()) {
                stringBuilder.append(" ignored");
            }
            if (this.isTmpDetached()) {
                stringBuilder.append(" tmpDetached");
            }
            if (!this.isRecyclable()) {
                stringBuilder.append(" not recyclable(" + this.mIsRecyclableCount + ")");
            }
            if (this.isAdapterPositionUnknown()) {
                stringBuilder.append(" undefined adapter position");
            }
            if (this.itemView.getParent() == null) {
                stringBuilder.append(" no parent");
            }
            stringBuilder.append("}");
            return stringBuilder.toString();
        }

        void unScrap() {
            this.mScrapContainer.unscrapView(this);
        }

        boolean wasReturnedFromScrap() {
            return (this.mFlags & 0x20) != 0;
        }
    }
}

