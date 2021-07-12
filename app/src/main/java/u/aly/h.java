/*
 * Decompiled with CFR 0.151.
 */
package u.aly;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import u.aly.bl;
import u.aly.d;
import u.aly.f;
import u.aly.i;
import u.aly.l;

public class h
implements Serializable {
    private static final long a = 1L;
    private Map<List<String>, i> b = new HashMap<List<String>, i>();
    private long c = 0L;

    private void a(f f2, l l2, List<String> list) {
        if (this.a(list)) {
            this.a(f2, l2);
            return;
        }
        this.a(f2, list, l2);
    }

    private void a(i i2, i i3) {
        i3.c(i3.g() + i2.g());
        i3.b(i3.f() + i2.f());
        i3.a(i3.e() + i2.e());
        for (int i4 = 0; i4 < i2.d().size(); ++i4) {
            i3.a(i2.d().get(i4));
        }
    }

    private void b(Map<List<String>, i> object) {
        new ArrayList();
        new ArrayList();
        object = this.b.entrySet().iterator();
        while (object.hasNext()) {
            Map.Entry entry = (Map.Entry)object.next();
            List list = (List)entry.getKey();
            Iterator<Map.Entry<List<String>, i>> iterator = this.b.entrySet().iterator();
            while (object.hasNext()) {
                Object object2 = iterator.next();
                Object object3 = (List)entry.getKey();
                if (!list.equals(object3)) {
                    this.b.put((List<String>)object3, object2.getValue());
                    continue;
                }
                object3 = (i)entry.getValue();
                object2 = object2.getValue();
                this.a((i)object3, (i)object2);
                this.b.remove(list);
                this.b.put(list, (i)object2);
            }
        }
    }

    public Map<List<String>, i> a() {
        return this.b;
    }

    public void a(long l2) {
        this.c = l2;
    }

    public void a(Map<List<String>, i> map) {
        if (this.b.size() <= 0) {
            this.b = map;
            return;
        }
        this.b(map);
    }

    public void a(f f2) {
        Iterator<List<String>> iterator = this.b.keySet().iterator();
        while (true) {
            List<String> list;
            block4: {
                block3: {
                    if (!iterator.hasNext()) break block3;
                    list = iterator.next();
                    if (!f2.a()) break block4;
                }
                return;
            }
            f2.a(this.b.get(list), false);
        }
    }

    public void a(f f2, List<String> list, l l2) {
        i i2 = new i();
        i2.a(l2);
        this.b.put(list, i2);
        f2.a(this, false);
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void a(final f f2, l l2) {
        try {
            if (this.a(l2.a())) {
                i i2 = this.b.get(l2.a());
                if (i2 != null) {
                    i2.a(new f(){

                        @Override
                        public void a(Object object, boolean bl2) {
                            object = (i)object;
                            h.this.b.remove(((i)object).a());
                            h.this.b.put(((i)object).b(), object);
                            f2.a(this, false);
                        }
                    }, l2);
                    return;
                }
                this.a(f2, l2.a(), l2);
                return;
            }
        }
        catch (Exception exception) {
            bl.e("aggregated faild!");
            return;
        }
        {
            this.a(f2, l2.a(), l2);
            return;
        }
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void a(f f2, l l2, List<String> list, List<String> list2) {
        while (true) {
            try {
                if (list.size() < 1) break;
                if (list.size() == 1) {
                    if (!this.a(list2, list)) {
                        f2.a(false, false);
                        return;
                    }
                    this.a(f2, l2, list);
                    return;
                }
            }
            catch (Exception exception) {
                bl.e("overFlowAggregated faild");
                return;
            }
            {
                if (this.a(list2, list)) {
                    this.a(f2, l2, list);
                    return;
                }
                list.remove(list.size() - 1);
                continue;
            }
            break;
        }
    }

    public boolean a(List<?> list) {
        return this.b != null && this.b.containsKey(list);
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean a(List<String> list, List<String> list2) {
        ArrayList<List<String>> arrayList;
        block4: {
            block3: {
                if (list == null || list.size() == 0) break block3;
                arrayList = new ArrayList<List<String>>();
                for (int i2 = 0; i2 < list.size() - 1; ++i2) {
                    arrayList.add(d.a(list.get(i2)));
                }
                if (list != null && list.size() != 0) break block4;
            }
            return false;
        }
        return arrayList.contains(list2);
    }

    public long b() {
        return this.c;
    }

    public int c() {
        if (this.b != null) {
            return this.b.size();
        }
        return 0;
    }

    public void d() {
        this.b.clear();
    }
}

