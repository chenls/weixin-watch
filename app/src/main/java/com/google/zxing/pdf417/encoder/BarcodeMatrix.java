/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.pdf417.encoder;

import com.google.zxing.pdf417.encoder.BarcodeRow;
import java.lang.reflect.Array;

public final class BarcodeMatrix {
    private int currentRow;
    private final int height;
    private final BarcodeRow[] matrix;
    private final int width;

    BarcodeMatrix(int n2, int n3) {
        this.matrix = new BarcodeRow[n2];
        int n4 = this.matrix.length;
        for (int i2 = 0; i2 < n4; ++i2) {
            this.matrix[i2] = new BarcodeRow((n3 + 4) * 17 + 1);
        }
        this.width = n3 * 17;
        this.height = n2;
        this.currentRow = -1;
    }

    BarcodeRow getCurrentRow() {
        return this.matrix[this.currentRow];
    }

    public byte[][] getMatrix() {
        return this.getScaledMatrix(1, 1);
    }

    public byte[][] getScaledMatrix(int n2, int n3) {
        int n4 = this.height;
        int n5 = this.width;
        byte[][] byArray = (byte[][])Array.newInstance(Byte.TYPE, n4 * n3, n5 * n2);
        n5 = this.height * n3;
        for (n4 = 0; n4 < n5; ++n4) {
            byArray[n5 - n4 - 1] = this.matrix[n4 / n3].getScaledRow(n2);
        }
        return byArray;
    }

    void set(int n2, int n3, byte by2) {
        this.matrix[n3].set(n2, by2);
    }

    void startRow() {
        ++this.currentRow;
    }
}

