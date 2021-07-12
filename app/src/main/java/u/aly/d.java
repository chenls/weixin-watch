/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.text.TextUtils
 */
package u.aly;

import android.content.Context;
import android.text.TextUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class d {
    public static final String a = "/data/data/";
    public static final String b = "/databases/cc/";
    public static final String c = "cc.db";
    public static final int d = 1;
    public static final String e = "Id";
    public static final String f = "INTEGER";

    public static String a(Context context) {
        return a + context.getPackageName() + b;
    }

    public static String a(List<String> list) {
        return TextUtils.join((CharSequence)"!", list);
    }

    public static List<String> a(String string2) {
        return new ArrayList<String>(Arrays.asList(string2.split("!")));
    }

    public static List<String> b(List<String> object) {
        ArrayList<String> arrayList = new ArrayList<String>();
        try {
            object = object.iterator();
            while (object.hasNext()) {
                String string2 = (String)object.next();
                if (Collections.frequency(arrayList, string2) >= 1) continue;
                arrayList.add(string2);
            }
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        return arrayList;
    }

    public static class u.aly.d$a {
        public static final String a = "aggregated";
        public static final String b = "aggregated_cache";

        public static class a {
            public static final String a = "key";
            public static final String b = "totalTimestamp";
            public static final String c = "value";
            public static final String d = "count";
            public static final String e = "label";
            public static final String f = "timeWindowNum";
        }

        public static class b {
            public static final String a = "TEXT";
            public static final String b = "TEXT";
            public static final String c = "INTEGER";
            public static final String d = "INTEGER";
            public static final String e = "TEXT";
            public static final String f = "TEXT";
        }
    }

    public static class u.aly.d$b {
        public static final String a = "limitedck";

        public static class a {
            public static final String a = "ck";
        }

        public static class b {
            public static final String a = "TEXT";
        }
    }

    public static class c {
        public static final String a = "system";

        public static class a {
            public static final String a = "key";
            public static final String b = "timeStamp";
            public static final String c = "count";
            public static final String d = "label";
        }

        public static class b {
            public static final String a = "TEXT";
            public static final String b = "INTEGER";
            public static final String c = "INTEGER";
            public static final String d = "TEXT";
        }
    }
}

