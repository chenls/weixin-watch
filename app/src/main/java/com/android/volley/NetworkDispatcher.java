/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.net.TrafficStats
 *  android.os.Build$VERSION
 *  android.os.Process
 *  android.os.SystemClock
 */
package com.android.volley;

import android.annotation.TargetApi;
import android.net.TrafficStats;
import android.os.Build;
import android.os.Process;
import android.os.SystemClock;
import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ResponseDelivery;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import java.util.concurrent.BlockingQueue;

public class NetworkDispatcher
extends Thread {
    private final Cache mCache;
    private final ResponseDelivery mDelivery;
    private final Network mNetwork;
    private final BlockingQueue<Request<?>> mQueue;
    private volatile boolean mQuit = false;

    public NetworkDispatcher(BlockingQueue<Request<?>> blockingQueue, Network network, Cache cache, ResponseDelivery responseDelivery) {
        this.mQueue = blockingQueue;
        this.mNetwork = network;
        this.mCache = cache;
        this.mDelivery = responseDelivery;
    }

    @TargetApi(value=14)
    private void addTrafficStatsTag(Request<?> request) {
        if (Build.VERSION.SDK_INT >= 14) {
            TrafficStats.setThreadStatsTag((int)request.getTrafficStatsTag());
        }
    }

    private void parseAndDeliverNetworkError(Request<?> request, VolleyError volleyError) {
        volleyError = request.parseNetworkError(volleyError);
        this.mDelivery.postError(request, volleyError);
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
        Process.setThreadPriority((int)10);
        while (true) {
            Object object;
            Request<?> request;
            long l2;
            block11: {
                l2 = SystemClock.elapsedRealtime();
                try {
                    request = this.mQueue.take();
                }
                catch (InterruptedException interruptedException) {
                    if (!this.mQuit) continue;
                    return;
                }
                request.addMarker("network-queue-take");
                if (request.isCanceled()) {
                    request.finish("network-discard-cancelled");
                }
                this.addTrafficStatsTag(request);
                object = this.mNetwork.performRequest(request);
                request.addMarker("network-http-complete");
                if (!((NetworkResponse)object).notModified || !request.hasHadResponseDelivered()) break block11;
                request.finish("not-modified");
                continue;
            }
            try {
                object = request.parseNetworkResponse((NetworkResponse)object);
                request.addMarker("network-parse-complete");
                if (request.shouldCache() && ((Response)object).cacheEntry != null) {
                    this.mCache.put(request.getCacheKey(), ((Response)object).cacheEntry);
                    request.addMarker("network-cache-written");
                }
                request.markDelivered();
                this.mDelivery.postResponse(request, (Response<?>)object);
                continue;
            }
            catch (VolleyError volleyError) {
                volleyError.setNetworkTimeMs(SystemClock.elapsedRealtime() - l2);
                this.parseAndDeliverNetworkError(request, volleyError);
            }
            catch (Exception exception) {
                VolleyLog.e(exception, "Unhandled exception %s", exception.toString());
                object = new VolleyError(exception);
                ((VolleyError)object).setNetworkTimeMs(SystemClock.elapsedRealtime() - l2);
                this.mDelivery.postError(request, (VolleyError)object);
                continue;
            }
            break;
        }
    }
}

