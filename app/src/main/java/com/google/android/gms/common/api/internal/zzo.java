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
package com.google.android.gms.common.api.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.common.api.Status;

public interface zzo
extends IInterface {
    public void zzp(Status var1) throws RemoteException;

    public static abstract class com.google.android.gms.common.api.internal.zzo$zza
    extends Binder
    implements zzo {
        public static zzo zzaN(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface("com.google.android.gms.common.api.internal.IStatusCallback");
            if (iInterface != null && iInterface instanceof zzo) {
                return (zzo)iInterface;
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
            void var2_4;
            switch (n2) {
                default: {
                    void var4_7;
                    void var3_6;
                    return super.onTransact(n2, object, (Parcel)var3_6, (int)var4_7);
                }
                case 1598968902: {
                    void var3_6;
                    var3_6.writeString("com.google.android.gms.common.api.internal.IStatusCallback");
                    return true;
                }
                case 1: 
            }
            object.enforceInterface("com.google.android.gms.common.api.internal.IStatusCallback");
            if (object.readInt() != 0) {
                Status status = (Status)Status.CREATOR.createFromParcel(object);
            } else {
                Object var2_5 = null;
            }
            this.zzp((Status)var2_4);
            return true;
        }

        private static class zza
        implements zzo {
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
            public void zzp(Status status) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.common.api.internal.IStatusCallback");
                    if (status != null) {
                        parcel.writeInt(1);
                        status.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
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

