/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.client.result;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.google.zxing.client.result.ResultParser;
import com.google.zxing.client.result.VINParsedResult;
import java.util.regex.Pattern;

public final class VINResultParser
extends ResultParser {
    private static final Pattern AZ09;
    private static final Pattern IOQ;

    static {
        IOQ = Pattern.compile("[IOQ]");
        AZ09 = Pattern.compile("[A-Z0-9]{17}");
    }

    private static char checkChar(int n2) {
        if (n2 < 10) {
            return (char)(n2 + 48);
        }
        if (n2 == 10) {
            return 'X';
        }
        throw new IllegalArgumentException();
    }

    private static boolean checkChecksum(CharSequence charSequence) {
        int n2 = 0;
        for (int i2 = 0; i2 < charSequence.length(); ++i2) {
            n2 += VINResultParser.vinPositionWeight(i2 + 1) * VINResultParser.vinCharValue(charSequence.charAt(i2));
        }
        return charSequence.charAt(8) == VINResultParser.checkChar(n2 % 11);
    }

    /*
     * Enabled aggressive block sorting
     */
    private static String countryCode(CharSequence charSequence) {
        char c2 = charSequence.charAt(0);
        char c3 = charSequence.charAt(1);
        switch (c2) {
            case '1': 
            case '4': 
            case '5': {
                return "US";
            }
            case '2': {
                return "CA";
            }
            case '3': {
                if (c3 < 'A' || c3 > 'W') return null;
                return "MX";
            }
            case '9': {
                if ((c3 < 'A' || c3 > 'E') && (c3 < '3' || c3 > '9')) return null;
                return "BR";
            }
            case 'J': {
                if (c3 < 'A' || c3 > 'T') return null;
                return "JP";
            }
            case 'K': {
                if (c3 < 'L' || c3 > 'R') return null;
                return "KO";
            }
            case 'L': {
                return "CN";
            }
            case 'M': {
                if (c3 < 'A' || c3 > 'E') return null;
                return "IN";
            }
            case 'S': {
                if (c3 >= 'A' && c3 <= 'M') {
                    return "UK";
                }
                if (c3 < 'N' || c3 > 'T') return null;
                return "DE";
            }
            case 'V': {
                if (c3 >= 'F' && c3 <= 'R') {
                    return "FR";
                }
                if (c3 < 'S' || c3 > 'W') return null;
                return "ES";
            }
            case 'W': {
                return "DE";
            }
            case 'X': {
                if (c3 != '0' && (c3 < '3' || c3 > '9')) return null;
                return "RU";
            }
            default: {
                return null;
            }
            case 'Z': 
        }
        if (c3 < 'A' || c3 > 'R') return null;
        return "IT";
    }

    private static int modelYear(char c2) {
        if (c2 >= 'E' && c2 <= 'H') {
            return c2 - 69 + 1984;
        }
        if (c2 >= 'J' && c2 <= 'N') {
            return c2 - 74 + 1988;
        }
        if (c2 == 'P') {
            return 1993;
        }
        if (c2 >= 'R' && c2 <= 'T') {
            return c2 - 82 + 1994;
        }
        if (c2 >= 'V' && c2 <= 'Y') {
            return c2 - 86 + 1997;
        }
        if (c2 >= '1' && c2 <= '9') {
            return c2 - 49 + 2001;
        }
        if (c2 >= 'A' && c2 <= 'D') {
            return c2 - 65 + 2010;
        }
        throw new IllegalArgumentException();
    }

    private static int vinCharValue(char c2) {
        if (c2 >= 'A' && c2 <= 'I') {
            return c2 - 65 + 1;
        }
        if (c2 >= 'J' && c2 <= 'R') {
            return c2 - 74 + 1;
        }
        if (c2 >= 'S' && c2 <= 'Z') {
            return c2 - 83 + 2;
        }
        if (c2 >= '0' && c2 <= '9') {
            return c2 - 48;
        }
        throw new IllegalArgumentException();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static int vinPositionWeight(int n2) {
        int n3 = 10;
        if (n2 >= 1 && n2 <= 7) {
            return 9 - n2;
        }
        if (n2 == 8) return n3;
        if (n2 == 9) {
            return 0;
        }
        if (n2 < 10) throw new IllegalArgumentException();
        if (n2 > 17) throw new IllegalArgumentException();
        return 19 - n2;
    }

    @Override
    public VINParsedResult parse(Result object) {
        block5: {
            if (((Result)object).getBarcodeFormat() != BarcodeFormat.CODE_39) {
                return null;
            }
            object = ((Result)object).getText();
            if (!AZ09.matcher((CharSequence)(object = IOQ.matcher((CharSequence)object).replaceAll("").trim())).matches()) {
                return null;
            }
            try {
                if (VINResultParser.checkChecksum((CharSequence)object)) break block5;
                return null;
            }
            catch (IllegalArgumentException illegalArgumentException) {
                return null;
            }
        }
        String string2 = ((String)object).substring(0, 3);
        object = new VINParsedResult((String)object, string2, ((String)object).substring(3, 9), ((String)object).substring(9, 17), VINResultParser.countryCode(string2), ((String)object).substring(3, 8), VINResultParser.modelYear(((String)object).charAt(9)), ((String)object).charAt(10), ((String)object).substring(11));
        return object;
    }
}

