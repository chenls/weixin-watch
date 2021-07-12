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
import com.mobvoi.android.common.api.Status;
import com.mobvoi.android.wearable.internal.DataHolder;
import com.mobvoi.android.wearable.internal.DeleteDataItemsResponse;
import com.mobvoi.android.wearable.internal.GetConfigResponse;
import com.mobvoi.android.wearable.internal.GetConnectedNodesResponse;
import com.mobvoi.android.wearable.internal.GetDataItemResponse;
import com.mobvoi.android.wearable.internal.GetFdForAssetResponse;
import com.mobvoi.android.wearable.internal.GetLocalNodeResponse;
import com.mobvoi.android.wearable.internal.PutDataResponse;
import com.mobvoi.android.wearable.internal.SendMessageResponse;
import com.mobvoi.android.wearable.internal.StorageInfoResponse;

public interface bg
extends IInterface {
    public void a(Status var1) throws RemoteException;

    public void a(DataHolder var1) throws RemoteException;

    public void a(DeleteDataItemsResponse var1) throws RemoteException;

    public void a(GetConfigResponse var1) throws RemoteException;

    public void a(GetConnectedNodesResponse var1) throws RemoteException;

    public void a(GetDataItemResponse var1) throws RemoteException;

    public void a(GetFdForAssetResponse var1) throws RemoteException;

    public void a(GetLocalNodeResponse var1) throws RemoteException;

    public void a(PutDataResponse var1) throws RemoteException;

    public void a(SendMessageResponse var1) throws RemoteException;

    public void a(StorageInfoResponse var1) throws RemoteException;

    public void a(boolean var1) throws RemoteException;

    public static abstract class mobvoiapi.bg$a
    extends Binder
    implements bg {
        public mobvoiapi.bg$a() {
            this.attachInterface(this, "com.mobvoi.android.wearable.internal.IWearableCallback");
        }

        public static bg a(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface("com.mobvoi.android.wearable.internal.IWearableCallback");
            if (iInterface != null && iInterface instanceof bg) {
                return (bg)iInterface;
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
        public boolean onTransact(int n2, Parcel parcel, Parcel parcel2, int n3) throws RemoteException {
            Object var7_5 = null;
            Object var8_6 = null;
            Object var9_7 = null;
            Object var10_8 = null;
            Object var11_9 = null;
            Object var12_10 = null;
            Object var13_11 = null;
            Object var14_12 = null;
            Object var15_13 = null;
            Object var16_14 = null;
            Object var6_15 = null;
            switch (n2) {
                default: {
                    return super.onTransact(n2, parcel, parcel2, n3);
                }
                case 1598968902: {
                    parcel2.writeString("com.mobvoi.android.wearable.internal.IWearableCallback");
                    return true;
                }
                case 1: {
                    void var6_17;
                    parcel.enforceInterface("com.mobvoi.android.wearable.internal.IWearableCallback");
                    if (parcel.readInt() != 0) {
                        Status status = (Status)Status.CREATOR.createFromParcel(parcel);
                    }
                    this.a((Status)var6_17);
                    parcel2.writeNoException();
                    return true;
                }
                case 2: {
                    void var6_20;
                    parcel.enforceInterface("com.mobvoi.android.wearable.internal.IWearableCallback");
                    Object var6_18 = var7_5;
                    if (parcel.readInt() != 0) {
                        DataHolder dataHolder = (DataHolder)DataHolder.CREATOR.createFromParcel(parcel);
                    }
                    this.a((DataHolder)var6_20);
                    parcel2.writeNoException();
                    return true;
                }
                case 3: {
                    void var6_23;
                    parcel.enforceInterface("com.mobvoi.android.wearable.internal.IWearableCallback");
                    Object var6_21 = var8_6;
                    if (parcel.readInt() != 0) {
                        DeleteDataItemsResponse deleteDataItemsResponse = (DeleteDataItemsResponse)DeleteDataItemsResponse.CREATOR.createFromParcel(parcel);
                    }
                    this.a((DeleteDataItemsResponse)var6_23);
                    parcel2.writeNoException();
                    return true;
                }
                case 4: {
                    void var6_26;
                    parcel.enforceInterface("com.mobvoi.android.wearable.internal.IWearableCallback");
                    Object var6_24 = var9_7;
                    if (parcel.readInt() != 0) {
                        GetConfigResponse getConfigResponse = (GetConfigResponse)GetConfigResponse.CREATOR.createFromParcel(parcel);
                    }
                    this.a((GetConfigResponse)var6_26);
                    parcel2.writeNoException();
                    return true;
                }
                case 5: {
                    void var6_29;
                    parcel.enforceInterface("com.mobvoi.android.wearable.internal.IWearableCallback");
                    Object var6_27 = var10_8;
                    if (parcel.readInt() != 0) {
                        GetConnectedNodesResponse getConnectedNodesResponse = (GetConnectedNodesResponse)GetConnectedNodesResponse.CREATOR.createFromParcel(parcel);
                    }
                    this.a((GetConnectedNodesResponse)var6_29);
                    parcel2.writeNoException();
                    return true;
                }
                case 6: {
                    void var6_32;
                    parcel.enforceInterface("com.mobvoi.android.wearable.internal.IWearableCallback");
                    Object var6_30 = var11_9;
                    if (parcel.readInt() != 0) {
                        GetDataItemResponse getDataItemResponse = (GetDataItemResponse)GetDataItemResponse.CREATOR.createFromParcel(parcel);
                    }
                    this.a((GetDataItemResponse)var6_32);
                    parcel2.writeNoException();
                    return true;
                }
                case 7: {
                    void var6_35;
                    parcel.enforceInterface("com.mobvoi.android.wearable.internal.IWearableCallback");
                    Object var6_33 = var12_10;
                    if (parcel.readInt() != 0) {
                        GetFdForAssetResponse getFdForAssetResponse = (GetFdForAssetResponse)GetFdForAssetResponse.CREATOR.createFromParcel(parcel);
                    }
                    this.a((GetFdForAssetResponse)var6_35);
                    parcel2.writeNoException();
                    return true;
                }
                case 8: {
                    void var6_38;
                    parcel.enforceInterface("com.mobvoi.android.wearable.internal.IWearableCallback");
                    Object var6_36 = var13_11;
                    if (parcel.readInt() != 0) {
                        GetLocalNodeResponse getLocalNodeResponse = (GetLocalNodeResponse)GetLocalNodeResponse.CREATOR.createFromParcel(parcel);
                    }
                    this.a((GetLocalNodeResponse)var6_38);
                    parcel2.writeNoException();
                    return true;
                }
                case 9: {
                    void var6_41;
                    parcel.enforceInterface("com.mobvoi.android.wearable.internal.IWearableCallback");
                    Object var6_39 = var14_12;
                    if (parcel.readInt() != 0) {
                        PutDataResponse putDataResponse = (PutDataResponse)PutDataResponse.CREATOR.createFromParcel(parcel);
                    }
                    this.a((PutDataResponse)var6_41);
                    parcel2.writeNoException();
                    return true;
                }
                case 10: {
                    void var6_44;
                    parcel.enforceInterface("com.mobvoi.android.wearable.internal.IWearableCallback");
                    Object var6_42 = var15_13;
                    if (parcel.readInt() != 0) {
                        SendMessageResponse sendMessageResponse = (SendMessageResponse)SendMessageResponse.CREATOR.createFromParcel(parcel);
                    }
                    this.a((SendMessageResponse)var6_44);
                    parcel2.writeNoException();
                    return true;
                }
                case 11: {
                    void var6_47;
                    parcel.enforceInterface("com.mobvoi.android.wearable.internal.IWearableCallback");
                    Object var6_45 = var16_14;
                    if (parcel.readInt() != 0) {
                        StorageInfoResponse storageInfoResponse = (StorageInfoResponse)StorageInfoResponse.CREATOR.createFromParcel(parcel);
                    }
                    this.a((StorageInfoResponse)var6_47);
                    parcel2.writeNoException();
                    return true;
                }
                case 12: 
            }
            parcel.enforceInterface("com.mobvoi.android.wearable.internal.IWearableCallback");
            boolean bl2 = parcel.readInt() != 0;
            this.a(bl2);
            parcel2.writeNoException();
            return true;
        }

        static class a
        implements bg {
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
            public void a(Status status) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.mobvoi.android.wearable.internal.IWearableCallback");
                    if (status != null) {
                        parcel.writeInt(1);
                        status.writeToParcel(parcel, 0);
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
            public void a(DataHolder dataHolder) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.mobvoi.android.wearable.internal.IWearableCallback");
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
            public void a(DeleteDataItemsResponse deleteDataItemsResponse) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.mobvoi.android.wearable.internal.IWearableCallback");
                    if (deleteDataItemsResponse != null) {
                        parcel.writeInt(1);
                        deleteDataItemsResponse.writeToParcel(parcel, 0);
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
            public void a(GetConfigResponse getConfigResponse) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.mobvoi.android.wearable.internal.IWearableCallback");
                    if (getConfigResponse != null) {
                        parcel.writeInt(1);
                        getConfigResponse.writeToParcel(parcel, 0);
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
            public void a(GetConnectedNodesResponse getConnectedNodesResponse) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.mobvoi.android.wearable.internal.IWearableCallback");
                    if (getConnectedNodesResponse != null) {
                        parcel.writeInt(1);
                        getConnectedNodesResponse.writeToParcel(parcel, 0);
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
            public void a(GetDataItemResponse getDataItemResponse) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.mobvoi.android.wearable.internal.IWearableCallback");
                    if (getDataItemResponse != null) {
                        parcel.writeInt(1);
                        getDataItemResponse.writeToParcel(parcel, 0);
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
            public void a(GetFdForAssetResponse getFdForAssetResponse) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.mobvoi.android.wearable.internal.IWearableCallback");
                    if (getFdForAssetResponse != null) {
                        parcel.writeInt(1);
                        getFdForAssetResponse.writeToParcel(parcel, 0);
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
            public void a(GetLocalNodeResponse getLocalNodeResponse) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.mobvoi.android.wearable.internal.IWearableCallback");
                    if (getLocalNodeResponse != null) {
                        parcel.writeInt(1);
                        getLocalNodeResponse.writeToParcel(parcel, 0);
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
            public void a(PutDataResponse putDataResponse) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.mobvoi.android.wearable.internal.IWearableCallback");
                    if (putDataResponse != null) {
                        parcel.writeInt(1);
                        putDataResponse.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
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
            public void a(SendMessageResponse sendMessageResponse) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.mobvoi.android.wearable.internal.IWearableCallback");
                    if (sendMessageResponse != null) {
                        parcel.writeInt(1);
                        sendMessageResponse.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
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
            public void a(StorageInfoResponse storageInfoResponse) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.mobvoi.android.wearable.internal.IWearableCallback");
                    if (storageInfoResponse != null) {
                        parcel.writeInt(1);
                        storageInfoResponse.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.a.transact(11, parcel, parcel2, 0);
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void a(boolean bl2) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                int n2;
                block4: {
                    n2 = 0;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    parcel2.writeInterfaceToken("com.mobvoi.android.wearable.internal.IWearableCallback");
                    if (!bl2) break block4;
                    n2 = 1;
                }
                try {
                    parcel2.writeInt(n2);
                    this.a.transact(12, parcel2, parcel, 0);
                    parcel.readException();
                    return;
                }
                finally {
                    parcel.recycle();
                    parcel2.recycle();
                }
            }

            public IBinder asBinder() {
                return this.a;
            }
        }
    }
}

