/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.wearable.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.wearable.internal.zzh;
import com.google.android.gms.wearable.zzd;

public class AncsNotificationParcelable
implements SafeParcelable,
zzd {
    public static final Parcelable.Creator<AncsNotificationParcelable> CREATOR = new zzh();
    private int mId;
    final int mVersionCode;
    private String zzTJ;
    private String zzWQ;
    private final String zzaDH;
    private final String zzaUa;
    private final String zzaaH;
    private final String zzapg;
    private final String zzbrH;
    private byte zzbrI;
    private byte zzbrJ;
    private byte zzbrK;
    private byte zzbrL;

    AncsNotificationParcelable(int n2, int n3, String string2, String string3, String string4, String string5, String string6, String string7, byte by2, byte by3, byte by4, byte by5, String string8) {
        this.mId = n3;
        this.mVersionCode = n2;
        this.zzaUa = string2;
        this.zzbrH = string3;
        this.zzaaH = string4;
        this.zzapg = string5;
        this.zzaDH = string6;
        this.zzWQ = string7;
        this.zzbrI = by2;
        this.zzbrJ = by3;
        this.zzbrK = by4;
        this.zzbrL = by5;
        this.zzTJ = string8;
    }

    public int describeContents() {
        return 0;
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean equals(Object object) {
        block16: {
            block15: {
                if (this == object) break block15;
                if (object == null || this.getClass() != object.getClass()) {
                    return false;
                }
                object = (AncsNotificationParcelable)object;
                if (this.zzbrL != ((AncsNotificationParcelable)object).zzbrL) {
                    return false;
                }
                if (this.zzbrK != ((AncsNotificationParcelable)object).zzbrK) {
                    return false;
                }
                if (this.zzbrJ != ((AncsNotificationParcelable)object).zzbrJ) {
                    return false;
                }
                if (this.zzbrI != ((AncsNotificationParcelable)object).zzbrI) {
                    return false;
                }
                if (this.mId != ((AncsNotificationParcelable)object).mId) {
                    return false;
                }
                if (this.mVersionCode != ((AncsNotificationParcelable)object).mVersionCode) {
                    return false;
                }
                if (!this.zzaUa.equals(((AncsNotificationParcelable)object).zzaUa)) {
                    return false;
                }
                if (this.zzbrH != null ? !this.zzbrH.equals(((AncsNotificationParcelable)object).zzbrH) : ((AncsNotificationParcelable)object).zzbrH != null) {
                    return false;
                }
                if (!this.zzWQ.equals(((AncsNotificationParcelable)object).zzWQ)) {
                    return false;
                }
                if (!this.zzaaH.equals(((AncsNotificationParcelable)object).zzaaH)) {
                    return false;
                }
                if (!this.zzaDH.equals(((AncsNotificationParcelable)object).zzaDH)) {
                    return false;
                }
                if (!this.zzapg.equals(((AncsNotificationParcelable)object).zzapg)) break block16;
            }
            return true;
        }
        return false;
    }

    public String getDisplayName() {
        if (this.zzWQ == null) {
            return this.zzaUa;
        }
        return this.zzWQ;
    }

    public int getId() {
        return this.mId;
    }

    public String getPackageName() {
        return this.zzTJ;
    }

    public String getTitle() {
        return this.zzapg;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public int hashCode() {
        int n2;
        int n3 = this.mVersionCode;
        int n4 = this.mId;
        int n5 = this.zzaUa.hashCode();
        if (this.zzbrH != null) {
            n2 = this.zzbrH.hashCode();
            return ((((((((n2 + ((n3 * 31 + n4) * 31 + n5) * 31) * 31 + this.zzaaH.hashCode()) * 31 + this.zzapg.hashCode()) * 31 + this.zzaDH.hashCode()) * 31 + this.zzWQ.hashCode()) * 31 + this.zzbrI) * 31 + this.zzbrJ) * 31 + this.zzbrK) * 31 + this.zzbrL;
        }
        n2 = 0;
        return ((((((((n2 + ((n3 * 31 + n4) * 31 + n5) * 31) * 31 + this.zzaaH.hashCode()) * 31 + this.zzapg.hashCode()) * 31 + this.zzaDH.hashCode()) * 31 + this.zzWQ.hashCode()) * 31 + this.zzbrI) * 31 + this.zzbrJ) * 31 + this.zzbrK) * 31 + this.zzbrL;
    }

    public String toString() {
        return "AncsNotificationParcelable{mVersionCode=" + this.mVersionCode + ", mId=" + this.mId + ", mAppId='" + this.zzaUa + '\'' + ", mDateTime='" + this.zzbrH + '\'' + ", mNotificationText='" + this.zzaaH + '\'' + ", mTitle='" + this.zzapg + '\'' + ", mSubtitle='" + this.zzaDH + '\'' + ", mDisplayName='" + this.zzWQ + '\'' + ", mEventId=" + this.zzbrI + ", mEventFlags=" + this.zzbrJ + ", mCategoryId=" + this.zzbrK + ", mCategoryCount=" + this.zzbrL + '}';
    }

    public void writeToParcel(Parcel parcel, int n2) {
        zzh.zza(this, parcel, n2);
    }

    public String zzIB() {
        return this.zzbrH;
    }

    public String zzIC() {
        return this.zzaaH;
    }

    public byte zzID() {
        return this.zzbrI;
    }

    public byte zzIE() {
        return this.zzbrJ;
    }

    public byte zzIF() {
        return this.zzbrK;
    }

    public byte zzIG() {
        return this.zzbrL;
    }

    public String zzwK() {
        return this.zzaUa;
    }

    public String zzwc() {
        return this.zzaDH;
    }
}

