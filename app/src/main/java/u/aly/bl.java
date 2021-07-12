/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.util.Log
 */
package u.aly;

import android.util.Log;
import java.io.Serializable;
import java.util.Formatter;
import java.util.Locale;

public class bl {
    public static boolean a = false;
    private static String b = "MobclickAgent";

    private bl() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static void a(String string2) {
        bl.a(b, string2, null);
    }

    /*
     * Could not resolve type clashes
     * Unable to fully structure code
     */
    public static void a(String var0, String var1_2, Throwable var2_3) {
        block8: {
            block7: {
                if (!bl.a) ** GOTO lbl28
                if (var2_3 != null) break block7;
                if (var1_2 != null) ** GOTO lbl8
                Log.w((String)var0, (String)"the msg is null!");
                return;
lbl8:
                // 1 sources

                Log.v((String)var0, (String)var1_2);
                return;
            }
            if (var1_2 == null) break block8;
            Log.v((String)var0, (String)(var2_3.toString() + ":  [" + (String)var1_2 + "]"));
lbl16:
            // 2 sources

            ** do 
lbl-1000:
            // 2 sources

            {
                for (Serializable var2_3 : var2_3.getStackTrace()) {
                    Log.v((String)var0, (String)("        at  " + var2_3.toString()));
                }
                ** GOTO lbl28
            }
        }
        try {
            Log.v((String)var0, (String)var2_3.toString());
            ** continue;
lbl28:
            // 2 sources

            return;
        }
        catch (Throwable var0_1) {
            return;
        }
    }

