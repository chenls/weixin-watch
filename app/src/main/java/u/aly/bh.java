/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.content.ServiceConnection
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.Parcel
 *  android.os.RemoteException
 */
package u.aly;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;

public class bh {
    public static String a(Context object) {
        block3: {
            try {
                object = bh.b(object);
                if (object != null) break block3;
                return null;
            }
            catch (Exception exception) {
                return null;
            }
        }
        object = ((a)object).b();
        return object;
    }

    private static a b(Context context) throws Exception {
        block7: {
            context.getPackageManager().getPackageInfo("com.android.vending", 0);
            b b2 = new b();
            Object object = new Intent("com.google.android.gms.ads.identifier.service.START");
            object.setPackage("com.google.android.gms");
            if (!context.bindService((Intent)object, (ServiceConnection)b2, 1)) break block7;
            try {
                object = new c(b2.a());
                object = new a(((c)object).a(), ((c)object).a(true));
                return object;
            }
            catch (Exception exception) {
                throw exception;
            }
            finally {
                context.unbindService((ServiceConnection)b2);
            }
        }
        throw new IOException("Google Play connection failed");
    }

    private static final class a {
        private final String a;
        private final boolean b;

        a(String string2, boolean bl2) {
            this.a = string2;
            this.b = bl2;
        }

        private String b() {
            return this.a;
        }

        public boolean a() {
            return this.b;
        }
    }

    private static final class b
    implements ServiceConnection {
        boolean a = false;
        private final LinkedBlockingQueue<IBinder> b = new LinkedBlockingQueue(1);

        private b() {
        }

        public IBinder a() throws InterruptedException {
            if (this.a) {
                throw new IllegalStateException();
            }
            this.a = true;
            return this.b.take();
        }

        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            try {
                this.b.put(iBinder);
                return;
            }
            catch (InterruptedException interruptedException) {
                return;
            }
        }

        public void onServiceDisconnected(ComponentName componentName) {
        }
    }

    private static final class c
    implements IInterface {
        private IBinder a;

        public c(IBinder iBinder) {
            this.a = iBinder;
        }

        public String a() throws RemoteException {
            Parcel parcel = Parcel.obtain();
            Parcel parcel2 = Parcel.obtain();
            try {
                parcel.writeInterfaceToken("com.google.android.gms.ads.identifier.internal.IAdvertisingIdService");
                this.a.transact(1, parcel, parcel2, 0);
                parcel2.readException();
                String string2 = parcel2.readString();
                return string2;
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
        public boolean a(boolean bl2) throws RemoteException {
            boolean bl3 = true;
            Parcel parcel = Parcel.obtain();
            Parcel parcel2 = Parcel.obtain();
            try {
                parcel.writeInterfaceToken("com.google.android.gms.ads.identifier.internal.IAdvertisingIdService");
                int n2 = bl2 ? 1 : 0;
                parcel.writeInt(n2);
                this.a.transact(2, parcel, parcel2, 0);
                parcel2.readException();
                n2 = parcel2.readInt();
                bl2 = n2 != 0 ? bl3 : false;
                return bl2;
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

