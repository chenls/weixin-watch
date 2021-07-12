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
import com.umeng.analytics.ReportPolicy;
import com.umeng.analytics.b;
import java.io.File;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import u.aly.ao;
import u.aly.aw;
import u.aly.bd;
import u.aly.be;
import u.aly.bk;
import u.aly.bp;
import u.aly.bs;
import u.aly.by;

public class x {
    private static final String a = ".imprint";
    private static final byte[] b = "pbl0".getBytes();
    private static x f;
    private ao c;
    private a d = new a();
    private bd e = null;
    private Context g;

    x(Context context) {
        this.g = context;
    }

    private bd a(bd bd2, bd bd3) {
        if (bd3 == null) {
            return bd2;
        }
        Map<String, be> map = bd2.d();
        for (Map.Entry<String, be> entry : bd3.d().entrySet()) {
            if (entry.getValue().e()) {
                map.put(entry.getKey(), entry.getValue());
                continue;
            }
            map.remove(entry.getKey());
        }
        bd2.a(bd3.g());
        bd2.a(this.a(bd2));
        return bd2;
    }

    public static x a(Context object) {
        synchronized (x.class) {
            if (f == null) {
                f = new x((Context)object);
                f.c();
            }
            object = f;
            return object;
        }
    }

    private boolean a(String string2, String string3) {
        if (string2 == null) {
            return string3 == null;
        }
        return string2.equals(string3);
    }

    private boolean c(bd object) {
        if (!((bd)((Object)object)).j().equals(this.a((bd)((Object)object)))) {
            return false;
        }
        for (be be2 : ((bd)((Object)object)).d().values()) {
            byte[] byArray = com.umeng.analytics.b.a(be2.i());
            byte[] object2 = this.a(be2);
            for (int i2 = 0; i2 < 4; ++i2) {
                if (byArray[i2] == object2[i2]) continue;
                return false;
            }
        }
        return true;
    }

    private bd d(bd bd2) {
        Map<String, be> map = bd2.d();
        Object object = new ArrayList(map.size() / 2);
        for (Map.Entry<String, be> entry : map.entrySet()) {
            if (entry.getValue().e()) continue;
            object.add(entry.getKey());
        }
        object = object.iterator();
        while (object.hasNext()) {
            map.remove((String)object.next());
        }
        return bd2;
    }

