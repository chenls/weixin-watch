/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing;

import com.google.zxing.LuminanceSource;

public final class RGBLuminanceSource
extends LuminanceSource {
    private final int dataHeight;
    private final int dataWidth;
    private final int left;
    private final byte[] luminances;
    private final int top;

    /*
     * Enabled aggressive block sorting
     */
    public RGBLuminanceSource(int n2, int n3, int[] nArray) {
        super(n2, n3);
        this.dataWidth = n2;
        this.dataHeight = n3;
        this.left = 0;
        this.top = 0;
        this.luminances = new byte[n2 * n3];
        int n4 = 0;
        while (n4 < n3) {
            int n5 = n4 * n2;
            for (int i2 = 0; i2 < n2; ++i2) {
                int n6 = nArray[n5 + i2];
                int n7 = n6 >> 16 & 0xFF;
                int n8 = n6 >> 8 & 0xFF;
                this.luminances[n5 + i2] = n7 == n8 && n8 == n6 ? (byte)n7 : (byte)((n8 * 2 + n7 + (n6 &= 0xFF)) / 4);
            }
            ++n4;
        }
        return;
    }

    private RGBLuminanceSource(byte[] byArray, int n2, int n3, int n4, int n5, int n6, int n7) {
        super(n6, n7);
        if (n4 + n6 > n2 || n5 + n7 > n3) {
            throw new IllegalArgumentException("Crop rectangle does not fit within image data.");
        }
        this.luminances = byArray;
        this.dataWidth = n2;
        this.dataHeight = n3;
        this.left = n4;
        this.top = n5;
    }

    @Override
    public LuminanceSource crop(int n2, int n3, int n4, int n5) {
        return new RGBLuminanceSource(this.luminances, this.dataWidth, this.dataHeight, this.left + n2, this.top + n3, n4, n5);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public byte[] getMatrix() {
        int n2 = this.getWidth();
        int n3 = this.getHeight();
        if (n2 == this.dataWidth && n3 == this.dataHeight) {
            return this.luminances;
        }
        int n4 = n2 * n3;
        byte[] byArray = new byte[n4];
        int n5 = this.top * this.dataWidth + this.left;
        if (n2 == this.dataWidth) {
            System.arraycopy(this.luminances, n5, byArray, 0, n4);
            return byArray;
        }
        byte[] byArray2 = this.luminances;
        n4 = 0;
        while (true) {
            byte[] byArray3 = byArray;
            if (n4 >= n3) return byArray3;
            System.arraycopy(byArray2, n5, byArray, n4 * n2, n2);
            n5 += this.dataWidth;
            ++n4;
        }
    }

    @Override
    public byte[] getRow(int n2, byte[] byArray) {
        byte[] byArray2;
        int n3;
        block5: {
            block4: {
                if (n2 < 0 || n2 >= this.getHeight()) {
                    throw new IllegalArgumentException("Requested row is outside the image: " + n2);
                }
                n3 = this.getWidth();
                if (byArray == null) break block4;
                byArray2 = byArray;
                if (byArray.length >= n3) break block5;
            }
            byArray2 = new byte[n3];
        }
        int n4 = this.top;
        int n5 = this.dataWidth;
        int n6 = this.left;
        System.arraycopy(this.luminances, (n4 + n2) * n5 + n6, byArray2, 0, n3);
        return byArray2;
    }

    @Override
    public boolean isCropSupported() {
        return true;
    }
}

