/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Bitmap
 *  android.os.Message
 *  android.webkit.JsPromptResult
 *  android.webkit.JsResult
 *  android.webkit.WebChromeClient
 *  android.webkit.WebView
 *  org.json.JSONObject
 */
package com.umeng.analytics;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Message;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import com.umeng.analytics.MobclickAgent;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

public class MobclickAgentJSInterface {
    private Context a;

    public MobclickAgentJSInterface(Context context, WebView webView) {
        this.a = context;
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient((WebChromeClient)new a(null));
    }

    public MobclickAgentJSInterface(Context context, WebView webView, WebChromeClient webChromeClient) {
        this.a = context;
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient((WebChromeClient)new a(webChromeClient));
    }

    static /* synthetic */ Context a(MobclickAgentJSInterface mobclickAgentJSInterface) {
        return mobclickAgentJSInterface.a;
    }

    final class a
    extends WebChromeClient {
        WebChromeClient a = null;
        private final String c;
        private final String d;

        public a(WebChromeClient webChromeClient) {
            this.c = "ekv";
            this.d = "event";
            if (webChromeClient == null) {
                this.a = new WebChromeClient();
                return;
            }
            this.a = webChromeClient;
        }

        public void onCloseWindow(WebView webView) {
            this.a.onCloseWindow(webView);
        }

        public boolean onCreateWindow(WebView webView, boolean bl2, boolean bl3, Message message) {
            return this.a.onCreateWindow(webView, bl2, bl3, message);
        }

        public boolean onJsAlert(WebView webView, String string2, String string3, JsResult jsResult) {
            return this.a.onJsAlert(webView, string2, string3, jsResult);
        }

        public boolean onJsBeforeUnload(WebView webView, String string2, String string3, JsResult jsResult) {
            return this.a.onJsBeforeUnload(webView, string2, string3, jsResult);
        }

        public boolean onJsConfirm(WebView webView, String string2, String string3, JsResult jsResult) {
            return this.a.onJsConfirm(webView, string2, string3, jsResult);
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public boolean onJsPrompt(WebView var1_1, String var2_4, String var3_5, String var4_6, JsPromptResult var5_7) {
            block12: {
                block11: {
                    block10: {
                        if (!"ekv".equals(var3_5)) break block12;
                        try {
                            var1_1 /* !! */  = new JSONObject((String)var4_6);
                            var2_4 = new HashMap<K, V>();
                            var3_5 = (String)var1_1 /* !! */ .remove("id");
                            if (!var1_1 /* !! */ .isNull("duration")) break block10;
                            var6_8 = 0;
                        }
                        catch (Exception var1_2) {
                            var1_2.printStackTrace();
lbl11:
                            // 3 sources

                            while (true) {
                                var5_7.confirm();
                                return true;
                            }
                        }
lbl14:
                        // 2 sources

                        while (true) {
                            var4_6 = var1_1 /* !! */ .keys();
                            while (var4_6.hasNext()) {
                                var7_9 = (String)var4_6.next();
                                var2_4.put(var7_9, var1_1 /* !! */ .getString(var7_9));
                            }
                            break block11;
                            break;
                        }
                    }
                    var6_8 = (Integer)var1_1 /* !! */ .remove("duration");
                    ** while (true)
                }
                MobclickAgent.getAgent().a(MobclickAgentJSInterface.a(MobclickAgentJSInterface.this), var3_5, (Map<String, Object>)var2_4, var6_8);
                ** GOTO lbl11
            }
            if (!"event".equals(var3_5)) {
                return this.a.onJsPrompt(var1_1 /* !! */ , (String)var2_4, var3_5, (String)var4_6, var5_7);
            }
            try {
                var3_5 = new JSONObject((String)var4_6);
                var2_4 = var3_5.optString("label");
                var1_1 /* !! */  = var2_4;
                if ("".equals(var2_4)) {
                    var1_1 /* !! */  = null;
                }
                MobclickAgent.getAgent().a(MobclickAgentJSInterface.a(MobclickAgentJSInterface.this), var3_5.getString("tag"), (String)var1_1 /* !! */ , var3_5.optInt("duration"), 1);
            }
            catch (Exception var1_3) {
            }
            ** while (true)
        }

        public void onProgressChanged(WebView webView, int n2) {
            this.a.onProgressChanged(webView, n2);
        }

        public void onReceivedIcon(WebView webView, Bitmap bitmap) {
            this.a.onReceivedIcon(webView, bitmap);
        }

        public void onReceivedTitle(WebView webView, String string2) {
            this.a.onReceivedTitle(webView, string2);
        }

        public void onRequestFocus(WebView webView) {
            this.a.onRequestFocus(webView);
        }
    }
}

