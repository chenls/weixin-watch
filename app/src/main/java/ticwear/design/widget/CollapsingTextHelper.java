/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.res.TypedArray
 *  android.graphics.Bitmap
 *  android.graphics.Bitmap$Config
 *  android.graphics.Canvas
 *  android.graphics.Color
 *  android.graphics.Paint
 *  android.graphics.Rect
 *  android.graphics.RectF
 *  android.graphics.Typeface
 *  android.os.Build$VERSION
 *  android.text.TextPaint
 *  android.text.TextUtils
 *  android.text.TextUtils$TruncateAt
 *  android.view.View
 *  android.view.animation.Interpolator
 */
package ticwear.design.widget;

import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v4.text.TextDirectionHeuristicCompat;
import android.support.v4.text.TextDirectionHeuristicsCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Interpolator;
import ticwear.design.R;
import ticwear.design.widget.AnimationUtils;
import ticwear.design.widget.MathUtils;

final class CollapsingTextHelper {
    private static final boolean DEBUG_DRAW = false;
    private static final Paint DEBUG_DRAW_PAINT;
    private static final boolean USE_SCALING_TEXTURE;
    private boolean mBoundsChanged;
    private final Rect mCollapsedBounds;
    private float mCollapsedDrawX;
    private float mCollapsedDrawY;
    private int mCollapsedShadowColor;
    private float mCollapsedShadowDx;
    private float mCollapsedShadowDy;
    private float mCollapsedShadowRadius;
    private int mCollapsedTextColor;
    private int mCollapsedTextGravity = 16;
    private float mCollapsedTextSize = 15.0f;
    private Typeface mCollapsedTypeface;
    private final RectF mCurrentBounds;
    private float mCurrentDrawX;
    private float mCurrentDrawY;
    private float mCurrentTextSize;
    private Typeface mCurrentTypeface;
    private boolean mDrawTitle;
    private final Rect mExpandedBounds;
    private float mExpandedDrawX;
    private float mExpandedDrawY;
    private float mExpandedFraction;
    private int mExpandedShadowColor;
    private float mExpandedShadowDx;
    private float mExpandedShadowDy;
    private float mExpandedShadowRadius;
    private int mExpandedTextColor;
    private int mExpandedTextGravity = 16;
    private float mExpandedTextSize = 15.0f;
    private Bitmap mExpandedTitleTexture;
    private Typeface mExpandedTypeface;
    private boolean mIsRtl;
    private Interpolator mPositionInterpolator;
    private float mScale;
    private CharSequence mText;
    private final TextPaint mTextPaint;
    private Interpolator mTextSizeInterpolator;
    private CharSequence mTextToDraw;
    private float mTextureAscent;
    private float mTextureDescent;
    private Paint mTexturePaint;
    private boolean mUseTexture;
    private final View mView;

    /*
     * Enabled aggressive block sorting
     */
    static {
        boolean bl2 = Build.VERSION.SDK_INT < 18;
        USE_SCALING_TEXTURE = bl2;
        DEBUG_DRAW_PAINT = null;
        if (DEBUG_DRAW_PAINT != null) {
            DEBUG_DRAW_PAINT.setAntiAlias(true);
            DEBUG_DRAW_PAINT.setColor(-65281);
        }
    }

    public CollapsingTextHelper(View view) {
        this.mView = view;
        this.mTextPaint = new TextPaint();
        this.mTextPaint.setAntiAlias(true);
        this.mCollapsedBounds = new Rect();
        this.mExpandedBounds = new Rect();
        this.mCurrentBounds = new RectF();
    }

    private static int blendColors(int n2, int n3, float f2) {
        float f3 = 1.0f - f2;
        float f4 = Color.alpha((int)n2);
        float f5 = Color.alpha((int)n3);
        float f6 = Color.red((int)n2);
        float f7 = Color.red((int)n3);
        float f8 = Color.green((int)n2);
        float f9 = Color.green((int)n3);
        float f10 = Color.blue((int)n2);
        float f11 = Color.blue((int)n3);
        return Color.argb((int)((int)(f4 * f3 + f5 * f2)), (int)((int)(f6 * f3 + f7 * f2)), (int)((int)(f8 * f3 + f9 * f2)), (int)((int)(f10 * f3 + f11 * f2)));
    }

