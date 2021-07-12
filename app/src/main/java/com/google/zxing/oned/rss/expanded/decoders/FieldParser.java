/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.oned.rss.expanded.decoders;

import com.google.zxing.NotFoundException;

final class FieldParser {
    private static final Object[][] FOUR_DIGIT_DATA_LENGTH;
    private static final Object[][] THREE_DIGIT_DATA_LENGTH;
    private static final Object[][] THREE_DIGIT_PLUS_DIGIT_DATA_LENGTH;
    private static final Object[][] TWO_DIGIT_DATA_LENGTH;
    private static final Object VARIABLE_LENGTH;

    static {
        VARIABLE_LENGTH = new Object();
        Object[] objectArray = new Object[]{"10", VARIABLE_LENGTH, 20};
        Object[] objectArray2 = new Object[]{"11", 6};
        Object[] objectArray3 = new Object[]{"12", 6};
        Object[] objectArray4 = new Object[]{"13", 6};
        Object[] objectArray5 = new Object[]{"20", 2};
        Object object = VARIABLE_LENGTH;
        Object[] objectArray6 = new Object[]{"22", VARIABLE_LENGTH, 29};
        Object object2 = VARIABLE_LENGTH;
        Object object3 = VARIABLE_LENGTH;
        Object[] objectArray7 = new Object[]{"90", VARIABLE_LENGTH, 30};
        Object[] objectArray8 = new Object[]{"91", VARIABLE_LENGTH, 30};
        Object object4 = VARIABLE_LENGTH;
        Object[] objectArray9 = new Object[]{"93", VARIABLE_LENGTH, 30};
        Object[] objectArray10 = new Object[]{"94", VARIABLE_LENGTH, 30};
        Object[] objectArray11 = new Object[]{"95", VARIABLE_LENGTH, 30};
        Object[] objectArray12 = new Object[]{"96", VARIABLE_LENGTH, 30};
        Object object5 = VARIABLE_LENGTH;
        Object object6 = VARIABLE_LENGTH;
        Object object7 = VARIABLE_LENGTH;
        TWO_DIGIT_DATA_LENGTH = new Object[][]{{"00", 18}, {"01", 14}, {"02", 14}, objectArray, objectArray2, objectArray3, objectArray4, {"15", 6}, {"17", 6}, objectArray5, {"21", object, 20}, objectArray6, {"30", object2, 8}, {"37", object3, 8}, objectArray7, objectArray8, {"92", object4, 30}, objectArray9, objectArray10, objectArray11, objectArray12, {"97", object5, 30}, {"98", object6, 30}, {"99", object7, 30}};
        object5 = new Object[]{"240", VARIABLE_LENGTH, 30};
        object6 = new Object[]{"241", VARIABLE_LENGTH, 30};
        object7 = new Object[]{"242", VARIABLE_LENGTH, 6};
        object = VARIABLE_LENGTH;
        objectArray = new Object[]{"251", VARIABLE_LENGTH, 30};
        objectArray2 = new Object[]{"253", VARIABLE_LENGTH, 17};
        objectArray3 = new Object[]{"254", VARIABLE_LENGTH, 20};
        objectArray4 = new Object[]{"400", VARIABLE_LENGTH, 30};
        object2 = VARIABLE_LENGTH;
        object3 = VARIABLE_LENGTH;
        objectArray5 = new Object[]{"412", 13};
        objectArray6 = new Object[]{"413", 13};
        object4 = VARIABLE_LENGTH;
        objectArray7 = new Object[]{"421", VARIABLE_LENGTH, 15};
        objectArray8 = new Object[]{"422", 3};
        objectArray9 = new Object[]{"423", VARIABLE_LENGTH, 15};
        objectArray10 = new Object[]{"426", 3};
        THREE_DIGIT_DATA_LENGTH = new Object[][]{object5, object6, object7, {"250", object, 30}, objectArray, objectArray2, objectArray3, objectArray4, {"401", object2, 30}, {"402", 17}, {"403", object3, 30}, {"410", 13}, {"411", 13}, objectArray5, objectArray6, {"414", 13}, {"420", object4, 20}, objectArray7, objectArray8, objectArray9, {"424", 3}, {"425", 3}, objectArray10};
        object = new Object[]{"310", 6};
        object2 = new Object[]{"311", 6};
        object3 = new Object[]{"312", 6};
        object4 = new Object[]{"313", 6};
        object5 = new Object[]{"314", 6};
        object6 = new Object[]{"315", 6};
        object7 = new Object[]{"316", 6};
        objectArray = new Object[]{"320", 6};
        objectArray2 = new Object[]{"321", 6};
        objectArray3 = new Object[]{"322", 6};
        objectArray4 = new Object[]{"323", 6};
        objectArray5 = new Object[]{"324", 6};
        objectArray6 = new Object[]{"326", 6};
        objectArray7 = new Object[]{"327", 6};
        objectArray8 = new Object[]{"328", 6};
        objectArray9 = new Object[]{"329", 6};
        objectArray10 = new Object[]{"330", 6};
        objectArray11 = new Object[]{"331", 6};
        objectArray12 = new Object[]{"332", 6};
        Object[] objectArray13 = new Object[]{"333", 6};
        Object[] objectArray14 = new Object[]{"334", 6};
        Object[] objectArray15 = new Object[]{"335", 6};
        Object[] objectArray16 = new Object[]{"336", 6};
        Object[] objectArray17 = new Object[]{"340", 6};
        Object[] objectArray18 = new Object[]{"341", 6};
        Object[] objectArray19 = new Object[]{"342", 6};
        Object[] objectArray20 = new Object[]{"343", 6};
        Object[] objectArray21 = new Object[]{"344", 6};
        Object[] objectArray22 = new Object[]{"345", 6};
        Object[] objectArray23 = new Object[]{"346", 6};
        Object[] objectArray24 = new Object[]{"347", 6};
        Object[] objectArray25 = new Object[]{"349", 6};
        Object[] objectArray26 = new Object[]{"350", 6};
        Object[] objectArray27 = new Object[]{"351", 6};
        Object[] objectArray28 = new Object[]{"352", 6};
        Object[] objectArray29 = new Object[]{"353", 6};
        Object[] objectArray30 = new Object[]{"354", 6};
        Object[] objectArray31 = new Object[]{"355", 6};
        Object[] objectArray32 = new Object[]{"356", 6};
        Object[] objectArray33 = new Object[]{"360", 6};
        Object[] objectArray34 = new Object[]{"361", 6};
        Object[] objectArray35 = new Object[]{"362", 6};
        Object[] objectArray36 = new Object[]{"363", 6};
        Object[] objectArray37 = new Object[]{"364", 6};
        Object[] objectArray38 = new Object[]{"365", 6};
        Object[] objectArray39 = new Object[]{"366", 6};
        Object[] objectArray40 = new Object[]{"367", 6};
        Object[] objectArray41 = new Object[]{"368", 6};
        Object[] objectArray42 = new Object[]{"390", VARIABLE_LENGTH, 15};
        Object[] objectArray43 = new Object[]{"391", VARIABLE_LENGTH, 18};
        Object[] objectArray44 = new Object[]{"392", VARIABLE_LENGTH, 15};
        Object[] objectArray45 = new Object[]{"393", VARIABLE_LENGTH, 18};
        Object[] objectArray46 = new Object[]{"703", VARIABLE_LENGTH, 30};
        THREE_DIGIT_PLUS_DIGIT_DATA_LENGTH = new Object[][]{object, object2, object3, object4, object5, object6, object7, objectArray, objectArray2, objectArray3, objectArray4, objectArray5, {"325", 6}, objectArray6, objectArray7, objectArray8, objectArray9, objectArray10, objectArray11, objectArray12, objectArray13, objectArray14, objectArray15, objectArray16, objectArray17, objectArray18, objectArray19, objectArray20, objectArray21, objectArray22, objectArray23, objectArray24, {"348", 6}, objectArray25, objectArray26, objectArray27, objectArray28, objectArray29, objectArray30, objectArray31, objectArray32, {"357", 6}, objectArray33, objectArray34, objectArray35, objectArray36, objectArray37, objectArray38, objectArray39, objectArray40, objectArray41, {"369", 6}, objectArray42, objectArray43, objectArray44, objectArray45, objectArray46};
        objectArray = new Object[]{"7001", 13};
        object = VARIABLE_LENGTH;
        objectArray2 = new Object[]{"7003", 10};
        objectArray3 = new Object[]{"8001", 14};
        object2 = VARIABLE_LENGTH;
        objectArray4 = new Object[]{"8003", VARIABLE_LENGTH, 30};
        object3 = VARIABLE_LENGTH;
        objectArray5 = new Object[]{"8005", 6};
        objectArray6 = new Object[]{"8006", 18};
        object4 = VARIABLE_LENGTH;
        object5 = VARIABLE_LENGTH;
        object6 = VARIABLE_LENGTH;
        objectArray7 = new Object[]{"8100", 6};
        objectArray8 = new Object[]{"8102", 2};
        object7 = VARIABLE_LENGTH;
        objectArray9 = new Object[]{"8200", VARIABLE_LENGTH, 70};
        FOUR_DIGIT_DATA_LENGTH = new Object[][]{objectArray, {"7002", object, 30}, objectArray2, objectArray3, {"8002", object2, 20}, objectArray4, {"8004", object3, 30}, objectArray5, objectArray6, {"8007", object4, 30}, {"8008", object5, 12}, {"8018", 18}, {"8020", object6, 25}, objectArray7, {"8101", 10}, objectArray8, {"8110", object7, 70}, objectArray9};
    }

