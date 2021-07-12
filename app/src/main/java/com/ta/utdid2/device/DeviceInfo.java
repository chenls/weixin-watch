/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.ta.utdid2.device;

import android.content.Context;
import com.ta.utdid2.android.utils.PhoneInfoUtils;
import com.ta.utdid2.android.utils.StringUtils;
import com.ta.utdid2.device.Device;
import com.ta.utdid2.device.UTUtdid;
import java.util.zip.Adler32;

public class DeviceInfo {
    static final Object CREATE_DEVICE_METADATA_LOCK;
    static String HMAC_KEY;
    static final byte UTDID_VERSION_CODE = 1;
    private static Device mDevice;

    static {
        mDevice = null;
        HMAC_KEY = "d6fc3a4a06adbde89223bvefedc24fecde188aaa9161";
        CREATE_DEVICE_METADATA_LOCK = new Object();
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static Device _initDeviceMetadata(Context object) {
        if (object == null) return null;
        new Device();
        Object object2 = CREATE_DEVICE_METADATA_LOCK;
        synchronized (object2) {
            void var0_2;
            block7: {
                String string2;
                Object object3;
                try {
                    object3 = UTUtdid.instance(object).getValue();
                    if (StringUtils.isEmpty((String)object3)) break block7;
                    string2 = object3;
                    if (((String)object3).endsWith("\n")) {
                        string2 = ((String)object3).substring(0, ((String)object3).length() - 1);
                    }
                    object3 = new Device();
                }
                catch (Throwable throwable) {}
                try {
                    long l2 = System.currentTimeMillis();
                    String string3 = PhoneInfoUtils.getImei(object);
                    String string4 = PhoneInfoUtils.getImsi(object);
                    ((Device)object3).setDeviceId(string3);
                    ((Device)object3).setImei(string3);
                    ((Device)object3).setCreateTimestamp(l2);
                    ((Device)object3).setImsi(string4);
                    ((Device)object3).setUtdid(string2);
                    ((Device)object3).setCheckSum(DeviceInfo.getMetadataCheckSum((Device)object3));
                    return object3;
                }
                catch (Throwable throwable) {}
            }
            return null;
            throw var0_2;
        }
    }

    /*
     * WARNING - void declaration
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static Device getDevice(Context object) {
        synchronized (DeviceInfo.class) {
            Device device;
            void var0_2;
            block6: {
                if (mDevice == null) break block6;
                Device device2 = mDevice;
                return var0_2;
            }
            if (object == null) return var0_2;
            mDevice = device = DeviceInfo._initDeviceMetadata(object);
            return var0_2;
        }
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
}

