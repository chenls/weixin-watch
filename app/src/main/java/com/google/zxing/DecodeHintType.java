/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing;

import com.google.zxing.ResultPointCallback;
import java.util.List;

public enum DecodeHintType {
    OTHER(Object.class),
    PURE_BARCODE(Void.class),
    POSSIBLE_FORMATS(List.class),
    TRY_HARDER(Void.class),
    CHARACTER_SET(String.class),
    ALLOWED_LENGTHS(int[].class),
    ASSUME_CODE_39_CHECK_DIGIT(Void.class),
    ASSUME_GS1(Void.class),
    RETURN_CODABAR_START_END(Void.class),
    NEED_RESULT_POINT_CALLBACK(ResultPointCallback.class),
    ALLOWED_EAN_EXTENSIONS(int[].class);

    private final Class<?> valueType;

    private DecodeHintType(Class<?> clazz) {
        this.valueType = clazz;
    }

    public Class<?> getValueType() {
        return this.valueType;
    }
}

