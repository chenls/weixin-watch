/*
 * Decompiled with CFR 0.151.
 */
package com.mobvoi.android.semantic;

import com.mobvoi.android.semantic.SemanticException;
import com.mobvoi.android.semantic.TagValue;

public class EntityTagValue
extends TagValue {
    public String normData;

    protected static EntityTagValue b(String object) throws SemanticException {
        if (object == null) {
            return null;
        }
        String[] stringArray = ((String)object).split("::");
        if (stringArray.length != 2) {
            throw new SemanticException("Invalid format for given entity type tag value: " + (String)object);
        }
        object = new EntityTagValue();
        ((EntityTagValue)object).rawData = stringArray[0];
        ((EntityTagValue)object).normData = stringArray[1];
        return object;
    }
}

