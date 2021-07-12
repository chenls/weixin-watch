/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing;

import com.google.zxing.InvertedLuminanceSource;

public abstract class LuminanceSource {
    private final int height;
    private final int width;

    protected LuminanceSource(int n2, int n3) {
        this.width = n2;
        this.height = n3;
    }

    public LuminanceSource crop(int n2, int n3, int n4, int n5) {
        throw new UnsupportedOperationException("This luminance source does not support cropping.");
    }

    public final int getHeight() {
        return this.height;
    }

    public abstract byte[] getMatrix();

    public abstract byte[] getRow(int var1, byte[] var2);

    public final int getWidth() {
        return this.width;
    }

    public LuminanceSource invert() {
        return new InvertedLuminanceSource(this);
    }

    public boolean isCropSupported() {
        return false;
    }

    public boolean isRotateSupported() {
        return false;
    }

    public LuminanceSource rotateCounterClockwise() {
        throw new UnsupportedOperationException("This luminance source does not support rotation by 90 degrees.");
    }

    public LuminanceSource rotateCounterClockwise45() {
        throw new UnsupportedOperationException("This luminance source does not support rotation by 45 degrees.");
    }

    /*
     * Enabled aggressive block sorting
     */
    public final String toString() {
        byte[] byArray = new byte[this.width];
        StringBuilder stringBuilder = new StringBuilder(this.height * (this.width + 1));
        int n2 = 0;
        while (n2 < this.height) {
            byArray = this.getRow(n2, byArray);
            for (int i2 = 0; i2 < this.width; ++i2) {
                int n3 = byArray[i2] & 0xFF;
                int n4 = n3 < 64 ? 35 : (n3 < 128 ? 43 : (n3 < 192 ? 46 : 32));
                stringBuilder.append((char)n4);
            }
            stringBuilder.append('\n');
            ++n2;
        }
        return stringBuilder.toString();
    }
}

