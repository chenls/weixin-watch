/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.media.Rating
 *  android.media.RemoteControlClient
 *  android.media.RemoteControlClient$MetadataEditor
 *  android.media.RemoteControlClient$OnMetadataUpdateListener
 *  android.os.Bundle
 */
package android.support.v4.media.session;

import android.media.Rating;
import android.media.RemoteControlClient;
import android.os.Bundle;
import android.support.v4.media.session.MediaSessionCompatApi14;
import android.support.v4.media.session.MediaSessionCompatApi18;

class MediaSessionCompatApi19 {
    private static final long ACTION_SET_RATING = 128L;
    private static final String METADATA_KEY_RATING = "android.media.metadata.RATING";
    private static final String METADATA_KEY_USER_RATING = "android.media.metadata.USER_RATING";
    private static final String METADATA_KEY_YEAR = "android.media.metadata.YEAR";

    MediaSessionCompatApi19() {
    }

    /*
     * Enabled aggressive block sorting
     */
    static void addNewMetadata(Bundle bundle, RemoteControlClient.MetadataEditor metadataEditor) {
        block6: {
            block5: {
                if (bundle == null) break block5;
                if (bundle.containsKey(METADATA_KEY_YEAR)) {
                    metadataEditor.putLong(8, bundle.getLong(METADATA_KEY_YEAR));
                }
                if (bundle.containsKey(METADATA_KEY_RATING)) {
                    metadataEditor.putObject(101, (Object)bundle.getParcelable(METADATA_KEY_RATING));
                }
                if (bundle.containsKey(METADATA_KEY_USER_RATING)) break block6;
            }
            return;
        }
        metadataEditor.putObject(0x10000001, (Object)bundle.getParcelable(METADATA_KEY_USER_RATING));
    }

    public static Object createMetadataUpdateListener(Callback callback) {
        return new OnMetadataUpdateListener<Callback>(callback);
    }

    static int getRccTransportControlFlagsFromActions(long l2) {
        int n2;
        int n3 = n2 = MediaSessionCompatApi18.getRccTransportControlFlagsFromActions(l2);
        if ((0x80L & l2) != 0L) {
            n3 = n2 | 0x200;
        }
        return n3;
    }

    public static void setMetadata(Object object, Bundle bundle, long l2) {
        object = ((RemoteControlClient)object).editMetadata(true);
        MediaSessionCompatApi14.buildOldMetadata(bundle, (RemoteControlClient.MetadataEditor)object);
        MediaSessionCompatApi19.addNewMetadata(bundle, (RemoteControlClient.MetadataEditor)object);
        if ((0x80L & l2) != 0L) {
            object.addEditableKey(0x10000001);
        }
        object.apply();
    }

    public static void setOnMetadataUpdateListener(Object object, Object object2) {
        ((RemoteControlClient)object).setMetadataUpdateListener((RemoteControlClient.OnMetadataUpdateListener)object2);
    }

    public static void setTransportControlFlags(Object object, long l2) {
        ((RemoteControlClient)object).setTransportControlFlags(MediaSessionCompatApi19.getRccTransportControlFlagsFromActions(l2));
    }

    static interface Callback
    extends MediaSessionCompatApi18.Callback {
        public void onSetRating(Object var1);
    }

    static class OnMetadataUpdateListener<T extends Callback>
    implements RemoteControlClient.OnMetadataUpdateListener {
        protected final T mCallback;

        public OnMetadataUpdateListener(T t2) {
            this.mCallback = t2;
        }

        public void onMetadataUpdate(int n2, Object object) {
            if (n2 == 0x10000001 && object instanceof Rating) {
                this.mCallback.onSetRating(object);
            }
        }
    }
}

