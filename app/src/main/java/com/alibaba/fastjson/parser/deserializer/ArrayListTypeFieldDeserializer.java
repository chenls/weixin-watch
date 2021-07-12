/*
 * Decompiled with CFR 0.151.
 */
package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.JSONToken;
import com.alibaba.fastjson.parser.ParseContext;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.parser.deserializer.FieldDeserializer;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.util.FieldInfo;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class ArrayListTypeFieldDeserializer
extends FieldDeserializer {
    private ObjectDeserializer deserializer;
    private int itemFastMatchToken;
    private final Type itemType;

    public ArrayListTypeFieldDeserializer(ParserConfig parserConfig, Class<?> clazz, FieldInfo fieldInfo) {
        super(clazz, fieldInfo);
        if (fieldInfo.fieldType instanceof ParameterizedType) {
            this.itemType = ((ParameterizedType)fieldInfo.fieldType).getActualTypeArguments()[0];
            return;
        }
        this.itemType = Object.class;
    }

    @Override
    public int getFastMatchToken() {
        return 14;
    }

    /*
     * Handled impossible loop by adding 'first' condition
     * Enabled aggressive block sorting
     */
    public final void parseArray(DefaultJSONParser object, Type object2, Collection object3) {
        int n2;
        Type type = this.itemType;
        Object object4 = this.deserializer;
        Type type2 = type;
        Object object5 = object4;
        boolean bl2 = true;
        while (true) {
            block17: {
                block15: {
                    int n3;
                    ParameterizedType parameterizedType;
                    block16: {
                        int n4;
                        int n5;
                        block14: {
                            if (!bl2 || (bl2 = false)) break block14;
                            if (!(type instanceof TypeVariable)) break block15;
                            type2 = type;
                            object5 = object4;
                            if (!(object2 instanceof ParameterizedType)) break block15;
                            type2 = (TypeVariable)type;
                            parameterizedType = (ParameterizedType)object2;
                            object5 = null;
                            if (parameterizedType.getRawType() instanceof Class) {
                                object5 = (Class)parameterizedType.getRawType();
                            }
                            n3 = n5 = -1;
                            if (object5 == null) break block16;
                            n2 = 0;
                            n4 = ((Class)object5).getTypeParameters().length;
                        }
                        n3 = n5;
                        if (n2 >= n4) break block16;
                        if (!((Class)object5).getTypeParameters()[n2].getName().equals(type2.getName())) break block17;
                        n3 = n2;
                    }
                    type2 = type;
                    object5 = object4;
                    if (n3 != -1) {
                        type2 = type = parameterizedType.getActualTypeArguments()[n3];
                        object5 = object4;
                        if (!type.equals(this.itemType)) {
                            object5 = ((DefaultJSONParser)object).getConfig().getDeserializer(type);
                            type2 = type;
                        }
                    }
                }
                if ((object4 = ((DefaultJSONParser)object).lexer).token() == 14) break;
                object = object3 = "exepct '[', but " + JSONToken.name(object4.token());
                if (object2 != null) {
                    object = (String)object3 + ", type : " + object2;
                }
                throw new JSONException((String)object);
            }
            ++n2;
        }
        object2 = object5;
        if (object5 == null) {
            this.deserializer = object2 = ((DefaultJSONParser)object).getConfig().getDeserializer(type2);
            this.itemFastMatchToken = this.deserializer.getFastMatchToken();
        }
        object4.nextToken(this.itemFastMatchToken);
        n2 = 0;
        while (true) {
            if (object4.isEnabled(Feature.AllowArbitraryCommas)) {
                while (object4.token() == 16) {
                    object4.nextToken();
                }
            }
            if (object4.token() == 15) {
                object4.nextToken(16);
                return;
            }
            object3.add(object2.deserialze((DefaultJSONParser)object, type2, n2));
            ((DefaultJSONParser)object).checkListResolve((Collection)object3);
            if (object4.token() == 16) {
                object4.nextToken(this.itemFastMatchToken);
            }
            ++n2;
        }
    }

    @Override
    public void parseField(DefaultJSONParser defaultJSONParser, Object object, Type type, Map<String, Object> map) {
        if (defaultJSONParser.lexer.token() == 8) {
            this.setValue(object, null);
            return;
        }
        ArrayList arrayList = new ArrayList();
        ParseContext parseContext = defaultJSONParser.getContext();
        defaultJSONParser.setContext(parseContext, object, this.fieldInfo.name);
        this.parseArray(defaultJSONParser, type, arrayList);
        defaultJSONParser.setContext(parseContext);
        if (object == null) {
            map.put(this.fieldInfo.name, arrayList);
            return;
        }
        this.setValue(object, arrayList);
    }
}

