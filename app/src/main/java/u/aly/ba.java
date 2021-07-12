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

public class ba
implements Serializable,
Cloneable,
bp<ba, e> {
    public static final Map<e, cb> e;
    private static final ct f;
    private static final cj g;
    private static final cj h;
    private static final cj i;
    private static final cj j;
    private static final Map<Class<? extends cw>, cx> k;
    private static final int l = 0;
    public String a;
    public String b;
    public String c;
    public long d;
    private byte m = 0;
    private e[] n = new e[]{u.aly.ba$e.b};

    static {
        f = new ct("IdJournal");
        g = new cj("domain", 11, 1);
        h = new cj("old_id", 11, 2);
        i = new cj("new_id", 11, 3);
        j = new cj("ts", 10, 4);
        k = new HashMap<Class<? extends cw>, cx>();
        k.put(cy.class, new b());
        k.put(cz.class, new d());
        EnumMap<e, cb> enumMap = new EnumMap<e, cb>(e.class);
        enumMap.put(u.aly.ba$e.a, new cb("domain", 1, new cc(11)));
        enumMap.put(u.aly.ba$e.b, new cb("old_id", 2, new cc(11)));
        enumMap.put(u.aly.ba$e.c, new cb("new_id", 1, new cc(11)));
        enumMap.put(u.aly.ba$e.d, new cb("ts", 1, new cc(10)));
        e = Collections.unmodifiableMap(enumMap);
        cb.a(ba.class, e);
    }

    public ba() {
    }

    public ba(String string2, String string3, long l2) {
        this();
        this.a = string2;
        this.c = string3;
        this.d = l2;
        this.d(true);
    }

    public ba(ba ba2) {
        this.m = ba2.m;
        if (ba2.e()) {
            this.a = ba2.a;
        }
        if (ba2.h()) {
            this.b = ba2.b;
        }
        if (ba2.k()) {
            this.c = ba2.c;
        }
        this.d = ba2.d;
    }

    private void a(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        try {
            this.m = 0;
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
        return u.aly.ba$e.a(n2);
    }

    public ba a() {
        return new ba(this);
    }

    public ba a(long l2) {
        this.d = l2;
        this.d(true);
        return this;
    }

    public ba a(String string2) {
        this.a = string2;
        return this;
    }

    @Override
    public void a(co co2) throws bv {
        k.get(co2.D()).b().b(co2, (ba)this);
    }

    public void a(boolean bl2) {
        if (!bl2) {
            this.a = null;
        }
    }

    public ba b(String string2) {
        this.b = string2;
        return this;
    }

    @Override
    public /* synthetic */ bw b(int n2) {
        return this.a(n2);
    }

    @Override
    public void b() {
        this.a = null;
        this.b = null;
        this.c = null;
        this.d(false);
        this.d = 0L;
    }

    @Override
    public void b(co co2) throws bv {
        k.get(co2.D()).b().a(co2, (ba)this);
    }

    public void b(boolean bl2) {
        if (!bl2) {
            this.b = null;
        }
    }

    public String c() {
        return this.a;
    }

    public ba c(String string2) {
        this.c = string2;
        return this;
    }

    public void c(boolean bl2) {
        if (!bl2) {
            this.c = null;
        }
    }

    public void d() {
        this.a = null;
    }

    public void d(boolean bl2) {
        this.m = bm.a(this.m, 0, bl2);
    }

    public boolean e() {
        return this.a != null;
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

    public String i() {
        return this.c;
    }

    public void j() {
        this.c = null;
    }

    public boolean k() {
        return this.c != null;
    }

    public long l() {
        return this.d;
    }

    public void m() {
        this.m = bm.b(this.m, 0);
    }

    public boolean n() {
        return bm.a(this.m, 0);
    }

    public void o() throws bv {
        if (this.a == null) {
            throw new cp("Required field 'domain' was not present! Struct: " + this.toString());
        }
        if (this.c == null) {
            throw new cp("Required field 'new_id' was not present! Struct: " + this.toString());
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
        StringBuilder stringBuilder = new StringBuilder("IdJournal(");
        stringBuilder.append("domain:");
        if (this.a == null) {
            stringBuilder.append("null");
        } else {
            stringBuilder.append(this.a);
        }
        if (this.h()) {
            stringBuilder.append(", ");
            stringBuilder.append("old_id:");
            if (this.b == null) {
                stringBuilder.append("null");
            } else {
                stringBuilder.append(this.b);
            }
        }
        stringBuilder.append(", ");
        stringBuilder.append("new_id:");
        if (this.c == null) {
            stringBuilder.append("null");
        } else {
            stringBuilder.append(this.c);
        }
        stringBuilder.append(", ");
        stringBuilder.append("ts:");
        stringBuilder.append(this.d);
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    private static class a
    extends cy<ba> {
        private a() {
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public void a(co co2, ba ba2) throws bv {
            co2.j();
            while (true) {
                cj cj2 = co2.l();
                if (cj2.b == 0) {
                    co2.k();
                    if (ba2.n()) break;
                    throw new cp("Required field 'ts' was not found in serialized data! Struct: " + this.toString());
                }
                switch (cj2.c) {
                    default: {
                        cr.a(co2, cj2.b);
                        break;
                    }
                    case 1: {
                        if (cj2.b == 11) {
                            ba2.a = co2.z();
                            ba2.a(true);
                            break;
                        }
                        cr.a(co2, cj2.b);
                        break;
                    }
                    case 2: {
                        if (cj2.b == 11) {
                            ba2.b = co2.z();
                            ba2.b(true);
                            break;
                        }
                        cr.a(co2, cj2.b);
                        break;
                    }
                    case 3: {
                        if (cj2.b == 11) {
                            ba2.c = co2.z();
                            ba2.c(true);
                            break;
                        }
                        cr.a(co2, cj2.b);
                        break;
                    }
                    case 4: {
                        if (cj2.b == 10) {
                            ba2.d = co2.x();
                            ba2.d(true);
                            break;
                        }
                        cr.a(co2, cj2.b);
                    }
                }
                co2.m();
            }
            ba2.o();
        }

        @Override
        public /* synthetic */ void a(co co2, bp bp2) throws bv {
            this.b(co2, (ba)bp2);
        }

        @Override
        public void b(co co2, ba ba2) throws bv {
            ba2.o();
            co2.a(f);
            if (ba2.a != null) {
                co2.a(g);
                co2.a(ba2.a);
                co2.c();
            }
            if (ba2.b != null && ba2.h()) {
                co2.a(h);
                co2.a(ba2.b);
                co2.c();
            }
            if (ba2.c != null) {
                co2.a(i);
                co2.a(ba2.c);
                co2.c();
            }
            co2.a(j);
            co2.a(ba2.d);
            co2.c();
            co2.d();
            co2.b();
        }

        @Override
        public /* synthetic */ void b(co co2, bp bp2) throws bv {
            this.a(co2, (ba)bp2);
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
    extends cz<ba> {
        private c() {
        }

        @Override
        public void a(co co2, ba ba2) throws bv {
            co2 = (cu)co2;
            ((ci)co2).a(ba2.a);
            ((ci)co2).a(ba2.c);
            ((ci)co2).a(ba2.d);
            BitSet bitSet = new BitSet();
            if (ba2.h()) {
                bitSet.set(0);
            }
            ((cu)co2).a(bitSet, 1);
            if (ba2.h()) {
                ((ci)co2).a(ba2.b);
            }
        }

        @Override
        public void b(co co2, ba ba2) throws bv {
            co2 = (cu)co2;
            ba2.a = ((ci)co2).z();
            ba2.a(true);
            ba2.c = ((ci)co2).z();
            ba2.c(true);
            ba2.d = ((ci)co2).x();
            ba2.d(true);
            if (((cu)co2).b(1).get(0)) {
                ba2.b = ((ci)co2).z();
                ba2.b(true);
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
        a(1, "domain"),
        b(2, "old_id"),
        c(3, "new_id"),
        d(4, "ts");

        private static final Map<String, e> e;
        private final short f;
        private final String g;

        static {
            e = new HashMap<String, e>();
            for (e e2 : EnumSet.allOf(e.class)) {
                e.put(e2.b(), e2);
            }
        }

        private e(short s2, String string3) {
            this.f = s2;
            this.g = string3;
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
                case 4: 
            }
            return d;
        }

        public static e a(String string2) {
            return e.get(string2);
        }

        public static e b(int n2) {
            e e2 = u.aly.ba$e.a(n2);
            if (e2 == null) {
                throw new IllegalArgumentException("Field " + n2 + " doesn't exist!");
            }
            return e2;
        }

        @Override
        public short a() {
            return this.f;
        }

        @Override
        public String b() {
            return this.g;
        }
    }
}

