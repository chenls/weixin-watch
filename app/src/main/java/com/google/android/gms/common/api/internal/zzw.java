/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.app.PendingIntent
 *  android.content.Context
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnCancelListener
 *  android.content.Intent
 *  android.content.IntentSender$SendIntentException
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.Looper
 *  android.os.Parcelable
 *  android.util.Log
 *  android.util.SparseArray
 */
package com.google.android.gms.common.api.internal;

import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.util.SparseArray;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.internal.zzn;
import com.google.android.gms.common.internal.zzx;
import com.google.android.gms.common.zzc;
import com.google.android.gms.common.zze;
import java.io.FileDescriptor;
import java.io.PrintWriter;

public class zzw
extends Fragment
implements DialogInterface.OnCancelListener {
    private boolean mStarted;
    private int zzaiA = -1;
    private ConnectionResult zzaiB;
    private final Handler zzaiC = new Handler(Looper.getMainLooper());
    protected zzn zzaiD;
    private final SparseArray<zza> zzaiE = new SparseArray();
    private boolean zzaiz;

    static /* synthetic */ int zza(zzw zzw2, int n2) {
        zzw2.zzaiA = n2;
        return n2;
    }

    static /* synthetic */ ConnectionResult zza(zzw zzw2, ConnectionResult connectionResult) {
        zzw2.zzaiB = connectionResult;
        return connectionResult;
    }

    @Nullable
    public static zzw zza(FragmentActivity object) {
        block5: {
            block4: {
                zzw zzw2;
                zzx.zzcD("Must be called from main thread of process");
                object = ((FragmentActivity)object).getSupportFragmentManager();
                try {
                    zzw2 = (zzw)((FragmentManager)object).findFragmentByTag("GmsSupportLifecycleFrag");
                    if (zzw2 == null) break block4;
                    object = zzw2;
                }
                catch (ClassCastException classCastException) {
                    throw new IllegalStateException("Fragment with tag GmsSupportLifecycleFrag is not a SupportLifecycleFragment", classCastException);
                }
                if (!zzw2.isRemoving()) break block5;
            }
            object = null;
        }
        return object;
    }

    private void zza(int n2, ConnectionResult connectionResult) {
        Log.w((String)"GmsSupportLifecycleFrag", (String)"Unresolved error while connecting client. Stopping auto-manage.");
        GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener = (zza)this.zzaiE.get(n2);
        if (onConnectionFailedListener != null) {
            this.zzbD(n2);
            onConnectionFailedListener = ((zza)onConnectionFailedListener).zzaiH;
            if (onConnectionFailedListener != null) {
                onConnectionFailedListener.onConnectionFailed(connectionResult);
            }
        }
        this.zzpP();
    }

    static /* synthetic */ boolean zza(zzw zzw2, boolean bl2) {
        zzw2.zzaiz = bl2;
        return bl2;
    }

    public static zzw zzb(FragmentActivity object) {
        zzw zzw2 = zzw.zza((FragmentActivity)object);
        FragmentManager fragmentManager = ((FragmentActivity)object).getSupportFragmentManager();
        object = zzw2;
        if (zzw2 == null) {
            zzw2 = zzw.zzpO();
            object = zzw2;
            if (zzw2 == null) {
                Log.w((String)"GmsSupportLifecycleFrag", (String)"Unable to find connection error message resources (Did you include play-services-base and the proper proguard rules?); error dialogs may be unavailable.");
                object = new zzw();
            }
            fragmentManager.beginTransaction().add((Fragment)object, "GmsSupportLifecycleFrag").commitAllowingStateLoss();
            fragmentManager.executePendingTransactions();
        }
        return object;
    }

    private static String zzi(ConnectionResult connectionResult) {
        return connectionResult.getErrorMessage() + " (" + connectionResult.getErrorCode() + ": " + zze.getErrorString(connectionResult.getErrorCode()) + ')';
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Nullable
    private static zzw zzpO() {
        try {
            var0 = Class.forName("com.google.android.gms.common.api.internal.SupportLifecycleFragmentImpl");
        }
        catch (ClassNotFoundException var0_1) {}
        ** GOTO lbl-1000
        catch (LinkageError var0_7) {
            ** GOTO lbl-1000
        }
        catch (SecurityException var0_8) {}
lbl-1000:
        // 3 sources

        {
            if (Log.isLoggable((String)"GmsSupportLifecycleFrag", (int)3) == false) return null;
            Log.d((String)"GmsSupportLifecycleFrag", (String)"Unable to find SupportLifecycleFragmentImpl class", var0);
            return null;
        }
        if (var0 == null) return null;
        try {
            return (zzw)var0.newInstance();
        }
        catch (InstantiationException var0_2) {}
        ** GOTO lbl-1000
        catch (RuntimeException var0_4) {
            ** GOTO lbl-1000
        }
        catch (IllegalAccessException var0_5) {
            ** GOTO lbl-1000
        }
        catch (ExceptionInInitializerError var0_6) {}
lbl-1000:
        // 4 sources

        {
            if (Log.isLoggable((String)"GmsSupportLifecycleFrag", (int)3) == false) return null;
            Log.d((String)"GmsSupportLifecycleFrag", (String)"Unable to instantiate SupportLifecycleFragmentImpl class", (Throwable)var0_3);
        }
        return null;
    }

    @Override
    public void dump(String string2, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] stringArray) {
        super.dump(string2, fileDescriptor, printWriter, stringArray);
        for (int i2 = 0; i2 < this.zzaiE.size(); ++i2) {
            ((zza)this.zzaiE.valueAt(i2)).dump(string2, fileDescriptor, printWriter, stringArray);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void onActivityResult(int n2, int n3, Intent intent) {
        block5: {
            int n4 = 1;
            switch (n2) {
                case 2: {
                    if (this.zzpQ().isGooglePlayServicesAvailable((Context)this.getActivity()) != 0) break;
                    n2 = n4;
                    break block5;
                }
                case 1: {
                    n2 = n4;
                    if (n3 == -1) break block5;
                    if (n3 != 0) break;
                    this.zzaiB = new ConnectionResult(13, null);
                }
            }
            n2 = 0;
        }
        if (n2 != 0) {
            this.zzpP();
            return;
        }
        this.zza(this.zzaiA, this.zzaiB);
    }

    public void onCancel(DialogInterface dialogInterface) {
        this.zza(this.zzaiA, new ConnectionResult(13, null));
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (bundle != null) {
            this.zzaiz = bundle.getBoolean("resolving_error", false);
            this.zzaiA = bundle.getInt("failed_client_id", -1);
            if (this.zzaiA >= 0) {
                this.zzaiB = new ConnectionResult(bundle.getInt("failed_status"), (PendingIntent)bundle.getParcelable("failed_resolution"));
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putBoolean("resolving_error", this.zzaiz);
        if (this.zzaiA >= 0) {
            bundle.putInt("failed_client_id", this.zzaiA);
            bundle.putInt("failed_status", this.zzaiB.getErrorCode());
            bundle.putParcelable("failed_resolution", (Parcelable)this.zzaiB.getResolution());
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        this.mStarted = true;
        if (!this.zzaiz) {
            for (int i2 = 0; i2 < this.zzaiE.size(); ++i2) {
                ((zza)this.zzaiE.valueAt((int)i2)).zzaiG.connect();
            }
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        this.mStarted = false;
        for (int i2 = 0; i2 < this.zzaiE.size(); ++i2) {
            ((zza)this.zzaiE.valueAt((int)i2)).zzaiG.disconnect();
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void zza(int n2, GoogleApiClient googleApiClient, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        zzx.zzb(googleApiClient, (Object)"GoogleApiClient instance cannot be null");
        boolean bl2 = this.zzaiE.indexOfKey(n2) < 0;
        zzx.zza(bl2, (Object)("Already managing a GoogleApiClient with id " + n2));
        onConnectionFailedListener = new zza(n2, googleApiClient, onConnectionFailedListener);
        this.zzaiE.put(n2, (Object)onConnectionFailedListener);
        if (this.mStarted && !this.zzaiz) {
            googleApiClient.connect();
        }
    }

    protected void zzb(int n2, ConnectionResult connectionResult) {
        Log.w((String)"GmsSupportLifecycleFrag", (String)("Failed to connect due to user resolvable error " + zzw.zzi(connectionResult)));
        this.zza(n2, connectionResult);
    }

    public void zzbD(int n2) {
        zza zza2 = (zza)this.zzaiE.get(n2);
        this.zzaiE.remove(n2);
        if (zza2 != null) {
            zza2.zzpR();
        }
    }

    protected void zzc(int n2, ConnectionResult connectionResult) {
        Log.w((String)"GmsSupportLifecycleFrag", (String)"Unable to connect, GooglePlayServices is updating.");
        this.zza(n2, connectionResult);
    }

    protected void zzpP() {
        this.zzaiz = false;
        this.zzaiA = -1;
        this.zzaiB = null;
        if (this.zzaiD != null) {
            this.zzaiD.unregister();
            this.zzaiD = null;
        }
        for (int i2 = 0; i2 < this.zzaiE.size(); ++i2) {
            ((zza)this.zzaiE.valueAt((int)i2)).zzaiG.connect();
        }
    }

    protected zzc zzpQ() {
        return zzc.zzoK();
    }

    private class zza
    implements GoogleApiClient.OnConnectionFailedListener {
        public final int zzaiF;
        public final GoogleApiClient zzaiG;
        public final GoogleApiClient.OnConnectionFailedListener zzaiH;

        public zza(int n2, GoogleApiClient googleApiClient, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
            this.zzaiF = n2;
            this.zzaiG = googleApiClient;
            this.zzaiH = onConnectionFailedListener;
            googleApiClient.registerConnectionFailedListener(this);
        }

        public void dump(String string2, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] stringArray) {
            printWriter.append(string2).append("GoogleApiClient #").print(this.zzaiF);
            printWriter.println(":");
            this.zzaiG.dump(string2 + "  ", fileDescriptor, printWriter, stringArray);
        }

        @Override
        public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
            zzw.this.zzaiC.post((Runnable)new zzb(this.zzaiF, connectionResult));
        }

        public void zzpR() {
            this.zzaiG.unregisterConnectionFailedListener(this);
            this.zzaiG.disconnect();
        }
    }

    private class zzb
    implements Runnable {
        private final int zzaiJ;
        private final ConnectionResult zzaiK;

        public zzb(int n2, ConnectionResult connectionResult) {
            this.zzaiJ = n2;
            this.zzaiK = connectionResult;
        }

        @Override
        @MainThread
        public void run() {
            if (!zzw.this.mStarted || zzw.this.zzaiz) {
                return;
            }
            zzw.zza(zzw.this, true);
            zzw.zza(zzw.this, this.zzaiJ);
            zzw.zza(zzw.this, this.zzaiK);
            if (this.zzaiK.hasResolution()) {
                try {
                    int n2 = zzw.this.getActivity().getSupportFragmentManager().getFragments().indexOf(zzw.this);
                    this.zzaiK.startResolutionForResult(zzw.this.getActivity(), (n2 + 1 << 16) + 1);
                    return;
                }
                catch (IntentSender.SendIntentException sendIntentException) {
                    zzw.this.zzpP();
                    return;
                }
            }
            if (zzw.this.zzpQ().isUserResolvableError(this.zzaiK.getErrorCode())) {
                zzw.this.zzb(this.zzaiJ, this.zzaiK);
                return;
            }
            if (this.zzaiK.getErrorCode() == 18) {
                zzw.this.zzc(this.zzaiJ, this.zzaiK);
                return;
            }
            zzw.this.zza(this.zzaiJ, this.zzaiK);
        }
    }
}

