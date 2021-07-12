/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.Rect
 *  android.graphics.drawable.Drawable
 *  android.os.Build$VERSION
 *  android.util.AttributeSet
 *  android.util.Log
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.widget.ImageView
 */
package android.support.design.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.R;
import android.support.design.widget.AnimationUtils;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButtonEclairMr1;
import android.support.design.widget.FloatingActionButtonIcs;
import android.support.design.widget.FloatingActionButtonImpl;
import android.support.design.widget.FloatingActionButtonLollipop;
import android.support.design.widget.ShadowViewDelegate;
import android.support.design.widget.Snackbar;
import android.support.design.widget.ThemeUtils;
import android.support.design.widget.ValueAnimatorCompat;
import android.support.design.widget.ViewGroupUtils;
import android.support.design.widget.ViewUtils;
import android.support.design.widget.VisibilityAwareImageButton;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.AppCompatDrawableManager;
import android.support.v7.widget.AppCompatImageHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import java.util.List;

@CoordinatorLayout.DefaultBehavior(value=Behavior.class)
public class FloatingActionButton
extends VisibilityAwareImageButton {
    private static final String LOG_TAG = "FloatingActionButton";
    private static final int SIZE_MINI = 1;
    private static final int SIZE_NORMAL = 0;
    private ColorStateList mBackgroundTint;
    private PorterDuff.Mode mBackgroundTintMode;
    private int mBorderWidth;
    private boolean mCompatPadding;
    private AppCompatImageHelper mImageHelper;
    private int mImagePadding;
    private FloatingActionButtonImpl mImpl;
    private int mRippleColor;
    private final Rect mShadowPadding = new Rect();
    private int mSize;

    public FloatingActionButton(Context context) {
        this(context, null);
    }

    public FloatingActionButton(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public FloatingActionButton(Context context, AttributeSet attributeSet, int n2) {
        super(context, attributeSet, n2);
        ThemeUtils.checkAppCompatTheme(context);
        context = context.obtainStyledAttributes(attributeSet, R.styleable.FloatingActionButton, n2, R.style.Widget_Design_FloatingActionButton);
        this.mBackgroundTint = context.getColorStateList(R.styleable.FloatingActionButton_backgroundTint);
        this.mBackgroundTintMode = FloatingActionButton.parseTintMode(context.getInt(R.styleable.FloatingActionButton_backgroundTintMode, -1), null);
        this.mRippleColor = context.getColor(R.styleable.FloatingActionButton_rippleColor, 0);
        this.mSize = context.getInt(R.styleable.FloatingActionButton_fabSize, 0);
        this.mBorderWidth = context.getDimensionPixelSize(R.styleable.FloatingActionButton_borderWidth, 0);
        float f2 = context.getDimension(R.styleable.FloatingActionButton_elevation, 0.0f);
        float f3 = context.getDimension(R.styleable.FloatingActionButton_pressedTranslationZ, 0.0f);
        this.mCompatPadding = context.getBoolean(R.styleable.FloatingActionButton_useCompatPadding, false);
        context.recycle();
        this.mImageHelper = new AppCompatImageHelper((ImageView)this, AppCompatDrawableManager.get());
        this.mImageHelper.loadFromAttributes(attributeSet, n2);
        n2 = (int)this.getResources().getDimension(R.dimen.design_fab_image_size);
        this.mImagePadding = (this.getSizeDimension() - n2) / 2;
        this.getImpl().setBackgroundDrawable(this.mBackgroundTint, this.mBackgroundTintMode, this.mRippleColor, this.mBorderWidth);
        this.getImpl().setElevation(f2);
        this.getImpl().setPressedTranslationZ(f3);
        this.getImpl().updatePadding();
    }

    private FloatingActionButtonImpl createImpl() {
        int n2 = Build.VERSION.SDK_INT;
        if (n2 >= 21) {
            return new FloatingActionButtonLollipop(this, new ShadowDelegateImpl());
        }
        if (n2 >= 14) {
            return new FloatingActionButtonIcs(this, new ShadowDelegateImpl());
        }
        return new FloatingActionButtonEclairMr1(this, new ShadowDelegateImpl());
    }

    private FloatingActionButtonImpl getImpl() {
        if (this.mImpl == null) {
            this.mImpl = this.createImpl();
        }
        return this.mImpl;
    }

    private void hide(@Nullable OnVisibilityChangedListener onVisibilityChangedListener, boolean bl2) {
        this.getImpl().hide(this.wrapOnVisibilityChangedListener(onVisibilityChangedListener), bl2);
    }

    static PorterDuff.Mode parseTintMode(int n2, PorterDuff.Mode mode) {
        switch (n2) {
            default: {
                return mode;
            }
            case 3: {
                return PorterDuff.Mode.SRC_OVER;
            }
            case 5: {
                return PorterDuff.Mode.SRC_IN;
            }
            case 9: {
                return PorterDuff.Mode.SRC_ATOP;
            }
            case 14: {
                return PorterDuff.Mode.MULTIPLY;
            }
            case 15: 
        }
        return PorterDuff.Mode.SCREEN;
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
        this.getImpl().show(this.wrapOnVisibilityChangedListener(onVisibilityChangedListener), bl2);
    }

    @Nullable
    private FloatingActionButtonImpl.InternalVisibilityChangedListener wrapOnVisibilityChangedListener(final @Nullable OnVisibilityChangedListener onVisibilityChangedListener) {
        if (onVisibilityChangedListener == null) {
            return null;
        }
        return new FloatingActionButtonImpl.InternalVisibilityChangedListener(){

            @Override
            public void onHidden() {
                onVisibilityChangedListener.onHidden(FloatingActionButton.this);
            }

            @Override
            public void onShown() {
                onVisibilityChangedListener.onShown(FloatingActionButton.this);
            }
        };
    }

    protected void drawableStateChanged() {
        super.drawableStateChanged();
        this.getImpl().onDrawableStateChanged(this.getDrawableState());
    }

    @Nullable
    public ColorStateList getBackgroundTintList() {
        return this.mBackgroundTint;
    }

    @Nullable
    public PorterDuff.Mode getBackgroundTintMode() {
        return this.mBackgroundTintMode;
    }

    public float getCompatElevation() {
        return this.getImpl().getElevation();
    }

    @NonNull
    public Drawable getContentBackground() {
        return this.getImpl().getContentBackground();
    }

    public boolean getContentRect(@NonNull Rect rect) {
        boolean bl2 = false;
        if (ViewCompat.isLaidOut((View)this)) {
            rect.set(0, 0, this.getWidth(), this.getHeight());
            rect.left += this.mShadowPadding.left;
            rect.top += this.mShadowPadding.top;
            rect.right -= this.mShadowPadding.right;
            rect.bottom -= this.mShadowPadding.bottom;
            bl2 = true;
        }
        return bl2;
    }

    final int getSizeDimension() {
        switch (this.mSize) {
            default: {
                return this.getResources().getDimensionPixelSize(R.dimen.design_fab_size_normal);
            }
            case 1: 
        }
        return this.getResources().getDimensionPixelSize(R.dimen.design_fab_size_mini);
    }

    public boolean getUseCompatPadding() {
        return this.mCompatPadding;
    }

    public void hide() {
        this.hide(null);
    }

    public void hide(@Nullable OnVisibilityChangedListener onVisibilityChangedListener) {
        this.hide(onVisibilityChangedListener, true);
    }

    @TargetApi(value=11)
    public void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        this.getImpl().jumpDrawableToCurrentState();
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.getImpl().onAttachedToWindow();
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.getImpl().onDetachedFromWindow();
    }

    protected void onMeasure(int n2, int n3) {
        int n4 = this.getSizeDimension();
        n2 = Math.min(FloatingActionButton.resolveAdjustedSize(n4, n2), FloatingActionButton.resolveAdjustedSize(n4, n3));
        this.setMeasuredDimension(this.mShadowPadding.left + n2 + this.mShadowPadding.right, this.mShadowPadding.top + n2 + this.mShadowPadding.bottom);
    }

    public void setBackgroundColor(int n2) {
        Log.i((String)LOG_TAG, (String)"Setting a custom background is not supported.");
    }

    public void setBackgroundDrawable(Drawable drawable2) {
        Log.i((String)LOG_TAG, (String)"Setting a custom background is not supported.");
    }

    public void setBackgroundResource(int n2) {
        Log.i((String)LOG_TAG, (String)"Setting a custom background is not supported.");
    }

    public void setBackgroundTintList(@Nullable ColorStateList colorStateList) {
        if (this.mBackgroundTint != colorStateList) {
            this.mBackgroundTint = colorStateList;
            this.getImpl().setBackgroundTintList(colorStateList);
        }
    }

    public void setBackgroundTintMode(@Nullable PorterDuff.Mode mode) {
        if (this.mBackgroundTintMode != mode) {
            this.mBackgroundTintMode = mode;
            this.getImpl().setBackgroundTintMode(mode);
        }
    }

    public void setCompatElevation(float f2) {
        this.getImpl().setElevation(f2);
    }

    public void setImageResource(@DrawableRes int n2) {
        this.mImageHelper.setImageResource(n2);
    }

    public void setRippleColor(@ColorInt int n2) {
        if (this.mRippleColor != n2) {
            this.mRippleColor = n2;
            this.getImpl().setRippleColor(n2);
        }
    }

    public void setUseCompatPadding(boolean bl2) {
        if (this.mCompatPadding != bl2) {
            this.mCompatPadding = bl2;
            this.getImpl().onCompatShadowChanged();
        }
    }

    public void show() {
        this.show(null);
    }

    public void show(@Nullable OnVisibilityChangedListener onVisibilityChangedListener) {
        this.show(onVisibilityChangedListener, true);
    }

    public static class Behavior
    extends CoordinatorLayout.Behavior<FloatingActionButton> {
        private static final boolean SNACKBAR_BEHAVIOR_ENABLED;
        private float mFabTranslationY;
        private ValueAnimatorCompat mFabTranslationYAnimator;
        private Rect mTmpRect;

        /*
         * Enabled aggressive block sorting
         */
        static {
            boolean bl2 = Build.VERSION.SDK_INT >= 11;
            SNACKBAR_BEHAVIOR_ENABLED = bl2;
        }

        private float getFabTranslationYForSnackbar(CoordinatorLayout coordinatorLayout, FloatingActionButton floatingActionButton) {
            float f2 = 0.0f;
            List<View> list = coordinatorLayout.getDependencies((View)floatingActionButton);
            int n2 = list.size();
            for (int i2 = 0; i2 < n2; ++i2) {
                View view = list.get(i2);
                float f3 = f2;
                if (view instanceof Snackbar.SnackbarLayout) {
                    f3 = f2;
                    if (coordinatorLayout.doViewsOverlap((View)floatingActionButton, view)) {
                        f3 = Math.min(f2, ViewCompat.getTranslationY(view) - (float)view.getHeight());
                    }
                }
                f2 = f3;
            }
            return f2;
        }

        /*
         * Enabled aggressive block sorting
         */
        private void offsetIfNeeded(CoordinatorLayout coordinatorLayout, FloatingActionButton floatingActionButton) {
            Rect rect = floatingActionButton.mShadowPadding;
            if (rect != null && rect.centerX() > 0 && rect.centerY() > 0) {
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
        private void updateFabTranslationForSnackbar(CoordinatorLayout coordinatorLayout, final FloatingActionButton floatingActionButton, View view) {
            float f2 = this.getFabTranslationYForSnackbar(coordinatorLayout, floatingActionButton);
            if (this.mFabTranslationY == f2) {
                return;
            }
            float f3 = ViewCompat.getTranslationY((View)floatingActionButton);
            if (this.mFabTranslationYAnimator != null && this.mFabTranslationYAnimator.isRunning()) {
                this.mFabTranslationYAnimator.cancel();
            }
            if (floatingActionButton.isShown() && Math.abs(f3 - f2) > (float)floatingActionButton.getHeight() * 0.667f) {
                if (this.mFabTranslationYAnimator == null) {
                    this.mFabTranslationYAnimator = ViewUtils.createAnimator();
                    this.mFabTranslationYAnimator.setInterpolator(AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR);
                    this.mFabTranslationYAnimator.setUpdateListener(new ValueAnimatorCompat.AnimatorUpdateListener(){

                        @Override
                        public void onAnimationUpdate(ValueAnimatorCompat valueAnimatorCompat) {
                            ViewCompat.setTranslationY((View)floatingActionButton, valueAnimatorCompat.getAnimatedFloatValue());
                        }
                    });
                }
                this.mFabTranslationYAnimator.setFloatValues(f3, f2);
                this.mFabTranslationYAnimator.start();
            } else {
                ViewCompat.setTranslationY((View)floatingActionButton, f2);
            }
            this.mFabTranslationY = f2;
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
        public boolean layoutDependsOn(CoordinatorLayout coordinatorLayout, FloatingActionButton floatingActionButton, View view) {
            return SNACKBAR_BEHAVIOR_ENABLED && view instanceof Snackbar.SnackbarLayout;
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public boolean onDependentViewChanged(CoordinatorLayout coordinatorLayout, FloatingActionButton floatingActionButton, View view) {
            if (view instanceof Snackbar.SnackbarLayout) {
                this.updateFabTranslationForSnackbar(coordinatorLayout, floatingActionButton, view);
                return false;
            }
            if (!(view instanceof AppBarLayout)) return false;
            this.updateFabVisibility(coordinatorLayout, (AppBarLayout)view, floatingActionButton);
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

    public static abstract class OnVisibilityChangedListener {
        public void onHidden(FloatingActionButton floatingActionButton) {
        }

        public void onShown(FloatingActionButton floatingActionButton) {
        }
    }

    private class ShadowDelegateImpl
    implements ShadowViewDelegate {
        private ShadowDelegateImpl() {
        }

        @Override
        public float getRadius() {
            return (float)FloatingActionButton.this.getSizeDimension() / 2.0f;
        }

        @Override
        public boolean isCompatPaddingEnabled() {
            return FloatingActionButton.this.mCompatPadding;
        }

        @Override
        public void setBackgroundDrawable(Drawable drawable2) {
            FloatingActionButton.super.setBackgroundDrawable(drawable2);
        }

        @Override
        public void setShadowPadding(int n2, int n3, int n4, int n5) {
            FloatingActionButton.this.mShadowPadding.set(n2, n3, n4, n5);
            FloatingActionButton.this.setPadding(FloatingActionButton.this.mImagePadding + n2, FloatingActionButton.this.mImagePadding + n3, FloatingActionButton.this.mImagePadding + n4, FloatingActionButton.this.mImagePadding + n5);
        }
    }
}

