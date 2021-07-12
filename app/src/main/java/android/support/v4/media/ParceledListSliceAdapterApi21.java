/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.media.browse.MediaBrowser$MediaItem
 */
package android.support.v4.media;

import android.media.browse.MediaBrowser;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

class ParceledListSliceAdapterApi21 {
    private static Constructor sConstructor;

    /*
     * Unable to fully structure code
     */
    static {
        try {
            ParceledListSliceAdapterApi21.sConstructor = Class.forName("android.content.pm.ParceledListSlice").getConstructor(new Class[]{List.class});
            return;
        }
        catch (ClassNotFoundException var0) lbl-1000:
        // 2 sources

        {
            while (true) {
                var0_1.printStackTrace();
                return;
            }
        }
        catch (NoSuchMethodException var0_2) {
            ** continue;
        }
    }

    ParceledListSliceAdapterApi21() {
    }

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    static Object newInstance(List<MediaBrowser.MediaItem> var0) {
        try {
            var0 /* !! */  = ParceledListSliceAdapterApi21.sConstructor.newInstance(new Object[]{var0 /* !! */ });
            return var0 /* !! */ ;
        }
        catch (InstantiationException var0_1) lbl-1000:
        // 3 sources

        {
            while (true) {
                var0_2.printStackTrace();
                return null;
            }
        }
        catch (IllegalAccessException var0_3) {
            ** GOTO lbl-1000
        }
        catch (InvocationTargetException var0_4) {
            ** continue;
        }
    }
}

