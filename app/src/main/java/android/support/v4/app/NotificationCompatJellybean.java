/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.app.Notification
 *  android.app.Notification$BigPictureStyle
 *  android.app.Notification$BigTextStyle
 *  android.app.Notification$Builder
 *  android.app.Notification$InboxStyle
 *  android.app.PendingIntent
 *  android.content.Context
 *  android.graphics.Bitmap
 *  android.os.Bundle
 *  android.os.Parcelable
 *  android.util.Log
 *  android.util.SparseArray
 *  android.widget.RemoteViews
 */
package android.support.v4.app;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.BundleUtil;
import android.support.v4.app.NotificationBuilderWithActions;
import android.support.v4.app.NotificationBuilderWithBuilderAccessor;
import android.support.v4.app.NotificationCompatBase;
import android.support.v4.app.RemoteInputCompatBase;
import android.support.v4.app.RemoteInputCompatJellybean;
import android.util.Log;
import android.util.SparseArray;
import android.widget.RemoteViews;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

class NotificationCompatJellybean {
    static final String EXTRA_ACTION_EXTRAS = "android.support.actionExtras";
    static final String EXTRA_ALLOW_GENERATED_REPLIES = "android.support.allowGeneratedReplies";
    static final String EXTRA_GROUP_KEY = "android.support.groupKey";
    static final String EXTRA_GROUP_SUMMARY = "android.support.isGroupSummary";
    static final String EXTRA_LOCAL_ONLY = "android.support.localOnly";
    static final String EXTRA_REMOTE_INPUTS = "android.support.remoteInputs";
    static final String EXTRA_SORT_KEY = "android.support.sortKey";
    static final String EXTRA_USE_SIDE_CHANNEL = "android.support.useSideChannel";
    private static final String KEY_ACTION_INTENT = "actionIntent";
    private static final String KEY_ALLOW_GENERATED_REPLIES = "allowGeneratedReplies";
    private static final String KEY_EXTRAS = "extras";
    private static final String KEY_ICON = "icon";
    private static final String KEY_REMOTE_INPUTS = "remoteInputs";
    private static final String KEY_TITLE = "title";
    public static final String TAG = "NotificationCompat";
    private static Class<?> sActionClass;
    private static Field sActionIconField;
    private static Field sActionIntentField;
    private static Field sActionTitleField;
    private static boolean sActionsAccessFailed;
    private static Field sActionsField;
    private static final Object sActionsLock;
    private static Field sExtrasField;
    private static boolean sExtrasFieldAccessFailed;
    private static final Object sExtrasLock;

    static {
        sExtrasLock = new Object();
        sActionsLock = new Object();
    }

    NotificationCompatJellybean() {
    }

    public static void addBigPictureStyle(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor, CharSequence charSequence, boolean bl2, CharSequence charSequence2, Bitmap bitmap, Bitmap bitmap2, boolean bl3) {
        notificationBuilderWithBuilderAccessor = new Notification.BigPictureStyle(notificationBuilderWithBuilderAccessor.getBuilder()).setBigContentTitle(charSequence).bigPicture(bitmap);
        if (bl3) {
            notificationBuilderWithBuilderAccessor.bigLargeIcon(bitmap2);
        }
        if (bl2) {
            notificationBuilderWithBuilderAccessor.setSummaryText(charSequence2);
        }
    }

    public static void addBigTextStyle(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor, CharSequence charSequence, boolean bl2, CharSequence charSequence2, CharSequence charSequence3) {
        notificationBuilderWithBuilderAccessor = new Notification.BigTextStyle(notificationBuilderWithBuilderAccessor.getBuilder()).setBigContentTitle(charSequence).bigText(charSequence3);
        if (bl2) {
            notificationBuilderWithBuilderAccessor.setSummaryText(charSequence2);
        }
    }

    public static void addInboxStyle(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor, CharSequence object, boolean bl2, CharSequence charSequence, ArrayList<CharSequence> arrayList) {
        notificationBuilderWithBuilderAccessor = new Notification.InboxStyle(notificationBuilderWithBuilderAccessor.getBuilder()).setBigContentTitle((CharSequence)object);
        if (bl2) {
            notificationBuilderWithBuilderAccessor.setSummaryText(charSequence);
        }
        object = arrayList.iterator();
        while (object.hasNext()) {
            notificationBuilderWithBuilderAccessor.addLine((CharSequence)object.next());
        }
    }

