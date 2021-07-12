/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.os.Binder
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.Parcel
 *  android.os.RemoteException
 */
package com.google.android.gms.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.dynamic.zzd;

public interface zzpk
extends IInterface {
    public boolean getBooleanFlagValue(String var1, boolean var2, int var3) throws RemoteException;

    public int getIntFlagValue(String var1, int var2, int var3) throws RemoteException;

    public long getLongFlagValue(String var1, long var2, int var4) throws RemoteException;

    public String getStringFlagValue(String var1, String var2, int var3) throws RemoteException;

    public void init(zzd var1) throws RemoteException;

    public static abstract class com.google.android.gms.internal.zzpk$zza
    extends Binder
    implements zzpk {
        public com.google.android.gms.internal.zzpk$zza() {
            this.attachInterface(this, "com.google.android.gms.flags.IFlagProvider");
        }

        public static zzpk asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface("com.google.android.gms.flags.IFlagProvider");
            if (iInterface != null && iInterface instanceof zzpk) {
                return (zzpk)iInterface;
            }
            return new zza(iBinder);
        }

        public IBinder asBinder() {
            return this;
        }

        /*
         * WARNING - void declaration
         * Enabled aggressive block sorting
         */
        public boolean onTransact(int n2, Parcel object, Parcel parcel, int n3) throws RemoteException {
            void var3_4;
            int n4 = 0;
            switch (n2) {
                default: {
                    void var4_5;
                    return super.onTransact(n2, object, (Parcel)var3_4, (int)var4_5);
                }
                case 1598968902: {
                    var3_4.writeString("com.google.android.gms.flags.IFlagProvider");
                    return true;
                }
                case 1: {
                    object.enforceInterface("com.google.android.gms.flags.IFlagProvider");
                    this.init(zzd.zza.zzbs(object.readStrongBinder()));
                    var3_4.writeNoException();
                    return true;
                }
                case 2: {
                    object.enforceInterface("com.google.android.gms.flags.IFlagProvider");
                    String string2 = object.readString();
                    boolean bl2 = object.readInt() != 0;
                    bl2 = this.getBooleanFlagValue(string2, bl2, object.readInt());
                    var3_4.writeNoException();
                    n2 = n4;
                    if (bl2) {
                        n2 = 1;
                    }
                    var3_4.writeInt(n2);
                    return true;
                }
                case 3: {
                    object.enforceInterface("com.google.android.gms.flags.IFlagProvider");
                    n2 = this.getIntFlagValue(object.readString(), object.readInt(), object.readInt());
                    var3_4.writeNoException();
                    var3_4.writeInt(n2);
                    return true;
                }
                case 4: {
                    object.enforceInterface("com.google.android.gms.flags.IFlagProvider");
                    long l2 = this.getLongFlagValue(object.readString(), object.readLong(), object.readInt());
                    var3_4.writeNoException();
                    var3_4.writeLong(l2);
                    return true;
                }
                case 5: 
            }
            object.enforceInterface("com.google.android.gms.flags.IFlagProvider");
            String string3 = this.getStringFlagValue(object.readString(), object.readString(), object.readInt());
            var3_4.writeNoException();
            var3_4.writeString(string3);
            return true;
        }

        private static class zza
        implements zzpk {
            private IBinder zzoz;

            zza(IBinder iBinder) {
                this.zzoz = iBinder;
            }

            public IBinder asBinder() {
                return this.zzoz;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public boolean getBooleanFlagValue(String string2, boolean bl2, int n2) throws RemoteException {
                boolean bl3 = true;
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.flags.IFlagProvider");
                    parcel.writeString(string2);
                    int n3 = bl2 ? 1 : 0;
                    parcel.writeInt(n3);
                    parcel.writeInt(n2);
                    this.zzoz.transact(2, parcel, parcel2, 0);
                    parcel2.readException();
                    n2 = parcel2.readInt();
                    bl2 = n2 != 0 ? bl3 : false;
                    return bl2;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int getIntFlagValue(String string2, int n2, int n3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.flags.IFlagProvider");
                    parcel.writeString(string2);
                    parcel.writeInt(n2);
                    parcel.writeInt(n3);
                    this.zzoz.transact(3, parcel, parcel2, 0);
                    parcel2.readException();
                    n2 = parcel2.readInt();
                    return n2;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public long getLongFlagValue(String string2, long l2, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.flags.IFlagProvider");
                    parcel.writeString(string2);
                    parcel.writeLong(l2);
                    parcel.writeInt(n2);
                    this.zzoz.transact(4, parcel, parcel2, 0);
                    parcel2.readException();
                    l2 = parcel2.readLong();
                    return l2;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public String getStringFlagValue(String string2, String string3, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.flags.IFlagProvider");
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    parcel.writeInt(n2);
                    this.zzoz.transact(5, parcel, parcel2, 0);
                    parcel2.readException();
                    string2 = parcel2.readString();
                    return string2;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void init(zzd zzd2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.flags.IFlagProvider");
                    zzd2 = zzd2 != null ? zzd2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)zzd2);
                    this.zzoz.transact(1, parcel, parcel2, 0);
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }
        }
    }
}

