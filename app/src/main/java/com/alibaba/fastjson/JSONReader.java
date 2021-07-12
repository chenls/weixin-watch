/*
 * Decompiled with CFR 0.151.
 */
package com.alibaba.fastjson;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONStreamContext;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.parser.JSONReaderScanner;
import com.alibaba.fastjson.util.TypeUtils;
import java.io.Closeable;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

public class JSONReader
implements Closeable {
    private JSONStreamContext context;
    private final DefaultJSONParser parser;

    public JSONReader(DefaultJSONParser defaultJSONParser) {
        this.parser = defaultJSONParser;
    }

    public JSONReader(JSONLexer jSONLexer) {
        this(new DefaultJSONParser(jSONLexer));
    }

    public JSONReader(Reader reader) {
        this(reader, new Feature[0]);
    }

    public JSONReader(Reader reader, Feature ... featureArray) {
        this(new JSONReaderScanner(reader));
        int n2 = featureArray.length;
        for (int i2 = 0; i2 < n2; ++i2) {
            this.config(featureArray[i2], true);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void endStructure() {
        int n2;
        block8: {
            block7: {
                this.context = this.context.parent;
                if (this.context == null) break block7;
                int n3 = this.context.state;
                n2 = -1;
                switch (n3) {
                    case 1002: {
                        n2 = 1003;
                        break;
                    }
                    case 1004: {
                        n2 = 1005;
                        break;
                    }
                    case 1001: 
                    case 1003: {
                        n2 = 1002;
                        break;
                    }
                }
                if (n2 != -1) break block8;
            }
            return;
        }
        this.context.state = n2;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void readAfter() {
        int n2 = this.context.state;
        int n3 = -1;
        switch (n2) {
            default: {
                throw new JSONException("illegal state : " + n2);
            }
            case 1001: {
                n3 = 1002;
                break;
            }
            case 1002: {
                n3 = 1003;
                break;
            }
            case 1003: {
                n3 = 1002;
            }
            case 1005: {
                break;
            }
            case 1004: {
                n3 = 1005;
            }
        }
        if (n3 != -1) {
            this.context.state = n3;
        }
    }

    private void readBefore() {
        int n2 = this.context.state;
        switch (n2) {
            default: {
                throw new JSONException("illegal state : " + n2);
            }
            case 1002: {
                this.parser.accept(17);
            }
            case 1001: 
            case 1004: {
                return;
            }
            case 1003: {
                this.parser.accept(16, 18);
                return;
            }
            case 1005: 
        }
        this.parser.accept(16);
    }

    private void startStructure() {
        switch (this.context.state) {
            default: {
                throw new JSONException("illegal state : " + this.context.state);
            }
            case 1002: {
                this.parser.accept(17);
            }
            case 1001: 
            case 1004: {
                return;
            }
            case 1003: 
            case 1005: 
        }
        this.parser.accept(16);
    }

    @Override
    public void close() {
        this.parser.close();
    }

    public void config(Feature feature, boolean bl2) {
        this.parser.config(feature, bl2);
    }

    public void endArray() {
        this.parser.accept(15);
        this.endStructure();
    }

    public void endObject() {
        this.parser.accept(13);
        this.endStructure();
    }

    public Locale getLocal() {
        return this.parser.lexer.getLocale();
    }

    public TimeZone getTimzeZone() {
        return this.parser.lexer.getTimeZone();
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean hasNext() {
        block5: {
            if (this.context == null) {
                throw new JSONException("context is null");
            }
            int n2 = this.parser.lexer.token();
            int n3 = this.context.state;
            switch (n3) {
                default: {
                    throw new JSONException("illegal state : " + n3);
                }
                case 1004: 
                case 1005: {
                    if (n2 != 15) break;
                    return false;
                }
                case 1001: 
                case 1003: {
                    if (n2 == 13) break block5;
                }
            }
            return true;
        }
        return false;
    }

    public int peek() {
        return this.parser.lexer.token();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public Integer readInteger() {
        Object object;
        if (this.context == null) {
            object = this.parser.parse();
            return TypeUtils.castToInt(object);
        }
        this.readBefore();
        object = this.parser.parse();
        this.readAfter();
        return TypeUtils.castToInt(object);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public Long readLong() {
        Object object;
        if (this.context == null) {
            object = this.parser.parse();
            return TypeUtils.castToLong(object);
        }
        this.readBefore();
        object = this.parser.parse();
        this.readAfter();
        return TypeUtils.castToLong(object);
    }

    /*
     * Enabled aggressive block sorting
     */
    public Object readObject() {
        Object object;
        if (this.context == null) {
            return this.parser.parse();
        }
        this.readBefore();
        switch (this.context.state) {
            default: {
                object = this.parser.parse();
                break;
            }
            case 1001: 
            case 1003: {
                object = this.parser.parseKey();
            }
        }
        this.readAfter();
        return object;
    }

    public <T> T readObject(TypeReference<T> typeReference) {
        return this.readObject(typeReference.getType());
    }

    public <T> T readObject(Class<T> clazz) {
        if (this.context == null) {
            return this.parser.parseObject(clazz);
        }
        this.readBefore();
        clazz = this.parser.parseObject(clazz);
        this.readAfter();
        return (T)clazz;
    }

    public <T> T readObject(Type type) {
        if (this.context == null) {
            return this.parser.parseObject(type);
        }
        this.readBefore();
        type = this.parser.parseObject(type);
        this.readAfter();
        return (T)type;
    }

    public Object readObject(Map object) {
        if (this.context == null) {
            return this.parser.parseObject((Map)object);
        }
        this.readBefore();
        object = this.parser.parseObject((Map)object);
        this.readAfter();
        return object;
    }

    public void readObject(Object object) {
        if (this.context == null) {
            this.parser.parseObject(object);
            return;
        }
        this.readBefore();
        this.parser.parseObject(object);
        this.readAfter();
    }

    /*
     * Enabled aggressive block sorting
     */
    public String readString() {
        Object object;
        if (this.context == null) {
            object = this.parser.parse();
            return TypeUtils.castToString(object);
        }
        this.readBefore();
        JSONLexer jSONLexer = this.parser.lexer;
        if (this.context.state == 1001 && jSONLexer.token() == 18) {
            object = jSONLexer.stringVal();
            jSONLexer.nextToken();
        } else {
            object = this.parser.parse();
        }
        this.readAfter();
        return TypeUtils.castToString(object);
    }

    public void setLocale(Locale locale) {
        this.parser.lexer.setLocale(locale);
    }

    public void setTimzeZone(TimeZone timeZone) {
        this.parser.lexer.setTimeZone(timeZone);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void startArray() {
        if (this.context == null) {
            this.context = new JSONStreamContext(null, 1004);
        } else {
            this.startStructure();
            this.context = new JSONStreamContext(this.context, 1004);
        }
        this.parser.accept(14);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void startObject() {
        if (this.context == null) {
            this.context = new JSONStreamContext(null, 1001);
        } else {
            this.startStructure();
            this.context = new JSONStreamContext(this.context, 1001);
        }
        this.parser.accept(12, 18);
    }
}

