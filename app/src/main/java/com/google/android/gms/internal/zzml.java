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
import com.google.android.gms.internal.zzmk;

public interface zzml
extends IInterface {
    public void zza(zzmk var1) throws RemoteException;

    public static abstract class com.google.android.gms.internal.zzml$zza
    extends Binder
    implements zzml {
        public static zzml zzaY(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface("com.google.android.gms.common.internal.service.ICommonService");
            if (iInterface != null && iInterface instanceof zzml) {
                return (zzml)iInterface;
            }
            return new zza(iBinder);
        }

        public boolean onTransact(int n2, Parcel parcel, Parcel parcel2, int n3) throws RemoteException {
            switch (n2) {
                default: {
                    return super.onTransact(n2, parcel, parcel2, n3);
                }
                case 1598968902: {
                    parcel2.writeString("com.google.android.gms.common.internal.service.ICommonService");
                    return true;
                }
                case 1: 
            }
            parcel.enforceInterface("com.google.android.gms.common.internal.service.ICommonService");
            this.zza(zzmk.zza.zzaX(parcel.readStrongBinder()));
            return true;
        }

        private static class zza
        implements zzml {
            private IBinder zzoz;

            zza(IBinder iBinder) {
                this.zzoz = iBinder;
            }

            public IBinder asBinder() {
                return this.zzoz;
            }

            @Override
            public void zza(zzmk zzmk2) throws RemoteException {
                IBinder iBinder = null;
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.common.internal.service.ICommonService");
                    if (zzmk2 != null) {
                        iBinder = zzmk2.asBinder();
                    }
                    parcel.writeStrongBinder(iBinder);
                    this.zzoz.transact(1, parcel, null, 1);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }
        }
    }
}

