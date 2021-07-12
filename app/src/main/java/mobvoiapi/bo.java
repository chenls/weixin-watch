/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.os.Parcel
 *  android.os.Parcelable
 */
package mobvoiapi;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.List;

public class bo {
    public static int a(Parcel parcel) {
        return bo.b(parcel, 20293);
    }

    public static void a(Parcel parcel, int n2) {
        bo.c(parcel, n2);
    }

    public static void a(Parcel parcel, int n2, int n3) {
        bo.b(parcel, n2, 4);
        parcel.writeInt(n3);
    }

    public static void a(Parcel parcel, int n2, long l2) {
        bo.b(parcel, n2, 8);
        parcel.writeLong(l2);
    }

    public static void a(Parcel parcel, int n2, Bundle bundle, boolean bl2) {
        if (bundle == null) {
            if (bl2) {
                bo.b(parcel, n2, 0);
            }
            return;
        }
        n2 = bo.b(parcel, n2);
        parcel.writeBundle(bundle);
        bo.c(parcel, n2);
    }

    public static void a(Parcel parcel, int n2, Parcelable parcelable, int n3, boolean bl2) {
        if (parcelable == null) {
            if (bl2) {
                bo.b(parcel, n2, 0);
            }
            return;
        }
        n2 = bo.b(parcel, n2);
        parcelable.writeToParcel(parcel, n3);
        bo.c(parcel, n2);
    }

    public static void a(Parcel parcel, int n2, String string2, boolean bl2) {
        if (string2 == null) {
            if (bl2) {
                bo.b(parcel, n2, 0);
            }
            return;
        }
        n2 = bo.b(parcel, n2);
        parcel.writeString(string2);
        bo.c(parcel, n2);
    }

    /*
     * Enabled aggressive block sorting
     */
    public static void a(Parcel parcel, int n2, List<?> list, boolean bl2) {
        if (list == null) {
            if (bl2) {
                bo.b(parcel, n2, 0);
            }
            return;
        }
        int n3 = bo.b(parcel, n2);
        int n4 = list.size();
        parcel.writeInt(n4);
        n2 = 0;
        while (true) {
            if (n2 >= n4) {
                bo.c(parcel, n3);
                return;
            }
            Parcelable parcelable = (Parcelable)list.get(n2);
            if (parcelable == null) {
                parcel.writeInt(0);
            } else {
                bo.a(parcel, parcelable, 0);
            }
            ++n2;
        }
    }

    public static void a(Parcel parcel, int n2, byte[] byArray, boolean bl2) {
        if (byArray == null) {
            if (bl2) {
                bo.b(parcel, n2, 0);
            }
            return;
        }
        n2 = bo.b(parcel, n2);
        parcel.writeByteArray(byArray);
        bo.c(parcel, n2);
    }

    private static void a(Parcel parcel, Parcelable parcelable, int n2) {
        int n3 = parcel.dataPosition();
        parcel.writeInt(1);
        int n4 = parcel.dataPosition();
        parcelable.writeToParcel(parcel, n2);
        n2 = parcel.dataPosition();
        parcel.setDataPosition(n3);
        parcel.writeInt(n2 - n4);
        parcel.setDataPosition(n2);
    }

    private static int b(Parcel parcel, int n2) {
        parcel.writeInt(0xFFFF0000 | n2);
        parcel.writeInt(0);
        return parcel.dataPosition();
    }

    private static void b(Parcel parcel, int n2, int n3) {
        if (n3 >= 65535) {
            parcel.writeInt(0xFFFF0000 | n2);
            parcel.writeInt(n3);
            return;
        }
        parcel.writeInt(n3 << 16 | n2);
    }

    private static void c(Parcel parcel, int n2) {
        int n3 = parcel.dataPosition();
        parcel.setDataPosition(n2 - 4);
        parcel.writeInt(n3 - n2);
        parcel.setDataPosition(n3);
    }
}

