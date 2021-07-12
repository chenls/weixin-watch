/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.os.WorkSource
 *  android.util.Log
 */
package com.google.android.gms.internal;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.WorkSource;
import android.util.Log;
import com.google.android.gms.internal.zzne;
import com.google.android.gms.internal.zzni;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class zznj {
    private static final Method zzaol = zznj.zzsp();
    private static final Method zzaom = zznj.zzsq();
    private static final Method zzaon = zznj.zzsr();
    private static final Method zzaoo = zznj.zzss();
    private static final Method zzaop = zznj.zzst();

    public static int zza(WorkSource workSource) {
        if (zzaon != null) {
            try {
                int n2 = (Integer)zzaon.invoke(workSource, new Object[0]);
                return n2;
            }
            catch (Exception exception) {
                Log.wtf((String)"WorkSourceUtil", (String)"Unable to assign blame through WorkSource", (Throwable)exception);
            }
        }
        return 0;
    }

    public static String zza(WorkSource object, int n2) {
        if (zzaop != null) {
            try {
                object = (String)zzaop.invoke(object, n2);
                return object;
            }
            catch (Exception exception) {
                Log.wtf((String)"WorkSourceUtil", (String)"Unable to assign blame through WorkSource", (Throwable)exception);
            }
        }
        return null;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void zza(WorkSource workSource, int n2, String string2) {
        if (zzaom != null) {
            String string3 = string2;
            if (string2 == null) {
                string3 = "";
            }
            try {
                zzaom.invoke(workSource, n2, string3);
                return;
            }
            catch (Exception exception) {
                Log.wtf((String)"WorkSourceUtil", (String)"Unable to assign blame through WorkSource", (Throwable)exception);
                return;
            }
        } else {
            if (zzaol == null) return;
            try {
                zzaol.invoke(workSource, n2);
                return;
            }
            catch (Exception exception) {
                Log.wtf((String)"WorkSourceUtil", (String)"Unable to assign blame through WorkSource", (Throwable)exception);
                return;
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public static boolean zzaA(Context context) {
        PackageManager packageManager;
        return context != null && (packageManager = context.getPackageManager()) != null && packageManager.checkPermission("android.permission.UPDATE_DEVICE_STATS", context.getPackageName()) == 0;
    }

    /*
     * Enabled aggressive block sorting
     */
    public static List<String> zzb(WorkSource workSource) {
        int n2 = 0;
        int n3 = workSource == null ? 0 : zznj.zza(workSource);
        if (n3 == 0) {
            return Collections.EMPTY_LIST;
        }
        ArrayList<Object> arrayList = new ArrayList<Object>();
        while (true) {
            Object object = arrayList;
            if (n2 >= n3) return object;
            object = zznj.zza(workSource, n2);
            if (!zzni.zzcV((String)object)) {
                arrayList.add(object);
            }
            ++n2;
        }
    }

    public static WorkSource zzf(int n2, String string2) {
        WorkSource workSource = new WorkSource();
        zznj.zza(workSource, n2, string2);
        return workSource;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static WorkSource zzl(Context context, String string2) {
        if (context == null || context.getPackageManager() == null) {
            return null;
        }
        try {
            context = context.getPackageManager().getApplicationInfo(string2, 0);
            if (context != null) return zznj.zzf(context.uid, string2);
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            Log.e((String)"WorkSourceUtil", (String)("Could not find package: " + string2));
            return null;
        }
        Log.e((String)"WorkSourceUtil", (String)("Could not get applicationInfo from package: " + string2));
        return null;
    }

    private static Method zzsp() {
        try {
            Method method = WorkSource.class.getMethod("add", Integer.TYPE);
            return method;
        }
        catch (Exception exception) {
            return null;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static Method zzsq() {
        Method method = null;
        if (!zzne.zzsj()) return method;
        try {
            return WorkSource.class.getMethod("add", Integer.TYPE, String.class);
        }
        catch (Exception exception) {
            return null;
        }
    }

    private static Method zzsr() {
        try {
            Method method = WorkSource.class.getMethod("size", new Class[0]);
            return method;
        }
        catch (Exception exception) {
            return null;
        }
    }

    private static Method zzss() {
        try {
            Method method = WorkSource.class.getMethod("get", Integer.TYPE);
            return method;
        }
        catch (Exception exception) {
            return null;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static Method zzst() {
        Method method = null;
        if (!zzne.zzsj()) return method;
        try {
            return WorkSource.class.getMethod("getName", Integer.TYPE);
        }
        catch (Exception exception) {
            return null;
        }
    }
}

