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
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.wearable.internal.AmsEntityUpdateParcelable;
import com.google.android.gms.wearable.internal.AncsNotificationParcelable;
import com.google.android.gms.wearable.internal.CapabilityInfoParcelable;
import com.google.android.gms.wearable.internal.ChannelEventParcelable;
import com.google.android.gms.wearable.internal.MessageEventParcelable;
import com.google.android.gms.wearable.internal.NodeParcelable;
import java.util.List;

public interface zzaw
extends IInterface {
    public void onConnectedNodes(List<NodeParcelable> var1) throws RemoteException;

    public void zza(AmsEntityUpdateParcelable var1) throws RemoteException;

    public void zza(AncsNotificationParcelable var1) throws RemoteException;

    public void zza(CapabilityInfoParcelable var1) throws RemoteException;

    public void zza(ChannelEventParcelable var1) throws RemoteException;

    public void zza(MessageEventParcelable var1) throws RemoteException;

    public void zza(NodeParcelable var1) throws RemoteException;

    public void zzag(DataHolder var1) throws RemoteException;

    public void zzb(NodeParcelable var1) throws RemoteException;

    public static abstract class com.google.android.gms.wearable.internal.zzaw$zza
    extends Binder
    implements zzaw {
        public com.google.android.gms.wearable.internal.zzaw$zza() {
            this.attachInterface(this, "com.google.android.gms.wearable.internal.IWearableListener");
        }

        public static zzaw zzet(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface("com.google.android.gms.wearable.internal.IWearableListener");
            if (iInterface != null && iInterface instanceof zzaw) {
                return (zzaw)iInterface;
            }
            return new zza(iBinder);
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int n2, Parcel parcel, Parcel object, int n3) throws RemoteException {
            Object var6_5 = null;
            Object var7_6 = null;
            Object var8_7 = null;
            Object var9_8 = null;
            Object var10_9 = null;
            Object var11_10 = null;
            Object var12_11 = null;
            Object var5_12 = null;
            switch (n2) {
                default: {
                    return super.onTransact(n2, parcel, object, n3);
                }
                case 1598968902: {
                    object.writeString("com.google.android.gms.wearable.internal.IWearableListener");
                    return true;
                }
                case 1: {
                    parcel.enforceInterface("com.google.android.gms.wearable.internal.IWearableListener");
                    object = var5_12;
                    if (parcel.readInt() != 0) {
                        object = DataHolder.CREATOR.zzak(parcel);
                    }
                    this.zzag((DataHolder)object);
                    return true;
                }
                case 2: {
                    parcel.enforceInterface("com.google.android.gms.wearable.internal.IWearableListener");
                    object = var6_5;
                    if (parcel.readInt() != 0) {
                        object = (MessageEventParcelable)MessageEventParcelable.CREATOR.createFromParcel(parcel);
                    }
                    this.zza((MessageEventParcelable)object);
                    return true;
                }
                case 3: {
                    parcel.enforceInterface("com.google.android.gms.wearable.internal.IWearableListener");
                    object = var7_6;
                    if (parcel.readInt() != 0) {
                        object = (NodeParcelable)NodeParcelable.CREATOR.createFromParcel(parcel);
                    }
                    this.zza((NodeParcelable)object);
                    return true;
                }
                case 4: {
                    parcel.enforceInterface("com.google.android.gms.wearable.internal.IWearableListener");
                    object = var8_7;
                    if (parcel.readInt() != 0) {
                        object = (NodeParcelable)NodeParcelable.CREATOR.createFromParcel(parcel);
                    }
                    this.zzb((NodeParcelable)object);
                    return true;
                }
                case 5: {
                    parcel.enforceInterface("com.google.android.gms.wearable.internal.IWearableListener");
                    this.onConnectedNodes(parcel.createTypedArrayList(NodeParcelable.CREATOR));
                    return true;
                }
                case 6: {
                    parcel.enforceInterface("com.google.android.gms.wearable.internal.IWearableListener");
                    object = var9_8;
                    if (parcel.readInt() != 0) {
                        object = (AncsNotificationParcelable)AncsNotificationParcelable.CREATOR.createFromParcel(parcel);
                    }
                    this.zza((AncsNotificationParcelable)object);
                    return true;
                }
                case 7: {
                    parcel.enforceInterface("com.google.android.gms.wearable.internal.IWearableListener");
                    object = var10_9;
                    if (parcel.readInt() != 0) {
                        object = (ChannelEventParcelable)ChannelEventParcelable.CREATOR.createFromParcel(parcel);
                    }
                    this.zza((ChannelEventParcelable)object);
                    return true;
                }
                case 8: {
                    parcel.enforceInterface("com.google.android.gms.wearable.internal.IWearableListener");
                    object = var11_10;
                    if (parcel.readInt() != 0) {
                        object = (CapabilityInfoParcelable)CapabilityInfoParcelable.CREATOR.createFromParcel(parcel);
                    }
                    this.zza((CapabilityInfoParcelable)object);
                    return true;
                }
                case 9: 
            }
            parcel.enforceInterface("com.google.android.gms.wearable.internal.IWearableListener");
            object = var12_11;
            if (parcel.readInt() != 0) {
                object = (AmsEntityUpdateParcelable)AmsEntityUpdateParcelable.CREATOR.createFromParcel(parcel);
            }
            this.zza((AmsEntityUpdateParcelable)object);
            return true;
        }

        private static class zza
        implements zzaw {
            private IBinder zzoz;

            zza(IBinder iBinder) {
                this.zzoz = iBinder;
            }

            public IBinder asBinder() {
                return this.zzoz;
            }

            @Override
            public void onConnectedNodes(List<NodeParcelable> list) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.wearable.internal.IWearableListener");
                    parcel.writeTypedList(list);
                    this.zzoz.transact(5, parcel, null, 1);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void zza(AmsEntityUpdateParcelable amsEntityUpdateParcelable) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.wearable.internal.IWearableListener");
                    if (amsEntityUpdateParcelable != null) {
                        parcel.writeInt(1);
                        amsEntityUpdateParcelable.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.zzoz.transact(9, parcel, null, 1);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void zza(AncsNotificationParcelable ancsNotificationParcelable) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.wearable.internal.IWearableListener");
                    if (ancsNotificationParcelable != null) {
                        parcel.writeInt(1);
                        ancsNotificationParcelable.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.zzoz.transact(6, parcel, null, 1);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void zza(CapabilityInfoParcelable capabilityInfoParcelable) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.wearable.internal.IWearableListener");
                    if (capabilityInfoParcelable != null) {
                        parcel.writeInt(1);
                        capabilityInfoParcelable.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.zzoz.transact(8, parcel, null, 1);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void zza(ChannelEventParcelable channelEventParcelable) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.wearable.internal.IWearableListener");
                    if (channelEventParcelable != null) {
                        parcel.writeInt(1);
                        channelEventParcelable.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.zzoz.transact(7, parcel, null, 1);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void zza(MessageEventParcelable messageEventParcelable) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.wearable.internal.IWearableListener");
                    if (messageEventParcelable != null) {
                        parcel.writeInt(1);
                        messageEventParcelable.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.zzoz.transact(2, parcel, null, 1);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void zza(NodeParcelable nodeParcelable) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.wearable.internal.IWearableListener");
                    if (nodeParcelable != null) {
                        parcel.writeInt(1);
                        nodeParcelable.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.zzoz.transact(3, parcel, null, 1);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void zzag(DataHolder dataHolder) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.wearable.internal.IWearableListener");
                    if (dataHolder != null) {
                        parcel.writeInt(1);
                        dataHolder.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.zzoz.transact(1, parcel, null, 1);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void zzb(NodeParcelable nodeParcelable) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.wearable.internal.IWearableListener");
                    if (nodeParcelable != null) {
                        parcel.writeInt(1);
                        nodeParcelable.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.zzoz.transact(4, parcel, null, 1);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }
        }
    }
}

