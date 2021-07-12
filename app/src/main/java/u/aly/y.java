/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package u.aly;

import android.content.Context;
import u.aly.bj;
import u.aly.r;

public class y
extends r {
    private static final String a = "mac";
    private Context b;

    public y(Context context) {
        super(a);
        this.b = context;
    }

    @Override
    public String f() {
        try {
            String string2 = bj.u(this.b);
            return string2;
        }
        catch (Exception exception) {
            return null;
        }
    }
}

