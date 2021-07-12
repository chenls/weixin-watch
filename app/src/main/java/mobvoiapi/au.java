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
import com.mobvoi.android.search.OneboxRequest;
import mobvoiapi.at;

public interface au
extends IInterface {
    public void a(at var1, OneboxRequest var2) throws RemoteException;

    public static abstract class mobvoiapi.au$a
    extends Binder
    implements au {
        public static au a(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface("com.mobvoi.android.search.internal.ISearchService");
            if (iInterface != null && iInterface instanceof au) {
                return (au)iInterface;
            }
            return new a(iBinder);
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
                    var3_6.writeString("com.mobvoi.android.search.internal.ISearchService");
                    return true;
                }
                case 1: 
            }
            object.enforceInterface("com.mobvoi.android.search.internal.ISearchService");
            at at2 = at.a.a(object.readStrongBinder());
            if (object.readInt() != 0) {
                OneboxRequest oneboxRequest = (OneboxRequest)OneboxRequest.CREATOR.createFromParcel(object);
            } else {
                Object var2_5 = null;
            }
            this.a(at2, (OneboxRequest)var2_4);
            var3_6.writeNoException();
            return true;
        }

        static class a
        implements au {
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
            public void a(at at2, OneboxRequest oneboxRequest) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.mobvoi.android.search.internal.ISearchService");
                    at2 = at2 != null ? at2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)at2);
                    if (oneboxRequest != null) {
                        parcel.writeInt(1);
                        oneboxRequest.writeToParcel(parcel, 0);
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

