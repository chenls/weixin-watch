/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 */
package com.google.android.gms.common.server.response;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzx;
import com.google.android.gms.common.server.response.FastJsonResponse;
import com.google.android.gms.common.server.response.zzb;
import com.google.android.gms.common.server.response.zzc;
import com.google.android.gms.common.server.response.zzd;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class FieldMappingDictionary
implements SafeParcelable {
    public static final zzc CREATOR = new zzc();
    private final int mVersionCode;
    private final HashMap<String, Map<String, FastJsonResponse.Field<?, ?>>> zzamV;
    private final ArrayList<Entry> zzamW;
    private final String zzamX;

    FieldMappingDictionary(int n2, ArrayList<Entry> arrayList, String string2) {
        this.mVersionCode = n2;
        this.zzamW = null;
        this.zzamV = FieldMappingDictionary.zze(arrayList);
        this.zzamX = zzx.zzz(string2);
        this.zzry();
    }

    public FieldMappingDictionary(Class<? extends FastJsonResponse> clazz) {
        this.mVersionCode = 1;
        this.zzamW = null;
        this.zzamV = new HashMap();
        this.zzamX = clazz.getCanonicalName();
    }

    private static HashMap<String, Map<String, FastJsonResponse.Field<?, ?>>> zze(ArrayList<Entry> arrayList) {
        HashMap hashMap = new HashMap();
        int n2 = arrayList.size();
        for (int i2 = 0; i2 < n2; ++i2) {
            Entry entry = arrayList.get(i2);
            hashMap.put(entry.className, entry.zzrC());
        }
        return hashMap;
    }

    public int describeContents() {
        zzc zzc2 = CREATOR;
        return 0;
    }

    int getVersionCode() {
        return this.mVersionCode;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String string2 : this.zzamV.keySet()) {
            stringBuilder.append(string2).append(":\n");
            Map<String, FastJsonResponse.Field<?, ?>> object = this.zzamV.get(string2);
            for (String string3 : object.keySet()) {
                stringBuilder.append("  ").append(string3).append(": ");
                stringBuilder.append(object.get(string3));
            }
        }
        return stringBuilder.toString();
    }

    public void writeToParcel(Parcel parcel, int n2) {
        zzc zzc2 = CREATOR;
        zzc.zza(this, parcel, n2);
    }

    public void zza(Class<? extends FastJsonResponse> clazz, Map<String, FastJsonResponse.Field<?, ?>> map) {
        this.zzamV.put(clazz.getCanonicalName(), map);
    }

    public boolean zzb(Class<? extends FastJsonResponse> clazz) {
        return this.zzamV.containsKey(clazz.getCanonicalName());
    }

    public Map<String, FastJsonResponse.Field<?, ?>> zzcR(String string2) {
        return this.zzamV.get(string2);
    }

    ArrayList<Entry> zzrA() {
        ArrayList<Entry> arrayList = new ArrayList<Entry>();
        for (String string2 : this.zzamV.keySet()) {
            arrayList.add(new Entry(string2, this.zzamV.get(string2)));
        }
        return arrayList;
    }

    public String zzrB() {
        return this.zzamX;
    }

    public void zzry() {
        for (String string2 : this.zzamV.keySet()) {
            Map<String, FastJsonResponse.Field<?, ?>> object = this.zzamV.get(string2);
            Iterator<String> iterator = object.keySet().iterator();
            while (iterator.hasNext()) {
                object.get(iterator.next()).zza(this);
            }
        }
    }

    public void zzrz() {
        for (String string2 : this.zzamV.keySet()) {
            Map<String, FastJsonResponse.Field<?, ?>> map = this.zzamV.get(string2);
            HashMap hashMap = new HashMap();
            for (String string3 : map.keySet()) {
                hashMap.put(string3, map.get(string3).zzro());
            }
            this.zzamV.put(string2, hashMap);
        }
    }

    public static class Entry
    implements SafeParcelable {
        public static final zzd CREATOR = new zzd();
        final String className;
        final int versionCode;
        final ArrayList<FieldMapPair> zzamY;

        Entry(int n2, String string2, ArrayList<FieldMapPair> arrayList) {
            this.versionCode = n2;
            this.className = string2;
            this.zzamY = arrayList;
        }

        Entry(String string2, Map<String, FastJsonResponse.Field<?, ?>> map) {
            this.versionCode = 1;
            this.className = string2;
            this.zzamY = Entry.zzM(map);
        }

        private static ArrayList<FieldMapPair> zzM(Map<String, FastJsonResponse.Field<?, ?>> map) {
            if (map == null) {
                return null;
            }
            ArrayList<FieldMapPair> arrayList = new ArrayList<FieldMapPair>();
            for (String string2 : map.keySet()) {
                arrayList.add(new FieldMapPair(string2, map.get(string2)));
            }
            return arrayList;
        }

        public int describeContents() {
            zzd zzd2 = CREATOR;
            return 0;
        }

        public void writeToParcel(Parcel parcel, int n2) {
            zzd zzd2 = CREATOR;
            zzd.zza(this, parcel, n2);
        }

        HashMap<String, FastJsonResponse.Field<?, ?>> zzrC() {
            HashMap hashMap = new HashMap();
            int n2 = this.zzamY.size();
            for (int i2 = 0; i2 < n2; ++i2) {
                FieldMapPair fieldMapPair = this.zzamY.get(i2);
                hashMap.put(fieldMapPair.key, fieldMapPair.zzamZ);
            }
            return hashMap;
        }
    }

    public static class FieldMapPair
    implements SafeParcelable {
        public static final zzb CREATOR = new zzb();
        final String key;
        final int versionCode;
        final FastJsonResponse.Field<?, ?> zzamZ;

        FieldMapPair(int n2, String string2, FastJsonResponse.Field<?, ?> field) {
            this.versionCode = n2;
            this.key = string2;
            this.zzamZ = field;
        }

        FieldMapPair(String string2, FastJsonResponse.Field<?, ?> field) {
            this.versionCode = 1;
            this.key = string2;
            this.zzamZ = field;
        }

        public int describeContents() {
            zzb zzb2 = CREATOR;
            return 0;
        }

        public void writeToParcel(Parcel parcel, int n2) {
            zzb zzb2 = CREATOR;
            zzb.zza(this, parcel, n2);
        }
    }
}

