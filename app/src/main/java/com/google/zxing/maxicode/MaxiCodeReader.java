/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.maxicode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.Reader;
import com.google.zxing.Result;
import com.google.zxing.ResultMetadataType;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.DecoderResult;
import com.google.zxing.maxicode.decoder.Decoder;
import java.util.Map;

public final class MaxiCodeReader
implements Reader {
    private static final int MATRIX_HEIGHT = 33;
    private static final int MATRIX_WIDTH = 30;
    private static final ResultPoint[] NO_POINTS = new ResultPoint[0];
    private final Decoder decoder = new Decoder();

    private static BitMatrix extractPureBits(BitMatrix bitMatrix) throws NotFoundException {
        Object object = bitMatrix.getEnclosingRectangle();
        if (object == null) {
            throw NotFoundException.getNotFoundInstance();
        }
        int n2 = object[0];
        int n3 = object[1];
        int n4 = object[2];
        int n5 = object[3];
        object = new BitMatrix(30, 33);
        for (int i2 = 0; i2 < 33; ++i2) {
            int n6 = (i2 * n5 + n5 / 2) / 33;
            for (int i3 = 0; i3 < 30; ++i3) {
                if (!bitMatrix.get(n2 + (i3 * n4 + n4 / 2 + (i2 & 1) * n4 / 2) / 30, n3 + n6)) continue;
                object.set(i3, i2);
            }
        }
        return object;
    }

    @Override
    public Result decode(BinaryBitmap binaryBitmap) throws NotFoundException, ChecksumException, FormatException {
        return this.decode(binaryBitmap, null);
    }

    @Override
    public Result decode(BinaryBitmap object, Map<DecodeHintType, ?> object2) throws NotFoundException, ChecksumException, FormatException {
        if (object2 != null && object2.containsKey((Object)DecodeHintType.PURE_BARCODE)) {
            object = MaxiCodeReader.extractPureBits(((BinaryBitmap)object).getBlackMatrix());
            object = this.decoder.decode((BitMatrix)object, (Map<DecodeHintType, ?>)object2);
            object2 = NO_POINTS;
            object2 = new Result(((DecoderResult)object).getText(), ((DecoderResult)object).getRawBytes(), (ResultPoint[])object2, BarcodeFormat.MAXICODE);
            if ((object = ((DecoderResult)object).getECLevel()) != null) {
                ((Result)object2).putMetadata(ResultMetadataType.ERROR_CORRECTION_LEVEL, object);
            }
            return object2;
        }
        throw NotFoundException.getNotFoundInstance();
    }

    @Override
    public void reset() {
    }
}

