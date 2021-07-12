/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 */
package com.mobvoi.android.common;

import android.content.Context;
import android.content.pm.PackageManager;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.Wearable;
import com.mobvoi.android.common.NoAvailableServiceException;
import com.mobvoi.android.common.api.Api;
import com.mobvoi.android.common.api.MobvoiApiClient;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import mobvoiapi.ab;
import mobvoiapi.be;
import mobvoiapi.bp;
import mobvoiapi.i;

public class MobvoiApiManager {
    public static final String TAG = "MobvoiApiManager";
    private static final String[] a = new String[]{"com.mobvoi.android", "com.mobvoi.companion", "com.mobvoi.companion.global"};
    private static final String[] b = new String[]{"", "", ""};
    private static final String[] c = new String[]{"com.google.android.gms", "com.google.android.wearable.app", "com.google.android.wearable.app.cn"};
    private static final String[] d = new String[]{"", "", ""};
    private static CertificateFactory e;
    private static MobvoiApiManager f;
    private ApiGroup g = ApiGroup.MMS;
    private boolean h = false;
    private Set<ab> i = new HashSet<ab>();

    static {
        try {
            e = CertificateFactory.getInstance("X.509");
        }
        catch (CertificateException certificateException) {}
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void a() throws NoAvailableServiceException {
        synchronized (this) {
            if (this.g == ApiGroup.NONE) {
                throw new NoAvailableServiceException();
            }
            this.h = true;
            bp.a(TAG, "start load proxies, group = " + (Object)((Object)this.g));
            Iterator<ab> iterator = this.i.iterator();
            while (iterator.hasNext()) {
                iterator.next().a();
            }
            return;
        }
    }

    private boolean a(PackageManager packageManager, String string2, String string3) {
        try {
            packageManager.getPackageInfo(string2, 0);
            return true;
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            bp.b(TAG, string2 + " service is invalid.");
            return false;
        }
        catch (Exception exception) {
            bp.b(TAG, "Fail to check the package " + string2, exception);
            return false;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static MobvoiApiManager getInstance() {
        if (f == null) {
            synchronized (MobvoiApiManager.class) {
                if (f == null) {
                    f = new MobvoiApiManager();
                }
            }
        }
        return f;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void adaptService(Context context) throws NoAvailableServiceException {
        if (this.isMmsAvailable(context)) {
            this.g = ApiGroup.MMS;
        } else {
            if (!this.isGmsAvailable(context)) {
                throw new NoAvailableServiceException();
            }
            this.g = ApiGroup.GMS;
        }
        this.a();
    }

    public void checkGmsNodeConnected(final Context context, final NodeAvailableCallback nodeAvailableCallback) {
        if (!this.isGmsAvailable(context)) {
            nodeAvailableCallback.onNodeAvailability(false);
            return;
        }
        new Thread(){

            /*
             * Enabled aggressive block sorting
             */
            @Override
            public void run() {
                Object object = new GoogleApiClient.Builder(context).addApi(Wearable.API).build();
                ((GoogleApiClient)object).blockingConnect(2000L, TimeUnit.MILLISECONDS);
                if (!((GoogleApiClient)object).isConnected()) {
                    nodeAvailableCallback.onNodeAvailability(false);
                    return;
                }
                if (!(object = Wearable.NodeApi.getConnectedNodes((GoogleApiClient)object).await(2000L, TimeUnit.MILLISECONDS)).getStatus().isSuccess()) {
                    nodeAvailableCallback.onNodeAvailability(false);
                    return;
                }
                NodeAvailableCallback nodeAvailableCallback2 = nodeAvailableCallback;
                boolean bl2 = !object.getNodes().isEmpty();
                nodeAvailableCallback2.onNodeAvailability(bl2);
            }
        }.start();
    }

    public void checkMmsNodeConnected(final Context context, final NodeAvailableCallback nodeAvailableCallback) {
        if (!this.isMmsAvailable(context)) {
            nodeAvailableCallback.onNodeAvailability(false);
            return;
        }
        new Thread(){

            /*
             * Enabled aggressive block sorting
             */
            @Override
            public void run() {
                Object object = new HashSet<Api>();
                object.add(com.mobvoi.android.wearable.Wearable.API);
                object = new i(context, (Set<Api>)object, (Set<MobvoiApiClient.ConnectionCallbacks>)new HashSet<MobvoiApiClient.ConnectionCallbacks>(), (Set<MobvoiApiClient.OnConnectionFailedListener>)new HashSet<MobvoiApiClient.OnConnectionFailedListener>(), null);
                object.blockingConnect(2000L, TimeUnit.MILLISECONDS);
                if (!object.isConnected()) {
                    nodeAvailableCallback.onNodeAvailability(false);
                    return;
                }
                if (!(object = new be().getConnectedNodes((MobvoiApiClient)object).await(2000L, TimeUnit.MILLISECONDS)).getStatus().isSuccess()) {
                    nodeAvailableCallback.onNodeAvailability(false);
                    return;
                }
                NodeAvailableCallback nodeAvailableCallback2 = nodeAvailableCallback;
                boolean bl2 = !object.getNodes().isEmpty();
                nodeAvailableCallback2.onNodeAvailability(bl2);
            }
        }.start();
    }

    public ApiGroup getGroup() {
        return this.g;
    }

    public boolean isGmsAvailable(Context context) {
        boolean bl2 = false;
        context = context.getPackageManager();
        int n2 = 0;
        while (true) {
            block4: {
                boolean bl3;
                block3: {
                    bl3 = bl2;
                    if (n2 >= c.length) break block3;
                    if (!this.a((PackageManager)context, c[n2], d[n2])) break block4;
                    bl3 = true;
                }
                return bl3;
            }
            ++n2;
        }
    }

    public boolean isInitialized() {
        synchronized (this) {
            boolean bl2 = this.h;
            return bl2;
        }
    }

    public boolean isMmsAvailable(Context context) {
        boolean bl2 = false;
        context = context.getPackageManager();
        int n2 = 0;
        while (true) {
            block4: {
                boolean bl3;
                block3: {
                    bl3 = bl2;
                    if (n2 >= a.length) break block3;
                    if (!this.a((PackageManager)context, a[n2], b[n2])) break block4;
                    bl3 = true;
                }
                return bl3;
            }
            ++n2;
        }
    }

    public void loadService(Context context, ApiGroup apiGroup) throws NoAvailableServiceException {
        this.g = apiGroup;
        if (apiGroup == ApiGroup.MMS ? !this.isMmsAvailable(context) : (apiGroup == ApiGroup.GMS ? !this.isGmsAvailable(context) : apiGroup == ApiGroup.NONE)) {
            throw new NoAvailableServiceException();
        }
        this.a();
    }

    public void registerProxy(ab ab2) {
        bp.a(TAG, "register proxy " + ab2.getClass().getSimpleName());
        this.i.add(ab2);
    }

    public static enum ApiGroup {
        MMS,
        GMS,
        NONE;

    }

    public static interface NodeAvailableCallback {
        public void onNodeAvailability(boolean var1);
    }
}

