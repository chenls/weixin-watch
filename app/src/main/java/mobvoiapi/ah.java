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
import mobvoiapi.ai;

public interface ah
extends IInterface {
    public void a(ai var1, int var2) throws RemoteException;

    public void b(ai var1, int var2) throws RemoteException;

    public static abstract class mobvoiapi.ah$a
    extends Binder
    implements ah {
        public static ah a(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface("com.mobvoi.android.gesture.internal.IGestureService");
            if (iInterface != null && iInterface instanceof ah) {
                return (ah)iInterface;
            }
            return new a(iBinder);
        }

        public boolean onTransact(int n2, Parcel parcel, Parcel parcel2, int n3) throws RemoteException {
            switch (n2) {
                default: {
                    return super.onTransact(n2, parcel, parcel2, n3);
                }
                case 1598968902: {
                    parcel2.writeString("com.mobvoi.android.gesture.internal.IGestureService");
                    return true;
                }
                case 1: {
                    parcel.enforceInterface("com.mobvoi.android.gesture.internal.IGestureService");
                    this.a(ai.a.asInterface(parcel.readStrongBinder()), parcel.readInt());
                    parcel2.writeNoException();
                    return true;
                }
                case 2: 
            }
            parcel.enforceInterface("com.mobvoi.android.gesture.internal.IGestureService");
            this.b(ai.a.asInterface(parcel.readStrongBinder()), parcel.readInt());
            parcel2.writeNoException();
            return true;
        }

        static class a
        implements ah {
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
            public void a(ai ai2, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.mobvoi.android.gesture.internal.IGestureService");
                    ai2 = ai2 != null ? ai2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)ai2);
                    parcel.writeInt(n2);
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
            public void b(ai ai2, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.mobvoi.android.gesture.internal.IGestureService");
                    ai2 = ai2 != null ? ai2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)ai2);
                    parcel.writeInt(n2);
                    this.a.transact(2, parcel, parcel2, 0);
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

