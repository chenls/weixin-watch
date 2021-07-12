/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.util.Log
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewGroup$MarginLayoutParams
 *  android.view.WindowInsets
 *  android.widget.FrameLayout
 *  android.widget.FrameLayout$LayoutParams
 */
package android.support.wearable.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.support.wearable.view.CardFrame;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.widget.FrameLayout;

@TargetApi(value=20)
public class CardScrollView
extends FrameLayout {
    private static final int CARD_SHADOW_WIDTH_DP = 8;
    private static final boolean DEBUG = false;
    private static final String TAG = "CardScrollView";
    private CardFrame mCardFrame;
    private final int mCardShadowWidth;
    private boolean mRoundDisplay;

    public CardScrollView(Context context) {
        this(context, null);
    }

    public CardScrollView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, 0);
        this.mCardShadowWidth = (int)(8.0f * this.getResources().getDisplayMetrics().density);
    }

    private boolean hasCardFrame() {
        if (this.mCardFrame == null) {
            Log.w((String)TAG, (String)"No CardFrame has been added.");
            return false;
        }
        return true;
    }

    public void addView(View view, int n2, ViewGroup.LayoutParams layoutParams) {
        if (this.getChildCount() > 0 || !(view instanceof CardFrame)) {
            throw new IllegalStateException("CardScrollView may contain only a single CardFrame.");
        }
        super.addView(view, n2, layoutParams);
        this.mCardFrame = (CardFrame)view;
    }

    public boolean canScrollHorizontally(int n2) {
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    public int getAvailableScrollDelta(int n2) {
        int n3;
        block12: {
            int n4;
            int n5;
            int n6;
            block11: {
                if (!this.hasCardFrame()) {
                    return 0;
                }
                n3 = this.getPaddingTop();
                n6 = this.getPaddingBottom();
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams)this.mCardFrame.getLayoutParams();
                n5 = layoutParams.topMargin;
                n4 = layoutParams.bottomMargin;
                n3 = this.mCardFrame.getMeasuredHeight() + (n3 + n6) + (n5 + n4);
                if (n3 <= this.getMeasuredHeight()) {
                    return 0;
                }
                n5 = n3 - this.getMeasuredHeight();
                n6 = 0;
                n4 = this.getScrollY();
                if (this.mCardFrame.getExpansionDirection() != 1) break block11;
                n3 = n6;
                if (n4 >= 0) {
                    if (n2 < 0) {
                        n3 = -n4;
                        break block12;
                    } else {
                        n3 = n6;
                        if (n2 > 0) {
                            n3 = Math.max(0, n5 - n4);
                        }
                    }
                }
                break block12;
            }
            n3 = n6;
            if (this.mCardFrame.getExpansionDirection() == -1) {
                n3 = n6;
                if (n4 <= 0) {
                    if (n2 > 0) {
                        n3 = -n4;
                    } else {
                        n3 = n6;
                        if (n2 < 0) {
                            n3 = -(n5 + n4);
                        }
                    }
                }
            }
        }
        n2 = n3;
        if (!Log.isLoggable((String)TAG, (int)3)) return n2;
        Log.d((String)TAG, (String)("getVerticalScrollableDistance: " + Math.max(0, n3)));
        return n3;
    }

    public int getCardGravity() {
        if (this.hasCardFrame()) {
            return ((FrameLayout.LayoutParams)this.mCardFrame.getLayoutParams()).gravity;
        }
        return 0;
    }

    public int getExpansionDirection() {
        if (this.hasCardFrame()) {
            return this.mCardFrame.getExpansionDirection();
        }
        return 0;
    }

    public float getExpansionFactor() {
        if (this.hasCardFrame()) {
            return this.mCardFrame.getExpansionFactor();
        }
        return 0.0f;
    }

    public boolean isExpansionEnabled() {
        if (this.hasCardFrame()) {
            return this.mCardFrame.isExpansionEnabled();
        }
        return false;
    }

    public WindowInsets onApplyWindowInsets(WindowInsets windowInsets) {
        FrameLayout.LayoutParams layoutParams;
        boolean bl2 = windowInsets.isRound();
        if (this.mRoundDisplay != bl2) {
            this.mRoundDisplay = bl2;
            layoutParams = (FrameLayout.LayoutParams)this.mCardFrame.getLayoutParams();
            layoutParams.leftMargin = -this.mCardShadowWidth;
            layoutParams.rightMargin = -this.mCardShadowWidth;
            layoutParams.bottomMargin = -this.mCardShadowWidth;
            this.mCardFrame.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
        }
        if (windowInsets.getSystemWindowInsetBottom() > 0) {
            int n2 = windowInsets.getSystemWindowInsetBottom();
            layoutParams = this.getLayoutParams();
            if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
                ((ViewGroup.MarginLayoutParams)layoutParams).bottomMargin = n2;
            }
        }
        if (this.mRoundDisplay && this.mCardFrame != null) {
            this.mCardFrame.onApplyWindowInsets(windowInsets);
        }
        this.requestLayout();
        return windowInsets;
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.requestApplyInsets();
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        if (this.getChildCount() == 0 || !(this.getChildAt(0) instanceof CardFrame)) {
            Log.w((String)TAG, (String)"No CardFrame has been added!");
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void onLayout(boolean bl2, int n2, int n3, int n4, int n5) {
        if (this.mCardFrame != null) {
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams)this.mCardFrame.getLayoutParams();
            int n6 = this.mCardFrame.getMeasuredHeight();
            int n7 = this.mCardFrame.getMeasuredWidth();
            n2 = this.getPaddingTop() + n6 + layoutParams.topMargin <= (n5 -= n3) ? ((layoutParams.gravity & 0x70) == 80 ? 1 : 0) : (this.mCardFrame.getExpansionDirection() == -1 ? 1 : 0);
            int n8 = this.getPaddingLeft() + layoutParams.leftMargin;
            n4 = this.getPaddingTop() + layoutParams.topMargin;
            n3 = n4 + n6;
            if (n2 != 0) {
                n3 = n5 - (this.getPaddingBottom() + layoutParams.bottomMargin);
                n4 = n3 - n6;
            }
            this.mCardFrame.layout(n8, n4, n8 + n7, n3);
        }
    }

    protected void onMeasure(int n2, int n3) {
        if (this.mCardFrame != null) {
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams)this.mCardFrame.getLayoutParams();
            int n4 = this.getPaddingLeft();
            int n5 = this.getPaddingRight();
            int n6 = this.getPaddingTop();
            int n7 = this.getPaddingBottom();
            int n8 = View.MeasureSpec.getSize((int)n3);
            int n9 = View.MeasureSpec.getSize((int)n2);
            int n10 = marginLayoutParams.leftMargin;
            int n11 = marginLayoutParams.rightMargin;
            int n12 = marginLayoutParams.topMargin;
            int n13 = marginLayoutParams.bottomMargin;
            n4 = View.MeasureSpec.makeMeasureSpec((int)(n9 - (n4 + n5) - (n10 + n11)), (int)0x40000000);
            n6 = View.MeasureSpec.makeMeasureSpec((int)(n8 - (n6 + n7) - (n12 + n13)), (int)Integer.MIN_VALUE);
            this.mCardFrame.measure(n4, n6);
        }
        this.setMeasuredDimension(CardScrollView.getDefaultSize((int)this.getSuggestedMinimumWidth(), (int)n2), CardScrollView.getDefaultSize((int)this.getSuggestedMinimumWidth(), (int)n3));
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    int roundAwayFromZero(float f2) {
        double d2;
        if (f2 < 0.0f) {
            d2 = Math.floor(f2);
            return (int)d2;
        }
        d2 = Math.ceil(f2);
        return (int)d2;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    int roundTowardZero(float f2) {
        double d2;
        if (f2 > 0.0f) {
            d2 = Math.floor(f2);
            return (int)d2;
        }
        d2 = Math.ceil(f2);
        return (int)d2;
    }

    public void setCardGravity(int n2) {
        if (Log.isLoggable((String)TAG, (int)3)) {
            Log.d((String)TAG, (String)("setCardGravity: " + n2));
        }
        if (this.hasCardFrame() && ((FrameLayout.LayoutParams)this.mCardFrame.getLayoutParams()).gravity != (n2 &= 0x70)) {
            this.mCardFrame.setLayoutParams((ViewGroup.LayoutParams)new FrameLayout.LayoutParams(-1, -2, n2));
            this.requestLayout();
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setExpansionDirection(int n2) {
        if (Log.isLoggable((String)TAG, (int)3)) {
            Log.d((String)TAG, (String)("setExpansionDirection: " + n2));
        }
        if (this.hasCardFrame() && n2 != this.mCardFrame.getExpansionDirection()) {
            this.mCardFrame.setExpansionDirection(n2);
            if (n2 == 1 && this.getScrollY() < 0) {
                this.scrollTo(0, 0);
            } else if (n2 == -1 && this.getScrollY() > 0) {
                this.scrollTo(0, 0);
            }
            this.requestLayout();
        }
    }

    public void setExpansionEnabled(boolean bl2) {
        if (Log.isLoggable((String)TAG, (int)3)) {
            Log.d((String)TAG, (String)("setExpansionEnabled: " + bl2));
        }
        if (this.hasCardFrame() && bl2 != this.mCardFrame.isExpansionEnabled()) {
            this.mCardFrame.setExpansionEnabled(bl2);
            if (!bl2) {
                this.scrollTo(0, 0);
            }
        }
    }

    public void setExpansionFactor(float f2) {
        if (Log.isLoggable((String)TAG, (int)3)) {
            Log.d((String)TAG, (String)("setExpansionFactor: " + f2));
        }
        if (this.hasCardFrame()) {
            this.mCardFrame.setExpansionFactor(f2);
        }
    }
}

