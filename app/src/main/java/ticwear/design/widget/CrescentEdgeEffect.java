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
 *  android.graphics.RadialGradient
 *  android.graphics.Rect
 *  android.graphics.Shader
 *  android.graphics.Shader$TileMode
 *  android.graphics.Xfermode
 */
package ticwear.design.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.Xfermode;
import ticwear.design.widget.EdgeEffect;

public class CrescentEdgeEffect
extends EdgeEffect {
    private static final float CRESCENT_EDGE_MIDDLE_STOP = 0.95384616f;
    private static final float CRESCENT_EDGE_STOP = 0.7692308f;
    private static final float GLOW_SCALE = 0.15f;
    private static final String TAG = "CrescentEdgeEffect";
    private float mGlowCenterX;
    private float mGlowCenterY;
    private float mGlowRadius;
    private final Paint mPaint = new Paint();

    public CrescentEdgeEffect(Context context) {
        super(context);
        this.mPaint.setColor(0);
        this.mPaint.setAntiAlias(true);
        this.mPaint.setStyle(Paint.Style.FILL);
        this.mPaint.setXfermode((Xfermode)new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));
    }

    private void updatePaint() {
        int n2 = this.getColor();
        int n3 = this.getColor();
        float f2 = this.mGlowCenterX;
        float f3 = this.mGlowCenterY;
        float f4 = this.mGlowRadius;
        Shader.TileMode tileMode = Shader.TileMode.CLAMP;
        tileMode = new RadialGradient(f2, f3, f4, new int[]{0, 0, n3 & 0xFFFFFF | 0x7F000000, n2 & 0xFFFFFF | 0xFF000000}, new float[]{0.0f, 0.7692308f, 0.95384616f, 1.0f}, tileMode);
        this.mPaint.setShader((Shader)tileMode);
    }

    @Override
    public void onDraw(Canvas canvas) {
        Rect rect = this.getBounds();
        float f2 = Math.min(this.getGlowScaleY(), 1.0f);
        float f3 = rect.height();
        float f4 = Math.max(0.0f, Math.min(this.getDisplacement(), 1.0f));
        f4 = (float)rect.width() * (f4 - 0.5f) / 2.0f;
        f3 = -(f3 * 0.15f);
        int n2 = canvas.save();
        canvas.clipRect(rect);
        canvas.translate(f4, f3 * (1.0f - f2));
        this.mPaint.setAlpha((int)(255.0f * this.getGlowAlpha()));
        canvas.drawCircle(this.mGlowCenterX, this.mGlowCenterY, this.mGlowRadius, this.mPaint);
        canvas.restoreToCount(n2);
    }

    @Override
    public void setColor(int n2) {
        if (n2 == this.getColor()) {
            return;
        }
        super.setColor(n2);
        this.updatePaint();
    }

    @Override
    public void setSize(int n2, int n3) {
        Rect rect = this.getBounds();
        if (n2 == rect.width() && n3 == rect.height()) {
            return;
        }
        super.setSize(n2, n3);
        this.mGlowRadius = (float)Math.max(rect.width(), rect.height()) * 0.5f / 0.7692308f;
        this.mGlowCenterX = (float)rect.width() * 0.5f;
        this.mGlowCenterY = this.mGlowRadius;
        this.updatePaint();
    }
}

