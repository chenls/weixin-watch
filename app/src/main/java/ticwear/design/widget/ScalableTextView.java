/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.content.Context
 *  android.graphics.Canvas
 *  android.graphics.RectF
 *  android.util.AttributeSet
 *  android.util.TypedValue
 *  android.widget.TextView
 */
package ticwear.design.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.TextView;
import ticwear.design.R;

public class ScalableTextView
extends TextView {
    private float mScaleFactor;
    private RectF mTempDst;
    private RectF mTempSrc = new RectF();
    private float mTextScale = 1.0f;

    public ScalableTextView(Context context) {
        this(context, null);
    }

    public ScalableTextView(Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 16842884);
    }

    public ScalableTextView(Context context, @Nullable AttributeSet attributeSet, int n2) {
        this(context, attributeSet, n2, 0);
    }

    /*
     * Enabled aggressive block sorting
     */
    @TargetApi(value=21)
    public ScalableTextView(Context context, AttributeSet attributeSet, int n2, int n3) {
        super(context, attributeSet, n2, n3);
        float f2;
        this.mTempDst = new RectF();
        context = context.obtainStyledAttributes(attributeSet, R.styleable.ScalableTextView, n2, n3);
        if (this.isInEditMode()) {
            f2 = 0.0f;
        } else {
            attributeSet = new TypedValue();
            this.getResources().getValue(R.integer.design_factor_title_scale, (TypedValue)attributeSet, true);
            f2 = attributeSet.getFloat();
        }
        this.mScaleFactor = context.getFloat(R.styleable.ScalableTextView_tic_scaleFactor, f2);
        context.recycle();
    }

    private void updateContainerRect(@NonNull RectF rectF, int n2, int n3, int n4, int n5) {
        rectF.set(0.0f, 0.0f, (float)(n4 - n2), (float)(n5 - n3));
    }

    /*
     * Enabled aggressive block sorting
     */
    private void updateDrawingMatrix() {
        if (this.mTempSrc.isEmpty() || this.mTempDst.isEmpty()) {
            return;
        }
        if (!(this.mScaleFactor > 0.0f)) {
            this.mTextScale = 1.0f;
            return;
        }
        int n2 = (int)this.mTempSrc.width();
        int n3 = (int)this.mTempSrc.height();
        int n4 = (int)this.mTempDst.width();
        int n5 = (int)this.mTempDst.height();
        float f2 = n2 * n5 > n4 * n3 ? (float)n5 / (float)n3 : (float)n4 / (float)n2;
        this.mTextScale = (f2 - 1.0f) * this.mScaleFactor + 1.0f;
    }

    public float getScaleFactor() {
        return this.mScaleFactor;
    }

    protected void onDraw(Canvas canvas) {
        int n2 = this.getWidth();
        int n3 = this.getHeight();
        canvas.scale(this.mTextScale, this.mTextScale, (float)n2 * 0.5f, (float)n3 * 0.5f);
        super.onDraw(canvas);
    }

    protected void onLayout(boolean bl2, int n2, int n3, int n4, int n5) {
        super.onLayout(bl2, n2, n3, n4, n5);
        if (bl2) {
            if (this.mTempSrc.isEmpty()) {
                this.updateContainerRect(this.mTempSrc, n2, n3, n4, n5);
            }
            this.updateContainerRect(this.mTempDst, n2, n3, n4, n5);
            this.updateDrawingMatrix();
        }
    }

    public void setScaleFactor(float f2) {
        this.mScaleFactor = f2;
        this.updateDrawingMatrix();
    }
}

