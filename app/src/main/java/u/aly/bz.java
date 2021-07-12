/*
 * Decompiled with CFR 0.151.
 */
package u.aly;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import u.aly.bp;
import u.aly.bq;
import u.aly.bv;
import u.aly.bw;
import u.aly.cj;
import u.aly.co;
import u.aly.cp;
import u.aly.ct;
import u.aly.cw;
import u.aly.cx;
import u.aly.cy;
import u.aly.cz;

public abstract class bz<T extends bz<?, ?>, F extends bw>
implements bp<T, F> {
    private static final Map<Class<? extends cw>, cx> c = new HashMap<Class<? extends cw>, cx>();
    protected Object a;
    protected F b;

    static {
        c.put(cy.class, new b());
        c.put(cz.class, new d());
    }

    protected bz() {
        this.b = null;
        this.a = null;
    }

    protected bz(F f2, Object object) {
        this.a(f2, object);
    }

    protected bz(bz<T, F> bz2) {
        if (!bz2.getClass().equals(this.getClass())) {
            throw new ClassCastException();
        }
        this.b = bz2.b;
        this.a = bz.a(bz2.a);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static Object a(Object object) {
        if (object instanceof bp) {
            return ((bp)object).p();
        }
        if (object instanceof ByteBuffer) {
            return bq.d((ByteBuffer)object);
        }
        if (object instanceof List) {
            return bz.a((List)object);
        }
        if (object instanceof Set) {
            return bz.a((Set)object);
        }
        Object object2 = object;
        if (!(object instanceof Map)) return object2;
        return bz.a((Map)object);
    }

    private static List a(List object) {
        ArrayList<Object> arrayList = new ArrayList<Object>(object.size());
        object = object.iterator();
        while (object.hasNext()) {
            arrayList.add(bz.a(object.next()));
        }
        return arrayList;
    }

    private static Map a(Map<Object, Object> object) {
        HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
        for (Map.Entry entry : object.entrySet()) {
            hashMap.put(bz.a(entry.getKey()), bz.a(entry.getValue()));
        }
        return hashMap;
    }

    private static Set a(Set object) {
        HashSet<Object> hashSet = new HashSet<Object>();
        object = object.iterator();
        while (object.hasNext()) {
            hashSet.add(bz.a(object.next()));
        }
        return hashSet;
    }

    public Object a(int n2) {
        return this.a(this.a((short)n2));
    }

    public Object a(F f2) {
        if (f2 != this.b) {
            throw new IllegalArgumentException("Cannot get the value of field " + f2 + " because union's set field is " + this.b);
        }
        return this.c();
    }

    protected abstract Object a(co var1, cj var2) throws bv;

    protected abstract Object a(co var1, short var2) throws bv;

    public F a() {
        return this.b;
    }

    protected abstract F a(short var1);

    public void a(int n2, Object object) {
        this.a(this.a((short)n2), object);
    }

    public void a(F f2, Object object) {
        this.b(f2, object);
        this.b = f2;
        this.a = object;
    }

    @Override
    public void a(co co2) throws bv {
        c.get(co2.D()).b().b(co2, (bz)this);
    }

    @Override
    public final void b() {
        this.b = null;
        this.a = null;
    }

    protected abstract void b(F var1, Object var2) throws ClassCastException;

    @Override
    public void b(co co2) throws bv {
        c.get(co2.D()).b().a(co2, (bz)this);
    }

    public boolean b(F f2) {
        return this.b == f2;
    }

    public Object c() {
        return this.a;
    }

    protected abstract cj c(F var1);

    protected abstract void c(co var1) throws bv;

    public boolean c(int n2) {
        return this.b(this.a((short)n2));
    }

    protected abstract void d(co var1) throws bv;

    public boolean d() {
        return this.b != null;
    }

    protected abstract ct e();

    /*
     * Enabled aggressive block sorting
     */
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<");
        stringBuilder.append(this.getClass().getSimpleName());
        stringBuilder.append(" ");
        if (this.a() != null) {
            Object object = this.c();
            stringBuilder.append(this.c(this.a()).a);
            stringBuilder.append(":");
            if (object instanceof ByteBuffer) {
                bq.a((ByteBuffer)object, stringBuilder);
            } else {
                stringBuilder.append(object.toString());
            }
        }
        stringBuilder.append(">");
        return stringBuilder.toString();
    }

    private static class a
    extends cy<bz> {
        private a() {
        }

        @Override
        public /* synthetic */ void a(co co2, bp bp2) throws bv {
            this.b(co2, (bz)bp2);
        }

        @Override
        public void a(co co2, bz bz2) throws bv {
            bz2.b = null;
            bz2.a = null;
            co2.j();
            cj cj2 = co2.l();
            bz2.a = bz2.a(co2, cj2);
            if (bz2.a != null) {
                bz2.b = bz2.a(cj2.c);
            }
            co2.m();
            co2.l();
            co2.k();
        }

        @Override
        public /* synthetic */ void b(co co2, bp bp2) throws bv {
            this.a(co2, (bz)bp2);
        }

        @Override
        public void b(co co2, bz bz2) throws bv {
            if (bz2.a() == null || bz2.c() == null) {
                throw new cp("Cannot write a TUnion with no set value!");
            }
            co2.a(bz2.e());
            co2.a(bz2.c(bz2.b));
            bz2.c(co2);
            co2.c();
            co2.d();
            co2.b();
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
    extends cz<bz> {
        private c() {
        }

        @Override
        public /* synthetic */ void a(co co2, bp bp2) throws bv {
            this.b(co2, (bz)bp2);
        }

        @Override
        public void a(co co2, bz bz2) throws bv {
            bz2.b = null;
            bz2.a = null;
            short s2 = co2.v();
            bz2.a = bz2.a(co2, s2);
            if (bz2.a != null) {
                bz2.b = bz2.a(s2);
            }
        }

        @Override
        public /* synthetic */ void b(co co2, bp bp2) throws bv {
            this.a(co2, (bz)bp2);
        }

        @Override
        public void b(co co2, bz bz2) throws bv {
            if (bz2.a() == null || bz2.c() == null) {
                throw new cp("Cannot write a TUnion with no set value!");
            }
            co2.a(bz2.b.a());
            bz2.d(co2);
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
}

