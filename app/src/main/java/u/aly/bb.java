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
import u.aly.bm;
import u.aly.bp;
import u.aly.bv;
import u.aly.bw;
import u.aly.cb;
import u.aly.cc;
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

public class bb
implements Serializable,
Cloneable,
bp<bb, e> {
    public static final Map<e, cb> d;
    private static final ct e;
    private static final cj f;
    private static final cj g;
    private static final cj h;
    private static final Map<Class<? extends cw>, cx> i;
    private static final int j = 0;
    private static final int k = 1;
    public String a;
    public long b;
    public int c;
    private byte l = 0;

    static {
        e = new ct("IdSnapshot");
        f = new cj("identity", 11, 1);
        g = new cj("ts", 10, 2);
        h = new cj("version", 8, 3);
        i = new HashMap<Class<? extends cw>, cx>();
        i.put(cy.class, new b());
        i.put(cz.class, new d());
        EnumMap<e, cb> enumMap = new EnumMap<e, cb>(e.class);
        enumMap.put(u.aly.bb$e.a, new cb("identity", 1, new cc(11)));
        enumMap.put(u.aly.bb$e.b, new cb("ts", 1, new cc(10)));
        enumMap.put(u.aly.bb$e.c, new cb("version", 1, new cc(8)));
        d = Collections.unmodifiableMap(enumMap);
        cb.a(bb.class, d);
    }

    public bb() {
    }

    public bb(String string2, long l2, int n2) {
        this();
        this.a = string2;
        this.b = l2;
        this.b(true);
        this.c = n2;
        this.c(true);
    }

    public bb(bb bb2) {
        this.l = bb2.l;
        if (bb2.e()) {
            this.a = bb2.a;
        }
        this.b = bb2.b;
        this.c = bb2.c;
    }

    private void a(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        try {
            this.l = 0;
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

    public bb a() {
        return new bb(this);
    }

    public bb a(int n2) {
        this.c = n2;
        this.c(true);
        return this;
    }

    public bb a(long l2) {
        this.b = l2;
        this.b(true);
        return this;
    }

    public bb a(String string2) {
        this.a = string2;
        return this;
    }

    @Override
    public void a(co co2) throws bv {
        i.get(co2.D()).b().b(co2, (bb)this);
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
        this.b = 0L;
        this.c(false);
        this.c = 0;
    }

    @Override
    public void b(co co2) throws bv {
        i.get(co2.D()).b().a(co2, (bb)this);
    }

    public void b(boolean bl2) {
        this.l = bm.a(this.l, 0, bl2);
    }

    public String c() {
        return this.a;
    }

    public e c(int n2) {
        return u.aly.bb$e.a(n2);
    }

    public void c(boolean bl2) {
        this.l = bm.a(this.l, 1, bl2);
    }

    public void d() {
        this.a = null;
    }

    public boolean e() {
        return this.a != null;
    }

    public long f() {
        return this.b;
    }

    public void g() {
        this.l = bm.b(this.l, 0);
    }

    public boolean h() {
        return bm.a(this.l, 0);
    }

    public int i() {
        return this.c;
    }

    public void j() {
        this.l = bm.b(this.l, 1);
    }

    public boolean k() {
        return bm.a(this.l, 1);
    }

    public void l() throws bv {
        if (this.a == null) {
            throw new cp("Required field 'identity' was not present! Struct: " + this.toString());
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
        StringBuilder stringBuilder = new StringBuilder("IdSnapshot(");
        stringBuilder.append("identity:");
        if (this.a == null) {
            stringBuilder.append("null");
        } else {
            stringBuilder.append(this.a);
        }
        stringBuilder.append(", ");
        stringBuilder.append("ts:");
        stringBuilder.append(this.b);
        stringBuilder.append(", ");
        stringBuilder.append("version:");
        stringBuilder.append(this.c);
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    private static class a
    extends cy<bb> {
        private a() {
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public void a(co co2, bb bb2) throws bv {
            co2.j();
            while (true) {
                cj cj2 = co2.l();
                if (cj2.b == 0) {
                    co2.k();
                    if (bb2.h()) break;
                    throw new cp("Required field 'ts' was not found in serialized data! Struct: " + this.toString());
                }
                switch (cj2.c) {
                    default: {
                        cr.a(co2, cj2.b);
                        break;
                    }
                    case 1: {
                        if (cj2.b == 11) {
                            bb2.a = co2.z();
                            bb2.a(true);
                            break;
                        }
                        cr.a(co2, cj2.b);
                        break;
                    }
                    case 2: {
                        if (cj2.b == 10) {
                            bb2.b = co2.x();
                            bb2.b(true);
                            break;
                        }
                        cr.a(co2, cj2.b);
                        break;
                    }
                    case 3: {
                        if (cj2.b == 8) {
                            bb2.c = co2.w();
                            bb2.c(true);
                            break;
                        }
                        cr.a(co2, cj2.b);
                    }
                }
                co2.m();
            }
            if (!bb2.k()) {
                throw new cp("Required field 'version' was not found in serialized data! Struct: " + this.toString());
            }
            bb2.l();
        }

        @Override
        public /* synthetic */ void a(co co2, bp bp2) throws bv {
            this.b(co2, (bb)bp2);
        }

        @Override
        public void b(co co2, bb bb2) throws bv {
            bb2.l();
            co2.a(e);
            if (bb2.a != null) {
                co2.a(f);
                co2.a(bb2.a);
                co2.c();
            }
            co2.a(g);
            co2.a(bb2.b);
            co2.c();
            co2.a(h);
            co2.a(bb2.c);
            co2.c();
            co2.d();
            co2.b();
        }

        @Override
        public /* synthetic */ void b(co co2, bp bp2) throws bv {
            this.a(co2, (bb)bp2);
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
    extends cz<bb> {
        private c() {
        }

        @Override
        public void a(co co2, bb bb2) throws bv {
            co2 = (cu)co2;
            ((ci)co2).a(bb2.a);
            ((ci)co2).a(bb2.b);
            ((ci)co2).a(bb2.c);
        }

        @Override
        public void b(co co2, bb bb2) throws bv {
            co2 = (cu)co2;
            bb2.a = ((ci)co2).z();
            bb2.a(true);
            bb2.b = ((ci)co2).x();
            bb2.b(true);
            bb2.c = ((ci)co2).w();
            bb2.c(true);
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
        a(1, "identity"),
        b(2, "ts"),
        c(3, "version");

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
            e e2 = u.aly.bb$e.a(n2);
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

