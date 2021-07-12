/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.app.Activity
 *  android.view.View
 */
package android.support.wearable.view;

import android.annotation.TargetApi;
import android.app.Activity;
import android.view.View;

@TargetApi(value=20)
class Func {
    Func() {
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    static float clamp(float f2, int n2, int n3) {
        if (f2 < (float)n2) {
            return n2;
        }
        float f3 = f2;
        if (!(f2 > (float)n3)) return f3;
        return n3;
    }

    static int clamp(int n2, int n3, int n4) {
        if (n2 < n3) {
            return n3;
        }
        if (n2 > n4) {
            return n4;
        }
        return n2;
    }

    static boolean getWindowOverscan(View view) {
        block3: {
            boolean bl2;
            block2: {
                view = view.getContext();
                bl2 = false;
                if (!(view instanceof Activity)) break block2;
                if ((0x2000000 & ((Activity)view).getWindow().getAttributes().flags) == 0) break block3;
                bl2 = true;
            }
            return bl2;
        }
        return false;
    }
}

