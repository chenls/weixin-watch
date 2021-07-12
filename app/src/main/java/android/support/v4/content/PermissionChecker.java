/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Binder
 *  android.os.Process
 */
package android.support.v4.content;

import android.content.Context;
import android.os.Binder;
import android.os.Process;
import android.support.annotation.NonNull;
import android.support.v4.app.AppOpsManagerCompat;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class PermissionChecker {
    public static final int PERMISSION_DENIED = -1;
    public static final int PERMISSION_DENIED_APP_OP = -2;
    public static final int PERMISSION_GRANTED = 0;

    private PermissionChecker() {
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static int checkCallingOrSelfPermission(@NonNull Context context, @NonNull String string2) {
        String string3;
        if (Binder.getCallingPid() == Process.myPid()) {
            string3 = context.getPackageName();
            return PermissionChecker.checkPermission(context, string2, Binder.getCallingPid(), Binder.getCallingUid(), string3);
        }
        string3 = null;
        return PermissionChecker.checkPermission(context, string2, Binder.getCallingPid(), Binder.getCallingUid(), string3);
    }

    public static int checkCallingPermission(@NonNull Context context, @NonNull String string2, String string3) {
        if (Binder.getCallingPid() == Process.myPid()) {
            return -1;
        }
        return PermissionChecker.checkPermission(context, string2, Binder.getCallingPid(), Binder.getCallingUid(), string3);
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    public static int checkPermission(@NonNull Context context, @NonNull String object, int n2, int n3, String string2) {
        void var1_5;
        String string3;
        block6: {
            String[] stringArray;
            block7: {
                block5: {
                    void var4_8;
                    void var3_7;
                    void var2_6;
                    if (context.checkPermission((String)object, (int)var2_6, (int)var3_7) == -1) break block5;
                    string3 = AppOpsManagerCompat.permissionToOp((String)object);
                    if (string3 == null) {
                        return 0;
                    }
                    void var1_2 = var4_8;
                    if (var4_8 != null) break block6;
                    stringArray = context.getPackageManager().getPackagesForUid((int)var3_7);
                    if (stringArray != null && stringArray.length > 0) break block7;
                }
                return -1;
            }
            String string4 = stringArray[0];
        }
        if (AppOpsManagerCompat.noteProxyOp(context, string3, (String)var1_5) != 0) {
            return -2;
        }
        return 0;
    }

    public static int checkSelfPermission(@NonNull Context context, @NonNull String string2) {
        return PermissionChecker.checkPermission(context, string2, Process.myPid(), Process.myUid(), context.getPackageName());
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface PermissionResult {
    }
}

