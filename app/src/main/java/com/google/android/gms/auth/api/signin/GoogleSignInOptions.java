/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.accounts.Account
 *  android.os.Parcel
 *  android.os.Parcelable$Creator
 *  android.text.TextUtils
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.google.android.gms.auth.api.signin;

import android.accounts.Account;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.google.android.gms.auth.api.signin.internal.zze;
import com.google.android.gms.auth.api.signin.zzc;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzx;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GoogleSignInOptions
implements Api.ApiOptions.Optional,
SafeParcelable {
    public static final Parcelable.Creator<GoogleSignInOptions> CREATOR;
    public static final GoogleSignInOptions DEFAULT_SIGN_IN;
    private static Comparator<Scope> zzWV;
    public static final Scope zzWW;
    public static final Scope zzWX;
    public static final Scope zzWY;
    final int versionCode;
    private Account zzTI;
    private final ArrayList<Scope> zzWZ;
    private boolean zzXa;
    private final boolean zzXb;
    private final boolean zzXc;
    private String zzXd;
    private String zzXe;

    static {
        zzWW = new Scope("profile");
        zzWX = new Scope("email");
        zzWY = new Scope("openid");
        DEFAULT_SIGN_IN = new Builder().requestId().requestProfile().build();
        CREATOR = new zzc();
        zzWV = new Comparator<Scope>(){

            @Override
            public /* synthetic */ int compare(Object object, Object object2) {
                return this.zza((Scope)object, (Scope)object2);
            }

            public int zza(Scope scope, Scope scope2) {
                return scope.zzpb().compareTo(scope2.zzpb());
            }
        };
    }

    GoogleSignInOptions(int n2, ArrayList<Scope> arrayList, Account account, boolean bl2, boolean bl3, boolean bl4, String string2, String string3) {
        this.versionCode = n2;
        this.zzWZ = arrayList;
        this.zzTI = account;
        this.zzXa = bl2;
        this.zzXb = bl3;
        this.zzXc = bl4;
        this.zzXd = string2;
        this.zzXe = string3;
    }

    private GoogleSignInOptions(Set<Scope> set, Account account, boolean bl2, boolean bl3, boolean bl4, String string2, String string3) {
        this(2, new ArrayList<Scope>(set), account, bl2, bl3, bl4, string2, string3);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Nullable
    public static GoogleSignInOptions zzbJ(@Nullable String string2) throws JSONException {
        if (TextUtils.isEmpty((CharSequence)string2)) {
            return null;
        }
        JSONObject jSONObject = new JSONObject(string2);
        HashSet<Scope> hashSet = new HashSet<Scope>();
        string2 = jSONObject.getJSONArray("scopes");
        int n2 = string2.length();
        for (int i2 = 0; i2 < n2; ++i2) {
            hashSet.add(new Scope(string2.getString(i2)));
        }
        string2 = jSONObject.optString("accountName", null);
        if (!TextUtils.isEmpty((CharSequence)string2)) {
            string2 = new Account(string2, "com.google");
            return new GoogleSignInOptions(hashSet, (Account)string2, jSONObject.getBoolean("idTokenRequested"), jSONObject.getBoolean("serverAuthRequested"), jSONObject.getBoolean("forceCodeForRefreshToken"), jSONObject.optString("serverClientId", null), jSONObject.optString("hostedDomain", null));
        }
        string2 = null;
        return new GoogleSignInOptions(hashSet, (Account)string2, jSONObject.getBoolean("idTokenRequested"), jSONObject.getBoolean("serverAuthRequested"), jSONObject.getBoolean("forceCodeForRefreshToken"), jSONObject.optString("serverClientId", null), jSONObject.optString("hostedDomain", null));
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private JSONObject zzmJ() {
        JSONArray jSONArray;
        JSONObject jSONObject = new JSONObject();
        try {
            jSONArray = new JSONArray();
            Collections.sort(this.zzWZ, zzWV);
            Iterator<Scope> iterator = this.zzWZ.iterator();
            while (iterator.hasNext()) {
                jSONArray.put((Object)iterator.next().zzpb());
            }
        }
        catch (JSONException jSONException) {
            throw new RuntimeException(jSONException);
        }
        {
            jSONObject.put("scopes", (Object)jSONArray);
            if (this.zzTI != null) {
                jSONObject.put("accountName", (Object)this.zzTI.name);
            }
            jSONObject.put("idTokenRequested", this.zzXa);
            jSONObject.put("forceCodeForRefreshToken", this.zzXc);
            jSONObject.put("serverAuthRequested", this.zzXb);
            if (!TextUtils.isEmpty((CharSequence)this.zzXd)) {
                jSONObject.put("serverClientId", (Object)this.zzXd);
            }
            if (!TextUtils.isEmpty((CharSequence)this.zzXe)) {
                jSONObject.put("hostedDomain", (Object)this.zzXe);
            }
            return jSONObject;
        }
    }

    public int describeContents() {
        return 0;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public boolean equals(Object object) {
        if (object == null) {
            return false;
        }
        try {
            object = (GoogleSignInOptions)object;
            if (this.zzWZ.size() != ((GoogleSignInOptions)object).zzmN().size()) return false;
            if (!this.zzWZ.containsAll(((GoogleSignInOptions)object).zzmN())) return false;
            if (this.zzTI == null) {
                if (((GoogleSignInOptions)object).getAccount() != null) return false;
            } else if (!this.zzTI.equals((Object)((GoogleSignInOptions)object).getAccount())) return false;
            if (TextUtils.isEmpty((CharSequence)this.zzXd)) {
                if (!TextUtils.isEmpty((CharSequence)((GoogleSignInOptions)object).zzmR())) return false;
            } else {
                boolean bl2 = this.zzXd.equals(((GoogleSignInOptions)object).zzmR());
                if (!bl2) return false;
            }
            if (this.zzXc != ((GoogleSignInOptions)object).zzmQ()) return false;
            if (this.zzXa != ((GoogleSignInOptions)object).zzmO()) return false;
            if (this.zzXb != ((GoogleSignInOptions)object).zzmP()) return false;
            return true;
        }
        catch (ClassCastException classCastException) {
            return false;
        }
    }

    public Account getAccount() {
        return this.zzTI;
    }

    public Scope[] getScopeArray() {
        return this.zzWZ.toArray(new Scope[this.zzWZ.size()]);
    }

    public int hashCode() {
        ArrayList<String> arrayList = new ArrayList<String>();
        Iterator<Scope> iterator = this.zzWZ.iterator();
        while (iterator.hasNext()) {
            arrayList.add(iterator.next().zzpb());
        }
        Collections.sort(arrayList);
        return new zze().zzp(arrayList).zzp(this.zzTI).zzp(this.zzXd).zzP(this.zzXc).zzP(this.zzXa).zzP(this.zzXb).zzne();
    }

    public void writeToParcel(Parcel parcel, int n2) {
        zzc.zza(this, parcel, n2);
    }

    public String zzmI() {
        return this.zzmJ().toString();
    }

    public ArrayList<Scope> zzmN() {
        return new ArrayList<Scope>(this.zzWZ);
    }

    public boolean zzmO() {
        return this.zzXa;
    }

    public boolean zzmP() {
        return this.zzXb;
    }

    public boolean zzmQ() {
        return this.zzXc;
    }

    public String zzmR() {
        return this.zzXd;
    }

    public String zzmS() {
        return this.zzXe;
    }

    public static final class Builder {
        private Account zzTI;
        private boolean zzXa;
        private boolean zzXb;
        private boolean zzXc;
        private String zzXd;
        private String zzXe;
        private Set<Scope> zzXf = new HashSet<Scope>();

        public Builder() {
        }

        public Builder(@NonNull GoogleSignInOptions googleSignInOptions) {
            zzx.zzz(googleSignInOptions);
            this.zzXf = new HashSet<Scope>(googleSignInOptions.zzWZ);
            this.zzXb = googleSignInOptions.zzXb;
            this.zzXc = googleSignInOptions.zzXc;
            this.zzXa = googleSignInOptions.zzXa;
            this.zzXd = googleSignInOptions.zzXd;
            this.zzTI = googleSignInOptions.zzTI;
            this.zzXe = googleSignInOptions.zzXe;
        }

        /*
         * Enabled aggressive block sorting
         */
        private String zzbK(String string2) {
            zzx.zzcM(string2);
            boolean bl2 = this.zzXd == null || this.zzXd.equals(string2);
            zzx.zzb(bl2, (Object)"two different server client ids provided");
            return string2;
        }

        public GoogleSignInOptions build() {
            if (this.zzXa && (this.zzTI == null || !this.zzXf.isEmpty())) {
                this.requestId();
            }
            return new GoogleSignInOptions(this.zzXf, this.zzTI, this.zzXa, this.zzXb, this.zzXc, this.zzXd, this.zzXe);
        }

        public Builder requestEmail() {
            this.zzXf.add(zzWX);
            return this;
        }

        public Builder requestId() {
            this.zzXf.add(zzWY);
            return this;
        }

        public Builder requestIdToken(String string2) {
            this.zzXa = true;
            this.zzXd = this.zzbK(string2);
            return this;
        }

        public Builder requestProfile() {
            this.zzXf.add(zzWW);
            return this;
        }

        public Builder requestScopes(Scope scope, Scope ... scopeArray) {
            this.zzXf.add(scope);
            this.zzXf.addAll(Arrays.asList(scopeArray));
            return this;
        }

        public Builder requestServerAuthCode(String string2) {
            return this.requestServerAuthCode(string2, false);
        }

        public Builder requestServerAuthCode(String string2, boolean bl2) {
            this.zzXb = true;
            this.zzXd = this.zzbK(string2);
            this.zzXc = bl2;
            return this;
        }

        public Builder setAccountName(String string2) {
            this.zzTI = new Account(zzx.zzcM(string2), "com.google");
            return this;
        }

        public Builder setHostedDomain(String string2) {
            this.zzXe = zzx.zzcM(string2);
            return this;
        }
    }
}

