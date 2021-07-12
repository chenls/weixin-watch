/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.os.Build
 *  android.util.Log
 */
package mobvoiapi;

import android.os.Build;
import android.util.Log;
import java.util.Locale;
import mobvoiapi.af;

public class ag {
    private static boolean a = false;

    public static void a(int n2, String string2, String string3, Throwable throwable, Object ... objectArray) {
        if (a) {
            String string4 = string3;
            if (objectArray != null) {
                string4 = string3;
                if (objectArray.length > 0) {
                    string4 = String.format(Locale.US, string3, objectArray);
                }
            }
            System.out.println("[" + string2 + "] " + string4);
            if (throwable != null) {
                throwable.printStackTrace();
            }
            return;
        }
        mobvoiapi.ag$a.a(n2, string2, string3, throwable, objectArray);
    }

    public static void a(String string2, String string3) {
        ag.a(3, string2, string3, null, new Object[0]);
    }

    public static void a(String string2, String string3, Throwable throwable) {
        ag.a(5, string2, string3, throwable, new Object[0]);
    }

    public static void a(String string2, String string3, Object ... objectArray) {
        ag.a(5, string2, string3, null, objectArray);
    }

    public static void b(String string2, String string3) {
        ag.a(4, string2, string3, null, new Object[0]);
    }

    public static void b(String string2, String string3, Throwable throwable) {
        ag.a(6, string2, string3, throwable, new Object[0]);
    }

    public static void c(String string2, String string3) {
        ag.a(5, string2, string3, null, new Object[0]);
    }

    public static void d(String string2, String string3) {
        ag.a(6, string2, string3, null, new Object[0]);
    }

    static class a {
        private static boolean a;
        private static af b;

        /*
         * Enabled aggressive block sorting
         */
        static {
            boolean bl2 = !Build.TYPE.equals("user");
            a = bl2;
        }

        /*
         * Enabled aggressive block sorting
         */
        private static void a(int n2, String string2, String string3, Throwable throwable) {
            if (throwable == null) {
                Log.println((int)n2, (String)string2, (String)string3);
            } else {
                Log.println((int)n2, (String)string2, (String)(string3 + '\n' + Log.getStackTraceString((Throwable)throwable)));
            }
            mobvoiapi.ag$a.a(string2, string3, throwable);
        }

        public static void a(int n2, String string2, String string3, Throwable throwable, Object ... objectArray) {
            if (mobvoiapi.ag$a.a(n2, string2)) {
                String string4 = string3;
                if (objectArray != null) {
                    string4 = string3;
                    if (objectArray.length > 0) {
                        string4 = String.format(Locale.US, string3, objectArray);
                    }
                }
                mobvoiapi.ag$a.a(n2, string2, string4, throwable);
            }
        }

        private static void a(String string2, String string3, Throwable throwable) {
            if (a && b != null) {
                b.a(string2, string3, throwable);
            }
        }

        private static boolean a(int n2, String string2) {
            return mobvoiapi.ag$a.a(string2, n2) || a;
        }

        private static boolean a(String string2, int n2) {
            try {
                boolean bl2 = Log.isLoggable((String)string2, (int)n2);
                return bl2;
            }
            catch (Exception exception) {
                if (a) {
                    throw exception;
                }
                Log.e((String)string2, (String)"Can't detect is loggable.", (Throwable)exception);
                return false;
            }
        }
    }
}

