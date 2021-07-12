/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.client.result;

import com.google.zxing.Result;
import com.google.zxing.client.result.GeoParsedResult;
import com.google.zxing.client.result.ResultParser;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class GeoResultParser
extends ResultParser {
    private static final Pattern GEO_URL_PATTERN = Pattern.compile("geo:([\\-0-9.]+),([\\-0-9.]+)(?:,([\\-0-9.]+))?(?:\\?(.*))?", 2);

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public GeoParsedResult parse(Result object) {
        double d2;
        double d3;
        double d4;
        double d5;
        String string2;
        block7: {
            block6: {
                object = GeoResultParser.getMassagedText((Result)object);
                if (!((Matcher)(object = GEO_URL_PATTERN.matcher((CharSequence)object))).matches()) return null;
                string2 = ((Matcher)object).group(4);
                try {
                    d5 = Double.parseDouble(((Matcher)object).group(1));
                    if (!(d5 > 90.0 || d5 < -90.0 || (d4 = Double.parseDouble(((Matcher)object).group(2))) > 180.0) && !(d4 < -180.0)) break block6;
                }
                catch (NumberFormatException numberFormatException) {
                    return null;
                }
                return null;
            }
            String string3 = ((Matcher)object).group(3);
            if (string3 != null) break block7;
            d3 = 0.0;
            return new GeoParsedResult(d5, d4, d3, string2);
        }
        d3 = d2 = Double.parseDouble(((Matcher)object).group(3));
        if (!(d2 < 0.0)) return new GeoParsedResult(d5, d4, d3, string2);
        return null;
    }
}

