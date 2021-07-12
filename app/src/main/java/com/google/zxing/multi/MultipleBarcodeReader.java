/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.multi;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import java.util.Map;

public interface MultipleBarcodeReader {
    public Result[] decodeMultiple(BinaryBitmap var1) throws NotFoundException;

    public Result[] decodeMultiple(BinaryBitmap var1, Map<DecodeHintType, ?> var2) throws NotFoundException;
}

