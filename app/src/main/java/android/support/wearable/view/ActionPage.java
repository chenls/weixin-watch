/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.animation.AnimatorInflater
 *  android.animation.StateListAnimator
 *  android.annotation.TargetApi
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.content.res.TypedArray
 *  android.graphics.Point
 *  android.graphics.drawable.Drawable
 *  android.util.AttributeSet
 *  android.view.View$MeasureSpec
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.view.WindowInsets
 */
package android.support.wearable.view;

import android.animation.AnimatorInflater;
import android.animation.StateListAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.wearable.R;
import android.support.wearable.view.ActionLabel;
import android.support.wearable.view.CircularButton;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;

@TargetApi(value=21)
public class ActionPage
extends ViewGroup {
    private static final float CIRCLE_SIZE_RATIO = 0.45f;
    private static final float CIRCLE_VERT_POSITION_SQUARE = 0.43f;
    private static final boolean DEBUG = false;
    private static final float LABEL_BOTTOM_MARGIN_RATIO_ROUND = 0.09375f;
    private static final float LABEL_WIDTH_RATIO = 0.892f;
    private static final float LABEL_WIDTH_RATIO_ROUND = 0.625f;
    public static int SCALE_MODE_CENTER = 0;
    public static int SCALE_MODE_FIT = 0;
    private static final String TAG = "ActionPage";
    private int mBottomInset;
    private final Point mButtonCenter = new Point();
    private float mButtonRadius;
    private int mButtonSize;
    private CircularButton mCircularButton;
    private boolean mInsetsApplied;
    private boolean mIsRound;
    private final ActionLabel mLabel;
    private int mTextHeight;
    private int mTextWidth;

    static {
        SCALE_MODE_CENTER = 1;
        SCALE_MODE_FIT = 0;
    }

    public ActionPage(Context context) {
        this(context, null);
    }

    public ActionPage(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ActionPage(Context context, AttributeSet attributeSet, int n2) {
        this(context, attributeSet, n2, R.style.Widget_ActionPage);
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    public ActionPage(Context context, AttributeSet attributeSet, int n2, int n3) {
        super(context, attributeSet, n2, n3);
        this.mCircularButton = new CircularButton(context);
        this.mLabel = new ActionLabel(context);
        this.mLabel.setGravity(17);
        this.mLabel.setMaxLines(2);
        float f2 = 1.0f;
        float f3 = 0.0f;
        Object var13_7 = null;
        int n4 = 1;
        int n5 = 0;
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.ActionPage, n2, n3);
        n3 = 0;
        n2 = n4;
        attributeSet = var13_7;
        while (true) {
            void var13_10;
            float f4;
            float f5;
            int n6;
            if (n3 >= typedArray.getIndexCount()) {
                typedArray.recycle();
                this.mLabel.setLineSpacing(f3, f2);
                this.mLabel.setTypefaceFromAttrs((String)attributeSet, n2, n5);
                this.addView(this.mLabel);
                this.addView(this.mCircularButton);
                return;
            }
            int n7 = typedArray.getIndex(n3);
            if (n7 == R.styleable.ActionPage_android_color) {
                this.mCircularButton.setColor(typedArray.getColorStateList(n7));
                n6 = n2;
                n4 = n5;
                f5 = f2;
                f4 = f3;
                AttributeSet attributeSet2 = attributeSet;
            } else if (n7 == R.styleable.ActionPage_android_src) {
                this.mCircularButton.setImageDrawable(typedArray.getDrawable(n7));
                AttributeSet attributeSet3 = attributeSet;
                f4 = f3;
                f5 = f2;
                n4 = n5;
                n6 = n2;
            } else if (n7 == R.styleable.ActionPage_imageScaleMode) {
                this.mCircularButton.setImageScaleMode(typedArray.getInt(n7, 0));
                AttributeSet attributeSet4 = attributeSet;
                f4 = f3;
                f5 = f2;
                n4 = n5;
                n6 = n2;
            } else if (n7 == R.styleable.ActionPage_buttonRippleColor) {
                this.mCircularButton.setRippleColor(typedArray.getColor(n7, -1));
                AttributeSet attributeSet5 = attributeSet;
                f4 = f3;
                f5 = f2;
                n4 = n5;
                n6 = n2;
            } else if (n7 == R.styleable.ActionPage_pressedButtonTranslationZ) {
                this.mCircularButton.setPressedTranslationZ(typedArray.getDimension(n7, 0.0f));
                AttributeSet attributeSet6 = attributeSet;
                f4 = f3;
                f5 = f2;
                n4 = n5;
                n6 = n2;
            } else if (n7 == R.styleable.ActionPage_android_text) {
                this.mLabel.setText(typedArray.getText(n7));
                AttributeSet attributeSet7 = attributeSet;
                f4 = f3;
                f5 = f2;
                n4 = n5;
                n6 = n2;
            } else if (n7 == R.styleable.ActionPage_minTextSize) {
                this.mLabel.setMinTextSize(0, typedArray.getDimension(n7, 10.0f));
                AttributeSet attributeSet8 = attributeSet;
                f4 = f3;
                f5 = f2;
                n4 = n5;
                n6 = n2;
            } else if (n7 == R.styleable.ActionPage_maxTextSize) {
                this.mLabel.setMaxTextSize(0, typedArray.getDimension(n7, 60.0f));
                AttributeSet attributeSet9 = attributeSet;
                f4 = f3;
                f5 = f2;
                n4 = n5;
                n6 = n2;
            } else if (n7 == R.styleable.ActionPage_android_textColor) {
                this.mLabel.setTextColor(typedArray.getColorStateList(n7));
                AttributeSet attributeSet10 = attributeSet;
                f4 = f3;
                f5 = f2;
                n4 = n5;
                n6 = n2;
            } else if (n7 == R.styleable.ActionPage_android_maxLines) {
                this.mLabel.setMaxLines(typedArray.getInt(n7, 2));
                AttributeSet attributeSet11 = attributeSet;
                f4 = f3;
                f5 = f2;
                n4 = n5;
                n6 = n2;
            } else if (n7 == R.styleable.ActionPage_android_fontFamily) {
                String string2 = typedArray.getString(n7);
                f4 = f3;
                f5 = f2;
                n4 = n5;
                n6 = n2;
            } else if (n7 == R.styleable.ActionPage_android_typeface) {
                n6 = typedArray.getInt(n7, n2);
                AttributeSet attributeSet12 = attributeSet;
                f4 = f3;
                f5 = f2;
                n4 = n5;
            } else if (n7 == R.styleable.ActionPage_android_textStyle) {
                n4 = typedArray.getInt(n7, n5);
                AttributeSet attributeSet13 = attributeSet;
                f4 = f3;
                f5 = f2;
                n6 = n2;
            } else if (n7 == R.styleable.ActionPage_android_gravity) {
                this.mLabel.setGravity(typedArray.getInt(n7, 17));
                AttributeSet attributeSet14 = attributeSet;
                f4 = f3;
                f5 = f2;
                n4 = n5;
                n6 = n2;
            } else if (n7 == R.styleable.ActionPage_android_lineSpacingExtra) {
                f4 = typedArray.getDimension(n7, f3);
                AttributeSet attributeSet15 = attributeSet;
                f5 = f2;
                n4 = n5;
                n6 = n2;
            } else if (n7 == R.styleable.ActionPage_android_lineSpacingMultiplier) {
                f5 = typedArray.getDimension(n7, f2);
                AttributeSet attributeSet16 = attributeSet;
                f4 = f3;
                n4 = n5;
                n6 = n2;
            } else {
                AttributeSet attributeSet17 = attributeSet;
                f4 = f3;
                f5 = f2;
                n4 = n5;
                n6 = n2;
                if (n7 == R.styleable.ActionPage_android_stateListAnimator) {
                    this.mCircularButton.setStateListAnimator(AnimatorInflater.loadStateListAnimator((Context)context, (int)typedArray.getResourceId(n7, 0)));
                    AttributeSet attributeSet18 = attributeSet;
                    f4 = f3;
                    f5 = f2;
                    n4 = n5;
                    n6 = n2;
                }
            }
            ++n3;
            attributeSet = var13_10;
            f3 = f4;
            f2 = f5;
            n5 = n4;
            n2 = n6;
        }
    }

    public CircularButton getButton() {
        return this.mCircularButton;
    }

    public ActionLabel getLabel() {
        return this.mLabel;
    }

    public WindowInsets onApplyWindowInsets(WindowInsets windowInsets) {
        int n2;
        this.mInsetsApplied = true;
        if (this.mIsRound != windowInsets.isRound()) {
            this.mIsRound = windowInsets.isRound();
            this.requestLayout();
        }
        if (this.mBottomInset != (n2 = windowInsets.getSystemWindowInsetBottom())) {
            this.mBottomInset = n2;
            this.requestLayout();
        }
        if (this.mIsRound) {
            this.mBottomInset = (int)Math.max((float)this.mBottomInset, 0.09375f * (float)this.getMeasuredHeight());
        }
        return windowInsets;
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (!this.mInsetsApplied) {
            this.requestApplyInsets();
        }
    }

    protected void onLayout(boolean bl2, int n2, int n3, int n4, int n5) {
        this.mCircularButton.layout((int)((float)this.mButtonCenter.x - this.mButtonRadius), (int)((float)this.mButtonCenter.y - this.mButtonRadius), (int)((float)this.mButtonCenter.x + this.mButtonRadius), (int)((float)this.mButtonCenter.y + this.mButtonRadius));
        n2 = (int)((float)(n4 - n2 - this.mTextWidth) / 2.0f);
        this.mLabel.layout(n2, this.mCircularButton.getBottom(), this.mTextWidth + n2, this.mCircularButton.getBottom() + this.mTextHeight);
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void onMeasure(int n2, int n3) {
        super.onMeasure(n2, n3);
        n2 = this.getMeasuredHeight();
        n3 = this.getMeasuredWidth();
        if (this.mCircularButton.getImageScaleMode() == 1 && this.mCircularButton.getImageDrawable() != null) {
            this.mCircularButton.measure(0, 0);
            this.mButtonSize = Math.min(this.mCircularButton.getMeasuredWidth(), this.mCircularButton.getMeasuredHeight());
            this.mButtonRadius = (float)this.mButtonSize / 2.0f;
        } else {
            this.mButtonSize = (int)((float)Math.min(n3, n2) * 0.45f);
            this.mButtonRadius = (float)this.mButtonSize / 2.0f;
            this.mCircularButton.measure(View.MeasureSpec.makeMeasureSpec((int)this.mButtonSize, (int)0x40000000), View.MeasureSpec.makeMeasureSpec((int)this.mButtonSize, (int)0x40000000));
        }
        if (this.mIsRound) {
            this.mButtonCenter.set(n3 / 2, n2 / 2);
            this.mTextWidth = (int)((float)n3 * 0.625f);
            this.mBottomInset = (int)((float)n2 * 0.09375f);
        } else {
            this.mButtonCenter.set(n3 / 2, (int)((float)n2 * 0.43f));
            this.mTextWidth = (int)((float)n3 * 0.892f);
        }
        this.mTextHeight = (int)((float)n2 - ((float)this.mButtonCenter.y + this.mButtonRadius) - (float)this.mBottomInset);
        this.mLabel.measure(View.MeasureSpec.makeMeasureSpec((int)this.mTextWidth, (int)0x40000000), View.MeasureSpec.makeMeasureSpec((int)this.mTextHeight, (int)0x40000000));
    }

    public void setColor(int n2) {
        this.mCircularButton.setColor(n2);
    }

    public void setColor(ColorStateList colorStateList) {
        this.mCircularButton.setColor(colorStateList);
    }

    public void setEnabled(boolean bl2) {
        super.setEnabled(bl2);
        if (this.mCircularButton != null) {
            this.mCircularButton.setEnabled(bl2);
        }
    }

    public void setImageDrawable(Drawable drawable2) {
        this.mCircularButton.setImageDrawable(drawable2);
    }

    public void setImageResource(@DrawableRes int n2) {
        this.mCircularButton.setImageResource(n2);
    }

    public void setImageScaleMode(int n2) {
        this.mCircularButton.setImageScaleMode(n2);
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        if (this.mCircularButton != null) {
            this.mCircularButton.setOnClickListener(onClickListener);
        }
    }

    public void setStateListAnimator(StateListAnimator stateListAnimator) {
        if (this.mCircularButton != null) {
            this.mCircularButton.setStateListAnimator(stateListAnimator);
        }
    }

    public void setText(CharSequence charSequence) {
        this.mLabel.setText(charSequence);
    }
}

