/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.aztec.encoder;

import com.google.zxing.aztec.encoder.BinaryShiftToken;
import com.google.zxing.aztec.encoder.SimpleToken;
import com.google.zxing.common.BitArray;

abstract class Token {
    static final Token EMPTY = new SimpleToken(null, 0, 0);
    private final Token previous;

    Token(Token token) {
        this.previous = token;
    }

    final Token add(int n2, int n3) {
        return new SimpleToken(this, n2, n3);
    }

    final Token addBinaryShift(int n2, int n3) {
        return new BinaryShiftToken(this, n2, n3);
    }

    abstract void appendTo(BitArray var1, byte[] var2);

    final Token getPrevious() {
        return this.previous;
    }
}

