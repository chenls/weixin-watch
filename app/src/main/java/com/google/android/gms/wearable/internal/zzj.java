/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.IntentFilter
 *  android.net.Uri
 *  android.os.RemoteException
 */
package com.google.android.gms.wearable.internal;

import android.content.IntentFilter;
import android.net.Uri;
import android.os.RemoteException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.zza;
import com.google.android.gms.common.api.internal.zzq;
import com.google.android.gms.common.internal.zzx;
import com.google.android.gms.wearable.CapabilityApi;
import com.google.android.gms.wearable.CapabilityInfo;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.internal.zzb;
import com.google.android.gms.wearable.internal.zzbn;
import com.google.android.gms.wearable.internal.zzbp;
import com.google.android.gms.wearable.internal.zzi;
import java.util.Map;
import java.util.Set;

public class zzj
implements CapabilityApi {
    private PendingResult<Status> zza(GoogleApiClient googleApiClient, CapabilityApi.CapabilityListener capabilityListener, IntentFilter[] intentFilterArray) {
        return com.google.android.gms.wearable.internal.zzb.zza(googleApiClient, zzj.zza(intentFilterArray), capabilityListener);
    }

    private static zzb.zza<CapabilityApi.CapabilityListener> zza(final IntentFilter[] intentFilterArray) {
        return new zzb.zza<CapabilityApi.CapabilityListener>(){

            @Override
            public void zza(zzbp zzbp2, zza.zzb<Status> zzb2, CapabilityApi.CapabilityListener capabilityListener, zzq<CapabilityApi.CapabilityListener> zzq2) throws RemoteException {
                zzbp2.zza(zzb2, capabilityListener, zzq2, intentFilterArray);
            }
        };
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public PendingResult<Status> addCapabilityListener(GoogleApiClient googleApiClient, CapabilityApi.CapabilityListener object, String string2) {
        boolean bl2 = string2 != null;
        zzx.zzb(bl2, (Object)"capability must not be null");
        zzb zzb2 = new zzb((CapabilityApi.CapabilityListener)object, string2);
        IntentFilter intentFilter = zzbn.zzgM("com.google.android.gms.wearable.CAPABILITY_CHANGED");
        object = string2;
        if (!string2.startsWith("/")) {
            object = "/" + string2;
        }
        intentFilter.addDataPath((String)object, 0);
        return this.zza(googleApiClient, zzb2, new IntentFilter[]{intentFilter});
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public PendingResult<Status> addListener(GoogleApiClient googleApiClient, CapabilityApi.CapabilityListener capabilityListener, Uri uri, int n2) {
        boolean bl2 = uri != null;
        zzx.zzb(bl2, (Object)"uri must not be null");
        bl2 = n2 == 0 || n2 == 1;
        zzx.zzb(bl2, (Object)"invalid filter type");
        return this.zza(googleApiClient, capabilityListener, new IntentFilter[]{zzbn.zza("com.google.android.gms.wearable.CAPABILITY_CHANGED", uri, n2)});
    }

    @Override
    public PendingResult<CapabilityApi.AddLocalCapabilityResult> addLocalCapability(GoogleApiClient googleApiClient, final String string2) {
        return googleApiClient.zza(new zzi<CapabilityApi.AddLocalCapabilityResult>(googleApiClient){

            @Override
            protected void zza(zzbp zzbp2) throws RemoteException {
                zzbp2.zzr(this, string2);
            }

            protected CapabilityApi.AddLocalCapabilityResult zzbq(Status status) {
                return new zza(status);
            }

            @Override
            protected /* synthetic */ Result zzc(Status status) {
                return this.zzbq(status);
            }
        });
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public PendingResult<CapabilityApi.GetAllCapabilitiesResult> getAllCapabilities(GoogleApiClient googleApiClient, final int n2) {
        boolean bl2;
        boolean bl3 = bl2 = true;
        if (n2 != 0) {
            bl3 = n2 == 1 ? bl2 : false;
        }
        zzx.zzac(bl3);
        return googleApiClient.zza(new zzi<CapabilityApi.GetAllCapabilitiesResult>(googleApiClient){

            @Override
            protected void zza(zzbp zzbp2) throws RemoteException {
                zzbp2.zzb(this, n2);
            }

            protected CapabilityApi.GetAllCapabilitiesResult zzbp(Status status) {
                return new zzd(status, null);
            }

            @Override
            protected /* synthetic */ Result zzc(Status status) {
                return this.zzbp(status);
            }
        });
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public PendingResult<CapabilityApi.GetCapabilityResult> getCapability(GoogleApiClient googleApiClient, final String string2, final int n2) {
        boolean bl2;
        boolean bl3 = bl2 = true;
        if (n2 != 0) {
            bl3 = n2 == 1 ? bl2 : false;
        }
        zzx.zzac(bl3);
        return googleApiClient.zza(new zzi<CapabilityApi.GetCapabilityResult>(googleApiClient){

            @Override
            protected void zza(zzbp zzbp2) throws RemoteException {
                zzbp2.zzg(this, string2, n2);
            }

            protected CapabilityApi.GetCapabilityResult zzbo(Status status) {
                return new zze(status, null);
            }

            @Override
            protected /* synthetic */ Result zzc(Status status) {
                return this.zzbo(status);
            }
        });
    }

    @Override
    public PendingResult<Status> removeCapabilityListener(GoogleApiClient googleApiClient, CapabilityApi.CapabilityListener capabilityListener, String string2) {
        return googleApiClient.zza(new zzf(googleApiClient, new zzb(capabilityListener, string2)));
    }

    @Override
    public PendingResult<Status> removeListener(GoogleApiClient googleApiClient, CapabilityApi.CapabilityListener capabilityListener) {
        return googleApiClient.zza(new zzf(googleApiClient, capabilityListener));
    }

    @Override
    public PendingResult<CapabilityApi.RemoveLocalCapabilityResult> removeLocalCapability(GoogleApiClient googleApiClient, final String string2) {
        return googleApiClient.zza(new zzi<CapabilityApi.RemoveLocalCapabilityResult>(googleApiClient){

            @Override
            protected void zza(zzbp zzbp2) throws RemoteException {
                zzbp2.zzs(this, string2);
            }

            protected CapabilityApi.RemoveLocalCapabilityResult zzbr(Status status) {
                return new zza(status);
            }

            @Override
            protected /* synthetic */ Result zzc(Status status) {
                return this.zzbr(status);
            }
        });
    }

    public static class zza
    implements CapabilityApi.AddLocalCapabilityResult,
    CapabilityApi.RemoveLocalCapabilityResult {
        private final Status zzUX;

        public zza(Status status) {
            this.zzUX = status;
        }

        @Override
        public Status getStatus() {
            return this.zzUX;
        }
    }

    private static class zzb
    implements CapabilityApi.CapabilityListener {
        final CapabilityApi.CapabilityListener zzbrQ;
        final String zzbrR;

        zzb(CapabilityApi.CapabilityListener capabilityListener, String string2) {
            this.zzbrQ = capabilityListener;
            this.zzbrR = string2;
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        public boolean equals(Object object) {
            boolean bl2 = false;
            if (this == object) {
                return true;
            }
            boolean bl3 = bl2;
            if (object == null) return bl3;
            bl3 = bl2;
            if (this.getClass() != object.getClass()) return bl3;
            object = (zzb)object;
            bl3 = bl2;
            if (!this.zzbrQ.equals(((zzb)object).zzbrQ)) return bl3;
            return this.zzbrR.equals(((zzb)object).zzbrR);
        }

        public int hashCode() {
            return this.zzbrQ.hashCode() * 31 + this.zzbrR.hashCode();
        }

        @Override
        public void onCapabilityChanged(CapabilityInfo capabilityInfo) {
            this.zzbrQ.onCapabilityChanged(capabilityInfo);
        }
    }

    public static class zzc
    implements CapabilityInfo {
        private final String mName;
        private final Set<Node> zzbrS;

        public zzc(CapabilityInfo capabilityInfo) {
            this(capabilityInfo.getName(), capabilityInfo.getNodes());
        }

        public zzc(String string2, Set<Node> set) {
            this.mName = string2;
            this.zzbrS = set;
        }

        @Override
        public String getName() {
            return this.mName;
        }

        @Override
        public Set<Node> getNodes() {
            return this.zzbrS;
        }
    }

    public static class zzd
    implements CapabilityApi.GetAllCapabilitiesResult {
        private final Status zzUX;
        private final Map<String, CapabilityInfo> zzbrT;

        public zzd(Status status, Map<String, CapabilityInfo> map) {
            this.zzUX = status;
            this.zzbrT = map;
        }

        @Override
        public Map<String, CapabilityInfo> getAllCapabilities() {
            return this.zzbrT;
        }

        @Override
        public Status getStatus() {
            return this.zzUX;
        }
    }

    public static class zze
    implements CapabilityApi.GetCapabilityResult {
        private final Status zzUX;
        private final CapabilityInfo zzbrU;

        public zze(Status status, CapabilityInfo capabilityInfo) {
            this.zzUX = status;
            this.zzbrU = capabilityInfo;
        }

        @Override
        public CapabilityInfo getCapability() {
            return this.zzbrU;
        }

        @Override
        public Status getStatus() {
            return this.zzUX;
        }
    }

    private static final class zzf
    extends zzi<Status> {
        private CapabilityApi.CapabilityListener zzbrQ;

        private zzf(GoogleApiClient googleApiClient, CapabilityApi.CapabilityListener capabilityListener) {
            super(googleApiClient);
            this.zzbrQ = capabilityListener;
        }

        @Override
        protected void zza(zzbp zzbp2) throws RemoteException {
            zzbp2.zza((zza.zzb<Status>)this, this.zzbrQ);
            this.zzbrQ = null;
        }

        public Status zzb(Status status) {
            this.zzbrQ = null;
            return status;
        }

        @Override
        public /* synthetic */ Result zzc(Status status) {
            return this.zzb(status);
        }
    }
}

