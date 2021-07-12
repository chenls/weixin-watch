/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Handler
 *  android.os.SystemClock
 */
package android.support.v4.content;

import android.content.Context;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.content.Loader;
import android.support.v4.content.ModernAsyncTask;
import android.support.v4.os.OperationCanceledException;
import android.support.v4.util.TimeUtils;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;

public abstract class AsyncTaskLoader<D>
extends Loader<D> {
    static final boolean DEBUG = false;
    static final String TAG = "AsyncTaskLoader";
    volatile LoadTask mCancellingTask;
    private final Executor mExecutor;
    Handler mHandler;
    long mLastLoadCompleteTime = -10000L;
    volatile LoadTask mTask;
    long mUpdateThrottle;

    public AsyncTaskLoader(Context context) {
        this(context, ModernAsyncTask.THREAD_POOL_EXECUTOR);
    }

    private AsyncTaskLoader(Context context, Executor executor) {
        super(context);
        this.mExecutor = executor;
    }

    public void cancelLoadInBackground() {
    }

    void dispatchOnCancelled(LoadTask loadTask, D d2) {
        this.onCanceled(d2);
        if (this.mCancellingTask == loadTask) {
            this.rollbackContentChanged();
            this.mLastLoadCompleteTime = SystemClock.uptimeMillis();
            this.mCancellingTask = null;
            this.deliverCancellation();
            this.executePendingTask();
        }
    }

    void dispatchOnLoadComplete(LoadTask loadTask, D d2) {
        if (this.mTask != loadTask) {
            this.dispatchOnCancelled(loadTask, d2);
            return;
        }
        if (this.isAbandoned()) {
            this.onCanceled(d2);
            return;
        }
        this.commitContentChanged();
        this.mLastLoadCompleteTime = SystemClock.uptimeMillis();
        this.mTask = null;
        this.deliverResult(d2);
    }

    @Override
    public void dump(String string2, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] stringArray) {
        super.dump(string2, fileDescriptor, printWriter, stringArray);
        if (this.mTask != null) {
            printWriter.print(string2);
            printWriter.print("mTask=");
            printWriter.print(this.mTask);
            printWriter.print(" waiting=");
            printWriter.println(this.mTask.waiting);
        }
        if (this.mCancellingTask != null) {
            printWriter.print(string2);
            printWriter.print("mCancellingTask=");
            printWriter.print(this.mCancellingTask);
            printWriter.print(" waiting=");
            printWriter.println(this.mCancellingTask.waiting);
        }
        if (this.mUpdateThrottle != 0L) {
            printWriter.print(string2);
            printWriter.print("mUpdateThrottle=");
            TimeUtils.formatDuration(this.mUpdateThrottle, printWriter);
            printWriter.print(" mLastLoadCompleteTime=");
            TimeUtils.formatDuration(this.mLastLoadCompleteTime, SystemClock.uptimeMillis(), printWriter);
            printWriter.println();
        }
    }

    void executePendingTask() {
        block5: {
            block4: {
                if (this.mCancellingTask != null || this.mTask == null) break block4;
                if (this.mTask.waiting) {
                    this.mTask.waiting = false;
                    this.mHandler.removeCallbacks((Runnable)this.mTask);
                }
                if (this.mUpdateThrottle <= 0L || SystemClock.uptimeMillis() >= this.mLastLoadCompleteTime + this.mUpdateThrottle) break block5;
                this.mTask.waiting = true;
                this.mHandler.postAtTime((Runnable)this.mTask, this.mLastLoadCompleteTime + this.mUpdateThrottle);
            }
            return;
        }
        this.mTask.executeOnExecutor(this.mExecutor, null);
    }

    public boolean isLoadInBackgroundCanceled() {
        return this.mCancellingTask != null;
    }

    public abstract D loadInBackground();

    @Override
    protected boolean onCancelLoad() {
        block7: {
            block6: {
                if (this.mTask == null) break block6;
                if (this.mCancellingTask == null) break block7;
                if (this.mTask.waiting) {
                    this.mTask.waiting = false;
                    this.mHandler.removeCallbacks((Runnable)this.mTask);
                }
                this.mTask = null;
            }
            return false;
        }
        if (this.mTask.waiting) {
            this.mTask.waiting = false;
            this.mHandler.removeCallbacks((Runnable)this.mTask);
            this.mTask = null;
            return false;
        }
        boolean bl2 = this.mTask.cancel(false);
        if (bl2) {
            this.mCancellingTask = this.mTask;
            this.cancelLoadInBackground();
        }
        this.mTask = null;
        return bl2;
    }

    public void onCanceled(D d2) {
    }

    @Override
    protected void onForceLoad() {
        super.onForceLoad();
        this.cancelLoad();
        this.mTask = new LoadTask();
        this.executePendingTask();
    }

    protected D onLoadInBackground() {
        return this.loadInBackground();
    }

    public void setUpdateThrottle(long l2) {
        this.mUpdateThrottle = l2;
        if (l2 != 0L) {
            this.mHandler = new Handler();
        }
    }

    public void waitForLoader() {
        LoadTask loadTask = this.mTask;
        if (loadTask != null) {
            loadTask.waitForLoader();
        }
    }

    final class LoadTask
    extends ModernAsyncTask<Void, Void, D>
    implements Runnable {
        private final CountDownLatch mDone = new CountDownLatch(1);
        boolean waiting;

        LoadTask() {
        }

        protected D doInBackground(Void ... object) {
            try {
                object = AsyncTaskLoader.this.onLoadInBackground();
            }
            catch (OperationCanceledException operationCanceledException) {
                if (!this.isCancelled()) {
                    throw operationCanceledException;
                }
                return null;
            }
            return object;
        }

        @Override
        protected void onCancelled(D d2) {
            try {
                AsyncTaskLoader.this.dispatchOnCancelled(this, d2);
                return;
            }
            finally {
                this.mDone.countDown();
            }
        }

        @Override
        protected void onPostExecute(D d2) {
            try {
                AsyncTaskLoader.this.dispatchOnLoadComplete(this, d2);
                return;
            }
            finally {
                this.mDone.countDown();
            }
        }

        @Override
        public void run() {
            this.waiting = false;
            AsyncTaskLoader.this.executePendingTask();
        }

        public void waitForLoader() {
            try {
                this.mDone.await();
                return;
            }
            catch (InterruptedException interruptedException) {
                return;
            }
        }
    }
}

