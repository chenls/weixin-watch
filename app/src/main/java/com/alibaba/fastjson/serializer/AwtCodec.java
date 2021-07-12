/*
 * Decompiled with CFR 0.151.
 */
package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.IOException;
import java.lang.reflect.Type;

public class AwtCodec
implements ObjectSerializer,
ObjectDeserializer {
    public static final AwtCodec instance = new AwtCodec();

    public static boolean support(Class<?> clazz) {
        return clazz == Point.class || clazz == Rectangle.class || clazz == Font.class || clazz == Color.class;
    }

    @Override
    public <T> T deserialze(DefaultJSONParser defaultJSONParser, Type type, Object object) {
        object = defaultJSONParser.lexer;
        if (object.token() == 8) {
            object.nextToken(16);
            return null;
        }
        if (object.token() != 12 && object.token() != 16) {
            throw new JSONException("syntax error");
        }
        object.nextToken();
        if (type == Point.class) {
            return (T)this.parsePoint(defaultJSONParser);
        }
        if (type == Rectangle.class) {
            return (T)this.parseRectangle(defaultJSONParser);
        }
        if (type == Color.class) {
            return (T)this.parseColor(defaultJSONParser);
        }
        if (type == Font.class) {
            return (T)this.parseFont(defaultJSONParser);
        }
        throw new JSONException("not support awt class : " + type);
    }

    @Override
    public int getFastMatchToken() {
        return 12;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected Color parseColor(DefaultJSONParser object) {
        object = ((DefaultJSONParser)object).lexer;
        int n2 = 0;
        int n3 = 0;
        int n4 = 0;
        int n5 = 0;
        while (true) {
            int n6;
            int n7;
            int n8;
            if (object.token() == 13) {
                object.nextToken();
                return new Color(n2, n3, n4, n5);
            }
            if (object.token() != 4) {
                throw new JSONException("syntax error");
            }
            String string2 = object.stringVal();
            object.nextTokenWithColon(2);
            if (object.token() != 2) {
                throw new JSONException("syntax error");
            }
            int n9 = object.intValue();
            object.nextToken();
            if (string2.equalsIgnoreCase("r")) {
                n8 = n9;
                n7 = n3;
                n6 = n4;
                n9 = n5;
            } else if (string2.equalsIgnoreCase("g")) {
                n7 = n9;
                n9 = n5;
                n6 = n4;
                n8 = n2;
            } else if (string2.equalsIgnoreCase("b")) {
                n6 = n9;
                n9 = n5;
                n7 = n3;
                n8 = n2;
            } else {
                if (!string2.equalsIgnoreCase("alpha")) {
                    throw new JSONException("syntax error, " + string2);
                }
                n6 = n4;
                n7 = n3;
                n8 = n2;
            }
            n5 = n9;
            n4 = n6;
            n3 = n7;
            n2 = n8;
            if (object.token() != 16) continue;
            object.nextToken(4);
            n5 = n9;
            n4 = n6;
            n3 = n7;
            n2 = n8;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    protected Font parseFont(DefaultJSONParser object) {
        JSONLexer jSONLexer = ((DefaultJSONParser)object).lexer;
        int n2 = 0;
        int n3 = 0;
        object = null;
        while (true) {
            int n4;
            int n5;
            if (jSONLexer.token() == 13) {
                jSONLexer.nextToken();
                return new Font((String)object, n3, n2);
            }
            if (jSONLexer.token() != 4) {
                throw new JSONException("syntax error");
            }
            Object object2 = jSONLexer.stringVal();
            jSONLexer.nextTokenWithColon(2);
            if (((String)object2).equalsIgnoreCase("name")) {
                if (jSONLexer.token() != 4) {
                    throw new JSONException("syntax error");
                }
                object2 = jSONLexer.stringVal();
                jSONLexer.nextToken();
                n5 = n3;
                n4 = n2;
            } else if (((String)object2).equalsIgnoreCase("style")) {
                if (jSONLexer.token() != 2) {
                    throw new JSONException("syntax error");
                }
                n5 = jSONLexer.intValue();
                jSONLexer.nextToken();
                object2 = object;
                n4 = n2;
            } else {
                if (!((String)object2).equalsIgnoreCase("size")) {
                    throw new JSONException("syntax error, " + (String)object2);
                }
                if (jSONLexer.token() != 2) {
                    throw new JSONException("syntax error");
                }
                n4 = jSONLexer.intValue();
                jSONLexer.nextToken();
                object2 = object;
                n5 = n3;
            }
            object = object2;
            n2 = n4;
            n3 = n5;
            if (jSONLexer.token() != 16) continue;
            jSONLexer.nextToken(4);
            object = object2;
            n2 = n4;
            n3 = n5;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    protected Point parsePoint(DefaultJSONParser defaultJSONParser) {
        JSONLexer jSONLexer = defaultJSONParser.lexer;
        int n2 = 0;
        int n3 = 0;
        while (true) {
            int n4;
            if (jSONLexer.token() == 13) {
                jSONLexer.nextToken();
                return new Point(n2, n3);
            }
            if (jSONLexer.token() != 4) {
                throw new JSONException("syntax error");
            }
            String string2 = jSONLexer.stringVal();
            if (JSON.DEFAULT_TYPE_KEY.equals(string2)) {
                defaultJSONParser.acceptType("java.awt.Point");
                continue;
            }
            jSONLexer.nextTokenWithColon(2);
            if (jSONLexer.token() != 2) {
                throw new JSONException("syntax error : " + jSONLexer.tokenName());
            }
            int n5 = jSONLexer.intValue();
            jSONLexer.nextToken();
            if (string2.equalsIgnoreCase("x")) {
                n4 = n3;
            } else {
                if (!string2.equalsIgnoreCase("y")) {
                    throw new JSONException("syntax error, " + string2);
                }
                n4 = n5;
                n5 = n2;
            }
            n2 = n5;
            n3 = n4;
            if (jSONLexer.token() != 16) continue;
            jSONLexer.nextToken(4);
            n2 = n5;
            n3 = n4;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    protected Rectangle parseRectangle(DefaultJSONParser object) {
        object = ((DefaultJSONParser)object).lexer;
        int n2 = 0;
        int n3 = 0;
        int n4 = 0;
        int n5 = 0;
        while (true) {
            int n6;
            int n7;
            int n8;
            if (object.token() == 13) {
                object.nextToken();
                return new Rectangle(n2, n3, n4, n5);
            }
            if (object.token() != 4) {
                throw new JSONException("syntax error");
            }
            String string2 = object.stringVal();
            object.nextTokenWithColon(2);
            if (object.token() != 2) {
                throw new JSONException("syntax error");
            }
            int n9 = object.intValue();
            object.nextToken();
            if (string2.equalsIgnoreCase("x")) {
                n8 = n3;
                n7 = n9;
                n6 = n4;
                n9 = n5;
            } else if (string2.equalsIgnoreCase("y")) {
                n8 = n9;
                n9 = n5;
                n6 = n4;
                n7 = n2;
            } else if (string2.equalsIgnoreCase("width")) {
                n6 = n9;
                n9 = n5;
                n7 = n2;
                n8 = n3;
            } else {
                if (!string2.equalsIgnoreCase("height")) {
                    throw new JSONException("syntax error, " + string2);
                }
                n6 = n4;
                n7 = n2;
                n8 = n3;
            }
            n5 = n9;
            n4 = n6;
            n2 = n7;
            n3 = n8;
            if (object.token() != 16) continue;
            object.nextToken(4);
            n5 = n9;
            n4 = n6;
            n2 = n7;
            n3 = n8;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void write(JSONSerializer object, Object object2, Object object3, Type type, int n2) throws IOException {
        object = ((JSONSerializer)object).out;
        if (object2 == null) {
            ((SerializeWriter)object).writeNull();
            return;
        }
        if (object2 instanceof Point) {
            object2 = (Point)object2;
            ((SerializeWriter)object).writeFieldValue(this.writeClassName((SerializeWriter)object, Point.class, '{'), "x", ((Point)object2).getX());
            ((SerializeWriter)object).writeFieldValue(',', "y", ((Point)object2).getY());
        } else if (object2 instanceof Font) {
            object2 = (Font)object2;
            ((SerializeWriter)object).writeFieldValue(this.writeClassName((SerializeWriter)object, Font.class, '{'), "name", ((Font)object2).getName());
            ((SerializeWriter)object).writeFieldValue(',', "style", ((Font)object2).getStyle());
            ((SerializeWriter)object).writeFieldValue(',', "size", ((Font)object2).getSize());
        } else if (object2 instanceof Rectangle) {
            object2 = (Rectangle)object2;
            ((SerializeWriter)object).writeFieldValue(this.writeClassName((SerializeWriter)object, Rectangle.class, '{'), "x", ((Rectangle)object2).getX());
            ((SerializeWriter)object).writeFieldValue(',', "y", ((Rectangle)object2).getY());
            ((SerializeWriter)object).writeFieldValue(',', "width", ((Rectangle)object2).getWidth());
            ((SerializeWriter)object).writeFieldValue(',', "height", ((Rectangle)object2).getHeight());
        } else {
            if (!(object2 instanceof Color)) {
                throw new JSONException("not support awt class : " + object2.getClass().getName());
            }
            object2 = (Color)object2;
            ((SerializeWriter)object).writeFieldValue(this.writeClassName((SerializeWriter)object, Color.class, '{'), "r", ((Color)object2).getRed());
            ((SerializeWriter)object).writeFieldValue(',', "g", ((Color)object2).getGreen());
            ((SerializeWriter)object).writeFieldValue(',', "b", ((Color)object2).getBlue());
            if (((Color)object2).getAlpha() > 0) {
                ((SerializeWriter)object).writeFieldValue(',', "alpha", ((Color)object2).getAlpha());
            }
        }
        ((SerializeWriter)object).write(125);
    }

    protected char writeClassName(SerializeWriter serializeWriter, Class<?> clazz, char c2) {
        if (serializeWriter.isEnabled(SerializerFeature.WriteClassName)) {
            serializeWriter.write(123);
            serializeWriter.writeFieldName(JSON.DEFAULT_TYPE_KEY);
            serializeWriter.writeString(clazz.getName());
            c2 = (char)44;
        }
        return c2;
    }
}

