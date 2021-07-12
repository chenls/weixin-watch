/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.client.result;

import com.google.zxing.Result;
import com.google.zxing.client.result.ResultParser;
import com.google.zxing.client.result.URIParsedResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class URIResultParser
extends ResultParser {
    private static final Pattern URL_WITHOUT_PROTOCOL_PATTERN;
    private static final Pattern URL_WITH_PROTOCOL_PATTERN;

    static {
        URL_WITH_PROTOCOL_PATTERN = Pattern.compile("[a-zA-Z][a-zA-Z0-9+-.]+:");
        URL_WITHOUT_PROTOCOL_PATTERN = Pattern.compile("([a-zA-Z0-9\\-]+\\.)+[a-zA-Z]{2,}(:\\d{1,5})?(/|\\?|$)");
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    static boolean isBasicallyValidURI(String object) {
        boolean bl2;
        boolean bl3 = true;
        if (((String)object).contains(" ")) {
            return false;
        }
        Matcher matcher = URL_WITH_PROTOCOL_PATTERN.matcher((CharSequence)object);
        if (matcher.find()) {
            bl2 = bl3;
            if (matcher.start() == 0) return bl2;
        }
        if (!((Matcher)(object = URL_WITHOUT_PROTOCOL_PATTERN.matcher((CharSequence)object))).find()) return false;
        bl2 = bl3;
        if (((Matcher)object).start() == 0) return bl2;
        return false;
    }

    @Override
    public URIParsedResult parse(Result object) {
        if (((String)(object = URIResultParser.getMassagedText((Result)object))).startsWith("URL:") || ((String)object).startsWith("URI:")) {
            return new URIParsedResult(((String)object).substring(4).trim(), null);
        }
        if (URIResultParser.isBasicallyValidURI((String)(object = ((String)object).trim()))) {
            return new URIParsedResult((String)object, null);
        }
        return null;
    }
}

