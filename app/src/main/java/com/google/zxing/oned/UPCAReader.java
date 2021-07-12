/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.common.BitArray;
import com.google.zxing.oned.EAN13Reader;
import com.google.zxing.oned.UPCEANReader;
import java.util.Map;

public final class UPCAReader
extends UPCEANReader {
    private final UPCEANReader ean13Reader = new EAN13Reader();

    private static Result maybeReturnResult(Result result) throws FormatException {
        String string2 = result.getText();
        if (string2.charAt(0) == '0') {
            return new Result(string2.substring(1), null, result.getResultPoints(), BarcodeFormat.UPC_A);
        }
        throw FormatException.getFormatInstance();
    }

    @Override
    public Result decode(BinaryBitmap binaryBitmap) throws NotFoundException, FormatException {
        return UPCAReader.maybeReturnResult(this.ean13Reader.decode(binaryBitmap));
    }

    @Override
    public Result decode(BinaryBitmap binaryBitmap, Map<DecodeHintType, ?> map) throws NotFoundException, FormatException {
        return UPCAReader.maybeReturnResult(this.ean13Reader.decode(binaryBitmap, map));
    }

    @Override
    protected int decodeMiddle(BitArray bitArray, int[] nArray, StringBuilder stringBuilder) throws NotFoundException {
        return this.ean13Reader.decodeMiddle(bitArray, nArray, stringBuilder);
    }

    @Override
    public Result decodeRow(int n2, BitArray bitArray, Map<DecodeHintType, ?> map) throws NotFoundException, FormatException, ChecksumException {
        return UPCAReader.maybeReturnResult(this.ean13Reader.decodeRow(n2, bitArray, map));
    }

    @Override
    public Result decodeRow(int n2, BitArray bitArray, int[] nArray, Map<DecodeHintType, ?> map) throws NotFoundException, FormatException, ChecksumException {
        return UPCAReader.maybeReturnResult(this.ean13Reader.decodeRow(n2, bitArray, nArray, map));
    }

    @Override
    BarcodeFormat getBarcodeFormat() {
        return BarcodeFormat.UPC_A;
    }
}

