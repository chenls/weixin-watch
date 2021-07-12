/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.qrcode.decoder;

import com.google.zxing.ResultPoint;

public final class QRCodeDecoderMetaData {
    private final boolean mirrored;

    QRCodeDecoderMetaData(boolean bl2) {
        this.mirrored = bl2;
    }

    public void applyMirroredCorrection(ResultPoint[] resultPointArray) {
        if (!this.mirrored || resultPointArray == null || resultPointArray.length < 3) {
            return;
        }
        ResultPoint resultPoint = resultPointArray[0];
        resultPointArray[0] = resultPointArray[2];
        resultPointArray[2] = resultPoint;
    }

    public boolean isMirrored() {
        return this.mirrored;
    }
}

