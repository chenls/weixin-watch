/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable$Creator
 *  android.text.TextUtils
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.google.android.gms.auth.api.signin.internal;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.google.android.gms.auth.api.signin.EmailSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.internal.zze;
import com.google.android.gms.auth.api.signin.internal.zzp;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzx;
import org.json.JSONException;
import org.json.JSONObject;

public final class SignInConfiguration
implements SafeParcelable {
    public static final Parcelable.Creator<SignInConfiguration> CREATOR = new zzp();
    final int versionCode;
    private final String zzXL;
    private EmailSignInOptions zzXM;
    private GoogleSignInOptions zzXN;
    private String zzXO;
    private String zzXd;

    SignInConfiguration(int n2, String string2, String string3, EmailSignInOptions emailSignInOptions, GoogleSignInOptions googleSignInOptions, String string4) {
        this.versionCode = n2;
        this.zzXL = zzx.zzcM(string2);
        this.zzXd = string3;
        this.zzXM = emailSignInOptions;
        this.zzXN = googleSignInOptions;
        this.zzXO = string4;
    }

    public SignInConfiguration(String string2) {
        this(2, string2, null, null, null, null);
    }

    private JSONObject zzmJ() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("consumerPackageName", (Object)this.zzXL);
            if (!TextUtils.isEmpty((CharSequence)this.zzXd)) {
                jSONObject.put("serverClientId", (Object)this.zzXd);
            }
            if (this.zzXM != null) {
                jSONObject.put("emailSignInOptions", (Object)this.zzXM.zzmI());
            }
            if (this.zzXN != null) {
                jSONObject.put("googleSignInOptions", (Object)this.zzXN.zzmI());
            }
            if (!TextUtils.isEmpty((CharSequence)this.zzXO)) {
                jSONObject.put("apiKey", (Object)this.zzXO);
            }
            return jSONObject;
        }
        catch (JSONException jSONException) {
            throw new RuntimeException(jSONException);
        }
    }

    public int describeContents() {
        return 0;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean equals(Object object) {
        if (object == null) {
            return false;
        }
        try {
            if (!this.zzXL.equals(((SignInConfiguration)(object = (SignInConfiguration)object)).zznk())) return false;
            if (TextUtils.isEmpty((CharSequence)this.zzXd)) {
                if (!TextUtils.isEmpty((CharSequence)((SignInConfiguration)object).zzmR())) return false;
            } else if (!this.zzXd.equals(((SignInConfiguration)object).zzmR())) return false;
            if (TextUtils.isEmpty((CharSequence)this.zzXO)) {
                if (!TextUtils.isEmpty((CharSequence)((SignInConfiguration)object).zznn())) return false;
            } else if (!this.zzXO.equals(((SignInConfiguration)object).zznn())) return false;
            if (this.zzXM == null) {
                if (((SignInConfiguration)object).zznl() != null) return false;
            } else if (!this.zzXM.equals(((SignInConfiguration)object).zznl())) return false;
            if (this.zzXN == null) {
                if (((SignInConfiguration)object).zznm() != null) return false;
                return true;
            }
            boolean bl2 = this.zzXN.equals(((SignInConfiguration)object).zznm());
            if (!bl2) return false;
            return true;
        }
        catch (ClassCastException classCastException) {
            return false;
        }
    }

    public int hashCode() {
        return new zze().zzp(this.zzXL).zzp(this.zzXd).zzp(this.zzXO).zzp(this.zzXM).zzp(this.zzXN).zzne();
    }

    public void writeToParcel(Parcel parcel, int n2) {
        zzp.zza(this, parcel, n2);
    }

    public SignInConfiguration zzj(GoogleSignInOptions googleSignInOptions) {
        this.zzXN = zzx.zzb(googleSignInOptions, (Object)"GoogleSignInOptions cannot be null.");
        return this;
    }

    public String zzmI() {
        return this.zzmJ().toString();
    }

    public String zzmR() {
        return this.zzXd;
    }

    public String zznk() {
        return this.zzXL;
    }

    public EmailSignInOptions zznl() {
        return this.zzXM;
    }

    public GoogleSignInOptions zznm() {
        return this.zzXN;
    }

    public String zznn() {
        return this.zzXO;
    }
}

