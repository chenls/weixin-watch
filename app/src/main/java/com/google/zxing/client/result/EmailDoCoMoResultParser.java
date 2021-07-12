/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.client.result;

import com.google.zxing.Result;
import com.google.zxing.client.result.AbstractDoCoMoResultParser;
import com.google.zxing.client.result.EmailAddressParsedResult;
import java.util.regex.Pattern;

public final class EmailDoCoMoResultParser
extends AbstractDoCoMoResultParser {
    private static final Pattern ATEXT_ALPHANUMERIC = Pattern.compile("[a-zA-Z0-9@.!#$%&'*+\\-/=?^_`{|}~]+");

    static boolean isBasicallyValidEmailAddress(String string2) {
        return string2 != null && ATEXT_ALPHANUMERIC.matcher(string2).matches() && string2.indexOf(64) >= 0;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public EmailAddressParsedResult parse(Result object) {
        String[] stringArray;
        if (((String)(object = EmailDoCoMoResultParser.getMassagedText((Result)object))).startsWith("MATMSG:") && (stringArray = EmailDoCoMoResultParser.matchDoCoMoPrefixedField("TO:", (String)object, true)) != null) {
            int n2 = stringArray.length;
            int n3 = 0;
            while (true) {
                if (n3 >= n2) {
                    return new EmailAddressParsedResult(stringArray, null, null, EmailDoCoMoResultParser.matchSingleDoCoMoPrefixedField("SUB:", (String)object, false), EmailDoCoMoResultParser.matchSingleDoCoMoPrefixedField("BODY:", (String)object, false));
                }
                if (!EmailDoCoMoResultParser.isBasicallyValidEmailAddress(stringArray[n3])) break;
                ++n3;
            }
        }
        return null;
    }
}

