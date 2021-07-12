/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.client.result;

import com.google.zxing.Result;
import com.google.zxing.client.result.AddressBookParsedResult;
import com.google.zxing.client.result.ResultParser;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class VCardResultParser
extends ResultParser {
    private static final Pattern BEGIN_VCARD = Pattern.compile("BEGIN:VCARD", 2);
    private static final Pattern COMMA;
    private static final Pattern CR_LF_SPACE_TAB;
    private static final Pattern EQUALS;
    private static final Pattern NEWLINE_ESCAPE;
    private static final Pattern SEMICOLON;
    private static final Pattern SEMICOLON_OR_COMMA;
    private static final Pattern UNESCAPED_SEMICOLONS;
    private static final Pattern VCARD_ESCAPES;
    private static final Pattern VCARD_LIKE_DATE;

    static {
        VCARD_LIKE_DATE = Pattern.compile("\\d{4}-?\\d{2}-?\\d{2}");
        CR_LF_SPACE_TAB = Pattern.compile("\r\n[ \t]");
        NEWLINE_ESCAPE = Pattern.compile("\\\\[nN]");
        VCARD_ESCAPES = Pattern.compile("\\\\([,;\\\\])");
        EQUALS = Pattern.compile("=");
        SEMICOLON = Pattern.compile(";");
        UNESCAPED_SEMICOLONS = Pattern.compile("(?<!\\\\);+");
        COMMA = Pattern.compile(",");
        SEMICOLON_OR_COMMA = Pattern.compile("[;,]");
    }

    /*
     * Enabled aggressive block sorting
     */
    private static String decodeQuotedPrintable(CharSequence charSequence, String string2) {
        int n2 = charSequence.length();
        StringBuilder stringBuilder = new StringBuilder(n2);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int n3 = 0;
        while (true) {
            if (n3 >= n2) {
                VCardResultParser.maybeAppendFragment(byteArrayOutputStream, string2, stringBuilder);
                return stringBuilder.toString();
            }
            char c2 = charSequence.charAt(n3);
            int n4 = n3;
            switch (c2) {
                default: {
                    VCardResultParser.maybeAppendFragment(byteArrayOutputStream, string2, stringBuilder);
                    stringBuilder.append(c2);
                    n4 = n3;
                }
                case '\n': 
                case '\r': {
                    break;
                }
                case '=': {
                    n4 = n3;
                    if (n3 >= n2 - 2) break;
                    c2 = charSequence.charAt(n3 + 1);
                    n4 = n3;
                    if (c2 == '\r') break;
                    n4 = n3;
                    if (c2 == '\n') break;
                    char c3 = charSequence.charAt(n3 + 2);
                    n4 = VCardResultParser.parseHexDigit(c2);
                    int n5 = VCardResultParser.parseHexDigit(c3);
                    if (n4 >= 0 && n5 >= 0) {
                        byteArrayOutputStream.write((n4 << 4) + n5);
                    }
                    n4 = n3 + 2;
                }
            }
            n3 = n4 + 1;
        }
    }

    private static void formatNames(Iterable<List<String>> object) {
        if (object != null) {
            object = object.iterator();
            while (object.hasNext()) {
                int n2;
                List list = (List)object.next();
                CharSequence charSequence = (String)list.get(0);
                String[] stringArray = new String[5];
                int n3 = 0;
                for (int i2 = 0; i2 < stringArray.length - 1 && (n2 = ((String)charSequence).indexOf(59, n3)) >= 0; ++i2) {
                    stringArray[i2] = ((String)charSequence).substring(n3, n2);
                    n3 = n2 + 1;
                }
                stringArray[i2] = ((String)charSequence).substring(n3);
                charSequence = new StringBuilder(100);
                VCardResultParser.maybeAppendComponent(stringArray, 3, (StringBuilder)charSequence);
                VCardResultParser.maybeAppendComponent(stringArray, 1, (StringBuilder)charSequence);
                VCardResultParser.maybeAppendComponent(stringArray, 2, (StringBuilder)charSequence);
                VCardResultParser.maybeAppendComponent(stringArray, 0, (StringBuilder)charSequence);
                VCardResultParser.maybeAppendComponent(stringArray, 4, (StringBuilder)charSequence);
                list.set(0, ((StringBuilder)charSequence).toString().trim());
            }
        }
    }

    private static boolean isLikeVCardDate(CharSequence charSequence) {
        return charSequence == null || VCARD_LIKE_DATE.matcher(charSequence).matches();
    }

    static List<String> matchSingleVCardPrefixedField(CharSequence object, String string2, boolean bl2, boolean bl3) {
        if ((object = VCardResultParser.matchVCardPrefixedField((CharSequence)object, string2, bl2, bl3)) == null || object.isEmpty()) {
            return null;
        }
        return (List)object.get(0);
    }

    /*
     * Enabled aggressive block sorting
     */
    static List<List<String>> matchVCardPrefixedField(CharSequence charSequence, String string2, boolean bl2, boolean bl3) {
        Object object = null;
        int n2 = 0;
        int n3 = string2.length();
        while (true) {
            int n4;
            int n5;
            Object object2;
            block29: {
                block28: {
                    if (n2 >= n3) break block28;
                    object2 = Pattern.compile("(?:^|\n)" + charSequence + "(?:;([^:]*))?:", 2).matcher(string2);
                    n5 = n2;
                    if (n2 > 0) {
                        n5 = n2 - 1;
                    }
                    if (((Matcher)object2).find(n5)) break block29;
                }
                return object;
            }
            int n6 = ((Matcher)object2).end(0);
            String[] stringArray = ((Matcher)object2).group(1);
            Object object3 = null;
            Object object4 = null;
            n5 = 0;
            n2 = 0;
            Object object5 = null;
            object2 = null;
            if (stringArray != null) {
                stringArray = SEMICOLON.split((CharSequence)stringArray);
                int n7 = stringArray.length;
                n4 = 0;
                while (true) {
                    object3 = object4;
                    n5 = n2;
                    object5 = object2;
                    if (n4 >= n7) break;
                    object5 = stringArray[n4];
                    object3 = object4;
                    if (object4 == null) {
                        object3 = new ArrayList(1);
                    }
                    object3.add(object5);
                    object4 = EQUALS.split((CharSequence)object5, 2);
                    n5 = n2;
                    object5 = object2;
                    if (((String[])object4).length > 1) {
                        String string3 = object4[0];
                        object4 = object4[1];
                        if ("ENCODING".equalsIgnoreCase(string3) && "QUOTED-PRINTABLE".equalsIgnoreCase((String)object4)) {
                            n5 = 1;
                            object5 = object2;
                        } else {
                            n5 = n2;
                            object5 = object2;
                            if ("CHARSET".equalsIgnoreCase(string3)) {
                                object5 = object4;
                                n5 = n2;
                            }
                        }
                    }
                    ++n4;
                    object4 = object3;
                    n2 = n5;
                    object2 = object5;
                }
            }
            n2 = n6;
            while ((n4 = string2.indexOf(10, n2)) >= 0) {
                if (n4 < string2.length() - 1 && (string2.charAt(n4 + 1) == ' ' || string2.charAt(n4 + 1) == '\t')) {
                    n2 = n4 + 2;
                    continue;
                }
                if (n5 == 0 || (n4 < 1 || string2.charAt(n4 - 1) != '=') && (n4 < 2 || string2.charAt(n4 - 2) != '=')) break;
                n2 = n4 + 1;
            }
            if (n4 < 0) {
                n2 = n3;
                continue;
            }
            if (n4 > n6) {
                object4 = object;
                if (object == null) {
                    object4 = new ArrayList(1);
                }
                n2 = n4;
                if (n4 >= 1) {
                    n2 = n4;
                    if (string2.charAt(n4 - 1) == '\r') {
                        n2 = n4 - 1;
                    }
                }
                object2 = object = string2.substring(n6, n2);
                if (bl2) {
                    object2 = ((String)object).trim();
                }
                if (n5 != 0) {
                    object2 = object = VCardResultParser.decodeQuotedPrintable((CharSequence)object2, (String)object5);
                    if (bl3) {
                        object2 = UNESCAPED_SEMICOLONS.matcher((CharSequence)object).replaceAll("\n").trim();
                    }
                } else {
                    object = object2;
                    if (bl3) {
                        object = UNESCAPED_SEMICOLONS.matcher((CharSequence)object2).replaceAll("\n").trim();
                    }
                    object2 = CR_LF_SPACE_TAB.matcher((CharSequence)object).replaceAll("");
                    object2 = NEWLINE_ESCAPE.matcher((CharSequence)object2).replaceAll("\n");
                    object2 = VCARD_ESCAPES.matcher((CharSequence)object2).replaceAll("$1");
                }
                if (object3 == null) {
                    object3 = new ArrayList(1);
                    object3.add(object2);
                    object4.add(object3);
                } else {
                    object3.add(0, object2);
                    object4.add(object3);
                }
                ++n2;
                object = object4;
                continue;
            }
            n2 = n4 + 1;
        }
    }

    private static void maybeAppendComponent(String[] stringArray, int n2, StringBuilder stringBuilder) {
        if (stringArray[n2] != null && !stringArray[n2].isEmpty()) {
            if (stringBuilder.length() > 0) {
                stringBuilder.append(' ');
            }
            stringBuilder.append(stringArray[n2]);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static void maybeAppendFragment(ByteArrayOutputStream byteArrayOutputStream, String string2, StringBuilder stringBuilder) {
        if (byteArrayOutputStream.size() > 0) {
            byte[] byArray = byteArrayOutputStream.toByteArray();
            if (string2 == null) {
                string2 = new String(byArray, Charset.forName("UTF-8"));
            } else {
                try {
                    string2 = new String(byArray, string2);
                }
                catch (UnsupportedEncodingException unsupportedEncodingException) {
                    string2 = new String(byArray, Charset.forName("UTF-8"));
                }
            }
            byteArrayOutputStream.reset();
            stringBuilder.append(string2);
        }
    }

    private static String toPrimaryValue(List<String> list) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    private static String[] toPrimaryValues(Collection<List<String>> collection) {
        if (collection == null || collection.isEmpty()) {
            return null;
        }
        ArrayList<String> arrayList = new ArrayList<String>(collection.size());
        Iterator<List<String>> iterator = collection.iterator();
        while (iterator.hasNext()) {
            String string2 = iterator.next().get(0);
            if (string2 == null || string2.isEmpty()) continue;
            arrayList.add(string2);
        }
        return arrayList.toArray(new String[collection.size()]);
    }

    /*
     * Enabled aggressive block sorting
     */
    private static String[] toTypes(Collection<List<String>> collection) {
        if (collection == null || collection.isEmpty()) {
            return null;
        }
        ArrayList<String> arrayList = new ArrayList<String>(collection.size());
        Iterator<List<String>> iterator = collection.iterator();
        block0: while (iterator.hasNext()) {
            List<String> list = iterator.next();
            String string2 = null;
            int n2 = 1;
            while (true) {
                block8: {
                    String string3;
                    block7: {
                        int n3;
                        string3 = string2;
                        if (n2 >= list.size() || (n3 = (string3 = list.get(n2)).indexOf(61)) < 0) break block7;
                        if (!"TYPE".equalsIgnoreCase(string3.substring(0, n3))) break block8;
                        string3 = string3.substring(n3 + 1);
                    }
                    arrayList.add(string3);
                    continue block0;
                }
                ++n2;
            }
            break;
        }
        return arrayList.toArray(new String[collection.size()]);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public AddressBookParsedResult parse(Result list) {
        Object object = VCardResultParser.getMassagedText(list);
        if (!((Matcher)((Object)(list = BEGIN_VCARD.matcher((CharSequence)object)))).find() || ((Matcher)((Object)list)).start() != 0) {
            return null;
        }
        list = VCardResultParser.matchVCardPrefixedField("FN", (String)object, true, false);
        List<List<String>> list2 = list;
        if (list == null) {
            list2 = VCardResultParser.matchVCardPrefixedField("N", (String)object, true, false);
            VCardResultParser.formatNames(list2);
        }
        String[] stringArray = (list = VCardResultParser.matchSingleVCardPrefixedField("NICKNAME", (String)object, true, false)) == null ? null : COMMA.split((CharSequence)list.get(0));
        List<List<String>> list3 = VCardResultParser.matchVCardPrefixedField("TEL", (String)object, true, false);
        List<List<String>> list4 = VCardResultParser.matchVCardPrefixedField("EMAIL", (String)object, true, false);
        List<String> list5 = VCardResultParser.matchSingleVCardPrefixedField("NOTE", (String)object, false, false);
        List<List<String>> list6 = VCardResultParser.matchVCardPrefixedField("ADR", (String)object, true, true);
        List<String> list7 = VCardResultParser.matchSingleVCardPrefixedField("ORG", (String)object, true, true);
        List<Object> list8 = list = VCardResultParser.matchSingleVCardPrefixedField("BDAY", (String)object, true, false);
        if (list != null) {
            list8 = list;
            if (!VCardResultParser.isLikeVCardDate((CharSequence)list.get(0))) {
                list8 = null;
            }
        }
        List<String> list9 = VCardResultParser.matchSingleVCardPrefixedField("TITLE", (String)object, true, false);
        List<List<String>> list10 = VCardResultParser.matchVCardPrefixedField("URL", (String)object, true, false);
        List<String> list11 = VCardResultParser.matchSingleVCardPrefixedField("IMPP", (String)object, true, false);
        list = VCardResultParser.matchSingleVCardPrefixedField("GEO", (String)object, true, false);
        list = list == null ? null : SEMICOLON_OR_COMMA.split((CharSequence)list.get(0));
        object = list;
        if (list != null) {
            object = list;
            if (((List<Object>)list).length != 2) {
                object = null;
            }
        }
        return new AddressBookParsedResult(VCardResultParser.toPrimaryValues(list2), stringArray, null, VCardResultParser.toPrimaryValues(list3), VCardResultParser.toTypes(list3), VCardResultParser.toPrimaryValues(list4), VCardResultParser.toTypes(list4), VCardResultParser.toPrimaryValue(list11), VCardResultParser.toPrimaryValue(list5), VCardResultParser.toPrimaryValues(list6), VCardResultParser.toTypes(list6), VCardResultParser.toPrimaryValue(list7), VCardResultParser.toPrimaryValue(list8), VCardResultParser.toPrimaryValue(list9), VCardResultParser.toPrimaryValues(list10), (String[])object);
    }
}

