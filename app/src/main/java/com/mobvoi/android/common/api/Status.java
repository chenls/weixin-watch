/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.PendingIntent
 *  android.content.IntentSender$SendIntentException
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.mobvoi.android.common.api;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.IntentSender;
import android.os.Parcel;
import android.os.Parcelable;
import com.mobvoi.android.common.api.CommonStatusCodes;
import com.mobvoi.android.common.api.Result;
import com.mobvoi.android.common.internal.safeparcel.SafeParcelable;
import mobvoiapi.j;

public class Status
extends CommonStatusCodes
implements Result,
SafeParcelable {
    public static final Parcelable.Creator<Status> CREATOR;
    public static final Status ST_INTERNAL_ERROR;
    public static final Status ST_SUCCESS;
    private int a;
    private int b;
    private String c;
    private final PendingIntent d;

    static {
        ST_SUCCESS = new Status(0);
        ST_INTERNAL_ERROR = new Status(8);
        CREATOR = new Parcelable.Creator<Status>(){

            public Status createFromParcel(Parcel parcel) {
                return new Status(parcel);
            }

            public Status[] newArray(int n2) {
                return new Status[n2];
            }
        };
    }

    public Status(int n2) {
        this(1, n2, null, null);
    }

    Status(int n2, int n3, String string2, PendingIntent pendingIntent) {
        this.a = n2;
        this.b = n3;
        this.c = string2;
        this.d = pendingIntent;
    }

    public Status(int n2, String string2, PendingIntent pendingIntent) {
        this(1, n2, string2, pendingIntent);
    }

    private Status(Parcel parcel) {
        this.a = parcel.readInt();
        this.b = parcel.readInt();
        this.c = parcel.readString();
        this.d = (PendingIntent)parcel.readParcelable(PendingIntent.class.getClassLoader());
    }

    private String a() {
        if (this.c != null) {
            return this.c;
        }
        return CommonStatusCodes.getStatusCodeString(this.b);
    }

    public int describeContents() {
        return 0;
    }

    public PendingIntent getResolution() {
        return this.d;
    }

    @Override
    public Status getStatus() {
        return this;
    }

    public int getStatusCode() {
        return this.b;
    }

    public String getStatusMessage() {
        return this.c;
    }

    public boolean hasResolution() {
        return this.b == 0 && this.d != null;
    }

    public boolean isCanceled() {
        return this.b == 16;
    }

    public boolean isInterrupted() {
        return this.b == 12;
    }

    public boolean isSuccess() {
        return this.b <= 0;
    }

    public boolean isTimeout() {
        return this.b == 13;
    }

    public void startResolutionForResult(Activity activity, int n2) throws IntentSender.SendIntentException {
        if (!this.hasResolution()) {
            return;
        }
        activity.startIntentSenderForResult(this.d.getIntentSender(), n2, null, 0, 0, 0);
    }

    public String toString() {
        return j.a(this).a("statusCode", this.a()).a("resolution", this.d).toString();
    }

    public void writeToParcel(Parcel parcel, int n2) {
        parcel.writeInt(this.a);
        parcel.writeInt(this.b);
        parcel.writeString(this.c);
        parcel.writeParcelable((Parcelable)this.d, n2);
    }
}