    private FieldParser() {
    }

    static String parseFieldsInGeneralPurpose(String string2) throws NotFoundException {
        if (string2.isEmpty()) {
            return null;
        }
        if (string2.length() < 2) {
            throw NotFoundException.getNotFoundInstance();
        }
        String string3 = string2.substring(0, 2);
        for (Object[] objectArray : TWO_DIGIT_DATA_LENGTH) {
            if (!objectArray[0].equals(string3)) continue;
            if (objectArray[1] == VARIABLE_LENGTH) {
                return FieldParser.processVariableAI(2, (Integer)objectArray[2], string2);
            }
            return FieldParser.processFixedAI(2, (Integer)objectArray[1], string2);
        }
        if (string2.length() < 3) {
            throw NotFoundException.getNotFoundInstance();
        }
        string3 = string2.substring(0, 3);
        for (Object[] objectArray : THREE_DIGIT_DATA_LENGTH) {
            if (!objectArray[0].equals(string3)) continue;
            if (objectArray[1] == VARIABLE_LENGTH) {
                return FieldParser.processVariableAI(3, (Integer)objectArray[2], string2);
            }
            return FieldParser.processFixedAI(3, (Integer)objectArray[1], string2);
        }
        for (Object[] objectArray : THREE_DIGIT_PLUS_DIGIT_DATA_LENGTH) {
            if (!objectArray[0].equals(string3)) continue;
            if (objectArray[1] == VARIABLE_LENGTH) {
                return FieldParser.processVariableAI(4, (Integer)objectArray[2], string2);
            }
            return FieldParser.processFixedAI(4, (Integer)objectArray[1], string2);
        }
        if (string2.length() < 4) {
            throw NotFoundException.getNotFoundInstance();
        }
        string3 = string2.substring(0, 4);
        for (Object[] objectArray : FOUR_DIGIT_DATA_LENGTH) {
            if (!objectArray[0].equals(string3)) continue;
            if (objectArray[1] == VARIABLE_LENGTH) {
                return FieldParser.processVariableAI(4, (Integer)objectArray[2], string2);
            }
            return FieldParser.processFixedAI(4, (Integer)objectArray[1], string2);
        }
        throw NotFoundException.getNotFoundInstance();
    }

    private static String processFixedAI(int n2, int n3, String string2) throws NotFoundException {
        if (string2.length() < n2) {
            throw NotFoundException.getNotFoundInstance();
        }
        String string3 = string2.substring(0, n2);
        if (string2.length() < n2 + n3) {
            throw NotFoundException.getNotFoundInstance();
        }
        String string4 = string2.substring(n2, n2 + n3);
        string2 = string2.substring(n2 + n3);
        string3 = '(' + string3 + ')' + string4;
        if ((string2 = FieldParser.parseFieldsInGeneralPurpose(string2)) == null) {
            return string3;
        }
        return string3 + string2;
    }

    /*
     * Enabled aggressive block sorting
     */
    private static String processVariableAI(int n2, int n3, String string2) throws NotFoundException {
        String string3 = string2.substring(0, n2);
        n3 = string2.length() < n2 + n3 ? string2.length() : n2 + n3;
        String string4 = string2.substring(n2, n3);
        string2 = string2.substring(n3);
        string3 = '(' + string3 + ')' + string4;
        if ((string2 = FieldParser.parseFieldsInGeneralPurpose(string2)) == null) {
            return string3;
        }
        return string3 + string2;
    }
}

