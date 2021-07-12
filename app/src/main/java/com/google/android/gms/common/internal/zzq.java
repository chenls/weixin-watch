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
package com.google.android.gms.common.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface zzq
extends IInterface {
    public void cancel() throws RemoteException;

    public static abstract class com.google.android.gms.common.internal.zzq$zza
    extends Binder
    implements zzq {
        public static zzq zzaQ(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface("com.google.android.gms.common.internal.ICancelToken");
            if (iInterface != null && iInterface instanceof zzq) {
                return (zzq)iInterface;
            }
            return new zza(iBinder);
        }

        public boolean onTransact(int n2, Parcel parcel, Parcel parcel2, int n3) throws RemoteException {
            switch (n2) {
                default: {
                    return super.onTransact(n2, parcel, parcel2, n3);
                }
                case 1598968902: {
                    parcel2.writeString("com.google.android.gms.common.internal.ICancelToken");
                    return true;
                }
                case 2: 
            }
            parcel.enforceInterface("com.google.android.gms.common.internal.ICancelToken");
            this.cancel();
            return true;
        }

        private static class zza
        implements zzq {
            private IBinder zzoz;

            zza(IBinder iBinder) {
                this.zzoz = iBinder;
            }

            public IBinder asBinder() {
                return this.zzoz;
            }

            @Override
            public void cancel() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.common.internal.ICancelToken");
                    this.zzoz.transact(2, parcel, null, 1);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }
        }
    }
}

