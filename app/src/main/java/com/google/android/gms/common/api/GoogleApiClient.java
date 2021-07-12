/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.accounts.Account
 *  android.content.Context
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.Looper
 *  android.view.View
 */
package com.google.android.gms.common.api;

import android.accounts.Account;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.util.ArrayMap;
import android.view.View;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.zza;
import com.google.android.gms.common.api.internal.zzj;
import com.google.android.gms.common.api.internal.zzq;
import com.google.android.gms.common.api.internal.zzu;
import com.google.android.gms.common.api.internal.zzw;
import com.google.android.gms.common.api.internal.zzx;
import com.google.android.gms.common.internal.zzad;
import com.google.android.gms.common.internal.zzf;
import com.google.android.gms.common.zzc;
import com.google.android.gms.internal.zzrl;
import com.google.android.gms.internal.zzrn;
import com.google.android.gms.internal.zzro;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public abstract class GoogleApiClient {
    public static final int SIGN_IN_MODE_OPTIONAL = 2;
    public static final int SIGN_IN_MODE_REQUIRED = 1;
    private static final Set<GoogleApiClient> zzagg = Collections.newSetFromMap(new WeakHashMap());

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void dumpAll(String string2, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] stringArray) {
        Set<GoogleApiClient> set = zzagg;
        synchronized (set) {
            String string3 = string2 + "  ";
            Iterator<GoogleApiClient> iterator = zzagg.iterator();
            int n2 = 0;
            while (iterator.hasNext()) {
                GoogleApiClient googleApiClient = iterator.next();
                printWriter.append(string2).append("GoogleApiClient#").println(n2);
                googleApiClient.dump(string3, fileDescriptor, printWriter, stringArray);
                ++n2;
            }
            return;
        }
    }

    public static Set<GoogleApiClient> zzoV() {
        return zzagg;
    }

    public abstract ConnectionResult blockingConnect();

    public abstract ConnectionResult blockingConnect(long var1, @NonNull TimeUnit var3);

    public abstract PendingResult<Status> clearDefaultAccountAndReconnect();

    public abstract void connect();

    public void connect(int n2) {
        throw new UnsupportedOperationException();
    }

    public abstract void disconnect();

    public abstract void dump(String var1, FileDescriptor var2, PrintWriter var3, String[] var4);

    @NonNull
    public abstract ConnectionResult getConnectionResult(@NonNull Api<?> var1);

    public Context getContext() {
        throw new UnsupportedOperationException();
    }

    public Looper getLooper() {
        throw new UnsupportedOperationException();
    }

    public abstract boolean hasConnectedApi(@NonNull Api<?> var1);

    public abstract boolean isConnected();

    public abstract boolean isConnecting();

    public abstract boolean isConnectionCallbacksRegistered(@NonNull ConnectionCallbacks var1);

    public abstract boolean isConnectionFailedListenerRegistered(@NonNull OnConnectionFailedListener var1);

    public abstract void reconnect();

    public abstract void registerConnectionCallbacks(@NonNull ConnectionCallbacks var1);

    public abstract void registerConnectionFailedListener(@NonNull OnConnectionFailedListener var1);

    public abstract void stopAutoManage(@NonNull FragmentActivity var1);

    public abstract void unregisterConnectionCallbacks(@NonNull ConnectionCallbacks var1);

    public abstract void unregisterConnectionFailedListener(@NonNull OnConnectionFailedListener var1);

    @NonNull
    public <C extends Api.zzb> C zza(@NonNull Api.zzc<C> zzc2) {
        throw new UnsupportedOperationException();
    }

    public <A extends Api.zzb, R extends Result, T extends zza.zza<R, A>> T zza(@NonNull T t2) {
        throw new UnsupportedOperationException();
    }

    public void zza(zzx zzx2) {
        throw new UnsupportedOperationException();
    }

    public boolean zza(@NonNull Api<?> api) {
        throw new UnsupportedOperationException();
    }

    public boolean zza(zzu zzu2) {
        throw new UnsupportedOperationException();
    }

    public <A extends Api.zzb, T extends zza.zza<? extends Result, A>> T zzb(@NonNull T t2) {
        throw new UnsupportedOperationException();
    }

    public void zzb(zzx zzx2) {
        throw new UnsupportedOperationException();
    }

    public void zzoW() {
        throw new UnsupportedOperationException();
    }

    public <L> zzq<L> zzr(@NonNull L l2) {
        throw new UnsupportedOperationException();
    }

    public static final class Builder {
        private final Context mContext;
        private Account zzTI;
        private String zzUW;
        private final Set<Scope> zzagh = new HashSet<Scope>();
        private final Set<Scope> zzagi = new HashSet<Scope>();
        private int zzagj;
        private View zzagk;
        private String zzagl;
        private final Map<Api<?>, zzf.zza> zzagm = new ArrayMap();
        private final Map<Api<?>, Api.ApiOptions> zzagn = new ArrayMap();
        private FragmentActivity zzago;
        private int zzagp = -1;
        private OnConnectionFailedListener zzagq;
        private Looper zzagr;
        private zzc zzags = zzc.zzoK();
        private Api.zza<? extends zzrn, zzro> zzagt = zzrl.zzUJ;
        private final ArrayList<ConnectionCallbacks> zzagu = new ArrayList();
        private final ArrayList<OnConnectionFailedListener> zzagv = new ArrayList();

        public Builder(@NonNull Context context) {
            this.mContext = context;
            this.zzagr = context.getMainLooper();
            this.zzUW = context.getPackageName();
            this.zzagl = context.getClass().getName();
        }

        public Builder(@NonNull Context context, @NonNull ConnectionCallbacks connectionCallbacks, @NonNull OnConnectionFailedListener onConnectionFailedListener) {
            this(context);
            com.google.android.gms.common.internal.zzx.zzb(connectionCallbacks, (Object)"Must provide a connected listener");
            this.zzagu.add(connectionCallbacks);
            com.google.android.gms.common.internal.zzx.zzb(onConnectionFailedListener, (Object)"Must provide a connection failed listener");
            this.zzagv.add(onConnectionFailedListener);
        }

        private static <C extends Api.zzb, O> C zza(Api.zza<C, O> zza2, Object object, Context context, Looper looper, zzf zzf2, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener) {
            return zza2.zza(context, looper, zzf2, object, connectionCallbacks, onConnectionFailedListener);
        }

        private static <C extends Api.zzd, O> zzad zza(Api.zze<C, O> zze2, Object object, Context context, Looper looper, zzf zzf2, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener) {
            return new zzad(context, looper, zze2.zzoU(), connectionCallbacks, onConnectionFailedListener, zzf2, (Api.zzd)zze2.zzq(object));
        }

        /*
         * Enabled aggressive block sorting
         */
        private <O extends Api.ApiOptions> void zza(Api<O> api, O object, int n2, Scope ... scopeArray) {
            boolean bl2 = true;
            int n3 = 0;
            if (n2 != 1) {
                if (n2 != 2) {
                    throw new IllegalArgumentException("Invalid resolution mode: '" + n2 + "', use a constant from GoogleApiClient.ResolutionMode");
                }
                bl2 = false;
            }
            object = new HashSet<Scope>(api.zzoP().zzo(object));
            int n4 = scopeArray.length;
            n2 = n3;
            while (true) {
                if (n2 >= n4) {
                    this.zzagm.put(api, new zzf.zza((Set<Scope>)object, bl2));
                    return;
                }
                object.add(scopeArray[n2]);
                ++n2;
            }
        }

        private void zza(zzw zzw2, GoogleApiClient googleApiClient) {
            zzw2.zza(this.zzagp, googleApiClient, this.zzagq);
        }

        private void zze(final GoogleApiClient googleApiClient) {
            zzw zzw2 = zzw.zza(this.zzago);
            if (zzw2 == null) {
                new Handler(this.mContext.getMainLooper()).post(new Runnable(){

                    @Override
                    public void run() {
                        if (Builder.this.zzago.isFinishing() || Builder.this.zzago.getSupportFragmentManager().isDestroyed()) {
                            return;
                        }
                        Builder.this.zza(zzw.zzb(Builder.this.zzago), googleApiClient);
                    }
                });
                return;
            }
            this.zza(zzw2, googleApiClient);
        }

        /*
         * Enabled aggressive block sorting
         */
        private GoogleApiClient zzoZ() {
            int n2;
            zzf zzf2 = this.zzoY();
            Api api = null;
            Map<Api<?>, zzf.zza> map = zzf2.zzqu();
            ArrayMap arrayMap = new ArrayMap();
            ArrayMap arrayMap2 = new ArrayMap();
            ArrayList<com.google.android.gms.common.api.internal.zzc> arrayList = new ArrayList<com.google.android.gms.common.api.internal.zzc>();
            Iterator<Api<?>> iterator = this.zzagn.keySet().iterator();
            Api api2 = null;
            while (iterator.hasNext()) {
                Object object;
                Api api3 = iterator.next();
                Api api4 = this.zzagn.get(api3);
                n2 = 0;
                if (map.get(api3) != null) {
                    n2 = map.get(api3).zzalf ? 1 : 2;
                }
                arrayMap.put(api3, n2);
                com.google.android.gms.common.api.internal.zzc zzc2 = new com.google.android.gms.common.api.internal.zzc(api3, n2);
                arrayList.add(zzc2);
                if (api3.zzoS()) {
                    object = api3.zzoQ();
                    if (object.getPriority() == 1) {
                        api2 = api3;
                    }
                    api4 = Builder.zza(object, (Object)api4, this.mContext, this.zzagr, zzf2, (ConnectionCallbacks)zzc2, (OnConnectionFailedListener)zzc2);
                } else {
                    object = api3.zzoP();
                    if (((Api.zza)object).getPriority() == 1) {
                        api2 = api3;
                    }
                    api4 = Builder.zza(object, (Object)api4, this.mContext, this.zzagr, zzf2, (ConnectionCallbacks)zzc2, (OnConnectionFailedListener)zzc2);
                }
                arrayMap2.put(api3.zzoR(), (Api.zzb)((Object)api4));
                if (api4.zznb()) {
                    api4 = api3;
                    if (api != null) {
                        throw new IllegalStateException(api3.getName() + " cannot be used with " + api.getName());
                    }
                } else {
                    api4 = api;
                }
                api = api4;
            }
            if (api != null) {
                if (api2 != null) {
                    throw new IllegalStateException(api.getName() + " cannot be used with " + api2.getName());
                }
                boolean bl2 = this.zzTI == null;
                com.google.android.gms.common.internal.zzx.zza(bl2, "Must not set an account in GoogleApiClient.Builder when using %s. Set account in GoogleSignInOptions.Builder instead", api.getName());
                com.google.android.gms.common.internal.zzx.zza(this.zzagh.equals(this.zzagi), "Must not set scopes in GoogleApiClient.Builder when using %s. Set account in GoogleSignInOptions.Builder instead.", api.getName());
            }
            n2 = zzj.zza(arrayMap2.values(), true);
            return new zzj(this.mContext, new ReentrantLock(), this.zzagr, zzf2, this.zzags, this.zzagt, arrayMap, this.zzagu, this.zzagv, arrayMap2, this.zzagp, n2, arrayList);
        }

        public Builder addApi(@NonNull Api<? extends Api.ApiOptions.NotRequiredOptions> object) {
            com.google.android.gms.common.internal.zzx.zzb(object, (Object)"Api must not be null");
            this.zzagn.put((Api<?>)object, (Api.ApiOptions)null);
            object = ((Api)object).zzoP().zzo(null);
            this.zzagi.addAll((Collection<Scope>)object);
            this.zzagh.addAll((Collection<Scope>)object);
            return this;
        }

        public <O extends Api.ApiOptions.HasOptions> Builder addApi(@NonNull Api<O> object, @NonNull O o2) {
            com.google.android.gms.common.internal.zzx.zzb(object, (Object)"Api must not be null");
            com.google.android.gms.common.internal.zzx.zzb(o2, (Object)"Null options are not permitted for this Api");
            this.zzagn.put((Api<?>)object, o2);
            object = ((Api)object).zzoP().zzo(o2);
            this.zzagi.addAll((Collection<Scope>)object);
            this.zzagh.addAll((Collection<Scope>)object);
            return this;
        }

        public <O extends Api.ApiOptions.HasOptions> Builder addApiIfAvailable(@NonNull Api<O> api, @NonNull O o2, Scope ... scopeArray) {
            com.google.android.gms.common.internal.zzx.zzb(api, (Object)"Api must not be null");
            com.google.android.gms.common.internal.zzx.zzb(o2, (Object)"Null options are not permitted for this Api");
            this.zzagn.put(api, o2);
            this.zza(api, o2, 1, scopeArray);
            return this;
        }

        public Builder addApiIfAvailable(@NonNull Api<? extends Api.ApiOptions.NotRequiredOptions> api, Scope ... scopeArray) {
            com.google.android.gms.common.internal.zzx.zzb(api, (Object)"Api must not be null");
            this.zzagn.put(api, null);
            this.zza(api, null, 1, scopeArray);
            return this;
        }

        public Builder addConnectionCallbacks(@NonNull ConnectionCallbacks connectionCallbacks) {
            com.google.android.gms.common.internal.zzx.zzb(connectionCallbacks, (Object)"Listener must not be null");
            this.zzagu.add(connectionCallbacks);
            return this;
        }

        public Builder addOnConnectionFailedListener(@NonNull OnConnectionFailedListener onConnectionFailedListener) {
            com.google.android.gms.common.internal.zzx.zzb(onConnectionFailedListener, (Object)"Listener must not be null");
            this.zzagv.add(onConnectionFailedListener);
            return this;
        }

        public Builder addScope(@NonNull Scope scope) {
            com.google.android.gms.common.internal.zzx.zzb(scope, (Object)"Scope must not be null");
            this.zzagh.add(scope);
            return this;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Converted monitor instructions to comments
         * Lifted jumps to return sites
         */
        public GoogleApiClient build() {
            boolean bl2 = !this.zzagn.isEmpty();
            com.google.android.gms.common.internal.zzx.zzb(bl2, (Object)"must call addApi() to add at least one API");
            GoogleApiClient googleApiClient = this.zzoZ();
            Set set = zzagg;
            // MONITORENTER : set
            zzagg.add(googleApiClient);
            // MONITOREXIT : set
            if (this.zzagp < 0) return googleApiClient;
            this.zze(googleApiClient);
            return googleApiClient;
        }

        /*
         * Enabled aggressive block sorting
         */
        public Builder enableAutoManage(@NonNull FragmentActivity fragmentActivity, int n2, @Nullable OnConnectionFailedListener onConnectionFailedListener) {
            boolean bl2 = n2 >= 0;
            com.google.android.gms.common.internal.zzx.zzb(bl2, (Object)"clientId must be non-negative");
            this.zzagp = n2;
            this.zzago = com.google.android.gms.common.internal.zzx.zzb(fragmentActivity, (Object)"Null activity is not permitted.");
            this.zzagq = onConnectionFailedListener;
            return this;
        }

        public Builder enableAutoManage(@NonNull FragmentActivity fragmentActivity, @Nullable OnConnectionFailedListener onConnectionFailedListener) {
            return this.enableAutoManage(fragmentActivity, 0, onConnectionFailedListener);
        }

        /*
         * Enabled aggressive block sorting
         */
        public Builder setAccountName(String string2) {
            string2 = string2 == null ? null : new Account(string2, "com.google");
            this.zzTI = string2;
            return this;
        }

        public Builder setGravityForPopups(int n2) {
            this.zzagj = n2;
            return this;
        }

        public Builder setHandler(@NonNull Handler handler) {
            com.google.android.gms.common.internal.zzx.zzb(handler, (Object)"Handler must not be null");
            this.zzagr = handler.getLooper();
            return this;
        }

        public Builder setViewForPopups(@NonNull View view) {
            com.google.android.gms.common.internal.zzx.zzb(view, (Object)"View must not be null");
            this.zzagk = view;
            return this;
        }

        public Builder useDefaultAccount() {
            return this.setAccountName("<<default account>>");
        }

        public zzf zzoY() {
            zzro zzro2 = zzro.zzbgV;
            if (this.zzagn.containsKey(zzrl.API)) {
                zzro2 = (zzro)this.zzagn.get(zzrl.API);
            }
            return new zzf(this.zzTI, this.zzagh, this.zzagm, this.zzagj, this.zzagk, this.zzUW, this.zzagl, zzro2);
        }
    }

    public static interface ConnectionCallbacks {
        public static final int CAUSE_NETWORK_LOST = 2;
        public static final int CAUSE_SERVICE_DISCONNECTED = 1;

        public void onConnected(@Nullable Bundle var1);

        public void onConnectionSuspended(int var1);
    }

    public static interface OnConnectionFailedListener {
        public void onConnectionFailed(@NonNull ConnectionResult var1);
    }

    public static interface zza {
        public void zza(@NonNull ConnectionResult var1);
    }
}

