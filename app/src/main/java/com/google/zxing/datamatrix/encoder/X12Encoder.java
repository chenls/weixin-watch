/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.datamatrix.encoder;

import com.google.zxing.datamatrix.encoder.C40Encoder;
import com.google.zxing.datamatrix.encoder.EncoderContext;
import com.google.zxing.datamatrix.encoder.HighLevelEncoder;

final class X12Encoder
extends C40Encoder {
    X12Encoder() {
    }

    @Override
    public void encode(EncoderContext encoderContext) {
        StringBuilder stringBuilder = new StringBuilder();
        while (encoderContext.hasMoreCharacters()) {
            char c2 = encoderContext.getCurrentChar();
            ++encoderContext.pos;
            this.encodeChar(c2, stringBuilder);
            if (stringBuilder.length() % 3 != 0) continue;
            X12Encoder.writeNextTriplet(encoderContext, stringBuilder);
            int n2 = HighLevelEncoder.lookAheadTest(encoderContext.getMessage(), encoderContext.pos, this.getEncodingMode());
            if (n2 == this.getEncodingMode()) continue;
            encoderContext.signalEncoderChange(n2);
            break;
        }
        this.handleEOD(encoderContext, stringBuilder);
    }

    @Override
    int encodeChar(char c2, StringBuilder stringBuilder) {
        if (c2 == '\r') {
            stringBuilder.append('\u0000');
            return 1;
        }
        if (c2 == '*') {
            stringBuilder.append('\u0001');
            return 1;
        }
        if (c2 == '>') {
            stringBuilder.append('\u0002');
            return 1;
        }
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
        HighLevelEncoder.illegalCharacter(c2);
        return 1;
    }

    @Override
    public int getEncodingMode() {
        return 3;
    }

    @Override
    void handleEOD(EncoderContext encoderContext, StringBuilder stringBuilder) {
        encoderContext.updateSymbolInfo();
        int n2 = encoderContext.getSymbolInfo().getDataCapacity() - encoderContext.getCodewordCount();
        int n3 = stringBuilder.length();
        encoderContext.pos -= n3;
        if (encoderContext.getRemainingCharacters() > 1 || n2 > 1 || encoderContext.getRemainingCharacters() != n2) {
            encoderContext.writeCodeword('\u00fe');
        }
        if (encoderContext.getNewEncoding() < 0) {
            encoderContext.signalEncoderChange(0);
        }
    }
}

