/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.qrcode.encoder;

import java.lang.reflect.Array;

public final class ByteMatrix {
    private final byte[][] bytes;
    private final int height;
    private final int width;

    public ByteMatrix(int n2, int n3) {
        this.bytes = (byte[][])Array.newInstance(Byte.TYPE, n3, n2);
        this.width = n2;
        this.height = n3;
    }

    public void clear(byte by2) {
        for (int i2 = 0; i2 < this.height; ++i2) {
            for (int i3 = 0; i3 < this.width; ++i3) {
                this.bytes[i2][i3] = by2;
            }
        }
    }

    public byte get(int n2, int n3) {
        return this.bytes[n3][n2];
    }

    public byte[][] getArray() {
        return this.bytes;
    }

    public int getHeight() {
        return this.height;
    }

    public int getWidth() {
        return this.width;
    }

    public void set(int n2, int n3, byte by2) {
        this.bytes[n3][n2] = by2;
    }

    public void set(int n2, int n3, int n4) {
        this.bytes[n3][n2] = (byte)n4;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void set(int n2, int n3, boolean bl2) {
        byte[] byArray = this.bytes[n3];
        n3 = bl2 ? 1 : 0;
        byArray[n2] = (byte)n3;
    }

    /*
     * Enabled aggressive block sorting
     */
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(this.width * 2 * this.height + 2);
        int n2 = 0;
        while (n2 < this.height) {
            block5: for (int i2 = 0; i2 < this.width; ++i2) {
                switch (this.bytes[n2][i2]) {
                    default: {
                        stringBuilder.append("  ");
                        continue block5;
                    }
                    case 0: {
                        stringBuilder.append(" 0");
                        continue block5;
                    }
                    case 1: {
                        stringBuilder.append(" 1");
                    }
                }
            }
            stringBuilder.append('\n');
            ++n2;
        }
        return stringBuilder.toString();
    }
}