    /*
     * Enabled aggressive block sorting
     */
    private void calculateBaseOffsets() {
        float f2;
        float f3;
        int n2 = 1;
        float f4 = this.mCurrentTextSize;
        this.calculateUsingTextSize(this.mCollapsedTextSize);
        float f5 = this.mTextToDraw != null ? this.mTextPaint.measureText(this.mTextToDraw, 0, this.mTextToDraw.length()) : 0.0f;
        int n3 = this.mCollapsedTextGravity;
        int n4 = this.mIsRtl ? 1 : 0;
        n4 = GravityCompat.getAbsoluteGravity(n3, n4);
        switch (n4 & 0x70) {
            default: {
                f3 = (this.mTextPaint.descent() - this.mTextPaint.ascent()) / 2.0f;
                f2 = this.mTextPaint.descent();
                this.mCollapsedDrawY = (float)this.mCollapsedBounds.centerY() + (f3 - f2);
                break;
            }
            case 80: {
                this.mCollapsedDrawY = this.mCollapsedBounds.bottom;
                break;
            }
            case 48: {
                this.mCollapsedDrawY = (float)this.mCollapsedBounds.top - this.mTextPaint.ascent();
                break;
            }
        }
        switch (n4 & 7) {
            default: {
                this.mCollapsedDrawX = this.mCollapsedBounds.left;
                break;
            }
            case 1: {
                this.mCollapsedDrawX = (float)this.mCollapsedBounds.centerX() - f5 / 2.0f;
                break;
            }
            case 5: {
                this.mCollapsedDrawX = (float)this.mCollapsedBounds.right - f5;
            }
        }
        this.calculateUsingTextSize(this.mExpandedTextSize);
        f5 = this.mTextToDraw != null ? this.mTextPaint.measureText(this.mTextToDraw, 0, this.mTextToDraw.length()) : 0.0f;
        n3 = this.mExpandedTextGravity;
        n4 = this.mIsRtl ? n2 : 0;
        n4 = GravityCompat.getAbsoluteGravity(n3, n4);
        switch (n4 & 0x70) {
            default: {
                f3 = (this.mTextPaint.descent() - this.mTextPaint.ascent()) / 2.0f;
                f2 = this.mTextPaint.descent();
                this.mExpandedDrawY = (float)this.mExpandedBounds.centerY() + (f3 - f2);
                break;
            }
            case 80: {
                this.mExpandedDrawY = this.mExpandedBounds.bottom;
                break;
            }
            case 48: {
                this.mExpandedDrawY = (float)this.mExpandedBounds.top - this.mTextPaint.ascent();
            }
        }
        switch (n4 & 7) {
            default: {
                this.mExpandedDrawX = this.mExpandedBounds.left;
                break;
            }
            case 1: {
                this.mExpandedDrawX = (float)this.mExpandedBounds.centerX() - f5 / 2.0f;
                break;
            }
            case 5: {
                this.mExpandedDrawX = (float)this.mExpandedBounds.right - f5;
            }
        }
        this.clearTexture();
        this.setInterpolatedTextSize(f4);
    }

