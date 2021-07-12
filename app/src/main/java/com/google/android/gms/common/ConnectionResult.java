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
package com.google.android.gms.common;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.IntentSender;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzw;
import com.google.android.gms.common.zzb;

public final class ConnectionResult
implements SafeParcelable {
    public static final int API_UNAVAILABLE = 16;
    public static final int CANCELED = 13;
    public static final Parcelable.Creator<ConnectionResult> CREATOR;
    public static final int DEVELOPER_ERROR = 10;
    @Deprecated
    public static final int DRIVE_EXTERNAL_STORAGE_REQUIRED = 1500;
    public static final int INTERNAL_ERROR = 8;
    public static final int INTERRUPTED = 15;
    public static final int INVALID_ACCOUNT = 5;
    public static final int LICENSE_CHECK_FAILED = 11;
    public static final int NETWORK_ERROR = 7;
    public static final int RESOLUTION_REQUIRED = 6;
    public static final int RESTRICTED_PROFILE = 20;
    public static final int SERVICE_DISABLED = 3;
    public static final int SERVICE_INVALID = 9;
    public static final int SERVICE_MISSING = 1;
    public static final int SERVICE_MISSING_PERMISSION = 19;
    public static final int SERVICE_UPDATING = 18;
    public static final int SERVICE_VERSION_UPDATE_REQUIRED = 2;
    public static final int SIGN_IN_FAILED = 17;
    public static final int SIGN_IN_REQUIRED = 4;
    public static final int SUCCESS = 0;
    public static final int TIMEOUT = 14;
    public static final ConnectionResult zzafB;
    private final PendingIntent mPendingIntent;
    final int mVersionCode;
    private final int zzade;
    private final String zzafC;

    static {
        zzafB = new ConnectionResult(0);
        CREATOR = new zzb();
    }

    public ConnectionResult(int n2) {
        this(n2, null, null);
    }

    ConnectionResult(int n2, int n3, PendingIntent pendingIntent, String string2) {
        this.mVersionCode = n2;
        this.zzade = n3;
        this.mPendingIntent = pendingIntent;
        this.zzafC = string2;
    }

    public ConnectionResult(int n2, PendingIntent pendingIntent) {
        this(n2, pendingIntent, null);
    }

    public ConnectionResult(int n2, PendingIntent pendingIntent, String string2) {
        this(1, n2, pendingIntent, string2);
    }

    static String getStatusString(int n2) {
        switch (n2) {
            default: {
                return "UNKNOWN_ERROR_CODE(" + n2 + ")";
            }
            case 0: {
                return "SUCCESS";
            }
            case 1: {
                return "SERVICE_MISSING";
            }
            case 2: {
                return "SERVICE_VERSION_UPDATE_REQUIRED";
            }
            case 3: {
                return "SERVICE_DISABLED";
            }
            case 4: {
                return "SIGN_IN_REQUIRED";
            }
            case 5: {
                return "INVALID_ACCOUNT";
            }
            case 6: {
                return "RESOLUTION_REQUIRED";
            }
            case 7: {
                return "NETWORK_ERROR";
            }
            case 8: {
                return "INTERNAL_ERROR";
            }
            case 9: {
                return "SERVICE_INVALID";
            }
            case 10: {
                return "DEVELOPER_ERROR";
            }
            case 11: {
                return "LICENSE_CHECK_FAILED";
            }
            case 13: {
                return "CANCELED";
            }
            case 14: {
                return "TIMEOUT";
            }
            case 15: {
                return "INTERRUPTED";
            }
            case 16: {
                return "API_UNAVAILABLE";
            }
            case 17: {
                return "SIGN_IN_FAILED";
            }
            case 18: {
                return "SERVICE_UPDATING";
            }
            case 19: {
                return "SERVICE_MISSING_PERMISSION";
            }
            case 20: 
        }
        return "RESTRICTED_PROFILE";
    }

    public int describeContents() {
        return 0;
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean equals(Object object) {
        block5: {
            block4: {
                if (object == this) break block4;
                if (!(object instanceof ConnectionResult)) {
                    return false;
                }
                object = (ConnectionResult)object;
                if (this.zzade != ((ConnectionResult)object).zzade || !zzw.equal(this.mPendingIntent, ((ConnectionResult)object).mPendingIntent) || !zzw.equal(this.zzafC, ((ConnectionResult)object).zzafC)) break block5;
            }
            return true;
        }
        return false;
    }

    public int getErrorCode() {
        return this.zzade;
    }

    @Nullable
    public String getErrorMessage() {
        return this.zzafC;
    }

    @Nullable
    public PendingIntent getResolution() {
        return this.mPendingIntent;
    }

    public boolean hasResolution() {
        return this.zzade != 0 && this.mPendingIntent != null;
    }

    public int hashCode() {
        return zzw.hashCode(this.zzade, this.mPendingIntent, this.zzafC);
    }

    public boolean isSuccess() {
        return this.zzade == 0;
    }

    public void startResolutionForResult(Activity activity, int n2) throws IntentSender.SendIntentException {
        if (!this.hasResolution()) {
            return;
        }
        activity.startIntentSenderForResult(this.mPendingIntent.getIntentSender(), n2, null, 0, 0, 0);
    }

    public String toString() {
        return zzw.zzy(this).zzg("statusCode", ConnectionResult.getStatusString(this.zzade)).zzg("resolution", this.mPendingIntent).zzg("message", this.zzafC).toString();
    }

    public void writeToParcel(Parcel parcel, int n2) {
        zzb.zza(this, parcel, n2);
    }
}

