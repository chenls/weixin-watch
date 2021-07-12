/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing;

public abstract class ReaderException
extends Exception {
    protected static final boolean isStackTrace;

    /*
     * Enabled aggressive block sorting
     */
    static {
        boolean bl2 = System.getProperty("surefire.test.class.path") != null;
        isStackTrace = bl2;
    }

    ReaderException() {
    }

    ReaderException(Throwable throwable) {
        super(throwable);
    }

    @Override
    public final Throwable fillInStackTrace() {
        return null;
    }
}

