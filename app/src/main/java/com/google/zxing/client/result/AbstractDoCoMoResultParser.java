/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.client.result;

import com.google.zxing.client.result.ResultParser;

abstract class AbstractDoCoMoResultParser
extends ResultParser {
    AbstractDoCoMoResultParser() {
    }

    static String[] matchDoCoMoPrefixedField(String string2, String string3, boolean bl2) {
        return AbstractDoCoMoResultParser.matchPrefixedField(string2, string3, ';', bl2);
    }

    static String matchSingleDoCoMoPrefixedField(String string2, String string3, boolean bl2) {
        return AbstractDoCoMoResultParser.matchSinglePrefixedField(string2, string3, ';', bl2);
    }
}

