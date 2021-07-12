/*
 * Decompiled with CFR 0.151.
 */
package u.aly;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collections;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import u.aly.be;
import u.aly.bm;
import u.aly.bp;
import u.aly.bv;
import u.aly.bw;
import u.aly.cb;
import u.aly.cc;
import u.aly.ce;
import u.aly.cg;
import u.aly.ci;
import u.aly.cj;
import u.aly.cl;
import u.aly.co;
import u.aly.cp;
import u.aly.cr;
import u.aly.ct;
import u.aly.cu;
import u.aly.cw;
import u.aly.cx;
import u.aly.cy;
import u.aly.cz;
import u.aly.da;

public class bd
implements Serializable,
Cloneable,
bp<bd, e> {
    public static final Map<e, cb> d;
    private static final ct e;
    private static final cj f;
    private static final cj g;
    private static final cj h;
    private static final Map<Class<? extends cw>, cx> i;
    private static final int j = 0;
    public Map<String, be> a;
    public int b;
    public String c;
    private byte k = 0;

    static {
        e = new ct("Imprint");
        f = new cj("property", 13, 1);
        g = new cj("version", 8, 2);
        h = new cj("checksum", 11, 3);
        i = new HashMap<Class<? extends cw>, cx>();
        i.put(cy.class, new b());
        i.put(cz.class, new d());
        EnumMap<e, cb> enumMap = new EnumMap<e, cb>(e.class);
        enumMap.put(u.aly.bd$e.a, new cb("property", 1, new ce(13, new cc(11), new cg(12, be.class))));
        enumMap.put(u.aly.bd$e.b, new cb("version", 1, new cc(8)));
        enumMap.put(u.aly.bd$e.c, new cb("checksum", 1, new cc(11)));
        d = Collections.unmodifiableMap(enumMap);
        cb.a(bd.class, d);
    }

    public bd() {
    }

    public bd(Map<String, be> map, int n2, String string2) {
        this();
        this.a = map;
        this.b = n2;
        this.b(true);
        this.c = string2;
    }

    public bd(bd bd2) {
        this.k = bd2.k;
        if (bd2.f()) {
            HashMap<String, be> hashMap = new HashMap<String, be>();
            for (Map.Entry<String, be> entry : bd2.a.entrySet()) {
                hashMap.put(entry.getKey(), new be(entry.getValue()));
            }
            this.a = hashMap;
        }
        this.b = bd2.b;
        if (bd2.l()) {
            this.c = bd2.c;
        }
    }

    private void a(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        try {
            this.k = 0;
            this.a(new ci(new da(objectInputStream)));
            return;
        }
        catch (bv bv2) {
            throw new IOException(bv2.getMessage());
        }
    }

    private void a(ObjectOutputStream objectOutputStream) throws IOException {
        try {
            this.b(new ci(new da(objectOutputStream)));
            return;
        }
        catch (bv bv2) {
            throw new IOException(bv2.getMessage());
        }
    }

    public bd a() {
        return new bd(this);
    }

    public bd a(int n2) {
        this.b = n2;
        this.b(true);
        return this;
    }

    public bd a(String string2) {
        this.c = string2;
        return this;
    }

    public bd a(Map<String, be> map) {
        this.a = map;
        return this;
    }

    public void a(String string2, be be2) {
        if (this.a == null) {
            this.a = new HashMap<String, be>();
        }
        this.a.put(string2, be2);
    }

    @Override
    public void a(co co2) throws bv {
        i.get(co2.D()).b().b(co2, (bd)this);
    }

    public void a(boolean bl2) {
        if (!bl2) {
            this.a = null;
        }
    }

    @Override
    public /* synthetic */ bw b(int n2) {
        return this.c(n2);
    }

    @Override
    public void b() {
        this.a = null;
        this.b(false);
        this.b = 0;
        this.c = null;
    }

    @Override
    public void b(co co2) throws bv {
        i.get(co2.D()).b().a(co2, (bd)this);
    }

    public void b(boolean bl2) {
        this.k = bm.a(this.k, 0, bl2);
    }

    public int c() {
        if (this.a == null) {
            return 0;
        }
        return this.a.size();
    }

    public e c(int n2) {
        return u.aly.bd$e.a(n2);
    }

    public void c(boolean bl2) {
        if (!bl2) {
            this.c = null;
        }
    }

    public Map<String, be> d() {
        return this.a;
    }

    public void e() {
        this.a = null;
    }

    public boolean f() {
        return this.a != null;
    }

    public int g() {
        return this.b;
    }

    public void h() {
        this.k = bm.b(this.k, 0);
    }

    public boolean i() {
        return bm.a(this.k, 0);
    }

    public String j() {
        return this.c;
    }

    public void k() {
        this.c = null;
    }

    public boolean l() {
        return this.c != null;
    }

    public void m() throws bv {
        if (this.a == null) {
            throw new cp("Required field 'property' was not present! Struct: " + this.toString());
        }
        if (this.c == null) {
            throw new cp("Required field 'checksum' was not present! Struct: " + this.toString());
        }
    }

    @Override
    public /* synthetic */ bp p() {
        return this.a();
    }

    /*
     * Enabled aggressive block sorting
     */
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("Imprint(");
        stringBuilder.append("property:");
        if (this.a == null) {
            stringBuilder.append("null");
        } else {
            stringBuilder.append(this.a);
        }
        stringBuilder.append(", ");
        stringBuilder.append("version:");
        stringBuilder.append(this.b);
        stringBuilder.append(", ");
        stringBuilder.append("checksum:");
        if (this.c == null) {
            stringBuilder.append("null");
        } else {
            stringBuilder.append(this.c);
        }
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    private static class a
    extends cy<bd> {
        private a() {
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public void a(co co2, bd bd2) throws bv {
            co2.j();
            while (true) {
                Object object = co2.l();
                if (((cj)object).b == 0) {
                    co2.k();
                    if (bd2.i()) break;
                    throw new cp("Required field 'version' was not found in serialized data! Struct: " + this.toString());
                }
                switch (((cj)object).c) {
                    default: {
                        cr.a(co2, ((cj)object).b);
                        break;
                    }
                    case 1: {
                        if (((cj)object).b == 13) {
                            object = co2.n();
                            bd2.a = new HashMap<String, be>(((cl)object).c * 2);
                            for (int i2 = 0; i2 < ((cl)object).c; ++i2) {
                                String string2 = co2.z();
                                be be2 = new be();
                                be2.a(co2);
                                bd2.a.put(string2, be2);
                            }
                            co2.o();
                            bd2.a(true);
                            break;
                        }
                        cr.a(co2, ((cj)object).b);
                        break;
                    }
                    case 2: {
                        if (((cj)object).b == 8) {
                            bd2.b = co2.w();
                            bd2.b(true);
                            break;
                        }
                        cr.a(co2, ((cj)object).b);
                        break;
                    }
                    case 3: {
                        if (((cj)object).b == 11) {
                            bd2.c = co2.z();
                            bd2.c(true);
                            break;
                        }
                        cr.a(co2, ((cj)object).b);
                    }
                }
                co2.m();
            }
            bd2.m();
        }

        @Override
        public /* synthetic */ void a(co co2, bp bp2) throws bv {
            this.b(co2, (bd)bp2);
        }

        @Override
        public void b(co co2, bd bd2) throws bv {
            bd2.m();
            co2.a(e);
            if (bd2.a != null) {
                co2.a(f);
                co2.a(new cl(11, 12, bd2.a.size()));
                for (Map.Entry<String, be> entry : bd2.a.entrySet()) {
                    co2.a(entry.getKey());
                    entry.getValue().b(co2);
                }
                co2.e();
                co2.c();
            }
            co2.a(g);
            co2.a(bd2.b);
            co2.c();
            if (bd2.c != null) {
                co2.a(h);
                co2.a(bd2.c);
                co2.c();
            }
            co2.d();
            co2.b();
        }

        @Override
        public /* synthetic */ void b(co co2, bp bp2) throws bv {
            this.a(co2, (bd)bp2);
        }
    }

    private static class b
    implements cx {
        private b() {
        }

        public a a() {
            return new a();
        }

        public /* synthetic */ cw b() {
            return this.a();
        }
    }

    private static class c
    extends cz<bd> {
        private c() {
        }

        @Override
        public void a(co co2, bd bd2) throws bv {
            co2 = (cu)co2;
            ((ci)co2).a(bd2.a.size());
            for (Map.Entry<String, be> entry : bd2.a.entrySet()) {
                ((ci)co2).a(entry.getKey());
                entry.getValue().b(co2);
            }
            ((ci)co2).a(bd2.b);
            ((ci)co2).a(bd2.c);
        }

        @Override
        public void b(co co2, bd bd2) throws bv {
            co2 = (cu)co2;
            cl cl2 = new cl(11, 12, ((ci)co2).w());
            bd2.a = new HashMap<String, be>(cl2.c * 2);
            for (int i2 = 0; i2 < cl2.c; ++i2) {
                String string2 = ((ci)co2).z();
                be be2 = new be();
                be2.a(co2);
                bd2.a.put(string2, be2);
            }
            bd2.a(true);
            bd2.b = ((ci)co2).w();
            bd2.b(true);
            bd2.c = ((ci)co2).z();
            bd2.c(true);
        }
    }

    private static class d
    implements cx {
        private d() {
        }

        public c a() {
            return new c();
        }

        public /* synthetic */ cw b() {
            return this.a();
        }
    }

    public static enum e implements bw
    {
        a(1, "property"),
        b(2, "version"),
        c(3, "checksum");

        private static final Map<String, e> d;
        private final short e;
        private final String f;

        static {
            d = new HashMap<String, e>();
            for (e e2 : EnumSet.allOf(e.class)) {
                d.put(e2.b(), e2);
            }
        }

        private e(short s2, String string3) {
            this.e = s2;
            this.f = string3;
        }

        public static e a(int n2) {
            switch (n2) {
                default: {
                    return null;
                }
                case 1: {
                    return a;
                }
                case 2: {
                    return b;
                }
                case 3: 
            }
            return c;
        }

        public static e a(String string2) {
            return d.get(string2);
        }

        public static e b(int n2) {
            e e2 = u.aly.bd$e.a(n2);
            if (e2 == null) {
                throw new IllegalArgumentException("Field " + n2 + " doesn't exist!");
            }
            return e2;
        }

        @Override
        public short a() {
            return this.e;
        }

        @Override
        public String b() {
            return this.f;
        }
    }
}

