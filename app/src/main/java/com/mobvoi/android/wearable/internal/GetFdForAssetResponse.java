/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.ParcelFileDescriptor
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.mobvoi.android.wearable.internal;

import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import com.mobvoi.android.common.internal.safeparcel.SafeParcelable;
import mobvoiapi.bn;
import mobvoiapi.bo;

public class GetFdForAssetResponse
implements SafeParcelable {
    public static final Parcelable.Creator<GetFdForAssetResponse> CREATOR = new a();
    public final int a;
    public final int b;
    public final ParcelFileDescriptor c;

    public GetFdForAssetResponse(int n2, int n3, ParcelFileDescriptor parcelFileDescriptor) {
        this.a = n2;
        this.b = n3;
        this.c = parcelFileDescriptor;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        com.mobvoi.android.wearable.internal.GetFdForAssetResponse$a.a(this, parcel, n2 | 1);
    }

    public static class a
    implements Parcelable.Creator<GetFdForAssetResponse> {
        public static void a(GetFdForAssetResponse getFdForAssetResponse, Parcel parcel, int n2) {
            int n3 = bo.a(parcel);
            bo.a(parcel, 1, getFdForAssetResponse.a);
            bo.a(parcel, 2, getFdForAssetResponse.b);
            bo.a(parcel, 3, (Parcelable)getFdForAssetResponse.c, n2, false);
            bo.a(parcel, n3);
        }

        public GetFdForAssetResponse a(Parcel parcel) {
            int n2 = 0;
            int n3 = bn.b(parcel);
            ParcelFileDescriptor parcelFileDescriptor = null;
            int n4 = 0;
            block5: while (parcel.dataPosition() < n3) {
                int n5 = bn.a(parcel);
                switch (bn.a(n5)) {
                    default: {
                        bn.b(parcel, n5);
                        continue block5;
                    }
                    case 1: {
                        n4 = bn.c(parcel, n5);
                        continue block5;
                    }
                    case 2: {
                        n2 = bn.c(parcel, n5);
                        continue block5;
                    }
                    case 3: 
                }
                parcelFileDescriptor = (ParcelFileDescriptor)bn.a(parcel, n5, ParcelFileDescriptor.CREATOR);
            }
            if (parcel.dataPosition() != n3) {
                throw new RuntimeException("parcel size exceeded. index = " + n3 + ", parcel = " + parcel);
            }
            return new GetFdForAssetResponse(n4, n2, parcelFileDescriptor);
        }

        public GetFdForAssetResponse[] a(int n2) {
            return new GetFdForAssetResponse[n2];
        }

        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return this.a(parcel);
        }

        public /* synthetic */ Object[] newArray(int n2) {
            return this.a(n2);
        }
    }
}