    public static void a(String string2, Throwable throwable) {
        bl.c(b, string2, throwable);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void a(String string2, Object ... objectArray) {
        String string3 = "";
        try {
            if (string2.contains("%")) {
                string2 = new Formatter().format(string2, objectArray).toString();
                bl.c(b, string2, null);
                return;
            }
            if (objectArray != null) {
                string3 = (String)objectArray[0];
            }
            bl.c(string2, string3, null);
            return;
        }
        catch (Throwable throwable) {
            bl.e(throwable);
            return;
        }
    }

    public static void a(Throwable throwable) {
        bl.c(b, null, throwable);
    }

    public static void a(Locale object, String string2, Object ... objectArray) {
        try {
            object = new Formatter((Locale)object).format(string2, objectArray).toString();
            bl.c(b, (String)object, null);
            return;
        }
        catch (Throwable throwable) {
            bl.e(throwable);
            return;
        }
    }

    public static void b(String string2) {
        bl.b(b, string2, null);
    }

    /*
     * Could not resolve type clashes
     * Unable to fully structure code
     */
    public static void b(String var0, String var1_2, Throwable var2_3) {
        block8: {
            block7: {
                if (!bl.a) ** GOTO lbl28
                if (var2_3 != null) break block7;
                if (var1_2 != null) ** GOTO lbl8
                Log.w((String)var0, (String)"the msg is null!");
                return;
lbl8:
                // 1 sources

                Log.d((String)var0, (String)var1_2);
                return;
            }
            if (var1_2 == null) break block8;
            Log.d((String)var0, (String)(var2_3.toString() + ":  [" + (String)var1_2 + "]"));
lbl16:
            // 2 sources

            ** do 
lbl-1000:
            // 2 sources

            {
                for (Serializable var2_3 : var2_3.getStackTrace()) {
                    Log.d((String)var0, (String)("        at  " + var2_3.toString()));
                }
                ** GOTO lbl28
            }
        }
        try {
            Log.d((String)var0, (String)var2_3.toString());
            ** continue;
lbl28:
            // 2 sources

            return;
        }
        catch (Throwable var0_1) {
            return;
        }
    }

    public static void b(String string2, Throwable throwable) {
        bl.a(b, string2, throwable);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void b(String string2, Object ... objectArray) {
        String string3 = "";
        try {
            if (string2.contains("%")) {
                string2 = new Formatter().format(string2, objectArray).toString();
                bl.b(b, string2, null);
                return;
            }
            if (objectArray != null) {
                string3 = (String)objectArray[0];
            }
            bl.b(string2, string3, null);
            return;
        }
        catch (Throwable throwable) {
            bl.e(throwable);
            return;
        }
    }

    public static void b(Throwable throwable) {
        bl.a(b, null, throwable);
    }

    public static void b(Locale object, String string2, Object ... objectArray) {
        try {
            object = new Formatter((Locale)object).format(string2, objectArray).toString();
            bl.b(b, (String)object, null);
            return;
        }
        catch (Throwable throwable) {
            bl.e(throwable);
            return;
        }
    }

    public static void c(String string2) {
        bl.c(b, string2, null);
    }

    /*
     * Could not resolve type clashes
     * Unable to fully structure code
     */
    public static void c(String var0, String var1_2, Throwable var2_3) {
        block8: {
            block7: {
                if (!bl.a) ** GOTO lbl28
                if (var2_3 != null) break block7;
                if (var1_2 != null) ** GOTO lbl8
                Log.w((String)var0, (String)"the msg is null!");
                return;
lbl8:
                // 1 sources

                Log.i((String)var0, (String)var1_2);
                return;
            }
            if (var1_2 == null) break block8;
            Log.i((String)var0, (String)(var2_3.toString() + ":  [" + (String)var1_2 + "]"));
lbl16:
            // 2 sources

            ** do 
lbl-1000:
            // 2 sources

            {
                for (Serializable var2_3 : var2_3.getStackTrace()) {
                    Log.i((String)var0, (String)("        at  " + var2_3.toString()));
                }
                ** GOTO lbl28
            }
        }
        try {
            Log.i((String)var0, (String)var2_3.toString());
            ** continue;
lbl28:
            // 2 sources

            return;
        }
        catch (Throwable var0_1) {
            return;
        }
    }

    public static void c(String string2, Throwable throwable) {
        bl.d(b, string2, throwable);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void c(String string2, Object ... objectArray) {
        String string3 = "";
        try {
            if (string2.contains("%")) {
                string2 = new Formatter().format(string2, objectArray).toString();
                bl.e(b, string2, null);
                return;
            }
            if (objectArray != null) {
                string3 = (String)objectArray[0];
            }
            bl.e(string2, string3, null);
            return;
        }
        catch (Throwable throwable) {
            bl.e(throwable);
            return;
        }
    }

    public static void c(Throwable throwable) {
        bl.d(b, null, throwable);
    }

    public static void c(Locale object, String string2, Object ... objectArray) {
        try {
            object = new Formatter((Locale)object).format(string2, objectArray).toString();
            bl.e(b, (String)object, null);
            return;
        }
        catch (Throwable throwable) {
            bl.e(throwable);
            return;
        }
    }

    public static void d(String string2) {
        bl.d(b, string2, null);
    }

    /*
     * Could not resolve type clashes
     * Unable to fully structure code
     */
    public static void d(String var0, String var1_2, Throwable var2_3) {
        block8: {
            block7: {
                if (!bl.a) ** GOTO lbl28
                if (var2_3 != null) break block7;
                if (var1_2 != null) ** GOTO lbl8
                Log.w((String)var0, (String)"the msg is null!");
                return;
lbl8:
                // 1 sources

                Log.w((String)var0, (String)var1_2);
                return;
            }
            if (var1_2 == null) break block8;
            Log.w((String)var0, (String)(var2_3.toString() + ":  [" + (String)var1_2 + "]"));
lbl16:
            // 2 sources

            ** do 
lbl-1000:
            // 2 sources

            {
                for (Serializable var2_3 : var2_3.getStackTrace()) {
                    Log.w((String)var0, (String)("        at  " + var2_3.toString()));
                }
                ** GOTO lbl28
            }
        }
        try {
            Log.w((String)var0, (String)var2_3.toString());
            ** continue;
lbl28:
            // 2 sources

            return;
        }
        catch (Throwable var0_1) {
            return;
        }
    }

    public static void d(String string2, Throwable throwable) {
        bl.b(b, string2, throwable);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void d(String string2, Object ... objectArray) {
        String string3 = "";
        try {
            if (string2.contains("%")) {
                string2 = new Formatter().format(string2, objectArray).toString();
                bl.a(b, string2, null);
                return;
            }
            if (objectArray != null) {
                string3 = (String)objectArray[0];
            }
            bl.a(string2, string3, null);
            return;
        }
        catch (Throwable throwable) {
            bl.e(throwable);
            return;
        }
    }

    public static void d(Throwable throwable) {
        bl.b(b, null, throwable);
    }

    public static void d(Locale object, String string2, Object ... objectArray) {
        try {
            object = new Formatter((Locale)object).format(string2, objectArray).toString();
            bl.a(b, (String)object, null);
            return;
        }
        catch (Throwable throwable) {
            bl.e(throwable);
            return;
        }
    }

    public static void e(String string2) {
        bl.e(b, string2, null);
    }

    /*
     * Could not resolve type clashes
     * Unable to fully structure code
     */
    public static void e(String var0, String var1_2, Throwable var2_3) {
        block8: {
            block7: {
                if (!bl.a) ** GOTO lbl28
                if (var2_3 != null) break block7;
                if (var1_2 != null) ** GOTO lbl8
                Log.w((String)var0, (String)"the msg is null!");
                return;
lbl8:
                // 1 sources

                Log.e((String)var0, (String)var1_2);
                return;
            }
            if (var1_2 == null) break block8;
            Log.e((String)var0, (String)(var2_3.toString() + ":  [" + (String)var1_2 + "]"));
lbl16:
            // 2 sources

            ** do 
lbl-1000:
            // 2 sources

            {
                for (Serializable var2_3 : var2_3.getStackTrace()) {
                    Log.e((String)var0, (String)("        at  " + var2_3.toString()));
                }
                ** GOTO lbl28
            }
        }
        try {
            Log.e((String)var0, (String)var2_3.toString());
            ** continue;
lbl28:
            // 2 sources

            return;
        }
        catch (Throwable var0_1) {
            return;
        }
    }

    public static void e(String string2, Throwable throwable) {
        bl.e(b, string2, throwable);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void e(String string2, Object ... objectArray) {
        String string3 = "";
        try {
            if (string2.contains("%")) {
                string2 = new Formatter().format(string2, objectArray).toString();
                bl.d(b, string2, null);
                return;
            }
            if (objectArray != null) {
                string3 = (String)objectArray[0];
            }
            bl.d(string2, string3, null);
            return;
        }
        catch (Throwable throwable) {
            bl.e(throwable);
            return;
        }
    }

    public static void e(Throwable throwable) {
        bl.e(b, null, throwable);
    }

    public static void e(Locale object, String string2, Object ... objectArray) {
        try {
            object = new Formatter((Locale)object).format(string2, objectArray).toString();
            bl.d(b, (String)object, null);
            return;
        }
        catch (Throwable throwable) {
            bl.e(throwable);
            return;
        }
    }
}