    private void calculateCurrentOffsets() {
        this.calculateOffsets(this.mExpandedFraction);
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean calculateIsRtl(CharSequence charSequence) {
        TextDirectionHeuristicCompat textDirectionHeuristicCompat;
        boolean bl2 = true;
        if (ViewCompat.getLayoutDirection(this.mView) != 1) {
            bl2 = false;
        }
        if (bl2) {
            textDirectionHeuristicCompat = TextDirectionHeuristicsCompat.FIRSTSTRONG_RTL;
            return textDirectionHeuristicCompat.isRtl(charSequence, 0, charSequence.length());
        }
        textDirectionHeuristicCompat = TextDirectionHeuristicsCompat.FIRSTSTRONG_LTR;
        return textDirectionHeuristicCompat.isRtl(charSequence, 0, charSequence.length());
    }

    /*
     * Enabled aggressive block sorting
     */
    private void calculateOffsets(float f2) {
        this.interpolateBounds(f2);
        this.mCurrentDrawX = CollapsingTextHelper.lerp(this.mExpandedDrawX, this.mCollapsedDrawX, f2, this.mPositionInterpolator);
        this.mCurrentDrawY = CollapsingTextHelper.lerp(this.mExpandedDrawY, this.mCollapsedDrawY, f2, this.mPositionInterpolator);
        this.setInterpolatedTextSize(CollapsingTextHelper.lerp(this.mExpandedTextSize, this.mCollapsedTextSize, f2, this.mTextSizeInterpolator));
        if (this.mCollapsedTextColor != this.mExpandedTextColor) {
            this.mTextPaint.setColor(CollapsingTextHelper.blendColors(this.mExpandedTextColor, this.mCollapsedTextColor, f2));
        } else {
            this.mTextPaint.setColor(this.mCollapsedTextColor);
        }
        this.mTextPaint.setShadowLayer(CollapsingTextHelper.lerp(this.mExpandedShadowRadius, this.mCollapsedShadowRadius, f2, null), CollapsingTextHelper.lerp(this.mExpandedShadowDx, this.mCollapsedShadowDx, f2, null), CollapsingTextHelper.lerp(this.mExpandedShadowDy, this.mCollapsedShadowDy, f2, null), CollapsingTextHelper.blendColors(this.mExpandedShadowColor, this.mCollapsedShadowColor, f2));
        ViewCompat.postInvalidateOnAnimation(this.mView);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void calculateUsingTextSize(float f2) {
        CharSequence charSequence;
        block13: {
            block12: {
                float f3;
                if (this.mText == null) break block12;
                boolean bl2 = false;
                boolean bl3 = false;
                if (CollapsingTextHelper.isClose(f2, this.mCollapsedTextSize)) {
                    float f4 = this.mCollapsedBounds.width();
                    float f5 = this.mCollapsedTextSize;
                    this.mScale = 1.0f;
                    f2 = f4;
                    f3 = f5;
                    if (this.mCurrentTypeface != this.mCollapsedTypeface) {
                        this.mCurrentTypeface = this.mCollapsedTypeface;
                        bl3 = true;
                        f3 = f5;
                        f2 = f4;
                    }
                } else {
                    float f6 = this.mExpandedBounds.width();
                    f3 = this.mExpandedTextSize;
                    bl3 = bl2;
                    if (this.mCurrentTypeface != this.mExpandedTypeface) {
                        this.mCurrentTypeface = this.mExpandedTypeface;
                        bl3 = true;
                    }
                    if (CollapsingTextHelper.isClose(f2, this.mExpandedTextSize)) {
                        this.mScale = 1.0f;
                        f2 = f6;
                    } else {
                        this.mScale = f2 / this.mExpandedTextSize;
                        f2 = f6;
                    }
                }
                bl2 = bl3;
                if (f2 > 0.0f) {
                    bl3 = this.mCurrentTextSize != f3 || this.mBoundsChanged || bl3;
                    this.mCurrentTextSize = f3;
                    this.mBoundsChanged = false;
                    bl2 = bl3;
                }
                if (this.mTextToDraw != null && !bl2) break block12;
                this.mTextPaint.setTextSize(this.mCurrentTextSize);
                this.mTextPaint.setTypeface(this.mCurrentTypeface);
                charSequence = TextUtils.ellipsize((CharSequence)this.mText, (TextPaint)this.mTextPaint, (float)f2, (TextUtils.TruncateAt)TextUtils.TruncateAt.END);
                if (!TextUtils.equals((CharSequence)charSequence, (CharSequence)this.mTextToDraw)) break block13;
            }
            return;
        }
        this.mTextToDraw = charSequence;
        this.mIsRtl = this.calculateIsRtl(this.mTextToDraw);
    }

    private void clearTexture() {
        if (this.mExpandedTitleTexture != null) {
            this.mExpandedTitleTexture.recycle();
            this.mExpandedTitleTexture = null;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void ensureExpandedTexture() {
        block3: {
            block2: {
                if (this.mExpandedTitleTexture != null || this.mExpandedBounds.isEmpty() || TextUtils.isEmpty((CharSequence)this.mTextToDraw)) break block2;
                this.calculateOffsets(0.0f);
                this.mTextureAscent = this.mTextPaint.ascent();
                this.mTextureDescent = this.mTextPaint.descent();
                int n2 = Math.round(this.mTextPaint.measureText(this.mTextToDraw, 0, this.mTextToDraw.length()));
                int n3 = Math.round(this.mTextureDescent - this.mTextureAscent);
                if (n2 <= 0 && n3 <= 0) break block2;
                this.mExpandedTitleTexture = Bitmap.createBitmap((int)n2, (int)n3, (Bitmap.Config)Bitmap.Config.ARGB_8888);
                new Canvas(this.mExpandedTitleTexture).drawText(this.mTextToDraw, 0, this.mTextToDraw.length(), 0.0f, (float)n3 - this.mTextPaint.descent(), (Paint)this.mTextPaint);
                if (this.mTexturePaint == null) break block3;
            }
            return;
        }
        this.mTexturePaint = new Paint(3);
    }

    private void interpolateBounds(float f2) {
        this.mCurrentBounds.left = CollapsingTextHelper.lerp(this.mExpandedBounds.left, this.mCollapsedBounds.left, f2, this.mPositionInterpolator);
        this.mCurrentBounds.top = CollapsingTextHelper.lerp(this.mExpandedDrawY, this.mCollapsedDrawY, f2, this.mPositionInterpolator);
        this.mCurrentBounds.right = CollapsingTextHelper.lerp(this.mExpandedBounds.right, this.mCollapsedBounds.right, f2, this.mPositionInterpolator);
        this.mCurrentBounds.bottom = CollapsingTextHelper.lerp(this.mExpandedBounds.bottom, this.mCollapsedBounds.bottom, f2, this.mPositionInterpolator);
    }

    private static boolean isClose(float f2, float f3) {
        return Math.abs(f2 - f3) < 0.001f;
    }

    private static float lerp(float f2, float f3, float f4, Interpolator interpolator2) {
        float f5 = f4;
        if (interpolator2 != null) {
            f5 = interpolator2.getInterpolation(f4);
        }
        return AnimationUtils.lerp(f2, f3, f5);
    }

    private Typeface readFontFamilyTypeface(int n2) {
        TypedArray typedArray;
        block4: {
            typedArray = this.mView.getContext().obtainStyledAttributes(n2, new int[]{16843692});
            String string2 = typedArray.getString(0);
            if (string2 == null) break block4;
            string2 = Typeface.create((String)string2, (int)0);
            return string2;
        }
        typedArray.recycle();
        return null;
        finally {
            typedArray.recycle();
        }
    }

    private static boolean rectEquals(Rect rect, int n2, int n3, int n4, int n5) {
        return rect.left == n2 && rect.top == n3 && rect.right == n4 && rect.bottom == n5;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void setInterpolatedTextSize(float f2) {
        this.calculateUsingTextSize(f2);
        boolean bl2 = USE_SCALING_TEXTURE && this.mScale != 1.0f;
        this.mUseTexture = bl2;
        if (this.mUseTexture) {
            this.ensureExpandedTexture();
        }
        ViewCompat.postInvalidateOnAnimation(this.mView);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void draw(Canvas canvas) {
        int n2 = canvas.save();
        if (this.mTextToDraw != null && this.mDrawTitle) {
            float f2;
            float f3;
            float f4 = this.mCurrentDrawX;
            float f5 = this.mCurrentDrawY;
            boolean bl2 = this.mUseTexture && this.mExpandedTitleTexture != null;
            this.mTextPaint.setTextSize(this.mCurrentTextSize);
            if (bl2) {
                f3 = this.mTextureAscent * this.mScale;
                f2 = this.mTextureDescent;
                f2 = this.mScale;
            } else {
                f3 = this.mTextPaint.ascent() * this.mScale;
                this.mTextPaint.descent();
                f2 = this.mScale;
            }
            f2 = f5;
            if (bl2) {
                f2 = f5 + f3;
            }
            if (this.mScale != 1.0f) {
                canvas.scale(this.mScale, this.mScale, f4, f2);
            }
            if (bl2) {
                canvas.drawBitmap(this.mExpandedTitleTexture, f4, f2, this.mTexturePaint);
            } else {
                canvas.drawText(this.mTextToDraw, 0, this.mTextToDraw.length(), f4, f2, (Paint)this.mTextPaint);
            }
        }
        canvas.restoreToCount(n2);
    }

    int getCollapsedTextColor() {
        return this.mCollapsedTextColor;
    }

    int getCollapsedTextGravity() {
        return this.mCollapsedTextGravity;
    }

    float getCollapsedTextSize() {
        return this.mCollapsedTextSize;
    }

    Typeface getCollapsedTypeface() {
        if (this.mCollapsedTypeface != null) {
            return this.mCollapsedTypeface;
        }
        return Typeface.DEFAULT;
    }

    int getExpandedTextColor() {
        return this.mExpandedTextColor;
    }

    int getExpandedTextGravity() {
        return this.mExpandedTextGravity;
    }

    float getExpandedTextSize() {
        return this.mExpandedTextSize;
    }

    Typeface getExpandedTypeface() {
        if (this.mExpandedTypeface != null) {
            return this.mExpandedTypeface;
        }
        return Typeface.DEFAULT;
    }

    float getExpansionFraction() {
        return this.mExpandedFraction;
    }

    CharSequence getText() {
        return this.mText;
    }

    /*
     * Enabled aggressive block sorting
     */
    void onBoundsChanged() {
        boolean bl2 = this.mCollapsedBounds.width() > 0 && this.mCollapsedBounds.height() > 0 && this.mExpandedBounds.width() > 0 && this.mExpandedBounds.height() > 0;
        this.mDrawTitle = bl2;
    }

    public void recalculate() {
        if (this.mView.getHeight() > 0 && this.mView.getWidth() > 0) {
            this.calculateBaseOffsets();
            this.calculateCurrentOffsets();
        }
    }

    void setCollapsedBounds(int n2, int n3, int n4, int n5) {
        if (!CollapsingTextHelper.rectEquals(this.mCollapsedBounds, n2, n3, n4, n5)) {
            this.mCollapsedBounds.set(n2, n3, n4, n5);
            this.mBoundsChanged = true;
            this.onBoundsChanged();
        }
    }

    void setCollapsedTextAppearance(int n2) {
        TypedArray typedArray = this.mView.getContext().obtainStyledAttributes(n2, R.styleable.TextAppearance);
        if (typedArray.hasValue(R.styleable.TextAppearance_android_textColor)) {
            this.mCollapsedTextColor = typedArray.getColor(R.styleable.TextAppearance_android_textColor, this.mCollapsedTextColor);
        }
        if (typedArray.hasValue(R.styleable.TextAppearance_android_textSize)) {
            this.mCollapsedTextSize = typedArray.getDimensionPixelSize(R.styleable.TextAppearance_android_textSize, (int)this.mCollapsedTextSize);
        }
        this.mCollapsedShadowColor = typedArray.getInt(R.styleable.TextAppearance_android_shadowColor, 0);
        this.mCollapsedShadowDx = typedArray.getFloat(R.styleable.TextAppearance_android_shadowDx, 0.0f);
        this.mCollapsedShadowDy = typedArray.getFloat(R.styleable.TextAppearance_android_shadowDy, 0.0f);
        this.mCollapsedShadowRadius = typedArray.getFloat(R.styleable.TextAppearance_android_shadowRadius, 0.0f);
        typedArray.recycle();
        if (Build.VERSION.SDK_INT >= 16) {
            this.mCollapsedTypeface = this.readFontFamilyTypeface(n2);
        }
        this.recalculate();
    }

    void setCollapsedTextColor(int n2) {
        if (this.mCollapsedTextColor != n2) {
            this.mCollapsedTextColor = n2;
            this.recalculate();
        }
    }

    void setCollapsedTextGravity(int n2) {
        if (this.mCollapsedTextGravity != n2) {
            this.mCollapsedTextGravity = n2;
            this.recalculate();
        }
    }

    void setCollapsedTextSize(float f2) {
        if (this.mCollapsedTextSize != f2) {
            this.mCollapsedTextSize = f2;
            this.recalculate();
        }
    }

    void setCollapsedTypeface(Typeface typeface) {
        if (this.mCollapsedTypeface != typeface) {
            this.mCollapsedTypeface = typeface;
            this.recalculate();
        }
    }

    void setExpandedBounds(int n2, int n3, int n4, int n5) {
        if (!CollapsingTextHelper.rectEquals(this.mExpandedBounds, n2, n3, n4, n5)) {
            this.mExpandedBounds.set(n2, n3, n4, n5);
            this.mBoundsChanged = true;
            this.onBoundsChanged();
        }
    }

    void setExpandedTextAppearance(int n2) {
        TypedArray typedArray = this.mView.getContext().obtainStyledAttributes(n2, R.styleable.TextAppearance);
        if (typedArray.hasValue(R.styleable.TextAppearance_android_textColor)) {
            this.mExpandedTextColor = typedArray.getColor(R.styleable.TextAppearance_android_textColor, this.mExpandedTextColor);
        }
        if (typedArray.hasValue(R.styleable.TextAppearance_android_textSize)) {
            this.mExpandedTextSize = typedArray.getDimensionPixelSize(R.styleable.TextAppearance_android_textSize, (int)this.mExpandedTextSize);
        }
        this.mExpandedShadowColor = typedArray.getInt(R.styleable.TextAppearance_android_shadowColor, 0);
        this.mExpandedShadowDx = typedArray.getFloat(R.styleable.TextAppearance_android_shadowDx, 0.0f);
        this.mExpandedShadowDy = typedArray.getFloat(R.styleable.TextAppearance_android_shadowDy, 0.0f);
        this.mExpandedShadowRadius = typedArray.getFloat(R.styleable.TextAppearance_android_shadowRadius, 0.0f);
        typedArray.recycle();
        if (Build.VERSION.SDK_INT >= 16) {
            this.mExpandedTypeface = this.readFontFamilyTypeface(n2);
        }
        this.recalculate();
    }

    void setExpandedTextColor(int n2) {
        if (this.mExpandedTextColor != n2) {
            this.mExpandedTextColor = n2;
            this.recalculate();
        }
    }

    void setExpandedTextGravity(int n2) {
        if (this.mExpandedTextGravity != n2) {
            this.mExpandedTextGravity = n2;
            this.recalculate();
        }
    }

    void setExpandedTextSize(float f2) {
        if (this.mExpandedTextSize != f2) {
            this.mExpandedTextSize = f2;
            this.recalculate();
        }
    }

    void setExpandedTypeface(Typeface typeface) {
        if (this.mExpandedTypeface != typeface) {
            this.mExpandedTypeface = typeface;
            this.recalculate();
        }
    }

    void setExpansionFraction(float f2) {
        if ((f2 = MathUtils.constrain(f2, 0.0f, 1.0f)) != this.mExpandedFraction) {
            this.mExpandedFraction = f2;
            this.calculateCurrentOffsets();
        }
    }

    void setPositionInterpolator(Interpolator interpolator2) {
        this.mPositionInterpolator = interpolator2;
        this.recalculate();
    }

    void setText(CharSequence charSequence) {
        if (charSequence == null || !charSequence.equals(this.mText)) {
            this.mText = charSequence;
            this.mTextToDraw = null;
            this.clearTexture();
            this.recalculate();
        }
    }

    void setTextSizeInterpolator(Interpolator interpolator2) {
        this.mTextSizeInterpolator = interpolator2;
        this.recalculate();
    }

    void setTypefaces(Typeface typeface) {
        this.mExpandedTypeface = typeface;
        this.mCollapsedTypeface = typeface;
        this.recalculate();
    }
}

