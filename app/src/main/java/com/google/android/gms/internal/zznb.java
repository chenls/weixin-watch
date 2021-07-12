/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.text.TextUtils
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.google.android.gms.internal;

import android.text.TextUtils;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class zznb {
    private static final Pattern zzaoi = Pattern.compile("\\\\.");
    private static final Pattern zzaoj = Pattern.compile("[\\\\\"/\b\f\n\r\t]");

    public static String zzcU(String string2) {
        StringBuffer stringBuffer;
        Matcher matcher;
        block16: {
            block15: {
                if (TextUtils.isEmpty((CharSequence)string2)) break block15;
                matcher = zzaoj.matcher(string2);
                stringBuffer = null;
                block10: while (matcher.find()) {
                    StringBuffer stringBuffer2 = stringBuffer;
                    if (stringBuffer == null) {
                        stringBuffer2 = new StringBuffer();
                    }
                    switch (matcher.group().charAt(0)) {
                        default: {
                            stringBuffer = stringBuffer2;
                            continue block10;
                        }
                        case '\b': {
                            matcher.appendReplacement(stringBuffer2, "\\\\b");
                            stringBuffer = stringBuffer2;
                            continue block10;
                        }
                        case '\"': {
                            matcher.appendReplacement(stringBuffer2, "\\\\\\\"");
                            stringBuffer = stringBuffer2;
                            continue block10;
                        }
                        case '\\': {
                            matcher.appendReplacement(stringBuffer2, "\\\\\\\\");
                            stringBuffer = stringBuffer2;
                            continue block10;
                        }
                        case '/': {
                            matcher.appendReplacement(stringBuffer2, "\\\\/");
                            stringBuffer = stringBuffer2;
                            continue block10;
                        }
                        case '\f': {
                            matcher.appendReplacement(stringBuffer2, "\\\\f");
                            stringBuffer = stringBuffer2;
                            continue block10;
                        }
                        case '\n': {
                            matcher.appendReplacement(stringBuffer2, "\\\\n");
                            stringBuffer = stringBuffer2;
                            continue block10;
                        }
                        case '\r': {
                            matcher.appendReplacement(stringBuffer2, "\\\\r");
                            stringBuffer = stringBuffer2;
                            continue block10;
                        }
                        case '\t': 
                    }
                    matcher.appendReplacement(stringBuffer2, "\\\\t");
                    stringBuffer = stringBuffer2;
                }
                if (stringBuffer != null) break block16;
            }
            return string2;
        }
        matcher.appendTail(stringBuffer);
        return stringBuffer.toString();
    }

    /*
     * Unable to fully structure code
     */
    public static boolean zze(Object var0, Object var1_3) {
        block9: {
            block8: {
                var4_4 = false;
                if (var0 == null && var1_3 == null) {
                    var3_5 = true;
lbl4:
                    // 7 sources

                    return var3_5;
                }
                var3_5 = var4_4;
                if (var0 == null) ** GOTO lbl4
                var3_5 = var4_4;
                if (var1_3 == null) ** GOTO lbl4
                if (!(var0 instanceof JSONObject) || !(var1_3 instanceof JSONObject)) break block8;
                var0 = (JSONObject)var0;
                var1_3 = (JSONObject)var1_3;
                var3_5 = var4_4;
                if (var0.length() != var1_3.length()) ** GOTO lbl4
                var6_6 = var0.keys();
                while (var6_6.hasNext()) {
                    var7_7 = (String)var6_6.next();
                    var3_5 = var4_4;
                    if (!var1_3.has(var7_7)) ** GOTO lbl4
                    var3_5 = zznb.zze(var0.get(var7_7), var1_3.get(var7_7));
                    if (var3_5) continue;
                    return false;
                }
                return true;
            }
            if (!(var0 instanceof JSONArray) || !(var1_3 instanceof JSONArray)) break block9;
            var0 = (JSONArray)var0;
            var1_3 = (JSONArray)var1_3;
            var3_5 = var4_4;
            if (var0.length() != var1_3.length()) ** GOTO lbl4
            for (var2_8 = 0; var2_8 < var0.length(); ++var2_8) {
                var5_9 = zznb.zze(var0.get(var2_8), var1_3.get(var2_8));
                var3_5 = var4_4;
                if (var5_9) continue;
                ** continue;
            }
            return true;
        }
        return var0.equals(var1_3);
        catch (JSONException var0_1) {
            return false;
        }
        catch (JSONException var0_2) {
            return false;
        }
    }
}

