/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.client.result;

import com.google.zxing.client.result.ParsedResult;
import com.google.zxing.client.result.ParsedResultType;

public final class ISBNParsedResult
extends ParsedResult {
    private final String isbn;

    ISBNParsedResult(String string2) {
        super(ParsedResultType.ISBN);
        this.isbn = string2;
    }

    @Override
    public String getDisplayResult() {
        return this.isbn;
    }

    public String getISBN() {
        return this.isbn;
    }
}

