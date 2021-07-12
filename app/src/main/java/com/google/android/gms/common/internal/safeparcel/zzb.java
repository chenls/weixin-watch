/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.os.IBinder
 *  android.os.Parcel
 *  android.os.Parcelable
 */
package com.google.android.gms.common.internal.safeparcel;

import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.List;

public class zzb {
    private static int zzG(Parcel parcel, int n2) {
        parcel.writeInt(0xFFFF0000 | n2);
        parcel.writeInt(0);
        return parcel.dataPosition();
    }

    private static void zzH(Parcel parcel, int n2) {
        int n3 = parcel.dataPosition();
        parcel.setDataPosition(n2 - 4);
        parcel.writeInt(n3 - n2);
        parcel.setDataPosition(n3);
    }

    public static void zzI(Parcel parcel, int n2) {
        zzb.zzH(parcel, n2);
    }

    public static void zza(Parcel parcel, int n2, byte by2) {
        zzb.zzb(parcel, n2, 4);
        parcel.writeInt((int)by2);
    }

    public static void zza(Parcel parcel, int n2, double d2) {
        zzb.zzb(parcel, n2, 8);
        parcel.writeDouble(d2);
    }

    public static void zza(Parcel parcel, int n2, float f2) {
        zzb.zzb(parcel, n2, 4);
        parcel.writeFloat(f2);
    }

    public static void zza(Parcel parcel, int n2, long l2) {
        zzb.zzb(parcel, n2, 8);
        parcel.writeLong(l2);
    }

    public static void zza(Parcel parcel, int n2, Bundle bundle, boolean bl2) {
        if (bundle == null) {
            if (bl2) {
                zzb.zzb(parcel, n2, 0);
            }
            return;
        }
        n2 = zzb.zzG(parcel, n2);
        parcel.writeBundle(bundle);
        zzb.zzH(parcel, n2);
    }

    public static void zza(Parcel parcel, int n2, IBinder iBinder, boolean bl2) {
        if (iBinder == null) {
            if (bl2) {
                zzb.zzb(parcel, n2, 0);
            }
            return;
        }
        n2 = zzb.zzG(parcel, n2);
        parcel.writeStrongBinder(iBinder);
        zzb.zzH(parcel, n2);
    }

    public static void zza(Parcel parcel, int n2, Parcel parcel2, boolean bl2) {
        if (parcel2 == null) {
            if (bl2) {
                zzb.zzb(parcel, n2, 0);
            }
            return;
        }
        n2 = zzb.zzG(parcel, n2);
        parcel.appendFrom(parcel2, 0, parcel2.dataSize());
        zzb.zzH(parcel, n2);
    }

    public static void zza(Parcel parcel, int n2, Parcelable parcelable, int n3, boolean bl2) {
        if (parcelable == null) {
            if (bl2) {
                zzb.zzb(parcel, n2, 0);
            }
            return;
        }
        n2 = zzb.zzG(parcel, n2);
        parcelable.writeToParcel(parcel, n3);
        zzb.zzH(parcel, n2);
    }

    public static void zza(Parcel parcel, int n2, Boolean bl2, boolean bl3) {
        int n3 = 0;
        if (bl2 == null) {
            if (bl3) {
                zzb.zzb(parcel, n2, 0);
            }
            return;
        }
        zzb.zzb(parcel, n2, 4);
        n2 = n3;
        if (bl2.booleanValue()) {
            n2 = 1;
        }
        parcel.writeInt(n2);
    }

    public static void zza(Parcel parcel, int n2, Float f2, boolean bl2) {
        if (f2 == null) {
            if (bl2) {
                zzb.zzb(parcel, n2, 0);
            }
            return;
        }
        zzb.zzb(parcel, n2, 4);
        parcel.writeFloat(f2.floatValue());
    }

    public static void zza(Parcel parcel, int n2, Integer n3, boolean bl2) {
        if (n3 == null) {
            if (bl2) {
                zzb.zzb(parcel, n2, 0);
            }
            return;
        }
        zzb.zzb(parcel, n2, 4);
        parcel.writeInt(n3.intValue());
    }

    public static void zza(Parcel parcel, int n2, Long l2, boolean bl2) {
        if (l2 == null) {
            if (bl2) {
                zzb.zzb(parcel, n2, 0);
            }
            return;
        }
        zzb.zzb(parcel, n2, 8);
        parcel.writeLong(l2.longValue());
    }

    public static void zza(Parcel parcel, int n2, String string2, boolean bl2) {
        if (string2 == null) {
            if (bl2) {
                zzb.zzb(parcel, n2, 0);
            }
            return;
        }
        n2 = zzb.zzG(parcel, n2);
        parcel.writeString(string2);
        zzb.zzH(parcel, n2);
    }

    public static void zza(Parcel parcel, int n2, List<Integer> list, boolean bl2) {
        if (list == null) {
            if (bl2) {
                zzb.zzb(parcel, n2, 0);
            }
            return;
        }
        int n3 = zzb.zzG(parcel, n2);
        int n4 = list.size();
        parcel.writeInt(n4);
        for (n2 = 0; n2 < n4; ++n2) {
            parcel.writeInt(list.get(n2).intValue());
        }
        zzb.zzH(parcel, n3);
    }

    public static void zza(Parcel parcel, int n2, short s2) {
        zzb.zzb(parcel, n2, 4);
        parcel.writeInt((int)s2);
    }

    /*
     * Enabled aggressive block sorting
     */
    public static void zza(Parcel parcel, int n2, boolean bl2) {
        zzb.zzb(parcel, n2, 4);
        n2 = bl2 ? 1 : 0;
        parcel.writeInt(n2);
    }

