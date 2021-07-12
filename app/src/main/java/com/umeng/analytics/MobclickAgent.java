/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.text.TextUtils
 *  javax.microedition.khronos.opengles.GL10
 */
package com.umeng.analytics;

import android.content.Context;
import android.text.TextUtils;
import com.umeng.analytics.AnalyticsConfig;
import com.umeng.analytics.d;
import com.umeng.analytics.social.UMPlatformData;
import com.umeng.analytics.social.UMSocialService;
import com.umeng.analytics.social.e;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.microedition.khronos.opengles.GL10;
import u.aly.bl;

public class MobclickAgent {
    private static final String a = "input map is null";
    private static final d b = new d();

    public static void enableEncrypt(boolean bl2) {
        b.f(bl2);
    }

    public static d getAgent() {
        return b;
    }

    public static void onEvent(Context context, String string2) {
        b.a(context, string2, null, -1L, 1);
    }

    public static void onEvent(Context context, String string2, String string3) {
        if (TextUtils.isEmpty((CharSequence)string3)) {
            bl.c("label is null or empty");
            return;
        }
        b.a(context, string2, string3, -1L, 1);
    }

    public static void onEvent(Context context, String string2, Map<String, String> map) {
        if (map == null) {
            bl.e(a);
            return;
        }
        b.a(context, string2, new HashMap<String, Object>(map), -1L);
    }

    public static void onEvent(Context context, List<String> list, int n2, String string2) {
        b.a(context, list, n2, string2);
    }

    /*
     * Enabled aggressive block sorting
     */
    public static void onEventValue(Context context, String string2, Map<String, String> map, int n2) {
        map = map == null ? new HashMap<String, Object>() : new HashMap<String, Object>(map);
        ((HashMap)map).put("__ct__", n2);
        b.a(context, string2, map, -1L);
    }

    public static void onKillProcess(Context context) {
        b.d(context);
    }

    public static void onPageEnd(String string2) {
        if (!TextUtils.isEmpty((CharSequence)string2)) {
            b.b(string2);
            return;
        }
        bl.e("pageName is null or empty");
    }

    public static void onPageStart(String string2) {
        if (!TextUtils.isEmpty((CharSequence)string2)) {
            b.a(string2);
            return;
        }
        bl.e("pageName is null or empty");
    }

    public static void onPause(Context context) {
        b.b(context);
    }

    public static void onProfileSignIn(String string2) {
        MobclickAgent.onProfileSignIn("_adhoc", string2);
    }

    public static void onProfileSignIn(String string2, String string3) {
        if (TextUtils.isEmpty((CharSequence)string3)) {
            bl.d("uid is null");
            return;
        }
        if (string3.length() > 64) {
            bl.d("uid is Illegal(length bigger then  legitimate length).");
            return;
        }
        if (TextUtils.isEmpty((CharSequence)string2)) {
            b.b("_adhoc", string3);
            return;
        }
        if (string2.length() > 32) {
            bl.d("provider is Illegal(length bigger then  legitimate length).");
            return;
        }
        b.b(string2, string3);
    }

    public static void onProfileSignOff() {
        b.b();
    }

    public static void onResume(Context context) {
        if (context == null) {
            bl.e("unexpected null context in onResume");
            return;
        }
        b.a(context);
    }

    public static void onSocialEvent(Context context, String string2, UMPlatformData ... uMPlatformDataArray) {
        if (context == null) {
            bl.e("context is null in onShareEvent");
            return;
        }
        e.e = "3";
        UMSocialService.share(context, string2, uMPlatformDataArray);
    }

    public static void onSocialEvent(Context context, UMPlatformData ... uMPlatformDataArray) {
        if (context == null) {
            bl.e("context is null in onShareEvent");
            return;
        }
        e.e = "3";
        UMSocialService.share(context, uMPlatformDataArray);
    }

    public static void openActivityDurationTrack(boolean bl2) {
        b.c(bl2);
    }

    public static void reportError(Context context, String string2) {
        b.a(context, string2);
    }

    public static void reportError(Context context, Throwable throwable) {
        b.a(context, throwable);
    }

    public static void setCatchUncaughtExceptions(boolean bl2) {
        b.a(bl2);
    }

    public static void setCheckDevice(boolean bl2) {
        b.d(bl2);
    }

    public static void setDebugMode(boolean bl2) {
        b.e(bl2);
    }

    public static void setEnableEventBuffer(boolean bl2) {
        b.b(bl2);
    }

    public static void setLatencyWindow(long l2) {
        b.a(l2);
    }

    public static void setLocation(double d2, double d3) {
        b.a(d2, d3);
    }

    public static void setOpenGLContext(GL10 gL10) {
        b.a(gL10);
    }

    public static void setScenarioType(Context context, EScenarioType eScenarioType) {
        b.a(context, eScenarioType);
    }

    public static void setSecret(Context context, String string2) {
        b.b(context, string2);
    }

    public static void setSessionContinueMillis(long l2) {
        b.b(l2);
    }

    public static void setWrapper(String string2, String string3) {
        b.a(string2, string3);
    }

    public static void startWithConfigure(UMAnalyticsConfig uMAnalyticsConfig) {
        if (uMAnalyticsConfig != null) {
            b.a(uMAnalyticsConfig);
        }
    }

    public static enum EScenarioType {
        E_UM_NORMAL(0),
        E_UM_GAME(1),
        E_UM_ANALYTICS_OEM(224),
        E_UM_GAME_OEM(225);

        private int a;

        private EScenarioType(int n3) {
            this.a = n3;
        }

        public int toValue() {
            return this.a;
        }
    }

    public static class UMAnalyticsConfig {
        public String mAppkey = null;
        public String mChannelId = null;
        public Context mContext = null;
        public boolean mIsCrashEnable = true;
        public EScenarioType mType = EScenarioType.E_UM_NORMAL;

        private UMAnalyticsConfig() {
        }

        public UMAnalyticsConfig(Context context, String string2, String string3) {
            this(context, string2, string3, null, true);
        }

        public UMAnalyticsConfig(Context context, String string2, String string3, EScenarioType eScenarioType) {
            this(context, string2, string3, eScenarioType, true);
        }

        public UMAnalyticsConfig(Context context, String string2, String string3, EScenarioType eScenarioType, boolean bl2) {
            this.mContext = context;
            this.mAppkey = string2;
            this.mChannelId = string3;
            this.mIsCrashEnable = bl2;
            if (eScenarioType != null) {
                this.mType = eScenarioType;
                return;
            }
            switch (AnalyticsConfig.getVerticalType(context)) {
                default: {
                    return;
                }
                case 0: {
                    this.mType = EScenarioType.E_UM_NORMAL;
                    return;
                }
                case 1: {
                    this.mType = EScenarioType.E_UM_GAME;
                    return;
                }
                case 224: {
                    this.mType = EScenarioType.E_UM_ANALYTICS_OEM;
                    return;
                }
                case 225: 
            }
            this.mType = EScenarioType.E_UM_GAME_OEM;
        }
    }
}

