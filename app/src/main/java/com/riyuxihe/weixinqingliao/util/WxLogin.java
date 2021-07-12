package com.riyuxihe.weixinqingliao.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Base64;
import android.util.Log;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.riyuxihe.weixinqingliao.model.Token;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpCookie;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Properties;
import javax.net.ssl.HttpsURLConnection;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

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

    public static String formatQRUrl(String uuid) {
        return QR_URL + uuid;
    }

    public static String getUUid() {
        try {
            Properties prop = parseText(NetUtil.getHttpsResponse(JS_LOGIN_WX_URL + System.currentTimeMillis(), 0));
            if (Constants.LoginCode.LOGIN.equals(prop.getProperty(CODE_KEY))) {
                return prop.getProperty(UUID_KEY).replaceAll("\"", "");
            }
            return "";
        } catch (UnknownHostException e) {
            Log.w(TAG, "getUUid:unknownHostException");
            return UNKNOWN_HOST;
        } catch (IOException e2) {
            Log.w(TAG, "getUUid:exception", e2);
            return "";
        }
    }

    public static String pushLogin(String wxuin, String cookie) {
        try {
            String text = NetUtil.getHttpsResponse(String.format(PUSH_LOGIN, new Object[]{wxuin}), 0, cookie);
            Log.i(TAG, "pushLogin:httpRes=" + text);
            JSONObject res = JSON.parseObject(text);
            if (Constants.SyncCheckCode.SUCCESS.equals(res.getString(Token.RET))) {
                return res.getString("uuid");
            }
            return "";
        } catch (IOException e) {
            Log.w(TAG, "fastLogin:exception", e);
            return null;
        }
    }

    public static Properties parseText(String text) {
        Properties prop = new Properties();
        if (text != null && !text.isEmpty()) {
            char[] chars = text.toCharArray();
            StringBuilder builder = new StringBuilder();
            String key = null;
            boolean singleQuoteCouple = true;
            boolean doubleQuoteCouple = true;
            for (int i = 0; i < chars.length; i++) {
                char c = chars[i];
                if (!doubleQuoteCouple && '\"' != c) {
                    builder.append(chars[i]);
                } else if (singleQuoteCouple || '\'' == c) {
                    switch (c) {
                        case 9:
                        case 10:
                        case 13:
                        case ' ':
                            break;
                        case '\"':
                            if (doubleQuoteCouple) {
                                doubleQuoteCouple = false;
                                break;
                            } else {
                                doubleQuoteCouple = true;
                                break;
                            }
                        case '\'':
                            if (singleQuoteCouple) {
                                singleQuoteCouple = false;
                                break;
                            } else {
                                singleQuoteCouple = true;
                                break;
                            }
                        case ';':
                            String value = builder.toString();
                            builder.delete(0, builder.length());
                            prop.setProperty(key, value);
                            break;
                        case '=':
                            key = builder.toString();
                            builder.delete(0, builder.length());
                            break;
                        default:
                            builder.append(c);
                            break;
                    }
                } else {
                    builder.append(c);
                }
            }
            if (!(key == null || builder.length() == 0)) {
                String value2 = builder.toString();
                builder.delete(0, builder.length());
                prop.setProperty(key, value2);
            }
        }
        return prop;
    }

    public static Properties checkLoginStatus(String uuid) {
        try {
            String url = String.format(LOGIN_CHECK_URL, new Object[]{uuid}) + System.currentTimeMillis();
            Log.d(TAG, "checkLoginStatus:url=" + url);
            String text = NetUtil.getHttpsResponse(url, 30000);
//            Log.d(TAG, "checkLoginStatus:result=" + text);
            return parseText(text);
        } catch (IOException e) {
            Log.w(TAG, "checkLoginStatus:exception", e);
            return null;
        }
    }

    public static Token getToken(String url) {
        Uri uri = Uri.parse(url);
        WxHome.setWxBaseUri(uri.getScheme(), uri.getHost());
        try {
            HttpsURLConnection conn = NetUtil.getHttpsConnection(url + REDIRECT_SUFFIX, 30000);
            conn.connect();
            NodeList error = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(conn.getInputStream()).getElementsByTagName("error");
            if (error == null || error.getLength() == 0) {
                conn.disconnect();
                return null;
            }
            Token token = new Token();
            token.cookie = cookiesToStr((List) conn.getHeaderFields().get("Set-Cookie"));
//            Log.d(TAG, "getToken:cookie=" + token.cookie);
            NodeList elements = error.item(0).getChildNodes();
            for (int i = 0; i < elements.getLength(); i++) {
                Node e = elements.item(i);
                String value = e.getTextContent().trim();
                String nodeName = e.getNodeName();
                char c = 65535;
                switch (nodeName.hashCode()) {
                    case -1730959651:
                        if (nodeName.equals(Token.IS_GRAY_SCALE)) {
                            c = 6;
                            break;
                        }
                        break;
                    case 112801:
                        if (nodeName.equals(Token.RET)) {
                            c = 0;
                            break;
                        }
                        break;
                    case 3532044:
                        if (nodeName.equals(Token.SKEY)) {
                            c = 2;
                            break;
                        }
                        break;
                    case 113587789:
                        if (nodeName.equals(Token.WXSID)) {
                            c = 3;
                            break;
                        }
                        break;
                    case 113589721:
                        if (nodeName.equals(Token.WXUIN)) {
                            c = 4;
                            break;
                        }
                        break;
                    case 954925063:
                        if (nodeName.equals("message")) {
                            c = 1;
                            break;
                        }
                        break;
                    case 1713340154:
                        if (nodeName.equals(Token.PASS_TICKET)) {
                            c = 5;
                            break;
                        }
                        break;
                }
                switch (c) {
                    case 0:
                        token.ret = Integer.parseInt(value);
                        break;
                    case 1:
                        token.message = value;
                        break;
                    case 2:
                        token.setSkey(value);
                        break;
                    case 3:
                        token.setWxsid(value);
                        break;
                    case 4:
                        token.setWxuin(value);
                        break;
                    case 5:
                        token.setPassTicket(value);
                        break;
                    case 6:
                        token.setIsGrayScale(value);
                        break;
                }
            }
            conn.disconnect();
//            Log.i(TAG, "getToken:token=" + JSON.toJSONString(token));
            return token;
        } catch (Exception e2) {
            Log.w(TAG, "getToken:exception", e2);
            return null;
        }
    }

    private static String cookiesToStr(List<String> cookies) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String cookie : cookies) {
            stringBuilder.append(HttpCookie.parse(cookie).get(0).toString() + ";");
        }
        return stringBuilder.toString();
    }

    public static Bitmap getURLImage(String url) {
        Bitmap bmp = null;
        try {
            HttpsURLConnection conn = NetUtil.getHttpsConnection(url, 6000);
            conn.setUseCaches(false);
            conn.connect();
            InputStream is = conn.getInputStream();
            bmp = BitmapFactory.decodeStream(is);
            is.close();
            conn.disconnect();
            return bmp;
        } catch (Exception e) {
            Log.w(TAG, "getURLImage:exception", e);
            return bmp;
        }
    }

    public static Bitmap getBase64Image(String avatarUrl) {
        byte[] decode = Base64.decode(avatarUrl.replace("data:img/jpg;base64,", ""), 0);
        return BitmapFactory.decodeByteArray(decode, 0, decode.length);
    }
}
