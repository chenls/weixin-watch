/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.os.Looper
 *  android.util.Log
 */
package com.google.android.gms.clearcut;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Looper;
import android.util.Log;
import com.google.android.gms.clearcut.LogEventParcelable;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzf;
import com.google.android.gms.common.internal.zzx;
import com.google.android.gms.internal.zzlv;
import com.google.android.gms.internal.zzlw;
import com.google.android.gms.internal.zzmq;
import com.google.android.gms.internal.zzmt;
import com.google.android.gms.internal.zzsz;
import com.google.android.gms.playlog.internal.PlayLoggerContext;
import java.util.ArrayList;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public final class zzb {
    public static final Api<Api.ApiOptions.NoOptions> API;
    public static final Api.zzc<zzlw> zzUI;
    public static final Api.zza<zzlw, Api.ApiOptions.NoOptions> zzUJ;
    public static final com.google.android.gms.clearcut.zzc zzaeQ;
    private final Context mContext;
    private final String zzTJ;
    private final int zzaeR;
    private String zzaeS;
    private int zzaeT = -1;
    private String zzaeU;
    private String zzaeV;
    private final boolean zzaeW;
    private int zzaeX = 0;
    private final com.google.android.gms.clearcut.zzc zzaeY;
    private final com.google.android.gms.clearcut.zza zzaeZ;
    private zzc zzafa;
    private final zzmq zzqW;

    static {
        zzUI = new Api.zzc();
        zzUJ = new Api.zza<zzlw, Api.ApiOptions.NoOptions>(){

            @Override
            public /* synthetic */ Api.zzb zza(Context context, Looper looper, zzf zzf2, Object object, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
                return this.zze(context, looper, zzf2, (Api.ApiOptions.NoOptions)object, connectionCallbacks, onConnectionFailedListener);
            }

            public zzlw zze(Context context, Looper looper, zzf zzf2, Api.ApiOptions.NoOptions noOptions, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
                return new zzlw(context, looper, zzf2, connectionCallbacks, onConnectionFailedListener);
            }
        };
        API = new Api<Api.ApiOptions.NoOptions>("ClearcutLogger.API", zzUJ, zzUI);
        zzaeQ = new zzlv();
    }

    /*
     * Enabled aggressive block sorting
     */
    public zzb(Context context, int n2, String string2, String string3, String string4, boolean bl2, com.google.android.gms.clearcut.zzc zzc2, zzmq zzmq2, zzc zzc3, com.google.android.gms.clearcut.zza zza2) {
        Context context2 = context.getApplicationContext();
        if (context2 == null) {
            context2 = context;
        }
        this.mContext = context2;
        this.zzTJ = context.getPackageName();
        this.zzaeR = this.zzai(context);
        this.zzaeT = n2;
        this.zzaeS = string2;
        this.zzaeU = string3;
        this.zzaeV = string4;
        this.zzaeW = bl2;
        this.zzaeY = zzc2;
        this.zzqW = zzmq2;
        if (zzc3 == null) {
            zzc3 = new zzc();
        }
        this.zzafa = zzc3;
        this.zzaeZ = zza2;
        this.zzaeX = 0;
        if (this.zzaeW) {
            bl2 = this.zzaeU == null;
            zzx.zzb(bl2, (Object)"can't be anonymous with an upload account");
        }
    }

    @Deprecated
    public zzb(Context context, String string2, String string3, String string4) {
        this(context, -1, string2, string3, string4, false, zzaeQ, zzmt.zzsc(), null, com.google.android.gms.clearcut.zza.zzaeP);
    }

    private int zzai(Context context) {
        try {
            int n2 = context.getPackageManager().getPackageInfo((String)context.getPackageName(), (int)0).versionCode;
            return n2;
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            Log.wtf((String)"ClearcutLogger", (String)"This can't happen.");
            return 0;
        }
    }

    private static int[] zzb(ArrayList<Integer> object) {
        if (object == null) {
            return null;
        }
        int[] nArray = new int[((ArrayList)object).size()];
        object = ((ArrayList)object).iterator();
        int n2 = 0;
        while (object.hasNext()) {
            nArray[n2] = (Integer)object.next();
            ++n2;
        }
        return nArray;
    }

    public boolean zza(GoogleApiClient googleApiClient, long l2, TimeUnit timeUnit) {
        return this.zzaeY.zza(googleApiClient, l2, timeUnit);
    }

    public zza zzi(byte[] byArray) {
        return new zza(byArray);
    }

    public class zza {
        private String zzaeS;
        private int zzaeT;
        private String zzaeU;
        private String zzaeV;
        private int zzaeX;
        private final zzb zzafb;
        private zzb zzafc;
        private ArrayList<Integer> zzafd;
        private final zzsz.zzd zzafe;
        private boolean zzaff;

        private zza(byte[] byArray) {
            this(byArray, (zzb)null);
        }

        private zza(byte[] byArray, zzb zzb3) {
            this.zzaeT = zzb.this.zzaeT;
            this.zzaeS = zzb.this.zzaeS;
            this.zzaeU = zzb.this.zzaeU;
            this.zzaeV = zzb.this.zzaeV;
            this.zzaeX = zzb.this.zzaeX;
            this.zzafd = null;
            this.zzafe = new zzsz.zzd();
            this.zzaff = false;
            this.zzaeU = zzb.this.zzaeU;
            this.zzaeV = zzb.this.zzaeV;
            this.zzafe.zzbuR = zzb.this.zzqW.currentTimeMillis();
            this.zzafe.zzbuS = zzb.this.zzqW.elapsedRealtime();
            this.zzafe.zzbvi = zzb.this.zzaeZ.zzah(zzb.this.mContext);
            this.zzafe.zzbvd = zzb.this.zzafa.zzC(this.zzafe.zzbuR);
            if (byArray != null) {
                this.zzafe.zzbuY = byArray;
            }
            this.zzafb = zzb3;
        }

        public zza zzbq(int n2) {
            this.zzafe.zzbuU = n2;
            return this;
        }

        public zza zzbr(int n2) {
            this.zzafe.zzob = n2;
            return this;
        }

        public PendingResult<Status> zzd(GoogleApiClient googleApiClient) {
            if (this.zzaff) {
                throw new IllegalStateException("do not reuse LogEventBuilder");
            }
            this.zzaff = true;
            return zzb.this.zzaeY.zza(googleApiClient, this.zzoE());
        }

        public LogEventParcelable zzoE() {
            return new LogEventParcelable(new PlayLoggerContext(zzb.this.zzTJ, zzb.this.zzaeR, this.zzaeT, this.zzaeS, this.zzaeU, this.zzaeV, zzb.this.zzaeW, this.zzaeX), this.zzafe, this.zzafb, this.zzafc, zzb.zzb(this.zzafd));
        }
    }

    public static interface zzb {
        public byte[] zzoF();
    }

    public static class zzc {
        public long zzC(long l2) {
            return TimeZone.getDefault().getOffset(l2) / 1000;
        }
    }
}

