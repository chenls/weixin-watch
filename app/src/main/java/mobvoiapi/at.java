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
import com.mobvoi.android.search.internal.OneboxSearchResponse;

public interface at
extends IInterface {
    public void a(OneboxSearchResponse var1) throws RemoteException;

    public static abstract class mobvoiapi.at$a
    extends Binder
    implements at {
        public mobvoiapi.at$a() {
            this.attachInterface(this, "com.mobvoi.android.search.internal.ISearchCallback");
        }

        public static at a(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface("com.mobvoi.android.search.internal.ISearchCallback");
            if (iInterface != null && iInterface instanceof at) {
                return (at)iInterface;
            }
            return new a(iBinder);
        }

        public IBinder asBinder() {
            return this;
        }

        /*
         * WARNING - void declaration
         * Enabled aggressive block sorting
         */
        public boolean onTransact(int n2, Parcel object, Parcel parcel, int n3) throws RemoteException {
            void var2_4;
            void var3_6;
            switch (n2) {
                default: {
                    void var4_7;
                    return super.onTransact(n2, object, (Parcel)var3_6, (int)var4_7);
                }
                case 1598968902: {
                    var3_6.writeString("com.mobvoi.android.search.internal.ISearchCallback");
                    return true;
                }
                case 1: 
            }
            object.enforceInterface("com.mobvoi.android.search.internal.ISearchCallback");
            if (object.readInt() != 0) {
                OneboxSearchResponse oneboxSearchResponse = (OneboxSearchResponse)OneboxSearchResponse.CREATOR.createFromParcel(object);
            } else {
                Object var2_5 = null;
            }
            this.a((OneboxSearchResponse)var2_4);
            var3_6.writeNoException();
            return true;
        }

        static class a
        implements at {
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
            public void a(OneboxSearchResponse oneboxSearchResponse) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.mobvoi.android.search.internal.ISearchCallback");
                    if (oneboxSearchResponse != null) {
                        parcel.writeInt(1);
                        oneboxSearchResponse.writeToParcel(parcel, 0);
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

