/*
 * Decompiled with CFR 0.151.
 */
package mobvoiapi;

import com.mobvoi.android.common.MobvoiApiManager;
import com.mobvoi.android.common.api.MobvoiApiClient;
import com.mobvoi.android.common.api.PendingResult;
import com.mobvoi.android.common.api.Status;
import com.mobvoi.android.wearable.NodeApi;
import mobvoiapi.ab;
import mobvoiapi.be;
import mobvoiapi.bp;
import mobvoiapi.w;

public class ae
implements NodeApi,
ab {
    private NodeApi a;

    public ae() {
        MobvoiApiManager.getInstance().registerProxy(this);
        this.a();
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void a() {
        if (MobvoiApiManager.getInstance().getGroup() == MobvoiApiManager.ApiGroup.MMS) {
            this.a = new be();
        } else if (MobvoiApiManager.getInstance().getGroup() == MobvoiApiManager.ApiGroup.GMS) {
            this.a = new w();
        }
        bp.a("MobvoiApiManager", "load node api success.");
    }

    @Override
    public PendingResult<Status> addListener(MobvoiApiClient mobvoiApiClient, NodeApi.NodeListener nodeListener) {
        bp.a("MobvoiApiManager", "NodeApiProxy#addListener()");
        return this.a.addListener(mobvoiApiClient, nodeListener);
    }

    @Override
    public PendingResult<NodeApi.GetConnectedNodesResult> getConnectedNodes(MobvoiApiClient mobvoiApiClient) {
        bp.a("MobvoiApiManager", "NodeApiProxy#getConnectedNodes()");
        return this.a.getConnectedNodes(mobvoiApiClient);
    }

    @Override
    public PendingResult<NodeApi.GetLocalNodeResult> getLocalNode(MobvoiApiClient mobvoiApiClient) {
        bp.a("MobvoiApiManager", "NodeApiProxy#getLocalNode()");
        return this.a.getLocalNode(mobvoiApiClient);
    }

    @Override
    public PendingResult<Status> removeListener(MobvoiApiClient mobvoiApiClient, NodeApi.NodeListener nodeListener) {
        bp.a("MobvoiApiManager", "NodeApiProxy#removeListener()");
        return this.a.removeListener(mobvoiApiClient, nodeListener);
    }
}

