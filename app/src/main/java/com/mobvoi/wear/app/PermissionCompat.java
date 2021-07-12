/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.Fragment
 *  android.content.Context
 *  android.content.pm.PackageManager
 *  android.os.Handler
 *  android.os.Looper
 *  android.os.Process
 */
package com.mobvoi.wear.app;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Looper;
import android.os.Process;
import android.support.annotation.NonNull;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class PermissionCompat {
    public static final String ACTION_REQUEST_PERMISSIONS = "android.content.pm.action.REQUEST_PERMISSIONS";
    public static final String EXTRA_REQUEST_PERMISSIONS_NAMES = "android.content.pm.extra.REQUEST_PERMISSIONS_NAMES";
    public static final String EXTRA_REQUEST_PERMISSIONS_RESULTS = "android.content.pm.extra.REQUEST_PERMISSIONS_RESULTS";
    private static Method a;
    private static Method b;
    private static Method c;
    private static Method d;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private static void a() {
        if (a == null) {
            try {
                a = Activity.class.getMethod("shouldShowRequestPermissionRationale", String.class);
            }
            catch (NoSuchMethodException noSuchMethodException) {
                noSuchMethodException.printStackTrace();
            }
        }
        if (b != null) return;
        try {
            b = Activity.class.getMethod("requestPermissions", String[].class, Integer.TYPE);
            return;
        }
        catch (NoSuchMethodException noSuchMethodException) {
            noSuchMethodException.printStackTrace();
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private static void b() {
        if (c == null) {
            try {
                c = Fragment.class.getMethod("shouldShowRequestPermissionRationale", String.class);
            }
            catch (NoSuchMethodException noSuchMethodException) {
                noSuchMethodException.printStackTrace();
            }
        }
        if (d != null) return;
        try {
            d = Fragment.class.getMethod("requestPermissions", String[].class, Integer.TYPE);
            return;
        }
        catch (NoSuchMethodException noSuchMethodException) {
            noSuchMethodException.printStackTrace();
            return;
        }
    }

    public static int checkSelfPermission(@NonNull Context context, @NonNull String string2) {
        if (string2 == null) {
            throw new IllegalArgumentException("permission is null");
        }
        return context.checkPermission(string2, Process.myPid(), Process.myUid());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void requestPermissions(final @NonNull Activity activity, final @NonNull String[] stringArray, final int n2) {
        PermissionCompat.a();
        if (b != null) {
            try {
                b.invoke(activity, stringArray, n2);
                return;
            }
            catch (IllegalAccessException illegalAccessException) {
                illegalAccessException.printStackTrace();
                return;
            }
            catch (InvocationTargetException invocationTargetException) {
                invocationTargetException.printStackTrace();
                return;
            }
        } else {
            if (!(activity instanceof OnRequestPermissionsResultCallback)) return;
            new Handler(Looper.getMainLooper()).post(new Runnable(){

                @Override
                public void run() {
                    int[] nArray = new int[stringArray.length];
                    PackageManager packageManager = activity.getPackageManager();
                    String string2 = activity.getPackageName();
                    int n22 = stringArray.length;
                    for (int i2 = 0; i2 < n22; ++i2) {
                        nArray[i2] = packageManager.checkPermission(stringArray[i2], string2);
                    }
                    ((OnRequestPermissionsResultCallback)activity).onRequestPermissionsResult(n2, stringArray, nArray);
                }
            });
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void requestPermissions(final @NonNull Fragment fragment, final @NonNull String[] stringArray, final int n2) {
        PermissionCompat.a();
        if (d != null) {
            try {
                d.invoke(fragment, stringArray, n2);
                return;
            }
            catch (IllegalAccessException illegalAccessException) {
                illegalAccessException.printStackTrace();
                return;
            }
            catch (InvocationTargetException invocationTargetException) {
                invocationTargetException.printStackTrace();
                return;
            }
        } else {
            if (!(fragment instanceof OnRequestPermissionsResultCallback)) return;
            new Handler(Looper.getMainLooper()).post(new Runnable(){

                @Override
                public void run() {
                    int[] nArray = new int[stringArray.length];
                    Object object = fragment.getActivity();
                    PackageManager packageManager = object.getPackageManager();
                    object = object.getPackageName();
                    int n22 = stringArray.length;
                    for (int i2 = 0; i2 < n22; ++i2) {
                        nArray[i2] = packageManager.checkPermission(stringArray[i2], (String)object);
                    }
                    ((OnRequestPermissionsResultCallback)fragment).onRequestPermissionsResult(n2, stringArray, nArray);
                }
            });
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static boolean shouldShowRequestPermissionRationale(@NonNull Activity activity, @NonNull String string2) {
        PermissionCompat.a();
        if (a == null) return false;
        try {
            return (Boolean)a.invoke(activity, string2);
        }
        catch (IllegalAccessException illegalAccessException) {
            illegalAccessException.printStackTrace();
            return false;
        }
        catch (InvocationTargetException invocationTargetException) {
            invocationTargetException.printStackTrace();
            return false;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static boolean shouldShowRequestPermissionRationale(@NonNull Fragment fragment, @NonNull String string2) {
        PermissionCompat.b();
        if (c == null) return false;
        try {
            return (Boolean)c.invoke(fragment, string2);
        }
        catch (IllegalAccessException illegalAccessException) {
            illegalAccessException.printStackTrace();
            return false;
        }
        catch (InvocationTargetException invocationTargetException) {
            invocationTargetException.printStackTrace();
            return false;
        }
    }

    public static interface OnRequestPermissionsResultCallback {
        public void onRequestPermissionsResult(int var1, @NonNull String[] var2, @NonNull int[] var3);
    }
}

