/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.qrcode.decoder;

import com.google.zxing.qrcode.decoder.Version;

public enum Mode {
    TERMINATOR(new int[]{0, 0, 0}, 0),
    NUMERIC(new int[]{10, 12, 14}, 1),
    ALPHANUMERIC(new int[]{9, 11, 13}, 2),
    STRUCTURED_APPEND(new int[]{0, 0, 0}, 3),
    BYTE(new int[]{8, 16, 16}, 4),
    ECI(new int[]{0, 0, 0}, 7),
    KANJI(new int[]{8, 10, 12}, 8),
    FNC1_FIRST_POSITION(new int[]{0, 0, 0}, 5),
    FNC1_SECOND_POSITION(new int[]{0, 0, 0}, 9),
    HANZI(new int[]{8, 10, 12}, 13);

    private final int bits;
    private final int[] characterCountBitsForVersions;

    private Mode(int[] nArray, int n3) {
        this.characterCountBitsForVersions = nArray;
        this.bits = n3;
    }

    public static Mode forBits(int n2) {
        switch (n2) {
            default: {
                throw new IllegalArgumentException();
            }
            case 0: {
                return TERMINATOR;
            }
            case 1: {
                return NUMERIC;
            }
            case 2: {
                return ALPHANUMERIC;
            }
            case 3: {
                return STRUCTURED_APPEND;
            }
            case 4: {
                return BYTE;
            }
            case 5: {
                return FNC1_FIRST_POSITION;
            }
            case 7: {
                return ECI;
            }
            case 8: {
                return KANJI;
            }
            case 9: {
                return FNC1_SECOND_POSITION;
            }
            case 13: 
        }
        return HANZI;
    }

    public int getBits() {
        return this.bits;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public int getCharacterCountBits(Version version) {
        int n2 = version.getVersionNumber();
        if (n2 <= 9) {
            n2 = 0;
            return this.characterCountBitsForVersions[n2];
        }
        if (n2 <= 26) {
            n2 = 1;
            return this.characterCountBitsForVersions[n2];
        }
        n2 = 2;
        return this.characterCountBitsForVersions[n2];
    }
}

