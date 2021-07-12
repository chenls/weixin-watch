/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.graphics.Canvas
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.Rect
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.Drawable$Callback
 *  android.util.AttributeSet
 *  android.view.Gravity
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.ViewDebug$ExportedProperty
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.WindowInsets
 *  android.view.accessibility.AccessibilityEvent
 *  android.view.accessibility.AccessibilityNodeInfo
 *  android.widget.FrameLayout$LayoutParams
 *  android.widget.RemoteViews$RemoteView
 */
package android.support.wearable.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.wearable.R;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.FrameLayout;
import android.widget.RemoteViews;
import java.util.ArrayList;

@RemoteViews.RemoteView
@TargetApi(value=21)
public class WearableFrameLayout
extends ViewGroup {
    private static final int DEFAULT_CHILD_GRAVITY = 0x800033;
    private static final String TAG = "WearableFrameLayout";
    @ViewDebug.ExportedProperty(category="drawing")
    private Drawable mForeground;
    boolean mForegroundBoundsChanged = false;
    @ViewDebug.ExportedProperty(category="drawing")
    private int mForegroundGravity = 119;
    @ViewDebug.ExportedProperty(category="drawing")
    private boolean mForegroundInPadding = true;
    @ViewDebug.ExportedProperty(category="padding")
    private int mForegroundPaddingBottom = 0;
    @ViewDebug.ExportedProperty(category="padding")
    private int mForegroundPaddingLeft = 0;
    @ViewDebug.ExportedProperty(category="padding")
    private int mForegroundPaddingRight = 0;
    @ViewDebug.ExportedProperty(category="padding")
    private int mForegroundPaddingTop = 0;
    private ColorStateList mForegroundTintList = null;
    private PorterDuff.Mode mForegroundTintMode = null;
    private boolean mHasForegroundTint = false;
    private boolean mHasForegroundTintMode = false;
    private final ArrayList<View> mMatchParentChildren;
    @ViewDebug.ExportedProperty(category="measurement")
    boolean mMeasureAllChildren = false;
    private final Rect mOverlayBounds;
    private boolean mRound = false;
    private final Rect mSelfBounds = new Rect();

    public WearableFrameLayout(Context context) {
        super(context);
        this.mOverlayBounds = new Rect();
        this.mMatchParentChildren = new ArrayList(1);
    }

    public WearableFrameLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public WearableFrameLayout(Context context, AttributeSet attributeSet, int n2) {
        this(context, attributeSet, n2, 0);
    }

    public WearableFrameLayout(Context context, AttributeSet attributeSet, int n2, int n3) {
        super(context, attributeSet, n2, n3);
        this.mOverlayBounds = new Rect();
        this.mMatchParentChildren = new ArrayList(1);
        context = context.obtainStyledAttributes(attributeSet, R.styleable.WearableFrameLayout, n2, n3);
        this.mForegroundGravity = context.getInt(R.styleable.WearableFrameLayout_android_foregroundGravity, this.mForegroundGravity);
        attributeSet = context.getDrawable(R.styleable.WearableFrameLayout_android_foreground);
        if (attributeSet != null) {
            this.setForeground((Drawable)attributeSet);
        }
        if (context.getBoolean(R.styleable.WearableFrameLayout_android_measureAllChildren, false)) {
            this.setMeasureAllChildren(true);
        }
        if (context.hasValue(R.styleable.WearableFrameLayout_android_foregroundTint)) {
            this.mForegroundTintList = context.getColorStateList(R.styleable.WearableFrameLayout_android_foregroundTint);
            this.mHasForegroundTint = true;
        }
        context.recycle();
        this.applyForegroundTint();
    }

    private void applyForegroundTint() {
        if (this.mForeground != null && (this.mHasForegroundTint || this.mHasForegroundTintMode)) {
            this.mForeground = this.mForeground.mutate();
            if (this.mHasForegroundTint) {
                this.mForeground.setTintList(this.mForegroundTintList);
            }
            if (this.mHasForegroundTintMode) {
                this.mForeground.setTintMode(this.mForegroundTintMode);
            }
            if (this.mForeground.isStateful()) {
                this.mForeground.setState(this.getDrawableState());
            }
        }
    }

    private int getPaddingBottomWithForeground() {
        if (this.mForegroundInPadding) {
            return Math.max(this.getPaddingBottom(), this.mForegroundPaddingBottom);
        }
        return this.getPaddingBottom() + this.mForegroundPaddingBottom;
    }

    private int getPaddingTopWithForeground() {
        if (this.mForegroundInPadding) {
            return Math.max(this.getPaddingTop(), this.mForegroundPaddingTop);
        }
        return this.getPaddingTop() + this.mForegroundPaddingTop;
    }

    private int getParamsBottomMargin(LayoutParams layoutParams) {
        if (this.mRound) {
            return layoutParams.bottomMarginRound;
        }
        return layoutParams.bottomMargin;
    }

    private int getParamsGravity(LayoutParams layoutParams) {
        if (this.mRound) {
            return layoutParams.gravityRound;
        }
        return layoutParams.gravity;
    }

    private int getParamsHeight(LayoutParams layoutParams) {
        if (this.mRound) {
            return layoutParams.heightRound;
        }
        return layoutParams.height;
    }

    private int getParamsLeftMargin(LayoutParams layoutParams) {
        if (this.mRound) {
            return layoutParams.leftMarginRound;
        }
        return layoutParams.leftMargin;
    }

    private int getParamsRightMargin(LayoutParams layoutParams) {
        if (this.mRound) {
            return layoutParams.rightMarginRound;
        }
        return layoutParams.rightMargin;
    }

    private int getParamsTopMargin(LayoutParams layoutParams) {
        if (this.mRound) {
            return layoutParams.topMarginRound;
        }
        return layoutParams.topMargin;
    }

    private int getParamsWidth(LayoutParams layoutParams) {
        if (this.mRound) {
            return layoutParams.widthRound;
        }
        return layoutParams.width;
    }

    protected boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (this.mForeground != null) {
            Drawable drawable2 = this.mForeground;
            if (this.mForegroundBoundsChanged) {
                this.mForegroundBoundsChanged = false;
                Rect rect = this.mSelfBounds;
                Rect rect2 = this.mOverlayBounds;
                int n2 = this.getRight() - this.getLeft();
                int n3 = this.getBottom() - this.getTop();
                if (this.mForegroundInPadding) {
                    rect.set(0, 0, n2, n3);
                } else {
                    rect.set(this.getPaddingLeft(), this.getPaddingTop(), n2 - this.getPaddingRight(), n3 - this.getPaddingBottom());
                }
                n2 = this.getLayoutDirection();
                Gravity.apply((int)this.mForegroundGravity, (int)drawable2.getIntrinsicWidth(), (int)drawable2.getIntrinsicHeight(), (Rect)rect, (Rect)rect2, (int)n2);
                drawable2.setBounds(rect2);
            }
            drawable2.draw(canvas);
        }
    }

    public void drawableHotspotChanged(float f2, float f3) {
        super.drawableHotspotChanged(f2, f3);
        if (this.mForeground != null) {
            this.mForeground.setHotspot(f2, f3);
        }
    }

    protected void drawableStateChanged() {
        super.drawableStateChanged();
        if (this.mForeground != null && this.mForeground.isStateful()) {
            this.mForeground.setState(this.getDrawableState());
        }
    }

    protected LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-1, -1);
    }

    public LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(this.getContext(), attributeSet);
    }

    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return new LayoutParams((LayoutParams)layoutParams);
    }

    public Drawable getForeground() {
        return this.mForeground;
    }

    public int getForegroundGravity() {
        return this.mForegroundGravity;
    }

    public ColorStateList getForegroundTintList() {
        return this.mForegroundTintList;
    }

    public PorterDuff.Mode getForegroundTintMode() {
        return this.mForegroundTintMode;
    }

    public boolean getMeasureAllChildren() {
        return this.mMeasureAllChildren;
    }

    int getPaddingLeftWithForeground() {
        if (this.mForegroundInPadding) {
            return Math.max(this.getPaddingLeft(), this.mForegroundPaddingLeft);
        }
        return this.getPaddingLeft() + this.mForegroundPaddingLeft;
    }

    int getPaddingRightWithForeground() {
        if (this.mForegroundInPadding) {
            return Math.max(this.getPaddingRight(), this.mForegroundPaddingRight);
        }
        return this.getPaddingRight() + this.mForegroundPaddingRight;
    }

    public void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        if (this.mForeground != null) {
            this.mForeground.jumpToCurrentState();
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    void layoutChildren(int n2, int n3, int n4, int n5, boolean bl2) {
        int n6 = this.getChildCount();
        int n7 = this.getPaddingLeftWithForeground();
        int n8 = n4 - n2 - this.getPaddingRightWithForeground();
        int n9 = this.getPaddingTopWithForeground();
        n5 = n5 - n3 - this.getPaddingBottomWithForeground();
        this.mForegroundBoundsChanged = true;
        n4 = 0;
        while (true) {
            block12: {
                int n10;
                int n11;
                LayoutParams layoutParams;
                View view;
                block13: {
                    if (n4 >= n6) {
                        return;
                    }
                    view = this.getChildAt(n4);
                    if (view.getVisibility() == 8) break block12;
                    layoutParams = (LayoutParams)view.getLayoutParams();
                    n11 = view.getMeasuredWidth();
                    n10 = view.getMeasuredHeight();
                    n2 = n3 = this.getParamsGravity(layoutParams);
                    if (n3 == -1) {
                        n2 = 0x800033;
                    }
                    switch (Gravity.getAbsoluteGravity((int)n2, (int)this.getLayoutDirection()) & 7) {
                        default: {
                            break;
                        }
                        case 1: {
                            n3 = (n8 - n7 - n11) / 2 + n7 + this.getParamsLeftMargin(layoutParams) - this.getParamsRightMargin(layoutParams);
                            break block13;
                        }
                        case 5: {
                            if (bl2) break;
                            n3 = n8 - n11 - this.getParamsRightMargin(layoutParams);
                            break block13;
                        }
                    }
                    n3 = n7 + this.getParamsLeftMargin(layoutParams);
                }
                switch (n2 & 0x70) {
                    default: {
                        n2 = n9 + this.getParamsTopMargin(layoutParams);
                        break;
                    }
                    case 48: {
                        n2 = n9 + this.getParamsTopMargin(layoutParams);
                        break;
                    }
                    case 16: {
                        n2 = (n5 - n9 - n10) / 2 + n9 + this.getParamsTopMargin(layoutParams) - this.getParamsBottomMargin(layoutParams);
                        break;
                    }
                    case 80: {
                        n2 = n5 - n10 - this.getParamsBottomMargin(layoutParams);
                    }
                }
                view.layout(n3, n2, n3 + n11, n2 + n10);
            }
            ++n4;
        }
    }

    protected void measureChildWithMargins(View view, int n2, int n3, int n4, int n5) {
        LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
        view.measure(WearableFrameLayout.getChildMeasureSpec((int)n2, (int)(this.getPaddingLeft() + this.getPaddingRight() + this.getParamsLeftMargin(layoutParams) + this.getParamsRightMargin(layoutParams) + n3), (int)this.getParamsWidth(layoutParams)), WearableFrameLayout.getChildMeasureSpec((int)n4, (int)(this.getPaddingTop() + this.getPaddingBottom() + this.getParamsTopMargin(layoutParams) + this.getParamsBottomMargin(layoutParams) + n5), (int)this.getParamsHeight(layoutParams)));
    }

    /*
     * Enabled aggressive block sorting
     */
    public WindowInsets onApplyWindowInsets(WindowInsets windowInsets) {
        boolean bl2 = this.mRound != windowInsets.isRound();
        this.mRound = windowInsets.isRound();
        if (bl2) {
            this.requestLayout();
        }
        return super.onApplyWindowInsets(windowInsets);
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.requestApplyInsets();
    }

    public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        accessibilityEvent.setClassName((CharSequence)WearableFrameLayout.class.getName());
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName((CharSequence)WearableFrameLayout.class.getName());
    }

    protected void onLayout(boolean bl2, int n2, int n3, int n4, int n5) {
        this.layoutChildren(n2, n3, n4, n5, false);
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void onMeasure(int n2, int n3) {
        LayoutParams layoutParams;
        View view;
        int n4;
        int n5 = this.getChildCount();
        int n6 = View.MeasureSpec.getMode((int)n2) != 0x40000000 || View.MeasureSpec.getMode((int)n3) != 0x40000000 ? 1 : 0;
        this.mMatchParentChildren.clear();
        int n7 = 0;
        int n8 = 0;
        int n9 = 0;
        for (n4 = 0; n4 < n5; ++n4) {
            int n10;
            int n11;
            int n12;
            block8: {
                block9: {
                    block7: {
                        view = this.getChildAt(n4);
                        if (this.mMeasureAllChildren) break block7;
                        n12 = n9;
                        n11 = n7;
                        n10 = n8;
                        if (view.getVisibility() == 8) break block8;
                    }
                    this.measureChildWithMargins(view, n2, 0, n3, 0);
                    layoutParams = (LayoutParams)view.getLayoutParams();
                    n8 = Math.max(n8, view.getMeasuredWidth() + this.getParamsLeftMargin(layoutParams) + this.getParamsRightMargin(layoutParams));
                    n7 = Math.max(n7, view.getMeasuredHeight() + this.getParamsTopMargin(layoutParams) + this.getParamsBottomMargin(layoutParams));
                    n12 = n9 = WearableFrameLayout.combineMeasuredStates((int)n9, (int)view.getMeasuredState());
                    n11 = n7;
                    n10 = n8;
                    if (n6 == 0) break block8;
                    if (this.getParamsWidth(layoutParams) == -1) break block9;
                    n12 = n9;
                    n11 = n7;
                    n10 = n8;
                    if (this.getParamsHeight(layoutParams) != -1) break block8;
                }
                this.mMatchParentChildren.add(view);
                n10 = n8;
                n11 = n7;
                n12 = n9;
            }
            n9 = n12;
            n7 = n11;
            n8 = n10;
        }
        n6 = this.getPaddingLeftWithForeground();
        n4 = this.getPaddingRightWithForeground();
        n7 = Math.max(n7 + (this.getPaddingTopWithForeground() + this.getPaddingBottomWithForeground()), this.getSuggestedMinimumHeight());
        n4 = Math.max(n8 + (n6 + n4), this.getSuggestedMinimumWidth());
        view = this.getForeground();
        n8 = n7;
        n6 = n4;
        if (view != null) {
            n8 = Math.max(n7, view.getMinimumHeight());
            n6 = Math.max(n4, view.getMinimumWidth());
        }
        this.setMeasuredDimension(WearableFrameLayout.resolveSizeAndState((int)n6, (int)n2, (int)n9), WearableFrameLayout.resolveSizeAndState((int)n8, (int)n3, (int)(n9 << 16)));
        n7 = this.mMatchParentChildren.size();
        if (n7 > 1) {
            for (n9 = 0; n9 < n7; ++n9) {
                view = this.mMatchParentChildren.get(n9);
                layoutParams = (LayoutParams)view.getLayoutParams();
                n6 = this.getParamsWidth(layoutParams) == -1 ? View.MeasureSpec.makeMeasureSpec((int)(this.getMeasuredWidth() - this.getPaddingLeftWithForeground() - this.getPaddingRightWithForeground() - this.getParamsLeftMargin(layoutParams) - this.getParamsRightMargin(layoutParams)), (int)0x40000000) : WearableFrameLayout.getChildMeasureSpec((int)n2, (int)(this.getPaddingLeftWithForeground() + this.getPaddingRightWithForeground() + this.getParamsLeftMargin(layoutParams) + this.getParamsRightMargin(layoutParams)), (int)this.getParamsWidth(layoutParams));
                n8 = this.getParamsHeight(layoutParams) == -1 ? View.MeasureSpec.makeMeasureSpec((int)(this.getMeasuredHeight() - this.getPaddingTopWithForeground() - this.getPaddingBottomWithForeground() - this.getParamsTopMargin(layoutParams) - this.getParamsBottomMargin(layoutParams)), (int)0x40000000) : WearableFrameLayout.getChildMeasureSpec((int)n3, (int)(this.getPaddingTopWithForeground() + this.getPaddingBottomWithForeground() + this.getParamsTopMargin(layoutParams) + this.getParamsBottomMargin(layoutParams)), (int)this.getParamsHeight(layoutParams));
                view.measure(n6, n8);
            }
        }
    }

    protected void onSizeChanged(int n2, int n3, int n4, int n5) {
        super.onSizeChanged(n2, n3, n4, n5);
        this.mForegroundBoundsChanged = true;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setForeground(Drawable drawable2) {
        if (this.mForeground != drawable2) {
            if (this.mForeground != null) {
                this.mForeground.setCallback(null);
                this.unscheduleDrawable(this.mForeground);
            }
            this.mForeground = drawable2;
            this.mForegroundPaddingLeft = 0;
            this.mForegroundPaddingTop = 0;
            this.mForegroundPaddingRight = 0;
            this.mForegroundPaddingBottom = 0;
            if (drawable2 != null) {
                Rect rect;
                this.setWillNotDraw(false);
                drawable2.setCallback((Drawable.Callback)this);
                if (drawable2.isStateful()) {
                    drawable2.setState(this.getDrawableState());
                }
                this.applyForegroundTint();
                if (this.mForegroundGravity == 119 && drawable2.getPadding(rect = new Rect())) {
                    this.mForegroundPaddingLeft = rect.left;
                    this.mForegroundPaddingTop = rect.top;
                    this.mForegroundPaddingRight = rect.right;
                    this.mForegroundPaddingBottom = rect.bottom;
                }
            } else {
                this.setWillNotDraw(true);
            }
            this.requestLayout();
            this.invalidate();
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setForegroundGravity(int n2) {
        if (this.mForegroundGravity != n2) {
            int n3 = n2;
            if ((0x800007 & n2) == 0) {
                n3 = n2 | 0x800003;
            }
            n2 = n3;
            if ((n3 & 0x70) == 0) {
                n2 = n3 | 0x30;
            }
            this.mForegroundGravity = n2;
            if (this.mForegroundGravity == 119 && this.mForeground != null) {
                Rect rect = new Rect();
                if (this.mForeground.getPadding(rect)) {
                    this.mForegroundPaddingLeft = rect.left;
                    this.mForegroundPaddingTop = rect.top;
                    this.mForegroundPaddingRight = rect.right;
                    this.mForegroundPaddingBottom = rect.bottom;
                }
            } else {
                this.mForegroundPaddingLeft = 0;
                this.mForegroundPaddingTop = 0;
                this.mForegroundPaddingRight = 0;
                this.mForegroundPaddingBottom = 0;
            }
            this.requestLayout();
        }
    }

    public void setForegroundInPadding(boolean bl2) {
        this.mForegroundInPadding = bl2;
    }

    public void setForegroundTintList(ColorStateList colorStateList) {
        this.mForegroundTintList = colorStateList;
        this.mHasForegroundTint = true;
        this.applyForegroundTint();
    }

    public void setForegroundTintMode(PorterDuff.Mode mode) {
        this.mForegroundTintMode = mode;
        this.mHasForegroundTintMode = true;
        this.applyForegroundTint();
    }

    public void setMeasureAllChildren(boolean bl2) {
        this.mMeasureAllChildren = bl2;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setVisibility(int n2) {
        super.setVisibility(n2);
        if (this.mForeground != null) {
            Drawable drawable2 = this.mForeground;
            boolean bl2 = n2 == 0;
            drawable2.setVisible(bl2, false);
        }
    }

    public boolean shouldDelayChildPressedState() {
        return false;
    }

    protected boolean verifyDrawable(Drawable drawable2) {
        return super.verifyDrawable(drawable2) || drawable2 == this.mForeground;
    }

    public static class LayoutParams
    extends FrameLayout.LayoutParams {
        public int bottomMarginRound;
        public int gravityRound = -1;
        public int heightRound;
        public int leftMarginRound;
        public int rightMarginRound;
        public int topMarginRound;
        public int widthRound;

        public LayoutParams(int n2, int n3) {
            super(n2, n3);
            this.widthRound = n2;
            this.heightRound = n3;
        }

        public LayoutParams(int n2, int n3, int n4) {
            super(n2, n3, n4);
            this.widthRound = n2;
            this.heightRound = n3;
            this.gravityRound = n4;
        }

        public LayoutParams(int n2, int n3, int n4, int n5, int n6, int n7) {
            super(n2, n3, n4);
            this.widthRound = n5;
            this.heightRound = n6;
            this.gravityRound = n7;
        }

        /*
         * Enabled aggressive block sorting
         */
        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            context = context.obtainStyledAttributes(attributeSet, R.styleable.WearableFrameLayout);
            this.gravityRound = context.getInt(R.styleable.WearableFrameLayout_layout_gravityRound, this.gravity);
            this.widthRound = context.getLayoutDimension(R.styleable.WearableFrameLayout_layout_widthRound, this.width);
            this.heightRound = context.getLayoutDimension(R.styleable.WearableFrameLayout_layout_heightRound, this.height);
            int n2 = context.getDimensionPixelSize(R.styleable.WearableFrameLayout_layout_marginRound, -1);
            if (n2 >= 0) {
                this.bottomMarginRound = n2;
                this.topMarginRound = n2;
                this.rightMarginRound = n2;
                this.leftMarginRound = n2;
            } else {
                this.leftMarginRound = context.getDimensionPixelSize(R.styleable.WearableFrameLayout_layout_marginLeftRound, this.leftMargin);
                this.topMarginRound = context.getDimensionPixelSize(R.styleable.WearableFrameLayout_layout_marginTopRound, this.topMargin);
                this.rightMarginRound = context.getDimensionPixelSize(R.styleable.WearableFrameLayout_layout_marginRightRound, this.rightMargin);
                this.bottomMarginRound = context.getDimensionPixelSize(R.styleable.WearableFrameLayout_layout_marginBottomRound, this.bottomMargin);
            }
            context.recycle();
        }

        public LayoutParams(LayoutParams layoutParams) {
            super((FrameLayout.LayoutParams)layoutParams);
            this.widthRound = layoutParams.widthRound;
            this.heightRound = layoutParams.heightRound;
            this.gravityRound = layoutParams.gravityRound;
            this.leftMarginRound = layoutParams.leftMarginRound;
            this.topMarginRound = layoutParams.topMarginRound;
            this.rightMarginRound = layoutParams.rightMarginRound;
            this.bottomMarginRound = layoutParams.bottomMarginRound;
        }
    }
}

