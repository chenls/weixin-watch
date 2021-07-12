/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.util.Log
 */
package com.mobvoi.android.semantic;

import android.util.Log;
import com.mobvoi.android.semantic.EntityTagValue;
import com.mobvoi.android.semantic.SemanticException;
import java.util.Arrays;
import java.util.Date;

public class DatetimeTagValue
extends EntityTagValue {
    public static final String TYPE_AFTER = "after";
    public static final String TYPE_BEFORE = "before";
    public static final String TYPE_PERIOD = "period";
    public static final String TYPE_POINT = "point";
    public long[] timePoint;
    public String type;

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    protected static DatetimeTagValue a(String object) throws SemanticException {
        int n2;
        int n3 = 0;
        if (object == null) {
            return null;
        }
        Object[] objectArray = ((String)object).split("::");
        if (objectArray.length != 2) {
            throw new SemanticException("Invalid format: cannot separate norm and raw data: " + (String)object);
        }
        DatetimeTagValue datetimeTagValue = new DatetimeTagValue();
        datetimeTagValue.rawData = objectArray[0];
        object = objectArray[1];
        if ((objectArray = ((String)object).split("\\|\\|")).length != 2 && objectArray.length != 3) {
            Log.d((String)"DatetimeTagValue", (String)("spFields: " + Arrays.toString(objectArray)));
            throw new SemanticException("Invalid datetime format: " + (String)object);
        }
        datetimeTagValue.type = objectArray[0];
        if (!(TYPE_POINT.equals(datetimeTagValue.type) || TYPE_PERIOD.equals(datetimeTagValue.type) || TYPE_AFTER.equals(datetimeTagValue.type) || TYPE_BEFORE.equals(datetimeTagValue.type))) {
            throw new SemanticException("Invalid datetime type: " + datetimeTagValue.type);
        }
        try {
            n2 = objectArray.length - 1;
            datetimeTagValue.timePoint = new long[n2];
        }
        catch (Exception exception) {
            throw new SemanticException("Invalid format: can't parse long-type time");
        }
        while (true) {
            object = datetimeTagValue;
            if (n3 >= n2) return object;
            datetimeTagValue.timePoint[n3] = Long.parseLong((String)objectArray[n3 + 1]);
            ++n3;
            continue;
            break;
        }
    }

    public Date getFirstDate() {
        if (this.timePoint.length >= 1) {
            return new Date(this.timePoint[0]);
        }
        return null;
    }

    public Date getSecondDate() {
        if (this.timePoint.length >= 2) {
            return new Date(this.timePoint[1]);
        }
        return null;
    }
}

