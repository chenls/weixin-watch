/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 *  android.os.Binder
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.Parcel
 *  android.os.RemoteException
 */
package mobvoiapi;

import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.mobvoi.android.wearable.Asset;
import com.mobvoi.android.wearable.PutDataRequest;
import com.mobvoi.android.wearable.internal.AddListenerRequest;
import com.mobvoi.android.wearable.internal.ConnectionConfiguration;
import com.mobvoi.android.wearable.internal.RemoveListenerRequest;
import mobvoiapi.bg;

public interface bi
extends IInterface {
    public void a(bg var1) throws RemoteException;

    public void a(bg var1, Uri var2) throws RemoteException;

    public void a(bg var1, Asset var2) throws RemoteException;

    public void a(bg var1, PutDataRequest var2) throws RemoteException;

    public void a(bg var1, AddListenerRequest var2) throws RemoteException;

    public void a(bg var1, ConnectionConfiguration var2) throws RemoteException;

    public void a(bg var1, RemoveListenerRequest var2) throws RemoteException;

    public void a(bg var1, String var2, String var3, byte[] var4) throws RemoteException;

    public void a(bg var1, boolean var2) throws RemoteException;

    public void b(bg var1) throws RemoteException;

    public void b(bg var1, Uri var2) throws RemoteException;

    public void c(bg var1) throws RemoteException;

    public void c(bg var1, Uri var2) throws RemoteException;

    public void d(bg var1) throws RemoteException;

    public void e(bg var1) throws RemoteException;

    public void f(bg var1) throws RemoteException;

    public void g(bg var1) throws RemoteException;

    public static abstract class mobvoiapi.bi$a
    extends Binder
    implements bi {
        public static bi a(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface("com.mobvoi.android.wearable.internal.IWearableService");
            if (iInterface != null && iInterface instanceof bi) {
                return (bi)iInterface;
            }
            return new a(iBinder);
        }

        /*
         * Enabled aggressive block sorting
         */
        public boolean onTransact(int n2, Parcel parcel, Parcel parcel2, int n3) throws RemoteException {
            bg bg2 = null;
            bg bg3 = null;
            Object var9_7 = null;
            Object var10_8 = null;
            Object var11_9 = null;
            Object var12_10 = null;
            Object var13_11 = null;
            Object object = null;
            switch (n2) {
                default: {
                    return super.onTransact(n2, parcel, parcel2, n3);
                }
                case 1598968902: {
                    parcel2.writeString("com.mobvoi.android.wearable.internal.IWearableService");
                    return true;
                }
                case 1: {
                    parcel.enforceInterface("com.mobvoi.android.wearable.internal.IWearableService");
                    bg2 = bg.a.a(parcel.readStrongBinder());
                    if (parcel.readInt() != 0) {
                        object = (ConnectionConfiguration)ConnectionConfiguration.CREATOR.createFromParcel(parcel);
                    }
                    this.a(bg2, (ConnectionConfiguration)object);
                    parcel2.writeNoException();
                    return true;
                }
                case 2: {
                    parcel.enforceInterface("com.mobvoi.android.wearable.internal.IWearableService");
                    this.a(bg.a.a(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                }
                case 3: {
                    parcel.enforceInterface("com.mobvoi.android.wearable.internal.IWearableService");
                    this.a(bg.a.a(parcel.readStrongBinder()), parcel.readString(), parcel.readString(), parcel.createByteArray());
                    parcel2.writeNoException();
                    return true;
                }
                case 4: {
                    parcel.enforceInterface("com.mobvoi.android.wearable.internal.IWearableService");
                    bg3 = bg.a.a(parcel.readStrongBinder());
                    object = bg2;
                    if (parcel.readInt() != 0) {
                        object = (Uri)Uri.CREATOR.createFromParcel(parcel);
                    }
                    this.a(bg3, (Uri)object);
                    parcel2.writeNoException();
                    return true;
                }
                case 5: {
                    parcel.enforceInterface("com.mobvoi.android.wearable.internal.IWearableService");
                    bg2 = bg.a.a(parcel.readStrongBinder());
                    object = bg3;
                    if (parcel.readInt() != 0) {
                        object = (PutDataRequest)PutDataRequest.CREATOR.createFromParcel(parcel);
                    }
                    this.a(bg2, (PutDataRequest)object);
                    parcel2.writeNoException();
                    return true;
                }
                case 6: {
                    parcel.enforceInterface("com.mobvoi.android.wearable.internal.IWearableService");
                    bg2 = bg.a.a(parcel.readStrongBinder());
                    object = var9_7;
                    if (parcel.readInt() != 0) {
                        object = (Uri)Uri.CREATOR.createFromParcel(parcel);
                    }
                    this.b(bg2, (Uri)object);
                    parcel2.writeNoException();
                    return true;
                }
                case 7: {
                    parcel.enforceInterface("com.mobvoi.android.wearable.internal.IWearableService");
                    bg2 = bg.a.a(parcel.readStrongBinder());
                    object = var10_8;
                    if (parcel.readInt() != 0) {
                        object = (Uri)Uri.CREATOR.createFromParcel(parcel);
                    }
                    this.c(bg2, (Uri)object);
                    parcel2.writeNoException();
                    return true;
                }
                case 8: {
                    parcel.enforceInterface("com.mobvoi.android.wearable.internal.IWearableService");
                    bg2 = bg.a.a(parcel.readStrongBinder());
                    object = var11_9;
                    if (parcel.readInt() != 0) {
                        object = (Asset)Asset.CREATOR.createFromParcel(parcel);
                    }
                    this.a(bg2, (Asset)object);
                    parcel2.writeNoException();
                    return true;
                }
                case 9: {
                    parcel.enforceInterface("com.mobvoi.android.wearable.internal.IWearableService");
                    this.b(bg.a.a(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                }
                case 10: {
                    parcel.enforceInterface("com.mobvoi.android.wearable.internal.IWearableService");
                    this.c(bg.a.a(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                }
                case 11: {
                    parcel.enforceInterface("com.mobvoi.android.wearable.internal.IWearableService");
                    this.d(bg.a.a(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                }
                case 12: {
                    parcel.enforceInterface("com.mobvoi.android.wearable.internal.IWearableService");
                    this.e(bg.a.a(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                }
                case 13: {
                    parcel.enforceInterface("com.mobvoi.android.wearable.internal.IWearableService");
                    bg2 = bg.a.a(parcel.readStrongBinder());
                    object = var12_10;
                    if (parcel.readInt() != 0) {
                        object = (AddListenerRequest)AddListenerRequest.CREATOR.createFromParcel(parcel);
                    }
                    this.a(bg2, (AddListenerRequest)object);
                    parcel2.writeNoException();
                    return true;
                }
                case 14: {
                    parcel.enforceInterface("com.mobvoi.android.wearable.internal.IWearableService");
                    bg2 = bg.a.a(parcel.readStrongBinder());
                    object = var13_11;
                    if (parcel.readInt() != 0) {
                        object = (RemoveListenerRequest)RemoveListenerRequest.CREATOR.createFromParcel(parcel);
                    }
                    this.a(bg2, (RemoveListenerRequest)object);
                    parcel2.writeNoException();
                    return true;
                }
                case 15: {
                    parcel.enforceInterface("com.mobvoi.android.wearable.internal.IWearableService");
                    object = bg.a.a(parcel.readStrongBinder());
                    boolean bl2 = parcel.readInt() != 0;
                    this.a((bg)object, bl2);
                    parcel2.writeNoException();
                    return true;
                }
                case 16: {
                    parcel.enforceInterface("com.mobvoi.android.wearable.internal.IWearableService");
                    this.f(bg.a.a(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                }
                case 17: 
            }
            parcel.enforceInterface("com.mobvoi.android.wearable.internal.IWearableService");
            this.g(bg.a.a(parcel.readStrongBinder()));
            parcel2.writeNoException();
            return true;
        }

        static class a
        implements bi {
            private IBinder a;

            a(IBinder iBinder) {
                this.a = iBinder;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void a(bg bg2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.mobvoi.android.wearable.internal.IWearableService");
                    bg2 = bg2 != null ? bg2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)bg2);
                    this.a.transact(2, parcel, parcel2, 0);
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
            public void a(bg bg2, Uri uri) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.mobvoi.android.wearable.internal.IWearableService");
                    bg2 = bg2 != null ? bg2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)bg2);
                    if (uri != null) {
                        parcel.writeInt(1);
                        uri.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.a.transact(4, parcel, parcel2, 0);
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
            public void a(bg bg2, Asset asset) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.mobvoi.android.wearable.internal.IWearableService");
                    bg2 = bg2 != null ? bg2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)bg2);
                    if (asset != null) {
                        parcel.writeInt(1);
                        asset.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.a.transact(8, parcel, parcel2, 0);
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
            public void a(bg bg2, PutDataRequest putDataRequest) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.mobvoi.android.wearable.internal.IWearableService");
                    bg2 = bg2 != null ? bg2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)bg2);
                    if (putDataRequest != null) {
                        parcel.writeInt(1);
                        putDataRequest.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.a.transact(5, parcel, parcel2, 0);
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
            public void a(bg bg2, AddListenerRequest addListenerRequest) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.mobvoi.android.wearable.internal.IWearableService");
                    bg2 = bg2 != null ? bg2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)bg2);
                    if (addListenerRequest != null) {
                        parcel.writeInt(1);
                        addListenerRequest.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.a.transact(13, parcel, parcel2, 0);
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
            public void a(bg bg2, ConnectionConfiguration connectionConfiguration) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.mobvoi.android.wearable.internal.IWearableService");
                    bg2 = bg2 != null ? bg2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)bg2);
                    if (connectionConfiguration != null) {
                        parcel.writeInt(1);
                        connectionConfiguration.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.a.transact(1, parcel, parcel2, 0);
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
            public void a(bg bg2, RemoveListenerRequest removeListenerRequest) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.mobvoi.android.wearable.internal.IWearableService");
                    bg2 = bg2 != null ? bg2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)bg2);
                    if (removeListenerRequest != null) {
                        parcel.writeInt(1);
                        removeListenerRequest.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.a.transact(14, parcel, parcel2, 0);
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
            public void a(bg bg2, String string2, String string3, byte[] byArray) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.mobvoi.android.wearable.internal.IWearableService");
                    bg2 = bg2 != null ? bg2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)bg2);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    parcel.writeByteArray(byArray);
                    this.a.transact(3, parcel, parcel2, 0);
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
            public void a(bg bg2, boolean bl2) throws RemoteException {
                int n2 = 0;
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.mobvoi.android.wearable.internal.IWearableService");
                    bg2 = bg2 != null ? bg2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)bg2);
                    if (bl2) {
                        n2 = 1;
                    }
                    parcel.writeInt(n2);
                    this.a.transact(15, parcel, parcel2, 0);
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            public IBinder asBinder() {
                return this.a;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void b(bg bg2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.mobvoi.android.wearable.internal.IWearableService");
                    bg2 = bg2 != null ? bg2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)bg2);
                    this.a.transact(9, parcel, parcel2, 0);
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
            public void b(bg bg2, Uri uri) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.mobvoi.android.wearable.internal.IWearableService");
                    bg2 = bg2 != null ? bg2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)bg2);
                    if (uri != null) {
                        parcel.writeInt(1);
                        uri.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.a.transact(6, parcel, parcel2, 0);
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
            public void c(bg bg2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.mobvoi.android.wearable.internal.IWearableService");
                    bg2 = bg2 != null ? bg2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)bg2);
                    this.a.transact(10, parcel, parcel2, 0);
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
            public void c(bg bg2, Uri uri) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.mobvoi.android.wearable.internal.IWearableService");
                    bg2 = bg2 != null ? bg2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)bg2);
                    if (uri != null) {
                        parcel.writeInt(1);
                        uri.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.a.transact(7, parcel, parcel2, 0);
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
            public void d(bg bg2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.mobvoi.android.wearable.internal.IWearableService");
                    bg2 = bg2 != null ? bg2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)bg2);
                    this.a.transact(11, parcel, parcel2, 0);
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
            public void e(bg bg2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.mobvoi.android.wearable.internal.IWearableService");
                    bg2 = bg2 != null ? bg2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)bg2);
                    this.a.transact(12, parcel, parcel2, 0);
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
            public void f(bg bg2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.mobvoi.android.wearable.internal.IWearableService");
                    bg2 = bg2 != null ? bg2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)bg2);
                    this.a.transact(16, parcel, parcel2, 0);
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
            public void g(bg bg2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.mobvoi.android.wearable.internal.IWearableService");
                    bg2 = bg2 != null ? bg2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)bg2);
                    this.a.transact(17, parcel, parcel2, 0);
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

