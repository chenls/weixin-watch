/*
 * Decompiled with CFR 0.151.
 */
package com.riyuxihe.weixinqingliao.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;

public class StreamUtil {
    public static int BUFFER_SIZE;
    public static int CHUNK_LENGTH;
    public static String SPEECH_TO_TEXT;

    static {
        SPEECH_TO_TEXT = "http://streaming.mobvoi.com/speech2text";
        BUFFER_SIZE = 2048;
        CHUNK_LENGTH = 2048;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void fromInStreamToOutStream(InputStream inputStream, OutputStream outputStream) throws IOException {
        if (outputStream == null) {
            throw new IllegalArgumentException("Output stream may not be null");
        }
        try {
            int n2;
            byte[] byArray = new byte[BUFFER_SIZE];
            while ((n2 = inputStream.read(byArray)) != -1) {
                outputStream.write(byArray, 0, n2);
            }
            return;
        }
        finally {
            inputStream.close();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static String readFromStream(InputStream inputStream, String object) throws IOException {
        try {
            int n2;
            object = new InputStreamReader(inputStream, (String)object);
            StringBuffer stringBuffer = new StringBuffer();
            char[] cArray = new char[1024];
            while ((n2 = ((Reader)object).read(cArray)) != -1) {
                stringBuffer.append(cArray, 0, n2);
            }
            object = stringBuffer.toString();
            return object;
        }
        finally {
            inputStream.close();
        }
    }
}

