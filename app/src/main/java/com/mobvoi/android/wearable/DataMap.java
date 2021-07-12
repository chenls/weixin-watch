/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.os.Parcelable
 *  android.text.TextUtils
 *  android.util.Log
 */
package com.mobvoi.android.wearable;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import com.mobvoi.android.wearable.Asset;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import mobvoiapi.bp;

public class DataMap {
    public static final String INDEX_SEPARATOR = "_#_";
    public static final String PATH_SEPARATOR = "_@_";
    public static final String TAG = "DataMap";
    private final Map<String, Object> a = new HashMap<String, Object>();

    public DataMap() {
    }

    /*
     * Enabled aggressive block sorting
     */
    private DataMap(Bundle bundle) {
        if (bundle != null) {
            for (String string2 : bundle.keySet()) {
                Iterator iterator = bundle.get(string2);
                if (iterator instanceof Bundle) {
                    this.a.put(string2, DataMap.fromBundle((Bundle)iterator));
                    continue;
                }
                if (iterator instanceof ArrayList && !((ArrayList)((Object)iterator)).isEmpty() && ((ArrayList)((Object)iterator)).get(0) instanceof Bundle) {
                    ArrayList<DataMap> arrayList = new ArrayList<DataMap>();
                    iterator = ((ArrayList)((Object)iterator)).iterator();
                    while (iterator.hasNext()) {
                        arrayList.add(DataMap.fromBundle((Bundle)iterator.next()));
                    }
                    this.a.put(string2, arrayList);
                    continue;
                }
                this.a.put(string2, iterator);
            }
        }
    }

    private void a(String string2, Object object, String string3, ClassCastException classCastException) {
        this.a(string2, object, string3, "[null]", classCastException);
    }

