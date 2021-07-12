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

public class aa
extends r {
    private static final String a = "idmd5";
    private Context b;

    public aa(Context context) {
        super(a);
        this.b = context;
    }

    @Override
    public String f() {
        return bj.g(this.b);
    }
}

