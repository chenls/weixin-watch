/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.aztec.encoder;

import com.google.zxing.aztec.encoder.Token;
import com.google.zxing.common.BitArray;

final class SimpleToken
extends Token {
    private final short bitCount;
    private final short value;

    SimpleToken(Token token, int n2, int n3) {
        super(token);
        this.value = (short)n2;
        this.bitCount = (short)n3;
    }

    @Override
    void appendTo(BitArray bitArray, byte[] byArray) {
        bitArray.appendBits(this.value, this.bitCount);
    }

    public String toString() {
        short s2 = this.value;
        short s3 = this.bitCount;
        short s4 = this.bitCount;
        return '<' + Integer.toBinaryString(1 << this.bitCount | (s2 & (1 << s3) - 1 | 1 << s4)).substring(1) + '>';
    }
}

