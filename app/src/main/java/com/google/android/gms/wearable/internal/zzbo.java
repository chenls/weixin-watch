/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.os.ParcelFileDescriptor$AutoCloseInputStream
 *  android.os.ParcelFileDescriptor$AutoCloseOutputStream
 */
package com.google.android.gms.wearable.internal;

import android.os.ParcelFileDescriptor;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.zza;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.internal.zzx;
import com.google.android.gms.wearable.CapabilityApi;
import com.google.android.gms.wearable.CapabilityInfo;
import com.google.android.gms.wearable.Channel;
import com.google.android.gms.wearable.ChannelApi;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataItemBuffer;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.internal.AddLocalCapabilityResponse;
import com.google.android.gms.wearable.internal.CapabilityInfoParcelable;
import com.google.android.gms.wearable.internal.ChannelImpl;
import com.google.android.gms.wearable.internal.ChannelReceiveFileResponse;
import com.google.android.gms.wearable.internal.ChannelSendFileResponse;
import com.google.android.gms.wearable.internal.CloseChannelResponse;
import com.google.android.gms.wearable.internal.DeleteDataItemsResponse;
import com.google.android.gms.wearable.internal.GetAllCapabilitiesResponse;
import com.google.android.gms.wearable.internal.GetCapabilityResponse;
import com.google.android.gms.wearable.internal.GetChannelInputStreamResponse;
import com.google.android.gms.wearable.internal.GetChannelOutputStreamResponse;
import com.google.android.gms.wearable.internal.GetConnectedNodesResponse;
import com.google.android.gms.wearable.internal.GetDataItemResponse;
import com.google.android.gms.wearable.internal.GetFdForAssetResponse;
import com.google.android.gms.wearable.internal.GetLocalNodeResponse;
import com.google.android.gms.wearable.internal.OpenChannelResponse;
import com.google.android.gms.wearable.internal.PutDataResponse;
import com.google.android.gms.wearable.internal.RemoveLocalCapabilityResponse;
import com.google.android.gms.wearable.internal.SendMessageResponse;
import com.google.android.gms.wearable.internal.zzaz;
import com.google.android.gms.wearable.internal.zzbb;
import com.google.android.gms.wearable.internal.zzbk;
import com.google.android.gms.wearable.internal.zzj;
import com.google.android.gms.wearable.internal.zzl;
import com.google.android.gms.wearable.internal.zzx;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.FutureTask;

final class zzbo {
    private static Map<String, CapabilityInfo> zzG(List<CapabilityInfoParcelable> object) {
        HashMap<String, CapabilityInfo> hashMap = new HashMap<String, CapabilityInfo>(object.size() * 2);
        object = object.iterator();
        while (object.hasNext()) {
            CapabilityInfoParcelable capabilityInfoParcelable = (CapabilityInfoParcelable)object.next();
            hashMap.put(capabilityInfoParcelable.getName(), new zzj.zzc(capabilityInfoParcelable));
        }
        return hashMap;
    }

    static final class zza
    extends zzb<CapabilityApi.AddLocalCapabilityResult> {
        public zza(zza.zzb<CapabilityApi.AddLocalCapabilityResult> zzb2) {
            super(zzb2);
        }

        @Override
        public void zza(AddLocalCapabilityResponse addLocalCapabilityResponse) {
            this.zzX(new zzj.zza(zzbk.zzgc(addLocalCapabilityResponse.statusCode)));
        }
    }

    static abstract class zzb<T>
    extends com.google.android.gms.wearable.internal.zza {
        private zza.zzb<T> zzUz;

        public zzb(zza.zzb<T> zzb2) {
            this.zzUz = zzb2;
        }

        public void zzX(T t2) {
            zza.zzb<T> zzb2 = this.zzUz;
            if (zzb2 != null) {
                zzb2.zzs(t2);
                this.zzUz = null;
            }
        }
    }

    static final class zzc
    extends zzb<Status> {
        public zzc(zza.zzb<Status> zzb2) {
            super(zzb2);
        }

        @Override
        public void zza(CloseChannelResponse closeChannelResponse) {
            this.zzX(new Status(closeChannelResponse.statusCode));
        }
    }

    static final class zzd
    extends zzb<Status> {
        public zzd(zza.zzb<Status> zzb2) {
            super(zzb2);
        }

        @Override
        public void zzb(CloseChannelResponse closeChannelResponse) {
            this.zzX(new Status(closeChannelResponse.statusCode));
        }
    }

