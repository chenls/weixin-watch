/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.content.res.Resources$Theme
 *  android.graphics.Canvas
 *  android.graphics.Paint
 *  android.graphics.Paint$Cap
 *  android.graphics.Paint$Join
 *  android.graphics.Paint$Style
 *  android.graphics.drawable.Drawable
 *  android.util.AttributeSet
 *  android.util.TypedValue
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.widget.FrameLayout
 *  android.widget.FrameLayout$LayoutParams
 *  android.widget.SeekBar
 *  android.widget.SeekBar$OnSeekBarChangeListener
 */
package ticwear.design.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.SeekBar;
import ticwear.design.R;
import ticwear.design.widget.ProgressBarButton;

public class VolumeBar
extends FrameLayout {
    private static final int DEFAULT_DISABLED_ALPHA = 85;
    private static final int FULL_ALPHA = 255;
    private static final float SMALL_FLOAT = 1.0E-4f;
    private int mBgColor;
    private int mDisabledAlpha;
    private int mDrawableRadius;
    private OnVolumeChangedListener mListener;
    private ProgressBarButton mMaxButton;
    private Drawable mMaxButtonDrawable;
    private ProgressBarButton.TouchListener mMaxButtonListener;
    private int mMaxLimit = 100;
    private ProgressBarButton mMinButton;
    private Drawable mMinButtonDrawable;
    private ProgressBarButton.TouchListener mMinButtonListener = new ProgressBarButton.TouchListener(){

        @Override
        public void onDown() {
            VolumeBar.access$302(VolumeBar.this, VolumeBar.this.mProgress);
        }

        @Override
        public void onLongPress() {
            VolumeBar.this.adjustVolume(-1, false);
        }

        @Override
        public void onUp() {
            if (VolumeBar.this.mProgressStart - VolumeBar.this.mProgress < VolumeBar.this.mProgressStep) {
                int n2 = Math.min(VolumeBar.this.mProgressStep, VolumeBar.this.mProgressStep - (VolumeBar.this.mProgressStart - VolumeBar.this.mProgress));
                VolumeBar.this.adjustVolume(-n2, false);
            }
        }
    };
    private int mMinLimit = 0;
    private Drawable mNoVolumeDrawable;
    private Paint mPaint;
    private int mProgress = 50;
    private int mProgressStart;
    private int mProgressStep = 10;
    private SeekBar mSeekbar;
    private int mTouchPadding;
    private ColorStateList mValueColor;
    private Drawable mVolumeDrawable;

    public VolumeBar(Context context) {
        this(context, null);
    }

    public VolumeBar(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public VolumeBar(Context context, AttributeSet attributeSet, int n2) {
        this(context, attributeSet, n2, R.style.Widget_Ticwear_VolumeBar);
    }

    public VolumeBar(Context context, AttributeSet attributeSet, int n2, int n3) {
        super(context, attributeSet, n2, n3);
        this.mMaxButtonListener = new ProgressBarButton.TouchListener(){

            @Override
            public void onDown() {
                VolumeBar.access$302(VolumeBar.this, VolumeBar.this.mProgress);
            }

            @Override
            public void onLongPress() {
                VolumeBar.this.adjustVolume(1, false);
            }

            @Override
            public void onUp() {
                if (VolumeBar.this.mProgress - VolumeBar.this.mProgressStart < VolumeBar.this.mProgressStep) {
                    int n2 = Math.min(VolumeBar.this.mProgressStep, VolumeBar.this.mProgressStep - (VolumeBar.this.mProgress - VolumeBar.this.mProgressStart));
                    VolumeBar.this.adjustVolume(n2, false);
                }
            }
        };
        LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService("layout_inflater");
        this.setWillNotDraw(false);
        layoutInflater.inflate(R.layout.volume_bar_ticwear, (ViewGroup)this, true);
        attributeSet = context.obtainStyledAttributes(attributeSet, R.styleable.VolumeBar, n2, n3);
        this.mDrawableRadius = attributeSet.getDimensionPixelSize(R.styleable.VolumeBar_tic_vb_btnImageSize, 32) / 2;
        this.mBgColor = attributeSet.getColor(R.styleable.VolumeBar_tic_vb_bgColor, -65536);
        this.mValueColor = attributeSet.getColorStateList(R.styleable.VolumeBar_tic_vb_valueColor);
        this.mTouchPadding = attributeSet.getDimensionPixelSize(R.styleable.VolumeBar_tic_vb_touchPadding, 0);
        n2 = attributeSet.getResourceId(R.styleable.VolumeBar_tic_vb_thumbImage, 0);
        n3 = attributeSet.getResourceId(R.styleable.VolumeBar_tic_vb_thumbLeftImage, 0);
        attributeSet.recycle();
        this.mDisabledAlpha = 85;
        if (!this.isInEditMode()) {
            attributeSet = new TypedValue();
            if (context.getTheme().resolveAttribute(0x1010033, (TypedValue)attributeSet, true)) {
                this.mDisabledAlpha = (int)(255.0f * attributeSet.getFloat());
            }
        }
        this.mPaint = new Paint();
        this.mPaint.setDither(true);
        this.mPaint.setStyle(Paint.Style.STROKE);
        this.mPaint.setStrokeJoin(Paint.Join.ROUND);
        this.mPaint.setStrokeCap(Paint.Cap.ROUND);
        this.mPaint.setAntiAlias(true);
        context = context.getApplicationContext().getTheme();
        if (n2 != 0) {
            this.mVolumeDrawable = this.getResources().getDrawable(n2, (Resources.Theme)context);
            this.mNoVolumeDrawable = this.getResources().getDrawable(n3, (Resources.Theme)context);
        }
        this.mMinButtonDrawable = this.getResources().getDrawable(R.drawable.tic_ic_minus_32px, (Resources.Theme)context);
        this.mMaxButtonDrawable = this.getResources().getDrawable(R.drawable.tic_ic_plus_32px, (Resources.Theme)context);
        this.mMinButton = (ProgressBarButton)this.findViewById(R.id.min);
        this.mMinButton.setDefaultImageSize(this.mDrawableRadius * 2);
        this.mMinButton.setTouchListener(this.mMinButtonListener);
        this.mMaxButton = (ProgressBarButton)this.findViewById(R.id.max);
        this.mMaxButton.setTouchListener(this.mMaxButtonListener);
        this.mSeekbar = (SeekBar)this.findViewById(R.id.seekbar);
        this.mSeekbar.setProgress(this.mProgress);
        context = new FrameLayout.LayoutParams(-1, -1);
        context.setMargins(this.mTouchPadding, 0, this.mTouchPadding, 0);
        this.mSeekbar.setLayoutParams((ViewGroup.LayoutParams)context);
        this.mSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            public void onProgressChanged(SeekBar seekBar, int n2, boolean bl2) {
                if (bl2 && (n2 = VolumeBar.this.validateProgress(n2)) != VolumeBar.this.mProgress) {
                    VolumeBar.this.adjustVolume(n2 - VolumeBar.this.mProgress, true);
                }
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    static /* synthetic */ int access$302(VolumeBar volumeBar, int n2) {
        volumeBar.mProgressStart = n2;
        return n2;
    }

    private void adjustVolume(int n2, boolean bl2) {
        this.mProgress += n2;
        this.mProgress = this.validateProgress(this.mProgress);
        if (!bl2) {
            this.mSeekbar.setProgress(this.mProgress);
        }
        if (this.mListener != null) {
            this.mListener.onVolumeChanged(this, this.mProgress, true);
        }
        this.invalidate();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private int validateProgress(int n2) {
        if (n2 > this.mMaxLimit) {
            return this.mMaxLimit;
        }
        int n3 = n2;
        if (n2 >= this.mMinLimit) return n3;
        return this.mMinLimit;
    }

    protected void drawableStateChanged() {
        super.drawableStateChanged();
        this.invalidate();
    }

    public int getProgress() {
        return this.mProgress;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void onDraw(Canvas canvas) {
        int n2 = this.getHeight() / 2 - this.mTouchPadding;
        int n3 = n2 + this.mTouchPadding;
        this.mPaint.setStrokeWidth((float)(n2 * 2));
        this.mPaint.setColor(this.mBgColor);
        canvas.drawLine((float)n3, (float)n3, (float)(this.getWidth() - n3), (float)n3, this.mPaint);
        this.mPaint.setColor(this.mValueColor.getColorForState(this.getDrawableState(), this.mValueColor.getDefaultColor()));
        float f2 = n3;
        float f3 = n3;
        float f4 = n3;
        canvas.drawLine(f2, f3, (float)this.mProgress / 100.0f * (float)(this.getWidth() - n3 * 2) + (f4 + 1.0E-4f), (float)n3, this.mPaint);
        if ((float)this.mTouchPadding + (float)this.mProgress / 100.0f * (float)(this.getWidth() - n3 * 2) < (float)n3) {
            this.mMinButton.setImageDrawable(null);
        } else {
            this.mMinButton.setImageDrawable(this.mMinButtonDrawable);
        }
        if ((float)(this.mTouchPadding + n2 * 2) + (float)this.mProgress / 100.0f * (float)(this.getWidth() - n3 * 2) > (float)(this.getWidth() - n3)) {
            this.mMaxButton.setImageDrawable(null);
        } else {
            this.mMaxButton.setImageDrawable(this.mMaxButtonDrawable);
        }
        Drawable drawable2 = this.mProgress == 0 ? this.mNoVolumeDrawable : this.mVolumeDrawable;
        if (drawable2 != null) {
            drawable2.setBounds((int)((float)n3 + (float)this.mProgress / 100.0f * (float)(this.getWidth() - n3 * 2) - (float)this.mDrawableRadius), n3 - this.mDrawableRadius, (int)((float)n3 + (float)this.mProgress / 100.0f * (float)(this.getWidth() - n3 * 2) + (float)this.mDrawableRadius), this.mDrawableRadius + n3);
            drawable2.draw(canvas);
        }
        super.onDraw(canvas);
    }

    protected void onVisibilityChanged(@NonNull View view, int n2) {
        if (n2 == 0) {
            this.mMinButton.setTouchListener(this.mMinButtonListener);
            this.mMaxButton.setTouchListener(this.mMaxButtonListener);
            return;
        }
        this.mMinButton.setTouchListener(null);
        this.mMaxButton.setTouchListener(null);
    }

    public void setBgColor(@ColorInt int n2) {
        this.mBgColor = n2;
        this.invalidate();
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setDisabledAlpha(int n2) {
        int n3;
        if (this.mDisabledAlpha == n2) {
            return;
        }
        if (n2 > 255) {
            n3 = 255;
        } else {
            n3 = n2;
            if (n2 < 0) {
                n3 = 0;
            }
        }
        this.mDisabledAlpha = n3;
        this.invalidate();
    }

    public void setDrawable(Drawable drawable2) {
        this.mVolumeDrawable = drawable2;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setEnabled(boolean bl2) {
        this.mSeekbar.setEnabled(bl2);
        this.mMinButton.setEnabled(bl2);
        this.mMaxButton.setEnabled(bl2);
        if (bl2) {
            this.mMinButton.setTouchListener(this.mMinButtonListener);
            this.mMaxButton.setTouchListener(this.mMaxButtonListener);
            this.mPaint.setAlpha(255);
            this.mMinButtonDrawable.setAlpha(255);
            this.mMaxButtonDrawable.setAlpha(255);
            this.mNoVolumeDrawable.setAlpha(255);
            this.mVolumeDrawable.setAlpha(255);
        } else {
            this.mMinButton.setTouchListener(null);
            this.mMaxButton.setTouchListener(null);
            this.mPaint.setAlpha(this.mDisabledAlpha);
            this.mMinButtonDrawable.setAlpha(this.mDisabledAlpha);
            this.mMaxButtonDrawable.setAlpha(this.mDisabledAlpha);
            this.mNoVolumeDrawable.setAlpha(this.mDisabledAlpha);
            this.mVolumeDrawable.setAlpha(this.mDisabledAlpha);
        }
        this.invalidate();
    }

    public void setMaxLimit(int n2) {
        int n3 = n2;
        if (n2 < this.mMinLimit) {
            n3 = this.mMinLimit;
        }
        this.mMaxLimit = n3;
        if (this.mProgress > this.mMaxLimit) {
            this.mProgress = this.mMaxLimit;
            this.mSeekbar.setProgress(this.mProgress);
            if (this.mListener != null) {
                this.mListener.onVolumeChanged(this, this.mProgress, false);
            }
            this.invalidate();
        }
    }

    public void setMinLimit(int n2) {
        int n3 = n2;
        if (n2 > this.mMaxLimit) {
            n3 = this.mMaxLimit;
        }
        this.mMinLimit = n3;
        if (this.mProgress < this.mMinLimit) {
            this.mProgress = this.mMinLimit;
            this.mSeekbar.setProgress(this.mProgress);
            if (this.mListener != null) {
                this.mListener.onVolumeChanged(this, this.mProgress, false);
            }
            this.invalidate();
        }
    }

    public void setOnVolumeChangedListetener(OnVolumeChangedListener onVolumeChangedListener) {
        this.mListener = onVolumeChangedListener;
    }

    public void setProgress(int n2) {
        this.mProgress = this.validateProgress(n2);
        this.mSeekbar.setProgress(this.mProgress);
        if (this.mListener != null) {
            this.mListener.onVolumeChanged(this, this.mProgress, false);
        }
        this.invalidate();
    }

    public void setProgressDif(int n2) {
        this.mProgress += n2;
        this.mProgress = this.validateProgress(this.mProgress);
        this.mSeekbar.setProgress(this.mProgress);
        if (this.mListener != null) {
            this.mListener.onVolumeChanged(this, this.mProgress, false);
        }
        this.invalidate();
    }

    public void setStep(int n2) {
        this.mProgressStep = n2;
    }

    public void setValueColor(@ColorInt int n2) {
        this.setValueColor(ColorStateList.valueOf((int)n2));
    }

    public void setValueColor(ColorStateList colorStateList) {
        if (colorStateList != null) {
            this.mValueColor = colorStateList;
            this.invalidate();
        }
    }

    public static interface OnVolumeChangedListener {
        public void onVolumeChanged(VolumeBar var1, int var2, boolean var3);
    }
}

