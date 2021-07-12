/*
 * Decompiled with CFR 0.151.
 */
package mobvoiapi;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.Wearable;
import com.mobvoi.android.common.api.MobvoiApiClient;
import com.mobvoi.android.common.api.PendingResult;
import com.mobvoi.android.common.api.Status;
import com.mobvoi.android.wearable.NodeApi;
import mobvoiapi.bp;
import mobvoiapi.z;

public class w
implements NodeApi {
    private com.google.android.gms.wearable.NodeApi a = Wearable.NodeApi;

    @Override
    public PendingResult<Status> addListener(MobvoiApiClient object, NodeApi.NodeListener nodeListener) {
        bp.a("MobvoiApiManager", "NodeApiGoogleImpl#addListener()");
        object = z.a((MobvoiApiClient)object);
        return z.a(this.a.addListener((GoogleApiClient)object, z.a(nodeListener)));
    }

    @Override
    public PendingResult<NodeApi.GetConnectedNodesResult> getConnectedNodes(MobvoiApiClient object) {
        bp.a("MobvoiApiManager", "NodeApiGoogleImpl#getConnectedNodes()");
        object = z.a((MobvoiApiClient)object);
        return z.a(this.a.getConnectedNodes((GoogleApiClient)object));
    }

    @Override
    public PendingResult<NodeApi.GetLocalNodeResult> getLocalNode(MobvoiApiClient object) {
        bp.a("MobvoiApiManager", "NodeApiGoogleImpl#getLocalNode()");
        object = z.a((MobvoiApiClient)object);
        return z.a(this.a.getLocalNode((GoogleApiClient)object));
    }

    @Override
    public PendingResult<Status> removeListener(MobvoiApiClient object, NodeApi.NodeListener nodeListener) {
        bp.a("MobvoiApiManager", "NodeApiGoogleImpl#removeListener()");
        object = z.a((MobvoiApiClient)object);
        return z.a(this.a.removeListener((GoogleApiClient)object, z.a(nodeListener)));
    }
}

