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
package com.google.android.gms.wearable.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface zzau
extends IInterface {
    public void zzy(int var1, int var2) throws RemoteException;

    public static abstract class com.google.android.gms.wearable.internal.zzau$zza
    extends Binder
    implements zzau {
        public com.google.android.gms.wearable.internal.zzau$zza() {
            this.attachInterface(this, "com.google.android.gms.wearable.internal.IChannelStreamCallbacks");
        }

        public static zzau zzer(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface("com.google.android.gms.wearable.internal.IChannelStreamCallbacks");
            if (iInterface != null && iInterface instanceof zzau) {
                return (zzau)iInterface;
            }
            return new zza(iBinder);
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int n2, Parcel parcel, Parcel parcel2, int n3) throws RemoteException {
            switch (n2) {
                default: {
                    return super.onTransact(n2, parcel, parcel2, n3);
                }
                case 1598968902: {
                    parcel2.writeString("com.google.android.gms.wearable.internal.IChannelStreamCallbacks");
                    return true;
                }
                case 2: 
            }
            parcel.enforceInterface("com.google.android.gms.wearable.internal.IChannelStreamCallbacks");
            this.zzy(parcel.readInt(), parcel.readInt());
            parcel2.writeNoException();
            return true;
        }

        private static class zza
        implements zzau {
            private IBinder zzoz;

            zza(IBinder iBinder) {
                this.zzoz = iBinder;
            }

            public IBinder asBinder() {
                return this.zzoz;
            }

            @Override
            public void zzy(int n2, int n3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.wearable.internal.IChannelStreamCallbacks");
                    parcel.writeInt(n2);
                    parcel.writeInt(n3);
                    this.zzoz.transact(2, parcel, parcel2, 0);
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

