/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.mobvoi.android.search.internal;

import android.os.Parcel;
import android.os.Parcelable;
import mobvoiapi.bn;
import mobvoiapi.bo;

public class OneboxSearchResponse
implements Parcelable {
    public static final Parcelable.Creator<OneboxSearchResponse> CREATOR = new a();
    public final int a;
    public final String b;
    public final String c;
    public final String d;

    public OneboxSearchResponse(int n2, String string2, String string3, String string4) {
        this.a = n2;
        this.b = string2;
        this.c = string3;
        this.d = string4;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        com.mobvoi.android.search.internal.OneboxSearchResponse$a.a(this, parcel, n2);
    }

    public static class a
    implements Parcelable.Creator<OneboxSearchResponse> {
        public static void a(OneboxSearchResponse oneboxSearchResponse, Parcel parcel, int n2) {
            n2 = bo.a(parcel);
            bo.a(parcel, 1, oneboxSearchResponse.a);
            bo.a(parcel, 2, oneboxSearchResponse.b, true);
            bo.a(parcel, 3, oneboxSearchResponse.c, true);
            bo.a(parcel, 4, oneboxSearchResponse.d, true);
            bo.a(parcel, n2);
        }

        public OneboxSearchResponse a(Parcel parcel) {
            String string2 = null;
            int n2 = bn.b(parcel);
            int n3 = 0;
            String string3 = null;
            String string4 = null;
            block6: while (parcel.dataPosition() < n2) {
                int n4 = bn.a(parcel);
                switch (bn.a(n4)) {
                    default: {
                        bn.b(parcel, n4);
                        continue block6;
                    }
                    case 1: {
                        n3 = bn.c(parcel, n4);
                        continue block6;
                    }
                    case 2: {
                        string4 = bn.e(parcel, n4);
                        continue block6;
                    }
                    case 3: {
                        string3 = bn.e(parcel, n4);
                        continue block6;
                    }
                    case 4: 
                }
                string2 = bn.e(parcel, n4);
            }
            if (parcel.dataPosition() != n2) {
                throw new RuntimeException("parcel size exceeded. index = " + n2 + ", parcel = " + parcel);
            }
            return new OneboxSearchResponse(n3, string4, string3, string2);
        }

        public OneboxSearchResponse[] a(int n2) {
            return new OneboxSearchResponse[n2];
        }

        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return this.a(parcel);
        }

        public /* synthetic */ Object[] newArray(int n2) {
            return this.a(n2);
        }
    }
}

