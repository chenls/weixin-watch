/*
 * Decompiled with CFR 0.151.
 */
package u.aly;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import u.aly.ba;
import u.aly.bb;
import u.aly.bp;
import u.aly.bv;
import u.aly.bw;
import u.aly.cb;
import u.aly.cc;
import u.aly.cd;
import u.aly.ce;
import u.aly.cg;
import u.aly.ci;
import u.aly.cj;
import u.aly.ck;
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

public class bc
implements Serializable,
Cloneable,
bp<bc, e> {
    public static final Map<e, cb> d;
    private static final ct e;
    private static final cj f;
    private static final cj g;
    private static final cj h;
    private static final Map<Class<? extends cw>, cx> i;
    public Map<String, bb> a;
    public List<ba> b;
    public String c;
    private e[] j = new e[]{u.aly.bc$e.b, u.aly.bc$e.c};

    static {
        e = new ct("IdTracking");
        f = new cj("snapshots", 13, 1);
        g = new cj("journals", 15, 2);
        h = new cj("checksum", 11, 3);
        i = new HashMap<Class<? extends cw>, cx>();
        i.put(cy.class, new b());
        i.put(cz.class, new d());
        EnumMap<e, cb> enumMap = new EnumMap<e, cb>(e.class);
        enumMap.put(u.aly.bc$e.a, new cb("snapshots", 1, new ce(13, new cc(11), new cg(12, bb.class))));
        enumMap.put(u.aly.bc$e.b, new cb("journals", 2, new cd(15, new cg(12, ba.class))));
        enumMap.put(u.aly.bc$e.c, new cb("checksum", 2, new cc(11)));
        d = Collections.unmodifiableMap(enumMap);
        cb.a(bc.class, d);
    }

    public bc() {
    }

    public bc(Map<String, bb> map) {
        this();
        this.a = map;
    }

    public bc(bc bc2) {
        Cloneable cloneable;
        if (bc2.f()) {
            cloneable = new HashMap();
            for (Map.Entry entry : bc2.a.entrySet()) {
                cloneable.put((String)entry.getKey(), new bb((bb)entry.getValue()));
            }
            this.a = cloneable;
        }
        if (bc2.k()) {
            cloneable = new ArrayList();
            Iterator<Object> iterator = bc2.b.iterator();
            while (iterator.hasNext()) {
                cloneable.add(new ba((ba)iterator.next()));
            }
            this.b = cloneable;
        }
        if (bc2.n()) {
            this.c = bc2.c;
        }
    }

    private void a(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        try {
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
        return u.aly.bc$e.a(n2);
    }

    public bc a() {
        return new bc(this);
    }

    public bc a(String string2) {
        this.c = string2;
        return this;
    }

    public bc a(List<ba> list) {
        this.b = list;
        return this;
    }

    public bc a(Map<String, bb> map) {
        this.a = map;
        return this;
    }

    public void a(String string2, bb bb2) {
        if (this.a == null) {
            this.a = new HashMap<String, bb>();
        }
        this.a.put(string2, bb2);
    }

    public void a(ba ba2) {
        if (this.b == null) {
            this.b = new ArrayList<ba>();
        }
        this.b.add(ba2);
    }

    @Override
    public void a(co co2) throws bv {
        i.get(co2.D()).b().b(co2, (bc)this);
    }

    public void a(boolean bl2) {
        if (!bl2) {
            this.a = null;
        }
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
    }

    @Override
    public void b(co co2) throws bv {
        i.get(co2.D()).b().a(co2, (bc)this);
    }

    public void b(boolean bl2) {
        if (!bl2) {
            this.b = null;
        }
    }

    public int c() {
        if (this.a == null) {
            return 0;
        }
        return this.a.size();
    }

    public void c(boolean bl2) {
        if (!bl2) {
            this.c = null;
        }
    }

    public Map<String, bb> d() {
        return this.a;
    }

    public void e() {
        this.a = null;
    }

    public boolean f() {
        return this.a != null;
    }

    public int g() {
        if (this.b == null) {
            return 0;
        }
        return this.b.size();
    }

    public Iterator<ba> h() {
        if (this.b == null) {
            return null;
        }
        return this.b.iterator();
    }

    public List<ba> i() {
        return this.b;
    }

    public void j() {
        this.b = null;
    }

    public boolean k() {
        return this.b != null;
    }

    public String l() {
        return this.c;
    }

    public void m() {
        this.c = null;
    }

    public boolean n() {
        return this.c != null;
    }

    public void o() throws bv {
        if (this.a == null) {
            throw new cp("Required field 'snapshots' was not present! Struct: " + this.toString());
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
        StringBuilder stringBuilder = new StringBuilder("IdTracking(");
        stringBuilder.append("snapshots:");
        if (this.a == null) {
            stringBuilder.append("null");
        } else {
            stringBuilder.append(this.a);
        }
        if (this.k()) {
            stringBuilder.append(", ");
            stringBuilder.append("journals:");
            if (this.b == null) {
                stringBuilder.append("null");
            } else {
                stringBuilder.append(this.b);
            }
        }
        if (this.n()) {
            stringBuilder.append(", ");
            stringBuilder.append("checksum:");
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
    extends cy<bc> {
        private a() {
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public void a(co co2, bc bc2) throws bv {
            co2.j();
            while (true) {
                Object object = co2.l();
                if (((cj)object).b == 0) {
                    co2.k();
                    bc2.o();
                    return;
                }
                switch (((cj)object).c) {
                    default: {
                        cr.a(co2, ((cj)object).b);
                        break;
                    }
                    case 1: {
                        Object object2;
                        int n2;
                        if (((cj)object).b == 13) {
                            object = co2.n();
                            bc2.a = new HashMap<String, bb>(((cl)object).c * 2);
                            for (n2 = 0; n2 < ((cl)object).c; ++n2) {
                                object2 = co2.z();
                                bb bb2 = new bb();
                                bb2.a(co2);
                                bc2.a.put((String)object2, bb2);
                            }
                            co2.o();
                            bc2.a(true);
                            break;
                        }
                        cr.a(co2, ((cj)object).b);
                        break;
                    }
                    case 2: {
                        Object object2;
                        int n2;
                        if (((cj)object).b == 15) {
                            object = co2.p();
                            bc2.b = new ArrayList<ba>(((ck)object).b);
                            for (n2 = 0; n2 < ((ck)object).b; ++n2) {
                                object2 = new ba();
                                ((ba)object2).a(co2);
                                bc2.b.add((ba)object2);
                            }
                            co2.q();
                            bc2.b(true);
                            break;
                        }
                        cr.a(co2, ((cj)object).b);
                        break;
                    }
                    case 3: {
                        if (((cj)object).b == 11) {
                            bc2.c = co2.z();
                            bc2.c(true);
                            break;
                        }
                        cr.a(co2, ((cj)object).b);
                    }
                }
                co2.m();
            }
        }

        @Override
        public /* synthetic */ void a(co co2, bp bp2) throws bv {
            this.b(co2, (bc)bp2);
        }

        @Override
        public void b(co co2, bc bc2) throws bv {
            bc2.o();
            co2.a(e);
            if (bc2.a != null) {
                co2.a(f);
                co2.a(new cl(11, 12, bc2.a.size()));
                for (Map.Entry<String, bb> entry : bc2.a.entrySet()) {
                    co2.a(entry.getKey());
                    entry.getValue().b(co2);
                }
                co2.e();
                co2.c();
            }
            if (bc2.b != null && bc2.k()) {
                co2.a(g);
                co2.a(new ck(12, bc2.b.size()));
                Iterator<Object> iterator = bc2.b.iterator();
                while (iterator.hasNext()) {
                    ((ba)iterator.next()).b(co2);
                }
                co2.f();
                co2.c();
            }
            if (bc2.c != null && bc2.n()) {
                co2.a(h);
                co2.a(bc2.c);
                co2.c();
            }
            co2.d();
            co2.b();
        }

        @Override
        public /* synthetic */ void b(co co2, bp bp2) throws bv {
            this.a(co2, (bc)bp2);
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
    extends cz<bc> {
        private c() {
        }

        @Override
        public void a(co co2, bc bc2) throws bv {
            co2 = (cu)co2;
            ((ci)co2).a(bc2.a.size());
            for (Map.Entry<String, bb> entry : bc2.a.entrySet()) {
                ((ci)co2).a(entry.getKey());
                entry.getValue().b(co2);
            }
            Iterator<ba> iterator = new BitSet();
            if (bc2.k()) {
                ((BitSet)((Object)iterator)).set(0);
            }
            if (bc2.n()) {
                ((BitSet)((Object)iterator)).set(1);
            }
            ((cu)co2).a((BitSet)((Object)iterator), 2);
            if (bc2.k()) {
                ((ci)co2).a(bc2.b.size());
                iterator = bc2.b.iterator();
                while (iterator.hasNext()) {
                    iterator.next().b(co2);
                }
            }
            if (bc2.n()) {
                ((ci)co2).a(bc2.c);
            }
        }

        @Override
        public void b(co co2, bc bc2) throws bv {
            bp<bb, bb.e> bp2;
            Object object;
            int n2;
            int n3 = 0;
            co2 = (cu)co2;
            Object object2 = new cl(11, 12, ((ci)co2).w());
            bc2.a = new HashMap<String, bb>(((cl)object2).c * 2);
            for (n2 = 0; n2 < ((cl)object2).c; ++n2) {
                object = ((ci)co2).z();
                bp2 = new bb();
                ((bb)bp2).a(co2);
                bc2.a.put((String)object, (bb)bp2);
            }
            bc2.a(true);
            object2 = ((cu)co2).b(2);
            if (((BitSet)object2).get(0)) {
                object = new ck(12, ((ci)co2).w());
                bc2.b = new ArrayList<ba>(((ck)object).b);
                for (n2 = n3; n2 < ((ck)object).b; ++n2) {
                    bp2 = new ba();
                    ((ba)bp2).a(co2);
                    bc2.b.add((ba)bp2);
                }
                bc2.b(true);
            }
            if (((BitSet)object2).get(1)) {
                bc2.c = ((ci)co2).z();
                bc2.c(true);
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
        a(1, "snapshots"),
        b(2, "journals"),
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
            e e2 = u.aly.bc$e.a(n2);
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

