/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.graphics.Canvas
 *  android.graphics.Rect
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.LayerDrawable
 */
package ticwear.design.drawable;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.support.annotation.NonNull;
import java.util.Arrays;
import ticwear.design.drawable.CircularProgressDrawable;

public class CircularProgressContainerDrawable
extends LayerDrawable {
    private int mStrokeSize;

    public CircularProgressContainerDrawable(@NonNull Drawable[] drawableArray, @NonNull CircularProgressDrawable circularProgressDrawable) {
        super(CircularProgressContainerDrawable.buildLayers(drawableArray, circularProgressDrawable));
        this.mStrokeSize = circularProgressDrawable.getStrokeSize();
    }

    private static Drawable[] buildLayers(@NonNull Drawable[] drawableArray, @NonNull CircularProgressDrawable circularProgressDrawable) {
        Drawable[] drawableArray2 = Arrays.copyOf(drawableArray, drawableArray.length + 1);
        drawableArray2[drawableArray.length] = circularProgressDrawable;
        return drawableArray2;
    }

    public void draw(Canvas canvas) {
        canvas.save();
        canvas.translate((float)this.mStrokeSize, (float)this.mStrokeSize);
        for (int i2 = 0; i2 < this.getNumberOfLayers() - 1; ++i2) {
            this.getContentDrawable(i2).draw(canvas);
        }
        canvas.restore();
        this.getProgressDrawable().draw(canvas);
    }

    public Drawable getContentDrawable(int n2) {
        if (this.getNumberOfLayers() > n2 + 1) {
            return this.getDrawable(n2);
        }
        return null;
    }

    public CircularProgressDrawable getProgressDrawable() {
        if (this.getNumberOfLayers() > 0 && this.getDrawable(this.getNumberOfLayers() - 1) instanceof CircularProgressDrawable) {
            return (CircularProgressDrawable)this.getDrawable(this.getNumberOfLayers() - 1);
        }
        return null;
    }

    protected void onBoundsChange(Rect rect) {
        for (int i2 = 0; i2 < this.getNumberOfLayers() - 1; ++i2) {
            this.getContentDrawable(i2).setBounds(rect.left, rect.top, rect.right - this.mStrokeSize * 2, rect.bottom - this.mStrokeSize * 2);
        }
        this.getProgressDrawable().setBounds(rect);
    }
}

