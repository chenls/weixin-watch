/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.app.NotificationManager
 *  android.app.PendingIntent
 *  android.content.Context
 *  android.content.Intent
 *  android.content.pm.ApplicationInfo
 *  android.content.pm.PackageInfo
 *  android.content.pm.PackageInstaller$SessionInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.content.res.Resources
 *  android.net.Uri$Builder
 *  android.os.Build
 *  android.os.UserManager
 *  android.text.TextUtils
 *  android.util.Log
 */
package com.google.android.gms.common;

import android.annotation.TargetApi;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageInstaller;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.UserManager;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.internal.zzd;
import com.google.android.gms.common.zzc;
import com.google.android.gms.common.zzd;
import com.google.android.gms.common.zzf;
import com.google.android.gms.internal.zzmu;
import com.google.android.gms.internal.zzmx;
import com.google.android.gms.internal.zzne;
import java.io.InputStream;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class zze {
    @Deprecated
    public static final String GOOGLE_PLAY_SERVICES_PACKAGE = "com.google.android.gms";
    @Deprecated
    public static final int GOOGLE_PLAY_SERVICES_VERSION_CODE = zze.zzoM();
    public static final String GOOGLE_PLAY_STORE_PACKAGE = "com.android.vending";
    public static boolean zzafL = false;
    public static boolean zzafM = false;
    static int zzafN = -1;
    private static String zzafO;
    private static Integer zzafP;
    static final AtomicBoolean zzafQ;
    private static final AtomicBoolean zzafR;
    private static final Object zzqy;

    static {
        zzqy = new Object();
        zzafO = null;
        zzafP = null;
        zzafQ = new AtomicBoolean();
        zzafR = new AtomicBoolean();
    }

    zze() {
    }

    @Deprecated
    public static PendingIntent getErrorPendingIntent(int n2, Context context, int n3) {
        return zzc.zzoK().getErrorResolutionPendingIntent(context, n2, n3);
    }

    @Deprecated
    public static String getErrorString(int n2) {
        return ConnectionResult.getStatusString(n2);
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Deprecated
    public static String getOpenSourceSoftwareLicenseInfo(Context object) {
        InputStream inputStream;
        Object object2 = new Uri.Builder().scheme("android.resource").authority(GOOGLE_PLAY_SERVICES_PACKAGE).appendPath("raw").appendPath("oss_notice").build();
        try {
            inputStream = object.getContentResolver().openInputStream(object2);
        }
        catch (Exception exception) {
            return null;
        }
        String string2 = new Scanner(inputStream).useDelimiter("\\A").next();
        object2 = string2;
        if (inputStream == null) return object2;
        {
            catch (NoSuchElementException noSuchElementException) {
                if (inputStream == null) return null;
                inputStream.close();
                return null;
            }
            catch (Throwable throwable) {
                if (inputStream == null) throw throwable;
                inputStream.close();
                throw throwable;
            }
            inputStream.close();
            return string2;
        }
    }

    public static Context getRemoteContext(Context context) {
        try {
            context = context.createPackageContext(GOOGLE_PLAY_SERVICES_PACKAGE, 3);
            return context;
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            return null;
        }
    }

    public static Resources getRemoteResource(Context context) {
        try {
            context = context.getPackageManager().getResourcesForApplication(GOOGLE_PLAY_SERVICES_PACKAGE);
            return context;
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            return null;
        }
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Deprecated
    public static int isGooglePlayServicesAvailable(Context object) {
        PackageInfo packageInfo;
        PackageManager packageManager;
        block18: {
            zzf zzf2;
            block17: {
                if (zzd.zzakE) {
                    return 0;
                }
                packageManager = object.getPackageManager();
                try {
                    object.getResources().getString(R.string.common_google_play_services_unknown_issue);
                }
                catch (Throwable throwable) {
                    Log.e((String)"GooglePlayServicesUtil", (String)"The Google Play services resources were not found. Check your project configuration to ensure that the resources are included.");
                }
                if (!GOOGLE_PLAY_SERVICES_PACKAGE.equals(object.getPackageName())) {
                    zze.zzan(object);
                }
                try {
                    packageInfo = packageManager.getPackageInfo(GOOGLE_PLAY_SERVICES_PACKAGE, 64);
                }
                catch (PackageManager.NameNotFoundException nameNotFoundException) {
                    Log.w((String)"GooglePlayServicesUtil", (String)"Google Play services is missing.");
                    return 1;
                }
                zzf2 = zzf.zzoO();
                if (!zzmu.zzaw(object)) break block17;
                if (zzf2.zza(packageInfo, zzd.zzd.zzafK) == null) {
                    Log.w((String)"GooglePlayServicesUtil", (String)"Google Play services signature invalid.");
                    return 9;
                }
                break block18;
            }
            try {
                object = zzf2.zza(packageManager.getPackageInfo(GOOGLE_PLAY_STORE_PACKAGE, 8256), zzd.zzd.zzafK);
                if (object == null) {
                    Log.w((String)"GooglePlayServicesUtil", (String)"Google Play Store signature invalid.");
                    return 9;
                }
                if (zzf2.zza(packageInfo, new zzd.zza[]{object}) == null) {
                    Log.w((String)"GooglePlayServicesUtil", (String)"Google Play services signature invalid.");
                    return 9;
                }
            }
            catch (PackageManager.NameNotFoundException nameNotFoundException) {
                Log.w((String)"GooglePlayServicesUtil", (String)"Google Play Store is neither installed nor updating.");
                return 9;
            }
        }
        int n2 = zzmx.zzco(GOOGLE_PLAY_SERVICES_VERSION_CODE);
        if (zzmx.zzco(packageInfo.versionCode) < n2) {
            Log.w((String)"GooglePlayServicesUtil", (String)("Google Play services out of date.  Requires " + GOOGLE_PLAY_SERVICES_VERSION_CODE + " but found " + packageInfo.versionCode));
            return 2;
        }
        packageInfo = packageInfo.applicationInfo;
        object = packageInfo;
        if (packageInfo == null) {
            object = packageManager.getApplicationInfo(GOOGLE_PLAY_SERVICES_PACKAGE, 0);
        }
        if (object.enabled) return 0;
        return 3;
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            Log.wtf((String)"GooglePlayServicesUtil", (String)"Google Play services missing when getting application info.", (Throwable)nameNotFoundException);
            return 1;
        }
    }

    @Deprecated
    public static boolean isUserRecoverableError(int n2) {
        switch (n2) {
            default: {
                return false;
            }
            case 1: 
            case 2: 
            case 3: 
            case 9: 
        }
        return true;
    }

    @Deprecated
    public static void zzad(Context context) throws GooglePlayServicesRepairableException, GooglePlayServicesNotAvailableException {
        int n2 = zzc.zzoK().isGooglePlayServicesAvailable(context);
        if (n2 != 0) {
            context = zzc.zzoK().zza(context, n2, "e");
            Log.e((String)"GooglePlayServicesUtil", (String)("GooglePlayServices not available due to error " + n2));
            if (context == null) {
                throw new GooglePlayServicesNotAvailableException(n2);
            }
            throw new GooglePlayServicesRepairableException(n2, "Google Play Services not available", (Intent)context);
        }
    }

    @Deprecated
    public static int zzaj(Context context) {
        try {
            context = context.getPackageManager().getPackageInfo(GOOGLE_PLAY_SERVICES_PACKAGE, 0);
            return context.versionCode;
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            Log.w((String)"GooglePlayServicesUtil", (String)"Google Play services is missing.");
            return 0;
        }
    }

    @Deprecated
    public static void zzal(Context context) {
        if (zzafQ.getAndSet(true)) {
            return;
        }
        try {
            ((NotificationManager)context.getSystemService("notification")).cancel(10436);
            return;
        }
        catch (SecurityException securityException) {
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private static void zzan(Context object) {
        block13: {
            if (zzafR.get()) {
                return;
            }
            Object object2 = zzqy;
            // MONITORENTER : object2
            if (zzafO == null) {
                zzafO = object.getPackageName();
                try {
                    object = object.getPackageManager().getApplicationInfo((String)object.getPackageName(), (int)128).metaData;
                    if (object != null) {
                        zzafP = object.getInt("com.google.android.gms.version");
                        break block13;
                    }
                    zzafP = null;
                }
                catch (PackageManager.NameNotFoundException nameNotFoundException) {
                    Log.wtf((String)"GooglePlayServicesUtil", (String)"This should never happen.", (Throwable)nameNotFoundException);
                }
            } else if (!zzafO.equals(object.getPackageName())) {
                throw new IllegalArgumentException("isGooglePlayServicesAvailable should only be called with Context from your application's package. A previous call used package '" + zzafO + "' and this call used package '" + object.getPackageName() + "'.");
            }
        }
        object = zzafP;
        // MONITOREXIT : object2
        if (object == null) {
            throw new IllegalStateException("A required meta-data tag in your app's AndroidManifest.xml does not exist.  You must have the following declaration within the <application> element:     <meta-data android:name=\"com.google.android.gms.version\" android:value=\"@integer/google_play_services_version\" />");
        }
        if ((Integer)object == GOOGLE_PLAY_SERVICES_VERSION_CODE) return;
        throw new IllegalStateException("The meta-data tag in your app's AndroidManifest.xml does not have the right value.  Expected " + GOOGLE_PLAY_SERVICES_VERSION_CODE + " but" + " found " + object + ".  You must have the" + " following declaration within the <application> element: " + "    <meta-data android:name=\"" + "com.google.android.gms.version" + "\" android:value=\"@integer/google_play_services_version\" />");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static String zzao(Context context) {
        String string2;
        String string3 = string2 = context.getApplicationInfo().name;
        if (!TextUtils.isEmpty((CharSequence)string2)) return string3;
        string3 = context.getPackageName();
        string2 = context.getApplicationContext().getPackageManager();
        try {
            context = string2.getApplicationInfo(context.getPackageName(), 0);
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            context = null;
        }
        if (context == null) return string3;
        return string2.getApplicationLabel((ApplicationInfo)context).toString();
    }

    public static boolean zzap(Context context) {
        context = context.getPackageManager();
        return zzne.zzsm() && context.hasSystemFeature("cn.google");
    }

    @TargetApi(value=18)
    public static boolean zzaq(Context context) {
        return zzne.zzsj() && (context = ((UserManager)context.getSystemService("user")).getApplicationRestrictions(context.getPackageName())) != null && "true".equals(context.getString("restricted_profile"));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @TargetApi(value=19)
    public static boolean zzb(Context stringArray, int n2, String string2) {
        boolean bl2 = false;
        if (zzne.zzsk()) {
            stringArray = (String[])stringArray.getSystemService("appops");
            try {
                stringArray.checkPackage(n2, string2);
                return true;
            }
            catch (SecurityException securityException) {
                return false;
            }
        }
        stringArray = stringArray.getPackageManager().getPackagesForUid(n2);
        boolean bl3 = bl2;
        if (string2 == null) return bl3;
        bl3 = bl2;
        if (stringArray == null) return bl3;
        n2 = 0;
        while (true) {
            bl3 = bl2;
            if (n2 >= stringArray.length) return bl3;
            if (string2.equals(stringArray[n2])) {
                return true;
            }
            ++n2;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static boolean zzb(PackageManager packageManager) {
        boolean bl2 = true;
        Object object = zzqy;
        synchronized (object) {
            block7: {
                int n2 = zzafN;
                if (n2 == -1) {
                    try {
                        packageManager = packageManager.getPackageInfo(GOOGLE_PLAY_SERVICES_PACKAGE, 64);
                        if (zzf.zzoO().zza((PackageInfo)packageManager, zzd.zzd.zzafK[1]) != null) {
                            zzafN = 1;
                            break block7;
                        }
                        zzafN = 0;
                    }
                    catch (PackageManager.NameNotFoundException nameNotFoundException) {
                        zzafN = 0;
                    }
                }
            }
            if (zzafN != 0) return bl2;
            return false;
        }
    }

    @Deprecated
    public static Intent zzbv(int n2) {
        return zzc.zzoK().zza(null, n2, null);
    }

    static boolean zzbw(int n2) {
        switch (n2) {
            default: {
                return false;
            }
            case 1: 
            case 2: 
            case 3: 
            case 18: 
            case 42: 
        }
        return true;
    }

    public static boolean zzc(PackageManager packageManager) {
        return zze.zzb(packageManager) || !zze.zzoN();
    }

    @Deprecated
    public static boolean zzd(Context context, int n2) {
        if (n2 == 18) {
            return true;
        }
        if (n2 == 1) {
            return zze.zzi(context, GOOGLE_PLAY_SERVICES_PACKAGE);
        }
        return false;
    }

    @Deprecated
    public static boolean zze(Context context, int n2) {
        if (n2 == 9) {
            return zze.zzi(context, GOOGLE_PLAY_STORE_PACKAGE);
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static boolean zzf(Context context, int n2) {
        block3: {
            if (zze.zzb(context, n2, GOOGLE_PLAY_SERVICES_PACKAGE)) {
                PackageManager packageManager = context.getPackageManager();
                try {
                    packageManager = packageManager.getPackageInfo(GOOGLE_PLAY_SERVICES_PACKAGE, 64);
                    return zzf.zzoO().zza(context.getPackageManager(), (PackageInfo)packageManager);
                }
                catch (PackageManager.NameNotFoundException nameNotFoundException) {
                    if (!Log.isLoggable((String)"GooglePlayServicesUtil", (int)3)) break block3;
                    Log.d((String)"GooglePlayServicesUtil", (String)"Package manager can't find google play services package, defaulting to false");
                    return false;
                }
            }
        }
        return false;
    }

    @TargetApi(value=21)
    static boolean zzi(Context context, String string2) {
        if (zzne.zzsm()) {
            Iterator iterator = context.getPackageManager().getPackageInstaller().getAllSessions().iterator();
            while (iterator.hasNext()) {
                if (!string2.equals(((PackageInstaller.SessionInfo)iterator.next()).getAppPackageName())) continue;
                return true;
            }
        }
        if (zze.zzaq(context)) {
            return false;
        }
        context = context.getPackageManager();
        try {
            boolean bl2 = context.getApplicationInfo((String)string2, (int)8192).enabled;
            return bl2;
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            return false;
        }
    }

    private static int zzoM() {
        return 8487000;
    }

    public static boolean zzoN() {
        if (zzafL) {
            return zzafM;
        }
        return "user".equals(Build.TYPE);
    }
}

