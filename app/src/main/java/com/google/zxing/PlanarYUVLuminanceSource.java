/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing;

import com.google.zxing.LuminanceSource;

public final class PlanarYUVLuminanceSource
extends LuminanceSource {
    private static final int THUMBNAIL_SCALE_FACTOR = 2;
    private final int dataHeight;
    private final int dataWidth;
    private final int left;
    private final int top;
    private final byte[] yuvData;

    public PlanarYUVLuminanceSource(byte[] byArray, int n2, int n3, int n4, int n5, int n6, int n7, boolean bl2) {
        super(n6, n7);
        if (n4 + n6 > n2 || n5 + n7 > n3) {
            throw new IllegalArgumentException("Crop rectangle does not fit within image data.");
        }
        this.yuvData = byArray;
        this.dataWidth = n2;
        this.dataHeight = n3;
        this.left = n4;
        this.top = n5;
        if (bl2) {
            this.reverseHorizontal(n6, n7);
        }
    }

    private void reverseHorizontal(int n2, int n3) {
        byte[] byArray = this.yuvData;
        int n4 = 0;
        int n5 = this.top * this.dataWidth + this.left;
        while (n4 < n3) {
            int n6 = n2 / 2;
            int n7 = n5;
            int n8 = n5 + n2 - 1;
            while (n7 < n5 + n6) {
                byte by2 = byArray[n7];
                byArray[n7] = byArray[n8];
                byArray[n8] = by2;
                ++n7;
                --n8;
            }
            ++n4;
            n5 += this.dataWidth;
        }
    }

    @Override
    public LuminanceSource crop(int n2, int n3, int n4, int n5) {
        return new PlanarYUVLuminanceSource(this.yuvData, this.dataWidth, this.dataHeight, this.left + n2, this.top + n3, n4, n5, false);
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
            return this.yuvData;
        }
        int n4 = n2 * n3;
        byte[] byArray = new byte[n4];
        int n5 = this.top * this.dataWidth + this.left;
        if (n2 == this.dataWidth) {
            System.arraycopy(this.yuvData, n5, byArray, 0, n4);
            return byArray;
        }
        byte[] byArray2 = this.yuvData;
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
        System.arraycopy(this.yuvData, (n4 + n2) * n5 + n6, byArray2, 0, n3);
        return byArray2;
    }

    public int getThumbnailHeight() {
        return this.getHeight() / 2;
    }

    public int getThumbnailWidth() {
        return this.getWidth() / 2;
    }

    @Override
    public boolean isCropSupported() {
        return true;
    }

    public int[] renderThumbnail() {
        int n2 = this.getWidth() / 2;
        int n3 = this.getHeight() / 2;
        int[] nArray = new int[n2 * n3];
        byte[] byArray = this.yuvData;
        int n4 = this.top * this.dataWidth + this.left;
        for (int i2 = 0; i2 < n3; ++i2) {
            for (int i3 = 0; i3 < n2; ++i3) {
                nArray[i2 * n2 + i3] = 0xFF000000 | 65793 * (byArray[i3 * 2 + n4] & 0xFF);
            }
            n4 += this.dataWidth * 2;
        }
        return nArray;
    }
}

