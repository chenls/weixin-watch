/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.pdf417.encoder;

public final class Dimensions {
    private final int maxCols;
    private final int maxRows;
    private final int minCols;
    private final int minRows;

    public Dimensions(int n2, int n3, int n4, int n5) {
        this.minCols = n2;
        this.maxCols = n3;
        this.minRows = n4;
        this.maxRows = n5;
    }

    public int getMaxCols() {
        return this.maxCols;
    }

    public int getMaxRows() {
        return this.maxRows;
    }

    public int getMinCols() {
        return this.minCols;
    }

    public int getMinRows() {
        return this.minRows;
    }
}

