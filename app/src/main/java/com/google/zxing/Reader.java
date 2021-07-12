/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import java.util.Map;

public interface Reader {
    public Result decode(BinaryBitmap var1) throws NotFoundException, ChecksumException, FormatException;

    public Result decode(BinaryBitmap var1, Map<DecodeHintType, ?> var2) throws NotFoundException, ChecksumException, FormatException;

    public void reset();
}

