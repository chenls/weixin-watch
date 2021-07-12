/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.IntentFilter
 */
package com.google.android.gms.wearable.internal;

import android.content.IntentFilter;
import com.google.android.gms.common.api.internal.zzq;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.internal.zzx;
import com.google.android.gms.wearable.CapabilityApi;
import com.google.android.gms.wearable.ChannelApi;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.internal.AmsEntityUpdateParcelable;
import com.google.android.gms.wearable.internal.AncsNotificationParcelable;
import com.google.android.gms.wearable.internal.CapabilityInfoParcelable;
import com.google.android.gms.wearable.internal.ChannelEventParcelable;
import com.google.android.gms.wearable.internal.MessageEventParcelable;
import com.google.android.gms.wearable.internal.NodeParcelable;
import com.google.android.gms.wearable.internal.zzaw;
import com.google.android.gms.wearable.zza;
import com.google.android.gms.wearable.zzc;
import java.util.List;

final class zzbq<T>
extends zzaw.zza {
    private zzq<MessageApi.MessageListener> zzbbb;
    private final IntentFilter[] zzbsT;
    private zzq<zza.zza> zzbtp;
    private zzq<zzc.zza> zzbtq;
    private zzq<DataApi.DataListener> zzbtr;
    private zzq<NodeApi.NodeListener> zzbts;
    private zzq<NodeApi.zza> zzbtt;
    private zzq<ChannelApi.ChannelListener> zzbtu;
    private zzq<CapabilityApi.CapabilityListener> zzbtv;
    private final String zzbtw;

    private zzbq(IntentFilter[] intentFilterArray, String string2) {
        this.zzbsT = zzx.zzz(intentFilterArray);
        this.zzbtw = string2;
    }

    private static zzq.zzb<NodeApi.zza> zzI(final List<NodeParcelable> list) {
        return new zzq.zzb<NodeApi.zza>(){

            public void zza(NodeApi.zza zza2) {
                zza2.onConnectedNodes(list);
            }

            @Override
            public void zzpr() {
            }

            @Override
            public /* synthetic */ void zzt(Object object) {
                this.zza((NodeApi.zza)object);
            }
        };
    }

    public static zzbq<ChannelApi.ChannelListener> zza(zzq<ChannelApi.ChannelListener> zzq2, String object, IntentFilter[] intentFilterArray) {
        object = new zzbq(intentFilterArray, zzx.zzz(object));
        ((zzbq)object).zzbtu = zzx.zzz(zzq2);
        return object;
    }

    public static zzbq<DataApi.DataListener> zza(zzq<DataApi.DataListener> zzq2, IntentFilter[] object) {
        object = new zzbq((IntentFilter[])object, null);
        object.zzbtr = zzx.zzz(zzq2);
        return object;
    }

    private static zzq.zzb<DataApi.DataListener> zzai(final DataHolder dataHolder) {
        return new zzq.zzb<DataApi.DataListener>(){

            public void zza(DataApi.DataListener dataListener) {
                try {
                    dataListener.onDataChanged(new DataEventBuffer(dataHolder));
                    return;
                }
                finally {
                    dataHolder.close();
                }
            }

            @Override
            public void zzpr() {
                dataHolder.close();
            }

            @Override
            public /* synthetic */ void zzt(Object object) {
                this.zza((DataApi.DataListener)object);
            }
        };
    }

    private static zzq.zzb<zza.zza> zzb(final AmsEntityUpdateParcelable amsEntityUpdateParcelable) {
        return new zzq.zzb<zza.zza>(){

            public void zza(zza.zza zza2) {
                zza2.zza(amsEntityUpdateParcelable);
            }

            @Override
            public void zzpr() {
            }

            @Override
            public /* synthetic */ void zzt(Object object) {
                this.zza((zza.zza)object);
            }
        };
    }

    private static zzq.zzb<zzc.zza> zzb(final AncsNotificationParcelable ancsNotificationParcelable) {
        return new zzq.zzb<zzc.zza>(){

            public void zza(zzc.zza zza2) {
                zza2.zza(ancsNotificationParcelable);
            }

            @Override
            public void zzpr() {
            }

            @Override
            public /* synthetic */ void zzt(Object object) {
                this.zza((zzc.zza)object);
            }
        };
    }

    private static zzq.zzb<CapabilityApi.CapabilityListener> zzb(final CapabilityInfoParcelable capabilityInfoParcelable) {
        return new zzq.zzb<CapabilityApi.CapabilityListener>(){

            public void zza(CapabilityApi.CapabilityListener capabilityListener) {
                capabilityListener.onCapabilityChanged(capabilityInfoParcelable);
            }

            @Override
            public void zzpr() {
            }

            @Override
            public /* synthetic */ void zzt(Object object) {
                this.zza((CapabilityApi.CapabilityListener)object);
            }
        };
    }

    private static zzq.zzb<ChannelApi.ChannelListener> zzb(final ChannelEventParcelable channelEventParcelable) {
        return new zzq.zzb<ChannelApi.ChannelListener>(){

            public void zzb(ChannelApi.ChannelListener channelListener) {
                channelEventParcelable.zza(channelListener);
            }

            @Override
            public void zzpr() {
            }

            @Override
            public /* synthetic */ void zzt(Object object) {
                this.zzb((ChannelApi.ChannelListener)object);
            }
        };
    }

    private static zzq.zzb<MessageApi.MessageListener> zzb(final MessageEventParcelable messageEventParcelable) {
        return new zzq.zzb<MessageApi.MessageListener>(){

            public void zza(MessageApi.MessageListener messageListener) {
                messageListener.onMessageReceived(messageEventParcelable);
            }

            @Override
            public void zzpr() {
            }

            @Override
            public /* synthetic */ void zzt(Object object) {
                this.zza((MessageApi.MessageListener)object);
            }
        };
    }

    public static zzbq<MessageApi.MessageListener> zzb(zzq<MessageApi.MessageListener> zzq2, IntentFilter[] object) {
        object = new zzbq((IntentFilter[])object, null);
        object.zzbbb = zzx.zzz(zzq2);
        return object;
    }

    private static zzq.zzb<NodeApi.NodeListener> zzc(final NodeParcelable nodeParcelable) {
        return new zzq.zzb<NodeApi.NodeListener>(){

            public void zza(NodeApi.NodeListener nodeListener) {
                nodeListener.onPeerConnected(nodeParcelable);
            }

            @Override
            public void zzpr() {
            }

            @Override
            public /* synthetic */ void zzt(Object object) {
                this.zza((NodeApi.NodeListener)object);
            }
        };
    }

    public static zzbq<NodeApi.NodeListener> zzc(zzq<NodeApi.NodeListener> zzq2, IntentFilter[] object) {
        object = new zzbq((IntentFilter[])object, null);
        object.zzbts = zzx.zzz(zzq2);
        return object;
    }

    private static zzq.zzb<NodeApi.NodeListener> zzd(final NodeParcelable nodeParcelable) {
        return new zzq.zzb<NodeApi.NodeListener>(){

            public void zza(NodeApi.NodeListener nodeListener) {
                nodeListener.onPeerDisconnected(nodeParcelable);
            }

            @Override
            public void zzpr() {
            }

            @Override
            public /* synthetic */ void zzt(Object object) {
                this.zza((NodeApi.NodeListener)object);
            }
        };
    }

    public static zzbq<ChannelApi.ChannelListener> zzd(zzq<ChannelApi.ChannelListener> zzq2, IntentFilter[] object) {
        object = new zzbq((IntentFilter[])object, null);
        object.zzbtu = zzx.zzz(zzq2);
        return object;
    }

    public static zzbq<CapabilityApi.CapabilityListener> zze(zzq<CapabilityApi.CapabilityListener> zzq2, IntentFilter[] object) {
        object = new zzbq((IntentFilter[])object, null);
        object.zzbtv = zzx.zzz(zzq2);
        return object;
    }

    private static void zzh(zzq<?> zzq2) {
        if (zzq2 != null) {
            zzq2.clear();
        }
    }

    public void clear() {
        zzbq.zzh(this.zzbtp);
        this.zzbtp = null;
        zzbq.zzh(this.zzbtq);
        this.zzbtq = null;
        zzbq.zzh(this.zzbtr);
        this.zzbtr = null;
        zzbq.zzh(this.zzbbb);
        this.zzbbb = null;
        zzbq.zzh(this.zzbts);
        this.zzbts = null;
        zzbq.zzh(this.zzbtt);
        this.zzbtt = null;
        zzbq.zzh(this.zzbtu);
        this.zzbtu = null;
        zzbq.zzh(this.zzbtv);
        this.zzbtv = null;
    }

    @Override
    public void onConnectedNodes(List<NodeParcelable> list) {
        if (this.zzbtt != null) {
            this.zzbtt.zza(zzbq.zzI(list));
        }
    }

    public IntentFilter[] zzIO() {
        return this.zzbsT;
    }

    public String zzIP() {
        return this.zzbtw;
    }

    @Override
    public void zza(AmsEntityUpdateParcelable amsEntityUpdateParcelable) {
        if (this.zzbtp != null) {
            this.zzbtp.zza(zzbq.zzb(amsEntityUpdateParcelable));
        }
    }

    @Override
    public void zza(AncsNotificationParcelable ancsNotificationParcelable) {
        if (this.zzbtq != null) {
            this.zzbtq.zza(zzbq.zzb(ancsNotificationParcelable));
        }
    }

    @Override
    public void zza(CapabilityInfoParcelable capabilityInfoParcelable) {
        if (this.zzbtv != null) {
            this.zzbtv.zza(zzbq.zzb(capabilityInfoParcelable));
        }
    }

    @Override
    public void zza(ChannelEventParcelable channelEventParcelable) {
        if (this.zzbtu != null) {
            this.zzbtu.zza(zzbq.zzb(channelEventParcelable));
        }
    }

    @Override
    public void zza(MessageEventParcelable messageEventParcelable) {
        if (this.zzbbb != null) {
            this.zzbbb.zza(zzbq.zzb(messageEventParcelable));
        }
    }

    @Override
    public void zza(NodeParcelable nodeParcelable) {
        if (this.zzbts != null) {
            this.zzbts.zza(zzbq.zzc(nodeParcelable));
        }
    }

    @Override
    public void zzag(DataHolder dataHolder) {
        if (this.zzbtr != null) {
            this.zzbtr.zza(zzbq.zzai(dataHolder));
            return;
        }
        dataHolder.close();
    }

    @Override
    public void zzb(NodeParcelable nodeParcelable) {
        if (this.zzbts != null) {
            this.zzbts.zza(zzbq.zzd(nodeParcelable));
        }
    }
}