    public static SparseArray<Bundle> buildActionExtrasMap(List<Bundle> list) {
        SparseArray sparseArray = null;
        int n2 = list.size();
        for (int i2 = 0; i2 < n2; ++i2) {
            Bundle bundle = list.get(i2);
            SparseArray sparseArray2 = sparseArray;
            if (bundle != null) {
                sparseArray2 = sparseArray;
                if (sparseArray == null) {
                    sparseArray2 = new SparseArray();
                }
                sparseArray2.put(i2, (Object)bundle);
            }
            sparseArray = sparseArray2;
        }
        return sparseArray;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static boolean ensureActionReflectionReadyLocked() {
        boolean bl2 = true;
        if (sActionsAccessFailed) {
            return false;
        }
        try {
            if (sActionsField == null) {
                sActionClass = Class.forName("android.app.Notification$Action");
                sActionIconField = sActionClass.getDeclaredField(KEY_ICON);
                sActionTitleField = sActionClass.getDeclaredField(KEY_TITLE);
                sActionIntentField = sActionClass.getDeclaredField(KEY_ACTION_INTENT);
                sActionsField = Notification.class.getDeclaredField("actions");
                sActionsField.setAccessible(true);
            }
        }
        catch (ClassNotFoundException classNotFoundException) {
            Log.e((String)TAG, (String)"Unable to access notification actions", (Throwable)classNotFoundException);
            sActionsAccessFailed = true;
        }
        catch (NoSuchFieldException noSuchFieldException) {
            Log.e((String)TAG, (String)"Unable to access notification actions", (Throwable)noSuchFieldException);
            sActionsAccessFailed = true;
        }
        if (sActionsAccessFailed) return false;
        return bl2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static NotificationCompatBase.Action getAction(Notification object, int n2, NotificationCompatBase.Action.Factory factory, RemoteInputCompatBase.RemoteInput.Factory factory2) {
        Object object2 = sActionsLock;
        synchronized (object2) {
            try {
                Object object3 = NotificationCompatJellybean.getActionObjectsLocked(object)[n2];
                Object var4_7 = null;
                Bundle bundle = NotificationCompatJellybean.getExtras(object);
                object = var4_7;
                if (bundle == null) return NotificationCompatJellybean.readAction(factory, factory2, sActionIconField.getInt(object3), (CharSequence)sActionTitleField.get(object3), (PendingIntent)sActionIntentField.get(object3), (Bundle)object);
                bundle = bundle.getSparseParcelableArray(EXTRA_ACTION_EXTRAS);
                object = var4_7;
                if (bundle == null) return NotificationCompatJellybean.readAction(factory, factory2, sActionIconField.getInt(object3), (CharSequence)sActionTitleField.get(object3), (PendingIntent)sActionIntentField.get(object3), (Bundle)object);
                object = (Bundle)bundle.get(n2);
                return NotificationCompatJellybean.readAction(factory, factory2, sActionIconField.getInt(object3), (CharSequence)sActionTitleField.get(object3), (PendingIntent)sActionIntentField.get(object3), (Bundle)object);
            }
            catch (IllegalAccessException illegalAccessException) {
                Log.e((String)TAG, (String)"Unable to access notification actions", (Throwable)illegalAccessException);
                sActionsAccessFailed = true;
                return null;
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static int getActionCount(Notification objectArray) {
        Object object = sActionsLock;
        synchronized (object) {
            objectArray = NotificationCompatJellybean.getActionObjectsLocked((Notification)objectArray);
            if (objectArray == null) return 0;
            return objectArray.length;
        }
    }

    private static NotificationCompatBase.Action getActionFromBundle(Bundle bundle, NotificationCompatBase.Action.Factory factory, RemoteInputCompatBase.RemoteInput.Factory factory2) {
        return factory.build(bundle.getInt(KEY_ICON), bundle.getCharSequence(KEY_TITLE), (PendingIntent)bundle.getParcelable(KEY_ACTION_INTENT), bundle.getBundle(KEY_EXTRAS), RemoteInputCompatJellybean.fromBundleArray(BundleUtil.getBundleArrayFromBundle(bundle, KEY_REMOTE_INPUTS), factory2), bundle.getBoolean(KEY_ALLOW_GENERATED_REPLIES));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static Object[] getActionObjectsLocked(Notification objectArray) {
        Object object = sActionsLock;
        synchronized (object) {
            if (!NotificationCompatJellybean.ensureActionReflectionReadyLocked()) {
                return null;
            }
            try {
                return (Object[])sActionsField.get(objectArray);
            }
            catch (IllegalAccessException illegalAccessException) {
                Log.e((String)TAG, (String)"Unable to access notification actions", (Throwable)illegalAccessException);
                sActionsAccessFailed = true;
                return null;
            }
        }
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
            actionArray[n2] = NotificationCompatJellybean.getActionFromBundle((Bundle)arrayList.get(n2), factory, factory2);
            ++n2;
        }
    }

    private static Bundle getBundleForAction(NotificationCompatBase.Action action) {
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_ICON, action.getIcon());
        bundle.putCharSequence(KEY_TITLE, action.getTitle());
        bundle.putParcelable(KEY_ACTION_INTENT, (Parcelable)action.getActionIntent());
        bundle.putBundle(KEY_EXTRAS, action.getExtras());
        bundle.putParcelableArray(KEY_REMOTE_INPUTS, (Parcelable[])RemoteInputCompatJellybean.toBundleArray(action.getRemoteInputs()));
        return bundle;
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Bundle getExtras(Notification notification) {
        Object object = sExtrasLock;
        synchronized (object) {
            if (sExtrasFieldAccessFailed) {
                return null;
            }
            try {
                Field field;
                if (sExtrasField == null) {
                    field = Notification.class.getDeclaredField(KEY_EXTRAS);
                    if (!Bundle.class.isAssignableFrom(field.getType())) {
                        Log.e((String)TAG, (String)"Notification.extras field is not of type Bundle");
                        sExtrasFieldAccessFailed = true;
                        return null;
                    }
                    field.setAccessible(true);
                    sExtrasField = field;
                }
                Bundle bundle = (Bundle)sExtrasField.get(notification);
                field = bundle;
                if (bundle == null) {
                    field = new Bundle();
                    sExtrasField.set(notification, field);
                }
                return field;
            }
            catch (IllegalAccessException illegalAccessException) {
                block10: {
                    Log.e((String)TAG, (String)"Unable to access notification extras", (Throwable)illegalAccessException);
                    break block10;
                    catch (NoSuchFieldException noSuchFieldException) {
                        Log.e((String)TAG, (String)"Unable to access notification extras", (Throwable)noSuchFieldException);
                    }
                }
                sExtrasFieldAccessFailed = true;
                return null;
            }
        }
    }

    public static String getGroup(Notification notification) {
        return NotificationCompatJellybean.getExtras(notification).getString(EXTRA_GROUP_KEY);
    }

    public static boolean getLocalOnly(Notification notification) {
        return NotificationCompatJellybean.getExtras(notification).getBoolean(EXTRA_LOCAL_ONLY);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static ArrayList<Parcelable> getParcelableArrayListForActions(NotificationCompatBase.Action[] actionArray) {
        if (actionArray == null) {
            return null;
        }
        ArrayList<Bundle> arrayList = new ArrayList<Bundle>(actionArray.length);
        int n2 = actionArray.length;
        int n3 = 0;
        while (true) {
            ArrayList<Bundle> arrayList2 = arrayList;
            if (n3 >= n2) return arrayList2;
            arrayList.add(NotificationCompatJellybean.getBundleForAction(actionArray[n3]));
            ++n3;
        }
    }

    public static String getSortKey(Notification notification) {
        return NotificationCompatJellybean.getExtras(notification).getString(EXTRA_SORT_KEY);
    }

    public static boolean isGroupSummary(Notification notification) {
        return NotificationCompatJellybean.getExtras(notification).getBoolean(EXTRA_GROUP_SUMMARY);
    }

    public static NotificationCompatBase.Action readAction(NotificationCompatBase.Action.Factory factory, RemoteInputCompatBase.RemoteInput.Factory factory2, int n2, CharSequence charSequence, PendingIntent pendingIntent, Bundle bundle) {
        RemoteInputCompatBase.RemoteInput[] remoteInputArray = null;
        boolean bl2 = false;
        if (bundle != null) {
            remoteInputArray = RemoteInputCompatJellybean.fromBundleArray(BundleUtil.getBundleArrayFromBundle(bundle, EXTRA_REMOTE_INPUTS), factory2);
            bl2 = bundle.getBoolean(EXTRA_ALLOW_GENERATED_REPLIES);
        }
        return factory.build(n2, charSequence, pendingIntent, bundle, remoteInputArray, bl2);
    }

    public static Bundle writeActionAndGetExtras(Notification.Builder builder, NotificationCompatBase.Action action) {
        builder.addAction(action.getIcon(), action.getTitle(), action.getActionIntent());
        builder = new Bundle(action.getExtras());
        if (action.getRemoteInputs() != null) {
            builder.putParcelableArray(EXTRA_REMOTE_INPUTS, (Parcelable[])RemoteInputCompatJellybean.toBundleArray(action.getRemoteInputs()));
        }
        builder.putBoolean(EXTRA_ALLOW_GENERATED_REPLIES, action.getAllowGeneratedReplies());
        return builder;
    }

    public static class Builder
    implements NotificationBuilderWithBuilderAccessor,
    NotificationBuilderWithActions {
        private Notification.Builder b;
        private List<Bundle> mActionExtrasList = new ArrayList<Bundle>();
        private RemoteViews mBigContentView;
        private RemoteViews mContentView;
        private final Bundle mExtras;

        /*
         * Enabled aggressive block sorting
         */
        public Builder(Context context, Notification notification, CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3, RemoteViews remoteViews, int n2, PendingIntent pendingIntent, PendingIntent pendingIntent2, Bitmap bitmap, int n3, int n4, boolean bl2, boolean bl3, int n5, CharSequence charSequence4, boolean bl4, Bundle bundle, String string2, boolean bl5, String string3, RemoteViews remoteViews2, RemoteViews remoteViews3) {
            context = new Notification.Builder(context).setWhen(notification.when).setSmallIcon(notification.icon, notification.iconLevel).setContent(notification.contentView).setTicker(notification.tickerText, remoteViews).setSound(notification.sound, notification.audioStreamType).setVibrate(notification.vibrate).setLights(notification.ledARGB, notification.ledOnMS, notification.ledOffMS);
            boolean bl6 = (notification.flags & 2) != 0;
            context = context.setOngoing(bl6);
            bl6 = (notification.flags & 8) != 0;
            context = context.setOnlyAlertOnce(bl6);
            bl6 = (notification.flags & 0x10) != 0;
            context = context.setAutoCancel(bl6).setDefaults(notification.defaults).setContentTitle(charSequence).setContentText(charSequence2).setSubText(charSequence4).setContentInfo(charSequence3).setContentIntent(pendingIntent).setDeleteIntent(notification.deleteIntent);
            bl6 = (notification.flags & 0x80) != 0;
            this.b = context.setFullScreenIntent(pendingIntent2, bl6).setLargeIcon(bitmap).setNumber(n2).setUsesChronometer(bl3).setPriority(n5).setProgress(n3, n4, bl2);
            this.mExtras = new Bundle();
            if (bundle != null) {
                this.mExtras.putAll(bundle);
            }
            if (bl4) {
                this.mExtras.putBoolean(NotificationCompatJellybean.EXTRA_LOCAL_ONLY, true);
            }
            if (string2 != null) {
                this.mExtras.putString(NotificationCompatJellybean.EXTRA_GROUP_KEY, string2);
                if (bl5) {
                    this.mExtras.putBoolean(NotificationCompatJellybean.EXTRA_GROUP_SUMMARY, true);
                } else {
                    this.mExtras.putBoolean(NotificationCompatJellybean.EXTRA_USE_SIDE_CHANNEL, true);
                }
            }
            if (string3 != null) {
                this.mExtras.putString(NotificationCompatJellybean.EXTRA_SORT_KEY, string3);
            }
            this.mContentView = remoteViews2;
            this.mBigContentView = remoteViews3;
        }

        @Override
        public void addAction(NotificationCompatBase.Action action) {
            this.mActionExtrasList.add(NotificationCompatJellybean.writeActionAndGetExtras(this.b, action));
        }

        @Override
        public Notification build() {
            Notification notification = this.b.build();
            SparseArray<Bundle> sparseArray = NotificationCompatJellybean.getExtras(notification);
            Bundle bundle = new Bundle(this.mExtras);
            for (String string2 : this.mExtras.keySet()) {
                if (!sparseArray.containsKey(string2)) continue;
                bundle.remove(string2);
            }
            sparseArray.putAll(bundle);
            sparseArray = NotificationCompatJellybean.buildActionExtrasMap(this.mActionExtrasList);
            if (sparseArray != null) {
                NotificationCompatJellybean.getExtras(notification).putSparseParcelableArray(NotificationCompatJellybean.EXTRA_ACTION_EXTRAS, sparseArray);
            }
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

