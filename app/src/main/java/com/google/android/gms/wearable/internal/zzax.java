/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 *  android.os.Binder
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.Parcel
 *  android.os.ParcelFileDescriptor
 *  android.os.RemoteException
 */
package com.google.android.gms.wearable.internal;

import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import com.google.android.gms.wearable.Asset;
import com.google.android.gms.wearable.ConnectionConfiguration;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.internal.AddListenerRequest;
import com.google.android.gms.wearable.internal.AncsNotificationParcelable;
import com.google.android.gms.wearable.internal.RemoveListenerRequest;
import com.google.android.gms.wearable.internal.zzau;
import com.google.android.gms.wearable.internal.zzav;

public interface zzax
extends IInterface {
    public void zza(zzav var1) throws RemoteException;

    public void zza(zzav var1, byte var2) throws RemoteException;

    public void zza(zzav var1, int var2) throws RemoteException;

    public void zza(zzav var1, Uri var2) throws RemoteException;

    public void zza(zzav var1, Uri var2, int var3) throws RemoteException;

    public void zza(zzav var1, Asset var2) throws RemoteException;

    public void zza(zzav var1, ConnectionConfiguration var2) throws RemoteException;

    public void zza(zzav var1, PutDataRequest var2) throws RemoteException;

    public void zza(zzav var1, AddListenerRequest var2) throws RemoteException;

    public void zza(zzav var1, AncsNotificationParcelable var2) throws RemoteException;

    public void zza(zzav var1, RemoveListenerRequest var2) throws RemoteException;

    public void zza(zzav var1, zzau var2, String var3) throws RemoteException;

    public void zza(zzav var1, String var2) throws RemoteException;

    public void zza(zzav var1, String var2, int var3) throws RemoteException;

    public void zza(zzav var1, String var2, ParcelFileDescriptor var3) throws RemoteException;

    public void zza(zzav var1, String var2, ParcelFileDescriptor var3, long var4, long var6) throws RemoteException;

    public void zza(zzav var1, String var2, String var3) throws RemoteException;

    public void zza(zzav var1, String var2, String var3, byte[] var4) throws RemoteException;

    public void zza(zzav var1, boolean var2) throws RemoteException;

    public void zzb(zzav var1) throws RemoteException;

    public void zzb(zzav var1, int var2) throws RemoteException;

    public void zzb(zzav var1, Uri var2) throws RemoteException;

    public void zzb(zzav var1, Uri var2, int var3) throws RemoteException;

    public void zzb(zzav var1, ConnectionConfiguration var2) throws RemoteException;

    public void zzb(zzav var1, zzau var2, String var3) throws RemoteException;

    public void zzb(zzav var1, String var2) throws RemoteException;

    public void zzb(zzav var1, String var2, int var3) throws RemoteException;

    public void zzb(zzav var1, boolean var2) throws RemoteException;

    public void zzc(zzav var1) throws RemoteException;

    public void zzc(zzav var1, int var2) throws RemoteException;

    public void zzc(zzav var1, Uri var2) throws RemoteException;

    public void zzc(zzav var1, String var2) throws RemoteException;

    public void zzd(zzav var1) throws RemoteException;

    public void zzd(zzav var1, String var2) throws RemoteException;

    public void zze(zzav var1) throws RemoteException;

    public void zze(zzav var1, String var2) throws RemoteException;

    public void zzf(zzav var1) throws RemoteException;

    public void zzf(zzav var1, String var2) throws RemoteException;

    public void zzg(zzav var1) throws RemoteException;

    public void zzh(zzav var1) throws RemoteException;

    public void zzi(zzav var1) throws RemoteException;

    public void zzj(zzav var1) throws RemoteException;

    public void zzk(zzav var1) throws RemoteException;

    public void zzl(zzav var1) throws RemoteException;

    public void zzm(zzav var1) throws RemoteException;

    public void zzn(zzav var1) throws RemoteException;

    public void zzo(zzav var1) throws RemoteException;

    public void zzp(zzav var1) throws RemoteException;

    public static abstract class com.google.android.gms.wearable.internal.zzax$zza
    extends Binder
    implements zzax {
        public static zzax zzeu(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface("com.google.android.gms.wearable.internal.IWearableService");
            if (iInterface != null && iInterface instanceof zzax) {
                return (zzax)iInterface;
            }
            return new zza(iBinder);
        }

        /*
         * Enabled aggressive block sorting
         */
        public boolean onTransact(int n2, Parcel parcel, Parcel parcel2, int n3) throws RemoteException {
            boolean bl2 = false;
            Object object = null;
            Object var9_7 = null;
            Object var10_8 = null;
            Object var11_9 = null;
            Object var12_10 = null;
            Object var13_11 = null;
            Object var14_12 = null;
            Object var15_13 = null;
            Object var16_14 = null;
            Object var17_15 = null;
            Object object2 = null;
            Object var18_17 = null;
            zzav zzav2 = null;
            switch (n2) {
                default: {
                    return super.onTransact(n2, parcel, parcel2, n3);
                }
                case 1598968902: {
                    parcel2.writeString("com.google.android.gms.wearable.internal.IWearableService");
                    return true;
                }
                case 20: {
                    parcel.enforceInterface("com.google.android.gms.wearable.internal.IWearableService");
                    object = zzav.zza.zzes(parcel.readStrongBinder());
                    object2 = zzav2;
                    if (parcel.readInt() != 0) {
                        object2 = (ConnectionConfiguration)ConnectionConfiguration.CREATOR.createFromParcel(parcel);
                    }
                    this.zza((zzav)object, (ConnectionConfiguration)object2);
                    parcel2.writeNoException();
                    return true;
                }
                case 21: {
                    parcel.enforceInterface("com.google.android.gms.wearable.internal.IWearableService");
                    this.zza(zzav.zza.zzes(parcel.readStrongBinder()), parcel.readString());
                    parcel2.writeNoException();
                    return true;
                }
                case 22: {
                    parcel.enforceInterface("com.google.android.gms.wearable.internal.IWearableService");
                    this.zza(zzav.zza.zzes(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                }
                case 23: {
                    parcel.enforceInterface("com.google.android.gms.wearable.internal.IWearableService");
                    this.zzb(zzav.zza.zzes(parcel.readStrongBinder()), parcel.readString());
                    parcel2.writeNoException();
                    return true;
                }
                case 24: {
                    parcel.enforceInterface("com.google.android.gms.wearable.internal.IWearableService");
                    this.zzc(zzav.zza.zzes(parcel.readStrongBinder()), parcel.readString());
                    parcel2.writeNoException();
                    return true;
                }
                case 6: {
                    parcel.enforceInterface("com.google.android.gms.wearable.internal.IWearableService");
                    zzav2 = zzav.zza.zzes(parcel.readStrongBinder());
                    object2 = object;
                    if (parcel.readInt() != 0) {
                        object2 = (PutDataRequest)PutDataRequest.CREATOR.createFromParcel(parcel);
                    }
                    this.zza(zzav2, (PutDataRequest)object2);
                    parcel2.writeNoException();
                    return true;
                }
                case 7: {
                    parcel.enforceInterface("com.google.android.gms.wearable.internal.IWearableService");
                    zzav2 = zzav.zza.zzes(parcel.readStrongBinder());
                    object2 = var9_7;
                    if (parcel.readInt() != 0) {
                        object2 = (Uri)Uri.CREATOR.createFromParcel(parcel);
                    }
                    this.zza(zzav2, (Uri)object2);
                    parcel2.writeNoException();
                    return true;
                }
                case 8: {
                    parcel.enforceInterface("com.google.android.gms.wearable.internal.IWearableService");
                    this.zzb(zzav.zza.zzes(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                }
                case 9: {
                    parcel.enforceInterface("com.google.android.gms.wearable.internal.IWearableService");
                    zzav2 = zzav.zza.zzes(parcel.readStrongBinder());
                    object2 = var10_8;
                    if (parcel.readInt() != 0) {
                        object2 = (Uri)Uri.CREATOR.createFromParcel(parcel);
                    }
                    this.zzb(zzav2, (Uri)object2);
                    parcel2.writeNoException();
                    return true;
                }
                case 40: {
                    parcel.enforceInterface("com.google.android.gms.wearable.internal.IWearableService");
                    zzav2 = zzav.zza.zzes(parcel.readStrongBinder());
                    object2 = var11_9;
                    if (parcel.readInt() != 0) {
                        object2 = (Uri)Uri.CREATOR.createFromParcel(parcel);
                    }
                    this.zza(zzav2, (Uri)object2, parcel.readInt());
                    parcel2.writeNoException();
                    return true;
                }
                case 11: {
                    parcel.enforceInterface("com.google.android.gms.wearable.internal.IWearableService");
                    zzav2 = zzav.zza.zzes(parcel.readStrongBinder());
                    object2 = var12_10;
                    if (parcel.readInt() != 0) {
                        object2 = (Uri)Uri.CREATOR.createFromParcel(parcel);
                    }
                    this.zzc(zzav2, (Uri)object2);
                    parcel2.writeNoException();
                    return true;
                }
                case 41: {
                    parcel.enforceInterface("com.google.android.gms.wearable.internal.IWearableService");
                    zzav2 = zzav.zza.zzes(parcel.readStrongBinder());
                    object2 = var13_11;
                    if (parcel.readInt() != 0) {
                        object2 = (Uri)Uri.CREATOR.createFromParcel(parcel);
                    }
                    this.zzb(zzav2, (Uri)object2, parcel.readInt());
                    parcel2.writeNoException();
                    return true;
                }
                case 12: {
                    parcel.enforceInterface("com.google.android.gms.wearable.internal.IWearableService");
                    this.zza(zzav.zza.zzes(parcel.readStrongBinder()), parcel.readString(), parcel.readString(), parcel.createByteArray());
                    parcel2.writeNoException();
                    return true;
                }
                case 13: {
                    parcel.enforceInterface("com.google.android.gms.wearable.internal.IWearableService");
                    zzav2 = zzav.zza.zzes(parcel.readStrongBinder());
                    object2 = var14_12;
                    if (parcel.readInt() != 0) {
                        object2 = (Asset)Asset.CREATOR.createFromParcel(parcel);
                    }
                    this.zza(zzav2, (Asset)object2);
                    parcel2.writeNoException();
                    return true;
                }
                case 14: {
                    parcel.enforceInterface("com.google.android.gms.wearable.internal.IWearableService");
                    this.zzc(zzav.zza.zzes(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                }
                case 15: {
                    parcel.enforceInterface("com.google.android.gms.wearable.internal.IWearableService");
                    this.zzd(zzav.zza.zzes(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                }
                case 42: {
                    parcel.enforceInterface("com.google.android.gms.wearable.internal.IWearableService");
                    this.zza(zzav.zza.zzes(parcel.readStrongBinder()), parcel.readString(), parcel.readInt());
                    parcel2.writeNoException();
                    return true;
                }
                case 43: {
                    parcel.enforceInterface("com.google.android.gms.wearable.internal.IWearableService");
                    this.zza(zzav.zza.zzes(parcel.readStrongBinder()), parcel.readInt());
                    parcel2.writeNoException();
                    return true;
                }
                case 46: {
                    parcel.enforceInterface("com.google.android.gms.wearable.internal.IWearableService");
                    this.zzd(zzav.zza.zzes(parcel.readStrongBinder()), parcel.readString());
                    parcel2.writeNoException();
                    return true;
                }
                case 47: {
                    parcel.enforceInterface("com.google.android.gms.wearable.internal.IWearableService");
                    this.zze(zzav.zza.zzes(parcel.readStrongBinder()), parcel.readString());
                    parcel2.writeNoException();
                    return true;
                }
                case 16: {
                    parcel.enforceInterface("com.google.android.gms.wearable.internal.IWearableService");
                    zzav2 = zzav.zza.zzes(parcel.readStrongBinder());
                    object2 = var15_13;
                    if (parcel.readInt() != 0) {
                        object2 = (AddListenerRequest)AddListenerRequest.CREATOR.createFromParcel(parcel);
                    }
                    this.zza(zzav2, (AddListenerRequest)object2);
                    parcel2.writeNoException();
                    return true;
                }
                case 17: {
                    parcel.enforceInterface("com.google.android.gms.wearable.internal.IWearableService");
                    zzav2 = zzav.zza.zzes(parcel.readStrongBinder());
                    object2 = var16_14;
                    if (parcel.readInt() != 0) {
                        object2 = (RemoveListenerRequest)RemoveListenerRequest.CREATOR.createFromParcel(parcel);
                    }
                    this.zza(zzav2, (RemoveListenerRequest)object2);
                    parcel2.writeNoException();
                    return true;
                }
                case 18: {
                    parcel.enforceInterface("com.google.android.gms.wearable.internal.IWearableService");
                    this.zze(zzav.zza.zzes(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                }
                case 19: {
                    parcel.enforceInterface("com.google.android.gms.wearable.internal.IWearableService");
                    this.zzf(zzav.zza.zzes(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                }
                case 25: {
                    parcel.enforceInterface("com.google.android.gms.wearable.internal.IWearableService");
                    this.zzg(zzav.zza.zzes(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                }
                case 26: {
                    parcel.enforceInterface("com.google.android.gms.wearable.internal.IWearableService");
                    this.zzh(zzav.zza.zzes(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                }
                case 27: {
                    parcel.enforceInterface("com.google.android.gms.wearable.internal.IWearableService");
                    zzav2 = zzav.zza.zzes(parcel.readStrongBinder());
                    object2 = var17_15;
                    if (parcel.readInt() != 0) {
                        object2 = (AncsNotificationParcelable)AncsNotificationParcelable.CREATOR.createFromParcel(parcel);
                    }
                    this.zza(zzav2, (AncsNotificationParcelable)object2);
                    parcel2.writeNoException();
                    return true;
                }
                case 28: {
                    parcel.enforceInterface("com.google.android.gms.wearable.internal.IWearableService");
                    this.zzb(zzav.zza.zzes(parcel.readStrongBinder()), parcel.readInt());
                    parcel2.writeNoException();
                    return true;
                }
                case 29: {
                    parcel.enforceInterface("com.google.android.gms.wearable.internal.IWearableService");
                    this.zzc(zzav.zza.zzes(parcel.readStrongBinder()), parcel.readInt());
                    parcel2.writeNoException();
                    return true;
                }
                case 30: {
                    parcel.enforceInterface("com.google.android.gms.wearable.internal.IWearableService");
                    this.zzi(zzav.zza.zzes(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                }
                case 31: {
                    parcel.enforceInterface("com.google.android.gms.wearable.internal.IWearableService");
                    this.zza(zzav.zza.zzes(parcel.readStrongBinder()), parcel.readString(), parcel.readString());
                    parcel2.writeNoException();
                    return true;
                }
                case 32: {
                    parcel.enforceInterface("com.google.android.gms.wearable.internal.IWearableService");
                    this.zzf(zzav.zza.zzes(parcel.readStrongBinder()), parcel.readString());
                    parcel2.writeNoException();
                    return true;
                }
                case 33: {
                    parcel.enforceInterface("com.google.android.gms.wearable.internal.IWearableService");
                    this.zzb(zzav.zza.zzes(parcel.readStrongBinder()), parcel.readString(), parcel.readInt());
                    parcel2.writeNoException();
                    return true;
                }
                case 34: {
                    parcel.enforceInterface("com.google.android.gms.wearable.internal.IWearableService");
                    this.zza(zzav.zza.zzes(parcel.readStrongBinder()), zzau.zza.zzer(parcel.readStrongBinder()), parcel.readString());
                    parcel2.writeNoException();
                    return true;
                }
                case 35: {
                    parcel.enforceInterface("com.google.android.gms.wearable.internal.IWearableService");
                    this.zzb(zzav.zza.zzes(parcel.readStrongBinder()), zzau.zza.zzer(parcel.readStrongBinder()), parcel.readString());
                    parcel2.writeNoException();
                    return true;
                }
                case 38: {
                    parcel.enforceInterface("com.google.android.gms.wearable.internal.IWearableService");
                    zzav2 = zzav.zza.zzes(parcel.readStrongBinder());
                    object = parcel.readString();
                    if (parcel.readInt() != 0) {
                        object2 = (ParcelFileDescriptor)ParcelFileDescriptor.CREATOR.createFromParcel(parcel);
                    }
                    this.zza(zzav2, (String)object, (ParcelFileDescriptor)object2);
                    parcel2.writeNoException();
                    return true;
                }
                case 39: {
                    parcel.enforceInterface("com.google.android.gms.wearable.internal.IWearableService");
                    zzav2 = zzav.zza.zzes(parcel.readStrongBinder());
                    object = parcel.readString();
                    object2 = parcel.readInt() != 0 ? (ParcelFileDescriptor)ParcelFileDescriptor.CREATOR.createFromParcel(parcel) : null;
                    this.zza(zzav2, (String)object, (ParcelFileDescriptor)object2, parcel.readLong(), parcel.readLong());
                    parcel2.writeNoException();
                    return true;
                }
                case 37: {
                    parcel.enforceInterface("com.google.android.gms.wearable.internal.IWearableService");
                    this.zzj(zzav.zza.zzes(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                }
                case 48: {
                    parcel.enforceInterface("com.google.android.gms.wearable.internal.IWearableService");
                    object2 = zzav.zza.zzes(parcel.readStrongBinder());
                    bl2 = parcel.readInt() != 0;
                    this.zza((zzav)object2, bl2);
                    parcel2.writeNoException();
                    return true;
                }
                case 49: {
                    parcel.enforceInterface("com.google.android.gms.wearable.internal.IWearableService");
                    this.zzk(zzav.zza.zzes(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                }
                case 50: {
                    parcel.enforceInterface("com.google.android.gms.wearable.internal.IWearableService");
                    object2 = zzav.zza.zzes(parcel.readStrongBinder());
                    if (parcel.readInt() != 0) {
                        bl2 = true;
                    }
                    this.zzb((zzav)object2, bl2);
                    parcel2.writeNoException();
                    return true;
                }
                case 51: {
                    parcel.enforceInterface("com.google.android.gms.wearable.internal.IWearableService");
                    this.zzl(zzav.zza.zzes(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                }
                case 52: {
                    parcel.enforceInterface("com.google.android.gms.wearable.internal.IWearableService");
                    this.zzm(zzav.zza.zzes(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                }
                case 53: {
                    parcel.enforceInterface("com.google.android.gms.wearable.internal.IWearableService");
                    this.zza(zzav.zza.zzes(parcel.readStrongBinder()), parcel.readByte());
                    parcel2.writeNoException();
                    return true;
                }
                case 2: {
                    parcel.enforceInterface("com.google.android.gms.wearable.internal.IWearableService");
                    zzav2 = zzav.zza.zzes(parcel.readStrongBinder());
                    object2 = var18_17;
                    if (parcel.readInt() != 0) {
                        object2 = (ConnectionConfiguration)ConnectionConfiguration.CREATOR.createFromParcel(parcel);
                    }
                    this.zzb(zzav2, (ConnectionConfiguration)object2);
                    parcel2.writeNoException();
                    return true;
                }
                case 3: {
                    parcel.enforceInterface("com.google.android.gms.wearable.internal.IWearableService");
                    this.zzn(zzav.zza.zzes(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                }
                case 4: {
                    parcel.enforceInterface("com.google.android.gms.wearable.internal.IWearableService");
                    this.zzo(zzav.zza.zzes(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                }
                case 5: 
            }
            parcel.enforceInterface("com.google.android.gms.wearable.internal.IWearableService");
            this.zzp(zzav.zza.zzes(parcel.readStrongBinder()));
            parcel2.writeNoException();
            return true;
        }

        private static class zza
        implements zzax {
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
            public void zza(zzav zzav2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.wearable.internal.IWearableService");
                    zzav2 = zzav2 != null ? zzav2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)zzav2);
                    this.zzoz.transact(22, parcel, parcel2, 0);
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
            public void zza(zzav zzav2, byte by2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.wearable.internal.IWearableService");
                    zzav2 = zzav2 != null ? zzav2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)zzav2);
                    parcel.writeByte(by2);
                    this.zzoz.transact(53, parcel, parcel2, 0);
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
            public void zza(zzav zzav2, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.wearable.internal.IWearableService");
                    zzav2 = zzav2 != null ? zzav2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)zzav2);
                    parcel.writeInt(n2);
                    this.zzoz.transact(43, parcel, parcel2, 0);
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
            public void zza(zzav zzav2, Uri uri) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.wearable.internal.IWearableService");
                    zzav2 = zzav2 != null ? zzav2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)zzav2);
                    if (uri != null) {
                        parcel.writeInt(1);
                        uri.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.zzoz.transact(7, parcel, parcel2, 0);
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
            public void zza(zzav zzav2, Uri uri, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.wearable.internal.IWearableService");
                    zzav2 = zzav2 != null ? zzav2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)zzav2);
                    if (uri != null) {
                        parcel.writeInt(1);
                        uri.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n2);
                    this.zzoz.transact(40, parcel, parcel2, 0);
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
            public void zza(zzav zzav2, Asset asset) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.wearable.internal.IWearableService");
                    zzav2 = zzav2 != null ? zzav2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)zzav2);
                    if (asset != null) {
                        parcel.writeInt(1);
                        asset.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.zzoz.transact(13, parcel, parcel2, 0);
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
            public void zza(zzav zzav2, ConnectionConfiguration connectionConfiguration) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.wearable.internal.IWearableService");
                    zzav2 = zzav2 != null ? zzav2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)zzav2);
                    if (connectionConfiguration != null) {
                        parcel.writeInt(1);
                        connectionConfiguration.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.zzoz.transact(20, parcel, parcel2, 0);
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
            public void zza(zzav zzav2, PutDataRequest putDataRequest) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.wearable.internal.IWearableService");
                    zzav2 = zzav2 != null ? zzav2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)zzav2);
                    if (putDataRequest != null) {
                        parcel.writeInt(1);
                        putDataRequest.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.zzoz.transact(6, parcel, parcel2, 0);
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
            public void zza(zzav zzav2, AddListenerRequest addListenerRequest) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.wearable.internal.IWearableService");
                    zzav2 = zzav2 != null ? zzav2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)zzav2);
                    if (addListenerRequest != null) {
                        parcel.writeInt(1);
                        addListenerRequest.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.zzoz.transact(16, parcel, parcel2, 0);
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
            public void zza(zzav zzav2, AncsNotificationParcelable ancsNotificationParcelable) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.wearable.internal.IWearableService");
                    zzav2 = zzav2 != null ? zzav2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)zzav2);
                    if (ancsNotificationParcelable != null) {
                        parcel.writeInt(1);
                        ancsNotificationParcelable.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.zzoz.transact(27, parcel, parcel2, 0);
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
            public void zza(zzav zzav2, RemoveListenerRequest removeListenerRequest) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.wearable.internal.IWearableService");
                    zzav2 = zzav2 != null ? zzav2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)zzav2);
                    if (removeListenerRequest != null) {
                        parcel.writeInt(1);
                        removeListenerRequest.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.zzoz.transact(17, parcel, parcel2, 0);
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
            public void zza(zzav zzav2, zzau zzau2, String string2) throws RemoteException {
                Object var4_5 = null;
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.wearable.internal.IWearableService");
                    zzav2 = zzav2 != null ? zzav2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)zzav2);
                    zzav2 = var4_5;
                    if (zzau2 != null) {
                        zzav2 = zzau2.asBinder();
                    }
                    parcel.writeStrongBinder((IBinder)zzav2);
                    parcel.writeString(string2);
                    this.zzoz.transact(34, parcel, parcel2, 0);
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
            public void zza(zzav zzav2, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.wearable.internal.IWearableService");
                    zzav2 = zzav2 != null ? zzav2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)zzav2);
                    parcel.writeString(string2);
                    this.zzoz.transact(21, parcel, parcel2, 0);
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
            public void zza(zzav zzav2, String string2, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.wearable.internal.IWearableService");
                    zzav2 = zzav2 != null ? zzav2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)zzav2);
                    parcel.writeString(string2);
                    parcel.writeInt(n2);
                    this.zzoz.transact(42, parcel, parcel2, 0);
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
            public void zza(zzav zzav2, String string2, ParcelFileDescriptor parcelFileDescriptor) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.wearable.internal.IWearableService");
                    zzav2 = zzav2 != null ? zzav2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)zzav2);
                    parcel.writeString(string2);
                    if (parcelFileDescriptor != null) {
                        parcel.writeInt(1);
                        parcelFileDescriptor.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.zzoz.transact(38, parcel, parcel2, 0);
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
            public void zza(zzav zzav2, String string2, ParcelFileDescriptor parcelFileDescriptor, long l2, long l3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.wearable.internal.IWearableService");
                    zzav2 = zzav2 != null ? zzav2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)zzav2);
                    parcel.writeString(string2);
                    if (parcelFileDescriptor != null) {
                        parcel.writeInt(1);
                        parcelFileDescriptor.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeLong(l2);
                    parcel.writeLong(l3);
                    this.zzoz.transact(39, parcel, parcel2, 0);
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
            public void zza(zzav zzav2, String string2, String string3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.wearable.internal.IWearableService");
                    zzav2 = zzav2 != null ? zzav2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)zzav2);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    this.zzoz.transact(31, parcel, parcel2, 0);
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
            public void zza(zzav zzav2, String string2, String string3, byte[] byArray) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.wearable.internal.IWearableService");
                    zzav2 = zzav2 != null ? zzav2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)zzav2);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    parcel.writeByteArray(byArray);
                    this.zzoz.transact(12, parcel, parcel2, 0);
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
            public void zza(zzav zzav2, boolean bl2) throws RemoteException {
                int n2 = 0;
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.wearable.internal.IWearableService");
                    zzav2 = zzav2 != null ? zzav2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)zzav2);
                    if (bl2) {
                        n2 = 1;
                    }
                    parcel.writeInt(n2);
                    this.zzoz.transact(48, parcel, parcel2, 0);
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
            public void zzb(zzav zzav2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.wearable.internal.IWearableService");
                    zzav2 = zzav2 != null ? zzav2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)zzav2);
                    this.zzoz.transact(8, parcel, parcel2, 0);
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
            public void zzb(zzav zzav2, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.wearable.internal.IWearableService");
                    zzav2 = zzav2 != null ? zzav2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)zzav2);
                    parcel.writeInt(n2);
                    this.zzoz.transact(28, parcel, parcel2, 0);
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
            public void zzb(zzav zzav2, Uri uri) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.wearable.internal.IWearableService");
                    zzav2 = zzav2 != null ? zzav2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)zzav2);
                    if (uri != null) {
                        parcel.writeInt(1);
                        uri.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.zzoz.transact(9, parcel, parcel2, 0);
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
            public void zzb(zzav zzav2, Uri uri, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.wearable.internal.IWearableService");
                    zzav2 = zzav2 != null ? zzav2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)zzav2);
                    if (uri != null) {
                        parcel.writeInt(1);
                        uri.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n2);
                    this.zzoz.transact(41, parcel, parcel2, 0);
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
            public void zzb(zzav zzav2, ConnectionConfiguration connectionConfiguration) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.wearable.internal.IWearableService");
                    zzav2 = zzav2 != null ? zzav2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)zzav2);
                    if (connectionConfiguration != null) {
                        parcel.writeInt(1);
                        connectionConfiguration.writeToParcel(parcel, 0);
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

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void zzb(zzav zzav2, zzau zzau2, String string2) throws RemoteException {
                Object var4_5 = null;
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.wearable.internal.IWearableService");
                    zzav2 = zzav2 != null ? zzav2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)zzav2);
                    zzav2 = var4_5;
                    if (zzau2 != null) {
                        zzav2 = zzau2.asBinder();
                    }
                    parcel.writeStrongBinder((IBinder)zzav2);
                    parcel.writeString(string2);
                    this.zzoz.transact(35, parcel, parcel2, 0);
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
            public void zzb(zzav zzav2, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.wearable.internal.IWearableService");
                    zzav2 = zzav2 != null ? zzav2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)zzav2);
                    parcel.writeString(string2);
                    this.zzoz.transact(23, parcel, parcel2, 0);
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
            public void zzb(zzav zzav2, String string2, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.wearable.internal.IWearableService");
                    zzav2 = zzav2 != null ? zzav2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)zzav2);
                    parcel.writeString(string2);
                    parcel.writeInt(n2);
                    this.zzoz.transact(33, parcel, parcel2, 0);
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
            public void zzb(zzav zzav2, boolean bl2) throws RemoteException {
                int n2 = 0;
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.wearable.internal.IWearableService");
                    zzav2 = zzav2 != null ? zzav2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)zzav2);
                    if (bl2) {
                        n2 = 1;
                    }
                    parcel.writeInt(n2);
                    this.zzoz.transact(50, parcel, parcel2, 0);
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
            public void zzc(zzav zzav2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.wearable.internal.IWearableService");
                    zzav2 = zzav2 != null ? zzav2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)zzav2);
                    this.zzoz.transact(14, parcel, parcel2, 0);
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
            public void zzc(zzav zzav2, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.wearable.internal.IWearableService");
                    zzav2 = zzav2 != null ? zzav2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)zzav2);
                    parcel.writeInt(n2);
                    this.zzoz.transact(29, parcel, parcel2, 0);
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
            public void zzc(zzav zzav2, Uri uri) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.wearable.internal.IWearableService");
                    zzav2 = zzav2 != null ? zzav2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)zzav2);
                    if (uri != null) {
                        parcel.writeInt(1);
                        uri.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.zzoz.transact(11, parcel, parcel2, 0);
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
            public void zzc(zzav zzav2, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.wearable.internal.IWearableService");
                    zzav2 = zzav2 != null ? zzav2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)zzav2);
                    parcel.writeString(string2);
                    this.zzoz.transact(24, parcel, parcel2, 0);
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
            public void zzd(zzav zzav2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.wearable.internal.IWearableService");
                    zzav2 = zzav2 != null ? zzav2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)zzav2);
                    this.zzoz.transact(15, parcel, parcel2, 0);
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
            public void zzd(zzav zzav2, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.wearable.internal.IWearableService");
                    zzav2 = zzav2 != null ? zzav2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)zzav2);
                    parcel.writeString(string2);
                    this.zzoz.transact(46, parcel, parcel2, 0);
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
            public void zze(zzav zzav2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.wearable.internal.IWearableService");
                    zzav2 = zzav2 != null ? zzav2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)zzav2);
                    this.zzoz.transact(18, parcel, parcel2, 0);
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
            public void zze(zzav zzav2, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.wearable.internal.IWearableService");
                    zzav2 = zzav2 != null ? zzav2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)zzav2);
                    parcel.writeString(string2);
                    this.zzoz.transact(47, parcel, parcel2, 0);
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
            public void zzf(zzav zzav2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.wearable.internal.IWearableService");
                    zzav2 = zzav2 != null ? zzav2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)zzav2);
                    this.zzoz.transact(19, parcel, parcel2, 0);
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
            public void zzf(zzav zzav2, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.wearable.internal.IWearableService");
                    zzav2 = zzav2 != null ? zzav2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)zzav2);
                    parcel.writeString(string2);
                    this.zzoz.transact(32, parcel, parcel2, 0);
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
            public void zzg(zzav zzav2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.wearable.internal.IWearableService");
                    zzav2 = zzav2 != null ? zzav2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)zzav2);
                    this.zzoz.transact(25, parcel, parcel2, 0);
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
            public void zzh(zzav zzav2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.wearable.internal.IWearableService");
                    zzav2 = zzav2 != null ? zzav2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)zzav2);
                    this.zzoz.transact(26, parcel, parcel2, 0);
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
            public void zzi(zzav zzav2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.wearable.internal.IWearableService");
                    zzav2 = zzav2 != null ? zzav2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)zzav2);
                    this.zzoz.transact(30, parcel, parcel2, 0);
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
            public void zzj(zzav zzav2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.wearable.internal.IWearableService");
                    zzav2 = zzav2 != null ? zzav2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)zzav2);
                    this.zzoz.transact(37, parcel, parcel2, 0);
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
            public void zzk(zzav zzav2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.wearable.internal.IWearableService");
                    zzav2 = zzav2 != null ? zzav2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)zzav2);
                    this.zzoz.transact(49, parcel, parcel2, 0);
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
            public void zzl(zzav zzav2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.wearable.internal.IWearableService");
                    zzav2 = zzav2 != null ? zzav2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)zzav2);
                    this.zzoz.transact(51, parcel, parcel2, 0);
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
            public void zzm(zzav zzav2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.wearable.internal.IWearableService");
                    zzav2 = zzav2 != null ? zzav2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)zzav2);
                    this.zzoz.transact(52, parcel, parcel2, 0);
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
            public void zzn(zzav zzav2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.wearable.internal.IWearableService");
                    zzav2 = zzav2 != null ? zzav2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)zzav2);
                    this.zzoz.transact(3, parcel, parcel2, 0);
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
            public void zzo(zzav zzav2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.wearable.internal.IWearableService");
                    zzav2 = zzav2 != null ? zzav2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)zzav2);
                    this.zzoz.transact(4, parcel, parcel2, 0);
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
            public void zzp(zzav zzav2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.wearable.internal.IWearableService");
                    zzav2 = zzav2 != null ? zzav2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)zzav2);
                    this.zzoz.transact(5, parcel, parcel2, 0);
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

