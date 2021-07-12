/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.maxicode.decoder;

import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.DecoderResult;
import com.google.zxing.common.reedsolomon.GenericGF;
import com.google.zxing.common.reedsolomon.ReedSolomonDecoder;
import com.google.zxing.common.reedsolomon.ReedSolomonException;
import com.google.zxing.maxicode.decoder.BitMatrixParser;
import com.google.zxing.maxicode.decoder.DecodedBitStreamParser;
import java.util.Map;

public final class Decoder {
    private static final int ALL = 0;
    private static final int EVEN = 1;
    private static final int ODD = 2;
    private final ReedSolomonDecoder rsDecoder = new ReedSolomonDecoder(GenericGF.MAXICODE_FIELD_64);

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void correctErrors(byte[] byArray, int n2, int n3, int n4, int n5) throws ChecksumException {
        int n6 = n3 + n4;
        int n7 = n5 == 0 ? 1 : 2;
        int[] nArray = new int[n6 / n7];
        for (int i2 = 0; i2 < n6; ++i2) {
            if (n5 != 0 && i2 % 2 != n5 - 1) continue;
            nArray[i2 / n7] = byArray[i2 + n2] & 0xFF;
        }
        try {
            this.rsDecoder.decode(nArray, n4 / n7);
            n4 = 0;
        }
        catch (ReedSolomonException reedSolomonException) {
            throw ChecksumException.getChecksumInstance();
        }
        while (n4 < n3) {
            if (n5 == 0 || n4 % 2 == n5 - 1) {
                byArray[n4 + n2] = (byte)nArray[n4 / n7];
            }
            ++n4;
        }
        return;
    }

    public DecoderResult decode(BitMatrix bitMatrix) throws ChecksumException, FormatException {
        return this.decode(bitMatrix, null);
    }

    /*
     * Enabled aggressive block sorting
     */
    public DecoderResult decode(BitMatrix object, Map<DecodeHintType, ?> object2) throws FormatException, ChecksumException {
        object2 = new BitMatrixParser((BitMatrix)object).readCodewords();
        this.correctErrors((byte[])object2, 0, 10, 10, 0);
        int n2 = object2[0] & 0xF;
        switch (n2) {
            default: {
                throw FormatException.getFormatInstance();
            }
            case 2: 
            case 3: 
            case 4: {
                this.correctErrors((byte[])object2, 20, 84, 40, 1);
                this.correctErrors((byte[])object2, 20, 84, 40, 2);
                object = new byte[94];
                break;
            }
            case 5: {
                this.correctErrors((byte[])object2, 20, 68, 56, 1);
                this.correctErrors((byte[])object2, 20, 68, 56, 2);
                object = new byte[78];
            }
        }
        System.arraycopy(object2, 0, object, 0, 10);
        System.arraycopy(object2, 20, object, 10, ((Object)object).length - 10);
        return DecodedBitStreamParser.decode((byte[])object, n2);
    }
}

