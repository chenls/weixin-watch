/*
 * Decompiled with CFR 0.151.
 */
package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONLexerBase;
import com.alibaba.fastjson.parser.JSONScanner;
import com.alibaba.fastjson.parser.deserializer.AbstractDateDeserializer;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.util.IOUtils;
import com.alibaba.fastjson.util.TypeUtils;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateCodec
extends AbstractDateDeserializer
implements ObjectSerializer,
ObjectDeserializer {
    public static final DateCodec instance = new DateCodec();

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    protected <T> T cast(DefaultJSONParser object, Type type, Object object2, Object object3) {
        block11: {
            block9: {
                block10: {
                    if (object3 == null) {
                        object2 = null;
                        return (T)object2;
                    }
                    object2 = object3;
                    if (object3 instanceof Date) return (T)object2;
                    if (object3 instanceof Number) {
                        return (T)new Date(((Number)object3).longValue());
                    }
                    if (!(object3 instanceof String)) throw new JSONException("parse error");
                    object2 = (String)object3;
                    if (((String)object2).length() == 0) {
                        return null;
                    }
                    object3 = new JSONScanner((String)object2);
                    if (!((JSONScanner)object3).scanISO8601DateIfMatch(false)) break block9;
                    object = ((JSONLexerBase)object3).getCalendar();
                    if (type != Calendar.class) break block10;
                    ((JSONLexerBase)object3).close();
                    return (T)object;
                }
                object = ((Calendar)object).getTime();
                return (T)object;
            }
            ((JSONLexerBase)object3).close();
            if (((String)object2).length() != ((DefaultJSONParser)object).getDateFomartPattern().length()) break block11;
            object = ((DefaultJSONParser)object).getDateFormat();
            try {}
            catch (ParseException parseException) {
                // empty catch block
                break block11;
            }
            object = ((DateFormat)object).parse((String)object2);
            return (T)object;
            finally {
                ((JSONLexerBase)object3).close();
            }
        }
        object = object2;
        if (!((String)object2).startsWith("/Date(")) return (T)new Date(Long.parseLong((String)object));
        object = object2;
        if (!((String)object2).endsWith(")/")) return (T)new Date(Long.parseLong((String)object));
        object = ((String)object2).substring(6, ((String)object2).length() - 2);
        return (T)new Date(Long.parseLong((String)object));
    }

    @Override
    public int getFastMatchToken() {
        return 2;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void write(JSONSerializer object, Object object2, Object object3, Type object4, int n2) throws IOException {
        SerializeWriter serializeWriter = ((JSONSerializer)object).out;
        if (object2 == null) {
            serializeWriter.writeNull();
            return;
        }
        object3 = object2 instanceof Date ? (Date)object2 : TypeUtils.castToDate(object2);
        if (serializeWriter.isEnabled(SerializerFeature.WriteDateUseDateFormat)) {
            object2 = object4 = ((JSONSerializer)object).getDateFormat();
            if (object4 == null) {
                object2 = new SimpleDateFormat(JSON.DEFFAULT_DATE_FORMAT, ((JSONSerializer)object).locale);
                ((DateFormat)object2).setTimeZone(((JSONSerializer)object).timeZone);
            }
            serializeWriter.writeString(((DateFormat)object2).format((Date)object3));
            return;
        }
        if (serializeWriter.isEnabled(SerializerFeature.WriteClassName) && object2.getClass() != object4) {
            if (object2.getClass() == Date.class) {
                serializeWriter.write("new Date(");
                serializeWriter.writeLong(((Date)object2).getTime());
                serializeWriter.write(41);
                return;
            }
            serializeWriter.write(123);
            serializeWriter.writeFieldName(JSON.DEFAULT_TYPE_KEY);
            ((JSONSerializer)object).write(object2.getClass().getName());
            serializeWriter.writeFieldValue(',', "val", ((Date)object2).getTime());
            serializeWriter.write(125);
            return;
        }
        long l2 = ((Date)object3).getTime();
        if (!serializeWriter.isEnabled(SerializerFeature.UseISO8601DateFormat)) {
            serializeWriter.writeLong(l2);
            return;
        }
        n2 = serializeWriter.isEnabled(SerializerFeature.UseSingleQuotes) ? 39 : 34;
        serializeWriter.write(n2);
        object2 = Calendar.getInstance(((JSONSerializer)object).timeZone, ((JSONSerializer)object).locale);
        ((Calendar)object2).setTimeInMillis(l2);
        int n3 = ((Calendar)object2).get(1);
        int n4 = ((Calendar)object2).get(2) + 1;
        int n5 = ((Calendar)object2).get(5);
        int n6 = ((Calendar)object2).get(11);
        int n7 = ((Calendar)object2).get(12);
        int n8 = ((Calendar)object2).get(13);
        int n9 = ((Calendar)object2).get(14);
        if (n9 != 0) {
            object = "0000-00-00T00:00:00.000".toCharArray();
            IOUtils.getChars(n9, 23, (char[])object);
            IOUtils.getChars(n8, 19, (char[])object);
            IOUtils.getChars(n7, 16, (char[])object);
            IOUtils.getChars(n6, 13, (char[])object);
            IOUtils.getChars(n5, 10, (char[])object);
            IOUtils.getChars(n4, 7, (char[])object);
            IOUtils.getChars(n3, 4, (char[])object);
        } else if (n8 == 0 && n7 == 0 && n6 == 0) {
            object = "0000-00-00".toCharArray();
            IOUtils.getChars(n5, 10, (char[])object);
            IOUtils.getChars(n4, 7, (char[])object);
            IOUtils.getChars(n3, 4, (char[])object);
        } else {
            object = "0000-00-00T00:00:00".toCharArray();
            IOUtils.getChars(n8, 19, (char[])object);
            IOUtils.getChars(n7, 16, (char[])object);
            IOUtils.getChars(n6, 13, (char[])object);
            IOUtils.getChars(n5, 10, (char[])object);
            IOUtils.getChars(n4, 7, (char[])object);
            IOUtils.getChars(n3, 4, (char[])object);
        }
        serializeWriter.write((char[])object);
        n3 = ((Calendar)object2).getTimeZone().getRawOffset() / 3600000;
        if (n3 == 0) {
            serializeWriter.write(90);
        } else {
            if (n3 > 0) {
                serializeWriter.append('+').append(String.format("%02d", n3));
            } else {
                serializeWriter.append('-').append(String.format("%02d", -n3));
            }
            serializeWriter.append(":00");
        }
        serializeWriter.write(n2);
    }
}

