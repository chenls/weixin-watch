/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.ActivityOptions
 *  android.content.Context
 *  android.graphics.Bitmap
 *  android.os.Bundle
 *  android.util.Pair
 *  android.view.View
 */
package android.support.v4.app;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;

class ActivityOptionsCompat21 {
    private final ActivityOptions mActivityOptions;

    private ActivityOptionsCompat21(ActivityOptions activityOptions) {
        this.mActivityOptions = activityOptions;
    }

    public static ActivityOptionsCompat21 makeCustomAnimation(Context context, int n2, int n3) {
        return new ActivityOptionsCompat21(ActivityOptions.makeCustomAnimation((Context)context, (int)n2, (int)n3));
    }

    public static ActivityOptionsCompat21 makeScaleUpAnimation(View view, int n2, int n3, int n4, int n5) {
        return new ActivityOptionsCompat21(ActivityOptions.makeScaleUpAnimation((View)view, (int)n2, (int)n3, (int)n4, (int)n5));
    }

    public static ActivityOptionsCompat21 makeSceneTransitionAnimation(Activity activity, View view, String string2) {
        return new ActivityOptionsCompat21(ActivityOptions.makeSceneTransitionAnimation((Activity)activity, (View)view, (String)string2));
    }

    public static ActivityOptionsCompat21 makeSceneTransitionAnimation(Activity activity, View[] viewArray, String[] stringArray) {
        Pair[] pairArray = null;
        if (viewArray != null) {
            Pair[] pairArray2 = new Pair[viewArray.length];
            int n2 = 0;
            while (true) {
                pairArray = pairArray2;
                if (n2 >= pairArray2.length) break;
                pairArray2[n2] = Pair.create((Object)viewArray[n2], (Object)stringArray[n2]);
                ++n2;
            }
        }
        return new ActivityOptionsCompat21(ActivityOptions.makeSceneTransitionAnimation((Activity)activity, pairArray));
    }

    public static ActivityOptionsCompat21 makeTaskLaunchBehind() {
        return new ActivityOptionsCompat21(ActivityOptions.makeTaskLaunchBehind());
    }

    public static ActivityOptionsCompat21 makeThumbnailScaleUpAnimation(View view, Bitmap bitmap, int n2, int n3) {
        return new ActivityOptionsCompat21(ActivityOptions.makeThumbnailScaleUpAnimation((View)view, (Bitmap)bitmap, (int)n2, (int)n3));
    }

    public Bundle toBundle() {
        return this.mActivityOptions.toBundle();
    }

    public void update(ActivityOptionsCompat21 activityOptionsCompat21) {
        this.mActivityOptions.update(activityOptionsCompat21.mActivityOptions);
    }
}

