/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.app.PendingIntent
 *  android.location.Address
 *  android.location.Location
 *  android.os.Binder
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.Parcel
 *  android.os.RemoteException
 */
package mobvoiapi;

import android.app.PendingIntent;
import android.location.Address;
import android.location.Location;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.mobvoi.android.location.internal.LocationRequestInternal;
import mobvoiapi.ak;
import mobvoiapi.al;

public interface am
extends IInterface {
    public Location a() throws RemoteException;

    public void a(ak var1, PendingIntent var2) throws RemoteException;

    public void a(ak var1, Address var2) throws RemoteException;

    public void a(ak var1, LocationRequestInternal var2, PendingIntent var3) throws RemoteException;

    public void a(ak var1, LocationRequestInternal var2, al var3) throws RemoteException;

    public void a(ak var1, al var2) throws RemoteException;

    public static abstract class mobvoiapi.am$a
    extends Binder
    implements am {
        public static am a(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface("com.mobvoi.android.location.internal.ILocationService");
            if (iInterface != null && iInterface instanceof am) {
                return (am)iInterface;
            }
            return new a(iBinder);
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
                    parcel.writeString("com.mobvoi.android.location.internal.ILocationService");
                    return true;
                }
                case 1: {
                    object.enforceInterface("com.mobvoi.android.location.internal.ILocationService");
                    object = this.a();
                    parcel.writeNoException();
                    if (object != null) {
                        parcel.writeInt(1);
                        object.writeToParcel(parcel, 1);
                        return true;
                    }
                    parcel.writeInt(0);
                    return true;
                }
                case 2: {
                    object.enforceInterface("com.mobvoi.android.location.internal.ILocationService");
                    ak ak2 = ak.a.a(object.readStrongBinder());
                    LocationRequestInternal locationRequestInternal = object.readInt() != 0 ? (LocationRequestInternal)LocationRequestInternal.CREATOR.createFromParcel(object) : null;
                    object = object.readInt() != 0 ? (PendingIntent)PendingIntent.CREATOR.createFromParcel(object) : null;
                    this.a(ak2, locationRequestInternal, (PendingIntent)object);
                    parcel.writeNoException();
                    return true;
                }
                case 3: {
                    object.enforceInterface("com.mobvoi.android.location.internal.ILocationService");
                    ak ak3 = ak.a.a(object.readStrongBinder());
                    LocationRequestInternal locationRequestInternal = object.readInt() != 0 ? (LocationRequestInternal)LocationRequestInternal.CREATOR.createFromParcel(object) : null;
                    this.a(ak3, locationRequestInternal, al.a.a(object.readStrongBinder()));
                    parcel.writeNoException();
                    return true;
                }
                case 4: {
                    object.enforceInterface("com.mobvoi.android.location.internal.ILocationService");
                    ak ak4 = ak.a.a(object.readStrongBinder());
                    object = object.readInt() != 0 ? (PendingIntent)PendingIntent.CREATOR.createFromParcel(object) : null;
                    this.a(ak4, (PendingIntent)object);
                    parcel.writeNoException();
                    return true;
                }
                case 5: {
                    object.enforceInterface("com.mobvoi.android.location.internal.ILocationService");
                    this.a(ak.a.a(object.readStrongBinder()), al.a.a(object.readStrongBinder()));
                    parcel.writeNoException();
                    return true;
                }
                case 6: 
            }
            object.enforceInterface("com.mobvoi.android.location.internal.ILocationService");
            ak ak5 = ak.a.a(object.readStrongBinder());
            object = object.readInt() != 0 ? (Address)Address.CREATOR.createFromParcel(object) : null;
            this.a(ak5, (Address)object);
            parcel.writeNoException();
            return true;
        }

        static class a
        implements am {
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
            public Location a() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.mobvoi.android.location.internal.ILocationService");
                    this.a.transact(1, parcel, parcel2, 0);
                    parcel2.readException();
                    Location location = parcel2.readInt() != 0 ? (Location)Location.CREATOR.createFromParcel(parcel2) : null;
                    return location;
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
            public void a(ak ak2, PendingIntent pendingIntent) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.mobvoi.android.location.internal.ILocationService");
                    ak2 = ak2 != null ? ak2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)ak2);
                    if (pendingIntent != null) {
                        parcel.writeInt(1);
                        pendingIntent.writeToParcel(parcel, 0);
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
            public void a(ak ak2, Address address) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.mobvoi.android.location.internal.ILocationService");
                    ak2 = ak2 != null ? ak2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)ak2);
                    if (address != null) {
                        parcel.writeInt(1);
                        address.writeToParcel(parcel, 0);
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
            public void a(ak ak2, LocationRequestInternal locationRequestInternal, PendingIntent pendingIntent) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.mobvoi.android.location.internal.ILocationService");
                    ak2 = ak2 != null ? ak2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)ak2);
                    if (locationRequestInternal != null) {
                        parcel.writeInt(1);
                        locationRequestInternal.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (pendingIntent != null) {
                        parcel.writeInt(1);
                        pendingIntent.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
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
            public void a(ak ak2, LocationRequestInternal locationRequestInternal, al al2) throws RemoteException {
                Object var4_5 = null;
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.mobvoi.android.location.internal.ILocationService");
                    ak2 = ak2 != null ? ak2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)ak2);
                    if (locationRequestInternal != null) {
                        parcel.writeInt(1);
                        locationRequestInternal.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    ak2 = var4_5;
                    if (al2 != null) {
                        ak2 = al2.asBinder();
                    }
                    parcel.writeStrongBinder((IBinder)ak2);
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
            public void a(ak ak2, al al2) throws RemoteException {
                Object var3_4 = null;
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.mobvoi.android.location.internal.ILocationService");
                    ak2 = ak2 != null ? ak2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)ak2);
                    ak2 = var3_4;
                    if (al2 != null) {
                        ak2 = al2.asBinder();
                    }
                    parcel.writeStrongBinder((IBinder)ak2);
                    this.a.transact(5, parcel, parcel2, 0);
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

