/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.os.AsyncTask
 */
package android.support.v4.os;

import android.os.AsyncTask;

class AsyncTaskCompatHoneycomb {
    AsyncTaskCompatHoneycomb() {
    }

    static <Params, Progress, Result> void executeParallel(AsyncTask<Params, Progress, Result> asyncTask, Params ... ParamsArray) {
        asyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, (Object[])ParamsArray);
    }
}

