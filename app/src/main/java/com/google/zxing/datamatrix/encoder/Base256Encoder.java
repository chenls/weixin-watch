/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.datamatrix.encoder;

import com.google.zxing.datamatrix.encoder.Encoder;
import com.google.zxing.datamatrix.encoder.EncoderContext;
import com.google.zxing.datamatrix.encoder.HighLevelEncoder;

final class Base256Encoder
implements Encoder {
    Base256Encoder() {
    }

    private static char randomize255State(char c2, int n2) {
        if ((c2 = (char)(c2 + (n2 * 149 % 255 + 1))) <= '\u00ff') {
            return c2;
        }
        return (char)(c2 - 256);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void encode(EncoderContext encoderContext) {
        int n2;
        block8: {
            int n3;
            StringBuilder stringBuilder;
            block6: {
                block7: {
                    stringBuilder = new StringBuilder();
                    stringBuilder.append('\u0000');
                    while (encoderContext.hasMoreCharacters()) {
                        stringBuilder.append(encoderContext.getCurrentChar());
                        ++encoderContext.pos;
                        n3 = HighLevelEncoder.lookAheadTest(encoderContext.getMessage(), encoderContext.pos, this.getEncodingMode());
                        if (n3 == this.getEncodingMode()) continue;
                        encoderContext.signalEncoderChange(n3);
                        break;
                    }
                    n2 = stringBuilder.length() - 1;
                    n3 = encoderContext.getCodewordCount() + n2 + 1;
                    encoderContext.updateSymbolInfo(n3);
                    n3 = encoderContext.getSymbolInfo().getDataCapacity() - n3 > 0 ? 1 : 0;
                    if (!encoderContext.hasMoreCharacters() && n3 == 0) break block6;
                    if (n2 > 249) break block7;
                    stringBuilder.setCharAt(0, (char)n2);
                    break block6;
                }
                if (n2 <= 249 || n2 > 1555) break block8;
                stringBuilder.setCharAt(0, (char)(n2 / 250 + 249));
                stringBuilder.insert(1, (char)(n2 % 250));
            }
            n3 = 0;
            n2 = stringBuilder.length();
            while (true) {
                if (n3 >= n2) {
                    return;
                }
                encoderContext.writeCodeword(Base256Encoder.randomize255State(stringBuilder.charAt(n3), encoderContext.getCodewordCount() + 1));
                ++n3;
            }
        }
        throw new IllegalStateException("Message length not in valid ranges: " + n2);
    }

    @Override
    public int getEncodingMode() {
        return 5;
    }
}

