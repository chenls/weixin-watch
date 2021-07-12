/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Canvas
 *  android.graphics.Color
 *  android.graphics.Paint
 *  android.graphics.Paint$Cap
 *  android.graphics.Paint$Style
 *  android.graphics.Point
 *  android.graphics.RectF
 *  android.util.AttributeSet
 *  android.util.Log
 */
package ticwear.design.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import ticwear.design.DesignConfig;
import ticwear.design.R;
import ticwear.design.widget.MathUtils;

public class ScrollBarHelper {
    private static final float MIN_SWEEP = 3.0f;
    private static final float START_ANGLE = -30.0f;
    private static final float SWEEP_ANGLE = 60.0f;
    static final String TAG = "SBHelper";
    private int mBgColor;
    private boolean mIsRound = true;
    private final Point mOffset;
    private RectF mOval = null;
    private Paint mPaint = new Paint();
    private final float mScreenRadius;
    private int mSweepColor;

    public ScrollBarHelper(Context context, AttributeSet attributeSet, int n2) {
        this.mOffset = new Point();
        attributeSet = context.obtainStyledAttributes(attributeSet, R.styleable.ScrollBar, n2, R.style.Widget_Ticwear_ScrollBar);
        float f2 = attributeSet.getDimension(R.styleable.ScrollBar_tic_scroll_bar_strokeWidth, 0.0f);
        float f3 = attributeSet.getDimension(R.styleable.ScrollBar_tic_scroll_bar_margin, 0.0f);
        this.mBgColor = attributeSet.getColor(R.styleable.ScrollBar_tic_scroll_bar_bgColor, 0x66666666);
        this.mSweepColor = attributeSet.getColor(R.styleable.ScrollBar_tic_scroll_bar_sweepColor, -16738074);
        attributeSet.recycle();
        this.mScreenRadius = (float)context.getResources().getDisplayMetrics().widthPixels / 2.0f;
        float f4 = this.mScreenRadius - (f3 += f2 / 2.0f);
        this.mOval = new RectF(f3, f3, f4 * 2.0f, 2.0f * f4);
        this.mPaint.setStyle(Paint.Style.STROKE);
        this.mPaint.setStrokeCap(Paint.Cap.ROUND);
        this.mPaint.setAntiAlias(true);
        this.mPaint.setStrokeWidth(f2);
    }

    private void setColorWithOpacity(Paint paint, int n2, float f2) {
        int n3 = (int)((float)Color.alpha((int)n2) * f2);
        paint.setColor(n2);
        paint.setAlpha(n3);
    }

    public float getY(float f2, float f3) {
        double d2 = (double)f2 * Math.PI * 2.0 / 360.0;
        return (float)((double)this.mOval.centerY() + (double)(f3 - this.mOval.centerX()) * Math.tan(d2));
    }

    /*
     * Enabled aggressive block sorting
     */
    public void onDrawScrollBar(Canvas canvas, int n2, int n3, int n4, int n5) {
        float f2 = this.mPaint.getStrokeWidth() / 2.0f;
        float f3 = 0.0f;
        float f4 = this.mOffset.y;
        if (f4 > 0.0f) {
            f3 = MathUtils.constrain((float)(360.0 * -Math.asin((this.mScreenRadius - f4 - f2) / this.mScreenRadius) / Math.PI / 2.0) + 30.0f, 0.0f, 30.0f);
        } else if (f4 < 0.0f) {
            f3 = MathUtils.constrain(30.0f - (float)(360.0 * Math.asin((this.mScreenRadius + f4 - f2) / this.mScreenRadius) / Math.PI / 2.0), 0.0f, 30.0f);
        }
        float f5 = MathUtils.constrain(-30.0f + f3, -30.0f, 0.0f);
        f2 = MathUtils.constrain(60.0f - 2.0f * f3, 0.0f, 60.0f);
        float f6 = 3.0f * f2 / 60.0f;
        f6 = MathUtils.constrain((float)n4 * f2 / (float)n2, f6, f2);
        float f7 = (f2 - f6) * (float)n3 / (float)(n2 - n4);
        float f8 = (float)n5 / 255.0f;
        if (DesignConfig.DEBUG_SCROLLBAR && f4 != 0.0f) {
            Log.v((String)TAG, (String)("offset " + f4 + ", extra " + f3 + ", scrollbar (" + f5 + "," + f2 + "), with opacity " + f8));
        }
        canvas.save();
        canvas.translate((float)(-this.mOffset.x), (float)(-this.mOffset.y));
        if (this.mIsRound) {
            this.setColorWithOpacity(this.mPaint, this.mBgColor, f8);
            canvas.drawArc(this.mOval, f5, f2, false, this.mPaint);
            this.setColorWithOpacity(this.mPaint, this.mSweepColor, f8);
            canvas.rotate(f7, this.mOval.centerX(), this.mOval.centerY());
            canvas.drawArc(this.mOval, f5, f6, false, this.mPaint);
        } else {
            f3 = this.mOval.right;
            this.setColorWithOpacity(this.mPaint, this.mBgColor, f8);
            f4 = this.getY(f5, f3);
            f5 = this.getY(f5 + f2, f3) - f4;
            canvas.drawLine(f3, f4, f3, f4 + f5, this.mPaint);
            this.setColorWithOpacity(this.mPaint, this.mSweepColor, f8);
            canvas.drawLine(f3, f4 + f7 / f2 * f5, f3, f4 + (f7 + f6) / f2 * f5, this.mPaint);
        }
        canvas.restore();
    }

    public void setIsRound(boolean bl2) {
        this.mIsRound = bl2;
    }

    public void setViewOffset(int n2, int n3) {
        this.mOffset.set(n2, n3);
    }
}

