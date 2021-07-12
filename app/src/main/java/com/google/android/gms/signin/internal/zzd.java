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
package com.google.android.gms.signin.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.signin.internal.AuthAccountResult;
import com.google.android.gms.signin.internal.SignInResponse;

public interface zzd
extends IInterface {
    public void zza(ConnectionResult var1, AuthAccountResult var2) throws RemoteException;

    public void zza(Status var1, GoogleSignInAccount var2) throws RemoteException;

    public void zzb(SignInResponse var1) throws RemoteException;

    public void zzbl(Status var1) throws RemoteException;

    public void zzbm(Status var1) throws RemoteException;

    public static abstract class com.google.android.gms.signin.internal.zzd$zza
    extends Binder
    implements zzd {
        public com.google.android.gms.signin.internal.zzd$zza() {
            this.attachInterface(this, "com.google.android.gms.signin.internal.ISignInCallbacks");
        }

        public static zzd zzea(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface("com.google.android.gms.signin.internal.ISignInCallbacks");
            if (iInterface != null && iInterface instanceof zzd) {
                return (zzd)iInterface;
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
            void var2_16;
            void var3_18;
            switch (n2) {
                default: {
                    void var4_19;
                    return super.onTransact(n2, object, (Parcel)var3_18, (int)var4_19);
                }
                case 1598968902: {
                    var3_18.writeString("com.google.android.gms.signin.internal.ISignInCallbacks");
                    return true;
                }
                case 3: {
                    void var2_4;
                    object.enforceInterface("com.google.android.gms.signin.internal.ISignInCallbacks");
                    ConnectionResult connectionResult = object.readInt() != 0 ? (ConnectionResult)ConnectionResult.CREATOR.createFromParcel(object) : null;
                    if (object.readInt() != 0) {
                        AuthAccountResult authAccountResult = (AuthAccountResult)AuthAccountResult.CREATOR.createFromParcel(object);
                    } else {
                        Object var2_5 = null;
                    }
                    this.zza(connectionResult, (AuthAccountResult)var2_4);
                    var3_18.writeNoException();
                    return true;
                }
                case 4: {
                    void var2_7;
                    object.enforceInterface("com.google.android.gms.signin.internal.ISignInCallbacks");
                    if (object.readInt() != 0) {
                        Status status = (Status)Status.CREATOR.createFromParcel(object);
                    } else {
                        Object var2_8 = null;
                    }
                    this.zzbl((Status)var2_7);
                    var3_18.writeNoException();
                    return true;
                }
                case 6: {
                    void var2_10;
                    object.enforceInterface("com.google.android.gms.signin.internal.ISignInCallbacks");
                    if (object.readInt() != 0) {
                        Status status = (Status)Status.CREATOR.createFromParcel(object);
                    } else {
                        Object var2_11 = null;
                    }
                    this.zzbm((Status)var2_10);
                    var3_18.writeNoException();
                    return true;
                }
                case 7: {
                    void var2_13;
                    object.enforceInterface("com.google.android.gms.signin.internal.ISignInCallbacks");
                    Status status = object.readInt() != 0 ? (Status)Status.CREATOR.createFromParcel(object) : null;
                    if (object.readInt() != 0) {
                        GoogleSignInAccount googleSignInAccount = (GoogleSignInAccount)GoogleSignInAccount.CREATOR.createFromParcel(object);
                    } else {
                        Object var2_14 = null;
                    }
                    this.zza(status, (GoogleSignInAccount)var2_13);
                    var3_18.writeNoException();
                    return true;
                }
                case 8: 
            }
            object.enforceInterface("com.google.android.gms.signin.internal.ISignInCallbacks");
            if (object.readInt() != 0) {
                SignInResponse signInResponse = (SignInResponse)SignInResponse.CREATOR.createFromParcel(object);
            } else {
                Object var2_17 = null;
            }
            this.zzb((SignInResponse)var2_16);
            var3_18.writeNoException();
            return true;
        }

        private static class zza
        implements zzd {
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
            public void zza(ConnectionResult connectionResult, AuthAccountResult authAccountResult) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.signin.internal.ISignInCallbacks");
                    if (connectionResult != null) {
                        parcel.writeInt(1);
                        connectionResult.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (authAccountResult != null) {
                        parcel.writeInt(1);
                        authAccountResult.writeToParcel(parcel, 0);
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
            public void zza(Status status, GoogleSignInAccount googleSignInAccount) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.signin.internal.ISignInCallbacks");
                    if (status != null) {
                        parcel.writeInt(1);
                        status.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (googleSignInAccount != null) {
                        parcel.writeInt(1);
                        googleSignInAccount.writeToParcel(parcel, 0);
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
            public void zzb(SignInResponse signInResponse) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.signin.internal.ISignInCallbacks");
                    if (signInResponse != null) {
                        parcel.writeInt(1);
                        signInResponse.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
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
            public void zzbl(Status status) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.signin.internal.ISignInCallbacks");
                    if (status != null) {
                        parcel.writeInt(1);
                        status.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
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
            public void zzbm(Status status) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.signin.internal.ISignInCallbacks");
                    if (status != null) {
                        parcel.writeInt(1);
                        status.writeToParcel(parcel, 0);
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
        }
    }
}

