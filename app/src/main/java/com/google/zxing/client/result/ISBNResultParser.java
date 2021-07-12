/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.client.result;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.google.zxing.client.result.ISBNParsedResult;
import com.google.zxing.client.result.ResultParser;

public final class ISBNResultParser
extends ResultParser {
    /*
     * Enabled aggressive block sorting
     */
    @Override
    public ISBNParsedResult parse(Result object) {
        if (((Result)object).getBarcodeFormat() != BarcodeFormat.EAN_13 || ((String)(object = ISBNResultParser.getMassagedText((Result)object))).length() != 13 || !((String)object).startsWith("978") && !((String)object).startsWith("979")) {
            return null;
        }
        return new ISBNParsedResult((String)object);
    }
}

