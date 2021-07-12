/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.os.Build
 *  android.os.Build$VERSION
 */
package u.aly;

import android.os.Build;
import u.aly.r;

public class z
extends r {
    private static final String a = "serial";

    public z() {
        super(a);
    }

    @Override
    public String f() {
        if (Build.VERSION.SDK_INT >= 9) {
            return Build.SERIAL;
        }
        return null;
    }
}

