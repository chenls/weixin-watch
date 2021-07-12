/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 *  android.os.Parcel
 *  android.os.Parcelable$Creator
 *  android.text.TextUtils
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.google.android.gms.auth.api.signin;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.zzd;
import com.google.android.gms.auth.api.signin.zze;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzx;
import org.json.JSONException;
import org.json.JSONObject;

public class SignInAccount
implements SafeParcelable {
    public static final Parcelable.Creator<SignInAccount> CREATOR = new zze();
    final int versionCode;
    private String zzWP;
    private String zzWQ;
    private Uri zzWR;
    private String zzWk;
    private String zzXj;
    private GoogleSignInAccount zzXm;
    private String zzXn;
    private String zzrG;

    SignInAccount(int n2, String string2, String string3, String string4, String string5, Uri uri, GoogleSignInAccount googleSignInAccount, String string6, String string7) {
        this.versionCode = n2;
        this.zzWP = zzx.zzh(string4, "Email cannot be empty.");
        this.zzWQ = string5;
        this.zzWR = uri;
        this.zzXj = string2;
        this.zzWk = string3;
        this.zzXm = googleSignInAccount;
        this.zzrG = zzx.zzcM(string6);
        this.zzXn = string7;
    }

    public static SignInAccount zza(zzd zzd2, String string2, String string3, String string4, Uri uri, String string5, String string6) {
        String string7 = null;
        if (zzd2 != null) {
            string7 = zzd2.zzmT();
        }
        return new SignInAccount(2, string7, string2, string3, string4, uri, null, string5, string6);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static SignInAccount zzbM(String string2) throws JSONException {
        if (TextUtils.isEmpty((CharSequence)string2)) {
            return null;
        }
        JSONObject jSONObject = new JSONObject(string2);
        if (!TextUtils.isEmpty((CharSequence)(string2 = jSONObject.optString("photoUrl", null)))) {
            string2 = Uri.parse((String)string2);
            return SignInAccount.zza(zzd.zzbL(jSONObject.optString("providerId", null)), jSONObject.optString("tokenId", null), jSONObject.getString("email"), jSONObject.optString("displayName", null), (Uri)string2, jSONObject.getString("localId"), jSONObject.optString("refreshToken")).zza(GoogleSignInAccount.zzbH(jSONObject.optString("googleSignInAccount")));
        }
        string2 = null;
        return SignInAccount.zza(zzd.zzbL(jSONObject.optString("providerId", null)), jSONObject.optString("tokenId", null), jSONObject.getString("email"), jSONObject.optString("displayName", null), (Uri)string2, jSONObject.getString("localId"), jSONObject.optString("refreshToken")).zza(GoogleSignInAccount.zzbH(jSONObject.optString("googleSignInAccount")));
    }

    private JSONObject zzmJ() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("email", (Object)this.getEmail());
            if (!TextUtils.isEmpty((CharSequence)this.zzWQ)) {
                jSONObject.put("displayName", (Object)this.zzWQ);
            }
            if (this.zzWR != null) {
                jSONObject.put("photoUrl", (Object)this.zzWR.toString());
            }
            if (!TextUtils.isEmpty((CharSequence)this.zzXj)) {
                jSONObject.put("providerId", (Object)this.zzXj);
            }
            if (!TextUtils.isEmpty((CharSequence)this.zzWk)) {
                jSONObject.put("tokenId", (Object)this.zzWk);
            }
            if (this.zzXm != null) {
                jSONObject.put("googleSignInAccount", (Object)this.zzXm.zzmI());
            }
            if (!TextUtils.isEmpty((CharSequence)this.zzXn)) {
                jSONObject.put("refreshToken", (Object)this.zzXn);
            }
            jSONObject.put("localId", (Object)this.getUserId());
            return jSONObject;
        }
        catch (JSONException jSONException) {
            throw new RuntimeException(jSONException);
        }
    }

    public int describeContents() {
        return 0;
    }

    public String getDisplayName() {
        return this.zzWQ;
    }

    public String getEmail() {
        return this.zzWP;
    }

    public String getIdToken() {
        return this.zzWk;
    }

    public Uri getPhotoUrl() {
        return this.zzWR;
    }

    public String getUserId() {
        return this.zzrG;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        zze.zza(this, parcel, n2);
    }

    public SignInAccount zza(GoogleSignInAccount googleSignInAccount) {
        this.zzXm = googleSignInAccount;
        return this;
    }

    public String zzmI() {
        return this.zzmJ().toString();
    }

    String zzmT() {
        return this.zzXj;
    }

    public zzd zzmU() {
        return zzd.zzbL(this.zzXj);
    }

    public GoogleSignInAccount zzmV() {
        return this.zzXm;
    }

    public String zzmW() {
        return this.zzXn;
    }
}

