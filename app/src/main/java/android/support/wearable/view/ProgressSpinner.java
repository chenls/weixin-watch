/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.Animator$AnimatorListener
 *  android.animation.AnimatorListenerAdapter
 *  android.animation.ArgbEvaluator
 *  android.animation.ObjectAnimator
 *  android.annotation.TargetApi
 *  android.content.Context
 *  android.content.res.Resources$NotFoundException
 *  android.content.res.TypedArray
 *  android.graphics.Canvas
 *  android.graphics.ColorFilter
 *  android.graphics.Paint
 *  android.graphics.Paint$Cap
 *  android.graphics.Paint$Style
 *  android.graphics.RectF
 *  android.graphics.drawable.Drawable
 *  android.util.AttributeSet
 *  android.util.Property
 *  android.view.animation.AnimationUtils
 *  android.view.animation.Interpolator
 *  android.widget.ProgressBar
 */
package android.support.wearable.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.wearable.R;
import android.util.AttributeSet;
import android.util.Property;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.ProgressBar;

@TargetApi(value=20)
public class ProgressSpinner
extends ProgressBar {
    private static final Property<ProgressSpinner, Float> SHOWINGNESS = new Property<ProgressSpinner, Float>(Float.class, "showingness"){

        public Float get(ProgressSpinner progressSpinner) {
            return Float.valueOf(progressSpinner.getShowingness());
        }

        public void set(ProgressSpinner progressSpinner, Float f2) {
            progressSpinner.setShowingness(f2.floatValue());
        }
    };
    private static final int SHOWINGNESS_ANIMATION_MS = 460;
    private ObjectAnimator mAnimator;
    private int[] mColors = null;
    private ArgbEvaluator mEvaluator = new ArgbEvaluator();
    private Interpolator mInterpolator;
    private float mShowingness;
    private int mStartingLevel;

    public ProgressSpinner(Context context) {
        super(context);
        this.init(context, null, 0);
    }

    public ProgressSpinner(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.init(context, attributeSet, 0);
    }

    public ProgressSpinner(Context context, AttributeSet attributeSet, int n2) {
        super(context, attributeSet, n2);
        this.init(context, attributeSet, n2);
    }

    static /* synthetic */ int access$200(ProgressSpinner progressSpinner) {
        return progressSpinner.mStartingLevel;
    }

    static /* synthetic */ int access$202(ProgressSpinner progressSpinner, int n2) {
        progressSpinner.mStartingLevel = n2;
        return n2;
    }

    static /* synthetic */ ObjectAnimator access$302(ProgressSpinner progressSpinner, ObjectAnimator objectAnimator) {
        progressSpinner.mAnimator = objectAnimator;
        return objectAnimator;
    }

    static /* synthetic */ float access$500(ProgressSpinner progressSpinner) {
        return progressSpinner.mShowingness;
    }

    static /* synthetic */ float access$600(float f2, float f3, float f4) {
        return ProgressSpinner.lerpInvSat(f2, f3, f4);
    }

    static /* synthetic */ Interpolator access$700(ProgressSpinner progressSpinner) {
        return progressSpinner.mInterpolator;
    }

    static /* synthetic */ int access$800(ProgressSpinner progressSpinner, float f2, float f3, int n2, int n3) {
        return progressSpinner.getColor(f2, f3, n2, n3);
    }

    static /* synthetic */ float access$900(float f2, float f3, float f4) {
        return ProgressSpinner.lerpInv(f2, f3, f4);
    }

    private static float clamp(float f2, float f3, float f4) {
        if (f2 < f3) {
            return f3;
        }
        if (f2 > f4) {
            return f4;
        }
        return f2;
    }

    private int getColor(float f2, float f3, int n2, int n3) {
        return (Integer)this.mEvaluator.evaluate(ProgressSpinner.lerpInv(0.0f, f3, f2), (Object)n2, (Object)n3);
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Nullable
    private int[] getColorsFromAttributes(Context object, AttributeSet object2, int n2) {
        void var1_7;
        TypedArray typedArray;
        block4: {
            int n3;
            block5: {
                void var2_11;
                void var2_9;
                Object var5_14 = null;
                Object var4_15 = null;
                typedArray = object.obtainStyledAttributes((AttributeSet)var2_9, R.styleable.ProgressSpinner, n3, 0);
                Object var1_2 = var5_14;
                if (!typedArray.hasValue(R.styleable.ProgressSpinner_color_sequence)) break block4;
                try {
                    n3 = typedArray.getResourceId(R.styleable.ProgressSpinner_color_sequence, 0);
                    int[] nArray = this.getResources().getIntArray(n3);
                }
                catch (Resources.NotFoundException notFoundException) {
                    Object var2_12 = var4_15;
                }
                if (var2_11 == null) break block5;
                void var1_4 = var2_11;
                if (((void)var2_11).length > 0) break block4;
            }
            n3 = typedArray.getColor(R.styleable.ProgressSpinner_color_sequence, this.getResources().getColor(17170445));
            int[] nArray = new int[]{n3};
        }
        typedArray.recycle();
        return var1_7;
    }

    private float getShowingness() {
        return this.mShowingness;
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    private void init(Context context, @Nullable AttributeSet object, int n2) {
        void var2_5;
        int n3;
        if (!this.isInEditMode()) {
            this.mInterpolator = AnimationUtils.loadInterpolator((Context)context, (int)17563661);
        }
        this.setIndeterminateDrawable(new ProgressDrawable());
        if (this.getVisibility() == 0) {
            this.mShowingness = 1.0f;
        }
        int[] nArray = this.mColors;
        if (object != null) {
            nArray = this.getColorsFromAttributes(context, (AttributeSet)object, n3);
        }
        int[] nArray2 = nArray;
        if (nArray == null) {
            if (this.isInEditMode()) {
                int[] nArray3 = new int[]{context.getResources().getColor(17170456)};
            } else {
                context = this.getResources().obtainTypedArray(R.array.progress_spinner_sequence);
                int[] nArray4 = new int[context.length()];
                for (n3 = 0; n3 < context.length(); ++n3) {
                    nArray4[n3] = context.getColor(n3, 0);
                }
                context.recycle();
            }
        }
        this.setColors((int[])var2_5);
    }

    private static float lerpInv(float f2, float f3, float f4) {
        if (f2 != f3) {
            return (f4 - f2) / (f3 - f2);
        }
        return 0.0f;
    }

    private static float lerpInvSat(float f2, float f3, float f4) {
        return ProgressSpinner.saturate(ProgressSpinner.lerpInv(f2, f3, f4));
    }

    private static float saturate(float f2) {
        return ProgressSpinner.clamp(f2, 0.0f, 1.0f);
    }

    private void setShowingness(float f2) {
        this.mShowingness = f2;
        this.invalidate();
    }

    public void hide() {
        if (this.mAnimator != null) {
            this.mAnimator.cancel();
            this.mAnimator = null;
        }
        this.setVisibility(8);
        this.setShowingness(0.0f);
    }

    public void hideWithAnimation() {
        this.hideWithAnimation(null);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void hideWithAnimation(final AnimatorListenerAdapter animatorListenerAdapter) {
        if (this.mAnimator != null) {
            this.mAnimator.cancel();
            this.mAnimator = null;
        }
        if (this.getVisibility() == 0) {
            this.mAnimator = ObjectAnimator.ofFloat((Object)((Object)this), SHOWINGNESS, (float[])new float[]{this.getShowingness(), 0.0f});
            this.mAnimator.setDuration((long)(this.getShowingness() * 460.0f));
            this.mAnimator.addListener((Animator.AnimatorListener)new AnimatorListenerAdapter(){

                public void onAnimationEnd(Animator animator2) {
                    ProgressSpinner.this.setVisibility(8);
                    if (animatorListenerAdapter != null) {
                        animatorListenerAdapter.onAnimationEnd(animator2);
                    }
                }
            });
            this.mAnimator.start();
            return;
        } else {
            if (animatorListenerAdapter == null) return;
            animatorListenerAdapter.onAnimationEnd(null);
            return;
        }
    }

    public void setColors(int[] nArray) {
        if (nArray != null && nArray.length > 0) {
            this.mColors = nArray;
        }
    }

    public void showWithAnimation() {
        this.showWithAnimation(0L);
    }

    public void showWithAnimation(long l2) {
        this.showWithAnimation(l2, null);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void showWithAnimation(long l2, final @Nullable AnimatorListenerAdapter animatorListenerAdapter) {
        if (this.mAnimator != null) {
            this.mAnimator.cancel();
            this.mAnimator = null;
        }
        if (this.getVisibility() == 8) {
            this.mAnimator = ObjectAnimator.ofFloat((Object)((Object)this), SHOWINGNESS, (float[])new float[]{0.0f, 1.0f});
            this.mAnimator.setDuration(460L);
            if (l2 > 0L) {
                this.mAnimator.setStartDelay(l2);
            }
            this.mAnimator.addListener((Animator.AnimatorListener)new AnimatorListenerAdapter(){

                public void onAnimationEnd(Animator animator2) {
                    ProgressSpinner.access$202(ProgressSpinner.this, ProgressSpinner.this.getIndeterminateDrawable().getLevel());
                    ProgressSpinner.access$302(ProgressSpinner.this, null);
                    if (animatorListenerAdapter != null) {
                        animatorListenerAdapter.onAnimationEnd(animator2);
                    }
                }

                public void onAnimationStart(Animator animator2) {
                    if (animatorListenerAdapter != null) {
                        animatorListenerAdapter.onAnimationStart(animator2);
                    }
                    ProgressSpinner.this.setVisibility(0);
                }
            });
            this.mAnimator.start();
            return;
        } else {
            if (animatorListenerAdapter == null) return;
            animatorListenerAdapter.onAnimationStart(null);
            animatorListenerAdapter.onAnimationEnd(null);
            return;
        }
    }

    private class ProgressDrawable
    extends Drawable {
        static final float GROW_SHRINK_RATIO = 0.5f;
        static final float INNER_CIRCLE_MAX_SIZE = 0.5f;
        static final float INNER_CIRCLE_SHOW_END = 1.0f;
        static final float INNER_CIRCLE_SHOW_START = 0.4f;
        static final int INNER_RING_DEVISOR = 5;
        static final int MAX_LEVEL = 10000;
        static final float MIDDLE_CIRCLE_MAX_SIZE = 0.7f;
        static final float MIDDLE_CIRCLE_SHOW_END = 0.8f;
        static final float MIDDLE_CIRCLE_SHOW_START = 0.2f;
        static final float SHOW_STEP_VALUE = 0.2f;
        static final float STARTING_ANGLE = -90.0f;
        final Paint mForegroundPaint;
        final RectF mInnerCircleBounds = new RectF();

        ProgressDrawable() {
            this.mForegroundPaint = new Paint();
            this.mForegroundPaint.setAntiAlias(true);
            this.mForegroundPaint.setColor(-1);
            this.mForegroundPaint.setStyle(Paint.Style.STROKE);
            this.mForegroundPaint.setStrokeCap(Paint.Cap.ROUND);
        }

        private void drawEditModeSample(Canvas canvas) {
            RectF rectF = new RectF(this.getBounds());
            rectF.inset(10.0f, 10.0f);
            this.mForegroundPaint.setColor(ProgressSpinner.this.mColors[0]);
            this.mForegroundPaint.setStrokeWidth(7.0f);
            canvas.drawArc(rectF, 0.0f, 270.0f, false, this.mForegroundPaint);
        }

        /*
         * Unable to fully structure code
         */
        public void draw(Canvas var1_1) {
            block11: {
                block10: {
                    var2_2 = this.getBounds().width() / 2;
                    if (ProgressSpinner.this.isInEditMode()) {
                        this.drawEditModeSample(var1_1);
                        return;
                    }
                    if (ProgressSpinner.access$500(ProgressSpinner.this) < 1.0f) {
                        var3_3 = ProgressSpinner.access$700(ProgressSpinner.this).getInterpolation(ProgressSpinner.access$600(0.2f, 0.8f, ProgressSpinner.access$500(ProgressSpinner.this)));
                        var4_4 = ProgressSpinner.access$700(ProgressSpinner.this).getInterpolation(ProgressSpinner.access$600(0.4f, 1.0f, ProgressSpinner.access$500(ProgressSpinner.this)));
                        var3_3 = 0.7f * var3_3 * var2_2;
                        var4_4 = 0.5f * var4_4 * var2_2;
                        var2_2 = var2_2 - var3_3 + (var3_3 - var4_4) / 2.0f;
                        var3_3 -= var4_4;
lbl13:
                        // 2 sources

                        while (true) {
                            this.mInnerCircleBounds.set(this.getBounds());
                            this.mInnerCircleBounds.inset(var2_2, var2_2);
                            this.mForegroundPaint.setStrokeWidth(var3_3);
                            var12_5 = (this.getLevel() + 10000 - ProgressSpinner.access$200(ProgressSpinner.this)) % 10000;
                            var6_6 = 360.0f;
                            var10_7 = ProgressSpinner.access$400(ProgressSpinner.this)[0];
                            var11_8 = false;
                            var5_9 = 0.0f;
                            if (!(ProgressSpinner.access$500(ProgressSpinner.this) < 1.0f)) break block10;
                            var2_2 = 360.0f;
                            var8_10 = var11_8;
                            var4_4 = var5_9;
                            var7_11 = var10_7;
lbl27:
                            // 4 sources

                            while (true) {
                                this.mForegroundPaint.setColor(var7_11);
                                var5_9 = var2_2;
                                if (var2_2 < 1.0f) {
                                    var5_9 = 1.0f;
                                }
                                if (!((double)var3_3 > 0.1)) ** continue;
                                var1_1.rotate((float)var12_5 * 1.0E-4f * 2.0f * 360.0f - 90.0f + var4_4, this.mInnerCircleBounds.centerX(), this.mInnerCircleBounds.centerY());
                                var15_12 = this.mInnerCircleBounds;
                                if (var8_10) {
                                    var2_2 = 0.0f;
lbl37:
                                    // 2 sources

                                    while (true) {
                                        var1_1.drawArc(var15_12, var2_2, var5_9, false, this.mForegroundPaint);
                                        return;
                                    }
                                }
                                break block11;
                                break;
                            }
                            break;
                        }
                    }
                    var2_2 = this.getBounds().width() / 5;
                    var3_3 = this.getBounds().width() / 10;
                    ** while (true)
                }
                var13_13 = ProgressSpinner.access$400(ProgressSpinner.this).length;
                var14_14 = 10000 / var13_13;
                var9_15 = 0;
                while (true) {
                    block13: {
                        block12: {
                            block14: {
                                var2_2 = var6_6;
                                var7_11 = var10_7;
                                var4_4 = var5_9;
                                var8_10 = var11_8;
                                if (var9_15 >= var13_13) ** GOTO lbl27
                                if (var12_5 > (var9_15 + 1) * var14_14) break block13;
                                var2_2 = (float)(var12_5 - var9_15 * var14_14) / (float)var14_14;
                                if (!(var2_2 < 0.5f)) break block14;
                                var8_10 = true;
lbl58:
                                // 2 sources

                                while (true) {
                                    var4_4 = (360.0f - 306.0f) * var2_2;
                                    if (!var8_10) break block12;
                                    var7_11 = ProgressSpinner.access$800(ProgressSpinner.this, var2_2, 0.5f, ProgressSpinner.access$400(ProgressSpinner.this)[var9_15], ProgressSpinner.access$400(ProgressSpinner.this)[(var9_15 + 1) % ProgressSpinner.access$400(ProgressSpinner.this).length]);
                                    var2_2 = 306.0f * ProgressSpinner.access$700(ProgressSpinner.this).getInterpolation(ProgressSpinner.access$900(0.0f, 0.5f, var2_2));
                                    ** GOTO lbl27
                                    break;
                                }
                            }
                            var8_10 = false;
                            ** continue;
                        }
                        var7_11 = ProgressSpinner.access$400(ProgressSpinner.this)[(var9_15 + 1) % ProgressSpinner.access$400(ProgressSpinner.this).length];
                        var2_2 = 306.0f * (1.0f - ProgressSpinner.access$700(ProgressSpinner.this).getInterpolation(ProgressSpinner.access$900(0.5f, 1.0f, var2_2)));
                        ** continue;
                    }
                    ++var9_15;
                }
            }
            var2_2 = 306.0f - var5_9;
            ** while (true)
        }

        public int getOpacity() {
            return -3;
        }

        public void setAlpha(int n2) {
        }

        public void setColorFilter(ColorFilter colorFilter) {
        }
    }
}

