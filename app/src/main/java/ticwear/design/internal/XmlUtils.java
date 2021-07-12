/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.graphics.Bitmap
 *  android.graphics.Bitmap$CompressFormat
 *  android.graphics.BitmapFactory
 *  android.net.Uri
 *  android.util.Base64
 *  android.util.Xml
 *  org.xmlpull.v1.XmlPullParser
 *  org.xmlpull.v1.XmlPullParserException
 *  org.xmlpull.v1.XmlSerializer
 */
package ticwear.design.internal;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Base64;
import android.util.Xml;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ProtocolException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;
import ticwear.design.internal.FastXmlSerializer;

public class XmlUtils {
    public static final void beginDocument(XmlPullParser xmlPullParser, String string2) throws XmlPullParserException, IOException {
        int n2;
        while ((n2 = xmlPullParser.next()) != 2 && n2 != 1) {
        }
        if (n2 != 2) {
            throw new XmlPullParserException("No start tag found");
        }
        if (!xmlPullParser.getName().equals(string2)) {
            throw new XmlPullParserException("Unexpected start tag: found " + xmlPullParser.getName() + ", expected " + string2);
        }
    }

    public static final boolean convertValueToBoolean(CharSequence charSequence, boolean bl2) {
        block5: {
            block4: {
                boolean bl3 = false;
                if (charSequence == null) {
                    return bl2;
                }
                if (charSequence.equals("1") || charSequence.equals("true")) break block4;
                bl2 = bl3;
                if (!charSequence.equals("TRUE")) break block5;
            }
            bl2 = true;
        }
        return bl2;
    }

    /*
     * Enabled aggressive block sorting
     */
    public static final int convertValueToInt(CharSequence charSequence, int n2) {
        if (charSequence == null) {
            return n2;
        }
        charSequence = charSequence.toString();
        int n3 = 1;
        int n4 = 0;
        int n5 = ((String)charSequence).length();
        n2 = 10;
        if ('-' == ((String)charSequence).charAt(0)) {
            n3 = -1;
            n4 = 0 + 1;
        }
        if ('0' != ((String)charSequence).charAt(n4)) {
            n5 = n4;
            if ('#' != ((String)charSequence).charAt(n4)) return Integer.parseInt(((String)charSequence).substring(n5), n2) * n3;
            n5 = n4 + 1;
            n2 = 16;
            return Integer.parseInt(((String)charSequence).substring(n5), n2) * n3;
        }
        if (n4 == n5 - 1) {
            return 0;
        }
        n2 = ((String)charSequence).charAt(n4 + 1);
        if (120 != n2 && 88 != n2) {
            n5 = n4 + 1;
            n2 = 8;
            return Integer.parseInt(((String)charSequence).substring(n5), n2) * n3;
        }
        n5 = n4 + 2;
        n2 = 16;
        return Integer.parseInt(((String)charSequence).substring(n5), n2) * n3;
    }

    public static final int convertValueToList(CharSequence charSequence, String[] stringArray, int n2) {
        if (charSequence != null) {
            for (int i2 = 0; i2 < stringArray.length; ++i2) {
                if (!charSequence.equals(stringArray[i2])) continue;
                return i2;
            }
        }
        return n2;
    }

    public static int convertValueToUnsignedInt(String string2, int n2) {
        if (string2 == null) {
            return n2;
        }
        return XmlUtils.parseUnsignedIntAttribute(string2);
    }

    public static final void nextElement(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        int n2;
        while ((n2 = xmlPullParser.next()) != 2 && n2 != 1) {
        }
    }

    public static boolean nextElementWithin(XmlPullParser xmlPullParser, int n2) throws IOException, XmlPullParserException {
        int n3;
        do {
            if ((n3 = xmlPullParser.next()) != 1 && (n3 != 3 || xmlPullParser.getDepth() != n2)) continue;
            return false;
        } while (n3 != 2 || xmlPullParser.getDepth() != n2 + 1);
        return true;
    }

    /*
     * Enabled aggressive block sorting
     */
    public static int parseUnsignedIntAttribute(CharSequence charSequence) {
        charSequence = charSequence.toString();
        int n2 = 0;
        int n3 = ((String)charSequence).length();
        int n4 = 10;
        if ('0' != ((String)charSequence).charAt(0)) {
            if ('#' != ((String)charSequence).charAt(0)) return (int)Long.parseLong(((String)charSequence).substring(n2), n4);
            n2 = 0 + 1;
            n4 = 16;
            return (int)Long.parseLong(((String)charSequence).substring(n2), n4);
        }
        if (n3 - 1 == 0) {
            return 0;
        }
        n4 = ((String)charSequence).charAt(1);
        if (120 != n4 && 88 != n4) {
            n2 = 0 + 1;
            n4 = 8;
            return (int)Long.parseLong(((String)charSequence).substring(n2), n4);
        }
        n2 = 0 + 2;
        n4 = 16;
        return (int)Long.parseLong(((String)charSequence).substring(n2), n4);
    }

    public static Bitmap readBitmapAttribute(XmlPullParser object, String string2) {
        if ((object = (Object)XmlUtils.readByteArrayAttribute(object, string2)) != null) {
            return BitmapFactory.decodeByteArray((byte[])object, (int)0, (int)((XmlPullParser)object).length);
        }
        return null;
    }

    public static boolean readBooleanAttribute(XmlPullParser xmlPullParser, String string2) {
        return Boolean.parseBoolean(xmlPullParser.getAttributeValue(null, string2));
    }

