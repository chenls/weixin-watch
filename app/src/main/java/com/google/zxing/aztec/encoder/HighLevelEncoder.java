/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.aztec.encoder;

import com.google.zxing.aztec.encoder.State;
import com.google.zxing.common.BitArray;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public final class HighLevelEncoder {
    private static final int[][] CHAR_MAP;
    static final int[][] LATCH_TABLE;
    static final int MODE_DIGIT = 2;
    static final int MODE_LOWER = 1;
    static final int MODE_MIXED = 3;
    static final String[] MODE_NAMES;
    static final int MODE_PUNCT = 4;
    static final int MODE_UPPER = 0;
    static final int[][] SHIFT_TABLE;
    private final byte[] text;

    static {
        Object object;
        int n2;
        MODE_NAMES = new String[]{"UPPER", "LOWER", "DIGIT", "MIXED", "PUNCT"};
        LATCH_TABLE = new int[][]{{0, 327708, 327710, 327709, 656318}, {590318, 0, 327710, 327709, 656318}, {262158, 590300, 0, 590301, 932798}, {327709, 327708, 656318, 0, 327710}, {327711, 656380, 656382, 656381, 0}};
        CHAR_MAP = (int[][])Array.newInstance(Integer.TYPE, 5, 256);
        HighLevelEncoder.CHAR_MAP[0][32] = 1;
        for (n2 = 65; n2 <= 90; ++n2) {
            HighLevelEncoder.CHAR_MAP[0][n2] = n2 - 65 + 2;
        }
        HighLevelEncoder.CHAR_MAP[1][32] = 1;
        for (n2 = 97; n2 <= 122; ++n2) {
            HighLevelEncoder.CHAR_MAP[1][n2] = n2 - 97 + 2;
        }
        HighLevelEncoder.CHAR_MAP[2][32] = 1;
        for (n2 = 48; n2 <= 57; ++n2) {
            HighLevelEncoder.CHAR_MAP[2][n2] = n2 - 48 + 2;
        }
        HighLevelEncoder.CHAR_MAP[2][44] = 12;
        HighLevelEncoder.CHAR_MAP[2][46] = 13;
        int[] nArray = object = new int[28];
        object[0] = 0;
        nArray[1] = 32;
        nArray[2] = 1;
        nArray[3] = 2;
        nArray[4] = 3;
        nArray[5] = 4;
        nArray[6] = 5;
        nArray[7] = 6;
        nArray[8] = 7;
        nArray[9] = 8;
        nArray[10] = 9;
        nArray[11] = 10;
        nArray[12] = 11;
        nArray[13] = 12;
        nArray[14] = 13;
        nArray[15] = 27;
        nArray[16] = 28;
        nArray[17] = 29;
        nArray[18] = 30;
        nArray[19] = 31;
        nArray[20] = 64;
        nArray[21] = 92;
        nArray[22] = 94;
        nArray[23] = 95;
        nArray[24] = 96;
        nArray[25] = 124;
        nArray[26] = 126;
        nArray[27] = 127;
        for (n2 = 0; n2 < ((int[])object).length; ++n2) {
            HighLevelEncoder.CHAR_MAP[3][object[n2]] = n2;
        }
        int[] nArray2 = object = new int[31];
        object[0] = 0;
        nArray2[1] = 13;
        nArray2[2] = 0;
        nArray2[3] = 0;
        nArray2[4] = 0;
        nArray2[5] = 0;
        nArray2[6] = 33;
        nArray2[7] = 39;
        nArray2[8] = 35;
        nArray2[9] = 36;
        nArray2[10] = 37;
        nArray2[11] = 38;
        nArray2[12] = 39;
        nArray2[13] = 40;
        nArray2[14] = 41;
        nArray2[15] = 42;
        nArray2[16] = 43;
        nArray2[17] = 44;
        nArray2[18] = 45;
        nArray2[19] = 46;
        nArray2[20] = 47;
        nArray2[21] = 58;
        nArray2[22] = 59;
        nArray2[23] = 60;
        nArray2[24] = 61;
        nArray2[25] = 62;
        nArray2[26] = 63;
        nArray2[27] = 91;
        nArray2[28] = 93;
        nArray2[29] = 123;
        nArray2[30] = 125;
        for (n2 = 0; n2 < ((int[])object).length; ++n2) {
            if (object[n2] <= 0) continue;
            HighLevelEncoder.CHAR_MAP[4][object[n2]] = n2;
        }
        SHIFT_TABLE = (int[][])Array.newInstance(Integer.TYPE, 6, 6);
        object = SHIFT_TABLE;
        int n3 = ((int[])object).length;
        for (n2 = 0; n2 < n3; ++n2) {
            Arrays.fill((int[])object[n2], -1);
        }
        HighLevelEncoder.SHIFT_TABLE[0][4] = 0;
        HighLevelEncoder.SHIFT_TABLE[1][4] = 0;
        HighLevelEncoder.SHIFT_TABLE[1][0] = 28;
        HighLevelEncoder.SHIFT_TABLE[3][4] = 0;
        HighLevelEncoder.SHIFT_TABLE[2][4] = 0;
        HighLevelEncoder.SHIFT_TABLE[2][0] = 15;
    }

    public HighLevelEncoder(byte[] byArray) {
        this.text = byArray;
    }

    private static Collection<State> simplifyStates(Iterable<State> object) {
        LinkedList<State> linkedList = new LinkedList<State>();
        object = object.iterator();
        block0: while (object.hasNext()) {
            State state = (State)object.next();
            boolean bl2 = true;
            Iterator iterator = linkedList.iterator();
            while (true) {
                State state2;
                block5: {
                    boolean bl3;
                    block4: {
                        bl3 = bl2;
                        if (!iterator.hasNext()) break block4;
                        state2 = (State)iterator.next();
                        if (!state2.isBetterThanOrEqualTo(state)) break block5;
                        bl3 = false;
                    }
                    if (!bl3) continue block0;
                    linkedList.add(state);
                    continue block0;
                }
                if (!state.isBetterThanOrEqualTo(state2)) continue;
                iterator.remove();
            }
        }
        return linkedList;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void updateStateForChar(State state, int n2, Collection<State> collection) {
        char c2 = (char)(this.text[n2] & 0xFF);
        boolean bl2 = CHAR_MAP[state.getMode()][c2] > 0;
        State state2 = null;
        for (int i2 = 0; i2 <= 4; ++i2) {
            int n3 = CHAR_MAP[i2][c2];
            State state3 = state2;
            if (n3 > 0) {
                State state4 = state2;
                if (state2 == null) {
                    state4 = state.endBinaryShift(n2);
                }
                if (!bl2 || i2 == state.getMode() || i2 == 2) {
                    collection.add(state4.latchAndAppend(i2, n3));
                }
                state3 = state4;
                if (!bl2) {
                    state3 = state4;
                    if (SHIFT_TABLE[state.getMode()][i2] >= 0) {
                        collection.add(state4.shiftAndAppend(i2, n3));
                        state3 = state4;
                    }
                }
            }
            state2 = state3;
        }
        if (state.getBinaryShiftByteCount() > 0 || CHAR_MAP[state.getMode()][c2] == 0) {
            collection.add(state.addBinaryShiftChar(n2));
        }
    }

    private static void updateStateForPair(State state, int n2, int n3, Collection<State> collection) {
        State state2 = state.endBinaryShift(n2);
        collection.add(state2.latchAndAppend(4, n3));
        if (state.getMode() != 4) {
            collection.add(state2.shiftAndAppend(4, n3));
        }
        if (n3 == 3 || n3 == 4) {
            collection.add(state2.latchAndAppend(2, 16 - n3).latchAndAppend(2, 1));
        }
        if (state.getBinaryShiftByteCount() > 0) {
            collection.add(state.addBinaryShiftChar(n2).addBinaryShiftChar(n2 + 1));
        }
    }

    private Collection<State> updateStateListForChar(Iterable<State> object, int n2) {
        LinkedList<State> linkedList = new LinkedList<State>();
        object = object.iterator();
        while (object.hasNext()) {
            this.updateStateForChar((State)object.next(), n2, linkedList);
        }
        return HighLevelEncoder.simplifyStates(linkedList);
    }

    private static Collection<State> updateStateListForPair(Iterable<State> object, int n2, int n3) {
        LinkedList<State> linkedList = new LinkedList<State>();
        object = object.iterator();
        while (object.hasNext()) {
            HighLevelEncoder.updateStateForPair((State)object.next(), n2, n3, linkedList);
        }
        return HighLevelEncoder.simplifyStates(linkedList);
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    public BitArray encode() {
        List<State> list = Collections.singletonList(State.INITIAL_STATE);
        int n2 = 0;
        void var3_2;
        while (n2 < this.text.length) {
            int n3 = n2 + 1 < this.text.length ? this.text[n2 + 1] : 0;
            switch (this.text[n2]) {
                default: {
                    n3 = 0;
                    break;
                }
                case 13: {
                    if (n3 == 10) {
                        n3 = 2;
                        break;
                    }
                    n3 = 0;
                    break;
                }
                case 46: {
                    if (n3 == 32) {
                        n3 = 3;
                        break;
                    }
                    n3 = 0;
                    break;
                }
                case 44: {
                    if (n3 == 32) {
                        n3 = 4;
                        break;
                    }
                    n3 = 0;
                    break;
                }
                case 58: {
                    n3 = n3 == 32 ? 5 : 0;
                }
            }
            if (n3 > 0) {
                Collection<State> collection = HighLevelEncoder.updateStateListForPair((Iterable<State>)var3_2, n2, n3);
                ++n2;
            } else {
                Collection<State> collection = this.updateStateListForChar((Iterable<State>)var3_2, n2);
            }
            ++n2;
        }
        return Collections.min(var3_2, new Comparator<State>(){

            @Override
            public int compare(State state, State state2) {
                return state.getBitCount() - state2.getBitCount();
            }
        }).toBitArray(this.text);
    }
}

