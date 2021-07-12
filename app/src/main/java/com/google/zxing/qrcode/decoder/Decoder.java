/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.qrcode.decoder;

import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.DecoderResult;
import com.google.zxing.common.reedsolomon.GenericGF;
import com.google.zxing.common.reedsolomon.ReedSolomonDecoder;
import com.google.zxing.common.reedsolomon.ReedSolomonException;
import com.google.zxing.qrcode.decoder.BitMatrixParser;
import com.google.zxing.qrcode.decoder.DataBlock;
import com.google.zxing.qrcode.decoder.DecodedBitStreamParser;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.decoder.QRCodeDecoderMetaData;
import com.google.zxing.qrcode.decoder.Version;
import java.util.Map;

public final class Decoder {
    private final ReedSolomonDecoder rsDecoder = new ReedSolomonDecoder(GenericGF.QR_CODE_FIELD_256);

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

    private DecoderResult decode(BitMatrixParser dataBlockArray, Map<DecodeHintType, ?> map) throws FormatException, ChecksumException {
        int n2;
        Version version = dataBlockArray.readVersion();
        ErrorCorrectionLevel errorCorrectionLevel = dataBlockArray.readFormatInformation().getErrorCorrectionLevel();
        dataBlockArray = DataBlock.getDataBlocks(dataBlockArray.readCodewords(), version, errorCorrectionLevel);
        int n3 = 0;
        int n4 = dataBlockArray.length;
        for (n2 = 0; n2 < n4; ++n2) {
            n3 += dataBlockArray[n2].getNumDataCodewords();
        }
        byte[] byArray = new byte[n3];
        n2 = 0;
        for (DataBlock dataBlock : dataBlockArray) {
            byte[] byArray2 = dataBlock.getCodewords();
            int n5 = dataBlock.getNumDataCodewords();
            this.correctErrors(byArray2, n5);
            n4 = 0;
            while (n4 < n5) {
                byArray[n2] = byArray2[n4];
                ++n4;
                ++n2;
            }
        }
        return DecodedBitStreamParser.decode(byArray, version, errorCorrectionLevel, map);
    }

    public DecoderResult decode(BitMatrix bitMatrix) throws ChecksumException, FormatException {
        return this.decode(bitMatrix, null);
    }

    /*
     * Unable to fully structure code
     */
    public DecoderResult decode(BitMatrix var1_1, Map<DecodeHintType, ?> var2_3) throws FormatException, ChecksumException {
        block10: {
            var4_7 = new BitMatrixParser(var1_1);
            var3_8 = null;
            var1_1 = null;
            try {
                var5_10 = this.decode(var4_7, (Map<DecodeHintType, ?>)var2_3);
                return var5_10;
            }
            catch (FormatException var3_9) lbl-1000:
            // 2 sources

            {
                while (true) {
                    try {
                        var4_7.remask();
                        var4_7.setMirror(true);
                        var4_7.readVersion();
                        var4_7.readFormatInformation();
                        var4_7.mirror();
                        var2_3 = this.decode(var4_7, (Map<DecodeHintType, ?>)var2_3);
                        var2_3.setOther(new QRCodeDecoderMetaData(true));
                        return var2_3;
                    }
                    catch (FormatException var2_4) lbl-1000:
                    // 2 sources

                    {
                        while (true) {
                            if (var3_8 != null) {
                                throw var3_8;
                            }
                            break block10;
                            break;
                        }
                    }
                    break;
                }
            }
            catch (ChecksumException var1_2) {
                ** continue;
            }
        }
        if (var1_1 != null) {
            throw var1_1;
        }
        throw var2_5;
        {
            catch (ChecksumException var2_6) {
                ** continue;
            }
        }
    }

    public DecoderResult decode(boolean[][] blArray) throws ChecksumException, FormatException {
        return this.decode(blArray, null);
    }

    public DecoderResult decode(boolean[][] blArray, Map<DecodeHintType, ?> map) throws ChecksumException, FormatException {
        int n2 = blArray.length;
        BitMatrix bitMatrix = new BitMatrix(n2);
        for (int i2 = 0; i2 < n2; ++i2) {
            for (int i3 = 0; i3 < n2; ++i3) {
                if (!blArray[i2][i3]) continue;
                bitMatrix.set(i3, i2);
            }
        }
        return this.decode(bitMatrix, map);
    }
}

