/*
 * Decompiled with CFR 0.151.
 */
package com.ta.utdid2.device;

import com.ta.utdid2.android.utils.AESUtils;
import com.ta.utdid2.android.utils.Base64;
import com.ta.utdid2.android.utils.StringUtils;

public class UTUtdidHelper2 {
    private String mAESKey = Base64.encodeToString(this.mAESKey.getBytes(), 0);

    public String dePack(String string2) {
        return AESUtils.decrypt(this.mAESKey, string2);
    }

    public String dePackWithBase64(String string2) {
        if (!StringUtils.isEmpty(string2 = AESUtils.decrypt(this.mAESKey, string2))) {
            try {
                string2 = new String(Base64.decode(string2, 0));
                return string2;
            }
            catch (IllegalArgumentException illegalArgumentException) {
                return null;
            }
        }
        return null;
    }
}

