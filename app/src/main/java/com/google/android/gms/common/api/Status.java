/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.PendingIntent
 *  android.content.IntentSender$SendIntentException
 *  android.os.Parcel
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.common.api;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.IntentSender;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.zzc;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzw;

public final class Status
implements Result,
SafeParcelable {
    public static final Parcelable.Creator<Status> CREATOR;
    public static final Status zzagC;
    public static final Status zzagD;
    public static final Status zzagE;
    public static final Status zzagF;
    public static final Status zzagG;
    private final PendingIntent mPendingIntent;
    private final int mVersionCode;
    private final int zzade;
    private final String zzafC;

    static {
        zzagC = new Status(0);
        zzagD = new Status(14);
        zzagE = new Status(8);
        zzagF = new Status(15);
        zzagG = new Status(16);
        CREATOR = new zzc();
    }

    public Status(int n2) {
        this(n2, null);
    }

    Status(int n2, int n3, String string2, PendingIntent pendingIntent) {
        this.mVersionCode = n2;
        this.zzade = n3;
        this.zzafC = string2;
        this.mPendingIntent = pendingIntent;
    }

    public Status(int n2, String string2) {
        this(1, n2, string2, null);
    }

    public Status(int n2, String string2, PendingIntent pendingIntent) {
        this(1, n2, string2, pendingIntent);
    }

    private String zzpd() {
        if (this.zzafC != null) {
            return this.zzafC;
        }
        return CommonStatusCodes.getStatusCodeString(this.zzade);
    }

    public int describeContents() {
        return 0;
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean equals(Object object) {
        block3: {
            block2: {
                if (!(object instanceof Status)) break block2;
                object = (Status)object;
                if (this.mVersionCode == ((Status)object).mVersionCode && this.zzade == ((Status)object).zzade && zzw.equal(this.zzafC, ((Status)object).zzafC) && zzw.equal(this.mPendingIntent, ((Status)object).mPendingIntent)) break block3;
            }
            return false;
        }
        return true;
    }

    public PendingIntent getResolution() {
        return this.mPendingIntent;
    }

    @Override
    public Status getStatus() {
        return this;
    }

    public int getStatusCode() {
        return this.zzade;
    }

    public String getStatusMessage() {
        return this.zzafC;
    }

    int getVersionCode() {
        return this.mVersionCode;
    }

    public boolean hasResolution() {
        return this.mPendingIntent != null;
    }

    public int hashCode() {
        return zzw.hashCode(this.mVersionCode, this.zzade, this.zzafC, this.mPendingIntent);
    }

    public boolean isCanceled() {
        return this.zzade == 16;
    }

    public boolean isInterrupted() {
        return this.zzade == 14;
    }

    public boolean isSuccess() {
        return this.zzade <= 0;
    }

    public void startResolutionForResult(Activity activity, int n2) throws IntentSender.SendIntentException {
        if (!this.hasResolution()) {
            return;
        }
        activity.startIntentSenderForResult(this.mPendingIntent.getIntentSender(), n2, null, 0, 0, 0);
    }

    public String toString() {
        return zzw.zzy(this).zzg("statusCode", this.zzpd()).zzg("resolution", this.mPendingIntent).toString();
    }

    public void writeToParcel(Parcel parcel, int n2) {
        zzc.zza(this, parcel, n2);
    }

    PendingIntent zzpc() {
        return this.mPendingIntent;
    }
}

