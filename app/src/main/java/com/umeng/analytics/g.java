/*
 * Decompiled with CFR 0.151.
 */
package com.umeng.analytics;

public abstract class g
implements Runnable {
    public abstract void a();

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void run() {
        try {
            this.a();
            return;
        }
        catch (Throwable throwable) {
            if (throwable == null) return;
            throwable.printStackTrace();
            return;
        }
    }
}

