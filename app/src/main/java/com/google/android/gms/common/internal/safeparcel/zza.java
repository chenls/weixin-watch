/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.os.IBinder
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.common.internal.safeparcel;

import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class zza {
    public static BigDecimal[] zzA(Parcel parcel, int n2) {
        int n3 = zza.zza(parcel, n2);
        int n4 = parcel.dataPosition();
        if (n3 == 0) {
            return null;
        }
        int n5 = parcel.readInt();
        BigDecimal[] bigDecimalArray = new BigDecimal[n5];
        for (n2 = 0; n2 < n5; ++n2) {
            byte[] byArray = parcel.createByteArray();
            int n6 = parcel.readInt();
            bigDecimalArray[n2] = new BigDecimal(new BigInteger(byArray), n6);
        }
        parcel.setDataPosition(n4 + n3);
        return bigDecimalArray;
    }

    public static String[] zzB(Parcel parcel, int n2) {
        n2 = zza.zza(parcel, n2);
        int n3 = parcel.dataPosition();
        if (n2 == 0) {
            return null;
        }
        String[] stringArray = parcel.createStringArray();
        parcel.setDataPosition(n2 + n3);
        return stringArray;
    }

    public static ArrayList<Integer> zzC(Parcel parcel, int n2) {
        int n3 = zza.zza(parcel, n2);
        int n4 = parcel.dataPosition();
        if (n3 == 0) {
            return null;
        }
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        int n5 = parcel.readInt();
        for (n2 = 0; n2 < n5; ++n2) {
            arrayList.add(parcel.readInt());
        }
        parcel.setDataPosition(n4 + n3);
        return arrayList;
    }

    public static ArrayList<String> zzD(Parcel parcel, int n2) {
        n2 = zza.zza(parcel, n2);
        int n3 = parcel.dataPosition();
        if (n2 == 0) {
            return null;
        }
        ArrayList arrayList = parcel.createStringArrayList();
        parcel.setDataPosition(n2 + n3);
        return arrayList;
    }

    public static Parcel zzE(Parcel parcel, int n2) {
        n2 = zza.zza(parcel, n2);
        int n3 = parcel.dataPosition();
        if (n2 == 0) {
            return null;
        }
        Parcel parcel2 = Parcel.obtain();
        parcel2.appendFrom(parcel, n3, n2);
        parcel.setDataPosition(n2 + n3);
        return parcel2;
    }

    /*
     * Enabled aggressive block sorting
     */
    public static Parcel[] zzF(Parcel parcel, int n2) {
        int n3 = zza.zza(parcel, n2);
        int n4 = parcel.dataPosition();
        if (n3 == 0) {
            return null;
        }
        int n5 = parcel.readInt();
        Parcel[] parcelArray = new Parcel[n5];
        n2 = 0;
        while (true) {
            if (n2 >= n5) {
                parcel.setDataPosition(n4 + n3);
                return parcelArray;
            }
            int n6 = parcel.readInt();
            if (n6 != 0) {
                int n7 = parcel.dataPosition();
                Parcel parcel2 = Parcel.obtain();
                parcel2.appendFrom(parcel, n7, n6);
                parcelArray[n2] = parcel2;
                parcel.setDataPosition(n6 + n7);
            } else {
                parcelArray[n2] = null;
            }
            ++n2;
        }
    }

    public static int zza(Parcel parcel, int n2) {
        if ((n2 & 0xFFFF0000) != -65536) {
            return n2 >> 16 & 0xFFFF;
        }
        return parcel.readInt();
    }

    public static <T extends Parcelable> T zza(Parcel parcel, int n2, Parcelable.Creator<T> parcelable) {
        n2 = zza.zza(parcel, n2);
        int n3 = parcel.dataPosition();
        if (n2 == 0) {
            return null;
        }
        parcelable = (Parcelable)parcelable.createFromParcel(parcel);
        parcel.setDataPosition(n2 + n3);
        return (T)parcelable;
    }

    private static void zza(Parcel parcel, int n2, int n3) {
        if ((n2 = zza.zza(parcel, n2)) != n3) {
            throw new zza("Expected size " + n3 + " got " + n2 + " (0x" + Integer.toHexString(n2) + ")", parcel);
        }
    }

    private static void zza(Parcel parcel, int n2, int n3, int n4) {
        if (n3 != n4) {
            throw new zza("Expected size " + n4 + " got " + n3 + " (0x" + Integer.toHexString(n3) + ")", parcel);
        }
    }

    public static void zza(Parcel parcel, int n2, List list, ClassLoader classLoader) {
        n2 = zza.zza(parcel, n2);
        int n3 = parcel.dataPosition();
        if (n2 == 0) {
            return;
        }
        parcel.readList(list, classLoader);
        parcel.setDataPosition(n2 + n3);
    }

    public static int zzat(Parcel parcel) {
        return parcel.readInt();
    }

    public static int zzau(Parcel parcel) {
        int n2 = zza.zzat(parcel);
        int n3 = zza.zza(parcel, n2);
        int n4 = parcel.dataPosition();
        if (zza.zzca(n2) != 20293) {
            throw new zza("Expected object header. Got 0x" + Integer.toHexString(n2), parcel);
        }
        n2 = n4 + n3;
        if (n2 < n4 || n2 > parcel.dataSize()) {
            throw new zza("Size read is invalid start=" + n4 + " end=" + n2, parcel);
        }
        return n2;
    }

    public static void zzb(Parcel parcel, int n2) {
        parcel.setDataPosition(zza.zza(parcel, n2) + parcel.dataPosition());
    }

    public static <T> T[] zzb(Parcel parcel, int n2, Parcelable.Creator<T> objectArray) {
        n2 = zza.zza(parcel, n2);
        int n3 = parcel.dataPosition();
        if (n2 == 0) {
            return null;
        }
        objectArray = parcel.createTypedArray(objectArray);
        parcel.setDataPosition(n2 + n3);
        return objectArray;
    }

    public static <T> ArrayList<T> zzc(Parcel parcel, int n2, Parcelable.Creator<T> object) {
        n2 = zza.zza(parcel, n2);
        int n3 = parcel.dataPosition();
        if (n2 == 0) {
            return null;
        }
        object = parcel.createTypedArrayList(object);
        parcel.setDataPosition(n2 + n3);
        return object;
    }

    public static boolean zzc(Parcel parcel, int n2) {
        zza.zza(parcel, n2, 4);
        return parcel.readInt() != 0;
    }

    public static int zzca(int n2) {
        return 0xFFFF & n2;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static Boolean zzd(Parcel parcel, int n2) {
        boolean bl2;
        int n3 = zza.zza(parcel, n2);
        if (n3 == 0) {
            return null;
        }
        zza.zza(parcel, n2, n3, 4);
        if (parcel.readInt() != 0) {
            bl2 = true;
            return bl2;
        }
        bl2 = false;
        return bl2;
    }

    public static byte zze(Parcel parcel, int n2) {
        zza.zza(parcel, n2, 4);
        return (byte)parcel.readInt();
    }

    public static short zzf(Parcel parcel, int n2) {
        zza.zza(parcel, n2, 4);
        return (short)parcel.readInt();
    }

    public static int zzg(Parcel parcel, int n2) {
        zza.zza(parcel, n2, 4);
        return parcel.readInt();
    }

    public static Integer zzh(Parcel parcel, int n2) {
        int n3 = zza.zza(parcel, n2);
        if (n3 == 0) {
            return null;
        }
        zza.zza(parcel, n2, n3, 4);
        return parcel.readInt();
    }

    public static long zzi(Parcel parcel, int n2) {
        zza.zza(parcel, n2, 8);
        return parcel.readLong();
    }

    public static Long zzj(Parcel parcel, int n2) {
        int n3 = zza.zza(parcel, n2);
        if (n3 == 0) {
            return null;
        }
        zza.zza(parcel, n2, n3, 8);
        return parcel.readLong();
    }

    public static BigInteger zzk(Parcel parcel, int n2) {
        n2 = zza.zza(parcel, n2);
        int n3 = parcel.dataPosition();
        if (n2 == 0) {
            return null;
        }
        byte[] byArray = parcel.createByteArray();
        parcel.setDataPosition(n2 + n3);
        return new BigInteger(byArray);
    }

    public static float zzl(Parcel parcel, int n2) {
        zza.zza(parcel, n2, 4);
        return parcel.readFloat();
    }

    public static Float zzm(Parcel parcel, int n2) {
        int n3 = zza.zza(parcel, n2);
        if (n3 == 0) {
            return null;
        }
        zza.zza(parcel, n2, n3, 4);
        return Float.valueOf(parcel.readFloat());
    }

    public static double zzn(Parcel parcel, int n2) {
        zza.zza(parcel, n2, 8);
        return parcel.readDouble();
    }

    public static BigDecimal zzo(Parcel parcel, int n2) {
        n2 = zza.zza(parcel, n2);
        int n3 = parcel.dataPosition();
        if (n2 == 0) {
            return null;
        }
        byte[] byArray = parcel.createByteArray();
        int n4 = parcel.readInt();
        parcel.setDataPosition(n2 + n3);
        return new BigDecimal(new BigInteger(byArray), n4);
    }

    public static String zzp(Parcel parcel, int n2) {
        n2 = zza.zza(parcel, n2);
        int n3 = parcel.dataPosition();
        if (n2 == 0) {
            return null;
        }
        String string2 = parcel.readString();
        parcel.setDataPosition(n2 + n3);
        return string2;
    }

    public static IBinder zzq(Parcel parcel, int n2) {
        n2 = zza.zza(parcel, n2);
        int n3 = parcel.dataPosition();
        if (n2 == 0) {
            return null;
        }
        IBinder iBinder = parcel.readStrongBinder();
        parcel.setDataPosition(n2 + n3);
        return iBinder;
    }

    public static Bundle zzr(Parcel parcel, int n2) {
        n2 = zza.zza(parcel, n2);
        int n3 = parcel.dataPosition();
        if (n2 == 0) {
            return null;
        }
        Bundle bundle = parcel.readBundle();
        parcel.setDataPosition(n2 + n3);
        return bundle;
    }

    public static byte[] zzs(Parcel parcel, int n2) {
        n2 = zza.zza(parcel, n2);
        int n3 = parcel.dataPosition();
        if (n2 == 0) {
            return null;
        }
        byte[] byArray = parcel.createByteArray();
        parcel.setDataPosition(n2 + n3);
        return byArray;
    }

    public static byte[][] zzt(Parcel parcel, int n2) {
        int n3 = zza.zza(parcel, n2);
        int n4 = parcel.dataPosition();
        if (n3 == 0) {
            return null;
        }
        int n5 = parcel.readInt();
        byte[][] byArrayArray = new byte[n5][];
        for (n2 = 0; n2 < n5; ++n2) {
            byArrayArray[n2] = parcel.createByteArray();
        }
        parcel.setDataPosition(n4 + n3);
        return byArrayArray;
    }

    public static boolean[] zzu(Parcel parcel, int n2) {
        n2 = zza.zza(parcel, n2);
        int n3 = parcel.dataPosition();
        if (n2 == 0) {
            return null;
        }
        boolean[] blArray = parcel.createBooleanArray();
        parcel.setDataPosition(n2 + n3);
        return blArray;
    }

    public static int[] zzv(Parcel parcel, int n2) {
        n2 = zza.zza(parcel, n2);
        int n3 = parcel.dataPosition();
        if (n2 == 0) {
            return null;
        }
        int[] nArray = parcel.createIntArray();
        parcel.setDataPosition(n2 + n3);
        return nArray;
    }

    public static long[] zzw(Parcel parcel, int n2) {
        n2 = zza.zza(parcel, n2);
        int n3 = parcel.dataPosition();
        if (n2 == 0) {
            return null;
        }
        long[] lArray = parcel.createLongArray();
        parcel.setDataPosition(n2 + n3);
        return lArray;
    }

    public static BigInteger[] zzx(Parcel parcel, int n2) {
        int n3 = zza.zza(parcel, n2);
        int n4 = parcel.dataPosition();
        if (n3 == 0) {
            return null;
        }
        int n5 = parcel.readInt();
        BigInteger[] bigIntegerArray = new BigInteger[n5];
        for (n2 = 0; n2 < n5; ++n2) {
            bigIntegerArray[n2] = new BigInteger(parcel.createByteArray());
        }
        parcel.setDataPosition(n4 + n3);
        return bigIntegerArray;
    }

    public static float[] zzy(Parcel parcel, int n2) {
        n2 = zza.zza(parcel, n2);
        int n3 = parcel.dataPosition();
        if (n2 == 0) {
            return null;
        }
        float[] fArray = parcel.createFloatArray();
        parcel.setDataPosition(n2 + n3);
        return fArray;
    }

    public static double[] zzz(Parcel parcel, int n2) {
        n2 = zza.zza(parcel, n2);
        int n3 = parcel.dataPosition();
        if (n2 == 0) {
            return null;
        }
        double[] dArray = parcel.createDoubleArray();
        parcel.setDataPosition(n2 + n3);
        return dArray;
    }

    public static class zza
    extends RuntimeException {
        public zza(String string2, Parcel parcel) {
            super(string2 + " Parcel: pos=" + parcel.dataPosition() + " size=" + parcel.dataSize());
        }
    }
}

