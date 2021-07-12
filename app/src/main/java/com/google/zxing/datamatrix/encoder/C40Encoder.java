/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.datamatrix.encoder;

import com.google.zxing.datamatrix.encoder.Encoder;
import com.google.zxing.datamatrix.encoder.EncoderContext;
import com.google.zxing.datamatrix.encoder.HighLevelEncoder;

class C40Encoder
implements Encoder {
    C40Encoder() {
    }

    private int backtrackOneCharacter(EncoderContext encoderContext, StringBuilder stringBuilder, StringBuilder stringBuilder2, int n2) {
        int n3 = stringBuilder.length();
        stringBuilder.delete(n3 - n2, n3);
        --encoderContext.pos;
        n2 = this.encodeChar(encoderContext.getCurrentChar(), stringBuilder2);
        encoderContext.resetSymbolInfo();
        return n2;
    }

    private static String encodeToCodewords(CharSequence charSequence, int n2) {
        n2 = charSequence.charAt(n2) * 1600 + charSequence.charAt(n2 + 1) * 40 + charSequence.charAt(n2 + 2) + 1;
        return new String(new char[]{(char)(n2 / 256), (char)(n2 % 256)});
    }

    static void writeNextTriplet(EncoderContext encoderContext, StringBuilder stringBuilder) {
        encoderContext.writeCodewords(C40Encoder.encodeToCodewords(stringBuilder, 0));
        stringBuilder.delete(0, 3);
    }

    @Override
    public void encode(EncoderContext encoderContext) {
        StringBuilder stringBuilder = new StringBuilder();
        while (encoderContext.hasMoreCharacters()) {
            int n2;
            block4: {
                StringBuilder stringBuilder2;
                int n3;
                block5: {
                    int n4;
                    block6: {
                        char c2 = encoderContext.getCurrentChar();
                        ++encoderContext.pos;
                        n4 = this.encodeChar(c2, stringBuilder);
                        n2 = stringBuilder.length() / 3;
                        n2 = encoderContext.getCodewordCount() + n2 * 2;
                        encoderContext.updateSymbolInfo(n2);
                        n3 = encoderContext.getSymbolInfo().getDataCapacity() - n2;
                        if (encoderContext.hasMoreCharacters()) break block4;
                        stringBuilder2 = new StringBuilder();
                        n2 = n4;
                        if (stringBuilder.length() % 3 != 2) break block5;
                        if (n3 < 2) break block6;
                        n2 = n4;
                        if (n3 <= 2) break block5;
                    }
                    n2 = this.backtrackOneCharacter(encoderContext, stringBuilder, stringBuilder2, n4);
                }
                while (stringBuilder.length() % 3 == 1 && (n2 <= 3 && n3 != 1 || n2 > 3)) {
                    n2 = this.backtrackOneCharacter(encoderContext, stringBuilder, stringBuilder2, n2);
                }
                break;
            }
            if (stringBuilder.length() % 3 != 0 || (n2 = HighLevelEncoder.lookAheadTest(encoderContext.getMessage(), encoderContext.pos, this.getEncodingMode())) == this.getEncodingMode()) continue;
            encoderContext.signalEncoderChange(n2);
            break;
        }
        this.handleEOD(encoderContext, stringBuilder);
    }

    int encodeChar(char c2, StringBuilder stringBuilder) {
        if (c2 == ' ') {
            stringBuilder.append('\u0003');
            return 1;
        }
        if (c2 >= '0' && c2 <= '9') {
            stringBuilder.append((char)(c2 - 48 + 4));
            return 1;
        }
        if (c2 >= 'A' && c2 <= 'Z') {
            stringBuilder.append((char)(c2 - 65 + 14));
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
        if (c2 >= '`' && c2 <= '\u007f') {
            stringBuilder.append('\u0002');
            stringBuilder.append((char)(c2 - 96));
            return 2;
        }
        if (c2 >= '\u0080') {
            stringBuilder.append("\u0001\u001e");
            return 2 + this.encodeChar((char)(c2 - 128), stringBuilder);
        }
        throw new IllegalArgumentException("Illegal character: " + c2);
    }

    @Override
    public int getEncodingMode() {
        return 1;
    }

    /*
     * Enabled aggressive block sorting
     */
    void handleEOD(EncoderContext encoderContext, StringBuilder stringBuilder) {
        int n2 = stringBuilder.length() / 3;
        int n3 = stringBuilder.length() % 3;
        n2 = encoderContext.getCodewordCount() + n2 * 2;
        encoderContext.updateSymbolInfo(n2);
        n2 = encoderContext.getSymbolInfo().getDataCapacity() - n2;
        if (n3 == 2) {
            stringBuilder.append('\u0000');
            while (stringBuilder.length() >= 3) {
                C40Encoder.writeNextTriplet(encoderContext, stringBuilder);
            }
            if (encoderContext.hasMoreCharacters()) {
                encoderContext.writeCodeword('\u00fe');
            }
        } else if (n2 == 1 && n3 == 1) {
            while (stringBuilder.length() >= 3) {
                C40Encoder.writeNextTriplet(encoderContext, stringBuilder);
            }
            if (encoderContext.hasMoreCharacters()) {
                encoderContext.writeCodeword('\u00fe');
            }
            --encoderContext.pos;
        } else {
            if (n3 != 0) {
                throw new IllegalStateException("Unexpected case. Please report!");
            }
            while (stringBuilder.length() >= 3) {
                C40Encoder.writeNextTriplet(encoderContext, stringBuilder);
            }
            if (n2 > 0 || encoderContext.hasMoreCharacters()) {
                encoderContext.writeCodeword('\u00fe');
            }
        }
        encoderContext.signalEncoderChange(0);
    }
}

