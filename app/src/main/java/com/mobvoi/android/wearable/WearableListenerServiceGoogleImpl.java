/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.ComponentName
 *  android.content.Intent
 *  android.content.ServiceConnection
 *  android.os.IBinder
 *  android.os.RemoteException
 */
package com.mobvoi.android.wearable;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.WearableListenerService;
import com.mobvoi.android.wearable.DataEvent;
import com.mobvoi.android.wearable.DataEventBuffer;
import com.mobvoi.android.wearable.internal.DataEventParcelable;
import com.mobvoi.android.wearable.internal.DataHolder;
import com.mobvoi.android.wearable.internal.MessageEventHolder;
import com.mobvoi.android.wearable.internal.NodeHolder;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import mobvoiapi.bh;
import mobvoiapi.bp;
import mobvoiapi.z;

public class WearableListenerServiceGoogleImpl
extends WearableListenerService {
    private bh a;
    private ServiceConnection b;
    private CountDownLatch c = new CountDownLatch(1);

    static /* synthetic */ bh a(WearableListenerServiceGoogleImpl wearableListenerServiceGoogleImpl, bh bh2) {
        wearableListenerServiceGoogleImpl.a = bh2;
        return bh2;
    }

    private boolean a() {
        try {
            boolean bl2 = this.c.await(100L, TimeUnit.MILLISECONDS);
            return bl2;
        }
        catch (InterruptedException interruptedException) {
            return false;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (this.a == null) {
            try {
                Intent intent = new Intent("com.mobvoi.android.wearable.BIND_LISTENER").setPackage(this.getPackageName());
                if (this.b == null) {
                    this.b = new ForwardServiceConnection();
                }
                if (this.bindService(intent, this.b, 1)) {
                    bp.a("WearableListenerService", "bind to mobvoi wearable listener service success.");
                    return;
                }
                bp.d("WearableListenerService", "bind to mobvoi wearable listener service failed.");
                return;
            }
            catch (Exception exception) {
                bp.a("WearableListenerService", "bind service failed.", exception);
            }
        }
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void onDataChanged(com.google.android.gms.wearable.DataEventBuffer object) {
        DataEventBuffer dataEventBuffer = z.a((com.google.android.gms.wearable.DataEventBuffer)object);
        if (dataEventBuffer == null || dataEventBuffer.getCount() <= 0) {
            return;
        }
        ArrayList<DataEventParcelable> arrayList = new ArrayList<DataEventParcelable>();
        for (int i2 = 0; i2 < dataEventBuffer.getCount(); ++i2) {
            arrayList.add((DataEventParcelable)((DataEvent)dataEventBuffer.get(i2)));
        }
        object = new DataHolder(((com.google.android.gms.wearable.DataEventBuffer)object).getStatus().getStatusCode(), null, arrayList);
        try {
            if (this.a()) {
                this.a.onDataChanged((DataHolder)object);
                return;
            }
        }
        catch (RemoteException remoteException) {
            bp.a("WearableListenerService", "get remote exception in onDataChanged.", remoteException);
            return;
        }
        {
            bp.c("WearableListenerService", "discard a onDataChanged event, no mobvoi listener is ready.");
            return;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (this.b != null) {
            this.unbindService(this.b);
        }
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void onMessageReceived(MessageEvent object) {
        if (object == null) {
            return;
        }
        object = new MessageEventHolder(object.getRequestId(), object.getSourceNodeId(), object.getPath(), object.getData());
        try {
            if (this.a()) {
                this.a.onMessageReceived((MessageEventHolder)object);
                return;
            }
        }
        catch (RemoteException remoteException) {
            bp.a("WearableListenerService", "get remote exception in onMessageReceived.", remoteException);
            return;
        }
        {
            bp.c("WearableListenerService", "discard a onMessageReceived event, no mobvoi listener is ready.");
            return;
        }
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void onPeerConnected(Node object) {
        if ((object = (NodeHolder)z.a((Node)object)) == null) {
            return;
        }
        try {
            if (this.a()) {
                this.a.onPeerConnected((NodeHolder)object);
                return;
            }
        }
        catch (RemoteException remoteException) {
            bp.a("WearableListenerService", "get remote exception in onPeerConnected.", remoteException);
            return;
        }
        {
            bp.c("WearableListenerService", "discard a onPeerConnected event, no mobvoi listener is ready.");
            return;
        }
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void onPeerDisconnected(Node object) {
        if ((object = (NodeHolder)z.a((Node)object)) == null) {
            return;
        }
        try {
            if (this.a()) {
                this.a.onPeerDisconnected((NodeHolder)object);
                return;
            }
        }
        catch (RemoteException remoteException) {
            bp.a("WearableListenerService", "get remote exception in onPeerDisconnected.", remoteException);
            return;
        }
        {
            bp.c("WearableListenerService", "discard a onPeerDisconnected event, no mobvoi listener is ready.");
            return;
        }
    }

    class ForwardServiceConnection
    implements ServiceConnection {
        private ForwardServiceConnection() {
        }

        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            WearableListenerServiceGoogleImpl.a(WearableListenerServiceGoogleImpl.this, bh.a.asInterface(iBinder));
            WearableListenerServiceGoogleImpl.this.c.countDown();
        }

        public void onServiceDisconnected(ComponentName componentName) {
        }
    }
}

