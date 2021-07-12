/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.AlertDialog$Builder
 *  android.app.Dialog
 *  android.app.PendingIntent
 *  android.content.Context
 *  android.content.DialogInterface$OnCancelListener
 *  android.content.Intent
 *  android.view.View
 *  android.widget.ProgressBar
 */
package com.google.android.gms.common;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ProgressBar;
import com.google.android.gms.R;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.zzc;

public class GoogleApiAvailability
extends zzc {
    public static final String GOOGLE_PLAY_SERVICES_PACKAGE = "com.google.android.gms";
    public static final int GOOGLE_PLAY_SERVICES_VERSION_CODE;
    private static final GoogleApiAvailability zzafE;

    static {
        zzafE = new GoogleApiAvailability();
        GOOGLE_PLAY_SERVICES_VERSION_CODE = zzc.GOOGLE_PLAY_SERVICES_VERSION_CODE;
    }

    GoogleApiAvailability() {
    }

    public static GoogleApiAvailability getInstance() {
        return zzafE;
    }

    public Dialog getErrorDialog(Activity activity, int n2, int n3) {
        return GooglePlayServicesUtil.getErrorDialog(n2, activity, n3);
    }

    public Dialog getErrorDialog(Activity activity, int n2, int n3, DialogInterface.OnCancelListener onCancelListener) {
        return GooglePlayServicesUtil.getErrorDialog(n2, activity, n3, onCancelListener);
    }

    @Override
    @Nullable
    public PendingIntent getErrorResolutionPendingIntent(Context context, int n2, int n3) {
        return super.getErrorResolutionPendingIntent(context, n2, n3);
    }

    @Override
    public final String getErrorString(int n2) {
        return super.getErrorString(n2);
    }

    @Override
    @Nullable
    public String getOpenSourceSoftwareLicenseInfo(Context context) {
        return super.getOpenSourceSoftwareLicenseInfo(context);
    }

    @Override
    public int isGooglePlayServicesAvailable(Context context) {
        return super.isGooglePlayServicesAvailable(context);
    }

    @Override
    public final boolean isUserResolvableError(int n2) {
        return super.isUserResolvableError(n2);
    }

    public boolean showErrorDialogFragment(Activity activity, int n2, int n3) {
        return GooglePlayServicesUtil.showErrorDialogFragment(n2, activity, n3);
    }

    public boolean showErrorDialogFragment(Activity activity, int n2, int n3, DialogInterface.OnCancelListener onCancelListener) {
        return GooglePlayServicesUtil.showErrorDialogFragment(n2, activity, n3, onCancelListener);
    }

    public void showErrorNotification(Context context, int n2) {
        GooglePlayServicesUtil.showErrorNotification(n2, context);
    }

    public Dialog zza(Activity activity, DialogInterface.OnCancelListener onCancelListener) {
        Object object = new ProgressBar((Context)activity, null, 16842874);
        object.setIndeterminate(true);
        object.setVisibility(0);
        AlertDialog.Builder builder = new AlertDialog.Builder((Context)activity);
        builder.setView((View)object);
        object = GooglePlayServicesUtil.zzao((Context)activity);
        builder.setMessage((CharSequence)activity.getResources().getString(R.string.common_google_play_services_updating_text, new Object[]{object}));
        builder.setTitle(R.string.common_google_play_services_updating_title);
        builder.setPositiveButton((CharSequence)"", null);
        builder = builder.create();
        GooglePlayServicesUtil.zza(activity, onCancelListener, "GooglePlayServicesUpdatingDialog", (Dialog)builder);
        return builder;
    }

    @Override
    @Nullable
    public PendingIntent zza(Context context, int n2, int n3, @Nullable String string2) {
        return super.zza(context, n2, n3, string2);
    }

    @Override
    @Nullable
    public Intent zza(Context context, int n2, @Nullable String string2) {
        return super.zza(context, n2, string2);
    }

    @Override
    public int zzaj(Context context) {
        return super.zzaj(context);
    }

    @Override
    @Deprecated
    @Nullable
    public Intent zzbu(int n2) {
        return super.zzbu(n2);
    }

    @Override
    public boolean zzd(Context context, int n2) {
        return super.zzd(context, n2);
    }
}

