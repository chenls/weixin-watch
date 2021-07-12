/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.text.TextUtils
 */
package u.aly;

import android.os.Build;
import android.text.TextUtils;
import com.umeng.analytics.AnalyticsConfig;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import u.aly.ai;
import u.aly.ar;
import u.aly.bj;
import u.aly.bl;

public class av
implements Serializable {
    public static long c = 0L;
    private static final long d = -5254997387189944418L;
    public n a = new n();
    public m b = new m();

    static {
        c = 0L;
    }

    public boolean a() {
        return this.a.t != null && this.a.s != null && this.a.r != null && this.a.a != null && this.a.b != null && this.a.f != null && this.a.e != null && this.a.d != null;
    }

    public void b() {
        this.a = new n();
        this.b = new m();
        c = 0L;
    }

    public static class a {
        public static final int a = 0;
        public static final int b = 1;
        public static final int c = 2;
    }

    public static class b
    implements Serializable {
        private static final long b = 395432415169525323L;
        public long a = 0L;
    }

    public static class c
    implements Serializable {
        private static final long c = -6648526015472635581L;
        public String a = null;
        public String b = null;
    }

    public static class d
    implements Serializable {
        private static final long c = -4761083466478982295L;
        public Map<String, List<e>> a = new HashMap<String, List<e>>();
        public Map<String, List<f>> b = new HashMap<String, List<f>>();
    }

    public static class e
    implements Serializable {
        private static final long f = 8614138410597604223L;
        public long a = 0L;
        public long b = 0L;
        public int c = 0;
        public int d = 0;
        public List<String> e = new ArrayList<String>();
    }

    public static final class f
    implements Serializable {
        private static final long d = -7569163627707250811L;
        public int a = 0;
        public long b = 0L;
        public String c = null;
    }

    public static class g
    implements Serializable {
        private static final long d = -1010993116426830703L;
        public Integer a = 0;
        public long b = 0L;
        public boolean c = false;
    }

    public static class h
    implements Serializable {
        private static final long c = -7833224895044623144L;
        public String a = null;
        public List<j> b = new ArrayList<j>();
    }

    public static class i
    implements Serializable,
    ai {
        private static final long d = -7911804253674023187L;
        public long a = 0L;
        public long b = 0L;
        public String c = null;

        @Override
        public void a(av av2) {
            if (av2.b.i != null) {
                av2.b.i.add(this);
            }
        }
    }

    public static class j
    implements Serializable,
    ai {
        private static final long g = -1062440179015494286L;
        public int a = 0;
        public String b = null;
        public String c = null;
        public long d = 0L;
        public long e = 0L;
        public Map<String, Object> f = new HashMap<String, Object>();

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void a(av var1_1) {
            block15: {
                var3_3 = 0;
                if (this.b == null) {
                    this.b = ar.a();
                }
                if (var1_1.b.a == null) break block15;
                try {
                    if (this.a != 1) break block15;
                    var4_4 = var1_1.b.a.size();
                    if (var4_4 > 0) {
                        var2_5 = 0;
                    } else {
                        var5_6 = new h();
                        var5_6.a = this.b;
                        var5_6.b.add(this);
                        var1_1.b.a.add(var5_6);
                    }
lbl18:
                    // 2 sources

                    while (true) {
                        block17: {
                            if (var2_5 >= var4_4) break block17;
                            var5_6 = var1_1.b.a.get(var2_5);
                            if (!TextUtils.isEmpty((CharSequence)var5_6.a) && var5_6.a.equals(this.b)) {
                                var1_1.b.a.remove(var5_6);
                                var5_6.b.add(this);
                                var1_1.b.a.add(var5_6);
                                return;
                            }
                            ** GOTO lbl55
                        }
                        var5_6 = new h();
                        var5_6.a = this.b;
                        var5_6.b.add(this);
                        if (!var1_1.b.a.contains(var5_6)) {
                            var1_1.b.a.add(var5_6);
                        }
                        break;
                    }
                }
                catch (Throwable var5_7) {
                    bl.e(var5_7);
                }
            }
            if (var1_1.b.b == null) ** GOTO lbl54
            try {
                block16: {
                    if (this.a != 2) ** GOTO lbl54
                    var4_4 = var1_1.b.b.size();
                    if (var4_4 > 0) {
                        break block16;
                    } else {
                        var5_6 = new h();
                        var5_6.a = this.b;
                        var5_6.b.add(this);
                        var1_1.b.b.add(var5_6);
lbl54:
                        // 3 sources

                        return;
                    }
lbl55:
                    // 1 sources

                    ++var2_5;
                    ** continue;
                }
                for (var2_5 = var3_3; var2_5 < var4_4; ++var2_5) {
                    var5_6 = var1_1.b.b.get(var2_5);
                    if (TextUtils.isEmpty((CharSequence)var5_6.a) || !var5_6.a.equals(this.b)) continue;
                    var1_1.b.b.remove(var5_6);
                    var5_6.b.add(this);
                    var1_1.b.b.add(var5_6);
                    return;
                }
                var5_6 = new h();
                var5_6.a = this.b;
                var5_6.b.add(this);
                var1_1.b.b.add(var5_6);
                return;
            }
            catch (Throwable var1_2) {
                bl.e(var1_2);
                return;
            }
        }
    }

    public static final class k
    implements Serializable {
        private static final long d = -1397960951960451474L;
        public double a = 0.0;
        public double b = 0.0;
        public long c = 0L;
    }

    public static final class l
    implements Serializable {
        private static final long e = 2506525905874738341L;
        public String a = null;
        public long b = 0L;
        public long c = 0L;
        public long d = 0L;
    }

    public static class m
    implements Serializable {
        private static final long k = 5703014667657688269L;
        public List<h> a = new ArrayList<h>();
        public List<h> b = new ArrayList<h>();
        public List<o> c = new ArrayList<o>();
        public b d = new b();
        public g e = new g();
        public Map<String, Integer> f = new HashMap<String, Integer>();
        public c g = new c();
        public d h = new d();
        public List<i> i = new ArrayList<i>();
        public String j = null;
    }

    public static class n
    implements Serializable {
        private static final long P = 4568484649280698573L;
        public String A;
        public String B = null;
        public String C = null;
        public long D = 8L;
        public String E = null;
        public String F = null;
        public String G = null;
        public String H = null;
        public String I = null;
        public String J = null;
        public long K = 0L;
        public long L = 0L;
        public long M = 0L;
        public String N = null;
        public String O = null;
        public String a = null;
        public String b = null;
        public String c = null;
        public String d = null;
        public String e = null;
        public String f = null;
        public String g = null;
        public int h = 0;
        public String i = AnalyticsConfig.mWrapperType;
        public String j = AnalyticsConfig.mWrapperVersion;
        public String k = "Android";
        public String l = null;
        public int m = 0;
        public String n = null;
        public String o = bj.a();
        public String p = "Android";
        public String q = Build.VERSION.RELEASE;
        public String r = null;
        public String s = null;
        public String t = null;
        public String u = Build.MODEL;
        public String v = Build.BOARD;
        public String w = Build.BRAND;
        public long x = Build.TIME;
        public String y = Build.MANUFACTURER;
        public String z = Build.ID;

        public n() {
            this.A = Build.DEVICE;
        }
    }

    public static class o
    implements Serializable,
    ai {
        public static Map<String, l> g = new HashMap<String, l>();
        private static final long k = 8683938900576888953L;
        public int a = 0;
        public String b = null;
        public long c = 0L;
        public long d = 0L;
        public long e = 0L;
        public boolean f = false;
        public List<l> h = new ArrayList<l>();
        public p i = new p();
        public k j = new k();

        @Override
        public void a(av av2) {
            if (av2.b.c != null) {
                av2.b.c.add(this);
            }
        }
    }

    public static final class p
    implements Serializable {
        private static final long c = -7629272972021970177L;
        public long a = 0L;
        public long b = 0L;
    }
}