    static final class zze
    extends zzb<DataApi.DeleteDataItemsResult> {
        public zze(zza.zzb<DataApi.DeleteDataItemsResult> zzb2) {
            super(zzb2);
        }

        @Override
        public void zza(DeleteDataItemsResponse deleteDataItemsResponse) {
            this.zzX(new zzx.zzb(zzbk.zzgc(deleteDataItemsResponse.statusCode), deleteDataItemsResponse.zzbsz));
        }
    }

    static final class zzf
    extends zzb<CapabilityApi.GetAllCapabilitiesResult> {
        public zzf(zza.zzb<CapabilityApi.GetAllCapabilitiesResult> zzb2) {
            super(zzb2);
        }

        @Override
        public void zza(GetAllCapabilitiesResponse getAllCapabilitiesResponse) {
            this.zzX(new zzj.zzd(zzbk.zzgc(getAllCapabilitiesResponse.statusCode), zzbo.zzG(getAllCapabilitiesResponse.zzbsA)));
        }
    }

    static final class zzg
    extends zzb<CapabilityApi.GetCapabilityResult> {
        public zzg(zza.zzb<CapabilityApi.GetCapabilityResult> zzb2) {
            super(zzb2);
        }

        @Override
        public void zza(GetCapabilityResponse getCapabilityResponse) {
            this.zzX(new zzj.zze(zzbk.zzgc(getCapabilityResponse.statusCode), new zzj.zzc(getCapabilityResponse.zzbsB)));
        }
    }

    static final class zzh
    extends zzb<Channel.GetInputStreamResult> {
        private final com.google.android.gms.wearable.internal.zzt zzbtd;

        public zzh(zza.zzb<Channel.GetInputStreamResult> zzb2, com.google.android.gms.wearable.internal.zzt zzt2) {
            super(zzb2);
            this.zzbtd = zzx.zzz(zzt2);
        }

        @Override
        public void zza(GetChannelInputStreamResponse getChannelInputStreamResponse) {
            com.google.android.gms.wearable.internal.zzp zzp2 = null;
            if (getChannelInputStreamResponse.zzbsC != null) {
                zzp2 = new com.google.android.gms.wearable.internal.zzp((InputStream)new ParcelFileDescriptor.AutoCloseInputStream(getChannelInputStreamResponse.zzbsC));
                this.zzbtd.zza(zzp2.zzIJ());
            }
            this.zzX(new ChannelImpl.zza(new Status(getChannelInputStreamResponse.statusCode), zzp2));
        }
    }

    static final class zzi
    extends zzb<Channel.GetOutputStreamResult> {
        private final com.google.android.gms.wearable.internal.zzt zzbtd;

        public zzi(zza.zzb<Channel.GetOutputStreamResult> zzb2, com.google.android.gms.wearable.internal.zzt zzt2) {
            super(zzb2);
            this.zzbtd = zzx.zzz(zzt2);
        }

        @Override
        public void zza(GetChannelOutputStreamResponse getChannelOutputStreamResponse) {
            com.google.android.gms.wearable.internal.zzq zzq2 = null;
            if (getChannelOutputStreamResponse.zzbsC != null) {
                zzq2 = new com.google.android.gms.wearable.internal.zzq((OutputStream)new ParcelFileDescriptor.AutoCloseOutputStream(getChannelOutputStreamResponse.zzbsC));
                this.zzbtd.zza(zzq2.zzIJ());
            }
            this.zzX(new ChannelImpl.zzb(new Status(getChannelOutputStreamResponse.statusCode), zzq2));
        }
    }

    static final class zzj
    extends zzb<NodeApi.GetConnectedNodesResult> {
        public zzj(zza.zzb<NodeApi.GetConnectedNodesResult> zzb2) {
            super(zzb2);
        }

        @Override
        public void zza(GetConnectedNodesResponse getConnectedNodesResponse) {
            ArrayList<Node> arrayList = new ArrayList<Node>();
            arrayList.addAll(getConnectedNodesResponse.zzbsI);
            this.zzX(new zzbb.zza(zzbk.zzgc(getConnectedNodesResponse.statusCode), arrayList));
        }
    }

