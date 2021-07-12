/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.text.TextUtils
 */
package u.aly;

import android.text.TextUtils;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import u.aly.f;
import u.aly.k;

public class p
implements Serializable {
    private static final long a = 1L;
    private Map<String, k> b = new HashMap<String, k>();

    private void b(String string2) {
        k k2 = new k(string2, System.currentTimeMillis(), 1L);
        this.b.put(string2, k2);
    }

    private void b(k k2) {
        k k3 = this.b.get(k2.c()).a(k2);
        this.b.put(k2.c(), k3);
    }

    private void c(String string2) {
        k k2 = this.b.get(string2).a();
        this.b.put(string2, k2);
    }

    public Map<String, k> a() {
        return this.b;
    }

    public void a(Map<String, k> map) {
        this.b = map;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void a(f f2, String string2) {
        if (this.b.containsKey(string2)) {
            this.c(string2);
        } else {
            this.b(string2);
        }
        f2.a(this, false);
    }

    public void a(k k2) {
        if (this.a(k2.c())) {
            this.b(k2);
            return;
        }
        this.b.put(k2.c(), k2);
    }

    public boolean a(String string2) {
        if (TextUtils.isEmpty((CharSequence)string2)) {
            return false;
        }
        Iterator<Map.Entry<String, k>> iterator = this.b.entrySet().iterator();
        while (iterator.hasNext()) {
            if (!iterator.next().getKey().equals(string2)) continue;
            return true;
        }
        return false;
    }

    public void b() {
        this.b.clear();
    }
}

