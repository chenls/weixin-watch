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
import java.util.Arrays;

public class ConnectionConfiguration
implements SafeParcelable {
    public static final Parcelable.Creator<ConnectionConfiguration> CREATOR = new Parcelable.Creator<ConnectionConfiguration>(){

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        public ConnectionConfiguration a(Parcel parcel) {
            boolean bl2 = true;
            int n2 = parcel.readInt();
            String string2 = parcel.readString();
            String string3 = parcel.readString();
            int n3 = parcel.readInt();
            int n4 = parcel.readInt();
            if (parcel.readInt() == 1) {
                return new ConnectionConfiguration(n2, string2, string3, n3, n4, bl2);
            }
            bl2 = false;
            return new ConnectionConfiguration(n2, string2, string3, n3, n4, bl2);
        }

        public ConnectionConfiguration[] a(int n2) {
            return new ConnectionConfiguration[n2];
        }

        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return this.a(parcel);
        }

        public /* synthetic */ Object[] newArray(int n2) {
            return this.a(n2);
        }
    };
    final int a;
    private final String b;
    private final int c;
    private final boolean d;
    private final String e;
    private final int f;

    private ConnectionConfiguration(int n2, String string2, String string3, int n3, int n4, boolean bl2) {
        this.a = n2;
        this.e = string2;
        this.b = string3;
        this.f = n3;
        this.c = n4;
        this.d = bl2;
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        boolean bl2;
        boolean bl3 = bl2 = false;
        if (object instanceof ConnectionConfiguration) {
            object = (ConnectionConfiguration)object;
            bl3 = bl2;
            if (((ConnectionConfiguration)object).a == this.a) {
                bl3 = bl2;
                if (((ConnectionConfiguration)object).e.equals(this.e)) {
                    bl3 = bl2;
                    if (((ConnectionConfiguration)object).b.equals(this.b)) {
                        bl3 = bl2;
                        if (((ConnectionConfiguration)object).f == this.f) {
                            bl3 = bl2;
                            if (((ConnectionConfiguration)object).c == this.c) {
                                bl3 = bl2;
                                if (((ConnectionConfiguration)object).d == this.d) {
                                    bl3 = true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return bl3;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.a, this.e, this.b, this.f, this.c, this.d});
    }

    public String toString() {
        return "ConnectionConfiguration[ " + "mName=" + this.e + ", mAddress=" + this.b + ", mType=" + this.f + ", mRole=" + this.c + ", mEnabled=" + this.d + "]";
    }

    /*
     * Enabled aggressive block sorting
     */
    public void writeToParcel(Parcel parcel, int n2) {
        parcel.writeInt(this.a);
        parcel.writeString(this.e);
        parcel.writeString(this.b);
        parcel.writeInt(this.f);
        parcel.writeInt(this.c);
        n2 = this.d ? 1 : 0;
        parcel.writeInt(n2);
    }
}

