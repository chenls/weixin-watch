/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.ContentResolver
 *  android.content.Context
 *  android.provider.Settings$Secure
 */
package u.aly;

import android.content.ContentResolver;
import android.content.Context;
import android.provider.Settings;
import u.aly.r;

public class s
extends r {
    private static final String a = "android_id";
    private Context b;

    public s(Context context) {
        super(a);
        this.b = context;
    }

    @Override
    public String f() {
        try {
            String string2 = Settings.Secure.getString((ContentResolver)this.b.getContentResolver(), (String)a);
            return string2;
        }
        catch (Exception exception) {
            return null;
        }
    }
}

