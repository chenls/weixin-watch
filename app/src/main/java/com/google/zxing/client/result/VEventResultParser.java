/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.client.result;

import com.google.zxing.Result;
import com.google.zxing.client.result.CalendarParsedResult;
import com.google.zxing.client.result.ResultParser;
import com.google.zxing.client.result.VCardResultParser;
import java.util.List;

public final class VEventResultParser
extends ResultParser {
    private static String matchSingleVCardPrefixedField(CharSequence object, String string2, boolean bl2) {
        if ((object = VCardResultParser.matchSingleVCardPrefixedField((CharSequence)object, string2, bl2, false)) == null || object.isEmpty()) {
            return null;
        }
        return (String)object.get(0);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static String[] matchVCardPrefixedField(CharSequence stringArray, String stringArray2, boolean bl2) {
        List<List<String>> list = VCardResultParser.matchVCardPrefixedField((CharSequence)stringArray, (String)stringArray2, bl2, false);
        if (list == null) return null;
        if (list.isEmpty()) {
            return null;
        }
        int n2 = list.size();
        stringArray2 = new String[n2];
        int n3 = 0;
        while (true) {
            stringArray = stringArray2;
            if (n3 >= n2) return stringArray;
            stringArray2[n3] = list.get(n3).get(0);
            ++n3;
        }
    }

    private static String stripMailto(String string2) {
        String string3;
        block2: {
            block3: {
                string3 = string2;
                if (string2 == null) break block2;
                if (string2.startsWith("mailto:")) break block3;
                string3 = string2;
                if (!string2.startsWith("MAILTO:")) break block2;
            }
            string3 = string2.substring(7);
        }
        return string3;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public CalendarParsedResult parse(Result object) {
        double d2;
        double d3;
        int n2;
        String string2 = VEventResultParser.getMassagedText((Result)object);
        if (string2.indexOf("BEGIN:VEVENT") < 0) {
            return null;
        }
        object = VEventResultParser.matchSingleVCardPrefixedField("SUMMARY", string2, true);
        String string3 = VEventResultParser.matchSingleVCardPrefixedField("DTSTART", string2, true);
        if (string3 == null) {
            return null;
        }
        String string4 = VEventResultParser.matchSingleVCardPrefixedField("DTEND", string2, true);
        String string5 = VEventResultParser.matchSingleVCardPrefixedField("DURATION", string2, true);
        String string6 = VEventResultParser.matchSingleVCardPrefixedField("LOCATION", string2, true);
        String string7 = VEventResultParser.stripMailto(VEventResultParser.matchSingleVCardPrefixedField("ORGANIZER", string2, true));
        String[] stringArray = VEventResultParser.matchVCardPrefixedField("ATTENDEE", string2, true);
        if (stringArray != null) {
            for (n2 = 0; n2 < stringArray.length; ++n2) {
                stringArray[n2] = VEventResultParser.stripMailto(stringArray[n2]);
            }
        }
        String string8 = VEventResultParser.matchSingleVCardPrefixedField("DESCRIPTION", string2, true);
        if ((string2 = VEventResultParser.matchSingleVCardPrefixedField("GEO", string2, true)) == null) {
            d3 = Double.NaN;
            d2 = Double.NaN;
        } else {
            n2 = string2.indexOf(59);
            if (n2 < 0) {
                return null;
            }
            try {
                d3 = Double.parseDouble(string2.substring(0, n2));
                d2 = Double.parseDouble(string2.substring(n2 + 1));
            }
            catch (NumberFormatException numberFormatException) {
                return null;
            }
        }
        try {
            return new CalendarParsedResult((String)object, string3, string4, string5, string6, string7, stringArray, string8, d3, d2);
        }
        catch (IllegalArgumentException illegalArgumentException) {
            return null;
        }
    }
}

