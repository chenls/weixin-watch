/*
 * Decompiled with CFR 0.151.
 */
package com.alibaba.fastjson.asm;

public class ByteVector {
    byte[] data;
    int length;

    public ByteVector() {
        this.data = new byte[64];
    }

    public ByteVector(int n2) {
        this.data = new byte[n2];
    }

    private void enlarge(int n2) {
        int n3 = this.data.length * 2;
        if (n3 > (n2 = this.length + n2)) {
            n2 = n3;
        }
        byte[] byArray = new byte[n2];
        System.arraycopy(this.data, 0, byArray, 0, this.length);
        this.data = byArray;
    }

    ByteVector put11(int n2, int n3) {
        int n4 = this.length;
        if (n4 + 2 > this.data.length) {
            this.enlarge(2);
        }
        byte[] byArray = this.data;
        int n5 = n4 + 1;
        byArray[n4] = (byte)n2;
        byArray[n5] = (byte)n3;
        this.length = n5 + 1;
        return this;
    }

    ByteVector put12(int n2, int n3) {
        int n4 = this.length;
        if (n4 + 3 > this.data.length) {
            this.enlarge(3);
        }
        byte[] byArray = this.data;
        int n5 = n4 + 1;
        byArray[n4] = (byte)n2;
        n2 = n5 + 1;
        byArray[n5] = (byte)(n3 >>> 8);
        byArray[n2] = (byte)n3;
        this.length = n2 + 1;
        return this;
    }

    public ByteVector putByte(int n2) {
        int n3 = this.length;
        if (n3 + 1 > this.data.length) {
            this.enlarge(1);
        }
        this.data[n3] = (byte)n2;
        this.length = n3 + 1;
        return this;
    }

    public ByteVector putByteArray(byte[] byArray, int n2, int n3) {
        if (this.length + n3 > this.data.length) {
            this.enlarge(n3);
        }
        if (byArray != null) {
            System.arraycopy(byArray, n2, this.data, this.length, n3);
        }
        this.length += n3;
        return this;
    }

    public ByteVector putInt(int n2) {
        int n3 = this.length;
        if (n3 + 4 > this.data.length) {
            this.enlarge(4);
        }
        byte[] byArray = this.data;
        int n4 = n3 + 1;
        byArray[n3] = (byte)(n2 >>> 24);
        n3 = n4 + 1;
        byArray[n4] = (byte)(n2 >>> 16);
        n4 = n3 + 1;
        byArray[n3] = (byte)(n2 >>> 8);
        byArray[n4] = (byte)n2;
        this.length = n4 + 1;
        return this;
    }

    public ByteVector putShort(int n2) {
        int n3 = this.length;
        if (n3 + 2 > this.data.length) {
            this.enlarge(2);
        }
        byte[] byArray = this.data;
        int n4 = n3 + 1;
        byArray[n3] = (byte)(n2 >>> 8);
        byArray[n4] = (byte)n2;
        this.length = n4 + 1;
        return this;
    }

    public ByteVector putUTF8(String string2) {
        int n2 = this.length;
        int n3 = string2.length();
        if (n2 + 2 + n3 > this.data.length) {
            this.enlarge(n3 + 2);
        }
        byte[] byArray = this.data;
        int n4 = n2 + 1;
        byArray[n2] = (byte)(n3 >>> 8);
        byArray[n4] = (byte)n3;
        n2 = 0;
        ++n4;
        while (n2 < n3) {
            char c2 = string2.charAt(n2);
            if (c2 >= '\u0001' && c2 <= '\u007f') {
                byArray[n4] = (byte)c2;
                ++n2;
                ++n4;
                continue;
            }
            throw new UnsupportedOperationException();
        }
        this.length = n4;
        return this;
    }
}

