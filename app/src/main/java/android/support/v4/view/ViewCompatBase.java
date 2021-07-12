/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.res.ColorStateList
 *  android.graphics.PorterDuff$Mode
 *  android.view.View
 *  android.view.ViewParent
 */
package android.support.v4.view;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.support.v4.view.TintableBackgroundView;
import android.view.View;
import android.view.ViewParent;
import java.lang.reflect.Field;

class ViewCompatBase {
    private static final String TAG = "ViewCompatBase";
    private static Field sMinHeightField;
    private static boolean sMinHeightFieldFetched;
    private static Field sMinWidthField;
    private static boolean sMinWidthFieldFetched;

    ViewCompatBase() {
    }

    static ColorStateList getBackgroundTintList(View view) {
        if (view instanceof TintableBackgroundView) {
            return ((TintableBackgroundView)view).getSupportBackgroundTintList();
        }
        return null;
    }

    static PorterDuff.Mode getBackgroundTintMode(View view) {
        if (view instanceof TintableBackgroundView) {
            return ((TintableBackgroundView)view).getSupportBackgroundTintMode();
        }
        return null;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static int getMinimumHeight(View view) {
        if (!sMinHeightFieldFetched) {
            try {
                sMinHeightField = View.class.getDeclaredField("mMinHeight");
                sMinHeightField.setAccessible(true);
            }
            catch (NoSuchFieldException noSuchFieldException) {}
            sMinHeightFieldFetched = true;
        }
        if (sMinHeightField == null) return 0;
        try {
            return (Integer)sMinHeightField.get(view);
        }
        catch (Exception exception) {
            // empty catch block
        }
        return 0;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static int getMinimumWidth(View view) {
        if (!sMinWidthFieldFetched) {
            try {
                sMinWidthField = View.class.getDeclaredField("mMinWidth");
                sMinWidthField.setAccessible(true);
            }
            catch (NoSuchFieldException noSuchFieldException) {}
            sMinWidthFieldFetched = true;
        }
        if (sMinWidthField == null) return 0;
        try {
            return (Integer)sMinWidthField.get(view);
        }
        catch (Exception exception) {
            // empty catch block
        }
        return 0;
    }

    static boolean isAttachedToWindow(View view) {
        return view.getWindowToken() != null;
    }

    static boolean isLaidOut(View view) {
        return view.getWidth() > 0 && view.getHeight() > 0;
    }

    static void offsetLeftAndRight(View view, int n2) {
        block3: {
            block2: {
                int n3 = view.getLeft();
                view.offsetLeftAndRight(n2);
                if (n2 == 0) break block2;
                ViewParent viewParent = view.getParent();
                if (!(viewParent instanceof View)) break block3;
                n2 = Math.abs(n2);
                ((View)viewParent).invalidate(n3 - n2, view.getTop(), view.getWidth() + n3 + n2, view.getBottom());
            }
            return;
        }
        view.invalidate();
    }

    static void offsetTopAndBottom(View view, int n2) {
        block3: {
            block2: {
                int n3 = view.getTop();
                view.offsetTopAndBottom(n2);
                if (n2 == 0) break block2;
                ViewParent viewParent = view.getParent();
                if (!(viewParent instanceof View)) break block3;
                n2 = Math.abs(n2);
                ((View)viewParent).invalidate(view.getLeft(), n3 - n2, view.getRight(), view.getHeight() + n3 + n2);
            }
            return;
        }
        view.invalidate();
    }

    static void setBackgroundTintList(View view, ColorStateList colorStateList) {
        if (view instanceof TintableBackgroundView) {
            ((TintableBackgroundView)view).setSupportBackgroundTintList(colorStateList);
        }
    }

    static void setBackgroundTintMode(View view, PorterDuff.Mode mode) {
        if (view instanceof TintableBackgroundView) {
            ((TintableBackgroundView)view).setSupportBackgroundTintMode(mode);
        }
    }
}

