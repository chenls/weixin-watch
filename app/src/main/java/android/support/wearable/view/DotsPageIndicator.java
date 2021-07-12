/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.Animator$AnimatorListener
 *  android.annotation.TargetApi
 *  android.content.Context
 *  android.graphics.Canvas
 *  android.graphics.Paint
 *  android.graphics.Paint$Style
 *  android.graphics.RadialGradient
 *  android.graphics.Shader
 *  android.graphics.Shader$TileMode
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.View$MeasureSpec
 */
package android.support.wearable.view;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.support.wearable.R;
import android.support.wearable.view.GridPagerAdapter;
import android.support.wearable.view.GridViewPager;
import android.support.wearable.view.SimpleAnimatorListener;
import android.util.AttributeSet;
import android.view.View;
import java.util.concurrent.TimeUnit;

@TargetApi(value=20)
public class DotsPageIndicator
extends View
implements GridViewPager.OnPageChangeListener,
GridViewPager.OnAdapterChangeListener {
    private static final String TAG = "Dots";
    private GridPagerAdapter mAdapter;
    private GridViewPager.OnAdapterChangeListener mAdapterChangeListener;
    private int mColumnCount;
    private int mCurrentState;
    private int mDotColor;
    private int mDotColorSelected;
    private int mDotFadeInDuration;
    private int mDotFadeOutDelay;
    private int mDotFadeOutDuration;
    private boolean mDotFadeWhenIdle;
    private final Paint mDotPaint;
    private final Paint mDotPaintSelected;
    private final Paint mDotPaintShadow;
    private final Paint mDotPaintShadowSelected;
    private float mDotRadius;
    private float mDotRadiusSelected;
    private int mDotShadowColor;
    private float mDotShadowDx;
    private float mDotShadowDy;
    private float mDotShadowRadius;
    private int mDotSpacing;
    private GridViewPager.OnPageChangeListener mPageChangeListener;
    private GridViewPager mPager;
    private int mSelectedColumn;
    private int mSelectedRow;
    private boolean mVisible;

    public DotsPageIndicator(Context context) {
        this(context, null);
    }

    public DotsPageIndicator(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    /*
     * Enabled aggressive block sorting
     */
    public DotsPageIndicator(Context context, AttributeSet attributeSet, int n2) {
        super(context, attributeSet, n2);
        context = this.getContext().obtainStyledAttributes(attributeSet, R.styleable.DotsPageIndicator, 0, R.style.DotsPageIndicatorStyle);
        this.mDotSpacing = context.getDimensionPixelOffset(R.styleable.DotsPageIndicator_dotSpacing, 0);
        this.mDotRadius = context.getDimension(R.styleable.DotsPageIndicator_dotRadius, 0.0f);
        this.mDotRadiusSelected = context.getDimension(R.styleable.DotsPageIndicator_dotRadiusSelected, 0.0f);
        this.mDotColor = context.getColor(R.styleable.DotsPageIndicator_dotColor, 0);
        this.mDotColorSelected = context.getColor(R.styleable.DotsPageIndicator_dotColorSelected, 0);
        this.mDotFadeOutDelay = context.getInt(R.styleable.DotsPageIndicator_dotFadeOutDelay, 0);
        this.mDotFadeOutDuration = context.getInt(R.styleable.DotsPageIndicator_dotFadeOutDuration, 0);
        this.mDotFadeInDuration = context.getInt(R.styleable.DotsPageIndicator_dotFadeInDuration, 0);
        this.mDotFadeWhenIdle = context.getBoolean(R.styleable.DotsPageIndicator_dotFadeWhenIdle, false);
        this.mDotShadowDx = context.getDimension(R.styleable.DotsPageIndicator_dotShadowDx, 0.0f);
        this.mDotShadowDy = context.getDimension(R.styleable.DotsPageIndicator_dotShadowDy, 0.0f);
        this.mDotShadowRadius = context.getDimension(R.styleable.DotsPageIndicator_dotShadowRadius, 0.0f);
        this.mDotShadowColor = context.getColor(R.styleable.DotsPageIndicator_dotShadowColor, 0);
        context.recycle();
        this.mDotPaint = new Paint(1);
        this.mDotPaint.setColor(this.mDotColor);
        this.mDotPaint.setStyle(Paint.Style.FILL);
        this.mDotPaintSelected = new Paint(1);
        this.mDotPaintSelected.setColor(this.mDotColorSelected);
        this.mDotPaintSelected.setStyle(Paint.Style.FILL);
        this.mDotPaintShadow = new Paint(1);
        this.mDotPaintShadowSelected = new Paint(1);
        this.mCurrentState = 0;
        if (this.isInEditMode()) {
            this.mColumnCount = 5;
            this.mSelectedColumn = 2;
            this.mDotFadeWhenIdle = false;
        }
        if (this.mDotFadeWhenIdle) {
            this.mVisible = false;
            this.animate().alpha(0.0f).setStartDelay(2000L).setDuration((long)this.mDotFadeOutDuration).start();
        } else {
            this.animate().cancel();
            this.setAlpha(1.0f);
        }
        this.updateShadows();
    }

    static /* synthetic */ boolean access$002(DotsPageIndicator dotsPageIndicator, boolean bl2) {
        dotsPageIndicator.mVisible = bl2;
        return bl2;
    }

    private void columnChanged(int n2) {
        this.mSelectedColumn = n2;
        this.invalidate();
    }

    private void fadeIn() {
        this.mVisible = true;
        this.animate().cancel();
        this.animate().alpha(1.0f).setStartDelay(0L).setDuration((long)this.mDotFadeInDuration).start();
    }

    private void fadeInOut() {
        this.mVisible = true;
        this.animate().cancel();
        this.animate().alpha(1.0f).setStartDelay(0L).setDuration((long)this.mDotFadeInDuration).setListener((Animator.AnimatorListener)new SimpleAnimatorListener(){

            @Override
            public void onAnimationComplete(Animator animator2) {
                DotsPageIndicator.access$002(DotsPageIndicator.this, false);
                DotsPageIndicator.this.animate().alpha(0.0f).setListener(null).setStartDelay((long)DotsPageIndicator.this.mDotFadeOutDelay).setDuration((long)DotsPageIndicator.this.mDotFadeOutDuration).start();
            }
        }).start();
    }

    private void fadeOut(long l2) {
        this.mVisible = false;
        this.animate().cancel();
        this.animate().alpha(0.0f).setStartDelay(l2).setDuration((long)this.mDotFadeOutDuration).start();
    }

    /*
     * Enabled aggressive block sorting
     */
    private void rowChanged(int n2, int n3) {
        this.mSelectedRow = n2;
        if ((n2 = this.mAdapter.getColumnCount(n2)) != this.mColumnCount) {
            this.mColumnCount = n2;
            this.mSelectedColumn = n3;
            this.requestLayout();
            return;
        } else {
            if (n3 == this.mSelectedColumn) return;
            this.mSelectedColumn = n3;
            this.invalidate();
            return;
        }
    }

    private void updateDotPaint(Paint paint, Paint paint2, float f2, float f3, int n2, int n3) {
        f3 = f2 + f3;
        Shader.TileMode tileMode = Shader.TileMode.CLAMP;
        paint2.setShader((Shader)new RadialGradient(0.0f, 0.0f, f3, new int[]{n3, n3, 0}, new float[]{0.0f, f2 /= f3, 1.0f}, tileMode));
        paint.setColor(n2);
        paint.setStyle(Paint.Style.FILL);
    }

    private void updateShadows() {
        this.updateDotPaint(this.mDotPaint, this.mDotPaintShadow, this.mDotRadius, this.mDotShadowRadius, this.mDotColor, this.mDotShadowColor);
        this.updateDotPaint(this.mDotPaintSelected, this.mDotPaintShadowSelected, this.mDotRadiusSelected, this.mDotShadowRadius, this.mDotColorSelected, this.mDotShadowColor);
    }

    public int getDotColor() {
        return this.mDotColor;
    }

    public int getDotColorSelected() {
        return this.mDotColorSelected;
    }

    public int getDotFadeInDuration() {
        return this.mDotFadeInDuration;
    }

    public int getDotFadeOutDelay() {
        return this.mDotFadeOutDelay;
    }

    public int getDotFadeOutDuration() {
        return this.mDotFadeOutDuration;
    }

    public boolean getDotFadeWhenIdle() {
        return this.mDotFadeWhenIdle;
    }

    public float getDotRadius() {
        return this.mDotRadius;
    }

    public float getDotRadiusSelected() {
        return this.mDotRadiusSelected;
    }

    public int getDotShadowColor() {
        return this.mDotShadowColor;
    }

    public float getDotShadowDx() {
        return this.mDotShadowDx;
    }

    public float getDotShadowDy() {
        return this.mDotShadowDy;
    }

    public float getDotShadowRadius() {
        return this.mDotShadowRadius;
    }

    public float getDotSpacing() {
        return this.mDotSpacing;
    }

    @Override
    public void onAdapterChanged(GridPagerAdapter gridPagerAdapter, GridPagerAdapter gridPagerAdapter2) {
        this.mAdapter = gridPagerAdapter2;
        if (this.mAdapter != null) {
            this.rowChanged(0, 0);
            if (this.mDotFadeWhenIdle) {
                this.fadeInOut();
            }
        }
        if (this.mAdapterChangeListener != null) {
            this.mAdapterChangeListener.onAdapterChanged(gridPagerAdapter, gridPagerAdapter2);
        }
    }

    @Override
    public void onDataSetChanged() {
        if (this.mAdapter != null && this.mAdapter.getRowCount() > 0) {
            this.rowChanged(0, 0);
        }
        if (this.mAdapterChangeListener != null) {
            this.mAdapterChangeListener.onDataSetChanged();
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.mColumnCount > 1) {
            float f2 = this.getPaddingLeft();
            float f3 = (float)this.mDotSpacing / 2.0f;
            float f4 = (float)this.getHeight() / 2.0f;
            canvas.save();
            canvas.translate(f2 + f3, f4);
            for (int i2 = 0; i2 < this.mColumnCount; ++i2) {
                if (i2 == this.mSelectedColumn) {
                    f2 = this.mDotRadiusSelected;
                    f3 = this.mDotShadowRadius;
                    canvas.drawCircle(this.mDotShadowDx, this.mDotShadowDy, f2 + f3, this.mDotPaintShadowSelected);
                    canvas.drawCircle(0.0f, 0.0f, this.mDotRadiusSelected, this.mDotPaintSelected);
                } else {
                    f2 = this.mDotRadius;
                    f3 = this.mDotShadowRadius;
                    canvas.drawCircle(this.mDotShadowDx, this.mDotShadowDy, f2 + f3, this.mDotPaintShadow);
                    canvas.drawCircle(0.0f, 0.0f, this.mDotRadius, this.mDotPaint);
                }
                canvas.translate((float)this.mDotSpacing, 0.0f);
            }
            canvas.restore();
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void onMeasure(int n2, int n3) {
        int n4;
        int n5;
        if (View.MeasureSpec.getMode((int)n2) == 0x40000000) {
            n5 = View.MeasureSpec.getSize((int)n2);
        } else {
            n5 = this.mColumnCount;
            n4 = this.mDotSpacing;
            n5 = this.getPaddingLeft() + n5 * n4 + this.getPaddingRight();
        }
        if (View.MeasureSpec.getMode((int)n3) == 0x40000000) {
            n4 = View.MeasureSpec.getSize((int)n3);
        } else {
            n4 = (int)((float)((int)Math.ceil(2.0f * Math.max(this.mDotRadius + this.mDotShadowRadius, this.mDotRadiusSelected + this.mDotShadowRadius))) + this.mDotShadowDy);
            n4 = this.getPaddingTop() + n4 + this.getPaddingBottom();
        }
        this.setMeasuredDimension(DotsPageIndicator.resolveSizeAndState((int)n5, (int)n2, (int)0), DotsPageIndicator.resolveSizeAndState((int)n4, (int)n3, (int)0));
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void onPageScrollStateChanged(int n2) {
        if (this.mCurrentState != n2) {
            this.mCurrentState = n2;
            if (this.mDotFadeWhenIdle && n2 == 0) {
                if (this.mVisible) {
                    this.fadeOut(this.mDotFadeOutDelay);
                } else {
                    this.fadeInOut();
                }
            }
        }
        if (this.mPageChangeListener != null) {
            this.mPageChangeListener.onPageScrollStateChanged(n2);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void onPageScrolled(int n2, int n3, float f2, float f3, int n4, int n5) {
        if (this.mDotFadeWhenIdle && this.mCurrentState == 1) {
            if (f3 != 0.0f) {
                if (!this.mVisible) {
                    this.fadeIn();
                }
            } else if (this.mVisible) {
                this.fadeOut(0L);
            }
        }
        if (this.mPageChangeListener != null) {
            this.mPageChangeListener.onPageScrolled(n2, n3, f2, f3, n4, n5);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void onPageSelected(int n2, int n3) {
        if (n2 != this.mSelectedRow) {
            this.rowChanged(n2, n3);
        } else if (n3 != this.mSelectedColumn) {
            this.columnChanged(n3);
        }
        if (this.mPageChangeListener != null) {
            this.mPageChangeListener.onPageSelected(n2, n3);
        }
    }

    public void setDotColor(int n2) {
        if (this.mDotColor != n2) {
            this.mDotColor = n2;
            this.invalidate();
        }
    }

    public void setDotColorSelected(int n2) {
        if (this.mDotColorSelected != n2) {
            this.mDotColorSelected = n2;
            this.invalidate();
        }
    }

    public void setDotFadeInDuration(int n2, TimeUnit timeUnit) {
        this.mDotFadeInDuration = (int)TimeUnit.MILLISECONDS.convert(n2, timeUnit);
    }

    public void setDotFadeOutDelay(int n2) {
        this.mDotFadeOutDelay = n2;
    }

    public void setDotFadeOutDuration(int n2, TimeUnit timeUnit) {
        this.mDotFadeOutDuration = (int)TimeUnit.MILLISECONDS.convert(n2, timeUnit);
    }

    public void setDotFadeWhenIdle(boolean bl2) {
        this.mDotFadeWhenIdle = bl2;
        if (!bl2) {
            this.fadeIn();
        }
    }

    public void setDotRadius(int n2) {
        if (this.mDotRadius != (float)n2) {
            this.mDotRadius = n2;
            this.updateShadows();
            this.invalidate();
        }
    }

    public void setDotRadiusSelected(int n2) {
        if (this.mDotRadiusSelected != (float)n2) {
            this.mDotRadiusSelected = n2;
            this.updateShadows();
            this.invalidate();
        }
    }

    public void setDotShadowColor(int n2) {
        this.mDotShadowColor = n2;
        this.updateShadows();
        this.invalidate();
    }

    public void setDotShadowDx(float f2) {
        this.mDotShadowDx = f2;
        this.invalidate();
    }

    public void setDotShadowDy(float f2) {
        this.mDotShadowDy = f2;
        this.invalidate();
    }

    public void setDotShadowRadius(float f2) {
        if (this.mDotShadowRadius != f2) {
            this.mDotShadowRadius = f2;
            this.updateShadows();
            this.invalidate();
        }
    }

    public void setDotSpacing(int n2) {
        if (this.mDotSpacing != n2) {
            this.mDotSpacing = n2;
            this.requestLayout();
        }
    }

    public void setOnAdapterChangeListener(GridViewPager.OnAdapterChangeListener onAdapterChangeListener) {
        this.mAdapterChangeListener = onAdapterChangeListener;
    }

    public void setOnPageChangeListener(GridViewPager.OnPageChangeListener onPageChangeListener) {
        this.mPageChangeListener = onPageChangeListener;
    }

    public void setPager(GridViewPager gridViewPager) {
        if (this.mPager != gridViewPager) {
            if (this.mPager != null) {
                this.mPager.setOnPageChangeListener(null);
                this.mPager.setOnAdapterChangeListener(null);
                this.mPager = null;
            }
            this.mPager = gridViewPager;
            if (this.mPager != null) {
                this.mPager.setOnPageChangeListener(this);
                this.mPager.setOnAdapterChangeListener(this);
                this.mAdapter = this.mPager.getAdapter();
            }
        }
        if (this.mAdapter != null && this.mAdapter.getRowCount() > 0) {
            this.rowChanged(0, 0);
        }
    }
}

