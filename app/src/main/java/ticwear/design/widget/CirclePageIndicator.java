/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Canvas
 *  android.graphics.Paint
 *  android.graphics.Paint$Style
 *  android.graphics.drawable.Drawable
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.util.AttributeSet
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.View$BaseSavedState
 *  android.view.View$MeasureSpec
 *  android.view.ViewConfiguration
 */
package ticwear.design.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewConfigurationCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import ticwear.design.R;
import ticwear.design.utils.MetricsUtils;
import ticwear.design.widget.PageIndicator;

public class CirclePageIndicator
extends View
implements PageIndicator {
    private static final int INVALID_POINTER = -1;
    private int mActivePointerId = -1;
    private boolean mCentered;
    private int mCurrentPage;
    private boolean mIsDragging;
    private float mLastMotionX = -1.0f;
    private ViewPager.OnPageChangeListener mListener;
    private int mOrientation;
    private float mPageOffset;
    private final Paint mPaintFill;
    private final Paint mPaintPageFill = new Paint(1);
    private final Paint mPaintStroke = new Paint(1);
    private float mRadius;
    private int mScrollState;
    private boolean mSnap;
    private int mSnapPage;
    private int mTouchSlop;
    private ViewPager mViewPager;

    public CirclePageIndicator(Context context) {
        this(context, null);
    }

    public CirclePageIndicator(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.tic_CirclePageIndicatorStyle);
    }

    public CirclePageIndicator(Context context, AttributeSet attributeSet, int n2) {
        super(context, attributeSet, n2);
        this.mPaintFill = new Paint(1);
        if (this.isInEditMode()) {
            return;
        }
        attributeSet = context.obtainStyledAttributes(attributeSet, R.styleable.CirclePageIndicator, n2, 0);
        this.mCentered = attributeSet.getBoolean(R.styleable.CirclePageIndicator_tic_centered, true);
        this.mOrientation = attributeSet.getInt(R.styleable.CirclePageIndicator_android_orientation, 0);
        this.mPaintPageFill.setStyle(Paint.Style.FILL);
        this.mPaintPageFill.setColor(attributeSet.getColor(R.styleable.CirclePageIndicator_tic_pageColor, 0));
        this.mPaintStroke.setStyle(Paint.Style.STROKE);
        this.mPaintStroke.setColor(attributeSet.getColor(R.styleable.CirclePageIndicator_android_strokeColor, -3355444));
        this.mPaintStroke.setStrokeWidth(attributeSet.getDimension(R.styleable.CirclePageIndicator_tic_strokeWidth, MetricsUtils.convertDpToPixel(1.0f, this.getContext())));
        this.mPaintFill.setStyle(Paint.Style.FILL);
        this.mPaintFill.setColor(attributeSet.getColor(R.styleable.CirclePageIndicator_android_fillColor, -1));
        this.mRadius = attributeSet.getDimension(R.styleable.CirclePageIndicator_android_radius, MetricsUtils.convertDpToPixel(3.0f, this.getContext()));
        this.mSnap = attributeSet.getBoolean(R.styleable.CirclePageIndicator_tic_snap, false);
        Drawable drawable2 = attributeSet.getDrawable(R.styleable.CirclePageIndicator_android_background);
        if (drawable2 != null) {
            this.setBackground(drawable2);
        }
        attributeSet.recycle();
        this.mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(ViewConfiguration.get((Context)context));
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private int measureLong(int n2) {
        int n3;
        int n4 = View.MeasureSpec.getMode((int)n2);
        int n5 = View.MeasureSpec.getSize((int)n2);
        if (n4 == 0x40000000) return n5;
        if (this.mViewPager == null) {
            return n5;
        }
        n2 = this.mViewPager.getAdapter().getCount();
        n2 = n3 = (int)((float)(this.getPaddingLeft() + this.getPaddingRight()) + (float)(n2 * 2) * this.mRadius + (float)(n2 - 1) * this.mRadius + 1.0f);
        if (n4 != Integer.MIN_VALUE) return n2;
        return Math.min(n3, n5);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private int measureShort(int n2) {
        int n3;
        int n4 = View.MeasureSpec.getMode((int)n2);
        int n5 = View.MeasureSpec.getSize((int)n2);
        if (n4 == 0x40000000) {
            return n5;
        }
        n2 = n3 = (int)(2.0f * this.mRadius + (float)this.getPaddingTop() + (float)this.getPaddingBottom() + 1.0f);
        if (n4 != Integer.MIN_VALUE) return n2;
        return Math.min(n3, n5);
    }

    public int getFillColor() {
        return this.mPaintFill.getColor();
    }

    public int getOrientation() {
        return this.mOrientation;
    }

    public int getPageColor() {
        return this.mPaintPageFill.getColor();
    }

    public float getRadius() {
        return this.mRadius;
    }

    public int getStrokeColor() {
        return this.mPaintStroke.getColor();
    }

    public float getStrokeWidth() {
        return this.mPaintStroke.getStrokeWidth();
    }

    public boolean isCentered() {
        return this.mCentered;
    }

    public boolean isSnap() {
        return this.mSnap;
    }

    @Override
    public void notifyDataSetChanged() {
        this.invalidate();
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void onDraw(Canvas canvas) {
        float f2;
        int n2;
        int n3;
        int n4;
        int n5;
        int n6;
        super.onDraw(canvas);
        if (this.mViewPager == null || (n6 = this.mViewPager.getAdapter().getCount()) == 0) {
            return;
        }
        if (this.mCurrentPage >= n6) {
            this.setCurrentItem(n6 - 1);
            return;
        }
        if (this.mOrientation == 0) {
            n5 = this.getWidth();
            n4 = this.getPaddingLeft();
            n3 = this.getPaddingRight();
            n2 = this.getPaddingTop();
        } else {
            n5 = this.getHeight();
            n4 = this.getPaddingTop();
            n3 = this.getPaddingBottom();
            n2 = this.getPaddingLeft();
        }
        float f3 = n6 * 2;
        float f4 = this.mRadius;
        float f5 = n6 - 1;
        float f6 = this.mRadius;
        float f7 = this.mRadius * 3.0f;
        float f8 = (float)n2 + this.mRadius;
        float f9 = f2 = (float)n4 + this.mRadius;
        if (this.mCentered) {
            f9 = f2 + ((float)(n5 - n4 - n3) / 2.0f - (f3 * f4 + f5 * f6 + 1.0f) / 2.0f);
        }
        f2 = f3 = this.mRadius;
        if (this.mPaintStroke.getStrokeWidth() > 0.0f) {
            f2 = f3 - this.mPaintStroke.getStrokeWidth() / 2.0f;
        }
        for (n3 = 0; n3 < n6; ++n3) {
            f4 = f9 + (float)n3 * f7;
            if (this.mOrientation == 0) {
                f3 = f4;
                f4 = f8;
            } else {
                f3 = f8;
            }
            if (this.mPaintPageFill.getAlpha() > 0) {
                canvas.drawCircle(f3, f4, f2, this.mPaintPageFill);
            }
            if (f2 == this.mRadius) continue;
            canvas.drawCircle(f3, f4, this.mRadius, this.mPaintStroke);
        }
        n3 = this.mSnap ? this.mSnapPage : this.mCurrentPage;
        f2 = f3 = (float)n3 * f7;
        if (!this.mSnap) {
            f2 = f3 + this.mPageOffset * f7;
        }
        if (this.mOrientation == 0) {
            f2 = f9 + f2;
            f9 = f8;
            f8 = f2;
        } else {
            f9 += f2;
        }
        canvas.drawCircle(f8, f9, this.mRadius, this.mPaintFill);
    }

    protected void onMeasure(int n2, int n3) {
        if (this.mOrientation == 0) {
            this.setMeasuredDimension(this.measureLong(n2), this.measureShort(n3));
            return;
        }
        this.setMeasuredDimension(this.measureShort(n2), this.measureLong(n3));
    }

    @Override
    public void onPageScrollStateChanged(int n2) {
        this.mScrollState = n2;
        if (this.mListener != null) {
            this.mListener.onPageScrollStateChanged(n2);
        }
    }

    @Override
    public void onPageScrolled(int n2, float f2, int n3) {
        this.mCurrentPage = n2;
        this.mPageOffset = f2;
        this.invalidate();
        if (this.mListener != null) {
            this.mListener.onPageScrolled(n2, f2, n3);
        }
    }

    @Override
    public void onPageSelected(int n2) {
        if (this.mSnap || this.mScrollState == 0) {
            this.mCurrentPage = n2;
            this.mSnapPage = n2;
            this.invalidate();
        }
        if (this.mListener != null) {
            this.mListener.onPageSelected(n2);
        }
    }

    public void onRestoreInstanceState(Parcelable object) {
        object = (SavedState)((Object)object);
        super.onRestoreInstanceState(object.getSuperState());
        this.mCurrentPage = object.currentPage;
        this.mSnapPage = object.currentPage;
        this.requestLayout();
    }

    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.currentPage = this.mCurrentPage;
        return savedState;
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (super.onTouchEvent(motionEvent)) {
            return true;
        }
        if (this.mViewPager == null) return false;
        if (this.mViewPager.getAdapter().getCount() == 0) {
            return false;
        }
        int n2 = motionEvent.getAction() & 0xFF;
        switch (n2) {
            case 0: {
                this.mActivePointerId = MotionEventCompat.getPointerId(motionEvent, 0);
                this.mLastMotionX = motionEvent.getX();
                return true;
            }
            case 2: {
                float f2 = MotionEventCompat.getX(motionEvent, MotionEventCompat.findPointerIndex(motionEvent, this.mActivePointerId));
                float f3 = f2 - this.mLastMotionX;
                if (!this.mIsDragging && Math.abs(f3) > (float)this.mTouchSlop) {
                    this.mIsDragging = true;
                }
                if (!this.mIsDragging) return true;
                this.mLastMotionX = f2;
                if (!this.mViewPager.isFakeDragging()) {
                    if (!this.mViewPager.beginFakeDrag()) return true;
                }
                this.mViewPager.fakeDragBy(f3);
                return true;
            }
            case 1: 
            case 3: {
                if (!this.mIsDragging) {
                    int n3 = this.mViewPager.getAdapter().getCount();
                    int n4 = this.getWidth();
                    float f4 = (float)n4 / 2.0f;
                    float f5 = (float)n4 / 6.0f;
                    if (this.mCurrentPage > 0 && motionEvent.getX() < f4 - f5) {
                        if (n2 == 3) return true;
                        this.mViewPager.setCurrentItem(this.mCurrentPage - 1);
                        return true;
                    }
                    if (this.mCurrentPage < n3 - 1 && motionEvent.getX() > f4 + f5) {
                        if (n2 == 3) return true;
                        this.mViewPager.setCurrentItem(this.mCurrentPage + 1);
                        return true;
                    }
                }
                this.mIsDragging = false;
                this.mActivePointerId = -1;
                if (!this.mViewPager.isFakeDragging()) return true;
                this.mViewPager.endFakeDrag();
                return true;
            }
            case 5: {
                n2 = MotionEventCompat.getActionIndex(motionEvent);
                this.mLastMotionX = MotionEventCompat.getX(motionEvent, n2);
                this.mActivePointerId = MotionEventCompat.getPointerId(motionEvent, n2);
                return true;
            }
            case 6: {
                n2 = MotionEventCompat.getActionIndex(motionEvent);
                if (MotionEventCompat.getPointerId(motionEvent, n2) == this.mActivePointerId) {
                    n2 = n2 == 0 ? 1 : 0;
                    this.mActivePointerId = MotionEventCompat.getPointerId(motionEvent, n2);
                }
                this.mLastMotionX = MotionEventCompat.getX(motionEvent, MotionEventCompat.findPointerIndex(motionEvent, this.mActivePointerId));
                return true;
            }
        }
        return true;
    }

    public void setCentered(boolean bl2) {
        this.mCentered = bl2;
        this.invalidate();
    }

    @Override
    public void setCurrentItem(int n2) {
        if (this.mViewPager == null) {
            throw new IllegalStateException("ViewPager has not been bound.");
        }
        this.mViewPager.setCurrentItem(n2);
        this.mCurrentPage = n2;
        this.invalidate();
    }

    public void setFillColor(int n2) {
        this.mPaintFill.setColor(n2);
        this.invalidate();
    }

    @Override
    public void setOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener) {
        this.mListener = onPageChangeListener;
    }

    public void setOrientation(int n2) {
        switch (n2) {
            default: {
                throw new IllegalArgumentException("Orientation must be either HORIZONTAL or VERTICAL.");
            }
            case 0: 
            case 1: 
        }
        this.mOrientation = n2;
        this.requestLayout();
    }

    public void setPageColor(int n2) {
        this.mPaintPageFill.setColor(n2);
        this.invalidate();
    }

    public void setRadius(float f2) {
        this.mRadius = f2;
        this.invalidate();
    }

    public void setSnap(boolean bl2) {
        this.mSnap = bl2;
        this.invalidate();
    }

    public void setStrokeColor(int n2) {
        this.mPaintStroke.setColor(n2);
        this.invalidate();
    }

    public void setStrokeWidth(float f2) {
        this.mPaintStroke.setStrokeWidth(f2);
        this.invalidate();
    }

    @Override
    public void setViewPager(ViewPager viewPager) {
        if (this.mViewPager == viewPager) {
            return;
        }
        if (this.mViewPager != null) {
            this.mViewPager.setOnPageChangeListener(null);
        }
        if (viewPager.getAdapter() == null) {
            throw new IllegalStateException("ViewPager does not have adapter instance.");
        }
        this.mViewPager = viewPager;
        this.mViewPager.setOnPageChangeListener(this);
        this.invalidate();
    }

    @Override
    public void setViewPager(ViewPager viewPager, int n2) {
        this.setViewPager(viewPager);
        this.setCurrentItem(n2);
    }

    static class SavedState
    extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>(){

            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            public SavedState[] newArray(int n2) {
                return new SavedState[n2];
            }
        };
        int currentPage;

        private SavedState(Parcel parcel) {
            super(parcel);
            this.currentPage = parcel.readInt();
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        public void writeToParcel(Parcel parcel, int n2) {
            super.writeToParcel(parcel, n2);
            parcel.writeInt(this.currentPage);
        }
    }
}

