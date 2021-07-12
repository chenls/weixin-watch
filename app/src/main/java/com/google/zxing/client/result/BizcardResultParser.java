/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.client.result;

import com.google.zxing.Result;
import com.google.zxing.client.result.AbstractDoCoMoResultParser;
import com.google.zxing.client.result.AddressBookParsedResult;
import java.util.ArrayList;

public final class BizcardResultParser
extends AbstractDoCoMoResultParser {
    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static String buildName(String string2, String string3) {
        if (string2 == null) {
            return string3;
        }
        if (string3 != null) return string2 + ' ' + string3;
        return string2;
    }

    private static String[] buildPhoneNumbers(String string2, String string3, String string4) {
        int n2;
        ArrayList<String> arrayList = new ArrayList<String>(3);
        if (string2 != null) {
            arrayList.add(string2);
        }
        if (string3 != null) {
            arrayList.add(string3);
        }
        if (string4 != null) {
            arrayList.add(string4);
        }
        if ((n2 = arrayList.size()) == 0) {
            return null;
        }
        return arrayList.toArray(new String[n2]);
    }

    @Override
    public AddressBookParsedResult parse(Result object) {
        String string2 = BizcardResultParser.getMassagedText((Result)object);
        if (!string2.startsWith("BIZCARD:")) {
            return null;
        }
        object = BizcardResultParser.buildName(BizcardResultParser.matchSingleDoCoMoPrefixedField("N:", string2, true), BizcardResultParser.matchSingleDoCoMoPrefixedField("X:", string2, true));
        String string3 = BizcardResultParser.matchSingleDoCoMoPrefixedField("T:", string2, true);
        String string4 = BizcardResultParser.matchSingleDoCoMoPrefixedField("C:", string2, true);
        String[] stringArray = BizcardResultParser.matchDoCoMoPrefixedField("A:", string2, true);
        String string5 = BizcardResultParser.matchSingleDoCoMoPrefixedField("B:", string2, true);
        String string6 = BizcardResultParser.matchSingleDoCoMoPrefixedField("M:", string2, true);
        String string7 = BizcardResultParser.matchSingleDoCoMoPrefixedField("F:", string2, true);
        string2 = BizcardResultParser.matchSingleDoCoMoPrefixedField("E:", string2, true);
        return new AddressBookParsedResult(BizcardResultParser.maybeWrap((String)object), null, null, BizcardResultParser.buildPhoneNumbers(string5, string6, string7), null, BizcardResultParser.maybeWrap(string2), null, null, null, stringArray, null, string4, null, string3, null, null);
    }
}

