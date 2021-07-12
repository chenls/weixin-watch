/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.location.Location
 *  android.os.Binder
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.Parcel
 *  android.os.RemoteException
 */
package mobvoiapi;

import android.location.Location;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface al
extends IInterface {
    public void a(Location var1) throws RemoteException;

    public static abstract class mobvoiapi.al$a
    extends Binder
    implements al {
        public mobvoiapi.al$a() {
            this.attachInterface(this, "com.mobvoi.android.location.internal.ILocationListener");
        }

        public static al a(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface("com.mobvoi.android.location.internal.ILocationListener");
            if (iInterface != null && iInterface instanceof al) {
                return (al)iInterface;
            }
            return new a(iBinder);
        }

        public IBinder asBinder() {
            return this;
        }

        /*
         * Enabled aggressive block sorting
         */
        public boolean onTransact(int n2, Parcel object, Parcel parcel, int n3) throws RemoteException {
            switch (n2) {
                default: {
                    return super.onTransact(n2, object, parcel, n3);
                }
                case 1598968902: {
                    parcel.writeString("com.mobvoi.android.location.internal.ILocationListener");
                    return true;
                }
                case 1: 
            }
            object.enforceInterface("com.mobvoi.android.location.internal.ILocationListener");
            object = object.readInt() != 0 ? (Location)Location.CREATOR.createFromParcel(object) : null;
            this.a((Location)object);
            parcel.writeNoException();
            return true;
        }

        static class a
        implements al {
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
            public void a(Location location) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.mobvoi.android.location.internal.ILocationListener");
                    if (location != null) {
                        parcel.writeInt(1);
                        location.writeToParcel(parcel, 0);
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

            public IBinder asBinder() {
                return this.a;
            }
        }
    }
}

