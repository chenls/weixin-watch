/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.common;

public final class BitSource {
    private int bitOffset;
    private int byteOffset;
    private final byte[] bytes;

    public BitSource(byte[] byArray) {
        this.bytes = byArray;
    }

    public int available() {
        return (this.bytes.length - this.byteOffset) * 8 - this.bitOffset;
    }

    public int getBitOffset() {
        return this.bitOffset;
    }

    public int getByteOffset() {
        return this.byteOffset;
    }

    /*
     * Enabled aggressive block sorting
     */
    public int readBits(int n2) {
        if (n2 < 1 || n2 > 32 || n2 > this.available()) {
            throw new IllegalArgumentException(String.valueOf(n2));
        }
        int n3 = 0;
        int n4 = n2;
        if (this.bitOffset > 0) {
            n4 = 8 - this.bitOffset;
            n3 = n2 < n4 ? n2 : n4;
            int n5 = (this.bytes[this.byteOffset] & 255 >> 8 - n3 << (n4 -= n3)) >> n4;
            n2 -= n3;
            this.bitOffset += n3;
            n3 = n5;
            n4 = n2;
            if (this.bitOffset == 8) {
                this.bitOffset = 0;
                ++this.byteOffset;
                n4 = n2;
                n3 = n5;
            }
        }
        n2 = n3;
        if (n4 > 0) {
            while (n4 >= 8) {
                n3 = n3 << 8 | this.bytes[this.byteOffset] & 0xFF;
                ++this.byteOffset;
                n4 -= 8;
            }
            n2 = n3;
            if (n4 > 0) {
                n2 = 8 - n4;
                n2 = n3 << n4 | (this.bytes[this.byteOffset] & 255 >> n2 << n2) >> n2;
                this.bitOffset += n4;
            }
        }
        return n2;
    }
}

