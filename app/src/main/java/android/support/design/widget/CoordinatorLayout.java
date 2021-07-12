/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.TypedArray
 *  android.graphics.Canvas
 *  android.graphics.Paint
 *  android.graphics.Rect
 *  android.graphics.drawable.ColorDrawable
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.Drawable$Callback
 *  android.os.Build$VERSION
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.os.SystemClock
 *  android.text.TextUtils
 *  android.util.AttributeSet
 *  android.util.Log
 *  android.util.SparseArray
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.View$BaseSavedState
 *  android.view.View$MeasureSpec
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewGroup$MarginLayoutParams
 *  android.view.ViewGroup$OnHierarchyChangeListener
 *  android.view.ViewParent
 *  android.view.ViewTreeObserver$OnPreDrawListener
 */
package android.support.design.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.design.R;
import android.support.design.widget.CoordinatorLayoutInsetsHelper;
import android.support.design.widget.CoordinatorLayoutInsetsHelperLollipop;
import android.support.design.widget.ThemeUtils;
import android.support.design.widget.ViewGroupUtils;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.os.ParcelableCompat;
import android.support.v4.os.ParcelableCompatCreatorCallbacks;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.NestedScrollingParentHelper;
import android.support.v4.view.OnApplyWindowInsetsListener;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.WindowInsetsCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CoordinatorLayout
extends ViewGroup
implements NestedScrollingParent {
    static final Class<?>[] CONSTRUCTOR_PARAMS;
    static final CoordinatorLayoutInsetsHelper INSETS_HELPER;
    static final String TAG = "CoordinatorLayout";
    static final Comparator<View> TOP_SORTED_CHILDREN_COMPARATOR;
    private static final int TYPE_ON_INTERCEPT = 0;
    private static final int TYPE_ON_TOUCH = 1;
    static final String WIDGET_PACKAGE_NAME;
    static final ThreadLocal<Map<String, Constructor<Behavior>>> sConstructors;
    private View mBehaviorTouchView;
    private final List<View> mDependencySortedChildren;
    private boolean mDisallowInterceptReset;
    private boolean mDrawStatusBarBackground;
    private boolean mIsAttachedToWindow;
    private int[] mKeylines;
    private WindowInsetsCompat mLastInsets;
    final Comparator<View> mLayoutDependencyComparator = new Comparator<View>(){

        @Override
        public int compare(View view, View view2) {
            if (view == view2) {
                return 0;
            }
            if (((LayoutParams)view.getLayoutParams()).dependsOn(CoordinatorLayout.this, view, view2)) {
                return 1;
            }
            if (((LayoutParams)view2.getLayoutParams()).dependsOn(CoordinatorLayout.this, view2, view)) {
                return -1;
            }
            return 0;
        }
    };
    private boolean mNeedsPreDrawListener;
    private View mNestedScrollingDirectChild;
    private final NestedScrollingParentHelper mNestedScrollingParentHelper;
    private View mNestedScrollingTarget;
    private ViewGroup.OnHierarchyChangeListener mOnHierarchyChangeListener;
    private OnPreDrawListener mOnPreDrawListener;
    private Paint mScrimPaint;
    private Drawable mStatusBarBackground;
    private final List<View> mTempDependenciesList;
    private final int[] mTempIntPair;
    private final List<View> mTempList1;
    private final Rect mTempRect1;
    private final Rect mTempRect2;
    private final Rect mTempRect3;

    /*
     * Enabled aggressive block sorting
     */
    static {
        Object object = CoordinatorLayout.class.getPackage();
        object = object != null ? ((Package)object).getName() : null;
        WIDGET_PACKAGE_NAME = object;
        if (Build.VERSION.SDK_INT >= 21) {
            TOP_SORTED_CHILDREN_COMPARATOR = new ViewElevationComparator();
            INSETS_HELPER = new CoordinatorLayoutInsetsHelperLollipop();
        } else {
            TOP_SORTED_CHILDREN_COMPARATOR = null;
            INSETS_HELPER = null;
        }
        CONSTRUCTOR_PARAMS = new Class[]{Context.class, AttributeSet.class};
        sConstructors = new ThreadLocal();
    }

    public CoordinatorLayout(Context context) {
        this(context, null);
    }

    public CoordinatorLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public CoordinatorLayout(Context object, AttributeSet attributeSet, int n2) {
        super(object, attributeSet, n2);
        this.mDependencySortedChildren = new ArrayList<View>();
        this.mTempList1 = new ArrayList<View>();
        this.mTempDependenciesList = new ArrayList<View>();
        this.mTempRect1 = new Rect();
        this.mTempRect2 = new Rect();
        this.mTempRect3 = new Rect();
        this.mTempIntPair = new int[2];
        this.mNestedScrollingParentHelper = new NestedScrollingParentHelper(this);
        ThemeUtils.checkAppCompatTheme(object);
        attributeSet = object.obtainStyledAttributes(attributeSet, R.styleable.CoordinatorLayout, n2, R.style.Widget_Design_CoordinatorLayout);
        n2 = attributeSet.getResourceId(R.styleable.CoordinatorLayout_keylines, 0);
        if (n2 != 0) {
            object = object.getResources();
            this.mKeylines = object.getIntArray(n2);
            float f2 = object.getDisplayMetrics().density;
            int n3 = this.mKeylines.length;
            for (n2 = 0; n2 < n3; ++n2) {
                object = this.mKeylines;
                object[n2] = (Context)((int)((float)object[n2] * f2));
            }
        }
        this.mStatusBarBackground = attributeSet.getDrawable(R.styleable.CoordinatorLayout_statusBarBackground);
        attributeSet.recycle();
        if (INSETS_HELPER != null) {
            INSETS_HELPER.setupForWindowInsets((View)this, new ApplyInsetsListener());
        }
        super.setOnHierarchyChangeListener((ViewGroup.OnHierarchyChangeListener)new HierarchyChangeListener());
    }

    private WindowInsetsCompat dispatchApplyWindowInsetsToBehaviors(WindowInsetsCompat windowInsetsCompat) {
        if (windowInsetsCompat.isConsumed()) {
            return windowInsetsCompat;
        }
        int n2 = 0;
        int n3 = this.getChildCount();
        while (true) {
            WindowInsetsCompat windowInsetsCompat2;
            block6: {
                block5: {
                    windowInsetsCompat2 = windowInsetsCompat;
                    if (n2 >= n3) break block5;
                    View view = this.getChildAt(n2);
                    windowInsetsCompat2 = windowInsetsCompat;
                    if (!ViewCompat.getFitsSystemWindows(view)) break block6;
                    Behavior behavior = ((LayoutParams)view.getLayoutParams()).getBehavior();
                    windowInsetsCompat2 = windowInsetsCompat;
                    if (behavior == null) break block6;
                    windowInsetsCompat2 = windowInsetsCompat = behavior.onApplyWindowInsets(this, view, windowInsetsCompat);
                    if (!windowInsetsCompat.isConsumed()) break block6;
                    windowInsetsCompat2 = windowInsetsCompat;
                }
                return windowInsetsCompat2;
            }
            ++n2;
            windowInsetsCompat = windowInsetsCompat2;
        }
    }

    private int getKeyline(int n2) {
        if (this.mKeylines == null) {
            Log.e((String)TAG, (String)("No keylines defined for " + this + " - attempted index lookup " + n2));
            return 0;
        }
        if (n2 < 0 || n2 >= this.mKeylines.length) {
            Log.e((String)TAG, (String)("Keyline index " + n2 + " out of range for " + this));
            return 0;
        }
        return this.mKeylines[n2];
    }

    /*
     * Enabled aggressive block sorting
     */
    private void getTopSortedChildren(List<View> list) {
        list.clear();
        boolean bl2 = this.isChildrenDrawingOrderEnabled();
        int n2 = this.getChildCount();
        for (int i2 = n2 - 1; i2 >= 0; --i2) {
            int n3 = bl2 ? this.getChildDrawingOrder(n2, i2) : i2;
            list.add(this.getChildAt(n3));
        }
        if (TOP_SORTED_CHILDREN_COMPARATOR != null) {
            Collections.sort(list, TOP_SORTED_CHILDREN_COMPARATOR);
        }
    }

    private void layoutChild(View view, int n2) {
        LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
        Rect rect = this.mTempRect1;
        rect.set(this.getPaddingLeft() + layoutParams.leftMargin, this.getPaddingTop() + layoutParams.topMargin, this.getWidth() - this.getPaddingRight() - layoutParams.rightMargin, this.getHeight() - this.getPaddingBottom() - layoutParams.bottomMargin);
        if (this.mLastInsets != null && ViewCompat.getFitsSystemWindows((View)this) && !ViewCompat.getFitsSystemWindows(view)) {
            rect.left += this.mLastInsets.getSystemWindowInsetLeft();
            rect.top += this.mLastInsets.getSystemWindowInsetTop();
            rect.right -= this.mLastInsets.getSystemWindowInsetRight();
            rect.bottom -= this.mLastInsets.getSystemWindowInsetBottom();
        }
        Rect rect2 = this.mTempRect2;
        GravityCompat.apply(CoordinatorLayout.resolveGravity(layoutParams.gravity), view.getMeasuredWidth(), view.getMeasuredHeight(), rect, rect2, n2);
        view.layout(rect2.left, rect2.top, rect2.right, rect2.bottom);
    }

    private void layoutChildWithAnchor(View view, View view2, int n2) {
        LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
        layoutParams = this.mTempRect1;
        Rect rect = this.mTempRect2;
        this.getDescendantRect(view2, (Rect)layoutParams);
        this.getDesiredAnchoredChildRect(view, n2, (Rect)layoutParams, rect);
        view.layout(rect.left, rect.top, rect.right, rect.bottom);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void layoutChildWithKeyline(View view, int n2, int n3) {
        LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
        int n4 = GravityCompat.getAbsoluteGravity(CoordinatorLayout.resolveKeylineGravity(layoutParams.gravity), n3);
        int n5 = this.getWidth();
        int n6 = this.getHeight();
        int n7 = view.getMeasuredWidth();
        int n8 = view.getMeasuredHeight();
        int n9 = n2;
        if (n3 == 1) {
            n9 = n5 - n2;
        }
        n2 = this.getKeyline(n9) - n7;
        n3 = 0;
        switch (n4 & 7) {
            case 5: {
                n2 += n7;
            }
            default: {
                break;
            }
            case 1: {
                n2 += n7 / 2;
            }
        }
        switch (n4 & 0x70) {
            case 80: {
                n3 = 0 + n8;
            }
            default: {
                break;
            }
            case 16: {
                n3 = 0 + n8 / 2;
            }
        }
        n2 = Math.max(this.getPaddingLeft() + layoutParams.leftMargin, Math.min(n2, n5 - this.getPaddingRight() - n7 - layoutParams.rightMargin));
        n3 = Math.max(this.getPaddingTop() + layoutParams.topMargin, Math.min(n3, n6 - this.getPaddingBottom() - n8 - layoutParams.bottomMargin));
        view.layout(n2, n3, n2 + n7, n3 + n8);
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static Behavior parseBehavior(Context object, AttributeSet attributeSet, String string2) {
        String string3;
        if (TextUtils.isEmpty((CharSequence)string3)) {
            return null;
        }
        if (string3.startsWith(".")) {
            string3 = object.getPackageName() + string3;
        } else if (string3.indexOf(46) < 0 && !TextUtils.isEmpty((CharSequence)WIDGET_PACKAGE_NAME)) {
            string3 = WIDGET_PACKAGE_NAME + '.' + string3;
        }
        try {
            void var1_3;
            Constructor<?> constructor = sConstructors.get();
            Map<String, Constructor<Behavior>> map = constructor;
            if (constructor == null) {
                map = new HashMap<String, Constructor<Behavior>>();
                sConstructors.set(map);
            }
            Constructor<Behavior> constructor2 = map.get(string3);
            constructor = constructor2;
            if (constructor2 == null) {
                constructor = Class.forName(string3, true, object.getClassLoader()).getConstructor(CONSTRUCTOR_PARAMS);
                constructor.setAccessible(true);
                map.put(string3, constructor);
            }
            return (Behavior)constructor.newInstance(object, var1_3);
        }
        catch (Exception exception) {
            throw new RuntimeException("Could not inflate Behavior subclass " + string3, exception);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean performIntercept(MotionEvent motionEvent, int n2) {
        boolean bl2;
        boolean bl3 = false;
        boolean bl4 = false;
        LayoutParams layoutParams = null;
        int n3 = MotionEventCompat.getActionMasked(motionEvent);
        List<View> list = this.mTempList1;
        this.getTopSortedChildren(list);
        int n4 = list.size();
        int n5 = 0;
        while (true) {
            boolean bl5;
            boolean bl6;
            bl2 = bl3;
            if (n5 >= n4) break;
            View view = list.get(n5);
            LayoutParams layoutParams2 = (LayoutParams)view.getLayoutParams();
            Behavior behavior = layoutParams2.getBehavior();
            if ((bl3 || bl4) && n3 != 0) {
                layoutParams2 = layoutParams;
                bl6 = bl3;
                bl5 = bl4;
                if (behavior != null) {
                    layoutParams2 = layoutParams;
                    if (layoutParams == null) {
                        long l2 = SystemClock.uptimeMillis();
                        layoutParams2 = MotionEvent.obtain((long)l2, (long)l2, (int)3, (float)0.0f, (float)0.0f, (int)0);
                    }
                    switch (n2) {
                        default: {
                            bl5 = bl4;
                            bl6 = bl3;
                            break;
                        }
                        case 0: {
                            behavior.onInterceptTouchEvent(this, view, (MotionEvent)layoutParams2);
                            bl6 = bl3;
                            bl5 = bl4;
                            break;
                        }
                        case 1: {
                            behavior.onTouchEvent(this, view, (MotionEvent)layoutParams2);
                            bl6 = bl3;
                            bl5 = bl4;
                            break;
                        }
                    }
                }
            } else {
                bl2 = bl3;
                if (!bl3) {
                    bl2 = bl3;
                    if (behavior != null) {
                        switch (n2) {
                            case 0: {
                                bl3 = behavior.onInterceptTouchEvent(this, view, motionEvent);
                                break;
                            }
                            case 1: {
                                bl3 = behavior.onTouchEvent(this, view, motionEvent);
                            }
                        }
                        bl2 = bl3;
                        if (bl3) {
                            this.mBehaviorTouchView = view;
                            bl2 = bl3;
                        }
                    }
                }
                bl6 = layoutParams2.didBlockInteraction();
                bl3 = layoutParams2.isBlockingInteractionBelow(this, view);
                bl4 = bl3 && !bl6;
                layoutParams2 = layoutParams;
                bl6 = bl2;
                bl5 = bl4;
                if (bl3) {
                    layoutParams2 = layoutParams;
                    bl6 = bl2;
                    bl5 = bl4;
                    if (!bl4) break;
                }
            }
            ++n5;
            layoutParams = layoutParams2;
            bl3 = bl6;
            bl4 = bl5;
        }
        list.clear();
        return bl2;
    }

    private void prepareChildren() {
        this.mDependencySortedChildren.clear();
        int n2 = this.getChildCount();
        for (int i2 = 0; i2 < n2; ++i2) {
            View view = this.getChildAt(i2);
            this.getResolvedLayoutParams(view).findAnchorView(this, view);
            this.mDependencySortedChildren.add(view);
        }
        CoordinatorLayout.selectionSort(this.mDependencySortedChildren, this.mLayoutDependencyComparator);
    }

    private void resetTouchBehaviors() {
        if (this.mBehaviorTouchView != null) {
            Behavior behavior = ((LayoutParams)this.mBehaviorTouchView.getLayoutParams()).getBehavior();
            if (behavior != null) {
                long l2 = SystemClock.uptimeMillis();
                MotionEvent motionEvent = MotionEvent.obtain((long)l2, (long)l2, (int)3, (float)0.0f, (float)0.0f, (int)0);
                behavior.onTouchEvent(this, this.mBehaviorTouchView, motionEvent);
                motionEvent.recycle();
            }
            this.mBehaviorTouchView = null;
        }
        int n2 = this.getChildCount();
        for (int i2 = 0; i2 < n2; ++i2) {
            ((LayoutParams)this.getChildAt(i2).getLayoutParams()).resetTouchBehaviorTracking();
        }
        this.mDisallowInterceptReset = false;
    }

    private static int resolveAnchoredChildGravity(int n2) {
        int n3 = n2;
        if (n2 == 0) {
            n3 = 17;
        }
        return n3;
    }

    private static int resolveGravity(int n2) {
        int n3 = n2;
        if (n2 == 0) {
            n3 = 0x800033;
        }
        return n3;
    }

    private static int resolveKeylineGravity(int n2) {
        int n3 = n2;
        if (n2 == 0) {
            n3 = 8388661;
        }
        return n3;
    }

    /*
     * Enabled aggressive block sorting
     */
    private static void selectionSort(List<View> list, Comparator<View> comparator) {
        if (list != null && list.size() >= 2) {
            int n2;
            View[] viewArray = new View[list.size()];
            list.toArray(viewArray);
            int n3 = viewArray.length;
            for (n2 = 0; n2 < n3; ++n2) {
                int n4 = n2;
                for (int i2 = n2 + 1; i2 < n3; ++i2) {
                    int n5 = n4;
                    if (comparator.compare(viewArray[i2], viewArray[n4]) < 0) {
                        n5 = i2;
                    }
                    n4 = n5;
                }
                if (n2 == n4) continue;
                View view = viewArray[n4];
                viewArray[n4] = viewArray[n2];
                viewArray[n2] = view;
            }
            list.clear();
            for (n2 = 0; n2 < n3; ++n2) {
                list.add(viewArray[n2]);
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private WindowInsetsCompat setWindowInsets(WindowInsetsCompat windowInsetsCompat) {
        boolean bl2 = true;
        WindowInsetsCompat windowInsetsCompat2 = windowInsetsCompat;
        if (this.mLastInsets != windowInsetsCompat) {
            this.mLastInsets = windowInsetsCompat;
            boolean bl3 = windowInsetsCompat != null && windowInsetsCompat.getSystemWindowInsetTop() > 0;
            this.mDrawStatusBarBackground = bl3;
            bl3 = !this.mDrawStatusBarBackground && this.getBackground() == null ? bl2 : false;
            this.setWillNotDraw(bl3);
            windowInsetsCompat2 = this.dispatchApplyWindowInsetsToBehaviors(windowInsetsCompat);
            this.requestLayout();
        }
        return windowInsetsCompat2;
    }

    void addPreDrawListener() {
        if (this.mIsAttachedToWindow) {
            if (this.mOnPreDrawListener == null) {
                this.mOnPreDrawListener = new OnPreDrawListener();
            }
            this.getViewTreeObserver().addOnPreDrawListener((ViewTreeObserver.OnPreDrawListener)this.mOnPreDrawListener);
        }
        this.mNeedsPreDrawListener = true;
    }

    protected boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams && super.checkLayoutParams(layoutParams);
    }

    /*
     * Enabled aggressive block sorting
     */
    void dispatchDependentViewRemoved(View view) {
        int n2 = this.mDependencySortedChildren.size();
        boolean bl2 = false;
        int n3 = 0;
        while (n3 < n2) {
            boolean bl3;
            View view2 = this.mDependencySortedChildren.get(n3);
            if (view2 == view) {
                bl3 = true;
            } else {
                bl3 = bl2;
                if (bl2) {
                    LayoutParams layoutParams = (LayoutParams)view2.getLayoutParams();
                    Behavior behavior = layoutParams.getBehavior();
                    bl3 = bl2;
                    if (behavior != null) {
                        bl3 = bl2;
                        if (layoutParams.dependsOn(this, view2, view)) {
                            behavior.onDependentViewRemoved(this, view2, view);
                            bl3 = bl2;
                        }
                    }
                }
            }
            ++n3;
            bl2 = bl3;
        }
        return;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void dispatchDependentViewsChanged(View view) {
        int n2 = this.mDependencySortedChildren.size();
        boolean bl2 = false;
        int n3 = 0;
        while (n3 < n2) {
            boolean bl3;
            View view2 = this.mDependencySortedChildren.get(n3);
            if (view2 == view) {
                bl3 = true;
            } else {
                bl3 = bl2;
                if (bl2) {
                    LayoutParams layoutParams = (LayoutParams)view2.getLayoutParams();
                    Behavior behavior = layoutParams.getBehavior();
                    bl3 = bl2;
                    if (behavior != null) {
                        bl3 = bl2;
                        if (layoutParams.dependsOn(this, view2, view)) {
                            behavior.onDependentViewChanged(this, view2, view);
                            bl3 = bl2;
                        }
                    }
                }
            }
            ++n3;
            bl2 = bl3;
        }
        return;
    }

    /*
     * Handled impossible loop by adding 'first' condition
     * Enabled aggressive block sorting
     */
    void dispatchOnDependentViewChanged(boolean bl2) {
        int n2 = ViewCompat.getLayoutDirection((View)this);
        int n3 = this.mDependencySortedChildren.size();
        int n4 = 0;
        block0: while (n4 < n3) {
            int n5;
            View view = this.mDependencySortedChildren.get(n4);
            LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
            for (n5 = 0; n5 < n4; ++n5) {
                View view2 = this.mDependencySortedChildren.get(n5);
                if (layoutParams.mAnchorDirectChild != view2) continue;
                this.offsetChildToAnchor(view, n2);
            }
            layoutParams = this.mTempRect1;
            Rect rect = this.mTempRect2;
            this.getLastChildRect(view, (Rect)layoutParams);
            this.getChildRect(view, true, rect);
            boolean bl3 = true;
            while (true) {
                block13: {
                    block12: {
                        block11: {
                            if (!bl3 || (bl3 = false)) break block11;
                            if (layoutParams.equals(rect)) break block12;
                            this.recordLastChildRect(view, rect);
                            n5 = n4 + 1;
                        }
                        if (n5 < n3) break block13;
                    }
                    ++n4;
                    continue block0;
                }
                layoutParams = this.mDependencySortedChildren.get(n5);
                LayoutParams layoutParams2 = (LayoutParams)layoutParams.getLayoutParams();
                Behavior behavior = layoutParams2.getBehavior();
                if (behavior != null && behavior.layoutDependsOn(this, layoutParams, view)) {
                    if (!bl2 && layoutParams2.getChangedAfterNestedScroll()) {
                        layoutParams2.resetChangedAfterNestedScroll();
                    } else {
                        boolean bl4 = behavior.onDependentViewChanged(this, layoutParams, view);
                        if (bl2) {
                            layoutParams2.setChangedAfterNestedScroll(bl4);
                        }
                    }
                }
                ++n5;
            }
            break;
        }
        return;
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean doViewsOverlap(View view, View view2) {
        if (view.getVisibility() == 0 && view2.getVisibility() == 0) {
            Rect rect = this.mTempRect1;
            boolean bl2 = view.getParent() != this;
            this.getChildRect(view, bl2, rect);
            view = this.mTempRect2;
            bl2 = view2.getParent() != this;
            this.getChildRect(view2, bl2, (Rect)view);
            return rect.left <= view.right && rect.top <= view.bottom && rect.right >= view.left && rect.bottom >= view.top;
        }
        return false;
    }

    protected boolean drawChild(Canvas canvas, View view, long l2) {
        LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
        if (layoutParams.mBehavior != null && layoutParams.mBehavior.getScrimOpacity(this, view) > 0.0f) {
            if (this.mScrimPaint == null) {
                this.mScrimPaint = new Paint();
            }
            this.mScrimPaint.setColor(layoutParams.mBehavior.getScrimColor(this, view));
            canvas.drawRect((float)this.getPaddingLeft(), (float)this.getPaddingTop(), (float)(this.getWidth() - this.getPaddingRight()), (float)(this.getHeight() - this.getPaddingBottom()), this.mScrimPaint);
        }
        return super.drawChild(canvas, view, l2);
    }

    protected void drawableStateChanged() {
        super.drawableStateChanged();
        int[] nArray = this.getDrawableState();
        boolean bl2 = false;
        Drawable drawable2 = this.mStatusBarBackground;
        boolean bl3 = bl2;
        if (drawable2 != null) {
            bl3 = bl2;
            if (drawable2.isStateful()) {
                bl3 = false | drawable2.setState(nArray);
            }
        }
        if (bl3) {
            this.invalidate();
        }
    }

    void ensurePreDrawListener() {
        boolean bl2 = false;
        int n2 = this.getChildCount();
        int n3 = 0;
        while (true) {
            block6: {
                boolean bl3;
                block5: {
                    bl3 = bl2;
                    if (n3 >= n2) break block5;
                    if (!this.hasDependencies(this.getChildAt(n3))) break block6;
                    bl3 = true;
                }
                if (bl3 != this.mNeedsPreDrawListener) {
                    if (!bl3) break;
                    this.addPreDrawListener();
                }
                return;
            }
            ++n3;
        }
        this.removePreDrawListener();
    }

    protected LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-2, -2);
    }

    public LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(this.getContext(), attributeSet);
    }

    protected LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        if (layoutParams instanceof LayoutParams) {
            return new LayoutParams((LayoutParams)layoutParams);
        }
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            return new LayoutParams((ViewGroup.MarginLayoutParams)layoutParams);
        }
        return new LayoutParams(layoutParams);
    }

    void getChildRect(View view, boolean bl2, Rect rect) {
        if (view.isLayoutRequested() || view.getVisibility() == 8) {
            rect.set(0, 0, 0, 0);
            return;
        }
        if (bl2) {
            this.getDescendantRect(view, rect);
            return;
        }
        rect.set(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
    }

    /*
     * Enabled aggressive block sorting
     */
    public List<View> getDependencies(View view) {
        LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
        List<View> list = this.mTempDependenciesList;
        list.clear();
        int n2 = this.getChildCount();
        int n3 = 0;
        while (n3 < n2) {
            View view2 = this.getChildAt(n3);
            if (view2 != view && layoutParams.dependsOn(this, view, view2)) {
                list.add(view2);
            }
            ++n3;
        }
        return list;
    }

    void getDescendantRect(View view, Rect rect) {
        ViewGroupUtils.getDescendantRect(this, view, rect);
    }

    /*
     * Recovered potentially malformed switches.  Disable with '--allowmalformedswitch false'
     * Enabled aggressive block sorting
     */
    void getDesiredAnchoredChildRect(View view, int n2, Rect rect, Rect rect2) {
        LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
        int n3 = GravityCompat.getAbsoluteGravity(CoordinatorLayout.resolveAnchoredChildGravity(layoutParams.gravity), n2);
        int n4 = GravityCompat.getAbsoluteGravity(CoordinatorLayout.resolveGravity(layoutParams.anchorGravity), n2);
        int n5 = view.getMeasuredWidth();
        int n6 = view.getMeasuredHeight();
        switch (n4 & 7) {
            default: {
                n2 = rect.left;
                break;
            }
            case 5: {
                n2 = rect.right;
                break;
            }
            case 1: {
                n2 = rect.left + rect.width() / 2;
            }
        }
        switch (n4 & 0x70) {
            default: {
                n4 = rect.top;
                break;
            }
            case 80: {
                n4 = rect.bottom;
                break;
            }
            case 16: {
                n4 = rect.top + rect.height() / 2;
            }
        }
        int n7 = n2;
        switch (n3 & 7) {
            default: {
                n7 = n2 - n5;
            }
            case 5: {
                break;
            }
            case 1: {
                n7 = n2 - n5 / 2;
            }
        }
        n2 = n4;
        switch (n3 & 0x70) {
            default: {
                n2 = n4 - n6;
            }
            case 80: {
                break;
            }
            case 16: {
                n2 = n4 - n6 / 2;
            }
        }
        n3 = this.getWidth();
        n4 = this.getHeight();
        n7 = Math.max(this.getPaddingLeft() + layoutParams.leftMargin, Math.min(n7, n3 - this.getPaddingRight() - n5 - layoutParams.rightMargin));
        n2 = Math.max(this.getPaddingTop() + layoutParams.topMargin, Math.min(n2, n4 - this.getPaddingBottom() - n6 - layoutParams.bottomMargin));
        rect2.set(n7, n2, n7 + n5, n2 + n6);
    }

    void getLastChildRect(View view, Rect rect) {
        rect.set(((LayoutParams)view.getLayoutParams()).getLastChildRect());
    }

    @Override
    public int getNestedScrollAxes() {
        return this.mNestedScrollingParentHelper.getNestedScrollAxes();
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    LayoutParams getResolvedLayoutParams(View object) {
        LayoutParams layoutParams = (LayoutParams)object.getLayoutParams();
        if (!layoutParams.mBehaviorResolved) {
            DefaultBehavior defaultBehavior;
            Class<?> clazz = object.getClass();
            Object var1_2 = null;
            while (true) {
                DefaultBehavior defaultBehavior2;
                void var1_3;
                defaultBehavior = var1_3;
                if (clazz == null) break;
                defaultBehavior = defaultBehavior2 = clazz.getAnnotation(DefaultBehavior.class);
                if (defaultBehavior2 != null) break;
                clazz = clazz.getSuperclass();
            }
            if (defaultBehavior != null) {
                try {
                    layoutParams.setBehavior(defaultBehavior.value().newInstance());
                }
                catch (Exception exception) {
                    Log.e((String)TAG, (String)("Default behavior class " + defaultBehavior.value().getName() + " could not be instantiated. Did you forget a default constructor?"), (Throwable)exception);
                }
            }
            layoutParams.mBehaviorResolved = true;
        }
        return layoutParams;
    }

    @Nullable
    public Drawable getStatusBarBackground() {
        return this.mStatusBarBackground;
    }

    protected int getSuggestedMinimumHeight() {
        return Math.max(super.getSuggestedMinimumHeight(), this.getPaddingTop() + this.getPaddingBottom());
    }

    protected int getSuggestedMinimumWidth() {
        return Math.max(super.getSuggestedMinimumWidth(), this.getPaddingLeft() + this.getPaddingRight());
    }

    /*
     * Enabled aggressive block sorting
     */
    boolean hasDependencies(View view) {
        LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
        if (layoutParams.mAnchorView != null) {
            return true;
        }
        int n2 = this.getChildCount();
        int n3 = 0;
        while (n3 < n2) {
            View view2 = this.getChildAt(n3);
            if (view2 != view && layoutParams.dependsOn(this, view, view2)) {
                return true;
            }
            ++n3;
        }
        return false;
    }

    public boolean isPointInChildBounds(View view, int n2, int n3) {
        Rect rect = this.mTempRect1;
        this.getDescendantRect(view, rect);
        return rect.contains(n2, n3);
    }

    void offsetChildToAnchor(View view, int n2) {
        LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
        if (layoutParams.mAnchorView != null) {
            Object object = this.mTempRect1;
            Rect rect = this.mTempRect2;
            Rect rect2 = this.mTempRect3;
            this.getDescendantRect(layoutParams.mAnchorView, (Rect)object);
            this.getChildRect(view, false, rect);
            this.getDesiredAnchoredChildRect(view, n2, (Rect)object, rect2);
            n2 = rect2.left - rect.left;
            int n3 = rect2.top - rect.top;
            if (n2 != 0) {
                view.offsetLeftAndRight(n2);
            }
            if (n3 != 0) {
                view.offsetTopAndBottom(n3);
            }
            if ((n2 != 0 || n3 != 0) && (object = layoutParams.getBehavior()) != null) {
                ((Behavior)object).onDependentViewChanged(this, view, layoutParams.mAnchorView);
            }
        }
    }

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.resetTouchBehaviors();
        if (this.mNeedsPreDrawListener) {
            if (this.mOnPreDrawListener == null) {
                this.mOnPreDrawListener = new OnPreDrawListener();
            }
            this.getViewTreeObserver().addOnPreDrawListener((ViewTreeObserver.OnPreDrawListener)this.mOnPreDrawListener);
        }
        if (this.mLastInsets == null && ViewCompat.getFitsSystemWindows((View)this)) {
            ViewCompat.requestApplyInsets((View)this);
        }
        this.mIsAttachedToWindow = true;
    }

    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.resetTouchBehaviors();
        if (this.mNeedsPreDrawListener && this.mOnPreDrawListener != null) {
            this.getViewTreeObserver().removeOnPreDrawListener((ViewTreeObserver.OnPreDrawListener)this.mOnPreDrawListener);
        }
        if (this.mNestedScrollingTarget != null) {
            this.onStopNestedScroll(this.mNestedScrollingTarget);
        }
        this.mIsAttachedToWindow = false;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!this.mDrawStatusBarBackground) return;
        if (this.mStatusBarBackground == null) return;
        if (this.mLastInsets == null) return;
        int n2 = this.mLastInsets.getSystemWindowInsetTop();
        if (n2 <= 0) return;
        this.mStatusBarBackground.setBounds(0, 0, this.getWidth(), n2);
        this.mStatusBarBackground.draw(canvas);
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        int n2 = MotionEventCompat.getActionMasked(motionEvent);
        if (n2 == 0) {
            this.resetTouchBehaviors();
        }
        boolean bl2 = this.performIntercept(motionEvent, 0);
        if (false) {
            throw new NullPointerException();
        }
        if (n2 == 1 || n2 == 3) {
            this.resetTouchBehaviors();
        }
        return bl2;
    }

    protected void onLayout(boolean bl2, int n2, int n3, int n4, int n5) {
        n3 = ViewCompat.getLayoutDirection((View)this);
        n4 = this.mDependencySortedChildren.size();
        for (n2 = 0; n2 < n4; ++n2) {
            View view = this.mDependencySortedChildren.get(n2);
            Behavior behavior = ((LayoutParams)view.getLayoutParams()).getBehavior();
            if (behavior != null && behavior.onLayoutChild(this, view, n3)) continue;
            this.onLayoutChild(view, n3);
        }
    }

    public void onLayoutChild(View view, int n2) {
        LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
        if (layoutParams.checkAnchorChanged()) {
            throw new IllegalStateException("An anchor may not be changed after CoordinatorLayout measurement begins before layout is complete.");
        }
        if (layoutParams.mAnchorView != null) {
            this.layoutChildWithAnchor(view, layoutParams.mAnchorView, n2);
            return;
        }
        if (layoutParams.keyline >= 0) {
            this.layoutChildWithKeyline(view, layoutParams.keyline, n2);
            return;
        }
        this.layoutChild(view, n2);
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void onMeasure(int n2, int n3) {
        this.prepareChildren();
        this.ensurePreDrawListener();
        int n4 = this.getPaddingLeft();
        int n5 = this.getPaddingTop();
        int n6 = this.getPaddingRight();
        int n7 = this.getPaddingBottom();
        int n8 = ViewCompat.getLayoutDirection((View)this);
        boolean bl2 = n8 == 1;
        int n9 = View.MeasureSpec.getMode((int)n2);
        int n10 = View.MeasureSpec.getSize((int)n2);
        int n11 = View.MeasureSpec.getMode((int)n3);
        int n12 = View.MeasureSpec.getSize((int)n3);
        int n13 = this.getSuggestedMinimumWidth();
        int n14 = this.getSuggestedMinimumHeight();
        int n15 = 0;
        boolean bl3 = this.mLastInsets != null && ViewCompat.getFitsSystemWindows((View)this);
        int n16 = this.mDependencySortedChildren.size();
        int n17 = 0;
        while (true) {
            Behavior behavior;
            int n18;
            int n19;
            int n20;
            int n21;
            LayoutParams layoutParams;
            View view;
            block8: {
                block10: {
                    block9: {
                        if (n17 >= n16) {
                            this.setMeasuredDimension(ViewCompat.resolveSizeAndState(n13, n2, 0xFF000000 & n15), ViewCompat.resolveSizeAndState(n14, n3, n15 << 16));
                            return;
                        }
                        view = this.mDependencySortedChildren.get(n17);
                        layoutParams = (LayoutParams)view.getLayoutParams();
                        n20 = n21 = 0;
                        if (layoutParams.keyline < 0) break block8;
                        n20 = n21;
                        if (n9 == 0) break block8;
                        n19 = this.getKeyline(layoutParams.keyline);
                        n18 = GravityCompat.getAbsoluteGravity(CoordinatorLayout.resolveKeylineGravity(layoutParams.gravity), n8) & 7;
                        if ((n18 != 3 || bl2) && (n18 != 5 || !bl2)) break block9;
                        n20 = Math.max(0, n10 - n6 - n19);
                        break block8;
                    }
                    if (n18 == 5 && !bl2) break block10;
                    n20 = n21;
                    if (n18 != 3) break block8;
                    n20 = n21;
                    if (!bl2) break block8;
                }
                n20 = Math.max(0, n19 - n4);
            }
            n19 = n2;
            n18 = n3;
            int n22 = n19;
            n21 = n18;
            if (bl3) {
                n22 = n19;
                n21 = n18;
                if (!ViewCompat.getFitsSystemWindows(view)) {
                    n18 = this.mLastInsets.getSystemWindowInsetLeft();
                    n22 = this.mLastInsets.getSystemWindowInsetRight();
                    n21 = this.mLastInsets.getSystemWindowInsetTop();
                    n19 = this.mLastInsets.getSystemWindowInsetBottom();
                    n22 = View.MeasureSpec.makeMeasureSpec((int)(n10 - (n18 + n22)), (int)n9);
                    n21 = View.MeasureSpec.makeMeasureSpec((int)(n12 - (n21 + n19)), (int)n11);
                }
            }
            if ((behavior = layoutParams.getBehavior()) == null || !behavior.onMeasureChild(this, view, n22, n20, n21, 0)) {
                this.onMeasureChild(view, n22, n20, n21, 0);
            }
            n13 = Math.max(n13, view.getMeasuredWidth() + (n4 + n6) + layoutParams.leftMargin + layoutParams.rightMargin);
            n14 = Math.max(n14, view.getMeasuredHeight() + (n5 + n7) + layoutParams.topMargin + layoutParams.bottomMargin);
            n15 = ViewCompat.combineMeasuredStates(n15, ViewCompat.getMeasuredState(view));
            ++n17;
        }
    }

    public void onMeasureChild(View view, int n2, int n3, int n4, int n5) {
        this.measureChildWithMargins(view, n2, n3, n4, n5);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public boolean onNestedFling(View view, float f2, float f3, boolean bl2) {
        boolean bl3 = false;
        int n2 = this.getChildCount();
        for (int i2 = 0; i2 < n2; ++i2) {
            boolean bl4;
            View view2 = this.getChildAt(i2);
            LayoutParams layoutParams = (LayoutParams)view2.getLayoutParams();
            if (!layoutParams.isNestedScrollAccepted()) {
                bl4 = bl3;
            } else {
                Behavior behavior = layoutParams.getBehavior();
                bl4 = bl3;
                if (behavior != null) {
                    bl4 = bl3 | behavior.onNestedFling(this, view2, view, f2, f3, bl2);
                }
            }
            bl3 = bl4;
        }
        if (bl3) {
            this.dispatchOnDependentViewChanged(true);
        }
        return bl3;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public boolean onNestedPreFling(View view, float f2, float f3) {
        boolean bl2 = false;
        int n2 = this.getChildCount();
        int n3 = 0;
        while (n3 < n2) {
            boolean bl3;
            View view2 = this.getChildAt(n3);
            LayoutParams layoutParams = (LayoutParams)view2.getLayoutParams();
            if (!layoutParams.isNestedScrollAccepted()) {
                bl3 = bl2;
            } else {
                Behavior behavior = layoutParams.getBehavior();
                bl3 = bl2;
                if (behavior != null) {
                    bl3 = bl2 | behavior.onNestedPreFling(this, view2, view, f2, f3);
                }
            }
            ++n3;
            bl2 = bl3;
        }
        return bl2;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void onNestedPreScroll(View view, int n2, int n3, int[] nArray) {
        int n4 = 0;
        int n5 = 0;
        boolean bl2 = false;
        int n6 = this.getChildCount();
        for (int i2 = 0; i2 < n6; ++i2) {
            int n7;
            int n8;
            View view2 = this.getChildAt(i2);
            LayoutParams layoutParams = (LayoutParams)view2.getLayoutParams();
            if (!layoutParams.isNestedScrollAccepted()) {
                n8 = n5;
                n7 = n4;
            } else {
                Behavior behavior = layoutParams.getBehavior();
                n7 = n4;
                n8 = n5;
                if (behavior != null) {
                    int[] nArray2 = this.mTempIntPair;
                    this.mTempIntPair[1] = 0;
                    nArray2[0] = 0;
                    behavior.onNestedPreScroll(this, view2, view, n2, n3, this.mTempIntPair);
                    n4 = n2 > 0 ? Math.max(n4, this.mTempIntPair[0]) : Math.min(n4, this.mTempIntPair[0]);
                    n5 = n3 > 0 ? Math.max(n5, this.mTempIntPair[1]) : Math.min(n5, this.mTempIntPair[1]);
                    bl2 = true;
                    n7 = n4;
                    n8 = n5;
                }
            }
            n4 = n7;
            n5 = n8;
        }
        nArray[0] = n4;
        nArray[1] = n5;
        if (bl2) {
            this.dispatchOnDependentViewChanged(true);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void onNestedScroll(View view, int n2, int n3, int n4, int n5) {
        int n6 = this.getChildCount();
        boolean bl2 = false;
        for (int i2 = 0; i2 < n6; ++i2) {
            Behavior behavior;
            View view2 = this.getChildAt(i2);
            LayoutParams layoutParams = (LayoutParams)view2.getLayoutParams();
            if (!layoutParams.isNestedScrollAccepted() || (behavior = layoutParams.getBehavior()) == null) continue;
            behavior.onNestedScroll(this, view2, view, n2, n3, n4, n5);
            bl2 = true;
        }
        if (bl2) {
            this.dispatchOnDependentViewChanged(true);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void onNestedScrollAccepted(View view, View view2, int n2) {
        this.mNestedScrollingParentHelper.onNestedScrollAccepted(view, view2, n2);
        this.mNestedScrollingDirectChild = view;
        this.mNestedScrollingTarget = view2;
        int n3 = this.getChildCount();
        int n4 = 0;
        while (n4 < n3) {
            Behavior behavior;
            View view3 = this.getChildAt(n4);
            LayoutParams layoutParams = (LayoutParams)view3.getLayoutParams();
            if (layoutParams.isNestedScrollAccepted() && (behavior = layoutParams.getBehavior()) != null) {
                behavior.onNestedScrollAccepted(this, view3, view, view2, n2);
            }
            ++n4;
        }
        return;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void onRestoreInstanceState(Parcelable object) {
        if (!(object instanceof SavedState)) {
            super.onRestoreInstanceState(object);
            return;
        } else {
            object = (SavedState)((Object)object);
            super.onRestoreInstanceState(object.getSuperState());
            object = object.behaviorStates;
            int n2 = this.getChildCount();
            for (int i2 = 0; i2 < n2; ++i2) {
                Parcelable parcelable;
                View view = this.getChildAt(i2);
                int n3 = view.getId();
                Behavior behavior = this.getResolvedLayoutParams(view).getBehavior();
                if (n3 == -1 || behavior == null || (parcelable = (Parcelable)object.get(n3)) == null) continue;
                behavior.onRestoreInstanceState(this, view, parcelable);
            }
        }
    }

    protected Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        SparseArray sparseArray = new SparseArray();
        int n2 = this.getChildCount();
        for (int i2 = 0; i2 < n2; ++i2) {
            View view = this.getChildAt(i2);
            int n3 = view.getId();
            Behavior behavior = ((LayoutParams)view.getLayoutParams()).getBehavior();
            if (n3 == -1 || behavior == null || (view = behavior.onSaveInstanceState(this, view)) == null) continue;
            sparseArray.append(n3, (Object)view);
        }
        savedState.behaviorStates = sparseArray;
        return savedState;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public boolean onStartNestedScroll(View view, View view2, int n2) {
        boolean bl2 = false;
        int n3 = this.getChildCount();
        int n4 = 0;
        while (n4 < n3) {
            View view3 = this.getChildAt(n4);
            LayoutParams layoutParams = (LayoutParams)view3.getLayoutParams();
            Behavior behavior = layoutParams.getBehavior();
            if (behavior != null) {
                boolean bl3 = behavior.onStartNestedScroll(this, view3, view, view2, n2);
                bl2 |= bl3;
                layoutParams.acceptNestedScroll(bl3);
            } else {
                layoutParams.acceptNestedScroll(false);
            }
            ++n4;
        }
        return bl2;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void onStopNestedScroll(View view) {
        this.mNestedScrollingParentHelper.onStopNestedScroll(view);
        int n2 = this.getChildCount();
        int n3 = 0;
        while (true) {
            if (n3 >= n2) {
                this.mNestedScrollingDirectChild = null;
                this.mNestedScrollingTarget = null;
                return;
            }
            View view2 = this.getChildAt(n3);
            LayoutParams layoutParams = (LayoutParams)view2.getLayoutParams();
            if (layoutParams.isNestedScrollAccepted()) {
                Behavior behavior = layoutParams.getBehavior();
                if (behavior != null) {
                    behavior.onStopNestedScroll(this, view2, view);
                }
                layoutParams.resetNestedScroll();
                layoutParams.resetChangedAfterNestedScroll();
            }
            ++n3;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean onTouchEvent(MotionEvent motionEvent) {
        boolean bl2;
        boolean bl3;
        int n2;
        Object var10_5;
        Object var9_4;
        boolean bl4;
        block11: {
            boolean bl5;
            block10: {
                bl5 = false;
                bl4 = false;
                var9_4 = null;
                var10_5 = null;
                n2 = MotionEventCompat.getActionMasked(motionEvent);
                if (this.mBehaviorTouchView != null) break block10;
                bl3 = bl4 = this.performIntercept(motionEvent, 1);
                bl2 = bl5;
                if (!bl4) break block11;
            }
            Behavior behavior = ((LayoutParams)this.mBehaviorTouchView.getLayoutParams()).getBehavior();
            bl3 = bl4;
            bl2 = bl5;
            if (behavior != null) {
                bl2 = behavior.onTouchEvent(this, this.mBehaviorTouchView, motionEvent);
                bl3 = bl4;
            }
        }
        if (this.mBehaviorTouchView == null) {
            bl4 = bl2 | super.onTouchEvent(motionEvent);
            motionEvent = var10_5;
        } else {
            motionEvent = var10_5;
            bl4 = bl2;
            if (bl3) {
                motionEvent = var9_4;
                long l2 = SystemClock.uptimeMillis();
                motionEvent = MotionEvent.obtain((long)l2, (long)l2, (int)3, (float)0.0f, (float)0.0f, (int)0);
                super.onTouchEvent(motionEvent);
                bl4 = bl2;
            }
        }
        if (bl4 || n2 == 0) {
            // empty if block
        }
        if (motionEvent != null) {
            motionEvent.recycle();
        }
        if (n2 == 1 || n2 == 3) {
            this.resetTouchBehaviors();
        }
        return bl4;
    }

    void recordLastChildRect(View view, Rect rect) {
        ((LayoutParams)view.getLayoutParams()).setLastChildRect(rect);
    }

    void removePreDrawListener() {
        if (this.mIsAttachedToWindow && this.mOnPreDrawListener != null) {
            this.getViewTreeObserver().removeOnPreDrawListener((ViewTreeObserver.OnPreDrawListener)this.mOnPreDrawListener);
        }
        this.mNeedsPreDrawListener = false;
    }

    public void requestDisallowInterceptTouchEvent(boolean bl2) {
        super.requestDisallowInterceptTouchEvent(bl2);
        if (bl2 && !this.mDisallowInterceptReset) {
            this.resetTouchBehaviors();
            this.mDisallowInterceptReset = true;
        }
    }

    public void setOnHierarchyChangeListener(ViewGroup.OnHierarchyChangeListener onHierarchyChangeListener) {
        this.mOnHierarchyChangeListener = onHierarchyChangeListener;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setStatusBarBackground(@Nullable Drawable drawable2) {
        Drawable drawable3 = null;
        if (this.mStatusBarBackground == drawable2) return;
        if (this.mStatusBarBackground != null) {
            this.mStatusBarBackground.setCallback(null);
        }
        if (drawable2 != null) {
            drawable3 = drawable2.mutate();
        }
        this.mStatusBarBackground = drawable3;
        if (this.mStatusBarBackground != null) {
            if (this.mStatusBarBackground.isStateful()) {
                this.mStatusBarBackground.setState(this.getDrawableState());
            }
            DrawableCompat.setLayoutDirection((Drawable)this.mStatusBarBackground, (int)ViewCompat.getLayoutDirection((View)this));
            drawable2 = this.mStatusBarBackground;
            boolean bl2 = this.getVisibility() == 0;
            drawable2.setVisible(bl2, false);
            this.mStatusBarBackground.setCallback((Drawable.Callback)this);
        }
        ViewCompat.postInvalidateOnAnimation((View)this);
    }

    public void setStatusBarBackgroundColor(@ColorInt int n2) {
        this.setStatusBarBackground((Drawable)new ColorDrawable(n2));
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setStatusBarBackgroundResource(@DrawableRes int n2) {
        Drawable drawable2 = n2 != 0 ? ContextCompat.getDrawable(this.getContext(), n2) : null;
        this.setStatusBarBackground(drawable2);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setVisibility(int n2) {
        super.setVisibility(n2);
        boolean bl2 = n2 == 0;
        if (this.mStatusBarBackground != null && this.mStatusBarBackground.isVisible() != bl2) {
            this.mStatusBarBackground.setVisible(bl2, false);
        }
    }

    protected boolean verifyDrawable(Drawable drawable2) {
        return super.verifyDrawable(drawable2) || drawable2 == this.mStatusBarBackground;
    }

    private class ApplyInsetsListener
    implements OnApplyWindowInsetsListener {
        private ApplyInsetsListener() {
        }

        @Override
        public WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat) {
            return CoordinatorLayout.this.setWindowInsets(windowInsetsCompat);
        }
    }

    public static abstract class Behavior<V extends View> {
        public Behavior() {
        }

        public Behavior(Context context, AttributeSet attributeSet) {
        }

        public static Object getTag(View view) {
            return ((LayoutParams)view.getLayoutParams()).mBehaviorTag;
        }

        public static void setTag(View view, Object object) {
            ((LayoutParams)view.getLayoutParams()).mBehaviorTag = object;
        }

        public boolean blocksInteractionBelow(CoordinatorLayout coordinatorLayout, V v2) {
            return this.getScrimOpacity(coordinatorLayout, v2) > 0.0f;
        }

        public int getScrimColor(CoordinatorLayout coordinatorLayout, V v2) {
            return -16777216;
        }

        public float getScrimOpacity(CoordinatorLayout coordinatorLayout, V v2) {
            return 0.0f;
        }

        public boolean isDirty(CoordinatorLayout coordinatorLayout, V v2) {
            return false;
        }

        public boolean layoutDependsOn(CoordinatorLayout coordinatorLayout, V v2, View view) {
            return false;
        }

        public WindowInsetsCompat onApplyWindowInsets(CoordinatorLayout coordinatorLayout, V v2, WindowInsetsCompat windowInsetsCompat) {
            return windowInsetsCompat;
        }

        public boolean onDependentViewChanged(CoordinatorLayout coordinatorLayout, V v2, View view) {
            return false;
        }

        public void onDependentViewRemoved(CoordinatorLayout coordinatorLayout, V v2, View view) {
        }

        public boolean onInterceptTouchEvent(CoordinatorLayout coordinatorLayout, V v2, MotionEvent motionEvent) {
            return false;
        }

        public boolean onLayoutChild(CoordinatorLayout coordinatorLayout, V v2, int n2) {
            return false;
        }

        public boolean onMeasureChild(CoordinatorLayout coordinatorLayout, V v2, int n2, int n3, int n4, int n5) {
            return false;
        }

        public boolean onNestedFling(CoordinatorLayout coordinatorLayout, V v2, View view, float f2, float f3, boolean bl2) {
            return false;
        }

        public boolean onNestedPreFling(CoordinatorLayout coordinatorLayout, V v2, View view, float f2, float f3) {
            return false;
        }

        public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, V v2, View view, int n2, int n3, int[] nArray) {
        }

        public void onNestedScroll(CoordinatorLayout coordinatorLayout, V v2, View view, int n2, int n3, int n4, int n5) {
        }

        public void onNestedScrollAccepted(CoordinatorLayout coordinatorLayout, V v2, View view, View view2, int n2) {
        }

        public void onRestoreInstanceState(CoordinatorLayout coordinatorLayout, V v2, Parcelable parcelable) {
        }

        public Parcelable onSaveInstanceState(CoordinatorLayout coordinatorLayout, V v2) {
            return View.BaseSavedState.EMPTY_STATE;
        }

        public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, V v2, View view, View view2, int n2) {
            return false;
        }

        public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, V v2, View view) {
        }

        public boolean onTouchEvent(CoordinatorLayout coordinatorLayout, V v2, MotionEvent motionEvent) {
            return false;
        }
    }

    @Retention(value=RetentionPolicy.RUNTIME)
    public static @interface DefaultBehavior {
        public Class<? extends Behavior> value();
    }

    private class HierarchyChangeListener
    implements ViewGroup.OnHierarchyChangeListener {
        private HierarchyChangeListener() {
        }

        public void onChildViewAdded(View view, View view2) {
            if (CoordinatorLayout.this.mOnHierarchyChangeListener != null) {
                CoordinatorLayout.this.mOnHierarchyChangeListener.onChildViewAdded(view, view2);
            }
        }

        public void onChildViewRemoved(View view, View view2) {
            CoordinatorLayout.this.dispatchDependentViewRemoved(view2);
            if (CoordinatorLayout.this.mOnHierarchyChangeListener != null) {
                CoordinatorLayout.this.mOnHierarchyChangeListener.onChildViewRemoved(view, view2);
            }
        }
    }

    public static class LayoutParams
    extends ViewGroup.MarginLayoutParams {
        public int anchorGravity = 0;
        public int gravity = 0;
        public int keyline = -1;
        View mAnchorDirectChild;
        int mAnchorId = -1;
        View mAnchorView;
        Behavior mBehavior;
        boolean mBehaviorResolved = false;
        Object mBehaviorTag;
        private boolean mDidAcceptNestedScroll;
        private boolean mDidBlockInteraction;
        private boolean mDidChangeAfterNestedScroll;
        final Rect mLastChildRect = new Rect();

        public LayoutParams(int n2, int n3) {
            super(n2, n3);
        }

        LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.CoordinatorLayout_LayoutParams);
            this.gravity = typedArray.getInteger(R.styleable.CoordinatorLayout_LayoutParams_android_layout_gravity, 0);
            this.mAnchorId = typedArray.getResourceId(R.styleable.CoordinatorLayout_LayoutParams_layout_anchor, -1);
            this.anchorGravity = typedArray.getInteger(R.styleable.CoordinatorLayout_LayoutParams_layout_anchorGravity, 0);
            this.keyline = typedArray.getInteger(R.styleable.CoordinatorLayout_LayoutParams_layout_keyline, -1);
            this.mBehaviorResolved = typedArray.hasValue(R.styleable.CoordinatorLayout_LayoutParams_layout_behavior);
            if (this.mBehaviorResolved) {
                this.mBehavior = CoordinatorLayout.parseBehavior(context, attributeSet, typedArray.getString(R.styleable.CoordinatorLayout_LayoutParams_layout_behavior));
            }
            typedArray.recycle();
        }

        public LayoutParams(LayoutParams layoutParams) {
            super((ViewGroup.MarginLayoutParams)layoutParams);
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public LayoutParams(ViewGroup.MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
        }

        private void resolveAnchorView(View view, CoordinatorLayout coordinatorLayout) {
            this.mAnchorView = coordinatorLayout.findViewById(this.mAnchorId);
            if (this.mAnchorView != null) {
                if (this.mAnchorView == coordinatorLayout) {
                    if (coordinatorLayout.isInEditMode()) {
                        this.mAnchorDirectChild = null;
                        this.mAnchorView = null;
                        return;
                    }
                    throw new IllegalStateException("View can not be anchored to the the parent CoordinatorLayout");
                }
                View view2 = this.mAnchorView;
                for (ViewParent viewParent = this.mAnchorView.getParent(); viewParent != coordinatorLayout && viewParent != null; viewParent = viewParent.getParent()) {
                    if (viewParent == view) {
                        if (coordinatorLayout.isInEditMode()) {
                            this.mAnchorDirectChild = null;
                            this.mAnchorView = null;
                            return;
                        }
                        throw new IllegalStateException("Anchor must not be a descendant of the anchored view");
                    }
                    if (!(viewParent instanceof View)) continue;
                    view2 = (View)viewParent;
                }
                this.mAnchorDirectChild = view2;
                return;
            }
            if (coordinatorLayout.isInEditMode()) {
                this.mAnchorDirectChild = null;
                this.mAnchorView = null;
                return;
            }
            throw new IllegalStateException("Could not find CoordinatorLayout descendant view with id " + coordinatorLayout.getResources().getResourceName(this.mAnchorId) + " to anchor view " + view);
        }

        private boolean verifyAnchorView(View view, CoordinatorLayout coordinatorLayout) {
            if (this.mAnchorView.getId() != this.mAnchorId) {
                return false;
            }
            View view2 = this.mAnchorView;
            for (ViewParent viewParent = this.mAnchorView.getParent(); viewParent != coordinatorLayout; viewParent = viewParent.getParent()) {
                if (viewParent == null || viewParent == view) {
                    this.mAnchorDirectChild = null;
                    this.mAnchorView = null;
                    return false;
                }
                if (!(viewParent instanceof View)) continue;
                view2 = (View)viewParent;
            }
            this.mAnchorDirectChild = view2;
            return true;
        }

        void acceptNestedScroll(boolean bl2) {
            this.mDidAcceptNestedScroll = bl2;
        }

        boolean checkAnchorChanged() {
            return this.mAnchorView == null && this.mAnchorId != -1;
        }

        boolean dependsOn(CoordinatorLayout coordinatorLayout, View view, View view2) {
            return view2 == this.mAnchorDirectChild || this.mBehavior != null && this.mBehavior.layoutDependsOn(coordinatorLayout, view, view2);
        }

        boolean didBlockInteraction() {
            if (this.mBehavior == null) {
                this.mDidBlockInteraction = false;
            }
            return this.mDidBlockInteraction;
        }

        View findAnchorView(CoordinatorLayout coordinatorLayout, View view) {
            if (this.mAnchorId == -1) {
                this.mAnchorDirectChild = null;
                this.mAnchorView = null;
                return null;
            }
            if (this.mAnchorView == null || !this.verifyAnchorView(view, coordinatorLayout)) {
                this.resolveAnchorView(view, coordinatorLayout);
            }
            return this.mAnchorView;
        }

        public int getAnchorId() {
            return this.mAnchorId;
        }

        public Behavior getBehavior() {
            return this.mBehavior;
        }

        boolean getChangedAfterNestedScroll() {
            return this.mDidChangeAfterNestedScroll;
        }

        Rect getLastChildRect() {
            return this.mLastChildRect;
        }

        void invalidateAnchor() {
            this.mAnchorDirectChild = null;
            this.mAnchorView = null;
        }

        /*
         * Enabled aggressive block sorting
         */
        boolean isBlockingInteractionBelow(CoordinatorLayout coordinatorLayout, View view) {
            if (this.mDidBlockInteraction) {
                return true;
            }
            boolean bl2 = this.mDidBlockInteraction;
            boolean bl3 = this.mBehavior != null ? this.mBehavior.blocksInteractionBelow(coordinatorLayout, view) : false;
            this.mDidBlockInteraction = bl3 |= bl2;
            return bl3;
        }

        boolean isDirty(CoordinatorLayout coordinatorLayout, View view) {
            return this.mBehavior != null && this.mBehavior.isDirty(coordinatorLayout, view);
        }

        boolean isNestedScrollAccepted() {
            return this.mDidAcceptNestedScroll;
        }

        void resetChangedAfterNestedScroll() {
            this.mDidChangeAfterNestedScroll = false;
        }

        void resetNestedScroll() {
            this.mDidAcceptNestedScroll = false;
        }

        void resetTouchBehaviorTracking() {
            this.mDidBlockInteraction = false;
        }

        public void setAnchorId(int n2) {
            this.invalidateAnchor();
            this.mAnchorId = n2;
        }

        public void setBehavior(Behavior behavior) {
            if (this.mBehavior != behavior) {
                this.mBehavior = behavior;
                this.mBehaviorTag = null;
                this.mBehaviorResolved = true;
            }
        }

        void setChangedAfterNestedScroll(boolean bl2) {
            this.mDidChangeAfterNestedScroll = bl2;
        }

        void setLastChildRect(Rect rect) {
            this.mLastChildRect.set(rect);
        }
    }

    class OnPreDrawListener
    implements ViewTreeObserver.OnPreDrawListener {
        OnPreDrawListener() {
        }

        public boolean onPreDraw() {
            CoordinatorLayout.this.dispatchOnDependentViewChanged(false);
            return true;
        }
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
        SparseArray<Parcelable> behaviorStates;

        public SavedState(Parcel parcelableArray, ClassLoader classLoader) {
            super((Parcel)parcelableArray);
            int n2 = parcelableArray.readInt();
            int[] nArray = new int[n2];
            parcelableArray.readIntArray(nArray);
            parcelableArray = parcelableArray.readParcelableArray(classLoader);
            this.behaviorStates = new SparseArray(n2);
            for (int i2 = 0; i2 < n2; ++i2) {
                this.behaviorStates.append(nArray[i2], (Object)parcelableArray[i2]);
            }
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        /*
         * Enabled aggressive block sorting
         */
        public void writeToParcel(Parcel parcel, int n2) {
            super.writeToParcel(parcel, n2);
            int n3 = this.behaviorStates != null ? this.behaviorStates.size() : 0;
            parcel.writeInt(n3);
            int[] nArray = new int[n3];
            Parcelable[] parcelableArray = new Parcelable[n3];
            int n4 = 0;
            while (true) {
                if (n4 >= n3) {
                    parcel.writeIntArray(nArray);
                    parcel.writeParcelableArray(parcelableArray, n2);
                    return;
                }
                nArray[n4] = this.behaviorStates.keyAt(n4);
                parcelableArray[n4] = (Parcelable)this.behaviorStates.valueAt(n4);
                ++n4;
            }
        }
    }

    static class ViewElevationComparator
    implements Comparator<View> {
        ViewElevationComparator() {
        }

        @Override
        public int compare(View view, View view2) {
            float f2;
            float f3 = ViewCompat.getZ(view);
            if (f3 > (f2 = ViewCompat.getZ(view2))) {
                return -1;
            }
            if (f3 < f2) {
                return 1;
            }
            return 0;
        }
    }
}

