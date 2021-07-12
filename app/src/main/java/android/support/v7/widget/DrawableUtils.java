/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.Rect
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.DrawableContainer
 *  android.graphics.drawable.DrawableContainer$DrawableContainerState
 *  android.graphics.drawable.GradientDrawable
 *  android.graphics.drawable.InsetDrawable
 *  android.graphics.drawable.LayerDrawable
 *  android.graphics.drawable.ScaleDrawable
 *  android.graphics.drawable.StateListDrawable
 *  android.os.Build$VERSION
 */
package android.support.v7.widget;

import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ScaleDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.graphics.drawable.DrawableWrapper;
import android.support.v7.widget.ThemeUtils;

public class DrawableUtils {
    public static final Rect INSETS_NONE;
    private static final String TAG = "DrawableUtils";
    private static final String VECTOR_DRAWABLE_CLAZZ_NAME = "android.graphics.drawable.VectorDrawable";
    private static Class<?> sInsetsClazz;

    static {
        block2: {
            INSETS_NONE = new Rect();
            if (Build.VERSION.SDK_INT < 18) break block2;
            try {
                sInsetsClazz = Class.forName("android.graphics.Insets");
            }
            catch (ClassNotFoundException classNotFoundException) {}
        }
    }

    private DrawableUtils() {
    }

    /*
     * Enabled aggressive block sorting
     */
    public static boolean canSafelyMutateDrawable(@NonNull Drawable drawableArray) {
        int n2;
        if (drawableArray instanceof LayerDrawable) {
            if (Build.VERSION.SDK_INT >= 16) return true;
            return false;
        }
        if (drawableArray instanceof InsetDrawable) {
            if (Build.VERSION.SDK_INT >= 14) return true;
            return false;
        }
        if (drawableArray instanceof StateListDrawable) {
            if (Build.VERSION.SDK_INT >= 8) return true;
            return false;
        }
        if (drawableArray instanceof GradientDrawable) {
            if (Build.VERSION.SDK_INT >= 14) return true;
            return false;
        }
        if (drawableArray instanceof DrawableContainer) {
            if (!((drawableArray = drawableArray.getConstantState()) instanceof DrawableContainer.DrawableContainerState)) return true;
            drawableArray = ((DrawableContainer.DrawableContainerState)drawableArray).getChildren();
            n2 = drawableArray.length;
        } else {
            if (drawableArray instanceof android.support.v4.graphics.drawable.DrawableWrapper) {
                return DrawableUtils.canSafelyMutateDrawable(((android.support.v4.graphics.drawable.DrawableWrapper)drawableArray).getWrappedDrawable());
            }
            if (drawableArray instanceof DrawableWrapper) {
                return DrawableUtils.canSafelyMutateDrawable(((DrawableWrapper)drawableArray).getWrappedDrawable());
            }
            if (!(drawableArray instanceof ScaleDrawable)) return true;
            return DrawableUtils.canSafelyMutateDrawable(((ScaleDrawable)drawableArray).getDrawable());
        }
        for (int i2 = 0; i2 < n2; ++i2) {
            if (DrawableUtils.canSafelyMutateDrawable(drawableArray[i2])) continue;
            return false;
        }
        return true;
    }

    static void fixDrawable(@NonNull Drawable drawable2) {
        if (Build.VERSION.SDK_INT == 21 && VECTOR_DRAWABLE_CLAZZ_NAME.equals(drawable2.getClass().getName())) {
            DrawableUtils.fixVectorDrawableTinting(drawable2);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private static void fixVectorDrawableTinting(Drawable drawable2) {
        int[] nArray = drawable2.getState();
        if (nArray == null || nArray.length == 0) {
            drawable2.setState(ThemeUtils.CHECKED_STATE_SET);
        } else {
            drawable2.setState(ThemeUtils.EMPTY_STATE_SET);
        }
        drawable2.setState(nArray);
    }

    /*
     * Exception decompiling
     */
    public static Rect getOpticalBounds(Drawable var0) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Back jump on a try block [egrp 8[TRYBLOCK] [9 : 221->263)] java.lang.Exception
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op02WithProcessedDataAndRefs.insertExceptionBlocks(Op02WithProcessedDataAndRefs.java:2289)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:414)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1042)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:929)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:73)
         *     at org.benf.cfr.reader.Main.main(Main.java:49)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    /*
     * Enabled aggressive block sorting
     */
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
            case 15: {
                return PorterDuff.Mode.SCREEN;
            }
            case 16: {
                if (Build.VERSION.SDK_INT < 11) return mode;
                return PorterDuff.Mode.valueOf((String)"ADD");
            }
        }
    }
}

