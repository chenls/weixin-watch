/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.app.Notification
 *  android.app.NotificationManager
 *  android.content.ComponentName
 *  android.content.ContentResolver
 *  android.content.Context
 *  android.content.Intent
 *  android.content.ServiceConnection
 *  android.content.pm.ResolveInfo
 *  android.os.Build$VERSION
 *  android.os.DeadObjectException
 *  android.os.Handler
 *  android.os.Handler$Callback
 *  android.os.HandlerThread
 *  android.os.IBinder
 *  android.os.Message
 *  android.os.RemoteException
 *  android.provider.Settings$Secure
 *  android.util.Log
 */
package android.support.v4.app;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.os.DeadObjectException;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.provider.Settings;
import android.support.v4.app.INotificationSideChannel;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompatApi24;
import android.support.v4.app.NotificationManagerCompatKitKat;
import android.support.v4.os.BuildCompat;
import android.util.Log;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

public final class NotificationManagerCompat {
    public static final String ACTION_BIND_SIDE_CHANNEL = "android.support.BIND_NOTIFICATION_SIDE_CHANNEL";
    public static final String EXTRA_USE_SIDE_CHANNEL = "android.support.useSideChannel";
    private static final Impl IMPL;
    public static final int IMPORTANCE_DEFAULT = 3;
    public static final int IMPORTANCE_HIGH = 4;
    public static final int IMPORTANCE_LOW = 2;
    public static final int IMPORTANCE_MAX = 5;
    public static final int IMPORTANCE_MIN = 1;
    public static final int IMPORTANCE_NONE = 0;
    public static final int IMPORTANCE_UNSPECIFIED = -1000;
    static final int MAX_SIDE_CHANNEL_SDK_VERSION = 19;
    private static final String SETTING_ENABLED_NOTIFICATION_LISTENERS = "enabled_notification_listeners";
    static final int SIDE_CHANNEL_BIND_FLAGS;
    private static final int SIDE_CHANNEL_RETRY_BASE_INTERVAL_MS = 1000;
    private static final int SIDE_CHANNEL_RETRY_MAX_COUNT = 6;
    private static final String TAG = "NotifManCompat";
    private static Set<String> sEnabledNotificationListenerPackages;
    private static String sEnabledNotificationListeners;
    private static final Object sEnabledNotificationListenersLock;
    private static final Object sLock;
    private static SideChannelManager sSideChannelManager;
    private final Context mContext;
    private final NotificationManager mNotificationManager;

    /*
     * Enabled aggressive block sorting
     */
    static {
        sEnabledNotificationListenersLock = new Object();
        sEnabledNotificationListenerPackages = new HashSet<String>();
        sLock = new Object();
        IMPL = BuildCompat.isAtLeastN() ? new ImplApi24() : (Build.VERSION.SDK_INT >= 19 ? new ImplKitKat() : (Build.VERSION.SDK_INT >= 14 ? new ImplIceCreamSandwich() : new ImplBase()));
        SIDE_CHANNEL_BIND_FLAGS = IMPL.getSideChannelBindFlags();
    }

    private NotificationManagerCompat(Context context) {
        this.mContext = context;
        this.mNotificationManager = (NotificationManager)this.mContext.getSystemService("notification");
    }

