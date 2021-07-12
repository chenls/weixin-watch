/*
 * Decompiled with CFR 0.151.
 */
package com.riyuxihe.weixinqingliao.util;

import java.io.File;

public class FileUtil {
    public static void createDir(String object) {
        if (!((File)(object = new File((String)object))).exists()) {
            ((File)object).mkdir();
        }
    }
}

