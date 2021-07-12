/*
 * Decompiled with CFR 0.151.
 */
package u.aly;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import u.aly.bp;
import u.aly.bw;
import u.aly.cc;

public class cb
implements Serializable {
    private static Map<Class<? extends bp>, Map<? extends bw, cb>> d = new HashMap<Class<? extends bp>, Map<? extends bw, cb>>();
    public final String a;
    public final byte b;
    public final cc c;

    public cb(String string2, byte by2, cc cc2) {
        this.a = string2;
        this.b = by2;
        this.c = cc2;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static Map<? extends bw, cb> a(Class<? extends bp> clazz) {
        if (d.containsKey(clazz)) return d.get(clazz);
        try {
            clazz.newInstance();
        }
        catch (InstantiationException instantiationException) {
            throw new RuntimeException("InstantiationException for TBase class: " + clazz.getName() + ", message: " + instantiationException.getMessage());
        }
        catch (IllegalAccessException illegalAccessException) {
            throw new RuntimeException("IllegalAccessException for TBase class: " + clazz.getName() + ", message: " + illegalAccessException.getMessage());
        }
        return d.get(clazz);
    }

    public static void a(Class<? extends bp> clazz, Map<? extends bw, cb> map) {
        d.put(clazz, map);
    }
}

