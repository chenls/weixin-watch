/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.os.AsyncTask
 *  android.util.Log
 */
package com.google.android.gms.security;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.internal.zzx;
import com.google.android.gms.common.zzc;
import com.google.android.gms.common.zze;
import java.lang.reflect.Method;

public class ProviderInstaller {
    public static final String PROVIDER_NAME = "GmsCore_OpenSSL";
    private static final zzc zzbgP = zzc.zzoK();
    private static Method zzbgQ;
    private static final Object zzqy;

    static {
        zzqy = new Object();
        zzbgQ = null;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void installIfNeeded(Context object) throws GooglePlayServicesRepairableException, GooglePlayServicesNotAvailableException {
        zzx.zzb(object, (Object)"Context must not be null");
        zzbgP.zzak((Context)object);
        Context context = zze.getRemoteContext(object);
        if (context == null) {
            Log.e((String)"ProviderInstaller", (String)"Failed to get remote context");
            throw new GooglePlayServicesNotAvailableException(8);
        }
        object = zzqy;
        synchronized (object) {
            try {
                if (zzbgQ == null) {
                    ProviderInstaller.zzaV(context);
                }
                zzbgQ.invoke(null, context);
                return;
            }
            catch (Exception exception) {
                Log.e((String)"ProviderInstaller", (String)("Failed to install provider: " + exception.getMessage()));
                throw new GooglePlayServicesNotAvailableException(8);
            }
        }
    }

    public static void installIfNeededAsync(final Context context, final ProviderInstallListener providerInstallListener) {
        zzx.zzb(context, (Object)"Context must not be null");
        zzx.zzb(providerInstallListener, (Object)"Listener must not be null");
        zzx.zzcD("Must be called on the UI thread");
        new AsyncTask<Void, Void, Integer>(){

            protected /* synthetic */ Object doInBackground(Object[] objectArray) {
                return this.zzc((Void[])objectArray);
            }

            protected /* synthetic */ void onPostExecute(Object object) {
                this.zze((Integer)object);
            }

            protected Integer zzc(Void ... voidArray) {
                try {
                    ProviderInstaller.installIfNeeded(context);
                }
                catch (GooglePlayServicesRepairableException googlePlayServicesRepairableException) {
                    return googlePlayServicesRepairableException.getConnectionStatusCode();
                }
                catch (GooglePlayServicesNotAvailableException googlePlayServicesNotAvailableException) {
                    return googlePlayServicesNotAvailableException.errorCode;
                }
                return 0;
            }

            protected void zze(Integer n2) {
                if (n2 == 0) {
                    providerInstallListener.onProviderInstalled();
                    return;
                }
                Intent intent = zzbgP.zza(context, n2, "pi");
                providerInstallListener.onProviderInstallFailed(n2, intent);
            }
        }.execute((Object[])new Void[0]);
    }

    private static void zzaV(Context context) throws ClassNotFoundException, NoSuchMethodException {
        zzbgQ = context.getClassLoader().loadClass("com.google.android.gms.common.security.ProviderInstallerImpl").getMethod("insertProvider", Context.class);
    }

    public static interface ProviderInstallListener {
        public void onProviderInstallFailed(int var1, Intent var2);

        public void onProviderInstalled();
    }
}

