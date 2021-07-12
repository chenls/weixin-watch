/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.client.result;

import com.google.zxing.Result;
import com.google.zxing.client.result.AddressBookAUResultParser;
import com.google.zxing.client.result.AddressBookDoCoMoResultParser;
import com.google.zxing.client.result.BizcardResultParser;
import com.google.zxing.client.result.BookmarkDoCoMoResultParser;
import com.google.zxing.client.result.EmailAddressResultParser;
import com.google.zxing.client.result.EmailDoCoMoResultParser;
import com.google.zxing.client.result.ExpandedProductResultParser;
import com.google.zxing.client.result.GeoResultParser;
import com.google.zxing.client.result.ISBNResultParser;
import com.google.zxing.client.result.ParsedResult;
import com.google.zxing.client.result.ProductResultParser;
import com.google.zxing.client.result.SMSMMSResultParser;
import com.google.zxing.client.result.SMSTOMMSTOResultParser;
import com.google.zxing.client.result.SMTPResultParser;
import com.google.zxing.client.result.TelResultParser;
import com.google.zxing.client.result.TextParsedResult;
import com.google.zxing.client.result.URIResultParser;
import com.google.zxing.client.result.URLTOResultParser;
import com.google.zxing.client.result.VCardResultParser;
import com.google.zxing.client.result.VEventResultParser;
import com.google.zxing.client.result.VINResultParser;
import com.google.zxing.client.result.WifiResultParser;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public abstract class ResultParser {
    private static final Pattern AMPERSAND;
    private static final String BYTE_ORDER_MARK = "\ufeff";
    private static final Pattern DIGITS;
    private static final Pattern EQUALS;
    private static final ResultParser[] PARSERS;

    static {
        PARSERS = new ResultParser[]{new BookmarkDoCoMoResultParser(), new AddressBookDoCoMoResultParser(), new EmailDoCoMoResultParser(), new AddressBookAUResultParser(), new VCardResultParser(), new BizcardResultParser(), new VEventResultParser(), new EmailAddressResultParser(), new SMTPResultParser(), new TelResultParser(), new SMSMMSResultParser(), new SMSTOMMSTOResultParser(), new GeoResultParser(), new WifiResultParser(), new URLTOResultParser(), new URIResultParser(), new ISBNResultParser(), new ProductResultParser(), new ExpandedProductResultParser(), new VINResultParser()};
        DIGITS = Pattern.compile("\\d+");
        AMPERSAND = Pattern.compile("&");
        EQUALS = Pattern.compile("=");
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static void appendKeyValue(CharSequence charSequence, Map<String, String> map) {
        String[] stringArray = EQUALS.split(charSequence, 2);
        if (stringArray.length != 2) return;
        charSequence = stringArray[0];
        String string2 = stringArray[1];
        try {
            map.put((String)charSequence, ResultParser.urlDecode(string2));
            return;
        }
        catch (IllegalArgumentException illegalArgumentException) {
            return;
        }
    }

    private static int countPrecedingBackslashes(CharSequence charSequence, int n2) {
        int n3 = 0;
        --n2;
        while (n2 >= 0 && charSequence.charAt(n2) == '\\') {
            ++n3;
            --n2;
        }
        return n3;
    }

    protected static String getMassagedText(Result object) {
        String string2 = ((Result)object).getText();
        object = string2;
        if (string2.startsWith(BYTE_ORDER_MARK)) {
            object = string2.substring(1);
        }
        return object;
    }

    protected static boolean isStringOfDigits(CharSequence charSequence, int n2) {
        return charSequence != null && n2 > 0 && n2 == charSequence.length() && DIGITS.matcher(charSequence).matches();
    }

    /*
     * Enabled aggressive block sorting
     */
    protected static boolean isSubstringOfDigits(CharSequence charSequence, int n2, int n3) {
        block3: {
            block2: {
                if (charSequence == null || n3 <= 0) break block2;
                n3 = n2 + n3;
                if (charSequence.length() >= n3 && DIGITS.matcher(charSequence.subSequence(n2, n3)).matches()) break block3;
            }
            return false;
        }
        return true;
    }

    static String[] matchPrefixedField(String string2, String string3, char c2, boolean bl2) {
        ArrayList arrayList = null;
        int n2 = 0;
        int n3 = string3.length();
        block0: while (true) {
            if (n2 >= n3 || (n2 = string3.indexOf(string2, n2)) < 0) {
                if (arrayList != null && !arrayList.isEmpty()) break;
                return null;
            }
            int n4 = n2 + string2.length();
            boolean bl3 = true;
            ArrayList arrayList2 = arrayList;
            n2 = n4;
            while (true) {
                int n5;
                n2 = n5 = ++n2;
                arrayList = arrayList2;
                if (!bl3) continue block0;
                n2 = string3.indexOf(c2, n5);
                if (n2 < 0) {
                    n2 = string3.length();
                    bl3 = false;
                    continue;
                }
                if (ResultParser.countPrecedingBackslashes(string3, n2) % 2 != 0) continue;
                arrayList = arrayList2;
                if (arrayList2 == null) {
                    arrayList = new ArrayList(3);
                }
                String string4 = ResultParser.unescapeBackslash(string3.substring(n4, n2));
                arrayList2 = string4;
                if (bl2) {
                    arrayList2 = string4.trim();
                }
                if (!((String)((Object)arrayList2)).isEmpty()) {
                    arrayList.add(arrayList2);
                }
                ++n2;
                bl3 = false;
                arrayList2 = arrayList;
            }
            break;
        }
        return arrayList.toArray(new String[arrayList.size()]);
    }

    static String matchSinglePrefixedField(String stringArray, String string2, char c2, boolean bl2) {
        if ((stringArray = ResultParser.matchPrefixedField((String)stringArray, string2, c2, bl2)) == null) {
            return null;
        }
        return stringArray[0];
    }

    protected static void maybeAppend(String string2, StringBuilder stringBuilder) {
        if (string2 != null) {
            stringBuilder.append('\n');
            stringBuilder.append(string2);
        }
    }

    protected static void maybeAppend(String[] stringArray, StringBuilder stringBuilder) {
        if (stringArray != null) {
            for (String string2 : stringArray) {
                stringBuilder.append('\n');
                stringBuilder.append(string2);
            }
        }
    }

    protected static String[] maybeWrap(String string2) {
        if (string2 == null) {
            return null;
        }
        return new String[]{string2};
    }

    protected static int parseHexDigit(char c2) {
        if (c2 >= '0' && c2 <= '9') {
            return c2 - 48;
        }
        if (c2 >= 'a' && c2 <= 'f') {
            return c2 - 97 + 10;
        }
        if (c2 >= 'A' && c2 <= 'F') {
            return c2 - 65 + 10;
        }
        return -1;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    static Map<String, String> parseNameValuePairs(String hashMap) {
        int n2 = ((String)((Object)hashMap)).indexOf(63);
        if (n2 < 0) {
            return null;
        }
        HashMap<String, String> hashMap2 = new HashMap<String, String>(3);
        String[] stringArray = AMPERSAND.split(((String)((Object)hashMap)).substring(n2 + 1));
        int n3 = stringArray.length;
        n2 = 0;
        while (true) {
            hashMap = hashMap2;
            if (n2 >= n3) return hashMap;
            ResultParser.appendKeyValue(stringArray[n2], hashMap2);
            ++n2;
        }
    }

    public static ParsedResult parseResult(Result result) {
        ResultParser[] resultParserArray = PARSERS;
        int n2 = resultParserArray.length;
        for (int i2 = 0; i2 < n2; ++i2) {
            ParsedResult parsedResult = resultParserArray[i2].parse(result);
            if (parsedResult == null) continue;
            return parsedResult;
        }
        return new TextParsedResult(result.getText(), null);
    }

    /*
     * Enabled aggressive block sorting
     */
    protected static String unescapeBackslash(String string2) {
        int n2 = string2.indexOf(92);
        if (n2 < 0) {
            return string2;
        }
        int n3 = string2.length();
        StringBuilder stringBuilder = new StringBuilder(n3 - 1);
        stringBuilder.append(string2.toCharArray(), 0, n2);
        boolean bl2 = false;
        while (n2 < n3) {
            char c2 = string2.charAt(n2);
            if (bl2 || c2 != '\\') {
                stringBuilder.append(c2);
                bl2 = false;
            } else {
                bl2 = true;
            }
            ++n2;
        }
        return stringBuilder.toString();
    }

    static String urlDecode(String string2) {
        try {
            string2 = URLDecoder.decode(string2, "UTF-8");
            return string2;
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            throw new IllegalStateException(unsupportedEncodingException);
        }
    }

    public abstract ParsedResult parse(Result var1);
}

