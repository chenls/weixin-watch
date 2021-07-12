/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.client.result;

import com.google.zxing.client.result.ParsedResult;
import com.google.zxing.client.result.ParsedResultType;

public final class TelParsedResult
extends ParsedResult {
    private final String number;
    private final String telURI;
    private final String title;

    public TelParsedResult(String string2, String string3, String string4) {
        super(ParsedResultType.TEL);
        this.number = string2;
        this.telURI = string3;
        this.title = string4;
    }

    @Override
    public String getDisplayResult() {
        StringBuilder stringBuilder = new StringBuilder(20);
        TelParsedResult.maybeAppend(this.number, stringBuilder);
        TelParsedResult.maybeAppend(this.title, stringBuilder);
        return stringBuilder.toString();
    }

    public String getNumber() {
        return this.number;
    }

    public String getTelURI() {
        return this.telURI;
    }

    public String getTitle() {
        return this.title;
    }
}

