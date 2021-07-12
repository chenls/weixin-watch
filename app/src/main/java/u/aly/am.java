/*
 * Decompiled with CFR 0.151.
 */
package u.aly;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class am {
    public static Object a(String object) {
        if (object == null || ((String)object).length() == 0) {
            return null;
        }
        try {
            object = new ObjectInputStream(new ByteArrayInputStream(am.b((String)object))).readObject();
            return object;
        }
        catch (Exception exception) {
            return null;
        }
    }

    public static String a(Serializable object) {
        if (object == null) {
            return "";
        }
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(object);
            objectOutputStream.close();
            object = am.a(byteArrayOutputStream.toByteArray());
            return object;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public static String a(byte[] byArray) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i2 = 0; i2 < byArray.length; ++i2) {
            stringBuffer.append((char)((byArray[i2] >> 4 & 0xF) + 97));
            stringBuffer.append((char)((byArray[i2] & 0xF) + 97));
        }
        return stringBuffer.toString();
    }

    public static byte[] b(String string2) {
        byte[] byArray = new byte[string2.length() / 2];
        for (int i2 = 0; i2 < string2.length(); i2 += 2) {
            char c2 = string2.charAt(i2);
            byArray[i2 / 2] = (byte)(c2 - 97 << 4);
            c2 = string2.charAt(i2 + 1);
            int n2 = i2 / 2;
            byArray[n2] = (byte)(c2 - 97 + byArray[n2]);
        }
        return byArray;
    }
}

