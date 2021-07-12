/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.accounts.Account
 *  android.app.PendingIntent
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.content.ServiceConnection
 *  android.os.Bundle
 *  android.os.DeadObjectException
 *  android.os.Handler
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.Looper
 *  android.os.Message
 *  android.os.RemoteException
 *  android.util.Log
 */
package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.Handler;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.support.annotation.BinderThread;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.GetServiceRequest;
import com.google.android.gms.common.internal.zzk;
import com.google.android.gms.common.internal.zzl;
import com.google.android.gms.common.internal.zzp;
import com.google.android.gms.common.internal.zzr;
import com.google.android.gms.common.internal.zzs;
import com.google.android.gms.common.internal.zzx;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class zzj<T extends IInterface>
implements Api.zzb,
zzk.zza {
    public static final String[] zzalJ = new String[]{"service_esmobile", "service_googleme"};
    private final Context mContext;
    final Handler mHandler;
    private final Account zzTI;
    private final Set<Scope> zzXf;
    private final Looper zzagr;
    private final com.google.android.gms.common.zzc zzags;
    private final com.google.android.gms.common.internal.zzf zzahz;
    private GoogleApiClient.zza zzalA;
    private T zzalB;
    private final ArrayList<zzc<?>> zzalC;
    private zze zzalD;
    private int zzalE = 1;
    private final GoogleApiClient.ConnectionCallbacks zzalF;
    private final GoogleApiClient.OnConnectionFailedListener zzalG;
    private final int zzalH;
    protected AtomicInteger zzalI;
    private int zzals;
    private long zzalt;
    private long zzalu;
    private int zzalv;
    private long zzalw;
    private final zzl zzalx;
    private final Object zzaly;
    private zzs zzalz;
    private final Object zzpV = new Object();

    protected zzj(Context context, Looper looper, int n2, com.google.android.gms.common.internal.zzf zzf2, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        this(context, looper, zzl.zzau(context), com.google.android.gms.common.zzc.zzoK(), n2, zzf2, zzx.zzz(connectionCallbacks), zzx.zzz(onConnectionFailedListener));
    }

    protected zzj(Context context, Looper looper, zzl zzl2, com.google.android.gms.common.zzc zzc2, int n2, com.google.android.gms.common.internal.zzf zzf2, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        this.zzaly = new Object();
        this.zzalA = new zzf();
        this.zzalC = new ArrayList();
        this.zzalI = new AtomicInteger(0);
        this.mContext = zzx.zzb(context, (Object)"Context must not be null");
        this.zzagr = zzx.zzb(looper, (Object)"Looper must not be null");
        this.zzalx = zzx.zzb(zzl2, (Object)"Supervisor must not be null");
        this.zzags = zzx.zzb(zzc2, (Object)"API availability must not be null");
        this.mHandler = new zzb(looper);
        this.zzalH = n2;
        this.zzahz = zzx.zzz(zzf2);
        this.zzTI = zzf2.getAccount();
        this.zzXf = this.zza(zzf2.zzqt());
        this.zzalF = connectionCallbacks;
        this.zzalG = onConnectionFailedListener;
    }

    static /* synthetic */ zzs zza(zzj zzj2, zzs zzs2) {
        zzj2.zzalz = zzs2;
        return zzs2;
    }

    private Set<Scope> zza(Set<Scope> set) {
        Set<Scope> set2 = this.zzb(set);
        if (set2 == null) {
            return set2;
        }
        Iterator<Scope> iterator = set2.iterator();
        while (iterator.hasNext()) {
            if (set.contains(iterator.next())) continue;
            throw new IllegalStateException("Expanding scopes is not permitted, use implied scopes instead");
        }
        return set2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private boolean zza(int n2, int n3, T t2) {
        Object object = this.zzpV;
        synchronized (object) {
            if (this.zzalE != n2) {
                return false;
            }
            this.zzb(n3, t2);
            return true;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void zzb(int n2, T t2) {
        boolean bl2;
        boolean bl3 = true;
        boolean bl4 = n2 == 3;
        if (bl4 != (bl2 = t2 != null)) {
            bl3 = false;
        }
        zzx.zzac(bl3);
        Object object = this.zzpV;
        synchronized (object) {
            this.zzalE = n2;
            this.zzalB = t2;
            this.zzc(n2, t2);
            switch (n2) {
                case 2: {
                    this.zzqE();
                    break;
                }
                case 3: {
                    this.zza(t2);
                    break;
                }
                case 1: {
                    this.zzqF();
                    break;
                }
            }
            return;
        }
    }

    private void zzqE() {
        if (this.zzalD != null) {
            Log.e((String)"GmsClient", (String)("Calling connect() while still connected, missing disconnect() for " + this.zzgu()));
            this.zzalx.zzb(this.zzgu(), (ServiceConnection)this.zzalD, this.zzqD());
            this.zzalI.incrementAndGet();
        }
        this.zzalD = new zze(this.zzalI.get());
        if (!this.zzalx.zza(this.zzgu(), (ServiceConnection)this.zzalD, this.zzqD())) {
            Log.e((String)"GmsClient", (String)("unable to connect to service: " + this.zzgu()));
            this.zzm(8, this.zzalI.get());
        }
    }

    private void zzqF() {
        if (this.zzalD != null) {
            this.zzalx.zzb(this.zzgu(), (ServiceConnection)this.zzalD, this.zzqD());
            this.zzalD = null;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void disconnect() {
        this.zzalI.incrementAndGet();
        Object object = this.zzalC;
        synchronized (object) {
            int n2 = this.zzalC.size();
            int n3 = 0;
            while (true) {
                if (n3 >= n2) {
                    this.zzalC.clear();
                    // MONITOREXIT @DISABLED, blocks:[4, 6, 7] lbl10 : MonitorExitStatement: MONITOREXIT : var3_1
                    object = this.zzaly;
                    synchronized (object) {
                        this.zzalz = null;
                    }
                    this.zzb(1, null);
                    return;
                }
                this.zzalC.get(n3).zzqO();
                ++n3;
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void dump(String string2, FileDescriptor object, PrintWriter printWriter, String[] object2) {
        int n2;
        object = this.zzpV;
        synchronized (object) {
            n2 = this.zzalE;
            object2 = this.zzalB;
        }
        printWriter.append(string2).append("mConnectState=");
        switch (n2) {
            default: {
                printWriter.print("UNKNOWN");
                break;
            }
            case 2: {
                printWriter.print("CONNECTING");
                break;
            }
            case 3: {
                printWriter.print("CONNECTED");
                break;
            }
            case 4: {
                printWriter.print("DISCONNECTING");
                break;
            }
            case 1: {
                printWriter.print("DISCONNECTED");
            }
        }
        printWriter.append(" mService=");
        if (object2 == null) {
            printWriter.println("null");
        } else {
            printWriter.append(this.zzgv()).append("@").println(Integer.toHexString(System.identityHashCode(object2.asBinder())));
        }
        object = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.US);
        if (this.zzalu > 0L) {
            printWriter.append(string2).append("lastConnectedTime=").println(this.zzalu + " " + ((DateFormat)object).format(new Date(this.zzalu)));
        }
        if (this.zzalt > 0L) {
            printWriter.append(string2).append("lastSuspendedCause=");
            switch (this.zzals) {
                default: {
                    printWriter.append(String.valueOf(this.zzals));
                    break;
                }
                case 1: {
                    printWriter.append("CAUSE_SERVICE_DISCONNECTED");
                    break;
                }
                case 2: {
                    printWriter.append("CAUSE_NETWORK_LOST");
                }
            }
            printWriter.append(" lastSuspendedTime=").println(this.zzalt + " " + ((DateFormat)object).format(new Date(this.zzalt)));
        }
        if (this.zzalw > 0L) {
            printWriter.append(string2).append("lastFailedStatus=").append(CommonStatusCodes.getStatusCodeString(this.zzalv));
            printWriter.append(" lastFailedTime=").println(this.zzalw + " " + ((DateFormat)object).format(new Date(this.zzalw)));
        }
    }

    public final Context getContext() {
        return this.mContext;
    }

    public final Looper getLooper() {
        return this.zzagr;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public boolean isConnected() {
        Object object = this.zzpV;
        synchronized (object) {
            if (this.zzalE != 3) return false;
            return true;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean isConnecting() {
        Object object = this.zzpV;
        synchronized (object) {
            if (this.zzalE != 2) return false;
            return true;
        }
    }

    @CallSuper
    protected void onConnectionFailed(ConnectionResult connectionResult) {
        this.zzalv = connectionResult.getErrorCode();
        this.zzalw = System.currentTimeMillis();
    }

    @CallSuper
    protected void onConnectionSuspended(int n2) {
        this.zzals = n2;
        this.zzalt = System.currentTimeMillis();
    }

    @Nullable
    protected abstract T zzW(IBinder var1);

    @BinderThread
    protected void zza(int n2, IBinder iBinder, Bundle bundle, int n3) {
        this.mHandler.sendMessage(this.mHandler.obtainMessage(1, n3, -1, (Object)new zzg(n2, iBinder, bundle)));
    }

    @CallSuper
    protected void zza(@NonNull T t2) {
        this.zzalu = System.currentTimeMillis();
    }

    @Override
    public void zza(@NonNull GoogleApiClient.zza zza2) {
        this.zzalA = zzx.zzb(zza2, (Object)"Connection progress callbacks cannot be null.");
        this.zzb(2, null);
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    @WorkerThread
    public void zza(zzp var1_1, Set<Scope> var2_4) {
        try {
            var3_5 = this.zzml();
            var3_5 = new GetServiceRequest(this.zzalH).zzcG(this.mContext.getPackageName()).zzj((Bundle)var3_5);
            if (var2_4 != null) {
                var3_5.zzd(var2_4);
            }
            if (this.zzmE()) {
                var3_5.zzc(this.zzqq()).zzb((zzp)var1_1);
            }
            ** GOTO lbl-1000
        }
        catch (DeadObjectException var1_2) {
            Log.w((String)"GmsClient", (String)"service died");
            this.zzbS(1);
            return;
        }
        catch (RemoteException var1_3) {
            Log.w((String)"GmsClient", (String)"Remote exception occurred", (Throwable)var1_3);
            return;
        }
lbl21:
        // 3 sources

        while (true) {
            var1_1 = this.zzaly;
            synchronized (var1_1) {
                if (this.zzalz != null) {
                    this.zzalz.zza((zzr)new zzd(this, this.zzalI.get()), (GetServiceRequest)var3_5);
lbl26:
                    // 2 sources

                    return;
                }
                ** break block13
            }
            break;
        }
lbl-1000:
        // 1 sources

        {
            if (!this.zzqK()) ** GOTO lbl21
            var3_5.zzc(this.zzTI);
            ** continue;
        }
lbl-1000:
        // 1 sources

        {
            Log.w((String)"GmsClient", (String)"mServiceBroker is null, client disconnected");
            ** continue;
        }
    }

    @NonNull
    protected Set<Scope> zzb(@NonNull Set<Scope> set) {
        return set;
    }

    public void zzbS(int n2) {
        this.mHandler.sendMessage(this.mHandler.obtainMessage(4, this.zzalI.get(), n2));
    }

    void zzc(int n2, T t2) {
    }

    @NonNull
    protected abstract String zzgu();

    @NonNull
    protected abstract String zzgv();

    protected void zzm(int n2, int n3) {
        this.mHandler.sendMessage(this.mHandler.obtainMessage(5, n3, -1, (Object)new zzh(n2)));
    }

    @Override
    public boolean zzmE() {
        return false;
    }

    protected Bundle zzml() {
        return new Bundle();
    }

    @Override
    public boolean zznb() {
        return false;
    }

    @Override
    public Intent zznc() {
        throw new UnsupportedOperationException("Not a sign in API");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    @Nullable
    public IBinder zzoT() {
        Object object = this.zzaly;
        synchronized (object) {
            if (this.zzalz != null) return this.zzalz.asBinder();
            return null;
        }
    }

    @Override
    public Bundle zzoi() {
        return null;
    }

    @Nullable
    protected final String zzqD() {
        return this.zzahz.zzqw();
    }

    public void zzqG() {
        int n2 = this.zzags.isGooglePlayServicesAvailable(this.mContext);
        if (n2 != 0) {
            this.zzb(1, null);
            this.zzalA = new zzf();
            this.mHandler.sendMessage(this.mHandler.obtainMessage(3, this.zzalI.get(), n2));
            return;
        }
        this.zza(new zzf());
    }

    protected final com.google.android.gms.common.internal.zzf zzqH() {
        return this.zzahz;
    }

    protected final void zzqI() {
        if (!this.isConnected()) {
            throw new IllegalStateException("Not connected. Call connect() and wait for onConnected() to be called.");
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final T zzqJ() throws DeadObjectException {
        Object object = this.zzpV;
        synchronized (object) {
            if (this.zzalE == 4) {
                throw new DeadObjectException();
            }
            this.zzqI();
            boolean bl2 = this.zzalB != null;
            zzx.zza(bl2, (Object)"Client is connected but service is null");
            T t2 = this.zzalB;
            return t2;
        }
    }

    public boolean zzqK() {
        return false;
    }

    public final Account zzqq() {
        if (this.zzTI != null) {
            return this.zzTI;
        }
        return new Account("<<default account>>", "com.google");
    }

    private abstract class zza
    extends zzc<Boolean> {
        public final int statusCode;
        public final Bundle zzalK;

        @BinderThread
        protected zza(int n2, Bundle bundle) {
            super(true);
            this.statusCode = n2;
            this.zzalK = bundle;
        }

        /*
         * Enabled aggressive block sorting
         */
        protected void zzc(Boolean bl2) {
            Object var2_2 = null;
            if (bl2 == null) {
                zzj.this.zzb(1, null);
                return;
            }
            switch (this.statusCode) {
                default: {
                    zzj.this.zzb(1, null);
                    bl2 = var2_2;
                    if (this.zzalK != null) {
                        bl2 = (PendingIntent)this.zzalK.getParcelable("pendingIntent");
                    }
                    this.zzj(new ConnectionResult(this.statusCode, (PendingIntent)bl2));
                    return;
                }
                case 0: {
                    if (this.zzqL()) return;
                    zzj.this.zzb(1, null);
                    this.zzj(new ConnectionResult(8, null));
                    return;
                }
                case 10: 
            }
            zzj.this.zzb(1, null);
            throw new IllegalStateException("A fatal developer error has occurred. Check the logs for further information.");
        }

        protected abstract void zzj(ConnectionResult var1);

        protected abstract boolean zzqL();

        @Override
        protected void zzqM() {
        }

        @Override
        protected /* synthetic */ void zzw(Object object) {
            this.zzc((Boolean)object);
        }
    }

    final class zzb
    extends Handler {
        public zzb(Looper looper) {
            super(looper);
        }

        private void zza(Message object) {
            object = (zzc)((Message)object).obj;
            ((zzc)object).zzqM();
            ((zzc)object).unregister();
        }

        private boolean zzb(Message message) {
            return message.what == 2 || message.what == 1 || message.what == 5;
        }

        public void handleMessage(Message object) {
            if (zzj.this.zzalI.get() != object.arg1) {
                if (this.zzb((Message)object)) {
                    this.zza((Message)object);
                }
                return;
            }
            if (!(object.what != 1 && object.what != 5 || zzj.this.isConnecting())) {
                this.zza((Message)object);
                return;
            }
            if (object.what == 3) {
                object = new ConnectionResult(object.arg2, null);
                zzj.this.zzalA.zza((ConnectionResult)object);
                zzj.this.onConnectionFailed((ConnectionResult)object);
                return;
            }
            if (object.what == 4) {
                zzj.this.zzb(4, null);
                if (zzj.this.zzalF != null) {
                    zzj.this.zzalF.onConnectionSuspended(object.arg2);
                }
                zzj.this.onConnectionSuspended(object.arg2);
                zzj.this.zza(4, 1, null);
                return;
            }
            if (object.what == 2 && !zzj.this.isConnected()) {
                this.zza((Message)object);
                return;
            }
            if (this.zzb((Message)object)) {
                ((zzc)object.obj).zzqN();
                return;
            }
            Log.wtf((String)"GmsClient", (String)("Don't know how to handle message: " + object.what), (Throwable)new Exception());
        }
    }

    protected abstract class zzc<TListener> {
        private TListener mListener;
        private boolean zzalM;

        public zzc(TListener TListener) {
            this.mListener = TListener;
            this.zzalM = false;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void unregister() {
            this.zzqO();
            ArrayList arrayList = zzj.this.zzalC;
            synchronized (arrayList) {
                zzj.this.zzalC.remove(this);
                return;
            }
        }

        protected abstract void zzqM();

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Converted monitor instructions to comments
         * Lifted jumps to return sites
         */
        public void zzqN() {
            // MONITORENTER : this
            TListener TListener = this.mListener;
            if (this.zzalM) {
                Log.w((String)"GmsClient", (String)("Callback proxy " + this + " being reused. This is not safe."));
            }
            // MONITOREXIT : this
            if (TListener != null) {
                try {
                    this.zzw(TListener);
                }
                catch (RuntimeException runtimeException) {
                    this.zzqM();
                    throw runtimeException;
                }
            } else {
                this.zzqM();
            }
            // MONITORENTER : this
            this.zzalM = true;
            // MONITOREXIT : this
            this.unregister();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void zzqO() {
            synchronized (this) {
                this.mListener = null;
                return;
            }
        }

        protected abstract void zzw(TListener var1);
    }

    public static final class zzd
    extends zzr.zza {
        private zzj zzalN;
        private final int zzalO;

        public zzd(@NonNull zzj zzj2, int n2) {
            this.zzalN = zzj2;
            this.zzalO = n2;
        }

        private void zzqP() {
            this.zzalN = null;
        }

        @Override
        @BinderThread
        public void zza(int n2, @NonNull IBinder iBinder, @Nullable Bundle bundle) {
            zzx.zzb(this.zzalN, (Object)"onPostInitComplete can be called only once per call to getRemoteService");
            this.zzalN.zza(n2, iBinder, bundle, this.zzalO);
            this.zzqP();
        }

        @Override
        @BinderThread
        public void zzb(int n2, @Nullable Bundle bundle) {
            Log.wtf((String)"GmsClient", (String)"received deprecated onAccountValidationComplete callback, ignoring", (Throwable)new Exception());
        }
    }

    public final class zze
    implements ServiceConnection {
        private final int zzalO;

        public zze(int n2) {
            this.zzalO = n2;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void onServiceConnected(ComponentName object, IBinder iBinder) {
            zzx.zzb(iBinder, (Object)"Expecting a valid IBinder");
            object = zzj.this.zzaly;
            synchronized (object) {
                zzj.zza(zzj.this, zzs.zza.zzaS(iBinder));
            }
            zzj.this.zzm(0, this.zzalO);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void onServiceDisconnected(ComponentName object) {
            object = zzj.this.zzaly;
            synchronized (object) {
                zzj.zza(zzj.this, null);
            }
            zzj.this.mHandler.sendMessage(zzj.this.mHandler.obtainMessage(4, this.zzalO, 1));
        }
    }

    protected class zzf
    implements GoogleApiClient.zza {
        /*
         * Enabled aggressive block sorting
         */
        @Override
        public void zza(@NonNull ConnectionResult connectionResult) {
            if (connectionResult.isSuccess()) {
                zzj.this.zza(null, zzj.this.zzXf);
                return;
            } else {
                if (zzj.this.zzalG == null) return;
                zzj.this.zzalG.onConnectionFailed(connectionResult);
                return;
            }
        }
    }

    protected final class zzg
    extends zza {
        public final IBinder zzalP;

        @BinderThread
        public zzg(int n2, IBinder iBinder, Bundle bundle) {
            super(n2, bundle);
            this.zzalP = iBinder;
        }

        @Override
        protected void zzj(ConnectionResult connectionResult) {
            if (zzj.this.zzalG != null) {
                zzj.this.zzalG.onConnectionFailed(connectionResult);
            }
            zzj.this.onConnectionFailed(connectionResult);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        protected boolean zzqL() {
            block4: {
                String string2;
                try {
                    string2 = this.zzalP.getInterfaceDescriptor();
                    if (zzj.this.zzgv().equals(string2)) break block4;
                }
                catch (RemoteException remoteException) {
                    Log.w((String)"GmsClient", (String)"service probably died");
                    return false;
                }
                Log.e((String)"GmsClient", (String)("service descriptor mismatch: " + zzj.this.zzgv() + " vs. " + string2));
                return false;
            }
            Object t2 = zzj.this.zzW(this.zzalP);
            if (t2 == null || !zzj.this.zza(2, 3, t2)) return false;
            Bundle bundle = zzj.this.zzoi();
            if (zzj.this.zzalF == null) return true;
            zzj.this.zzalF.onConnected(bundle);
            return true;
        }
    }

    protected final class zzh
    extends zza {
        @BinderThread
        public zzh(int n2) {
            super(n2, null);
        }

        @Override
        protected void zzj(ConnectionResult connectionResult) {
            zzj.this.zzalA.zza(connectionResult);
            zzj.this.onConnectionFailed(connectionResult);
        }

        @Override
        protected boolean zzqL() {
            zzj.this.zzalA.zza(ConnectionResult.zzafB);
            return true;
        }
    }
}

