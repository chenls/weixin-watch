/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable$Creator
 *  android.util.Log
 */
package com.google.android.gms.wearable.internal;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.wearable.ChannelApi;
import com.google.android.gms.wearable.internal.ChannelImpl;
import com.google.android.gms.wearable.internal.zzn;

public final class ChannelEventParcelable
implements SafeParcelable {
    public static final Parcelable.Creator<ChannelEventParcelable> CREATOR = new zzn();
    final int mVersionCode;
    final int type;
    final int zzbsa;
    final int zzbsb;
    final ChannelImpl zzbsc;

    ChannelEventParcelable(int n2, ChannelImpl channelImpl, int n3, int n4, int n5) {
        this.mVersionCode = n2;
        this.zzbsc = channelImpl;
        this.type = n3;
        this.zzbsa = n4;
        this.zzbsb = n5;
    }

    private static String zzlG(int n2) {
        switch (n2) {
            default: {
                return Integer.toString(n2);
            }
            case 1: {
                return "CHANNEL_OPENED";
            }
            case 2: {
                return "CHANNEL_CLOSED";
            }
            case 4: {
                return "OUTPUT_CLOSED";
            }
            case 3: 
        }
        return "INPUT_CLOSED";
    }

    private static String zzlH(int n2) {
        switch (n2) {
            default: {
                return Integer.toString(n2);
            }
            case 1: {
                return "CLOSE_REASON_DISCONNECTED";
            }
            case 2: {
                return "CLOSE_REASON_REMOTE_CLOSE";
            }
            case 3: {
                return "CLOSE_REASON_LOCAL_CLOSE";
            }
            case 0: 
        }
        return "CLOSE_REASON_NORMAL";
    }

    public int describeContents() {
        return 0;
    }

    public String toString() {
        return "ChannelEventParcelable[versionCode=" + this.mVersionCode + ", channel=" + this.zzbsc + ", type=" + ChannelEventParcelable.zzlG(this.type) + ", closeReason=" + ChannelEventParcelable.zzlH(this.zzbsa) + ", appErrorCode=" + this.zzbsb + "]";
    }

    public void writeToParcel(Parcel parcel, int n2) {
        zzn.zza(this, parcel, n2);
    }

    public void zza(ChannelApi.ChannelListener channelListener) {
        switch (this.type) {
            default: {
                Log.w((String)"ChannelEventParcelable", (String)("Unknown type: " + this.type));
                return;
            }
            case 1: {
                channelListener.onChannelOpened(this.zzbsc);
                return;
            }
            case 2: {
                channelListener.onChannelClosed(this.zzbsc, this.zzbsa, this.zzbsb);
                return;
            }
            case 3: {
                channelListener.onInputClosed(this.zzbsc, this.zzbsa, this.zzbsb);
                return;
            }
            case 4: 
        }
        channelListener.onOutputClosed(this.zzbsc, this.zzbsa, this.zzbsb);
    }
}

