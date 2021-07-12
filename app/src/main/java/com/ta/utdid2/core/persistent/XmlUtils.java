/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.util.Xml
 *  org.xmlpull.v1.XmlPullParser
 *  org.xmlpull.v1.XmlPullParserException
 *  org.xmlpull.v1.XmlSerializer
 */
package com.ta.utdid2.core.persistent;

import android.util.Xml;
import com.ta.utdid2.core.persistent.FastXmlSerializer;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

class XmlUtils {
    XmlUtils() {
    }

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
        charSequence = ((Object)charSequence).toString();
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

    public static final int convertValueToUnsignedInt(String string2, int n2) {
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

    /*
     * Enabled aggressive block sorting
     */
    public static final int parseUnsignedIntAttribute(CharSequence charSequence) {
        charSequence = ((Object)charSequence).toString();
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

    public static final ArrayList readListXml(InputStream inputStream) throws XmlPullParserException, IOException {
        XmlPullParser xmlPullParser = Xml.newPullParser();
        xmlPullParser.setInput(inputStream, null);
        return (ArrayList)XmlUtils.readValueXml(xmlPullParser, new String[1]);
    }

    public static final HashMap readMapXml(InputStream inputStream) throws XmlPullParserException, IOException {
        XmlPullParser xmlPullParser = Xml.newPullParser();
        xmlPullParser.setInput(inputStream, null);
        return (HashMap)XmlUtils.readValueXml(xmlPullParser, new String[1]);
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

    /*
     * Enabled aggressive block sorting
     */
    public static final ArrayList readThisListXml(XmlPullParser xmlPullParser, String string2, String[] stringArray) throws XmlPullParserException, IOException {
        int n2;
        ArrayList<Object> arrayList = new ArrayList<Object>();
        int n3 = xmlPullParser.getEventType();
        do {
            if (n3 == 2) {
                arrayList.add(XmlUtils.readThisValueXml(xmlPullParser, stringArray));
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
     */
    public static final HashMap readThisMapXml(XmlPullParser xmlPullParser, String string2, String[] stringArray) throws XmlPullParserException, IOException {
        int n2;
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        int n3 = xmlPullParser.getEventType();
        do {
            if (n3 == 2) {
                Object object = XmlUtils.readThisValueXml(xmlPullParser, stringArray);
                if (stringArray[0] == null) {
                    throw new XmlPullParserException("Map value without name attribute: " + xmlPullParser.getName());
                }
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
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    private static final Object readThisValueXml(XmlPullParser object, String[] stringArray) throws XmlPullParserException, IOException {
        String string2;
        block25: {
            int n2;
            String string3;
            block24: {
                block23: {
                    string2 = object.getAttributeValue(null, "name");
                    string3 = object.getName();
                    if (!string3.equals("null")) break block23;
                    Object var3_7 = null;
                    break block24;
                }
                if (!string3.equals("string")) {
                    if (string3.equals("int")) {
                        Integer n3 = Integer.parseInt(object.getAttributeValue(null, "value"));
                    } else if (string3.equals("long")) {
                        Long l2 = Long.valueOf(object.getAttributeValue(null, "value"));
                    } else if (string3.equals("float")) {
                        Float f2 = new Float(object.getAttributeValue(null, "value"));
                    } else if (string3.equals("double")) {
                        Double d2 = new Double(object.getAttributeValue(null, "value"));
                    } else if (string3.equals("boolean")) {
                        Boolean bl2 = Boolean.valueOf(object.getAttributeValue(null, "value"));
                    } else {
                        void var1_4;
                        if (string3.equals("int-array")) {
                            object.next();
                            int[] nArray = XmlUtils.readThisIntArrayXml(object, "int-array", (String[])var1_4);
                            var1_4[0] = string2;
                            return nArray;
                        }
                        if (string3.equals("map")) {
                            object.next();
                            HashMap hashMap = XmlUtils.readThisMapXml(object, "map", (String[])var1_4);
                            var1_4[0] = string2;
                            return hashMap;
                        }
                        if (string3.equals("list")) {
                            object.next();
                            ArrayList arrayList = XmlUtils.readThisListXml(object, "list", (String[])var1_4);
                            var1_4[0] = string2;
                            return arrayList;
                        }
                        throw new XmlPullParserException("Unknown tag: " + string3);
                    }
                }
                break block25;
            }
            do {
                if ((n2 = object.next()) == 1) {
                    throw new XmlPullParserException("Unexpected end of document in <" + string3 + ">");
                }
                if (n2 == 3) {
                    if (object.getName().equals(string3)) {
                        void var3_8;
                        var1_4[0] = string2;
                        return var3_8;
                    }
                    throw new XmlPullParserException("Unexpected end tag in <" + string3 + ">: " + object.getName());
                }
                if (n2 != 4) continue;
                throw new XmlPullParserException("Unexpected text in <" + string3 + ">: " + object.getName());
            } while (n2 != 2);
            throw new XmlPullParserException("Unexpected start tag in <" + string3 + ">: " + object.getName());
        }
        String string4 = "";
        while (true) {
            void var3_10;
            int n4;
            if ((n4 = object.next()) == 1) {
                throw new XmlPullParserException("Unexpected end of document in <string>");
            }
            if (n4 == 3) {
                if (object.getName().equals("string")) {
                    var1_4[0] = string2;
                    return var3_10;
                }
                throw new XmlPullParserException("Unexpected end tag in <string>: " + object.getName());
            }
            if (n4 == 4) {
                String string5 = (String)var3_10 + object.getText();
                continue;
            }
            if (n4 == 2) break;
        }
        throw new XmlPullParserException("Unexpected start tag in <string>: " + object.getName());
    }

    /*
     * Loose catch block
     */
    public static final Object readValueXml(XmlPullParser xmlPullParser, String[] stringArray) throws XmlPullParserException, IOException {
        int n2 = xmlPullParser.getEventType();
        while (true) {
            int n3;
            if (n2 == 2) {
                return XmlUtils.readThisValueXml(xmlPullParser, stringArray);
            }
            if (n2 == 3) {
                throw new XmlPullParserException("Unexpected end tag at: " + xmlPullParser.getName());
            }
            if (n2 == 4) {
                throw new XmlPullParserException("Unexpected text: " + xmlPullParser.getText());
            }
            n2 = n3 = xmlPullParser.next();
            if (n3 != 1) continue;
            break;
        }
        throw new XmlPullParserException("Unexpected end of document");
        catch (Exception exception) {
            throw new XmlPullParserException("Unexpected call next(): " + xmlPullParser.getName());
        }
    }

    public static void skipCurrentTag(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        int n2;
        int n3 = xmlPullParser.getDepth();
        while ((n2 = xmlPullParser.next()) != 1 && (n2 != 3 || xmlPullParser.getDepth() > n3)) {
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

    public static final void writeMapXml(Map map, OutputStream outputStream) throws XmlPullParserException, IOException {
        FastXmlSerializer fastXmlSerializer = new FastXmlSerializer();
        fastXmlSerializer.setOutput(outputStream, "utf-8");
        fastXmlSerializer.startDocument(null, true);
        fastXmlSerializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
        XmlUtils.writeMapXml(map, null, fastXmlSerializer);
        fastXmlSerializer.endDocument();
    }

    public static final void writeMapXml(Map object, String object2, XmlSerializer xmlSerializer) throws XmlPullParserException, IOException {
        if (object == null) {
            xmlSerializer.startTag(null, "null");
            xmlSerializer.endTag(null, "null");
            return;
        }
        object = object.entrySet().iterator();
        xmlSerializer.startTag(null, "map");
        if (object2 != null) {
            xmlSerializer.attribute(null, "name", (String)object2);
        }
        while (object.hasNext()) {
            object2 = (Map.Entry)object.next();
            XmlUtils.writeValueXml(object2.getValue(), (String)object2.getKey(), xmlSerializer);
        }
        xmlSerializer.endTag(null, "map");
    }

    /*
     * Enabled aggressive block sorting
     */
    public static final void writeValueXml(Object object, String string2, XmlSerializer xmlSerializer) throws XmlPullParserException, IOException {
        block19: {
            String string3;
            block15: {
                block18: {
                    block17: {
                        block16: {
                            block14: {
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
                                if (!(object instanceof Integer)) break block14;
                                string3 = "int";
                                break block15;
                            }
                            if (!(object instanceof Long)) break block16;
                            string3 = "long";
                            break block15;
                        }
                        if (!(object instanceof Float)) break block17;
                        string3 = "float";
                        break block15;
                    }
                    if (!(object instanceof Double)) break block18;
                    string3 = "double";
                    break block15;
                }
                if (!(object instanceof Boolean)) break block19;
                string3 = "boolean";
            }
            xmlSerializer.startTag(null, string3);
            if (string2 != null) {
                xmlSerializer.attribute(null, "name", string2);
            }
            xmlSerializer.attribute(null, "value", object.toString());
            xmlSerializer.endTag(null, string3);
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
        if (object instanceof Map) {
            XmlUtils.writeMapXml((Map)object, string2, xmlSerializer);
            return;
        }
        if (object instanceof List) {
            XmlUtils.writeListXml((List)object, string2, xmlSerializer);
            return;
        }
        if (!(object instanceof CharSequence)) {
            throw new RuntimeException("writeValueXml: unable to write value " + object);
        }
        xmlSerializer.startTag(null, "string");
        if (string2 != null) {
            xmlSerializer.attribute(null, "name", string2);
        }
        xmlSerializer.text(object.toString());
        xmlSerializer.endTag(null, "string");
    }
}

