/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.SharedPreferences
 *  android.text.TextUtils
 *  org.json.JSONArray
 */
package u.aly;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONArray;
import u.aly.ap;
import u.aly.av;
import u.aly.bl;

public class at {
    private static final String a = "activities";
    private final Map<String, Long> b = new HashMap<String, Long>();
    private final ArrayList<av.l> c = new ArrayList();

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void a(SharedPreferences object, av.o o2) {
        if (TextUtils.isEmpty((CharSequence)(object = object.getString(a, "")))) return;
        try {
            object = ((String)object).split(";");
            for (int i2 = 0; i2 < ((Object)object).length; ++i2) {
                JSONArray jSONArray = new JSONArray((String)object[i2]);
                av.l l2 = new av.l();
                l2.a = jSONArray.getString(0);
                l2.b = jSONArray.getInt(1);
                o2.h.add(l2);
            }
            return;
        }
        catch (Exception exception) {
            bl.e(exception);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public void a() {
        String string2 = null;
        long l2 = 0L;
        Map<String, Long> map = this.b;
        // MONITORENTER : map
        Iterator<Map.Entry<String, Long>> iterator = this.b.entrySet().iterator();
        while (true) {
            if (!iterator.hasNext()) {
                // MONITOREXIT : map
                if (string2 == null) return;
                this.b(string2);
                return;
            }
            Map.Entry<String, Long> entry = iterator.next();
            if (entry.getValue() <= l2) continue;
            l2 = entry.getValue();
            string2 = entry.getKey();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void a(Context context) {
        Object object = ap.a(context);
        context = object.edit();
        if (this.c.size() > 0) {
            Object object2 = object.getString(a, "");
            object = new StringBuilder();
            if (!TextUtils.isEmpty((CharSequence)object2)) {
                ((StringBuilder)object).append((String)object2);
                ((StringBuilder)object).append(";");
            }
            object2 = this.c;
            synchronized (object2) {
                for (av.l l2 : this.c) {
                    ((StringBuilder)object).append(String.format("[\"%s\",%d]", l2.a, l2.b));
                    ((StringBuilder)object).append(";");
                }
                this.c.clear();
            }
            ((StringBuilder)object).deleteCharAt(((StringBuilder)object).length() - 1);
            context.remove(a);
            context.putString(a, ((StringBuilder)object).toString());
        }
        context.commit();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void a(String string2) {
        if (!TextUtils.isEmpty((CharSequence)string2)) {
            Map<String, Long> map = this.b;
            synchronized (map) {
                this.b.put(string2, System.currentTimeMillis());
                return;
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public void b(String string2) {
        if (TextUtils.isEmpty((CharSequence)string2)) return;
        Object object = this.b;
        // MONITORENTER : object
        Serializable serializable = this.b.remove(string2);
        // MONITOREXIT : object
        if (serializable == null) {
            bl.e("please call 'onPageStart(%s)' before onPageEnd", string2);
            return;
        }
        long l2 = System.currentTimeMillis();
        long l3 = serializable;
        object = this.c;
        // MONITORENTER : object
        serializable = new av.l();
        ((av.l)serializable).a = string2;
        ((av.l)serializable).b = l2 - l3;
        this.c.add((av.l)serializable);
        // MONITOREXIT : object
    }
}

