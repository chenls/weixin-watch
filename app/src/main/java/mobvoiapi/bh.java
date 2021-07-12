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
import com.mobvoi.android.common.internal.safeparcel.SafeParcelable;
import com.mobvoi.android.wearable.internal.DataHolder;
import com.mobvoi.android.wearable.internal.MessageEventHolder;
import com.mobvoi.android.wearable.internal.NodeHolder;

public interface bh
extends IInterface {
    public void onDataChanged(DataHolder var1) throws RemoteException;

    public void onMessageReceived(MessageEventHolder var1) throws RemoteException;

    public void onPeerConnected(NodeHolder var1) throws RemoteException;

    public void onPeerDisconnected(NodeHolder var1) throws RemoteException;

    public static abstract class mobvoiapi.bh$a
    extends Binder
    implements bh {
        public mobvoiapi.bh$a() {
            this.attachInterface(this, "com.mobvoi.android.wearable.internal.IWearableListener");
        }

        public static bh asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface("com.mobvoi.android.wearable.internal.IWearableListener");
            if (iInterface != null && iInterface instanceof bh) {
                return (bh)iInterface;
            }
            return new a(iBinder);
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int n2, Parcel parcel, Parcel parcel2, int n3) throws RemoteException {
            MessageEventHolder messageEventHolder = null;
            MessageEventHolder messageEventHolder2 = null;
            MessageEventHolder messageEventHolder3 = null;
            SafeParcelable safeParcelable = null;
            switch (n2) {
                default: {
                    return super.onTransact(n2, parcel, parcel2, n3);
                }
                case 1598968902: {
                    parcel2.writeString("com.mobvoi.android.wearable.internal.IWearableListener");
                    return true;
                }
                case 1: {
                    parcel.enforceInterface("com.mobvoi.android.wearable.internal.IWearableListener");
                    if (parcel.readInt() != 0) {
                        safeParcelable = (MessageEventHolder)MessageEventHolder.CREATOR.createFromParcel(parcel);
                    }
                    this.onMessageReceived((MessageEventHolder)safeParcelable);
                    parcel2.writeNoException();
                    return true;
                }
                case 2: {
                    parcel.enforceInterface("com.mobvoi.android.wearable.internal.IWearableListener");
                    safeParcelable = messageEventHolder;
                    if (parcel.readInt() != 0) {
                        safeParcelable = (DataHolder)DataHolder.CREATOR.createFromParcel(parcel);
                    }
                    this.onDataChanged((DataHolder)safeParcelable);
                    parcel2.writeNoException();
                    return true;
                }
                case 3: {
                    parcel.enforceInterface("com.mobvoi.android.wearable.internal.IWearableListener");
                    safeParcelable = messageEventHolder2;
                    if (parcel.readInt() != 0) {
                        safeParcelable = (NodeHolder)NodeHolder.CREATOR.createFromParcel(parcel);
                    }
                    this.onPeerConnected((NodeHolder)safeParcelable);
                    parcel2.writeNoException();
                    return true;
                }
                case 4: 
            }
            parcel.enforceInterface("com.mobvoi.android.wearable.internal.IWearableListener");
            safeParcelable = messageEventHolder3;
            if (parcel.readInt() != 0) {
                safeParcelable = (NodeHolder)NodeHolder.CREATOR.createFromParcel(parcel);
            }
            this.onPeerDisconnected((NodeHolder)safeParcelable);
            parcel2.writeNoException();
            return true;
        }

        static class a
        implements bh {
            private IBinder a;

            a(IBinder iBinder) {
                this.a = iBinder;
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
            public void onDataChanged(DataHolder dataHolder) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.mobvoi.android.wearable.internal.IWearableListener");
                    if (dataHolder != null) {
                        parcel.writeInt(1);
                        dataHolder.writeToParcel(parcel, 0);
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
            public void onMessageReceived(MessageEventHolder messageEventHolder) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.mobvoi.android.wearable.internal.IWearableListener");
                    if (messageEventHolder != null) {
                        parcel.writeInt(1);
                        messageEventHolder.writeToParcel(parcel, 0);
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
            public void onPeerConnected(NodeHolder nodeHolder) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.mobvoi.android.wearable.internal.IWearableListener");
                    if (nodeHolder != null) {
                        parcel.writeInt(1);
                        nodeHolder.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
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
            public void onPeerDisconnected(NodeHolder nodeHolder) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.mobvoi.android.wearable.internal.IWearableListener");
                    if (nodeHolder != null) {
                        parcel.writeInt(1);
                        nodeHolder.writeToParcel(parcel, 0);
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
        }
    }
}

