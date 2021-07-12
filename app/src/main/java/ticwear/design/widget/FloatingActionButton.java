/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.animation.ValueAnimator
 *  android.animation.ValueAnimator$AnimatorUpdateListener
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.graphics.Rect
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.RippleDrawable
 *  android.graphics.drawable.ShapeDrawable
 *  android.graphics.drawable.shapes.OvalShape
 *  android.graphics.drawable.shapes.Shape
 *  android.util.AttributeSet
 *  android.util.Log
 *  android.view.GestureDetector
 *  android.view.GestureDetector$OnGestureListener
 *  android.view.GestureDetector$SimpleOnGestureListener
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.View$MeasureSpec
 */
package ticwear.design.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.Shape;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import java.util.List;
import ticwear.design.R;
import ticwear.design.drawable.CircularProgressContainerDrawable;
import ticwear.design.drawable.CircularProgressDrawable;
import ticwear.design.widget.AppBarLayout;
import ticwear.design.widget.CoordinatorLayout;
import ticwear.design.widget.FloatingActionButtonAnimator;
import ticwear.design.widget.ViewGroupUtils;
import ticwear.design.widget.VisibilityAwareImageButton;

@CoordinatorLayout.DefaultBehavior(value=Behavior.class)
public class FloatingActionButton
extends VisibilityAwareImageButton {
    private static final String LOG_TAG = "FloatingActionButton";
    private static final int SIZE_MINI = 1;
    private static final int SIZE_NORMAL = 0;
    private final FloatingActionButtonAnimator mAnimator;
    private ValueAnimator mDelayedConfirmationAnimator;
    private DelayedConfirmationListener mDelayedConfirmationListener;
    private GestureDetector mGestureDetector;
    private int mImagePadding;
    private final Rect mProgressPadding = new Rect();
    private boolean mShowProgress;
    private int mSize;
    private Rect mTouchArea = new Rect();

    public FloatingActionButton(Context context) {
        this(context, null);
    }

    public FloatingActionButton(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public FloatingActionButton(Context context, AttributeSet attributeSet, int n2) {
        this(context, attributeSet, n2, R.style.Widget_Ticwear_FloatingActionButton);
    }

    public FloatingActionButton(Context object, AttributeSet attributeSet, int n2, int n3) {
        super((Context)object, attributeSet, n2, n3);
        attributeSet = object.obtainStyledAttributes(attributeSet, R.styleable.FloatingActionButton, n2, n3);
        int n4 = attributeSet.getColor(R.styleable.FloatingActionButton_tic_rippleColor, 0);
        this.mSize = attributeSet.getInt(R.styleable.FloatingActionButton_tic_fabSize, 0);
        float f2 = attributeSet.getDimension(R.styleable.FloatingActionButton_tic_pressedTranslationZ, 0.0f);
        int n5 = attributeSet.getResourceId(R.styleable.FloatingActionButton_tic_circularDrawableStyle, 0);
        n2 = attributeSet.getDimensionPixelOffset(R.styleable.FloatingActionButton_tic_minimizeTranslationX, 0);
        n3 = attributeSet.getDimensionPixelOffset(R.styleable.FloatingActionButton_tic_minimizeTranslationY, 0);
        attributeSet.recycle();
        attributeSet = this.createShapeDrawable();
        attributeSet = new RippleDrawable(ColorStateList.valueOf((int)n4), (Drawable)attributeSet, null);
        object = this.createProgressDrawable((Context)object, n5);
        super.setBackgroundDrawable((Drawable)new CircularProgressContainerDrawable(new Drawable[]{attributeSet}, (CircularProgressDrawable)((Object)object)));
        this.mProgressPadding.left = n4 = ((CircularProgressDrawable)((Object)object)).getStrokeSize();
        this.mProgressPadding.right = n4;
        this.mProgressPadding.top = n4;
        this.mProgressPadding.bottom = n4;
        n4 = (int)this.getResources().getDimension(R.dimen.tic_design_fab_image_size);
        this.mImagePadding = (this.getSizeDimension() + this.mProgressPadding.left + this.mProgressPadding.right - n4) / 2;
        this.setPadding(this.mImagePadding, this.mImagePadding, this.mImagePadding, this.mImagePadding);
        this.mAnimator = new FloatingActionButtonAnimator(this);
        this.mAnimator.setPressedTranslationZ(f2);
        this.setMinimizeTranslation(n2, n3);
        this.mShowProgress = false;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void finishDelayConfirmation(boolean bl2) {
        if (this.mDelayedConfirmationListener != null) {
            if (bl2) {
                this.mDelayedConfirmationListener.onButtonClicked(this);
            } else {
                this.mDelayedConfirmationListener.onTimerFinished(this);
            }
        }
        this.stopDelayConfirmation();
        if (this.getProgressDrawable() != null) {
            this.getProgressDrawable().stop();
        }
    }

    private RippleDrawable getRippleDrawable() {
        Drawable drawable2;
        if (this.getBackground() instanceof CircularProgressContainerDrawable && (drawable2 = ((CircularProgressContainerDrawable)this.getBackground()).getContentDrawable(0)) instanceof RippleDrawable) {
            return (RippleDrawable)drawable2;
        }
        return null;
    }

    private void hide(@Nullable OnVisibilityChangedListener onVisibilityChangedListener, boolean bl2) {
        this.mAnimator.hide(this.wrapOnVisibilityChangedListener(onVisibilityChangedListener), bl2);
    }

    private void minimize(@Nullable OnVisibilityChangedListener onVisibilityChangedListener, boolean bl2) {
        this.mAnimator.minimize(this.wrapOnVisibilityChangedListener(onVisibilityChangedListener), bl2);
    }

    private static int resolveAdjustedSize(int n2, int n3) {
        int n4 = View.MeasureSpec.getMode((int)n3);
        n3 = View.MeasureSpec.getSize((int)n3);
        switch (n4) {
            default: {
                return n2;
            }
            case 0: {
                return n2;
            }
            case -2147483648: {
                return Math.min(n2, n3);
            }
            case 0x40000000: 
        }
        return n3;
    }

    private void show(OnVisibilityChangedListener onVisibilityChangedListener, boolean bl2) {
        this.mAnimator.show(this.wrapOnVisibilityChangedListener(onVisibilityChangedListener), bl2);
    }

    @Nullable
    private FloatingActionButtonAnimator.InternalVisibilityChangedListener wrapOnVisibilityChangedListener(final @Nullable OnVisibilityChangedListener onVisibilityChangedListener) {
        if (onVisibilityChangedListener == null) {
            return null;
        }
        return new FloatingActionButtonAnimator.InternalVisibilityChangedListener(){

            @Override
            public void onHidden() {
                onVisibilityChangedListener.onHidden(FloatingActionButton.this);
            }

            @Override
            public void onMinimum() {
                onVisibilityChangedListener.onMinimum(FloatingActionButton.this);
            }

            @Override
            public void onShown() {
                onVisibilityChangedListener.onShown(FloatingActionButton.this);
            }
        };
    }

    CircularProgressDrawable createProgressDrawable(Context context, int n2) {
        return new CircularProgressDrawable.Builder(context, n2).build();
    }

    Drawable createShapeDrawable() {
        ShapeDrawable shapeDrawable = new ShapeDrawable();
        shapeDrawable.setShape((Shape)new OvalShape());
        shapeDrawable.getPaint().setColor(-1);
        return shapeDrawable;
    }

    public boolean getContentRect(@NonNull Rect rect) {
        boolean bl2 = false;
        if (ViewCompat.isLaidOut((View)this)) {
            rect.set(0, 0, this.getWidth(), this.getHeight());
            rect.left += this.mProgressPadding.left;
            rect.top += this.mProgressPadding.top;
            rect.right -= this.mProgressPadding.right;
            rect.bottom -= this.mProgressPadding.bottom;
            bl2 = true;
        }
        return bl2;
    }

    public CircularProgressDrawable getProgressDrawable() {
        if (this.getBackground() instanceof CircularProgressContainerDrawable) {
            return ((CircularProgressContainerDrawable)this.getBackground()).getProgressDrawable();
        }
        return null;
    }

    final int getSizeDimension() {
        switch (this.mSize) {
            default: {
                return this.getResources().getDimensionPixelSize(R.dimen.tic_design_fab_size_normal);
            }
            case 1: 
        }
        return this.getResources().getDimensionPixelSize(R.dimen.tic_design_fab_size_mini);
    }

    public void hide() {
        this.hide(null);
    }

    public void hide(@Nullable OnVisibilityChangedListener onVisibilityChangedListener) {
        this.hide(onVisibilityChangedListener, true);
    }

    public void minimize() {
        this.minimize(null);
    }

    public void minimize(@Nullable OnVisibilityChangedListener onVisibilityChangedListener) {
        this.minimize(onVisibilityChangedListener, true);
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.getVisibility() == 0) {
            this.startProgress();
        }
    }

    protected void onDetachedFromWindow() {
        this.stopDelayConfirmation();
        super.onDetachedFromWindow();
    }

    protected void onMeasure(int n2, int n3) {
        int n4 = this.getSizeDimension();
        n2 = Math.min(FloatingActionButton.resolveAdjustedSize(n4, n2), FloatingActionButton.resolveAdjustedSize(n4, n3));
        this.setMeasuredDimension(this.mProgressPadding.left + n2 + this.mProgressPadding.right, this.mProgressPadding.top + n2 + this.mProgressPadding.bottom);
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() == 0 && this.getContentRect(this.mTouchArea) && !this.mTouchArea.contains((int)motionEvent.getX(), (int)motionEvent.getY())) {
            return false;
        }
        if (this.mGestureDetector != null) {
            this.mGestureDetector.onTouchEvent(motionEvent);
        }
        return super.onTouchEvent(motionEvent);
    }

    protected void onVisibilityChanged(@NonNull View view, int n2) {
        block3: {
            block2: {
                super.onVisibilityChanged(view, n2);
                if (this.getProgressDrawable() == null) break block2;
                if (n2 != 0) break block3;
                this.startProgress();
            }
            return;
        }
        this.getProgressDrawable().stop();
    }

    public void setBackground(Drawable drawable2) {
        Log.e((String)LOG_TAG, (String)"Setting a custom background is not supported.");
    }

    public void setBackgroundColor(int n2) {
        Log.e((String)LOG_TAG, (String)"Setting a custom background is not supported.");
    }

    public void setBackgroundDrawable(Drawable drawable2) {
        Log.e((String)LOG_TAG, (String)"Setting a custom background is not supported.");
    }

    public void setBackgroundResource(int n2) {
        Log.e((String)LOG_TAG, (String)"Setting a custom background is not supported.");
    }

    public void setBackgroundTintList(ColorStateList colorStateList) {
        super.setBackgroundTintList(colorStateList);
    }

    public void setImageDrawable(Drawable drawable2) {
        super.setImageDrawable(drawable2);
    }

    public void setMinimizeTranslation(int n2, int n3) {
        this.mAnimator.setMinimizeTranslation(n2, n3);
    }

    public void setProgressAlpha(int n2) {
        if (this.getProgressDrawable() != null) {
            this.getProgressDrawable().setAlpha(n2);
        }
    }

    public void setProgressMode(int n2) {
        if (this.getProgressDrawable() == null || this.getProgressDrawable().getProgressMode() == n2) {
            return;
        }
        this.stopDelayConfirmation();
        this.getProgressDrawable().setProgressMode(n2);
    }

    public void setProgressPercent(float f2) {
        if (this.getProgressDrawable() == null || this.getProgressDrawable().getProgress() == f2) {
            return;
        }
        this.stopDelayConfirmation();
        this.getProgressDrawable().setProgress(f2);
    }

    public void setRippleColor(@ColorInt int n2) {
        if (this.getRippleDrawable() != null) {
            this.getRippleDrawable().setColor(ColorStateList.valueOf((int)n2));
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setShowProgress(boolean bl2) {
        block5: {
            block4: {
                if (this.mShowProgress == bl2) break block4;
                this.mShowProgress = bl2;
                this.stopDelayConfirmation();
                if (this.getProgressDrawable() != null) break block5;
            }
            return;
        }
        if (bl2) {
            this.getProgressDrawable().start();
            return;
        }
        this.getProgressDrawable().stop();
    }

    public void show() {
        this.show(null);
    }

    public void show(@Nullable OnVisibilityChangedListener onVisibilityChangedListener) {
        this.show(onVisibilityChangedListener, true);
    }

    public void startDelayConfirmation(long l2, DelayedConfirmationListener delayedConfirmationListener) {
        this.stopDelayConfirmation();
        this.setProgressMode(0);
        this.setProgressPercent(0.0f);
        this.setShowProgress(true);
        this.setClickable(true);
        this.mDelayedConfirmationListener = delayedConfirmationListener;
        this.mDelayedConfirmationAnimator = ValueAnimator.ofFloat((float[])new float[]{0.0f, 1.0f}).setDuration(l2);
        this.mDelayedConfirmationAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(){

            /*
             * Enabled aggressive block sorting
             */
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float f2 = ((Float)valueAnimator.getAnimatedValue()).floatValue();
                if (FloatingActionButton.this.getProgressDrawable() != null) {
                    FloatingActionButton.this.getProgressDrawable().setProgress(f2);
                } else {
                    FloatingActionButton.this.stopDelayConfirmation();
                }
                if (f2 >= 1.0f) {
                    FloatingActionButton.this.finishDelayConfirmation(false);
                }
            }
        });
        this.mDelayedConfirmationAnimator.start();
        if (this.mGestureDetector == null) {
            this.mGestureDetector = new GestureDetector(this.getContext(), (GestureDetector.OnGestureListener)new GestureDetector.SimpleOnGestureListener(){

                public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
                    FloatingActionButton.this.finishDelayConfirmation(true);
                    return super.onSingleTapConfirmed(motionEvent);
                }
            });
        }
    }

    public void startProgress() {
        if (this.mShowProgress && this.getProgressDrawable() != null) {
            this.getProgressDrawable().start();
        }
    }

    public void stopDelayConfirmation() {
        if (this.mDelayedConfirmationAnimator != null) {
            this.mDelayedConfirmationAnimator.cancel();
        }
        this.mDelayedConfirmationListener = null;
    }

    public static class Behavior
    extends CoordinatorLayout.Behavior<FloatingActionButton> {
        private Rect mTmpRect;

        /*
         * Enabled aggressive block sorting
         */
        private void offsetIfNeeded(CoordinatorLayout coordinatorLayout, FloatingActionButton floatingActionButton) {
            Rect rect = floatingActionButton.mProgressPadding;
            if (rect.centerX() > 0 && rect.centerY() > 0) {
                CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams)floatingActionButton.getLayoutParams();
                int n2 = 0;
                int n3 = 0;
                if (floatingActionButton.getRight() >= coordinatorLayout.getWidth() - layoutParams.rightMargin) {
                    n3 = rect.right;
                } else if (floatingActionButton.getLeft() <= layoutParams.leftMargin) {
                    n3 = -rect.left;
                }
                if (floatingActionButton.getBottom() >= coordinatorLayout.getBottom() - layoutParams.bottomMargin) {
                    n2 = rect.bottom;
                } else if (floatingActionButton.getTop() <= layoutParams.topMargin) {
                    n2 = -rect.top;
                }
                floatingActionButton.offsetTopAndBottom(n2);
                floatingActionButton.offsetLeftAndRight(n3);
            }
        }

        /*
         * Enabled aggressive block sorting
         */
        private boolean updateFabVisibility(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, FloatingActionButton floatingActionButton) {
            if (((CoordinatorLayout.LayoutParams)floatingActionButton.getLayoutParams()).getAnchorId() != appBarLayout.getId()) {
                return false;
            }
            if (floatingActionButton.getUserSetVisibility() != 0) return false;
            if (this.mTmpRect == null) {
                this.mTmpRect = new Rect();
            }
            Rect rect = this.mTmpRect;
            ViewGroupUtils.getDescendantRect(coordinatorLayout, (View)appBarLayout, rect);
            if (rect.bottom <= appBarLayout.getMinimumHeightForVisibleOverlappingContent()) {
                floatingActionButton.hide(null, false);
                return true;
            }
            floatingActionButton.show(null, false);
            return true;
        }

        @Override
        public boolean onDependentViewChanged(CoordinatorLayout coordinatorLayout, FloatingActionButton floatingActionButton, View view) {
            if (view instanceof AppBarLayout) {
                this.updateFabVisibility(coordinatorLayout, (AppBarLayout)view, floatingActionButton);
            }
            return false;
        }

        @Override
        public boolean onLayoutChild(CoordinatorLayout coordinatorLayout, FloatingActionButton floatingActionButton, int n2) {
            List<View> list = coordinatorLayout.getDependencies((View)floatingActionButton);
            int n3 = 0;
            int n4 = list.size();
            while (true) {
                View view;
                if (n3 >= n4 || (view = list.get(n3)) instanceof AppBarLayout && this.updateFabVisibility(coordinatorLayout, (AppBarLayout)view, floatingActionButton)) {
                    coordinatorLayout.onLayoutChild((View)floatingActionButton, n2);
                    this.offsetIfNeeded(coordinatorLayout, floatingActionButton);
                    return true;
                }
                ++n3;
            }
        }
    }

    public static interface DelayedConfirmationListener {
        public void onButtonClicked(FloatingActionButton var1);

        public void onTimerFinished(FloatingActionButton var1);
    }

    public static abstract class OnVisibilityChangedListener {
        public void onHidden(FloatingActionButton floatingActionButton) {
        }

        public void onMinimum(FloatingActionButton floatingActionButton) {
        }

        public void onShown(FloatingActionButton floatingActionButton) {
        }
    }
}

