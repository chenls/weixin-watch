/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.content.ServiceConnection
 *  android.content.pm.ResolveInfo
 *  android.os.IBinder
 *  android.os.RemoteException
 *  android.util.Log
 */
package com.mobvoi.android.gesture;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ResolveInfo;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import java.util.concurrent.atomic.AtomicBoolean;
import mobvoiapi.ah;
import mobvoiapi.ai;

public class MobvoiGestureClient {
    public static final String ACTION_GESTURE_SERVICE = "com.mobvoi.ticwear.gesture.GestureService";
    public static final String SERVICE_PACKAGE = "com.mobvoi.ticwear.gesturerecognizer";
    public static final String TAG = "MobvoiGestureClient";
    private ai a = new ai.a(){

        @Override
        public void onGestureDetected(int n2) throws RemoteException {
            Log.d((String)MobvoiGestureClient.TAG, (String)("onGestureDected: " + n2));
            MobvoiGestureClient.this.d.onGestureDetected(n2);
        }
    };
    private int b;
    private ah c = null;
    private IGestureDetectedCallback d;
    private MyConnection e = null;
    private boolean f = false;
    private boolean g = false;
    private AtomicBoolean h = new AtomicBoolean(false);

    private MobvoiGestureClient(int n2) {
        this.b = n2;
    }

    static /* synthetic */ ah a(MobvoiGestureClient mobvoiGestureClient, ah ah2) {
        mobvoiGestureClient.c = ah2;
        return ah2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void a(Context context) {
        if (this.c != null) {
            try {
                this.c.b(this.a, this.b);
            }
            catch (RemoteException remoteException) {
                Log.e((String)TAG, (String)remoteException.getMessage(), (Throwable)remoteException);
            }
        }
        context.getApplicationContext().unbindService((ServiceConnection)this.e);
    }

    public static Intent createExplicitFromImplicitIntent(Context object, Intent intent) {
        if ((object = object.getPackageManager().queryIntentServices(intent, 0)) == null || object.size() != 1) {
            return null;
        }
        object = (ResolveInfo)object.get(0);
        object = new ComponentName(object.serviceInfo.packageName, object.serviceInfo.name);
        intent = new Intent(intent);
        intent.setComponent((ComponentName)object);
        return intent;
    }

    public static MobvoiGestureClient getInstance(int n2) {
        return new MobvoiGestureClient(n2);
    }

    /*
     * Enabled aggressive block sorting
     */
    public static boolean startService(Context context) {
        Log.d((String)TAG, (String)"startService");
        Intent intent = new Intent(ACTION_GESTURE_SERVICE);
        intent.setPackage(SERVICE_PACKAGE);
        intent = MobvoiGestureClient.createExplicitFromImplicitIntent(context, intent);
        if (intent == null) {
            Log.w((String)TAG, (String)"can't find intent");
            return false;
        } else {
            if (context.startService(intent) == null) return false;
            return true;
        }
    }

    public static boolean stopService(Context context) {
        Log.d((String)TAG, (String)"stopService");
        Intent intent = new Intent(ACTION_GESTURE_SERVICE);
        intent.setPackage(SERVICE_PACKAGE);
        intent = MobvoiGestureClient.createExplicitFromImplicitIntent(context, intent);
        if (intent == null) {
            Log.w((String)TAG, (String)"can't find intent");
            return false;
        }
        return context.stopService(intent);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean register(Context object, IGestureDetectedCallback iGestureDetectedCallback) {
        boolean bl2 = false;
        synchronized (this) {
            if (!this.h.compareAndSet(false, true)) {
                throw new RuntimeException("register already called. you should not reuse a MobvoiGestureClient, try use a new one");
            }
            Intent intent = new Intent(ACTION_GESTURE_SERVICE);
            intent.setPackage(SERVICE_PACKAGE);
            intent = MobvoiGestureClient.createExplicitFromImplicitIntent(object, intent);
            if (intent == null) {
                Log.w((String)TAG, (String)"can't find intent");
            } else {
                this.e = new MyConnection((Context)object);
                this.f = object.getApplicationContext().bindService(intent, (ServiceConnection)this.e, 1);
                this.g = true;
                if (!this.f) {
                    Log.w((String)TAG, (String)"can't bind");
                } else {
                    this.d = iGestureDetectedCallback;
                    if (!Log.isLoggable((String)TAG, (int)3)) return true;
                    object = object.getApplicationInfo().packageName;
                    Log.d((String)TAG, (String)("Gesture register successfully for " + (String)object));
                    return true;
                }
            }
            return bl2;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean unregister(Context object) {
        synchronized (this) {
            if (!this.g) {
                throw new RuntimeException("unregister called before register finish");
            }
            this.a((Context)object);
            if (Log.isLoggable((String)TAG, (int)3)) {
                object = object.getApplicationInfo().packageName;
                Log.d((String)TAG, (String)("Gesture unregister successfully for " + (String)object));
            }
            return true;
        }
    }

    public static interface IGestureDetectedCallback {
        public void onGestureDetected(int var1);
    }

    class MyConnection
    implements ServiceConnection {
        private Context b;

        public MyConnection(Context context) {
            this.b = context;
        }

        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.d((String)MobvoiGestureClient.TAG, (String)"OnServiceConnected");
            MobvoiGestureClient.a(MobvoiGestureClient.this, ah.a.a(iBinder));
            try {
                MobvoiGestureClient.this.c.a(MobvoiGestureClient.this.a, MobvoiGestureClient.this.b);
                return;
            }
            catch (RemoteException remoteException) {
                Log.e((String)MobvoiGestureClient.TAG, (String)remoteException.getMessage(), (Throwable)remoteException);
                return;
            }
        }

        public void onServiceDisconnected(ComponentName componentName) {
            Log.d((String)MobvoiGestureClient.TAG, (String)"onServiceDisconnected");
            MobvoiGestureClient.a(MobvoiGestureClient.this, null);
        }
    }
}

