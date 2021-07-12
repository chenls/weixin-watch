/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.client.result;

import com.google.zxing.Result;
import com.google.zxing.client.result.EmailAddressParsedResult;
import com.google.zxing.client.result.ResultParser;

public final class SMTPResultParser
extends ResultParser {
    @Override
    public EmailAddressParsedResult parse(Result object) {
        if (!((String)(object = SMTPResultParser.getMassagedText((Result)object))).startsWith("smtp:") && !((String)object).startsWith("SMTP:")) {
            return null;
        }
        String string2 = ((String)object).substring(5);
        object = null;
        String string3 = null;
        int n2 = string2.indexOf(58);
        String string4 = string3;
        String string5 = string2;
        if (n2 >= 0) {
            String string6 = string2.substring(n2 + 1);
            string2 = string2.substring(0, n2);
            n2 = string6.indexOf(58);
            object = string6;
            string4 = string3;
            string5 = string2;
            if (n2 >= 0) {
                string4 = string6.substring(n2 + 1);
                object = string6.substring(0, n2);
                string5 = string2;
            }
        }
        return new EmailAddressParsedResult(new String[]{string5}, null, null, (String)object, string4);
    }
}

