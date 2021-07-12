/*
 * Decompiled with CFR 0.151.
 */
package com.mobvoi.android.wearable;

import com.mobvoi.android.common.api.MobvoiApiClient;
import com.mobvoi.android.common.api.PendingResult;
import com.mobvoi.android.common.api.Result;
import com.mobvoi.android.common.api.Status;
import com.mobvoi.android.wearable.Node;
import java.util.List;

public interface NodeApi {
    public PendingResult<Status> addListener(MobvoiApiClient var1, NodeListener var2);

    public PendingResult<GetConnectedNodesResult> getConnectedNodes(MobvoiApiClient var1);

    public PendingResult<GetLocalNodeResult> getLocalNode(MobvoiApiClient var1);

    public PendingResult<Status> removeListener(MobvoiApiClient var1, NodeListener var2);

    public static interface GetConnectedNodesResult
    extends Result {
        public List<Node> getNodes();
    }

    public static interface GetLocalNodeResult
    extends Result {
        public Node getNode();
    }

    public static interface NodeListener {
        public void onPeerConnected(Node var1);

        public void onPeerDisconnected(Node var1);
    }
}

