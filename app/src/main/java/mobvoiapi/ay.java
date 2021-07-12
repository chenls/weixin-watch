/*
 * Decompiled with CFR 0.151.
 */
package mobvoiapi;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ay {
    public static byte[] a(InputStream inputStream) throws IOException {
        int n2;
        if (inputStream instanceof ByteArrayInputStream) {
            int n3 = inputStream.available();
            byte[] byArray = new byte[n3];
            inputStream.read(byArray, 0, n3);
            return byArray;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] byArray = new byte[1024];
        while ((n2 = inputStream.read(byArray, 0, 1024)) != -1) {
            byteArrayOutputStream.write(byArray, 0, n2);
        }
        return byteArrayOutputStream.toByteArray();
    }
}

