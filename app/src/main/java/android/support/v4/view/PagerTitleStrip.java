/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.database.DataSetObserver
 *  android.graphics.drawable.Drawable
 *  android.os.Build$VERSION
 *  android.text.TextUtils$TruncateAt
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.ViewGroup
 *  android.widget.TextView
 */
package android.support.v4.view;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.FloatRange;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.PagerTitleStripIcs;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.lang.ref.WeakReference;

@ViewPager.DecorView
public class PagerTitleStrip
extends ViewGroup {
    private static final int[] ATTRS = new int[]{16842804, 16842901, 16842904, 16842927};
    private static final PagerTitleStripImpl IMPL;
    private static final float SIDE_ALPHA = 0.6f;
    private static final String TAG = "PagerTitleStrip";
    private static final int[] TEXT_ATTRS;
    private static final int TEXT_SPACING = 16;
    TextView mCurrText;
    private int mGravity;
    private int mLastKnownCurrentPage = -1;
    float mLastKnownPositionOffset = -1.0f;
    TextView mNextText;
    private int mNonPrimaryAlpha;
    private final PageListener mPageListener = new PageListener();
    ViewPager mPager;
    TextView mPrevText;
    private int mScaledTextSpacing;
    int mTextColor;
    private boolean mUpdatingPositions;
    private boolean mUpdatingText;
    private WeakReference<PagerAdapter> mWatchingAdapter;

    static {
        TEXT_ATTRS = new int[]{16843660};
        IMPL = Build.VERSION.SDK_INT >= 14 ? new PagerTitleStripImplIcs() : new PagerTitleStripImplBase();
    }

    public PagerTitleStrip(Context context) {
        this(context, null);
    }

    /*
     * Enabled aggressive block sorting
     */
    public PagerTitleStrip(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        int n2;
        TextView textView;
        this.mPrevText = textView = new TextView(context);
        this.addView((View)textView);
        this.mCurrText = textView = new TextView(context);
        this.addView((View)textView);
        this.mNextText = textView = new TextView(context);
        this.addView((View)textView);
        attributeSet = context.obtainStyledAttributes(attributeSet, ATTRS);
        int n3 = attributeSet.getResourceId(0, 0);
        if (n3 != 0) {
            this.mPrevText.setTextAppearance(context, n3);
            this.mCurrText.setTextAppearance(context, n3);
            this.mNextText.setTextAppearance(context, n3);
        }
        if ((n2 = attributeSet.getDimensionPixelSize(1, 0)) != 0) {
            this.setTextSize(0, n2);
        }
        if (attributeSet.hasValue(2)) {
            n2 = attributeSet.getColor(2, 0);
            this.mPrevText.setTextColor(n2);
            this.mCurrText.setTextColor(n2);
            this.mNextText.setTextColor(n2);
        }
        this.mGravity = attributeSet.getInteger(3, 80);
        attributeSet.recycle();
        this.mTextColor = this.mCurrText.getTextColors().getDefaultColor();
        this.setNonPrimaryAlpha(0.6f);
        this.mPrevText.setEllipsize(TextUtils.TruncateAt.END);
        this.mCurrText.setEllipsize(TextUtils.TruncateAt.END);
        this.mNextText.setEllipsize(TextUtils.TruncateAt.END);
        boolean bl2 = false;
        if (n3 != 0) {
            attributeSet = context.obtainStyledAttributes(n3, TEXT_ATTRS);
            bl2 = attributeSet.getBoolean(0, false);
            attributeSet.recycle();
        }
        if (bl2) {
            PagerTitleStrip.setSingleLineAllCaps(this.mPrevText);
            PagerTitleStrip.setSingleLineAllCaps(this.mCurrText);
            PagerTitleStrip.setSingleLineAllCaps(this.mNextText);
        } else {
            this.mPrevText.setSingleLine();
            this.mCurrText.setSingleLine();
            this.mNextText.setSingleLine();
        }
        this.mScaledTextSpacing = (int)(16.0f * context.getResources().getDisplayMetrics().density);
    }

    private static void setSingleLineAllCaps(TextView textView) {
        IMPL.setSingleLineAllCaps(textView);
    }

    int getMinHeight() {
        int n2 = 0;
        Drawable drawable2 = this.getBackground();
        if (drawable2 != null) {
            n2 = drawable2.getIntrinsicHeight();
        }
        return n2;
    }

    public int getTextSpacing() {
        return this.mScaledTextSpacing;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        Object object = this.getParent();
        if (!(object instanceof ViewPager)) {
            throw new IllegalStateException("PagerTitleStrip must be a direct child of a ViewPager.");
        }
        object = (ViewPager)((Object)object);
        PagerAdapter pagerAdapter = ((ViewPager)((Object)object)).getAdapter();
        ((ViewPager)((Object)object)).setInternalPageChangeListener(this.mPageListener);
        ((ViewPager)((Object)object)).addOnAdapterChangeListener(this.mPageListener);
        this.mPager = object;
        object = this.mWatchingAdapter != null ? (PagerAdapter)this.mWatchingAdapter.get() : null;
        this.updateAdapter((PagerAdapter)object, pagerAdapter);
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.mPager != null) {
            this.updateAdapter(this.mPager.getAdapter(), null);
            this.mPager.setInternalPageChangeListener(null);
            this.mPager.removeOnAdapterChangeListener(this.mPageListener);
            this.mPager = null;
        }
    }

    protected void onLayout(boolean bl2, int n2, int n3, int n4, int n5) {
        float f2 = 0.0f;
        if (this.mPager != null) {
            if (this.mLastKnownPositionOffset >= 0.0f) {
                f2 = this.mLastKnownPositionOffset;
            }
            this.updateTextPositions(this.mLastKnownCurrentPage, f2, true);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void onMeasure(int n2, int n3) {
        if (View.MeasureSpec.getMode((int)n2) != 0x40000000) {
            throw new IllegalStateException("Must measure with an exact width");
        }
        int n4 = this.getPaddingTop() + this.getPaddingBottom();
        int n5 = PagerTitleStrip.getChildMeasureSpec((int)n3, (int)n4, (int)-2);
        int n6 = View.MeasureSpec.getSize((int)n2);
        n2 = PagerTitleStrip.getChildMeasureSpec((int)n2, (int)((int)((float)n6 * 0.2f)), (int)-2);
        this.mPrevText.measure(n2, n5);
        this.mCurrText.measure(n2, n5);
        this.mNextText.measure(n2, n5);
        if (View.MeasureSpec.getMode((int)n3) == 0x40000000) {
            n2 = View.MeasureSpec.getSize((int)n3);
        } else {
            n2 = this.mCurrText.getMeasuredHeight();
            n2 = Math.max(this.getMinHeight(), n2 + n4);
        }
        this.setMeasuredDimension(n6, ViewCompat.resolveSizeAndState(n2, n3, ViewCompat.getMeasuredState((View)this.mCurrText) << 16));
    }

    public void requestLayout() {
        if (!this.mUpdatingText) {
            super.requestLayout();
        }
    }

    public void setGravity(int n2) {
        this.mGravity = n2;
        this.requestLayout();
    }

    public void setNonPrimaryAlpha(@FloatRange(from=0.0, to=1.0) float f2) {
        this.mNonPrimaryAlpha = (int)(255.0f * f2) & 0xFF;
        int n2 = this.mNonPrimaryAlpha << 24 | this.mTextColor & 0xFFFFFF;
        this.mPrevText.setTextColor(n2);
        this.mNextText.setTextColor(n2);
    }

    public void setTextColor(@ColorInt int n2) {
        this.mTextColor = n2;
        this.mCurrText.setTextColor(n2);
        n2 = this.mNonPrimaryAlpha << 24 | this.mTextColor & 0xFFFFFF;
        this.mPrevText.setTextColor(n2);
        this.mNextText.setTextColor(n2);
    }

    public void setTextSize(int n2, float f2) {
        this.mPrevText.setTextSize(n2, f2);
        this.mCurrText.setTextSize(n2, f2);
        this.mNextText.setTextSize(n2, f2);
    }

    public void setTextSpacing(int n2) {
        this.mScaledTextSpacing = n2;
        this.requestLayout();
    }

    void updateAdapter(PagerAdapter pagerAdapter, PagerAdapter pagerAdapter2) {
        if (pagerAdapter != null) {
            pagerAdapter.unregisterDataSetObserver(this.mPageListener);
            this.mWatchingAdapter = null;
        }
        if (pagerAdapter2 != null) {
            pagerAdapter2.registerDataSetObserver(this.mPageListener);
            this.mWatchingAdapter = new WeakReference<PagerAdapter>(pagerAdapter2);
        }
        if (this.mPager != null) {
            this.mLastKnownCurrentPage = -1;
            this.mLastKnownPositionOffset = -1.0f;
            this.updateText(this.mPager.getCurrentItem(), pagerAdapter2);
            this.requestLayout();
        }
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    void updateText(int n2, PagerAdapter pagerAdapter) {
        void var5_14;
        void var5_10;
        void var5_8;
        TextView textView;
        int n3 = pagerAdapter != null ? pagerAdapter.getCount() : 0;
        this.mUpdatingText = true;
        TextView textView2 = textView = null;
        if (n2 >= 1) {
            TextView textView3 = textView;
            if (pagerAdapter != null) {
                CharSequence charSequence = pagerAdapter.getPageTitle(n2 - 1);
            }
        }
        this.mPrevText.setText((CharSequence)var5_8);
        textView = this.mCurrText;
        if (pagerAdapter != null && n2 < n3) {
            CharSequence charSequence = pagerAdapter.getPageTitle(n2);
        } else {
            Object var5_15 = null;
        }
        textView.setText((CharSequence)var5_10);
        TextView textView4 = textView = null;
        if (n2 + 1 < n3) {
            TextView textView5 = textView;
            if (pagerAdapter != null) {
                CharSequence charSequence = pagerAdapter.getPageTitle(n2 + 1);
            }
        }
        this.mNextText.setText((CharSequence)var5_14);
        n3 = View.MeasureSpec.makeMeasureSpec((int)Math.max(0, (int)((float)(this.getWidth() - this.getPaddingLeft() - this.getPaddingRight()) * 0.8f)), (int)Integer.MIN_VALUE);
        int n4 = View.MeasureSpec.makeMeasureSpec((int)Math.max(0, this.getHeight() - this.getPaddingTop() - this.getPaddingBottom()), (int)Integer.MIN_VALUE);
        this.mPrevText.measure(n3, n4);
        this.mCurrText.measure(n3, n4);
        this.mNextText.measure(n3, n4);
        this.mLastKnownCurrentPage = n2;
        if (!this.mUpdatingPositions) {
            this.updateTextPositions(n2, this.mLastKnownPositionOffset, false);
        }
        this.mUpdatingText = false;
    }

    /*
     * Enabled aggressive block sorting
     */
    void updateTextPositions(int n2, float f2, boolean bl2) {
        float f3;
        if (n2 != this.mLastKnownCurrentPage) {
            this.updateText(n2, this.mPager.getAdapter());
        } else if (!bl2 && f2 == this.mLastKnownPositionOffset) {
            return;
        }
        this.mUpdatingPositions = true;
        int n3 = this.mPrevText.getMeasuredWidth();
        int n4 = this.mCurrText.getMeasuredWidth();
        int n5 = this.mNextText.getMeasuredWidth();
        int n6 = n4 / 2;
        int n7 = this.getWidth();
        int n8 = this.getHeight();
        int n9 = this.getPaddingLeft();
        int n10 = this.getPaddingRight();
        n2 = this.getPaddingTop();
        int n11 = this.getPaddingBottom();
        int n12 = n10 + n6;
        float f4 = f3 = f2 + 0.5f;
        if (f3 > 1.0f) {
            f4 = f3 - 1.0f;
        }
        n6 = n7 - n12 - (int)((float)(n7 - (n9 + n6) - n12) * f4) - n4 / 2;
        n4 = n6 + n4;
        int n13 = this.mPrevText.getBaseline();
        int n14 = this.mCurrText.getBaseline();
        n12 = this.mNextText.getBaseline();
        int n15 = Math.max(Math.max(n13, n14), n12);
        n13 = n15 - n13;
        n14 = n15 - n14;
        n12 = n15 - n12;
        n15 = this.mPrevText.getMeasuredHeight();
        int n16 = this.mCurrText.getMeasuredHeight();
        int n17 = this.mNextText.getMeasuredHeight();
        n15 = Math.max(Math.max(n13 + n15, n14 + n16), n12 + n17);
        switch (this.mGravity & 0x70) {
            default: {
                n11 = n2 + n13;
                n8 = n2 + n14;
                n2 += n12;
                break;
            }
            case 16: {
                n2 = (n8 - n2 - n11 - n15) / 2;
                n11 = n2 + n13;
                n8 = n2 + n14;
                n2 += n12;
                break;
            }
            case 80: {
                n2 = n8 - n11 - n15;
                n11 = n2 + n13;
                n8 = n2 + n14;
                n2 += n12;
            }
        }
        this.mCurrText.layout(n6, n8, n4, this.mCurrText.getMeasuredHeight() + n8);
        n8 = Math.min(n9, n6 - this.mScaledTextSpacing - n3);
        this.mPrevText.layout(n8, n11, n8 + n3, this.mPrevText.getMeasuredHeight() + n11);
        n8 = Math.max(n7 - n10 - n5, this.mScaledTextSpacing + n4);
        this.mNextText.layout(n8, n2, n8 + n5, this.mNextText.getMeasuredHeight() + n2);
        this.mLastKnownPositionOffset = f2;
        this.mUpdatingPositions = false;
    }

    private class PageListener
    extends DataSetObserver
    implements ViewPager.OnPageChangeListener,
    ViewPager.OnAdapterChangeListener {
        private int mScrollState;

        PageListener() {
        }

        @Override
        public void onAdapterChanged(ViewPager viewPager, PagerAdapter pagerAdapter, PagerAdapter pagerAdapter2) {
            PagerTitleStrip.this.updateAdapter(pagerAdapter, pagerAdapter2);
        }

        public void onChanged() {
            float f2 = 0.0f;
            PagerTitleStrip.this.updateText(PagerTitleStrip.this.mPager.getCurrentItem(), PagerTitleStrip.this.mPager.getAdapter());
            if (PagerTitleStrip.this.mLastKnownPositionOffset >= 0.0f) {
                f2 = PagerTitleStrip.this.mLastKnownPositionOffset;
            }
            PagerTitleStrip.this.updateTextPositions(PagerTitleStrip.this.mPager.getCurrentItem(), f2, true);
        }

        @Override
        public void onPageScrollStateChanged(int n2) {
            this.mScrollState = n2;
        }

        @Override
        public void onPageScrolled(int n2, float f2, int n3) {
            n3 = n2;
            if (f2 > 0.5f) {
                n3 = n2 + 1;
            }
            PagerTitleStrip.this.updateTextPositions(n3, f2, false);
        }

        @Override
        public void onPageSelected(int n2) {
            float f2 = 0.0f;
            if (this.mScrollState == 0) {
                PagerTitleStrip.this.updateText(PagerTitleStrip.this.mPager.getCurrentItem(), PagerTitleStrip.this.mPager.getAdapter());
                if (PagerTitleStrip.this.mLastKnownPositionOffset >= 0.0f) {
                    f2 = PagerTitleStrip.this.mLastKnownPositionOffset;
                }
                PagerTitleStrip.this.updateTextPositions(PagerTitleStrip.this.mPager.getCurrentItem(), f2, true);
            }
        }
    }

    static interface PagerTitleStripImpl {
        public void setSingleLineAllCaps(TextView var1);
    }

    static class PagerTitleStripImplBase
    implements PagerTitleStripImpl {
        PagerTitleStripImplBase() {
        }

        @Override
        public void setSingleLineAllCaps(TextView textView) {
            textView.setSingleLine();
        }
    }

    static class PagerTitleStripImplIcs
    implements PagerTitleStripImpl {
        PagerTitleStripImplIcs() {
        }

        @Override
        public void setSingleLineAllCaps(TextView textView) {
            PagerTitleStripIcs.setSingleLineAllCaps(textView);
        }
    }
}

