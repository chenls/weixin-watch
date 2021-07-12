/*
 * Decompiled with CFR 0.151.
 */
package com.ta.utdid2.core.persistent;

import java.util.Map;

public interface MySharedPreferences {
    public boolean checkFile();

    public boolean contains(String var1);

    public MyEditor edit();

    public Map<String, ?> getAll();

    public boolean getBoolean(String var1, boolean var2);

    public float getFloat(String var1, float var2);

    public int getInt(String var1, int var2);

    public long getLong(String var1, long var2);

    public String getString(String var1, String var2);

    public void registerOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener var1);

    public void unregisterOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener var1);

    public static interface MyEditor {
        public MyEditor clear();

        public boolean commit();

        public MyEditor putBoolean(String var1, boolean var2);

        public MyEditor putFloat(String var1, float var2);

        public MyEditor putInt(String var1, int var2);

        public MyEditor putLong(String var1, long var2);

        public MyEditor putString(String var1, String var2);

        public MyEditor remove(String var1);
    }

    public static interface OnSharedPreferenceChangeListener {
        public void onSharedPreferenceChanged(MySharedPreferences var1, String var2);
    }
}

