/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.app.PendingIntent
 *  android.content.Context
 *  android.content.Intent
 *  android.os.Bundle
 */
package android.support.v4.app;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

class TaskStackBuilderJellybean {
    TaskStackBuilderJellybean() {
    }

    public static PendingIntent getActivitiesPendingIntent(Context context, int n2, Intent[] intentArray, int n3, Bundle bundle) {
        return PendingIntent.getActivities((Context)context, (int)n2, (Intent[])intentArray, (int)n3, (Bundle)bundle);
    }
}

