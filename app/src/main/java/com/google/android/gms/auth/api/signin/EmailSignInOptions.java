/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 *  android.os.Parcel
 *  android.os.Parcelable$Creator
 *  android.text.TextUtils
 *  android.util.Patterns
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.google.android.gms.auth.api.signin;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Patterns;
import com.google.android.gms.auth.api.signin.internal.zze;
import com.google.android.gms.auth.api.signin.zza;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzx;
import org.json.JSONException;
import org.json.JSONObject;

public class EmailSignInOptions
implements SafeParcelable {
    public static final Parcelable.Creator<EmailSignInOptions> CREATOR = new zza();
    final int versionCode;
    private final Uri zzWL;
    private String zzWM;
    private Uri zzWN;

    EmailSignInOptions(int n2, Uri uri, String string2, Uri uri2) {
        zzx.zzb(uri, (Object)"Server widget url cannot be null in order to use email/password sign in.");
        zzx.zzh(uri.toString(), "Server widget url cannot be null in order to use email/password sign in.");
        zzx.zzb(Patterns.WEB_URL.matcher(uri.toString()).matches(), (Object)"Invalid server widget url");
        this.versionCode = n2;
        this.zzWL = uri;
        this.zzWM = string2;
        this.zzWN = uri2;
    }

    private JSONObject zzmJ() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("serverWidgetUrl", (Object)this.zzWL.toString());
            if (!TextUtils.isEmpty((CharSequence)this.zzWM)) {
                jSONObject.put("modeQueryName", (Object)this.zzWM);
            }
            if (this.zzWN != null) {
                jSONObject.put("tosUrl", (Object)this.zzWN.toString());
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
            if (!this.zzWL.equals((Object)((EmailSignInOptions)(object = (EmailSignInOptions)object)).zzmF())) return false;
            if (this.zzWN == null) {
                if (((EmailSignInOptions)object).zzmG() != null) return false;
            } else if (!this.zzWN.equals((Object)((EmailSignInOptions)object).zzmG())) return false;
            if (TextUtils.isEmpty((CharSequence)this.zzWM)) {
                if (!TextUtils.isEmpty((CharSequence)((EmailSignInOptions)object).zzmH())) return false;
                return true;
            }
            boolean bl2 = this.zzWM.equals(((EmailSignInOptions)object).zzmH());
            if (!bl2) return false;
            return true;
        }
        catch (ClassCastException classCastException) {
            return false;
        }
    }

    public int hashCode() {
        return new zze().zzp(this.zzWL).zzp(this.zzWN).zzp(this.zzWM).zzne();
    }

    public void writeToParcel(Parcel parcel, int n2) {
        zza.zza(this, parcel, n2);
    }

    public Uri zzmF() {
        return this.zzWL;
    }

    public Uri zzmG() {
        return this.zzWN;
    }

    public String zzmH() {
        return this.zzWM;
    }

    public String zzmI() {
        return this.zzmJ().toString();
    }
}

