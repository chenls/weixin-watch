/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.os.DeadObjectException
 *  android.os.Handler
 *  android.os.Looper
 *  android.os.Message
 *  android.os.RemoteException
 *  android.util.Pair
 */
package mobvoiapi;

import android.os.DeadObjectException;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.util.Pair;
import com.mobvoi.android.common.api.Api;
import com.mobvoi.android.common.api.PendingResult;
import com.mobvoi.android.common.api.Releasable;
import com.mobvoi.android.common.api.Result;
import com.mobvoi.android.common.api.ResultCallback;
import com.mobvoi.android.common.api.Status;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import mobvoiapi.bp;
import mobvoiapi.i;

public class h {
    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static void a(Result result) {
        if (!(result instanceof Releasable)) return;
        try {
            ((Releasable)((Object)result)).release();
            return;
        }
        catch (RuntimeException runtimeException) {
            bp.a("MobvoiApi", "release " + result + " failed.", runtimeException);
            return;
        }
    }

    public static abstract class a<R extends Result>
    implements PendingResult<R> {
        private CountDownLatch a = new CountDownLatch(1);
        private boolean b = false;
        private final Object c = new Object();
        private boolean d;
        private boolean e;
        private R f;
        private mobvoiapi.b g;
        private ResultCallback<R> h;
        private c<R> i;

        static <R extends Result> void a(a<R> a2) {
            super.e();
        }

        private void b(R r2) {
            this.f = r2;
            this.g = null;
            this.a.countDown();
            if (this.h != null) {
                this.i.a();
                if (!this.d) {
                    this.i.a(this.h, this.c());
                }
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        private R c() {
            Object object = this.c;
            synchronized (object) {
                boolean bl2 = !this.b;
                mobvoiapi.a.b(bl2, "Result has already been consumed.");
                mobvoiapi.a.b(this.b(), "Result is not ready.");
                R r2 = this.f;
                this.a();
                return r2;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        private void d() {
            Object object = this.c;
            synchronized (object) {
                if (!this.b()) {
                    this.a(this.a(new Status(12)));
                    this.e = true;
                }
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        private void e() {
            Object object = this.c;
            synchronized (object) {
                if (!this.b()) {
                    this.a(this.a(new Status(13)));
                    this.e = true;
                }
                return;
            }
        }

        protected abstract R a(Status var1);

        protected void a() {
            this.b = true;
            this.f = null;
            this.h = null;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public final void a(R r2) {
            boolean bl2 = true;
            Object object = this.c;
            synchronized (object) {
                if (this.e || this.d) {
                    mobvoiapi.h.a(r2);
                    return;
                }
                boolean bl3 = !this.b();
                mobvoiapi.a.b(bl3, "Results have already been set");
                bl3 = !this.b ? bl2 : false;
                mobvoiapi.a.b(bl3, "Result has already been consumed");
                this.b(r2);
                return;
            }
        }

        protected void a(c<R> c2) {
            this.i = c2;
        }

        @Override
        public final R await() {
            return this.await(5L, TimeUnit.MINUTES);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public final R await(long l2, TimeUnit timeUnit) {
            boolean bl2 = true;
            boolean bl3 = Looper.myLooper() != Looper.getMainLooper();
            mobvoiapi.a.b(bl3, "await must not be called on the UI thread");
            bl3 = !this.b ? bl2 : false;
            mobvoiapi.a.b(bl3, "Result has already been consumed");
            try {
                if (!this.a.await(l2, timeUnit)) {
                    this.e();
                }
            }
            catch (InterruptedException interruptedException) {
                this.d();
            }
            mobvoiapi.a.b(this.b(), "Result is not ready.");
            return this.c();
        }

        public final boolean b() {
            return this.a.getCount() == 0L;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public boolean isCanceled() {
            Object object = this.c;
            synchronized (object) {
                return this.d;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public final void setResultCallback(ResultCallback<R> resultCallback) {
            boolean bl2 = !this.b;
            mobvoiapi.a.b(bl2, "Result has already been consumed.");
            Object object = this.c;
            synchronized (object) {
                if (!this.isCanceled() && this.b()) {
                    this.i.a(resultCallback, this.c());
                }
            }
            this.h = resultCallback;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public final void setResultCallback(ResultCallback<R> resultCallback, long l2, TimeUnit timeUnit) {
            boolean bl2 = true;
            boolean bl3 = !this.b;
            mobvoiapi.a.b(bl3, "Result has already been consumed.");
            bl3 = this.i != null ? bl2 : false;
            mobvoiapi.a.b(bl3, "CallbackHandler has not been set before calling setResultCallback.");
            Object object = this.c;
            synchronized (object) {
                if (this.isCanceled()) {
                    return;
                }
                if (this.b()) {
                    this.i.a(resultCallback, this.c());
                } else {
                    this.h = resultCallback;
                    this.i.a(this, timeUnit.toMillis(l2));
                }
                return;
            }
        }
    }

    public static abstract class b<R extends Result, A extends Api.Connection>
    extends a<R>
    implements i.a<A> {
        private final Api.Key<A> a;
        private i.b b;

        protected b(Api.Key<A> key) {
            this.a = key;
        }

        @Override
        private void a(RemoteException remoteException) {
            this.b(new Status(8, remoteException.getLocalizedMessage(), null));
        }

        @Override
        protected void a() {
            super.a();
            if (this.b != null) {
                this.b.a(this);
                this.b = null;
            }
        }

        @Override
        protected abstract void a(A var1) throws RemoteException;

        public void b(A a2) throws DeadObjectException {
            this.a((A)((Object)new c(a2.getLooper())));
            try {
                this.a(a2);
                return;
            }
            catch (DeadObjectException deadObjectException) {
                this.a((RemoteException)((Object)deadObjectException));
                return;
            }
            catch (RemoteException remoteException) {
                this.a(remoteException);
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         */
        public final void b(Status status) {
            boolean bl2 = !status.isSuccess();
            mobvoiapi.a.a(bl2, (Object)"Failed result must not be success");
            this.a((A)this.a(status));
        }

        public Api.Key<A> c() {
            return this.a;
        }
    }

    public static class c<R extends Result>
    extends Handler {
        public c() {
            this(Looper.getMainLooper());
        }

        public c(Looper looper) {
            super(looper);
        }

        public void a() {
            this.removeMessages(2);
        }

        public void a(a<R> a2, long l2) {
            this.sendMessageDelayed(this.obtainMessage(4, a2), l2);
        }

        public boolean a(ResultCallback<R> resultCallback, R r2) {
            return this.sendMessage(this.obtainMessage(1, new Pair(resultCallback, r2)));
        }

        protected void b(ResultCallback<R> resultCallback, R r2) {
            try {
                resultCallback.onResult(r2);
                return;
            }
            catch (RuntimeException runtimeException) {
                h.a(r2);
                throw runtimeException;
            }
        }

        public void handleMessage(Message message) {
            switch (message.what) {
                default: {
                    bp.c("MobvoiApi", "discard a message, message = " + message);
                    return;
                }
                case 1: {
                    message = (Pair)message.obj;
                    this.b((ResultCallback)message.first, (Result)message.second);
                    return;
                }
                case 2: {
                    h.a((Result)message.obj);
                    return;
                }
                case 4: 
            }
            a.a((a)message.obj);
        }
    }
}

