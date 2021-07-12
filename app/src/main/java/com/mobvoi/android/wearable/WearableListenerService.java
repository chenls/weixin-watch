/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.app.Service
 *  android.content.Intent
 *  android.os.Binder
 *  android.os.Handler
 *  android.os.HandlerThread
 *  android.os.IBinder
 *  android.os.RemoteException
 *  android.os.SystemClock
 */
package com.mobvoi.android.wearable;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.SystemClock;
import com.mobvoi.android.wearable.DataApi;
import com.mobvoi.android.wearable.DataEvent;
import com.mobvoi.android.wearable.DataEventBuffer;
import com.mobvoi.android.wearable.MessageApi;
import com.mobvoi.android.wearable.MessageEvent;
import com.mobvoi.android.wearable.Node;
import com.mobvoi.android.wearable.NodeApi;
import com.mobvoi.android.wearable.internal.DataHolder;
import com.mobvoi.android.wearable.internal.MessageEventHolder;
import com.mobvoi.android.wearable.internal.NodeHolder;
import mobvoiapi.ag;
import mobvoiapi.bh;
import mobvoiapi.bp;
import mobvoiapi.br;

public abstract class WearableListenerService
extends Service
implements DataApi.DataListener,
MessageApi.MessageListener,
NodeApi.NodeListener {
    private static volatile Handler a;
    private IBinder b;
    private volatile int c = -1;
    private final Object d = new Object();
    private boolean e = false;
    private String f;

    static Handler a() {
        return a;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean a(int n2) {
        boolean bl2 = false;
        String[] stringArray = this.getPackageManager().getPackagesForUid(n2);
        String string2 = this.getPackageName();
        boolean bl3 = bl2;
        if (stringArray == null) return bl3;
        int n3 = stringArray.length;
        n2 = 0;
        while (true) {
            bl3 = bl2;
            if (n2 >= n3) return bl3;
            String string3 = stringArray[n2];
            if ("com.mobvoi.android".equals(string3)) return true;
            if (br.a(string3)) return true;
            if (string2.equals(string3)) {
                return true;
            }
            ++n2;
        }
    }

    static boolean a(WearableListenerService wearableListenerService) {
        return wearableListenerService.e;
    }

    private static void b() {
        if (a == null) {
            HandlerThread handlerThread = new HandlerThread("WearableListenerService");
            handlerThread.start();
            a = new Handler(handlerThread.getLooper());
        }
    }

    private void c() {
        block3: {
            block2: {
                int n2 = android.os.Binder.getCallingUid();
                if (n2 == this.c) break block2;
                if (!this.a(n2)) break block3;
                this.c = n2;
            }
            return;
        }
        throw new SecurityException("Caller is not MobvoiServices");
    }

    public IBinder onBind(Intent object) {
        bp.a("WearableListenerService", "on bind, package name = " + this.getPackageName());
        object = object.getAction();
        if ("com.mobvoi.android.wearable.BIND_LISTENER".equals(object) || "com.mobvoi.android.wearable.DATA_CHANGED".equals(object) || "com.mobvoi.android.wearable.MESSAGE_RECEIVED".equals(object) || "com.mobvoi.android.wearable.NODE_CHANGED".equals(object)) {
            bp.a("WearableListenerService", "on bind success, package name = " + this.getPackageName() + ", intent = " + (String)object);
            return this.b;
        }
        ag.a("WearableListenerService", "unknown action: %s", object);
        return null;
    }

    public void onCreate() {
        super.onCreate();
        bp.a("WearableListenerService", "on create start, package name = " + this.getPackageName() + ".");
        this.e = false;
        this.f = this.getPackageName();
        this.b = new Binder();
        WearableListenerService.b();
        bp.a("WearableListenerService", "on create success, package name = " + this.getPackageName() + ".");
    }

    @Override
    public void onDataChanged(DataEventBuffer dataEventBuffer) {
    }

    public void onDestroy() {
        bp.a("WearableListenerService", "onDestroy");
        this.e = true;
        super.onDestroy();
    }

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
    }

    @Override
    public void onPeerConnected(Node node) {
    }

    @Override
    public void onPeerDisconnected(Node node) {
    }

    class Binder
    extends bh.a {
        WearableListenerService a;

        private Binder() {
            this.a = WearableListenerService.this;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void onDataChanged(DataHolder object) throws RemoteException {
            final DataEventBuffer dataEventBuffer = new DataEventBuffer((DataHolder)object);
            object = null;
            if (dataEventBuffer.getCount() > 0) {
                object = ((DataEvent)dataEventBuffer.get(0)).getDataItem().getUri().getPath();
            }
            bp.a("WearableListenerService", "on data changed1, path=" + (String)object);
            WearableListenerService.this.c();
            Object object2 = WearableListenerService.this.d;
            synchronized (object2) {
                bp.a("WearableListenerService", "on data changed2, path=" + (String)object);
                if (!WearableListenerService.a(this.a)) {
                    bp.a("WearableListenerService", "on data changed3, path=" + (String)object);
                    WearableListenerService.a().post(new Runnable((String)object){
                        final /* synthetic */ String b;
                        {
                            this.b = string2;
                        }

                        @Override
                        public void run() {
                            long l2 = SystemClock.elapsedRealtime();
                            Binder.this.a.onDataChanged(dataEventBuffer);
                            l2 = SystemClock.elapsedRealtime() - l2;
                            bp.a("WearableListenerService", "on data changed4, path=" + this.b + ", time used: " + l2);
                            if (l2 > 500L) {
                                bp.a("WearableListenerService", "heavy operation detected: " + this.b);
                            }
                        }
                    });
                }
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void onMessageReceived(final MessageEventHolder messageEventHolder) throws RemoteException {
            bp.a("WearableListenerService", "on message received1, path=" + messageEventHolder.getPath());
            WearableListenerService.this.c();
            Object object = WearableListenerService.this.d;
            synchronized (object) {
                bp.a("WearableListenerService", "on message received2, path=" + messageEventHolder.getPath());
                if (!WearableListenerService.a(this.a)) {
                    bp.a("WearableListenerService", "on message received3, path=" + messageEventHolder.getPath());
                    WearableListenerService.a().post(new Runnable(){

                        @Override
                        public void run() {
                            long l2 = SystemClock.elapsedRealtime();
                            Binder.this.a.onMessageReceived(messageEventHolder);
                            l2 = SystemClock.elapsedRealtime() - l2;
                            bp.a("WearableListenerService", "on message received4, path=" + messageEventHolder.getPath() + ", time used: " + l2);
                            if (l2 > 500L) {
                                bp.a("WearableListenerService", "heavy operation detected: " + messageEventHolder.getPath());
                            }
                        }
                    });
                }
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void onPeerConnected(final NodeHolder nodeHolder) throws RemoteException {
            bp.a("WearableListenerService", "on peer connected1, package name = " + WearableListenerService.this.getPackageName());
            WearableListenerService.this.c();
            Object object = WearableListenerService.this.d;
            synchronized (object) {
                bp.a("WearableListenerService", "on peer connected2, package name = " + WearableListenerService.this.getPackageName());
                if (!WearableListenerService.a(this.a)) {
                    bp.a("WearableListenerService", "on peer connected3, package name = " + WearableListenerService.this.getPackageName());
                    WearableListenerService.a().post(new Runnable(){

                        @Override
                        public void run() {
                            long l2 = SystemClock.elapsedRealtime();
                            Binder.this.a.onPeerConnected(nodeHolder);
                            l2 = SystemClock.elapsedRealtime() - l2;
                            bp.a("WearableListenerService", "on peer connected4, package name = " + WearableListenerService.this.getPackageName() + ", time used: " + l2);
                            if (l2 > 500L) {
                                bp.a("WearableListenerService", "heavy operation detected");
                            }
                        }
                    });
                }
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void onPeerDisconnected(final NodeHolder nodeHolder) throws RemoteException {
            bp.a("WearableListenerService", "on peer disconnected1, package name = " + WearableListenerService.this.getPackageName());
            WearableListenerService.this.c();
            Object object = WearableListenerService.this.d;
            synchronized (object) {
                bp.a("WearableListenerService", "on peer disconnected2, package name = " + WearableListenerService.this.getPackageName());
                if (!WearableListenerService.a(this.a)) {
                    bp.a("WearableListenerService", "on peer disconnected3, package name = " + WearableListenerService.this.getPackageName());
                    WearableListenerService.a().post(new Runnable(){

                        @Override
                        public void run() {
                            long l2 = SystemClock.elapsedRealtime();
                            Binder.this.a.onPeerDisconnected(nodeHolder);
                            l2 = SystemClock.elapsedRealtime() - l2;
                            bp.a("WearableListenerService", "on peer disconnected4, package name = " + WearableListenerService.this.getPackageName() + ", time used: " + l2);
                            if (l2 > 500L) {
                                bp.a("WearableListenerService", "heavy operation detected");
                            }
                        }
                    });
                }
                return;
            }
        }
    }
}

