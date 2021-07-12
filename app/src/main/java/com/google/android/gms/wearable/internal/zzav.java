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
package com.google.android.gms.wearable.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.wearable.internal.AddLocalCapabilityResponse;
import com.google.android.gms.wearable.internal.ChannelReceiveFileResponse;
import com.google.android.gms.wearable.internal.ChannelSendFileResponse;
import com.google.android.gms.wearable.internal.CloseChannelResponse;
import com.google.android.gms.wearable.internal.DeleteDataItemsResponse;
import com.google.android.gms.wearable.internal.GetAllCapabilitiesResponse;
import com.google.android.gms.wearable.internal.GetCapabilityResponse;
import com.google.android.gms.wearable.internal.GetChannelInputStreamResponse;
import com.google.android.gms.wearable.internal.GetChannelOutputStreamResponse;
import com.google.android.gms.wearable.internal.GetCloudSyncOptInOutDoneResponse;
import com.google.android.gms.wearable.internal.GetCloudSyncOptInStatusResponse;
import com.google.android.gms.wearable.internal.GetCloudSyncSettingResponse;
import com.google.android.gms.wearable.internal.GetConfigResponse;
import com.google.android.gms.wearable.internal.GetConfigsResponse;
import com.google.android.gms.wearable.internal.GetConnectedNodesResponse;
import com.google.android.gms.wearable.internal.GetDataItemResponse;
import com.google.android.gms.wearable.internal.GetFdForAssetResponse;
import com.google.android.gms.wearable.internal.GetLocalNodeResponse;
import com.google.android.gms.wearable.internal.OpenChannelResponse;
import com.google.android.gms.wearable.internal.PutDataResponse;
import com.google.android.gms.wearable.internal.RemoveLocalCapabilityResponse;
import com.google.android.gms.wearable.internal.SendMessageResponse;
import com.google.android.gms.wearable.internal.StorageInfoResponse;

