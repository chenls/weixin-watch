/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.SharedPreferences
 *  android.text.TextUtils
 *  org.json.JSONException
 */
package com.google.android.gms.auth.api.signin.internal;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.SignInAccount;
import com.google.android.gms.auth.api.signin.internal.SignInConfiguration;
import com.google.android.gms.common.internal.zzx;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.json.JSONException;

public class zzq {
    private static final Lock zzYa = new ReentrantLock();
    private static zzq zzYb;
    private final Lock zzYc = new ReentrantLock();
    private final SharedPreferences zzYd;

    zzq(Context context) {
        this.zzYd = context.getSharedPreferences("com.google.android.gms.signin", 0);
    }

    public static zzq zzaf(Context object) {
        zzx.zzz(object);
        zzYa.lock();
        try {
            if (zzYb == null) {
                zzYb = new zzq(object.getApplicationContext());
            }
            object = zzYb;
            return object;
        }
        finally {
            zzYa.unlock();
        }
    }

    private String zzs(String string2, String string3) {
        return string2 + ":" + string3;
    }

    void zza(GoogleSignInAccount googleSignInAccount, GoogleSignInOptions googleSignInOptions) {
        zzx.zzz(googleSignInAccount);
        zzx.zzz(googleSignInOptions);
        String string2 = googleSignInAccount.zzmL();
        this.zzr(this.zzs("googleSignInAccount", string2), googleSignInAccount.zzmM());
        this.zzr(this.zzs("googleSignInOptions", string2), googleSignInOptions.zzmI());
    }

    void zza(SignInAccount signInAccount, SignInConfiguration signInConfiguration) {
        zzx.zzz(signInAccount);
        zzx.zzz(signInConfiguration);
        String string2 = signInAccount.getUserId();
        SignInAccount signInAccount2 = this.zzbP(string2);
        if (signInAccount2 != null && signInAccount2.zzmV() != null) {
            this.zzbU(signInAccount2.zzmV().zzmL());
        }
        this.zzr(this.zzs("signInConfiguration", string2), signInConfiguration.zzmI());
        this.zzr(this.zzs("signInAccount", string2), signInAccount.zzmI());
        if (signInAccount.zzmV() != null) {
            this.zza(signInAccount.zzmV(), signInConfiguration.zznm());
        }
    }

    public void zzb(GoogleSignInAccount googleSignInAccount, GoogleSignInOptions googleSignInOptions) {
        zzx.zzz(googleSignInAccount);
        zzx.zzz(googleSignInOptions);
        this.zzr("defaultGoogleSignInAccount", googleSignInAccount.zzmL());
        this.zza(googleSignInAccount, googleSignInOptions);
    }

    public void zzb(SignInAccount signInAccount, SignInConfiguration signInConfiguration) {
        zzx.zzz(signInAccount);
        zzx.zzz(signInConfiguration);
        this.zznq();
        this.zzr("defaultSignInAccount", signInAccount.getUserId());
        if (signInAccount.zzmV() != null) {
            this.zzr("defaultGoogleSignInAccount", signInAccount.zzmV().zzmL());
        }
        this.zza(signInAccount, signInConfiguration);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    SignInAccount zzbP(String object) {
        if (TextUtils.isEmpty((CharSequence)object) || TextUtils.isEmpty((CharSequence)(object = this.zzbS(this.zzs("signInAccount", (String)object))))) {
            return null;
        }
        try {
            GoogleSignInAccount googleSignInAccount;
            object = SignInAccount.zzbM((String)object);
            if (((SignInAccount)object).zzmV() != null && (googleSignInAccount = this.zzbQ(((SignInAccount)object).zzmV().zzmL())) != null) {
                ((SignInAccount)object).zza(googleSignInAccount);
            }
            return object;
        }
        catch (JSONException jSONException) {
            return null;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    GoogleSignInAccount zzbQ(String object) {
        if (TextUtils.isEmpty((CharSequence)object)) {
            return null;
        }
        if ((object = this.zzbS(this.zzs("googleSignInAccount", (String)object))) == null) return null;
        try {
            return GoogleSignInAccount.zzbH((String)object);
        }
        catch (JSONException jSONException) {
            return null;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    GoogleSignInOptions zzbR(String object) {
        if (TextUtils.isEmpty((CharSequence)object)) {
            return null;
        }
        if ((object = this.zzbS(this.zzs("googleSignInOptions", (String)object))) == null) return null;
        try {
            return GoogleSignInOptions.zzbJ((String)object);
        }
        catch (JSONException jSONException) {
            return null;
        }
    }

    protected String zzbS(String string2) {
        this.zzYc.lock();
        try {
            string2 = this.zzYd.getString(string2, null);
            return string2;
        }
        finally {
            this.zzYc.unlock();
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    void zzbT(String string2) {
        SignInAccount signInAccount;
        block3: {
            block2: {
                if (TextUtils.isEmpty((CharSequence)string2)) break block2;
                signInAccount = this.zzbP(string2);
                this.zzbV(this.zzs("signInAccount", string2));
                this.zzbV(this.zzs("signInConfiguration", string2));
                if (signInAccount != null && signInAccount.zzmV() != null) break block3;
            }
            return;
        }
        this.zzbU(signInAccount.zzmV().zzmL());
    }

    void zzbU(String string2) {
        if (TextUtils.isEmpty((CharSequence)string2)) {
            return;
        }
        this.zzbV(this.zzs("googleSignInAccount", string2));
        this.zzbV(this.zzs("googleSignInOptions", string2));
    }

    protected void zzbV(String string2) {
        this.zzYc.lock();
        try {
            this.zzYd.edit().remove(string2).apply();
            return;
        }
        finally {
            this.zzYc.unlock();
        }
    }

    public GoogleSignInAccount zzno() {
        return this.zzbQ(this.zzbS("defaultGoogleSignInAccount"));
    }

    public GoogleSignInOptions zznp() {
        return this.zzbR(this.zzbS("defaultGoogleSignInAccount"));
    }

    public void zznq() {
        String string2 = this.zzbS("defaultSignInAccount");
        this.zzbV("defaultSignInAccount");
        this.zznr();
        this.zzbT(string2);
    }

    public void zznr() {
        String string2 = this.zzbS("defaultGoogleSignInAccount");
        this.zzbV("defaultGoogleSignInAccount");
        this.zzbU(string2);
    }

    protected void zzr(String string2, String string3) {
        this.zzYc.lock();
        try {
            this.zzYd.edit().putString(string2, string3).apply();
            return;
        }
        finally {
            this.zzYc.unlock();
        }
    }
}

