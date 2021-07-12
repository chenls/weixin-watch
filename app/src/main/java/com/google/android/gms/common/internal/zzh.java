/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.ActivityNotFoundException
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnClickListener
 *  android.content.Intent
 *  android.util.Log
 */
package com.google.android.gms.common.internal;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.util.Log;

public class zzh
implements DialogInterface.OnClickListener {
    private final Activity mActivity;
    private final Intent mIntent;
    private final int zzagz;
    private final Fragment zzalg;

    public zzh(Activity activity, Intent intent, int n2) {
        this.mActivity = activity;
        this.zzalg = null;
        this.mIntent = intent;
        this.zzagz = n2;
    }

    public zzh(Fragment fragment, Intent intent, int n2) {
        this.mActivity = null;
        this.zzalg = fragment;
        this.mIntent = intent;
        this.zzagz = n2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void onClick(DialogInterface dialogInterface, int n2) {
        try {
            if (this.mIntent != null && this.zzalg != null) {
                this.zzalg.startActivityForResult(this.mIntent, this.zzagz);
            } else if (this.mIntent != null) {
                this.mActivity.startActivityForResult(this.mIntent, this.zzagz);
            }
            dialogInterface.dismiss();
            return;
        }
        catch (ActivityNotFoundException activityNotFoundException) {
            Log.e((String)"SettingsRedirect", (String)"Can't redirect to app settings for Google Play services");
            return;
        }
    }
}

