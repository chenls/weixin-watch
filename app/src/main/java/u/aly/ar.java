/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 */
package u.aly;

import android.content.Context;
import android.content.SharedPreferences;
import com.umeng.analytics.AnalyticsConfig;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.Method;
import u.aly.ae;
import u.aly.ai;
import u.aly.ap;
import u.aly.at;
import u.aly.av;
import u.aly.bj;
import u.aly.bk;
import u.aly.bl;

public class ar {
    private static final String a = "session_start_time";
    private static final String b = "session_end_time";
    private static final String c = "session_id";
    private static final String f = "activities";
    private static final String g = "uptr";
    private static final String h = "dntr";
    private static String i = null;
    private static Context j = null;
    private final String d;
    private final String e;

    public ar() {
        this.d = "a_start_time";
        this.e = "a_end_time";
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static String a() {
        try {
            if (i == null) {
                i = ap.a(j).getString(c, null);
            }
            return i;
        }
        catch (Exception exception) {
            return i;
        }
    }

    private String a(Context context, SharedPreferences sharedPreferences) {
        ae ae2 = ae.a(context);
        String string2 = this.b(context);
        av.o o2 = this.a(context);
        sharedPreferences = sharedPreferences.edit();
        sharedPreferences.putString(c, string2);
        sharedPreferences.putLong(a, System.currentTimeMillis());
        sharedPreferences.putLong(b, 0L);
        sharedPreferences.putLong("a_start_time", System.currentTimeMillis());
        sharedPreferences.putLong("a_end_time", 0L);
        sharedPreferences.putInt("versioncode", Integer.parseInt(bj.c(context)));
        sharedPreferences.putString("versionname", bj.d(context));
        sharedPreferences.commit();
        if (o2 != null) {
            ae2.a(o2);
            return string2;
        }
        ae2.a((ai)null);
        return string2;
    }

    private void a(SharedPreferences sharedPreferences) {
        sharedPreferences = sharedPreferences.edit();
        sharedPreferences.remove(a);
        sharedPreferences.remove(b);
        sharedPreferences.remove("a_start_time");
        sharedPreferences.remove("a_end_time");
        sharedPreferences.putString(f, "");
        sharedPreferences.commit();
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean b(SharedPreferences sharedPreferences) {
        long l2 = sharedPreferences.getLong("a_start_time", 0L);
        long l3 = sharedPreferences.getLong("a_end_time", 0L);
        long l4 = System.currentTimeMillis();
        if (l2 != 0L && l4 - l2 < AnalyticsConfig.kContinueSessionMillis) {
            bl.e("onResume called before onPause");
            return false;
        } else {
            if (l4 - l3 <= AnalyticsConfig.kContinueSessionMillis) return false;
            return true;
        }
    }

    public static String g(Context context) {
        if (i == null) {
            i = ap.a(context).getString(c, null);
        }
        return i;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public av.o a(Context context) {
        long l2;
        SharedPreferences sharedPreferences = ap.a(context);
        Object object = sharedPreferences.getString(c, null);
        if (object == null) {
            return null;
        }
        long l3 = sharedPreferences.getLong(a, 0L);
        long l4 = sharedPreferences.getLong(b, 0L);
        long l5 = 0L;
        if (l4 != 0L) {
            l5 = l2 = l4 - l3;
            if (Math.abs(l2) > 86400000L) {
                l5 = 0L;
            }
        }
        av.o o2 = new av.o();
        o2.b = object;
        o2.c = l3;
        o2.d = l4;
        o2.e = l5;
        object = AnalyticsConfig.getLocation();
        if (object != null) {
            o2.j.a = (double)object[0];
            o2.j.b = (double)object[1];
            o2.j.c = System.currentTimeMillis();
        }
        try {
            GenericDeclaration genericDeclaration = Class.forName("android.net.TrafficStats");
            object = ((Class)genericDeclaration).getMethod("getUidRxBytes", Integer.TYPE);
            genericDeclaration = ((Class)genericDeclaration).getMethod("getUidTxBytes", Integer.TYPE);
            int n2 = context.getApplicationInfo().uid;
            if (n2 == -1) {
                return null;
            }
            l3 = (Long)((Method)object).invoke(null, n2);
            l5 = (Long)((Method)genericDeclaration).invoke(null, n2);
            if (l3 > 0L && l5 > 0L) {
                l2 = sharedPreferences.getLong(g, -1L);
                l4 = sharedPreferences.getLong(h, -1L);
                sharedPreferences.edit().putLong(g, l5).putLong(h, l3).commit();
                if (l2 > 0L && l4 > 0L && (l3 -= l4) > 0L && (l5 -= l2) > 0L) {
                    o2.i.a = l3;
                    o2.i.b = l5;
                }
            }
        }
        catch (Throwable throwable) {}
        at.a(sharedPreferences, o2);
        this.a(sharedPreferences);
        return o2;
    }

    public String b(Context object) {
        String string2 = bj.f(object);
        object = AnalyticsConfig.getAppkey(object);
        long l2 = System.currentTimeMillis();
        if (object == null) {
            throw new RuntimeException("Appkey is null or empty, Please check AndroidManifest.xml");
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(l2).append((String)object).append(string2);
        i = bk.a(stringBuilder.toString());
        return i;
    }

    public void c(Context object) {
        j = object;
        SharedPreferences sharedPreferences = ap.a(object);
        if (sharedPreferences == null) {
            return;
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        int n2 = sharedPreferences.getInt("versioncode", 0);
        int n3 = Integer.parseInt(bj.c(j));
        if (n2 != 0 && n3 != n2) {
            if (ar.g(object) == null) {
                this.a((Context)object, sharedPreferences);
            }
            this.e(j);
            ae.a(j).c();
            this.f(j);
            return;
        }
        if (this.b(sharedPreferences)) {
            object = this.a((Context)object, sharedPreferences);
            bl.c("Start new session: " + (String)object);
            return;
        }
        object = sharedPreferences.getString(c, null);
        editor.putLong("a_start_time", System.currentTimeMillis());
        editor.putLong("a_end_time", 0L);
        editor.commit();
        bl.c("Extend current session: " + (String)object);
    }

    public void d(Context context) {
        if ((context = ap.a(context)) == null) {
            return;
        }
        if (context.getLong("a_start_time", 0L) == 0L && AnalyticsConfig.ACTIVITY_DURATION_OPEN) {
            bl.e("onPause called before onResume");
            return;
        }
        long l2 = System.currentTimeMillis();
        context = context.edit();
        context.putLong("a_start_time", 0L);
        context.putLong("a_end_time", l2);
        context.putLong(b, l2);
        context.commit();
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean e(Context object) {
        av.o o2;
        boolean bl2;
        Object object2;
        block6: {
            boolean bl3;
            block5: {
                boolean bl4 = false;
                bl3 = false;
                object2 = ap.a(object);
                if (object2 == null || object2.getString(c, null) == null) break block5;
                long l2 = object2.getLong("a_start_time", 0L);
                long l3 = object2.getLong("a_end_time", 0L);
                bl2 = bl4;
                if (l2 > 0L) {
                    bl2 = bl4;
                    if (l3 == 0L) {
                        bl2 = true;
                        this.d((Context)object);
                    }
                }
                object2 = ae.a(object);
                o2 = this.a((Context)object);
                bl3 = bl2;
                if (o2 != null) break block6;
            }
            return bl3;
        }
        ((ae)object2).b(o2);
        return bl2;
    }

    public void f(Context context) {
        SharedPreferences sharedPreferences = ap.a(context);
        if (sharedPreferences == null) {
            return;
        }
        String string2 = this.b(context);
        sharedPreferences = sharedPreferences.edit();
        sharedPreferences.putString(c, string2);
        sharedPreferences.putLong(a, System.currentTimeMillis());
        sharedPreferences.putLong(b, 0L);
        sharedPreferences.putLong("a_start_time", System.currentTimeMillis());
        sharedPreferences.putLong("a_end_time", 0L);
        sharedPreferences.putInt("versioncode", Integer.parseInt(bj.c(context)));
        sharedPreferences.putString("versionname", bj.d(context));
        sharedPreferences.commit();
        bl.c("Restart session: " + string2);
    }
}

