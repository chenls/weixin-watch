/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.datamatrix.encoder;

import com.google.zxing.datamatrix.encoder.C40Encoder;
import com.google.zxing.datamatrix.encoder.HighLevelEncoder;

final class TextEncoder
extends C40Encoder {
    TextEncoder() {
    }

    @Override
    int encodeChar(char c2, StringBuilder stringBuilder) {
        if (c2 == ' ') {
            stringBuilder.append('\u0003');
            return 1;
        }
        if (c2 >= '0' && c2 <= '9') {
            stringBuilder.append((char)(c2 - 48 + 4));
            return 1;
        }
        if (c2 >= 'a' && c2 <= 'z') {
            stringBuilder.append((char)(c2 - 97 + 14));
            return 1;
        }
        if (c2 >= '\u0000' && c2 <= '\u001f') {
            stringBuilder.append('\u0000');
            stringBuilder.append(c2);
            return 2;
        }
        if (c2 >= '!' && c2 <= '/') {
            stringBuilder.append('\u0001');
            stringBuilder.append((char)(c2 - 33));
            return 2;
        }
        if (c2 >= ':' && c2 <= '@') {
            stringBuilder.append('\u0001');
            stringBuilder.append((char)(c2 - 58 + 15));
            return 2;
        }
        if (c2 >= '[' && c2 <= '_') {
            stringBuilder.append('\u0001');
            stringBuilder.append((char)(c2 - 91 + 22));
            return 2;
        }
        if (c2 == '`') {
            stringBuilder.append('\u0002');
            stringBuilder.append((char)(c2 - 96));
            return 2;
        }
        if (c2 >= 'A' && c2 <= 'Z') {
            stringBuilder.append('\u0002');
            stringBuilder.append((char)(c2 - 65 + 1));
            return 2;
        }
        if (c2 >= '{' && c2 <= '\u007f') {
            stringBuilder.append('\u0002');
            stringBuilder.append((char)(c2 - 123 + 27));
            return 2;
        }
        if (c2 >= '\u0080') {
            stringBuilder.append("\u0001\u001e");
            return 2 + this.encodeChar((char)(c2 - 128), stringBuilder);
        }
        HighLevelEncoder.illegalCharacter(c2);
        return -1;
    }

    @Override
    public int getEncodingMode() {
        return 2;
    }
}

