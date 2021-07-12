/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.client.result;

import com.google.zxing.client.result.ParsedResult;
import com.google.zxing.client.result.ParsedResultType;
import com.google.zxing.client.result.ResultParser;
import java.util.regex.Pattern;

public final class URIParsedResult
extends ParsedResult {
    private static final Pattern USER_IN_HOST = Pattern.compile(":/*([^/@]+)@[^/]+");
    private final String title;
    private final String uri;

    public URIParsedResult(String string2, String string3) {
        super(ParsedResultType.URI);
        this.uri = URIParsedResult.massageURI(string2);
        this.title = string3;
    }

    private static boolean isColonFollowedByPortNumber(String string2, int n2) {
        int n3;
        int n4 = n2 + 1;
        n2 = n3 = string2.indexOf(47, n4);
        if (n3 < 0) {
            n2 = string2.length();
        }
        return ResultParser.isSubstringOfDigits(string2, n4, n2 - n4);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static String massageURI(String string2) {
        String string3 = string2.trim();
        int n2 = string3.indexOf(58);
        if (n2 < 0) {
            return "http://" + string3;
        }
        string2 = string3;
        if (!URIParsedResult.isColonFollowedByPortNumber(string3, n2)) return string2;
        return "http://" + string3;
    }

    @Override
    public String getDisplayResult() {
        StringBuilder stringBuilder = new StringBuilder(30);
        URIParsedResult.maybeAppend(this.title, stringBuilder);
        URIParsedResult.maybeAppend(this.uri, stringBuilder);
        return stringBuilder.toString();
    }

    public String getTitle() {
        return this.title;
    }

    public String getURI() {
        return this.uri;
    }

    public boolean isPossiblyMaliciousURI() {
        return USER_IN_HOST.matcher(this.uri).find();
    }
}

