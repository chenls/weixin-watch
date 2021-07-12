/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.client.result;

import com.google.zxing.client.result.ParsedResult;
import com.google.zxing.client.result.ParsedResultType;

public final class TextParsedResult
extends ParsedResult {
    private final String language;
    private final String text;

    public TextParsedResult(String string2, String string3) {
        super(ParsedResultType.TEXT);
        this.text = string2;
        this.language = string3;
    }

    @Override
    public String getDisplayResult() {
        return this.text;
    }

    public String getLanguage() {
        return this.language;
    }

    public String getText() {
        return this.text;
    }
}

