/*
 * Decompiled with CFR 0.151.
 */
package com.alibaba.fastjson;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONStreamContext;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;
import java.io.Writer;

public class JSONWriter
implements Closeable,
Flushable {
    private JSONStreamContext context;
    private JSONSerializer serializer;
    private SerializeWriter writer;

    public JSONWriter(Writer writer) {
        this.writer = new SerializeWriter(writer);
        this.serializer = new JSONSerializer(this.writer);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void afterWriter() {
        int n2;
        block9: {
            block8: {
                int n3;
                if (this.context == null) break block8;
                int n4 = this.context.state;
                n2 = n3 = -1;
                switch (n4) {
                    default: {
                        n2 = n3;
                        break;
                    }
                    case 1002: {
                        n2 = 1003;
                        break;
                    }
                    case 1001: 
                    case 1003: {
                        n2 = 1002;
                    }
                    case 1005: {
                        break;
                    }
                    case 1004: {
                        n2 = 1005;
                    }
                }
                if (n2 != -1) break block9;
            }
            return;
        }
        this.context.state = n2;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private void beforeWrite() {
        if (this.context == null) {
            return;
        }
        switch (this.context.state) {
            case 1001: 
            case 1004: {
                return;
            }
            default: {
                return;
            }
            case 1002: {
                this.writer.write(58);
                return;
            }
            case 1003: {
                this.writer.write(44);
                return;
            }
            case 1005: 
        }
        this.writer.write(44);
    }

    private void beginStructure() {
        int n2 = this.context.state;
        switch (this.context.state) {
            default: {
                throw new JSONException("illegal state : " + n2);
            }
            case 1002: {
                this.writer.write(58);
            }
            case 1001: 
            case 1004: {
                return;
            }
            case 1005: 
        }
        this.writer.write(44);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void endStructure() {
        int n2;
        block9: {
            block8: {
                int n3;
                this.context = this.context.parent;
                if (this.context == null) break block8;
                n2 = n3 = -1;
                switch (this.context.state) {
                    default: {
                        n2 = n3;
                        break;
                    }
                    case 1002: {
                        n2 = 1003;
                        break;
                    }
                    case 1004: {
                        n2 = 1005;
                    }
                    case 1003: 
                    case 1005: {
                        break;
                    }
                    case 1001: {
                        n2 = 1002;
                    }
                }
                if (n2 != -1) break block9;
            }
            return;
        }
        this.context.state = n2;
    }

    @Override
    public void close() throws IOException {
        this.writer.close();
    }

    public void config(SerializerFeature serializerFeature, boolean bl2) {
        this.writer.config(serializerFeature, bl2);
    }

    public void endArray() {
        this.writer.write(93);
        this.endStructure();
    }

    public void endObject() {
        this.writer.write(125);
        this.endStructure();
    }

    @Override
    public void flush() throws IOException {
        this.writer.flush();
    }

    public void startArray() {
        if (this.context != null) {
            this.beginStructure();
        }
        this.context = new JSONStreamContext(this.context, 1004);
        this.writer.write(91);
    }

    public void startObject() {
        if (this.context != null) {
            this.beginStructure();
        }
        this.context = new JSONStreamContext(this.context, 1001);
        this.writer.write(123);
    }

    @Deprecated
    public void writeEndArray() {
        this.endArray();
    }

    @Deprecated
    public void writeEndObject() {
        this.endObject();
    }

    public void writeKey(String string2) {
        this.writeObject(string2);
    }

    public void writeObject(Object object) {
        this.beforeWrite();
        this.serializer.write(object);
        this.afterWriter();
    }

    public void writeObject(String string2) {
        this.beforeWrite();
        this.serializer.write(string2);
        this.afterWriter();
    }

    @Deprecated
    public void writeStartArray() {
        this.startArray();
    }

    @Deprecated
    public void writeStartObject() {
        this.startObject();
    }

    public void writeValue(Object object) {
        this.writeObject(object);
    }
}

