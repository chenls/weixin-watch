/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.app.Service
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.os.Binder
 *  android.os.Handler
 *  android.os.HandlerThread
 *  android.os.IBinder
 *  android.util.Log
 */
package com.google.android.gms.wearable;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.util.Log;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.wearable.CapabilityApi;
import com.google.android.gms.wearable.CapabilityInfo;
import com.google.android.gms.wearable.Channel;
import com.google.android.gms.wearable.ChannelApi;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.internal.AmsEntityUpdateParcelable;
import com.google.android.gms.wearable.internal.AncsNotificationParcelable;
import com.google.android.gms.wearable.internal.CapabilityInfoParcelable;
import com.google.android.gms.wearable.internal.ChannelEventParcelable;
import com.google.android.gms.wearable.internal.MessageEventParcelable;
import com.google.android.gms.wearable.internal.NodeParcelable;
import com.google.android.gms.wearable.internal.zzaw;
import com.google.android.gms.wearable.zzj;
import java.util.List;

public abstract class WearableListenerService
extends Service
implements CapabilityApi.CapabilityListener,
ChannelApi.ChannelListener,
DataApi.DataListener,
MessageApi.MessageListener,
NodeApi.NodeListener,
NodeApi.zza {
    public static final String BIND_LISTENER_INTENT_ACTION = "com.google.android.gms.wearable.BIND_LISTENER";
    private boolean zzQl;
    private String zzTJ;
    private IBinder zzakD;
    private Handler zzbro;
    private final Object zzbrp = new Object();

    public final IBinder onBind(Intent intent) {
        if (BIND_LISTENER_INTENT_ACTION.equals(intent.getAction())) {
            return this.zzakD;
        }
        return null;
    }

    @Override
    public void onCapabilityChanged(CapabilityInfo capabilityInfo) {
    }

    @Override
    public void onChannelClosed(Channel channel, int n2, int n3) {
    }

    @Override
    public void onChannelOpened(Channel channel) {
    }

    @Override
    public void onConnectedNodes(List<Node> list) {
    }

    public void onCreate() {
        super.onCreate();
        if (Log.isLoggable((String)"WearableLS", (int)3)) {
            Log.d((String)"WearableLS", (String)("onCreate: " + new ComponentName(this.getPackageName(), this.getClass().getName()).flattenToShortString()));
        }
        this.zzTJ = this.getPackageName();
        HandlerThread handlerThread = new HandlerThread("WearableListenerService");
        handlerThread.start();
        this.zzbro = new Handler(handlerThread.getLooper());
        this.zzakD = new zza();
    }

    @Override
    public void onDataChanged(DataEventBuffer dataEventBuffer) {
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void onDestroy() {
        if (Log.isLoggable((String)"WearableLS", (int)3)) {
            Log.d((String)"WearableLS", (String)("onDestroy: " + new ComponentName(this.getPackageName(), this.getClass().getName()).flattenToShortString()));
        }
        Object object = this.zzbrp;
        synchronized (object) {
            this.zzQl = true;
            if (this.zzbro == null) {
                throw new IllegalStateException("onDestroy: mServiceHandler not set, did you override onCreate() but forget to call super.onCreate()?");
            }
            this.zzbro.getLooper().quit();
        }
        super.onDestroy();
    }

    @Override
    public void onInputClosed(Channel channel, int n2, int n3) {
    }

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
    }

    @Override
    public void onOutputClosed(Channel channel, int n2, int n3) {
    }

    @Override
    public void onPeerConnected(Node node) {
    }

    @Override
    public void onPeerDisconnected(Node node) {
    }

    private final class zza
    extends zzaw.zza {
        private volatile int zzakz = -1;

        private zza() {
        }

        private void zzIx() throws SecurityException {
            int n2 = Binder.getCallingUid();
            if (n2 == this.zzakz) {
                return;
            }
            if (GooglePlayServicesUtil.zzf((Context)WearableListenerService.this, n2)) {
                this.zzakz = n2;
                return;
            }
            throw new SecurityException("Caller is not GooglePlayServices");
        }

        private boolean zza(Runnable runnable, String string2, Object object) {
            if (!(WearableListenerService.this instanceof zzj)) {
                return false;
            }
            return this.zzb(runnable, string2, object);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        private boolean zzb(Runnable runnable, String object, Object object2) {
            if (Log.isLoggable((String)"WearableLS", (int)3)) {
                Log.d((String)"WearableLS", (String)String.format("%s: %s %s", object, WearableListenerService.this.zzTJ, object2));
            }
            this.zzIx();
            object = WearableListenerService.this.zzbrp;
            synchronized (object) {
                if (WearableListenerService.this.zzQl) {
                    return false;
                }
                WearableListenerService.this.zzbro.post(runnable);
                return true;
            }
        }

        @Override
        public void onConnectedNodes(final List<NodeParcelable> list) {
            this.zzb(new Runnable(){

                @Override
                public void run() {
                    WearableListenerService.this.onConnectedNodes(list);
                }
            }, "onConnectedNodes", list);
        }

        @Override
        public void zza(final AmsEntityUpdateParcelable amsEntityUpdateParcelable) {
            this.zza(new Runnable(){

                @Override
                public void run() {
                    ((zzj)WearableListenerService.this).zza(amsEntityUpdateParcelable);
                }
            }, "onEntityUpdate", amsEntityUpdateParcelable);
        }

        @Override
        public void zza(final AncsNotificationParcelable ancsNotificationParcelable) {
            this.zza(new Runnable(){

                @Override
                public void run() {
                    ((zzj)WearableListenerService.this).zza(ancsNotificationParcelable);
                }
            }, "onNotificationReceived", ancsNotificationParcelable);
        }

        @Override
        public void zza(final CapabilityInfoParcelable capabilityInfoParcelable) {
            this.zzb(new Runnable(){

                @Override
                public void run() {
                    WearableListenerService.this.onCapabilityChanged(capabilityInfoParcelable);
                }
            }, "onConnectedCapabilityChanged", capabilityInfoParcelable);
        }

        @Override
        public void zza(final ChannelEventParcelable channelEventParcelable) {
            this.zzb(new Runnable(){

                @Override
                public void run() {
                    channelEventParcelable.zza(WearableListenerService.this);
                }
            }, "onChannelEvent", channelEventParcelable);
        }

        @Override
        public void zza(final MessageEventParcelable messageEventParcelable) {
            this.zzb(new Runnable(){

                @Override
                public void run() {
                    WearableListenerService.this.onMessageReceived(messageEventParcelable);
                }
            }, "onMessageReceived", messageEventParcelable);
        }

        @Override
        public void zza(final NodeParcelable nodeParcelable) {
            this.zzb(new Runnable(){

                @Override
                public void run() {
                    WearableListenerService.this.onPeerConnected(nodeParcelable);
                }
            }, "onPeerConnected", nodeParcelable);
        }

        @Override
        public void zzag(final DataHolder dataHolder) {
            Runnable runnable = new Runnable(){

                @Override
                public void run() {
                    DataEventBuffer dataEventBuffer = new DataEventBuffer(dataHolder);
                    try {
                        WearableListenerService.this.onDataChanged(dataEventBuffer);
                        return;
                    }
                    finally {
                        dataEventBuffer.release();
                    }
                }
            };
            try {
                boolean bl2 = this.zzb(runnable, "onDataItemChanged", dataHolder);
                if (!bl2) {
                    dataHolder.close();
                }
                return;
            }
            catch (Throwable throwable) {
                dataHolder.close();
                throw throwable;
            }
        }

        @Override
        public void zzb(final NodeParcelable nodeParcelable) {
            this.zzb(new Runnable(){

                @Override
                public void run() {
                    WearableListenerService.this.onPeerDisconnected(nodeParcelable);
                }
            }, "onPeerDisconnected", nodeParcelable);
        }
    }
}

