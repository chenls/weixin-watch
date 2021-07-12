/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package mobvoiapi;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

public class bn {
    public static int a(int n2) {
        return 0xFFFF & n2;
    }

    public static int a(Parcel parcel) {
        return parcel.readInt();
    }

    public static int a(Parcel parcel, int n2) {
        if ((n2 & 0xFFFF0000) != -65536) {
            return n2 >> 16 & 0xFFFF;
        }
        return parcel.readInt();
    }

    public static <T extends Parcelable> T a(Parcel parcel, int n2, Parcelable.Creator<T> parcelable) {
        n2 = bn.a(parcel, n2);
        int n3 = parcel.dataPosition();
        if (n2 == 0) {
            return null;
        }
        parcelable = (Parcelable)parcelable.createFromParcel(parcel);
        parcel.setDataPosition(n2 + n3);
        return (T)parcelable;
    }

    private static void a(Parcel parcel, int n2, int n3) {
        if ((n2 = bn.a(parcel, n2)) != n3) {
            throw new a("Expected size = " + n3 + ", actual size = " + n2 + " (0x" + Integer.toHexString(n2) + ")", parcel);
        }
    }

    public static int b(Parcel parcel) {
        int n2 = bn.a(parcel);
        int n3 = bn.a(parcel, n2);
        int n4 = parcel.dataPosition();
        if (bn.a(n2) != 20293) {
            throw new a("Got 0x" + Integer.toHexString(n2) + " , not the object header.", parcel);
        }
        n2 = n4 + n3;
        if (n2 < n4 || n2 > parcel.dataSize()) {
            throw new a("invalid size, start= " + n4 + ", end= " + n2, parcel);
        }
        return n2;
    }

    public static <T> ArrayList<T> b(Parcel parcel, int n2, Parcelable.Creator<T> object) {
        n2 = bn.a(parcel, n2);
        int n3 = parcel.dataPosition();
        if (n2 == 0) {
            return null;
        }
        object = parcel.createTypedArrayList(object);
        parcel.setDataPosition(n2 + n3);
        return object;
    }

    public static void b(Parcel parcel, int n2) {
        parcel.setDataPosition(bn.a(parcel, n2) + parcel.dataPosition());
    }

    public static int c(Parcel parcel, int n2) {
        bn.a(parcel, n2, 4);
        return parcel.readInt();
    }

    public static long d(Parcel parcel, int n2) {
        bn.a(parcel, n2, 8);
        return parcel.readLong();
    }

    public static String e(Parcel parcel, int n2) {
        n2 = bn.a(parcel, n2);
        int n3 = parcel.dataPosition();
        if (n2 == 0) {
            return null;
        }
        String string2 = parcel.readString();
        parcel.setDataPosition(n2 + n3);
        return string2;
    }

    public static Bundle f(Parcel parcel, int n2) {
        n2 = bn.a(parcel, n2);
        int n3 = parcel.dataPosition();
        if (n2 == 0) {
            return null;
        }
        Bundle bundle = parcel.readBundle();
        parcel.setDataPosition(n2 + n3);
        return bundle;
    }

    public static byte[] g(Parcel parcel, int n2) {
        n2 = bn.a(parcel, n2);
        int n3 = parcel.dataPosition();
        if (n2 == 0) {
            return null;
        }
        byte[] byArray = parcel.createByteArray();
        parcel.setDataPosition(n2 + n3);
        return byArray;
    }

    static class a
    extends RuntimeException {
        public a(String string2, Parcel parcel) {
            super(string2 + " Parcel: pos=" + parcel.dataPosition() + " size: " + parcel.dataSize());
        }
    }
}

