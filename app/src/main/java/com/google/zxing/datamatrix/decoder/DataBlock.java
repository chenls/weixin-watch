/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.datamatrix.decoder;

import com.google.zxing.datamatrix.decoder.Version;

final class DataBlock {
    private final byte[] codewords;
    private final int numDataCodewords;

    private DataBlock(int n2, byte[] byArray) {
        this.numDataCodewords = n2;
        this.codewords = byArray;
    }

    /*
     * Enabled aggressive block sorting
     */
    static DataBlock[] getDataBlocks(byte[] byArray, Version version) {
        int n2;
        int n3;
        int n4;
        Version.ECBlocks eCBlocks = version.getECBlocks();
        int n5 = 0;
        Version.ECB[] eCBArray = eCBlocks.getECBlocks();
        int n6 = eCBArray.length;
        for (n4 = 0; n4 < n6; n5 += eCBArray[n4].getCount(), ++n4) {
        }
        DataBlock[] dataBlockArray = new DataBlock[n5];
        n5 = 0;
        for (Version.ECB eCB : eCBArray) {
            for (n6 = 0; n6 < eCB.getCount(); ++n6, ++n5) {
                n3 = eCB.getDataCodewords();
                dataBlockArray[n5] = new DataBlock(n3, new byte[eCBlocks.getECCodewords() + n3]);
            }
        }
        int n7 = dataBlockArray[0].codewords.length - eCBlocks.getECCodewords();
        n4 = 0;
        for (n6 = 0; n6 < n7 - 1; ++n6) {
            for (n2 = 0; n2 < n5; ++n2, ++n4) {
                dataBlockArray[n2].codewords[n6] = byArray[n4];
            }
        }
        n3 = version.getVersionNumber() == 24 ? 1 : 0;
        n6 = n3 != 0 ? 8 : n5;
        for (n2 = 0; n2 < n6; ++n2, ++n4) {
            dataBlockArray[n2].codewords[n7 - 1] = byArray[n4];
        }
        int n8 = dataBlockArray[0].codewords.length;
        n2 = n7;
        n6 = n4;
        n4 = n2;
        while (true) {
            if (n4 < n8) {
            } else {
                if (n6 != byArray.length) {
                    throw new IllegalArgumentException();
                }
                return dataBlockArray;
            }
            for (n2 = 0; n2 < n5; ++n2, ++n6) {
                n7 = n3 != 0 ? (n2 + 8) % n5 : n2;
                int n9 = n3 != 0 && n7 > 7 ? n4 - 1 : n4;
                dataBlockArray[n7].codewords[n9] = byArray[n6];
            }
            ++n4;
        }
    }

    byte[] getCodewords() {
        return this.codewords;
    }

    int getNumDataCodewords() {
        return this.numDataCodewords;
    }
}

