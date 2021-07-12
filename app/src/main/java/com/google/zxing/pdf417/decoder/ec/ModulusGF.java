/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.pdf417.decoder.ec;

import com.google.zxing.pdf417.decoder.ec.ModulusPoly;

public final class ModulusGF {
    public static final ModulusGF PDF417_GF = new ModulusGF(929, 3);
    private final int[] expTable;
    private final int[] logTable;
    private final int modulus;
    private final ModulusPoly one;
    private final ModulusPoly zero;

    private ModulusGF(int n2, int n3) {
        this.modulus = n2;
        this.expTable = new int[n2];
        this.logTable = new int[n2];
        int n4 = 1;
        for (int i2 = 0; i2 < n2; ++i2) {
            this.expTable[i2] = n4;
            n4 = n4 * n3 % n2;
        }
        for (n3 = 0; n3 < n2 - 1; ++n3) {
            this.logTable[this.expTable[n3]] = n3;
        }
        this.zero = new ModulusPoly(this, new int[]{0});
        this.one = new ModulusPoly(this, new int[]{1});
    }

    int add(int n2, int n3) {
        return (n2 + n3) % this.modulus;
    }

    ModulusPoly buildMonomial(int n2, int n3) {
        if (n2 < 0) {
            throw new IllegalArgumentException();
        }
        if (n3 == 0) {
            return this.zero;
        }
        int[] nArray = new int[n2 + 1];
        nArray[0] = n3;
        return new ModulusPoly(this, nArray);
    }

    int exp(int n2) {
        return this.expTable[n2];
    }

    ModulusPoly getOne() {
        return this.one;
    }

    int getSize() {
        return this.modulus;
    }

    ModulusPoly getZero() {
        return this.zero;
    }

    int inverse(int n2) {
        if (n2 == 0) {
            throw new ArithmeticException();
        }
        return this.expTable[this.modulus - this.logTable[n2] - 1];
    }

    int log(int n2) {
        if (n2 == 0) {
            throw new IllegalArgumentException();
        }
        return this.logTable[n2];
    }

    int multiply(int n2, int n3) {
        if (n2 == 0 || n3 == 0) {
            return 0;
        }
        return this.expTable[(this.logTable[n2] + this.logTable[n3]) % (this.modulus - 1)];
    }

    int subtract(int n2, int n3) {
        return (this.modulus + n2 - n3) % this.modulus;
    }
}

