/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.app.AppOpsManager
 *  android.content.Context
 *  android.content.pm.ApplicationInfo
 */
package android.support.v4.app;

import android.app.AppOpsManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import java.lang.reflect.InvocationTargetException;

class NotificationManagerCompatKitKat {
    private static final String CHECK_OP_NO_THROW = "checkOpNoThrow";
    private static final String OP_POST_NOTIFICATION = "OP_POST_NOTIFICATION";

    NotificationManagerCompatKitKat() {
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static boolean areNotificationsEnabled(Context object) {
        AppOpsManager appOpsManager = (AppOpsManager)object.getSystemService("appops");
        Object object2 = object.getApplicationInfo();
        String string2 = object.getApplicationContext().getPackageName();
        int n2 = ((ApplicationInfo)object2).uid;
        try {
            object2 = Class.forName(AppOpsManager.class.getName());
            n2 = (Integer)((Class)object2).getMethod(CHECK_OP_NO_THROW, Integer.TYPE, Integer.TYPE, String.class).invoke(appOpsManager, (int)((Integer)((Class)object2).getDeclaredField(OP_POST_NOTIFICATION).get(Integer.class)), n2, string2);
            return n2 == 0;
        }
        catch (ClassNotFoundException classNotFoundException) {
            return true;
        }
        catch (RuntimeException runtimeException) {
            return true;
        }
        catch (NoSuchFieldException noSuchFieldException) {
            return true;
        }
        catch (IllegalAccessException illegalAccessException) {
            return true;
        }
        catch (NoSuchMethodException noSuchMethodException) {
            return true;
        }
        catch (InvocationTargetException invocationTargetException) {
            return true;
        }
    }
}

