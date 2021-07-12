/*
 * Decompiled with CFR 0.151.
 */
package com.alibaba.fastjson.parser;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.ParserConfig;

@Deprecated
public class DefaultExtJSONParser
extends DefaultJSONParser {
    public DefaultExtJSONParser(String string2) {
        this(string2, ParserConfig.getGlobalInstance());
    }

    public DefaultExtJSONParser(String string2, ParserConfig parserConfig) {
        super(string2, parserConfig);
    }

    public DefaultExtJSONParser(String string2, ParserConfig parserConfig, int n2) {
        super(string2, parserConfig, n2);
    }

    public DefaultExtJSONParser(char[] cArray, int n2, ParserConfig parserConfig, int n3) {
        super(cArray, n2, parserConfig, n3);
    }
}

