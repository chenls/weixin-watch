/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.app.Service
 *  android.content.BroadcastReceiver
 *  android.content.Context
 *  android.content.Intent
 *  android.content.IntentFilter
 *  android.graphics.Rect
 *  android.os.Bundle
 *  android.os.IBinder
 *  android.os.PowerManager
 *  android.os.PowerManager$WakeLock
 *  android.os.RemoteException
 *  android.service.wallpaper.WallpaperService
 *  android.service.wallpaper.WallpaperService$Engine
 *  android.util.Log
 *  android.view.SurfaceHolder
 */
package android.support.wearable.watchface;

import android.annotation.TargetApi;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.RemoteException;
import android.service.wallpaper.WallpaperService;
import android.support.annotation.CallSuper;
import android.support.annotation.RequiresPermission;
import android.support.wearable.watchface.IWatchFaceService;
import android.support.wearable.watchface.WatchFaceStyle;
import android.util.Log;
import android.view.SurfaceHolder;
import java.util.Objects;

@TargetApi(value=21)
public abstract class WatchFaceService
extends WallpaperService {
    public static final String ACTION_REQUEST_STATE = "com.google.android.wearable.watchfaces.action.REQUEST_STATE";
    public static final String COMMAND_AMBIENT_UPDATE = "com.google.android.wearable.action.AMBIENT_UPDATE";
    public static final String COMMAND_BACKGROUND_ACTION = "com.google.android.wearable.action.BACKGROUND_ACTION";
    public static final String COMMAND_REQUEST_STYLE = "com.google.android.wearable.action.REQUEST_STYLE";
    public static final String COMMAND_SET_BINDER = "com.google.android.wearable.action.SET_BINDER";
    public static final String COMMAND_SET_PROPERTIES = "com.google.android.wearable.action.SET_PROPERTIES";
    public static final String COMMAND_TAP = "android.wallpaper.tap";
    public static final String COMMAND_TOUCH = "android.wallpaper.touch";
    public static final String COMMAND_TOUCH_CANCEL = "android.wallpaper.touch_cancel";
    public static final String EXTRA_AMBIENT_MODE = "ambient_mode";
    public static final String EXTRA_BINDER = "binder";
    public static final String EXTRA_CARD_LOCATION = "card_location";
    public static final String EXTRA_INDICATOR_STATUS = "indicator_status";
    public static final String EXTRA_INTERRUPTION_FILTER = "interruption_filter";
    public static final String EXTRA_NOTIFICATION_COUNT = "notification_count";
    public static final String EXTRA_TAP_TIME = "tap_time";
    public static final String EXTRA_UNREAD_COUNT = "unread_count";
    public static final int INTERRUPTION_FILTER_ALARMS = 4;
    public static final int INTERRUPTION_FILTER_ALL = 1;
    public static final int INTERRUPTION_FILTER_NONE = 3;
    public static final int INTERRUPTION_FILTER_PRIORITY = 2;
    public static final int INTERRUPTION_FILTER_UNKNOWN = 0;
    public static final String PROPERTY_BURN_IN_PROTECTION = "burn_in_protection";
    public static final String PROPERTY_LOW_BIT_AMBIENT = "low_bit_ambient";
    public static final String STATUS_AIRPLANE_MODE = "airplane_mode";
    public static final String STATUS_CHARGING = "charging";
    public static final String STATUS_CONNECTED = "connected";
    public static final String STATUS_GPS_ACTIVE = "gps_active";
    public static final String STATUS_INTERRUPTION_FILTER = "interruption_filter";
    private static final String[] STATUS_KEYS = new String[]{"charging", "airplane_mode", "connected", "theater_mode", "gps_active", "interruption_filter"};
    public static final String STATUS_THEATER_MODE = "theater_mode";
    private static final long SURFACE_DRAW_TIMEOUT_MS = 100L;
    private static final String TAG = "WatchFaceService";
    public static final int TAP_TYPE_TAP = 2;
    public static final int TAP_TYPE_TOUCH = 0;
    public static final int TAP_TYPE_TOUCH_CANCEL = 1;

    public abstract Engine onCreateEngine();

    public abstract class Engine
    extends WallpaperService.Engine {
        private final IntentFilter mAmbientTimeTickFilter;
        private PowerManager.WakeLock mAmbientUpdateWakelock;
        private boolean mInAmbientMode;
        private final IntentFilter mInteractiveTimeTickFilter;
        private int mInterruptionFilter;
        private Bundle mLastStatusBundle;
        private WatchFaceStyle mLastWatchFaceStyle;
        private int mNotificationCount;
        private final Rect mPeekCardPosition;
        private final BroadcastReceiver mTimeTickReceiver;
        private boolean mTimeTickRegistered;
        private int mUnreadCount;
        private IWatchFaceService mWatchFaceService;
        private WatchFaceStyle mWatchFaceStyle;

        @RequiresPermission(value="android.permission.WAKE_LOCK")
        public Engine() {
            super((WallpaperService)WatchFaceService.this);
            this.mTimeTickReceiver = new BroadcastReceiver(){

                public void onReceive(Context context, Intent intent) {
                    if (Log.isLoggable((String)WatchFaceService.TAG, (int)3)) {
                        Log.d((String)WatchFaceService.TAG, (String)("Received intent that triggers onTimeTick for: " + intent));
                    }
                    Engine.this.onTimeTick();
                }
            };
            this.mTimeTickRegistered = false;
            this.mPeekCardPosition = new Rect(0, 0, 0, 0);
            this.mAmbientTimeTickFilter = new IntentFilter();
            this.mAmbientTimeTickFilter.addAction("android.intent.action.DATE_CHANGED");
            this.mAmbientTimeTickFilter.addAction("android.intent.action.TIME_SET");
            this.mAmbientTimeTickFilter.addAction("android.intent.action.TIMEZONE_CHANGED");
            this.mInteractiveTimeTickFilter = new IntentFilter(this.mAmbientTimeTickFilter);
            this.mInteractiveTimeTickFilter.addAction("android.intent.action.TIME_TICK");
        }

        private void dispatchAmbientModeChanged() {
            if (Log.isLoggable((String)WatchFaceService.TAG, (int)3)) {
                Log.d((String)WatchFaceService.TAG, (String)("dispatchAmbientModeChanged: " + this.mInAmbientMode));
            }
            this.onAmbientModeChanged(this.mInAmbientMode);
            this.updateTimeTickReceiver();
        }

        private void maybeUpdateAmbientMode(Bundle bundle) {
            boolean bl2;
            if (bundle.containsKey(WatchFaceService.EXTRA_AMBIENT_MODE) && this.mInAmbientMode != (bl2 = bundle.getBoolean(WatchFaceService.EXTRA_AMBIENT_MODE, false))) {
                this.mInAmbientMode = bl2;
                this.dispatchAmbientModeChanged();
            }
        }

        private void maybeUpdateInterruptionFilter(Bundle bundle) {
            int n2;
            if (bundle.containsKey("interruption_filter") && (n2 = bundle.getInt("interruption_filter", 1)) != this.mInterruptionFilter) {
                this.mInterruptionFilter = n2;
                this.onInterruptionFilterChanged(n2);
            }
        }

        private void maybeUpdateNotificationCount(Bundle bundle) {
            int n2;
            if (bundle.containsKey(WatchFaceService.EXTRA_NOTIFICATION_COUNT) && (n2 = bundle.getInt(WatchFaceService.EXTRA_NOTIFICATION_COUNT, 0)) != this.mNotificationCount) {
                this.mNotificationCount = n2;
                this.onNotificationCountChanged(this.mNotificationCount);
            }
        }

        private void maybeUpdatePeekCardPosition(Bundle bundle) {
            if (bundle.containsKey(WatchFaceService.EXTRA_CARD_LOCATION) && !(bundle = Rect.unflattenFromString((String)bundle.getString(WatchFaceService.EXTRA_CARD_LOCATION))).equals((Object)this.mPeekCardPosition)) {
                this.mPeekCardPosition.set((Rect)bundle);
                this.onPeekCardPositionUpdate((Rect)bundle);
            }
        }

        private void maybeUpdateStatus(Bundle bundle) {
            if (!((bundle = bundle.getBundle(WatchFaceService.EXTRA_INDICATOR_STATUS)) == null || this.mLastStatusBundle != null && this.sameStatus(bundle, this.mLastStatusBundle))) {
                this.mLastStatusBundle = new Bundle(bundle);
                this.onStatusChanged(bundle);
            }
        }

        private void maybeUpdateUnreadCount(Bundle bundle) {
            int n2;
            if (bundle.containsKey(WatchFaceService.EXTRA_UNREAD_COUNT) && (n2 = bundle.getInt(WatchFaceService.EXTRA_UNREAD_COUNT, 0)) != this.mUnreadCount) {
                this.mUnreadCount = n2;
                this.onUnreadCountChanged(this.mUnreadCount);
            }
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        private void onSetBinder(Bundle bundle) {
            if ((bundle = bundle.getBinder(WatchFaceService.EXTRA_BINDER)) != null) {
                this.mWatchFaceService = IWatchFaceService.Stub.asInterface((IBinder)bundle);
                if (this.mWatchFaceStyle == null) return;
                try {
                    this.mWatchFaceService.setStyle(this.mWatchFaceStyle);
                    this.mWatchFaceStyle = null;
                    return;
                }
                catch (RemoteException remoteException) {
                    Log.w((String)WatchFaceService.TAG, (String)"Failed to set WatchFaceStyle", (Throwable)remoteException);
                    return;
                }
            }
            Log.w((String)WatchFaceService.TAG, (String)"Binder is null.");
        }

        private boolean sameStatus(Bundle bundle, Bundle bundle2) {
            for (String string2 : STATUS_KEYS) {
                if (Objects.equals(bundle.get(string2), bundle2.get(string2))) continue;
                return false;
            }
            return true;
        }

        /*
         * Enabled aggressive block sorting
         */
        private void updateTimeTickReceiver() {
            if (Log.isLoggable((String)WatchFaceService.TAG, (int)3)) {
                Log.d((String)WatchFaceService.TAG, (String)("updateTimeTickReceiver: " + this.mTimeTickRegistered + " -> (" + this.isVisible() + ", " + this.mInAmbientMode + ")"));
            }
            if (this.mTimeTickRegistered) {
                WatchFaceService.this.unregisterReceiver(this.mTimeTickReceiver);
                this.mTimeTickRegistered = false;
            }
            if (this.isVisible()) {
                if (this.mInAmbientMode) {
                    WatchFaceService.this.registerReceiver(this.mTimeTickReceiver, this.mAmbientTimeTickFilter);
                } else {
                    WatchFaceService.this.registerReceiver(this.mTimeTickReceiver, this.mInteractiveTimeTickFilter);
                }
                this.mTimeTickRegistered = true;
                this.onTimeTick();
            }
        }

        public final int getInterruptionFilter() {
            return this.mInterruptionFilter;
        }

        public final int getNotificationCount() {
            return this.mNotificationCount;
        }

        public final Rect getPeekCardPosition() {
            return this.mPeekCardPosition;
        }

        public final int getUnreadCount() {
            return this.mUnreadCount;
        }

        public final boolean isInAmbientMode() {
            return this.mInAmbientMode;
        }

        public void onAmbientModeChanged(boolean bl2) {
        }

        /*
         * Enabled aggressive block sorting
         */
        public Bundle onCommand(String string2, int n2, int n3, int n4, Bundle bundle, boolean bl2) {
            if (Log.isLoggable((String)WatchFaceService.TAG, (int)3)) {
                Log.d((String)WatchFaceService.TAG, (String)("received command: " + string2));
            }
            if (WatchFaceService.COMMAND_BACKGROUND_ACTION.equals(string2)) {
                this.maybeUpdateAmbientMode(bundle);
                this.maybeUpdateInterruptionFilter(bundle);
                this.maybeUpdatePeekCardPosition(bundle);
                this.maybeUpdateUnreadCount(bundle);
                this.maybeUpdateNotificationCount(bundle);
                this.maybeUpdateStatus(bundle);
                return null;
            }
            if (WatchFaceService.COMMAND_AMBIENT_UPDATE.equals(string2)) {
                if (!this.mInAmbientMode) return null;
                if (Log.isLoggable((String)WatchFaceService.TAG, (int)3)) {
                    Log.d((String)WatchFaceService.TAG, (String)"ambient mode update");
                }
                this.mAmbientUpdateWakelock.acquire();
                this.onTimeTick();
                this.mAmbientUpdateWakelock.acquire(100L);
                return null;
            }
            if (WatchFaceService.COMMAND_SET_PROPERTIES.equals(string2)) {
                this.onPropertiesChanged(bundle);
                return null;
            }
            if (WatchFaceService.COMMAND_SET_BINDER.equals(string2)) {
                this.onSetBinder(bundle);
                return null;
            }
            if (WatchFaceService.COMMAND_REQUEST_STYLE.equals(string2)) {
                if (this.mLastWatchFaceStyle != null) {
                    this.setWatchFaceStyle(this.mLastWatchFaceStyle);
                    return null;
                }
                if (!Log.isLoggable((String)WatchFaceService.TAG, (int)3)) return null;
                Log.d((String)WatchFaceService.TAG, (String)"Last watch face style is null.");
                return null;
            }
            if (!WatchFaceService.COMMAND_TOUCH.equals(string2) && !WatchFaceService.COMMAND_TOUCH_CANCEL.equals(string2)) {
                if (!WatchFaceService.COMMAND_TAP.equals(string2)) return null;
            }
            long l2 = bundle.getLong(WatchFaceService.EXTRA_TAP_TIME);
            n4 = 0;
            if (WatchFaceService.COMMAND_TOUCH_CANCEL.equals(string2)) {
                n4 = 1;
            } else if (WatchFaceService.COMMAND_TAP.equals(string2)) {
                n4 = 2;
            }
            this.onTapCommand(n4, n2, n3, l2);
            return null;
        }

        @CallSuper
        public void onCreate(SurfaceHolder surfaceHolder) {
            super.onCreate(surfaceHolder);
            this.mWatchFaceStyle = new WatchFaceStyle.Builder((Service)WatchFaceService.this).build();
            this.mAmbientUpdateWakelock = ((PowerManager)WatchFaceService.this.getSystemService("power")).newWakeLock(1, "WatchFaceService[AmbientUpdate]");
            this.mAmbientUpdateWakelock.setReferenceCounted(false);
        }

        @CallSuper
        public void onDestroy() {
            if (this.mTimeTickRegistered) {
                this.mTimeTickRegistered = false;
                WatchFaceService.this.unregisterReceiver(this.mTimeTickReceiver);
            }
            super.onDestroy();
        }

        public void onInterruptionFilterChanged(int n2) {
        }

        public void onNotificationCountChanged(int n2) {
        }

        public void onPeekCardPositionUpdate(Rect rect) {
        }

        public void onPropertiesChanged(Bundle bundle) {
        }

        public void onStatusChanged(Bundle bundle) {
        }

        public void onTapCommand(@TapType int n2, int n3, int n4, long l2) {
        }

        public void onTimeTick() {
        }

        public void onUnreadCountChanged(int n2) {
        }

        @CallSuper
        public void onVisibilityChanged(boolean bl2) {
            super.onVisibilityChanged(bl2);
            if (Log.isLoggable((String)WatchFaceService.TAG, (int)3)) {
                Log.d((String)WatchFaceService.TAG, (String)("onVisibilityChanged: " + bl2));
            }
            Intent intent = new Intent(WatchFaceService.ACTION_REQUEST_STATE);
            WatchFaceService.this.sendBroadcast(intent);
            this.updateTimeTickReceiver();
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        public void setWatchFaceStyle(WatchFaceStyle watchFaceStyle) {
            if (Log.isLoggable((String)WatchFaceService.TAG, (int)3)) {
                Log.d((String)WatchFaceService.TAG, (String)("setWatchFaceStyle " + watchFaceStyle));
            }
            this.mWatchFaceStyle = watchFaceStyle;
            this.mLastWatchFaceStyle = watchFaceStyle;
            if (this.mWatchFaceService == null) return;
            try {
                this.mWatchFaceService.setStyle(watchFaceStyle);
                this.mWatchFaceStyle = null;
                return;
            }
            catch (RemoteException remoteException) {
                Log.e((String)WatchFaceService.TAG, (String)"Failed to set WatchFaceStyle: ", (Throwable)remoteException);
                return;
            }
        }
    }

    public static @interface TapType {
    }
}