    public static NotificationManagerCompat from(Context context) {
        return new NotificationManagerCompat(context);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Set<String> getEnabledListenerPackages(Context object) {
        String string2 = Settings.Secure.getString((ContentResolver)object.getContentResolver(), (String)SETTING_ENABLED_NOTIFICATION_LISTENERS);
        object = sEnabledNotificationListenersLock;
        synchronized (object) {
            if (string2 == null) return sEnabledNotificationListenerPackages;
            if (string2.equals(sEnabledNotificationListeners)) return sEnabledNotificationListenerPackages;
            String[] stringArray = string2.split(":");
            HashSet<String> hashSet = new HashSet<String>(stringArray.length);
            int n2 = stringArray.length;
            int n3 = 0;
            while (true) {
                if (n3 < n2) {
                    ComponentName componentName = ComponentName.unflattenFromString((String)stringArray[n3]);
                    if (componentName != null) {
                        hashSet.add(componentName.getPackageName());
                    }
                } else {
                    sEnabledNotificationListenerPackages = hashSet;
                    sEnabledNotificationListeners = string2;
                    return sEnabledNotificationListenerPackages;
                }
                ++n3;
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void pushSideChannelQueue(Task task) {
        Object object = sLock;
        synchronized (object) {
            if (sSideChannelManager == null) {
                sSideChannelManager = new SideChannelManager(this.mContext.getApplicationContext());
            }
            sSideChannelManager.queueTask(task);
            return;
        }
    }

    private static boolean useSideChannelForNotification(Notification notification) {
        return (notification = NotificationCompat.getExtras(notification)) != null && notification.getBoolean(EXTRA_USE_SIDE_CHANNEL);
    }

    public boolean areNotificationsEnabled() {
        return IMPL.areNotificationsEnabled(this.mContext, this.mNotificationManager);
    }

    public void cancel(int n2) {
        this.cancel(null, n2);
    }

    public void cancel(String string2, int n2) {
        IMPL.cancelNotification(this.mNotificationManager, string2, n2);
        if (Build.VERSION.SDK_INT <= 19) {
            this.pushSideChannelQueue(new CancelTask(this.mContext.getPackageName(), n2, string2));
        }
    }

    public void cancelAll() {
        this.mNotificationManager.cancelAll();
        if (Build.VERSION.SDK_INT <= 19) {
            this.pushSideChannelQueue(new CancelTask(this.mContext.getPackageName()));
        }
    }

    public int getImportance() {
        return IMPL.getImportance(this.mNotificationManager);
    }

    public void notify(int n2, Notification notification) {
        this.notify(null, n2, notification);
    }

    public void notify(String string2, int n2, Notification notification) {
        if (NotificationManagerCompat.useSideChannelForNotification(notification)) {
            this.pushSideChannelQueue(new NotifyTask(this.mContext.getPackageName(), n2, string2, notification));
            IMPL.cancelNotification(this.mNotificationManager, string2, n2);
            return;
        }
        IMPL.postNotification(this.mNotificationManager, string2, n2, notification);
    }

    private static class CancelTask
    implements Task {
        final boolean all;
        final int id;
        final String packageName;
        final String tag;

        public CancelTask(String string2) {
            this.packageName = string2;
            this.id = 0;
            this.tag = null;
            this.all = true;
        }

        public CancelTask(String string2, int n2, String string3) {
            this.packageName = string2;
            this.id = n2;
            this.tag = string3;
            this.all = false;
        }

        @Override
        public void send(INotificationSideChannel iNotificationSideChannel) throws RemoteException {
            if (this.all) {
                iNotificationSideChannel.cancelAll(this.packageName);
                return;
            }
            iNotificationSideChannel.cancel(this.packageName, this.id, this.tag);
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder("CancelTask[");
            stringBuilder.append("packageName:").append(this.packageName);
            stringBuilder.append(", id:").append(this.id);
            stringBuilder.append(", tag:").append(this.tag);
            stringBuilder.append(", all:").append(this.all);
            stringBuilder.append("]");
            return stringBuilder.toString();
        }
    }

    static interface Impl {
        public boolean areNotificationsEnabled(Context var1, NotificationManager var2);

        public void cancelNotification(NotificationManager var1, String var2, int var3);

        public int getImportance(NotificationManager var1);

        public int getSideChannelBindFlags();

        public void postNotification(NotificationManager var1, String var2, int var3, Notification var4);
    }

    static class ImplApi24
    extends ImplKitKat {
        ImplApi24() {
        }

        @Override
        public boolean areNotificationsEnabled(Context context, NotificationManager notificationManager) {
            return NotificationManagerCompatApi24.areNotificationsEnabled(notificationManager);
        }

        @Override
        public int getImportance(NotificationManager notificationManager) {
            return NotificationManagerCompatApi24.getImportance(notificationManager);
        }
    }

    static class ImplBase
    implements Impl {
        ImplBase() {
        }

        @Override
        public boolean areNotificationsEnabled(Context context, NotificationManager notificationManager) {
            return true;
        }

        @Override
        public void cancelNotification(NotificationManager notificationManager, String string2, int n2) {
            notificationManager.cancel(string2, n2);
        }

        @Override
        public int getImportance(NotificationManager notificationManager) {
            return -1000;
        }

        @Override
        public int getSideChannelBindFlags() {
            return 1;
        }

        @Override
        public void postNotification(NotificationManager notificationManager, String string2, int n2, Notification notification) {
            notificationManager.notify(string2, n2, notification);
        }
    }

    static class ImplIceCreamSandwich
    extends ImplBase {
        ImplIceCreamSandwich() {
        }

        @Override
        public int getSideChannelBindFlags() {
            return 33;
        }
    }

    static class ImplKitKat
    extends ImplIceCreamSandwich {
        ImplKitKat() {
        }

        @Override
        public boolean areNotificationsEnabled(Context context, NotificationManager notificationManager) {
            return NotificationManagerCompatKitKat.areNotificationsEnabled(context);
        }
    }

    private static class NotifyTask
    implements Task {
        final int id;
        final Notification notif;
        final String packageName;
        final String tag;

        public NotifyTask(String string2, int n2, String string3, Notification notification) {
            this.packageName = string2;
            this.id = n2;
            this.tag = string3;
            this.notif = notification;
        }

        @Override
        public void send(INotificationSideChannel iNotificationSideChannel) throws RemoteException {
            iNotificationSideChannel.notify(this.packageName, this.id, this.tag, this.notif);
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder("NotifyTask[");
            stringBuilder.append("packageName:").append(this.packageName);
            stringBuilder.append(", id:").append(this.id);
            stringBuilder.append(", tag:").append(this.tag);
            stringBuilder.append("]");
            return stringBuilder.toString();
        }
    }

    private static class ServiceConnectedEvent {
        final ComponentName componentName;
        final IBinder iBinder;

        public ServiceConnectedEvent(ComponentName componentName, IBinder iBinder) {
            this.componentName = componentName;
            this.iBinder = iBinder;
        }
    }

    private static class SideChannelManager
    implements Handler.Callback,
    ServiceConnection {
        private static final String KEY_BINDER = "binder";
        private static final int MSG_QUEUE_TASK = 0;
        private static final int MSG_RETRY_LISTENER_QUEUE = 3;
        private static final int MSG_SERVICE_CONNECTED = 1;
        private static final int MSG_SERVICE_DISCONNECTED = 2;
        private Set<String> mCachedEnabledPackages;
        private final Context mContext;
        private final Handler mHandler;
        private final HandlerThread mHandlerThread;
        private final Map<ComponentName, ListenerRecord> mRecordMap = new HashMap<ComponentName, ListenerRecord>();

        public SideChannelManager(Context context) {
            this.mCachedEnabledPackages = new HashSet<String>();
            this.mContext = context;
            this.mHandlerThread = new HandlerThread("NotificationManagerCompat");
            this.mHandlerThread.start();
            this.mHandler = new Handler(this.mHandlerThread.getLooper(), (Handler.Callback)this);
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        private boolean ensureServiceBound(ListenerRecord listenerRecord) {
            if (listenerRecord.bound) {
                return true;
            }
            Intent intent = new Intent(NotificationManagerCompat.ACTION_BIND_SIDE_CHANNEL).setComponent(listenerRecord.componentName);
            listenerRecord.bound = this.mContext.bindService(intent, (ServiceConnection)this, SIDE_CHANNEL_BIND_FLAGS);
            if (listenerRecord.bound) {
                listenerRecord.retryCount = 0;
                return listenerRecord.bound;
            }
            Log.w((String)NotificationManagerCompat.TAG, (String)("Unable to bind to listener " + listenerRecord.componentName));
            this.mContext.unbindService((ServiceConnection)this);
            return listenerRecord.bound;
        }

        private void ensureServiceUnbound(ListenerRecord listenerRecord) {
            if (listenerRecord.bound) {
                this.mContext.unbindService((ServiceConnection)this);
                listenerRecord.bound = false;
            }
            listenerRecord.service = null;
        }

        private void handleQueueTask(Task task) {
            this.updateListenerMap();
            for (ListenerRecord listenerRecord : this.mRecordMap.values()) {
                listenerRecord.taskQueue.add(task);
                this.processListenerQueue(listenerRecord);
            }
        }

        private void handleRetryListenerQueue(ComponentName object) {
            if ((object = this.mRecordMap.get(object)) != null) {
                this.processListenerQueue((ListenerRecord)object);
            }
        }

        private void handleServiceConnected(ComponentName object, IBinder iBinder) {
            if ((object = this.mRecordMap.get(object)) != null) {
                object.service = INotificationSideChannel.Stub.asInterface(iBinder);
                object.retryCount = 0;
                this.processListenerQueue((ListenerRecord)object);
            }
        }

        private void handleServiceDisconnected(ComponentName object) {
            if ((object = this.mRecordMap.get(object)) != null) {
                this.ensureServiceUnbound((ListenerRecord)object);
            }
        }

        /*
         * Unable to fully structure code
         */
        private void processListenerQueue(ListenerRecord var1_1) {
            if (Log.isLoggable((String)"NotifManCompat", (int)3)) {
                Log.d((String)"NotifManCompat", (String)("Processing component " + var1_1.componentName + ", " + var1_1.taskQueue.size() + " queued tasks"));
            }
            if (var1_1.taskQueue.isEmpty()) {
                return;
            }
            if (this.ensureServiceBound(var1_1) && var1_1.service != null) ** GOTO lbl17
            this.scheduleListenerRetry(var1_1);
            return;
lbl-1000:
            // 1 sources

            {
                if (Log.isLoggable((String)"NotifManCompat", (int)3)) {
                    Log.d((String)"NotifManCompat", (String)("Sending task " + var2_2));
                }
                var2_2.send(var1_1.service);
                var1_1.taskQueue.remove();
lbl17:
                // 2 sources

                ** while ((var2_2 = var1_1.taskQueue.peek()) != null)
            }
lbl18:
            // 4 sources

            while (true) {
                if (var1_1.taskQueue.isEmpty()) ** continue;
                this.scheduleListenerRetry(var1_1);
                return;
            }
            catch (DeadObjectException var2_3) {
                if (!Log.isLoggable((String)"NotifManCompat", (int)3)) ** GOTO lbl18
                Log.d((String)"NotifManCompat", (String)("Remote service has died: " + var1_1.componentName));
                ** GOTO lbl18
            }
            catch (RemoteException var2_4) {
                Log.w((String)"NotifManCompat", (String)("RemoteException communicating with " + var1_1.componentName), (Throwable)var2_4);
                ** continue;
            }
        }

        private void scheduleListenerRetry(ListenerRecord listenerRecord) {
            if (this.mHandler.hasMessages(3, (Object)listenerRecord.componentName)) {
                return;
            }
            ++listenerRecord.retryCount;
            if (listenerRecord.retryCount > 6) {
                Log.w((String)NotificationManagerCompat.TAG, (String)("Giving up on delivering " + listenerRecord.taskQueue.size() + " tasks to " + listenerRecord.componentName + " after " + listenerRecord.retryCount + " retries"));
                listenerRecord.taskQueue.clear();
                return;
            }
            int n2 = (1 << listenerRecord.retryCount - 1) * 1000;
            if (Log.isLoggable((String)NotificationManagerCompat.TAG, (int)3)) {
                Log.d((String)NotificationManagerCompat.TAG, (String)("Scheduling retry for " + n2 + " ms"));
            }
            listenerRecord = this.mHandler.obtainMessage(3, (Object)listenerRecord.componentName);
            this.mHandler.sendMessageDelayed((Message)listenerRecord, (long)n2);
        }

        /*
         * Enabled aggressive block sorting
         */
        private void updateListenerMap() {
            Object object = NotificationManagerCompat.getEnabledListenerPackages(this.mContext);
            if (!object.equals(this.mCachedEnabledPackages)) {
                this.mCachedEnabledPackages = object;
                ComponentName componentName3 = this.mContext.getPackageManager().queryIntentServices(new Intent().setAction(NotificationManagerCompat.ACTION_BIND_SIDE_CHANNEL), 4);
                HashSet<ComponentName> hashSet = new HashSet<ComponentName>();
                for (ResolveInfo resolveInfo : componentName3) {
                    if (!object.contains(resolveInfo.serviceInfo.packageName)) continue;
                    ComponentName componentName2 = new ComponentName(resolveInfo.serviceInfo.packageName, resolveInfo.serviceInfo.name);
                    if (resolveInfo.serviceInfo.permission != null) {
                        Log.w((String)NotificationManagerCompat.TAG, (String)("Permission present on component " + componentName2 + ", not adding listener record."));
                        continue;
                    }
                    hashSet.add(componentName2);
                }
                for (ComponentName componentName3 : hashSet) {
                    if (this.mRecordMap.containsKey(componentName3)) continue;
                    if (Log.isLoggable((String)NotificationManagerCompat.TAG, (int)3)) {
                        Log.d((String)NotificationManagerCompat.TAG, (String)("Adding listener record for " + componentName3));
                    }
                    this.mRecordMap.put(componentName3, new ListenerRecord(componentName3));
                }
                object = this.mRecordMap.entrySet().iterator();
                while (object.hasNext()) {
                    componentName3 = object.next();
                    if (hashSet.contains(componentName3.getKey())) continue;
                    if (Log.isLoggable((String)NotificationManagerCompat.TAG, (int)3)) {
                        Log.d((String)NotificationManagerCompat.TAG, (String)("Removing listener record for " + componentName3.getKey()));
                    }
                    this.ensureServiceUnbound(componentName3.getValue());
                    object.remove();
                }
            }
        }

        public boolean handleMessage(Message object) {
            switch (object.what) {
                default: {
                    return false;
                }
                case 0: {
                    this.handleQueueTask((Task)object.obj);
                    return true;
                }
                case 1: {
                    object = (ServiceConnectedEvent)object.obj;
                    this.handleServiceConnected(object.componentName, object.iBinder);
                    return true;
                }
                case 2: {
                    this.handleServiceDisconnected((ComponentName)object.obj);
                    return true;
                }
                case 3: 
            }
            this.handleRetryListenerQueue((ComponentName)object.obj);
            return true;
        }

        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            if (Log.isLoggable((String)NotificationManagerCompat.TAG, (int)3)) {
                Log.d((String)NotificationManagerCompat.TAG, (String)("Connected to service " + componentName));
            }
            this.mHandler.obtainMessage(1, (Object)new ServiceConnectedEvent(componentName, iBinder)).sendToTarget();
        }

        public void onServiceDisconnected(ComponentName componentName) {
            if (Log.isLoggable((String)NotificationManagerCompat.TAG, (int)3)) {
                Log.d((String)NotificationManagerCompat.TAG, (String)("Disconnected from service " + componentName));
            }
            this.mHandler.obtainMessage(2, (Object)componentName).sendToTarget();
        }

        public void queueTask(Task task) {
            this.mHandler.obtainMessage(0, (Object)task).sendToTarget();
        }

        private static class ListenerRecord {
            public boolean bound = false;
            public final ComponentName componentName;
            public int retryCount = 0;
            public INotificationSideChannel service;
            public LinkedList<Task> taskQueue = new LinkedList();

            public ListenerRecord(ComponentName componentName) {
                this.componentName = componentName;
            }
        }
    }

    private static interface Task {
        public void send(INotificationSideChannel var1) throws RemoteException;
    }
}

