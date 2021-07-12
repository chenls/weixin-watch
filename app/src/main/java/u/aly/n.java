/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.text.TextUtils
 */
package u.aly;

import android.text.TextUtils;
import java.util.List;

public class n {
    private n() {
    }

    public static n a() {
        return a.a;
    }

    private boolean a(String string2, int n2) {
        for (int i2 = 0; i2 < string2.length(); ++i2) {
            if (string2.charAt(i2) >= n2) continue;
            return false;
        }
        return true;
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean a(String string2) {
        block5: {
            block4: {
                if ("".equals(string2)) break block4;
                if (string2 == null) {
                    return false;
                }
                if (string2.getBytes().length >= 160 || !this.a(string2, 48)) break block5;
            }
            return true;
        }
        return false;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean a(List<String> list) {
        boolean bl2 = true;
        if (list == null) {
            return false;
        }
        boolean bl3 = bl2;
        if (list.size() <= 1) return bl3;
        int n2 = 1;
        while (true) {
            bl3 = bl2;
            if (n2 >= list.size()) return bl3;
            if (TextUtils.isEmpty((CharSequence)list.get(n2))) {
                return false;
            }
            if (!this.a(list.get(n2), 48)) {
                return false;
            }
            ++n2;
        }
    }

    public int b() {
        return 8;
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean b(String string2) {
        return !TextUtils.isEmpty((CharSequence)string2) && string2.length() < 16 && this.a(string2, 48);
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean b(List<String> iterator) {
        block4: {
            block3: {
                if (iterator == null || iterator.size() <= 0) break block3;
                iterator = iterator.iterator();
                int n2 = 0;
                while (iterator.hasNext()) {
                    n2 = ((String)iterator.next()).getBytes().length + n2;
                }
                if (n2 < 256) break block4;
            }
            return false;
        }
        return true;
    }

    public int c() {
        return 128;
    }

    public int d() {
        return 512;
    }

    private static class a {
        private static final n a = new n();

        private a() {
        }
    }
}

