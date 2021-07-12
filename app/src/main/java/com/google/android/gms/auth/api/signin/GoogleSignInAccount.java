/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 *  android.os.Parcel
 *  android.os.Parcelable$Creator
 *  android.text.TextUtils
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.google.android.gms.auth.api.signin;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.google.android.gms.auth.api.signin.zzb;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzx;
import com.google.android.gms.internal.zzmq;
import com.google.android.gms.internal.zzmt;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GoogleSignInAccount
implements SafeParcelable {
    public static final Parcelable.Creator<GoogleSignInAccount> CREATOR = new zzb();
    public static zzmq zzWO = zzmt.zzsc();
    private static Comparator<Scope> zzWV = new Comparator<Scope>(){

        @Override
        public /* synthetic */ int compare(Object object, Object object2) {
            return this.zza((Scope)object, (Scope)object2);
        }

        public int zza(Scope scope, Scope scope2) {
            return scope.zzpb().compareTo(scope2.zzpb());
        }
    };
    final int versionCode;
    List<Scope> zzVs;
    private String zzWP;
    private String zzWQ;
    private Uri zzWR;
    private String zzWS;
    private long zzWT;
    private String zzWU;
    private String zzWk;
    private String zzyv;

    GoogleSignInAccount(int n2, String string2, String string3, String string4, String string5, Uri uri, String string6, long l2, String string7, List<Scope> list) {
        this.versionCode = n2;
        this.zzyv = string2;
        this.zzWk = string3;
        this.zzWP = string4;
        this.zzWQ = string5;
        this.zzWR = uri;
        this.zzWS = string6;
        this.zzWT = l2;
        this.zzWU = string7;
        this.zzVs = list;
    }

    public static GoogleSignInAccount zza(@Nullable String string2, @Nullable String string3, @Nullable String string4, @Nullable String string5, @Nullable Uri uri, @Nullable Long l2, @NonNull String string6, @NonNull Set<Scope> set) {
        Long l3 = l2;
        if (l2 == null) {
            l3 = zzWO.currentTimeMillis() / 1000L;
        }
        return new GoogleSignInAccount(2, string2, string3, string4, string5, uri, null, l3, zzx.zzcM(string6), new ArrayList<Scope>((Collection)zzx.zzz(set)));
    }

    /*
     * Enabled aggressive block sorting
     */
    @Nullable
    public static GoogleSignInAccount zzbH(@Nullable String string2) throws JSONException {
        if (TextUtils.isEmpty((CharSequence)string2)) {
            return null;
        }
        JSONObject jSONObject = new JSONObject(string2);
        string2 = !TextUtils.isEmpty((CharSequence)(string2 = jSONObject.optString("photoUrl", null))) ? Uri.parse((String)string2) : null;
        long l2 = Long.parseLong(jSONObject.getString("expirationTime"));
        HashSet<Scope> hashSet = new HashSet<Scope>();
        JSONArray jSONArray = jSONObject.getJSONArray("grantedScopes");
        int n2 = jSONArray.length();
        int n3 = 0;
        while (n3 < n2) {
            hashSet.add(new Scope(jSONArray.getString(n3)));
            ++n3;
        }
        return GoogleSignInAccount.zza(jSONObject.optString("id"), jSONObject.optString("tokenId", null), jSONObject.optString("email", null), jSONObject.optString("displayName", null), (Uri)string2, l2, jSONObject.getString("obfuscatedIdentifier"), hashSet).zzbI(jSONObject.optString("serverAuthCode", null));
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
            if (this.getId() != null) {
                jSONObject.put("id", (Object)this.getId());
            }
            if (this.getIdToken() != null) {
                jSONObject.put("tokenId", (Object)this.getIdToken());
            }
            if (this.getEmail() != null) {
                jSONObject.put("email", (Object)this.getEmail());
            }
            if (this.getDisplayName() != null) {
                jSONObject.put("displayName", (Object)this.getDisplayName());
            }
            if (this.getPhotoUrl() != null) {
                jSONObject.put("photoUrl", (Object)this.getPhotoUrl().toString());
            }
            if (this.getServerAuthCode() != null) {
                jSONObject.put("serverAuthCode", (Object)this.getServerAuthCode());
            }
            jSONObject.put("expirationTime", this.zzWT);
            jSONObject.put("obfuscatedIdentifier", (Object)this.zzmL());
            jSONArray = new JSONArray();
            Collections.sort(this.zzVs, zzWV);
            Iterator<Scope> iterator = this.zzVs.iterator();
            while (iterator.hasNext()) {
                jSONArray.put((Object)iterator.next().zzpb());
            }
        }
        catch (JSONException jSONException) {
            throw new RuntimeException(jSONException);
        }
        {
            jSONObject.put("grantedScopes", (Object)jSONArray);
            return jSONObject;
        }
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        if (!(object instanceof GoogleSignInAccount)) {
            return false;
        }
        return ((GoogleSignInAccount)object).zzmI().equals(this.zzmI());
    }

    @Nullable
    public String getDisplayName() {
        return this.zzWQ;
    }

    @Nullable
    public String getEmail() {
        return this.zzWP;
    }

    @NonNull
    public Set<Scope> getGrantedScopes() {
        return new HashSet<Scope>(this.zzVs);
    }

    @Nullable
    public String getId() {
        return this.zzyv;
    }

    @Nullable
    public String getIdToken() {
        return this.zzWk;
    }

    @Nullable
    public Uri getPhotoUrl() {
        return this.zzWR;
    }

    @Nullable
    public String getServerAuthCode() {
        return this.zzWS;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        zzb.zza(this, parcel, n2);
    }

    public boolean zzb() {
        return zzWO.currentTimeMillis() / 1000L >= this.zzWT - 300L;
    }

    public GoogleSignInAccount zzbI(String string2) {
        this.zzWS = string2;
        return this;
    }

    public String zzmI() {
        return this.zzmJ().toString();
    }

    public long zzmK() {
        return this.zzWT;
    }

    @NonNull
    public String zzmL() {
        return this.zzWU;
    }

    public String zzmM() {
        JSONObject jSONObject = this.zzmJ();
        jSONObject.remove("serverAuthCode");
        return jSONObject.toString();
    }
}

