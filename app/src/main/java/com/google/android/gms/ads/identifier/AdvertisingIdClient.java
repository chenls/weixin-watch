/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.os.RemoteException
 *  android.util.Log
 */
package com.google.android.gms.ads.identifier;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.internal.zzx;
import com.google.android.gms.common.stats.zzb;
import com.google.android.gms.common.zzc;
import com.google.android.gms.internal.zzat;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class AdvertisingIdClient {
    private final Context mContext;
    com.google.android.gms.common.zza zzoS;
    zzat zzoT;
    boolean zzoU;
    Object zzoV = new Object();
    zza zzoW;
    final long zzoX;

    public AdvertisingIdClient(Context context) {
        this(context, 30000L);
    }

    public AdvertisingIdClient(Context context, long l2) {
        zzx.zzz(context);
        this.mContext = context;
        this.zzoU = false;
        this.zzoX = l2;
    }

    public static Info getAdvertisingIdInfo(Context object) throws IOException, IllegalStateException, GooglePlayServicesNotAvailableException, GooglePlayServicesRepairableException {
        object = new AdvertisingIdClient((Context)object, -1L);
        try {
            ((AdvertisingIdClient)object).zzb(false);
            Info info = ((AdvertisingIdClient)object).getInfo();
            return info;
        }
        finally {
            ((AdvertisingIdClient)object).finish();
        }
    }

    public static void setShouldSkipGmsCoreVersionCheck(boolean bl2) {
    }

    static zzat zza(Context object, com.google.android.gms.common.zza zza2) throws IOException {
        try {
            object = zzat.zza.zzb(zza2.zzoJ());
            return object;
        }
        catch (InterruptedException interruptedException) {
            throw new IOException("Interrupted exception");
        }
        catch (Throwable throwable) {
            throw new IOException(throwable);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void zzaJ() {
        Object object = this.zzoV;
        synchronized (object) {
            if (this.zzoW != null) {
                this.zzoW.cancel();
                try {
                    this.zzoW.join();
                }
                catch (InterruptedException interruptedException) {}
            }
            if (this.zzoX > 0L) {
                this.zzoW = new zza(this, this.zzoX);
            }
            return;
        }
    }

    /*
     * Recovered potentially malformed switches.  Disable with '--allowmalformedswitch false'
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static com.google.android.gms.common.zza zzp(Context context) throws IOException, GooglePlayServicesNotAvailableException, GooglePlayServicesRepairableException {
        context.getPackageManager().getPackageInfo("com.android.vending", 0);
        {
            catch (PackageManager.NameNotFoundException nameNotFoundException) {
                throw new GooglePlayServicesNotAvailableException(9);
            }
        }
        switch (zzc.zzoK().isGooglePlayServicesAvailable(context)) {
            default: {
                throw new IOException("Google Play services not available");
            }
            case 0: 
            case 2: 
        }
        com.google.android.gms.common.zza zza2 = new com.google.android.gms.common.zza();
        Intent intent = new Intent("com.google.android.gms.ads.identifier.service.START");
        intent.setPackage("com.google.android.gms");
        try {
            boolean bl2 = zzb.zzrP().zza(context, intent, zza2, 1);
            if (!bl2) throw new IOException("Connection failure");
            return zza2;
        }
        catch (Throwable throwable) {
            throw new IOException(throwable);
        }
    }

    protected void finalize() throws Throwable {
        this.finish();
        super.finalize();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void finish() {
        zzx.zzcE("Calling this from your main thread can lead to deadlock");
        synchronized (this) {
            if (this.mContext == null || this.zzoS == null) {
                return;
            }
            try {
                if (this.zzoU) {
                    zzb.zzrP().zza(this.mContext, this.zzoS);
                }
            }
            catch (IllegalArgumentException illegalArgumentException) {
                Log.i((String)"AdvertisingIdClient", (String)"AdvertisingIdClient unbindService failed.", (Throwable)illegalArgumentException);
            }
            this.zzoU = false;
            this.zzoT = null;
            this.zzoS = null;
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Info getInfo() throws IOException {
        zzx.zzcE("Calling this from your main thread can lead to deadlock");
        synchronized (this) {
            Object object;
            block14: {
                if (!this.zzoU) {
                    object = this.zzoV;
                    synchronized (object) {
                        if (this.zzoW == null || !this.zzoW.zzaK()) {
                            throw new IOException("AdvertisingIdClient is not connected.");
                        }
                    }
                    try {
                        this.zzb(false);
                        if (this.zzoU) break block14;
                        throw new IOException("AdvertisingIdClient cannot reconnect.");
                    }
                    catch (Exception exception) {
                        throw new IOException("AdvertisingIdClient cannot reconnect.", exception);
                    }
                }
            }
            zzx.zzz(this.zzoS);
            zzx.zzz(this.zzoT);
            try {
                object = new Info(this.zzoT.getId(), this.zzoT.zzc(true));
                // MONITOREXIT @DISABLED, blocks:[6, 8] lbl21 : MonitorExitStatement: MONITOREXIT : this
                this.zzaJ();
                return object;
            }
            catch (RemoteException remoteException) {
                Log.i((String)"AdvertisingIdClient", (String)"GMS remote exception ", (Throwable)remoteException);
                throw new IOException("Remote exception");
            }
        }
    }

    public void start() throws IOException, IllegalStateException, GooglePlayServicesNotAvailableException, GooglePlayServicesRepairableException {
        this.zzb(true);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected void zzb(boolean bl2) throws IOException, IllegalStateException, GooglePlayServicesNotAvailableException, GooglePlayServicesRepairableException {
        zzx.zzcE("Calling this from your main thread can lead to deadlock");
        synchronized (this) {
            if (this.zzoU) {
                this.finish();
            }
            this.zzoS = AdvertisingIdClient.zzp(this.mContext);
            this.zzoT = AdvertisingIdClient.zza(this.mContext, this.zzoS);
            this.zzoU = true;
            if (bl2) {
                this.zzaJ();
            }
            return;
        }
    }

    public static final class Info {
        private final String zzpc;
        private final boolean zzpd;

        public Info(String string2, boolean bl2) {
            this.zzpc = string2;
            this.zzpd = bl2;
        }

        public String getId() {
            return this.zzpc;
        }

        public boolean isLimitAdTrackingEnabled() {
            return this.zzpd;
        }

        public String toString() {
            return "{" + this.zzpc + "}" + this.zzpd;
        }
    }

    static class zza
    extends Thread {
        private WeakReference<AdvertisingIdClient> zzoY;
        private long zzoZ;
        CountDownLatch zzpa;
        boolean zzpb;

        public zza(AdvertisingIdClient advertisingIdClient, long l2) {
            this.zzoY = new WeakReference<AdvertisingIdClient>(advertisingIdClient);
            this.zzoZ = l2;
            this.zzpa = new CountDownLatch(1);
            this.zzpb = false;
            this.start();
        }

        private void disconnect() {
            AdvertisingIdClient advertisingIdClient = (AdvertisingIdClient)this.zzoY.get();
            if (advertisingIdClient != null) {
                advertisingIdClient.finish();
                this.zzpb = true;
            }
        }

        public void cancel() {
            this.zzpa.countDown();
        }

        @Override
        public void run() {
            try {
                if (!this.zzpa.await(this.zzoZ, TimeUnit.MILLISECONDS)) {
                    this.disconnect();
                }
                return;
            }
            catch (InterruptedException interruptedException) {
                this.disconnect();
                return;
            }
        }

        public boolean zzaK() {
            return this.zzpb;
        }
    }
}

