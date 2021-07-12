/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.os.Parcelable
 */
package android.support.v4.app;

import android.os.Bundle;
import android.os.Parcelable;
import java.util.Arrays;

class BundleUtil {
    BundleUtil() {
    }

    public static Bundle[] getBundleArrayFromBundle(Bundle bundle, String string2) {
        Parcelable[] parcelableArray = bundle.getParcelableArray(string2);
        if (parcelableArray instanceof Bundle[] || parcelableArray == null) {
            return (Bundle[])parcelableArray;
        }
        parcelableArray = (Bundle[])Arrays.copyOf(parcelableArray, parcelableArray.length, Bundle[].class);
        bundle.putParcelableArray(string2, parcelableArray);
        return parcelableArray;
    }
}

