/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.os.IBinder
 *  android.util.Log
 */
package android.support.v4.app;

import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class BundleCompatGingerbread {
    private static final String TAG = "BundleCompatGingerbread";
    private static Method sGetIBinderMethod;
    private static boolean sGetIBinderMethodFetched;
    private static Method sPutIBinderMethod;
    private static boolean sPutIBinderMethodFetched;

    BundleCompatGingerbread() {
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static IBinder getBinder(Bundle var0, String var1_5) {
        if (!BundleCompatGingerbread.sGetIBinderMethodFetched) {
            try {
                BundleCompatGingerbread.sGetIBinderMethod = Bundle.class.getMethod("getIBinder", new Class[]{String.class});
                BundleCompatGingerbread.sGetIBinderMethod.setAccessible(true);
            }
            catch (NoSuchMethodException var2_6) {
                Log.i((String)"BundleCompatGingerbread", (String)"Failed to retrieve getIBinder method", (Throwable)var2_6);
            }
            BundleCompatGingerbread.sGetIBinderMethodFetched = true;
        }
        if (BundleCompatGingerbread.sGetIBinderMethod == null) return null;
        try {
            return (IBinder)BundleCompatGingerbread.sGetIBinderMethod.invoke(var0, new Object[]{var1_5});
        }
        catch (IllegalAccessException var0_1) {}
        ** GOTO lbl-1000
        catch (IllegalArgumentException var0_3) {
            ** GOTO lbl-1000
        }
        catch (InvocationTargetException var0_4) {}
lbl-1000:
        // 3 sources

        {
            Log.i((String)"BundleCompatGingerbread", (String)"Failed to invoke getIBinder via reflection", (Throwable)var0_2);
            BundleCompatGingerbread.sGetIBinderMethod = null;
        }
        return null;
    }

    /*
     * Unable to fully structure code
     */
    public static void putBinder(Bundle var0, String var1_5, IBinder var2_6) {
        if (BundleCompatGingerbread.sPutIBinderMethodFetched) ** GOTO lbl-1000
        try {
            BundleCompatGingerbread.sPutIBinderMethod = Bundle.class.getMethod("putIBinder", new Class[]{String.class, IBinder.class});
            BundleCompatGingerbread.sPutIBinderMethod.setAccessible(true);
lbl5:
            // 2 sources

            while (true) {
                BundleCompatGingerbread.sPutIBinderMethodFetched = true;
                break;
            }
        }
        catch (NoSuchMethodException var3_7) {
            Log.i((String)"BundleCompatGingerbread", (String)"Failed to retrieve putIBinder method", (Throwable)var3_7);
            ** continue;
        }
lbl-1000:
        // 2 sources

        {
            if (BundleCompatGingerbread.sPutIBinderMethod == null) ** GOTO lbl15
            BundleCompatGingerbread.sPutIBinderMethod.invoke(var0, new Object[]{var1_5, var2_6});
lbl15:
            // 2 sources

            return;
        }
        catch (IllegalAccessException var0_1) lbl-1000:
        // 3 sources

        {
            while (true) {
                Log.i((String)"BundleCompatGingerbread", (String)"Failed to invoke putIBinder via reflection", (Throwable)var0_2);
                BundleCompatGingerbread.sPutIBinderMethod = null;
                return;
            }
        }
        catch (IllegalArgumentException var0_3) {
            ** GOTO lbl-1000
        }
        catch (InvocationTargetException var0_4) {
            ** continue;
        }
    }
}

