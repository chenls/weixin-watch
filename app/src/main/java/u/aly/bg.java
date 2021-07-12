/*
 * Decompiled with CFR 0.151.
 */
package u.aly;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.BitSet;
import java.util.Collections;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import u.aly.bm;
import u.aly.bp;
import u.aly.bq;
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

public class bg
implements Serializable,
Cloneable,
bp<bg, e> {
    private static final int A = 3;
    public static final Map<e, cb> k;
    private static final ct l;
    private static final cj m;
    private static final cj n;
    private static final cj o;
    private static final cj p;
    private static final cj q;
    private static final cj r;
    private static final cj s;
    private static final cj t;
    private static final cj u;
    private static final cj v;
    private static final Map<Class<? extends cw>, cx> w;
    private static final int x = 0;
    private static final int y = 1;
    private static final int z = 2;
    private byte B = 0;
    private e[] C = new e[]{u.aly.bg$e.j};
    public String a;
    public String b;
    public String c;
    public int d;
    public int e;
    public int f;
    public ByteBuffer g;
    public String h;
    public String i;
    public int j;

    static {
        l = new ct("UMEnvelope");
        m = new cj("version", 11, 1);
        n = new cj("address", 11, 2);
        o = new cj("signature", 11, 3);
        p = new cj("serial_num", 8, 4);
        q = new cj("ts_secs", 8, 5);
        r = new cj("length", 8, 6);
        s = new cj("entity", 11, 7);
        t = new cj("guid", 11, 8);
        u = new cj("checksum", 11, 9);
        v = new cj("codex", 8, 10);
        w = new HashMap<Class<? extends cw>, cx>();
        w.put(cy.class, new b());
        w.put(cz.class, new d());
        EnumMap<e, cb> enumMap = new EnumMap<e, cb>(e.class);
        enumMap.put(u.aly.bg$e.a, new cb("version", 1, new cc(11)));
        enumMap.put(u.aly.bg$e.b, new cb("address", 1, new cc(11)));
        enumMap.put(u.aly.bg$e.c, new cb("signature", 1, new cc(11)));
        enumMap.put(u.aly.bg$e.d, new cb("serial_num", 1, new cc(8)));
        enumMap.put(u.aly.bg$e.e, new cb("ts_secs", 1, new cc(8)));
        enumMap.put(u.aly.bg$e.f, new cb("length", 1, new cc(8)));
        enumMap.put(u.aly.bg$e.g, new cb("entity", 1, new cc(11, true)));
        enumMap.put(u.aly.bg$e.h, new cb("guid", 1, new cc(11)));
        enumMap.put(u.aly.bg$e.i, new cb("checksum", 1, new cc(11)));
        enumMap.put(u.aly.bg$e.j, new cb("codex", 2, new cc(8)));
        k = Collections.unmodifiableMap(enumMap);
        cb.a(bg.class, k);
    }

    public bg() {
    }

    public bg(String string2, String string3, String string4, int n2, int n3, int n4, ByteBuffer byteBuffer, String string5, String string6) {
        this();
        this.a = string2;
        this.b = string3;
        this.c = string4;
        this.d = n2;
        this.d(true);
        this.e = n3;
        this.e(true);
        this.f = n4;
        this.f(true);
        this.g = byteBuffer;
        this.h = string5;
        this.i = string6;
    }

    public bg(bg bg2) {
        this.B = bg2.B;
        if (bg2.e()) {
            this.a = bg2.a;
        }
        if (bg2.h()) {
            this.b = bg2.b;
        }
        if (bg2.k()) {
            this.c = bg2.c;
        }
        this.d = bg2.d;
        this.e = bg2.e;
        this.f = bg2.f;
        if (bg2.y()) {
            this.g = bq.d(bg2.g);
        }
        if (bg2.B()) {
            this.h = bg2.h;
        }
        if (bg2.E()) {
            this.i = bg2.i;
        }
        this.j = bg2.j;
    }

    private void a(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        try {
            this.B = 0;
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

    public void A() {
        this.h = null;
    }

    public boolean B() {
        return this.h != null;
    }

    public String C() {
        return this.i;
    }

    public void D() {
        this.i = null;
    }

    public boolean E() {
        return this.i != null;
    }

    public int F() {
        return this.j;
    }

    public void G() {
        this.B = bm.b(this.B, 3);
    }

    public boolean H() {
        return bm.a(this.B, 3);
    }

    public void I() throws bv {
        if (this.a == null) {
            throw new cp("Required field 'version' was not present! Struct: " + this.toString());
        }
        if (this.b == null) {
            throw new cp("Required field 'address' was not present! Struct: " + this.toString());
        }
        if (this.c == null) {
            throw new cp("Required field 'signature' was not present! Struct: " + this.toString());
        }
        if (this.g == null) {
            throw new cp("Required field 'entity' was not present! Struct: " + this.toString());
        }
        if (this.h == null) {
            throw new cp("Required field 'guid' was not present! Struct: " + this.toString());
        }
        if (this.i == null) {
            throw new cp("Required field 'checksum' was not present! Struct: " + this.toString());
        }
    }

    public bg a() {
        return new bg(this);
    }

    public bg a(int n2) {
        this.d = n2;
        this.d(true);
        return this;
    }

    public bg a(String string2) {
        this.a = string2;
        return this;
    }

    public bg a(ByteBuffer byteBuffer) {
        this.g = byteBuffer;
        return this;
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    public bg a(byte[] object) {
        void var1_3;
        if (object == null) {
            ByteBuffer byteBuffer = null;
        } else {
            ByteBuffer byteBuffer = ByteBuffer.wrap(object);
        }
        this.a((ByteBuffer)var1_3);
        return this;
    }

    @Override
    public void a(co co2) throws bv {
        w.get(co2.D()).b().b(co2, (bg)this);
    }

    public void a(boolean bl2) {
        if (!bl2) {
            this.a = null;
        }
    }

    public bg b(String string2) {
        this.b = string2;
        return this;
    }

    @Override
    public /* synthetic */ bw b(int n2) {
        return this.f(n2);
    }

    @Override
    public void b() {
        this.a = null;
        this.b = null;
        this.c = null;
        this.d(false);
        this.d = 0;
        this.e(false);
        this.e = 0;
        this.f(false);
        this.f = 0;
        this.g = null;
        this.h = null;
        this.i = null;
        this.j(false);
        this.j = 0;
    }

    @Override
    public void b(co co2) throws bv {
        w.get(co2.D()).b().a(co2, (bg)this);
    }

    public void b(boolean bl2) {
        if (!bl2) {
            this.b = null;
        }
    }

    public String c() {
        return this.a;
    }

    public bg c(int n2) {
        this.e = n2;
        this.e(true);
        return this;
    }

    public bg c(String string2) {
        this.c = string2;
        return this;
    }

    public void c(boolean bl2) {
        if (!bl2) {
            this.c = null;
        }
    }

    public bg d(int n2) {
        this.f = n2;
        this.f(true);
        return this;
    }

    public bg d(String string2) {
        this.h = string2;
        return this;
    }

    public void d() {
        this.a = null;
    }

    public void d(boolean bl2) {
        this.B = bm.a(this.B, 0, bl2);
    }

    public bg e(int n2) {
        this.j = n2;
        this.j(true);
        return this;
    }

    public bg e(String string2) {
        this.i = string2;
        return this;
    }

    public void e(boolean bl2) {
        this.B = bm.a(this.B, 1, bl2);
    }

    public boolean e() {
        return this.a != null;
    }

    public String f() {
        return this.b;
    }

    public e f(int n2) {
        return u.aly.bg$e.a(n2);
    }

    public void f(boolean bl2) {
        this.B = bm.a(this.B, 2, bl2);
    }

    public void g() {
        this.b = null;
    }

    public void g(boolean bl2) {
        if (!bl2) {
            this.g = null;
        }
    }

    public void h(boolean bl2) {
        if (!bl2) {
            this.h = null;
        }
    }

    public boolean h() {
        return this.b != null;
    }

    public String i() {
        return this.c;
    }

    public void i(boolean bl2) {
        if (!bl2) {
            this.i = null;
        }
    }

    public void j() {
        this.c = null;
    }

    public void j(boolean bl2) {
        this.B = bm.a(this.B, 3, bl2);
    }

    public boolean k() {
        return this.c != null;
    }

    public int l() {
        return this.d;
    }

    public void m() {
        this.B = bm.b(this.B, 0);
    }

    public boolean n() {
        return bm.a(this.B, 0);
    }

    public int o() {
        return this.e;
    }

    @Override
    public /* synthetic */ bp p() {
        return this.a();
    }

    public void q() {
        this.B = bm.b(this.B, 1);
    }

    public boolean r() {
        return bm.a(this.B, 1);
    }

    public int s() {
        return this.f;
    }

    public void t() {
        this.B = bm.b(this.B, 2);
    }

    /*
     * Enabled aggressive block sorting
     */
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("UMEnvelope(");
        stringBuilder.append("version:");
        if (this.a == null) {
            stringBuilder.append("null");
        } else {
            stringBuilder.append(this.a);
        }
        stringBuilder.append(", ");
        stringBuilder.append("address:");
        if (this.b == null) {
            stringBuilder.append("null");
        } else {
            stringBuilder.append(this.b);
        }
        stringBuilder.append(", ");
        stringBuilder.append("signature:");
        if (this.c == null) {
            stringBuilder.append("null");
        } else {
            stringBuilder.append(this.c);
        }
        stringBuilder.append(", ");
        stringBuilder.append("serial_num:");
        stringBuilder.append(this.d);
        stringBuilder.append(", ");
        stringBuilder.append("ts_secs:");
        stringBuilder.append(this.e);
        stringBuilder.append(", ");
        stringBuilder.append("length:");
        stringBuilder.append(this.f);
        stringBuilder.append(", ");
        stringBuilder.append("entity:");
        if (this.g == null) {
            stringBuilder.append("null");
        } else {
            bq.a(this.g, stringBuilder);
        }
        stringBuilder.append(", ");
        stringBuilder.append("guid:");
        if (this.h == null) {
            stringBuilder.append("null");
        } else {
            stringBuilder.append(this.h);
        }
        stringBuilder.append(", ");
        stringBuilder.append("checksum:");
        if (this.i == null) {
            stringBuilder.append("null");
        } else {
            stringBuilder.append(this.i);
        }
        if (this.H()) {
            stringBuilder.append(", ");
            stringBuilder.append("codex:");
            stringBuilder.append(this.j);
        }
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    public boolean u() {
        return bm.a(this.B, 2);
    }

    public byte[] v() {
        this.a(bq.c(this.g));
        if (this.g == null) {
            return null;
        }
        return this.g.array();
    }

    public ByteBuffer w() {
        return this.g;
    }

    public void x() {
        this.g = null;
    }

    public boolean y() {
        return this.g != null;
    }

    public String z() {
        return this.h;
    }

    private static class a
    extends cy<bg> {
        private a() {
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public void a(co co2, bg bg2) throws bv {
            co2.j();
            while (true) {
                cj cj2 = co2.l();
                if (cj2.b == 0) {
                    co2.k();
                    if (bg2.n()) break;
                    throw new cp("Required field 'serial_num' was not found in serialized data! Struct: " + this.toString());
                }
                switch (cj2.c) {
                    default: {
                        cr.a(co2, cj2.b);
                        break;
                    }
                    case 1: {
                        if (cj2.b == 11) {
                            bg2.a = co2.z();
                            bg2.a(true);
                            break;
                        }
                        cr.a(co2, cj2.b);
                        break;
                    }
                    case 2: {
                        if (cj2.b == 11) {
                            bg2.b = co2.z();
                            bg2.b(true);
                            break;
                        }
                        cr.a(co2, cj2.b);
                        break;
                    }
                    case 3: {
                        if (cj2.b == 11) {
                            bg2.c = co2.z();
                            bg2.c(true);
                            break;
                        }
                        cr.a(co2, cj2.b);
                        break;
                    }
                    case 4: {
                        if (cj2.b == 8) {
                            bg2.d = co2.w();
                            bg2.d(true);
                            break;
                        }
                        cr.a(co2, cj2.b);
                        break;
                    }
                    case 5: {
                        if (cj2.b == 8) {
                            bg2.e = co2.w();
                            bg2.e(true);
                            break;
                        }
                        cr.a(co2, cj2.b);
                        break;
                    }
                    case 6: {
                        if (cj2.b == 8) {
                            bg2.f = co2.w();
                            bg2.f(true);
                            break;
                        }
                        cr.a(co2, cj2.b);
                        break;
                    }
                    case 7: {
                        if (cj2.b == 11) {
                            bg2.g = co2.A();
                            bg2.g(true);
                            break;
                        }
                        cr.a(co2, cj2.b);
                        break;
                    }
                    case 8: {
                        if (cj2.b == 11) {
                            bg2.h = co2.z();
                            bg2.h(true);
                            break;
                        }
                        cr.a(co2, cj2.b);
                        break;
                    }
                    case 9: {
                        if (cj2.b == 11) {
                            bg2.i = co2.z();
                            bg2.i(true);
                            break;
                        }
                        cr.a(co2, cj2.b);
                        break;
                    }
                    case 10: {
                        if (cj2.b == 8) {
                            bg2.j = co2.w();
                            bg2.j(true);
                            break;
                        }
                        cr.a(co2, cj2.b);
                    }
                }
                co2.m();
            }
            if (!bg2.r()) {
                throw new cp("Required field 'ts_secs' was not found in serialized data! Struct: " + this.toString());
            }
            if (!bg2.u()) {
                throw new cp("Required field 'length' was not found in serialized data! Struct: " + this.toString());
            }
            bg2.I();
        }

        @Override
        public /* synthetic */ void a(co co2, bp bp2) throws bv {
            this.b(co2, (bg)bp2);
        }

        @Override
        public void b(co co2, bg bg2) throws bv {
            bg2.I();
            co2.a(l);
            if (bg2.a != null) {
                co2.a(m);
                co2.a(bg2.a);
                co2.c();
            }
            if (bg2.b != null) {
                co2.a(n);
                co2.a(bg2.b);
                co2.c();
            }
            if (bg2.c != null) {
                co2.a(o);
                co2.a(bg2.c);
                co2.c();
            }
            co2.a(p);
            co2.a(bg2.d);
            co2.c();
            co2.a(q);
            co2.a(bg2.e);
            co2.c();
            co2.a(r);
            co2.a(bg2.f);
            co2.c();
            if (bg2.g != null) {
                co2.a(s);
                co2.a(bg2.g);
                co2.c();
            }
            if (bg2.h != null) {
                co2.a(t);
                co2.a(bg2.h);
                co2.c();
            }
            if (bg2.i != null) {
                co2.a(u);
                co2.a(bg2.i);
                co2.c();
            }
            if (bg2.H()) {
                co2.a(v);
                co2.a(bg2.j);
                co2.c();
            }
            co2.d();
            co2.b();
        }

        @Override
        public /* synthetic */ void b(co co2, bp bp2) throws bv {
            this.a(co2, (bg)bp2);
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
    extends cz<bg> {
        private c() {
        }

        @Override
        public void a(co co2, bg bg2) throws bv {
            co2 = (cu)co2;
            ((ci)co2).a(bg2.a);
            ((ci)co2).a(bg2.b);
            ((ci)co2).a(bg2.c);
            ((ci)co2).a(bg2.d);
            ((ci)co2).a(bg2.e);
            ((ci)co2).a(bg2.f);
            ((ci)co2).a(bg2.g);
            ((ci)co2).a(bg2.h);
            ((ci)co2).a(bg2.i);
            BitSet bitSet = new BitSet();
            if (bg2.H()) {
                bitSet.set(0);
            }
            ((cu)co2).a(bitSet, 1);
            if (bg2.H()) {
                ((ci)co2).a(bg2.j);
            }
        }

        @Override
        public void b(co co2, bg bg2) throws bv {
            co2 = (cu)co2;
            bg2.a = ((ci)co2).z();
            bg2.a(true);
            bg2.b = ((ci)co2).z();
            bg2.b(true);
            bg2.c = ((ci)co2).z();
            bg2.c(true);
            bg2.d = ((ci)co2).w();
            bg2.d(true);
            bg2.e = ((ci)co2).w();
            bg2.e(true);
            bg2.f = ((ci)co2).w();
            bg2.f(true);
            bg2.g = ((ci)co2).A();
            bg2.g(true);
            bg2.h = ((ci)co2).z();
            bg2.h(true);
            bg2.i = ((ci)co2).z();
            bg2.i(true);
            if (((cu)co2).b(1).get(0)) {
                bg2.j = ((ci)co2).w();
                bg2.j(true);
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
        a(1, "version"),
        b(2, "address"),
        c(3, "signature"),
        d(4, "serial_num"),
        e(5, "ts_secs"),
        f(6, "length"),
        g(7, "entity"),
        h(8, "guid"),
        i(9, "checksum"),
        j(10, "codex");

        private static final Map<String, e> k;
        private final short l;
        private final String m;

        static {
            k = new HashMap<String, e>();
            for (e e2 : EnumSet.allOf(e.class)) {
                k.put(e2.b(), e2);
            }
        }

        private e(short s2, String string3) {
            this.l = s2;
            this.m = string3;
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
                case 3: {
                    return c;
                }
                case 4: {
                    return d;
                }
                case 5: {
                    return e;
                }
                case 6: {
                    return f;
                }
                case 7: {
                    return g;
                }
                case 8: {
                    return h;
                }
                case 9: {
                    return i;
                }
                case 10: 
            }
            return j;
        }

        public static e a(String string2) {
            return k.get(string2);
        }

        public static e b(int n2) {
            e e2 = u.aly.bg$e.a(n2);
            if (e2 == null) {
                throw new IllegalArgumentException("Field " + n2 + " doesn't exist!");
            }
            return e2;
        }

        @Override
        public short a() {
            return this.l;
        }

        @Override
        public String b() {
            return this.m;
        }
    }
}

