/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.pdf417.encoder;

final class BarcodeRow {
    private int currentLocation;
    private final byte[] row;

    BarcodeRow(int n2) {
        this.row = new byte[n2];
        this.currentLocation = 0;
    }

    void addBar(boolean bl2, int n2) {
        for (int i2 = 0; i2 < n2; ++i2) {
            int n3 = this.currentLocation;
            this.currentLocation = n3 + 1;
            this.set(n3, bl2);
        }
    }

    byte[] getScaledRow(int n2) {
        byte[] byArray = new byte[this.row.length * n2];
        for (int i2 = 0; i2 < byArray.length; ++i2) {
            byArray[i2] = this.row[i2 / n2];
        }
        return byArray;
    }

    void set(int n2, byte by2) {
        this.row[n2] = by2;
    }

    /*
     * Enabled aggressive block sorting
     */
    void set(int n2, boolean bl2) {
        byte[] byArray = this.row;
        boolean bl3 = bl2;
        byArray[n2] = (byte)(bl3 ? 1 : 0);
    }
}

