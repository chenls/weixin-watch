/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.client.result;

import com.google.zxing.Result;
import com.google.zxing.client.result.ResultParser;
import com.google.zxing.client.result.SMSParsedResult;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public final class SMSMMSResultParser
extends ResultParser {
    /*
     * Enabled aggressive block sorting
     */
    private static void addNumberVia(Collection<String> object, Collection<String> collection, String string2) {
        int n2 = string2.indexOf(59);
        if (n2 < 0) {
            object.add(string2);
            collection.add(null);
            return;
        }
        object.add(string2.substring(0, n2));
        object = string2.substring(n2 + 1);
        object = ((String)object).startsWith("via=") ? ((String)object).substring(4) : null;
        collection.add((String)object);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public SMSParsedResult parse(Result object) {
        Object object2 = SMSMMSResultParser.getMassagedText((Result)object);
        if (!(((String)object2).startsWith("sms:") || ((String)object2).startsWith("SMS:") || ((String)object2).startsWith("mms:") || ((String)object2).startsWith("MMS:"))) {
            return null;
        }
        Map<String, String> map = SMSMMSResultParser.parseNameValuePairs((String)object2);
        String string2 = null;
        ArrayList<String> arrayList = null;
        int n2 = 0;
        Object object3 = arrayList;
        int n3 = n2;
        object = string2;
        if (map != null) {
            object3 = arrayList;
            n3 = n2;
            object = string2;
            if (!map.isEmpty()) {
                object = map.get("subject");
                object3 = map.get("body");
                n3 = 1;
            }
        }
        string2 = (n2 = ((String)object2).indexOf(63, 4)) < 0 || n3 == 0 ? ((String)object2).substring(4) : ((String)object2).substring(4, n2);
        n3 = -1;
        arrayList = new ArrayList<String>(1);
        object2 = new ArrayList(1);
        while (true) {
            if ((n2 = string2.indexOf(44, n3 + 1)) <= n3) {
                SMSMMSResultParser.addNumberVia(arrayList, (Collection<String>)object2, string2.substring(n3 + 1));
                return new SMSParsedResult(arrayList.toArray(new String[arrayList.size()]), object2.toArray(new String[object2.size()]), (String)object, (String)object3);
            }
            SMSMMSResultParser.addNumberVia(arrayList, (Collection<String>)object2, string2.substring(n3 + 1, n2));
            n3 = n2;
        }
    }
}

