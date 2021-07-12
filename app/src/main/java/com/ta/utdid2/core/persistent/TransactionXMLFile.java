/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  org.xmlpull.v1.XmlPullParserException
 */
package com.ta.utdid2.core.persistent;

import com.ta.utdid2.core.persistent.MySharedPreferences;
import com.ta.utdid2.core.persistent.XmlUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.WeakHashMap;
import org.xmlpull.v1.XmlPullParserException;

public class TransactionXMLFile {
    private static final Object GLOBAL_COMMIT_LOCK = new Object();
    public static final int MODE_PRIVATE = 0;
    public static final int MODE_WORLD_READABLE = 1;
    public static final int MODE_WORLD_WRITEABLE = 2;
    private File mPreferencesDir;
    private final Object mSync = new Object();
    private HashMap<File, MySharedPreferencesImpl> sSharedPrefs = new HashMap();

    public TransactionXMLFile(String string2) {
        if (string2 != null && string2.length() > 0) {
            this.mPreferencesDir = new File(string2);
            return;
        }
        throw new RuntimeException("Directory can not be empty");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private File getPreferencesDir() {
        Object object = this.mSync;
        synchronized (object) {
            return this.mPreferencesDir;
        }
    }

    private File getSharedPrefsFile(String string2) {
        return this.makeFilename(this.getPreferencesDir(), string2 + ".xml");
    }

    private static File makeBackupFile(File file) {
        return new File(file.getPath() + ".bak");
    }

    private File makeFilename(File file, String string2) {
        if (string2.indexOf(File.separatorChar) < 0) {
            return new File(file, string2);
        }
        throw new IllegalArgumentException("File " + string2 + " contains a path separator");
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public MySharedPreferences getMySharedPreferences(String var1_1, int var2_9) {
        block33: {
            var10_10 = this.getSharedPrefsFile((String)var1_1);
            var1_1 = TransactionXMLFile.GLOBAL_COMMIT_LOCK;
            synchronized (var1_1) {
                var6_11 = this.sSharedPrefs.get(var10_10);
                if (var6_11 != null && !var6_11.hasFileChanged()) {
                    return var6_11;
                }
            }
            var1_1 = TransactionXMLFile.makeBackupFile(var10_10);
            if (var1_1.exists()) {
                var10_10.delete();
                var1_1.renameTo(var10_10);
            }
            if (!var10_10.exists() || !var10_10.canRead()) {
                // empty if block
            }
            var4_12 = null;
            var9_13 = null;
            var8_14 = null;
            var5_15 = null;
            var1_1 = var3_16 = null;
            if (var10_10.exists()) {
                var1_1 = var3_16;
                if (var10_10.canRead()) {
                    var7_21 = new FileInputStream(var10_10);
                    var3_16 = var9_13;
                    var4_12 = var8_14;
                    break block33;
                }
            }
lbl28:
            // 12 sources

            while (true) {
                var5_15 = TransactionXMLFile.GLOBAL_COMMIT_LOCK;
                synchronized (var5_15) {
                    block34: {
                        if (var6_11 != null) {
                            var6_11.replace((Map)var1_1);
                            return var6_11;
                        }
                        var3_16 = var4_12 = this.sSharedPrefs.get(var10_10);
                        if (var4_12 != null) return var3_16;
                        var3_16 = new MySharedPreferencesImpl(var10_10, var2_9, (Map)var1_1);
                        try {
                            this.sSharedPrefs.put(var10_10, (MySharedPreferencesImpl)var3_16);
                        }
                        catch (Throwable var1_3) {
                            break block34;
                        }
                        return var3_16;
                    }
                    throw var1_3;
                }
                break;
            }
        }
        var3_16 = var1_1 = XmlUtils.readMapXml(var7_21);
        var4_12 = var1_1;
        var5_15 = var1_1;
        var7_21.close();
        catch (XmlPullParserException var1_2) {
            var3_16 = null;
            var1_1 = var4_12;
lbl56:
            // 2 sources

            while (true) {
                var3_16 = new FileInputStream(var10_10);
                try {
                    var4_12 = new byte[var3_16.available()];
                    var3_16.read((byte[])var4_12);
                    new String((byte[])var4_12, 0, ((Object)var4_12).length, "UTF-8");
                    ** GOTO lbl28
                }
                catch (FileNotFoundException var3_17) lbl-1000:
                // 2 sources

                {
                    while (true) {
                        var3_16.printStackTrace();
                        ** GOTO lbl28
                        break;
                    }
                }
                catch (IOException var3_18) lbl-1000:
                // 2 sources

                {
                    while (true) {
                        var3_16.printStackTrace();
                        ** GOTO lbl28
                        break;
                    }
                }
                break;
            }
            catch (IOException var1_5) {
                var1_1 = var3_16;
            }
            catch (FileNotFoundException var1_7) {
                var1_1 = var4_12;
            }
            catch (FileNotFoundException var3_20) {
                ** continue;
            }
            catch (IOException var3_19) {
                ** continue;
            }
        }
        catch (XmlPullParserException var1_8) {
            var1_1 = var5_15;
            var3_16 = var7_21;
            ** continue;
        }
        catch (IOException var1_4) {
            var1_1 = var3_16;
            ** GOTO lbl28
        }
        catch (FileNotFoundException var1_6) {
            var1_1 = var3_16;
            ** continue;
        }
    }

    private static final class MySharedPreferencesImpl
    implements MySharedPreferences {
        private static final Object mContent = new Object();
        private boolean hasChange = false;
        private final File mBackupFile;
        private final File mFile;
        private WeakHashMap<MySharedPreferences.OnSharedPreferenceChangeListener, Object> mListeners;
        private Map mMap;
        private final int mMode;

        /*
         * Enabled aggressive block sorting
         */
        MySharedPreferencesImpl(File file, int n2, Map hashMap) {
            this.mFile = file;
            this.mBackupFile = TransactionXMLFile.makeBackupFile(file);
            this.mMode = n2;
            if (hashMap == null) {
                hashMap = new HashMap();
            }
            this.mMap = hashMap;
            this.mListeners = new WeakHashMap();
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        private FileOutputStream createFileOutputStream(File object) {
            Object var2_3 = null;
            try {
                FileOutputStream fileOutputStream = new FileOutputStream((File)object);
                return fileOutputStream;
            }
            catch (FileNotFoundException fileNotFoundException) {
                if (!((File)object).getParentFile().mkdir()) {
                    return null;
                }
                try {
                    return new FileOutputStream((File)object);
                }
                catch (FileNotFoundException fileNotFoundException2) {
                    return var2_3;
                }
            }
        }

        /*
         * Unable to fully structure code
         */
        private boolean writeFileLocked() {
            if (this.mFile.exists()) {
                if (!this.mBackupFile.exists()) {
                    if (!this.mFile.renameTo(this.mBackupFile)) lbl-1000:
                    // 3 sources

                    {
                        return false;
                    }
                } else {
                    this.mFile.delete();
                }
            }
            var1_1 = this.createFileOutputStream(this.mFile);
            if (var1_1 == null) ** GOTO lbl-1000
            try {
                XmlUtils.writeMapXml(this.mMap, var1_1);
                var1_1.close();
                this.mBackupFile.delete();
                return true;
            }
            catch (IOException var1_2) lbl-1000:
            // 2 sources

            {
                while (true) {
                    if (this.mFile.exists() && !this.mFile.delete()) ** break;
                    ** continue;
                    return false;
                }
            }
            catch (XmlPullParserException var1_3) {
                ** continue;
            }
        }

        @Override
        public boolean checkFile() {
            return this.mFile != null && new File(this.mFile.getAbsolutePath()).exists();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public boolean contains(String string2) {
            synchronized (this) {
                return this.mMap.containsKey(string2);
            }
        }

        @Override
        public MySharedPreferences.MyEditor edit() {
            return new EditorImpl();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public Map<String, ?> getAll() {
            synchronized (this) {
                return new HashMap(this.mMap);
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public boolean getBoolean(String object, boolean bl2) {
            synchronized (this) {
                object = (Boolean)this.mMap.get(object);
                if (object == null) return bl2;
                return (Boolean)object;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public float getFloat(String object, float f2) {
            synchronized (this) {
                object = (Float)this.mMap.get(object);
                if (object == null) return f2;
                return ((Float)object).floatValue();
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public int getInt(String object, int n2) {
            synchronized (this) {
                object = (Integer)this.mMap.get(object);
                if (object == null) return n2;
                return (Integer)object;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public long getLong(String object, long l2) {
            synchronized (this) {
                object = (Long)this.mMap.get(object);
                if (object == null) return l2;
                return (Long)object;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public String getString(String string2, String string3) {
            synchronized (this) {
                string2 = (String)this.mMap.get(string2);
                if (string2 != null) return string2;
                return string3;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public boolean hasFileChanged() {
            synchronized (this) {
                return this.hasChange;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void registerOnSharedPreferenceChangeListener(MySharedPreferences.OnSharedPreferenceChangeListener onSharedPreferenceChangeListener) {
            synchronized (this) {
                this.mListeners.put(onSharedPreferenceChangeListener, mContent);
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void replace(Map map) {
            if (map != null) {
                synchronized (this) {
                    this.mMap = map;
                    return;
                }
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void setHasChange(boolean bl2) {
            synchronized (this) {
                this.hasChange = bl2;
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void unregisterOnSharedPreferenceChangeListener(MySharedPreferences.OnSharedPreferenceChangeListener onSharedPreferenceChangeListener) {
            synchronized (this) {
                this.mListeners.remove(onSharedPreferenceChangeListener);
                return;
            }
        }

        public final class EditorImpl
        implements MySharedPreferences.MyEditor {
            private boolean mClear = false;
            private final Map<String, Object> mModified = new HashMap<String, Object>();

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public MySharedPreferences.MyEditor clear() {
                synchronized (this) {
                    this.mClear = true;
                    return this;
                }
            }

            /*
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Converted monitor instructions to comments
             * Lifted jumps to return sites
             */
            @Override
            public boolean commit() {
                boolean bl2;
                void var3_2;
                int n2;
                Object object;
                HashSet hashSet;
                ArrayList<String> arrayList;
                block19: {
                    arrayList = null;
                    hashSet = null;
                    object = GLOBAL_COMMIT_LOCK;
                    // MONITORENTER : object
                    n2 = MySharedPreferencesImpl.this.mListeners.size() > 0 ? 1 : 0;
                    if (n2 == 0) break block19;
                    arrayList = new ArrayList<String>();
                    try {
                        hashSet = new HashSet(MySharedPreferencesImpl.this.mListeners.keySet());
                    }
                    catch (Throwable throwable) {
                        throw var3_2;
                    }
                }
                if (this.mClear) {
                    MySharedPreferencesImpl.this.mMap.clear();
                    this.mClear = false;
                }
                Iterator<Map.Entry<String, Object>> iterator = this.mModified.entrySet().iterator();
                while (iterator.hasNext()) {
                    Object object2 = iterator.next();
                    Object object3 = object2.getKey();
                    if ((object2 = object2.getValue()) == this) {
                        MySharedPreferencesImpl.this.mMap.remove(object3);
                    } else {
                        MySharedPreferencesImpl.this.mMap.put(object3, object2);
                    }
                    if (n2 == 0) continue;
                    arrayList.add((String)object3);
                }
                this.mModified.clear();
                // MONITOREXIT : this
                try {
                    bl2 = MySharedPreferencesImpl.this.writeFileLocked();
                    if (bl2) {
                        MySharedPreferencesImpl.this.setHasChange(true);
                    }
                    // MONITOREXIT : object
                    if (n2 == 0) return bl2;
                    n2 = arrayList.size() - 1;
                }
                catch (Throwable throwable) {
                    throw var3_2;
                }
                while (n2 >= 0) {
                    object = (String)arrayList.get(n2);
                    for (Object object3 : hashSet) {
                        if (object3 == null) continue;
                        object3.onSharedPreferenceChanged(MySharedPreferencesImpl.this, (String)object);
                    }
                    --n2;
                }
                return bl2;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public MySharedPreferences.MyEditor putBoolean(String string2, boolean bl2) {
                synchronized (this) {
                    this.mModified.put(string2, bl2);
                    return this;
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public MySharedPreferences.MyEditor putFloat(String string2, float f2) {
                synchronized (this) {
                    this.mModified.put(string2, Float.valueOf(f2));
                    return this;
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public MySharedPreferences.MyEditor putInt(String string2, int n2) {
                synchronized (this) {
                    this.mModified.put(string2, n2);
                    return this;
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public MySharedPreferences.MyEditor putLong(String string2, long l2) {
                synchronized (this) {
                    this.mModified.put(string2, l2);
                    return this;
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public MySharedPreferences.MyEditor putString(String string2, String string3) {
                synchronized (this) {
                    this.mModified.put(string2, string3);
                    return this;
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public MySharedPreferences.MyEditor remove(String string2) {
                synchronized (this) {
                    this.mModified.put(string2, this);
                    return this;
                }
            }
        }
    }
}

