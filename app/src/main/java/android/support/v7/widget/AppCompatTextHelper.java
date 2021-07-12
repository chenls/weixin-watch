/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.TypedArray
 *  android.graphics.drawable.Drawable
 *  android.os.Build$VERSION
 *  android.text.method.PasswordTransformationMethod
 *  android.util.AttributeSet
 *  android.widget.TextView
 */
package android.support.v7.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.appcompat.R;
import android.support.v7.text.AllCapsTransformationMethod;
import android.support.v7.widget.AppCompatDrawableManager;
import android.support.v7.widget.AppCompatTextHelperV17;
import android.support.v7.widget.TintInfo;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.widget.TextView;

class AppCompatTextHelper {
    private static final int[] TEXT_APPEARANCE_ATTRS;
    private static final int[] VIEW_ATTRS;
    private TintInfo mDrawableBottomTint;
    private TintInfo mDrawableLeftTint;
    private TintInfo mDrawableRightTint;
    private TintInfo mDrawableTopTint;
    final TextView mView;

    static {
        VIEW_ATTRS = new int[]{16842804, 16843119, 16843117, 0x1010170, 16843118};
        TEXT_APPEARANCE_ATTRS = new int[]{R.attr.textAllCaps};
    }

    AppCompatTextHelper(TextView textView) {
        this.mView = textView;
    }

    static AppCompatTextHelper create(TextView textView) {
        if (Build.VERSION.SDK_INT >= 17) {
            return new AppCompatTextHelperV17(textView);
        }
        return new AppCompatTextHelper(textView);
    }

    protected static TintInfo createTintInfo(Context context, AppCompatDrawableManager object, int n2) {
        if ((context = ((AppCompatDrawableManager)object).getTintList(context, n2)) != null) {
            object = new TintInfo();
            ((TintInfo)object).mHasTintList = true;
            ((TintInfo)object).mTintList = context;
            return object;
        }
        return null;
    }

    final void applyCompoundDrawableTint(Drawable drawable2, TintInfo tintInfo) {
        if (drawable2 != null && tintInfo != null) {
            AppCompatDrawableManager.tintDrawable(drawable2, tintInfo, this.mView.getDrawableState());
        }
    }

    void applyCompoundDrawablesTints() {
        if (this.mDrawableLeftTint != null || this.mDrawableTopTint != null || this.mDrawableRightTint != null || this.mDrawableBottomTint != null) {
            Drawable[] drawableArray = this.mView.getCompoundDrawables();
            this.applyCompoundDrawableTint(drawableArray[0], this.mDrawableLeftTint);
            this.applyCompoundDrawableTint(drawableArray[1], this.mDrawableTopTint);
            this.applyCompoundDrawableTint(drawableArray[2], this.mDrawableRightTint);
            this.applyCompoundDrawableTint(drawableArray[3], this.mDrawableBottomTint);
        }
    }

    void loadFromAttributes(AttributeSet attributeSet, int n2) {
        Context context = this.mView.getContext();
        AppCompatDrawableManager appCompatDrawableManager = AppCompatDrawableManager.get();
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, VIEW_ATTRS, n2, 0);
        int n3 = typedArray.getResourceId(0, -1);
        if (typedArray.hasValue(1)) {
            this.mDrawableLeftTint = AppCompatTextHelper.createTintInfo(context, appCompatDrawableManager, typedArray.getResourceId(1, 0));
        }
        if (typedArray.hasValue(2)) {
            this.mDrawableTopTint = AppCompatTextHelper.createTintInfo(context, appCompatDrawableManager, typedArray.getResourceId(2, 0));
        }
        if (typedArray.hasValue(3)) {
            this.mDrawableRightTint = AppCompatTextHelper.createTintInfo(context, appCompatDrawableManager, typedArray.getResourceId(3, 0));
        }
        if (typedArray.hasValue(4)) {
            this.mDrawableBottomTint = AppCompatTextHelper.createTintInfo(context, appCompatDrawableManager, typedArray.getResourceId(4, 0));
        }
        typedArray.recycle();
        if (!(this.mView.getTransformationMethod() instanceof PasswordTransformationMethod)) {
            boolean bl2 = false;
            boolean bl3 = false;
            boolean bl4 = false;
            boolean bl5 = false;
            if (n3 != -1) {
                appCompatDrawableManager = context.obtainStyledAttributes(n3, R.styleable.TextAppearance);
                bl2 = bl3;
                bl4 = bl5;
                if (appCompatDrawableManager.hasValue(R.styleable.TextAppearance_textAllCaps)) {
                    bl4 = true;
                    bl2 = appCompatDrawableManager.getBoolean(R.styleable.TextAppearance_textAllCaps, false);
                }
                appCompatDrawableManager.recycle();
            }
            if ((attributeSet = context.obtainStyledAttributes(attributeSet, TEXT_APPEARANCE_ATTRS, n2, 0)).hasValue(0)) {
                bl4 = true;
                bl2 = attributeSet.getBoolean(0, false);
            }
            attributeSet.recycle();
            if (bl4) {
                this.setAllCaps(bl2);
            }
        }
    }

    void onSetTextAppearance(Context context, int n2) {
        if ((context = context.obtainStyledAttributes(n2, TEXT_APPEARANCE_ATTRS)).hasValue(0)) {
            this.setAllCaps(context.getBoolean(0, false));
        }
        context.recycle();
    }

    /*
     * Enabled aggressive block sorting
     */
    void setAllCaps(boolean bl2) {
        TextView textView = this.mView;
        AllCapsTransformationMethod allCapsTransformationMethod = bl2 ? new AllCapsTransformationMethod(this.mView.getContext()) : null;
        textView.setTransformationMethod(allCapsTransformationMethod);
    }
}

