/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.accounts.Account
 *  android.os.Binder
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.Parcel
 *  android.os.RemoteException
 */
package com.google.android.gms.signin.internal;

import android.accounts.Account;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.common.internal.AuthAccountRequest;
import com.google.android.gms.common.internal.ResolveAccountRequest;
import com.google.android.gms.common.internal.zzp;
import com.google.android.gms.common.internal.zzt;
import com.google.android.gms.signin.internal.CheckServerAuthResult;
import com.google.android.gms.signin.internal.RecordConsentRequest;
import com.google.android.gms.signin.internal.SignInRequest;
import com.google.android.gms.signin.internal.zzd;

public interface zze
extends IInterface {
    public void zza(int var1, Account var2, zzd var3) throws RemoteException;

    public void zza(AuthAccountRequest var1, zzd var2) throws RemoteException;

    public void zza(ResolveAccountRequest var1, zzt var2) throws RemoteException;

    public void zza(zzp var1, int var2, boolean var3) throws RemoteException;

    public void zza(CheckServerAuthResult var1) throws RemoteException;

    public void zza(RecordConsentRequest var1, zzd var2) throws RemoteException;

    public void zza(SignInRequest var1, zzd var2) throws RemoteException;

    public void zzav(boolean var1) throws RemoteException;

    public void zzb(zzd var1) throws RemoteException;

    public void zzka(int var1) throws RemoteException;

    public static abstract class com.google.android.gms.signin.internal.zze$zza
    extends Binder
    implements zze {
        public static zze zzeb(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface("com.google.android.gms.signin.internal.ISignInService");
            if (iInterface != null && iInterface instanceof zze) {
                return (zze)iInterface;
            }
            return new zza(iBinder);
        }

        /*
         * Enabled aggressive block sorting
         */
        public boolean onTransact(int n2, Parcel parcel, Parcel parcel2, int n3) throws RemoteException {
            boolean bl2 = false;
            zzp zzp2 = null;
            zzp zzp3 = null;
            Object var9_8 = null;
            zzp zzp4 = null;
            zzp zzp5 = null;
            Object object = null;
            switch (n2) {
                default: {
                    return super.onTransact(n2, parcel, parcel2, n3);
                }
                case 1598968902: {
                    parcel2.writeString("com.google.android.gms.signin.internal.ISignInService");
                    return true;
                }
                case 2: {
                    parcel.enforceInterface("com.google.android.gms.signin.internal.ISignInService");
                    if (parcel.readInt() != 0) {
                        object = (AuthAccountRequest)AuthAccountRequest.CREATOR.createFromParcel(parcel);
                    }
                    this.zza((AuthAccountRequest)object, zzd.zza.zzea(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                }
                case 3: {
                    parcel.enforceInterface("com.google.android.gms.signin.internal.ISignInService");
                    object = zzp2;
                    if (parcel.readInt() != 0) {
                        object = (CheckServerAuthResult)CheckServerAuthResult.CREATOR.createFromParcel(parcel);
                    }
                    this.zza((CheckServerAuthResult)object);
                    parcel2.writeNoException();
                    return true;
                }
                case 4: {
                    parcel.enforceInterface("com.google.android.gms.signin.internal.ISignInService");
                    bl2 = parcel.readInt() != 0;
                    this.zzav(bl2);
                    parcel2.writeNoException();
                    return true;
                }
                case 5: {
                    parcel.enforceInterface("com.google.android.gms.signin.internal.ISignInService");
                    object = zzp3;
                    if (parcel.readInt() != 0) {
                        object = (ResolveAccountRequest)ResolveAccountRequest.CREATOR.createFromParcel(parcel);
                    }
                    this.zza((ResolveAccountRequest)object, zzt.zza.zzaT(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                }
                case 7: {
                    parcel.enforceInterface("com.google.android.gms.signin.internal.ISignInService");
                    this.zzka(parcel.readInt());
                    parcel2.writeNoException();
                    return true;
                }
                case 8: {
                    parcel.enforceInterface("com.google.android.gms.signin.internal.ISignInService");
                    n2 = parcel.readInt();
                    object = var9_8;
                    if (parcel.readInt() != 0) {
                        object = (Account)Account.CREATOR.createFromParcel(parcel);
                    }
                    this.zza(n2, (Account)object, zzd.zza.zzea(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                }
                case 9: {
                    parcel.enforceInterface("com.google.android.gms.signin.internal.ISignInService");
                    object = zzp.zza.zzaP(parcel.readStrongBinder());
                    n2 = parcel.readInt();
                    if (parcel.readInt() != 0) {
                        bl2 = true;
                    }
                    this.zza((zzp)object, n2, bl2);
                    parcel2.writeNoException();
                    return true;
                }
                case 10: {
                    parcel.enforceInterface("com.google.android.gms.signin.internal.ISignInService");
                    object = zzp4;
                    if (parcel.readInt() != 0) {
                        object = (RecordConsentRequest)RecordConsentRequest.CREATOR.createFromParcel(parcel);
                    }
                    this.zza((RecordConsentRequest)object, zzd.zza.zzea(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                }
                case 11: {
                    parcel.enforceInterface("com.google.android.gms.signin.internal.ISignInService");
                    this.zzb(zzd.zza.zzea(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                }
                case 12: 
            }
            parcel.enforceInterface("com.google.android.gms.signin.internal.ISignInService");
            object = zzp5;
            if (parcel.readInt() != 0) {
                object = (SignInRequest)SignInRequest.CREATOR.createFromParcel(parcel);
            }
            this.zza((SignInRequest)object, zzd.zza.zzea(parcel.readStrongBinder()));
            parcel2.writeNoException();
            return true;
        }

        private static class zza
        implements zze {
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
            public void zza(int n2, Account object, zzd zzd2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.signin.internal.ISignInService");
                    parcel.writeInt(n2);
                    if (object != null) {
                        parcel.writeInt(1);
                        object.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    object = zzd2 != null ? zzd2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)object);
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
            public void zza(AuthAccountRequest authAccountRequest, zzd zzd2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.signin.internal.ISignInService");
                    if (authAccountRequest != null) {
                        parcel.writeInt(1);
                        authAccountRequest.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    authAccountRequest = zzd2 != null ? zzd2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)authAccountRequest);
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
            public void zza(ResolveAccountRequest resolveAccountRequest, zzt zzt2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.signin.internal.ISignInService");
                    if (resolveAccountRequest != null) {
                        parcel.writeInt(1);
                        resolveAccountRequest.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    resolveAccountRequest = zzt2 != null ? zzt2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)resolveAccountRequest);
                    this.zzoz.transact(5, parcel, parcel2, 0);
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
            public void zza(zzp zzp2, int n2, boolean bl2) throws RemoteException {
                int n3 = 0;
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.signin.internal.ISignInService");
                    zzp2 = zzp2 != null ? zzp2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)zzp2);
                    parcel.writeInt(n2);
                    n2 = n3;
                    if (bl2) {
                        n2 = 1;
                    }
                    parcel.writeInt(n2);
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
            public void zza(CheckServerAuthResult checkServerAuthResult) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.signin.internal.ISignInService");
                    if (checkServerAuthResult != null) {
                        parcel.writeInt(1);
                        checkServerAuthResult.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
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
            public void zza(RecordConsentRequest recordConsentRequest, zzd zzd2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.signin.internal.ISignInService");
                    if (recordConsentRequest != null) {
                        parcel.writeInt(1);
                        recordConsentRequest.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    recordConsentRequest = zzd2 != null ? zzd2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)recordConsentRequest);
                    this.zzoz.transact(10, parcel, parcel2, 0);
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
            public void zza(SignInRequest signInRequest, zzd zzd2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.signin.internal.ISignInService");
                    if (signInRequest != null) {
                        parcel.writeInt(1);
                        signInRequest.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    signInRequest = zzd2 != null ? zzd2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)signInRequest);
                    this.zzoz.transact(12, parcel, parcel2, 0);
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void zzav(boolean bl2) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                int n2;
                block4: {
                    n2 = 0;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    parcel2.writeInterfaceToken("com.google.android.gms.signin.internal.ISignInService");
                    if (!bl2) break block4;
                    n2 = 1;
                }
                try {
                    parcel2.writeInt(n2);
                    this.zzoz.transact(4, parcel2, parcel, 0);
                    parcel.readException();
                    return;
                }
                finally {
                    parcel.recycle();
                    parcel2.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void zzb(zzd zzd2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.signin.internal.ISignInService");
                    zzd2 = zzd2 != null ? zzd2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)zzd2);
                    this.zzoz.transact(11, parcel, parcel2, 0);
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void zzka(int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.signin.internal.ISignInService");
                    parcel.writeInt(n2);
                    this.zzoz.transact(7, parcel, parcel2, 0);
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

