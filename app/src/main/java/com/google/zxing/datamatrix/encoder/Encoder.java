/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.datamatrix.encoder;

import com.google.zxing.datamatrix.encoder.EncoderContext;

interface Encoder {
    public void encode(EncoderContext var1);

    public int getEncodingMode();
}

