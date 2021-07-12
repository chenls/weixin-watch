/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.IntentFilter
 *  android.os.RemoteException
 */
package mobvoiapi;

import android.content.IntentFilter;
import android.os.RemoteException;
import com.mobvoi.android.wearable.DataApi;
import com.mobvoi.android.wearable.DataEventBuffer;
import com.mobvoi.android.wearable.MessageApi;
import com.mobvoi.android.wearable.NodeApi;
import com.mobvoi.android.wearable.internal.DataHolder;
import com.mobvoi.android.wearable.internal.MessageEventHolder;
import com.mobvoi.android.wearable.internal.NodeHolder;
import mobvoiapi.bh;
import mobvoiapi.bp;

public class bl
extends bh.a {
    private final DataApi.DataListener a;
    private final MessageApi.MessageListener b;
    private final NodeApi.NodeListener c;
    private final IntentFilter[] d;

    private bl(DataApi.DataListener dataListener, MessageApi.MessageListener messageListener, NodeApi.NodeListener nodeListener, IntentFilter[] intentFilterArray) {
        this.a = dataListener;
        this.b = messageListener;
        this.c = nodeListener;
        this.d = intentFilterArray;
    }

    public static bl a(DataApi.DataListener dataListener) {
        bp.a("WearableListener", "create data listener " + dataListener);
        return new bl(dataListener, null, null, null);
    }

    public static bl a(MessageApi.MessageListener messageListener) {
        bp.a("WearableListener", "create: message listener" + messageListener);
        return new bl(null, messageListener, null, null);
    }

    public static bl a(NodeApi.NodeListener nodeListener) {
        bp.a("WearableListener", "create node listener " + nodeListener);
        return new bl(null, null, nodeListener, null);
    }

    public IntentFilter[] a() {
        return this.d;
    }

    @Override
    public void onDataChanged(DataHolder object) throws RemoteException {
        bp.a("WearableListener", "on data changed, dataHolder = " + object + ", listener=" + this.a);
        if (this.a != null) {
            object = new DataEventBuffer((DataHolder)object);
            this.a.onDataChanged((DataEventBuffer)object);
        }
    }

    @Override
    public void onMessageReceived(MessageEventHolder messageEventHolder) throws RemoteException {
        bp.a("WearableListener", "on message received, event = " + messageEventHolder + ", listener = " + this.b);
        if (this.b != null) {
            this.b.onMessageReceived(messageEventHolder);
        }
    }

    @Override
    public void onPeerConnected(NodeHolder nodeHolder) throws RemoteException {
        bp.a("WearableListener", "on peer connected, node = " + nodeHolder + ", listener=" + this.c);
        if (this.c != null) {
            this.c.onPeerConnected(nodeHolder);
        }
    }

    @Override
    public void onPeerDisconnected(NodeHolder nodeHolder) throws RemoteException {
        bp.a("WearableListener", "on peer disconnected, node = " + nodeHolder + ", listener=" + this.c);
        if (this.c != null) {
            this.c.onPeerDisconnected(nodeHolder);
        }
    }
}