    private void a(String string2, Object object, String string3, Object object2, ClassCastException classCastException) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Expected key ");
        stringBuffer.append(string2);
        stringBuffer.append(" with value ");
        stringBuffer.append(string3);
        stringBuffer.append(" but got the actual value ");
        stringBuffer.append(object.getClass().getName());
        stringBuffer.append(".  So return the default ");
        stringBuffer.append(object2);
        stringBuffer.append(" .");
        Log.w((String)TAG, (String)stringBuffer.toString());
        Log.w((String)TAG, (String)"Internal exception: ", (Throwable)classCastException);
    }

    private static boolean a(Asset asset, Asset asset2) {
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
    private static boolean a(DataMap dataMap, DataMap dataMap2) {
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
                if (DataMap.a((Asset)t2, (Asset)t3)) continue;
                return false;
            }
            if (t2 instanceof Bundle) {
                if (!(t3 instanceof Bundle)) return false;
                return ((Bundle)t2).equals(t3);
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
        return new DataMap(bundle);
    }

    @Nullable
    public static DataMap fromByteArray(@Nullable byte[] object) {
        if (object == null || ((byte[])object).length == 0) {
            return null;
        }
        try {
            object = new ByteArrayInputStream((byte[])object);
            DataInputStream dataInputStream = new DataInputStream((InputStream)object);
            DataMap dataMap = new DataMap();
            dataMap.readFields(dataInputStream);
            ((ByteArrayInputStream)object).close();
            return dataMap;
        }
        catch (Exception exception) {
            bp.b(TAG, "deserialize dataMap failed.", exception);
            return null;
        }
    }

    public void clear() {
        this.a.clear();
    }

    public boolean containsKey(String string2) {
        return this.a.containsKey(string2);
    }

    public boolean equals(Object object) {
        if (!(object instanceof DataMap)) {
            return false;
        }
        return DataMap.a(this, (DataMap)object);
    }

    public <T> T get(String string2) {
        return (T)this.a.get(string2);
    }

    public Asset getAsset(String string2) {
        Object object = this.a.get(string2);
        if (object == null) {
            return null;
        }
        try {
            Asset asset = (Asset)object;
            return asset;
        }
        catch (ClassCastException classCastException) {
            this.a(string2, object, "Asset", classCastException);
            return null;
        }
    }

    public boolean getBoolean(String string2) {
        return this.getBoolean(string2, false);
    }

    public boolean getBoolean(String string2, boolean bl2) {
        Object object = this.a.get(string2);
        if (object == null) {
            return bl2;
        }
        try {
            boolean bl3 = (Boolean)object;
            return bl3;
        }
        catch (ClassCastException classCastException) {
            this.a(string2, object, "Boolean", bl2, classCastException);
            return bl2;
        }
    }

    public byte getByte(String string2) {
        return this.getByte(string2, (byte)0);
    }

    public byte getByte(String string2, byte by2) {
        Object object = this.a.get(string2);
        if (object == null) {
            return by2;
        }
        try {
            byte by3 = (Byte)object;
            return by3;
        }
        catch (ClassCastException classCastException) {
            this.a(string2, object, "Byte", by2, classCastException);
            return by2;
        }
    }

    public byte[] getByteArray(String string2) {
        Object object = this.a.get(string2);
        if (object == null) {
            return null;
        }
        try {
            byte[] byArray = (byte[])object;
            return byArray;
        }
        catch (ClassCastException classCastException) {
            this.a(string2, object, "byte[]", classCastException);
            return null;
        }
    }

    public DataMap getDataMap(String string2) {
        Object object = this.a.get(string2);
        if (object == null) {
            return null;
        }
        try {
            DataMap dataMap = (DataMap)object;
            return dataMap;
        }
        catch (ClassCastException classCastException) {
            this.a(string2, object, TAG, classCastException);
            return null;
        }
    }

    public ArrayList<DataMap> getDataMapArrayList(String string2) {
        Object object = this.a.get(string2);
        if (object == null) {
            return null;
        }
        try {
            ArrayList arrayList = (ArrayList)object;
            return arrayList;
        }
        catch (ClassCastException classCastException) {
            this.a(string2, object, "ArrayList<DataMap>", classCastException);
            return null;
        }
    }

    public double getDouble(String string2) {
        return this.getDouble(string2, 0.0);
    }

    public double getDouble(String string2, double d2) {
        Object object = this.a.get(string2);
        if (object == null) {
            return d2;
        }
        try {
            double d3 = (Double)object;
            return d3;
        }
        catch (ClassCastException classCastException) {
            this.a(string2, object, "Double", d2, classCastException);
            return d2;
        }
    }

    public float getFloat(String string2) {
        return this.getFloat(string2, 0.0f);
    }

    public float getFloat(String string2, float f2) {
        Object object = this.a.get(string2);
        if (object == null) {
            return f2;
        }
        try {
            float f3 = ((Float)object).floatValue();
            return f3;
        }
        catch (ClassCastException classCastException) {
            this.a(string2, object, "Float", Float.valueOf(f2), classCastException);
            return f2;
        }
    }

    public float[] getFloatArray(String string2) {
        Object object = this.a.get(string2);
        if (object == null) {
            return null;
        }
        try {
            float[] fArray = (float[])object;
            return fArray;
        }
        catch (ClassCastException classCastException) {
            this.a(string2, object, "float[]", classCastException);
            return null;
        }
    }

    public int getInt(String string2) {
        return this.getInt(string2, 0);
    }

    public int getInt(String string2, int n2) {
        Object object = this.a.get(string2);
        if (object == null) {
            return n2;
        }
        try {
            int n3 = (Integer)object;
            return n3;
        }
        catch (ClassCastException classCastException) {
            this.a(string2, object, "Integer", n2, classCastException);
            return n2;
        }
    }

    public ArrayList<Integer> getIntegerArrayList(String string2) {
        Object object = this.a.get(string2);
        if (object == null) {
            return null;
        }
        try {
            ArrayList arrayList = (ArrayList)object;
            return arrayList;
        }
        catch (ClassCastException classCastException) {
            this.a(string2, object, "ArrayList<Integer>", classCastException);
            return null;
        }
    }

    public long getLong(String string2) {
        return this.getLong(string2, 0L);
    }

    public long getLong(String string2, long l2) {
        Object object = this.a.get(string2);
        if (object == null) {
            return l2;
        }
        try {
            long l3 = (Long)object;
            return l3;
        }
        catch (ClassCastException classCastException) {
            this.a(string2, object, "Long", l2, classCastException);
            return l2;
        }
    }

    public long[] getLongArray(String string2) {
        Object object = this.a.get(string2);
        if (object == null) {
            return null;
        }
        try {
            long[] lArray = (long[])object;
            return lArray;
        }
        catch (ClassCastException classCastException) {
            this.a(string2, object, "long[]", classCastException);
            return null;
        }
    }

    public String getString(String string2) {
        return this.getString(string2, null);
    }

    public String getString(String string2, String string3) {
        Object object = this.a.get(string2);
        if (object == null) {
            return string3;
        }
        try {
            String string4 = (String)object;
            return string4;
        }
        catch (ClassCastException classCastException) {
            this.a(string2, object, "String", string3, classCastException);
            return string3;
        }
    }

    public String[] getStringArray(String string2) {
        Object object = this.a.get(string2);
        if (object == null) {
            return null;
        }
        try {
            String[] stringArray = (String[])object;
            return stringArray;
        }
        catch (ClassCastException classCastException) {
            this.a(string2, object, "String[]", classCastException);
            return null;
        }
    }

    public ArrayList<String> getStringArrayList(String string2) {
        Object object = this.a.get(string2);
        if (object == null) {
            return null;
        }
        try {
            ArrayList arrayList = (ArrayList)object;
            return arrayList;
        }
        catch (ClassCastException classCastException) {
            this.a(string2, object, "ArrayList<String>", classCastException);
            return null;
        }
    }

    public int hashCode() {
        return this.a.hashCode() * 29;
    }

    public boolean isEmpty() {
        return this.a.isEmpty();
    }

    public Set<String> keySet() {
        return this.a.keySet();
    }

    public void putAll(DataMap dataMap) {
        for (String string2 : dataMap.keySet()) {
            this.a.put(string2, dataMap.get(string2));
        }
    }

    public void putAsset(String string2, Asset asset) {
        this.a.put(string2, asset);
    }

    public void putBoolean(String string2, boolean bl2) {
        this.a.put(string2, bl2);
    }

    public void putByte(String string2, byte by2) {
        this.a.put(string2, by2);
    }

    public void putByteArray(String string2, byte[] byArray) {
        this.a.put(string2, byArray);
    }

    public void putDataMap(String string2, DataMap dataMap) {
        this.a.put(string2, dataMap);
    }

    public void putDataMapArrayList(String string2, ArrayList<DataMap> arrayList) {
        this.a.put(string2, arrayList);
    }

    public void putDouble(String string2, double d2) {
        this.a.put(string2, d2);
    }

    public void putFloat(String string2, float f2) {
        this.a.put(string2, Float.valueOf(f2));
    }

    public void putFloatArray(String string2, float[] fArray) {
        this.a.put(string2, fArray);
    }

    public void putInt(String string2, int n2) {
        this.a.put(string2, n2);
    }

    public void putIntegerArrayList(String string2, ArrayList<Integer> arrayList) {
        this.a.put(string2, arrayList);
    }

    public void putLong(String string2, long l2) {
        this.a.put(string2, l2);
    }

    public void putLongArray(String string2, long[] lArray) {
        this.a.put(string2, lArray);
    }

    public void putString(String string2, String string3) {
        this.a.put(string2, string3);
    }

    public void putStringArray(String string2, String[] stringArray) {
        this.a.put(string2, stringArray);
    }

    public void putStringArrayList(String string2, ArrayList<String> arrayList) {
        this.a.put(string2, arrayList);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void readFields(DataInput dataInput) throws IOException {
        int n2 = dataInput.readInt();
        int n3 = 0;
        while (n3 < n2) {
            int n4;
            Object object;
            String string2 = dataInput.readUTF();
            int n5 = dataInput.readByte();
            if (n5 == -1) {
                this.a.put(string2, null);
            } else if (n5 == 0) {
                this.a.put(string2, dataInput.readBoolean());
            } else if (n5 == 1) {
                this.a.put(string2, dataInput.readByte());
            } else if (n5 == 2) {
                this.a.put(string2, dataInput.readInt());
            } else if (n5 == 3) {
                this.a.put(string2, dataInput.readLong());
            } else if (n5 == 4) {
                this.a.put(string2, Float.valueOf(dataInput.readFloat()));
            } else if (n5 == 5) {
                this.a.put(string2, dataInput.readDouble());
            } else if (n5 == 6) {
                this.a.put(string2, dataInput.readUTF());
            } else if (n5 == 7) {
                object = new DataMap();
                ((DataMap)object).readFields(dataInput);
                this.a.put(string2, object);
            } else if (n5 == 8) {
                object = new byte[dataInput.readInt()];
                dataInput.readFully((byte[])object);
                this.a.put(string2, object);
            } else if (n5 == 9) {
                n4 = dataInput.readInt();
                object = new long[n4];
                for (n5 = 0; n5 < n4; ++n5) {
                    object[n5] = (String)dataInput.readLong();
                }
                this.a.put(string2, object);
            } else if (n5 == 10) {
                n4 = dataInput.readInt();
                object = new float[n4];
                for (n5 = 0; n5 < n4; ++n5) {
                    object[n5] = (String)dataInput.readFloat();
                }
                this.a.put(string2, object);
            } else if (n5 == 11) {
                n4 = dataInput.readInt();
                object = new String[n4];
                for (n5 = 0; n5 < n4; ++n5) {
                    object[n5] = dataInput.readUTF();
                }
                this.a.put(string2, object);
            } else if (n5 == 12) {
                n4 = dataInput.readInt();
                object = new ArrayList();
                for (n5 = 0; n5 < n4; ++n5) {
                    ((ArrayList)object).add(dataInput.readUTF());
                }
                this.a.put(string2, object);
            } else if (n5 == 13) {
                n4 = dataInput.readInt();
                object = new ArrayList();
                for (n5 = 0; n5 < n4; ++n5) {
                    DataMap dataMap = new DataMap();
                    dataMap.readFields(dataInput);
                    ((ArrayList)object).add(dataMap);
                }
                this.a.put(string2, object);
            } else if (n5 == 14) {
                n4 = dataInput.readInt();
                object = new ArrayList();
                for (n5 = 0; n5 < n4; ++n5) {
                    ((ArrayList)object).add(dataInput.readInt());
                }
                this.a.put(string2, object);
            }
            ++n3;
        }
        return;
    }

    public Object remove(String string2) {
        Object object = this.a.get(string2);
        this.a.remove(string2);
        return object;
    }

    public int size() {
        return this.a.size();
    }

    public Bundle toBundle() {
        Bundle bundle = new Bundle();
        for (String string2 : this.a.keySet()) {
            ArrayList<Bundle> arrayList = this.a.get(string2);
            if (arrayList == null) continue;
            if (arrayList instanceof Boolean) {
                bundle.putBoolean(string2, ((Boolean)((Object)arrayList)).booleanValue());
                continue;
            }
            if (arrayList instanceof Byte) {
                bundle.putByte(string2, ((Byte)((Object)arrayList)).byteValue());
                continue;
            }
            if (arrayList instanceof Integer) {
                bundle.putInt(string2, ((Integer)((Object)arrayList)).intValue());
                continue;
            }
            if (arrayList instanceof Long) {
                bundle.putLong(string2, ((Long)((Object)arrayList)).longValue());
                continue;
            }
            if (arrayList instanceof Float) {
                bundle.putFloat(string2, ((Float)((Object)arrayList)).floatValue());
                continue;
            }
            if (arrayList instanceof Double) {
                bundle.putDouble(string2, ((Double)((Object)arrayList)).doubleValue());
                continue;
            }
            if (arrayList instanceof String) {
                bundle.putString(string2, (String)((Object)arrayList));
                continue;
            }
            if (arrayList instanceof Asset) {
                bundle.putParcelable(string2, (Parcelable)((Asset)((Object)arrayList)));
                continue;
            }
            if (arrayList instanceof DataMap) {
                bundle.putBundle(string2, ((DataMap)((Object)arrayList)).toBundle());
                continue;
            }
            if (arrayList instanceof byte[]) {
                bundle.putByteArray(string2, (byte[])arrayList);
                continue;
            }
            if (arrayList instanceof long[]) {
                bundle.putLongArray(string2, (long[])arrayList);
                continue;
            }
            if (arrayList instanceof float[]) {
                bundle.putFloatArray(string2, (float[])arrayList);
                continue;
            }
            if (arrayList instanceof String[]) {
                bundle.putStringArray(string2, (String[])arrayList);
                continue;
            }
            if (!(arrayList instanceof ArrayList)) continue;
            Object object = arrayList;
            if (((ArrayList)object).size() == 0 || ((ArrayList)object).get(0) instanceof String) {
                bundle.putStringArrayList(string2, (ArrayList)object);
                continue;
            }
            if (((ArrayList)object).get(0) instanceof DataMap) {
                arrayList = new ArrayList<Bundle>();
                object = ((ArrayList)object).iterator();
                while (object.hasNext()) {
                    arrayList.add(((DataMap)object.next()).toBundle());
                }
                bundle.putParcelableArrayList(string2, arrayList);
                continue;
            }
            if (!(((ArrayList)object).get(0) instanceof Integer)) continue;
            bundle.putIntegerArrayList(string2, (ArrayList)object);
        }
        return bundle;
    }

    public byte[] toByteArray() {
        try {
            Object object = new ByteArrayOutputStream();
            this.writeFields(new DataOutputStream((OutputStream)object));
            ((OutputStream)object).flush();
            ((ByteArrayOutputStream)object).close();
            object = ((ByteArrayOutputStream)object).toByteArray();
            return object;
        }
        catch (IOException iOException) {
            bp.b(TAG, "serialize dataMap failed.", iOException);
            return null;
        }
    }

    public String toString() {
        return this.a.toString();
    }

    /*
     * Could not resolve type clashes
     * Unable to fully structure code
     */
    public void writeFields(DataOutput var1_1) throws IOException {
        var1_1.writeInt(this.a.size());
        for (String[] var5_5 : this.a.keySet()) {
            var1_1.writeUTF((String)var5_5);
            var5_5 = this.a.get(var5_5);
            if (var5_5 == null) {
                var1_1.writeByte(-1);
                continue;
            }
            if (var5_5 instanceof Boolean) {
                var1_1.writeByte(0);
                var1_1.writeBoolean((Boolean)var5_5);
                continue;
            }
            if (var5_5 instanceof Byte) {
                var1_1.writeByte(1);
                var1_1.writeByte(((Byte)var5_5).byteValue());
                continue;
            }
            if (var5_5 instanceof Integer) {
                var1_1.writeByte(2);
                var1_1.writeInt((Integer)var5_5);
                continue;
            }
            if (var5_5 instanceof Long) {
                var1_1.writeByte(3);
                var1_1.writeLong((Long)var5_5);
                continue;
            }
            if (var5_5 instanceof Float) {
                var1_1.writeByte(4);
                var1_1.writeFloat(((Float)var5_5).floatValue());
                continue;
            }
            if (var5_5 instanceof Double) {
                var1_1.writeByte(5);
                var1_1.writeDouble((Double)var5_5);
                continue;
            }
            if (var5_5 instanceof String) {
                var1_1.writeByte(6);
                var1_1.writeUTF((String)var5_5);
                continue;
            }
            if (var5_5 instanceof DataMap) {
                var1_1.writeByte(7);
                ((DataMap)var5_5).writeFields(var1_1);
                continue;
            }
            if (var5_5 instanceof byte[]) {
                var1_1.writeByte(8);
                var5_5 = (String[])((byte[])var5_5);
                var1_1.writeInt(var5_5.length);
                var1_1.write((byte[])var5_5);
                continue;
            }
            if (var5_5 instanceof long[]) {
                var1_1.writeByte(9);
                var5_5 = (String[])((long[])var5_5);
                var1_1.writeInt(var5_5.length);
                var3_4 = var5_5.length;
                for (var2_3 = 0; var2_3 < var3_4; ++var2_3) {
                    var1_1.writeLong((long)var5_5[var2_3]);
                }
                continue;
            }
            if (var5_5 instanceof float[]) {
                var1_1.writeByte(10);
                var5_5 = (String[])((float[])var5_5);
                var1_1.writeInt(var5_5.length);
                var3_4 = var5_5.length;
                for (var2_3 = 0; var2_3 < var3_4; ++var2_3) {
                    var1_1.writeFloat((float)var5_5[var2_3]);
                }
                continue;
            }
            if (var5_5 instanceof String[]) {
                var1_1.writeByte(11);
                var5_5 = var5_5;
                var1_1.writeInt(var5_5.length);
                block3: for (String var6_6 : var5_5) {
                    if (var6_6 != null) {
                        var1_1.writeUTF(var6_6);
lbl71:
                        // 2 sources

                        continue block3;
                    }
                    var1_1.writeUTF("");
                    ** continue;
                }
                continue;
            }
            if (!(var5_5 instanceof ArrayList)) continue;
            if ((var5_5 = (ArrayList)var5_5).size() == 0 || var5_5.get(0) instanceof String) {
                var1_1.writeByte(12);
                var1_1.writeInt(var5_5.size());
                block5: for (var2_3 = 0; var2_3 < var5_5.size(); ++var2_3) {
                    if (var5_5.get(var2_3) != null) {
                        var1_1.writeUTF((String)var5_5.get(var2_3));
lbl83:
                        // 2 sources

                        continue block5;
                    }
                    var1_1.writeUTF("");
                    ** continue;
                }
                continue;
            }
            if (var5_5.get(0) instanceof DataMap) {
                var1_1.writeByte(13);
                var1_1.writeInt(var5_5.size());
                block7: for (var2_3 = 0; var2_3 < var5_5.size(); ++var2_3) {
                    if (var5_5.get(var2_3) != null) {
                        ((DataMap)var5_5.get(var2_3)).writeFields(var1_1);
lbl94:
                        // 2 sources

                        continue block7;
                    }
                    new DataMap().writeFields(var1_1);
                    ** continue;
                }
                continue;
            }
            if (!(var5_5.get(0) instanceof Integer)) continue;
            var1_1.writeByte(14);
            var1_1.writeInt(var5_5.size());
            block9: for (var2_3 = 0; var2_3 < var5_5.size(); ++var2_3) {
                if (var5_5.get(var2_3) != null) {
                    var1_1.writeInt((Integer)var5_5.get(var2_3));
lbl105:
                    // 2 sources

                    continue block9;
                }
                var1_1.writeInt(0);
                ** continue;
            }
        }
    }
}

