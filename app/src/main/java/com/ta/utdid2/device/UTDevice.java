/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.ta.utdid2.device;

import android.content.Context;
import com.ta.utdid2.android.utils.StringUtils;
import com.ta.utdid2.device.Device;
import com.ta.utdid2.device.DeviceInfo;

public class UTDevice {
    public static String getUtdid(Context object) {
        if ((object = DeviceInfo.getDevice((Context)object)) == null || StringUtils.isEmpty(((Device)object).getUtdid())) {
            return "ffffffffffffffffffffffff";
        }
        return ((Device)object).getUtdid();
    }
}

