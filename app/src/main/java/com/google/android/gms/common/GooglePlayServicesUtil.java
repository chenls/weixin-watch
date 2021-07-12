/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.app.Activity
 *  android.app.AlertDialog$Builder
 *  android.app.Dialog
 *  android.app.FragmentManager
 *  android.app.Notification
 *  android.app.Notification$BigTextStyle
 *  android.app.Notification$Builder
 *  android.app.Notification$Style
 *  android.app.NotificationManager
 *  android.app.PendingIntent
 *  android.content.Context
 *  android.content.DialogInterface$OnCancelListener
 *  android.content.DialogInterface$OnClickListener
 *  android.content.Intent
 *  android.content.res.Resources
 *  android.os.Build$VERSION
 *  android.os.Handler
 *  android.os.Looper
 *  android.os.Message
 *  android.util.Log
 *  android.util.TypedValue
 */
package com.google.android.gms.common;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.util.TypedValue;
import com.google.android.gms.R;
import com.google.android.gms.common.ErrorDialogFragment;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.SupportErrorDialogFragment;
import com.google.android.gms.common.internal.zzg;
import com.google.android.gms.common.internal.zzh;
import com.google.android.gms.common.internal.zzx;
import com.google.android.gms.common.zze;
import com.google.android.gms.internal.zzmu;
import com.google.android.gms.internal.zzne;

