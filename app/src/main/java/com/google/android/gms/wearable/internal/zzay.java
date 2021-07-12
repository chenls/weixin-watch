/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.os.IBinder
 *  android.os.RemoteException
 *  android.util.Log
 */
package com.google.android.gms.wearable.internal;

import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.zza;
import com.google.android.gms.wearable.internal.AddListenerRequest;
import com.google.android.gms.wearable.internal.RemoveListenerRequest;
import com.google.android.gms.wearable.internal.zzav;
import com.google.android.gms.wearable.internal.zzax;
import com.google.android.gms.wearable.internal.zzbo;
import com.google.android.gms.wearable.internal.zzbp;
import com.google.android.gms.wearable.internal.zzbq;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

final class zzay<T> {
    private final Map<T, zzbq<T>> zzaxd = new HashMap<T, zzbq<T>>();

    zzay() {
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void zza(zzbp zzbp2, zza.zzb<Status> zzb2, T t2) throws RemoteException {
        Map<T, zzbq<T>> map = this.zzaxd;
        synchronized (map) {
            zzbq<T> zzbq2 = this.zzaxd.remove(t2);
            if (zzbq2 == null) {
                zzb2.zzs(new Status(4002));
                return;
            }
            zzbq2.clear();
            ((zzax)zzbp2.zzqJ()).zza(new zzb<T>(this.zzaxd, t2, zzb2), new RemoveListenerRequest(zzbq2));
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void zza(zzbp zzbp2, zza.zzb<Status> zzb2, T t2, zzbq<T> zzbq2) throws RemoteException {
        Map<T, zzbq<T>> map = this.zzaxd;
        synchronized (map) {
            if (this.zzaxd.get(t2) != null) {
                zzb2.zzs(new Status(4001));
                return;
            }
            this.zzaxd.put(t2, zzbq2);
            try {
                ((zzax)zzbp2.zzqJ()).zza(new zza<T>(this.zzaxd, t2, zzb2), new AddListenerRequest(zzbq2));
                return;
            }
            catch (RemoteException remoteException) {
                this.zzaxd.remove(t2);
                throw remoteException;
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void zzev(IBinder object) {
        Map<T, zzbq<T>> map = this.zzaxd;
        synchronized (map) {
            object = zzax.zza.zzeu((IBinder)object);
            zzbo.zzo zzo2 = new zzbo.zzo();
            Iterator<Map.Entry<T, zzbq<T>>> iterator = this.zzaxd.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<T, zzbq<T>> entry = iterator.next();
                zzbq<T> zzbq2 = entry.getValue();
                try {
                    object.zza((zzav)zzo2, new AddListenerRequest(zzbq2));
                    if (!Log.isLoggable((String)"WearableClient", (int)2)) continue;
                    Log.d((String)"WearableClient", (String)("onPostInitHandler: added: " + entry.getKey() + "/" + zzbq2));
                }
                catch (RemoteException remoteException) {
                    Log.d((String)"WearableClient", (String)("onPostInitHandler: Didn't add: " + entry.getKey() + "/" + zzbq2));
                    continue;
                }
                break;
            }
            return;
        }
    }

    private static class zza<T>
    extends zzbo.zzb<Status> {
        private WeakReference<Map<T, zzbq<T>>> zzbsM;
        private WeakReference<T> zzbsN;

        zza(Map<T, zzbq<T>> map, T t2, zza.zzb<Status> zzb2) {
            super(zzb2);
            this.zzbsM = new WeakReference<Map<Map<T, zzbq<T>>, zzbq<Map<T, zzbq<T>>>>>(map);
            this.zzbsN = new WeakReference<T>(t2);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void zza(Status status) {
            Map map = (Map)this.zzbsM.get();
            Object object = this.zzbsN.get();
            if (!status.getStatus().isSuccess() && map != null && object != null) {
                synchronized (map) {
                    object = (zzbq)map.remove(object);
                    if (object != null) {
                        ((zzbq)object).clear();
                    }
                }
            }
            this.zzX(status);
        }
    }

    private static class zzb<T>
    extends zzbo.zzb<Status> {
        private WeakReference<Map<T, zzbq<T>>> zzbsM;
        private WeakReference<T> zzbsN;

        zzb(Map<T, zzbq<T>> map, T t2, zza.zzb<Status> zzb2) {
            super(zzb2);
            this.zzbsM = new WeakReference<Map<Map<T, zzbq<T>>, zzbq<Map<T, zzbq<T>>>>>(map);
            this.zzbsN = new WeakReference<T>(t2);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void zza(Status status) {
            Map map = (Map)this.zzbsM.get();
            Object object = this.zzbsN.get();
            if (status.getStatus().getStatusCode() == 4002 && map != null && object != null) {
                synchronized (map) {
                    object = (zzbq)map.remove(object);
                    if (object != null) {
                        ((zzbq)object).clear();
                    }
                }
            }
            this.zzX(status);
        }
    }
}

