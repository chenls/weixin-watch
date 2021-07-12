/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable$Creator
 */
package com.mobvoi.android.wearable.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.mobvoi.android.common.internal.safeparcel.SafeParcelable;
import mobvoiapi.bn;
import mobvoiapi.bo;

public class DeleteDataItemsResponse
implements SafeParcelable {
    public static final Parcelable.Creator<DeleteDataItemsResponse> CREATOR = new a();
    public final int a;
    public final int b;
    public final int c;

    public DeleteDataItemsResponse(int n2, int n3, int n4) {
        this.a = n2;
        this.b = n3;
        this.c = n4;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        com.mobvoi.android.wearable.internal.DeleteDataItemsResponse$a.a(this, parcel);
    }

    public static class a
    implements Parcelable.Creator<DeleteDataItemsResponse> {
        public static void a(DeleteDataItemsResponse deleteDataItemsResponse, Parcel parcel) {
            int n2 = bo.a(parcel);
            bo.a(parcel, 1, deleteDataItemsResponse.a);
            bo.a(parcel, 2, deleteDataItemsResponse.b);
            bo.a(parcel, 3, deleteDataItemsResponse.c);
            bo.a(parcel, n2);
        }

        public DeleteDataItemsResponse a(Parcel parcel) {
            int n2 = 0;
            int n3 = bn.b(parcel);
            int n4 = 0;
            int n5 = 0;
            block5: while (parcel.dataPosition() < n3) {
                int n6 = bn.a(parcel);
                switch (bn.a(n6)) {
                    default: {
                        bn.b(parcel, n6);
                        continue block5;
                    }
                    case 1: {
                        n5 = bn.c(parcel, n6);
                        continue block5;
                    }
                    case 2: {
                        n4 = bn.c(parcel, n6);
                        continue block5;
                    }
                    case 3: 
                }
                n2 = bn.c(parcel, n6);
            }
            if (parcel.dataPosition() != n3) {
                throw new RuntimeException("parcel size exceeded. index = " + n3 + ", parcel = " + parcel);
            }
            return new DeleteDataItemsResponse(n5, n4, n2);
        }

        public DeleteDataItemsResponse[] a(int n2) {
            return new DeleteDataItemsResponse[n2];
        }

        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return this.a(parcel);
        }

        public /* synthetic */ Object[] newArray(int n2) {
            return this.a(n2);
        }
    }
}

