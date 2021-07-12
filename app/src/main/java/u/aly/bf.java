/*
 * Decompiled with CFR 0.151.
 */
package u.aly;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.BitSet;
import java.util.Collections;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import u.aly.bd;
import u.aly.bm;
import u.aly.bp;
import u.aly.bv;
import u.aly.bw;
import u.aly.cb;
import u.aly.cc;
import u.aly.cg;
import u.aly.ci;
import u.aly.cj;
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

public class bf
implements Serializable,
Cloneable,
bp<bf, e> {
    public static final Map<e, cb> d;
    private static final ct e;
    private static final cj f;
    private static final cj g;
    private static final cj h;
    private static final Map<Class<? extends cw>, cx> i;
    private static final int j = 0;
    public int a;
    public String b;
    public bd c;
    private byte k = 0;
    private e[] l = new e[]{u.aly.bf$e.b, u.aly.bf$e.c};

    static {
        e = new ct("Response");
        f = new cj("resp_code", 8, 1);
        g = new cj("msg", 11, 2);
        h = new cj("imprint", 12, 3);
        i = new HashMap<Class<? extends cw>, cx>();
        i.put(cy.class, new b());
        i.put(cz.class, new d());
        EnumMap<e, cb> enumMap = new EnumMap<e, cb>(e.class);
        enumMap.put(u.aly.bf$e.a, new cb("resp_code", 1, new cc(8)));
        enumMap.put(u.aly.bf$e.b, new cb("msg", 2, new cc(11)));
        enumMap.put(u.aly.bf$e.c, new cb("imprint", 2, new cg(12, bd.class)));
        d = Collections.unmodifiableMap(enumMap);
        cb.a(bf.class, d);
    }

    public bf() {
    }

    public bf(int n2) {
        this();
        this.a = n2;
        this.a(true);
    }

    public bf(bf bf2) {
        this.k = bf2.k;
        this.a = bf2.a;
        if (bf2.h()) {
            this.b = bf2.b;
        }
        if (bf2.k()) {
            this.c = new bd(bf2.c);
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

    public bf a() {
        return new bf(this);
    }

    public bf a(int n2) {
        this.a = n2;
        this.a(true);
        return this;
    }

    public bf a(String string2) {
        this.b = string2;
        return this;
    }

    public bf a(bd bd2) {
        this.c = bd2;
        return this;
    }

    @Override
    public void a(co co2) throws bv {
        i.get(co2.D()).b().b(co2, (bf)this);
    }

    public void a(boolean bl2) {
        this.k = bm.a(this.k, 0, bl2);
    }

    @Override
    public /* synthetic */ bw b(int n2) {
        return this.c(n2);
    }

    @Override
    public void b() {
        this.a(false);
        this.a = 0;
        this.b = null;
        this.c = null;
    }

    @Override
    public void b(co co2) throws bv {
        i.get(co2.D()).b().a(co2, (bf)this);
    }

    public void b(boolean bl2) {
        if (!bl2) {
            this.b = null;
        }
    }

    public int c() {
        return this.a;
    }

    public e c(int n2) {
        return u.aly.bf$e.a(n2);
    }

    public void c(boolean bl2) {
        if (!bl2) {
            this.c = null;
        }
    }

    public void d() {
        this.k = bm.b(this.k, 0);
    }

    public boolean e() {
        return bm.a(this.k, 0);
    }

    public String f() {
        return this.b;
    }

    public void g() {
        this.b = null;
    }

    public boolean h() {
        return this.b != null;
    }

    public bd i() {
        return this.c;
    }

    public void j() {
        this.c = null;
    }

    public boolean k() {
        return this.c != null;
    }

    public void l() throws bv {
        if (this.c != null) {
            this.c.m();
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
        StringBuilder stringBuilder = new StringBuilder("Response(");
        stringBuilder.append("resp_code:");
        stringBuilder.append(this.a);
        if (this.h()) {
            stringBuilder.append(", ");
            stringBuilder.append("msg:");
            if (this.b == null) {
                stringBuilder.append("null");
            } else {
                stringBuilder.append(this.b);
            }
        }
        if (this.k()) {
            stringBuilder.append(", ");
            stringBuilder.append("imprint:");
            if (this.c == null) {
                stringBuilder.append("null");
            } else {
                stringBuilder.append(this.c);
            }
        }
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    private static class a
    extends cy<bf> {
        private a() {
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public void a(co co2, bf bf2) throws bv {
            co2.j();
            while (true) {
                cj cj2 = co2.l();
                if (cj2.b == 0) {
                    co2.k();
                    if (bf2.e()) break;
                    throw new cp("Required field 'resp_code' was not found in serialized data! Struct: " + this.toString());
                }
                switch (cj2.c) {
                    default: {
                        cr.a(co2, cj2.b);
                        break;
                    }
                    case 1: {
                        if (cj2.b == 8) {
                            bf2.a = co2.w();
                            bf2.a(true);
                            break;
                        }
                        cr.a(co2, cj2.b);
                        break;
                    }
                    case 2: {
                        if (cj2.b == 11) {
                            bf2.b = co2.z();
                            bf2.b(true);
                            break;
                        }
                        cr.a(co2, cj2.b);
                        break;
                    }
                    case 3: {
                        if (cj2.b == 12) {
                            bf2.c = new bd();
                            bf2.c.a(co2);
                            bf2.c(true);
                            break;
                        }
                        cr.a(co2, cj2.b);
                    }
                }
                co2.m();
            }
            bf2.l();
        }

        @Override
        public /* synthetic */ void a(co co2, bp bp2) throws bv {
            this.b(co2, (bf)bp2);
        }

        @Override
        public void b(co co2, bf bf2) throws bv {
            bf2.l();
            co2.a(e);
            co2.a(f);
            co2.a(bf2.a);
            co2.c();
            if (bf2.b != null && bf2.h()) {
                co2.a(g);
                co2.a(bf2.b);
                co2.c();
            }
            if (bf2.c != null && bf2.k()) {
                co2.a(h);
                bf2.c.b(co2);
                co2.c();
            }
            co2.d();
            co2.b();
        }

        @Override
        public /* synthetic */ void b(co co2, bp bp2) throws bv {
            this.a(co2, (bf)bp2);
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
    extends cz<bf> {
        private c() {
        }

        @Override
        public void a(co co2, bf bf2) throws bv {
            co2 = (cu)co2;
            ((ci)co2).a(bf2.a);
            BitSet bitSet = new BitSet();
            if (bf2.h()) {
                bitSet.set(0);
            }
            if (bf2.k()) {
                bitSet.set(1);
            }
            ((cu)co2).a(bitSet, 2);
            if (bf2.h()) {
                ((ci)co2).a(bf2.b);
            }
            if (bf2.k()) {
                bf2.c.b(co2);
            }
        }

        @Override
        public void b(co co2, bf bf2) throws bv {
            co2 = (cu)co2;
            bf2.a = ((ci)co2).w();
            bf2.a(true);
            BitSet bitSet = ((cu)co2).b(2);
            if (bitSet.get(0)) {
                bf2.b = ((ci)co2).z();
                bf2.b(true);
            }
            if (bitSet.get(1)) {
                bf2.c = new bd();
                bf2.c.a(co2);
                bf2.c(true);
            }
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
        a(1, "resp_code"),
        b(2, "msg"),
        c(3, "imprint");

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
            e e2 = u.aly.bf$e.a(n2);
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

