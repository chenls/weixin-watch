/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.os.RemoteException
 */
package mobvoiapi;

import android.os.RemoteException;
import com.mobvoi.android.common.api.MobvoiApiClient;
import com.mobvoi.android.common.api.PendingResult;
import com.mobvoi.android.common.api.Result;
import com.mobvoi.android.common.api.Status;
import com.mobvoi.android.wearable.Node;
import com.mobvoi.android.wearable.NodeApi;
import java.util.ArrayList;
import java.util.List;
import mobvoiapi.bj;
import mobvoiapi.bm;

public class be
implements NodeApi {
    @Override
    public PendingResult<Status> addListener(MobvoiApiClient mobvoiApiClient, final NodeApi.NodeListener nodeListener) {
        return mobvoiApiClient.setResult(new bm<Status>(){

            @Override
            protected /* synthetic */ Result a(Status status) {
                return this.c(status);
            }

            @Override
            protected void a(bj bj2) throws RemoteException {
                bj2.a((bm<Status>)this, nodeListener);
            }

            protected Status c(Status status) {
                return status;
            }
        });
    }

    @Override
    public PendingResult<NodeApi.GetConnectedNodesResult> getConnectedNodes(MobvoiApiClient mobvoiApiClient) {
        return mobvoiApiClient.setResult(new bm<NodeApi.GetConnectedNodesResult>(){

            @Override
            protected /* synthetic */ Result a(Status status) {
                return this.c(status);
            }

            @Override
            protected void a(bj bj2) throws RemoteException {
                bj2.b(this);
            }

            protected NodeApi.GetConnectedNodesResult c(final Status status) {
                return new NodeApi.GetConnectedNodesResult(){

                    @Override
                    public List<Node> getNodes() {
                        return new ArrayList<Node>();
                    }

                    @Override
                    public Status getStatus() {
                        return status;
                    }
                };
            }
        });
    }

    @Override
    public PendingResult<NodeApi.GetLocalNodeResult> getLocalNode(MobvoiApiClient mobvoiApiClient) {
        return mobvoiApiClient.setResult(new bm<NodeApi.GetLocalNodeResult>(){

            @Override
            protected /* synthetic */ Result a(Status status) {
                return this.c(status);
            }

            @Override
            protected void a(bj bj2) throws RemoteException {
                bj2.c(this);
            }

            protected NodeApi.GetLocalNodeResult c(final Status status) {
                return new NodeApi.GetLocalNodeResult(){

                    @Override
                    public Node getNode() {
                        return new Node(){

                            @Override
                            public String getDisplayName() {
                                return "";
                            }

                            @Override
                            public String getId() {
                                return "";
                            }

                            @Override
                            public boolean isNearby() {
                                return true;
                            }
                        };
                    }

                    @Override
                    public Status getStatus() {
                        return status;
                    }
                };
            }
        });
    }

    @Override
    public PendingResult<Status> removeListener(MobvoiApiClient mobvoiApiClient, final NodeApi.NodeListener nodeListener) {
        return mobvoiApiClient.setResult(new bm<Status>(){

            @Override
            protected /* synthetic */ Result a(Status status) {
                return this.c(status);
            }

            @Override
            protected void a(bj bj2) throws RemoteException {
                bj2.b((bm<Status>)this, nodeListener);
            }

            protected Status c(Status status) {
                return status;
            }
        });
    }
}

