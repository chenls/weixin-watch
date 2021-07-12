/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.res.TypedArray
 *  org.xmlpull.v1.XmlPullParser
 */
package android.support.graphics.drawable;

import android.content.res.TypedArray;
import org.xmlpull.v1.XmlPullParser;

class TypedArrayUtils {
    private static final String NAMESPACE = "http://schemas.android.com/apk/res/android";

    TypedArrayUtils() {
    }

    public static boolean getNamedBoolean(TypedArray typedArray, XmlPullParser xmlPullParser, String string2, int n2, boolean bl2) {
        if (!TypedArrayUtils.hasAttribute(xmlPullParser, string2)) {
            return bl2;
        }
        return typedArray.getBoolean(n2, bl2);
    }

    public static int getNamedColor(TypedArray typedArray, XmlPullParser xmlPullParser, String string2, int n2, int n3) {
        if (!TypedArrayUtils.hasAttribute(xmlPullParser, string2)) {
            return n3;
        }
        return typedArray.getColor(n2, n3);
    }

    public static float getNamedFloat(TypedArray typedArray, XmlPullParser xmlPullParser, String string2, int n2, float f2) {
        if (!TypedArrayUtils.hasAttribute(xmlPullParser, string2)) {
            return f2;
        }
        return typedArray.getFloat(n2, f2);
    }

    public static int getNamedInt(TypedArray typedArray, XmlPullParser xmlPullParser, String string2, int n2, int n3) {
        if (!TypedArrayUtils.hasAttribute(xmlPullParser, string2)) {
            return n3;
        }
        return typedArray.getInt(n2, n3);
    }

    public static boolean hasAttribute(XmlPullParser xmlPullParser, String string2) {
        return xmlPullParser.getAttributeValue(NAMESPACE, string2) != null;
    }
}

