/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.IntentFilter
 *  android.os.RemoteException
 */
package com.google.android.gms.wearable.internal;

import android.content.IntentFilter;
import android.os.RemoteException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.zza;
import com.google.android.gms.common.api.internal.zzq;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.internal.zzb;
import com.google.android.gms.wearable.internal.zzbn;
import com.google.android.gms.wearable.internal.zzbp;
import com.google.android.gms.wearable.internal.zzi;
import java.util.ArrayList;
import java.util.List;

public final class zzbb
implements NodeApi {
    private static zzb.zza<NodeApi.NodeListener> zza(final IntentFilter[] intentFilterArray) {
        return new zzb.zza<NodeApi.NodeListener>(){

            @Override
            public void zza(zzbp zzbp2, zza.zzb<Status> zzb2, NodeApi.NodeListener nodeListener, zzq<NodeApi.NodeListener> zzq2) throws RemoteException {
                zzbp2.zza(zzb2, nodeListener, zzq2, intentFilterArray);
            }
        };
    }

    @Override
    public PendingResult<Status> addListener(GoogleApiClient googleApiClient, NodeApi.NodeListener nodeListener) {
        return com.google.android.gms.wearable.internal.zzb.zza(googleApiClient, zzbb.zza(new IntentFilter[]{zzbn.zzgM("com.google.android.gms.wearable.NODE_CHANGED")}), nodeListener);
    }

    @Override
    public PendingResult<NodeApi.GetConnectedNodesResult> getConnectedNodes(GoogleApiClient googleApiClient) {
        return googleApiClient.zza(new zzi<NodeApi.GetConnectedNodesResult>(googleApiClient){

            @Override
            protected void zza(zzbp zzbp2) throws RemoteException {
                zzbp2.zzt(this);
            }

            protected NodeApi.GetConnectedNodesResult zzbB(Status status) {
                return new zza(status, new ArrayList<Node>());
            }

            @Override
            protected /* synthetic */ Result zzc(Status status) {
                return this.zzbB(status);
            }
        });
    }

    @Override
    public PendingResult<NodeApi.GetLocalNodeResult> getLocalNode(GoogleApiClient googleApiClient) {
        return googleApiClient.zza(new zzi<NodeApi.GetLocalNodeResult>(googleApiClient){

            @Override
            protected void zza(zzbp zzbp2) throws RemoteException {
                zzbp2.zzs(this);
            }

            protected NodeApi.GetLocalNodeResult zzbA(Status status) {
                return new zzb(status, null);
            }

            @Override
            protected /* synthetic */ Result zzc(Status status) {
                return this.zzbA(status);
            }
        });
    }

    @Override
    public PendingResult<Status> removeListener(GoogleApiClient googleApiClient, final NodeApi.NodeListener nodeListener) {
        return googleApiClient.zza(new zzi<Status>(googleApiClient){

            @Override
            protected void zza(zzbp zzbp2) throws RemoteException {
                zzbp2.zza((zza.zzb<Status>)this, nodeListener);
            }

            public Status zzb(Status status) {
                return status;
            }

            @Override
            public /* synthetic */ Result zzc(Status status) {
                return this.zzb(status);
            }
        });
    }

    public static class zza
    implements NodeApi.GetConnectedNodesResult {
        private final Status zzUX;
        private final List<Node> zzbsW;

        public zza(Status status, List<Node> list) {
            this.zzUX = status;
            this.zzbsW = list;
        }

        @Override
        public List<Node> getNodes() {
            return this.zzbsW;
        }

        @Override
        public Status getStatus() {
            return this.zzUX;
        }
    }

    public static class zzb
    implements NodeApi.GetLocalNodeResult {
        private final Status zzUX;
        private final Node zzbsX;

        public zzb(Status status, Node node) {
            this.zzUX = status;
            this.zzbsX = node;
        }

        @Override
        public Node getNode() {
            return this.zzbsX;
        }

        @Override
        public Status getStatus() {
            return this.zzUX;
        }
    }
}

