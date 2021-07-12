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

public class be
implements Serializable,
Cloneable,
bp<be, e> {
    public static final Map<e, cb> d;
    private static final ct e;
    private static final cj f;
    private static final cj g;
    private static final cj h;
    private static final Map<Class<? extends cw>, cx> i;
    private static final int j = 0;
    public String a;
    public long b;
    public String c;
    private byte k = 0;
    private e[] l = new e[]{u.aly.be$e.a};

    static {
        e = new ct("ImprintValue");
        f = new cj("value", 11, 1);
        g = new cj("ts", 10, 2);
        h = new cj("guid", 11, 3);
        i = new HashMap<Class<? extends cw>, cx>();
        i.put(cy.class, new b());
        i.put(cz.class, new d());
        EnumMap<e, cb> enumMap = new EnumMap<e, cb>(e.class);
        enumMap.put(u.aly.be$e.a, new cb("value", 2, new cc(11)));
        enumMap.put(u.aly.be$e.b, new cb("ts", 1, new cc(10)));
        enumMap.put(u.aly.be$e.c, new cb("guid", 1, new cc(11)));
        d = Collections.unmodifiableMap(enumMap);
        cb.a(be.class, d);
    }

    public be() {
    }

    public be(long l2, String string2) {
        this();
        this.b = l2;
        this.b(true);
        this.c = string2;
    }

    public be(be be2) {
        this.k = be2.k;
        if (be2.e()) {
            this.a = be2.a;
        }
        this.b = be2.b;
        if (be2.k()) {
            this.c = be2.c;
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

    public e a(int n2) {
        return u.aly.be$e.a(n2);
    }

    public be a() {
        return new be(this);
    }

    public be a(long l2) {
        this.b = l2;
        this.b(true);
        return this;
    }

    public be a(String string2) {
        this.a = string2;
        return this;
    }

    @Override
    public void a(co co2) throws bv {
        i.get(co2.D()).b().b(co2, (be)this);
    }

    public void a(boolean bl2) {
        if (!bl2) {
            this.a = null;
        }
    }

    public be b(String string2) {
        this.c = string2;
        return this;
    }

    @Override
    public /* synthetic */ bw b(int n2) {
        return this.a(n2);
    }

    @Override
    public void b() {
        this.a = null;
        this.b(false);
        this.b = 0L;
        this.c = null;
    }

    @Override
    public void b(co co2) throws bv {
        i.get(co2.D()).b().a(co2, (be)this);
    }

    public void b(boolean bl2) {
        this.k = bm.a(this.k, 0, bl2);
    }

    public String c() {
        return this.a;
    }

    public void c(boolean bl2) {
        if (!bl2) {
            this.c = null;
        }
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
        this.k = bm.b(this.k, 0);
    }

    public boolean h() {
        return bm.a(this.k, 0);
    }

    public String i() {
        return this.c;
    }

    public void j() {
        this.c = null;
    }

    public boolean k() {
        return this.c != null;
    }

    public void l() throws bv {
        if (this.c == null) {
            throw new cp("Required field 'guid' was not present! Struct: " + this.toString());
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
        StringBuilder stringBuilder = new StringBuilder("ImprintValue(");
        boolean bl2 = true;
        if (this.e()) {
            stringBuilder.append("value:");
            if (this.a == null) {
                stringBuilder.append("null");
            } else {
                stringBuilder.append(this.a);
            }
            bl2 = false;
        }
        if (!bl2) {
            stringBuilder.append(", ");
        }
        stringBuilder.append("ts:");
        stringBuilder.append(this.b);
        stringBuilder.append(", ");
        stringBuilder.append("guid:");
        if (this.c == null) {
            stringBuilder.append("null");
        } else {
            stringBuilder.append(this.c);
        }
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    private static class a
    extends cy<be> {
        private a() {
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public void a(co co2, be be2) throws bv {
            co2.j();
            while (true) {
                cj cj2 = co2.l();
                if (cj2.b == 0) {
                    co2.k();
                    if (be2.h()) break;
                    throw new cp("Required field 'ts' was not found in serialized data! Struct: " + this.toString());
                }
                switch (cj2.c) {
                    default: {
                        cr.a(co2, cj2.b);
                        break;
                    }
                    case 1: {
                        if (cj2.b == 11) {
                            be2.a = co2.z();
                            be2.a(true);
                            break;
                        }
                        cr.a(co2, cj2.b);
                        break;
                    }
                    case 2: {
                        if (cj2.b == 10) {
                            be2.b = co2.x();
                            be2.b(true);
                            break;
                        }
                        cr.a(co2, cj2.b);
                        break;
                    }
                    case 3: {
                        if (cj2.b == 11) {
                            be2.c = co2.z();
                            be2.c(true);
                            break;
                        }
                        cr.a(co2, cj2.b);
                    }
                }
                co2.m();
            }
            be2.l();
        }

        @Override
        public /* synthetic */ void a(co co2, bp bp2) throws bv {
            this.b(co2, (be)bp2);
        }

        @Override
        public void b(co co2, be be2) throws bv {
            be2.l();
            co2.a(e);
            if (be2.a != null && be2.e()) {
                co2.a(f);
                co2.a(be2.a);
                co2.c();
            }
            co2.a(g);
            co2.a(be2.b);
            co2.c();
            if (be2.c != null) {
                co2.a(h);
                co2.a(be2.c);
                co2.c();
            }
            co2.d();
            co2.b();
        }

        @Override
        public /* synthetic */ void b(co co2, bp bp2) throws bv {
            this.a(co2, (be)bp2);
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
    extends cz<be> {
        private c() {
        }

        @Override
        public void a(co co2, be be2) throws bv {
            co2 = (cu)co2;
            ((ci)co2).a(be2.b);
            ((ci)co2).a(be2.c);
            BitSet bitSet = new BitSet();
            if (be2.e()) {
                bitSet.set(0);
            }
            ((cu)co2).a(bitSet, 1);
            if (be2.e()) {
                ((ci)co2).a(be2.a);
            }
        }

        @Override
        public void b(co co2, be be2) throws bv {
            co2 = (cu)co2;
            be2.b = ((ci)co2).x();
            be2.b(true);
            be2.c = ((ci)co2).z();
            be2.c(true);
            if (((cu)co2).b(1).get(0)) {
                be2.a = ((ci)co2).z();
                be2.a(true);
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
        a(1, "value"),
        b(2, "ts"),
        c(3, "guid");

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
            e e2 = u.aly.be$e.a(n2);
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