    public static boolean readBooleanAttribute(XmlPullParser object, String string2, boolean bl2) {
        if ((object = object.getAttributeValue(null, string2)) == null) {
            return bl2;
        }
        return Boolean.parseBoolean((String)object);
    }

    public static byte[] readByteArrayAttribute(XmlPullParser object, String string2) {
        Object var2_2 = null;
        string2 = object.getAttributeValue(null, string2);
        object = var2_2;
        if (string2 != null) {
            object = Base64.decode((String)string2, (int)0);
        }
        return object;
    }

    public static float readFloatAttribute(XmlPullParser object, String string2) throws IOException {
        object = object.getAttributeValue(null, string2);
        try {
            float f2 = Float.parseFloat((String)object);
            return f2;
        }
        catch (NumberFormatException numberFormatException) {
            throw new ProtocolException("problem parsing " + string2 + "=" + (String)object + " as long");
        }
    }

    public static int readIntAttribute(XmlPullParser object, String string2) throws IOException {
        object = object.getAttributeValue(null, string2);
        try {
            int n2 = Integer.parseInt((String)object);
            return n2;
        }
        catch (NumberFormatException numberFormatException) {
            throw new ProtocolException("problem parsing " + string2 + "=" + (String)object + " as int");
        }
    }

    public static int readIntAttribute(XmlPullParser object, String string2, int n2) {
        object = object.getAttributeValue(null, string2);
        try {
            int n3 = Integer.parseInt((String)object);
            return n3;
        }
        catch (NumberFormatException numberFormatException) {
            return n2;
        }
    }

    public static final ArrayList readListXml(InputStream inputStream) throws XmlPullParserException, IOException {
        XmlPullParser xmlPullParser = Xml.newPullParser();
        xmlPullParser.setInput(inputStream, null);
        return (ArrayList)XmlUtils.readValueXml(xmlPullParser, new String[1]);
    }

    public static long readLongAttribute(XmlPullParser object, String string2) throws IOException {
        object = object.getAttributeValue(null, string2);
        try {
            long l2 = Long.parseLong((String)object);
            return l2;
        }
        catch (NumberFormatException numberFormatException) {
            throw new ProtocolException("problem parsing " + string2 + "=" + (String)object + " as long");
        }
    }

    public static long readLongAttribute(XmlPullParser object, String string2, long l2) {
        object = object.getAttributeValue(null, string2);
        try {
            long l3 = Long.parseLong((String)object);
            return l3;
        }
        catch (NumberFormatException numberFormatException) {
            return l2;
        }
    }

    public static final HashMap<String, ?> readMapXml(InputStream inputStream) throws XmlPullParserException, IOException {
        XmlPullParser xmlPullParser = Xml.newPullParser();
        xmlPullParser.setInput(inputStream, null);
        return (HashMap)XmlUtils.readValueXml(xmlPullParser, new String[1]);
    }

    public static final HashSet readSetXml(InputStream inputStream) throws XmlPullParserException, IOException {
        XmlPullParser xmlPullParser = Xml.newPullParser();
        xmlPullParser.setInput(inputStream, null);
        return (HashSet)XmlUtils.readValueXml(xmlPullParser, new String[1]);
    }

