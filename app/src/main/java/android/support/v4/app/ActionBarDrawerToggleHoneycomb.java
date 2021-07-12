/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.app.ActionBar
 *  android.app.Activity
 *  android.graphics.drawable.Drawable
 *  android.os.Build$VERSION
 *  android.util.Log
 *  android.view.ViewGroup
 *  android.widget.ImageView
 */
package android.support.v4.app;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import java.lang.reflect.Method;

class ActionBarDrawerToggleHoneycomb {
    private static final String TAG = "ActionBarDrawerToggleHoneycomb";
    private static final int[] THEME_ATTRS = new int[]{16843531};

    ActionBarDrawerToggleHoneycomb() {
    }

    public static Drawable getThemeUpIndicator(Activity activity) {
        activity = activity.obtainStyledAttributes(THEME_ATTRS);
        Drawable drawable2 = activity.getDrawable(0);
        activity.recycle();
        return drawable2;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static Object setActionBarDescription(Object object, Activity activity, int n2) {
        Object object2 = object;
        if (object == null) {
            object2 = new SetIndicatorInfo(activity);
        }
        object = (SetIndicatorInfo)object2;
        if (((SetIndicatorInfo)object).setHomeAsUpIndicator == null) return object2;
        try {
            activity = activity.getActionBar();
            ((SetIndicatorInfo)object).setHomeActionContentDescription.invoke(activity, n2);
            if (Build.VERSION.SDK_INT > 19) return object2;
            activity.setSubtitle(activity.getSubtitle());
        }
        catch (Exception exception) {
            Log.w((String)TAG, (String)"Couldn't set content description via JB-MR2 API", (Throwable)exception);
            return object2;
        }
        return object2;
    }

    public static Object setActionBarUpIndicator(Object object, Activity activity, Drawable drawable2, int n2) {
        Object object2 = object;
        if (object == null) {
            object2 = new SetIndicatorInfo(activity);
        }
        object = (SetIndicatorInfo)object2;
        if (((SetIndicatorInfo)object).setHomeAsUpIndicator != null) {
            try {
                activity = activity.getActionBar();
                ((SetIndicatorInfo)object).setHomeAsUpIndicator.invoke(activity, drawable2);
                ((SetIndicatorInfo)object).setHomeActionContentDescription.invoke(activity, n2);
                return object2;
            }
            catch (Exception exception) {
                Log.w((String)TAG, (String)"Couldn't set home-as-up indicator via JB-MR2 API", (Throwable)exception);
                return object2;
            }
        }
        if (((SetIndicatorInfo)object).upIndicatorView != null) {
            ((SetIndicatorInfo)object).upIndicatorView.setImageDrawable(drawable2);
            return object2;
        }
        Log.w((String)TAG, (String)"Couldn't set home-as-up indicator");
        return object2;
    }

    private static class SetIndicatorInfo {
        public Method setHomeActionContentDescription;
        public Method setHomeAsUpIndicator;
        public ImageView upIndicatorView;

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        SetIndicatorInfo(Activity activity) {
            try {
                this.setHomeAsUpIndicator = ActionBar.class.getDeclaredMethod("setHomeAsUpIndicator", Drawable.class);
                this.setHomeActionContentDescription = ActionBar.class.getDeclaredMethod("setHomeActionContentDescription", Integer.TYPE);
                return;
            }
            catch (NoSuchMethodException noSuchMethodException) {
                ViewGroup viewGroup;
                if ((activity = activity.findViewById(16908332)) == null || (viewGroup = (ViewGroup)activity.getParent()).getChildCount() != 2) return;
                activity = viewGroup.getChildAt(0);
                viewGroup = viewGroup.getChildAt(1);
                if (activity.getId() == 16908332) {
                    activity = viewGroup;
                }
                if (!(activity instanceof ImageView)) return;
                this.upIndicatorView = (ImageView)activity;
                return;
            }
        }
    }
}

