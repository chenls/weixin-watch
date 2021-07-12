/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.graphics.Bitmap
 *  android.graphics.BitmapFactory
 *  android.util.Base64
 *  android.util.Log
 */
package com.riyuxihe.weixinqingliao.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.riyuxihe.weixinqingliao.model.Token;
import com.riyuxihe.weixinqingliao.util.NetUtil;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpCookie;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Properties;
import javax.net.ssl.HttpsURLConnection;

public class WxLogin {
    public static final String AVATAR_KEY = "window.userAvatar";
    private static final String CODE_KEY = "window.QRLogin.code";
    public static final String DEFAULT_AVATAR = "data:img/jpg;base64,/9j/4AAQSkZJRgABAQAAAQABAAD/2wBDABQODxIPDRQSEBIXFRQYHjIhHhwcHj0sLiQySUBMS0dARkVQWnNiUFVtVkVGZIhlbXd7gYKBTmCNl4x9lnN+gXz/wAALCACEAIQBAREA/8QAGQABAQEBAQEAAAAAAAAAAAAAAAMCAQQG/8QAIhABAAIBBAEFAQAAAAAAAAAAAAECEQMEITEFEhMyUWFB/9oACAEBAAA/APpa1rSsVpEVrEYiI6h0AAAAHk3Pi9lu9X3dxt6al8Y9UvWAAAAAAAAAAAAA7ETPTXtz9k6csdAAAAC0RiMOjF4zGUwAAAdr8oWByekQAAAdrxaFgcniEQAAAFazmGhjUn+JgAAAO0nFlnJ4hHsAAAAiM9KVpjmWxO1PpgAAAbrT7biIjp0ByaxPadqTH7DIAApSvGWwABPUjE5hgABWkx6YaAAE9SeoYAAAAAAAAAAAAAAAAAAT22pOrttLUtiJvSLTj9hQAAAAHzXnvObzYeQnQ0JpFIpE81zL/9k=";
    private static final String JS_LOGIN_WX_URL = "https://login.wx.qq.com/jslogin?appid=wx782c26e4c19acffb&redirect_uri=https%3A%2F%2Fwx.qq.com%2Fcgi-bin%2Fmmwebwx-bin%2Fwebwxnewloginpage&fun=new&lang=zh_CN&_=";
    private static final String LOGIN_CHECK_URL = "https://login.wx.qq.com/cgi-bin/mmwebwx-bin/login?loginicon=true&uuid=%s&tip=0&_=";
    public static final String LOGIN_CODE_KEY = "window.code";
    private static final String LOGIN_URL = "https://login.weixin.qq.com/l/";
    private static final String PUSH_LOGIN = "https://wx.qq.com/cgi-bin/mmwebwx-bin/webwxpushloginurl?uin=%s";
    private static final String QR_URL = "https://login.weixin.qq.com/qrcode/";
    public static final String REDIRECT_KEY = "window.redirect_uri";
    private static final String REDIRECT_SUFFIX = "&fun=new&version=v2";
    private static final String TAG = "WxLogin";
    public static final String UNKNOWN_HOST = "UNKNOWN_HOST";
    private static final String UUID_KEY = "window.QRLogin.uuid";

    public static Properties checkLoginStatus(String object) {
        long l2 = System.currentTimeMillis();
        try {
            object = String.format(LOGIN_CHECK_URL, object) + l2;
            Log.d((String)TAG, (String)("checkLoginStatus:url=" + (String)object));
            object = NetUtil.getHttpsResponse((String)object, 30000);
            Log.d((String)TAG, (String)("checkLoginStatus:result=" + (String)object));
            object = WxLogin.parseText((String)object);
            return object;
        }
        catch (IOException iOException) {
            Log.w((String)TAG, (String)"checkLoginStatus:exception", (Throwable)iOException);
            return null;
        }
    }

    private static String cookiesToStr(List<String> object) {
        StringBuilder stringBuilder = new StringBuilder();
        object = object.iterator();
        while (object.hasNext()) {
            String string2 = (String)object.next();
            stringBuilder.append(HttpCookie.parse(string2).get(0).toString() + ";");
        }
        return stringBuilder.toString();
    }

    public static String formatQRUrl(String string2) {
        return QR_URL + string2;
    }

    public static Bitmap getBase64Image(String object) {
        object = Base64.decode((String)((String)object).replace("data:img/jpg;base64,", ""), (int)0);
        return BitmapFactory.decodeByteArray((byte[])object, (int)0, (int)((Object)object).length);
    }

