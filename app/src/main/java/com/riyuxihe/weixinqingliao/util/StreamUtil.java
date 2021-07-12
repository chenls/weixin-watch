package com.riyuxihe.weixinqingliao.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;

public class StreamUtil {
    public static int BUFFER_SIZE = 2048;
    public static int CHUNK_LENGTH = 2048;
    public static String SPEECH_TO_TEXT = "http://streaming.mobvoi.com/speech2text";

    public static void fromInStreamToOutStream(InputStream inStream, OutputStream outStream) throws IOException {
        if (outStream == null) {
            throw new IllegalArgumentException("Output stream may not be null");
        }
        try {
            byte[] buffer = new byte[BUFFER_SIZE];
            while (true) {
                int l = inStream.read(buffer);
                if (l != -1) {
                    outStream.write(buffer, 0, l);
                } else {
                    return;
                }
            }
        } finally {
            inStream.close();
        }
    }

    public static String readFromStream(InputStream inStream, String charset) throws IOException {
        try {
            Reader reader = new InputStreamReader(inStream, charset);
            StringBuffer buffer = new StringBuffer();
            char[] tmp = new char[1024];
            while (true) {
                int l = reader.read(tmp);
                if (l == -1) {
                    return buffer.toString();
                }
                buffer.append(tmp, 0, l);
            }
        } finally {
            inStream.close();
        }
    }
}
