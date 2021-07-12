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
import com.google.android.gms.clearcut.LogEventParcelable;
import com.google.android.gms.internal.zzlx;

public interface zzly
extends IInterface {
    public void zza(zzlx var1, LogEventParcelable var2) throws RemoteException;

    public static abstract class com.google.android.gms.internal.zzly$zza
    extends Binder
    implements zzly {
        public static zzly zzaM(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface("com.google.android.gms.clearcut.internal.IClearcutLoggerService");
            if (iInterface != null && iInterface instanceof zzly) {
                return (zzly)iInterface;
            }
            return new zza(iBinder);
        }

        /*
         * WARNING - void declaration
         * Enabled aggressive block sorting
         */
        public boolean onTransact(int n2, Parcel object, Parcel object2, int n3) throws RemoteException {
            void var2_4;
            switch (n2) {
                default: {
                    void var4_8;
                    void var3_6;
                    return super.onTransact(n2, object, (Parcel)var3_6, (int)var4_8);
                }
                case 1598968902: {
                    void var3_6;
                    var3_6.writeString("com.google.android.gms.clearcut.internal.IClearcutLoggerService");
                    return true;
                }
                case 1: 
            }
            object.enforceInterface("com.google.android.gms.clearcut.internal.IClearcutLoggerService");
            zzlx zzlx2 = zzlx.zza.zzaL(object.readStrongBinder());
            if (object.readInt() != 0) {
                LogEventParcelable logEventParcelable = LogEventParcelable.CREATOR.zzaf((Parcel)object);
            } else {
                Object var2_5 = null;
            }
            this.zza(zzlx2, (LogEventParcelable)var2_4);
            return true;
        }

        private static class zza
        implements zzly {
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
            public void zza(zzlx zzlx2, LogEventParcelable logEventParcelable) throws RemoteException {
                IBinder iBinder = null;
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.clearcut.internal.IClearcutLoggerService");
                    if (zzlx2 != null) {
                        iBinder = zzlx2.asBinder();
                    }
                    parcel.writeStrongBinder(iBinder);
                    if (logEventParcelable != null) {
                        parcel.writeInt(1);
                        logEventParcelable.writeToParcel(parcel, 0);
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