    static final class zzk
    extends zzb<DataApi.DataItemResult> {
        public zzk(zza.zzb<DataApi.DataItemResult> zzb2) {
            super(zzb2);
        }

        @Override
        public void zza(GetDataItemResponse getDataItemResponse) {
            this.zzX(new zzx.zza(zzbk.zzgc(getDataItemResponse.statusCode), getDataItemResponse.zzbsJ));
        }
    }

    static final class zzl
    extends zzb<DataItemBuffer> {
        public zzl(zza.zzb<DataItemBuffer> zzb2) {
            super(zzb2);
        }

        @Override
        public void zzah(DataHolder dataHolder) {
            this.zzX(new DataItemBuffer(dataHolder));
        }
    }

    static final class zzm
    extends zzb<DataApi.GetFdForAssetResult> {
        public zzm(zza.zzb<DataApi.GetFdForAssetResult> zzb2) {
            super(zzb2);
        }

        @Override
        public void zza(GetFdForAssetResponse getFdForAssetResponse) {
            this.zzX(new zzx.zzc(zzbk.zzgc(getFdForAssetResponse.statusCode), getFdForAssetResponse.zzbsK));
        }
    }

    static final class zzn
    extends zzb<NodeApi.GetLocalNodeResult> {
        public zzn(zza.zzb<NodeApi.GetLocalNodeResult> zzb2) {
            super(zzb2);
        }

        @Override
        public void zza(GetLocalNodeResponse getLocalNodeResponse) {
            this.zzX(new zzbb.zzb(zzbk.zzgc(getLocalNodeResponse.statusCode), getLocalNodeResponse.zzbsL));
        }
    }

    static final class zzo
    extends com.google.android.gms.wearable.internal.zza {
        zzo() {
        }

        @Override
        public void zza(Status status) {
        }
    }

    static final class zzp
    extends zzb<ChannelApi.OpenChannelResult> {
        public zzp(zza.zzb<ChannelApi.OpenChannelResult> zzb2) {
            super(zzb2);
        }

        @Override
        public void zza(OpenChannelResponse openChannelResponse) {
            this.zzX(new zzl.zza(zzbk.zzgc(openChannelResponse.statusCode), openChannelResponse.zzbsc));
        }
    }

    static final class zzq
    extends zzb<DataApi.DataItemResult> {
        private final List<FutureTask<Boolean>> zzzM;

        zzq(zza.zzb<DataApi.DataItemResult> zzb2, List<FutureTask<Boolean>> list) {
            super(zzb2);
            this.zzzM = list;
        }

        @Override
        public void zza(PutDataResponse object) {
            this.zzX(new zzx.zza(zzbk.zzgc(((PutDataResponse)object).statusCode), ((PutDataResponse)object).zzbsJ));
            if (((PutDataResponse)object).statusCode != 0) {
                object = this.zzzM.iterator();
                while (object.hasNext()) {
                    ((FutureTask)object.next()).cancel(true);
                }
            }
        }
    }

    static final class zzr
    extends zzb<Status> {
        public zzr(zza.zzb<Status> zzb2) {
            super(zzb2);
        }

        @Override
        public void zza(ChannelSendFileResponse channelSendFileResponse) {
            this.zzX(new Status(channelSendFileResponse.statusCode));
        }
    }

    static final class zzs
    extends zzb<CapabilityApi.RemoveLocalCapabilityResult> {
        public zzs(zza.zzb<CapabilityApi.RemoveLocalCapabilityResult> zzb2) {
            super(zzb2);
        }

        @Override
        public void zza(RemoveLocalCapabilityResponse removeLocalCapabilityResponse) {
            this.zzX(new zzj.zza(zzbk.zzgc(removeLocalCapabilityResponse.statusCode)));
        }
    }

    static final class zzt
    extends zzb<MessageApi.SendMessageResult> {
        public zzt(zza.zzb<MessageApi.SendMessageResult> zzb2) {
            super(zzb2);
        }

        @Override
        public void zza(SendMessageResponse sendMessageResponse) {
            this.zzX(new zzaz.zzb(zzbk.zzgc(sendMessageResponse.statusCode), sendMessageResponse.zzaNj));
        }
    }

    static final class zzu
    extends zzb<Status> {
        public zzu(zza.zzb<Status> zzb2) {
            super(zzb2);
        }

        @Override
        public void zza(ChannelReceiveFileResponse channelReceiveFileResponse) {
            this.zzX(new Status(channelReceiveFileResponse.statusCode));
        }
    }
}

