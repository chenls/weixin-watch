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
import com.mobvoi.android.wearable.internal.DataEventParcelable;
import com.mobvoi.android.wearable.internal.DataItemParcelable;
import java.util.ArrayList;
import java.util.List;
import mobvoiapi.bn;
import mobvoiapi.bo;

public class DataHolder
implements SafeParcelable {
    public static final Parcelable.Creator<DataHolder> CREATOR = new a();
    private int a;
    private List<DataItemParcelable> b;
    private List<DataEventParcelable> c;

    public DataHolder(int n2, List<DataItemParcelable> list, List<DataEventParcelable> list2) {
        this.a = n2;
        this.b = list;
        this.c = list2;
    }

    public int a() {
        return this.a;
    }

    public List<DataItemParcelable> b() {
        return this.b;
    }

    public List<DataEventParcelable> c() {
        return this.c;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        com.mobvoi.android.wearable.internal.DataHolder$a.a(this, parcel, n2);
    }

    public static class a
    implements Parcelable.Creator<DataHolder> {
        static void a(DataHolder dataHolder, Parcel parcel, int n2) {
            n2 = bo.a(parcel);
            bo.a(parcel, 1, dataHolder.a);
            bo.a(parcel, 2, dataHolder.b, false);
            bo.a(parcel, 3, dataHolder.c, false);
            bo.a(parcel, n2);
        }

        public DataHolder a(Parcel parcel) {
            ArrayList<DataEventParcelable> arrayList = null;
            int n2 = bn.b(parcel);
            int n3 = 0;
            ArrayList<DataItemParcelable> arrayList2 = null;
            block5: while (parcel.dataPosition() < n2) {
                int n4 = bn.a(parcel);
                switch (bn.a(n4)) {
                    default: {
                        bn.b(parcel, n4);
                        continue block5;
                    }
                    case 1: {
                        n3 = bn.c(parcel, n4);
                        continue block5;
                    }
                    case 2: {
                        arrayList2 = bn.b(parcel, n4, DataItemParcelable.CREATOR);
                        continue block5;
                    }
                    case 3: 
                }
                arrayList = bn.b(parcel, n4, DataEventParcelable.CREATOR);
            }
            if (parcel.dataPosition() != n2) {
                throw new RuntimeException("parcel size exceeded. index = " + n2 + ", parcel = " + parcel);
            }
            return new DataHolder(n3, arrayList2, arrayList);
        }

        public DataHolder[] a(int n2) {
            return new DataHolder[n2];
        }

        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return this.a(parcel);
        }

        public /* synthetic */ Object[] newArray(int n2) {
            return this.a(n2);
        }
    }
}

