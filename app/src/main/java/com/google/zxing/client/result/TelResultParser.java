/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.client.result;

import com.google.zxing.Result;
import com.google.zxing.client.result.ResultParser;
import com.google.zxing.client.result.TelParsedResult;

public final class TelResultParser
extends ResultParser {
    /*
     * Enabled aggressive block sorting
     */
    @Override
    public TelParsedResult parse(Result object) {
        String string2 = TelResultParser.getMassagedText((Result)object);
        if (!string2.startsWith("tel:") && !string2.startsWith("TEL:")) {
            return null;
        }
        object = string2.startsWith("TEL:") ? "tel:" + string2.substring(4) : string2;
        int n2 = string2.indexOf(63, 4);
        if (n2 < 0) {
            string2 = string2.substring(4);
            return new TelParsedResult(string2, (String)object, null);
        }
        string2 = string2.substring(4, n2);
        return new TelParsedResult(string2, (String)object, null);
    }
}

