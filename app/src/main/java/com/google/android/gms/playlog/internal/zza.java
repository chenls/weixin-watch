/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.os.Binder
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.Parcel
 *  android.os.Parcelable$Creator
 *  android.os.RemoteException
 */
package com.google.android.gms.playlog.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.playlog.internal.LogEvent;
import com.google.android.gms.playlog.internal.PlayLoggerContext;
import java.util.List;

public interface zza
extends IInterface {
    public void zza(String var1, PlayLoggerContext var2, LogEvent var3) throws RemoteException;

    public void zza(String var1, PlayLoggerContext var2, List<LogEvent> var3) throws RemoteException;

    public void zza(String var1, PlayLoggerContext var2, byte[] var3) throws RemoteException;

    public static abstract class com.google.android.gms.playlog.internal.zza$zza
    extends Binder
    implements com.google.android.gms.playlog.internal.zza {
        public static com.google.android.gms.playlog.internal.zza zzdN(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface("com.google.android.gms.playlog.internal.IPlayLogService");
            if (iInterface != null && iInterface instanceof com.google.android.gms.playlog.internal.zza) {
                return (com.google.android.gms.playlog.internal.zza)iInterface;
            }
            return new zza(iBinder);
        }

        /*
         * WARNING - void declaration
         * Enabled aggressive block sorting
         */
        public boolean onTransact(int n2, Parcel parcel, Parcel object, int n3) throws RemoteException {
            void var3_12;
            String string2 = null;
            Object var7_15 = null;
            Object object2 = null;
            switch (n2) {
                default: {
                    void var4_13;
                    return super.onTransact(n2, parcel, object, (int)var4_13);
                }
                case 1598968902: {
                    object.writeString("com.google.android.gms.playlog.internal.IPlayLogService");
                    return true;
                }
                case 2: {
                    void var3_5;
                    parcel.enforceInterface("com.google.android.gms.playlog.internal.IPlayLogService");
                    string2 = parcel.readString();
                    if (parcel.readInt() != 0) {
                        PlayLoggerContext playLoggerContext = PlayLoggerContext.CREATOR.zzgz(parcel);
                    } else {
                        Object var3_6 = null;
                    }
                    if (parcel.readInt() != 0) {
                        object2 = LogEvent.CREATOR.zzgy(parcel);
                    }
                    this.zza(string2, (PlayLoggerContext)var3_5, (LogEvent)object2);
                    return true;
                }
                case 3: {
                    void var3_9;
                    parcel.enforceInterface("com.google.android.gms.playlog.internal.IPlayLogService");
                    object2 = parcel.readString();
                    String string3 = string2;
                    if (parcel.readInt() != 0) {
                        PlayLoggerContext playLoggerContext = PlayLoggerContext.CREATOR.zzgz(parcel);
                    }
                    this.zza((String)object2, (PlayLoggerContext)var3_9, parcel.createTypedArrayList((Parcelable.Creator)LogEvent.CREATOR));
                    return true;
                }
                case 4: 
            }
            parcel.enforceInterface("com.google.android.gms.playlog.internal.IPlayLogService");
            object2 = parcel.readString();
            Object var3_10 = var7_15;
            if (parcel.readInt() != 0) {
                PlayLoggerContext playLoggerContext = PlayLoggerContext.CREATOR.zzgz(parcel);
            }
            this.zza((String)object2, (PlayLoggerContext)var3_12, parcel.createByteArray());
            return true;
        }

        private static class zza
        implements com.google.android.gms.playlog.internal.zza {
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
            public void zza(String string2, PlayLoggerContext playLoggerContext, LogEvent logEvent) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.playlog.internal.IPlayLogService");
                    parcel.writeString(string2);
                    if (playLoggerContext != null) {
                        parcel.writeInt(1);
                        playLoggerContext.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (logEvent != null) {
                        parcel.writeInt(1);
                        logEvent.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.zzoz.transact(2, parcel, null, 1);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void zza(String string2, PlayLoggerContext playLoggerContext, List<LogEvent> list) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.playlog.internal.IPlayLogService");
                    parcel.writeString(string2);
                    if (playLoggerContext != null) {
                        parcel.writeInt(1);
                        playLoggerContext.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeTypedList(list);
                    this.zzoz.transact(3, parcel, null, 1);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void zza(String string2, PlayLoggerContext playLoggerContext, byte[] byArray) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.playlog.internal.IPlayLogService");
                    parcel.writeString(string2);
                    if (playLoggerContext != null) {
                        parcel.writeInt(1);
                        playLoggerContext.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeByteArray(byArray);
                    this.zzoz.transact(4, parcel, null, 1);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }
        }
    }
}

