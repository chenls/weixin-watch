/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.text.TextUtils
 */
package com.umeng.analytics.social;

import android.text.TextUtils;
import com.umeng.analytics.social.b;
import java.util.Locale;

public class UMPlatformData {
    private UMedia a;
    private String b = "";
    private String c = "";
    private String d;
    private GENDER e;

    public UMPlatformData(UMedia uMedia, String string2) {
        if (uMedia == null || TextUtils.isEmpty((CharSequence)string2)) {
            com.umeng.analytics.social.b.b("MobclickAgent", "parameter is not valid");
            return;
        }
        this.a = uMedia;
        this.b = string2;
    }

    public GENDER getGender() {
        return this.e;
    }

    public UMedia getMeida() {
        return this.a;
    }

    public String getName() {
        return this.d;
    }

    public String getUsid() {
        return this.b;
    }

    public String getWeiboId() {
        return this.c;
    }

    public boolean isValid() {
        return this.a != null && !TextUtils.isEmpty((CharSequence)this.b);
    }

    public void setGender(GENDER gENDER) {
        this.e = gENDER;
    }

    public void setName(String string2) {
        this.d = string2;
    }

    public void setWeiboId(String string2) {
        this.c = string2;
    }

    public String toString() {
        return "UMPlatformData [meida=" + (Object)((Object)this.a) + ", usid=" + this.b + ", weiboId=" + this.c + ", name=" + this.d + ", gender=" + (Object)((Object)this.e) + "]";
    }

    public static enum GENDER {
        MALE(0){

            public String toString() {
                return String.format(Locale.US, "Male:%d", this.value);
            }
        }
        ,
        FEMALE(1){

            public String toString() {
                return String.format(Locale.US, "Female:%d", this.value);
            }
        };

        public int value;

        private GENDER(int n3) {
            this.value = n3;
        }
    }

    public static enum UMedia {
        SINA_WEIBO{

            public String toString() {
                return "sina";
            }
        }
        ,
        TENCENT_WEIBO{

            public String toString() {
                return "tencent";
            }
        }
        ,
        TENCENT_QZONE{

            public String toString() {
                return "qzone";
            }
        }
        ,
        TENCENT_QQ{

            public String toString() {
                return "qq";
            }
        }
        ,
        WEIXIN_FRIENDS{

            public String toString() {
                return "wxsesion";
            }
        }
        ,
        WEIXIN_CIRCLE{

            public String toString() {
                return "wxtimeline";
            }
        }
        ,
        RENREN{

            public String toString() {
                return "renren";
            }
        }
        ,
        DOUBAN{

            public String toString() {
                return "douban";
            }
        };

    }
}

