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

public interface zzat
extends IInterface {
    public String getId() throws RemoteException;

    public void zzb(String var1, boolean var2) throws RemoteException;

    public boolean zzc(boolean var1) throws RemoteException;

    public String zzo(String var1) throws RemoteException;

    public static abstract class com.google.android.gms.internal.zzat$zza
    extends Binder
    implements zzat {
        public static zzat zzb(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface("com.google.android.gms.ads.identifier.internal.IAdvertisingIdService");
            if (iInterface != null && iInterface instanceof zzat) {
                return (zzat)iInterface;
            }
            return new zza(iBinder);
        }

        /*
         * WARNING - void declaration
         * Enabled aggressive block sorting
         */
        public boolean onTransact(int n2, Parcel object, Parcel parcel, int n3) throws RemoteException {
            void var3_5;
            boolean bl2 = false;
            int n4 = 0;
            switch (n2) {
                default: {
                    void var4_6;
                    return super.onTransact(n2, object, (Parcel)var3_5, (int)var4_6);
                }
                case 1598968902: {
                    var3_5.writeString("com.google.android.gms.ads.identifier.internal.IAdvertisingIdService");
                    return true;
                }
                case 1: {
                    object.enforceInterface("com.google.android.gms.ads.identifier.internal.IAdvertisingIdService");
                    String string2 = this.getId();
                    var3_5.writeNoException();
                    var3_5.writeString(string2);
                    return true;
                }
                case 2: {
                    object.enforceInterface("com.google.android.gms.ads.identifier.internal.IAdvertisingIdService");
                    bl2 = object.readInt() != 0;
                    bl2 = this.zzc(bl2);
                    var3_5.writeNoException();
                    n2 = n4;
                    if (bl2) {
                        n2 = 1;
                    }
                    var3_5.writeInt(n2);
                    return true;
                }
                case 3: {
                    object.enforceInterface("com.google.android.gms.ads.identifier.internal.IAdvertisingIdService");
                    String string3 = this.zzo(object.readString());
                    var3_5.writeNoException();
                    var3_5.writeString(string3);
                    return true;
                }
                case 4: 
            }
            object.enforceInterface("com.google.android.gms.ads.identifier.internal.IAdvertisingIdService");
            String string4 = object.readString();
            if (object.readInt() != 0) {
                bl2 = true;
            }
            this.zzb(string4, bl2);
            var3_5.writeNoException();
            return true;
        }

        private static class zza
        implements zzat {
            private IBinder zzoz;

            zza(IBinder iBinder) {
                this.zzoz = iBinder;
            }

            public IBinder asBinder() {
                return this.zzoz;
            }

            @Override
            public String getId() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.ads.identifier.internal.IAdvertisingIdService");
                    this.zzoz.transact(1, parcel, parcel2, 0);
                    parcel2.readException();
                    String string2 = parcel2.readString();
                    return string2;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void zzb(String string2, boolean bl2) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                int n2;
                block4: {
                    n2 = 0;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    parcel2.writeInterfaceToken("com.google.android.gms.ads.identifier.internal.IAdvertisingIdService");
                    parcel2.writeString(string2);
                    if (!bl2) break block4;
                    n2 = 1;
                }
                try {
                    parcel2.writeInt(n2);
                    this.zzoz.transact(4, parcel2, parcel, 0);
                    parcel.readException();
                    return;
                }
                finally {
                    parcel.recycle();
                    parcel2.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public boolean zzc(boolean bl2) throws RemoteException {
                boolean bl3 = true;
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.ads.identifier.internal.IAdvertisingIdService");
                    int n2 = bl2 ? 1 : 0;
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
            public String zzo(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.ads.identifier.internal.IAdvertisingIdService");
                    parcel.writeString(string2);
                    this.zzoz.transact(3, parcel, parcel2, 0);
                    parcel2.readException();
                    string2 = parcel2.readString();
                    return string2;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }
        }
    }
}

