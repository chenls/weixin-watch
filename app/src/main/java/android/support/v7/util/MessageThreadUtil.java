/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.os.Handler
 *  android.os.Looper
 *  android.util.Log
 */
package android.support.v7.util;

import android.os.Handler;
import android.os.Looper;
import android.support.v4.content.ParallelExecutorCompat;
import android.support.v7.util.ThreadUtil;
import android.support.v7.util.TileList;
import android.util.Log;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;

class MessageThreadUtil<T>
implements ThreadUtil<T> {
    MessageThreadUtil() {
    }

    @Override
    public ThreadUtil.BackgroundCallback<T> getBackgroundProxy(final ThreadUtil.BackgroundCallback<T> backgroundCallback) {
        return new ThreadUtil.BackgroundCallback<T>(){
            private static final int LOAD_TILE = 3;
            private static final int RECYCLE_TILE = 4;
            private static final int REFRESH = 1;
            private static final int UPDATE_RANGE = 2;
            private Runnable mBackgroundRunnable;
            AtomicBoolean mBackgroundRunning;
            private final Executor mExecutor;
            private final MessageQueue mQueue = new MessageQueue();
            {
                this.mExecutor = ParallelExecutorCompat.getParallelExecutor();
                this.mBackgroundRunning = new AtomicBoolean(false);
                this.mBackgroundRunnable = new Runnable(){

                    @Override
                    public void run() {
                        block6: while (true) {
                            SyncQueueItem syncQueueItem;
                            if ((syncQueueItem = mQueue.next()) == null) {
                                mBackgroundRunning.set(false);
                                return;
                            }
                            switch (syncQueueItem.what) {
                                default: {
                                    Log.e((String)"ThreadUtil", (String)("Unsupported message, what=" + syncQueueItem.what));
                                    continue block6;
                                }
                                case 1: {
                                    mQueue.removeMessages(1);
                                    backgroundCallback.refresh(syncQueueItem.arg1);
                                    continue block6;
                                }
                                case 2: {
                                    mQueue.removeMessages(2);
                                    mQueue.removeMessages(3);
                                    backgroundCallback.updateRange(syncQueueItem.arg1, syncQueueItem.arg2, syncQueueItem.arg3, syncQueueItem.arg4, syncQueueItem.arg5);
                                    continue block6;
                                }
                                case 3: {
                                    backgroundCallback.loadTile(syncQueueItem.arg1, syncQueueItem.arg2);
                                    continue block6;
                                }
                                case 4: 
                            }
                            backgroundCallback.recycleTile((TileList.Tile)syncQueueItem.data);
                        }
                    }
                };
            }

            private void maybeExecuteBackgroundRunnable() {
                if (this.mBackgroundRunning.compareAndSet(false, true)) {
                    this.mExecutor.execute(this.mBackgroundRunnable);
                }
            }

            private void sendMessage(SyncQueueItem syncQueueItem) {
                this.mQueue.sendMessage(syncQueueItem);
                this.maybeExecuteBackgroundRunnable();
            }

            private void sendMessageAtFrontOfQueue(SyncQueueItem syncQueueItem) {
                this.mQueue.sendMessageAtFrontOfQueue(syncQueueItem);
                this.maybeExecuteBackgroundRunnable();
            }

            @Override
            public void loadTile(int n2, int n3) {
                this.sendMessage(SyncQueueItem.obtainMessage(3, n2, n3));
            }

            @Override
            public void recycleTile(TileList.Tile<T> tile) {
                this.sendMessage(SyncQueueItem.obtainMessage(4, 0, tile));
            }

            @Override
            public void refresh(int n2) {
                this.sendMessageAtFrontOfQueue(SyncQueueItem.obtainMessage(1, n2, null));
            }

            @Override
            public void updateRange(int n2, int n3, int n4, int n5, int n6) {
                this.sendMessageAtFrontOfQueue(SyncQueueItem.obtainMessage(2, n2, n3, n4, n5, n6, null));
            }
        };
    }

    @Override
    public ThreadUtil.MainThreadCallback<T> getMainThreadProxy(final ThreadUtil.MainThreadCallback<T> mainThreadCallback) {
        return new ThreadUtil.MainThreadCallback<T>(){
            private static final int ADD_TILE = 2;
            private static final int REMOVE_TILE = 3;
            private static final int UPDATE_ITEM_COUNT = 1;
            private final Handler mMainThreadHandler;
            private Runnable mMainThreadRunnable;
            private final MessageQueue mQueue = new MessageQueue();
            {
                this.mMainThreadHandler = new Handler(Looper.getMainLooper());
                this.mMainThreadRunnable = new Runnable(){

                    /*
                     * Enabled aggressive block sorting
                     */
                    @Override
                    public void run() {
                        SyncQueueItem syncQueueItem = mQueue.next();
                        while (syncQueueItem != null) {
                            switch (syncQueueItem.what) {
                                default: {
                                    Log.e((String)"ThreadUtil", (String)("Unsupported message, what=" + syncQueueItem.what));
                                    break;
                                }
                                case 1: {
                                    mainThreadCallback.updateItemCount(syncQueueItem.arg1, syncQueueItem.arg2);
                                    break;
                                }
                                case 2: {
                                    mainThreadCallback.addTile(syncQueueItem.arg1, (TileList.Tile)syncQueueItem.data);
                                    break;
                                }
                                case 3: {
                                    mainThreadCallback.removeTile(syncQueueItem.arg1, syncQueueItem.arg2);
                                }
                            }
                            syncQueueItem = mQueue.next();
                        }
                        return;
                    }
                };
            }

            private void sendMessage(SyncQueueItem syncQueueItem) {
                this.mQueue.sendMessage(syncQueueItem);
                this.mMainThreadHandler.post(this.mMainThreadRunnable);
            }

            @Override
            public void addTile(int n2, TileList.Tile<T> tile) {
                this.sendMessage(SyncQueueItem.obtainMessage(2, n2, tile));
            }

            @Override
            public void removeTile(int n2, int n3) {
                this.sendMessage(SyncQueueItem.obtainMessage(3, n2, n3));
            }

            @Override
            public void updateItemCount(int n2, int n3) {
                this.sendMessage(SyncQueueItem.obtainMessage(1, n2, n3));
            }
        };
    }

    static class MessageQueue {
        private SyncQueueItem mRoot;

        MessageQueue() {
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        SyncQueueItem next() {
            synchronized (this) {
                SyncQueueItem syncQueueItem;
                block6: {
                    syncQueueItem = this.mRoot;
                    if (syncQueueItem != null) break block6;
                    return null;
                }
                syncQueueItem = this.mRoot;
                this.mRoot = this.mRoot.next;
                return syncQueueItem;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        void removeMessages(int n2) {
            synchronized (this) {
                SyncQueueItem syncQueueItem;
                while (this.mRoot != null && this.mRoot.what == n2) {
                    syncQueueItem = this.mRoot;
                    this.mRoot = this.mRoot.next;
                    syncQueueItem.recycle();
                }
                if (this.mRoot != null) {
                    SyncQueueItem syncQueueItem2 = this.mRoot;
                    syncQueueItem = syncQueueItem2.next;
                    while (syncQueueItem != null) {
                        SyncQueueItem syncQueueItem3 = syncQueueItem.next;
                        if (syncQueueItem.what == n2) {
                            SyncQueueItem.access$202(syncQueueItem2, syncQueueItem3);
                            syncQueueItem.recycle();
                        } else {
                            syncQueueItem2 = syncQueueItem;
                        }
                        syncQueueItem = syncQueueItem3;
                    }
                }
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        void sendMessage(SyncQueueItem syncQueueItem) {
            synchronized (this) {
                if (this.mRoot == null) {
                    this.mRoot = syncQueueItem;
                } else {
                    SyncQueueItem syncQueueItem2 = this.mRoot;
                    while (syncQueueItem2.next != null) {
                        syncQueueItem2 = syncQueueItem2.next;
                    }
                    SyncQueueItem.access$202(syncQueueItem2, syncQueueItem);
                }
                return;
            }
        }

        void sendMessageAtFrontOfQueue(SyncQueueItem syncQueueItem) {
            synchronized (this) {
                SyncQueueItem.access$202(syncQueueItem, this.mRoot);
                this.mRoot = syncQueueItem;
                return;
            }
        }
    }

    static class SyncQueueItem {
        private static SyncQueueItem sPool;
        private static final Object sPoolLock;
        public int arg1;
        public int arg2;
        public int arg3;
        public int arg4;
        public int arg5;
        public Object data;
        private SyncQueueItem next;
        public int what;

        static {
            sPoolLock = new Object();
        }

        SyncQueueItem() {
        }

        static /* synthetic */ SyncQueueItem access$202(SyncQueueItem syncQueueItem, SyncQueueItem syncQueueItem2) {
            syncQueueItem.next = syncQueueItem2;
            return syncQueueItem2;
        }

        static SyncQueueItem obtainMessage(int n2, int n3, int n4) {
            return SyncQueueItem.obtainMessage(n2, n3, n4, 0, 0, 0, null);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        static SyncQueueItem obtainMessage(int n2, int n3, int n4, int n5, int n6, int n7, Object object) {
            Object object2 = sPoolLock;
            synchronized (object2) {
                SyncQueueItem syncQueueItem;
                if (sPool == null) {
                    syncQueueItem = new SyncQueueItem();
                } else {
                    syncQueueItem = sPool;
                    sPool = SyncQueueItem.sPool.next;
                    syncQueueItem.next = null;
                }
                syncQueueItem.what = n2;
                syncQueueItem.arg1 = n3;
                syncQueueItem.arg2 = n4;
                syncQueueItem.arg3 = n5;
                syncQueueItem.arg4 = n6;
                syncQueueItem.arg5 = n7;
                syncQueueItem.data = object;
                return syncQueueItem;
            }
        }

        static SyncQueueItem obtainMessage(int n2, int n3, Object object) {
            return SyncQueueItem.obtainMessage(n2, n3, 0, 0, 0, 0, object);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        void recycle() {
            this.next = null;
            this.arg5 = 0;
            this.arg4 = 0;
            this.arg3 = 0;
            this.arg2 = 0;
            this.arg1 = 0;
            this.what = 0;
            this.data = null;
            Object object = sPoolLock;
            synchronized (object) {
                if (sPool != null) {
                    this.next = sPool;
                }
                sPool = this;
                return;
            }
        }
    }
}

