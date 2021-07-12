/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.common.reedsolomon;

import com.google.zxing.common.reedsolomon.GenericGFPoly;

public final class GenericGF {
    public static final GenericGF AZTEC_DATA_10;
    public static final GenericGF AZTEC_DATA_12;
    public static final GenericGF AZTEC_DATA_6;
    public static final GenericGF AZTEC_DATA_8;
    public static final GenericGF AZTEC_PARAM;
    public static final GenericGF DATA_MATRIX_FIELD_256;
    public static final GenericGF MAXICODE_FIELD_64;
    public static final GenericGF QR_CODE_FIELD_256;
    private final int[] expTable;
    private final int generatorBase;
    private final int[] logTable;
    private final GenericGFPoly one;
    private final int primitive;
    private final int size;
    private final GenericGFPoly zero;

    static {
        AZTEC_DATA_12 = new GenericGF(4201, 4096, 1);
        AZTEC_DATA_10 = new GenericGF(1033, 1024, 1);
        AZTEC_DATA_6 = new GenericGF(67, 64, 1);
        AZTEC_PARAM = new GenericGF(19, 16, 1);
        QR_CODE_FIELD_256 = new GenericGF(285, 256, 0);
        AZTEC_DATA_8 = DATA_MATRIX_FIELD_256 = new GenericGF(301, 256, 1);
        MAXICODE_FIELD_64 = AZTEC_DATA_6;
    }

    public GenericGF(int n2, int n3, int n4) {
        this.primitive = n2;
        this.size = n3;
        this.generatorBase = n4;
        this.expTable = new int[n3];
        this.logTable = new int[n3];
        n4 = 1;
        for (int i2 = 0; i2 < n3; ++i2) {
            int n5;
            this.expTable[i2] = n4;
            n4 = n5 = n4 * 2;
            if (n5 < n3) continue;
            n4 = (n5 ^ n2) & n3 - 1;
        }
        for (n2 = 0; n2 < n3 - 1; ++n2) {
            this.logTable[this.expTable[n2]] = n2;
        }
        this.zero = new GenericGFPoly(this, new int[]{0});
        this.one = new GenericGFPoly(this, new int[]{1});
    }

    static int addOrSubtract(int n2, int n3) {
        return n2 ^ n3;
    }

    GenericGFPoly buildMonomial(int n2, int n3) {
        if (n2 < 0) {
            throw new IllegalArgumentException();
        }
        if (n3 == 0) {
            return this.zero;
        }
        int[] nArray = new int[n2 + 1];
        nArray[0] = n3;
        return new GenericGFPoly(this, nArray);
    }

    int exp(int n2) {
        return this.expTable[n2];
    }

    public int getGeneratorBase() {
        return this.generatorBase;
    }

    GenericGFPoly getOne() {
        return this.one;
    }

    public int getSize() {
        return this.size;
    }

    GenericGFPoly getZero() {
        return this.zero;
    }

    int inverse(int n2) {
        if (n2 == 0) {
            throw new ArithmeticException();
        }
        return this.expTable[this.size - this.logTable[n2] - 1];
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
        return this.expTable[(this.logTable[n2] + this.logTable[n3]) % (this.size - 1)];
    }

    public String toString() {
        return "GF(0x" + Integer.toHexString(this.primitive) + ',' + this.size + ')';
    }
}

