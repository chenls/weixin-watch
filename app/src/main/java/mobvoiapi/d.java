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
package mobvoiapi;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import mobvoiapi.c;

public interface d
extends IInterface {
    public void a(c var1, int var2, String var3) throws RemoteException;

    public void b(c var1, int var2, String var3) throws RemoteException;

    public void c(c var1, int var2, String var3) throws RemoteException;

    public void d(c var1, int var2, String var3) throws RemoteException;

    public static abstract class mobvoiapi.d$a
    extends Binder
    implements d {
        public static d a(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface("com.mobvoi.android.common.internal.IMmsServiceBroker");
            if (iInterface != null && iInterface instanceof d) {
                return (d)iInterface;
            }
            return new a(iBinder);
        }

        public boolean onTransact(int n2, Parcel parcel, Parcel parcel2, int n3) throws RemoteException {
            switch (n2) {
                default: {
                    return super.onTransact(n2, parcel, parcel2, n3);
                }
                case 1598968902: {
                    parcel2.writeString("com.mobvoi.android.common.internal.IMmsServiceBroker");
                    return true;
                }
                case 1: {
                    parcel.enforceInterface("com.mobvoi.android.common.internal.IMmsServiceBroker");
                    this.a(c.a.a(parcel.readStrongBinder()), parcel.readInt(), parcel.readString());
                    parcel2.writeNoException();
                    return true;
                }
                case 2: {
                    parcel.enforceInterface("com.mobvoi.android.common.internal.IMmsServiceBroker");
                    this.b(c.a.a(parcel.readStrongBinder()), parcel.readInt(), parcel.readString());
                    parcel2.writeNoException();
                    return true;
                }
                case 3: {
                    parcel.enforceInterface("com.mobvoi.android.common.internal.IMmsServiceBroker");
                    this.c(c.a.a(parcel.readStrongBinder()), parcel.readInt(), parcel.readString());
                    parcel2.writeNoException();
                    return true;
                }
                case 4: 
            }
            parcel.enforceInterface("com.mobvoi.android.common.internal.IMmsServiceBroker");
            this.d(c.a.a(parcel.readStrongBinder()), parcel.readInt(), parcel.readString());
            parcel2.writeNoException();
            return true;
        }

        static class a
        implements d {
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
            public void a(c c2, int n2, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.mobvoi.android.common.internal.IMmsServiceBroker");
                    c2 = c2 != null ? c2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)c2);
                    parcel.writeInt(n2);
                    parcel.writeString(string2);
                    this.a.transact(1, parcel, parcel2, 0);
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
            public void b(c c2, int n2, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.mobvoi.android.common.internal.IMmsServiceBroker");
                    c2 = c2 != null ? c2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)c2);
                    parcel.writeInt(n2);
                    parcel.writeString(string2);
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
            public void c(c c2, int n2, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.mobvoi.android.common.internal.IMmsServiceBroker");
                    c2 = c2 != null ? c2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)c2);
                    parcel.writeInt(n2);
                    parcel.writeString(string2);
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
            public void d(c c2, int n2, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.mobvoi.android.common.internal.IMmsServiceBroker");
                    c2 = c2 != null ? c2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)c2);
                    parcel.writeInt(n2);
                    parcel.writeString(string2);
                    this.a.transact(4, parcel, parcel2, 0);
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

