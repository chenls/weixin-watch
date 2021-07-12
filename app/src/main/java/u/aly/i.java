/*
 * Decompiled with CFR 0.151.
 */
package u.aly;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import u.aly.d;
import u.aly.f;
import u.aly.l;
import u.aly.n;
import u.aly.q;

public class i
implements Serializable {
    private static final long a = 1L;
    private List<String> b = new ArrayList<String>();
    private List<String> c = new ArrayList<String>();
    private long d = 0L;
    private long e = 0L;
    private long f = 0L;
    private String g = null;

    public i() {
    }

    public i(List<String> list, long l2, long l3, long l4, List<String> list2, String string2) {
        this.b = list;
        this.c = list2;
        this.d = l2;
        this.e = l3;
        this.f = l4;
        this.g = string2;
    }

    public String a() {
        return u.aly.d.a(this.b);
    }

    public void a(long l2) {
        this.d = l2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void a(String string2) {
        int n2 = 0;
        try {
            if (this.c.size() < n.a().b()) {
                this.c.add(string2);
            } else {
                this.c.remove(this.c.get(0));
                this.c.add(string2);
            }
            if (this.c.size() <= n.a().b()) return;
            while (n2 < this.c.size() - n.a().b()) {
                this.c.remove(this.c.get(0));
                ++n2;
            }
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void a(List<String> list) {
        this.b = list;
    }

    public void a(f f2, l l2) {
        this.a(l2.b());
        ++this.f;
        this.e += l2.c();
        this.d += l2.d();
        f2.a(this, false);
    }

    public void a(l l2) {
        this.f = 1L;
        this.b = l2.a();
        this.a(l2.b());
        this.e = l2.c();
        this.d = System.currentTimeMillis();
        this.g = q.a(System.currentTimeMillis());
    }

    public List<String> b() {
        return this.b;
    }

    public void b(long l2) {
        this.e = l2;
    }

    public void b(String string2) {
        this.g = string2;
    }

    public void b(List<String> list) {
        this.c = list;
    }

    public String c() {
        return u.aly.d.a(this.c);
    }

    public void c(long l2) {
        this.f = l2;
    }

    public List<String> d() {
        return this.c;
    }

    public long e() {
        return this.d;
    }

    public long f() {
        return this.e;
    }

    public long g() {
        return this.f;
    }

    public String h() {
        return this.g;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[key: ").append(this.b).append("] [label: ").append(this.c).append("][ totalTimeStamp").append(this.g).append("][ value").append(this.e).append("][ count").append(this.f).append("][ timeWindowNum").append(this.g).append("]");
        return stringBuffer.toString();
    }
}

