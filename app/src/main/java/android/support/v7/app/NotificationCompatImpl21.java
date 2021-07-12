/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.app.Notification$MediaStyle
 *  android.media.session.MediaSession$Token
 */
package android.support.v7.app;

import android.app.Notification;
import android.media.session.MediaSession;
import android.support.v4.app.NotificationBuilderWithBuilderAccessor;

class NotificationCompatImpl21 {
    NotificationCompatImpl21() {
    }

    public static void addMediaStyle(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor, int[] nArray, Object object) {
        notificationBuilderWithBuilderAccessor = new Notification.MediaStyle(notificationBuilderWithBuilderAccessor.getBuilder());
        if (nArray != null) {
            notificationBuilderWithBuilderAccessor.setShowActionsInCompactView(nArray);
        }
        if (object != null) {
            notificationBuilderWithBuilderAccessor.setMediaSession((MediaSession.Token)object);
        }
    }
}

