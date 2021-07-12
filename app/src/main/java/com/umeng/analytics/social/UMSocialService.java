/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.AsyncTask
 *  android.text.TextUtils
 *  android.util.Log
 *  org.json.JSONObject
 */
package com.umeng.analytics.social;

import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import com.umeng.analytics.social.UMPlatformData;
import com.umeng.analytics.social.c;
import com.umeng.analytics.social.d;
import com.umeng.analytics.social.f;
import org.json.JSONObject;

public abstract class UMSocialService {
    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static void a(Context var0, b var1_3, String var2_4, UMPlatformData ... var3_5) {
        block6: {
            var4_6 = 0;
            if (var3_5 == null) ** GOTO lbl-1000
            try {
                var5_7 = var3_5.length;
lbl5:
                // 2 sources

                while (var4_6 < var5_7) {
                    if (!var3_5[var4_6].isValid()) {
                        throw new com.umeng.analytics.social.a("parameter is not valid.");
                    }
                    break block6;
                }
                ** GOTO lbl-1000
            }
            catch (com.umeng.analytics.social.a var0_1) {
                Log.e((String)"MobclickAgent", (String)"unable send event.", (Throwable)var0_1);
                return;
            }
            catch (Exception var0_2) {
                Log.e((String)"MobclickAgent", (String)"", (Throwable)var0_2);
                return;
            }
        }
        ++var4_6;
        ** GOTO lbl5
lbl-1000:
        // 2 sources

        {
            new a(f.a(var0, var2_4, var3_5), var1_3, var3_5).execute(new Void[0]);
            return;
        }
    }

    public static void share(Context context, String string2, UMPlatformData ... uMPlatformDataArray) {
        UMSocialService.a(context, null, string2, uMPlatformDataArray);
    }

    public static void share(Context context, UMPlatformData ... uMPlatformDataArray) {
        UMSocialService.a(context, null, null, uMPlatformDataArray);
    }

    private static class a
    extends AsyncTask<Void, Void, d> {
        String a;
        String b;
        b c;
        UMPlatformData[] d;

        public a(String[] stringArray, b b2, UMPlatformData[] uMPlatformDataArray) {
            this.a = stringArray[0];
            this.b = stringArray[1];
            this.c = b2;
            this.d = uMPlatformDataArray;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        protected d a(Void ... object) {
            object = TextUtils.isEmpty((CharSequence)this.b) ? com.umeng.analytics.social.c.a(this.a) : com.umeng.analytics.social.c.a(this.a, this.b);
            try {
                String string2;
                JSONObject jSONObject = new JSONObject((String)object);
                int n2 = jSONObject.optInt("st");
                if (n2 == 0) {
                    n2 = -404;
                }
                object = new d(n2);
                String string3 = jSONObject.optString("msg");
                if (!TextUtils.isEmpty((CharSequence)string3)) {
                    ((d)object).a(string3);
                }
                if (!TextUtils.isEmpty((CharSequence)(string2 = jSONObject.optString("data")))) {
                    ((d)object).b(string2);
                }
                return object;
            }
            catch (Exception exception) {
                return new d(-99, exception);
            }
        }

        protected void a(d d2) {
            if (this.c != null) {
                this.c.a(d2, this.d);
            }
        }

        protected /* synthetic */ Object doInBackground(Object[] objectArray) {
            return this.a((Void[])objectArray);
        }

        protected /* synthetic */ void onPostExecute(Object object) {
            this.a((d)object);
        }

        protected void onPreExecute() {
            if (this.c != null) {
                this.c.a();
            }
        }
    }

    public static interface b {
        public void a();

        public void a(d var1, UMPlatformData ... var2);
    }
}

