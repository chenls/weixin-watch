/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources$Theme
 *  android.content.res.TypedArray
 *  android.os.Build$VERSION
 *  android.util.DisplayMetrics
 *  android.util.TypedValue
 */
package ticwear.design.utils;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import ticwear.design.R;

public class ThemeUtils {
    private static final int[] DESIGN_CHECK_ATTRS = new int[]{R.attr.tic_windowIconStyle};
    private static TypedValue value;

    public static void checkDesignTheme(Context context) {
        boolean bl2 = false;
        if (!(context = context.obtainStyledAttributes(DESIGN_CHECK_ATTRS)).hasValue(0)) {
            bl2 = true;
        }
        context.recycle();
        if (bl2) {
            throw new IllegalArgumentException("You need to use a Theme.Ticwear theme (or descendant) with the design library.");
        }
    }

    public static int colorAccent(Context context, int n2) {
        return ThemeUtils.getColor(context, 16843829, n2);
    }

    public static int colorButtonNormal(Context context, int n2) {
        return ThemeUtils.getColor(context, 16843819, n2);
    }

    public static int colorControlActivated(Context context, int n2) {
        return ThemeUtils.getColor(context, 16843818, n2);
    }

    public static int colorControlHighlight(Context context, int n2) {
        return ThemeUtils.getColor(context, 16843820, n2);
    }

    public static int colorControlNormal(Context context, int n2) {
        return ThemeUtils.getColor(context, 16843817, n2);
    }

    public static int colorPrimary(Context context, int n2) {
        return ThemeUtils.getColor(context, 16843827, n2);
    }

    public static int colorPrimaryDark(Context context, int n2) {
        return ThemeUtils.getColor(context, 16843828, n2);
    }

    public static int dpToPx(Context context, int n2) {
        return (int)(TypedValue.applyDimension((int)1, (float)n2, (DisplayMetrics)context.getResources().getDisplayMetrics()) + 0.5f);
    }

    private static int getColor(Context context, int n2, int n3) {
        block7: {
            if (value == null) {
                value = new TypedValue();
            }
            Resources.Theme theme = context.getTheme();
            if (theme == null) break block7;
            try {
                if (theme.resolveAttribute(n2, value, true)) {
                    if (ThemeUtils.value.type >= 16 && ThemeUtils.value.type <= 31) {
                        return ThemeUtils.value.data;
                    }
                    if (ThemeUtils.value.type == 3) {
                        n2 = context.getResources().getColor(ThemeUtils.value.resourceId);
                        return n2;
                    }
                }
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        return n3;
    }

    public static CharSequence getString(TypedArray object, int n2, CharSequence charSequence) {
        if ((object = object.getString(n2)) == null) {
            return charSequence;
        }
        return object;
    }

    public static int getType(TypedArray typedArray, int n2) {
        if (Build.VERSION.SDK_INT >= 21) {
            return typedArray.getType(n2);
        }
        if ((typedArray = typedArray.peekValue(n2)) == null) {
            return 0;
        }
        return typedArray.type;
    }

    public static int spToPx(Context context, int n2) {
        return (int)(TypedValue.applyDimension((int)2, (float)n2, (DisplayMetrics)context.getResources().getDisplayMetrics()) + 0.5f);
    }

    public static int textColorPrimary(Context context, int n2) {
        return ThemeUtils.getColor(context, 16842806, n2);
    }

    public static int textColorSecondary(Context context, int n2) {
        return ThemeUtils.getColor(context, 16842808, n2);
    }

    public static int windowBackground(Context context, int n2) {
        return ThemeUtils.getColor(context, 16842836, n2);
    }
}

