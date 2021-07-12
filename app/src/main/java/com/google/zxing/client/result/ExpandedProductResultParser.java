/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.client.result;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.google.zxing.client.result.ExpandedProductParsedResult;
import com.google.zxing.client.result.ResultParser;
import java.util.HashMap;
import java.util.Map;

public final class ExpandedProductResultParser
extends ResultParser {
    /*
     * Enabled aggressive block sorting
     */
    private static String findAIvalue(int n2, String string2) {
        if (string2.charAt(n2) == '(') {
            string2 = string2.substring(n2 + 1);
            StringBuilder stringBuilder = new StringBuilder();
            n2 = 0;
            while (true) {
                if (n2 >= string2.length()) {
                    return stringBuilder.toString();
                }
                char c2 = string2.charAt(n2);
                if (c2 == ')') {
                    return stringBuilder.toString();
                }
                if (c2 < '0' || c2 > '9') break;
                stringBuilder.append(c2);
                ++n2;
            }
        }
        return null;
    }

    /*
     * Enabled aggressive block sorting
     */
    private static String findValue(int n2, String string2) {
        StringBuilder stringBuilder = new StringBuilder();
        string2 = string2.substring(n2);
        for (n2 = 0; n2 < string2.length(); ++n2) {
            char c2 = string2.charAt(n2);
            if (c2 == '(') {
                if (ExpandedProductResultParser.findAIvalue(n2, string2) != null) break;
                stringBuilder.append('(');
                continue;
            }
            stringBuilder.append(c2);
        }
        return stringBuilder.toString();
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public ExpandedProductParsedResult parse(Result object) {
        if (((Result)object).getBarcodeFormat() != BarcodeFormat.RSS_EXPANDED) {
            return null;
        }
        String string2 = ExpandedProductResultParser.getMassagedText((Result)object);
        Object object2 = null;
        Object object3 = null;
        Object object4 = null;
        Object object5 = null;
        Object object6 = null;
        Object object7 = null;
        Object object8 = null;
        Object object9 = null;
        String string3 = null;
        String string4 = null;
        Object object10 = null;
        String string5 = null;
        String string6 = null;
        HashMap<String, String> hashMap = new HashMap<String, String>();
        int n2 = 0;
        block50: while (n2 < string2.length()) {
            String string7 = ExpandedProductResultParser.findAIvalue(n2, string2);
            if (string7 == null) {
                return null;
            }
            object = ExpandedProductResultParser.findValue(n2 += string7.length() + 2, string2);
            int n3 = n2 + ((String)object).length();
            n2 = -1;
            switch (string7.hashCode()) {
                case 1536: {
                    if (!string7.equals("00")) break;
                    n2 = 0;
                    break;
                }
                case 1537: {
                    if (!string7.equals("01")) break;
                    n2 = 1;
                    break;
                }
                case 1567: {
                    if (!string7.equals("10")) break;
                    n2 = 2;
                    break;
                }
                case 1568: {
                    if (!string7.equals("11")) break;
                    n2 = 3;
                    break;
                }
                case 1570: {
                    if (!string7.equals("13")) break;
                    n2 = 4;
                    break;
                }
                case 1572: {
                    if (!string7.equals("15")) break;
                    n2 = 5;
                    break;
                }
                case 1574: {
                    if (!string7.equals("17")) break;
                    n2 = 6;
                    break;
                }
                case 1567966: {
                    if (!string7.equals("3100")) break;
                    n2 = 7;
                    break;
                }
                case 1567967: {
                    if (!string7.equals("3101")) break;
                    n2 = 8;
                    break;
                }
                case 1567968: {
                    if (!string7.equals("3102")) break;
                    n2 = 9;
                    break;
                }
                case 1567969: {
                    if (!string7.equals("3103")) break;
                    n2 = 10;
                    break;
                }
                case 1567970: {
                    if (!string7.equals("3104")) break;
                    n2 = 11;
                    break;
                }
                case 1567971: {
                    if (!string7.equals("3105")) break;
                    n2 = 12;
                    break;
                }
                case 1567972: {
                    if (!string7.equals("3106")) break;
                    n2 = 13;
                    break;
                }
                case 1567973: {
                    if (!string7.equals("3107")) break;
                    n2 = 14;
                    break;
                }
                case 1567974: {
                    if (!string7.equals("3108")) break;
                    n2 = 15;
                    break;
                }
                case 1567975: {
                    if (!string7.equals("3109")) break;
                    n2 = 16;
                    break;
                }
                case 1568927: {
                    if (!string7.equals("3200")) break;
                    n2 = 17;
                    break;
                }
                case 1568928: {
                    if (!string7.equals("3201")) break;
                    n2 = 18;
                    break;
                }
                case 1568929: {
                    if (!string7.equals("3202")) break;
                    n2 = 19;
                    break;
                }
                case 1568930: {
                    if (!string7.equals("3203")) break;
                    n2 = 20;
                    break;
                }
                case 1568931: {
                    if (!string7.equals("3204")) break;
                    n2 = 21;
                    break;
                }
                case 1568932: {
                    if (!string7.equals("3205")) break;
                    n2 = 22;
                    break;
                }
                case 1568933: {
                    if (!string7.equals("3206")) break;
                    n2 = 23;
                    break;
                }
                case 1568934: {
                    if (!string7.equals("3207")) break;
                    n2 = 24;
                    break;
                }
                case 1568935: {
                    if (!string7.equals("3208")) break;
                    n2 = 25;
                    break;
                }
                case 1568936: {
                    if (!string7.equals("3209")) break;
                    n2 = 26;
                    break;
                }
                case 1575716: {
                    if (!string7.equals("3920")) break;
                    n2 = 27;
                    break;
                }
                case 1575717: {
                    if (!string7.equals("3921")) break;
                    n2 = 28;
                    break;
                }
                case 1575718: {
                    if (!string7.equals("3922")) break;
                    n2 = 29;
                    break;
                }
                case 1575719: {
                    if (!string7.equals("3923")) break;
                    n2 = 30;
                    break;
                }
                case 1575747: {
                    if (!string7.equals("3930")) break;
                    n2 = 31;
                    break;
                }
                case 1575748: {
                    if (!string7.equals("3931")) break;
                    n2 = 32;
                    break;
                }
                case 1575749: {
                    if (!string7.equals("3932")) break;
                    n2 = 33;
                    break;
                }
                case 1575750: {
                    if (!string7.equals("3933")) break;
                    n2 = 34;
                    break;
                }
            }
            switch (n2) {
                default: {
                    hashMap.put(string7, (String)object);
                    n2 = n3;
                    continue block50;
                }
                case 0: {
                    object3 = object;
                    n2 = n3;
                    continue block50;
                }
                case 1: {
                    object2 = object;
                    n2 = n3;
                    continue block50;
                }
                case 2: {
                    object4 = object;
                    n2 = n3;
                    continue block50;
                }
                case 3: {
                    object5 = object;
                    n2 = n3;
                    continue block50;
                }
                case 4: {
                    object6 = object;
                    n2 = n3;
                    continue block50;
                }
                case 5: {
                    object7 = object;
                    n2 = n3;
                    continue block50;
                }
                case 6: {
                    object8 = object;
                    n2 = n3;
                    continue block50;
                }
                case 7: 
                case 8: 
                case 9: 
                case 10: 
                case 11: 
                case 12: 
                case 13: 
                case 14: 
                case 15: 
                case 16: {
                    string3 = "KG";
                    string4 = string7.substring(3);
                    object9 = object;
                    n2 = n3;
                    continue block50;
                }
                case 17: 
                case 18: 
                case 19: 
                case 20: 
                case 21: 
                case 22: 
                case 23: 
                case 24: 
                case 25: 
                case 26: {
                    string3 = "LB";
                    string4 = string7.substring(3);
                    object9 = object;
                    n2 = n3;
                    continue block50;
                }
                case 27: 
                case 28: 
                case 29: 
                case 30: {
                    string5 = string7.substring(3);
                    object10 = object;
                    n2 = n3;
                    continue block50;
                }
                case 31: 
                case 32: 
                case 33: 
                case 34: 
            }
            if (((String)object).length() < 4) {
                return null;
            }
            object10 = ((String)object).substring(3);
            string6 = ((String)object).substring(0, 3);
            string5 = string7.substring(3);
            n2 = n3;
        }
        return new ExpandedProductParsedResult(string2, (String)object2, (String)object3, (String)object4, (String)object5, (String)object6, (String)object7, (String)object8, (String)object9, string3, string4, (String)object10, string5, string6, (Map<String, String>)hashMap);
    }
}

