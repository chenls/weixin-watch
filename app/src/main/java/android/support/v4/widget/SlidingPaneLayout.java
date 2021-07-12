/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Bitmap
 *  android.graphics.Canvas
 *  android.graphics.ColorFilter
 *  android.graphics.Paint
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.PorterDuffColorFilter
 *  android.graphics.Rect
 *  android.graphics.drawable.Drawable
 *  android.os.Build$VERSION
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.util.AttributeSet
 *  android.util.Log
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.ViewConfiguration
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewGroup$MarginLayoutParams
 *  android.view.accessibility.AccessibilityEvent
 */
package android.support.v4.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.v4.os.ParcelableCompat;
import android.support.v4.os.ParcelableCompatCreatorCallbacks;
import android.support.v4.view.AbsSavedState;
import android.support.v4.view.AccessibilityDelegateCompat;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class SlidingPaneLayout
extends ViewGroup {
    private static final int DEFAULT_FADE_COLOR = -858993460;
    private static final int DEFAULT_OVERHANG_SIZE = 32;
    static final SlidingPanelLayoutImpl IMPL;
    private static final int MIN_FLING_VELOCITY = 400;
    private static final String TAG = "SlidingPaneLayout";
    private boolean mCanSlide;
    private int mCoveredFadeColor;
    final ViewDragHelper mDragHelper;
    private boolean mFirstLayout = true;
    private float mInitialMotionX;
    private float mInitialMotionY;
    boolean mIsUnableToDrag;
    private final int mOverhangSize;
    private PanelSlideListener mPanelSlideListener;
    private int mParallaxBy;
    private float mParallaxOffset;
    final ArrayList<DisableLayerRunnable> mPostedRunnables;
    boolean mPreservedOpenState;
    private Drawable mShadowDrawableLeft;
    private Drawable mShadowDrawableRight;
    float mSlideOffset;
    int mSlideRange;
    View mSlideableView;
    private int mSliderFadeColor = -858993460;
    private final Rect mTmpRect = new Rect();

    static {
        int n2 = Build.VERSION.SDK_INT;
        IMPL = n2 >= 17 ? new SlidingPanelLayoutImplJBMR1() : (n2 >= 16 ? new SlidingPanelLayoutImplJB() : new SlidingPanelLayoutImplBase());
    }

    public SlidingPaneLayout(Context context) {
        this(context, null);
    }

    public SlidingPaneLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public SlidingPaneLayout(Context context, AttributeSet attributeSet, int n2) {
        super(context, attributeSet, n2);
        this.mPostedRunnables = new ArrayList();
        float f2 = context.getResources().getDisplayMetrics().density;
        this.mOverhangSize = (int)(32.0f * f2 + 0.5f);
        ViewConfiguration.get((Context)context);
        this.setWillNotDraw(false);
        ViewCompat.setAccessibilityDelegate((View)this, new AccessibilityDelegate());
        ViewCompat.setImportantForAccessibility((View)this, 1);
        this.mDragHelper = ViewDragHelper.create(this, 0.5f, new DragHelperCallback());
        this.mDragHelper.setMinVelocity(400.0f * f2);
    }

    private boolean closePane(View view, int n2) {
        boolean bl2 = false;
        if (this.mFirstLayout || this.smoothSlideTo(0.0f, n2)) {
            this.mPreservedOpenState = false;
            bl2 = true;
        }
        return bl2;
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    private void dimChildView(View object, float f2, int n2) {
        void var3_4;
        void var2_3;
        LayoutParams layoutParams = (LayoutParams)object.getLayoutParams();
        if (var2_3 > 0.0f && var3_4 != false) {
            int n3 = (int)((float)((0xFF000000 & var3_4) >>> 24) * var2_3);
            if (layoutParams.dimPaint == null) {
                layoutParams.dimPaint = new Paint();
            }
            layoutParams.dimPaint.setColorFilter((ColorFilter)new PorterDuffColorFilter(n3 << 24 | 0xFFFFFF & var3_4, PorterDuff.Mode.SRC_OVER));
            if (ViewCompat.getLayerType(object) != 2) {
                ViewCompat.setLayerType(object, 2, layoutParams.dimPaint);
            }
            this.invalidateChildRegion((View)object);
            return;
        } else {
            if (ViewCompat.getLayerType(object) == 0) return;
            if (layoutParams.dimPaint != null) {
                layoutParams.dimPaint.setColorFilter(null);
            }
            DisableLayerRunnable disableLayerRunnable = new DisableLayerRunnable((View)object);
            this.mPostedRunnables.add(disableLayerRunnable);
            ViewCompat.postOnAnimation((View)this, disableLayerRunnable);
            return;
        }
    }

    private boolean openPane(View view, int n2) {
        if (this.mFirstLayout || this.smoothSlideTo(1.0f, n2)) {
            this.mPreservedOpenState = true;
            return true;
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void parallaxOtherViews(float f2) {
        int n2;
        boolean bl2 = this.isLayoutRtlSupport();
        LayoutParams layoutParams = (LayoutParams)this.mSlideableView.getLayoutParams();
        n2 = layoutParams.dimWhenOffset && (n2 = bl2 ? layoutParams.rightMargin : layoutParams.leftMargin) <= 0 ? 1 : 0;
        int n3 = this.getChildCount();
        int n4 = 0;
        while (n4 < n3) {
            layoutParams = this.getChildAt(n4);
            if (layoutParams != this.mSlideableView) {
                int n5;
                int n6 = (int)((1.0f - this.mParallaxOffset) * (float)this.mParallaxBy);
                this.mParallaxOffset = f2;
                n6 = n5 = n6 - (int)((1.0f - f2) * (float)this.mParallaxBy);
                if (bl2) {
                    n6 = -n5;
                }
                layoutParams.offsetLeftAndRight(n6);
                if (n2 != 0) {
                    float f3 = bl2 ? this.mParallaxOffset - 1.0f : 1.0f - this.mParallaxOffset;
                    this.dimChildView((View)layoutParams, f3, this.mCoveredFadeColor);
                }
            }
            ++n4;
        }
        return;
    }

    /*
     * Enabled aggressive block sorting
     */
    private static boolean viewIsOpaque(View view) {
        block6: {
            block5: {
                if (view.isOpaque()) break block5;
                if (Build.VERSION.SDK_INT >= 18) {
                    return false;
                }
                if ((view = view.getBackground()) == null) {
                    return false;
                }
                if (view.getOpacity() != -1) break block6;
            }
            return true;
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected boolean canScroll(View view, boolean bl2, int n2, int n3, int n4) {
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup)view;
            int n5 = view.getScrollX();
            int n6 = view.getScrollY();
            for (int i2 = viewGroup.getChildCount() - 1; i2 >= 0; --i2) {
                View view2 = viewGroup.getChildAt(i2);
                if (n3 + n5 < view2.getLeft() || n3 + n5 >= view2.getRight() || n4 + n6 < view2.getTop() || n4 + n6 >= view2.getBottom() || !this.canScroll(view2, true, n2, n3 + n5 - view2.getLeft(), n4 + n6 - view2.getTop())) continue;
                return true;
            }
        }
        if (bl2) {
            if (!this.isLayoutRtlSupport()) {
                n2 = -n2;
            }
            if (ViewCompat.canScrollHorizontally(view, n2)) {
                return true;
            }
        }
        return false;
    }

    @Deprecated
    public boolean canSlide() {
        return this.mCanSlide;
    }

    protected boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams && super.checkLayoutParams(layoutParams);
    }

    public boolean closePane() {
        return this.closePane(this.mSlideableView, 0);
    }

    public void computeScroll() {
        block3: {
            block2: {
                if (!this.mDragHelper.continueSettling(true)) break block2;
                if (this.mCanSlide) break block3;
                this.mDragHelper.abort();
            }
            return;
        }
        ViewCompat.postInvalidateOnAnimation((View)this);
    }

    void dispatchOnPanelClosed(View view) {
        if (this.mPanelSlideListener != null) {
            this.mPanelSlideListener.onPanelClosed(view);
        }
        this.sendAccessibilityEvent(32);
    }

    void dispatchOnPanelOpened(View view) {
        if (this.mPanelSlideListener != null) {
            this.mPanelSlideListener.onPanelOpened(view);
        }
        this.sendAccessibilityEvent(32);
    }

    void dispatchOnPanelSlide(View view) {
        if (this.mPanelSlideListener != null) {
            this.mPanelSlideListener.onPanelSlide(view, this.mSlideOffset);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void draw(Canvas canvas) {
        int n2;
        int n3;
        super.draw(canvas);
        Drawable drawable2 = this.isLayoutRtlSupport() ? this.mShadowDrawableRight : this.mShadowDrawableLeft;
        if (this.getChildCount() <= 1) return;
        View view = this.getChildAt(1);
        if (view == null) return;
        if (drawable2 == null) {
            return;
        }
        int n4 = view.getTop();
        int n5 = view.getBottom();
        int n6 = drawable2.getIntrinsicWidth();
        if (this.isLayoutRtlSupport()) {
            n3 = view.getRight();
            n2 = n3 + n6;
        } else {
            n2 = view.getLeft();
            n3 = n2 - n6;
        }
        drawable2.setBounds(n3, n4, n2, n5);
        drawable2.draw(canvas);
    }

    /*
     * Enabled aggressive block sorting
     */
    protected boolean drawChild(Canvas canvas, View view, long l2) {
        boolean bl2;
        LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
        int n2 = canvas.save(2);
        if (this.mCanSlide && !layoutParams.slideable && this.mSlideableView != null) {
            canvas.getClipBounds(this.mTmpRect);
            if (this.isLayoutRtlSupport()) {
                this.mTmpRect.left = Math.max(this.mTmpRect.left, this.mSlideableView.getRight());
            } else {
                this.mTmpRect.right = Math.min(this.mTmpRect.right, this.mSlideableView.getLeft());
            }
            canvas.clipRect(this.mTmpRect);
        }
        if (Build.VERSION.SDK_INT >= 11) {
            bl2 = super.drawChild(canvas, view, l2);
        } else if (layoutParams.dimWhenOffset && this.mSlideOffset > 0.0f) {
            Bitmap bitmap;
            if (!view.isDrawingCacheEnabled()) {
                view.setDrawingCacheEnabled(true);
            }
            if ((bitmap = view.getDrawingCache()) != null) {
                canvas.drawBitmap(bitmap, (float)view.getLeft(), (float)view.getTop(), layoutParams.dimPaint);
                bl2 = false;
            } else {
                Log.e((String)TAG, (String)("drawChild: child view " + view + " returned null drawing cache"));
                bl2 = super.drawChild(canvas, view, l2);
            }
        } else {
            if (view.isDrawingCacheEnabled()) {
                view.setDrawingCacheEnabled(false);
            }
            bl2 = super.drawChild(canvas, view, l2);
        }
        canvas.restoreToCount(n2);
        return bl2;
    }

    protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams();
    }

    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(this.getContext(), attributeSet);
    }

    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            return new LayoutParams((ViewGroup.MarginLayoutParams)layoutParams);
        }
        return new LayoutParams(layoutParams);
    }

    @ColorInt
    public int getCoveredFadeColor() {
        return this.mCoveredFadeColor;
    }

    public int getParallaxDistance() {
        return this.mParallaxBy;
    }

    @ColorInt
    public int getSliderFadeColor() {
        return this.mSliderFadeColor;
    }

    void invalidateChildRegion(View view) {
        IMPL.invalidateChildRegion(this, view);
    }

    /*
     * Enabled aggressive block sorting
     */
    boolean isDimmed(View object) {
        block3: {
            block2: {
                if (object == null) break block2;
                LayoutParams layoutParams = (LayoutParams)object.getLayoutParams();
                if (this.mCanSlide && layoutParams.dimWhenOffset && this.mSlideOffset > 0.0f) break block3;
            }
            return false;
        }
        return true;
    }

    boolean isLayoutRtlSupport() {
        return ViewCompat.getLayoutDirection((View)this) == 1;
    }

    public boolean isOpen() {
        return !this.mCanSlide || this.mSlideOffset == 1.0f;
    }

    public boolean isSlideable() {
        return this.mCanSlide;
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mFirstLayout = true;
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mFirstLayout = true;
        int n2 = this.mPostedRunnables.size();
        for (int i2 = 0; i2 < n2; ++i2) {
            this.mPostedRunnables.get(i2).run();
        }
        this.mPostedRunnables.clear();
    }

    /*
     * Handled duff style switch with additional control
     * Enabled aggressive block sorting
     */
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        boolean bl2;
        View view;
        int n2 = MotionEventCompat.getActionMasked(motionEvent);
        if (!this.mCanSlide && n2 == 0 && this.getChildCount() > 1 && (view = this.getChildAt(1)) != null) {
            boolean bl3 = !this.mDragHelper.isViewUnder(view, (int)motionEvent.getX(), (int)motionEvent.getY());
            this.mPreservedOpenState = bl3;
        }
        if (!this.mCanSlide || this.mIsUnableToDrag && n2 != 0) {
            this.mDragHelper.cancel();
            return super.onInterceptTouchEvent(motionEvent);
        }
        if (n2 == 3 || n2 == 1) {
            this.mDragHelper.cancel();
            return false;
        }
        boolean bl4 = bl2 = false;
        int n3 = Integer.MIN_VALUE;
        block5: do {
            switch (n3 == Integer.MIN_VALUE ? n2 : n3) {
                default: {
                    bl4 = bl2;
                    return this.mDragHelper.shouldInterceptTouchEvent(motionEvent) || bl4;
                }
                case 0: {
                    this.mIsUnableToDrag = false;
                    float f2 = motionEvent.getX();
                    float f3 = motionEvent.getY();
                    this.mInitialMotionX = f2;
                    this.mInitialMotionY = f3;
                    bl4 = bl2;
                    n3 = 1;
                    if (!this.mDragHelper.isViewUnder(this.mSlideableView, (int)f2, (int)f3)) continue block5;
                    bl4 = bl2;
                    n3 = 1;
                    if (!this.isDimmed(this.mSlideableView)) continue block5;
                    bl4 = true;
                    return this.mDragHelper.shouldInterceptTouchEvent(motionEvent) || bl4;
                }
                case 2: {
                    float f4 = motionEvent.getX();
                    float f5 = motionEvent.getY();
                    f4 = Math.abs(f4 - this.mInitialMotionX);
                    f5 = Math.abs(f5 - this.mInitialMotionY);
                    bl4 = bl2;
                    if (!(f4 > (float)this.mDragHelper.getTouchSlop())) return this.mDragHelper.shouldInterceptTouchEvent(motionEvent) || bl4;
                    bl4 = bl2;
                    if (!(f5 > f4)) return this.mDragHelper.shouldInterceptTouchEvent(motionEvent) || bl4;
                    this.mDragHelper.cancel();
                    this.mIsUnableToDrag = true;
                    return false;
                }
                case 1: 
            }
            return this.mDragHelper.shouldInterceptTouchEvent(motionEvent) || bl4;
        } while (true);
        return this.mDragHelper.shouldInterceptTouchEvent(motionEvent) || bl4;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void onLayout(boolean bl2, int n2, int n3, int n4, int n5) {
        boolean bl3 = this.isLayoutRtlSupport();
        if (bl3) {
            this.mDragHelper.setEdgeTrackingEnabled(2);
        } else {
            this.mDragHelper.setEdgeTrackingEnabled(1);
        }
        int n6 = n4 - n2;
        n2 = bl3 ? this.getPaddingRight() : this.getPaddingLeft();
        n4 = bl3 ? this.getPaddingLeft() : this.getPaddingRight();
        int n7 = this.getPaddingTop();
        int n8 = this.getChildCount();
        n5 = n2;
        if (this.mFirstLayout) {
            float f2 = this.mCanSlide && this.mPreservedOpenState ? 1.0f : 0.0f;
            this.mSlideOffset = f2;
        }
        int n9 = 0;
        n3 = n2;
        n2 = n5;
        for (n5 = n9; n5 < n8; ++n5) {
            View view = this.getChildAt(n5);
            if (view.getVisibility() == 8) continue;
            LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
            int n10 = view.getMeasuredWidth();
            int n11 = 0;
            if (layoutParams.slideable) {
                n9 = layoutParams.leftMargin;
                int n12 = layoutParams.rightMargin;
                this.mSlideRange = n12 = Math.min(n2, n6 - n4 - this.mOverhangSize) - n3 - (n9 + n12);
                n9 = bl3 ? layoutParams.rightMargin : layoutParams.leftMargin;
                bl2 = n3 + n9 + n12 + n10 / 2 > n6 - n4;
                layoutParams.dimWhenOffset = bl2;
                n12 = (int)((float)n12 * this.mSlideOffset);
                n3 += n12 + n9;
                this.mSlideOffset = (float)n12 / (float)this.mSlideRange;
                n9 = n11;
            } else if (this.mCanSlide && this.mParallaxBy != 0) {
                n9 = (int)((1.0f - this.mSlideOffset) * (float)this.mParallaxBy);
                n3 = n2;
            } else {
                n3 = n2;
                n9 = n11;
            }
            if (bl3) {
                n11 = n6 - n3 + n9;
                n9 = n11 - n10;
            } else {
                n9 = n3 - n9;
                n11 = n9 + n10;
            }
            view.layout(n9, n7, n11, n7 + view.getMeasuredHeight());
            n2 += view.getWidth();
        }
        if (this.mFirstLayout) {
            if (this.mCanSlide) {
                if (this.mParallaxBy != 0) {
                    this.parallaxOtherViews(this.mSlideOffset);
                }
                if (((LayoutParams)this.mSlideableView.getLayoutParams()).dimWhenOffset) {
                    this.dimChildView(this.mSlideableView, this.mSlideOffset, this.mSliderFadeColor);
                }
            } else {
                for (n2 = 0; n2 < n8; ++n2) {
                    this.dimChildView(this.getChildAt(n2), 0.0f, this.mSliderFadeColor);
                }
            }
            this.updateObscuredViewsVisibility(this.mSlideableView);
        }
        this.mFirstLayout = false;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void onMeasure(int n2, int n3) {
        int n4;
        LayoutParams layoutParams;
        View view;
        int n5;
        int n6;
        int n7;
        int n8;
        int n9;
        int n10;
        int n11;
        block27: {
            block25: {
                block26: {
                    n11 = View.MeasureSpec.getMode((int)n2);
                    n10 = View.MeasureSpec.getSize((int)n2);
                    n9 = View.MeasureSpec.getMode((int)n3);
                    n3 = View.MeasureSpec.getSize((int)n3);
                    if (n11 == 0x40000000) break block25;
                    if (!this.isInEditMode()) break block26;
                    if (n11 == Integer.MIN_VALUE) {
                        n8 = n10;
                        n2 = n3;
                        n7 = n9;
                        break block27;
                    } else {
                        n7 = n9;
                        n2 = n3;
                        n8 = n10;
                        if (n11 == 0) {
                            n8 = 300;
                            n7 = n9;
                            n2 = n3;
                        }
                    }
                    break block27;
                }
                throw new IllegalStateException("Width must have an exact value or MATCH_PARENT");
            }
            n7 = n9;
            n2 = n3;
            n8 = n10;
            if (n9 == 0) {
                if (!this.isInEditMode()) {
                    throw new IllegalStateException("Height must not be UNSPECIFIED");
                }
                n7 = n9;
                n2 = n3;
                n8 = n10;
                if (n9 == 0) {
                    n7 = Integer.MIN_VALUE;
                    n2 = 300;
                    n8 = n10;
                }
            }
        }
        n10 = 0;
        n3 = -1;
        switch (n7) {
            case 0x40000000: {
                n10 = n3 = n2 - this.getPaddingTop() - this.getPaddingBottom();
                break;
            }
            case -2147483648: {
                n3 = n2 - this.getPaddingTop() - this.getPaddingBottom();
            }
        }
        float f2 = 0.0f;
        boolean bl2 = false;
        n11 = n6 = n8 - this.getPaddingLeft() - this.getPaddingRight();
        int n12 = this.getChildCount();
        if (n12 > 2) {
            Log.e((String)TAG, (String)"onMeasure: More than two child views are not supported.");
        }
        this.mSlideableView = null;
        for (n5 = 0; n5 < n12; ++n5) {
            boolean bl3;
            block29: {
                float f3;
                block30: {
                    block28: {
                        view = this.getChildAt(n5);
                        layoutParams = (LayoutParams)view.getLayoutParams();
                        if (view.getVisibility() != 8) break block28;
                        layoutParams.dimWhenOffset = false;
                        n4 = n11;
                        n9 = n10;
                        bl3 = bl2;
                        break block29;
                    }
                    f3 = f2;
                    if (!(layoutParams.weight > 0.0f)) break block30;
                    f3 = f2 + layoutParams.weight;
                    bl3 = bl2;
                    n9 = n10;
                    f2 = f3;
                    n4 = n11;
                    if (layoutParams.width == 0) break block29;
                }
                n2 = layoutParams.leftMargin + layoutParams.rightMargin;
                n2 = layoutParams.width == -2 ? View.MeasureSpec.makeMeasureSpec((int)(n6 - n2), (int)Integer.MIN_VALUE) : (layoutParams.width == -1 ? View.MeasureSpec.makeMeasureSpec((int)(n6 - n2), (int)0x40000000) : View.MeasureSpec.makeMeasureSpec((int)layoutParams.width, (int)0x40000000));
                n9 = layoutParams.height == -2 ? View.MeasureSpec.makeMeasureSpec((int)n3, (int)Integer.MIN_VALUE) : (layoutParams.height == -1 ? View.MeasureSpec.makeMeasureSpec((int)n3, (int)0x40000000) : View.MeasureSpec.makeMeasureSpec((int)layoutParams.height, (int)0x40000000));
                view.measure(n2, n9);
                n9 = view.getMeasuredWidth();
                n4 = view.getMeasuredHeight();
                n2 = n10;
                if (n7 == Integer.MIN_VALUE) {
                    n2 = n10;
                    if (n4 > n10) {
                        n2 = Math.min(n4, n3);
                    }
                }
                bl3 = (n10 = n11 - n9) < 0;
                layoutParams.slideable = bl3;
                bl2 |= bl3;
                bl3 = bl2;
                n9 = n2;
                f2 = f3;
                n4 = n10;
                if (layoutParams.slideable) {
                    this.mSlideableView = view;
                    bl3 = bl2;
                    n9 = n2;
                    f2 = f3;
                    n4 = n10;
                }
            }
            bl2 = bl3;
            n10 = n9;
            n11 = n4;
        }
        if (bl2 || f2 > 0.0f) {
            n5 = n6 - this.mOverhangSize;
            for (n9 = 0; n9 < n12; ++n9) {
                view = this.getChildAt(n9);
                if (view.getVisibility() == 8) continue;
                layoutParams = (LayoutParams)view.getLayoutParams();
                if (view.getVisibility() == 8) continue;
                n2 = layoutParams.width == 0 && layoutParams.weight > 0.0f ? 1 : 0;
                n7 = n2 != 0 ? 0 : view.getMeasuredWidth();
                if (bl2 && view != this.mSlideableView) {
                    if (layoutParams.width >= 0 || n7 <= n5 && !(layoutParams.weight > 0.0f)) continue;
                    n2 = n2 != 0 ? (layoutParams.height == -2 ? View.MeasureSpec.makeMeasureSpec((int)n3, (int)Integer.MIN_VALUE) : (layoutParams.height == -1 ? View.MeasureSpec.makeMeasureSpec((int)n3, (int)0x40000000) : View.MeasureSpec.makeMeasureSpec((int)layoutParams.height, (int)0x40000000))) : View.MeasureSpec.makeMeasureSpec((int)view.getMeasuredHeight(), (int)0x40000000);
                    view.measure(View.MeasureSpec.makeMeasureSpec((int)n5, (int)0x40000000), n2);
                    continue;
                }
                if (!(layoutParams.weight > 0.0f)) continue;
                n2 = layoutParams.width == 0 ? (layoutParams.height == -2 ? View.MeasureSpec.makeMeasureSpec((int)n3, (int)Integer.MIN_VALUE) : (layoutParams.height == -1 ? View.MeasureSpec.makeMeasureSpec((int)n3, (int)0x40000000) : View.MeasureSpec.makeMeasureSpec((int)layoutParams.height, (int)0x40000000))) : View.MeasureSpec.makeMeasureSpec((int)view.getMeasuredHeight(), (int)0x40000000);
                if (bl2) {
                    n4 = n6 - (layoutParams.leftMargin + layoutParams.rightMargin);
                    int n13 = View.MeasureSpec.makeMeasureSpec((int)n4, (int)0x40000000);
                    if (n7 == n4) continue;
                    view.measure(n13, n2);
                    continue;
                }
                n4 = Math.max(0, n11);
                view.measure(View.MeasureSpec.makeMeasureSpec((int)(n7 + (int)(layoutParams.weight * (float)n4 / f2)), (int)0x40000000), n2);
            }
        }
        this.setMeasuredDimension(n8, this.getPaddingTop() + n10 + this.getPaddingBottom());
        this.mCanSlide = bl2;
        if (this.mDragHelper.getViewDragState() != 0 && !bl2) {
            this.mDragHelper.abort();
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    void onPanelDragged(int n2) {
        if (this.mSlideableView == null) {
            this.mSlideOffset = 0.0f;
            return;
        }
        boolean bl2 = this.isLayoutRtlSupport();
        LayoutParams layoutParams = (LayoutParams)this.mSlideableView.getLayoutParams();
        int n3 = this.mSlideableView.getWidth();
        if (bl2) {
            n2 = this.getWidth() - n2 - n3;
        }
        n3 = bl2 ? this.getPaddingRight() : this.getPaddingLeft();
        int n4 = bl2 ? layoutParams.rightMargin : layoutParams.leftMargin;
        this.mSlideOffset = (float)(n2 - (n3 + n4)) / (float)this.mSlideRange;
        if (this.mParallaxBy != 0) {
            this.parallaxOtherViews(this.mSlideOffset);
        }
        if (layoutParams.dimWhenOffset) {
            this.dimChildView(this.mSlideableView, this.mSlideOffset, this.mSliderFadeColor);
        }
        this.dispatchOnPanelSlide(this.mSlideableView);
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        parcelable = (SavedState)parcelable;
        super.onRestoreInstanceState(parcelable.getSuperState());
        if (parcelable.isOpen) {
            this.openPane();
        } else {
            this.closePane();
        }
        this.mPreservedOpenState = parcelable.isOpen;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        boolean bl2 = this.isSlideable() ? this.isOpen() : this.mPreservedOpenState;
        savedState.isOpen = bl2;
        return savedState;
    }

    protected void onSizeChanged(int n2, int n3, int n4, int n5) {
        super.onSizeChanged(n2, n3, n4, n5);
        if (n2 != n4) {
            this.mFirstLayout = true;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (!this.mCanSlide) {
            return super.onTouchEvent(motionEvent);
        }
        this.mDragHelper.processTouchEvent(motionEvent);
        int n2 = motionEvent.getAction();
        boolean bl2 = true;
        switch (n2 & 0xFF) {
            default: {
                return true;
            }
            case 0: {
                float f2 = motionEvent.getX();
                float f3 = motionEvent.getY();
                this.mInitialMotionX = f2;
                this.mInitialMotionY = f3;
                return true;
            }
            case 1: 
        }
        boolean bl3 = bl2;
        if (!this.isDimmed(this.mSlideableView)) return bl3;
        float f4 = motionEvent.getX();
        float f5 = motionEvent.getY();
        float f6 = f4 - this.mInitialMotionX;
        float f7 = f5 - this.mInitialMotionY;
        n2 = this.mDragHelper.getTouchSlop();
        bl3 = bl2;
        if (!(f6 * f6 + f7 * f7 < (float)(n2 * n2))) return bl3;
        bl3 = bl2;
        if (!this.mDragHelper.isViewUnder(this.mSlideableView, (int)f4, (int)f5)) return bl3;
        this.closePane(this.mSlideableView, 0);
        return true;
    }

    public boolean openPane() {
        return this.openPane(this.mSlideableView, 0);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void requestChildFocus(View view, View view2) {
        super.requestChildFocus(view, view2);
        if (!this.isInTouchMode() && !this.mCanSlide) {
            boolean bl2 = view == this.mSlideableView;
            this.mPreservedOpenState = bl2;
        }
    }

    void setAllChildrenVisible() {
        int n2 = this.getChildCount();
        for (int i2 = 0; i2 < n2; ++i2) {
            View view = this.getChildAt(i2);
            if (view.getVisibility() != 4) continue;
            view.setVisibility(0);
        }
    }

    public void setCoveredFadeColor(@ColorInt int n2) {
        this.mCoveredFadeColor = n2;
    }

    public void setPanelSlideListener(PanelSlideListener panelSlideListener) {
        this.mPanelSlideListener = panelSlideListener;
    }

    public void setParallaxDistance(int n2) {
        this.mParallaxBy = n2;
        this.requestLayout();
    }

    @Deprecated
    public void setShadowDrawable(Drawable drawable2) {
        this.setShadowDrawableLeft(drawable2);
    }

    public void setShadowDrawableLeft(Drawable drawable2) {
        this.mShadowDrawableLeft = drawable2;
    }

    public void setShadowDrawableRight(Drawable drawable2) {
        this.mShadowDrawableRight = drawable2;
    }

    @Deprecated
    public void setShadowResource(@DrawableRes int n2) {
        this.setShadowDrawable(this.getResources().getDrawable(n2));
    }

    public void setShadowResourceLeft(int n2) {
        this.setShadowDrawableLeft(this.getResources().getDrawable(n2));
    }

    public void setShadowResourceRight(int n2) {
        this.setShadowDrawableRight(this.getResources().getDrawable(n2));
    }

    public void setSliderFadeColor(@ColorInt int n2) {
        this.mSliderFadeColor = n2;
    }

    @Deprecated
    public void smoothSlideClosed() {
        this.closePane();
    }

    @Deprecated
    public void smoothSlideOpen() {
        this.openPane();
    }

    /*
     * Enabled aggressive block sorting
     */
    boolean smoothSlideTo(float f2, int n2) {
        block6: {
            block5: {
                if (!this.mCanSlide) break block5;
                boolean bl2 = this.isLayoutRtlSupport();
                LayoutParams layoutParams = (LayoutParams)this.mSlideableView.getLayoutParams();
                if (bl2) {
                    n2 = this.getPaddingRight();
                    int n3 = layoutParams.rightMargin;
                    int n4 = this.mSlideableView.getWidth();
                    n2 = (int)((float)this.getWidth() - ((float)(n2 + n3) + (float)this.mSlideRange * f2 + (float)n4));
                } else {
                    n2 = (int)((float)(this.getPaddingLeft() + layoutParams.leftMargin) + (float)this.mSlideRange * f2);
                }
                if (this.mDragHelper.smoothSlideViewTo(this.mSlideableView, n2, this.mSlideableView.getTop())) break block6;
            }
            return false;
        }
        this.setAllChildrenVisible();
        ViewCompat.postInvalidateOnAnimation((View)this);
        return true;
    }

    /*
     * Enabled aggressive block sorting
     */
    void updateObscuredViewsVisibility(View view) {
        int n2;
        int n3;
        int n4;
        int n5;
        boolean bl2 = this.isLayoutRtlSupport();
        int n6 = bl2 ? this.getWidth() - this.getPaddingRight() : this.getPaddingLeft();
        int n7 = bl2 ? this.getPaddingLeft() : this.getWidth() - this.getPaddingRight();
        int n8 = this.getPaddingTop();
        int n9 = this.getHeight();
        int n10 = this.getPaddingBottom();
        if (view != null && SlidingPaneLayout.viewIsOpaque(view)) {
            n5 = view.getLeft();
            n4 = view.getRight();
            n3 = view.getTop();
            n2 = view.getBottom();
        } else {
            n2 = 0;
            n3 = 0;
            n4 = 0;
            n5 = 0;
        }
        int n11 = 0;
        int n12 = this.getChildCount();
        View view2;
        while (n11 < n12 && (view2 = this.getChildAt(n11)) != view) {
            if (view2.getVisibility() != 8) {
                int n13 = bl2 ? n7 : n6;
                int n14 = Math.max(n13, view2.getLeft());
                int n15 = Math.max(n8, view2.getTop());
                n13 = bl2 ? n6 : n7;
                n13 = Math.min(n13, view2.getRight());
                int n16 = Math.min(n9 - n10, view2.getBottom());
                n13 = n14 >= n5 && n15 >= n3 && n13 <= n4 && n16 <= n2 ? 4 : 0;
                view2.setVisibility(n13);
            }
            ++n11;
        }
        return;
    }

    class AccessibilityDelegate
    extends AccessibilityDelegateCompat {
        private final Rect mTmpRect = new Rect();

        AccessibilityDelegate() {
        }

        private void copyNodeInfoNoChildren(AccessibilityNodeInfoCompat accessibilityNodeInfoCompat, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat2) {
            Rect rect = this.mTmpRect;
            accessibilityNodeInfoCompat2.getBoundsInParent(rect);
            accessibilityNodeInfoCompat.setBoundsInParent(rect);
            accessibilityNodeInfoCompat2.getBoundsInScreen(rect);
            accessibilityNodeInfoCompat.setBoundsInScreen(rect);
            accessibilityNodeInfoCompat.setVisibleToUser(accessibilityNodeInfoCompat2.isVisibleToUser());
            accessibilityNodeInfoCompat.setPackageName(accessibilityNodeInfoCompat2.getPackageName());
            accessibilityNodeInfoCompat.setClassName(accessibilityNodeInfoCompat2.getClassName());
            accessibilityNodeInfoCompat.setContentDescription(accessibilityNodeInfoCompat2.getContentDescription());
            accessibilityNodeInfoCompat.setEnabled(accessibilityNodeInfoCompat2.isEnabled());
            accessibilityNodeInfoCompat.setClickable(accessibilityNodeInfoCompat2.isClickable());
            accessibilityNodeInfoCompat.setFocusable(accessibilityNodeInfoCompat2.isFocusable());
            accessibilityNodeInfoCompat.setFocused(accessibilityNodeInfoCompat2.isFocused());
            accessibilityNodeInfoCompat.setAccessibilityFocused(accessibilityNodeInfoCompat2.isAccessibilityFocused());
            accessibilityNodeInfoCompat.setSelected(accessibilityNodeInfoCompat2.isSelected());
            accessibilityNodeInfoCompat.setLongClickable(accessibilityNodeInfoCompat2.isLongClickable());
            accessibilityNodeInfoCompat.addAction(accessibilityNodeInfoCompat2.getActions());
            accessibilityNodeInfoCompat.setMovementGranularities(accessibilityNodeInfoCompat2.getMovementGranularities());
        }

        public boolean filter(View view) {
            return SlidingPaneLayout.this.isDimmed(view);
        }

        @Override
        public void onInitializeAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
            super.onInitializeAccessibilityEvent(view, accessibilityEvent);
            accessibilityEvent.setClassName((CharSequence)SlidingPaneLayout.class.getName());
        }

        @Override
        public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            AccessibilityNodeInfoCompat accessibilityNodeInfoCompat2 = AccessibilityNodeInfoCompat.obtain(accessibilityNodeInfoCompat);
            super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat2);
            this.copyNodeInfoNoChildren(accessibilityNodeInfoCompat, accessibilityNodeInfoCompat2);
            accessibilityNodeInfoCompat2.recycle();
            accessibilityNodeInfoCompat.setClassName(SlidingPaneLayout.class.getName());
            accessibilityNodeInfoCompat.setSource(view);
            view = ViewCompat.getParentForAccessibility(view);
            if (view instanceof View) {
                accessibilityNodeInfoCompat.setParent(view);
            }
            int n2 = SlidingPaneLayout.this.getChildCount();
            for (int i2 = 0; i2 < n2; ++i2) {
                view = SlidingPaneLayout.this.getChildAt(i2);
                if (this.filter(view) || view.getVisibility() != 0) continue;
                ViewCompat.setImportantForAccessibility(view, 1);
                accessibilityNodeInfoCompat.addChild(view);
            }
        }

        @Override
        public boolean onRequestSendAccessibilityEvent(ViewGroup viewGroup, View view, AccessibilityEvent accessibilityEvent) {
            if (!this.filter(view)) {
                return super.onRequestSendAccessibilityEvent(viewGroup, view, accessibilityEvent);
            }
            return false;
        }
    }

    private class DisableLayerRunnable
    implements Runnable {
        final View mChildView;

        DisableLayerRunnable(View view) {
            this.mChildView = view;
        }

        @Override
        public void run() {
            if (this.mChildView.getParent() == SlidingPaneLayout.this) {
                ViewCompat.setLayerType(this.mChildView, 0, null);
                SlidingPaneLayout.this.invalidateChildRegion(this.mChildView);
            }
            SlidingPaneLayout.this.mPostedRunnables.remove(this);
        }
    }

    private class DragHelperCallback
    extends ViewDragHelper.Callback {
        DragHelperCallback() {
        }

        @Override
        public int clampViewPositionHorizontal(View object, int n2, int n3) {
            object = (LayoutParams)SlidingPaneLayout.this.mSlideableView.getLayoutParams();
            if (SlidingPaneLayout.this.isLayoutRtlSupport()) {
                n3 = SlidingPaneLayout.this.getWidth() - (SlidingPaneLayout.this.getPaddingRight() + object.rightMargin + SlidingPaneLayout.this.mSlideableView.getWidth());
                int n4 = SlidingPaneLayout.this.mSlideRange;
                return Math.max(Math.min(n2, n3), n3 - n4);
            }
            n3 = SlidingPaneLayout.this.getPaddingLeft() + object.leftMargin;
            int n5 = SlidingPaneLayout.this.mSlideRange;
            return Math.min(Math.max(n2, n3), n3 + n5);
        }

        @Override
        public int clampViewPositionVertical(View view, int n2, int n3) {
            return view.getTop();
        }

        @Override
        public int getViewHorizontalDragRange(View view) {
            return SlidingPaneLayout.this.mSlideRange;
        }

        @Override
        public void onEdgeDragStarted(int n2, int n3) {
            SlidingPaneLayout.this.mDragHelper.captureChildView(SlidingPaneLayout.this.mSlideableView, n3);
        }

        @Override
        public void onViewCaptured(View view, int n2) {
            SlidingPaneLayout.this.setAllChildrenVisible();
        }

        @Override
        public void onViewDragStateChanged(int n2) {
            block3: {
                block2: {
                    if (SlidingPaneLayout.this.mDragHelper.getViewDragState() != 0) break block2;
                    if (SlidingPaneLayout.this.mSlideOffset != 0.0f) break block3;
                    SlidingPaneLayout.this.updateObscuredViewsVisibility(SlidingPaneLayout.this.mSlideableView);
                    SlidingPaneLayout.this.dispatchOnPanelClosed(SlidingPaneLayout.this.mSlideableView);
                    SlidingPaneLayout.this.mPreservedOpenState = false;
                }
                return;
            }
            SlidingPaneLayout.this.dispatchOnPanelOpened(SlidingPaneLayout.this.mSlideableView);
            SlidingPaneLayout.this.mPreservedOpenState = true;
        }

        @Override
        public void onViewPositionChanged(View view, int n2, int n3, int n4, int n5) {
            SlidingPaneLayout.this.onPanelDragged(n2);
            SlidingPaneLayout.this.invalidate();
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public void onViewReleased(View view, float f2, float f3) {
            int n2;
            block7: {
                int n3;
                block8: {
                    LayoutParams layoutParams;
                    block4: {
                        int n4;
                        block6: {
                            block5: {
                                layoutParams = (LayoutParams)view.getLayoutParams();
                                if (!SlidingPaneLayout.this.isLayoutRtlSupport()) break block4;
                                n4 = SlidingPaneLayout.this.getPaddingRight() + layoutParams.rightMargin;
                                if (f2 < 0.0f) break block5;
                                n2 = n4;
                                if (f2 != 0.0f) break block6;
                                n2 = n4;
                                if (!(SlidingPaneLayout.this.mSlideOffset > 0.5f)) break block6;
                            }
                            n2 = n4 + SlidingPaneLayout.this.mSlideRange;
                        }
                        n4 = SlidingPaneLayout.this.mSlideableView.getWidth();
                        n2 = SlidingPaneLayout.this.getWidth() - n2 - n4;
                        break block7;
                    }
                    n3 = SlidingPaneLayout.this.getPaddingLeft() + layoutParams.leftMargin;
                    if (f2 > 0.0f) break block8;
                    n2 = n3;
                    if (f2 != 0.0f) break block7;
                    n2 = n3;
                    if (!(SlidingPaneLayout.this.mSlideOffset > 0.5f)) break block7;
                }
                n2 = n3 + SlidingPaneLayout.this.mSlideRange;
            }
            SlidingPaneLayout.this.mDragHelper.settleCapturedViewAt(n2, view.getTop());
            SlidingPaneLayout.this.invalidate();
        }

        @Override
        public boolean tryCaptureView(View view, int n2) {
            if (SlidingPaneLayout.this.mIsUnableToDrag) {
                return false;
            }
            return ((LayoutParams)view.getLayoutParams()).slideable;
        }
    }

    public static class LayoutParams
    extends ViewGroup.MarginLayoutParams {
        private static final int[] ATTRS = new int[]{0x1010181};
        Paint dimPaint;
        boolean dimWhenOffset;
        boolean slideable;
        public float weight = 0.0f;

        public LayoutParams() {
            super(-1, -1);
        }

        public LayoutParams(int n2, int n3) {
            super(n2, n3);
        }

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            context = context.obtainStyledAttributes(attributeSet, ATTRS);
            this.weight = context.getFloat(0, 0.0f);
            context.recycle();
        }

        public LayoutParams(LayoutParams layoutParams) {
            super((ViewGroup.MarginLayoutParams)layoutParams);
            this.weight = layoutParams.weight;
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public LayoutParams(ViewGroup.MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
        }
    }

    public static interface PanelSlideListener {
        public void onPanelClosed(View var1);

        public void onPanelOpened(View var1);

        public void onPanelSlide(View var1, float var2);
    }

    static class SavedState
    extends AbsSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = ParcelableCompat.newCreator(new ParcelableCompatCreatorCallbacks<SavedState>(){

            @Override
            public SavedState createFromParcel(Parcel parcel, ClassLoader classLoader) {
                return new SavedState(parcel, classLoader);
            }

            public SavedState[] newArray(int n2) {
                return new SavedState[n2];
            }
        });
        boolean isOpen;

        /*
         * Enabled aggressive block sorting
         */
        SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            boolean bl2 = parcel.readInt() != 0;
            this.isOpen = bl2;
        }

        SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public void writeToParcel(Parcel parcel, int n2) {
            super.writeToParcel(parcel, n2);
            n2 = this.isOpen ? 1 : 0;
            parcel.writeInt(n2);
        }
    }

    public static class SimplePanelSlideListener
    implements PanelSlideListener {
        @Override
        public void onPanelClosed(View view) {
        }

        @Override
        public void onPanelOpened(View view) {
        }

        @Override
        public void onPanelSlide(View view, float f2) {
        }
    }

    static interface SlidingPanelLayoutImpl {
        public void invalidateChildRegion(SlidingPaneLayout var1, View var2);
    }

    static class SlidingPanelLayoutImplBase
    implements SlidingPanelLayoutImpl {
        SlidingPanelLayoutImplBase() {
        }

        @Override
        public void invalidateChildRegion(SlidingPaneLayout slidingPaneLayout, View view) {
            ViewCompat.postInvalidateOnAnimation((View)slidingPaneLayout, view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
        }
    }

    static class SlidingPanelLayoutImplJB
    extends SlidingPanelLayoutImplBase {
        private Method mGetDisplayList;
        private Field mRecreateDisplayList;

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        SlidingPanelLayoutImplJB() {
            try {
                this.mGetDisplayList = View.class.getDeclaredMethod("getDisplayList", null);
            }
            catch (NoSuchMethodException noSuchMethodException) {
                Log.e((String)SlidingPaneLayout.TAG, (String)"Couldn't fetch getDisplayList method; dimming won't work right.", (Throwable)noSuchMethodException);
            }
            try {
                this.mRecreateDisplayList = View.class.getDeclaredField("mRecreateDisplayList");
                this.mRecreateDisplayList.setAccessible(true);
                return;
            }
            catch (NoSuchFieldException noSuchFieldException) {
                Log.e((String)SlidingPaneLayout.TAG, (String)"Couldn't fetch mRecreateDisplayList field; dimming will be slow.", (Throwable)noSuchFieldException);
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void invalidateChildRegion(SlidingPaneLayout slidingPaneLayout, View view) {
            if (this.mGetDisplayList != null && this.mRecreateDisplayList != null) {
                try {
                    this.mRecreateDisplayList.setBoolean(view, true);
                    this.mGetDisplayList.invoke(view, null);
                }
                catch (Exception exception) {
                    Log.e((String)SlidingPaneLayout.TAG, (String)"Error refreshing display list state", (Throwable)exception);
                }
                super.invalidateChildRegion(slidingPaneLayout, view);
                return;
            }
            view.invalidate();
        }
    }

    static class SlidingPanelLayoutImplJBMR1
    extends SlidingPanelLayoutImplBase {
        SlidingPanelLayoutImplJBMR1() {
        }

        @Override
        public void invalidateChildRegion(SlidingPaneLayout slidingPaneLayout, View view) {
            ViewCompat.setLayerPaint(view, ((LayoutParams)view.getLayoutParams()).dimPaint);
        }
    }
}

