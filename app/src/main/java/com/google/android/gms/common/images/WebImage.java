/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 *  android.os.Parcel
 *  android.os.Parcelable$Creator
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.google.android.gms.common.images;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.images.zzb;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzw;
import org.json.JSONException;
import org.json.JSONObject;

public final class WebImage
implements SafeParcelable {
    public static final Parcelable.Creator<WebImage> CREATOR = new zzb();
    private final int mVersionCode;
    private final Uri zzajZ;
    private final int zzoG;
    private final int zzoH;

    WebImage(int n2, Uri uri, int n3, int n4) {
        this.mVersionCode = n2;
        this.zzajZ = uri;
        this.zzoG = n3;
        this.zzoH = n4;
    }

    public WebImage(Uri uri) throws IllegalArgumentException {
        this(uri, 0, 0);
    }

    public WebImage(Uri uri, int n2, int n3) throws IllegalArgumentException {
        this(1, uri, n2, n3);
        if (uri == null) {
            throw new IllegalArgumentException("url cannot be null");
        }
        if (n2 < 0 || n3 < 0) {
            throw new IllegalArgumentException("width and height must not be negative");
        }
    }

    public WebImage(JSONObject jSONObject) throws IllegalArgumentException {
        this(WebImage.zzj(jSONObject), jSONObject.optInt("width", 0), jSONObject.optInt("height", 0));
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static Uri zzj(JSONObject jSONObject) {
        Uri uri = null;
        if (!jSONObject.has("url")) return uri;
        try {
            return Uri.parse((String)jSONObject.getString("url"));
        }
        catch (JSONException jSONException) {
            return null;
        }
    }

    public int describeContents() {
        return 0;
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean equals(Object object) {
        block5: {
            block4: {
                if (this == object) break block4;
                if (object == null || !(object instanceof WebImage)) {
                    return false;
                }
                object = (WebImage)object;
                if (!zzw.equal(this.zzajZ, ((WebImage)object).zzajZ) || this.zzoG != ((WebImage)object).zzoG || this.zzoH != ((WebImage)object).zzoH) break block5;
            }
            return true;
        }
        return false;
    }

    public int getHeight() {
        return this.zzoH;
    }

    public Uri getUrl() {
        return this.zzajZ;
    }

    int getVersionCode() {
        return this.mVersionCode;
    }

    public int getWidth() {
        return this.zzoG;
    }

    public int hashCode() {
        return zzw.hashCode(this.zzajZ, this.zzoG, this.zzoH);
    }

    public JSONObject toJson() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("url", (Object)this.zzajZ.toString());
            jSONObject.put("width", this.zzoG);
            jSONObject.put("height", this.zzoH);
            return jSONObject;
        }
        catch (JSONException jSONException) {
            return jSONObject;
        }
    }

    public String toString() {
        return String.format("Image %dx%d %s", this.zzoG, this.zzoH, this.zzajZ.toString());
    }

    public void writeToParcel(Parcel parcel, int n2) {
        zzb.zza(this, parcel, n2);
    }
}

