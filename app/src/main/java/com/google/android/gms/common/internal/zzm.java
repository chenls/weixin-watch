/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.content.ServiceConnection
 *  android.os.Handler
 *  android.os.Handler$Callback
 *  android.os.IBinder
 *  android.os.Message
 */
package com.google.android.gms.common.internal;

import android.annotation.TargetApi;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import com.google.android.gms.common.internal.zzl;
import com.google.android.gms.common.internal.zzw;
import com.google.android.gms.common.internal.zzx;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

final class zzm
extends zzl
implements Handler.Callback {
    private final Handler mHandler;
    private final HashMap<zza, zzb> zzalZ = new HashMap();
    private final com.google.android.gms.common.stats.zzb zzama;
    private final long zzamb;
    private final Context zzsa;

    zzm(Context context) {
        this.zzsa = context.getApplicationContext();
        this.mHandler = new Handler(context.getMainLooper(), (Handler.Callback)this);
        this.zzama = com.google.android.gms.common.stats.zzb.zzrP();
        this.zzamb = 5000L;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private boolean zza(zza object, ServiceConnection serviceConnection, String string2) {
        zzx.zzb(serviceConnection, (Object)"ServiceConnection must not be null");
        HashMap<zza, zzb> hashMap = this.zzalZ;
        synchronized (hashMap) {
            zzb zzb2 = this.zzalZ.get(object);
            if (zzb2 == null) {
                zzb2 = new zzb((zza)object);
                zzb2.zza(serviceConnection, string2);
                zzb2.zzcH(string2);
                this.zzalZ.put((zza)object, zzb2);
                object = zzb2;
                return ((zzb)object).isBound();
            } else {
                this.mHandler.removeMessages(0, (Object)zzb2);
                if (zzb2.zza(serviceConnection)) {
                    throw new IllegalStateException("Trying to bind a GmsServiceConnection that was already connected before.  config=" + object);
                }
                zzb2.zza(serviceConnection, string2);
                switch (zzb2.getState()) {
                    case 1: {
                        serviceConnection.onServiceConnected(zzb2.getComponentName(), zzb2.getBinder());
                        object = zzb2;
                        return ((zzb)object).isBound();
                    }
                    case 2: {
                        zzb2.zzcH(string2);
                        object = zzb2;
                        return ((zzb)object).isBound();
                    }
                    default: {
                        object = zzb2;
                    }
                }
            }
            return ((zzb)object).isBound();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void zzb(zza zza2, ServiceConnection serviceConnection, String string2) {
        zzx.zzb(serviceConnection, (Object)"ServiceConnection must not be null");
        HashMap<zza, zzb> hashMap = this.zzalZ;
        synchronized (hashMap) {
            zzb zzb2 = this.zzalZ.get(zza2);
            if (zzb2 == null) {
                throw new IllegalStateException("Nonexistent connection status for service config: " + zza2);
            }
            if (!zzb2.zza(serviceConnection)) {
                throw new IllegalStateException("Trying to unbind a GmsServiceConnection  that was not bound before.  config=" + zza2);
            }
            zzb2.zzb(serviceConnection, string2);
            if (zzb2.zzqT()) {
                zza2 = this.mHandler.obtainMessage(0, (Object)zzb2);
                this.mHandler.sendMessageDelayed((Message)zza2, this.zzamb);
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean handleMessage(Message object) {
        switch (object.what) {
            default: {
                return false;
            }
            case 0: 
        }
        zzb zzb2 = (zzb)object.obj;
        object = this.zzalZ;
        synchronized (object) {
            if (zzb2.zzqT()) {
                if (zzb2.isBound()) {
                    zzb2.zzcI("GmsClientSupervisor");
                }
                this.zzalZ.remove(zzb2.zzamg);
            }
            return true;
        }
    }

    @Override
    public boolean zza(ComponentName componentName, ServiceConnection serviceConnection, String string2) {
        return this.zza(new zza(componentName), serviceConnection, string2);
    }

    @Override
    public boolean zza(String string2, ServiceConnection serviceConnection, String string3) {
        return this.zza(new zza(string2), serviceConnection, string3);
    }

    @Override
    public void zzb(ComponentName componentName, ServiceConnection serviceConnection, String string2) {
        this.zzb(new zza(componentName), serviceConnection, string2);
    }

    @Override
    public void zzb(String string2, ServiceConnection serviceConnection, String string3) {
        this.zzb(new zza(string2), serviceConnection, string3);
    }

    private static final class zza {
        private final String zzSU;
        private final ComponentName zzamc;

        public zza(ComponentName componentName) {
            this.zzSU = null;
            this.zzamc = zzx.zzz(componentName);
        }

        public zza(String string2) {
            this.zzSU = zzx.zzcM(string2);
            this.zzamc = null;
        }

        /*
         * Enabled aggressive block sorting
         */
        public boolean equals(Object object) {
            block5: {
                block4: {
                    if (this == object) break block4;
                    if (!(object instanceof zza)) {
                        return false;
                    }
                    object = (zza)object;
                    if (!zzw.equal(this.zzSU, ((zza)object).zzSU) || !zzw.equal(this.zzamc, ((zza)object).zzamc)) break block5;
                }
                return true;
            }
            return false;
        }

        public int hashCode() {
            return zzw.hashCode(this.zzSU, this.zzamc);
        }

        public String toString() {
            if (this.zzSU == null) {
                return this.zzamc.flattenToString();
            }
            return this.zzSU;
        }

        public Intent zzqS() {
            if (this.zzSU != null) {
                return new Intent(this.zzSU).setPackage("com.google.android.gms");
            }
            return new Intent().setComponent(this.zzamc);
        }
    }

    private final class zzb {
        private int mState;
        private IBinder zzakD;
        private ComponentName zzamc;
        private final zza zzamd;
        private final Set<ServiceConnection> zzame;
        private boolean zzamf;
        private final com.google.android.gms.common.internal.zzm$zza zzamg;

        public zzb(com.google.android.gms.common.internal.zzm$zza zza2) {
            this.zzamg = zza2;
            this.zzamd = new zza();
            this.zzame = new HashSet<ServiceConnection>();
            this.mState = 2;
        }

        static /* synthetic */ int zza(zzb zzb2, int n2) {
            zzb2.mState = n2;
            return n2;
        }

        static /* synthetic */ ComponentName zza(zzb zzb2, ComponentName componentName) {
            zzb2.zzamc = componentName;
            return componentName;
        }

        static /* synthetic */ IBinder zza(zzb zzb2, IBinder iBinder) {
            zzb2.zzakD = iBinder;
            return iBinder;
        }

        public IBinder getBinder() {
            return this.zzakD;
        }

        public ComponentName getComponentName() {
            return this.zzamc;
        }

        public int getState() {
            return this.mState;
        }

        public boolean isBound() {
            return this.zzamf;
        }

        public void zza(ServiceConnection serviceConnection, String string2) {
            zzm.this.zzama.zza(zzm.this.zzsa, serviceConnection, string2, this.zzamg.zzqS());
            this.zzame.add(serviceConnection);
        }

        public boolean zza(ServiceConnection serviceConnection) {
            return this.zzame.contains(serviceConnection);
        }

        public void zzb(ServiceConnection serviceConnection, String string2) {
            zzm.this.zzama.zzb(zzm.this.zzsa, serviceConnection);
            this.zzame.remove(serviceConnection);
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @TargetApi(value=14)
        public void zzcH(String string2) {
            this.mState = 3;
            this.zzamf = zzm.this.zzama.zza(zzm.this.zzsa, string2, this.zzamg.zzqS(), this.zzamd, 129);
            if (this.zzamf) return;
            this.mState = 2;
            try {
                zzm.this.zzama.zza(zzm.this.zzsa, this.zzamd);
                return;
            }
            catch (IllegalArgumentException illegalArgumentException) {
                return;
            }
        }

        public void zzcI(String string2) {
            zzm.this.zzama.zza(zzm.this.zzsa, this.zzamd);
            this.zzamf = false;
            this.mState = 2;
        }

        public boolean zzqT() {
            return this.zzame.isEmpty();
        }

        public class zza
        implements ServiceConnection {
            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                HashMap hashMap = zzm.this.zzalZ;
                synchronized (hashMap) {
                    zzb.zza(zzb.this, iBinder);
                    zzb.zza(zzb.this, componentName);
                    Iterator iterator = zzb.this.zzame.iterator();
                    while (true) {
                        if (!iterator.hasNext()) {
                            zzb.zza(zzb.this, 1);
                            return;
                        }
                        ((ServiceConnection)iterator.next()).onServiceConnected(componentName, iBinder);
                    }
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            public void onServiceDisconnected(ComponentName componentName) {
                HashMap hashMap = zzm.this.zzalZ;
                synchronized (hashMap) {
                    zzb.zza(zzb.this, null);
                    zzb.zza(zzb.this, componentName);
                    Iterator iterator = zzb.this.zzame.iterator();
                    while (true) {
                        if (!iterator.hasNext()) {
                            zzb.zza(zzb.this, 2);
                            return;
                        }
                        ((ServiceConnection)iterator.next()).onServiceDisconnected(componentName);
                    }
                }
            }
        }
    }
}