    /*
     * Exception decompiling
     */
    public static Token getToken(String var0) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Back jump on a try block [egrp 12[TRYBLOCK] [14 : 422->509)] java.lang.Exception
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op02WithProcessedDataAndRefs.insertExceptionBlocks(Op02WithProcessedDataAndRefs.java:2289)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:414)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1042)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:929)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:73)
         *     at org.benf.cfr.reader.Main.main(Main.java:49)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    public static Bitmap getURLImage(String string2) {
        HttpsURLConnection httpsURLConnection;
        String string3;
        String string4 = string3 = null;
        try {
            httpsURLConnection = NetUtil.getHttpsConnection(string2, 6000);
            string4 = string3;
        }
        catch (Exception exception) {
            Log.w((String)TAG, (String)"getURLImage:exception", (Throwable)exception);
            return string4;
        }
        httpsURLConnection.setUseCaches(false);
        string4 = string3;
        httpsURLConnection.connect();
        string4 = string3;
        InputStream inputStream = httpsURLConnection.getInputStream();
        string4 = string3;
        string4 = string2 = BitmapFactory.decodeStream((InputStream)inputStream);
        inputStream.close();
        string4 = string2;
        httpsURLConnection.disconnect();
        return string2;
    }

    public static String getUUid() {
        long l2 = System.currentTimeMillis();
        Properties properties = WxLogin.parseText(NetUtil.getHttpsResponse(JS_LOGIN_WX_URL + l2, 0));
        String string2 = properties.getProperty(CODE_KEY);
        String string3 = "";
        try {
            if ("200".equals(string2)) {
                string3 = properties.getProperty(UUID_KEY).replaceAll("\"", "");
            }
            return string3;
        }
        catch (UnknownHostException unknownHostException) {
            Log.w((String)TAG, (String)"getUUid:unknownHostException");
            return UNKNOWN_HOST;
        }
        catch (IOException iOException) {
            Log.w((String)TAG, (String)"getUUid:exception", (Throwable)iOException);
            return "";
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public static Properties parseText(String string2) {
        String string3;
        StringBuilder stringBuilder;
        Properties properties;
        block16: {
            block15: {
                properties = new Properties();
                if (string2 == null || string2.isEmpty()) break block15;
                char[] cArray = string2.toCharArray();
                stringBuilder = new StringBuilder();
                string2 = null;
                boolean bl2 = true;
                boolean bl3 = true;
                for (int i2 = 0; i2 < cArray.length; ++i2) {
                    boolean bl4;
                    boolean bl5;
                    char c2 = cArray[i2];
                    if (!bl3 && '\"' != c2) {
                        stringBuilder.append(cArray[i2]);
                        bl5 = bl2;
                        string3 = string2;
                        bl4 = bl3;
                    } else if (!bl2 && '\'' != c2) {
                        stringBuilder.append(c2);
                        bl4 = bl3;
                        string3 = string2;
                        bl5 = bl2;
                    } else {
                        bl4 = bl3;
                        string3 = string2;
                        bl5 = bl2;
                        switch (c2) {
                            case '\t': 
                            case '\n': 
                            case '\r': 
                            case ' ': {
                                break;
                            }
                            default: {
                                stringBuilder.append(c2);
                                bl4 = bl3;
                                string3 = string2;
                                bl5 = bl2;
                                break;
                            }
                            case '\'': {
                                bl5 = !bl2;
                                bl4 = bl3;
                                string3 = string2;
                                break;
                            }
                            case '\"': {
                                bl5 = !bl3;
                                bl4 = bl5;
                                string3 = string2;
                                bl5 = bl2;
                                break;
                            }
                            case '=': {
                                string3 = stringBuilder.toString();
                                stringBuilder.delete(0, stringBuilder.length());
                                bl4 = bl3;
                                bl5 = bl2;
                                break;
                            }
                            case ';': {
                                string3 = stringBuilder.toString();
                                stringBuilder.delete(0, stringBuilder.length());
                                properties.setProperty(string2, string3);
                                bl4 = bl3;
                                string3 = string2;
                                bl5 = bl2;
                            }
                        }
                    }
                    bl3 = bl4;
                    string2 = string3;
                    bl2 = bl5;
                }
                if (string2 != null && stringBuilder.length() != 0) break block16;
            }
            return properties;
        }
        string3 = stringBuilder.toString();
        stringBuilder.delete(0, stringBuilder.length());
        properties.setProperty(string2, string3);
        return properties;
    }

    public static String pushLogin(String object, String string2) {
        object = String.format(PUSH_LOGIN, object);
        try {
            object = NetUtil.getHttpsResponse((String)object, 0, string2);
            Log.i((String)TAG, (String)("pushLogin:httpRes=" + (String)object));
            object = JSON.parseObject((String)object);
            if ("0".equals(((JSONObject)object).getString("ret"))) {
                return ((JSONObject)object).getString("uuid");
            }
            return "";
        }
        catch (IOException iOException) {
            Log.w((String)TAG, (String)"fastLogin:exception", (Throwable)iOException);
            return null;
        }
    }
}

