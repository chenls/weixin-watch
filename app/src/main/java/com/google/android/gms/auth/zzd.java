/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.accounts.Account
 *  android.accounts.AccountManager
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.content.ServiceConnection
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.os.IBinder
 *  android.os.RemoteException
 *  android.os.SystemClock
 *  android.text.TextUtils
 *  android.util.Log
 */
package com.google.android.gms.auth;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.SystemClock;
import android.support.annotation.RequiresPermission;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.auth.AccountChangeEvent;
import com.google.android.gms.auth.AccountChangeEventsRequest;
import com.google.android.gms.auth.AccountChangeEventsResponse;
import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GooglePlayServicesAvailabilityException;
import com.google.android.gms.auth.TokenData;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.internal.zzl;
import com.google.android.gms.common.internal.zzx;
import com.google.android.gms.common.zze;
import com.google.android.gms.internal.zzas;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public class zzd {
    public static final int CHANGE_TYPE_ACCOUNT_ADDED = 1;
    public static final int CHANGE_TYPE_ACCOUNT_REMOVED = 2;
    public static final int CHANGE_TYPE_ACCOUNT_RENAMED_FROM = 3;
    public static final int CHANGE_TYPE_ACCOUNT_RENAMED_TO = 4;
    public static final String GOOGLE_ACCOUNT_TYPE = "com.google";
    public static final String KEY_ANDROID_PACKAGE_NAME;
    public static final String KEY_CALLER_UID;
    public static final String KEY_REQUEST_ACTIONS = "request_visible_actions";
    @Deprecated
    public static final String KEY_REQUEST_VISIBLE_ACTIVITIES = "request_visible_actions";
    public static final String KEY_SUPPRESS_PROGRESS_SCREEN = "suppressProgressScreen";
    private static final ComponentName zzVe;
    private static final ComponentName zzVf;

    static {
        if (Build.VERSION.SDK_INT >= 11) {
            // empty if block
        }
        KEY_CALLER_UID = "callerUid";
        if (Build.VERSION.SDK_INT >= 14) {
            // empty if block
        }
        KEY_ANDROID_PACKAGE_NAME = "androidPackageName";
        zzVe = new ComponentName("com.google.android.gms", "com.google.android.gms.auth.GetToken");
        zzVf = new ComponentName("com.google.android.gms", "com.google.android.gms.recovery.RecoveryService");
    }

    zzd() {
    }

    public static void clearToken(Context context, String object) throws GooglePlayServicesAvailabilityException, GoogleAuthException, IOException {
        zzx.zzcE("Calling this from your main thread can lead to deadlock");
        zzd.zzad(context);
        Bundle bundle = new Bundle();
        String string2 = context.getApplicationInfo().packageName;
        bundle.putString("clientPackageName", string2);
        if (!bundle.containsKey(KEY_ANDROID_PACKAGE_NAME)) {
            bundle.putString(KEY_ANDROID_PACKAGE_NAME, string2);
        }
        object = new zza<Void>((String)object, bundle){
            final /* synthetic */ String zzVj;
            final /* synthetic */ Bundle zzVk;
            {
                this.zzVj = string2;
                this.zzVk = bundle;
            }

            @Override
            public /* synthetic */ Object zzan(IBinder iBinder) throws RemoteException, IOException, GoogleAuthException {
                return this.zzao(iBinder);
            }

            public Void zzao(IBinder iBinder) throws RemoteException, IOException, GoogleAuthException {
                iBinder = (Bundle)zzd.zzm(zzas.zza.zza(iBinder).zza(this.zzVj, this.zzVk));
                String string2 = iBinder.getString("Error");
                if (!iBinder.getBoolean("booleanResult")) {
                    throw new GoogleAuthException(string2);
                }
                return null;
            }
        };
        zzd.zza(context, zzVe, object);
    }

    public static List<AccountChangeEvent> getAccountChangeEvents(Context context, int n2, String object) throws GoogleAuthException, IOException {
        zzx.zzh((String)object, "accountName must be provided");
        zzx.zzcE("Calling this from your main thread can lead to deadlock");
        zzd.zzad(context);
        object = new zza<List<AccountChangeEvent>>((String)object, n2){
            final /* synthetic */ String zzVl;
            final /* synthetic */ int zzVm;
            {
                this.zzVl = string2;
                this.zzVm = n2;
            }

            @Override
            public /* synthetic */ Object zzan(IBinder iBinder) throws RemoteException, IOException, GoogleAuthException {
                return this.zzap(iBinder);
            }

            public List<AccountChangeEvent> zzap(IBinder iBinder) throws RemoteException, IOException, GoogleAuthException {
                return ((AccountChangeEventsResponse)zzd.zzm(zzas.zza.zza(iBinder).zza(new AccountChangeEventsRequest().setAccountName(this.zzVl).setEventIndex(this.zzVm)))).getEvents();
            }
        };
        return (List)zzd.zza(context, zzVe, object);
    }

    public static String getAccountId(Context context, String string2) throws GoogleAuthException, IOException {
        zzx.zzh(string2, "accountName must be provided");
        zzx.zzcE("Calling this from your main thread can lead to deadlock");
        zzd.zzad(context);
        return zzd.getToken(context, string2, "^^_account_id_^^", new Bundle());
    }

    public static String getToken(Context context, Account account, String string2) throws IOException, UserRecoverableAuthException, GoogleAuthException {
        return zzd.getToken(context, account, string2, new Bundle());
    }

    public static String getToken(Context context, Account account, String string2, Bundle bundle) throws IOException, UserRecoverableAuthException, GoogleAuthException {
        return zzd.zzc(context, account, string2, bundle).getToken();
    }

    @Deprecated
    public static String getToken(Context context, String string2, String string3) throws IOException, UserRecoverableAuthException, GoogleAuthException {
        return zzd.getToken(context, new Account(string2, GOOGLE_ACCOUNT_TYPE), string3);
    }

    @Deprecated
    public static String getToken(Context context, String string2, String string3, Bundle bundle) throws IOException, UserRecoverableAuthException, GoogleAuthException {
        return zzd.getToken(context, new Account(string2, GOOGLE_ACCOUNT_TYPE), string3, bundle);
    }

    @Deprecated
    @RequiresPermission(value="android.permission.MANAGE_ACCOUNTS")
    public static void invalidateToken(Context context, String string2) {
        AccountManager.get((Context)context).invalidateAuthToken(GOOGLE_ACCOUNT_TYPE, string2);
    }

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    private static <T> T zza(Context var0, ComponentName var1_4, zza<T> var2_5) throws IOException, GoogleAuthException {
        var3_6 = new com.google.android.gms.common.zza();
        var4_7 = zzl.zzau(var0 /* !! */ );
        if (var4_7.zza(var1_4, (ServiceConnection)var3_6, "GoogleAuthUtil")) {
            try {
                var0 /* !! */  = var2_5.zzan(var3_6.zzoJ());
                return (T)var0 /* !! */ ;
            }
            catch (InterruptedException var0_1) lbl-1000:
            // 2 sources

            {
                while (true) {
                    Log.i((String)"GoogleAuthUtil", (String)"Error on service connection.", (Throwable)var0 /* !! */ );
                    throw new IOException("Error on service connection.", (Throwable)var0 /* !! */ );
                }
            }
        }
        throw new IOException("Could not bind to service.");
        catch (RemoteException var0_3) {
            ** continue;
        }
        finally {
            var4_7.zzb(var1_4, (ServiceConnection)var3_6, "GoogleAuthUtil");
        }
    }

    private static void zzad(Context context) throws GoogleAuthException {
        try {
            zze.zzad(context.getApplicationContext());
            return;
        }
        catch (GooglePlayServicesRepairableException googlePlayServicesRepairableException) {
            throw new GooglePlayServicesAvailabilityException(googlePlayServicesRepairableException.getConnectionStatusCode(), googlePlayServicesRepairableException.getMessage(), googlePlayServicesRepairableException.getIntent());
        }
        catch (GooglePlayServicesNotAvailableException googlePlayServicesNotAvailableException) {
            throw new GoogleAuthException(googlePlayServicesNotAvailableException.getMessage());
        }
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    public static TokenData zzc(Context context, final Account object, String string2, Bundle bundle) throws IOException, UserRecoverableAuthException, GoogleAuthException {
        void var2_3;
        Bundle bundle2;
        zzx.zzcE("Calling this from your main thread can lead to deadlock");
        zzd.zzad(context);
        bundle2 = bundle2 == null ? new Bundle() : new Bundle(bundle2);
        String string3 = context.getApplicationInfo().packageName;
        bundle2.putString("clientPackageName", string3);
        if (TextUtils.isEmpty((CharSequence)bundle2.getString(KEY_ANDROID_PACKAGE_NAME))) {
            bundle2.putString(KEY_ANDROID_PACKAGE_NAME, string3);
        }
        bundle2.putLong("service_connection_start_time_millis", SystemClock.elapsedRealtime());
        zza<TokenData> zza2 = new zza<TokenData>((String)var2_3, bundle2){
            final /* synthetic */ String zzVh;
            final /* synthetic */ Bundle zzVi;
            {
                this.zzVh = string2;
                this.zzVi = bundle;
            }

            public TokenData zzam(IBinder object2) throws RemoteException, IOException, GoogleAuthException {
                Bundle bundle = (Bundle)zzd.zzm(zzas.zza.zza(object2).zza(object, this.zzVh, this.zzVi));
                if ((object2 = TokenData.zzc(bundle, "tokenDetails")) != null) {
                    return object2;
                }
                object2 = bundle.getString("Error");
                bundle = (Intent)bundle.getParcelable("userRecoveryIntent");
                com.google.android.gms.auth.firstparty.shared.zzd zzd2 = com.google.android.gms.auth.firstparty.shared.zzd.zzbY((String)object2);
                if (com.google.android.gms.auth.firstparty.shared.zzd.zza(zzd2)) {
                    throw new UserRecoverableAuthException((String)object2, (Intent)bundle);
                }
                if (com.google.android.gms.auth.firstparty.shared.zzd.zzc(zzd2)) {
                    throw new IOException((String)object2);
                }
                throw new GoogleAuthException((String)object2);
            }

            @Override
            public /* synthetic */ Object zzan(IBinder iBinder) throws RemoteException, IOException, GoogleAuthException {
                return this.zzam(iBinder);
            }
        };
        return zzd.zza(context, zzVe, zza2);
    }

    static void zzi(Intent object) {
        if (object == null) {
            throw new IllegalArgumentException("Callback cannot be null.");
        }
        object = object.toUri(1);
        try {
            Intent.parseUri((String)object, (int)1);
            return;
        }
        catch (URISyntaxException uRISyntaxException) {
            throw new IllegalArgumentException("Parameter callback contains invalid data. It must be serializable using toUri() and parseUri().");
        }
    }

    private static <T> T zzm(T t2) throws IOException {
        if (t2 == null) {
            Log.w((String)"GoogleAuthUtil", (String)"Binder call returned null.");
            throw new IOException("Service unavailable.");
        }
        return t2;
    }

    private static interface zza<T> {
        public T zzan(IBinder var1) throws RemoteException, IOException, GoogleAuthException;
    }
}

