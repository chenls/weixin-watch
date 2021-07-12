/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.app.Notification
 *  android.app.Notification$Action
 *  android.app.Notification$Action$Builder
 *  android.app.Notification$Builder
 *  android.app.PendingIntent
 *  android.app.RemoteInput
 *  android.content.Context
 *  android.graphics.Bitmap
 *  android.os.Bundle
 *  android.os.Parcelable
 *  android.widget.RemoteViews
 */
package android.support.v4.app;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.RemoteInput;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.NotificationBuilderWithActions;
import android.support.v4.app.NotificationBuilderWithBuilderAccessor;
import android.support.v4.app.NotificationCompatBase;
import android.support.v4.app.RemoteInputCompatApi20;
import android.support.v4.app.RemoteInputCompatBase;
import android.widget.RemoteViews;
import java.util.ArrayList;

class NotificationCompatApi20 {
    NotificationCompatApi20() {
    }

    /*
     * Enabled aggressive block sorting
     */
    public static void addAction(Notification.Builder builder, NotificationCompatBase.Action action) {
        Bundle bundle;
        Notification.Action.Builder builder2 = new Notification.Action.Builder(action.getIcon(), action.getTitle(), action.getActionIntent());
        if (action.getRemoteInputs() != null) {
            bundle = RemoteInputCompatApi20.fromCompat(action.getRemoteInputs());
            int n2 = ((RemoteInput[])bundle).length;
            for (int i2 = 0; i2 < n2; ++i2) {
                builder2.addRemoteInput((RemoteInput)bundle[i2]);
            }
        }
        bundle = action.getExtras() != null ? new Bundle(action.getExtras()) : new Bundle();
        bundle.putBoolean("android.support.allowGeneratedReplies", action.getAllowGeneratedReplies());
        builder2.addExtras(bundle);
        builder.addAction(builder2.build());
    }

    public static NotificationCompatBase.Action getAction(Notification notification, int n2, NotificationCompatBase.Action.Factory factory, RemoteInputCompatBase.RemoteInput.Factory factory2) {
        return NotificationCompatApi20.getActionCompatFromAction(notification.actions[n2], factory, factory2);
    }

    private static NotificationCompatBase.Action getActionCompatFromAction(Notification.Action action, NotificationCompatBase.Action.Factory factory, RemoteInputCompatBase.RemoteInput.Factory remoteInputArray) {
        remoteInputArray = RemoteInputCompatApi20.toCompat(action.getRemoteInputs(), (RemoteInputCompatBase.RemoteInput.Factory)remoteInputArray);
        boolean bl2 = action.getExtras().getBoolean("android.support.allowGeneratedReplies");
        return factory.build(action.icon, action.title, action.actionIntent, action.getExtras(), remoteInputArray, bl2);
    }

