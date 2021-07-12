/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 */
package com.google.android.gms.common.server.converter;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.server.converter.zzb;
import com.google.android.gms.common.server.converter.zzc;
import com.google.android.gms.common.server.response.FastJsonResponse;
import java.util.ArrayList;
import java.util.HashMap;

public final class StringToIntConverter
implements SafeParcelable,
FastJsonResponse.zza<String, Integer> {
    public static final zzb CREATOR = new zzb();
    private final int mVersionCode;
    private final HashMap<String, Integer> zzamG;
    private final HashMap<Integer, String> zzamH;
    private final ArrayList<Entry> zzamI;

    public StringToIntConverter() {
        this.mVersionCode = 1;
        this.zzamG = new HashMap();
        this.zzamH = new HashMap();
        this.zzamI = null;
    }

    StringToIntConverter(int n2, ArrayList<Entry> arrayList) {
        this.mVersionCode = n2;
        this.zzamG = new HashMap();
        this.zzamH = new HashMap();
        this.zzamI = null;
        this.zzd(arrayList);
    }

    private void zzd(ArrayList<Entry> object) {
        object = ((ArrayList)object).iterator();
        while (object.hasNext()) {
            Entry entry = (Entry)object.next();
            this.zzh(entry.zzamJ, entry.zzamK);
        }
    }

    @Override
    public /* synthetic */ Object convertBack(Object object) {
        return this.zzb((Integer)object);
    }

    public int describeContents() {
        zzb zzb2 = CREATOR;
        return 0;
    }

    int getVersionCode() {
        return this.mVersionCode;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        zzb zzb2 = CREATOR;
        zzb.zza(this, parcel, n2);
    }

    public String zzb(Integer object) {
        String string2 = this.zzamH.get(object);
        object = string2;
        if (string2 == null) {
            object = string2;
            if (this.zzamG.containsKey("gms_unknown")) {
                object = "gms_unknown";
            }
        }
        return object;
    }

    public StringToIntConverter zzh(String string2, int n2) {
        this.zzamG.put(string2, n2);
        this.zzamH.put(n2, string2);
        return this;
    }

    ArrayList<Entry> zzri() {
        ArrayList<Entry> arrayList = new ArrayList<Entry>();
        for (String string2 : this.zzamG.keySet()) {
            arrayList.add(new Entry(string2, this.zzamG.get(string2)));
        }
        return arrayList;
    }

    @Override
    public int zzrj() {
        return 7;
    }

    @Override
    public int zzrk() {
        return 0;
    }

    public static final class Entry
    implements SafeParcelable {
        public static final zzc CREATOR = new zzc();
        final int versionCode;
        final String zzamJ;
        final int zzamK;

        Entry(int n2, String string2, int n3) {
            this.versionCode = n2;
            this.zzamJ = string2;
            this.zzamK = n3;
        }

        Entry(String string2, int n2) {
            this.versionCode = 1;
            this.zzamJ = string2;
            this.zzamK = n2;
        }

        public int describeContents() {
            zzc zzc2 = CREATOR;
            return 0;
        }

        public void writeToParcel(Parcel parcel, int n2) {
            zzc zzc2 = CREATOR;
            zzc.zza(this, parcel, n2);
        }
    }
}

