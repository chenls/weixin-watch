/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.accounts.Account
 *  android.content.Context
 *  android.os.Bundle
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.Looper
 *  android.os.Parcelable
 *  android.os.RemoteException
 *  android.util.Log
 */
package com.google.android.gms.signin.internal;

import android.accounts.Account;
import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.Parcelable;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.internal.zzq;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.internal.ResolveAccountRequest;
import com.google.android.gms.common.internal.zzf;
import com.google.android.gms.common.internal.zzj;
import com.google.android.gms.common.internal.zzp;
import com.google.android.gms.common.internal.zzx;
import com.google.android.gms.internal.zzrn;
import com.google.android.gms.internal.zzro;
import com.google.android.gms.signin.internal.SignInRequest;
import com.google.android.gms.signin.internal.SignInResponse;
import com.google.android.gms.signin.internal.zzd;
import com.google.android.gms.signin.internal.zze;

public class zzh
extends zzj<zze>
implements zzrn {
    private final zzf zzahz;
    private Integer zzale;
    private final Bundle zzbgU;
    private final boolean zzbhi;

    public zzh(Context context, Looper looper, boolean bl2, zzf zzf2, Bundle bundle, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        super(context, looper, 44, zzf2, connectionCallbacks, onConnectionFailedListener);
        this.zzbhi = bl2;
        this.zzahz = zzf2;
        this.zzbgU = bundle;
        this.zzale = zzf2.zzqz();
    }

    public zzh(Context context, Looper looper, boolean bl2, zzf zzf2, zzro zzro2, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        this(context, looper, bl2, zzf2, zzh.zza(zzf2), connectionCallbacks, onConnectionFailedListener);
    }

    private ResolveAccountRequest zzFN() {
        Account account = this.zzahz.zzqq();
        GoogleSignInAccount googleSignInAccount = null;
        if ("<<default account>>".equals(account.name)) {
            googleSignInAccount = zzq.zzaf(this.getContext()).zzno();
        }
        return new ResolveAccountRequest(account, this.zzale, googleSignInAccount);
    }

    public static Bundle zza(zzf zzf2) {
        zzro zzro2 = zzf2.zzqy();
        Integer n2 = zzf2.zzqz();
        Bundle bundle = new Bundle();
        bundle.putParcelable("com.google.android.gms.signin.internal.clientRequestedAccount", (Parcelable)zzf2.getAccount());
        if (n2 != null) {
            bundle.putInt("com.google.android.gms.common.internal.ClientSettings.sessionId", n2.intValue());
        }
        if (zzro2 != null) {
            bundle.putBoolean("com.google.android.gms.signin.internal.offlineAccessRequested", zzro2.zzFH());
            bundle.putBoolean("com.google.android.gms.signin.internal.idTokenRequested", zzro2.zzmO());
            bundle.putString("com.google.android.gms.signin.internal.serverClientId", zzro2.zzmR());
            bundle.putBoolean("com.google.android.gms.signin.internal.usePromptModeForAuthCode", true);
            bundle.putBoolean("com.google.android.gms.signin.internal.forceCodeForRefreshToken", zzro2.zzmQ());
            bundle.putString("com.google.android.gms.signin.internal.hostedDomain", zzro2.zzmS());
            bundle.putBoolean("com.google.android.gms.signin.internal.waitForAccessTokenRefresh", zzro2.zzFI());
        }
        return bundle;
    }

    @Override
    public void connect() {
        this.zza(new zzj.zzf());
    }

    @Override
    public void zzFG() {
        try {
            ((zze)this.zzqJ()).zzka(this.zzale);
            return;
        }
        catch (RemoteException remoteException) {
            Log.w((String)"SignInClientImpl", (String)"Remote service probably died when clearAccountFromSessionStore is called");
            return;
        }
    }

    @Override
    protected /* synthetic */ IInterface zzW(IBinder iBinder) {
        return this.zzec(iBinder);
    }

    @Override
    public void zza(zzp zzp2, boolean bl2) {
        try {
            ((zze)this.zzqJ()).zza(zzp2, this.zzale, bl2);
            return;
        }
        catch (RemoteException remoteException) {
            Log.w((String)"SignInClientImpl", (String)"Remote service probably died when saveDefaultAccount is called");
            return;
        }
    }

    @Override
    public void zza(zzd zzd2) {
        zzx.zzb(zzd2, (Object)"Expecting a valid ISignInCallbacks");
        try {
            ResolveAccountRequest resolveAccountRequest = this.zzFN();
            ((zze)this.zzqJ()).zza(new SignInRequest(resolveAccountRequest), zzd2);
            return;
        }
        catch (RemoteException remoteException) {
            Log.w((String)"SignInClientImpl", (String)"Remote service probably died when signIn is called");
            try {
                zzd2.zzb(new SignInResponse(8));
                return;
            }
            catch (RemoteException remoteException2) {
                Log.wtf((String)"SignInClientImpl", (String)"ISignInCallbacks#onSignInComplete should be executed from the same process, unexpected RemoteException.", (Throwable)remoteException);
                return;
            }
        }
    }

    protected zze zzec(IBinder iBinder) {
        return zze.zza.zzeb(iBinder);
    }

    @Override
    protected String zzgu() {
        return "com.google.android.gms.signin.service.START";
    }

    @Override
    protected String zzgv() {
        return "com.google.android.gms.signin.internal.ISignInService";
    }

    @Override
    public boolean zzmE() {
        return this.zzbhi;
    }

    @Override
    protected Bundle zzml() {
        String string2 = this.zzahz.zzqv();
        if (!this.getContext().getPackageName().equals(string2)) {
            this.zzbgU.putString("com.google.android.gms.signin.internal.realClientPackageName", this.zzahz.zzqv());
        }
        return this.zzbgU;
    }
}