    public String a(bd bd2) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry entry : new TreeMap<String, be>(bd2.d()).entrySet()) {
            stringBuilder.append((String)entry.getKey());
            if (((be)entry.getValue()).e()) {
                stringBuilder.append(((be)entry.getValue()).c());
            }
            stringBuilder.append(((be)entry.getValue()).f());
            stringBuilder.append(((be)entry.getValue()).i());
        }
        stringBuilder.append(bd2.b);
        return bk.a(stringBuilder.toString()).toLowerCase(Locale.US);
    }

    public bd a() {
        synchronized (this) {
            bd bd2 = this.e;
            return bd2;
        }
    }

    public void a(ao ao2) {
        this.c = ao2;
    }

    public byte[] a(be object) {
        Object object2 = ByteBuffer.allocate(8);
        ((ByteBuffer)object2).order(null);
        ((ByteBuffer)object2).putLong(((be)object).f());
        object = ((ByteBuffer)object2).array();
        object2 = b;
        byte[] byArray = new byte[4];
        for (int i2 = 0; i2 < 4; ++i2) {
            byArray[i2] = (byte)(object[i2] ^ object2[i2]);
        }
        return byArray;
    }

    public a b() {
        return this.d;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void b(bd object) {
        block8: {
            block7: {
                Object var4_2 = null;
                if (object == null || !this.c((bd)object)) break block7;
                boolean bl2 = false;
                synchronized (this) {
                    bd bd2 = this.e;
                    String string2 = bd2 == null ? null : bd2.j();
                    object = bd2 == null ? this.d((bd)object) : this.a(bd2, (bd)object);
                    this.e = object;
                    object = object == null ? var4_2 : ((bd)object).j();
                    if (!this.a(string2, (String)object)) {
                        bl2 = true;
                    }
                    if (this.e == null || !bl2) break block7;
                }
                this.d.a(this.e);
                if (this.c != null) break block8;
            }
            return;
        }
        this.c.a(this.d);
    }

    /*
     * Unable to fully structure code
     */
    public void c() {
        var2_1 = null;
        var4_2 = null;
        if (!new File(this.g.getFilesDir(), ".imprint").exists()) {
            return;
        }
        var2_1 = var1_3 = this.g.openFileInput(".imprint");
        try {
            var3_8 = bk.b((InputStream)var1_3);
        }
        catch (Exception var3_11) {
            ** continue;
        }
        var2_1 = var3_8;
        bk.c((InputStream)var1_3);
lbl12:
        // 2 sources

        while (true) {
            if (var2_1 == null) ** continue;
            try {
                var1_3 = new bd();
                new bs().a((bp)var1_3, (byte[])var2_1);
                this.e = var1_3;
                this.d.a((bd)var1_3);
                return;
            }
            catch (Exception var1_4) {
                var1_4.printStackTrace();
                return;
            }
            break;
        }
        catch (Exception var3_9) {
            var1_3 = null;
lbl25:
            // 2 sources

            while (true) {
                var2_1 = var1_3;
                var3_10.printStackTrace();
                bk.c((InputStream)var1_3);
                var2_1 = var4_2;
                ** continue;
                break;
            }
        }
        catch (Throwable var1_5) lbl-1000:
        // 2 sources

        {
            while (true) {
                bk.c(var2_1);
                throw var1_6;
            }
        }
        {
            catch (Throwable var1_7) {
                ** continue;
            }
        }
    }

    public void d() {
        if (this.e == null) {
            return;
        }
        try {
            byte[] byArray = new by().a(this.e);
            bk.a(new File(this.g.getFilesDir(), a), byArray);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return;
        }
    }

    public boolean e() {
        return new File(this.g.getFilesDir(), a).delete();
    }

    public static class a {
        private int a = -1;
        private int b = -1;
        private int c = -1;
        private int d = -1;
        private int e = -1;
        private String f = null;
        private int g = -1;
        private String h = null;
        private int i = -1;
        private int j = -1;
        private String k = null;
        private String l = null;
        private String m = null;

        a() {
        }

        a(bd bd2) {
            this.a(bd2);
        }

        private int a(bd bp2, String string2) {
            if (bp2 == null || !((bd)bp2).f()) {
                return -1;
            }
            if ((bp2 = ((bd)bp2).d().get(string2)) == null || TextUtils.isEmpty((CharSequence)((be)bp2).c())) {
                return -1;
            }
            try {
                int n2 = Integer.parseInt(((be)bp2).c().trim());
                return n2;
            }
            catch (Exception exception) {
                return -1;
            }
        }

        private String b(bd bp2, String string2) {
            if (bp2 == null || !((bd)bp2).f()) {
                return null;
            }
            if ((bp2 = ((bd)bp2).d().get(string2)) == null || TextUtils.isEmpty((CharSequence)((be)bp2).c())) {
                return null;
            }
            return ((be)bp2).c();
        }

        /*
         * Enabled aggressive block sorting
         */
        public int a(int n2) {
            if (this.a == -1 || this.a > 3 || this.a < 0) {
                return n2;
            }
            return this.a;
        }

        /*
         * Enabled aggressive block sorting
         */
        public long a(long l2) {
            if (this.j == -1 || this.j < 48) {
                return l2;
            }
            return 3600000L * (long)this.j;
        }

        public String a(String string2) {
            if (this.m != null) {
                string2 = this.m;
            }
            return string2;
        }

        public void a(bd bd2) {
            if (bd2 == null) {
                return;
            }
            this.a = this.a(bd2, "defcon");
            this.b = this.a(bd2, "latent");
            this.c = this.a(bd2, "codex");
            this.d = this.a(bd2, "report_policy");
            this.e = this.a(bd2, "report_interval");
            this.f = this.b(bd2, "client_test");
            this.g = this.a(bd2, "test_report_interval");
            this.h = this.b(bd2, "umid");
            this.i = this.a(bd2, "integrated_test");
            this.j = this.a(bd2, "latent_hours");
            this.k = this.b(bd2, "country");
            this.l = this.b(bd2, "domain_p");
            this.m = this.b(bd2, "domain_s");
        }

        public boolean a() {
            return this.g != -1;
        }

        public int[] a(int n2, int n3) {
            if (this.d == -1 || !ReportPolicy.a(this.d)) {
                return new int[]{n2, n3};
            }
            if (this.e == -1 || this.e < 90 || this.e > 86400) {
                this.e = 90;
            }
            return new int[]{this.d, this.e * 1000};
        }

        /*
         * Enabled aggressive block sorting
         */
        public int b(int n2) {
            if (this.b == -1 || this.b < 0 || this.b > 1800) {
                return n2;
            }
            return this.b * 1000;
        }

        public String b(String string2) {
            if (this.l != null) {
                string2 = this.l;
            }
            return string2;
        }

        public boolean b() {
            return this.i == 1;
        }

        public int c(int n2) {
            if (this.c == 0 || this.c == 1 || this.c == -1) {
                n2 = this.c;
            }
            return n2;
        }

        public String c(String string2) {
            if (this.k != null) {
                string2 = this.k;
            }
            return string2;
        }

        public int d(int n2) {
            if (this.g == -1 || this.g < 90 || this.g > 86400) {
                return n2;
            }
            return this.g * 1000;
        }

        public String d(String string2) {
            if (this.f == null || !aw.a(this.f)) {
                return string2;
            }
            return this.f;
        }

        public String e(String string2) {
            return this.h;
        }
    }
}

