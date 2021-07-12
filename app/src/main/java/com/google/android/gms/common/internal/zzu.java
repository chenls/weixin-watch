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
import com.google.android.gms.common.internal.SignInButtonConfig;
import com.google.android.gms.dynamic.zzd;

public interface zzu
extends IInterface {
    public zzd zza(zzd var1, int var2, int var3) throws RemoteException;

    public zzd zza(zzd var1, SignInButtonConfig var2) throws RemoteException;

    public static abstract class com.google.android.gms.common.internal.zzu$zza
    extends Binder
    implements zzu {
        public static zzu zzaU(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface("com.google.android.gms.common.internal.ISignInButtonCreator");
            if (iInterface != null && iInterface instanceof zzu) {
                return (zzu)iInterface;
            }
            return new zza(iBinder);
        }

        /*
         * WARNING - void declaration
         * Enabled aggressive block sorting
         */
        public boolean onTransact(int n2, Parcel object, Parcel parcel, int n3) throws RemoteException {
            void var2_11;
            void var2_8;
            void var3_13;
            Object var5_15 = null;
            switch (n2) {
                default: {
                    void var4_14;
                    return super.onTransact(n2, object, (Parcel)var3_13, (int)var4_14);
                }
                case 1598968902: {
                    var3_13.writeString("com.google.android.gms.common.internal.ISignInButtonCreator");
                    return true;
                }
                case 1: {
                    void var2_5;
                    object.enforceInterface("com.google.android.gms.common.internal.ISignInButtonCreator");
                    zzd zzd2 = this.zza(zzd.zza.zzbs(object.readStrongBinder()), object.readInt(), object.readInt());
                    var3_13.writeNoException();
                    if (zzd2 != null) {
                        IBinder iBinder = zzd2.asBinder();
                    } else {
                        Object var2_6 = null;
                    }
                    var3_13.writeStrongBinder((IBinder)var2_5);
                    return true;
                }
                case 2: 
            }
            object.enforceInterface("com.google.android.gms.common.internal.ISignInButtonCreator");
            zzd zzd3 = zzd.zza.zzbs(object.readStrongBinder());
            if (object.readInt() != 0) {
                SignInButtonConfig signInButtonConfig = (SignInButtonConfig)SignInButtonConfig.CREATOR.createFromParcel(object);
            } else {
                Object var2_12 = null;
            }
            zzd3 = this.zza(zzd3, (SignInButtonConfig)var2_8);
            var3_13.writeNoException();
            Object var2_9 = var5_15;
            if (zzd3 != null) {
                IBinder iBinder = zzd3.asBinder();
            }
            var3_13.writeStrongBinder((IBinder)var2_11);
            return true;
        }

        private static class zza
        implements zzu {
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
            public zzd zza(zzd zzd2, int n2, int n3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.common.internal.ISignInButtonCreator");
                    zzd2 = zzd2 != null ? zzd2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)zzd2);
                    parcel.writeInt(n2);
                    parcel.writeInt(n3);
                    this.zzoz.transact(1, parcel, parcel2, 0);
                    parcel2.readException();
                    zzd2 = zzd.zza.zzbs(parcel2.readStrongBinder());
                    return zzd2;
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
            public zzd zza(zzd zzd2, SignInButtonConfig signInButtonConfig) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.common.internal.ISignInButtonCreator");
                    zzd2 = zzd2 != null ? zzd2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)zzd2);
                    if (signInButtonConfig != null) {
                        parcel.writeInt(1);
                        signInButtonConfig.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.zzoz.transact(2, parcel, parcel2, 0);
                    parcel2.readException();
                    zzd2 = zzd.zza.zzbs(parcel2.readStrongBinder());
                    return zzd2;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }
        }
    }
}

