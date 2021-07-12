/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.util.Log
 */
package android.support.v4.app;

import android.os.Bundle;
import android.support.v4.app.FragmentHostCallback;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.util.DebugUtils;
import android.support.v4.util.SparseArrayCompat;
import android.util.Log;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.reflect.Modifier;

class LoaderManagerImpl
extends LoaderManager {
    static boolean DEBUG = false;
    static final String TAG = "LoaderManager";
    boolean mCreatingLoader;
    FragmentHostCallback mHost;
    final SparseArrayCompat<LoaderInfo> mInactiveLoaders;
    final SparseArrayCompat<LoaderInfo> mLoaders = new SparseArrayCompat();
    boolean mRetaining;
    boolean mRetainingStarted;
    boolean mStarted;
    final String mWho;

    static {
        DEBUG = false;
    }

    LoaderManagerImpl(String string2, FragmentHostCallback fragmentHostCallback, boolean bl2) {
        this.mInactiveLoaders = new SparseArrayCompat();
        this.mWho = string2;
        this.mHost = fragmentHostCallback;
        this.mStarted = bl2;
    }

    private LoaderInfo createAndInstallLoader(int n2, Bundle object, LoaderManager.LoaderCallbacks<Object> loaderCallbacks) {
        try {
            this.mCreatingLoader = true;
            object = this.createLoader(n2, (Bundle)object, loaderCallbacks);
            this.installLoader((LoaderInfo)object);
            return object;
        }
        finally {
            this.mCreatingLoader = false;
        }
    }

    private LoaderInfo createLoader(int n2, Bundle bundle, LoaderManager.LoaderCallbacks<Object> loaderCallbacks) {
        LoaderInfo loaderInfo = new LoaderInfo(n2, bundle, loaderCallbacks);
        loaderInfo.mLoader = loaderCallbacks.onCreateLoader(n2, bundle);
        return loaderInfo;
    }

    @Override
    public void destroyLoader(int n2) {
        LoaderInfo loaderInfo;
        int n3;
        if (this.mCreatingLoader) {
            throw new IllegalStateException("Called while creating a loader");
        }
        if (DEBUG) {
            Log.v((String)TAG, (String)("destroyLoader in " + this + " of " + n2));
        }
        if ((n3 = this.mLoaders.indexOfKey(n2)) >= 0) {
            loaderInfo = this.mLoaders.valueAt(n3);
            this.mLoaders.removeAt(n3);
            loaderInfo.destroy();
        }
        if ((n2 = this.mInactiveLoaders.indexOfKey(n2)) >= 0) {
            loaderInfo = this.mInactiveLoaders.valueAt(n2);
            this.mInactiveLoaders.removeAt(n2);
            loaderInfo.destroy();
        }
        if (this.mHost != null && !this.hasRunningLoaders()) {
            this.mHost.mFragmentManager.startPendingDeferredFragments();
        }
    }

    void doDestroy() {
        int n2;
        if (!this.mRetaining) {
            if (DEBUG) {
                Log.v((String)TAG, (String)("Destroying Active in " + this));
            }
            for (n2 = this.mLoaders.size() - 1; n2 >= 0; --n2) {
                this.mLoaders.valueAt(n2).destroy();
            }
            this.mLoaders.clear();
        }
        if (DEBUG) {
            Log.v((String)TAG, (String)("Destroying Inactive in " + this));
        }
        for (n2 = this.mInactiveLoaders.size() - 1; n2 >= 0; --n2) {
            this.mInactiveLoaders.valueAt(n2).destroy();
        }
        this.mInactiveLoaders.clear();
    }

    void doReportNextStart() {
        for (int i2 = this.mLoaders.size() - 1; i2 >= 0; --i2) {
            this.mLoaders.valueAt((int)i2).mReportNextStart = true;
        }
    }

    void doReportStart() {
        for (int i2 = this.mLoaders.size() - 1; i2 >= 0; --i2) {
            this.mLoaders.valueAt(i2).reportStart();
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    void doRetain() {
        if (DEBUG) {
            Log.v((String)TAG, (String)("Retaining in " + this));
        }
        if (!this.mStarted) {
            RuntimeException runtimeException = new RuntimeException("here");
            runtimeException.fillInStackTrace();
            Log.w((String)TAG, (String)("Called doRetain when not started: " + this), (Throwable)runtimeException);
            return;
        } else {
            this.mRetaining = true;
            this.mStarted = false;
            for (int i2 = this.mLoaders.size() - 1; i2 >= 0; --i2) {
                this.mLoaders.valueAt(i2).retain();
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    void doStart() {
        if (DEBUG) {
            Log.v((String)TAG, (String)("Starting in " + this));
        }
        if (this.mStarted) {
            RuntimeException runtimeException = new RuntimeException("here");
            runtimeException.fillInStackTrace();
            Log.w((String)TAG, (String)("Called doStart when already started: " + this), (Throwable)runtimeException);
            return;
        } else {
            this.mStarted = true;
            for (int i2 = this.mLoaders.size() - 1; i2 >= 0; --i2) {
                this.mLoaders.valueAt(i2).start();
            }
        }
    }

    void doStop() {
        if (DEBUG) {
            Log.v((String)TAG, (String)("Stopping in " + this));
        }
        if (!this.mStarted) {
            RuntimeException runtimeException = new RuntimeException("here");
            runtimeException.fillInStackTrace();
            Log.w((String)TAG, (String)("Called doStop when not started: " + this), (Throwable)runtimeException);
            return;
        }
        for (int i2 = this.mLoaders.size() - 1; i2 >= 0; --i2) {
            this.mLoaders.valueAt(i2).stop();
        }
        this.mStarted = false;
    }

    @Override
    public void dump(String string2, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] stringArray) {
        LoaderInfo loaderInfo;
        int n2;
        String string3;
        if (this.mLoaders.size() > 0) {
            printWriter.print(string2);
            printWriter.println("Active Loaders:");
            string3 = string2 + "    ";
            for (n2 = 0; n2 < this.mLoaders.size(); ++n2) {
                loaderInfo = this.mLoaders.valueAt(n2);
                printWriter.print(string2);
                printWriter.print("  #");
                printWriter.print(this.mLoaders.keyAt(n2));
                printWriter.print(": ");
                printWriter.println(loaderInfo.toString());
                loaderInfo.dump(string3, fileDescriptor, printWriter, stringArray);
            }
        }
        if (this.mInactiveLoaders.size() > 0) {
            printWriter.print(string2);
            printWriter.println("Inactive Loaders:");
            string3 = string2 + "    ";
            for (n2 = 0; n2 < this.mInactiveLoaders.size(); ++n2) {
                loaderInfo = this.mInactiveLoaders.valueAt(n2);
                printWriter.print(string2);
                printWriter.print("  #");
                printWriter.print(this.mInactiveLoaders.keyAt(n2));
                printWriter.print(": ");
                printWriter.println(loaderInfo.toString());
                loaderInfo.dump(string3, fileDescriptor, printWriter, stringArray);
            }
        }
    }

    void finishRetain() {
        if (this.mRetaining) {
            if (DEBUG) {
                Log.v((String)TAG, (String)("Finished Retaining in " + this));
            }
            this.mRetaining = false;
            for (int i2 = this.mLoaders.size() - 1; i2 >= 0; --i2) {
                this.mLoaders.valueAt(i2).finishRetain();
            }
        }
    }

    @Override
    public <D> Loader<D> getLoader(int n2) {
        if (this.mCreatingLoader) {
            throw new IllegalStateException("Called while creating a loader");
        }
        LoaderInfo loaderInfo = this.mLoaders.get(n2);
        if (loaderInfo != null) {
            if (loaderInfo.mPendingLoader != null) {
                return loaderInfo.mPendingLoader.mLoader;
            }
            return loaderInfo.mLoader;
        }
        return null;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public boolean hasRunningLoaders() {
        boolean bl2 = false;
        int n2 = this.mLoaders.size();
        int n3 = 0;
        while (n3 < n2) {
            LoaderInfo loaderInfo = this.mLoaders.valueAt(n3);
            boolean bl3 = loaderInfo.mStarted && !loaderInfo.mDeliveredData;
            bl2 |= bl3;
            ++n3;
        }
        return bl2;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public <D> Loader<D> initLoader(int n2, Bundle object, LoaderManager.LoaderCallbacks<D> object2) {
        if (this.mCreatingLoader) {
            throw new IllegalStateException("Called while creating a loader");
        }
        LoaderInfo loaderInfo = this.mLoaders.get(n2);
        if (DEBUG) {
            Log.v((String)TAG, (String)("initLoader in " + this + ": args=" + object));
        }
        if (loaderInfo == null) {
            object = object2 = this.createAndInstallLoader(n2, (Bundle)object, (LoaderManager.LoaderCallbacks<Object>)object2);
            if (DEBUG) {
                Log.v((String)TAG, (String)("  Created new loader " + object2));
                object = object2;
            }
        } else {
            if (DEBUG) {
                Log.v((String)TAG, (String)("  Re-using existing loader " + loaderInfo));
            }
            loaderInfo.mCallbacks = object2;
            object = loaderInfo;
        }
        if (((LoaderInfo)object).mHaveData && this.mStarted) {
            ((LoaderInfo)object).callOnLoadFinished(((LoaderInfo)object).mLoader, ((LoaderInfo)object).mData);
        }
        return ((LoaderInfo)object).mLoader;
    }

    void installLoader(LoaderInfo loaderInfo) {
        this.mLoaders.put(loaderInfo.mId, loaderInfo);
        if (this.mStarted) {
            loaderInfo.start();
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public <D> Loader<D> restartLoader(int n2, Bundle bundle, LoaderManager.LoaderCallbacks<D> loaderCallbacks) {
        LoaderInfo loaderInfo;
        block16: {
            block17: {
                block15: {
                    if (this.mCreatingLoader) {
                        throw new IllegalStateException("Called while creating a loader");
                    }
                    loaderInfo = this.mLoaders.get(n2);
                    if (DEBUG) {
                        Log.v((String)TAG, (String)("restartLoader in " + this + ": args=" + bundle));
                    }
                    if (loaderInfo == null) break block15;
                    LoaderInfo loaderInfo2 = this.mInactiveLoaders.get(n2);
                    if (loaderInfo2 == null) break block16;
                    if (!loaderInfo.mHaveData) break block17;
                    if (DEBUG) {
                        Log.v((String)TAG, (String)("  Removing last inactive loader: " + loaderInfo));
                    }
                    loaderInfo2.mDeliveredData = false;
                    loaderInfo2.destroy();
                    loaderInfo.mLoader.abandon();
                    this.mInactiveLoaders.put(n2, loaderInfo);
                }
                return this.createAndInstallLoader((int)n2, (Bundle)bundle, loaderCallbacks).mLoader;
            }
            if (!loaderInfo.cancel()) {
                if (DEBUG) {
                    Log.v((String)TAG, (String)"  Current loader is stopped; replacing");
                }
                this.mLoaders.put(n2, null);
                loaderInfo.destroy();
                return this.createAndInstallLoader((int)n2, (Bundle)bundle, loaderCallbacks).mLoader;
            }
            if (DEBUG) {
                Log.v((String)TAG, (String)"  Current loader is running; configuring pending loader");
            }
            if (loaderInfo.mPendingLoader != null) {
                if (DEBUG) {
                    Log.v((String)TAG, (String)("  Removing pending loader: " + loaderInfo.mPendingLoader));
                }
                loaderInfo.mPendingLoader.destroy();
                loaderInfo.mPendingLoader = null;
            }
            if (DEBUG) {
                Log.v((String)TAG, (String)"  Enqueuing as new pending loader");
            }
            loaderInfo.mPendingLoader = this.createLoader(n2, bundle, loaderCallbacks);
            return loaderInfo.mPendingLoader.mLoader;
        }
        if (DEBUG) {
            Log.v((String)TAG, (String)("  Making last loader inactive: " + loaderInfo));
        }
        loaderInfo.mLoader.abandon();
        this.mInactiveLoaders.put(n2, loaderInfo);
        return this.createAndInstallLoader((int)n2, (Bundle)bundle, loaderCallbacks).mLoader;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(128);
        stringBuilder.append("LoaderManager{");
        stringBuilder.append(Integer.toHexString(System.identityHashCode(this)));
        stringBuilder.append(" in ");
        DebugUtils.buildShortClassTag(this.mHost, stringBuilder);
        stringBuilder.append("}}");
        return stringBuilder.toString();
    }

    void updateHostController(FragmentHostCallback fragmentHostCallback) {
        this.mHost = fragmentHostCallback;
    }

    final class LoaderInfo
    implements Loader.OnLoadCompleteListener<Object>,
    Loader.OnLoadCanceledListener<Object> {
        final Bundle mArgs;
        LoaderManager.LoaderCallbacks<Object> mCallbacks;
        Object mData;
        boolean mDeliveredData;
        boolean mDestroyed;
        boolean mHaveData;
        final int mId;
        boolean mListenerRegistered;
        Loader<Object> mLoader;
        LoaderInfo mPendingLoader;
        boolean mReportNextStart;
        boolean mRetaining;
        boolean mRetainingStarted;
        boolean mStarted;

        public LoaderInfo(int n2, Bundle bundle, LoaderManager.LoaderCallbacks<Object> loaderCallbacks) {
            this.mId = n2;
            this.mArgs = bundle;
            this.mCallbacks = loaderCallbacks;
        }

        void callOnLoadFinished(Loader<Object> loader, Object object) {
            String string2;
            if (this.mCallbacks != null) {
                string2 = null;
                if (LoaderManagerImpl.this.mHost != null) {
                    string2 = LoaderManagerImpl.this.mHost.mFragmentManager.mNoTransactionsBecause;
                    LoaderManagerImpl.this.mHost.mFragmentManager.mNoTransactionsBecause = "onLoadFinished";
                }
                if (DEBUG) {
                    Log.v((String)LoaderManagerImpl.TAG, (String)("  onLoadFinished in " + loader + ": " + loader.dataToString(object)));
                }
                this.mCallbacks.onLoadFinished(loader, object);
                this.mDeliveredData = true;
            }
            return;
            finally {
                if (LoaderManagerImpl.this.mHost != null) {
                    LoaderManagerImpl.this.mHost.mFragmentManager.mNoTransactionsBecause = string2;
                }
            }
        }

        boolean cancel() {
            if (DEBUG) {
                Log.v((String)LoaderManagerImpl.TAG, (String)("  Canceling: " + this));
            }
            if (this.mStarted && this.mLoader != null && this.mListenerRegistered) {
                boolean bl2 = this.mLoader.cancelLoad();
                if (!bl2) {
                    this.onLoadCanceled(this.mLoader);
                }
                return bl2;
            }
            return false;
        }

        void destroy() {
            String string2;
            if (DEBUG) {
                Log.v((String)LoaderManagerImpl.TAG, (String)("  Destroying: " + this));
            }
            this.mDestroyed = true;
            boolean bl2 = this.mDeliveredData;
            this.mDeliveredData = false;
            if (this.mCallbacks != null && this.mLoader != null && this.mHaveData && bl2) {
                if (DEBUG) {
                    Log.v((String)LoaderManagerImpl.TAG, (String)("  Resetting: " + this));
                }
                string2 = null;
                if (LoaderManagerImpl.this.mHost != null) {
                    string2 = LoaderManagerImpl.this.mHost.mFragmentManager.mNoTransactionsBecause;
                    LoaderManagerImpl.this.mHost.mFragmentManager.mNoTransactionsBecause = "onLoaderReset";
                }
                this.mCallbacks.onLoaderReset(this.mLoader);
            }
            this.mCallbacks = null;
            this.mData = null;
            this.mHaveData = false;
            if (this.mLoader != null) {
                if (this.mListenerRegistered) {
                    this.mListenerRegistered = false;
                    this.mLoader.unregisterListener(this);
                    this.mLoader.unregisterOnLoadCanceledListener(this);
                }
                this.mLoader.reset();
            }
            if (this.mPendingLoader != null) {
                this.mPendingLoader.destroy();
            }
            return;
            finally {
                if (LoaderManagerImpl.this.mHost != null) {
                    LoaderManagerImpl.this.mHost.mFragmentManager.mNoTransactionsBecause = string2;
                }
            }
        }

        public void dump(String string2, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] stringArray) {
            printWriter.print(string2);
            printWriter.print("mId=");
            printWriter.print(this.mId);
            printWriter.print(" mArgs=");
            printWriter.println(this.mArgs);
            printWriter.print(string2);
            printWriter.print("mCallbacks=");
            printWriter.println(this.mCallbacks);
            printWriter.print(string2);
            printWriter.print("mLoader=");
            printWriter.println(this.mLoader);
            if (this.mLoader != null) {
                this.mLoader.dump(string2 + "  ", fileDescriptor, printWriter, stringArray);
            }
            if (this.mHaveData || this.mDeliveredData) {
                printWriter.print(string2);
                printWriter.print("mHaveData=");
                printWriter.print(this.mHaveData);
                printWriter.print("  mDeliveredData=");
                printWriter.println(this.mDeliveredData);
                printWriter.print(string2);
                printWriter.print("mData=");
                printWriter.println(this.mData);
            }
            printWriter.print(string2);
            printWriter.print("mStarted=");
            printWriter.print(this.mStarted);
            printWriter.print(" mReportNextStart=");
            printWriter.print(this.mReportNextStart);
            printWriter.print(" mDestroyed=");
            printWriter.println(this.mDestroyed);
            printWriter.print(string2);
            printWriter.print("mRetaining=");
            printWriter.print(this.mRetaining);
            printWriter.print(" mRetainingStarted=");
            printWriter.print(this.mRetainingStarted);
            printWriter.print(" mListenerRegistered=");
            printWriter.println(this.mListenerRegistered);
            if (this.mPendingLoader != null) {
                printWriter.print(string2);
                printWriter.println("Pending Loader ");
                printWriter.print(this.mPendingLoader);
                printWriter.println(":");
                this.mPendingLoader.dump(string2 + "  ", fileDescriptor, printWriter, stringArray);
            }
        }

        void finishRetain() {
            if (this.mRetaining) {
                if (DEBUG) {
                    Log.v((String)LoaderManagerImpl.TAG, (String)("  Finished Retaining: " + this));
                }
                this.mRetaining = false;
                if (this.mStarted != this.mRetainingStarted && !this.mStarted) {
                    this.stop();
                }
            }
            if (this.mStarted && this.mHaveData && !this.mReportNextStart) {
                this.callOnLoadFinished(this.mLoader, this.mData);
            }
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public void onLoadCanceled(Loader<Object> object) {
            if (DEBUG) {
                Log.v((String)LoaderManagerImpl.TAG, (String)("onLoadCanceled: " + this));
            }
            if (this.mDestroyed) {
                if (!DEBUG) return;
                Log.v((String)LoaderManagerImpl.TAG, (String)"  Ignoring load canceled -- destroyed");
                return;
            }
            if (LoaderManagerImpl.this.mLoaders.get(this.mId) != this) {
                if (!DEBUG) return;
                Log.v((String)LoaderManagerImpl.TAG, (String)"  Ignoring load canceled -- not active");
                return;
            } else {
                object = this.mPendingLoader;
                if (object == null) return;
                if (DEBUG) {
                    Log.v((String)LoaderManagerImpl.TAG, (String)("  Switching to pending loader: " + object));
                }
                this.mPendingLoader = null;
                LoaderManagerImpl.this.mLoaders.put(this.mId, null);
                this.destroy();
                LoaderManagerImpl.this.installLoader((LoaderInfo)object);
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public void onLoadComplete(Loader<Object> object, Object object2) {
            if (DEBUG) {
                Log.v((String)LoaderManagerImpl.TAG, (String)("onLoadComplete: " + this));
            }
            if (this.mDestroyed) {
                if (!DEBUG) return;
                Log.v((String)LoaderManagerImpl.TAG, (String)"  Ignoring load complete -- destroyed");
                return;
            }
            if (LoaderManagerImpl.this.mLoaders.get(this.mId) != this) {
                if (!DEBUG) return;
                Log.v((String)LoaderManagerImpl.TAG, (String)"  Ignoring load complete -- not active");
                return;
            } else {
                LoaderInfo loaderInfo = this.mPendingLoader;
                if (loaderInfo != null) {
                    if (DEBUG) {
                        Log.v((String)LoaderManagerImpl.TAG, (String)("  Switching to pending loader: " + loaderInfo));
                    }
                    this.mPendingLoader = null;
                    LoaderManagerImpl.this.mLoaders.put(this.mId, null);
                    this.destroy();
                    LoaderManagerImpl.this.installLoader(loaderInfo);
                    return;
                }
                if (this.mData != object2 || !this.mHaveData) {
                    this.mData = object2;
                    this.mHaveData = true;
                    if (this.mStarted) {
                        this.callOnLoadFinished((Loader<Object>)object, object2);
                    }
                }
                if ((object = LoaderManagerImpl.this.mInactiveLoaders.get(this.mId)) != null && object != this) {
                    ((LoaderInfo)object).mDeliveredData = false;
                    ((LoaderInfo)object).destroy();
                    LoaderManagerImpl.this.mInactiveLoaders.remove(this.mId);
                }
                if (LoaderManagerImpl.this.mHost == null || LoaderManagerImpl.this.hasRunningLoaders()) return;
                LoaderManagerImpl.this.mHost.mFragmentManager.startPendingDeferredFragments();
                return;
            }
        }

        void reportStart() {
            if (this.mStarted && this.mReportNextStart) {
                this.mReportNextStart = false;
                if (this.mHaveData && !this.mRetaining) {
                    this.callOnLoadFinished(this.mLoader, this.mData);
                }
            }
        }

        void retain() {
            if (DEBUG) {
                Log.v((String)LoaderManagerImpl.TAG, (String)("  Retaining: " + this));
            }
            this.mRetaining = true;
            this.mRetainingStarted = this.mStarted;
            this.mStarted = false;
            this.mCallbacks = null;
        }

        /*
         * Enabled aggressive block sorting
         */
        void start() {
            if (this.mRetaining && this.mRetainingStarted) {
                this.mStarted = true;
                return;
            }
            if (this.mStarted) return;
            this.mStarted = true;
            if (DEBUG) {
                Log.v((String)LoaderManagerImpl.TAG, (String)("  Starting: " + this));
            }
            if (this.mLoader == null && this.mCallbacks != null) {
                this.mLoader = this.mCallbacks.onCreateLoader(this.mId, this.mArgs);
            }
            if (this.mLoader == null) return;
            if (this.mLoader.getClass().isMemberClass() && !Modifier.isStatic(this.mLoader.getClass().getModifiers())) {
                throw new IllegalArgumentException("Object returned from onCreateLoader must not be a non-static inner member class: " + this.mLoader);
            }
            if (!this.mListenerRegistered) {
                this.mLoader.registerListener(this.mId, this);
                this.mLoader.registerOnLoadCanceledListener(this);
                this.mListenerRegistered = true;
            }
            this.mLoader.startLoading();
        }

        void stop() {
            if (DEBUG) {
                Log.v((String)LoaderManagerImpl.TAG, (String)("  Stopping: " + this));
            }
            this.mStarted = false;
            if (!this.mRetaining && this.mLoader != null && this.mListenerRegistered) {
                this.mListenerRegistered = false;
                this.mLoader.unregisterListener(this);
                this.mLoader.unregisterOnLoadCanceledListener(this);
                this.mLoader.stopLoading();
            }
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder(64);
            stringBuilder.append("LoaderInfo{");
            stringBuilder.append(Integer.toHexString(System.identityHashCode(this)));
            stringBuilder.append(" #");
            stringBuilder.append(this.mId);
            stringBuilder.append(" : ");
            DebugUtils.buildShortClassTag(this.mLoader, stringBuilder);
            stringBuilder.append("}}");
            return stringBuilder.toString();
        }
    }
}

