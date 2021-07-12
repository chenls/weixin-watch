/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.datamatrix.decoder;

import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.DecoderResult;
import com.google.zxing.common.reedsolomon.GenericGF;
import com.google.zxing.common.reedsolomon.ReedSolomonDecoder;
import com.google.zxing.common.reedsolomon.ReedSolomonException;
import com.google.zxing.datamatrix.decoder.BitMatrixParser;
import com.google.zxing.datamatrix.decoder.DataBlock;
import com.google.zxing.datamatrix.decoder.DecodedBitStreamParser;
import com.google.zxing.datamatrix.decoder.Version;

public final class Decoder {
    private final ReedSolomonDecoder rsDecoder = new ReedSolomonDecoder(GenericGF.DATA_MATRIX_FIELD_256);

    private void correctErrors(byte[] byArray, int n2) throws ChecksumException {
        int n3;
        int n4 = byArray.length;
        int[] nArray = new int[n4];
        for (n3 = 0; n3 < n4; ++n3) {
            nArray[n3] = byArray[n3] & 0xFF;
        }
        n3 = byArray.length;
        try {
            this.rsDecoder.decode(nArray, n3 - n2);
        }
        catch (ReedSolomonException reedSolomonException) {
            throw ChecksumException.getChecksumInstance();
        }
        for (n3 = 0; n3 < n2; ++n3) {
            byArray[n3] = (byte)nArray[n3];
        }
    }

    public DecoderResult decode(BitMatrix dataBlockArray) throws FormatException, ChecksumException {
        int n2;
        dataBlockArray = new BitMatrixParser((BitMatrix)dataBlockArray);
        Object object = dataBlockArray.getVersion();
        dataBlockArray = DataBlock.getDataBlocks(dataBlockArray.readCodewords(), (Version)object);
        int n3 = dataBlockArray.length;
        int n4 = 0;
        int n5 = dataBlockArray.length;
        for (n2 = 0; n2 < n5; ++n2) {
            n4 += dataBlockArray[n2].getNumDataCodewords();
        }
        object = new byte[n4];
        for (n2 = 0; n2 < n3; ++n2) {
            DataBlock dataBlock = dataBlockArray[n2];
            byte[] byArray = dataBlock.getCodewords();
            n5 = dataBlock.getNumDataCodewords();
            this.correctErrors(byArray, n5);
            for (n4 = 0; n4 < n5; ++n4) {
                object[n4 * n3 + n2] = byArray[n4];
            }
        }
        return DecodedBitStreamParser.decode((byte[])object);
    }

    public DecoderResult decode(boolean[][] blArray) throws FormatException, ChecksumException {
        int n2 = blArray.length;
        BitMatrix bitMatrix = new BitMatrix(n2);
        for (int i2 = 0; i2 < n2; ++i2) {
            for (int i3 = 0; i3 < n2; ++i3) {
                if (!blArray[i2][i3]) continue;
                bitMatrix.set(i3, i2);
            }
        }
        return this.decode(bitMatrix);
    }
}

