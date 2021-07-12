/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Canvas
 *  android.graphics.Paint
 *  android.graphics.Paint$Style
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.PorterDuffXfermode
 *  android.graphics.Rect
 *  android.graphics.Xfermode
 */
package ticwear.design.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Xfermode;
import ticwear.design.widget.EdgeEffect;

public class ClassicEdgeEffect
extends EdgeEffect {
    private static final double ANGLE = 0.5235987755982988;
    private static final float COS;
    private static final float SIN;
    private static final String TAG = "ClassicEdgeEffect";
    private float mBaseGlowScale;
    private final Paint mPaint = new Paint();
    private float mRadius;

    static {
        SIN = (float)Math.sin(0.5235987755982988);
        COS = (float)Math.cos(0.5235987755982988);
    }

    public ClassicEdgeEffect(Context context) {
        super(context);
        this.mPaint.setAntiAlias(true);
        this.mPaint.setStyle(Paint.Style.FILL);
        this.mPaint.setXfermode((Xfermode)new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));
        this.updatePaint();
    }

    private void updatePaint() {
        int n2 = this.getColor();
        this.mPaint.setColor(0xFFFFFF & n2 | 0x33000000);
    }

    @Override
    public void onDraw(Canvas canvas) {
        int n2 = canvas.save();
        Rect rect = this.getBounds();
        float f2 = rect.centerX();
        float f3 = rect.height();
        float f4 = this.mRadius;
        canvas.scale(1.0f, Math.min(this.getGlowScaleY(), 1.0f) * this.mBaseGlowScale, f2, 0.0f);
        float f5 = Math.max(0.0f, Math.min(this.getDisplacement(), 1.0f));
        f5 = (float)rect.width() * (f5 - 0.5f) / 2.0f;
        canvas.clipRect(rect);
        canvas.translate(f5, 0.0f);
        this.mPaint.setAlpha((int)(127.0f * this.getGlowAlpha()));
        canvas.drawCircle(f2, f3 - f4, this.mRadius, this.mPaint);
        canvas.restoreToCount(n2);
    }

    @Override
    public void setColor(int n2) {
        super.setColor(n2);
        this.updatePaint();
    }

    @Override
    public void setSize(int n2, int n3) {
        float f2 = 1.0f;
        float f3 = (float)n2 * 0.75f / SIN;
        float f4 = f3 - COS * f3;
        float f5 = (float)n3 * 0.75f / SIN;
        float f6 = COS;
        this.mRadius = f3;
        if (f4 > 0.0f) {
            f2 = Math.min((f5 - f6 * f5) / f4, 1.0f);
        }
        this.mBaseGlowScale = f2;
        Rect rect = this.getBounds();
        rect.set(rect.left, rect.top, n2, (int)Math.min((float)n3, f4));
    }
}

