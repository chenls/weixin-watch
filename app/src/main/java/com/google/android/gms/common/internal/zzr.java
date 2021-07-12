/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.os.Binder
 *  android.os.Bundle
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.Parcel
 *  android.os.RemoteException
 */
package com.google.android.gms.common.internal;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface zzr
extends IInterface {
    public void zza(int var1, IBinder var2, Bundle var3) throws RemoteException;

    public void zzb(int var1, Bundle var2) throws RemoteException;

    public static abstract class com.google.android.gms.common.internal.zzr$zza
    extends Binder
    implements zzr {
        public com.google.android.gms.common.internal.zzr$zza() {
            this.attachInterface(this, "com.google.android.gms.common.internal.IGmsCallbacks");
        }

        public static zzr zzaR(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface("com.google.android.gms.common.internal.IGmsCallbacks");
            if (iInterface != null && iInterface instanceof zzr) {
                return (zzr)iInterface;
            }
            return new zza(iBinder);
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int n2, Parcel parcel, Parcel parcel2, int n3) throws RemoteException {
            IBinder iBinder = null;
            IBinder iBinder2 = null;
            switch (n2) {
                default: {
                    return super.onTransact(n2, parcel, parcel2, n3);
                }
                case 1598968902: {
                    parcel2.writeString("com.google.android.gms.common.internal.IGmsCallbacks");
                    return true;
                }
                case 1: {
                    parcel.enforceInterface("com.google.android.gms.common.internal.IGmsCallbacks");
                    n2 = parcel.readInt();
                    iBinder = parcel.readStrongBinder();
                    if (parcel.readInt() != 0) {
                        iBinder2 = (Bundle)Bundle.CREATOR.createFromParcel(parcel);
                    }
                    this.zza(n2, iBinder, (Bundle)iBinder2);
                    parcel2.writeNoException();
                    return true;
                }
                case 2: 
            }
            parcel.enforceInterface("com.google.android.gms.common.internal.IGmsCallbacks");
            n2 = parcel.readInt();
            iBinder2 = iBinder;
            if (parcel.readInt() != 0) {
                iBinder2 = (Bundle)Bundle.CREATOR.createFromParcel(parcel);
            }
            this.zzb(n2, (Bundle)iBinder2);
            parcel2.writeNoException();
            return true;
        }

        private static class zza
        implements zzr {
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
            public void zza(int n2, IBinder iBinder, Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.common.internal.IGmsCallbacks");
                    parcel.writeInt(n2);
                    parcel.writeStrongBinder(iBinder);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.zzoz.transact(1, parcel, parcel2, 0);
                    parcel2.readException();
                    return;
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
            public void zzb(int n2, Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.common.internal.IGmsCallbacks");
                    parcel.writeInt(n2);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
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

