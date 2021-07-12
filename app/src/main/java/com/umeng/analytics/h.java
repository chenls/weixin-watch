/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 *  android.text.TextUtils
 *  org.json.JSONArray
 *  org.json.JSONObject
 */
package com.umeng.analytics;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.umeng.analytics.f;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;
import u.aly.ap;
import u.aly.av;
import u.aly.bj;
import u.aly.bk;
import u.aly.bl;
import u.aly.m;

public final class h {
    private static h a = null;
    private static Context b;
    private static String c;
    private static long e = 0L;
    private static long f = 0L;
    private static final String g = "mobclick_agent_user_";
    private static final String h = "mobclick_agent_header_";
    private static final String i = "mobclick_agent_update_";
    private static final String j = "mobclick_agent_state_";
    private static final String k = "mobclick_agent_cached_";
    private a d;

    static {
        e = 1209600000L;
        f = 0x200000L;
    }

    public h(Context context) {
        this.d = new a(context);
    }

    public static h a(Context object) {
        synchronized (h.class) {
            b = object.getApplicationContext();
            c = object.getPackageName();
            if (a == null) {
                a = new h((Context)object);
            }
            object = a;
            return object;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void a(av av2, JSONObject jSONObject, StringBuilder stringBuilder) throws Exception {
        jSONObject.put("appkey", (Object)av2.a.a);
        jSONObject.put("channel", (Object)av2.a.b);
        if (av2.a.c != null) {
            jSONObject.put("secret", (Object)av2.a.c);
        }
        jSONObject.put("app_version", (Object)av2.a.d);
        jSONObject.put("display_name", (Object)av2.a.g);
        jSONObject.put("package_name", (Object)av2.a.e);
        jSONObject.put("app_signature", (Object)av2.a.f);
        jSONObject.put("version_code", av2.a.h);
        jSONObject.put("wrapper_type", (Object)av2.a.i);
        jSONObject.put("wrapper_version", (Object)av2.a.j);
        jSONObject.put("sdk_type", (Object)av2.a.k);
        jSONObject.put("sdk_version", (Object)av2.a.l);
        jSONObject.put("vertical_type", av2.a.m);
        jSONObject.put("idmd5", (Object)av2.a.n);
        jSONObject.put("cpu", (Object)av2.a.o);
        jSONObject.put("os", (Object)av2.a.p);
        jSONObject.put("os_version", (Object)av2.a.q);
        jSONObject.put("resolution", (Object)av2.a.r);
        jSONObject.put("mc", (Object)av2.a.s);
        jSONObject.put("device_id", (Object)av2.a.t);
        jSONObject.put("device_model", (Object)av2.a.u);
        jSONObject.put("device_board", (Object)av2.a.v);
        jSONObject.put("device_brand", (Object)av2.a.w);
        jSONObject.put("device_manutime", av2.a.x);
        jSONObject.put("device_manufacturer", (Object)av2.a.y);
        jSONObject.put("device_manuid", (Object)av2.a.z);
        jSONObject.put("device_name", (Object)av2.a.A);
        if (av2.a.B != null) {
            jSONObject.put("sub_os_name", (Object)av2.a.B);
        }
        if (av2.a.C != null) {
            jSONObject.put("sub_os_version", (Object)av2.a.C);
        }
        jSONObject.put("timezone", av2.a.D);
        jSONObject.put("language", (Object)av2.a.E);
        jSONObject.put("country", (Object)av2.a.F);
        jSONObject.put("carrier", (Object)av2.a.G);
        jSONObject.put("access", (Object)av2.a.H);
        jSONObject.put("access_subtype", (Object)av2.a.I);
        String string2 = av2.a.J == null ? "" : av2.a.J;
        jSONObject.put("mccmnc", (Object)string2);
        jSONObject.put("successful_requests", av2.a.K);
        jSONObject.put("failed_requests", av2.a.L);
        jSONObject.put("req_time", av2.a.M);
        jSONObject.put("imprint", (Object)av2.a.N);
        jSONObject.put("id_tracking", (Object)av2.a.O);
        stringBuilder.append("sdk_version:").append(av2.a.l).append(";device_id:").append(av2.a.t).append(";device_manufacturer:").append(av2.a.y).append(";device_board:").append(av2.a.v).append(";device_brand:").append(av2.a.w).append(";os_version:").append(av2.a.q);
    }

    private static boolean a(File file) {
        long l2 = file.length();
        if (file.exists() && l2 > f) {
            m.a(b).a(l2, System.currentTimeMillis(), "__data_size_of");
            return true;
        }
        return false;
    }

    private void b(av av2, JSONObject jSONObject, StringBuilder stringBuilder) throws Exception {
        int n2;
        JSONObject jSONObject2;
        Object object;
        int n3;
        String string2;
        Object object3;
        JSONObject jSONObject3 = new JSONObject();
        if (av2.b.h != null && av2.b.h.a != null && av2.b.h.a.size() > 0) {
            object3 = new JSONObject();
            for (Map.Entry<String, List<av.e>> entry : av2.b.h.a.entrySet()) {
                string2 = entry.getKey();
                List<av.e> list = entry.getValue();
                JSONArray jSONArray = new JSONArray();
                for (n3 = 0; n3 < list.size(); ++n3) {
                    object = list.get(n3);
                    jSONObject2 = new JSONObject();
                    jSONObject2.put("v_sum", ((av.e)object).a);
                    jSONObject2.put("ts_sum", ((av.e)object).b);
                    jSONObject2.put("tw_num", ((av.e)object).c);
                    jSONObject2.put("count", ((av.e)object).d);
                    jSONObject2.put("labels", (Object)new JSONArray(((av.e)object).e));
                    jSONArray.put((Object)jSONObject2);
                }
                object3.put(string2, (Object)jSONArray);
            }
            jSONObject3.put("ag", object3);
        }
        if (av2.b.h != null && av2.b.h.b != null && av2.b.h.b.size() > 0) {
            object3 = new JSONObject();
            for (Map.Entry<String, List<av.f>> entry : av2.b.h.b.entrySet()) {
                string2 = entry.getKey();
                List<av.f> list = entry.getValue();
                JSONArray jSONArray = new JSONArray();
                for (n3 = 0; n3 < list.size(); ++n3) {
                    object = list.get(n3);
                    jSONObject2 = new JSONObject();
                    jSONObject2.put("value", ((av.f)object).a);
                    jSONObject2.put("ts", ((av.f)object).b);
                    jSONObject2.put("label", (Object)((av.f)object).c);
                    jSONArray.put((Object)jSONObject2);
                }
                object3.put(string2, (Object)jSONArray);
            }
            jSONObject3.put("ve_meta", object3);
        }
        if (jSONObject3 != null && jSONObject3.length() > 0) {
            jSONObject.put("cc", (Object)jSONObject3);
            stringBuilder.append("; cc: ").append(jSONObject3.toString());
        }
        if (av2.b.a != null && av2.b.a.size() > 0) {
            jSONObject3 = new JSONArray();
            for (n3 = 0; n3 < av2.b.a.size(); ++n3) {
                object3 = av2.b.a.get(n3);
                JSONArray jSONArray = new JSONArray();
                for (n2 = 0; n2 < ((av.h)object3).b.size(); ++n2) {
                    string2 = new JSONObject();
                    av.j j2 = ((av.h)object3).b.get(n2);
                    string2.put("id", (Object)j2.c);
                    string2.put("ts", j2.d);
                    string2.put("du", j2.e);
                    for (Map.Entry<String, Object> entry : j2.f.entrySet()) {
                        object = entry.getValue();
                        if (!(object instanceof String) && !(object instanceof Integer) && !(object instanceof Long)) continue;
                        string2.put(entry.getKey(), entry.getValue());
                    }
                    jSONArray.put((Object)string2);
                }
                if (((av.h)object3).a == null || jSONArray == null || jSONArray.length() <= 0) continue;
                string2 = new JSONObject();
                string2.put(((av.h)object3).a, (Object)jSONArray);
                jSONObject3.put((Object)string2);
            }
            if (jSONObject3 != null && jSONObject3.length() > 0) {
                jSONObject.put("ekv", (Object)jSONObject3);
                stringBuilder.append(";ekv:").append(jSONObject3.toString());
            }
        }
        if (av2.b.b != null && av2.b.b.size() > 0) {
            jSONObject3 = new JSONArray();
            for (n3 = 0; n3 < av2.b.b.size(); ++n3) {
                object3 = av2.b.b.get(n3);
                JSONArray jSONArray = new JSONArray();
                for (n2 = 0; n2 < ((av.h)object3).b.size(); ++n2) {
                    av.j j3 = ((av.h)object3).b.get(n2);
                    string2 = new JSONObject();
                    string2.put("id", (Object)j3.c);
                    string2.put("ts", j3.d);
                    string2.put("du", j3.e);
                    for (Map.Entry<String, Object> entry : j3.f.entrySet()) {
                        object = entry.getValue();
                        if (!(object instanceof String) && !(object instanceof Integer) && !(object instanceof Long)) continue;
                        string2.put(entry.getKey(), entry.getValue());
                    }
                    jSONArray.put((Object)string2);
                }
                if (((av.h)object3).a == null || jSONArray == null || jSONArray.length() <= 0) continue;
                string2 = new JSONObject();
                string2.put(((av.h)object3).a, (Object)jSONArray);
                jSONObject3.put((Object)string2);
            }
            if (jSONObject3 != null && jSONObject3.length() > 0) {
                jSONObject.put("gkv", (Object)jSONObject3);
                stringBuilder.append("; gkv:").append(jSONObject3.toString());
            }
        }
        if (av2.b.i != null && av2.b.i.size() > 0) {
            jSONObject3 = new JSONArray();
            for (n3 = 0; n3 < av2.b.i.size(); ++n3) {
                object3 = av2.b.i.get(n3);
                JSONObject jSONObject4 = new JSONObject();
                jSONObject4.put("ts", ((av.i)object3).a);
                jSONObject4.put("error_source", ((av.i)object3).b);
                jSONObject4.put("context", (Object)((av.i)object3).c);
                jSONObject3.put((Object)jSONObject4);
            }
            jSONObject.put("error", (Object)jSONObject3);
        }
        if (av2.b.c != null && av2.b.c.size() > 0) {
            jSONObject3 = new JSONArray();
            for (n3 = 0; n3 < av2.b.c.size(); ++n3) {
                object3 = av2.b.c.get(n3);
                JSONObject jSONObject5 = new JSONObject();
                jSONObject5.put("id", (Object)((av.o)object3).b);
                jSONObject5.put("start_time", ((av.o)object3).c);
                jSONObject5.put("end_time", ((av.o)object3).d);
                jSONObject5.put("duration", ((av.o)object3).e);
                if (object3.i.a != 0L || object3.i.b != 0L) {
                    string2 = new JSONObject();
                    string2.put("download_traffic", object3.i.a);
                    string2.put("upload_traffic", object3.i.b);
                    jSONObject5.put("traffic", (Object)string2);
                }
                if (((av.o)object3).h.size() > 0) {
                    string2 = new JSONArray();
                    for (av.l l2 : ((av.o)object3).h) {
                        object = new JSONObject();
                        object.put("page_name", (Object)l2.a);
                        object.put("duration", l2.b);
                        string2.put(object);
                    }
                    jSONObject5.put("pages", (Object)string2);
                }
                if (object3.j.c != 0L) {
                    string2 = new JSONArray();
                    JSONObject jSONObject6 = new JSONObject();
                    jSONObject6.put("lat", object3.j.a);
                    jSONObject6.put("lng", object3.j.b);
                    jSONObject6.put("ts", object3.j.c);
                    string2.put((Object)jSONObject6);
                    jSONObject5.put("locations", (Object)string2);
                }
                jSONObject3.put((Object)jSONObject5);
            }
            if (jSONObject3 != null && jSONObject3.length() > 0) {
                jSONObject.put("sessions", (Object)jSONObject3);
                stringBuilder.append("; sessions:").append(jSONObject3.toString());
            }
        }
        if (av2.b.d.a != 0L) {
            jSONObject3 = new JSONObject();
            jSONObject3.put("ts", av2.b.d.a);
            if (jSONObject3.length() > 0) {
                jSONObject.put("activate_msg", (Object)jSONObject3);
                stringBuilder.append("; active_msg: ").append(jSONObject3.toString());
            }
        }
        if (av2.b.e.c) {
            jSONObject3 = new JSONObject();
            object3 = new JSONObject();
            object3.put("interval", av2.b.e.b);
            object3.put("latency", (Object)av2.b.e.a);
            jSONObject3.put("latent", object3);
            if (jSONObject3.length() > 0) {
                jSONObject.put("control_policy", (Object)jSONObject3);
                stringBuilder.append("; control_policy: ").append(jSONObject3.toString());
            }
        }
        if (av2.b.f.size() > 0) {
            jSONObject3 = new JSONObject();
            for (Map.Entry entry : av2.b.f.entrySet()) {
                jSONObject3.put((String)entry.getKey(), entry.getValue());
            }
            jSONObject.put("group_info", (Object)jSONObject3);
        }
        if (av2.b.g.a != null || av2.b.g.b != null) {
            jSONObject3 = new JSONObject();
            jSONObject3.put("provider", (Object)av2.b.g.a);
            jSONObject3.put("puid", (Object)av2.b.g.b);
            if (jSONObject3.length() > 0) {
                jSONObject.put("active_user", (Object)jSONObject3);
                stringBuilder.append("; active_user: ").append(jSONObject3.toString());
            }
        }
        if (av2.b.j != null) {
            jSONObject.put("userlevel", (Object)av2.b.j);
        }
    }

    private SharedPreferences o() {
        return b.getSharedPreferences(g + c, 0);
    }

    private String p() {
        return h + c;
    }

    private String q() {
        SharedPreferences sharedPreferences = ap.a(b);
        if (sharedPreferences != null) {
            int n2 = sharedPreferences.getInt("versioncode", 0);
            int n3 = Integer.parseInt(bj.c(b));
            if (n2 != 0 && n3 != n2) {
                return k + c + n2;
            }
            return k + c + bj.c(b);
        }
        return k + c + bj.c(b);
    }

    void a(int n2) {
        SharedPreferences sharedPreferences = ap.a(b);
        if (sharedPreferences != null) {
            sharedPreferences.edit().putInt("vt", n2).commit();
        }
    }

    void a(String string2) {
        SharedPreferences sharedPreferences = ap.a(b);
        if (sharedPreferences != null) {
            sharedPreferences.edit().putString("appkey", string2).commit();
        }
    }

    public void a(String string2, String string3) {
        if (!TextUtils.isEmpty((CharSequence)string2) && !TextUtils.isEmpty((CharSequence)string3)) {
            SharedPreferences.Editor editor = this.o().edit();
            editor.putString("au_p", string2);
            editor.putString("au_u", string3);
            editor.commit();
        }
    }

    /*
     * Unable to fully structure code
     */
    public void a(av var1_1) {
        block32: {
            var2_8 = null;
            var3_12 = this.q();
            var3_12 = new File(com.umeng.analytics.h.b.getApplicationContext().getFilesDir().getAbsolutePath(), (String)var3_12);
            var3_12 = new FileOutputStream((File)var3_12);
            var2_8 = new ObjectOutputStream((OutputStream)var3_12);
            var2_8.writeObject(var1_1);
            var2_8.flush();
            if (var2_8 == null) break block32;
            try {
                var2_8.close();
            }
            catch (IOException var1_2) {
                var1_2.printStackTrace();
                ** continue;
            }
        }
lbl15:
        // 2 sources

        while (true) {
            if (var3_12 == null) ** GOTO lbl19
            var3_12.close();
lbl19:
            // 3 sources

            return;
            break;
        }
        catch (IOException var1_3) {
            var1_3.printStackTrace();
            return;
        }
        catch (Exception var3_13) {
            var4_14 = null;
            var1_1 = var2_8;
            var2_8 = var4_14;
lbl31:
            // 3 sources

            while (true) {
                block33: {
                    bl.e((Throwable)var3_12);
                    var3_12.printStackTrace();
                    if (var2_8 == null) break block33;
                    try {
                        var2_8.close();
                    }
                    catch (IOException var2_9) {
                        var2_9.printStackTrace();
                        ** continue;
                    }
                }
lbl42:
                // 2 sources

                while (true) {
                    if (var1_1 == null) ** continue;
                    try {
                        var1_1.close();
                        return;
                    }
                    catch (IOException var1_4) {
                        var1_4.printStackTrace();
                        return;
                    }
                    break;
                }
                break;
            }
        }
        catch (Throwable var1_5) {
            var2_8 = null;
            var3_12 = null;
lbl53:
            // 4 sources

            while (true) {
                if (var2_8 != null) {
                    var2_8.close();
                }
lbl57:
                // 4 sources

                while (true) {
                    if (var3_12 != null) {
                        var3_12.close();
                    }
lbl61:
                    // 4 sources

                    throw var1_1;
                }
                catch (IOException var2_10) {
                    var2_10.printStackTrace();
                    ** continue;
                }
                catch (IOException var2_11) {
                    var2_11.printStackTrace();
                    ** continue;
                }
                break;
            }
        }
        catch (Throwable var1_6) {
            var2_8 = null;
            ** GOTO lbl53
        }
        catch (Throwable var1_7) {
            ** GOTO lbl53
        }
        {
            catch (Throwable var4_15) {
                var3_12 = var1_1;
                var1_1 = var4_15;
                ** continue;
            }
        }
        catch (Exception var4_16) {
            var2_8 = null;
            var1_1 = var3_12;
            var3_12 = var4_16;
            ** GOTO lbl31
        }
        catch (Exception var4_17) {
            var1_1 = var3_12;
            var3_12 = var4_17;
            ** continue;
        }
    }

    public void a(byte[] byArray) {
        this.d.a(byArray);
    }

    public String[] a() {
        Object var2_1 = null;
        String[] stringArray = this.o();
        String string2 = stringArray.getString("au_p", null);
        String string3 = stringArray.getString("au_u", null);
        stringArray = var2_1;
        if (string2 != null) {
            stringArray = var2_1;
            if (string3 != null) {
                stringArray = new String[]{string2, string3};
            }
        }
        return stringArray;
    }

    public void b() {
        this.o().edit().remove("au_p").remove("au_u").commit();
    }

    void b(String string2) {
        SharedPreferences sharedPreferences = ap.a(b);
        if (sharedPreferences != null) {
            sharedPreferences.edit().putString("channel", string2).commit();
        }
    }

    public byte[] b(av object) {
        try {
            JSONObject jSONObject = new JSONObject();
            StringBuilder stringBuilder = new StringBuilder();
            jSONObject.put("header", (Object)new JSONObject((av)object, stringBuilder){
                final /* synthetic */ av a;
                final /* synthetic */ StringBuilder b;
                {
                    this.a = av2;
                    this.b = stringBuilder;
                    h.this.a(this.a, this, this.b);
                }
            });
            object = new JSONObject((av)object, stringBuilder){
                final /* synthetic */ av a;
                final /* synthetic */ StringBuilder b;
                {
                    this.a = av2;
                    this.b = stringBuilder;
                    h.this.b(this.a, this, this.b);
                }
            };
            if (object.length() > 0) {
                jSONObject.put("body", object);
            }
            bl.b("serialize entry:" + String.valueOf(stringBuilder));
            object = String.valueOf(jSONObject).getBytes();
            return object;
        }
        catch (Exception exception) {
            bl.e("Fail to serialize log ...", exception);
            return null;
        }
    }

    String c() {
        String string2 = null;
        SharedPreferences sharedPreferences = ap.a(b);
        if (sharedPreferences != null) {
            string2 = sharedPreferences.getString("appkey", null);
        }
        return string2;
    }

    void c(String string2) {
        SharedPreferences sharedPreferences = ap.a(b);
        if (sharedPreferences != null) {
            sharedPreferences.edit().putString("st", string2).commit();
        }
    }

    String d() {
        String string2 = null;
        SharedPreferences sharedPreferences = ap.a(b);
        if (sharedPreferences != null) {
            string2 = sharedPreferences.getString("channel", null);
        }
        return string2;
    }

    String e() {
        String string2 = null;
        SharedPreferences sharedPreferences = ap.a(b);
        if (sharedPreferences != null) {
            string2 = sharedPreferences.getString("st", null);
        }
        return string2;
    }

    int f() {
        int n2 = 0;
        SharedPreferences sharedPreferences = ap.a(b);
        if (sharedPreferences != null) {
            n2 = sharedPreferences.getInt("vt", 0);
        }
        return n2;
    }

    /*
     * Exception decompiling
     */
    public av g() {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Back jump on a try block [egrp 8[TRYBLOCK] [13 : 138->142)] java.lang.Exception
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

    public void h() {
        b.deleteFile(this.p());
        b.deleteFile(this.q());
        m.a(b).d(new u.aly.f(){

            @Override
            public void a(Object object, boolean bl2) {
                if (object.equals("success")) {
                    // empty if block
                }
            }
        });
    }

    public boolean i() {
        return this.d.a();
    }

    public a j() {
        return this.d;
    }

    public SharedPreferences k() {
        return b.getSharedPreferences(h + c, 0);
    }

    public SharedPreferences l() {
        return b.getSharedPreferences(i + c, 0);
    }

    public SharedPreferences m() {
        return b.getSharedPreferences(j + c, 0);
    }

    public static class a {
        private final int a;
        private File b;
        private FilenameFilter c = new FilenameFilter(){

            @Override
            public boolean accept(File file, String string2) {
                return string2.startsWith("um");
            }
        };

        public a(Context context) {
            this(context, ".um");
        }

        public a(Context context, String string2) {
            this.a = 10;
            this.b = new File(context.getFilesDir(), string2);
            if (!this.b.exists() || !this.b.isDirectory()) {
                this.b.mkdir();
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void a(b b2) {
            int n2;
            int n3;
            int n4 = 0;
            Object[] objectArray = this.b.listFiles(this.c);
            if (objectArray != null && objectArray.length >= 10) {
                Arrays.sort(objectArray);
                n3 = objectArray.length - 10;
                com.umeng.analytics.f.b(new Runnable(){

                    @Override
                    public void run() {
                        if (n3 > 0) {
                            m.a(b).a(n3, System.currentTimeMillis(), "__evp_file_of");
                        }
                    }
                });
                for (n2 = 0; n2 < n3; ++n2) {
                    ((File)objectArray[n2]).delete();
                }
            }
            if (objectArray != null && objectArray.length > 0) {
                b2.a(this.b);
                n3 = objectArray.length;
                for (n2 = n4; n2 < n3; ++n2) {
                    try {
                        boolean bl2 = b2.b((File)objectArray[n2]);
                        if (!bl2) continue;
                        ((File)objectArray[n2]).delete();
                        continue;
                    }
                    catch (Throwable throwable) {
                        ((File)objectArray[n2]).delete();
                        continue;
                    }
                    catch (Throwable throwable) {
                        throw throwable;
                    }
                }
                b2.c(this.b);
            }
        }

        public void a(byte[] byArray) {
            if (byArray == null || byArray.length == 0) {
                return;
            }
            Object object = String.format(Locale.US, "um_cache_%d.env", System.currentTimeMillis());
            object = new File(this.b, (String)object);
            try {
                bk.a((File)object, byArray);
                return;
            }
            catch (Exception exception) {
                return;
            }
        }

        public boolean a() {
            File[] fileArray = this.b.listFiles();
            return fileArray != null && fileArray.length > 0;
        }

        public void b() {
            File[] fileArray = this.b.listFiles(this.c);
            if (fileArray != null && fileArray.length > 0) {
                int n2 = fileArray.length;
                for (int i2 = 0; i2 < n2; ++i2) {
                    fileArray[i2].delete();
                }
            }
        }

        public int c() {
            File[] fileArray = this.b.listFiles(this.c);
            if (fileArray != null && fileArray.length > 0) {
                return fileArray.length;
            }
            return 0;
        }
    }

    public static interface b {
        public void a(File var1);

        public boolean b(File var1);

        public void c(File var1);
    }
}

