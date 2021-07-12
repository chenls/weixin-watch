/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.os.Build$VERSION
 *  android.text.TextUtils
 */
package com.umeng.analytics.social;

import android.os.Build;
import android.text.TextUtils;
import com.umeng.analytics.social.b;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Random;
import java.util.zip.GZIPInputStream;
import java.util.zip.InflaterInputStream;

public abstract class c {
    /*
     * Loose catch block
     */
    private static String a(InputStream inputStream) {
        StringBuilder stringBuilder;
        block12: {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream), 8192);
            stringBuilder = new StringBuilder();
            while (true) {
                String string2 = bufferedReader.readLine();
                if (string2 == null) break block12;
                stringBuilder.append(string2 + "\n");
                continue;
                break;
            }
            catch (IOException iOException) {
                b.b("MobclickAgent", "Caught IOException in convertStreamToString()", iOException);
                inputStream.close();
                return null;
            }
        }
        try {
            inputStream.close();
            return stringBuilder.toString();
        }
        catch (IOException iOException) {
            b.b("MobclickAgent", "Caught IOException in convertStreamToString()", iOException);
            return null;
        }
        {
            catch (IOException iOException) {
                b.b("MobclickAgent", "Caught IOException in convertStreamToString()", iOException);
                return null;
            }
            catch (Throwable throwable) {
                try {
                    inputStream.close();
                }
                catch (IOException iOException) {
                    b.b("MobclickAgent", "Caught IOException in convertStreamToString()", iOException);
                    return null;
                }
                throw throwable;
            }
        }
    }

    /*
     * Loose catch block
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected static String a(String string2) {
        Object object = null;
        int n2 = new Random().nextInt(1000);
        String string3 = System.getProperty("line.separator");
        if (string2.length() <= 1) {
            b.b("MobclickAgent", n2 + ":  Invalid baseUrl.");
            return null;
        }
        Object object2 = (HttpURLConnection)new URL(string2).openConnection();
        try {
            ((URLConnection)object2).setConnectTimeout(10000);
            ((URLConnection)object2).setReadTimeout(20000);
            ((HttpURLConnection)object2).setRequestMethod("GET");
            if (Integer.parseInt(Build.VERSION.SDK) < 8) {
                System.setProperty("http.keepAlive", "false");
            }
            b.a("MobclickAgent", n2 + ": GET_URL: " + string2);
            if (((HttpURLConnection)object2).getResponseCode() == 200) {
                object = ((URLConnection)object2).getInputStream();
                String string4 = ((URLConnection)object2).getHeaderField("Content-Encoding");
                if (!TextUtils.isEmpty((CharSequence)string4) && string4.equalsIgnoreCase("gzip")) {
                    b.a("MobclickAgent", n2 + "  Use GZIPInputStream get data....");
                    object = new GZIPInputStream((InputStream)object);
                } else if (!TextUtils.isEmpty((CharSequence)string4) && string4.equalsIgnoreCase("deflate")) {
                    b.a("MobclickAgent", n2 + "  Use InflaterInputStream get data....");
                    object = new InflaterInputStream((InputStream)object);
                }
                object = c.a((InputStream)object);
                b.a("MobclickAgent", n2 + ":  response: " + string3 + (String)object);
                if (object == null) {
                    if (object2 != null) {
                        ((HttpURLConnection)object2).disconnect();
                    }
                    return null;
                }
                if (object2 != null) {
                    ((HttpURLConnection)object2).disconnect();
                }
                return object;
            }
            b.a("MobclickAgent", n2 + ":  Failed to get message." + string2);
            if (object2 != null) {
                ((HttpURLConnection)object2).disconnect();
            }
            return null;
        }
        catch (Exception exception) {
            block23: {
                object = object2;
                object2 = exception;
                break block23;
                catch (Throwable throwable) {}
                catch (Throwable throwable) {
                    object = object2;
                }
                catch (Exception exception2) {
                    object = null;
                }
            }
            try {
                b.c("MobclickAgent", n2 + ":  Exception,Failed to send message." + string2, (Exception)object2);
                if (object != null) {
                    ((HttpURLConnection)object).disconnect();
                }
                return null;
            }
            catch (Throwable throwable) {}
        }
        {
            void var0_2;
            if (object != null) {
                ((HttpURLConnection)object).disconnect();
            }
            throw var0_2;
        }
    }

    /*
     * Unable to fully structure code
     */
    protected static String a(String var0, String var1_5) {
        block20: {
            var2_6 = new Random().nextInt(1000);
            var4_7 = System.getProperty("line.separator");
            var3_9 = (HttpURLConnection)new URL(var0).openConnection();
            var3_9.setConnectTimeout(10000);
            var3_9.setReadTimeout(20000);
            var3_9.setDoOutput(true);
            var3_9.setDoInput(true);
            var3_9.setUseCaches(false);
            var3_9.setRequestMethod("POST");
            b.a("MobclickAgent", var2_6 + ": POST_URL: " + var0);
            if (Integer.parseInt(Build.VERSION.SDK) < 8) {
                System.setProperty("http.keepAlive", "false");
            }
            if (!TextUtils.isEmpty((CharSequence)var1_5)) {
                b.a("MobclickAgent", var2_6 + ": POST_BODY: " + (String)var1_5);
                var5_11 = new ArrayList<E>();
                var5_11.add("data=" + (String)var1_5);
                var1_5 = var3_9.getOutputStream();
                var1_5.write(URLEncoder.encode(var5_11.toString()).getBytes());
                var1_5.flush();
                var1_5.close();
            }
            if (var3_9.getResponseCode() != 200) break block20;
            var1_5 = var3_9.getInputStream();
            var5_11 = var3_9.getHeaderField("Content-Encoding");
            if (!TextUtils.isEmpty((CharSequence)var5_11) && var5_11.equalsIgnoreCase("gzip")) {
                var1_5 = new InflaterInputStream((InputStream)var1_5);
            }
            var1_5 = c.a((InputStream)var1_5);
            b.a("MobclickAgent", var2_6 + ":  response: " + var4_7 + (String)var1_5);
            if (var1_5 == null) {
                if (var3_9 != null) {
                    var3_9.disconnect();
                }
                return null;
            }
            if (var3_9 != null) {
                var3_9.disconnect();
            }
            return var1_5;
        }
        b.c("MobclickAgent", var2_6 + ":  Failed to send message." + var0);
        if (var3_9 != null) {
            var3_9.disconnect();
        }
        return null;
        catch (Exception var3_10) {
            var1_5 = null;
lbl47:
            // 2 sources

            while (true) {
                b.c("MobclickAgent", var2_6 + ":  Exception,Failed to send message." + var0, (Exception)var3_9);
                if (var1_5 != null) {
                    var1_5.disconnect();
                }
                return null;
                break;
            }
        }
        catch (Throwable var0_1) {
            var1_5 = null;
lbl55:
            // 3 sources

            while (true) {
                if (var1_5 != null) {
                    var1_5.disconnect();
                }
                throw var0_2;
            }
        }
        catch (Throwable var0_3) {
            var1_5 = var3_9;
            ** GOTO lbl55
        }
        {
            catch (Throwable var0_4) {
                ** continue;
            }
        }
        catch (Exception var4_8) {
            var1_5 = var3_9;
            var3_9 = var4_8;
            ** continue;
        }
    }
}

