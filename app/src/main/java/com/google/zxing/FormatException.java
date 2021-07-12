/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing;

import com.google.zxing.ReaderException;

public final class FormatException
extends ReaderException {
    private static final FormatException instance = new FormatException();

    private FormatException() {
    }

    private FormatException(Throwable throwable) {
        super(throwable);
    }

    public static FormatException getFormatInstance() {
        if (isStackTrace) {
            return new FormatException();
        }
        return instance;
    }

    public static FormatException getFormatInstance(Throwable throwable) {
        if (isStackTrace) {
            return new FormatException(throwable);
        }
        return instance;
    }
}

