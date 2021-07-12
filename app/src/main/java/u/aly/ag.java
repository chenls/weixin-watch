/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package u.aly;

import android.content.Context;
import com.umeng.analytics.g;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import u.aly.ae;
import u.aly.ar;
import u.aly.av;
import u.aly.bl;
import u.aly.f;
import u.aly.l;
import u.aly.m;
import u.aly.n;

public class ag {
    private final int a;
    private final int b;
    private final int c;
    private Context d;
    private ae e;

    public ag(Context context) {
        this.a = 128;
        this.b = 256;
        this.c = 10;
        if (context == null) {
            throw new RuntimeException("Context is null, can't track event");
        }
        this.d = context.getApplicationContext();
        this.e = ae.a(this.d);
    }

    private boolean a(String string2) {
        int n2;
        if (string2 != null && (n2 = string2.trim().getBytes().length) > 0 && n2 <= 128) {
            return true;
        }
        bl.e("Event id is empty or too long in tracking Event");
        return false;
    }

    private boolean a(Map<String, Object> object) {
        if (object == null || object.isEmpty()) {
            bl.e("map is null or empty in onEvent");
            return false;
        }
        for (Map.Entry entry : object.entrySet()) {
            if (!this.a((String)entry.getKey())) {
                return false;
            }
            if (entry.getValue() == null) {
                return false;
            }
            if (!(entry.getValue() instanceof String) || this.b(entry.getValue().toString())) continue;
            return false;
        }
        return true;
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean b(String string2) {
        if (string2 == null || string2.trim().getBytes().length <= 256) {
            return true;
        }
        bl.e("Event label or value is empty or too long in tracking Event");
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void a(String string2, String string3, long l2, int n2) {
        if (!this.a(string2) || !this.b(string3)) {
            return;
        }
        HashMap<String, String> hashMap = new HashMap<String, String>();
        String string4 = string3 == null ? "" : string3;
        hashMap.put(string2, string4);
        av.j j2 = new av.j();
        j2.c = string2;
        j2.d = System.currentTimeMillis();
        if (l2 > 0L) {
            j2.e = l2;
        }
        j2.a = 1;
        Map<String, Object> map = j2.f;
        string4 = string3;
        if (string3 == null) {
            string4 = "";
        }
        map.put(string2, string4);
        if (j2.b == null) {
            j2.b = ar.g(this.d);
        }
        this.e.a(j2);
    }

    public void a(String object, Map<String, Object> object2) {
        if (!this.a((String)object)) {
            return;
        }
        av.j j2 = new av.j();
        j2.c = object;
        j2.d = System.currentTimeMillis();
        j2.e = 0L;
        j2.a = 2;
        object = object2.entrySet().iterator();
        for (int i2 = 0; i2 < 10; ++i2) {
            if (!object.hasNext()) break;
            object2 = (Map.Entry)object.next();
            j2.f.put((String)object2.getKey(), object2.getValue());
            continue;
        }
        try {
            if (j2.b == null) {
                j2.b = ar.g(this.d);
            }
            this.e.a(j2);
            return;
        }
        catch (Exception exception) {
            bl.e("Exception occurred in Mobclick.onEvent(). ", exception);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void a(String iterator, Map<String, Object> entry, long l2) {
        try {
            if (!this.a((String)((Object)iterator))) {
                return;
            }
            if (!this.a((Map<String, Object>)((Object)entry))) return;
            av.j j2 = new av.j();
            j2.c = iterator;
            j2.d = System.currentTimeMillis();
            if (l2 > 0L) {
                j2.e = l2;
            }
            j2.a = 1;
            iterator = entry.entrySet().iterator();
            for (int i2 = 0; i2 < 10 && iterator.hasNext(); ++i2) {
                entry = iterator.next();
                j2.f.put((String)entry.getKey(), entry.getValue());
            }
            if (j2.b == null) {
                j2.b = ar.g(this.d);
            }
            this.e.a(j2);
            return;
        }
        catch (Exception exception) {
            bl.e("Exception occurred in Mobclick.onEvent(). ", exception);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean a(List<String> object, int n2, final String hashMap) {
        n n3 = n.a();
        if (object == null) {
            bl.e("cklist is null!");
            return true;
        }
        if (object.size() <= 0) {
            bl.e("the KeyList is null!");
            return false;
        }
        ArrayList<String> arrayList = new ArrayList<String>((Collection<String>)object);
        if (!n3.b((String)arrayList.get(0))) {
            bl.e("Primary key Invalid!");
            return false;
        }
        if (arrayList.size() > 8) {
            object = (String)arrayList.get(0);
            arrayList.clear();
            arrayList.add((String)object);
            arrayList.add("__cc");
            arrayList.add("illegal");
        } else if (!n3.a(arrayList)) {
            object = (String)arrayList.get(0);
            arrayList.clear();
            arrayList.add((String)object);
            arrayList.add("__cc");
            arrayList.add("illegal");
        } else if (!n3.b(arrayList)) {
            object = (String)arrayList.get(0);
            arrayList.clear();
            arrayList.add((String)object);
            arrayList.add("__cc");
            arrayList.add("illegal");
        } else {
            for (int i2 = 0; i2 < arrayList.size(); ++i2) {
                object = (String)arrayList.get(i2);
                if (((String)object).length() <= 16) continue;
                arrayList.remove(i2);
                arrayList.add(i2, ((String)object).substring(0, 16));
            }
        }
        if (!n3.a((String)((Object)hashMap))) {
            bl.e("label  Invalid!");
            object = "__illegal";
        } else {
            object = hashMap;
        }
        hashMap = new HashMap();
        hashMap.put(arrayList, new l(arrayList, n2, (String)object, System.currentTimeMillis()));
        com.umeng.analytics.f.b(new g(){

            @Override
            public void a() {
                m.a(ag.this.d).a(new f(){

                    @Override
                    public void a(Object object, boolean bl2) {
                        if (object.equals("success")) {
                            // empty if block
                        }
                    }
                }, hashMap);
            }
        });
        return true;
    }
}

