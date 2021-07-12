/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.text.TextUtils
 */
package u.aly;

import android.content.Context;
import android.text.TextUtils;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import u.aly.aa;
import u.aly.ab;
import u.aly.ac;
import u.aly.ap;
import u.aly.ba;
import u.aly.bb;
import u.aly.bc;
import u.aly.bk;
import u.aly.bp;
import u.aly.bs;
import u.aly.by;
import u.aly.r;
import u.aly.s;
import u.aly.u;
import u.aly.w;
import u.aly.y;
import u.aly.z;

public class v {
    public static v a;
    private final String b;
    private File c;
    private bc d = null;
    private long e;
    private long f;
    private Set<r> g = new HashSet<r>();
    private a h = null;

    v(Context context) {
        this.b = "umeng_it.cache";
        this.c = new File(context.getFilesDir(), "umeng_it.cache");
        this.f = 86400000L;
        this.h = new a(context);
        this.h.b();
    }

    public static v a(Context object) {
        synchronized (v.class) {
            if (a == null) {
                a = new v((Context)object);
                a.a(new w((Context)object));
                a.a(new s((Context)object));
                a.a(new ab((Context)object));
                a.a(new aa((Context)object));
                a.a(new u((Context)object));
                a.a(new y((Context)object));
                a.a(new z());
                a.a(new ac((Context)object));
                a.e();
            }
            object = a;
            return object;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private void a(bc object) {
        if (object == null) return;
        object = new by().a((bp)object);
        // MONITOREXIT : this
        if (object == null) return;
        try {
            bk.a(this.c, (byte[])object);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void g() {
        bc bc2 = new bc();
        HashMap<String, bb> hashMap = new HashMap<String, bb>();
        ArrayList<ba> arrayList = new ArrayList<ba>();
        Iterator<r> iterator = this.g.iterator();
        while (true) {
            if (!iterator.hasNext()) {
                bc2.a(arrayList);
                bc2.a(hashMap);
                synchronized (this) {
                    this.d = bc2;
                    return;
                }
            }
            r r2 = iterator.next();
            if (!r2.c()) continue;
            if (r2.d() != null) {
                hashMap.put(r2.b(), r2.d());
            }
            if (r2.e() == null || r2.e().isEmpty()) continue;
            arrayList.addAll(r2.e());
        }
    }

    /*
     * Unable to fully structure code
     */
    private bc h() {
        if (!this.c.exists()) {
            return null;
        }
        var1_4 = var2_1 = new FileInputStream(this.c);
        var3_6 = bk.b(var2_1);
        var1_4 = var2_1;
        var4_9 = new bc();
        var1_4 = var2_1;
        try {
            new bs().a((bp)var4_9, (byte[])var3_6);
        }
        catch (Exception var3_8) {
            ** continue;
        }
        bk.c(var2_1);
        return var4_9;
        catch (Exception var3_7) {
            var2_1 = null;
lbl17:
            // 2 sources

            while (true) {
                var1_4 = var2_1;
                var3_6.printStackTrace();
                bk.c(var2_1);
                return null;
            }
        }
        catch (Throwable var1_5) {
            var3_6 = null;
            var2_2 = var1_5;
lbl26:
            // 2 sources

            while (true) {
                bk.c(var3_6);
                throw var2_2;
            }
        }
        {
            catch (Throwable var2_3) {
                var3_6 = var1_4;
                ** continue;
            }
        }
    }

    public void a() {
        long l2 = System.currentTimeMillis();
        if (l2 - this.e >= this.f) {
            Iterator<r> iterator = this.g.iterator();
            boolean bl2 = false;
            while (iterator.hasNext()) {
                boolean bl3;
                r r2 = iterator.next();
                if (!r2.c() || !r2.a()) continue;
                bl2 = bl3 = true;
                if (r2.c()) continue;
                this.h.b(r2.b());
                bl2 = bl3;
            }
            if (bl2) {
                this.g();
                this.h.a();
                this.f();
            }
            this.e = l2;
        }
    }

    public void a(long l2) {
        this.f = l2;
    }

    public boolean a(r r2) {
        if (this.h.a(r2.b())) {
            return this.g.add(r2);
        }
        return false;
    }

    public bc b() {
        return this.d;
    }

    public String c() {
        return null;
    }

    public void d() {
        Iterator<r> iterator = this.g.iterator();
        boolean bl2 = false;
        while (iterator.hasNext()) {
            r r2 = iterator.next();
            if (!r2.c() || r2.e() == null || r2.e().isEmpty()) continue;
            r2.a((List<ba>)null);
            bl2 = true;
        }
        if (bl2) {
            this.d.b(false);
            this.f();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void e() {
        Object object = this.h();
        if (object == null) {
            return;
        }
        Object object2 = new ArrayList(this.g.size());
        synchronized (this) {
            this.d = object;
            for (r r2 : this.g) {
                r2.a(this.d);
                if (r2.c()) continue;
                object2.add(r2);
            }
            object2 = object2.iterator();
            while (true) {
                if (!object2.hasNext()) {
                    // MONITOREXIT @DISABLED, blocks:[2, 4, 5] lbl18 : MonitorExitStatement: MONITOREXIT : this
                    this.g();
                    return;
                }
                object = (r)object2.next();
                this.g.remove(object);
            }
        }
    }

    public void f() {
        if (this.d != null) {
            this.a(this.d);
        }
    }

    public static class a {
        private Context a;
        private Set<String> b = new HashSet<String>();

        public a(Context context) {
            this.a = context;
        }

        public void a() {
            if (!this.b.isEmpty()) {
                StringBuilder stringBuilder = new StringBuilder();
                Iterator<String> iterator = this.b.iterator();
                while (iterator.hasNext()) {
                    stringBuilder.append(iterator.next());
                    stringBuilder.append(',');
                }
                stringBuilder.deleteCharAt(stringBuilder.length() - 1);
                ap.a(this.a).edit().putString("invld_id", stringBuilder.toString()).commit();
            }
        }

        public boolean a(String string2) {
            return !this.b.contains(string2);
        }

        public void b() {
            String[] stringArray = ap.a(this.a).getString("invld_id", null);
            if (!TextUtils.isEmpty((CharSequence)stringArray) && (stringArray = stringArray.split(",")) != null) {
                for (String string2 : stringArray) {
                    if (TextUtils.isEmpty((CharSequence)string2)) continue;
                    this.b.add(string2);
                }
            }
        }

        public void b(String string2) {
            this.b.add(string2);
        }

        public void c(String string2) {
            this.b.remove(string2);
        }
    }
}

