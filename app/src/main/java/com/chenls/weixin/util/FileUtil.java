package com.chenls.weixin.util;

import java.io.File;

public class FileUtil {
    public static void createDir(String path) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdir();
        }
    }
}
