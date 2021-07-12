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

public interface ai
extends IInterface {
    public void onGestureDetected(int var1) throws RemoteException;

    public static abstract class mobvoiapi.ai$a
    extends Binder
    implements ai {
        public mobvoiapi.ai$a() {
            this.attachInterface(this, "com.mobvoi.android.gesture.internal.IGestureServiceCallback");
        }

        public static ai asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface("com.mobvoi.android.gesture.internal.IGestureServiceCallback");
            if (iInterface != null && iInterface instanceof ai) {
                return (ai)iInterface;
            }
            return new a(iBinder);
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int n2, Parcel parcel, Parcel parcel2, int n3) throws RemoteException {
            switch (n2) {
                default: {
                    return super.onTransact(n2, parcel, parcel2, n3);
                }
                case 1598968902: {
                    parcel2.writeString("com.mobvoi.android.gesture.internal.IGestureServiceCallback");
                    return true;
                }
                case 1: 
            }
            parcel.enforceInterface("com.mobvoi.android.gesture.internal.IGestureServiceCallback");
            this.onGestureDetected(parcel.readInt());
            parcel2.writeNoException();
            return true;
        }

        static class a
        implements ai {
            private IBinder a;

            a(IBinder iBinder) {
                this.a = iBinder;
            }

            public IBinder asBinder() {
                return this.a;
            }

            @Override
            public void onGestureDetected(int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.mobvoi.android.gesture.internal.IGestureServiceCallback");
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
        }
    }
}

