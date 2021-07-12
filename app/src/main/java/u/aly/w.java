/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.telephony.TelephonyManager
 */
package u.aly;

import android.content.Context;
import android.telephony.TelephonyManager;
import u.aly.bj;
import u.aly.r;

public class w
extends r {
    private static final String a = "imei";
    private Context b;

    public w(Context context) {
        super(a);
        this.b = context;
    }

    @Override
    public String f() {
        Object object = (TelephonyManager)this.b.getSystemService("phone");
        if (object == null) {
            // empty if block
        }
        try {
            if (bj.a(this.b, "android.permission.READ_PHONE_STATE")) {
                object = object.getDeviceId();
                return object;
            }
        }
        catch (Exception exception) {
            return null;
        }
        return null;
    }
}

