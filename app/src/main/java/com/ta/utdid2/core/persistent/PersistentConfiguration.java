/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 *  android.os.Environment
 */
package com.ta.utdid2.core.persistent;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import com.ta.utdid2.android.utils.StringUtils;
import com.ta.utdid2.core.persistent.MySharedPreferences;
import com.ta.utdid2.core.persistent.TransactionXMLFile;
import java.io.File;
import java.util.Map;

public class PersistentConfiguration {
    private static final String KEY_TIMESTAMP = "t";
    private static final String KEY_TIMESTAMP2 = "t2";
    private boolean mCanRead;
    private boolean mCanWrite;
    private String mConfigName;
    private Context mContext;
    private SharedPreferences.Editor mEditor;
    private String mFolderName;
    private boolean mIsLessMode;
    private boolean mIsSafety;
    private MySharedPreferences.MyEditor mMyEditor;
    private MySharedPreferences mMySP;
    private SharedPreferences mSp;
    private TransactionXMLFile mTxf;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public PersistentConfiguration(Context object, String string2, String string3, boolean bl2, boolean bl3) {
        long l2;
        long l3;
        long l4;
        block30: {
            long l5;
            block31: {
                String string4;
                this.mConfigName = "";
                this.mFolderName = "";
                this.mIsSafety = false;
                this.mCanRead = false;
                this.mCanWrite = false;
                this.mSp = null;
                this.mMySP = null;
                this.mEditor = null;
                this.mMyEditor = null;
                this.mContext = null;
                this.mTxf = null;
                this.mIsLessMode = false;
                this.mIsSafety = bl2;
                this.mIsLessMode = bl3;
                this.mConfigName = string3;
                this.mFolderName = string2;
                this.mContext = object;
                l4 = 0L;
                l5 = 0L;
                if (object != null) {
                    this.mSp = object.getSharedPreferences(string3, 0);
                    l4 = this.mSp.getLong(KEY_TIMESTAMP, 0L);
                }
                if (StringUtils.isEmpty(string4 = Environment.getExternalStorageState())) {
                    this.mCanWrite = false;
                    this.mCanRead = false;
                } else if (string4.equals("mounted")) {
                    this.mCanWrite = true;
                    this.mCanRead = true;
                } else if (string4.equals("mounted_ro")) {
                    this.mCanRead = true;
                    this.mCanWrite = false;
                } else {
                    this.mCanWrite = false;
                    this.mCanRead = false;
                }
                if (this.mCanRead) break block31;
                l3 = l5;
                l2 = l4;
                if (!this.mCanWrite) break block30;
            }
            l3 = l5;
            l2 = l4;
            if (object != null) {
                l3 = l5;
                l2 = l4;
                if (!StringUtils.isEmpty(string2)) {
                    this.mTxf = this.getTransactionXMLFile(string2);
                    l3 = l5;
                    l2 = l4;
                    if (this.mTxf != null) {
                        l3 = l5;
                        l2 = l4;
                        try {
                            this.mMySP = this.mTxf.getMySharedPreferences(string3, 0);
                            l3 = l5;
                            l2 = l4;
                            l5 = this.mMySP.getLong(KEY_TIMESTAMP, 0L);
                            if (!bl3) {
                                if (l4 > l5) {
                                    l3 = l5;
                                    l2 = l4;
                                    this.copySPToMySP(this.mSp, this.mMySP);
                                    l3 = l5;
                                    l2 = l4;
                                    this.mMySP = this.mTxf.getMySharedPreferences(string3, 0);
                                    l2 = l4;
                                    l3 = l5;
                                    break block30;
                                }
                                if (l4 < l5) {
                                    l3 = l5;
                                    l2 = l4;
                                    this.copyMySPToSP(this.mMySP, this.mSp);
                                    l3 = l5;
                                    l2 = l4;
                                    this.mSp = object.getSharedPreferences(string3, 0);
                                    l3 = l5;
                                    l2 = l4;
                                    break block30;
                                }
                                l3 = l5;
                                l2 = l4;
                                if (l4 == l5) {
                                    l3 = l5;
                                    l2 = l4;
                                    this.copySPToMySP(this.mSp, this.mMySP);
                                    l3 = l5;
                                    l2 = l4;
                                    this.mMySP = this.mTxf.getMySharedPreferences(string3, 0);
                                    l3 = l5;
                                    l2 = l4;
                                }
                                break block30;
                            }
                            l3 = l5;
                            l2 = l4;
                            l4 = this.mSp.getLong(KEY_TIMESTAMP2, 0L);
                            l3 = l5;
                            l2 = l4;
                            l5 = this.mMySP.getLong(KEY_TIMESTAMP2, 0L);
                            if (l4 < l5 && l4 > 0L) {
                                l3 = l5;
                                l2 = l4;
                                this.copySPToMySP(this.mSp, this.mMySP);
                                l3 = l5;
                                l2 = l4;
                                this.mMySP = this.mTxf.getMySharedPreferences(string3, 0);
                                l3 = l5;
                                l2 = l4;
                                break block30;
                            }
                            if (l4 > l5 && l5 > 0L) {
                                l3 = l5;
                                l2 = l4;
                                this.copyMySPToSP(this.mMySP, this.mSp);
                                l3 = l5;
                                l2 = l4;
                                this.mSp = object.getSharedPreferences(string3, 0);
                                l3 = l5;
                                l2 = l4;
                                break block30;
                            }
                            if (l4 == 0L && l5 > 0L) {
                                l3 = l5;
                                l2 = l4;
                                this.copyMySPToSP(this.mMySP, this.mSp);
                                l3 = l5;
                                l2 = l4;
                                this.mSp = object.getSharedPreferences(string3, 0);
                                l3 = l5;
                                l2 = l4;
                                break block30;
                            }
                            if (l5 == 0L && l4 > 0L) {
                                l3 = l5;
                                l2 = l4;
                                this.copySPToMySP(this.mSp, this.mMySP);
                                l3 = l5;
                                l2 = l4;
                                this.mMySP = this.mTxf.getMySharedPreferences(string3, 0);
                                l3 = l5;
                                l2 = l4;
                            } else {
                                l3 = l5;
                                l2 = l4;
                                if (l4 == l5) {
                                    l3 = l5;
                                    l2 = l4;
                                    this.copySPToMySP(this.mSp, this.mMySP);
                                    l3 = l5;
                                    l2 = l4;
                                    this.mMySP = this.mTxf.getMySharedPreferences(string3, 0);
                                    l3 = l5;
                                    l2 = l4;
                                }
                            }
                        }
                        catch (Exception exception) {}
                    }
                }
            }
        }
        if (l2 == l3) {
            if (l2 != 0L) return;
            if (l3 != 0L) return;
        }
        l4 = System.currentTimeMillis();
        if (this.mIsLessMode) {
            if (!this.mIsLessMode) return;
            if (l2 != 0L) return;
            if (l3 != 0L) return;
        }
        if (this.mSp != null) {
            object = this.mSp.edit();
            object.putLong(KEY_TIMESTAMP2, l4);
            object.commit();
        }
        try {
            if (this.mMySP == null) return;
            object = this.mMySP.edit();
            object.putLong(KEY_TIMESTAMP2, l4);
            object.commit();
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    private boolean checkSDCardXMLFile() {
        if (this.mMySP != null) {
            boolean bl2 = this.mMySP.checkFile();
            if (!bl2) {
                this.commit();
            }
            return bl2;
        }
        return false;
    }

    private void copyMySPToSP(MySharedPreferences object, SharedPreferences sharedPreferences) {
        if (object != null && sharedPreferences != null && (sharedPreferences = sharedPreferences.edit()) != null) {
            sharedPreferences.clear();
            for (Map.Entry<String, ?> entry : object.getAll().entrySet()) {
                String string2 = entry.getKey();
                Object entry2 = entry.getValue();
                if (entry2 instanceof String) {
                    sharedPreferences.putString(string2, (String)entry2);
                    continue;
                }
                if (entry2 instanceof Integer) {
                    sharedPreferences.putInt(string2, ((Integer)entry2).intValue());
                    continue;
                }
                if (entry2 instanceof Long) {
                    sharedPreferences.putLong(string2, ((Long)entry2).longValue());
                    continue;
                }
                if (entry2 instanceof Float) {
                    sharedPreferences.putFloat(string2, ((Float)entry2).floatValue());
                    continue;
                }
                if (!(entry2 instanceof Boolean)) continue;
                sharedPreferences.putBoolean(string2, ((Boolean)entry2).booleanValue());
            }
            sharedPreferences.commit();
        }
    }

    private void copySPToMySP(SharedPreferences object, MySharedPreferences object2) {
        if (object != null && object2 != null && (object2 = object2.edit()) != null) {
            object2.clear();
            for (Map.Entry entry : object.getAll().entrySet()) {
                String string2 = (String)entry.getKey();
                Object entry2 = entry.getValue();
                if (entry2 instanceof String) {
                    object2.putString(string2, (String)entry2);
                    continue;
                }
                if (entry2 instanceof Integer) {
                    object2.putInt(string2, (Integer)entry2);
                    continue;
                }
                if (entry2 instanceof Long) {
                    object2.putLong(string2, (Long)entry2);
                    continue;
                }
                if (entry2 instanceof Float) {
                    object2.putFloat(string2, ((Float)entry2).floatValue());
                    continue;
                }
                if (!(entry2 instanceof Boolean)) continue;
                object2.putBoolean(string2, (Boolean)entry2);
            }
            object2.commit();
        }
    }

    private File getRootFolder(String object) {
        File file = Environment.getExternalStorageDirectory();
        if (file != null) {
            object = new File(String.format("%s%s%s", file.getAbsolutePath(), File.separator, object));
            if (object != null && !((File)object).exists()) {
                ((File)object).mkdirs();
            }
            return object;
        }
        return null;
    }

    private TransactionXMLFile getTransactionXMLFile(String object) {
        if ((object = this.getRootFolder((String)object)) != null) {
            this.mTxf = new TransactionXMLFile(((File)object).getAbsolutePath());
            return this.mTxf;
        }
        return null;
    }

    private void initEditor() {
        if (this.mEditor == null && this.mSp != null) {
            this.mEditor = this.mSp.edit();
        }
        if (this.mCanWrite && this.mMyEditor == null && this.mMySP != null) {
            this.mMyEditor = this.mMySP.edit();
        }
        this.checkSDCardXMLFile();
    }

    public void clear() {
        this.initEditor();
        long l2 = System.currentTimeMillis();
        if (this.mEditor != null) {
            this.mEditor.clear();
            this.mEditor.putLong(KEY_TIMESTAMP, l2);
        }
        if (this.mMyEditor != null) {
            this.mMyEditor.clear();
            this.mMyEditor.putLong(KEY_TIMESTAMP, l2);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean commit() {
        boolean bl2 = true;
        long l2 = System.currentTimeMillis();
        boolean bl3 = bl2;
        if (this.mEditor != null) {
            if (!this.mIsLessMode && this.mSp != null) {
                this.mEditor.putLong(KEY_TIMESTAMP, l2);
            }
            bl3 = bl2;
            if (!this.mEditor.commit()) {
                bl3 = false;
            }
        }
        if (this.mSp != null && this.mContext != null) {
            this.mSp = this.mContext.getSharedPreferences(this.mConfigName, 0);
        }
        String string2 = Environment.getExternalStorageState();
        boolean bl4 = bl3;
        if (StringUtils.isEmpty(string2)) return bl4;
        bl2 = bl3;
        if (string2.equals("mounted")) {
            if (this.mMySP == null) {
                TransactionXMLFile transactionXMLFile = this.getTransactionXMLFile(this.mFolderName);
                bl2 = bl3;
                if (transactionXMLFile != null) {
                    this.mMySP = transactionXMLFile.getMySharedPreferences(this.mConfigName, 0);
                    if (!this.mIsLessMode) {
                        this.copySPToMySP(this.mSp, this.mMySP);
                    } else {
                        this.copyMySPToSP(this.mMySP, this.mSp);
                    }
                    this.mMyEditor = this.mMySP.edit();
                    bl2 = bl3;
                }
            } else {
                bl2 = bl3;
                if (this.mMyEditor != null) {
                    bl2 = bl3;
                    if (!this.mMyEditor.commit()) {
                        bl2 = false;
                    }
                }
            }
        }
        if (!string2.equals("mounted")) {
            bl4 = bl2;
            if (!string2.equals("mounted_ro")) return bl4;
            bl4 = bl2;
            if (this.mMySP == null) return bl4;
        }
        bl4 = bl2;
        try {
            if (this.mTxf == null) return bl4;
            this.mMySP = this.mTxf.getMySharedPreferences(this.mConfigName, 0);
            return bl2;
        }
        catch (Exception exception) {
            return bl2;
        }
    }

    public Map<String, ?> getAll() {
        this.checkSDCardXMLFile();
        if (this.mSp != null) {
            return this.mSp.getAll();
        }
        if (this.mMySP != null) {
            return this.mMySP.getAll();
        }
        return null;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean getBoolean(String string2) {
        boolean bl2 = false;
        this.checkSDCardXMLFile();
        if (this.mSp != null) {
            return this.mSp.getBoolean(string2, false);
        }
        if (this.mMySP == null) return bl2;
        return this.mMySP.getBoolean(string2, false);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public float getFloat(String string2) {
        float f2 = 0.0f;
        this.checkSDCardXMLFile();
        if (this.mSp != null) {
            return this.mSp.getFloat(string2, 0.0f);
        }
        if (this.mMySP == null) return f2;
        return this.mMySP.getFloat(string2, 0.0f);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public int getInt(String string2) {
        int n2 = 0;
        this.checkSDCardXMLFile();
        if (this.mSp != null) {
            return this.mSp.getInt(string2, 0);
        }
        if (this.mMySP == null) return n2;
        return this.mMySP.getInt(string2, 0);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public long getLong(String string2) {
        long l2 = 0L;
        this.checkSDCardXMLFile();
        if (this.mSp != null) {
            return this.mSp.getLong(string2, 0L);
        }
        if (this.mMySP == null) return l2;
        return this.mMySP.getLong(string2, 0L);
    }

    public String getString(String string2) {
        String string3;
        this.checkSDCardXMLFile();
        if (this.mSp != null && !StringUtils.isEmpty(string3 = this.mSp.getString(string2, ""))) {
            return string3;
        }
        if (this.mMySP != null) {
            return this.mMySP.getString(string2, "");
        }
        return "";
    }

    public void putBoolean(String string2, boolean bl2) {
        if (!StringUtils.isEmpty(string2) && !string2.equals(KEY_TIMESTAMP)) {
            this.initEditor();
            if (this.mEditor != null) {
                this.mEditor.putBoolean(string2, bl2);
            }
            if (this.mMyEditor != null) {
                this.mMyEditor.putBoolean(string2, bl2);
            }
        }
    }

    public void putFloat(String string2, float f2) {
        if (!StringUtils.isEmpty(string2) && !string2.equals(KEY_TIMESTAMP)) {
            this.initEditor();
            if (this.mEditor != null) {
                this.mEditor.putFloat(string2, f2);
            }
            if (this.mMyEditor != null) {
                this.mMyEditor.putFloat(string2, f2);
            }
        }
    }

    public void putInt(String string2, int n2) {
        if (!StringUtils.isEmpty(string2) && !string2.equals(KEY_TIMESTAMP)) {
            this.initEditor();
            if (this.mEditor != null) {
                this.mEditor.putInt(string2, n2);
            }
            if (this.mMyEditor != null) {
                this.mMyEditor.putInt(string2, n2);
            }
        }
    }

    public void putLong(String string2, long l2) {
        if (!StringUtils.isEmpty(string2) && !string2.equals(KEY_TIMESTAMP)) {
            this.initEditor();
            if (this.mEditor != null) {
                this.mEditor.putLong(string2, l2);
            }
            if (this.mMyEditor != null) {
                this.mMyEditor.putLong(string2, l2);
            }
        }
    }

    public void putString(String string2, String string3) {
        if (!StringUtils.isEmpty(string2) && !string2.equals(KEY_TIMESTAMP)) {
            this.initEditor();
            if (this.mEditor != null) {
                this.mEditor.putString(string2, string3);
            }
            if (this.mMyEditor != null) {
                this.mMyEditor.putString(string2, string3);
            }
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public void reload() {
        String string2;
        if (this.mSp != null && this.mContext != null) {
            this.mSp = this.mContext.getSharedPreferences(this.mConfigName, 0);
        }
        if (StringUtils.isEmpty(string2 = Environment.getExternalStorageState()) || !string2.equals("mounted") && (!string2.equals("mounted_ro") || this.mMySP == null)) return;
        try {
            if (this.mTxf == null) return;
            this.mMySP = this.mTxf.getMySharedPreferences(this.mConfigName, 0);
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    public void remove(String string2) {
        if (!StringUtils.isEmpty(string2) && !string2.equals(KEY_TIMESTAMP)) {
            this.initEditor();
            if (this.mEditor != null) {
                this.mEditor.remove(string2);
            }
            if (this.mMyEditor != null) {
                this.mMyEditor.remove(string2);
            }
        }
    }
}

