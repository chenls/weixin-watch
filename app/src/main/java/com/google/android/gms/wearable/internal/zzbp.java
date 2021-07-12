/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.IntentFilter
 *  android.net.Uri
 *  android.os.Bundle
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.Looper
 *  android.os.ParcelFileDescriptor
 *  android.os.ParcelFileDescriptor$AutoCloseOutputStream
 *  android.os.RemoteException
 *  android.util.Log
 */
package com.google.android.gms.wearable.internal;

import android.content.Context;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.zza;
import com.google.android.gms.common.api.internal.zzq;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzf;
import com.google.android.gms.common.internal.zzj;
import com.google.android.gms.common.internal.zzx;
import com.google.android.gms.wearable.Asset;
import com.google.android.gms.wearable.CapabilityApi;
import com.google.android.gms.wearable.Channel;
import com.google.android.gms.wearable.ChannelApi;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataItemAsset;
import com.google.android.gms.wearable.DataItemBuffer;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.internal.PutDataResponse;
import com.google.android.gms.wearable.internal.zzav;
import com.google.android.gms.wearable.internal.zzax;
import com.google.android.gms.wearable.internal.zzay;
import com.google.android.gms.wearable.internal.zzbj;
import com.google.android.gms.wearable.internal.zzbo;
import com.google.android.gms.wearable.internal.zzbq;
import com.google.android.gms.wearable.internal.zzt;
import com.google.android.gms.wearable.zza;
import com.google.android.gms.wearable.zzc;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class zzbp
extends zzj<zzax> {
    private final ExecutorService zzbkn = Executors.newCachedThreadPool();
    private final zzay<zzc.zza> zzbte = new zzay();
    private final zzay<zza.zza> zzbtf = new zzay();
    private final zzay<ChannelApi.ChannelListener> zzbtg = new zzay();
    private final zzay<DataApi.DataListener> zzbth = new zzay();
    private final zzay<MessageApi.MessageListener> zzbti = new zzay();
    private final zzay<NodeApi.NodeListener> zzbtj = new zzay();
    private final zzay<NodeApi.zza> zzbtk = new zzay();
    private final zzay<CapabilityApi.CapabilityListener> zzbtl = new zzay();

    public zzbp(Context context, Looper looper, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener, zzf zzf2) {
        super(context, looper, 14, zzf2, connectionCallbacks, onConnectionFailedListener);
    }

    private FutureTask<Boolean> zza(final ParcelFileDescriptor parcelFileDescriptor, final byte[] byArray) {
        return new FutureTask<Boolean>(new Callable<Boolean>(){

            @Override
            public /* synthetic */ Object call() throws Exception {
                return this.zzvt();
            }

            /*
             * Loose catch block
             * Enabled force condition propagation
             * Lifted jumps to return sites
             */
            public Boolean zzvt() {
                if (Log.isLoggable((String)"WearableClient", (int)3)) {
                    Log.d((String)"WearableClient", (String)("processAssets: writing data to FD : " + parcelFileDescriptor));
                }
                ParcelFileDescriptor.AutoCloseOutputStream autoCloseOutputStream = new ParcelFileDescriptor.AutoCloseOutputStream(parcelFileDescriptor);
                autoCloseOutputStream.write(byArray);
                autoCloseOutputStream.flush();
                if (Log.isLoggable((String)"WearableClient", (int)3)) {
                    Log.d((String)"WearableClient", (String)("processAssets: wrote data: " + parcelFileDescriptor));
                }
                Boolean bl2 = true;
                try {
                    if (Log.isLoggable((String)"WearableClient", (int)3)) {
                        Log.d((String)"WearableClient", (String)("processAssets: closing: " + parcelFileDescriptor));
                    }
                    autoCloseOutputStream.close();
                    return bl2;
                }
                catch (IOException iOException) {
                    return bl2;
                }
                catch (IOException iOException) {
                    try {
                        Log.w((String)"WearableClient", (String)("processAssets: writing data failed: " + parcelFileDescriptor));
                    }
                    catch (Throwable throwable) {
                        try {
                            if (Log.isLoggable((String)"WearableClient", (int)3)) {
                                Log.d((String)"WearableClient", (String)("processAssets: closing: " + parcelFileDescriptor));
                            }
                            autoCloseOutputStream.close();
                        }
                        catch (IOException iOException2) {
                            throw throwable;
                        }
                        throw throwable;
                    }
                    try {
                        if (Log.isLoggable((String)"WearableClient", (int)3)) {
                            Log.d((String)"WearableClient", (String)("processAssets: closing: " + parcelFileDescriptor));
                        }
                        autoCloseOutputStream.close();
                    }
                    catch (IOException iOException3) {
                        return false;
                    }
                    return false;
                }
            }
        });
    }

    /*
     * Enabled aggressive block sorting
     */
    private Runnable zzb(final zza.zzb<Status> zzb2, final String string2, final Uri uri, final long l2, final long l3) {
        zzx.zzz(zzb2);
        zzx.zzz(string2);
        zzx.zzz(uri);
        boolean bl2 = l2 >= 0L;
        zzx.zzb(bl2, "startOffset is negative: %s", l2);
        bl2 = l3 >= -1L;
        zzx.zzb(bl2, "invalid length: %s", l3);
        return new Runnable(){

            /*
             * Loose catch block
             * Enabled force condition propagation
             * Lifted jumps to return sites
             */
            @Override
            public void run() {
                ParcelFileDescriptor parcelFileDescriptor;
                if (Log.isLoggable((String)"WearableClient", (int)2)) {
                    Log.v((String)"WearableClient", (String)"Executing sendFileToChannelTask");
                }
                if (!"file".equals(uri.getScheme())) {
                    Log.w((String)"WearableClient", (String)"Channel.sendFile used with non-file URI");
                    zzb2.zzw(new Status(10, "Channel.sendFile used with non-file URI"));
                    return;
                }
                File file = new File(uri.getPath());
                try {
                    parcelFileDescriptor = ParcelFileDescriptor.open((File)file, (int)0x10000000);
                }
                catch (FileNotFoundException fileNotFoundException) {
                    Log.w((String)"WearableClient", (String)("File couldn't be opened for Channel.sendFile: " + file));
                    zzb2.zzw(new Status(13));
                    return;
                }
                ((zzax)zzbp.this.zzqJ()).zza(new zzbo.zzr(zzb2), string2, parcelFileDescriptor, l2, l3);
                try {
                    parcelFileDescriptor.close();
                    return;
                }
                catch (IOException iOException) {
                    Log.w((String)"WearableClient", (String)"Failed to close sourceFd", (Throwable)iOException);
                    return;
                }
                catch (RemoteException remoteException) {
                    try {
                        Log.w((String)"WearableClient", (String)"Channel.sendFile failed.", (Throwable)remoteException);
                        zzb2.zzw(new Status(8));
                    }
                    catch (Throwable throwable) {
                        try {
                            parcelFileDescriptor.close();
                        }
                        catch (IOException iOException) {
                            Log.w((String)"WearableClient", (String)"Failed to close sourceFd", (Throwable)iOException);
                            throw throwable;
                        }
                        throw throwable;
                    }
                    try {
                        parcelFileDescriptor.close();
                        return;
                    }
                    catch (IOException iOException) {
                        Log.w((String)"WearableClient", (String)"Failed to close sourceFd", (Throwable)iOException);
                        return;
                    }
                }
            }
        };
    }

    private Runnable zzb(final zza.zzb<Status> zzb2, final String string2, final Uri uri, final boolean bl2) {
        zzx.zzz(zzb2);
        zzx.zzz(string2);
        zzx.zzz(uri);
        return new Runnable(){

            /*
             * Loose catch block
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void run() {
                ParcelFileDescriptor parcelFileDescriptor;
                if (Log.isLoggable((String)"WearableClient", (int)2)) {
                    Log.v((String)"WearableClient", (String)"Executing receiveFileFromChannelTask");
                }
                if (!"file".equals(uri.getScheme())) {
                    Log.w((String)"WearableClient", (String)"Channel.receiveFile used with non-file URI");
                    zzb2.zzw(new Status(10, "Channel.receiveFile used with non-file URI"));
                    return;
                }
                File file = new File(uri.getPath());
                int n2 = bl2 ? 0x2000000 : 0;
                try {
                    parcelFileDescriptor = ParcelFileDescriptor.open((File)file, (int)(n2 | 0x20000000));
                }
                catch (FileNotFoundException fileNotFoundException) {
                    Log.w((String)"WearableClient", (String)("File couldn't be opened for Channel.receiveFile: " + file));
                    zzb2.zzw(new Status(13));
                    return;
                }
                ((zzax)zzbp.this.zzqJ()).zza((zzav)new zzbo.zzu(zzb2), string2, parcelFileDescriptor);
                try {
                    parcelFileDescriptor.close();
                    return;
                }
                catch (IOException iOException) {
                    Log.w((String)"WearableClient", (String)"Failed to close targetFd", (Throwable)iOException);
                    return;
                }
                catch (RemoteException remoteException) {
                    try {
                        Log.w((String)"WearableClient", (String)"Channel.receiveFile failed.", (Throwable)remoteException);
                        zzb2.zzw(new Status(8));
                    }
                    catch (Throwable throwable) {
                        try {
                            parcelFileDescriptor.close();
                        }
                        catch (IOException iOException) {
                            Log.w((String)"WearableClient", (String)"Failed to close targetFd", (Throwable)iOException);
                            throw throwable;
                        }
                        throw throwable;
                    }
                    try {
                        parcelFileDescriptor.close();
                        return;
                    }
                    catch (IOException iOException) {
                        Log.w((String)"WearableClient", (String)"Failed to close targetFd", (Throwable)iOException);
                        return;
                    }
                }
            }
        };
    }

    @Override
    protected /* synthetic */ IInterface zzW(IBinder iBinder) {
        return this.zzew(iBinder);
    }

    @Override
    protected void zza(int n2, IBinder iBinder, Bundle bundle, int n3) {
        if (Log.isLoggable((String)"WearableClient", (int)2)) {
            Log.d((String)"WearableClient", (String)("onPostInitHandler: statusCode " + n2));
        }
        if (n2 == 0) {
            this.zzbte.zzev(iBinder);
            this.zzbtf.zzev(iBinder);
            this.zzbtg.zzev(iBinder);
            this.zzbth.zzev(iBinder);
            this.zzbti.zzev(iBinder);
            this.zzbtj.zzev(iBinder);
            this.zzbtk.zzev(iBinder);
            this.zzbtl.zzev(iBinder);
        }
        super.zza(n2, iBinder, bundle, n3);
    }

    public void zza(zza.zzb<DataApi.DataItemResult> zzb2, Uri uri) throws RemoteException {
        ((zzax)this.zzqJ()).zza((zzav)new zzbo.zzk(zzb2), uri);
    }

    public void zza(zza.zzb<DataItemBuffer> zzb2, Uri uri, int n2) throws RemoteException {
        ((zzax)this.zzqJ()).zza((zzav)new zzbo.zzl(zzb2), uri, n2);
    }

    public void zza(zza.zzb<DataApi.GetFdForAssetResult> zzb2, Asset asset) throws RemoteException {
        ((zzax)this.zzqJ()).zza((zzav)new zzbo.zzm(zzb2), asset);
    }

    public void zza(zza.zzb<Status> zzb2, CapabilityApi.CapabilityListener capabilityListener) throws RemoteException {
        this.zzbtl.zza(this, zzb2, capabilityListener);
    }

    public void zza(zza.zzb<Status> zzb2, CapabilityApi.CapabilityListener capabilityListener, zzq<CapabilityApi.CapabilityListener> zzq2, IntentFilter[] intentFilterArray) throws RemoteException {
        this.zzbtl.zza(this, zzb2, capabilityListener, zzbq.zze(zzq2, intentFilterArray));
    }

    public void zza(zza.zzb<Status> zzb2, ChannelApi.ChannelListener channelListener, zzq<ChannelApi.ChannelListener> zzq2, String string2, IntentFilter[] intentFilterArray) throws RemoteException {
        if (string2 == null) {
            this.zzbtg.zza(this, zzb2, channelListener, zzbq.zzd(zzq2, intentFilterArray));
            return;
        }
        channelListener = new zzbj(string2, channelListener);
        this.zzbtg.zza(this, zzb2, channelListener, zzbq.zza(zzq2, string2, intentFilterArray));
    }

    public void zza(zza.zzb<Status> zzb2, ChannelApi.ChannelListener channelListener, String string2) throws RemoteException {
        if (string2 == null) {
            this.zzbtg.zza(this, zzb2, channelListener);
            return;
        }
        channelListener = new zzbj(string2, channelListener);
        this.zzbtg.zza(this, zzb2, channelListener);
    }

    public void zza(zza.zzb<Status> zzb2, DataApi.DataListener dataListener) throws RemoteException {
        this.zzbth.zza(this, zzb2, dataListener);
    }

    public void zza(zza.zzb<Status> zzb2, DataApi.DataListener dataListener, zzq<DataApi.DataListener> zzq2, IntentFilter[] intentFilterArray) throws RemoteException {
        this.zzbth.zza(this, zzb2, dataListener, zzbq.zza(zzq2, intentFilterArray));
    }

    public void zza(zza.zzb<DataApi.GetFdForAssetResult> zzb2, DataItemAsset dataItemAsset) throws RemoteException {
        this.zza(zzb2, Asset.createFromRef(dataItemAsset.getId()));
    }

    public void zza(zza.zzb<Status> zzb2, MessageApi.MessageListener messageListener) throws RemoteException {
        this.zzbti.zza(this, zzb2, messageListener);
    }

    public void zza(zza.zzb<Status> zzb2, MessageApi.MessageListener messageListener, zzq<MessageApi.MessageListener> zzq2, IntentFilter[] intentFilterArray) throws RemoteException {
        this.zzbti.zza(this, zzb2, messageListener, zzbq.zzb(zzq2, intentFilterArray));
    }

    public void zza(zza.zzb<Status> zzb2, NodeApi.NodeListener nodeListener) throws RemoteException {
        this.zzbtj.zza(this, zzb2, nodeListener);
    }

    public void zza(zza.zzb<Status> zzb2, NodeApi.NodeListener nodeListener, zzq<NodeApi.NodeListener> zzq2, IntentFilter[] intentFilterArray) throws RemoteException {
        this.zzbtj.zza(this, zzb2, nodeListener, zzbq.zzc(zzq2, intentFilterArray));
    }

    public void zza(zza.zzb<DataApi.DataItemResult> zzb2, PutDataRequest putDataRequest) throws RemoteException {
        SafeParcelable safeParcelable;
        Object object = putDataRequest.getAssets().entrySet().iterator();
        while (object.hasNext()) {
            safeParcelable = object.next().getValue();
            if (((Asset)safeParcelable).getData() != null || ((Asset)safeParcelable).getDigest() != null || ((Asset)safeParcelable).getFd() != null || ((Asset)safeParcelable).getUri() != null) continue;
            throw new IllegalArgumentException("Put for " + putDataRequest.getUri() + " contains invalid asset: " + safeParcelable);
        }
        safeParcelable = PutDataRequest.zzr(putDataRequest.getUri());
        ((PutDataRequest)safeParcelable).setData(putDataRequest.getData());
        if (putDataRequest.isUrgent()) {
            ((PutDataRequest)safeParcelable).setUrgent();
        }
        object = new ArrayList();
        for (Map.Entry<String, Asset> entry : putDataRequest.getAssets().entrySet()) {
            Object object2;
            Object object3 = entry.getValue();
            if (((Asset)object3).getData() != null) {
                try {
                    object2 = ParcelFileDescriptor.createPipe();
                }
                catch (IOException iOException) {
                    throw new IllegalStateException("Unable to create ParcelFileDescriptor for asset in request: " + putDataRequest, iOException);
                }
                if (Log.isLoggable((String)"WearableClient", (int)3)) {
                    Log.d((String)"WearableClient", (String)("processAssets: replacing data with FD in asset: " + object3 + " read:" + object2[0] + " write:" + object2[1]));
                }
                ((PutDataRequest)safeParcelable).putAsset(entry.getKey(), Asset.createFromFd(object2[0]));
                object3 = this.zza(object2[1], ((Asset)object3).getData());
                object.add(object3);
                this.zzbkn.submit((Runnable)object3);
                continue;
            }
            if (((Asset)object3).getUri() != null) {
                try {
                    object2 = Asset.createFromFd(this.getContext().getContentResolver().openFileDescriptor(((Asset)object3).getUri(), "r"));
                    ((PutDataRequest)safeParcelable).putAsset(entry.getKey(), (Asset)object2);
                    continue;
                }
                catch (FileNotFoundException fileNotFoundException) {
                    new zzbo.zzq(zzb2, (List<FutureTask<Boolean>>)object).zza(new PutDataResponse(4005, null));
                    Log.w((String)"WearableClient", (String)("Couldn't resolve asset URI: " + ((Asset)object3).getUri()));
                    return;
                }
            }
            ((PutDataRequest)safeParcelable).putAsset(entry.getKey(), (Asset)object3);
        }
        ((zzax)this.zzqJ()).zza((zzav)new zzbo.zzq(zzb2, (List<FutureTask<Boolean>>)object), (PutDataRequest)safeParcelable);
    }

    public void zza(zza.zzb<Status> zzb2, String string2, Uri uri, long l2, long l3) {
        try {
            this.zzbkn.execute(this.zzb(zzb2, string2, uri, l2, l3));
            return;
        }
        catch (RuntimeException runtimeException) {
            zzb2.zzw(new Status(8));
            throw runtimeException;
        }
    }

    public void zza(zza.zzb<Status> zzb2, String string2, Uri uri, boolean bl2) {
        try {
            this.zzbkn.execute(this.zzb(zzb2, string2, uri, bl2));
            return;
        }
        catch (RuntimeException runtimeException) {
            zzb2.zzw(new Status(8));
            throw runtimeException;
        }
    }

    public void zza(zza.zzb<MessageApi.SendMessageResult> zzb2, String string2, String string3, byte[] byArray) throws RemoteException {
        ((zzax)this.zzqJ()).zza(new zzbo.zzt(zzb2), string2, string3, byArray);
    }

    public void zzb(zza.zzb<CapabilityApi.GetAllCapabilitiesResult> zzb2, int n2) throws RemoteException {
        ((zzax)this.zzqJ()).zza((zzav)new zzbo.zzf(zzb2), n2);
    }

    public void zzb(zza.zzb<DataApi.DeleteDataItemsResult> zzb2, Uri uri, int n2) throws RemoteException {
        ((zzax)this.zzqJ()).zzb((zzav)new zzbo.zze(zzb2), uri, n2);
    }

    public void zze(zza.zzb<ChannelApi.OpenChannelResult> zzb2, String string2, String string3) throws RemoteException {
        ((zzax)this.zzqJ()).zza((zzav)new zzbo.zzp(zzb2), string2, string3);
    }

    protected zzax zzew(IBinder iBinder) {
        return zzax.zza.zzeu(iBinder);
    }

    public void zzg(zza.zzb<CapabilityApi.GetCapabilityResult> zzb2, String string2, int n2) throws RemoteException {
        ((zzax)this.zzqJ()).zza((zzav)new zzbo.zzg(zzb2), string2, n2);
    }

    @Override
    protected String zzgu() {
        return "com.google.android.gms.wearable.BIND";
    }

    @Override
    protected String zzgv() {
        return "com.google.android.gms.wearable.internal.IWearableService";
    }

    public void zzh(zza.zzb<Status> zzb2, String string2, int n2) throws RemoteException {
        ((zzax)this.zzqJ()).zzb((zzav)new zzbo.zzd(zzb2), string2, n2);
    }

    public void zzr(zza.zzb<DataItemBuffer> zzb2) throws RemoteException {
        ((zzax)this.zzqJ()).zzb(new zzbo.zzl(zzb2));
    }

    public void zzr(zza.zzb<CapabilityApi.AddLocalCapabilityResult> zzb2, String string2) throws RemoteException {
        ((zzax)this.zzqJ()).zzd(new zzbo.zza(zzb2), string2);
    }

    public void zzs(zza.zzb<NodeApi.GetLocalNodeResult> zzb2) throws RemoteException {
        ((zzax)this.zzqJ()).zzc(new zzbo.zzn(zzb2));
    }

    public void zzs(zza.zzb<CapabilityApi.RemoveLocalCapabilityResult> zzb2, String string2) throws RemoteException {
        ((zzax)this.zzqJ()).zze(new zzbo.zzs(zzb2), string2);
    }

    public void zzt(zza.zzb<NodeApi.GetConnectedNodesResult> zzb2) throws RemoteException {
        ((zzax)this.zzqJ()).zzd(new zzbo.zzj(zzb2));
    }

    public void zzt(zza.zzb<Status> zzb2, String string2) throws RemoteException {
        ((zzax)this.zzqJ()).zzf(new zzbo.zzc(zzb2), string2);
    }

    public void zzu(zza.zzb<Channel.GetInputStreamResult> zzb2, String string2) throws RemoteException {
        zzt zzt2 = new zzt();
        ((zzax)this.zzqJ()).zza((zzav)new zzbo.zzh(zzb2, zzt2), zzt2, string2);
    }

    public void zzv(zza.zzb<Channel.GetOutputStreamResult> zzb2, String string2) throws RemoteException {
        zzt zzt2 = new zzt();
        ((zzax)this.zzqJ()).zzb((zzav)new zzbo.zzi(zzb2, zzt2), zzt2, string2);
    }
}

