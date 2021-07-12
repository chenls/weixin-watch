/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.graphics.drawable.Drawable$ConstantState
 *  android.graphics.drawable.DrawableContainer
 *  android.graphics.drawable.DrawableContainer$DrawableContainerState
 *  android.os.Build$VERSION
 *  android.util.Log
 */
package android.support.design.widget;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.os.Build;
import android.util.Log;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

class DrawableUtils {
    private static final String LOG_TAG = "DrawableUtils";
    private static Field sDrawableContainerStateField;
    private static boolean sDrawableContainerStateFieldFetched;
    private static Method sSetConstantStateMethod;
    private static boolean sSetConstantStateMethodFetched;

    private DrawableUtils() {
    }

    static boolean setContainerConstantState(DrawableContainer drawableContainer, Drawable.ConstantState constantState) {
        if (Build.VERSION.SDK_INT >= 9) {
            return DrawableUtils.setContainerConstantStateV9(drawableContainer, constantState);
        }
        return DrawableUtils.setContainerConstantStateV7(drawableContainer, constantState);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static boolean setContainerConstantStateV7(DrawableContainer drawableContainer, Drawable.ConstantState constantState) {
        if (!sDrawableContainerStateFieldFetched) {
            try {
                sDrawableContainerStateField = DrawableContainer.class.getDeclaredField("mDrawableContainerStateField");
                sDrawableContainerStateField.setAccessible(true);
            }
            catch (NoSuchFieldException noSuchFieldException) {
                Log.e((String)LOG_TAG, (String)"Could not fetch mDrawableContainerStateField. Oh well.");
            }
            sDrawableContainerStateFieldFetched = true;
        }
        if (sDrawableContainerStateField != null) {
            try {
                sDrawableContainerStateField.set(drawableContainer, constantState);
                return true;
            }
            catch (Exception exception) {
                Log.e((String)LOG_TAG, (String)"Could not set mDrawableContainerStateField. Oh well.");
            }
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static boolean setContainerConstantStateV9(DrawableContainer drawableContainer, Drawable.ConstantState constantState) {
        if (!sSetConstantStateMethodFetched) {
            try {
                sSetConstantStateMethod = DrawableContainer.class.getDeclaredMethod("setConstantState", DrawableContainer.DrawableContainerState.class);
                sSetConstantStateMethod.setAccessible(true);
            }
            catch (NoSuchMethodException noSuchMethodException) {
                Log.e((String)LOG_TAG, (String)"Could not fetch setConstantState(). Oh well.");
            }
            sSetConstantStateMethodFetched = true;
        }
        if (sSetConstantStateMethod != null) {
            try {
                sSetConstantStateMethod.invoke(drawableContainer, constantState);
                return true;
            }
            catch (Exception exception) {
                Log.e((String)LOG_TAG, (String)"Could not invoke setConstantState(). Oh well.");
            }
        }
        return false;
    }
}

