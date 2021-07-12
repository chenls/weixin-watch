/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.client.result;

import com.google.zxing.Result;
import com.google.zxing.client.result.AbstractDoCoMoResultParser;
import com.google.zxing.client.result.AddressBookParsedResult;

public final class AddressBookDoCoMoResultParser
extends AbstractDoCoMoResultParser {
    private static String parseName(String string2) {
        int n2 = string2.indexOf(44);
        String string3 = string2;
        if (n2 >= 0) {
            string3 = string2.substring(n2 + 1) + ' ' + string2.substring(0, n2);
        }
        return string3;
    }

    @Override
    public AddressBookParsedResult parse(Result stringArray) {
        String string2 = AddressBookDoCoMoResultParser.getMassagedText((Result)stringArray);
        if (!string2.startsWith("MECARD:")) {
            return null;
        }
        stringArray = AddressBookDoCoMoResultParser.matchDoCoMoPrefixedField("N:", string2, true);
        if (stringArray == null) {
            return null;
        }
        String string3 = AddressBookDoCoMoResultParser.parseName(stringArray[0]);
        String string4 = AddressBookDoCoMoResultParser.matchSingleDoCoMoPrefixedField("SOUND:", string2, true);
        String[] stringArray2 = AddressBookDoCoMoResultParser.matchDoCoMoPrefixedField("TEL:", string2, true);
        String[] stringArray3 = AddressBookDoCoMoResultParser.matchDoCoMoPrefixedField("EMAIL:", string2, true);
        String string5 = AddressBookDoCoMoResultParser.matchSingleDoCoMoPrefixedField("NOTE:", string2, false);
        String[] stringArray4 = AddressBookDoCoMoResultParser.matchDoCoMoPrefixedField("ADR:", string2, true);
        String[] stringArray5 = AddressBookDoCoMoResultParser.matchSingleDoCoMoPrefixedField("BDAY:", string2, true);
        stringArray = stringArray5;
        if (!AddressBookDoCoMoResultParser.isStringOfDigits((CharSequence)stringArray5, 8)) {
            stringArray = null;
        }
        stringArray5 = AddressBookDoCoMoResultParser.matchDoCoMoPrefixedField("URL:", string2, true);
        string2 = AddressBookDoCoMoResultParser.matchSingleDoCoMoPrefixedField("ORG:", string2, true);
        return new AddressBookParsedResult(AddressBookDoCoMoResultParser.maybeWrap(string3), null, string4, stringArray2, null, stringArray3, null, null, string5, stringArray4, null, string2, (String)stringArray, null, stringArray5, null);
    }
}

