/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.client.result;

import com.google.zxing.Result;
import com.google.zxing.client.result.ResultParser;
import com.google.zxing.client.result.WifiParsedResult;

public final class WifiResultParser
extends ResultParser {
    /*
     * Enabled aggressive block sorting
     */
    @Override
    public WifiParsedResult parse(Result object) {
        String string2;
        String string3 = WifiResultParser.getMassagedText((Result)object);
        if (!string3.startsWith("WIFI:") || (string2 = WifiResultParser.matchSinglePrefixedField("S:", string3, ';', false)) == null || string2.isEmpty()) {
            return null;
        }
        String string4 = WifiResultParser.matchSinglePrefixedField("P:", string3, ';', false);
        String string5 = WifiResultParser.matchSinglePrefixedField("T:", string3, ';', false);
        object = string5;
        if (string5 == null) {
            object = "nopass";
        }
        return new WifiParsedResult((String)object, string2, string4, Boolean.parseBoolean(WifiResultParser.matchSinglePrefixedField("H:", string3, ';', false)));
    }
}