    public static String readStringAttribute(XmlPullParser xmlPullParser, String string2) {
        return xmlPullParser.getAttributeValue(null, string2);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static final boolean[] readThisBooleanArrayXml(XmlPullParser xmlPullParser, String string2, String[] objectArray) throws XmlPullParserException, IOException {
        int n2;
        int n3;
        try {
            n3 = Integer.parseInt(xmlPullParser.getAttributeValue(null, "num"));
        }
        catch (NullPointerException nullPointerException) {
            throw new XmlPullParserException("Need num attribute in string-array");
        }
        catch (NumberFormatException numberFormatException) {
            throw new XmlPullParserException("Not a number in num attribute in string-array");
        }
        xmlPullParser.next();
        boolean[] blArray = new boolean[n3];
        n3 = 0;
        int n4 = xmlPullParser.getEventType();
        do {
            int n5;
            if (n4 == 2) {
                if (!xmlPullParser.getName().equals("item")) {
                    throw new XmlPullParserException("Expected item tag at: " + xmlPullParser.getName());
                }
                try {
                    blArray[n3] = Boolean.valueOf(xmlPullParser.getAttributeValue(null, "value"));
                    n5 = n3;
                }
                catch (NullPointerException nullPointerException) {
                    throw new XmlPullParserException("Need value attribute in item");
                }
                catch (NumberFormatException numberFormatException) {
                    throw new XmlPullParserException("Not a number in value attribute in item");
                }
            } else {
                n5 = n3;
                if (n4 == 3) {
                    if (xmlPullParser.getName().equals(string2)) {
                        return blArray;
                    }
                    if (!xmlPullParser.getName().equals("item")) {
                        throw new XmlPullParserException("Expected " + string2 + " end tag at: " + xmlPullParser.getName());
                    }
                    n5 = n3 + 1;
                }
            }
            n4 = n2 = xmlPullParser.next();
            n3 = n5;
        } while (n2 != 1);
        throw new XmlPullParserException("Document ended before " + string2 + " end tag");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static final double[] readThisDoubleArrayXml(XmlPullParser xmlPullParser, String string2, String[] objectArray) throws XmlPullParserException, IOException {
        int n2;
        int n3;
        try {
            n3 = Integer.parseInt(xmlPullParser.getAttributeValue(null, "num"));
        }
        catch (NullPointerException nullPointerException) {
            throw new XmlPullParserException("Need num attribute in double-array");
        }
        catch (NumberFormatException numberFormatException) {
            throw new XmlPullParserException("Not a number in num attribute in double-array");
        }
        xmlPullParser.next();
        double[] dArray = new double[n3];
        n3 = 0;
        int n4 = xmlPullParser.getEventType();
        do {
            int n5;
            if (n4 == 2) {
                if (!xmlPullParser.getName().equals("item")) {
                    throw new XmlPullParserException("Expected item tag at: " + xmlPullParser.getName());
                }
                try {
                    dArray[n3] = Double.parseDouble(xmlPullParser.getAttributeValue(null, "value"));
                    n5 = n3;
                }
                catch (NullPointerException nullPointerException) {
                    throw new XmlPullParserException("Need value attribute in item");
                }
                catch (NumberFormatException numberFormatException) {
                    throw new XmlPullParserException("Not a number in value attribute in item");
                }
            } else {
                n5 = n3;
                if (n4 == 3) {
                    if (xmlPullParser.getName().equals(string2)) {
                        return dArray;
                    }
                    if (!xmlPullParser.getName().equals("item")) {
                        throw new XmlPullParserException("Expected " + string2 + " end tag at: " + xmlPullParser.getName());
                    }
                    n5 = n3 + 1;
                }
            }
            n4 = n2 = xmlPullParser.next();
            n3 = n5;
        } while (n2 != 1);
        throw new XmlPullParserException("Document ended before " + string2 + " end tag");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static final int[] readThisIntArrayXml(XmlPullParser xmlPullParser, String string2, String[] objectArray) throws XmlPullParserException, IOException {
        int n2;
        int n3;
        try {
            n3 = Integer.parseInt(xmlPullParser.getAttributeValue(null, "num"));
        }
        catch (NullPointerException nullPointerException) {
            throw new XmlPullParserException("Need num attribute in byte-array");
        }
        catch (NumberFormatException numberFormatException) {
            throw new XmlPullParserException("Not a number in num attribute in byte-array");
        }
        xmlPullParser.next();
        int[] nArray = new int[n3];
        n3 = 0;
        int n4 = xmlPullParser.getEventType();
        do {
            int n5;
            if (n4 == 2) {
                if (!xmlPullParser.getName().equals("item")) {
                    throw new XmlPullParserException("Expected item tag at: " + xmlPullParser.getName());
                }
                try {
                    nArray[n3] = Integer.parseInt(xmlPullParser.getAttributeValue(null, "value"));
                    n5 = n3;
                }
                catch (NullPointerException nullPointerException) {
                    throw new XmlPullParserException("Need value attribute in item");
                }
                catch (NumberFormatException numberFormatException) {
                    throw new XmlPullParserException("Not a number in value attribute in item");
                }
            } else {
                n5 = n3;
                if (n4 == 3) {
                    if (xmlPullParser.getName().equals(string2)) {
                        return nArray;
                    }
                    if (!xmlPullParser.getName().equals("item")) {
                        throw new XmlPullParserException("Expected " + string2 + " end tag at: " + xmlPullParser.getName());
                    }
                    n5 = n3 + 1;
                }
            }
            n4 = n2 = xmlPullParser.next();
            n3 = n5;
        } while (n2 != 1);
        throw new XmlPullParserException("Document ended before " + string2 + " end tag");
    }

    public static final ArrayList readThisListXml(XmlPullParser xmlPullParser, String string2, String[] stringArray) throws XmlPullParserException, IOException {
        return XmlUtils.readThisListXml(xmlPullParser, string2, stringArray, null);
    }

    /*
     * Enabled aggressive block sorting
     */
    private static final ArrayList readThisListXml(XmlPullParser xmlPullParser, String string2, String[] stringArray, ReadMapCallback readMapCallback) throws XmlPullParserException, IOException {
        int n2;
        ArrayList<Object> arrayList = new ArrayList<Object>();
        int n3 = xmlPullParser.getEventType();
        do {
            if (n3 == 2) {
                arrayList.add(XmlUtils.readThisValueXml(xmlPullParser, stringArray, readMapCallback));
            } else if (n3 == 3) {
                if (!xmlPullParser.getName().equals(string2)) throw new XmlPullParserException("Expected " + string2 + " end tag at: " + xmlPullParser.getName());
                return arrayList;
            }
            n3 = n2 = xmlPullParser.next();
        } while (n2 != 1);
        throw new XmlPullParserException("Document ended before " + string2 + " end tag");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static final long[] readThisLongArrayXml(XmlPullParser xmlPullParser, String string2, String[] objectArray) throws XmlPullParserException, IOException {
        int n2;
        int n3;
        try {
            n3 = Integer.parseInt(xmlPullParser.getAttributeValue(null, "num"));
        }
        catch (NullPointerException nullPointerException) {
            throw new XmlPullParserException("Need num attribute in long-array");
        }
        catch (NumberFormatException numberFormatException) {
            throw new XmlPullParserException("Not a number in num attribute in long-array");
        }
        xmlPullParser.next();
        long[] lArray = new long[n3];
        n3 = 0;
        int n4 = xmlPullParser.getEventType();
        do {
            int n5;
            if (n4 == 2) {
                if (!xmlPullParser.getName().equals("item")) {
                    throw new XmlPullParserException("Expected item tag at: " + xmlPullParser.getName());
                }
                try {
                    lArray[n3] = Long.parseLong(xmlPullParser.getAttributeValue(null, "value"));
                    n5 = n3;
                }
                catch (NullPointerException nullPointerException) {
                    throw new XmlPullParserException("Need value attribute in item");
                }
                catch (NumberFormatException numberFormatException) {
                    throw new XmlPullParserException("Not a number in value attribute in item");
                }
            } else {
                n5 = n3;
                if (n4 == 3) {
                    if (xmlPullParser.getName().equals(string2)) {
                        return lArray;
                    }
                    if (!xmlPullParser.getName().equals("item")) {
                        throw new XmlPullParserException("Expected " + string2 + " end tag at: " + xmlPullParser.getName());
                    }
                    n5 = n3 + 1;
                }
            }
            n4 = n2 = xmlPullParser.next();
            n3 = n5;
        } while (n2 != 1);
        throw new XmlPullParserException("Document ended before " + string2 + " end tag");
    }

    public static final HashMap<String, ?> readThisMapXml(XmlPullParser xmlPullParser, String string2, String[] stringArray) throws XmlPullParserException, IOException {
        return XmlUtils.readThisMapXml(xmlPullParser, string2, stringArray, null);
    }

    /*
     * Enabled aggressive block sorting
     */
    public static final HashMap<String, ?> readThisMapXml(XmlPullParser xmlPullParser, String string2, String[] stringArray, ReadMapCallback readMapCallback) throws XmlPullParserException, IOException {
        int n2;
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        int n3 = xmlPullParser.getEventType();
        do {
            if (n3 == 2) {
                Object object = XmlUtils.readThisValueXml(xmlPullParser, stringArray, readMapCallback);
                hashMap.put(stringArray[0], object);
            } else if (n3 == 3) {
                if (!xmlPullParser.getName().equals(string2)) throw new XmlPullParserException("Expected " + string2 + " end tag at: " + xmlPullParser.getName());
                return hashMap;
            }
            n3 = n2 = xmlPullParser.next();
        } while (n2 != 1);
        throw new XmlPullParserException("Document ended before " + string2 + " end tag");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static final Object readThisPrimitiveValueXml(XmlPullParser object, String string2) throws XmlPullParserException, IOException {
        Boolean bl2 = null;
        try {
            if (string2.equals("int")) {
                return Integer.parseInt(object.getAttributeValue(null, "value"));
            }
            if (string2.equals("long")) {
                return Long.valueOf(object.getAttributeValue(null, "value"));
            }
            if (string2.equals("float")) {
                return new Float(object.getAttributeValue(null, "value"));
            }
            if (string2.equals("double")) {
                return new Double(object.getAttributeValue(null, "value"));
            }
            if (!string2.equals("boolean")) return bl2;
            return Boolean.valueOf(object.getAttributeValue(null, "value"));
        }
        catch (NullPointerException nullPointerException) {
            throw new XmlPullParserException("Need value attribute in <" + string2 + ">");
        }
        catch (NumberFormatException numberFormatException) {
            throw new XmlPullParserException("Not a number in value attribute in <" + string2 + ">");
        }
    }

    public static final HashSet readThisSetXml(XmlPullParser xmlPullParser, String string2, String[] stringArray) throws XmlPullParserException, IOException {
        return XmlUtils.readThisSetXml(xmlPullParser, string2, stringArray, null);
    }

    /*
     * Enabled aggressive block sorting
     */
    private static final HashSet readThisSetXml(XmlPullParser xmlPullParser, String string2, String[] stringArray, ReadMapCallback readMapCallback) throws XmlPullParserException, IOException {
        int n2;
        HashSet<Object> hashSet = new HashSet<Object>();
        int n3 = xmlPullParser.getEventType();
        do {
            if (n3 == 2) {
                hashSet.add(XmlUtils.readThisValueXml(xmlPullParser, stringArray, readMapCallback));
            } else if (n3 == 3) {
                if (!xmlPullParser.getName().equals(string2)) throw new XmlPullParserException("Expected " + string2 + " end tag at: " + xmlPullParser.getName());
                return hashSet;
            }
            n3 = n2 = xmlPullParser.next();
        } while (n2 != 1);
        throw new XmlPullParserException("Document ended before " + string2 + " end tag");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static final String[] readThisStringArrayXml(XmlPullParser xmlPullParser, String string2, String[] stringArray) throws XmlPullParserException, IOException {
        int n2;
        int n3;
        try {
            n3 = Integer.parseInt(xmlPullParser.getAttributeValue(null, "num"));
        }
        catch (NullPointerException nullPointerException) {
            throw new XmlPullParserException("Need num attribute in string-array");
        }
        catch (NumberFormatException numberFormatException) {
            throw new XmlPullParserException("Not a number in num attribute in string-array");
        }
        xmlPullParser.next();
        stringArray = new String[n3];
        n3 = 0;
        int n4 = xmlPullParser.getEventType();
        do {
            int n5;
            if (n4 == 2) {
                if (!xmlPullParser.getName().equals("item")) {
                    throw new XmlPullParserException("Expected item tag at: " + xmlPullParser.getName());
                }
                try {
                    stringArray[n3] = xmlPullParser.getAttributeValue(null, "value");
                    n5 = n3;
                }
                catch (NullPointerException nullPointerException) {
                    throw new XmlPullParserException("Need value attribute in item");
                }
                catch (NumberFormatException numberFormatException) {
                    throw new XmlPullParserException("Not a number in value attribute in item");
                }
            } else {
                n5 = n3;
                if (n4 == 3) {
                    if (xmlPullParser.getName().equals(string2)) {
                        return stringArray;
                    }
                    if (!xmlPullParser.getName().equals("item")) {
                        throw new XmlPullParserException("Expected " + string2 + " end tag at: " + xmlPullParser.getName());
                    }
                    n5 = n3 + 1;
                }
            }
            n4 = n2 = xmlPullParser.next();
            n3 = n5;
        } while (n2 != 1);
        throw new XmlPullParserException("Document ended before " + string2 + " end tag");
    }

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    private static final Object readThisValueXml(XmlPullParser var0, String[] var1_1, ReadMapCallback var2_2) throws XmlPullParserException, IOException {
        block20: {
            block17: {
                block18: {
                    block19: {
                        var6_3 = var0 /* !! */ .getAttributeValue(null, "name");
                        var7_4 = var0 /* !! */ .getName();
                        if (!var7_4.equals("null")) break block19;
                        var4_5 = null;
lbl5:
                        // 3 sources

                        while ((var3_6 = var0 /* !! */ .next()) != 1) {
                            if (var3_6 != 3) break block17;
                            if (var0 /* !! */ .getName().equals(var7_4)) {
                                var1_1[0] = var6_3;
                                return var4_5;
                            }
                            break block18;
                        }
                        break block20;
                    }
                    if (var7_4.equals("string")) {
                        var2_2 = "";
                        while ((var3_7 = var0 /* !! */ .next()) != 1) {
                            if (var3_7 == 3) {
                                if (var0 /* !! */ .getName().equals("string")) {
                                    var1_1[0] = var6_3;
                                    return var2_2;
                                }
                                throw new XmlPullParserException("Unexpected end tag in <string>: " + var0 /* !! */ .getName());
                            }
                            if (var3_7 == 4) {
                                var2_2 = (String)var2_2 + var0 /* !! */ .getText();
                                continue;
                            }
                            if (var3_7 != 2) continue;
                            throw new XmlPullParserException("Unexpected start tag in <string>: " + var0 /* !! */ .getName());
                        }
                        throw new XmlPullParserException("Unexpected end of document in <string>");
                    }
                    var4_5 = var5_8 = XmlUtils.readThisPrimitiveValueXml((XmlPullParser)var0 /* !! */ , var7_4);
                    if (var5_8 != null) ** GOTO lbl5
                    if (var7_4.equals("int-array")) {
                        var0 /* !! */  = (String[])XmlUtils.readThisIntArrayXml((XmlPullParser)var0 /* !! */ , "int-array", var1_1);
                        var1_1[0] = var6_3;
                        return var0 /* !! */ ;
                    }
                    if (var7_4.equals("long-array")) {
                        var0 /* !! */  = (String[])XmlUtils.readThisLongArrayXml((XmlPullParser)var0 /* !! */ , "long-array", var1_1);
                        var1_1[0] = var6_3;
                        return var0 /* !! */ ;
                    }
                    if (var7_4.equals("double-array")) {
                        var0 /* !! */  = (String[])XmlUtils.readThisDoubleArrayXml((XmlPullParser)var0 /* !! */ , "double-array", var1_1);
                        var1_1[0] = var6_3;
                        return var0 /* !! */ ;
                    }
                    if (var7_4.equals("string-array")) {
                        var0 /* !! */  = XmlUtils.readThisStringArrayXml((XmlPullParser)var0 /* !! */ , "string-array", var1_1);
                        var1_1[0] = var6_3;
                        return var0 /* !! */ ;
                    }
                    if (var7_4.equals("boolean-array")) {
                        var0 /* !! */  = (String[])XmlUtils.readThisBooleanArrayXml((XmlPullParser)var0 /* !! */ , "boolean-array", var1_1);
                        var1_1[0] = var6_3;
                        return var0 /* !! */ ;
                    }
                    if (var7_4.equals("map")) {
                        var0 /* !! */ .next();
                        var0 /* !! */  = XmlUtils.readThisMapXml((XmlPullParser)var0 /* !! */ , "map", var1_1);
                        var1_1[0] = var6_3;
                        return var0 /* !! */ ;
                    }
                    if (var7_4.equals("list")) {
                        var0 /* !! */ .next();
                        var0 /* !! */  = XmlUtils.readThisListXml((XmlPullParser)var0 /* !! */ , "list", var1_1);
                        var1_1[0] = var6_3;
                        return var0 /* !! */ ;
                    }
                    if (var7_4.equals("set")) {
                        var0 /* !! */ .next();
                        var0 /* !! */  = XmlUtils.readThisSetXml((XmlPullParser)var0 /* !! */ , "set", var1_1);
                        var1_1[0] = var6_3;
                        return var0 /* !! */ ;
                    }
                    if (var2_2 != null) {
                        var0 /* !! */  = var2_2.readThisUnknownObjectXml((XmlPullParser)var0 /* !! */ , var7_4);
                        var1_1[0] = var6_3;
                        return var0 /* !! */ ;
                    }
                    throw new XmlPullParserException("Unknown tag: " + var7_4);
                }
                throw new XmlPullParserException("Unexpected end tag in <" + var7_4 + ">: " + var0 /* !! */ .getName());
            }
            if (var3_6 == 4) {
                throw new XmlPullParserException("Unexpected text in <" + var7_4 + ">: " + var0 /* !! */ .getName());
            }
            if (var3_6 != 2) ** GOTO lbl5
            throw new XmlPullParserException("Unexpected start tag in <" + var7_4 + ">: " + var0 /* !! */ .getName());
        }
        throw new XmlPullParserException("Unexpected end of document in <" + var7_4 + ">");
    }

    public static Uri readUriAttribute(XmlPullParser xmlPullParser, String string2) {
        Object var2_2 = null;
        string2 = xmlPullParser.getAttributeValue(null, string2);
        xmlPullParser = var2_2;
        if (string2 != null) {
            xmlPullParser = Uri.parse((String)string2);
        }
        return xmlPullParser;
    }

    public static final Object readValueXml(XmlPullParser xmlPullParser, String[] stringArray) throws XmlPullParserException, IOException {
        int n2;
        int n3 = xmlPullParser.getEventType();
        do {
            if (n3 == 2) {
                return XmlUtils.readThisValueXml(xmlPullParser, stringArray, null);
            }
            if (n3 == 3) {
                throw new XmlPullParserException("Unexpected end tag at: " + xmlPullParser.getName());
            }
            if (n3 == 4) {
                throw new XmlPullParserException("Unexpected text: " + xmlPullParser.getText());
            }
            n3 = n2 = xmlPullParser.next();
        } while (n2 != 1);
        throw new XmlPullParserException("Unexpected end of document");
    }

    public static void skipCurrentTag(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        int n2;
        int n3 = xmlPullParser.getDepth();
        while ((n2 = xmlPullParser.next()) != 1 && (n2 != 3 || xmlPullParser.getDepth() > n3)) {
        }
    }

    @Deprecated
    public static void writeBitmapAttribute(XmlSerializer xmlSerializer, String string2, Bitmap bitmap) throws IOException {
        if (bitmap != null) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, (OutputStream)byteArrayOutputStream);
            XmlUtils.writeByteArrayAttribute(xmlSerializer, string2, byteArrayOutputStream.toByteArray());
        }
    }

    public static final void writeBooleanArrayXml(boolean[] blArray, String string2, XmlSerializer xmlSerializer) throws XmlPullParserException, IOException {
        if (blArray == null) {
            xmlSerializer.startTag(null, "null");
            xmlSerializer.endTag(null, "null");
            return;
        }
        xmlSerializer.startTag(null, "boolean-array");
        if (string2 != null) {
            xmlSerializer.attribute(null, "name", string2);
        }
        int n2 = blArray.length;
        xmlSerializer.attribute(null, "num", Integer.toString(n2));
        for (int i2 = 0; i2 < n2; ++i2) {
            xmlSerializer.startTag(null, "item");
            xmlSerializer.attribute(null, "value", Boolean.toString(blArray[i2]));
            xmlSerializer.endTag(null, "item");
        }
        xmlSerializer.endTag(null, "boolean-array");
    }

    public static void writeBooleanAttribute(XmlSerializer xmlSerializer, String string2, boolean bl2) throws IOException {
        xmlSerializer.attribute(null, string2, Boolean.toString(bl2));
    }

    public static void writeByteArrayAttribute(XmlSerializer xmlSerializer, String string2, byte[] byArray) throws IOException {
        if (byArray != null) {
            xmlSerializer.attribute(null, string2, Base64.encodeToString((byte[])byArray, (int)0));
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public static final void writeByteArrayXml(byte[] byArray, String charSequence, XmlSerializer xmlSerializer) throws XmlPullParserException, IOException {
        if (byArray == null) {
            xmlSerializer.startTag(null, "null");
            xmlSerializer.endTag(null, "null");
            return;
        }
        xmlSerializer.startTag(null, "byte-array");
        if (charSequence != null) {
            xmlSerializer.attribute(null, "name", (String)charSequence);
        }
        int n2 = byArray.length;
        xmlSerializer.attribute(null, "num", Integer.toString(n2));
        charSequence = new StringBuilder(byArray.length * 2);
        int n3 = 0;
        while (true) {
            if (n3 >= n2) {
                xmlSerializer.text(((StringBuilder)charSequence).toString());
                xmlSerializer.endTag(null, "byte-array");
                return;
            }
            byte by2 = byArray[n3];
            int n4 = by2 >> 4;
            n4 = n4 >= 10 ? n4 + 97 - 10 : (n4 += 48);
            ((StringBuilder)charSequence).append(n4);
            n4 = by2 & 0xFF;
            n4 = n4 >= 10 ? n4 + 97 - 10 : (n4 += 48);
            ((StringBuilder)charSequence).append(n4);
            ++n3;
        }
    }

    public static final void writeDoubleArrayXml(double[] dArray, String string2, XmlSerializer xmlSerializer) throws XmlPullParserException, IOException {
        if (dArray == null) {
            xmlSerializer.startTag(null, "null");
            xmlSerializer.endTag(null, "null");
            return;
        }
        xmlSerializer.startTag(null, "double-array");
        if (string2 != null) {
            xmlSerializer.attribute(null, "name", string2);
        }
        int n2 = dArray.length;
        xmlSerializer.attribute(null, "num", Integer.toString(n2));
        for (int i2 = 0; i2 < n2; ++i2) {
            xmlSerializer.startTag(null, "item");
            xmlSerializer.attribute(null, "value", Double.toString(dArray[i2]));
            xmlSerializer.endTag(null, "item");
        }
        xmlSerializer.endTag(null, "double-array");
    }

    public static void writeFloatAttribute(XmlSerializer xmlSerializer, String string2, float f2) throws IOException {
        xmlSerializer.attribute(null, string2, Float.toString(f2));
    }

    public static final void writeIntArrayXml(int[] nArray, String string2, XmlSerializer xmlSerializer) throws XmlPullParserException, IOException {
        if (nArray == null) {
            xmlSerializer.startTag(null, "null");
            xmlSerializer.endTag(null, "null");
            return;
        }
        xmlSerializer.startTag(null, "int-array");
        if (string2 != null) {
            xmlSerializer.attribute(null, "name", string2);
        }
        int n2 = nArray.length;
        xmlSerializer.attribute(null, "num", Integer.toString(n2));
        for (int i2 = 0; i2 < n2; ++i2) {
            xmlSerializer.startTag(null, "item");
            xmlSerializer.attribute(null, "value", Integer.toString(nArray[i2]));
            xmlSerializer.endTag(null, "item");
        }
        xmlSerializer.endTag(null, "int-array");
    }

    public static void writeIntAttribute(XmlSerializer xmlSerializer, String string2, int n2) throws IOException {
        xmlSerializer.attribute(null, string2, Integer.toString(n2));
    }

    public static final void writeListXml(List list, OutputStream outputStream) throws XmlPullParserException, IOException {
        XmlSerializer xmlSerializer = Xml.newSerializer();
        xmlSerializer.setOutput(outputStream, "utf-8");
        xmlSerializer.startDocument(null, Boolean.valueOf(true));
        xmlSerializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
        XmlUtils.writeListXml(list, null, xmlSerializer);
        xmlSerializer.endDocument();
    }

    public static final void writeListXml(List list, String string2, XmlSerializer xmlSerializer) throws XmlPullParserException, IOException {
        if (list == null) {
            xmlSerializer.startTag(null, "null");
            xmlSerializer.endTag(null, "null");
            return;
        }
        xmlSerializer.startTag(null, "list");
        if (string2 != null) {
            xmlSerializer.attribute(null, "name", string2);
        }
        int n2 = list.size();
        for (int i2 = 0; i2 < n2; ++i2) {
            XmlUtils.writeValueXml(list.get(i2), null, xmlSerializer);
        }
        xmlSerializer.endTag(null, "list");
    }

    public static final void writeLongArrayXml(long[] lArray, String string2, XmlSerializer xmlSerializer) throws XmlPullParserException, IOException {
        if (lArray == null) {
            xmlSerializer.startTag(null, "null");
            xmlSerializer.endTag(null, "null");
            return;
        }
        xmlSerializer.startTag(null, "long-array");
        if (string2 != null) {
            xmlSerializer.attribute(null, "name", string2);
        }
        int n2 = lArray.length;
        xmlSerializer.attribute(null, "num", Integer.toString(n2));
        for (int i2 = 0; i2 < n2; ++i2) {
            xmlSerializer.startTag(null, "item");
            xmlSerializer.attribute(null, "value", Long.toString(lArray[i2]));
            xmlSerializer.endTag(null, "item");
        }
        xmlSerializer.endTag(null, "long-array");
    }

    public static void writeLongAttribute(XmlSerializer xmlSerializer, String string2, long l2) throws IOException {
        xmlSerializer.attribute(null, string2, Long.toString(l2));
    }

    public static final void writeMapXml(Map map, OutputStream outputStream) throws XmlPullParserException, IOException {
        FastXmlSerializer fastXmlSerializer = new FastXmlSerializer();
        fastXmlSerializer.setOutput(outputStream, "utf-8");
        fastXmlSerializer.startDocument(null, true);
        fastXmlSerializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
        XmlUtils.writeMapXml(map, null, fastXmlSerializer);
        fastXmlSerializer.endDocument();
    }

    public static final void writeMapXml(Map map, String string2, XmlSerializer xmlSerializer) throws XmlPullParserException, IOException {
        XmlUtils.writeMapXml(map, string2, xmlSerializer, null);
    }

    public static final void writeMapXml(Map map, String string2, XmlSerializer xmlSerializer, WriteMapCallback writeMapCallback) throws XmlPullParserException, IOException {
        if (map == null) {
            xmlSerializer.startTag(null, "null");
            xmlSerializer.endTag(null, "null");
            return;
        }
        xmlSerializer.startTag(null, "map");
        if (string2 != null) {
            xmlSerializer.attribute(null, "name", string2);
        }
        XmlUtils.writeMapXml(map, xmlSerializer, writeMapCallback);
        xmlSerializer.endTag(null, "map");
    }

    /*
     * Enabled aggressive block sorting
     */
    public static final void writeMapXml(Map iterator, XmlSerializer xmlSerializer, WriteMapCallback writeMapCallback) throws XmlPullParserException, IOException {
        if (iterator != null) {
            for (Map.Entry entry : iterator.entrySet()) {
                XmlUtils.writeValueXml(entry.getValue(), (String)entry.getKey(), xmlSerializer, writeMapCallback);
            }
        }
    }

    public static final void writeSetXml(Set object, String string2, XmlSerializer xmlSerializer) throws XmlPullParserException, IOException {
        if (object == null) {
            xmlSerializer.startTag(null, "null");
            xmlSerializer.endTag(null, "null");
            return;
        }
        xmlSerializer.startTag(null, "set");
        if (string2 != null) {
            xmlSerializer.attribute(null, "name", string2);
        }
        object = object.iterator();
        while (object.hasNext()) {
            XmlUtils.writeValueXml(object.next(), null, xmlSerializer);
        }
        xmlSerializer.endTag(null, "set");
    }

    public static final void writeStringArrayXml(String[] stringArray, String string2, XmlSerializer xmlSerializer) throws XmlPullParserException, IOException {
        if (stringArray == null) {
            xmlSerializer.startTag(null, "null");
            xmlSerializer.endTag(null, "null");
            return;
        }
        xmlSerializer.startTag(null, "string-array");
        if (string2 != null) {
            xmlSerializer.attribute(null, "name", string2);
        }
        int n2 = stringArray.length;
        xmlSerializer.attribute(null, "num", Integer.toString(n2));
        for (int i2 = 0; i2 < n2; ++i2) {
            xmlSerializer.startTag(null, "item");
            xmlSerializer.attribute(null, "value", stringArray[i2]);
            xmlSerializer.endTag(null, "item");
        }
        xmlSerializer.endTag(null, "string-array");
    }

    public static void writeStringAttribute(XmlSerializer xmlSerializer, String string2, String string3) throws IOException {
        if (string3 != null) {
            xmlSerializer.attribute(null, string2, string3);
        }
    }

    public static void writeUriAttribute(XmlSerializer xmlSerializer, String string2, Uri uri) throws IOException {
        if (uri != null) {
            xmlSerializer.attribute(null, string2, uri.toString());
        }
    }

    public static final void writeValueXml(Object object, String string2, XmlSerializer xmlSerializer) throws XmlPullParserException, IOException {
        XmlUtils.writeValueXml(object, string2, xmlSerializer, null);
    }

    /*
     * Enabled aggressive block sorting
     */
    private static final void writeValueXml(Object object, String string2, XmlSerializer xmlSerializer, WriteMapCallback object2) throws XmlPullParserException, IOException {
        block25: {
            block21: {
                block24: {
                    block23: {
                        block22: {
                            block20: {
                                if (object == null) {
                                    xmlSerializer.startTag(null, "null");
                                    if (string2 != null) {
                                        xmlSerializer.attribute(null, "name", string2);
                                    }
                                    xmlSerializer.endTag(null, "null");
                                    return;
                                }
                                if (object instanceof String) {
                                    xmlSerializer.startTag(null, "string");
                                    if (string2 != null) {
                                        xmlSerializer.attribute(null, "name", string2);
                                    }
                                    xmlSerializer.text(object.toString());
                                    xmlSerializer.endTag(null, "string");
                                    return;
                                }
                                if (!(object instanceof Integer)) break block20;
                                object2 = "int";
                                break block21;
                            }
                            if (!(object instanceof Long)) break block22;
                            object2 = "long";
                            break block21;
                        }
                        if (!(object instanceof Float)) break block23;
                        object2 = "float";
                        break block21;
                    }
                    if (!(object instanceof Double)) break block24;
                    object2 = "double";
                    break block21;
                }
                if (!(object instanceof Boolean)) break block25;
                object2 = "boolean";
            }
            xmlSerializer.startTag(null, (String)object2);
            if (string2 != null) {
                xmlSerializer.attribute(null, "name", string2);
            }
            xmlSerializer.attribute(null, "value", object.toString());
            xmlSerializer.endTag(null, (String)object2);
            return;
        }
        if (object instanceof byte[]) {
            XmlUtils.writeByteArrayXml((byte[])object, string2, xmlSerializer);
            return;
        }
        if (object instanceof int[]) {
            XmlUtils.writeIntArrayXml((int[])object, string2, xmlSerializer);
            return;
        }
        if (object instanceof long[]) {
            XmlUtils.writeLongArrayXml((long[])object, string2, xmlSerializer);
            return;
        }
        if (object instanceof double[]) {
            XmlUtils.writeDoubleArrayXml((double[])object, string2, xmlSerializer);
            return;
        }
        if (object instanceof String[]) {
            XmlUtils.writeStringArrayXml((String[])object, string2, xmlSerializer);
            return;
        }
        if (object instanceof boolean[]) {
            XmlUtils.writeBooleanArrayXml((boolean[])object, string2, xmlSerializer);
            return;
        }
        if (object instanceof Map) {
            XmlUtils.writeMapXml((Map)object, string2, xmlSerializer);
            return;
        }
        if (object instanceof List) {
            XmlUtils.writeListXml((List)object, string2, xmlSerializer);
            return;
        }
        if (object instanceof Set) {
            XmlUtils.writeSetXml((Set)object, string2, xmlSerializer);
            return;
        }
        if (object instanceof CharSequence) {
            xmlSerializer.startTag(null, "string");
            if (string2 != null) {
                xmlSerializer.attribute(null, "name", string2);
            }
            xmlSerializer.text(object.toString());
            xmlSerializer.endTag(null, "string");
            return;
        }
        if (object2 != null) {
            object2.writeUnknownObject(object, string2, xmlSerializer);
            return;
        }
        throw new RuntimeException("writeValueXml: unable to write value " + object);
    }

    public static interface ReadMapCallback {
        public Object readThisUnknownObjectXml(XmlPullParser var1, String var2) throws XmlPullParserException, IOException;
    }

    public static interface WriteMapCallback {
        public void writeUnknownObject(Object var1, String var2, XmlSerializer var3) throws XmlPullParserException, IOException;
    }
}