public final class GooglePlayServicesUtil
extends zze {
    public static final String GMS_ERROR_DIALOG = "GooglePlayServicesErrorDialog";
    @Deprecated
    public static final String GOOGLE_PLAY_SERVICES_PACKAGE = "com.google.android.gms";
    @Deprecated
    public static final int GOOGLE_PLAY_SERVICES_VERSION_CODE = zze.GOOGLE_PLAY_SERVICES_VERSION_CODE;
    public static final String GOOGLE_PLAY_STORE_PACKAGE = "com.android.vending";

    private GooglePlayServicesUtil() {
    }

    @Deprecated
    public static Dialog getErrorDialog(int n2, Activity activity, int n3) {
        return GooglePlayServicesUtil.getErrorDialog(n2, activity, n3, null);
    }

    @Deprecated
    public static Dialog getErrorDialog(int n2, Activity activity, int n3, DialogInterface.OnCancelListener onCancelListener) {
        return GooglePlayServicesUtil.zza(n2, activity, null, n3, onCancelListener);
    }

    @Deprecated
    public static PendingIntent getErrorPendingIntent(int n2, Context context, int n3) {
        return zze.getErrorPendingIntent(n2, context, n3);
    }

    @Deprecated
    public static String getErrorString(int n2) {
        return zze.getErrorString(n2);
    }

    @Deprecated
    public static String getOpenSourceSoftwareLicenseInfo(Context context) {
        return zze.getOpenSourceSoftwareLicenseInfo(context);
    }

    public static Context getRemoteContext(Context context) {
        return zze.getRemoteContext(context);
    }

    public static Resources getRemoteResource(Context context) {
        return zze.getRemoteResource(context);
    }

    @Deprecated
    public static int isGooglePlayServicesAvailable(Context context) {
        return zze.isGooglePlayServicesAvailable(context);
    }

    @Deprecated
    public static boolean isUserRecoverableError(int n2) {
        return zze.isUserRecoverableError(n2);
    }

    @Deprecated
    public static boolean showErrorDialogFragment(int n2, Activity activity, int n3) {
        return GooglePlayServicesUtil.showErrorDialogFragment(n2, activity, n3, null);
    }

    @Deprecated
    public static boolean showErrorDialogFragment(int n2, Activity activity, int n3, DialogInterface.OnCancelListener onCancelListener) {
        return GooglePlayServicesUtil.showErrorDialogFragment(n2, activity, null, n3, onCancelListener);
    }

    public static boolean showErrorDialogFragment(int n2, Activity activity, Fragment fragment, int n3, DialogInterface.OnCancelListener onCancelListener) {
        if ((fragment = GooglePlayServicesUtil.zza(n2, activity, fragment, n3, onCancelListener)) == null) {
            return false;
        }
        GooglePlayServicesUtil.zza(activity, onCancelListener, GMS_ERROR_DIALOG, (Dialog)fragment);
        return true;
    }

    @Deprecated
    public static void showErrorNotification(int n2, Context context) {
        int n3 = n2;
        if (zzmu.zzaw(context)) {
            n3 = n2;
            if (n2 == 2) {
                n3 = 42;
            }
        }
        if (GooglePlayServicesUtil.zzd(context, n3) || GooglePlayServicesUtil.zze(context, n3)) {
            GooglePlayServicesUtil.zzam(context);
            return;
        }
        GooglePlayServicesUtil.zza(n3, context);
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    @TargetApi(value=14)
    private static Dialog zza(int n2, Activity object, Fragment object2, int n3, DialogInterface.OnCancelListener object3) {
        String string2;
        void var3_4;
        zzh zzh2;
        void var4_5;
        AlertDialog.Builder builder = null;
        if (n2 == 0) {
            return null;
        }
        int n4 = n2;
        if (zzmu.zzaw((Context)object)) {
            n4 = n2;
            if (n2 == 2) {
                n4 = 42;
            }
        }
        n2 = n4;
        if (GooglePlayServicesUtil.zzd((Context)object, n4)) {
            n2 = 18;
        }
        AlertDialog.Builder builder2 = builder;
        if (zzne.zzsg()) {
            TypedValue typedValue = new TypedValue();
            object.getTheme().resolveAttribute(16843529, typedValue, true);
            builder2 = builder;
            if ("Theme.Dialog.Alert".equals(object.getResources().getResourceEntryName(typedValue.resourceId))) {
                builder2 = new AlertDialog.Builder((Context)object, 5);
            }
        }
        builder = builder2;
        if (builder2 == null) {
            builder = new AlertDialog.Builder((Context)object);
        }
        builder.setMessage((CharSequence)zzg.zzc((Context)object, n2, GooglePlayServicesUtil.zzao((Context)object)));
        if (var4_5 != null) {
            builder.setOnCancelListener((DialogInterface.OnCancelListener)var4_5);
        }
        Intent intent = GoogleApiAvailability.getInstance().zza((Context)object, n2, "d");
        zzh2 = zzh2 == null ? new zzh((Activity)object, intent, (int)var3_4) : new zzh((Fragment)((Object)zzh2), intent, (int)var3_4);
        String string3 = zzg.zzh((Context)object, n2);
        if (string3 != null) {
            builder.setPositiveButton((CharSequence)string3, (DialogInterface.OnClickListener)zzh2);
        }
        if ((string2 = zzg.zzg((Context)object, n2)) != null) {
            builder.setTitle((CharSequence)string2);
        }
        return builder.create();
    }

    @TargetApi(value=21)
    private static void zza(int n2, Context context) {
        GooglePlayServicesUtil.zza(n2, context, null);
    }

    /*
     * Enabled aggressive block sorting
     */
    @TargetApi(value=21)
    private static void zza(int n2, Context context, String string2) {
        String string3;
        Resources resources = context.getResources();
        String string4 = GooglePlayServicesUtil.zzao(context);
        String string5 = string3 = zzg.zzg(context, n2);
        if (string3 == null) {
            string5 = resources.getString(R.string.common_google_play_services_notification_ticker);
        }
        string3 = zzg.zzc(context, n2, string4);
        string4 = GoogleApiAvailability.getInstance().zza(context, n2, 0, "n");
        if (zzmu.zzaw(context)) {
            zzx.zzab(zzne.zzsh());
            string5 = new Notification.Builder(context).setSmallIcon(R.drawable.common_ic_googleplayservices).setPriority(2).setAutoCancel(true).setStyle((Notification.Style)new Notification.BigTextStyle().bigText((CharSequence)(string5 + " " + string3))).addAction(R.drawable.common_full_open_on_phone, (CharSequence)resources.getString(R.string.common_open_on_phone), (PendingIntent)string4).build();
        } else {
            String string6 = resources.getString(R.string.common_google_play_services_notification_ticker);
            if (zzne.zzsd()) {
                string5 = new Notification.Builder(context).setSmallIcon(17301642).setContentTitle((CharSequence)string5).setContentText((CharSequence)string3).setContentIntent((PendingIntent)string4).setTicker((CharSequence)string6).setAutoCancel(true);
                if (zzne.zzsl()) {
                    string5.setLocalOnly(true);
                }
                if (zzne.zzsh()) {
                    string5.setStyle((Notification.Style)new Notification.BigTextStyle().bigText((CharSequence)string3));
                    string5 = string5.build();
                } else {
                    string5 = string5.getNotification();
                }
                if (Build.VERSION.SDK_INT == 19) {
                    ((Notification)string5).extras.putBoolean("android.support.localOnly", true);
                }
            } else {
                string5 = new NotificationCompat.Builder(context).setSmallIcon(17301642).setTicker(string6).setWhen(System.currentTimeMillis()).setAutoCancel(true).setContentIntent((PendingIntent)string4).setContentTitle(string5).setContentText(string3).build();
            }
        }
        if (GooglePlayServicesUtil.zzbw(n2)) {
            zzafQ.set(false);
            n2 = 10436;
        } else {
            n2 = 39789;
        }
        context = (NotificationManager)context.getSystemService("notification");
        if (string2 != null) {
            context.notify(string2, n2, (Notification)string5);
            return;
        }
        context.notify(n2, (Notification)string5);
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @TargetApi(value=11)
    public static void zza(Activity object, DialogInterface.OnCancelListener onCancelListener, String string2, @NonNull Dialog dialog) {
        void var2_4;
        void var1_3;
        void var3_5;
        boolean bl2;
        try {
            bl2 = object instanceof FragmentActivity;
        }
        catch (NoClassDefFoundError noClassDefFoundError) {
            bl2 = false;
        }
        if (bl2) {
            FragmentManager fragmentManager = ((FragmentActivity)object).getSupportFragmentManager();
            SupportErrorDialogFragment.newInstance((Dialog)var3_5, (DialogInterface.OnCancelListener)var1_3).show(fragmentManager, (String)var2_4);
            return;
        }
        if (zzne.zzsd()) {
            android.app.FragmentManager fragmentManager = object.getFragmentManager();
            ErrorDialogFragment.newInstance((Dialog)var3_5, (DialogInterface.OnCancelListener)var1_3).show(fragmentManager, (String)var2_4);
            return;
        }
        throw new RuntimeException("This Activity does not support Fragments.");
    }

    private static void zzam(Context object) {
        object = new zza((Context)object);
        object.sendMessageDelayed(object.obtainMessage(1), 120000L);
    }

    @Deprecated
    public static Intent zzbv(int n2) {
        return zze.zzbv(n2);
    }

    @Deprecated
    public static boolean zzd(Context context, int n2) {
        return zze.zzd(context, n2);
    }

    @Deprecated
    public static boolean zze(Context context, int n2) {
        return zze.zze(context, n2);
    }

    private static class zza
    extends Handler {
        private final Context zzsa;

        /*
         * Enabled aggressive block sorting
         */
        zza(Context context) {
            Looper looper = Looper.myLooper() == null ? Looper.getMainLooper() : Looper.myLooper();
            super(looper);
            this.zzsa = context.getApplicationContext();
        }

        /*
         * Enabled aggressive block sorting
         */
        public void handleMessage(Message message) {
            switch (message.what) {
                default: {
                    Log.w((String)"GooglePlayServicesUtil", (String)("Don't know how to handle this message: " + message.what));
                    return;
                }
                case 1: {
                    int n2 = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this.zzsa);
                    if (!GooglePlayServicesUtil.isUserRecoverableError(n2)) return;
                    GooglePlayServicesUtil.zza(n2, this.zzsa);
                    return;
                }
            }
        }
    }
}

