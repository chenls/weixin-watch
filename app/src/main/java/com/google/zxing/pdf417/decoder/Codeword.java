/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.pdf417.decoder;

final class Codeword {
    private static final int BARCODE_ROW_UNKNOWN = -1;
    private final int bucket;
    private final int endX;
    private int rowNumber = -1;
    private final int startX;
    private final int value;

    Codeword(int n2, int n3, int n4, int n5) {
        this.startX = n2;
        this.endX = n3;
        this.bucket = n4;
        this.value = n5;
    }

    int getBucket() {
        return this.bucket;
    }

    int getEndX() {
        return this.endX;
    }

    int getRowNumber() {
        return this.rowNumber;
    }

    int getStartX() {
        return this.startX;
    }

    int getValue() {
        return this.value;
    }

    int getWidth() {
        return this.endX - this.startX;
    }

    boolean hasValidRowNumber() {
        return this.isValidRowNumber(this.rowNumber);
    }

    boolean isValidRowNumber(int n2) {
        return n2 != -1 && this.bucket == n2 % 3 * 3;
    }

    void setRowNumber(int n2) {
        this.rowNumber = n2;
    }

    void setRowNumberAsRowIndicatorColumn() {
        this.rowNumber = this.value / 30 * 3 + this.bucket / 3;
    }

    public String toString() {
        return this.rowNumber + "|" + this.value;
    }
}