    public static void zza(Parcel parcel, int n2, byte[] byArray, boolean bl2) {
        if (byArray == null) {
            if (bl2) {
                zzb.zzb(parcel, n2, 0);
            }
            return;
        }
        n2 = zzb.zzG(parcel, n2);
        parcel.writeByteArray(byArray);
        zzb.zzH(parcel, n2);
    }

    public static void zza(Parcel parcel, int n2, float[] fArray, boolean bl2) {
        if (fArray == null) {
            if (bl2) {
                zzb.zzb(parcel, n2, 0);
            }
            return;
        }
        n2 = zzb.zzG(parcel, n2);
        parcel.writeFloatArray(fArray);
        zzb.zzH(parcel, n2);
    }

    public static void zza(Parcel parcel, int n2, int[] nArray, boolean bl2) {
        if (nArray == null) {
            if (bl2) {
                zzb.zzb(parcel, n2, 0);
            }
            return;
        }
        n2 = zzb.zzG(parcel, n2);
        parcel.writeIntArray(nArray);
        zzb.zzH(parcel, n2);
    }

    /*
     * Enabled aggressive block sorting
     */
    public static <T extends Parcelable> void zza(Parcel parcel, int n2, T[] TArray, int n3, boolean bl2) {
        if (TArray == null) {
            if (bl2) {
                zzb.zzb(parcel, n2, 0);
            }
            return;
        }
        int n4 = zzb.zzG(parcel, n2);
        int n5 = TArray.length;
        parcel.writeInt(n5);
        n2 = 0;
        while (true) {
            if (n2 >= n5) {
                zzb.zzH(parcel, n4);
                return;
            }
            T t2 = TArray[n2];
            if (t2 == null) {
                parcel.writeInt(0);
            } else {
                zzb.zza(parcel, t2, n3);
            }
            ++n2;
        }
    }

    public static void zza(Parcel parcel, int n2, String[] stringArray, boolean bl2) {
        if (stringArray == null) {
            if (bl2) {
                zzb.zzb(parcel, n2, 0);
            }
            return;
        }
        n2 = zzb.zzG(parcel, n2);
        parcel.writeStringArray(stringArray);
        zzb.zzH(parcel, n2);
    }

    public static void zza(Parcel parcel, int n2, boolean[] blArray, boolean bl2) {
        if (blArray == null) {
            if (bl2) {
                zzb.zzb(parcel, n2, 0);
            }
            return;
        }
        n2 = zzb.zzG(parcel, n2);
        parcel.writeBooleanArray(blArray);
        zzb.zzH(parcel, n2);
    }

    public static void zza(Parcel parcel, int n2, byte[][] byArray, boolean bl2) {
        int n3 = 0;
        if (byArray == null) {
            if (bl2) {
                zzb.zzb(parcel, n2, 0);
            }
            return;
        }
        int n4 = zzb.zzG(parcel, n2);
        int n5 = byArray.length;
        parcel.writeInt(n5);
        for (n2 = n3; n2 < n5; ++n2) {
            parcel.writeByteArray(byArray[n2]);
        }
        zzb.zzH(parcel, n4);
    }

    private static <T extends Parcelable> void zza(Parcel parcel, T t2, int n2) {
        int n3 = parcel.dataPosition();
        parcel.writeInt(1);
        int n4 = parcel.dataPosition();
        t2.writeToParcel(parcel, n2);
        n2 = parcel.dataPosition();
        parcel.setDataPosition(n3);
        parcel.writeInt(n2 - n4);
        parcel.setDataPosition(n2);
    }

    public static int zzav(Parcel parcel) {
        return zzb.zzG(parcel, 20293);
    }

    private static void zzb(Parcel parcel, int n2, int n3) {
        if (n3 >= 65535) {
            parcel.writeInt(0xFFFF0000 | n2);
            parcel.writeInt(n3);
            return;
        }
        parcel.writeInt(n3 << 16 | n2);
    }

    public static void zzb(Parcel parcel, int n2, List<String> list, boolean bl2) {
        if (list == null) {
            if (bl2) {
                zzb.zzb(parcel, n2, 0);
            }
            return;
        }
        n2 = zzb.zzG(parcel, n2);
        parcel.writeStringList(list);
        zzb.zzH(parcel, n2);
    }

    public static void zzc(Parcel parcel, int n2, int n3) {
        zzb.zzb(parcel, n2, 4);
        parcel.writeInt(n3);
    }

    /*
     * Enabled aggressive block sorting
     */
    public static <T extends Parcelable> void zzc(Parcel parcel, int n2, List<T> list, boolean bl2) {
        if (list == null) {
            if (bl2) {
                zzb.zzb(parcel, n2, 0);
            }
            return;
        }
        int n3 = zzb.zzG(parcel, n2);
        int n4 = list.size();
        parcel.writeInt(n4);
        n2 = 0;
        while (true) {
            if (n2 >= n4) {
                zzb.zzH(parcel, n3);
                return;
            }
            Parcelable parcelable = (Parcelable)list.get(n2);
            if (parcelable == null) {
                parcel.writeInt(0);
            } else {
                zzb.zza(parcel, parcelable, 0);
            }
            ++n2;
        }
    }

    public static void zzd(Parcel parcel, int n2, List list, boolean bl2) {
        if (list == null) {
            if (bl2) {
                zzb.zzb(parcel, n2, 0);
            }
            return;
        }
        n2 = zzb.zzG(parcel, n2);
        parcel.writeList(list);
        zzb.zzH(parcel, n2);
    }
}

