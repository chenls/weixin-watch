/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.ContentResolver
 *  android.content.Context
 *  android.provider.Settings$System
 */
package com.ta.utdid2.device;

import android.content.ContentResolver;
import android.content.Context;
import android.provider.Settings;
import com.ta.utdid2.android.utils.Base64;
import com.ta.utdid2.android.utils.IntUtils;
import com.ta.utdid2.android.utils.PhoneInfoUtils;
import com.ta.utdid2.android.utils.StringUtils;
import com.ta.utdid2.core.persistent.PersistentConfiguration;
import com.ta.utdid2.device.Device;
import com.ta.utdid2.device.UTUtdidHelper;
import com.ta.utdid2.device.UTUtdidHelper2;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Random;
import java.util.regex.Pattern;
import java.util.zip.Adler32;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class UTUtdid {
    private static final Object CREATE_LOCK = new Object();
    private static final String HMAC_KEY = "d6fc3a4a06adbde89223bvefedc24fecde188aaa9161";
    private static final String S_GLOBAL_PERSISTENT_CONFIG_DIR;
    private static final String S_GLOBAL_PERSISTENT_CONFIG_KEY = "Alvin2";
    private static final String S_LOCAL_STORAGE_KEY = "ContextData";
    private static final String S_LOCAL_STORAGE_NAME = ".DataStorage";
    static final String UM_SETTINGS_STORAGE = "dxCRMxhQkdGePGnp";
    static final String UM_SETTINGS_STORAGE_NEW = "mqBRboGZkQPcAkyk";
    private static UTUtdid s_umutdid;
    private String mCBDomain = "xx_utdid_domain";
    private String mCBKey = "xx_utdid_key";
    private Context mContext = null;
    private PersistentConfiguration mPC = null;
    private Pattern mPattern = Pattern.compile("[^0-9a-zA-Z=/+]+");
    private PersistentConfiguration mTaoPC = null;
    private String mUtdid = null;
    private UTUtdidHelper mUtdidHelper = null;

    static {
        s_umutdid = null;
        S_GLOBAL_PERSISTENT_CONFIG_DIR = ".UTSystemConfig" + File.separator + "Global";
    }

    public UTUtdid(Context context) {
        this.mContext = context;
        this.mTaoPC = new PersistentConfiguration(context, S_GLOBAL_PERSISTENT_CONFIG_DIR, S_GLOBAL_PERSISTENT_CONFIG_KEY, false, true);
        this.mPC = new PersistentConfiguration(context, S_LOCAL_STORAGE_NAME, S_LOCAL_STORAGE_KEY, false, true);
        this.mUtdidHelper = new UTUtdidHelper();
        this.mCBKey = String.format("K_%d", StringUtils.hashCode(this.mCBKey));
        this.mCBDomain = String.format("D_%d", StringUtils.hashCode(this.mCBDomain));
    }

    private static String _calcHmac(byte[] byArray) throws Exception {
        Mac mac = Mac.getInstance("HmacSHA1");
        mac.init(new SecretKeySpec(HMAC_KEY.getBytes(), mac.getAlgorithm()));
        return Base64.encodeToString(mac.doFinal(byArray), 2);
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private final byte[] _generateUtdid() throws Exception {
        void var3_6;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int n2 = (int)(System.currentTimeMillis() / 1000L);
        int n3 = new Random().nextInt();
        byte[] byArray = IntUtils.getBytes(n2);
        byte[] byArray2 = IntUtils.getBytes(n3);
        byteArrayOutputStream.write(byArray, 0, 4);
        byteArrayOutputStream.write(byArray2, 0, 4);
        byteArrayOutputStream.write(3);
        byteArrayOutputStream.write(0);
        try {
            String string2 = PhoneInfoUtils.getImei(this.mContext);
        }
        catch (Exception exception) {
            String string3 = "" + new Random().nextInt();
        }
        byteArrayOutputStream.write(IntUtils.getBytes(StringUtils.hashCode((String)var3_6)), 0, 4);
        byteArrayOutputStream.write(IntUtils.getBytes(StringUtils.hashCode(UTUtdid._calcHmac(byteArrayOutputStream.toByteArray()))));
        return byteArrayOutputStream.toByteArray();
    }

    static long getMetadataCheckSum(Device object) {
        if (object != null && !StringUtils.isEmpty((String)(object = String.format("%s%s%s%s%s", ((Device)object).getUtdid(), ((Device)object).getDeviceId(), ((Device)object).getCreateTimestamp(), ((Device)object).getImsi(), ((Device)object).getImei())))) {
            Adler32 adler32 = new Adler32();
            adler32.reset();
            adler32.update(((String)object).getBytes());
            return adler32.getValue();
        }
        return 0L;
    }

    private String getUtdidFromTaoPPC() {
        String string2;
        if (this.mTaoPC != null && !StringUtils.isEmpty(string2 = this.mTaoPC.getString("UTDID")) && this.mUtdidHelper.packUtdidStr(string2) != null) {
            return string2;
        }
        return null;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static UTUtdid instance(Context context) {
        if (context != null && s_umutdid == null) {
            Object object = CREATE_LOCK;
            synchronized (object) {
                if (s_umutdid == null) {
                    s_umutdid = new UTUtdid(context);
                }
            }
        }
        return s_umutdid;
    }

    private boolean isValidUTDID(String string2) {
        boolean bl2;
        boolean bl3 = bl2 = false;
        if (string2 != null) {
            String string3 = string2;
            if (string2.endsWith("\n")) {
                string3 = string2.substring(0, string2.length() - 1);
            }
            bl3 = bl2;
            if (24 == string3.length()) {
                bl3 = bl2;
                if (!this.mPattern.matcher(string3).find()) {
                    bl3 = true;
                }
            }
        }
        return bl3;
    }

    private void saveUtdidToLocalStorage(String string2) {
        if (string2 != null && this.mPC != null && !string2.equals(this.mPC.getString(this.mCBKey))) {
            this.mPC.putString(this.mCBKey, string2);
            this.mPC.commit();
        }
    }

    private void saveUtdidToNewSettings(String string2) {
        if (this.mContext.checkCallingOrSelfPermission("android.permission.WRITE_SETTINGS") == 0 && this.isValidUTDID(string2)) {
            String string3 = string2;
            if (string2.endsWith("\n")) {
                string3 = string2.substring(0, string2.length() - 1);
            }
            if (24 == string3.length() && !this.isValidUTDID(Settings.System.getString((ContentResolver)this.mContext.getContentResolver(), (String)UM_SETTINGS_STORAGE_NEW))) {
                Settings.System.putString((ContentResolver)this.mContext.getContentResolver(), (String)UM_SETTINGS_STORAGE_NEW, (String)string3);
            }
        }
    }

    private void saveUtdidToSettings(String string2) {
        if (this.mContext.checkCallingOrSelfPermission("android.permission.WRITE_SETTINGS") == 0 && string2 != null) {
            this.syncUTDIDToSettings(string2);
        }
    }

    private void saveUtdidToTaoPPC(String string2) {
        if (this.isValidUTDID(string2)) {
            String string3 = string2;
            if (string2.endsWith("\n")) {
                string3 = string2.substring(0, string2.length() - 1);
            }
            if (string3.length() == 24 && this.mTaoPC != null) {
                String string4;
                String string5;
                String string6 = this.mTaoPC.getString("UTDID");
                string2 = string5 = this.mTaoPC.getString("EI");
                if (StringUtils.isEmpty(string5)) {
                    string2 = PhoneInfoUtils.getImei(this.mContext);
                }
                string5 = string4 = this.mTaoPC.getString("SI");
                if (StringUtils.isEmpty(string4)) {
                    string5 = PhoneInfoUtils.getImsi(this.mContext);
                }
                Object object = this.mTaoPC.getString("DID");
                string4 = object;
                if (StringUtils.isEmpty((String)object)) {
                    string4 = string2;
                }
                if (string6 == null || !string6.equals(string3)) {
                    object = new Device();
                    ((Device)object).setImei(string2);
                    ((Device)object).setImsi(string5);
                    ((Device)object).setUtdid(string3);
                    ((Device)object).setDeviceId(string4);
                    ((Device)object).setCreateTimestamp(System.currentTimeMillis());
                    this.mTaoPC.putString("UTDID", string3);
                    this.mTaoPC.putString("EI", ((Device)object).getImei());
                    this.mTaoPC.putString("SI", ((Device)object).getImsi());
                    this.mTaoPC.putString("DID", ((Device)object).getDeviceId());
                    this.mTaoPC.putLong("timestamp", ((Device)object).getCreateTimestamp());
                    this.mTaoPC.putLong("S", UTUtdid.getMetadataCheckSum((Device)object));
                    this.mTaoPC.commit();
                }
            }
        }
    }

    private void syncUTDIDToSettings(String string2) {
        if (!string2.equals(Settings.System.getString((ContentResolver)this.mContext.getContentResolver(), (String)UM_SETTINGS_STORAGE))) {
            Settings.System.putString((ContentResolver)this.mContext.getContentResolver(), (String)UM_SETTINGS_STORAGE, (String)string2);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public String getValue() {
        synchronized (this) {
            Object object;
            block19: {
                String string2;
                boolean bl2;
                UTUtdidHelper2 uTUtdidHelper2;
                String string3;
                block20: {
                    block17: {
                        block18: {
                            if (this.mUtdid != null) {
                                return this.mUtdid;
                            }
                            string3 = Settings.System.getString((ContentResolver)this.mContext.getContentResolver(), (String)UM_SETTINGS_STORAGE_NEW);
                            object = string3;
                            if (this.isValidUTDID(string3)) return object;
                            uTUtdidHelper2 = new UTUtdidHelper2();
                            bl2 = false;
                            string3 = Settings.System.getString((ContentResolver)this.mContext.getContentResolver(), (String)UM_SETTINGS_STORAGE);
                            if (StringUtils.isEmpty(string3)) break block17;
                            object = uTUtdidHelper2.dePackWithBase64(string3);
                            if (!this.isValidUTDID((String)object)) break block18;
                            this.saveUtdidToNewSettings((String)object);
                            break block19;
                        }
                        string2 = uTUtdidHelper2.dePack(string3);
                        object = string3;
                        if (this.isValidUTDID(string2)) {
                            string2 = this.mUtdidHelper.packUtdidStr(string2);
                            object = string3;
                            if (!StringUtils.isEmpty(string2)) {
                                this.saveUtdidToSettings(string2);
                                object = Settings.System.getString((ContentResolver)this.mContext.getContentResolver(), (String)UM_SETTINGS_STORAGE);
                            }
                        }
                        if (this.isValidUTDID(string3 = this.mUtdidHelper.dePack((String)object))) {
                            this.mUtdid = string3;
                            this.saveUtdidToTaoPPC(string3);
                            this.saveUtdidToLocalStorage((String)object);
                            this.saveUtdidToNewSettings(this.mUtdid);
                            return this.mUtdid;
                        }
                        break block20;
                    }
                    bl2 = true;
                }
                if (this.isValidUTDID((String)(object = this.getUtdidFromTaoPPC()))) {
                    string3 = this.mUtdidHelper.packUtdidStr((String)object);
                    if (bl2) {
                        this.saveUtdidToSettings(string3);
                    }
                    this.saveUtdidToNewSettings((String)object);
                    this.saveUtdidToLocalStorage(string3);
                    this.mUtdid = object;
                } else {
                    string2 = this.mPC.getString(this.mCBKey);
                    if (!StringUtils.isEmpty(string2)) {
                        string3 = uTUtdidHelper2.dePack(string2);
                        object = string3;
                        if (!this.isValidUTDID(string3)) {
                            object = this.mUtdidHelper.dePack(string2);
                        }
                        if (this.isValidUTDID((String)object)) {
                            string3 = this.mUtdidHelper.packUtdidStr((String)object);
                            if (!StringUtils.isEmpty((String)object)) {
                                this.mUtdid = object;
                                if (bl2) {
                                    this.saveUtdidToSettings(string3);
                                }
                                this.saveUtdidToTaoPPC(this.mUtdid);
                                return this.mUtdid;
                            }
                        }
                    }
                    try {
                        object = this._generateUtdid();
                        if (object == null) return null;
                        this.mUtdid = Base64.encodeToString((byte[])object, 2);
                        this.saveUtdidToTaoPPC(this.mUtdid);
                        object = this.mUtdidHelper.pack((byte[])object);
                        if (object == null) return this.mUtdid;
                        if (bl2) {
                            this.saveUtdidToSettings((String)object);
                        }
                        this.saveUtdidToLocalStorage((String)object);
                        return this.mUtdid;
                    }
                    catch (Exception exception) {
                        exception.printStackTrace();
                    }
                    return null;
                }
            }
            return object;
        }
    }
}

