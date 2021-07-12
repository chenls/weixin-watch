/*
 * Decompiled with CFR 0.151.
 */
package com.alibaba.fastjson.parser;

import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.SymbolTable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Locale;
import java.util.TimeZone;

public interface JSONLexer {
    public static final int ARRAY = 2;
    public static final int END = 4;
    public static final char EOI = '\u001a';
    public static final int NOT_MATCH = -1;
    public static final int NOT_MATCH_NAME = -2;
    public static final int OBJECT = 1;
    public static final int UNKNOWN = 0;
    public static final int VALUE = 3;
    public static final int VALUE_NULL = 5;

    public byte[] bytesValue();

    public void close();

    public void config(Feature var1, boolean var2);

    public Number decimalValue(boolean var1);

    public BigDecimal decimalValue();

    public float floatValue();

    public char getCurrent();

    public Locale getLocale();

    public TimeZone getTimeZone();

    public String info();

    public int intValue();

    public Number integerValue();

    public boolean isBlankInput();

    public boolean isEnabled(int var1);

    public boolean isEnabled(Feature var1);

    public boolean isRef();

    public long longValue();

    public char next();

    public void nextToken();

    public void nextToken(int var1);

    public void nextTokenWithColon();

    public void nextTokenWithColon(int var1);

    public String numberString();

    public int pos();

    public void resetStringPosition();

    public boolean scanBoolean(char var1);

    public double scanDouble(char var1);

    public Enum<?> scanEnum(Class<?> var1, SymbolTable var2, char var3);

    public float scanFloat(char var1);

    public int scanInt(char var1);

    public long scanLong(char var1);

    public void scanNumber();

    public String scanString(char var1);

    public void scanString();

    public void scanStringArray(Collection<String> var1, char var2);

    public String scanSymbol(SymbolTable var1);

    public String scanSymbol(SymbolTable var1, char var2);

    public String scanSymbolUnQuoted(SymbolTable var1);

    public String scanSymbolWithSeperator(SymbolTable var1, char var2);

    public void setLocale(Locale var1);

    public void setTimeZone(TimeZone var1);

    public void skipWhitespace();

    public String stringVal();

    public int token();

    public String tokenName();
}

