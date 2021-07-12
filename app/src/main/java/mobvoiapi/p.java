/*
 * Decompiled with CFR 0.151.
 */
package mobvoiapi;

import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.mobvoi.android.wearable.NodeApi;
import mobvoiapi.bp;
import mobvoiapi.z;

public class p
implements NodeApi.NodeListener {
    private NodeApi.NodeListener a;

    public p(NodeApi.NodeListener nodeListener) {
        this.a = nodeListener;
    }

    public boolean equals(Object object) {
        if (object instanceof p) {
            return this.a.equals(((p)object).a);
        }
        return false;
    }

    public int hashCode() {
        return this.a.hashCode();
    }

    @Override
    public void onPeerConnected(Node node) {
        bp.a("MobvoiApiManager", "NodeListenerWrapper#onPeerConnected()");
        this.a.onPeerConnected(z.a(node));
    }

    @Override
    public void onPeerDisconnected(Node node) {
        bp.a("MobvoiApiManager", "NodeListenerWrapper#onPeerDisconnected()");
        this.a.onPeerDisconnected(z.a(node));
    }
}

