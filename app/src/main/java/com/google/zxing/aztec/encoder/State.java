/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.aztec.encoder;

import com.google.zxing.aztec.encoder.HighLevelEncoder;
import com.google.zxing.aztec.encoder.Token;
import com.google.zxing.common.BitArray;
import java.util.LinkedList;

final class State {
    static final State INITIAL_STATE = new State(Token.EMPTY, 0, 0, 0);
    private final int binaryShiftByteCount;
    private final int bitCount;
    private final int mode;
    private final Token token;

    private State(Token token, int n2, int n3, int n4) {
        this.token = token;
        this.mode = n2;
        this.binaryShiftByteCount = n3;
        this.bitCount = n4;
    }

    /*
     * Enabled aggressive block sorting
     */
    State addBinaryShiftChar(int n2) {
        Object object;
        int n3;
        int n4;
        int n5;
        Object object2;
        block3: {
            int n6;
            block2: {
                object2 = this.token;
                n6 = this.mode;
                n5 = this.bitCount;
                if (this.mode == 4) break block2;
                n4 = n5;
                n3 = n6;
                object = object2;
                if (this.mode != 2) break block3;
            }
            n3 = HighLevelEncoder.LATCH_TABLE[n6][0];
            object = ((Token)object2).add(0xFFFF & n3, n3 >> 16);
            n4 = n5 + (n3 >> 16);
            n3 = 0;
        }
        n5 = this.binaryShiftByteCount == 0 || this.binaryShiftByteCount == 31 ? 18 : (this.binaryShiftByteCount == 62 ? 9 : 8);
        object = object2 = new State((Token)object, n3, this.binaryShiftByteCount + 1, n4 + n5);
        if (((State)object2).binaryShiftByteCount != 2078) return object;
        return ((State)object2).endBinaryShift(n2 + 1);
    }

    State endBinaryShift(int n2) {
        if (this.binaryShiftByteCount == 0) {
            return this;
        }
        return new State(this.token.addBinaryShift(n2 - this.binaryShiftByteCount, this.binaryShiftByteCount), this.mode, 0, this.bitCount);
    }

    int getBinaryShiftByteCount() {
        return this.binaryShiftByteCount;
    }

    int getBitCount() {
        return this.bitCount;
    }

    int getMode() {
        return this.mode;
    }

    Token getToken() {
        return this.token;
    }

    boolean isBetterThanOrEqualTo(State state) {
        int n2;
        block2: {
            int n3;
            block3: {
                n2 = n3 = this.bitCount + (HighLevelEncoder.LATCH_TABLE[this.mode][state.mode] >> 16);
                if (state.binaryShiftByteCount <= 0) break block2;
                if (this.binaryShiftByteCount == 0) break block3;
                n2 = n3;
                if (this.binaryShiftByteCount <= state.binaryShiftByteCount) break block2;
            }
            n2 = n3 + 10;
        }
        return n2 <= state.bitCount;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    State latchAndAppend(int n2, int n3) {
        int n4 = this.bitCount;
        Token token = this.token;
        int n5 = n4;
        Token token2 = token;
        if (n2 != this.mode) {
            n5 = HighLevelEncoder.LATCH_TABLE[this.mode][n2];
            token2 = token.add(0xFFFF & n5, n5 >> 16);
            n5 = n4 + (n5 >> 16);
        }
        if (n2 == 2) {
            n4 = 4;
            return new State(token2.add(n3, n4), n2, 0, n5 + n4);
        }
        n4 = 5;
        return new State(token2.add(n3, n4), n2, 0, n5 + n4);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    State shiftAndAppend(int n2, int n3) {
        int n4;
        Token token = this.token;
        if (this.mode == 2) {
            n4 = 4;
            return new State(token.add(HighLevelEncoder.SHIFT_TABLE[this.mode][n2], n4).add(n3, 5), this.mode, 0, this.bitCount + n4 + 5);
        }
        n4 = 5;
        return new State(token.add(HighLevelEncoder.SHIFT_TABLE[this.mode][n2], n4).add(n3, 5), this.mode, 0, this.bitCount + n4 + 5);
    }

    BitArray toBitArray(byte[] byArray) {
        Object object;
        Object object2 = new LinkedList<Token>();
        for (object = this.endBinaryShift((int)byArray.length).token; object != null; object = ((Token)object).getPrevious()) {
            object2.addFirst(object);
        }
        object = new BitArray();
        object2 = object2.iterator();
        while (object2.hasNext()) {
            ((Token)object2.next()).appendTo((BitArray)object, byArray);
        }
        return object;
    }

    public String toString() {
        return String.format("%s bits=%d bytes=%d", HighLevelEncoder.MODE_NAMES[this.mode], this.bitCount, this.binaryShiftByteCount);
    }
}

