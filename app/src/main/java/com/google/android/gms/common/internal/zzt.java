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
import com.google.android.gms.common.internal.ResolveAccountResponse;

public interface zzt
extends IInterface {
    public void zza(ResolveAccountResponse var1) throws RemoteException;

    public static abstract class com.google.android.gms.common.internal.zzt$zza
    extends Binder
    implements zzt {
        public static zzt zzaT(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface("com.google.android.gms.common.internal.IResolveAccountCallbacks");
            if (iInterface != null && iInterface instanceof zzt) {
                return (zzt)iInterface;
            }
            return new zza(iBinder);
        }

        /*
         * WARNING - void declaration
         * Enabled aggressive block sorting
         */
        public boolean onTransact(int n2, Parcel object, Parcel parcel, int n3) throws RemoteException {
            void var2_4;
            void var3_6;
            switch (n2) {
                default: {
                    void var4_7;
                    return super.onTransact(n2, object, (Parcel)var3_6, (int)var4_7);
                }
                case 1598968902: {
                    var3_6.writeString("com.google.android.gms.common.internal.IResolveAccountCallbacks");
                    return true;
                }
                case 2: 
            }
            object.enforceInterface("com.google.android.gms.common.internal.IResolveAccountCallbacks");
            if (object.readInt() != 0) {
                ResolveAccountResponse resolveAccountResponse = (ResolveAccountResponse)ResolveAccountResponse.CREATOR.createFromParcel(object);
            } else {
                Object var2_5 = null;
            }
            this.zza((ResolveAccountResponse)var2_4);
            var3_6.writeNoException();
            return true;
        }

        private static class zza
        implements zzt {
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
            public void zza(ResolveAccountResponse resolveAccountResponse) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.common.internal.IResolveAccountCallbacks");
                    if (resolveAccountResponse != null) {
                        parcel.writeInt(1);
                        resolveAccountResponse.writeToParcel(parcel, 0);
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

