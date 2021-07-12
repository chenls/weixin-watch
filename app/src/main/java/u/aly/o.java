/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.database.sqlite.SQLiteDatabase
 */
package u.aly;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import java.util.List;
import java.util.Map;
import u.aly.av;
import u.aly.b;
import u.aly.bl;
import u.aly.f;
import u.aly.i;
import u.aly.k;
import u.aly.q;

public class o {
    private static Context a;

    private o() {
        if (a != null) {
            // empty if block
        }
    }

    public static o a(Context context) {
        a = context;
        return u.aly.o$a.a;
    }

    public Map<String, List<av.e>> a() {
        try {
            Map<String, List<av.e>> map = u.aly.a.b(b.a(a).a());
            return map;
        }
        catch (Exception exception) {
            bl.e("upload agg date error");
            return null;
        }
        finally {
            b.a(a).c();
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public void a(f f2) {
        try {
            SQLiteDatabase sQLiteDatabase = b.a(a).a();
            String string2 = u.aly.a.a(sQLiteDatabase);
            String string3 = q.a(System.currentTimeMillis());
            if (string2.equals("0")) {
                f2.a("faild", false);
                return;
            }
            if (!string2.equals(string3)) {
                u.aly.a.a(sQLiteDatabase, f2);
                return;
            }
            u.aly.a.b(sQLiteDatabase, f2);
            return;
        }
        catch (Exception exception) {
            f2.a(false, false);
            bl.e("load agg data error");
            return;
        }
        finally {
            b.a(a).c();
        }
    }

    public void a(f f2, String string2, long l2, long l3) {
        try {
            u.aly.a.a(b.a(a).b(), string2, l2, l3);
            f2.a("success", false);
            return;
        }
        catch (Exception exception) {
            bl.e("package size to big or envelopeOverflowPackageCount exception");
            return;
        }
        finally {
            b.a(a).c();
        }
    }

    public void a(f f2, List<String> list) {
        try {
            u.aly.a.a(f2, b.a(a).b(), list);
            return;
        }
        catch (Exception exception) {
            bl.e("saveToLimitCKTable exception");
            return;
        }
        finally {
            b.a(a).c();
        }
    }

    public void a(f f2, Map<List<String>, i> map) {
        try {
            u.aly.a.a(b.a(a).b(), map.values());
            f2.a("success", false);
            return;
        }
        catch (Exception exception) {
            bl.e("save agg data error");
            return;
        }
        finally {
            b.a(a).c();
        }
    }

    public void a(f f2, boolean bl2) {
        try {
            u.aly.a.a(b.a(a).b(), bl2, f2);
            return;
        }
        catch (Exception exception) {
            bl.e("notifyUploadSuccess error");
            return;
        }
        finally {
            b.a(a).c();
        }
    }

    public List<String> b() {
        try {
            List<String> list = u.aly.a.c(b.a(a).a());
            return list;
        }
        catch (Exception exception) {
            bl.e("loadCKToMemory exception");
            return null;
        }
        finally {
            b.a(a).c();
        }
    }

    public Map<String, List<av.f>> b(f object) {
        try {
            object = u.aly.a.a((f)object, b.a(a).a());
            return object;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
        finally {
            b.a(a).c();
        }
    }

    public void b(f f2, Map<String, k> map) {
        try {
            u.aly.a.a(b.a(a).b(), map, f2);
            return;
        }
        catch (Exception exception) {
            bl.e("arrgetated system buffer exception");
            return;
        }
        finally {
            b.a(a).c();
        }
    }

    public void c(f f2, Map<List<String>, i> map) {
        try {
            u.aly.a.a(f2, b.a(a).b(), map.values());
            return;
        }
        catch (Exception exception) {
            bl.e("cacheToData error");
            return;
        }
        finally {
            b.a(a).c();
        }
    }

    private static final class a {
        private static final o a = new o();

        private a() {
        }
    }
}

