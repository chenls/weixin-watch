/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.client.result;

import com.google.zxing.Result;
import com.google.zxing.client.result.AddressBookParsedResult;
import com.google.zxing.client.result.ResultParser;
import java.util.ArrayList;

public final class AddressBookAUResultParser
extends ResultParser {
    private static String[] matchMultipleValuePrefix(String string2, int n2, String string3, boolean bl2) {
        ArrayList<String> arrayList = null;
        int n3 = 1;
        while (true) {
            String string4;
            if (n3 > n2 || (string4 = AddressBookAUResultParser.matchSinglePrefixedField(string2 + n3 + ':', string3, '\r', bl2)) == null) {
                if (arrayList != null) break;
                return null;
            }
            ArrayList<String> arrayList2 = arrayList;
            if (arrayList == null) {
                arrayList2 = new ArrayList<String>(n2);
            }
            arrayList2.add(string4);
            ++n3;
            arrayList = arrayList2;
        }
        return arrayList.toArray(new String[arrayList.size()]);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public AddressBookParsedResult parse(Result object) {
        if (!((String)(object = AddressBookAUResultParser.getMassagedText((Result)object))).contains("MEMORY") || !((String)object).contains("\r\n")) {
            return null;
        }
        String string2 = AddressBookAUResultParser.matchSinglePrefixedField("NAME1:", (String)object, '\r', true);
        String string3 = AddressBookAUResultParser.matchSinglePrefixedField("NAME2:", (String)object, '\r', true);
        String[] stringArray = AddressBookAUResultParser.matchMultipleValuePrefix("TEL", 3, (String)object, true);
        String[] stringArray2 = AddressBookAUResultParser.matchMultipleValuePrefix("MAIL", 3, (String)object, true);
        String string4 = AddressBookAUResultParser.matchSinglePrefixedField("MEMORY:", (String)object, '\r', false);
        String string5 = AddressBookAUResultParser.matchSinglePrefixedField("ADD:", (String)object, '\r', true);
        if (string5 == null) {
            object = null;
            return new AddressBookParsedResult(AddressBookAUResultParser.maybeWrap(string2), null, string3, stringArray, null, stringArray2, null, null, string4, (String[])object, null, null, null, null, null, null);
        }
        object = new String[]{string5};
        return new AddressBookParsedResult(AddressBookAUResultParser.maybeWrap(string2), null, string3, stringArray, null, stringArray2, null, null, string4, (String[])object, null, null, null, null, null, null);
    }
}

