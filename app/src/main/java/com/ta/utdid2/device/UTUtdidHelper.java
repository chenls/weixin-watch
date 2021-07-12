/*
 * Decompiled with CFR 0.151.
 */
package com.ta.utdid2.device;

import com.ta.utdid2.android.utils.AESUtils;
import com.ta.utdid2.android.utils.Base64;
import java.util.Random;

public class UTUtdidHelper {
    private static Random random = new Random();
    private String mAESKey = Base64.encodeToString(this.mAESKey.getBytes(), 2);

    public static String generateRandomUTDID() {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i2 = 0; i2 < 24; ++i2) {
            stringBuffer.append((char)(random.nextInt(25) + 65));
        }
        return stringBuffer.toString();
    }

    public String dePack(String string2) {
        return AESUtils.decrypt(this.mAESKey, string2);
    }

    public String pack(byte[] object) {
        object = Base64.encodeToString(object, 2);
        return AESUtils.encrypt(this.mAESKey, (String)object);
    }

    public String packUtdidStr(String string2) {
        return AESUtils.encrypt(this.mAESKey, string2);
    }
}

