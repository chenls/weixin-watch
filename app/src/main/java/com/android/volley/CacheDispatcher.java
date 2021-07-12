/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.os.Process
 */
package com.android.volley;

import android.os.Process;
import com.android.volley.Cache;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ResponseDelivery;
import com.android.volley.VolleyLog;
import java.util.concurrent.BlockingQueue;

public class CacheDispatcher
extends Thread {
    private static final boolean DEBUG = VolleyLog.DEBUG;
    private final Cache mCache;
    private final BlockingQueue<Request<?>> mCacheQueue;
    private final ResponseDelivery mDelivery;
    private final BlockingQueue<Request<?>> mNetworkQueue;
    private volatile boolean mQuit = false;

    public CacheDispatcher(BlockingQueue<Request<?>> blockingQueue, BlockingQueue<Request<?>> blockingQueue2, Cache cache, ResponseDelivery responseDelivery) {
        this.mCacheQueue = blockingQueue;
        this.mNetworkQueue = blockingQueue2;
        this.mCache = cache;
        this.mDelivery = responseDelivery;
    }

    public void quit() {
        this.mQuit = true;
        this.interrupt();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void run() {
        if (DEBUG) {
            VolleyLog.v("start new dispatcher", new Object[0]);
        }
        Process.setThreadPriority((int)10);
        this.mCache.initialize();
        while (true) {
            Cache.Entry entry;
            Request<?> request;
            block12: {
                try {
                    request = this.mCacheQueue.take();
                }
                catch (InterruptedException interruptedException) {
                    if (!this.mQuit) continue;
                    return;
                }
                request.addMarker("cache-queue-take");
                if (request.isCanceled()) {
                    request.finish("cache-discard-canceled");
                }
                try {
                    entry = this.mCache.get(request.getCacheKey());
                    if (entry == null) {
                        request.addMarker("cache-miss");
                        this.mNetworkQueue.put(request);
                        continue;
                    }
                    break block12;
                }
                catch (Exception exception) {
                    VolleyLog.e(exception, "Unhandled exception %s", exception.toString());
                }
                continue;
            }
            if (entry.isExpired()) {
                request.addMarker("cache-hit-expired");
                request.setCacheEntry(entry);
                this.mNetworkQueue.put(request);
                continue;
            }
            request.addMarker("cache-hit");
            Response<?> response = request.parseNetworkResponse(new NetworkResponse(entry.data, entry.responseHeaders));
            request.addMarker("cache-hit-parsed");
            if (!entry.refreshNeeded()) {
                this.mDelivery.postResponse(request, response);
                continue;
            }
            request.addMarker("cache-hit-refresh-needed");
            request.setCacheEntry(entry);
            response.intermediate = true;
            this.mDelivery.postResponse(request, response, new Runnable(){

                @Override
                public void run() {
                    try {
                        CacheDispatcher.this.mNetworkQueue.put(request);
                        return;
                    }
                    catch (InterruptedException interruptedException) {
                        return;
                    }
                }
            });
        }
    }
}