public interface zzav
extends IInterface {
    public void zza(Status var1) throws RemoteException;

    public void zza(AddLocalCapabilityResponse var1) throws RemoteException;

    public void zza(ChannelReceiveFileResponse var1) throws RemoteException;

    public void zza(ChannelSendFileResponse var1) throws RemoteException;

    public void zza(CloseChannelResponse var1) throws RemoteException;

    public void zza(DeleteDataItemsResponse var1) throws RemoteException;

    public void zza(GetAllCapabilitiesResponse var1) throws RemoteException;

    public void zza(GetCapabilityResponse var1) throws RemoteException;

    public void zza(GetChannelInputStreamResponse var1) throws RemoteException;

    public void zza(GetChannelOutputStreamResponse var1) throws RemoteException;

    public void zza(GetCloudSyncOptInOutDoneResponse var1) throws RemoteException;

    public void zza(GetCloudSyncOptInStatusResponse var1) throws RemoteException;

    public void zza(GetCloudSyncSettingResponse var1) throws RemoteException;

    public void zza(GetConfigResponse var1) throws RemoteException;

    public void zza(GetConfigsResponse var1) throws RemoteException;

    public void zza(GetConnectedNodesResponse var1) throws RemoteException;

    public void zza(GetDataItemResponse var1) throws RemoteException;

    public void zza(GetFdForAssetResponse var1) throws RemoteException;

    public void zza(GetLocalNodeResponse var1) throws RemoteException;

    public void zza(OpenChannelResponse var1) throws RemoteException;

    public void zza(PutDataResponse var1) throws RemoteException;

    public void zza(RemoveLocalCapabilityResponse var1) throws RemoteException;

    public void zza(SendMessageResponse var1) throws RemoteException;

    public void zza(StorageInfoResponse var1) throws RemoteException;

    public void zzah(DataHolder var1) throws RemoteException;

    public void zzb(CloseChannelResponse var1) throws RemoteException;

    public static abstract class com.google.android.gms.wearable.internal.zzav$zza
    extends Binder
    implements zzav {
        public com.google.android.gms.wearable.internal.zzav$zza() {
            this.attachInterface(this, "com.google.android.gms.wearable.internal.IWearableCallbacks");
        }

        public static zzav zzes(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface("com.google.android.gms.wearable.internal.IWearableCallbacks");
            if (iInterface != null && iInterface instanceof zzav) {
                return (zzav)iInterface;
            }
            return new zza(iBinder);
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int n2, Parcel parcel, Parcel parcel2, int n3) throws RemoteException {
            GetConfigResponse getConfigResponse = null;
            GetConfigResponse getConfigResponse2 = null;
            GetConfigResponse getConfigResponse3 = null;
            GetConfigResponse getConfigResponse4 = null;
            GetConfigResponse getConfigResponse5 = null;
            GetConfigResponse getConfigResponse6 = null;
            GetConfigResponse getConfigResponse7 = null;
            GetConfigResponse getConfigResponse8 = null;
            GetConfigResponse getConfigResponse9 = null;
            GetConfigResponse getConfigResponse10 = null;
            GetConfigResponse getConfigResponse11 = null;
            GetConfigResponse getConfigResponse12 = null;
            GetConfigResponse getConfigResponse13 = null;
            GetConfigResponse getConfigResponse14 = null;
            GetConfigResponse getConfigResponse15 = null;
            GetConfigResponse getConfigResponse16 = null;
            GetConfigResponse getConfigResponse17 = null;
            GetConfigResponse getConfigResponse18 = null;
            GetConfigResponse getConfigResponse19 = null;
            GetConfigResponse getConfigResponse20 = null;
            GetConfigResponse getConfigResponse21 = null;
            GetConfigResponse getConfigResponse22 = null;
            GetConfigResponse getConfigResponse23 = null;
            GetConfigResponse getConfigResponse24 = null;
            GetConfigResponse getConfigResponse25 = null;
            SafeParcelable safeParcelable = null;
            switch (n2) {
                default: {
                    return super.onTransact(n2, parcel, parcel2, n3);
                }
                case 1598968902: {
                    parcel2.writeString("com.google.android.gms.wearable.internal.IWearableCallbacks");
                    return true;
                }
                case 2: {
                    parcel.enforceInterface("com.google.android.gms.wearable.internal.IWearableCallbacks");
                    if (parcel.readInt() != 0) {
                        safeParcelable = (GetConfigResponse)GetConfigResponse.CREATOR.createFromParcel(parcel);
                    }
                    this.zza((GetConfigResponse)safeParcelable);
                    parcel2.writeNoException();
                    return true;
                }
                case 13: {
                    parcel.enforceInterface("com.google.android.gms.wearable.internal.IWearableCallbacks");
                    safeParcelable = getConfigResponse;
                    if (parcel.readInt() != 0) {
                        safeParcelable = (GetConfigsResponse)GetConfigsResponse.CREATOR.createFromParcel(parcel);
                    }
                    this.zza((GetConfigsResponse)safeParcelable);
                    parcel2.writeNoException();
                    return true;
                }
                case 28: {
                    parcel.enforceInterface("com.google.android.gms.wearable.internal.IWearableCallbacks");
                    safeParcelable = getConfigResponse2;
                    if (parcel.readInt() != 0) {
                        safeParcelable = (GetCloudSyncOptInOutDoneResponse)GetCloudSyncOptInOutDoneResponse.CREATOR.createFromParcel(parcel);
                    }
                    this.zza((GetCloudSyncOptInOutDoneResponse)safeParcelable);
                    parcel2.writeNoException();
                    return true;
                }
                case 29: {
                    parcel.enforceInterface("com.google.android.gms.wearable.internal.IWearableCallbacks");
                    safeParcelable = getConfigResponse3;
                    if (parcel.readInt() != 0) {
                        safeParcelable = (GetCloudSyncSettingResponse)GetCloudSyncSettingResponse.CREATOR.createFromParcel(parcel);
                    }
                    this.zza((GetCloudSyncSettingResponse)safeParcelable);
                    parcel2.writeNoException();
                    return true;
                }
                case 30: {
                    parcel.enforceInterface("com.google.android.gms.wearable.internal.IWearableCallbacks");
                    safeParcelable = getConfigResponse4;
                    if (parcel.readInt() != 0) {
                        safeParcelable = (GetCloudSyncOptInStatusResponse)GetCloudSyncOptInStatusResponse.CREATOR.createFromParcel(parcel);
                    }
                    this.zza((GetCloudSyncOptInStatusResponse)safeParcelable);
                    parcel2.writeNoException();
                    return true;
                }
                case 3: {
                    parcel.enforceInterface("com.google.android.gms.wearable.internal.IWearableCallbacks");
                    safeParcelable = getConfigResponse5;
                    if (parcel.readInt() != 0) {
                        safeParcelable = (PutDataResponse)PutDataResponse.CREATOR.createFromParcel(parcel);
                    }
                    this.zza((PutDataResponse)safeParcelable);
                    parcel2.writeNoException();
                    return true;
                }
                case 4: {
                    parcel.enforceInterface("com.google.android.gms.wearable.internal.IWearableCallbacks");
                    safeParcelable = getConfigResponse6;
                    if (parcel.readInt() != 0) {
                        safeParcelable = (GetDataItemResponse)GetDataItemResponse.CREATOR.createFromParcel(parcel);
                    }
                    this.zza((GetDataItemResponse)safeParcelable);
                    parcel2.writeNoException();
                    return true;
                }
                case 5: {
                    parcel.enforceInterface("com.google.android.gms.wearable.internal.IWearableCallbacks");
                    safeParcelable = getConfigResponse7;
                    if (parcel.readInt() != 0) {
                        safeParcelable = DataHolder.CREATOR.zzak(parcel);
                    }
                    this.zzah((DataHolder)safeParcelable);
                    parcel2.writeNoException();
                    return true;
                }
                case 6: {
                    parcel.enforceInterface("com.google.android.gms.wearable.internal.IWearableCallbacks");
                    safeParcelable = getConfigResponse8;
                    if (parcel.readInt() != 0) {
                        safeParcelable = (DeleteDataItemsResponse)DeleteDataItemsResponse.CREATOR.createFromParcel(parcel);
                    }
                    this.zza((DeleteDataItemsResponse)safeParcelable);
                    parcel2.writeNoException();
                    return true;
                }
                case 7: {
                    parcel.enforceInterface("com.google.android.gms.wearable.internal.IWearableCallbacks");
                    safeParcelable = getConfigResponse9;
                    if (parcel.readInt() != 0) {
                        safeParcelable = (SendMessageResponse)SendMessageResponse.CREATOR.createFromParcel(parcel);
                    }
                    this.zza((SendMessageResponse)safeParcelable);
                    parcel2.writeNoException();
                    return true;
                }
                case 8: {
                    parcel.enforceInterface("com.google.android.gms.wearable.internal.IWearableCallbacks");
                    safeParcelable = getConfigResponse10;
                    if (parcel.readInt() != 0) {
                        safeParcelable = (GetFdForAssetResponse)GetFdForAssetResponse.CREATOR.createFromParcel(parcel);
                    }
                    this.zza((GetFdForAssetResponse)safeParcelable);
                    parcel2.writeNoException();
                    return true;
                }
                case 9: {
                    parcel.enforceInterface("com.google.android.gms.wearable.internal.IWearableCallbacks");
                    safeParcelable = getConfigResponse11;
                    if (parcel.readInt() != 0) {
                        safeParcelable = (GetLocalNodeResponse)GetLocalNodeResponse.CREATOR.createFromParcel(parcel);
                    }
                    this.zza((GetLocalNodeResponse)safeParcelable);
                    parcel2.writeNoException();
                    return true;
                }
                case 10: {
                    parcel.enforceInterface("com.google.android.gms.wearable.internal.IWearableCallbacks");
                    safeParcelable = getConfigResponse12;
                    if (parcel.readInt() != 0) {
                        safeParcelable = (GetConnectedNodesResponse)GetConnectedNodesResponse.CREATOR.createFromParcel(parcel);
                    }
                    this.zza((GetConnectedNodesResponse)safeParcelable);
                    parcel2.writeNoException();
                    return true;
                }
                case 14: {
                    parcel.enforceInterface("com.google.android.gms.wearable.internal.IWearableCallbacks");
                    safeParcelable = getConfigResponse13;
                    if (parcel.readInt() != 0) {
                        safeParcelable = (OpenChannelResponse)OpenChannelResponse.CREATOR.createFromParcel(parcel);
                    }
                    this.zza((OpenChannelResponse)safeParcelable);
                    parcel2.writeNoException();
                    return true;
                }
                case 15: {
                    parcel.enforceInterface("com.google.android.gms.wearable.internal.IWearableCallbacks");
                    safeParcelable = getConfigResponse14;
                    if (parcel.readInt() != 0) {
                        safeParcelable = (CloseChannelResponse)CloseChannelResponse.CREATOR.createFromParcel(parcel);
                    }
                    this.zza((CloseChannelResponse)safeParcelable);
                    parcel2.writeNoException();
                    return true;
                }
                case 16: {
                    parcel.enforceInterface("com.google.android.gms.wearable.internal.IWearableCallbacks");
                    safeParcelable = getConfigResponse15;
                    if (parcel.readInt() != 0) {
                        safeParcelable = (CloseChannelResponse)CloseChannelResponse.CREATOR.createFromParcel(parcel);
                    }
                    this.zzb((CloseChannelResponse)safeParcelable);
                    parcel2.writeNoException();
                    return true;
                }
                case 17: {
                    parcel.enforceInterface("com.google.android.gms.wearable.internal.IWearableCallbacks");
                    safeParcelable = getConfigResponse16;
                    if (parcel.readInt() != 0) {
                        safeParcelable = (GetChannelInputStreamResponse)GetChannelInputStreamResponse.CREATOR.createFromParcel(parcel);
                    }
                    this.zza((GetChannelInputStreamResponse)safeParcelable);
                    parcel2.writeNoException();
                    return true;
                }
                case 18: {
                    parcel.enforceInterface("com.google.android.gms.wearable.internal.IWearableCallbacks");
                    safeParcelable = getConfigResponse17;
                    if (parcel.readInt() != 0) {
                        safeParcelable = (GetChannelOutputStreamResponse)GetChannelOutputStreamResponse.CREATOR.createFromParcel(parcel);
                    }
                    this.zza((GetChannelOutputStreamResponse)safeParcelable);
                    parcel2.writeNoException();
                    return true;
                }
                case 19: {
                    parcel.enforceInterface("com.google.android.gms.wearable.internal.IWearableCallbacks");
                    safeParcelable = getConfigResponse18;
                    if (parcel.readInt() != 0) {
                        safeParcelable = (ChannelReceiveFileResponse)ChannelReceiveFileResponse.CREATOR.createFromParcel(parcel);
                    }
                    this.zza((ChannelReceiveFileResponse)safeParcelable);
                    parcel2.writeNoException();
                    return true;
                }
                case 20: {
                    parcel.enforceInterface("com.google.android.gms.wearable.internal.IWearableCallbacks");
                    safeParcelable = getConfigResponse19;
                    if (parcel.readInt() != 0) {
                        safeParcelable = (ChannelSendFileResponse)ChannelSendFileResponse.CREATOR.createFromParcel(parcel);
                    }
                    this.zza((ChannelSendFileResponse)safeParcelable);
                    parcel2.writeNoException();
                    return true;
                }
                case 11: {
                    parcel.enforceInterface("com.google.android.gms.wearable.internal.IWearableCallbacks");
                    safeParcelable = getConfigResponse20;
                    if (parcel.readInt() != 0) {
                        safeParcelable = (Status)Status.CREATOR.createFromParcel(parcel);
                    }
                    this.zza((Status)safeParcelable);
                    parcel2.writeNoException();
                    return true;
                }
                case 12: {
                    parcel.enforceInterface("com.google.android.gms.wearable.internal.IWearableCallbacks");
                    safeParcelable = getConfigResponse21;
                    if (parcel.readInt() != 0) {
                        safeParcelable = (StorageInfoResponse)StorageInfoResponse.CREATOR.createFromParcel(parcel);
                    }
                    this.zza((StorageInfoResponse)safeParcelable);
                    parcel2.writeNoException();
                    return true;
                }
                case 22: {
                    parcel.enforceInterface("com.google.android.gms.wearable.internal.IWearableCallbacks");
                    safeParcelable = getConfigResponse22;
                    if (parcel.readInt() != 0) {
                        safeParcelable = (GetCapabilityResponse)GetCapabilityResponse.CREATOR.createFromParcel(parcel);
                    }
                    this.zza((GetCapabilityResponse)safeParcelable);
                    parcel2.writeNoException();
                    return true;
                }
                case 23: {
                    parcel.enforceInterface("com.google.android.gms.wearable.internal.IWearableCallbacks");
                    safeParcelable = getConfigResponse23;
                    if (parcel.readInt() != 0) {
                        safeParcelable = (GetAllCapabilitiesResponse)GetAllCapabilitiesResponse.CREATOR.createFromParcel(parcel);
                    }
                    this.zza((GetAllCapabilitiesResponse)safeParcelable);
                    parcel2.writeNoException();
                    return true;
                }
                case 26: {
                    parcel.enforceInterface("com.google.android.gms.wearable.internal.IWearableCallbacks");
                    safeParcelable = getConfigResponse24;
                    if (parcel.readInt() != 0) {
                        safeParcelable = (AddLocalCapabilityResponse)AddLocalCapabilityResponse.CREATOR.createFromParcel(parcel);
                    }
                    this.zza((AddLocalCapabilityResponse)safeParcelable);
                    parcel2.writeNoException();
                    return true;
                }
                case 27: 
            }
            parcel.enforceInterface("com.google.android.gms.wearable.internal.IWearableCallbacks");
            safeParcelable = getConfigResponse25;
            if (parcel.readInt() != 0) {
                safeParcelable = (RemoveLocalCapabilityResponse)RemoveLocalCapabilityResponse.CREATOR.createFromParcel(parcel);
            }
            this.zza((RemoveLocalCapabilityResponse)safeParcelable);
            parcel2.writeNoException();
            return true;
        }

        private static class zza
        implements zzav {
            private IBinder zzoz;

            zza(IBinder iBinder) {
                this.zzoz = iBinder;
            }

            public IBinder asBinder() {
                return this.zzoz;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void zza(Status status) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.wearable.internal.IWearableCallbacks");
                    if (status != null) {
                        parcel.writeInt(1);
                        status.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.zzoz.transact(11, parcel, parcel2, 0);
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
            public void zza(AddLocalCapabilityResponse addLocalCapabilityResponse) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.wearable.internal.IWearableCallbacks");
                    if (addLocalCapabilityResponse != null) {
                        parcel.writeInt(1);
                        addLocalCapabilityResponse.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.zzoz.transact(26, parcel, parcel2, 0);
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
            public void zza(ChannelReceiveFileResponse channelReceiveFileResponse) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.wearable.internal.IWearableCallbacks");
                    if (channelReceiveFileResponse != null) {
                        parcel.writeInt(1);
                        channelReceiveFileResponse.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.zzoz.transact(19, parcel, parcel2, 0);
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
            public void zza(ChannelSendFileResponse channelSendFileResponse) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.wearable.internal.IWearableCallbacks");
                    if (channelSendFileResponse != null) {
                        parcel.writeInt(1);
                        channelSendFileResponse.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.zzoz.transact(20, parcel, parcel2, 0);
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
            public void zza(CloseChannelResponse closeChannelResponse) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.wearable.internal.IWearableCallbacks");
                    if (closeChannelResponse != null) {
                        parcel.writeInt(1);
                        closeChannelResponse.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.zzoz.transact(15, parcel, parcel2, 0);
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
            public void zza(DeleteDataItemsResponse deleteDataItemsResponse) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.wearable.internal.IWearableCallbacks");
                    if (deleteDataItemsResponse != null) {
                        parcel.writeInt(1);
                        deleteDataItemsResponse.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.zzoz.transact(6, parcel, parcel2, 0);
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
            public void zza(GetAllCapabilitiesResponse getAllCapabilitiesResponse) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.wearable.internal.IWearableCallbacks");
                    if (getAllCapabilitiesResponse != null) {
                        parcel.writeInt(1);
                        getAllCapabilitiesResponse.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.zzoz.transact(23, parcel, parcel2, 0);
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
            public void zza(GetCapabilityResponse getCapabilityResponse) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.wearable.internal.IWearableCallbacks");
                    if (getCapabilityResponse != null) {
                        parcel.writeInt(1);
                        getCapabilityResponse.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.zzoz.transact(22, parcel, parcel2, 0);
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
            public void zza(GetChannelInputStreamResponse getChannelInputStreamResponse) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.wearable.internal.IWearableCallbacks");
                    if (getChannelInputStreamResponse != null) {
                        parcel.writeInt(1);
                        getChannelInputStreamResponse.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.zzoz.transact(17, parcel, parcel2, 0);
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
            public void zza(GetChannelOutputStreamResponse getChannelOutputStreamResponse) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.wearable.internal.IWearableCallbacks");
                    if (getChannelOutputStreamResponse != null) {
                        parcel.writeInt(1);
                        getChannelOutputStreamResponse.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.zzoz.transact(18, parcel, parcel2, 0);
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
            public void zza(GetCloudSyncOptInOutDoneResponse getCloudSyncOptInOutDoneResponse) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.wearable.internal.IWearableCallbacks");
                    if (getCloudSyncOptInOutDoneResponse != null) {
                        parcel.writeInt(1);
                        getCloudSyncOptInOutDoneResponse.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.zzoz.transact(28, parcel, parcel2, 0);
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
            public void zza(GetCloudSyncOptInStatusResponse getCloudSyncOptInStatusResponse) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.wearable.internal.IWearableCallbacks");
                    if (getCloudSyncOptInStatusResponse != null) {
                        parcel.writeInt(1);
                        getCloudSyncOptInStatusResponse.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.zzoz.transact(30, parcel, parcel2, 0);
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
            public void zza(GetCloudSyncSettingResponse getCloudSyncSettingResponse) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.wearable.internal.IWearableCallbacks");
                    if (getCloudSyncSettingResponse != null) {
                        parcel.writeInt(1);
                        getCloudSyncSettingResponse.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.zzoz.transact(29, parcel, parcel2, 0);
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
            public void zza(GetConfigResponse getConfigResponse) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.wearable.internal.IWearableCallbacks");
                    if (getConfigResponse != null) {
                        parcel.writeInt(1);
                        getConfigResponse.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.zzoz.transact(2, parcel, parcel2, 0);
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
            public void zza(GetConfigsResponse getConfigsResponse) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.wearable.internal.IWearableCallbacks");
                    if (getConfigsResponse != null) {
                        parcel.writeInt(1);
                        getConfigsResponse.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.zzoz.transact(13, parcel, parcel2, 0);
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
            public void zza(GetConnectedNodesResponse getConnectedNodesResponse) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.wearable.internal.IWearableCallbacks");
                    if (getConnectedNodesResponse != null) {
                        parcel.writeInt(1);
                        getConnectedNodesResponse.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.zzoz.transact(10, parcel, parcel2, 0);
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
            public void zza(GetDataItemResponse getDataItemResponse) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.wearable.internal.IWearableCallbacks");
                    if (getDataItemResponse != null) {
                        parcel.writeInt(1);
                        getDataItemResponse.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.zzoz.transact(4, parcel, parcel2, 0);
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
            public void zza(GetFdForAssetResponse getFdForAssetResponse) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.wearable.internal.IWearableCallbacks");
                    if (getFdForAssetResponse != null) {
                        parcel.writeInt(1);
                        getFdForAssetResponse.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.zzoz.transact(8, parcel, parcel2, 0);
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
            public void zza(GetLocalNodeResponse getLocalNodeResponse) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.wearable.internal.IWearableCallbacks");
                    if (getLocalNodeResponse != null) {
                        parcel.writeInt(1);
                        getLocalNodeResponse.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.zzoz.transact(9, parcel, parcel2, 0);
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
            public void zza(OpenChannelResponse openChannelResponse) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.wearable.internal.IWearableCallbacks");
                    if (openChannelResponse != null) {
                        parcel.writeInt(1);
                        openChannelResponse.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.zzoz.transact(14, parcel, parcel2, 0);
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
            public void zza(PutDataResponse putDataResponse) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.wearable.internal.IWearableCallbacks");
                    if (putDataResponse != null) {
                        parcel.writeInt(1);
                        putDataResponse.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.zzoz.transact(3, parcel, parcel2, 0);
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
            public void zza(RemoveLocalCapabilityResponse removeLocalCapabilityResponse) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.wearable.internal.IWearableCallbacks");
                    if (removeLocalCapabilityResponse != null) {
                        parcel.writeInt(1);
                        removeLocalCapabilityResponse.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.zzoz.transact(27, parcel, parcel2, 0);
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
            public void zza(SendMessageResponse sendMessageResponse) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.wearable.internal.IWearableCallbacks");
                    if (sendMessageResponse != null) {
                        parcel.writeInt(1);
                        sendMessageResponse.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.zzoz.transact(7, parcel, parcel2, 0);
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
            public void zza(StorageInfoResponse storageInfoResponse) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.wearable.internal.IWearableCallbacks");
                    if (storageInfoResponse != null) {
                        parcel.writeInt(1);
                        storageInfoResponse.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.zzoz.transact(12, parcel, parcel2, 0);
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
            public void zzah(DataHolder dataHolder) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.wearable.internal.IWearableCallbacks");
                    if (dataHolder != null) {
                        parcel.writeInt(1);
                        dataHolder.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.zzoz.transact(5, parcel, parcel2, 0);
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
            public void zzb(CloseChannelResponse closeChannelResponse) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.wearable.internal.IWearableCallbacks");
                    if (closeChannelResponse != null) {
                        parcel.writeInt(1);
                        closeChannelResponse.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.zzoz.transact(16, parcel, parcel2, 0);
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

