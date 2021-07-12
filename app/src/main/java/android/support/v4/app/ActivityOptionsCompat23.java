/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.ActivityOptions
 *  android.app.PendingIntent
 *  android.content.Context
 *  android.graphics.Bitmap
 *  android.os.Bundle
 *  android.util.Pair
 *  android.view.View
 */
package android.support.v4.app;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;

class ActivityOptionsCompat23 {
    private final ActivityOptions mActivityOptions;

    private ActivityOptionsCompat23(ActivityOptions activityOptions) {
        this.mActivityOptions = activityOptions;
    }

    public static ActivityOptionsCompat23 makeBasic() {
        return new ActivityOptionsCompat23(ActivityOptions.makeBasic());
    }

    public static ActivityOptionsCompat23 makeClipRevealAnimation(View view, int n2, int n3, int n4, int n5) {
        return new ActivityOptionsCompat23(ActivityOptions.makeClipRevealAnimation((View)view, (int)n2, (int)n3, (int)n4, (int)n5));
    }

    public static ActivityOptionsCompat23 makeCustomAnimation(Context context, int n2, int n3) {
        return new ActivityOptionsCompat23(ActivityOptions.makeCustomAnimation((Context)context, (int)n2, (int)n3));
    }

    public static ActivityOptionsCompat23 makeScaleUpAnimation(View view, int n2, int n3, int n4, int n5) {
        return new ActivityOptionsCompat23(ActivityOptions.makeScaleUpAnimation((View)view, (int)n2, (int)n3, (int)n4, (int)n5));
    }

    public static ActivityOptionsCompat23 makeSceneTransitionAnimation(Activity activity, View view, String string2) {
        return new ActivityOptionsCompat23(ActivityOptions.makeSceneTransitionAnimation((Activity)activity, (View)view, (String)string2));
    }

    public static ActivityOptionsCompat23 makeSceneTransitionAnimation(Activity activity, View[] viewArray, String[] stringArray) {
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
        return new ActivityOptionsCompat23(ActivityOptions.makeSceneTransitionAnimation((Activity)activity, pairArray));
    }

    public static ActivityOptionsCompat23 makeTaskLaunchBehind() {
        return new ActivityOptionsCompat23(ActivityOptions.makeTaskLaunchBehind());
    }

    public static ActivityOptionsCompat23 makeThumbnailScaleUpAnimation(View view, Bitmap bitmap, int n2, int n3) {
        return new ActivityOptionsCompat23(ActivityOptions.makeThumbnailScaleUpAnimation((View)view, (Bitmap)bitmap, (int)n2, (int)n3));
    }

    public void requestUsageTimeReport(PendingIntent pendingIntent) {
        this.mActivityOptions.requestUsageTimeReport(pendingIntent);
    }

    public Bundle toBundle() {
        return this.mActivityOptions.toBundle();
    }

    public void update(ActivityOptionsCompat23 activityOptionsCompat23) {
        this.mActivityOptions.update(activityOptionsCompat23.mActivityOptions);
    }
}

