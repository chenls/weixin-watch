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
package android.support.wearable.watchface;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.support.wearable.watchface.WatchFaceStyle;

public interface IWatchFaceService
extends IInterface {
    public void setStyle(WatchFaceStyle var1) throws RemoteException;

    public static abstract class Stub
    extends Binder
    implements IWatchFaceService {
        private static final String DESCRIPTOR = "android.support.wearable.watchface.IWatchFaceService";
        static final int TRANSACTION_setStyle = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IWatchFaceService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IWatchFaceService) {
                return (IWatchFaceService)iInterface;
            }
            return new Proxy(iBinder);
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
                    var3_6.writeString(DESCRIPTOR);
                    return true;
                }
                case 1: 
            }
            object.enforceInterface(DESCRIPTOR);
            if (object.readInt() != 0) {
                WatchFaceStyle watchFaceStyle = (WatchFaceStyle)WatchFaceStyle.CREATOR.createFromParcel(object);
            } else {
                Object var2_5 = null;
            }
            this.setStyle((WatchFaceStyle)var2_4);
            var3_6.writeNoException();
            return true;
        }

        private static class Proxy
        implements IWatchFaceService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void setStyle(WatchFaceStyle watchFaceStyle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (watchFaceStyle != null) {
                        parcel.writeInt(1);
                        watchFaceStyle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.mRemote.transact(1, parcel, parcel2, 0);
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

