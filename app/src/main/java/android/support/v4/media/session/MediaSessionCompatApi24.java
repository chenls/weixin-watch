/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.media.session.MediaSession
 *  android.net.Uri
 *  android.os.Bundle
 *  android.util.Log
 */
package android.support.v4.media.session;

import android.media.session.MediaSession;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.media.session.MediaSessionCompatApi23;
import android.util.Log;
import java.lang.reflect.InvocationTargetException;

class MediaSessionCompatApi24 {
    private static final String TAG = "MediaSessionCompatApi24";

    MediaSessionCompatApi24() {
    }

    public static Object createCallback(Callback callback) {
        return new CallbackProxy<Callback>(callback);
    }

    /*
     * Unable to fully structure code
     */
    public static String getCallingPackage(Object var0) {
        var0 = (MediaSession)var0;
        try {
            var0 = (String)var0.getClass().getMethod("getCallingPackage", new Class[0]).invoke(var0, new Object[0]);
            return var0;
        }
        catch (IllegalAccessException var0_1) lbl-1000:
        // 3 sources

        {
            while (true) {
                Log.e((String)"MediaSessionCompatApi24", (String)"Cannot execute MediaSession.getCallingPackage()", (Throwable)var0_2);
                return null;
            }
        }
        catch (NoSuchMethodException var0_3) {
            ** GOTO lbl-1000
        }
        catch (InvocationTargetException var0_4) {
            ** continue;
        }
    }

    public static interface Callback
    extends MediaSessionCompatApi23.Callback {
        public void onPrepare();

        public void onPrepareFromMediaId(String var1, Bundle var2);

        public void onPrepareFromSearch(String var1, Bundle var2);

        public void onPrepareFromUri(Uri var1, Bundle var2);
    }

    static class CallbackProxy<T extends Callback>
    extends MediaSessionCompatApi23.CallbackProxy<T> {
        public CallbackProxy(T t2) {
            super(t2);
        }

        public void onPrepare() {
            ((Callback)this.mCallback).onPrepare();
        }

        public void onPrepareFromMediaId(String string2, Bundle bundle) {
            ((Callback)this.mCallback).onPrepareFromMediaId(string2, bundle);
        }

        public void onPrepareFromSearch(String string2, Bundle bundle) {
            ((Callback)this.mCallback).onPrepareFromSearch(string2, bundle);
        }

        public void onPrepareFromUri(Uri uri, Bundle bundle) {
            ((Callback)this.mCallback).onPrepareFromUri(uri, bundle);
        }
    }
}

