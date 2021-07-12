/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.content.Context
 *  android.graphics.Canvas
 *  android.graphics.LinearGradient
 *  android.graphics.Matrix
 *  android.graphics.Paint
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.PorterDuffXfermode
 *  android.graphics.Rect
 *  android.graphics.Shader
 *  android.graphics.Shader$TileMode
 *  android.graphics.Xfermode
 *  android.util.AttributeSet
 *  android.util.Log
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewGroup$MarginLayoutParams
 *  android.view.WindowInsets
 *  android.view.accessibility.AccessibilityEvent
 *  android.view.accessibility.AccessibilityNodeInfo
 */
package android.support.wearable.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.Xfermode;
import android.support.wearable.R;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

@TargetApi(value=20)
public class CardFrame
extends ViewGroup {
    private static float BOX_FACTOR = 0.0f;
    private static final boolean DEBUG = false;
    private static final int DEFAULT_CONTENT_PADDING_DP = 12;
    private static final int DEFAULT_CONTENT_PADDING_TOP_DP = 8;
    private static final int EDGE_FADE_DISTANCE_DP = 40;
    public static final int EXPAND_DOWN = 1;
    public static final int EXPAND_UP = -1;
    public static final float NO_EXPANSION = 1.0f;
    private static final String TAG = "CardFrame";
    private int mBoxInset;
    private boolean mCanExpand;
    private int mCardBaseHeight;
    private final Rect mChildClipBounds = new Rect();
    private final Rect mContentPadding;
    private final EdgeFade mEdgeFade;
    private final int mEdgeFadeDistance;
    private int mExpansionDirection = 1;
    private boolean mExpansionEnabled = true;
    private float mExpansionFactor = 1.0f;
    private boolean mHasBottomInset;
    private final Rect mInsetPadding = new Rect();
    private boolean mRoundDisplay;

    static {
        BOX_FACTOR = 0.146467f;
    }

    public CardFrame(Context context) {
        this(context, null, 0);
    }

    public CardFrame(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public CardFrame(Context context, AttributeSet attributeSet, int n2) {
        super(context, attributeSet, n2);
        this.mContentPadding = new Rect();
        this.mEdgeFade = new EdgeFade();
        float f2 = context.getResources().getDisplayMetrics().density;
        this.mEdgeFadeDistance = (int)(40.0f * f2);
        this.setBackgroundResource(R.drawable.card_background);
        n2 = (int)(12.0f * f2);
        this.setContentPadding(n2, (int)(8.0f * f2), n2, n2);
    }

    public static int getDefaultSize(int n2, int n3, boolean bl2) {
        int n4 = View.MeasureSpec.getMode((int)n3);
        n3 = View.MeasureSpec.getSize((int)n3);
        switch (n4) {
            default: {
                return n2;
            }
            case 0: {
                return n2;
            }
            case -2147483648: {
                if (bl2) {
                    return n3;
                }
                return n2;
            }
            case 0x40000000: 
        }
        return n3;
    }

    public void addView(View view) {
        if (this.getChildCount() > 0) {
            throw new IllegalStateException("CardFrame can host only one direct child");
        }
        super.addView(view);
    }

    public void addView(View view, int n2) {
        if (this.getChildCount() > 0) {
            throw new IllegalStateException("CardFrame can host only one direct child");
        }
        super.addView(view, n2);
    }

    public void addView(View view, int n2, ViewGroup.LayoutParams layoutParams) {
        if (this.getChildCount() > 0) {
            throw new IllegalStateException("CardFrame can host only one direct child");
        }
        super.addView(view, n2, layoutParams);
    }

    public void addView(View view, ViewGroup.LayoutParams layoutParams) {
        if (this.getChildCount() > 0) {
            throw new IllegalStateException("CardFrame can host only one direct child");
        }
        super.addView(view, layoutParams);
    }

    /*
     * Enabled aggressive block sorting
     */
    protected boolean drawChild(Canvas canvas, View view, long l2) {
        int n2 = this.mEdgeFadeDistance;
        int n3 = 0;
        boolean bl2 = false;
        this.mChildClipBounds.set(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
        int n4 = this.getPaddingTop() + this.getPaddingBottom();
        int n5 = view.getHeight();
        int n6 = n3;
        boolean bl3 = bl2;
        if (this.mCanExpand) {
            if (this.mExpansionDirection == -1 && n5 + n4 > this.getHeight()) {
                bl3 = true;
                this.mChildClipBounds.top = this.getPaddingTop();
                n6 = n3;
            } else {
                n6 = n3;
                bl3 = bl2;
                if (this.mExpansionDirection == 1) {
                    n6 = n3;
                    bl3 = bl2;
                    if (n5 + n4 > this.getHeight()) {
                        n6 = 1;
                        this.mChildClipBounds.bottom = this.getHeight() - this.getPaddingBottom();
                        bl3 = bl2;
                    }
                }
            }
        }
        n3 = canvas.getSaveCount();
        canvas.clipRect(this.mChildClipBounds);
        if (bl3) {
            canvas.saveLayer((float)this.mChildClipBounds.left, (float)this.mChildClipBounds.top, (float)this.mChildClipBounds.right, (float)(this.mChildClipBounds.top + n2), null, 4);
        }
        if (n6 != 0) {
            canvas.saveLayer((float)this.mChildClipBounds.left, (float)(this.mChildClipBounds.bottom - n2), (float)this.mChildClipBounds.right, (float)this.mChildClipBounds.bottom, null, 4);
        }
        boolean bl4 = super.drawChild(canvas, view, l2);
        if (bl3) {
            this.mEdgeFade.matrix.reset();
            this.mEdgeFade.matrix.setScale(1.0f, (float)n2);
            this.mEdgeFade.matrix.postTranslate((float)this.mChildClipBounds.left, (float)this.mChildClipBounds.top);
            this.mEdgeFade.shader.setLocalMatrix(this.mEdgeFade.matrix);
            this.mEdgeFade.paint.setShader(this.mEdgeFade.shader);
            canvas.drawRect((float)this.mChildClipBounds.left, (float)this.mChildClipBounds.top, (float)this.mChildClipBounds.right, (float)(this.mChildClipBounds.top + n2), this.mEdgeFade.paint);
        }
        if (n6 != 0) {
            this.mEdgeFade.matrix.reset();
            this.mEdgeFade.matrix.setScale(1.0f, (float)n2);
            this.mEdgeFade.matrix.postRotate(180.0f);
            this.mEdgeFade.matrix.postTranslate((float)this.mChildClipBounds.left, (float)this.mChildClipBounds.bottom);
            this.mEdgeFade.shader.setLocalMatrix(this.mEdgeFade.matrix);
            this.mEdgeFade.paint.setShader(this.mEdgeFade.shader);
            canvas.drawRect((float)this.mChildClipBounds.left, (float)(this.mChildClipBounds.bottom - n2), (float)this.mChildClipBounds.right, (float)this.mChildClipBounds.bottom, this.mEdgeFade.paint);
        }
        canvas.restoreToCount(n3);
        return bl4;
    }

    public int getContentPaddingBottom() {
        return this.mContentPadding.bottom;
    }

    public int getContentPaddingLeft() {
        return this.mContentPadding.left;
    }

    public int getContentPaddingRight() {
        return this.mContentPadding.right;
    }

    public int getContentPaddingTop() {
        return this.mContentPadding.top;
    }

    public int getExpansionDirection() {
        return this.mExpansionDirection;
    }

    public float getExpansionFactor() {
        return this.mExpansionFactor;
    }

    public boolean isExpansionEnabled() {
        return this.mExpansionEnabled;
    }

    /*
     * Enabled aggressive block sorting
     */
    public WindowInsets onApplyWindowInsets(WindowInsets windowInsets) {
        boolean bl2 = windowInsets.isRound();
        if (bl2 != this.mRoundDisplay) {
            this.mRoundDisplay = bl2;
            this.requestLayout();
        }
        if ((bl2 = windowInsets.getSystemWindowInsetBottom() > 0) != this.mHasBottomInset) {
            this.mHasBottomInset = bl2;
            this.requestLayout();
        }
        return windowInsets.consumeSystemWindowInsets();
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.requestApplyInsets();
    }

    public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        accessibilityEvent.setClassName((CharSequence)CardFrame.class.getName());
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName((CharSequence)CardFrame.class.getName());
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void onLayout(boolean bl2, int n2, int n3, int n4, int n5) {
        if (this.getChildCount() == 0) {
            return;
        }
        View view = this.getChildAt(0);
        n4 = this.getPaddingLeft() + this.mInsetPadding.left + this.mContentPadding.left;
        int n6 = view.getMeasuredWidth();
        if (this.mExpansionDirection == -1) {
            n2 = n5 - n3;
            n3 = n2 - (view.getMeasuredHeight() + this.getPaddingBottom() + this.mInsetPadding.bottom + this.mContentPadding.bottom);
        } else {
            n3 = this.getPaddingTop() + this.mInsetPadding.top + this.mContentPadding.top;
            n2 = n3 + view.getMeasuredHeight();
        }
        view.layout(n4, n3, n4 + n6, n2);
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void onMeasure(int n2, int n3) {
        int n4;
        int n5;
        int n6;
        int n7;
        ViewGroup.MarginLayoutParams marginLayoutParams;
        int n8 = View.MeasureSpec.getSize((int)n2);
        int n9 = View.MeasureSpec.getSize((int)n3);
        if (this.mRoundDisplay) {
            marginLayoutParams = (ViewGroup.MarginLayoutParams)this.getLayoutParams();
            this.mInsetPadding.setEmpty();
            n7 = 0;
            n6 = 0;
            n5 = 0;
            n4 = n8;
            if (marginLayoutParams.leftMargin < 0) {
                n7 = -marginLayoutParams.leftMargin;
                n4 = n8 - n7;
            }
            n8 = n4;
            if (marginLayoutParams.rightMargin < 0) {
                n5 = -marginLayoutParams.rightMargin;
                n8 = n4 - n5;
            }
            n4 = n9;
            if (marginLayoutParams.bottomMargin < 0) {
                n6 = -marginLayoutParams.bottomMargin;
                n4 = n9 - n6;
            }
            this.mBoxInset = (int)(BOX_FACTOR * (float)Math.max(n8, n4));
            this.mInsetPadding.left = this.mBoxInset - (this.getPaddingLeft() - n7);
            this.mInsetPadding.right = this.mBoxInset - (this.getPaddingRight() - n5);
            if (!this.mHasBottomInset) {
                this.mInsetPadding.bottom = this.mBoxInset - (this.getPaddingBottom() - n6);
            }
        }
        n8 = CardFrame.getDefaultSize(this.getSuggestedMinimumWidth(), n2, true);
        n2 = CardFrame.getDefaultSize(this.getSuggestedMinimumHeight(), n3, false);
        if (this.getChildCount() == 0) {
            this.setMeasuredDimension(n8, n2);
            return;
        }
        marginLayoutParams = this.getChildAt(0);
        n2 = View.MeasureSpec.getSize((int)n3);
        n3 = View.MeasureSpec.getMode((int)n3);
        n7 = 0;
        this.mCanExpand = this.mExpansionEnabled;
        if (n3 == 0 || n2 == 0) {
            Log.w((String)TAG, (String)"height measure spec passed with mode UNSPECIFIED, or zero height.");
            this.mCanExpand = false;
            this.mCardBaseHeight = 0;
            n4 = 0;
            n7 = 1;
            n2 = 0;
            n3 = 0;
        } else if (n3 == 0x40000000) {
            Log.w((String)TAG, (String)"height measure spec passed with mode EXACT");
            this.mCanExpand = false;
            n4 = this.mCardBaseHeight = n2;
            n2 = 0x40000000;
            n3 = n4;
        } else {
            n4 = n2 = (this.mCardBaseHeight = n2);
            if (this.mCanExpand) {
                n4 = (int)((float)n2 * this.mExpansionFactor);
            }
            if (this.mExpansionDirection == -1) {
                n2 = 0;
                n3 = 0;
            } else {
                n2 = Integer.MIN_VALUE;
                n3 = n4 + this.getPaddingBottom();
            }
        }
        n6 = this.getPaddingLeft();
        n9 = this.getPaddingRight();
        int n10 = this.mContentPadding.left;
        int n11 = this.mContentPadding.right;
        int n12 = this.mInsetPadding.left;
        int n13 = this.mInsetPadding.right;
        n5 = this.getPaddingTop() + this.getPaddingBottom() + this.mContentPadding.top + this.mContentPadding.bottom + this.mInsetPadding.top + this.mInsetPadding.bottom;
        n6 = View.MeasureSpec.makeMeasureSpec((int)(n8 - (n6 + n9 + n10 + n11 + n12 + n13)), (int)0x40000000);
        n2 = View.MeasureSpec.makeMeasureSpec((int)(n3 - n5), (int)n2);
        marginLayoutParams.measure(CardFrame.getChildMeasureSpec((int)n6, (int)0, (int)marginLayoutParams.getLayoutParams().width), n2);
        if (n7 != 0) {
            n2 = marginLayoutParams.getMeasuredHeight() + n5;
        } else {
            n3 = Math.min(n4, marginLayoutParams.getMeasuredHeight() + n5);
            int n14 = this.mCanExpand;
            n2 = marginLayoutParams.getMeasuredHeight() > n3 - n5 ? 1 : 0;
            this.mCanExpand = n2 & n14;
            n2 = n3;
        }
        this.setMeasuredDimension(n8, n2);
    }

    public void setContentPadding(int n2, int n3, int n4, int n5) {
        this.mContentPadding.set(n2, n3, n4, n5);
        this.requestLayout();
    }

    public void setExpansionDirection(int n2) {
        this.mExpansionDirection = n2;
        this.requestLayout();
        this.invalidate();
    }

    public void setExpansionEnabled(boolean bl2) {
        this.mExpansionEnabled = bl2;
        this.requestLayout();
        this.invalidate();
    }

    public void setExpansionFactor(float f2) {
        this.mExpansionFactor = f2;
        this.requestLayout();
        this.invalidate();
    }

    public boolean shouldDelayChildPressedState() {
        return false;
    }

    private static class EdgeFade {
        private final Matrix matrix = new Matrix();
        private final Paint paint;
        private final Shader shader = new LinearGradient(0.0f, 0.0f, 0.0f, 1.0f, -16777216, 0, Shader.TileMode.CLAMP);

        public EdgeFade() {
            this.paint = new Paint();
            this.paint.setShader(this.shader);
            this.paint.setXfermode((Xfermode)new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        }
    }
}

