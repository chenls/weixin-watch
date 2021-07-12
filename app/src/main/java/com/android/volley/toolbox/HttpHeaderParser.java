/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  org.apache.http.impl.cookie.DateParseException
 *  org.apache.http.impl.cookie.DateUtils
 */
package com.android.volley.toolbox;

import com.android.volley.Cache;
import com.android.volley.NetworkResponse;
import java.util.Map;
import org.apache.http.impl.cookie.DateParseException;
import org.apache.http.impl.cookie.DateUtils;

public class HttpHeaderParser {
    /*
     * Handled impossible loop by adding 'first' condition
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Cache.Entry parseCacheHeaders(NetworkResponse networkResponse) {
        long l2 = System.currentTimeMillis();
        Map<String, String> map = networkResponse.headers;
        long l3 = 0L;
        long l4 = 0L;
        long l5 = 0L;
        long l6 = 0L;
        long l7 = 0L;
        long l8 = 0L;
        long l9 = 0L;
        boolean bl2 = false;
        boolean bl3 = false;
        boolean bl4 = false;
        String string2 = map.get("Date");
        if (string2 != null) {
            l3 = HttpHeaderParser.parseDateAsEpoch(string2);
        }
        String string3 = map.get("Cache-Control");
        long l10 = l8;
        long l11 = l9;
        boolean bl5 = true;
        while (true) {
            int n2;
            block20: {
                String string4;
                String string5;
                Object object;
                block18: {
                    block19: {
                        String[] stringArray;
                        boolean bl6;
                        block17: {
                            if (!bl5 || (bl5 = false)) break block17;
                            if (string3 == null) break block18;
                            bl6 = true;
                            stringArray = string3.split(",");
                            n2 = 0;
                        }
                        bl2 = bl6;
                        bl3 = bl4;
                        l10 = l8;
                        l11 = l9;
                        if (n2 >= stringArray.length) break block18;
                        object = stringArray[n2].trim();
                        if (((String)object).equals("no-cache") || ((String)object).equals("no-store")) {
                            return null;
                        }
                        if (((String)object).startsWith("max-age=")) {
                            l11 = Long.parseLong(((String)object).substring(8));
                            l10 = l9;
                        }
                        if (((String)object).startsWith("stale-while-revalidate=")) {
                            l10 = Long.parseLong(((String)object).substring(23));
                            l11 = l8;
                        }
                        if (((String)object).equals("must-revalidate")) break block19;
                        l11 = l8;
                        l10 = l9;
                        if (!((String)object).equals("proxy-revalidate")) break block20;
                    }
                    bl4 = true;
                    l11 = l8;
                    l10 = l9;
                    break block20;
                }
                if ((string5 = map.get("Expires")) != null) {
                    l5 = HttpHeaderParser.parseDateAsEpoch(string5);
                }
                if ((string4 = map.get("Last-Modified")) != null) {
                    l4 = HttpHeaderParser.parseDateAsEpoch(string4);
                }
                String string6 = map.get("ETag");
                if (bl2) {
                    l8 = l2 + 1000L * l10;
                    l9 = bl3 ? l8 : l8 + 1000L * l11;
                } else {
                    l9 = l7;
                    l8 = l6;
                    if (l3 > 0L) {
                        l9 = l7;
                        l8 = l6;
                        if (l5 >= l3) {
                            l9 = l8 = l2 + (l5 - l3);
                        }
                    }
                }
                object = new Cache.Entry();
                ((Cache.Entry)object).data = networkResponse.data;
                ((Cache.Entry)object).etag = string6;
                ((Cache.Entry)object).softTtl = l8;
                ((Cache.Entry)object).ttl = l9;
                ((Cache.Entry)object).serverDate = l3;
                ((Cache.Entry)object).lastModified = l4;
                ((Cache.Entry)object).responseHeaders = map;
                return object;
                catch (Exception exception) {
                    l11 = l8;
                    l10 = l9;
                }
                break block20;
                catch (Exception exception) {
                    l11 = l8;
                    l10 = l9;
                }
            }
            ++n2;
            l8 = l11;
            l9 = l10;
        }
    }

    public static String parseCharset(Map<String, String> map) {
        return HttpHeaderParser.parseCharset(map, "ISO-8859-1");
    }

    /*
     * WARNING - void declaration
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static String parseCharset(Map<String, String> object, String string2) {
        void var0_6;
        void var1_7;
        String[] stringArray = (String[])object.get("Content-Type");
        void var0_1 = var1_7;
        if (stringArray == null) return var0_6;
        stringArray = stringArray.split(";");
        int n2 = 1;
        while (true) {
            void var0_3 = var1_7;
            if (n2 >= stringArray.length) return var0_6;
            String[] stringArray2 = stringArray[n2].trim().split("=");
            if (stringArray2.length == 2 && stringArray2[0].equals("charset")) {
                String string3 = stringArray2[1];
                return var0_6;
            }
            ++n2;
        }
    }

    public static long parseDateAsEpoch(String string2) {
        try {
            long l2 = DateUtils.parseDate((String)string2).getTime();
            return l2;
        }
        catch (DateParseException dateParseException) {
            return 0L;
        }
    }
}

