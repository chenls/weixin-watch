/*
 * Decompiled with CFR 0.151.
 */
package com.google.android.gms.common.internal;

import com.google.android.gms.common.internal.zzx;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class zzw {
    public static boolean equal(Object object, Object object2) {
        return object == object2 || object != null && object.equals(object2);
    }

    public static int hashCode(Object ... objectArray) {
        return Arrays.hashCode(objectArray);
    }

    public static zza zzy(Object object) {
        return new zza(object);
    }

    public static final class zza {
        private final Object zzML;
        private final List<String> zzamp;

        private zza(Object object) {
            this.zzML = zzx.zzz(object);
            this.zzamp = new ArrayList<String>();
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder(100).append(this.zzML.getClass().getSimpleName()).append('{');
            int n2 = this.zzamp.size();
            for (int i2 = 0; i2 < n2; ++i2) {
                stringBuilder.append(this.zzamp.get(i2));
                if (i2 >= n2 - 1) continue;
                stringBuilder.append(", ");
            }
            return stringBuilder.append('}').toString();
        }

        public zza zzg(String string2, Object object) {
            this.zzamp.add(zzx.zzz(string2) + "=" + String.valueOf(object));
            return this;
        }
    }
}

