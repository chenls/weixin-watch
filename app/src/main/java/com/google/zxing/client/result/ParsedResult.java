/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.client.result;

import com.google.zxing.client.result.ParsedResultType;

public abstract class ParsedResult {
    private final ParsedResultType type;

    protected ParsedResult(ParsedResultType parsedResultType) {
        this.type = parsedResultType;
    }

    public static void maybeAppend(String string2, StringBuilder stringBuilder) {
        if (string2 != null && !string2.isEmpty()) {
            if (stringBuilder.length() > 0) {
                stringBuilder.append('\n');
            }
            stringBuilder.append(string2);
        }
    }

    public static void maybeAppend(String[] stringArray, StringBuilder stringBuilder) {
        if (stringArray != null) {
            int n2 = stringArray.length;
            for (int i2 = 0; i2 < n2; ++i2) {
                ParsedResult.maybeAppend(stringArray[i2], stringBuilder);
            }
        }
    }

    public abstract String getDisplayResult();

    public final ParsedResultType getType() {
        return this.type;
    }

    public final String toString() {
        return this.getDisplayResult();
    }
}

