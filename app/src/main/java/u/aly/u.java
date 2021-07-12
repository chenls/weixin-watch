/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package u.aly;

import android.content.Context;
import u.aly.bh;
import u.aly.r;

public class u
extends r {
    private static final String a = "idfa";
    private Context b;

    public u(Context context) {
        super(a);
        this.b = context;
    }

    @Override
    public String f() {
        String string2;
        String string3 = string2 = bh.a(this.b);
        if (string2 == null) {
            string3 = "";
        }
        return string3;
    }
}

