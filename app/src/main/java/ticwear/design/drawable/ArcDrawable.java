/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.res.ColorStateList
 *  android.graphics.Canvas
 *  android.graphics.ColorFilter
 *  android.graphics.Paint
 *  android.graphics.Path
 *  android.graphics.Path$Direction
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.RectF
 *  android.graphics.drawable.ColorDrawable
 *  android.graphics.drawable.Drawable
 */
package ticwear.design.drawable;

import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;

public class ArcDrawable
extends Drawable {
    private final Paint mArcPaint = new Paint();
    private final Path mClipPath = new Path();
    private final Drawable srcDrawable;

    public ArcDrawable(int n2) {
        this((Drawable)new ColorDrawable(n2));
    }

    public ArcDrawable(@NonNull Drawable drawable2) {
        this.srcDrawable = drawable2;
        this.mArcPaint.setAntiAlias(true);
    }

    private void drawWithClip(Canvas canvas, Drawable drawable2) {
        canvas.clipRect(this.getBounds());
        canvas.clipPath(this.mClipPath);
        drawable2.draw(canvas);
    }

    private void drawWithColor(Canvas canvas, @ColorInt int n2, int n3) {
        ColorFilter colorFilter = this.mArcPaint.getColorFilter();
        if (n2 >>> 24 != 0 || colorFilter != null) {
            this.mArcPaint.setColor(n2);
            colorFilter = new RectF(this.getBounds());
            colorFilter.right = colorFilter.left + (float)n3;
            colorFilter.bottom = colorFilter.top + (float)n3;
            canvas.drawArc((RectF)colorFilter, 180.0f, 180.0f, true, this.mArcPaint);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void draw(Canvas canvas) {
        canvas.save();
        if (this.srcDrawable instanceof ColorDrawable) {
            this.drawWithColor(canvas, ((ColorDrawable)this.srcDrawable).getColor(), this.srcDrawable.getBounds().width());
        } else {
            this.drawWithClip(canvas, this.srcDrawable);
        }
        canvas.restore();
    }

    public int getOpacity() {
        return -3;
    }

    public void setAlpha(int n2) {
        this.srcDrawable.setAlpha(n2);
    }

    public void setBounds(int n2, int n3, int n4, int n5) {
        super.setBounds(n2, n3, n4, n5);
        n4 = Math.max(this.getBounds().width(), this.getBounds().height());
        this.srcDrawable.setBounds(n2, n3, n4, n4);
        this.mClipPath.reset();
        float f2 = (float)n4 * 0.5f;
        this.mClipPath.addCircle((float)n2 + f2, (float)n3 + f2, f2, Path.Direction.CW);
    }

    public void setColorFilter(ColorFilter colorFilter) {
        this.srcDrawable.setColorFilter(colorFilter);
        this.mArcPaint.setColorFilter(colorFilter);
    }

    public void setTintList(ColorStateList colorStateList) {
        this.srcDrawable.setTintList(colorStateList);
    }

    public void setTintMode(@NonNull PorterDuff.Mode mode) {
        this.srcDrawable.setTintMode(mode);
    }
}

