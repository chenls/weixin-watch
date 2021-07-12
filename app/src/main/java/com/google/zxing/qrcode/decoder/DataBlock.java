/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.qrcode.decoder;

import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.decoder.Version;

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
    static DataBlock[] getDataBlocks(byte[] byArray, Version object, ErrorCorrectionLevel eCBArray) {
        int n2;
        int n3;
        int n4;
        int n5;
        if (byArray.length != ((Version)object).getTotalCodewords()) {
            throw new IllegalArgumentException();
        }
        object = ((Version)object).getECBlocksForLevel((ErrorCorrectionLevel)eCBArray);
        int n6 = 0;
        eCBArray = ((Version.ECBlocks)object).getECBlocks();
        int n7 = eCBArray.length;
        for (n5 = 0; n5 < n7; n6 += eCBArray[n5].getCount(), ++n5) {
        }
        DataBlock[] dataBlockArray = new DataBlock[n6];
        n7 = 0;
        for (Version.ECB eCB : eCBArray) {
            for (n6 = 0; n6 < eCB.getCount(); ++n6, ++n7) {
                n4 = eCB.getDataCodewords();
                dataBlockArray[n7] = new DataBlock(n4, new byte[((Version.ECBlocks)object).getECCodewordsPerBlock() + n4]);
            }
        }
        n6 = dataBlockArray[0].codewords.length;
        n5 = dataBlockArray.length - 1;
        while (true) {
            if (n5 < 0 || dataBlockArray[n5].codewords.length == n6) {
                n3 = n5 + 1;
                n4 = n6 - ((Version.ECBlocks)object).getECCodewordsPerBlock();
                n5 = 0;
                break;
            }
            --n5;
        }
        for (n6 = 0; n6 < n4; ++n6) {
            for (n2 = 0; n2 < n7; ++n2, ++n5) {
                dataBlockArray[n2].codewords[n6] = byArray[n5];
            }
        }
        for (n6 = n3; n6 < n7; ++n6, ++n5) {
            dataBlockArray[n6].codewords[n4] = byArray[n5];
        }
        int n8 = dataBlockArray[0].codewords.length;
        n2 = n4;
        n6 = n5;
        n5 = n2;
        while (n5 < n8) {
            for (n2 = 0; n2 < n7; ++n2, ++n6) {
                n4 = n2 < n3 ? n5 : n5 + 1;
                dataBlockArray[n2].codewords[n4] = byArray[n6];
            }
            ++n5;
        }
        return dataBlockArray;
    }

    byte[] getCodewords() {
        return this.codewords;
    }

    int getNumDataCodewords() {
        return this.numDataCodewords;
    }
}

