/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Intent
 *  android.os.Binder
 *  android.os.Bundle
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.Parcel
 *  android.os.RemoteException
 */
package com.google.android.gms.dynamic;

import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.dynamic.zzd;

public interface zzc
extends IInterface {
    public Bundle getArguments() throws RemoteException;

    public int getId() throws RemoteException;

    public boolean getRetainInstance() throws RemoteException;

    public String getTag() throws RemoteException;

    public int getTargetRequestCode() throws RemoteException;

    public boolean getUserVisibleHint() throws RemoteException;

    public zzd getView() throws RemoteException;

    public boolean isAdded() throws RemoteException;

    public boolean isDetached() throws RemoteException;

    public boolean isHidden() throws RemoteException;

    public boolean isInLayout() throws RemoteException;

    public boolean isRemoving() throws RemoteException;

    public boolean isResumed() throws RemoteException;

    public boolean isVisible() throws RemoteException;

    public void setHasOptionsMenu(boolean var1) throws RemoteException;

    public void setMenuVisibility(boolean var1) throws RemoteException;

    public void setRetainInstance(boolean var1) throws RemoteException;

    public void setUserVisibleHint(boolean var1) throws RemoteException;

    public void startActivity(Intent var1) throws RemoteException;

    public void startActivityForResult(Intent var1, int var2) throws RemoteException;

    public void zzn(zzd var1) throws RemoteException;

    public void zzo(zzd var1) throws RemoteException;

    public zzd zztV() throws RemoteException;

    public zzc zztW() throws RemoteException;

    public zzd zztX() throws RemoteException;

    public zzc zztY() throws RemoteException;

    public static abstract class com.google.android.gms.dynamic.zzc$zza
    extends Binder
    implements zzc {
        public com.google.android.gms.dynamic.zzc$zza() {
            this.attachInterface(this, "com.google.android.gms.dynamic.IFragmentWrapper");
        }

        public static zzc zzbr(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface("com.google.android.gms.dynamic.IFragmentWrapper");
            if (iInterface != null && iInterface instanceof zzc) {
                return (zzc)iInterface;
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
            void var3_20;
            zzd zzd2 = null;
            Object var19_23 = null;
            Object var20_24 = null;
            Object var21_25 = null;
            Object var22_26 = null;
            Object var23_27 = null;
            Object var17_28 = null;
            int n4 = 0;
            int n5 = 0;
            int n6 = 0;
            int n7 = 0;
            int n8 = 0;
            int n9 = 0;
            int n10 = 0;
            boolean bl2 = false;
            boolean bl3 = false;
            boolean bl4 = false;
            boolean bl5 = false;
            int n11 = 0;
            switch (n2) {
                default: {
                    void var4_21;
                    return super.onTransact(n2, object, (Parcel)var3_20, (int)var4_21);
                }
                case 1598968902: {
                    var3_20.writeString("com.google.android.gms.dynamic.IFragmentWrapper");
                    return true;
                }
                case 2: {
                    void var2_5;
                    object.enforceInterface("com.google.android.gms.dynamic.IFragmentWrapper");
                    zzd2 = this.zztV();
                    var3_20.writeNoException();
                    Object var2_3 = var17_28;
                    if (zzd2 != null) {
                        IBinder iBinder = zzd2.asBinder();
                    }
                    var3_20.writeStrongBinder((IBinder)var2_5);
                    return true;
                }
                case 3: {
                    object.enforceInterface("com.google.android.gms.dynamic.IFragmentWrapper");
                    Bundle bundle = this.getArguments();
                    var3_20.writeNoException();
                    if (bundle != null) {
                        var3_20.writeInt(1);
                        bundle.writeToParcel((Parcel)var3_20, 1);
                        return true;
                    }
                    var3_20.writeInt(0);
                    return true;
                }
                case 4: {
                    object.enforceInterface("com.google.android.gms.dynamic.IFragmentWrapper");
                    n2 = this.getId();
                    var3_20.writeNoException();
                    var3_20.writeInt(n2);
                    return true;
                }
                case 5: {
                    void var2_9;
                    object.enforceInterface("com.google.android.gms.dynamic.IFragmentWrapper");
                    zzc zzc2 = this.zztW();
                    var3_20.writeNoException();
                    zzd zzd3 = zzd2;
                    if (zzc2 != null) {
                        IBinder iBinder = zzc2.asBinder();
                    }
                    var3_20.writeStrongBinder((IBinder)var2_9);
                    return true;
                }
                case 6: {
                    void var2_12;
                    object.enforceInterface("com.google.android.gms.dynamic.IFragmentWrapper");
                    zzd zzd4 = this.zztX();
                    var3_20.writeNoException();
                    Object var2_10 = var19_23;
                    if (zzd4 != null) {
                        IBinder iBinder = zzd4.asBinder();
                    }
                    var3_20.writeStrongBinder((IBinder)var2_12);
                    return true;
                }
                case 7: {
                    object.enforceInterface("com.google.android.gms.dynamic.IFragmentWrapper");
                    bl2 = this.getRetainInstance();
                    var3_20.writeNoException();
                    n2 = bl2 ? 1 : 0;
                    var3_20.writeInt(n2);
                    return true;
                }
                case 8: {
                    object.enforceInterface("com.google.android.gms.dynamic.IFragmentWrapper");
                    String string2 = this.getTag();
                    var3_20.writeNoException();
                    var3_20.writeString(string2);
                    return true;
                }
                case 9: {
                    void var2_16;
                    object.enforceInterface("com.google.android.gms.dynamic.IFragmentWrapper");
                    zzc zzc3 = this.zztY();
                    var3_20.writeNoException();
                    Object var2_14 = var20_24;
                    if (zzc3 != null) {
                        IBinder iBinder = zzc3.asBinder();
                    }
                    var3_20.writeStrongBinder((IBinder)var2_16);
                    return true;
                }
                case 10: {
                    object.enforceInterface("com.google.android.gms.dynamic.IFragmentWrapper");
                    n2 = this.getTargetRequestCode();
                    var3_20.writeNoException();
                    var3_20.writeInt(n2);
                    return true;
                }
                case 11: {
                    object.enforceInterface("com.google.android.gms.dynamic.IFragmentWrapper");
                    bl2 = this.getUserVisibleHint();
                    var3_20.writeNoException();
                    n2 = n11;
                    if (bl2) {
                        n2 = 1;
                    }
                    var3_20.writeInt(n2);
                    return true;
                }
                case 12: {
                    void var2_19;
                    object.enforceInterface("com.google.android.gms.dynamic.IFragmentWrapper");
                    zzd zzd5 = this.getView();
                    var3_20.writeNoException();
                    Object var2_17 = var21_25;
                    if (zzd5 != null) {
                        IBinder iBinder = zzd5.asBinder();
                    }
                    var3_20.writeStrongBinder((IBinder)var2_19);
                    return true;
                }
                case 13: {
                    object.enforceInterface("com.google.android.gms.dynamic.IFragmentWrapper");
                    bl2 = this.isAdded();
                    var3_20.writeNoException();
                    n2 = n4;
                    if (bl2) {
                        n2 = 1;
                    }
                    var3_20.writeInt(n2);
                    return true;
                }
                case 14: {
                    object.enforceInterface("com.google.android.gms.dynamic.IFragmentWrapper");
                    bl2 = this.isDetached();
                    var3_20.writeNoException();
                    n2 = n5;
                    if (bl2) {
                        n2 = 1;
                    }
                    var3_20.writeInt(n2);
                    return true;
                }
                case 15: {
                    object.enforceInterface("com.google.android.gms.dynamic.IFragmentWrapper");
                    bl2 = this.isHidden();
                    var3_20.writeNoException();
                    n2 = n6;
                    if (bl2) {
                        n2 = 1;
                    }
                    var3_20.writeInt(n2);
                    return true;
                }
                case 16: {
                    object.enforceInterface("com.google.android.gms.dynamic.IFragmentWrapper");
                    bl2 = this.isInLayout();
                    var3_20.writeNoException();
                    n2 = n7;
                    if (bl2) {
                        n2 = 1;
                    }
                    var3_20.writeInt(n2);
                    return true;
                }
                case 17: {
                    object.enforceInterface("com.google.android.gms.dynamic.IFragmentWrapper");
                    bl2 = this.isRemoving();
                    var3_20.writeNoException();
                    n2 = n8;
                    if (bl2) {
                        n2 = 1;
                    }
                    var3_20.writeInt(n2);
                    return true;
                }
                case 18: {
                    object.enforceInterface("com.google.android.gms.dynamic.IFragmentWrapper");
                    bl2 = this.isResumed();
                    var3_20.writeNoException();
                    n2 = n9;
                    if (bl2) {
                        n2 = 1;
                    }
                    var3_20.writeInt(n2);
                    return true;
                }
                case 19: {
                    object.enforceInterface("com.google.android.gms.dynamic.IFragmentWrapper");
                    bl2 = this.isVisible();
                    var3_20.writeNoException();
                    n2 = n10;
                    if (bl2) {
                        n2 = 1;
                    }
                    var3_20.writeInt(n2);
                    return true;
                }
                case 20: {
                    object.enforceInterface("com.google.android.gms.dynamic.IFragmentWrapper");
                    this.zzn(zzd.zza.zzbs(object.readStrongBinder()));
                    var3_20.writeNoException();
                    return true;
                }
                case 21: {
                    object.enforceInterface("com.google.android.gms.dynamic.IFragmentWrapper");
                    if (object.readInt() != 0) {
                        bl2 = true;
                    }
                    this.setHasOptionsMenu(bl2);
                    var3_20.writeNoException();
                    return true;
                }
                case 22: {
                    object.enforceInterface("com.google.android.gms.dynamic.IFragmentWrapper");
                    bl2 = bl3;
                    if (object.readInt() != 0) {
                        bl2 = true;
                    }
                    this.setMenuVisibility(bl2);
                    var3_20.writeNoException();
                    return true;
                }
                case 23: {
                    object.enforceInterface("com.google.android.gms.dynamic.IFragmentWrapper");
                    bl2 = bl4;
                    if (object.readInt() != 0) {
                        bl2 = true;
                    }
                    this.setRetainInstance(bl2);
                    var3_20.writeNoException();
                    return true;
                }
                case 24: {
                    object.enforceInterface("com.google.android.gms.dynamic.IFragmentWrapper");
                    bl2 = bl5;
                    if (object.readInt() != 0) {
                        bl2 = true;
                    }
                    this.setUserVisibleHint(bl2);
                    var3_20.writeNoException();
                    return true;
                }
                case 25: {
                    void var17_35;
                    object.enforceInterface("com.google.android.gms.dynamic.IFragmentWrapper");
                    Object var17_33 = var22_26;
                    if (object.readInt() != 0) {
                        Intent intent = (Intent)Intent.CREATOR.createFromParcel(object);
                    }
                    this.startActivity((Intent)var17_35);
                    var3_20.writeNoException();
                    return true;
                }
                case 26: {
                    void var17_38;
                    object.enforceInterface("com.google.android.gms.dynamic.IFragmentWrapper");
                    Object var17_36 = var23_27;
                    if (object.readInt() != 0) {
                        Intent intent = (Intent)Intent.CREATOR.createFromParcel(object);
                    }
                    this.startActivityForResult((Intent)var17_38, object.readInt());
                    var3_20.writeNoException();
                    return true;
                }
                case 27: 
            }
            object.enforceInterface("com.google.android.gms.dynamic.IFragmentWrapper");
            this.zzo(zzd.zza.zzbs(object.readStrongBinder()));
            var3_20.writeNoException();
            return true;
        }

        private static class zza
        implements zzc {
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
            public Bundle getArguments() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
                    this.zzoz.transact(3, parcel, parcel2, 0);
                    parcel2.readException();
                    Bundle bundle = parcel2.readInt() != 0 ? (Bundle)Bundle.CREATOR.createFromParcel(parcel2) : null;
                    return bundle;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int getId() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
                    this.zzoz.transact(4, parcel, parcel2, 0);
                    parcel2.readException();
                    int n2 = parcel2.readInt();
                    return n2;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public boolean getRetainInstance() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                boolean bl2;
                block2: {
                    bl2 = false;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
                        this.zzoz.transact(7, parcel2, parcel, 0);
                        parcel.readException();
                        int n2 = parcel.readInt();
                        if (n2 == 0) break block2;
                        bl2 = true;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.recycle();
                parcel2.recycle();
                return bl2;
            }

            @Override
            public String getTag() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
                    this.zzoz.transact(8, parcel, parcel2, 0);
                    parcel2.readException();
                    String string2 = parcel2.readString();
                    return string2;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int getTargetRequestCode() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
                    this.zzoz.transact(10, parcel, parcel2, 0);
                    parcel2.readException();
                    int n2 = parcel2.readInt();
                    return n2;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public boolean getUserVisibleHint() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                boolean bl2;
                block2: {
                    bl2 = false;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
                        this.zzoz.transact(11, parcel2, parcel, 0);
                        parcel.readException();
                        int n2 = parcel.readInt();
                        if (n2 == 0) break block2;
                        bl2 = true;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.recycle();
                parcel2.recycle();
                return bl2;
            }

            @Override
            public zzd getView() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
                    this.zzoz.transact(12, parcel, parcel2, 0);
                    parcel2.readException();
                    zzd zzd2 = zzd.zza.zzbs(parcel2.readStrongBinder());
                    return zzd2;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public boolean isAdded() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                boolean bl2;
                block2: {
                    bl2 = false;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
                        this.zzoz.transact(13, parcel2, parcel, 0);
                        parcel.readException();
                        int n2 = parcel.readInt();
                        if (n2 == 0) break block2;
                        bl2 = true;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.recycle();
                parcel2.recycle();
                return bl2;
            }

            @Override
            public boolean isDetached() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                boolean bl2;
                block2: {
                    bl2 = false;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
                        this.zzoz.transact(14, parcel2, parcel, 0);
                        parcel.readException();
                        int n2 = parcel.readInt();
                        if (n2 == 0) break block2;
                        bl2 = true;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.recycle();
                parcel2.recycle();
                return bl2;
            }

            @Override
            public boolean isHidden() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                boolean bl2;
                block2: {
                    bl2 = false;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
                        this.zzoz.transact(15, parcel2, parcel, 0);
                        parcel.readException();
                        int n2 = parcel.readInt();
                        if (n2 == 0) break block2;
                        bl2 = true;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.recycle();
                parcel2.recycle();
                return bl2;
            }

            @Override
            public boolean isInLayout() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                boolean bl2;
                block2: {
                    bl2 = false;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
                        this.zzoz.transact(16, parcel2, parcel, 0);
                        parcel.readException();
                        int n2 = parcel.readInt();
                        if (n2 == 0) break block2;
                        bl2 = true;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.recycle();
                parcel2.recycle();
                return bl2;
            }

            @Override
            public boolean isRemoving() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                boolean bl2;
                block2: {
                    bl2 = false;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
                        this.zzoz.transact(17, parcel2, parcel, 0);
                        parcel.readException();
                        int n2 = parcel.readInt();
                        if (n2 == 0) break block2;
                        bl2 = true;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.recycle();
                parcel2.recycle();
                return bl2;
            }

            @Override
            public boolean isResumed() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                boolean bl2;
                block2: {
                    bl2 = false;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
                        this.zzoz.transact(18, parcel2, parcel, 0);
                        parcel.readException();
                        int n2 = parcel.readInt();
                        if (n2 == 0) break block2;
                        bl2 = true;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.recycle();
                parcel2.recycle();
                return bl2;
            }

            @Override
            public boolean isVisible() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                boolean bl2;
                block2: {
                    bl2 = false;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
                        this.zzoz.transact(19, parcel2, parcel, 0);
                        parcel.readException();
                        int n2 = parcel.readInt();
                        if (n2 == 0) break block2;
                        bl2 = true;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.recycle();
                parcel2.recycle();
                return bl2;
            }

            @Override
            public void setHasOptionsMenu(boolean bl2) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                int n2;
                block4: {
                    n2 = 0;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    parcel2.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
                    if (!bl2) break block4;
                    n2 = 1;
                }
                try {
                    parcel2.writeInt(n2);
                    this.zzoz.transact(21, parcel2, parcel, 0);
                    parcel.readException();
                    return;
                }
                finally {
                    parcel.recycle();
                    parcel2.recycle();
                }
            }

            @Override
            public void setMenuVisibility(boolean bl2) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                int n2;
                block4: {
                    n2 = 0;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    parcel2.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
                    if (!bl2) break block4;
                    n2 = 1;
                }
                try {
                    parcel2.writeInt(n2);
                    this.zzoz.transact(22, parcel2, parcel, 0);
                    parcel.readException();
                    return;
                }
                finally {
                    parcel.recycle();
                    parcel2.recycle();
                }
            }

            @Override
            public void setRetainInstance(boolean bl2) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                int n2;
                block4: {
                    n2 = 0;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    parcel2.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
                    if (!bl2) break block4;
                    n2 = 1;
                }
                try {
                    parcel2.writeInt(n2);
                    this.zzoz.transact(23, parcel2, parcel, 0);
                    parcel.readException();
                    return;
                }
                finally {
                    parcel.recycle();
                    parcel2.recycle();
                }
            }

            @Override
            public void setUserVisibleHint(boolean bl2) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                int n2;
                block4: {
                    n2 = 0;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    parcel2.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
                    if (!bl2) break block4;
                    n2 = 1;
                }
                try {
                    parcel2.writeInt(n2);
                    this.zzoz.transact(24, parcel2, parcel, 0);
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
            public void startActivity(Intent intent) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
                    if (intent != null) {
                        parcel.writeInt(1);
                        intent.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
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
            public void startActivityForResult(Intent intent, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
                    if (intent != null) {
                        parcel.writeInt(1);
                        intent.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n2);
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
            public void zzn(zzd zzd2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
                    zzd2 = zzd2 != null ? zzd2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)zzd2);
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
            public void zzo(zzd zzd2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
                    zzd2 = zzd2 != null ? zzd2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)zzd2);
                    this.zzoz.transact(27, parcel, parcel2, 0);
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public zzd zztV() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
                    this.zzoz.transact(2, parcel, parcel2, 0);
                    parcel2.readException();
                    zzd zzd2 = zzd.zza.zzbs(parcel2.readStrongBinder());
                    return zzd2;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public zzc zztW() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
                    this.zzoz.transact(5, parcel, parcel2, 0);
                    parcel2.readException();
                    zzc zzc2 = com.google.android.gms.dynamic.zzc$zza.zzbr(parcel2.readStrongBinder());
                    return zzc2;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public zzd zztX() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
                    this.zzoz.transact(6, parcel, parcel2, 0);
                    parcel2.readException();
                    zzd zzd2 = zzd.zza.zzbs(parcel2.readStrongBinder());
                    return zzd2;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public zzc zztY() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
                    this.zzoz.transact(9, parcel, parcel2, 0);
                    parcel2.readException();
                    zzc zzc2 = com.google.android.gms.dynamic.zzc$zza.zzbr(parcel2.readStrongBinder());
                    return zzc2;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }
        }
    }
}

