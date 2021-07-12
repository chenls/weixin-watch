/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.qrcode.decoder;

public enum ErrorCorrectionLevel {
    L(1),
    M(0),
    Q(3),
    H(2);

    private static final ErrorCorrectionLevel[] FOR_BITS = new ErrorCorrectionLevel[]{M, L, H, Q};
    private final int bits;

    private ErrorCorrectionLevel(int n3) {
        this.bits = n3;
    }

    public static ErrorCorrectionLevel forBits(int n2) {
        if (n2 < 0 || n2 >= FOR_BITS.length) {
            throw new IllegalArgumentException();
        }
        return FOR_BITS[n2];
    }

    public int getBits() {
        return this.bits;
    }
}

