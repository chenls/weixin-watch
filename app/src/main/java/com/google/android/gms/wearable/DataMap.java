/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.os.Parcelable
 *  android.text.TextUtils
 *  android.util.Log
 */
package com.google.android.gms.wearable;

import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.internal.zzsi;
import com.google.android.gms.internal.zzsj;
import com.google.android.gms.internal.zzst;
import com.google.android.gms.internal.zzsu;
import com.google.android.gms.wearable.Asset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class DataMap {
    public static final String TAG = "DataMap";
    private final HashMap<String, Object> zzbrc = new HashMap();

    public static ArrayList<DataMap> arrayListFromBundleArrayList(ArrayList<Bundle> object) {
        ArrayList<DataMap> arrayList = new ArrayList<DataMap>();
        object = ((ArrayList)object).iterator();
        while (object.hasNext()) {
            arrayList.add(DataMap.fromBundle((Bundle)object.next()));
        }
        return arrayList;
    }

    public static DataMap fromBundle(Bundle bundle) {
        bundle.setClassLoader(Asset.class.getClassLoader());
        DataMap dataMap = new DataMap();
        for (String string2 : bundle.keySet()) {
            DataMap.zza(dataMap, string2, bundle.get(string2));
        }
        return dataMap;
    }

    public static DataMap fromByteArray(byte[] object) {
        try {
            object = zzsi.zza(new zzsi.zza(zzsj.zzA(object), new ArrayList<Asset>()));
            return object;
        }
        catch (zzst zzst2) {
            throw new IllegalArgumentException("Unable to convert data", zzst2);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private static void zza(DataMap dataMap, String string2, Object object) {
        if (object instanceof String) {
            dataMap.putString(string2, (String)object);
            return;
        }
        if (object instanceof Integer) {
            dataMap.putInt(string2, (Integer)object);
            return;
        }
        if (object instanceof Long) {
            dataMap.putLong(string2, (Long)object);
            return;
        }
        if (object instanceof Double) {
            dataMap.putDouble(string2, (Double)object);
            return;
        }
        if (object instanceof Float) {
            dataMap.putFloat(string2, ((Float)object).floatValue());
            return;
        }
        if (object instanceof Boolean) {
            dataMap.putBoolean(string2, (Boolean)object);
            return;
        }
        if (object instanceof Byte) {
            dataMap.putByte(string2, (Byte)object);
            return;
        }
        if (object instanceof byte[]) {
            dataMap.putByteArray(string2, (byte[])object);
            return;
        }
        if (object instanceof String[]) {
            dataMap.putStringArray(string2, (String[])object);
            return;
        }
        if (object instanceof long[]) {
            dataMap.putLongArray(string2, (long[])object);
            return;
        }
        if (object instanceof float[]) {
            dataMap.putFloatArray(string2, (float[])object);
            return;
        }
        if (object instanceof Asset) {
            dataMap.putAsset(string2, (Asset)object);
            return;
        }
        if (object instanceof Bundle) {
            dataMap.putDataMap(string2, DataMap.fromBundle((Bundle)object));
            return;
        }
        if (!(object instanceof ArrayList)) return;
        switch (DataMap.zzf((ArrayList)object)) {
            default: {
                return;
            }
            case 0: {
                dataMap.putStringArrayList(string2, (ArrayList)object);
                return;
            }
            case 1: {
                dataMap.putStringArrayList(string2, (ArrayList)object);
                return;
            }
            case 3: {
                dataMap.putStringArrayList(string2, (ArrayList)object);
                return;
            }
            case 2: {
                dataMap.putIntegerArrayList(string2, (ArrayList)object);
                return;
            }
            case 5: 
        }
        dataMap.putDataMapArrayList(string2, DataMap.arrayListFromBundleArrayList((ArrayList)object));
    }

    private void zza(String string2, Object object, String string3, ClassCastException classCastException) {
        this.zza(string2, object, string3, "<null>", classCastException);
    }

    private void zza(String string2, Object object, String string3, Object object2, ClassCastException classCastException) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Key ");
        stringBuilder.append(string2);
        stringBuilder.append(" expected ");
        stringBuilder.append(string3);
        stringBuilder.append(" but value was a ");
        stringBuilder.append(object.getClass().getName());
        stringBuilder.append(".  The default value ");
        stringBuilder.append(object2);
        stringBuilder.append(" was returned.");
        Log.w((String)TAG, (String)stringBuilder.toString());
        Log.w((String)TAG, (String)"Attempt to cast generated internal exception:", (Throwable)classCastException);
    }

    private static boolean zza(Asset asset, Asset asset2) {
        if (asset == null || asset2 == null) {
            return asset == asset2;
        }
        if (!TextUtils.isEmpty((CharSequence)asset.getDigest())) {
            return asset.getDigest().equals(asset2.getDigest());
        }
        return Arrays.equals(asset.getData(), asset2.getData());
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static boolean zza(DataMap dataMap, DataMap dataMap2) {
        if (dataMap.size() != dataMap2.size()) {
            return false;
        }
        Iterator<String> iterator = dataMap.keySet().iterator();
        while (iterator.hasNext()) {
            String string2 = iterator.next();
            Object t2 = dataMap.get(string2);
            Object t3 = dataMap2.get(string2);
            if (t2 instanceof Asset) {
                if (!(t3 instanceof Asset)) return false;
                if (DataMap.zza((Asset)t2, (Asset)t3)) continue;
                return false;
            }
            if (t2 instanceof String[]) {
                if (!(t3 instanceof String[])) return false;
                if (Arrays.equals((String[])t2, (String[])t3)) continue;
                return false;
            }
            if (t2 instanceof long[]) {
                if (!(t3 instanceof long[])) return false;
                if (Arrays.equals((long[])t2, (long[])t3)) continue;
                return false;
            }
            if (t2 instanceof float[]) {
                if (!(t3 instanceof float[])) return false;
                if (Arrays.equals((float[])t2, (float[])t3)) continue;
                return false;
            }
            if (t2 instanceof byte[]) {
                if (!(t3 instanceof byte[])) return false;
                if (Arrays.equals((byte[])t2, (byte[])t3)) continue;
                return false;
            }
            if (t2 == null || t3 == null) {
                if (t2 != t3) return false;
                return true;
            }
            if (!t2.equals(t3)) return false;
        }
        return true;
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private static void zzb(Bundle bundle, String string2, Object iterator) {
        if (iterator instanceof String) {
            bundle.putString(string2, (String)((Object)iterator));
            return;
        }
        if (iterator instanceof Integer) {
            bundle.putInt(string2, ((Integer)((Object)iterator)).intValue());
            return;
        }
        if (iterator instanceof Long) {
            bundle.putLong(string2, ((Long)((Object)iterator)).longValue());
            return;
        }
        if (iterator instanceof Double) {
            bundle.putDouble(string2, ((Double)((Object)iterator)).doubleValue());
            return;
        }
        if (iterator instanceof Float) {
            bundle.putFloat(string2, ((Float)((Object)iterator)).floatValue());
            return;
        }
        if (iterator instanceof Boolean) {
            bundle.putBoolean(string2, ((Boolean)((Object)iterator)).booleanValue());
            return;
        }
        if (iterator instanceof Byte) {
            bundle.putByte(string2, ((Byte)((Object)iterator)).byteValue());
            return;
        }
        if (iterator instanceof byte[]) {
            bundle.putByteArray(string2, (byte[])iterator);
            return;
        }
        if (iterator instanceof String[]) {
            bundle.putStringArray(string2, (String[])iterator);
            return;
        }
        if (iterator instanceof long[]) {
            bundle.putLongArray(string2, (long[])iterator);
            return;
        }
        if (iterator instanceof float[]) {
            bundle.putFloatArray(string2, (float[])iterator);
            return;
        }
        if (iterator instanceof Asset) {
            bundle.putParcelable(string2, (Parcelable)((Asset)((Object)iterator)));
            return;
        }
        if (iterator instanceof DataMap) {
            bundle.putParcelable(string2, (Parcelable)((DataMap)((Object)iterator)).toBundle());
            return;
        }
        if (!(iterator instanceof ArrayList)) return;
        switch (DataMap.zzf((ArrayList)((Object)iterator))) {
            default: {
                return;
            }
            case 0: {
                bundle.putStringArrayList(string2, (ArrayList)((Object)iterator));
                return;
            }
            case 1: {
                bundle.putStringArrayList(string2, (ArrayList)((Object)iterator));
                return;
            }
            case 3: {
                bundle.putStringArrayList(string2, (ArrayList)((Object)iterator));
                return;
            }
            case 2: {
                bundle.putIntegerArrayList(string2, (ArrayList)((Object)iterator));
                return;
            }
            case 4: 
        }
        ArrayList<Bundle> arrayList = new ArrayList<Bundle>();
        iterator = ((ArrayList)((Object)iterator)).iterator();
        while (true) {
            if (!iterator.hasNext()) {
                bundle.putParcelableArrayList(string2, arrayList);
                return;
            }
            arrayList.add(((DataMap)iterator.next()).toBundle());
        }
    }

    private static int zzf(ArrayList<?> object) {
        if (((ArrayList)object).isEmpty()) {
            return 0;
        }
        object = ((ArrayList)object).iterator();
        while (object.hasNext()) {
            Object e2 = object.next();
            if (e2 == null) continue;
            if (e2 instanceof Integer) {
                return 2;
            }
            if (e2 instanceof String) {
                return 3;
            }
            if (e2 instanceof DataMap) {
                return 4;
            }
            if (!(e2 instanceof Bundle)) continue;
            return 5;
        }
        return 1;
    }

    public void clear() {
        this.zzbrc.clear();
    }

    public boolean containsKey(String string2) {
        return this.zzbrc.containsKey(string2);
    }

    public boolean equals(Object object) {
        if (!(object instanceof DataMap)) {
            return false;
        }
        return DataMap.zza(this, (DataMap)object);
    }

    public <T> T get(String string2) {
        return (T)this.zzbrc.get(string2);
    }

    public Asset getAsset(String string2) {
        Object object = this.zzbrc.get(string2);
        if (object == null) {
            return null;
        }
        try {
            Asset asset = (Asset)object;
            return asset;
        }
        catch (ClassCastException classCastException) {
            this.zza(string2, object, "Asset", classCastException);
            return null;
        }
    }

    public boolean getBoolean(String string2) {
        return this.getBoolean(string2, false);
    }

    public boolean getBoolean(String string2, boolean bl2) {
        Object object = this.zzbrc.get(string2);
        if (object == null) {
            return bl2;
        }
        try {
            boolean bl3 = (Boolean)object;
            return bl3;
        }
        catch (ClassCastException classCastException) {
            this.zza(string2, object, "Boolean", bl2, classCastException);
            return bl2;
        }
    }

    public byte getByte(String string2) {
        return this.getByte(string2, (byte)0);
    }

    public byte getByte(String string2, byte by2) {
        Object object = this.zzbrc.get(string2);
        if (object == null) {
            return by2;
        }
        try {
            byte by3 = (Byte)object;
            return by3;
        }
        catch (ClassCastException classCastException) {
            this.zza(string2, object, "Byte", by2, classCastException);
            return by2;
        }
    }

    public byte[] getByteArray(String string2) {
        Object object = this.zzbrc.get(string2);
        if (object == null) {
            return null;
        }
        try {
            byte[] byArray = (byte[])object;
            return byArray;
        }
        catch (ClassCastException classCastException) {
            this.zza(string2, object, "byte[]", classCastException);
            return null;
        }
    }

    public DataMap getDataMap(String string2) {
        Object object = this.zzbrc.get(string2);
        if (object == null) {
            return null;
        }
        try {
            DataMap dataMap = (DataMap)object;
            return dataMap;
        }
        catch (ClassCastException classCastException) {
            this.zza(string2, object, TAG, classCastException);
            return null;
        }
    }

    public ArrayList<DataMap> getDataMapArrayList(String string2) {
        Object object = this.zzbrc.get(string2);
        if (object == null) {
            return null;
        }
        try {
            ArrayList arrayList = (ArrayList)object;
            return arrayList;
        }
        catch (ClassCastException classCastException) {
            this.zza(string2, object, "ArrayList<DataMap>", classCastException);
            return null;
        }
    }

    public double getDouble(String string2) {
        return this.getDouble(string2, 0.0);
    }

    public double getDouble(String string2, double d2) {
        Object object = this.zzbrc.get(string2);
        if (object == null) {
            return d2;
        }
        try {
            double d3 = (Double)object;
            return d3;
        }
        catch (ClassCastException classCastException) {
            this.zza(string2, object, "Double", d2, classCastException);
            return d2;
        }
    }

    public float getFloat(String string2) {
        return this.getFloat(string2, 0.0f);
    }

    public float getFloat(String string2, float f2) {
        Object object = this.zzbrc.get(string2);
        if (object == null) {
            return f2;
        }
        try {
            float f3 = ((Float)object).floatValue();
            return f3;
        }
        catch (ClassCastException classCastException) {
            this.zza(string2, object, "Float", Float.valueOf(f2), classCastException);
            return f2;
        }
    }

    public float[] getFloatArray(String string2) {
        Object object = this.zzbrc.get(string2);
        if (object == null) {
            return null;
        }
        try {
            float[] fArray = (float[])object;
            return fArray;
        }
        catch (ClassCastException classCastException) {
            this.zza(string2, object, "float[]", classCastException);
            return null;
        }
    }

    public int getInt(String string2) {
        return this.getInt(string2, 0);
    }

    public int getInt(String string2, int n2) {
        Object object = this.zzbrc.get(string2);
        if (object == null) {
            return n2;
        }
        try {
            int n3 = (Integer)object;
            return n3;
        }
        catch (ClassCastException classCastException) {
            this.zza(string2, object, "Integer", classCastException);
            return n2;
        }
    }

    public ArrayList<Integer> getIntegerArrayList(String string2) {
        Object object = this.zzbrc.get(string2);
        if (object == null) {
            return null;
        }
        try {
            ArrayList arrayList = (ArrayList)object;
            return arrayList;
        }
        catch (ClassCastException classCastException) {
            this.zza(string2, object, "ArrayList<Integer>", classCastException);
            return null;
        }
    }

    public long getLong(String string2) {
        return this.getLong(string2, 0L);
    }

    public long getLong(String string2, long l2) {
        Object object = this.zzbrc.get(string2);
        if (object == null) {
            return l2;
        }
        try {
            long l3 = (Long)object;
            return l3;
        }
        catch (ClassCastException classCastException) {
            this.zza(string2, object, "long", classCastException);
            return l2;
        }
    }

    public long[] getLongArray(String string2) {
        Object object = this.zzbrc.get(string2);
        if (object == null) {
            return null;
        }
        try {
            long[] lArray = (long[])object;
            return lArray;
        }
        catch (ClassCastException classCastException) {
            this.zza(string2, object, "long[]", classCastException);
            return null;
        }
    }

    public String getString(String string2) {
        Object object = this.zzbrc.get(string2);
        if (object == null) {
            return null;
        }
        try {
            String string3 = (String)object;
            return string3;
        }
        catch (ClassCastException classCastException) {
            this.zza(string2, object, "String", classCastException);
            return null;
        }
    }

    public String getString(String string2, String string3) {
        if ((string2 = this.getString(string2)) == null) {
            return string3;
        }
        return string2;
    }

    public String[] getStringArray(String string2) {
        Object object = this.zzbrc.get(string2);
        if (object == null) {
            return null;
        }
        try {
            String[] stringArray = (String[])object;
            return stringArray;
        }
        catch (ClassCastException classCastException) {
            this.zza(string2, object, "String[]", classCastException);
            return null;
        }
    }

    public ArrayList<String> getStringArrayList(String string2) {
        Object object = this.zzbrc.get(string2);
        if (object == null) {
            return null;
        }
        try {
            ArrayList arrayList = (ArrayList)object;
            return arrayList;
        }
        catch (ClassCastException classCastException) {
            this.zza(string2, object, "ArrayList<String>", classCastException);
            return null;
        }
    }

    public int hashCode() {
        return this.zzbrc.hashCode() * 29;
    }

    public boolean isEmpty() {
        return this.zzbrc.isEmpty();
    }

    public Set<String> keySet() {
        return this.zzbrc.keySet();
    }

    public void putAll(DataMap dataMap) {
        for (String string2 : dataMap.keySet()) {
            this.zzbrc.put(string2, dataMap.get(string2));
        }
    }

    public void putAsset(String string2, Asset asset) {
        this.zzbrc.put(string2, asset);
    }

    public void putBoolean(String string2, boolean bl2) {
        this.zzbrc.put(string2, bl2);
    }

    public void putByte(String string2, byte by2) {
        this.zzbrc.put(string2, by2);
    }

    public void putByteArray(String string2, byte[] byArray) {
        this.zzbrc.put(string2, byArray);
    }

    public void putDataMap(String string2, DataMap dataMap) {
        this.zzbrc.put(string2, dataMap);
    }

    public void putDataMapArrayList(String string2, ArrayList<DataMap> arrayList) {
        this.zzbrc.put(string2, arrayList);
    }

    public void putDouble(String string2, double d2) {
        this.zzbrc.put(string2, d2);
    }

    public void putFloat(String string2, float f2) {
        this.zzbrc.put(string2, Float.valueOf(f2));
    }

    public void putFloatArray(String string2, float[] fArray) {
        this.zzbrc.put(string2, fArray);
    }

    public void putInt(String string2, int n2) {
        this.zzbrc.put(string2, n2);
    }

    public void putIntegerArrayList(String string2, ArrayList<Integer> arrayList) {
        this.zzbrc.put(string2, arrayList);
    }

    public void putLong(String string2, long l2) {
        this.zzbrc.put(string2, l2);
    }

    public void putLongArray(String string2, long[] lArray) {
        this.zzbrc.put(string2, lArray);
    }

    public void putString(String string2, String string3) {
        this.zzbrc.put(string2, string3);
    }

    public void putStringArray(String string2, String[] stringArray) {
        this.zzbrc.put(string2, stringArray);
    }

    public void putStringArrayList(String string2, ArrayList<String> arrayList) {
        this.zzbrc.put(string2, arrayList);
    }

    public Object remove(String string2) {
        return this.zzbrc.remove(string2);
    }

    public int size() {
        return this.zzbrc.size();
    }

    public Bundle toBundle() {
        Bundle bundle = new Bundle();
        for (String string2 : this.zzbrc.keySet()) {
            DataMap.zzb(bundle, string2, this.zzbrc.get(string2));
        }
        return bundle;
    }

    public byte[] toByteArray() {
        return zzsu.toByteArray(zzsi.zza((DataMap)this).zzbty);
    }

    public String toString() {
        return this.zzbrc.toString();
    }
}

