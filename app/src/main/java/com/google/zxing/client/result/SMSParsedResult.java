/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.client.result;

import com.google.zxing.client.result.ParsedResult;
import com.google.zxing.client.result.ParsedResultType;

public final class SMSParsedResult
extends ParsedResult {
    private final String body;
    private final String[] numbers;
    private final String subject;
    private final String[] vias;

    public SMSParsedResult(String string2, String string3, String string4, String string5) {
        super(ParsedResultType.SMS);
        this.numbers = new String[]{string2};
        this.vias = new String[]{string3};
        this.subject = string4;
        this.body = string5;
    }

    public SMSParsedResult(String[] stringArray, String[] stringArray2, String string2, String string3) {
        super(ParsedResultType.SMS);
        this.numbers = stringArray;
        this.vias = stringArray2;
        this.subject = string2;
        this.body = string3;
    }

    public String getBody() {
        return this.body;
    }

    @Override
    public String getDisplayResult() {
        StringBuilder stringBuilder = new StringBuilder(100);
        SMSParsedResult.maybeAppend(this.numbers, stringBuilder);
        SMSParsedResult.maybeAppend(this.subject, stringBuilder);
        SMSParsedResult.maybeAppend(this.body, stringBuilder);
        return stringBuilder.toString();
    }

    public String[] getNumbers() {
        return this.numbers;
    }

    /*
     * Enabled aggressive block sorting
     */
    public String getSMSURI() {
        int n2;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("sms:");
        boolean bl2 = true;
        for (n2 = 0; n2 < this.numbers.length; ++n2) {
            if (bl2) {
                bl2 = false;
            } else {
                stringBuilder.append(',');
            }
            stringBuilder.append(this.numbers[n2]);
            if (this.vias == null || this.vias[n2] == null) continue;
            stringBuilder.append(";via=");
            stringBuilder.append(this.vias[n2]);
        }
        n2 = this.body != null ? 1 : 0;
        bl2 = this.subject != null;
        if (n2 != 0 || bl2) {
            stringBuilder.append('?');
            if (n2 != 0) {
                stringBuilder.append("body=");
                stringBuilder.append(this.body);
            }
            if (bl2) {
                if (n2 != 0) {
                    stringBuilder.append('&');
                }
                stringBuilder.append("subject=");
                stringBuilder.append(this.subject);
            }
        }
        return stringBuilder.toString();
    }

    public String getSubject() {
        return this.subject;
    }

    public String[] getVias() {
        return this.vias;
    }
}

