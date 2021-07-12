/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing;

import com.google.zxing.Binarizer;
import com.google.zxing.LuminanceSource;
import com.google.zxing.NotFoundException;
import com.google.zxing.common.BitArray;
import com.google.zxing.common.BitMatrix;

public final class BinaryBitmap {
    private final Binarizer binarizer;
    private BitMatrix matrix;

    public BinaryBitmap(Binarizer binarizer) {
        if (binarizer == null) {
            throw new IllegalArgumentException("Binarizer must be non-null.");
        }
        this.binarizer = binarizer;
    }

    public BinaryBitmap crop(int n2, int n3, int n4, int n5) {
        LuminanceSource luminanceSource = this.binarizer.getLuminanceSource().crop(n2, n3, n4, n5);
        return new BinaryBitmap(this.binarizer.createBinarizer(luminanceSource));
    }

    public BitMatrix getBlackMatrix() throws NotFoundException {
        if (this.matrix == null) {
            this.matrix = this.binarizer.getBlackMatrix();
        }
        return this.matrix;
    }

    public BitArray getBlackRow(int n2, BitArray bitArray) throws NotFoundException {
        return this.binarizer.getBlackRow(n2, bitArray);
    }

    public int getHeight() {
        return this.binarizer.getHeight();
    }

    public int getWidth() {
        return this.binarizer.getWidth();
    }

    public boolean isCropSupported() {
        return this.binarizer.getLuminanceSource().isCropSupported();
    }

    public boolean isRotateSupported() {
        return this.binarizer.getLuminanceSource().isRotateSupported();
    }

    public BinaryBitmap rotateCounterClockwise() {
        LuminanceSource luminanceSource = this.binarizer.getLuminanceSource().rotateCounterClockwise();
        return new BinaryBitmap(this.binarizer.createBinarizer(luminanceSource));
    }

    public BinaryBitmap rotateCounterClockwise45() {
        LuminanceSource luminanceSource = this.binarizer.getLuminanceSource().rotateCounterClockwise45();
        return new BinaryBitmap(this.binarizer.createBinarizer(luminanceSource));
    }

    public String toString() {
        try {
            String string2 = this.getBlackMatrix().toString();
            return string2;
        }
        catch (NotFoundException notFoundException) {
            return "";
        }
    }
}