    private static Notification.Action getActionFromActionCompat(NotificationCompatBase.Action remoteInputArray) {
        Notification.Action.Builder builder = new Notification.Action.Builder(remoteInputArray.getIcon(), remoteInputArray.getTitle(), remoteInputArray.getActionIntent()).addExtras(remoteInputArray.getExtras());
        if ((remoteInputArray = remoteInputArray.getRemoteInputs()) != null) {
            remoteInputArray = RemoteInputCompatApi20.fromCompat(remoteInputArray);
            int n2 = remoteInputArray.length;
            for (int i2 = 0; i2 < n2; ++i2) {
                builder.addRemoteInput((RemoteInput)remoteInputArray[i2]);
            }
        }
        return builder.build();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static NotificationCompatBase.Action[] getActionsFromParcelableArrayList(ArrayList<Parcelable> arrayList, NotificationCompatBase.Action.Factory factory, RemoteInputCompatBase.RemoteInput.Factory factory2) {
        if (arrayList == null) {
            return null;
        }
        NotificationCompatBase.Action[] actionArray = factory.newArray(arrayList.size());
        int n2 = 0;
        while (true) {
            NotificationCompatBase.Action[] actionArray2 = actionArray;
            if (n2 >= actionArray.length) return actionArray2;
            actionArray[n2] = NotificationCompatApi20.getActionCompatFromAction((Notification.Action)arrayList.get(n2), factory, factory2);
            ++n2;
        }
    }

    public static String getGroup(Notification notification) {
        return notification.getGroup();
    }

    public static boolean getLocalOnly(Notification notification) {
        return (notification.flags & 0x100) != 0;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static ArrayList<Parcelable> getParcelableArrayListForActions(NotificationCompatBase.Action[] actionArray) {
        if (actionArray == null) {
            return null;
        }
        ArrayList<Notification.Action> arrayList = new ArrayList<Notification.Action>(actionArray.length);
        int n2 = actionArray.length;
        int n3 = 0;
        while (true) {
            ArrayList<Notification.Action> arrayList2 = arrayList;
            if (n3 >= n2) return arrayList2;
            arrayList.add(NotificationCompatApi20.getActionFromActionCompat(actionArray[n3]));
            ++n3;
        }
    }

    public static String getSortKey(Notification notification) {
        return notification.getSortKey();
    }

    public static boolean isGroupSummary(Notification notification) {
        return (notification.flags & 0x200) != 0;
    }

    public static class Builder
    implements NotificationBuilderWithBuilderAccessor,
    NotificationBuilderWithActions {
        private Notification.Builder b;
        private RemoteViews mBigContentView;
        private RemoteViews mContentView;
        private Bundle mExtras;

        /*
         * Enabled aggressive block sorting
         */
        public Builder(Context context, Notification notification, CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3, RemoteViews remoteViews, int n2, PendingIntent pendingIntent, PendingIntent pendingIntent2, Bitmap bitmap, int n3, int n4, boolean bl2, boolean bl3, boolean bl4, int n5, CharSequence charSequence4, boolean bl5, ArrayList<String> arrayList, Bundle bundle, String string2, boolean bl6, String string3, RemoteViews remoteViews2, RemoteViews remoteViews3) {
            context = new Notification.Builder(context).setWhen(notification.when).setShowWhen(bl3).setSmallIcon(notification.icon, notification.iconLevel).setContent(notification.contentView).setTicker(notification.tickerText, remoteViews).setSound(notification.sound, notification.audioStreamType).setVibrate(notification.vibrate).setLights(notification.ledARGB, notification.ledOnMS, notification.ledOffMS);
            bl3 = (notification.flags & 2) != 0;
            context = context.setOngoing(bl3);
            bl3 = (notification.flags & 8) != 0;
            context = context.setOnlyAlertOnce(bl3);
            bl3 = (notification.flags & 0x10) != 0;
            context = context.setAutoCancel(bl3).setDefaults(notification.defaults).setContentTitle(charSequence).setContentText(charSequence2).setSubText(charSequence4).setContentInfo(charSequence3).setContentIntent(pendingIntent).setDeleteIntent(notification.deleteIntent);
            bl3 = (notification.flags & 0x80) != 0;
            this.b = context.setFullScreenIntent(pendingIntent2, bl3).setLargeIcon(bitmap).setNumber(n2).setUsesChronometer(bl4).setPriority(n5).setProgress(n3, n4, bl2).setLocalOnly(bl5).setGroup(string2).setGroupSummary(bl6).setSortKey(string3);
            this.mExtras = new Bundle();
            if (bundle != null) {
                this.mExtras.putAll(bundle);
            }
            if (arrayList != null && !arrayList.isEmpty()) {
                this.mExtras.putStringArray("android.people", arrayList.toArray(new String[arrayList.size()]));
            }
            this.mContentView = remoteViews2;
            this.mBigContentView = remoteViews3;
        }

        @Override
        public void addAction(NotificationCompatBase.Action action) {
            NotificationCompatApi20.addAction(this.b, action);
        }

        @Override
        public Notification build() {
            this.b.setExtras(this.mExtras);
            Notification notification = this.b.build();
            if (this.mContentView != null) {
                notification.contentView = this.mContentView;
            }
            if (this.mBigContentView != null) {
                notification.bigContentView = this.mBigContentView;
            }
            return notification;
        }

        @Override
        public Notification.Builder getBuilder() {
            return this.b;
        }
    }
}

