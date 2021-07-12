/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.annotation.SuppressLint
 *  android.annotation.TargetApi
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.graphics.Canvas
 *  android.graphics.Typeface
 *  android.text.Layout
 *  android.text.Layout$Alignment
 *  android.text.StaticLayout
 *  android.text.TextPaint
 *  android.util.AttributeSet
 *  android.util.DisplayMetrics
 *  android.util.TypedValue
 *  android.view.View
 *  android.view.View$MeasureSpec
 */
package android.support.wearable.view;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.support.wearable.R;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import java.util.Objects;

@TargetApi(value=21)
public class ActionLabel
extends View {
    private static final boolean DEBUG = false;
    static final int MAX_TEXT_SIZE = 60;
    static final int MIN_TEXT_SIZE = 10;
    private static final int MONOSPACE = 3;
    private static final int SANS = 1;
    private static final int SERIF = 2;
    private static final String TAG = "ActionLabel";
    private int mCurTextColor;
    private float mCurrentTextSize;
    private int mDrawMaxLines;
    private int mGravity = 0x800033;
    private Layout mLayout;
    private float mLineSpacingAdd;
    private float mLineSpacingMult;
    private int mMaxLines = Integer.MAX_VALUE;
    private float mMaxTextSize;
    private float mMinTextSize;
    private float mSpacingAdd = 0.0f;
    private float mSpacingMult = 1.0f;
    private CharSequence mText;
    private ColorStateList mTextColor;
    private TextPaint mTextPaint;
    private float mTextScaleFactor;

    public ActionLabel(Context context) {
        this(context, null);
    }

    public ActionLabel(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ActionLabel(Context context, AttributeSet attributeSet, int n2) {
        this(context, attributeSet, n2, 0);
    }

    public ActionLabel(Context context, AttributeSet attributeSet, int n2, int n3) {
        super(context, attributeSet, n2, n3);
        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        float f2 = displayMetrics.density;
        float f3 = displayMetrics.scaledDensity;
        this.mTextScaleFactor = f3 / f2;
        this.mMinTextSize = 10.0f * f3;
        this.mMaxTextSize = 60.0f * f3;
        this.mTextPaint = new TextPaint(1);
        context = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.ActionLabel, n2, n3);
        this.mText = context.getText(R.styleable.ActionLabel_android_text);
        this.mMinTextSize = context.getDimension(R.styleable.ActionLabel_minTextSize, this.mMinTextSize);
        this.mMaxTextSize = context.getDimension(R.styleable.ActionLabel_maxTextSize, this.mMaxTextSize);
        this.mTextColor = context.getColorStateList(R.styleable.ActionLabel_android_textColor);
        this.mMaxLines = context.getInt(R.styleable.ActionLabel_android_maxLines, 2);
        if (this.mTextColor != null) {
            this.updateTextColors();
        }
        this.mTextPaint.setTextSize(this.mMaxTextSize);
        this.setTypefaceFromAttrs(context.getString(R.styleable.ActionLabel_android_fontFamily), context.getInt(R.styleable.ActionLabel_android_typeface, -1), context.getInt(R.styleable.ActionLabel_android_textStyle, -1));
        this.mGravity = context.getInt(R.styleable.ActionLabel_android_gravity, this.mGravity);
        this.mLineSpacingAdd = context.getDimensionPixelSize(R.styleable.ActionLabel_android_lineSpacingExtra, (int)this.mLineSpacingAdd);
        this.mLineSpacingMult = context.getFloat(R.styleable.ActionLabel_android_lineSpacingMultiplier, this.mLineSpacingMult);
        context.recycle();
        if (this.mText == null) {
            this.mText = "";
        }
    }

    private int getAvailableHeight() {
        return this.getHeight() - (this.getPaddingTop() + this.getPaddingBottom());
    }

    @SuppressLint(value={"RtlHardcoded"})
    private Layout.Alignment getLayoutAlignment() {
        switch (this.getTextAlignment()) {
            default: {
                return Layout.Alignment.ALIGN_NORMAL;
            }
            case 1: {
                switch (this.mGravity & 0x800007) {
                    default: {
                        return Layout.Alignment.ALIGN_NORMAL;
                    }
                    case 0x800003: {
                        return Layout.Alignment.ALIGN_NORMAL;
                    }
                    case 0x800005: {
                        return Layout.Alignment.ALIGN_OPPOSITE;
                    }
                    case 3: {
                        return Layout.Alignment.ALIGN_NORMAL;
                    }
                    case 5: {
                        return Layout.Alignment.ALIGN_OPPOSITE;
                    }
                    case 1: 
                }
                return Layout.Alignment.ALIGN_CENTER;
            }
            case 2: {
                return Layout.Alignment.ALIGN_NORMAL;
            }
            case 3: {
                return Layout.Alignment.ALIGN_OPPOSITE;
            }
            case 4: 
        }
        return Layout.Alignment.ALIGN_CENTER;
    }

    /*
     * Enabled aggressive block sorting
     */
    private Layout makeNewLayout(int n2, int n3, Layout.Alignment alignment) {
        StaticLayout staticLayout;
        block7: {
            int n4;
            int n5;
            int n6;
            boolean bl2;
            StaticLayout staticLayout2;
            boolean bl3;
            int n7;
            int n8;
            block6: {
                if (n3 <= 0 || n2 <= 0) {
                    return null;
                }
                n8 = n3 - (this.getPaddingTop() + this.getPaddingBottom());
                n7 = n2 - (this.getPaddingLeft() + this.getPaddingRight());
                this.mCurrentTextSize = this.mMaxTextSize;
                this.mTextPaint.setTextSize(this.mMaxTextSize);
                int n9 = 1;
                StaticLayout staticLayout3 = new StaticLayout(this.mText, this.mTextPaint, n7, alignment, this.mSpacingMult, this.mSpacingAdd, true);
                n2 = staticLayout3.getLineCount() > this.mMaxLines ? 1 : 0;
                n3 = staticLayout3.getLineTop(staticLayout3.getLineCount()) > n8 ? 1 : 0;
                bl3 = this.mTextPaint.getTextSize() > this.mMinTextSize;
                staticLayout2 = staticLayout3;
                bl2 = bl3;
                n6 = n2;
                n5 = n3;
                n4 = n9;
                if (n2 != 0) break block6;
                staticLayout = staticLayout3;
                if (n3 == 0) break block7;
                n4 = n9;
                n5 = n3;
                n6 = n2;
                bl2 = bl3;
                staticLayout2 = staticLayout3;
            }
            while (true) {
                if (n6 == 0) {
                    staticLayout = staticLayout2;
                    if (n5 == 0) break;
                }
                staticLayout = staticLayout2;
                if (!bl2) break;
                this.mCurrentTextSize -= 1.0f;
                this.mTextPaint.setTextSize(this.mCurrentTextSize);
                staticLayout2 = new StaticLayout(this.mText, this.mTextPaint, n7, alignment, this.mSpacingMult, this.mSpacingAdd, true);
                n2 = staticLayout2.getLineTop(staticLayout2.getLineCount()) > n8 ? 1 : 0;
                n3 = staticLayout2.getLineCount() > this.mMaxLines ? 1 : 0;
                bl3 = this.mTextPaint.getTextSize() > this.mMinTextSize;
                ++n4;
                bl2 = bl3;
                n6 = n3;
                n5 = n2;
            }
        }
        this.mDrawMaxLines = Math.min(this.mMaxLines, staticLayout.getLineCount());
        return staticLayout;
    }

    private void updateTextColors() {
        int n2 = this.mTextColor.getColorForState(this.getDrawableState(), 0);
        if (n2 != this.mCurTextColor) {
            this.mCurTextColor = n2;
            this.invalidate();
        }
    }

    protected void drawableStateChanged() {
        super.drawableStateChanged();
        if (this.mTextColor != null && this.mTextColor.isStateful()) {
            this.updateTextColors();
        }
    }

    public final int getCurrentTextColor() {
        return this.mCurTextColor;
    }

    public int getGravity() {
        return this.mGravity;
    }

    public float getLineSpacingExtra() {
        return this.mSpacingAdd;
    }

    public float getLineSpacingMultiplier() {
        return this.mSpacingMult;
    }

    public int getMaxLines() {
        return this.mMaxLines;
    }

    public final ColorStateList getTextColors() {
        return this.mTextColor;
    }

    public Typeface getTypeface() {
        return this.mTextPaint.getTypeface();
    }

    int getVerticalOffset() {
        int n2 = this.getAvailableHeight();
        int n3 = this.mLayout.getLineTop(this.mDrawMaxLines);
        switch (this.mGravity & 0x70) {
            default: {
                return 0;
            }
            case 48: {
                return 0;
            }
            case 16: {
                return (n2 - n3) / 2;
            }
            case 80: 
        }
        return n2 - n3;
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.mLayout != null) {
            canvas.save();
            this.mTextPaint.setColor(this.mCurTextColor);
            this.mTextPaint.drawableState = this.getDrawableState();
            canvas.translate((float)this.getPaddingLeft(), (float)(this.getPaddingTop() + this.getVerticalOffset()));
            canvas.clipRect(0, 0, this.getWidth() - this.getPaddingRight(), this.mLayout.getLineTop(this.mDrawMaxLines));
            this.mLayout.draw(canvas);
            canvas.restore();
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void onMeasure(int n2, int n3) {
        int n4 = View.MeasureSpec.getMode((int)n2);
        int n5 = View.MeasureSpec.getMode((int)n3);
        int n6 = View.MeasureSpec.getSize((int)n2);
        int n7 = View.MeasureSpec.getSize((int)n3);
        n2 = -1;
        n3 = -1;
        if (n4 == 0x40000000) {
            n2 = n6;
        }
        if (n5 == 0x40000000) {
            n3 = n7;
        }
        int n8 = n2;
        if (n2 == -1) {
            this.mTextPaint.setTextSize(this.mMaxTextSize);
            n8 = (int)Math.ceil(Layout.getDesiredWidth((CharSequence)this.mText, (TextPaint)this.mTextPaint));
            this.mTextPaint.setTextSize(this.mCurrentTextSize);
        }
        int n9 = n8;
        if (n4 == Integer.MIN_VALUE) {
            n9 = Math.min(n8, n6);
        }
        Layout.Alignment alignment = this.getLayoutAlignment();
        n2 = n3;
        if (n3 == -1) {
            n2 = n5 == Integer.MIN_VALUE ? n7 : Integer.MAX_VALUE;
        }
        if (this.mLayout == null) {
            this.mLayout = this.makeNewLayout(n9, n2, alignment);
        } else {
            n3 = this.mLayout.getWidth() != n9 ? 1 : 0;
            n8 = this.mLayout.getHeight() != n2 ? 1 : 0;
            if (n3 != 0 || n8 != 0) {
                this.mLayout = this.makeNewLayout(n9, n2, alignment);
            }
        }
        if (this.mLayout == null) {
            this.setMeasuredDimension(0, 0);
            return;
        }
        if (n5 != 0x40000000) {
            n2 = this.mLayout.getLineTop(this.mLayout.getLineCount());
        }
        n3 = n2;
        if (n5 == Integer.MIN_VALUE) {
            n3 = Math.min(n2, n7);
        }
        this.setMeasuredDimension(n9, n3);
    }

    public void onRtlPropertiesChanged(int n2) {
        super.onRtlPropertiesChanged(n2);
        this.mLayout = null;
        this.requestLayout();
        this.invalidate();
    }

    public void setGravity(int n2) {
        if (this.mGravity != n2) {
            this.mGravity = n2;
            this.invalidate();
        }
    }

    public void setLineSpacing(float f2, float f3) {
        if (this.mSpacingAdd != f2 || this.mSpacingMult != f3) {
            this.mSpacingAdd = f2;
            this.mSpacingMult = f3;
            if (this.mLayout != null) {
                this.mLayout = null;
                this.requestLayout();
                this.invalidate();
            }
        }
    }

    public void setMaxLines(int n2) {
        if (this.mMaxLines != n2) {
            this.mMaxLines = n2;
            this.mLayout = null;
            this.requestLayout();
            this.invalidate();
        }
    }

    public void setMaxTextSize(float f2) {
        this.setMaxTextSize(2, f2);
    }

    public void setMaxTextSize(int n2, float f2) {
        if ((f2 = TypedValue.applyDimension((int)n2, (float)f2, (DisplayMetrics)this.getContext().getResources().getDisplayMetrics())) != this.mMaxTextSize) {
            this.mLayout = null;
            this.mMaxTextSize = f2;
            this.requestLayout();
            this.invalidate();
        }
    }

    public void setMinTextSize(float f2) {
        this.setMinTextSize(2, f2);
    }

    public void setMinTextSize(int n2, float f2) {
        if ((f2 = TypedValue.applyDimension((int)n2, (float)f2, (DisplayMetrics)this.getContext().getResources().getDisplayMetrics())) != this.mMinTextSize) {
            this.mLayout = null;
            this.mMinTextSize = f2;
            this.requestLayout();
            this.invalidate();
        }
    }

    public void setText(CharSequence charSequence) {
        if (charSequence == null) {
            throw new RuntimeException("Can not set ActionLabel text to null");
        }
        if (!Objects.equals(this.mText, charSequence)) {
            this.mLayout = null;
            this.mText = charSequence;
            this.requestLayout();
            this.invalidate();
        }
    }

    public void setTextColor(int n2) {
        this.mTextColor = ColorStateList.valueOf((int)n2);
        this.updateTextColors();
    }

    public void setTextColor(ColorStateList colorStateList) {
        if (colorStateList == null) {
            throw new NullPointerException();
        }
        this.mTextColor = colorStateList;
        this.updateTextColors();
    }

    public void setTypeface(Typeface typeface) {
        if (this.mTextPaint.getTypeface() != typeface) {
            this.mTextPaint.setTypeface(typeface);
            if (this.mLayout != null) {
                this.requestLayout();
                this.invalidate();
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setTypeface(Typeface typeface, int n2) {
        boolean bl2 = false;
        if (n2 <= 0) {
            this.mTextPaint.setFakeBoldText(false);
            this.mTextPaint.setTextSkewX(0.0f);
            this.setTypeface(typeface);
            return;
        }
        typeface = typeface == null ? Typeface.defaultFromStyle((int)n2) : Typeface.create((Typeface)typeface, (int)n2);
        this.setTypeface(typeface);
        int n3 = typeface != null ? typeface.getStyle() : 0;
        typeface = this.mTextPaint;
        if (((n2 &= ~n3) & 1) != 0) {
            bl2 = true;
        }
        typeface.setFakeBoldText(bl2);
        typeface = this.mTextPaint;
        float f2 = (n2 & 2) != 0 ? -0.25f : 0.0f;
        typeface.setTextSkewX(f2);
    }

    /*
     * Enabled aggressive block sorting
     */
    void setTypefaceFromAttrs(String string2, int n2, int n3) {
        String string3 = null;
        if (string2 != null) {
            string3 = string2 = Typeface.create((String)string2, (int)n3);
            if (string2 != null) {
                this.setTypeface((Typeface)string2);
                return;
            }
        }
        switch (n2) {
            default: {
                string2 = string3;
                break;
            }
            case 1: {
                string2 = Typeface.SANS_SERIF;
                break;
            }
            case 2: {
                string2 = Typeface.SERIF;
                break;
            }
            case 3: {
                string2 = Typeface.MONOSPACE;
            }
        }
        this.setTypeface((Typeface)string2, n3);
    }
}

