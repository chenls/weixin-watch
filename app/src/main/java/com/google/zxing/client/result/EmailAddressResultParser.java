/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.client.result;

import com.google.zxing.Result;
import com.google.zxing.client.result.EmailAddressParsedResult;
import com.google.zxing.client.result.EmailDoCoMoResultParser;
import com.google.zxing.client.result.ResultParser;
import java.util.Map;
import java.util.regex.Pattern;

public final class EmailAddressResultParser
extends ResultParser {
    private static final Pattern COMMA = Pattern.compile(",");

    @Override
    public EmailAddressParsedResult parse(Result stringArray) {
        String[] stringArray2 = EmailAddressResultParser.getMassagedText((Result)stringArray);
        if (stringArray2.startsWith("mailto:") || stringArray2.startsWith("MAILTO:")) {
            String[] stringArray3 = stringArray2.substring(7);
            int n2 = stringArray3.indexOf(63);
            stringArray = stringArray3;
            if (n2 >= 0) {
                stringArray = stringArray3.substring(0, n2);
            }
            stringArray3 = EmailAddressResultParser.urlDecode((String)stringArray);
            stringArray = null;
            if (!stringArray3.isEmpty()) {
                stringArray = COMMA.split((CharSequence)stringArray3);
            }
            Map<String, String> map = EmailAddressResultParser.parseNameValuePairs((String)stringArray2);
            stringArray2 = null;
            Object var9_6 = null;
            String[] stringArray4 = null;
            Object var8_8 = null;
            String string2 = null;
            String string3 = null;
            stringArray3 = stringArray;
            if (map != null) {
                stringArray3 = stringArray;
                if (stringArray == null) {
                    stringArray2 = map.get("to");
                    stringArray3 = stringArray;
                    if (stringArray2 != null) {
                        stringArray3 = COMMA.split((CharSequence)stringArray2);
                    }
                }
                stringArray2 = map.get("cc");
                stringArray = var9_6;
                if (stringArray2 != null) {
                    stringArray = COMMA.split((CharSequence)stringArray2);
                }
                stringArray4 = map.get("bcc");
                stringArray2 = var8_8;
                if (stringArray4 != null) {
                    stringArray2 = COMMA.split((CharSequence)stringArray4);
                }
                string2 = map.get("subject");
                string3 = map.get("body");
                stringArray4 = stringArray2;
                stringArray2 = stringArray;
            }
            return new EmailAddressParsedResult(stringArray3, stringArray2, stringArray4, string2, string3);
        }
        if (!EmailDoCoMoResultParser.isBasicallyValidEmailAddress((String)stringArray2)) {
            return null;
        }
        return new EmailAddressParsedResult((String)stringArray2);
    }
}

