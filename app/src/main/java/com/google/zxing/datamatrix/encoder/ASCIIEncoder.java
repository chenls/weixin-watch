/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.datamatrix.encoder;

import com.google.zxing.datamatrix.encoder.Encoder;
import com.google.zxing.datamatrix.encoder.EncoderContext;
import com.google.zxing.datamatrix.encoder.HighLevelEncoder;

final class ASCIIEncoder
implements Encoder {
    ASCIIEncoder() {
    }

    private static char encodeASCIIDigits(char c2, char c3) {
        if (HighLevelEncoder.isDigit(c2) && HighLevelEncoder.isDigit(c3)) {
            return (char)((c2 - 48) * 10 + (c3 - 48) + 130);
        }
        throw new IllegalArgumentException("not digits: " + c2 + c3);
    }

    @Override
    public void encode(EncoderContext encoderContext) {
        if (HighLevelEncoder.determineConsecutiveDigitCount(encoderContext.getMessage(), encoderContext.pos) >= 2) {
            encoderContext.writeCodeword(ASCIIEncoder.encodeASCIIDigits(encoderContext.getMessage().charAt(encoderContext.pos), encoderContext.getMessage().charAt(encoderContext.pos + 1)));
            encoderContext.pos += 2;
            return;
        }
        char c2 = encoderContext.getCurrentChar();
        int n2 = HighLevelEncoder.lookAheadTest(encoderContext.getMessage(), encoderContext.pos, this.getEncodingMode());
        if (n2 != this.getEncodingMode()) {
            switch (n2) {
                default: {
                    throw new IllegalStateException("Illegal mode: " + n2);
                }
                case 5: {
                    encoderContext.writeCodeword('\u00e7');
                    encoderContext.signalEncoderChange(5);
                    return;
                }
                case 1: {
                    encoderContext.writeCodeword('\u00e6');
                    encoderContext.signalEncoderChange(1);
                    return;
                }
                case 3: {
                    encoderContext.writeCodeword('\u00ee');
                    encoderContext.signalEncoderChange(3);
                    return;
                }
                case 2: {
                    encoderContext.writeCodeword('\u00ef');
                    encoderContext.signalEncoderChange(2);
                    return;
                }
                case 4: 
            }
            encoderContext.writeCodeword('\u00f0');
            encoderContext.signalEncoderChange(4);
            return;
        }
        if (HighLevelEncoder.isExtendedASCII(c2)) {
            encoderContext.writeCodeword('\u00eb');
            encoderContext.writeCodeword((char)(c2 - 128 + 1));
            ++encoderContext.pos;
            return;
        }
        encoderContext.writeCodeword((char)(c2 + '\u0001'));
        ++encoderContext.pos;
    }

    @Override
    public int getEncodingMode() {
        return 0;
    }
}

