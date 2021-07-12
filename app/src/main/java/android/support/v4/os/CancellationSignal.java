/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.os.Build$VERSION
 */
package android.support.v4.os;

import android.os.Build;
import android.support.v4.os.CancellationSignalCompatJellybean;
import android.support.v4.os.OperationCanceledException;

public final class CancellationSignal {
    private boolean mCancelInProgress;
    private Object mCancellationSignalObj;
    private boolean mIsCanceled;
    private OnCancelListener mOnCancelListener;

    private void waitForCancelFinishedLocked() {
        while (this.mCancelInProgress) {
            try {
                this.wait();
            }
            catch (InterruptedException interruptedException) {}
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void cancel() {
        synchronized (this) {
            if (this.mIsCanceled) {
                return;
            }
            this.mIsCanceled = true;
            this.mCancelInProgress = true;
            var1_1 = this.mOnCancelListener;
            var2_3 = this.mCancellationSignalObj;
            if (var1_1 == null) ** GOTO lbl11
        }
        try {
            var1_1.onCancel();
lbl11:
            // 2 sources

            if (var2_3 == null) return;
            CancellationSignalCompatJellybean.cancel(var2_3);
            return;
        }
        finally {
            synchronized (this) {
                this.mCancelInProgress = false;
                this.notifyAll();
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Object getCancellationSignalObject() {
        if (Build.VERSION.SDK_INT < 16) {
            return null;
        }
        synchronized (this) {
            if (this.mCancellationSignalObj != null) return this.mCancellationSignalObj;
            this.mCancellationSignalObj = CancellationSignalCompatJellybean.create();
            if (!this.mIsCanceled) return this.mCancellationSignalObj;
            CancellationSignalCompatJellybean.cancel(this.mCancellationSignalObj);
            return this.mCancellationSignalObj;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean isCanceled() {
        synchronized (this) {
            return this.mIsCanceled;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setOnCancelListener(OnCancelListener onCancelListener) {
        synchronized (this) {
            this.waitForCancelFinishedLocked();
            if (this.mOnCancelListener == onCancelListener) {
                return;
            }
            this.mOnCancelListener = onCancelListener;
            if (this.mIsCanceled && onCancelListener != null) {
                // MONITOREXIT @DISABLED, blocks:[2, 3] lbl8 : MonitorExitStatement: MONITOREXIT : this
                onCancelListener.onCancel();
                return;
            }
            return;
        }
    }

    public void throwIfCanceled() {
        if (this.isCanceled()) {
            throw new OperationCanceledException();
        }
    }

    public static interface OnCancelListener {
        public void onCancel();
    }
}

