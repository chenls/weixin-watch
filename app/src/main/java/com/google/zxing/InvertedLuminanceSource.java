/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing;

import com.google.zxing.LuminanceSource;

public final class InvertedLuminanceSource
extends LuminanceSource {
    private final LuminanceSource delegate;

    public InvertedLuminanceSource(LuminanceSource luminanceSource) {
        super(luminanceSource.getWidth(), luminanceSource.getHeight());
        this.delegate = luminanceSource;
    }

    @Override
    public LuminanceSource crop(int n2, int n3, int n4, int n5) {
        return new InvertedLuminanceSource(this.delegate.crop(n2, n3, n4, n5));
    }

    @Override
    public byte[] getMatrix() {
        byte[] byArray = this.delegate.getMatrix();
        int n2 = this.getWidth() * this.getHeight();
        byte[] byArray2 = new byte[n2];
        for (int i2 = 0; i2 < n2; ++i2) {
            byArray2[i2] = (byte)(255 - (byArray[i2] & 0xFF));
        }
        return byArray2;
    }

    @Override
    public byte[] getRow(int n2, byte[] byArray) {
        byArray = this.delegate.getRow(n2, byArray);
        int n3 = this.getWidth();
        for (n2 = 0; n2 < n3; ++n2) {
            byArray[n2] = (byte)(255 - (byArray[n2] & 0xFF));
        }
        return byArray;
    }

    @Override
    public LuminanceSource invert() {
        return this.delegate;
    }

    @Override
    public boolean isCropSupported() {
        return this.delegate.isCropSupported();
    }

    @Override
    public boolean isRotateSupported() {
        return this.delegate.isRotateSupported();
    }

    @Override
    public LuminanceSource rotateCounterClockwise() {
        return new InvertedLuminanceSource(this.delegate.rotateCounterClockwise());
    }

    @Override
    public LuminanceSource rotateCounterClockwise45() {
        return new InvertedLuminanceSource(this.delegate.rotateCounterClockwise45());
    }
}

