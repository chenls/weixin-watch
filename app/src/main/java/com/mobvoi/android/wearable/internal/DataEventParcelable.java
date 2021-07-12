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
import com.mobvoi.android.wearable.DataEvent;
import com.mobvoi.android.wearable.DataItem;
import com.mobvoi.android.wearable.internal.DataItemParcelable;
import mobvoiapi.bn;
import mobvoiapi.bo;

public class DataEventParcelable
implements SafeParcelable,
DataEvent {
    public static final Parcelable.Creator<DataEventParcelable> CREATOR = new a();
    private int a;
    private DataItemParcelable b;

    public DataEventParcelable(int n2, DataItemParcelable dataItemParcelable) {
        this.a = n2;
        this.b = dataItemParcelable;
    }

    public DataEvent a() {
        this.b.freeze();
        return this;
    }

    public int describeContents() {
        return 0;
    }

    @Override
    public /* synthetic */ Object freeze() {
        return this.a();
    }

    @Override
    public DataItem getDataItem() {
        return this.b;
    }

    @Override
    public int getType() {
        return this.a;
    }

    @Override
    public boolean isDataValid() {
        return this.b.isDataValid();
    }

    public void writeToParcel(Parcel parcel, int n2) {
        com.mobvoi.android.wearable.internal.DataEventParcelable$a.a(this, parcel, n2);
    }

    public static class a
    implements Parcelable.Creator<DataEventParcelable> {
        static void a(DataEventParcelable dataEventParcelable, Parcel parcel, int n2) {
            int n3 = bo.a(parcel);
            bo.a(parcel, 1, dataEventParcelable.a);
            bo.a(parcel, 2, dataEventParcelable.b, n2, false);
            bo.a(parcel, n3);
        }

        public DataEventParcelable a(Parcel parcel) {
            int n2 = bn.b(parcel);
            int n3 = 0;
            DataItemParcelable dataItemParcelable = null;
            block4: while (parcel.dataPosition() < n2) {
                int n4 = bn.a(parcel);
                switch (bn.a(n4)) {
                    default: {
                        bn.b(parcel, n4);
                        continue block4;
                    }
                    case 1: {
                        n3 = bn.c(parcel, n4);
                        continue block4;
                    }
                    case 2: 
                }
                dataItemParcelable = bn.a(parcel, n4, DataItemParcelable.CREATOR);
            }
            if (parcel.dataPosition() != n2) {
                throw new RuntimeException("parcel size exceeded. index = " + n2 + ", parcel = " + parcel);
            }
            return new DataEventParcelable(n3, dataItemParcelable);
        }

        public DataEventParcelable[] a(int n2) {
            return new DataEventParcelable[n2];
        }

        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return this.a(parcel);
        }

        public /* synthetic */ Object[] newArray(int n2) {
            return this.a(n2);
        }
    }
}

