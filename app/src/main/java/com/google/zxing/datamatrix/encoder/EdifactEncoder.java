/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.datamatrix.encoder;

import com.google.zxing.datamatrix.encoder.Encoder;
import com.google.zxing.datamatrix.encoder.EncoderContext;
import com.google.zxing.datamatrix.encoder.HighLevelEncoder;

final class EdifactEncoder
implements Encoder {
    EdifactEncoder() {
    }

    private static void encodeChar(char c2, StringBuilder stringBuilder) {
        if (c2 >= ' ' && c2 <= '?') {
            stringBuilder.append(c2);
            return;
        }
        if (c2 >= '@' && c2 <= '^') {
            stringBuilder.append((char)(c2 - 64));
            return;
        }
        HighLevelEncoder.illegalCharacter(c2);
    }

    /*
     * Enabled aggressive block sorting
     */
    private static String encodeToCodewords(CharSequence charSequence, int n2) {
        char c2 = '\u0000';
        int n3 = charSequence.length() - n2;
        if (n3 == 0) {
            throw new IllegalStateException("StringBuilder must not be empty");
        }
        char c3 = charSequence.charAt(n2);
        char c4 = n3 >= 2 ? charSequence.charAt(n2 + 1) : (char)'\u0000';
        char c5 = n3 >= 3 ? charSequence.charAt(n2 + 2) : (char)'\u0000';
        if (n3 >= 4) {
            c2 = charSequence.charAt(n2 + 3);
        }
        n2 = (c3 << 18) + (c4 << 12) + (c5 << 6) + c2;
        char c6 = (char)(n2 >> 16 & 0xFF);
        char c7 = (char)(n2 >> 8 & 0xFF);
        char c8 = (char)(n2 & 0xFF);
        charSequence = new StringBuilder(3);
        ((StringBuilder)charSequence).append(c6);
        if (n3 >= 2) {
            ((StringBuilder)charSequence).append(c7);
        }
        if (n3 >= 3) {
            ((StringBuilder)charSequence).append(c8);
        }
        return ((StringBuilder)charSequence).toString();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static void handleEOD(EncoderContext encoderContext, CharSequence charSequence) {
        int n2 = 1;
        try {
            int n3;
            int n4 = charSequence.length();
            if (n4 == 0) {
                encoderContext.signalEncoderChange(0);
                return;
            }
            if (n4 == 1) {
                encoderContext.updateSymbolInfo();
                n3 = encoderContext.getSymbolInfo().getDataCapacity();
                int n5 = encoderContext.getCodewordCount();
                int n6 = encoderContext.getRemainingCharacters();
                if (n6 == 0 && n3 - n5 <= 2) {
                    encoderContext.signalEncoderChange(0);
                    return;
                }
            }
            if (n4 > 4) {
                throw new IllegalStateException("Count must not exceed 4");
            }
            n3 = n4 - 1;
            charSequence = EdifactEncoder.encodeToCodewords(charSequence, 0);
            n4 = !encoderContext.hasMoreCharacters() ? 1 : 0;
            n4 = n4 != 0 && n3 <= 2 ? n2 : 0;
            n2 = n4;
            if (n3 <= 2) {
                encoderContext.updateSymbolInfo(encoderContext.getCodewordCount() + n3);
                n2 = n4;
                if (encoderContext.getSymbolInfo().getDataCapacity() - encoderContext.getCodewordCount() >= 3) {
                    n2 = 0;
                    encoderContext.updateSymbolInfo(encoderContext.getCodewordCount() + ((String)charSequence).length());
                }
            }
            if (n2 != 0) {
                encoderContext.resetSymbolInfo();
                encoderContext.pos -= n3;
                return;
            } else {
                encoderContext.writeCodewords((String)charSequence);
            }
            return;
        }
        finally {
            encoderContext.signalEncoderChange(0);
        }
    }

    @Override
    public void encode(EncoderContext encoderContext) {
        StringBuilder stringBuilder = new StringBuilder();
        while (encoderContext.hasMoreCharacters()) {
            EdifactEncoder.encodeChar(encoderContext.getCurrentChar(), stringBuilder);
            ++encoderContext.pos;
            if (stringBuilder.length() < 4) continue;
            encoderContext.writeCodewords(EdifactEncoder.encodeToCodewords(stringBuilder, 0));
            stringBuilder.delete(0, 4);
            if (HighLevelEncoder.lookAheadTest(encoderContext.getMessage(), encoderContext.pos, this.getEncodingMode()) == this.getEncodingMode()) continue;
            encoderContext.signalEncoderChange(0);
            break;
        }
        stringBuilder.append('\u001f');
        EdifactEncoder.handleEOD(encoderContext, stringBuilder);
    }

    @Override
    public int getEncodingMode() {
        return 4;
    }
}

