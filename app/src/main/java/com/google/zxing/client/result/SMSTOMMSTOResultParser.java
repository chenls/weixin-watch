/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.client.result;

import com.google.zxing.Result;
import com.google.zxing.client.result.ResultParser;
import com.google.zxing.client.result.SMSParsedResult;

public final class SMSTOMMSTOResultParser
extends ResultParser {
    @Override
    public SMSParsedResult parse(Result object) {
        if (!(((String)(object = SMSTOMMSTOResultParser.getMassagedText((Result)object))).startsWith("smsto:") || ((String)object).startsWith("SMSTO:") || ((String)object).startsWith("mmsto:") || ((String)object).startsWith("MMSTO:"))) {
            return null;
        }
        String string2 = ((String)object).substring(6);
        String string3 = null;
        int n2 = string2.indexOf(58);
        object = string2;
        if (n2 >= 0) {
            string3 = string2.substring(n2 + 1);
            object = string2.substring(0, n2);
        }
        return new SMSParsedResult((String)object, null, null, string3);
    }
}

