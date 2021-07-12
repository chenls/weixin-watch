/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.wearable;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.wearable.ConnectionConfiguration;

public class zzg
implements Parcelable.Creator<ConnectionConfiguration> {
    static void zza(ConnectionConfiguration connectionConfiguration, Parcel parcel, int n2) {
        n2 = zzb.zzav(parcel);
        zzb.zzc(parcel, 1, connectionConfiguration.mVersionCode);
        zzb.zza(parcel, 2, connectionConfiguration.getName(), false);
        zzb.zza(parcel, 3, connectionConfiguration.getAddress(), false);
        zzb.zzc(parcel, 4, connectionConfiguration.getType());
        zzb.zzc(parcel, 5, connectionConfiguration.getRole());
        zzb.zza(parcel, 6, connectionConfiguration.isEnabled());
        zzb.zza(parcel, 7, connectionConfiguration.isConnected());
        zzb.zza(parcel, 8, connectionConfiguration.zzIt(), false);
        zzb.zza(parcel, 9, connectionConfiguration.zzIu());
        zzb.zza(parcel, 10, connectionConfiguration.getNodeId(), false);
        zzb.zzI(parcel, n2);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.zzhY(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.zzlz(n2);
    }

    public ConnectionConfiguration zzhY(Parcel parcel) {
        String string2 = null;
        boolean bl2 = false;
        int n2 = zza.zzau(parcel);
        String string3 = null;
        boolean bl3 = false;
        boolean bl4 = false;
        int n3 = 0;
        int n4 = 0;
        String string4 = null;
        String string5 = null;
        int n5 = 0;
        block12: while (parcel.dataPosition() < n2) {
            int n6 = zza.zzat(parcel);
            switch (zza.zzca(n6)) {
                default: {
                    zza.zzb(parcel, n6);
                    continue block12;
                }
                case 1: {
                    n5 = zza.zzg(parcel, n6);
                    continue block12;
                }
                case 2: {
                    string5 = zza.zzp(parcel, n6);
                    continue block12;
                }
                case 3: {
                    string4 = zza.zzp(parcel, n6);
                    continue block12;
                }
                case 4: {
                    n4 = zza.zzg(parcel, n6);
                    continue block12;
                }
                case 5: {
                    n3 = zza.zzg(parcel, n6);
                    continue block12;
                }
                case 6: {
                    bl4 = zza.zzc(parcel, n6);
                    continue block12;
                }
                case 7: {
                    bl3 = zza.zzc(parcel, n6);
                    continue block12;
                }
                case 8: {
                    string3 = zza.zzp(parcel, n6);
                    continue block12;
                }
                case 9: {
                    bl2 = zza.zzc(parcel, n6);
                    continue block12;
                }
                case 10: 
            }
            string2 = zza.zzp(parcel, n6);
        }
        if (parcel.dataPosition() != n2) {
            throw new zza.zza("Overread allowed size end=" + n2, parcel);
        }
        return new ConnectionConfiguration(n5, string5, string4, n4, n3, bl4, bl3, string3, bl2, string2);
    }

    public ConnectionConfiguration[] zzlz(int n2) {
        return new ConnectionConfiguration[n2];
    }
}

