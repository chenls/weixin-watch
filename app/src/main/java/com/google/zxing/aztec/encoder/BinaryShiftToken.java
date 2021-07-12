/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.aztec.encoder;

import com.google.zxing.aztec.encoder.Token;
import com.google.zxing.common.BitArray;

final class BinaryShiftToken
extends Token {
    private final short binaryShiftByteCount;
    private final short binaryShiftStart;

    BinaryShiftToken(Token token, int n2, int n3) {
        super(token);
        this.binaryShiftStart = (short)n2;
        this.binaryShiftByteCount = (short)n3;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void appendTo(BitArray bitArray, byte[] byArray) {
        int n2 = 0;
        while (n2 < this.binaryShiftByteCount) {
            if (n2 == 0 || n2 == 31 && this.binaryShiftByteCount <= 62) {
                bitArray.appendBits(31, 5);
                if (this.binaryShiftByteCount > 62) {
                    bitArray.appendBits(this.binaryShiftByteCount - 31, 16);
                } else if (n2 == 0) {
                    bitArray.appendBits(Math.min(this.binaryShiftByteCount, 31), 5);
                } else {
                    bitArray.appendBits(this.binaryShiftByteCount - 31, 5);
                }
            }
            bitArray.appendBits(byArray[this.binaryShiftStart + n2], 8);
            ++n2;
        }
        return;
    }

    public String toString() {
        return "<" + this.binaryShiftStart + "::" + (this.binaryShiftStart + this.binaryShiftByteCount - 1) + '>';
    }
}

