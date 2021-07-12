/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.client.result;

import com.google.zxing.Result;
import com.google.zxing.client.result.ResultParser;
import com.google.zxing.client.result.URIParsedResult;

public final class URLTOResultParser
extends ResultParser {
    /*
     * Enabled aggressive block sorting
     */
    @Override
    public URIParsedResult parse(Result object) {
        Object var3_2 = null;
        String string2 = URLTOResultParser.getMassagedText((Result)object);
        if (!string2.startsWith("urlto:") && !string2.startsWith("URLTO:")) {
            return null;
        }
        int n2 = string2.indexOf(58, 6);
        if (n2 < 0) return null;
        if (n2 <= 6) {
            object = var3_2;
            return new URIParsedResult(string2.substring(n2 + 1), (String)object);
        }
        object = string2.substring(6, n2);
        return new URIParsedResult(string2.substring(n2 + 1), (String)object);
    }
}

