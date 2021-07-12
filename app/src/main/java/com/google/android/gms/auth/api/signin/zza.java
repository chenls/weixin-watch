/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.auth.api.signin;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.auth.api.signin.EmailSignInOptions;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

public class zza
implements Parcelable.Creator<EmailSignInOptions> {
    static void zza(EmailSignInOptions emailSignInOptions, Parcel parcel, int n2) {
        int n3 = zzb.zzav(parcel);
        zzb.zzc(parcel, 1, emailSignInOptions.versionCode);
        zzb.zza(parcel, 2, (Parcelable)emailSignInOptions.zzmF(), n2, false);
        zzb.zza(parcel, 3, emailSignInOptions.zzmH(), false);
        zzb.zza(parcel, 4, (Parcelable)emailSignInOptions.zzmG(), n2, false);
        zzb.zzI(parcel, n3);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.zzQ(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.zzaL(n2);
    }

    /*
     * Enabled aggressive block sorting
     */
    public EmailSignInOptions zzQ(Parcel parcel) {
        Uri uri = null;
        int n2 = com.google.android.gms.common.internal.safeparcel.zza.zzau(parcel);
        int n3 = 0;
        String string2 = null;
        String string3 = null;
        while (parcel.dataPosition() < n2) {
            String string4;
            int n4 = com.google.android.gms.common.internal.safeparcel.zza.zzat(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzca(n4)) {
                default: {
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, n4);
                    string4 = string2;
                    string2 = string3;
                    string3 = string4;
                    break;
                }
                case 1: {
                    n3 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, n4);
                    string4 = string3;
                    string3 = string2;
                    string2 = string4;
                    break;
                }
                case 2: {
                    string4 = (Uri)com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, n4, Uri.CREATOR);
                    string3 = string2;
                    string2 = string4;
                    break;
                }
                case 3: {
                    string4 = com.google.android.gms.common.internal.safeparcel.zza.zzp(parcel, n4);
                    string2 = string3;
                    string3 = string4;
                    break;
                }
                case 4: {
                    uri = (Uri)com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, n4, Uri.CREATOR);
                    string4 = string3;
                    string3 = string2;
                    string2 = string4;
                }
            }
            string4 = string2;
            string2 = string3;
            string3 = string4;
        }
        if (parcel.dataPosition() != n2) {
            throw new zza.zza("Overread allowed size end=" + n2, parcel);
        }
        return new EmailSignInOptions(n3, (Uri)string3, string2, uri);
    }

    public EmailSignInOptions[] zzaL(int n2) {
        return new EmailSignInOptions[n2];
    }
}

