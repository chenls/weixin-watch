/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing;

import com.google.zxing.ReaderException;

public final class ChecksumException
extends ReaderException {
    private static final ChecksumException instance = new ChecksumException();

    private ChecksumException() {
    }

    private ChecksumException(Throwable throwable) {
        super(throwable);
    }

    public static ChecksumException getChecksumInstance() {
        if (isStackTrace) {
            return new ChecksumException();
        }
        return instance;
    }

    public static ChecksumException getChecksumInstance(Throwable throwable) {
        if (isStackTrace) {
            return new ChecksumException(throwable);
        }
        return instance;
    }
}

