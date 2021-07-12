/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.client.result;

import com.google.zxing.Result;
import com.google.zxing.client.result.AbstractDoCoMoResultParser;
import com.google.zxing.client.result.URIParsedResult;
import com.google.zxing.client.result.URIResultParser;

public final class BookmarkDoCoMoResultParser
extends AbstractDoCoMoResultParser {
    /*
     * Enabled aggressive block sorting
     */
    @Override
    public URIParsedResult parse(Result object) {
        String string2;
        block3: {
            block2: {
                String string3 = ((Result)object).getText();
                if (!string3.startsWith("MEBKM:")) break block2;
                object = BookmarkDoCoMoResultParser.matchSingleDoCoMoPrefixedField("TITLE:", string3, true);
                String[] stringArray = BookmarkDoCoMoResultParser.matchDoCoMoPrefixedField("URL:", string3, true);
                if (stringArray != null && URIResultParser.isBasicallyValidURI(string2 = stringArray[0])) break block3;
            }
            return null;
        }
        return new URIParsedResult(string2, (String)object);
    }
}

