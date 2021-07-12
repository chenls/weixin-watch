/*
 * Decompiled with CFR 0.151.
 */
package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;
import com.alibaba.fastjson.asm.ClassWriter;
import com.alibaba.fastjson.asm.FieldWriter;
import com.alibaba.fastjson.asm.Label;
import com.alibaba.fastjson.asm.MethodVisitor;
import com.alibaba.fastjson.asm.MethodWriter;
import com.alibaba.fastjson.asm.Opcodes;
import com.alibaba.fastjson.asm.Type;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.BeanContext;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.JavaBeanSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerialContext;
import com.alibaba.fastjson.serializer.SerializeFilterable;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.util.ASMClassLoader;
import com.alibaba.fastjson.util.ASMUtils;
import com.alibaba.fastjson.util.FieldInfo;
import com.alibaba.fastjson.util.TypeUtils;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class ASMSerializerFactory
implements Opcodes {
    static final String JSONSerializer = ASMUtils.type(JSONSerializer.class);
    static final String JavaBeanSerializer;
    static final String JavaBeanSerializer_desc;
    static final String ObjectSerializer;
    static final String ObjectSerializer_desc;
    static final String SerialContext_desc;
    static final String SerializeFilterable_desc;
    static final String SerializeWriter;
    static final String SerializeWriter_desc;
    protected final ASMClassLoader classLoader = new ASMClassLoader();
    private final AtomicLong seed = new AtomicLong();

    static {
        ObjectSerializer = ASMUtils.type(ObjectSerializer.class);
        ObjectSerializer_desc = "L" + ObjectSerializer + ";";
        SerializeWriter = ASMUtils.type(SerializeWriter.class);
        SerializeWriter_desc = "L" + SerializeWriter + ";";
        JavaBeanSerializer = ASMUtils.type(JavaBeanSerializer.class);
        JavaBeanSerializer_desc = "L" + ASMUtils.type(JavaBeanSerializer.class) + ";";
        SerialContext_desc = ASMUtils.desc(SerialContext.class);
        SerializeFilterable_desc = ASMUtils.desc(SerializeFilterable.class);
    }

    private void _after(MethodVisitor methodVisitor, Context context) {
        methodVisitor.visitVarInsn(25, 1);
        methodVisitor.visitVarInsn(25, 0);
        methodVisitor.visitVarInsn(25, 2);
        methodVisitor.visitVarInsn(21, context.var("seperator"));
        methodVisitor.visitMethodInsn(182, JSONSerializer, "writeAfter", "(" + SerializeFilterable_desc + "Ljava/lang/Object;C)C");
        methodVisitor.visitVarInsn(54, context.var("seperator"));
    }

    /*
     * Enabled aggressive block sorting
     */
    private void _apply(MethodVisitor methodVisitor, FieldInfo clazz, Context context) {
        clazz = ((FieldInfo)clazz).fieldClass;
        methodVisitor.visitVarInsn(25, 1);
        methodVisitor.visitVarInsn(25, 0);
        methodVisitor.visitVarInsn(25, 2);
        methodVisitor.visitVarInsn(25, Context.fieldName);
        if (clazz == Byte.TYPE) {
            methodVisitor.visitVarInsn(21, context.var("byte"));
            methodVisitor.visitMethodInsn(184, "java/lang/Byte", "valueOf", "(B)Ljava/lang/Byte;");
        } else if (clazz == Short.TYPE) {
            methodVisitor.visitVarInsn(21, context.var("short"));
            methodVisitor.visitMethodInsn(184, "java/lang/Short", "valueOf", "(S)Ljava/lang/Short;");
        } else if (clazz == Integer.TYPE) {
            methodVisitor.visitVarInsn(21, context.var("int"));
            methodVisitor.visitMethodInsn(184, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;");
        } else if (clazz == Character.TYPE) {
            methodVisitor.visitVarInsn(21, context.var("char"));
            methodVisitor.visitMethodInsn(184, "java/lang/Character", "valueOf", "(C)Ljava/lang/Character;");
        } else if (clazz == Long.TYPE) {
            methodVisitor.visitVarInsn(22, context.var("long", 2));
            methodVisitor.visitMethodInsn(184, "java/lang/Long", "valueOf", "(J)Ljava/lang/Long;");
        } else if (clazz == Float.TYPE) {
            methodVisitor.visitVarInsn(23, context.var("float"));
            methodVisitor.visitMethodInsn(184, "java/lang/Float", "valueOf", "(F)Ljava/lang/Float;");
        } else if (clazz == Double.TYPE) {
            methodVisitor.visitVarInsn(24, context.var("double", 2));
            methodVisitor.visitMethodInsn(184, "java/lang/Double", "valueOf", "(D)Ljava/lang/Double;");
        } else if (clazz == Boolean.TYPE) {
            methodVisitor.visitVarInsn(21, context.var("boolean"));
            methodVisitor.visitMethodInsn(184, "java/lang/Boolean", "valueOf", "(Z)Ljava/lang/Boolean;");
        } else if (clazz == BigDecimal.class) {
            methodVisitor.visitVarInsn(25, context.var("decimal"));
        } else if (clazz == String.class) {
            methodVisitor.visitVarInsn(25, context.var("string"));
        } else if (clazz.isEnum()) {
            methodVisitor.visitVarInsn(25, context.var("enum"));
        } else if (List.class.isAssignableFrom(clazz)) {
            methodVisitor.visitVarInsn(25, context.var("list"));
        } else {
            methodVisitor.visitVarInsn(25, context.var("object"));
        }
        methodVisitor.visitMethodInsn(182, JSONSerializer, "apply", "(" + SerializeFilterable_desc + "Ljava/lang/Object;Ljava/lang/String;Ljava/lang/Object;)Z");
    }

    private void _before(MethodVisitor methodVisitor, Context context) {
        methodVisitor.visitVarInsn(25, 1);
        methodVisitor.visitVarInsn(25, 0);
        methodVisitor.visitVarInsn(25, 2);
        methodVisitor.visitVarInsn(21, context.var("seperator"));
        methodVisitor.visitMethodInsn(182, JSONSerializer, "writeBefore", "(" + SerializeFilterable_desc + "Ljava/lang/Object;C)C");
        methodVisitor.visitVarInsn(54, context.var("seperator"));
    }

    private void _decimal(Class<?> object, MethodVisitor methodVisitor, FieldInfo fieldInfo, Context context) {
        object = new Label();
        this._nameApply(methodVisitor, fieldInfo, context, (Label)object);
        this._get(methodVisitor, context, fieldInfo);
        methodVisitor.visitVarInsn(58, context.var("decimal"));
        this._filters(methodVisitor, fieldInfo, context, (Label)object);
        Label label = new Label();
        Label label2 = new Label();
        Label label3 = new Label();
        methodVisitor.visitLabel(label);
        methodVisitor.visitVarInsn(25, context.var("decimal"));
        methodVisitor.visitJumpInsn(199, label2);
        this._if_write_null(methodVisitor, fieldInfo, context);
        methodVisitor.visitJumpInsn(167, label3);
        methodVisitor.visitLabel(label2);
        methodVisitor.visitVarInsn(25, context.var("out"));
        methodVisitor.visitVarInsn(21, context.var("seperator"));
        methodVisitor.visitVarInsn(25, Context.fieldName);
        methodVisitor.visitVarInsn(25, context.var("decimal"));
        methodVisitor.visitMethodInsn(182, SerializeWriter, "writeFieldValue", "(CLjava/lang/String;Ljava/math/BigDecimal;)V");
        this._seperator(methodVisitor, context);
        methodVisitor.visitJumpInsn(167, label3);
        methodVisitor.visitLabel(label3);
        methodVisitor.visitLabel((Label)object);
    }

    private void _double(Class<?> object, MethodVisitor methodVisitor, FieldInfo fieldInfo, Context context) {
        object = new Label();
        this._nameApply(methodVisitor, fieldInfo, context, (Label)object);
        this._get(methodVisitor, context, fieldInfo);
        methodVisitor.visitVarInsn(57, context.var("double", 2));
        this._filters(methodVisitor, fieldInfo, context, (Label)object);
        methodVisitor.visitVarInsn(25, context.var("out"));
        methodVisitor.visitVarInsn(21, context.var("seperator"));
        methodVisitor.visitVarInsn(25, Context.fieldName);
        methodVisitor.visitVarInsn(24, context.var("double", 2));
        methodVisitor.visitMethodInsn(182, SerializeWriter, "writeFieldValue", "(CLjava/lang/String;D)V");
        this._seperator(methodVisitor, context);
        methodVisitor.visitLabel((Label)object);
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    private void _enum(Class<?> object, MethodVisitor methodVisitor, FieldInfo fieldInfo, Context context) {
        void var4_8;
        void var2_6;
        void var3_7;
        boolean bl2 = false;
        boolean bl3 = false;
        JSONField jSONField = var3_7.getAnnotation();
        if (jSONField != null) {
            SerializerFeature[] serializerFeatureArray = jSONField.serialzeFeatures();
            int n2 = serializerFeatureArray.length;
            int n3 = 0;
            while (true) {
                bl2 = bl3;
                if (n3 >= n2) break;
                if (serializerFeatureArray[n3] == SerializerFeature.WriteEnumUsingToString) {
                    bl3 = true;
                }
                ++n3;
            }
        }
        Label label = new Label();
        Label label2 = new Label();
        Label label3 = new Label();
        this._nameApply((MethodVisitor)var2_6, (FieldInfo)var3_7, (Context)var4_8, label3);
        this._get((MethodVisitor)var2_6, (Context)var4_8, (FieldInfo)var3_7);
        var2_6.visitTypeInsn(192, "java/lang/Enum");
        var2_6.visitVarInsn(58, var4_8.var("enum"));
        this._filters((MethodVisitor)var2_6, (FieldInfo)var3_7, (Context)var4_8, label3);
        var2_6.visitVarInsn(25, var4_8.var("enum"));
        var2_6.visitJumpInsn(199, label);
        this._if_write_null((MethodVisitor)var2_6, (FieldInfo)var3_7, (Context)var4_8);
        var2_6.visitJumpInsn(167, label2);
        var2_6.visitLabel(label);
        var2_6.visitVarInsn(25, var4_8.var("out"));
        var2_6.visitVarInsn(21, var4_8.var("seperator"));
        var2_6.visitVarInsn(25, Context.fieldName);
        var2_6.visitVarInsn(25, var4_8.var("enum"));
        if (bl2) {
            var2_6.visitMethodInsn(182, "java/lang/Object", "toString", "()Ljava/lang/String;");
            var2_6.visitMethodInsn(182, SerializeWriter, "writeFieldValue", "(CLjava/lang/String;Ljava/lang/String;)V");
        } else if (((Context)var4_8).writeDirect) {
            var2_6.visitMethodInsn(182, "java/lang/Enum", "name", "()Ljava/lang/String;");
            var2_6.visitMethodInsn(182, SerializeWriter, "writeFieldValueStringWithDoubleQuote", "(CLjava/lang/String;Ljava/lang/String;)V");
        } else {
            var2_6.visitMethodInsn(182, SerializeWriter, "writeFieldValue", "(CLjava/lang/String;Ljava/lang/Enum;)V");
        }
        this._seperator((MethodVisitor)var2_6, (Context)var4_8);
        var2_6.visitLabel(label2);
        var2_6.visitLabel(label3);
    }

    private void _filters(MethodVisitor methodVisitor, FieldInfo fieldInfo, Context context, Label label) {
        if (fieldInfo.field != null && Modifier.isTransient(fieldInfo.field.getModifiers())) {
            methodVisitor.visitVarInsn(25, context.var("out"));
            methodVisitor.visitLdcInsn(SerializerFeature.SkipTransientField.mask);
            methodVisitor.visitMethodInsn(182, SerializeWriter, "isEnabled", "(I)Z");
            methodVisitor.visitJumpInsn(154, label);
        }
        this._notWriteDefault(methodVisitor, fieldInfo, context, label);
        if (context.writeDirect) {
            return;
        }
        this._apply(methodVisitor, fieldInfo, context);
        methodVisitor.visitJumpInsn(153, label);
        this._processKey(methodVisitor, fieldInfo, context);
        this._processValue(methodVisitor, fieldInfo, context, label);
    }

    private void _float(Class<?> object, MethodVisitor methodVisitor, FieldInfo fieldInfo, Context context) {
        object = new Label();
        this._nameApply(methodVisitor, fieldInfo, context, (Label)object);
        this._get(methodVisitor, context, fieldInfo);
        methodVisitor.visitVarInsn(56, context.var("float"));
        this._filters(methodVisitor, fieldInfo, context, (Label)object);
        methodVisitor.visitVarInsn(25, context.var("out"));
        methodVisitor.visitVarInsn(21, context.var("seperator"));
        methodVisitor.visitVarInsn(25, Context.fieldName);
        methodVisitor.visitVarInsn(23, context.var("float"));
        methodVisitor.visitMethodInsn(182, SerializeWriter, "writeFieldValue", "(CLjava/lang/String;F)V");
        this._seperator(methodVisitor, context);
        methodVisitor.visitLabel((Label)object);
    }

    private void _get(MethodVisitor methodVisitor, Context context, FieldInfo fieldInfo) {
        Method method = fieldInfo.method;
        if (method != null) {
            methodVisitor.visitVarInsn(25, context.var("entity"));
            methodVisitor.visitMethodInsn(182, ASMUtils.type(method.getDeclaringClass()), method.getName(), ASMUtils.desc(method));
            return;
        }
        methodVisitor.visitVarInsn(25, context.var("entity"));
        methodVisitor.visitFieldInsn(180, ASMUtils.type(fieldInfo.declaringClass), fieldInfo.field.getName(), ASMUtils.desc(fieldInfo.fieldClass));
    }

    private void _getFieldSer(Context context, MethodVisitor methodVisitor, FieldInfo fieldInfo) {
        Label label = new Label();
        methodVisitor.visitVarInsn(25, 0);
        methodVisitor.visitFieldInsn(180, context.className, fieldInfo.name + "_asm_ser_", ObjectSerializer_desc);
        methodVisitor.visitJumpInsn(199, label);
        methodVisitor.visitVarInsn(25, 0);
        methodVisitor.visitVarInsn(25, 1);
        methodVisitor.visitLdcInsn(Type.getType(ASMUtils.desc(fieldInfo.fieldClass)));
        methodVisitor.visitMethodInsn(182, JSONSerializer, "getObjectWriter", "(Ljava/lang/Class;)" + ObjectSerializer_desc);
        methodVisitor.visitFieldInsn(181, context.className, fieldInfo.name + "_asm_ser_", ObjectSerializer_desc);
        methodVisitor.visitLabel(label);
        methodVisitor.visitVarInsn(25, 0);
        methodVisitor.visitFieldInsn(180, context.className, fieldInfo.name + "_asm_ser_", ObjectSerializer_desc);
    }

    private void _getListFieldItemSer(Context context, MethodVisitor methodVisitor, FieldInfo fieldInfo, Class<?> clazz) {
        Label label = new Label();
        methodVisitor.visitVarInsn(25, 0);
        methodVisitor.visitFieldInsn(180, context.className, fieldInfo.name + "_asm_list_item_ser_", ObjectSerializer_desc);
        methodVisitor.visitJumpInsn(199, label);
        methodVisitor.visitVarInsn(25, 0);
        methodVisitor.visitVarInsn(25, 1);
        methodVisitor.visitLdcInsn(Type.getType(ASMUtils.desc(clazz)));
        methodVisitor.visitMethodInsn(182, JSONSerializer, "getObjectWriter", "(Ljava/lang/Class;)" + ObjectSerializer_desc);
        methodVisitor.visitFieldInsn(181, context.className, fieldInfo.name + "_asm_list_item_ser_", ObjectSerializer_desc);
        methodVisitor.visitLabel(label);
        methodVisitor.visitVarInsn(25, 0);
        methodVisitor.visitFieldInsn(180, context.className, fieldInfo.name + "_asm_list_item_ser_", ObjectSerializer_desc);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void _if_write_null(MethodVisitor methodVisitor, FieldInfo object, Context context) {
        Class<?> clazz = ((FieldInfo)object).fieldClass;
        Label label = new Label();
        Label label2 = new Label();
        Label label3 = new Label();
        Label label4 = new Label();
        methodVisitor.visitLabel(label);
        object = ((FieldInfo)object).getAnnotation();
        int n2 = 0;
        if (object != null) {
            n2 = SerializerFeature.of(object.serialzeFeatures());
        }
        if ((SerializerFeature.WriteMapNullValue.mask & n2) == 0) {
            methodVisitor.visitVarInsn(25, context.var("out"));
            methodVisitor.visitLdcInsn(SerializerFeature.WriteMapNullValue.mask);
            methodVisitor.visitMethodInsn(182, SerializeWriter, "isEnabled", "(I)Z");
            methodVisitor.visitJumpInsn(153, label2);
        }
        methodVisitor.visitLabel(label3);
        methodVisitor.visitVarInsn(25, context.var("out"));
        methodVisitor.visitVarInsn(21, context.var("seperator"));
        methodVisitor.visitMethodInsn(182, SerializeWriter, "write", "(I)V");
        this._writeFieldName(methodVisitor, context);
        methodVisitor.visitVarInsn(25, context.var("out"));
        methodVisitor.visitLdcInsn(n2);
        if (clazz == String.class || clazz == Character.class) {
            methodVisitor.visitLdcInsn(SerializerFeature.WriteNullStringAsEmpty.mask);
        } else if (Number.class.isAssignableFrom(clazz)) {
            methodVisitor.visitLdcInsn(SerializerFeature.WriteNullNumberAsZero.mask);
        } else if (clazz == Boolean.class) {
            methodVisitor.visitLdcInsn(SerializerFeature.WriteNullBooleanAsFalse.mask);
        } else if (Collection.class.isAssignableFrom(clazz) || clazz.isArray()) {
            methodVisitor.visitLdcInsn(SerializerFeature.WriteNullListAsEmpty.mask);
        } else {
            methodVisitor.visitLdcInsn(0);
        }
        methodVisitor.visitMethodInsn(182, SerializeWriter, "writeNull", "(II)V");
        this._seperator(methodVisitor, context);
        methodVisitor.visitJumpInsn(167, label4);
        methodVisitor.visitLabel(label2);
        methodVisitor.visitLabel(label4);
    }

    private void _int(Class<?> object, MethodVisitor methodVisitor, FieldInfo fieldInfo, Context context, int n2, char c2) {
        object = new Label();
        this._nameApply(methodVisitor, fieldInfo, context, (Label)object);
        this._get(methodVisitor, context, fieldInfo);
        methodVisitor.visitVarInsn(54, n2);
        this._filters(methodVisitor, fieldInfo, context, (Label)object);
        methodVisitor.visitVarInsn(25, context.var("out"));
        methodVisitor.visitVarInsn(21, context.var("seperator"));
        methodVisitor.visitVarInsn(25, Context.fieldName);
        methodVisitor.visitVarInsn(21, n2);
        methodVisitor.visitMethodInsn(182, SerializeWriter, "writeFieldValue", "(CLjava/lang/String;" + c2 + ")V");
        this._seperator(methodVisitor, context);
        methodVisitor.visitLabel((Label)object);
    }

    private void _labelApply(MethodVisitor methodVisitor, FieldInfo fieldInfo, Context context, Label label) {
        methodVisitor.visitVarInsn(25, 1);
        methodVisitor.visitVarInsn(25, 0);
        methodVisitor.visitLdcInsn(fieldInfo.label);
        methodVisitor.visitMethodInsn(182, JSONSerializer, "applyLabel", "(" + SerializeFilterable_desc + "Ljava/lang/String;)Z");
        methodVisitor.visitJumpInsn(153, label);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void _list(Class<?> object, MethodVisitor methodVisitor, FieldInfo fieldInfo, Context context) {
        Object object2;
        Object object3;
        block18: {
            block17: {
                object = fieldInfo.fieldType;
                object3 = object instanceof Class ? Object.class : ((ParameterizedType)object).getActualTypeArguments()[0];
                object = null;
                if (object3 instanceof Class) {
                    object = (Class)object3;
                }
                if (object == Object.class) break block17;
                object2 = object;
                if (object != Serializable.class) break block18;
            }
            object2 = null;
        }
        Label label = new Label();
        object = new Label();
        Label label2 = new Label();
        this._nameApply(methodVisitor, fieldInfo, context, label);
        this._get(methodVisitor, context, fieldInfo);
        methodVisitor.visitTypeInsn(192, "java/util/List");
        methodVisitor.visitVarInsn(58, context.var("list"));
        this._filters(methodVisitor, fieldInfo, context, label);
        methodVisitor.visitVarInsn(25, context.var("list"));
        methodVisitor.visitJumpInsn(199, (Label)object);
        this._if_write_null(methodVisitor, fieldInfo, context);
        methodVisitor.visitJumpInsn(167, label2);
        methodVisitor.visitLabel((Label)object);
        methodVisitor.visitVarInsn(25, context.var("out"));
        methodVisitor.visitVarInsn(21, context.var("seperator"));
        methodVisitor.visitMethodInsn(182, SerializeWriter, "write", "(I)V");
        this._writeFieldName(methodVisitor, context);
        methodVisitor.visitVarInsn(25, context.var("list"));
        methodVisitor.visitMethodInsn(185, "java/util/List", "size", "()I");
        methodVisitor.visitVarInsn(54, context.var("size"));
        object = new Label();
        Label label3 = new Label();
        methodVisitor.visitVarInsn(21, context.var("size"));
        methodVisitor.visitInsn(3);
        methodVisitor.visitJumpInsn(160, (Label)object);
        methodVisitor.visitVarInsn(25, context.var("out"));
        methodVisitor.visitLdcInsn("[]");
        methodVisitor.visitMethodInsn(182, SerializeWriter, "write", "(Ljava/lang/String;)V");
        methodVisitor.visitJumpInsn(167, label3);
        methodVisitor.visitLabel((Label)object);
        if (!context.nonContext) {
            methodVisitor.visitVarInsn(25, 1);
            methodVisitor.visitVarInsn(25, context.var("list"));
            methodVisitor.visitVarInsn(25, Context.fieldName);
            methodVisitor.visitMethodInsn(182, JSONSerializer, "setContext", "(Ljava/lang/Object;Ljava/lang/Object;)V");
        }
        if (object3 == String.class && context.writeDirect) {
            methodVisitor.visitVarInsn(25, context.var("out"));
            methodVisitor.visitVarInsn(25, context.var("list"));
            methodVisitor.visitMethodInsn(182, SerializeWriter, "write", "(Ljava/util/List;)V");
        } else {
            methodVisitor.visitVarInsn(25, context.var("out"));
            methodVisitor.visitVarInsn(16, 91);
            methodVisitor.visitMethodInsn(182, SerializeWriter, "write", "(I)V");
            Label label4 = new Label();
            object = new Label();
            Label label5 = new Label();
            methodVisitor.visitInsn(3);
            methodVisitor.visitVarInsn(54, context.var("i"));
            methodVisitor.visitLabel(label4);
            methodVisitor.visitVarInsn(21, context.var("i"));
            methodVisitor.visitVarInsn(21, context.var("size"));
            methodVisitor.visitJumpInsn(162, label5);
            methodVisitor.visitVarInsn(21, context.var("i"));
            methodVisitor.visitJumpInsn(153, (Label)object);
            methodVisitor.visitVarInsn(25, context.var("out"));
            methodVisitor.visitVarInsn(16, 44);
            methodVisitor.visitMethodInsn(182, SerializeWriter, "write", "(I)V");
            methodVisitor.visitLabel((Label)object);
            methodVisitor.visitVarInsn(25, context.var("list"));
            methodVisitor.visitVarInsn(21, context.var("i"));
            methodVisitor.visitMethodInsn(185, "java/util/List", "get", "(I)Ljava/lang/Object;");
            methodVisitor.visitVarInsn(58, context.var("list_item"));
            Label label6 = new Label();
            object = new Label();
            methodVisitor.visitVarInsn(25, context.var("list_item"));
            methodVisitor.visitJumpInsn(199, (Label)object);
            methodVisitor.visitVarInsn(25, context.var("out"));
            methodVisitor.visitMethodInsn(182, SerializeWriter, "writeNull", "()V");
            methodVisitor.visitJumpInsn(167, label6);
            methodVisitor.visitLabel((Label)object);
            Label label7 = new Label();
            Label label8 = new Label();
            if (object2 != null && Modifier.isPublic(((Class)object2).getModifiers())) {
                methodVisitor.visitVarInsn(25, context.var("list_item"));
                methodVisitor.visitMethodInsn(182, "java/lang/Object", "getClass", "()Ljava/lang/Class;");
                methodVisitor.visitLdcInsn(Type.getType(ASMUtils.desc(object2)));
                methodVisitor.visitJumpInsn(166, label8);
                this._getListFieldItemSer(context, methodVisitor, fieldInfo, (Class<?>)object2);
                methodVisitor.visitVarInsn(58, context.var("list_item_desc"));
                Label label9 = new Label();
                Label label10 = new Label();
                if (context.writeDirect) {
                    object = context.nonContext && context.writeDirect ? "writeDirectNonContext" : "write";
                    methodVisitor.visitVarInsn(25, context.var("list_item_desc"));
                    methodVisitor.visitTypeInsn(193, JavaBeanSerializer);
                    methodVisitor.visitJumpInsn(153, label9);
                    methodVisitor.visitVarInsn(25, context.var("list_item_desc"));
                    methodVisitor.visitTypeInsn(192, JavaBeanSerializer);
                    methodVisitor.visitVarInsn(25, 1);
                    methodVisitor.visitVarInsn(25, context.var("list_item"));
                    if (context.nonContext) {
                        methodVisitor.visitInsn(1);
                    } else {
                        methodVisitor.visitVarInsn(21, context.var("i"));
                        methodVisitor.visitMethodInsn(184, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;");
                    }
                    methodVisitor.visitLdcInsn(Type.getType(ASMUtils.desc(object2)));
                    methodVisitor.visitLdcInsn(fieldInfo.serialzeFeatures);
                    methodVisitor.visitMethodInsn(182, JavaBeanSerializer, (String)object, "(L" + JSONSerializer + ";Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V");
                    methodVisitor.visitJumpInsn(167, label10);
                    methodVisitor.visitLabel(label9);
                }
                methodVisitor.visitVarInsn(25, context.var("list_item_desc"));
                methodVisitor.visitVarInsn(25, 1);
                methodVisitor.visitVarInsn(25, context.var("list_item"));
                if (context.nonContext) {
                    methodVisitor.visitInsn(1);
                } else {
                    methodVisitor.visitVarInsn(21, context.var("i"));
                    methodVisitor.visitMethodInsn(184, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;");
                }
                methodVisitor.visitLdcInsn(Type.getType(ASMUtils.desc(object2)));
                methodVisitor.visitLdcInsn(fieldInfo.serialzeFeatures);
                methodVisitor.visitMethodInsn(185, ObjectSerializer, "write", "(L" + JSONSerializer + ";Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V");
                methodVisitor.visitLabel(label10);
                methodVisitor.visitJumpInsn(167, label7);
            }
            methodVisitor.visitLabel(label8);
            methodVisitor.visitVarInsn(25, 1);
            methodVisitor.visitVarInsn(25, context.var("list_item"));
            if (context.nonContext) {
                methodVisitor.visitInsn(1);
            } else {
                methodVisitor.visitVarInsn(21, context.var("i"));
                methodVisitor.visitMethodInsn(184, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;");
            }
            if (object2 != null && Modifier.isPublic(((Class)object2).getModifiers())) {
                methodVisitor.visitLdcInsn(Type.getType(ASMUtils.desc((Class)object3)));
                methodVisitor.visitLdcInsn(fieldInfo.serialzeFeatures);
                methodVisitor.visitMethodInsn(182, JSONSerializer, "writeWithFieldName", "(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V");
            } else {
                methodVisitor.visitMethodInsn(182, JSONSerializer, "writeWithFieldName", "(Ljava/lang/Object;Ljava/lang/Object;)V");
            }
            methodVisitor.visitLabel(label7);
            methodVisitor.visitLabel(label6);
            methodVisitor.visitIincInsn(context.var("i"), 1);
            methodVisitor.visitJumpInsn(167, label4);
            methodVisitor.visitLabel(label5);
            methodVisitor.visitVarInsn(25, context.var("out"));
            methodVisitor.visitVarInsn(16, 93);
            methodVisitor.visitMethodInsn(182, SerializeWriter, "write", "(I)V");
        }
        methodVisitor.visitVarInsn(25, 1);
        methodVisitor.visitMethodInsn(182, JSONSerializer, "popContext", "()V");
        methodVisitor.visitLabel(label3);
        this._seperator(methodVisitor, context);
        methodVisitor.visitLabel(label2);
        methodVisitor.visitLabel(label);
    }

    private void _long(Class<?> object, MethodVisitor methodVisitor, FieldInfo fieldInfo, Context context) {
        object = new Label();
        this._nameApply(methodVisitor, fieldInfo, context, (Label)object);
        this._get(methodVisitor, context, fieldInfo);
        methodVisitor.visitVarInsn(55, context.var("long", 2));
        this._filters(methodVisitor, fieldInfo, context, (Label)object);
        methodVisitor.visitVarInsn(25, context.var("out"));
        methodVisitor.visitVarInsn(21, context.var("seperator"));
        methodVisitor.visitVarInsn(25, Context.fieldName);
        methodVisitor.visitVarInsn(22, context.var("long", 2));
        methodVisitor.visitMethodInsn(182, SerializeWriter, "writeFieldValue", "(CLjava/lang/String;J)V");
        this._seperator(methodVisitor, context);
        methodVisitor.visitLabel((Label)object);
    }

    private void _nameApply(MethodVisitor methodVisitor, FieldInfo fieldInfo, Context context, Label label) {
        if (!context.writeDirect) {
            methodVisitor.visitVarInsn(25, 1);
            methodVisitor.visitVarInsn(25, 0);
            methodVisitor.visitVarInsn(25, 2);
            methodVisitor.visitVarInsn(25, Context.fieldName);
            methodVisitor.visitMethodInsn(182, JSONSerializer, "applyName", "(" + SerializeFilterable_desc + "Ljava/lang/Object;Ljava/lang/String;)Z");
            methodVisitor.visitJumpInsn(153, label);
            this._labelApply(methodVisitor, fieldInfo, context, label);
        }
        if (fieldInfo.field == null) {
            methodVisitor.visitVarInsn(25, context.var("out"));
            methodVisitor.visitLdcInsn(SerializerFeature.IgnoreNonFieldGetter.mask);
            methodVisitor.visitMethodInsn(182, SerializeWriter, "isEnabled", "(I)Z");
            methodVisitor.visitJumpInsn(154, label);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void _notWriteDefault(MethodVisitor methodVisitor, FieldInfo clazz, Context context, Label label) {
        if (context.writeDirect) {
            return;
        }
        Label label2 = new Label();
        methodVisitor.visitVarInsn(21, context.var("notWriteDefaultValue"));
        methodVisitor.visitJumpInsn(153, label2);
        clazz = ((FieldInfo)clazz).fieldClass;
        if (clazz == Boolean.TYPE) {
            methodVisitor.visitVarInsn(21, context.var("boolean"));
            methodVisitor.visitJumpInsn(153, label);
        } else if (clazz == Byte.TYPE) {
            methodVisitor.visitVarInsn(21, context.var("byte"));
            methodVisitor.visitJumpInsn(153, label);
        } else if (clazz == Short.TYPE) {
            methodVisitor.visitVarInsn(21, context.var("short"));
            methodVisitor.visitJumpInsn(153, label);
        } else if (clazz == Integer.TYPE) {
            methodVisitor.visitVarInsn(21, context.var("int"));
            methodVisitor.visitJumpInsn(153, label);
        } else if (clazz == Long.TYPE) {
            methodVisitor.visitVarInsn(22, context.var("long"));
            methodVisitor.visitInsn(9);
            methodVisitor.visitInsn(148);
            methodVisitor.visitJumpInsn(153, label);
        } else if (clazz == Float.TYPE) {
            methodVisitor.visitVarInsn(23, context.var("float"));
            methodVisitor.visitInsn(11);
            methodVisitor.visitInsn(149);
            methodVisitor.visitJumpInsn(153, label);
        } else if (clazz == Double.TYPE) {
            methodVisitor.visitVarInsn(24, context.var("double"));
            methodVisitor.visitInsn(14);
            methodVisitor.visitInsn(151);
            methodVisitor.visitJumpInsn(153, label);
        }
        methodVisitor.visitLabel(label2);
    }

    private void _object(Class<?> object, MethodVisitor methodVisitor, FieldInfo fieldInfo, Context context) {
        object = new Label();
        this._nameApply(methodVisitor, fieldInfo, context, (Label)object);
        this._get(methodVisitor, context, fieldInfo);
        methodVisitor.visitVarInsn(58, context.var("object"));
        this._filters(methodVisitor, fieldInfo, context, (Label)object);
        this._writeObject(methodVisitor, fieldInfo, context, (Label)object);
        methodVisitor.visitLabel((Label)object);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void _processKey(MethodVisitor methodVisitor, FieldInfo clazz, Context context) {
        Label label = new Label();
        methodVisitor.visitVarInsn(21, context.var("hasNameFilters"));
        methodVisitor.visitJumpInsn(153, label);
        clazz = ((FieldInfo)clazz).fieldClass;
        methodVisitor.visitVarInsn(25, 1);
        methodVisitor.visitVarInsn(25, 0);
        methodVisitor.visitVarInsn(25, 2);
        methodVisitor.visitVarInsn(25, Context.fieldName);
        if (clazz == Byte.TYPE) {
            methodVisitor.visitVarInsn(21, context.var("byte"));
            methodVisitor.visitMethodInsn(184, "java/lang/Byte", "valueOf", "(B)Ljava/lang/Byte;");
        } else if (clazz == Short.TYPE) {
            methodVisitor.visitVarInsn(21, context.var("short"));
            methodVisitor.visitMethodInsn(184, "java/lang/Short", "valueOf", "(S)Ljava/lang/Short;");
        } else if (clazz == Integer.TYPE) {
            methodVisitor.visitVarInsn(21, context.var("int"));
            methodVisitor.visitMethodInsn(184, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;");
        } else if (clazz == Character.TYPE) {
            methodVisitor.visitVarInsn(21, context.var("char"));
            methodVisitor.visitMethodInsn(184, "java/lang/Character", "valueOf", "(C)Ljava/lang/Character;");
        } else if (clazz == Long.TYPE) {
            methodVisitor.visitVarInsn(22, context.var("long", 2));
            methodVisitor.visitMethodInsn(184, "java/lang/Long", "valueOf", "(J)Ljava/lang/Long;");
        } else if (clazz == Float.TYPE) {
            methodVisitor.visitVarInsn(23, context.var("float"));
            methodVisitor.visitMethodInsn(184, "java/lang/Float", "valueOf", "(F)Ljava/lang/Float;");
        } else if (clazz == Double.TYPE) {
            methodVisitor.visitVarInsn(24, context.var("double", 2));
            methodVisitor.visitMethodInsn(184, "java/lang/Double", "valueOf", "(D)Ljava/lang/Double;");
        } else if (clazz == Boolean.TYPE) {
            methodVisitor.visitVarInsn(21, context.var("boolean"));
            methodVisitor.visitMethodInsn(184, "java/lang/Boolean", "valueOf", "(Z)Ljava/lang/Boolean;");
        } else if (clazz == BigDecimal.class) {
            methodVisitor.visitVarInsn(25, context.var("decimal"));
        } else if (clazz == String.class) {
            methodVisitor.visitVarInsn(25, context.var("string"));
        } else if (clazz.isEnum()) {
            methodVisitor.visitVarInsn(25, context.var("enum"));
        } else if (List.class.isAssignableFrom(clazz)) {
            methodVisitor.visitVarInsn(25, context.var("list"));
        } else {
            methodVisitor.visitVarInsn(25, context.var("object"));
        }
        methodVisitor.visitMethodInsn(182, JSONSerializer, "processKey", "(" + SerializeFilterable_desc + "Ljava/lang/Object;Ljava/lang/String;Ljava/lang/Object;)Ljava/lang/String;");
        methodVisitor.visitVarInsn(58, Context.fieldName);
        methodVisitor.visitLabel(label);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void _processValue(MethodVisitor methodVisitor, FieldInfo fieldInfo, Context context, Label label) {
        Label label2 = new Label();
        Class<?> clazz = fieldInfo.fieldClass;
        if (clazz.isPrimitive()) {
            Label label3 = new Label();
            methodVisitor.visitVarInsn(21, context.var("checkValue"));
            methodVisitor.visitJumpInsn(154, label3);
            methodVisitor.visitInsn(1);
            methodVisitor.visitInsn(89);
            methodVisitor.visitVarInsn(58, Context.original);
            methodVisitor.visitVarInsn(58, Context.processValue);
            methodVisitor.visitJumpInsn(167, label2);
            methodVisitor.visitLabel(label3);
        }
        methodVisitor.visitVarInsn(25, 1);
        methodVisitor.visitVarInsn(25, 0);
        methodVisitor.visitInsn(89);
        methodVisitor.visitLdcInsn(context.getFieldOrinal(fieldInfo.name));
        methodVisitor.visitMethodInsn(182, JavaBeanSerializer, "getBeanContext", "(I)" + ASMUtils.desc(BeanContext.class));
        methodVisitor.visitVarInsn(25, 2);
        methodVisitor.visitVarInsn(25, Context.fieldName);
        if (clazz == Byte.TYPE) {
            methodVisitor.visitVarInsn(21, context.var("byte"));
            methodVisitor.visitMethodInsn(184, "java/lang/Byte", "valueOf", "(B)Ljava/lang/Byte;");
            methodVisitor.visitInsn(89);
            methodVisitor.visitVarInsn(58, Context.original);
        } else if (clazz == Short.TYPE) {
            methodVisitor.visitVarInsn(21, context.var("short"));
            methodVisitor.visitMethodInsn(184, "java/lang/Short", "valueOf", "(S)Ljava/lang/Short;");
            methodVisitor.visitInsn(89);
            methodVisitor.visitVarInsn(58, Context.original);
        } else if (clazz == Integer.TYPE) {
            methodVisitor.visitVarInsn(21, context.var("int"));
            methodVisitor.visitMethodInsn(184, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;");
            methodVisitor.visitInsn(89);
            methodVisitor.visitVarInsn(58, Context.original);
        } else if (clazz == Character.TYPE) {
            methodVisitor.visitVarInsn(21, context.var("char"));
            methodVisitor.visitMethodInsn(184, "java/lang/Character", "valueOf", "(C)Ljava/lang/Character;");
            methodVisitor.visitInsn(89);
            methodVisitor.visitVarInsn(58, Context.original);
        } else if (clazz == Long.TYPE) {
            methodVisitor.visitVarInsn(22, context.var("long", 2));
            methodVisitor.visitMethodInsn(184, "java/lang/Long", "valueOf", "(J)Ljava/lang/Long;");
            methodVisitor.visitInsn(89);
            methodVisitor.visitVarInsn(58, Context.original);
        } else if (clazz == Float.TYPE) {
            methodVisitor.visitVarInsn(23, context.var("float"));
            methodVisitor.visitMethodInsn(184, "java/lang/Float", "valueOf", "(F)Ljava/lang/Float;");
            methodVisitor.visitInsn(89);
            methodVisitor.visitVarInsn(58, Context.original);
        } else if (clazz == Double.TYPE) {
            methodVisitor.visitVarInsn(24, context.var("double", 2));
            methodVisitor.visitMethodInsn(184, "java/lang/Double", "valueOf", "(D)Ljava/lang/Double;");
            methodVisitor.visitInsn(89);
            methodVisitor.visitVarInsn(58, Context.original);
        } else if (clazz == Boolean.TYPE) {
            methodVisitor.visitVarInsn(21, context.var("boolean"));
            methodVisitor.visitMethodInsn(184, "java/lang/Boolean", "valueOf", "(Z)Ljava/lang/Boolean;");
            methodVisitor.visitInsn(89);
            methodVisitor.visitVarInsn(58, Context.original);
        } else if (clazz == BigDecimal.class) {
            methodVisitor.visitVarInsn(25, context.var("decimal"));
            methodVisitor.visitVarInsn(58, Context.original);
            methodVisitor.visitVarInsn(25, Context.original);
        } else if (clazz == String.class) {
            methodVisitor.visitVarInsn(25, context.var("string"));
            methodVisitor.visitVarInsn(58, Context.original);
            methodVisitor.visitVarInsn(25, Context.original);
        } else if (clazz.isEnum()) {
            methodVisitor.visitVarInsn(25, context.var("enum"));
            methodVisitor.visitVarInsn(58, Context.original);
            methodVisitor.visitVarInsn(25, Context.original);
        } else if (List.class.isAssignableFrom(clazz)) {
            methodVisitor.visitVarInsn(25, context.var("list"));
            methodVisitor.visitVarInsn(58, Context.original);
            methodVisitor.visitVarInsn(25, Context.original);
        } else {
            methodVisitor.visitVarInsn(25, context.var("object"));
            methodVisitor.visitVarInsn(58, Context.original);
            methodVisitor.visitVarInsn(25, Context.original);
        }
        methodVisitor.visitMethodInsn(182, JSONSerializer, "processValue", "(" + SerializeFilterable_desc + ASMUtils.desc(BeanContext.class) + "Ljava/lang/Object;Ljava/lang/String;" + "Ljava/lang/Object;" + ")Ljava/lang/Object;");
        methodVisitor.visitVarInsn(58, Context.processValue);
        methodVisitor.visitVarInsn(25, Context.original);
        methodVisitor.visitVarInsn(25, Context.processValue);
        methodVisitor.visitJumpInsn(165, label2);
        this._writeObject(methodVisitor, fieldInfo, context, label);
        methodVisitor.visitJumpInsn(167, label);
        methodVisitor.visitLabel(label2);
    }

    private void _seperator(MethodVisitor methodVisitor, Context context) {
        methodVisitor.visitVarInsn(16, 44);
        methodVisitor.visitVarInsn(54, context.var("seperator"));
    }

    /*
     * Enabled aggressive block sorting
     */
    private void _string(Class<?> object, MethodVisitor methodVisitor, FieldInfo fieldInfo, Context context) {
        object = new Label();
        this._nameApply(methodVisitor, fieldInfo, context, (Label)object);
        this._get(methodVisitor, context, fieldInfo);
        methodVisitor.visitVarInsn(58, context.var("string"));
        this._filters(methodVisitor, fieldInfo, context, (Label)object);
        Label label = new Label();
        Label label2 = new Label();
        methodVisitor.visitVarInsn(25, context.var("string"));
        methodVisitor.visitJumpInsn(199, label);
        this._if_write_null(methodVisitor, fieldInfo, context);
        methodVisitor.visitJumpInsn(167, label2);
        methodVisitor.visitLabel(label);
        if (context.writeDirect) {
            methodVisitor.visitVarInsn(25, context.var("out"));
            methodVisitor.visitVarInsn(21, context.var("seperator"));
            methodVisitor.visitVarInsn(25, Context.fieldName);
            methodVisitor.visitVarInsn(25, context.var("string"));
            methodVisitor.visitMethodInsn(182, SerializeWriter, "writeFieldValueStringWithDoubleQuoteCheck", "(CLjava/lang/String;Ljava/lang/String;)V");
        } else {
            methodVisitor.visitVarInsn(25, context.var("out"));
            methodVisitor.visitVarInsn(21, context.var("seperator"));
            methodVisitor.visitVarInsn(25, Context.fieldName);
            methodVisitor.visitVarInsn(25, context.var("string"));
            methodVisitor.visitMethodInsn(182, SerializeWriter, "writeFieldValue", "(CLjava/lang/String;Ljava/lang/String;)V");
        }
        this._seperator(methodVisitor, context);
        methodVisitor.visitLabel(label2);
        methodVisitor.visitLabel((Label)object);
    }

    private void _writeFieldName(MethodVisitor methodVisitor, Context context) {
        if (context.writeDirect) {
            methodVisitor.visitVarInsn(25, context.var("out"));
            methodVisitor.visitVarInsn(25, Context.fieldName);
            methodVisitor.visitMethodInsn(182, SerializeWriter, "writeFieldNameDirect", "(Ljava/lang/String;)V");
            return;
        }
        methodVisitor.visitVarInsn(25, context.var("out"));
        methodVisitor.visitVarInsn(25, Context.fieldName);
        methodVisitor.visitInsn(3);
        methodVisitor.visitMethodInsn(182, SerializeWriter, "writeFieldName", "(Ljava/lang/String;Z)V");
    }

    /*
     * Enabled aggressive block sorting
     */
    private void _writeObject(MethodVisitor methodVisitor, FieldInfo fieldInfo, Context context, Label object) {
        String string2 = fieldInfo.getFormat();
        Object object2 = fieldInfo.fieldClass;
        Label label = new Label();
        if (context.writeDirect) {
            methodVisitor.visitVarInsn(25, context.var("object"));
        } else {
            methodVisitor.visitVarInsn(25, Context.processValue);
        }
        methodVisitor.visitInsn(89);
        methodVisitor.visitVarInsn(58, context.var("object"));
        methodVisitor.visitJumpInsn(199, label);
        this._if_write_null(methodVisitor, fieldInfo, context);
        methodVisitor.visitJumpInsn(167, (Label)object);
        methodVisitor.visitLabel(label);
        methodVisitor.visitVarInsn(25, context.var("out"));
        methodVisitor.visitVarInsn(21, context.var("seperator"));
        methodVisitor.visitMethodInsn(182, SerializeWriter, "write", "(I)V");
        this._writeFieldName(methodVisitor, context);
        label = new Label();
        Label label2 = new Label();
        if (Modifier.isPublic(((Class)object2).getModifiers()) && !ParserConfig.isPrimitive(object2)) {
            methodVisitor.visitVarInsn(25, context.var("object"));
            methodVisitor.visitMethodInsn(182, "java/lang/Object", "getClass", "()Ljava/lang/Class;");
            methodVisitor.visitLdcInsn(Type.getType(ASMUtils.desc(object2)));
            methodVisitor.visitJumpInsn(166, label2);
            this._getFieldSer(context, methodVisitor, fieldInfo);
            methodVisitor.visitVarInsn(58, context.var("fied_ser"));
            object2 = new Label();
            Label label3 = new Label();
            methodVisitor.visitVarInsn(25, context.var("fied_ser"));
            methodVisitor.visitTypeInsn(193, JavaBeanSerializer);
            methodVisitor.visitJumpInsn(153, (Label)object2);
            object = context.nonContext && context.writeDirect ? "writeDirectNonContext" : "write";
            methodVisitor.visitVarInsn(25, context.var("fied_ser"));
            methodVisitor.visitTypeInsn(192, JavaBeanSerializer);
            methodVisitor.visitVarInsn(25, 1);
            methodVisitor.visitVarInsn(25, context.var("object"));
            methodVisitor.visitVarInsn(25, Context.fieldName);
            methodVisitor.visitVarInsn(25, 0);
            methodVisitor.visitFieldInsn(180, context.className, fieldInfo.name + "_asm_fieldType", "Ljava/lang/reflect/Type;");
            methodVisitor.visitLdcInsn(fieldInfo.serialzeFeatures);
            methodVisitor.visitMethodInsn(182, JavaBeanSerializer, (String)object, "(L" + JSONSerializer + ";Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V");
            methodVisitor.visitJumpInsn(167, label3);
            methodVisitor.visitLabel((Label)object2);
            methodVisitor.visitVarInsn(25, context.var("fied_ser"));
            methodVisitor.visitVarInsn(25, 1);
            methodVisitor.visitVarInsn(25, context.var("object"));
            methodVisitor.visitVarInsn(25, Context.fieldName);
            methodVisitor.visitVarInsn(25, 0);
            methodVisitor.visitFieldInsn(180, context.className, fieldInfo.name + "_asm_fieldType", "Ljava/lang/reflect/Type;");
            methodVisitor.visitLdcInsn(fieldInfo.serialzeFeatures);
            methodVisitor.visitMethodInsn(185, ObjectSerializer, "write", "(L" + JSONSerializer + ";Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V");
            methodVisitor.visitLabel(label3);
            methodVisitor.visitJumpInsn(167, label);
        }
        methodVisitor.visitLabel(label2);
        methodVisitor.visitVarInsn(25, 1);
        if (context.writeDirect) {
            methodVisitor.visitVarInsn(25, context.var("object"));
        } else {
            methodVisitor.visitVarInsn(25, Context.processValue);
        }
        if (string2 != null) {
            methodVisitor.visitLdcInsn(string2);
            methodVisitor.visitMethodInsn(182, JSONSerializer, "writeWithFormat", "(Ljava/lang/Object;Ljava/lang/String;)V");
        } else {
            methodVisitor.visitVarInsn(25, Context.fieldName);
            if (fieldInfo.fieldType instanceof Class && ((Class)fieldInfo.fieldType).isPrimitive()) {
                methodVisitor.visitMethodInsn(182, JSONSerializer, "writeWithFieldName", "(Ljava/lang/Object;Ljava/lang/Object;)V");
            } else {
                if (fieldInfo.fieldClass == String.class) {
                    methodVisitor.visitLdcInsn(Type.getType(ASMUtils.desc(String.class)));
                } else {
                    methodVisitor.visitVarInsn(25, 0);
                    methodVisitor.visitFieldInsn(180, context.className, fieldInfo.name + "_asm_fieldType", "Ljava/lang/reflect/Type;");
                }
                methodVisitor.visitLdcInsn(fieldInfo.serialzeFeatures);
                methodVisitor.visitMethodInsn(182, JSONSerializer, "writeWithFieldName", "(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V");
            }
        }
        methodVisitor.visitLabel(label);
        this._seperator(methodVisitor, context);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void generateWriteAsArray(Class<?> object, MethodVisitor methodVisitor, List<FieldInfo> list, Context context) throws Exception {
        methodVisitor.visitVarInsn(25, context.var("out"));
        methodVisitor.visitVarInsn(16, 91);
        methodVisitor.visitMethodInsn(182, SerializeWriter, "write", "(I)V");
        int n2 = list.size();
        if (n2 == 0) {
            methodVisitor.visitVarInsn(25, context.var("out"));
            methodVisitor.visitVarInsn(16, 93);
            methodVisitor.visitMethodInsn(182, SerializeWriter, "write", "(I)V");
            return;
        }
        int n3 = 0;
        while (n3 < n2) {
            Label label;
            Label label2;
            Label label3;
            Object object2;
            int n4 = n3 == n2 - 1 ? 93 : 44;
            FieldInfo fieldInfo = list.get(n3);
            Object object3 = fieldInfo.fieldClass;
            methodVisitor.visitLdcInsn(fieldInfo.name);
            methodVisitor.visitVarInsn(58, Context.fieldName);
            if (object3 == Byte.TYPE || object3 == Short.TYPE || object3 == Integer.TYPE) {
                methodVisitor.visitVarInsn(25, context.var("out"));
                methodVisitor.visitInsn(89);
                this._get(methodVisitor, context, fieldInfo);
                methodVisitor.visitMethodInsn(182, SerializeWriter, "writeInt", "(I)V");
                methodVisitor.visitVarInsn(16, n4);
                methodVisitor.visitMethodInsn(182, SerializeWriter, "write", "(I)V");
            } else if (object3 == Long.TYPE) {
                methodVisitor.visitVarInsn(25, context.var("out"));
                methodVisitor.visitInsn(89);
                this._get(methodVisitor, context, fieldInfo);
                methodVisitor.visitMethodInsn(182, SerializeWriter, "writeLong", "(J)V");
                methodVisitor.visitVarInsn(16, n4);
                methodVisitor.visitMethodInsn(182, SerializeWriter, "write", "(I)V");
            } else if (object3 == Float.TYPE) {
                methodVisitor.visitVarInsn(25, context.var("out"));
                methodVisitor.visitInsn(89);
                this._get(methodVisitor, context, fieldInfo);
                methodVisitor.visitInsn(4);
                methodVisitor.visitMethodInsn(182, SerializeWriter, "writeFloat", "(FZ)V");
                methodVisitor.visitVarInsn(16, n4);
                methodVisitor.visitMethodInsn(182, SerializeWriter, "write", "(I)V");
            } else if (object3 == Double.TYPE) {
                methodVisitor.visitVarInsn(25, context.var("out"));
                methodVisitor.visitInsn(89);
                this._get(methodVisitor, context, fieldInfo);
                methodVisitor.visitInsn(4);
                methodVisitor.visitMethodInsn(182, SerializeWriter, "writeDouble", "(DZ)V");
                methodVisitor.visitVarInsn(16, n4);
                methodVisitor.visitMethodInsn(182, SerializeWriter, "write", "(I)V");
            } else if (object3 == Boolean.TYPE) {
                methodVisitor.visitVarInsn(25, context.var("out"));
                methodVisitor.visitInsn(89);
                this._get(methodVisitor, context, fieldInfo);
                methodVisitor.visitMethodInsn(182, SerializeWriter, "write", "(Z)V");
                methodVisitor.visitVarInsn(16, n4);
                methodVisitor.visitMethodInsn(182, SerializeWriter, "write", "(I)V");
            } else if (object3 == Character.TYPE) {
                methodVisitor.visitVarInsn(25, context.var("out"));
                this._get(methodVisitor, context, fieldInfo);
                methodVisitor.visitMethodInsn(184, "java/lang/Character", "toString", "(C)Ljava/lang/String;");
                methodVisitor.visitVarInsn(16, n4);
                methodVisitor.visitMethodInsn(182, SerializeWriter, "writeString", "(Ljava/lang/String;C)V");
            } else if (object3 == String.class) {
                methodVisitor.visitVarInsn(25, context.var("out"));
                this._get(methodVisitor, context, fieldInfo);
                methodVisitor.visitVarInsn(16, n4);
                methodVisitor.visitMethodInsn(182, SerializeWriter, "writeString", "(Ljava/lang/String;C)V");
            } else if (((Class)object3).isEnum()) {
                methodVisitor.visitVarInsn(25, context.var("out"));
                methodVisitor.visitInsn(89);
                this._get(methodVisitor, context, fieldInfo);
                methodVisitor.visitMethodInsn(182, SerializeWriter, "writeEnum", "(Ljava/lang/Enum;)V");
                methodVisitor.visitVarInsn(16, n4);
                methodVisitor.visitMethodInsn(182, SerializeWriter, "write", "(I)V");
            } else if (List.class.isAssignableFrom((Class<?>)object3)) {
                object = fieldInfo.fieldType;
                object3 = object instanceof Class ? Object.class : ((ParameterizedType)object).getActualTypeArguments()[0];
                object = null;
                if (object3 instanceof Class) {
                    object = object2 = (Class)object3;
                    if (object2 == Object.class) {
                        object = null;
                    }
                }
                this._get(methodVisitor, context, fieldInfo);
                methodVisitor.visitTypeInsn(192, "java/util/List");
                methodVisitor.visitVarInsn(58, context.var("list"));
                if (object == String.class && context.writeDirect) {
                    methodVisitor.visitVarInsn(25, context.var("out"));
                    methodVisitor.visitVarInsn(25, context.var("list"));
                    methodVisitor.visitMethodInsn(182, SerializeWriter, "write", "(Ljava/util/List;)V");
                } else {
                    object2 = new Label();
                    label3 = new Label();
                    methodVisitor.visitVarInsn(25, context.var("list"));
                    methodVisitor.visitJumpInsn(199, label3);
                    methodVisitor.visitVarInsn(25, context.var("out"));
                    methodVisitor.visitMethodInsn(182, SerializeWriter, "writeNull", "()V");
                    methodVisitor.visitJumpInsn(167, (Label)object2);
                    methodVisitor.visitLabel(label3);
                    methodVisitor.visitVarInsn(25, context.var("list"));
                    methodVisitor.visitMethodInsn(185, "java/util/List", "size", "()I");
                    methodVisitor.visitVarInsn(54, context.var("size"));
                    methodVisitor.visitVarInsn(25, context.var("out"));
                    methodVisitor.visitVarInsn(16, 91);
                    methodVisitor.visitMethodInsn(182, SerializeWriter, "write", "(I)V");
                    label3 = new Label();
                    label2 = new Label();
                    label = new Label();
                    methodVisitor.visitInsn(3);
                    methodVisitor.visitVarInsn(54, context.var("i"));
                    methodVisitor.visitLabel(label3);
                    methodVisitor.visitVarInsn(21, context.var("i"));
                    methodVisitor.visitVarInsn(21, context.var("size"));
                    methodVisitor.visitJumpInsn(162, label);
                    methodVisitor.visitVarInsn(21, context.var("i"));
                    methodVisitor.visitJumpInsn(153, label2);
                    methodVisitor.visitVarInsn(25, context.var("out"));
                    methodVisitor.visitVarInsn(16, 44);
                    methodVisitor.visitMethodInsn(182, SerializeWriter, "write", "(I)V");
                    methodVisitor.visitLabel(label2);
                    methodVisitor.visitVarInsn(25, context.var("list"));
                    methodVisitor.visitVarInsn(21, context.var("i"));
                    methodVisitor.visitMethodInsn(185, "java/util/List", "get", "(I)Ljava/lang/Object;");
                    methodVisitor.visitVarInsn(58, context.var("list_item"));
                    label2 = new Label();
                    Label label4 = new Label();
                    methodVisitor.visitVarInsn(25, context.var("list_item"));
                    methodVisitor.visitJumpInsn(199, label4);
                    methodVisitor.visitVarInsn(25, context.var("out"));
                    methodVisitor.visitMethodInsn(182, SerializeWriter, "writeNull", "()V");
                    methodVisitor.visitJumpInsn(167, label2);
                    methodVisitor.visitLabel(label4);
                    label4 = new Label();
                    Label label5 = new Label();
                    if (object != null && Modifier.isPublic(((Class)object).getModifiers())) {
                        methodVisitor.visitVarInsn(25, context.var("list_item"));
                        methodVisitor.visitMethodInsn(182, "java/lang/Object", "getClass", "()Ljava/lang/Class;");
                        methodVisitor.visitLdcInsn(Type.getType(ASMUtils.desc(object)));
                        methodVisitor.visitJumpInsn(166, label5);
                        this._getListFieldItemSer(context, methodVisitor, fieldInfo, (Class<?>)object);
                        methodVisitor.visitVarInsn(58, context.var("list_item_desc"));
                        Label label6 = new Label();
                        Label label7 = new Label();
                        if (context.writeDirect) {
                            methodVisitor.visitVarInsn(25, context.var("list_item_desc"));
                            methodVisitor.visitTypeInsn(193, JavaBeanSerializer);
                            methodVisitor.visitJumpInsn(153, label6);
                            methodVisitor.visitVarInsn(25, context.var("list_item_desc"));
                            methodVisitor.visitTypeInsn(192, JavaBeanSerializer);
                            methodVisitor.visitVarInsn(25, 1);
                            methodVisitor.visitVarInsn(25, context.var("list_item"));
                            if (context.nonContext) {
                                methodVisitor.visitInsn(1);
                            } else {
                                methodVisitor.visitVarInsn(21, context.var("i"));
                                methodVisitor.visitMethodInsn(184, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;");
                            }
                            methodVisitor.visitLdcInsn(Type.getType(ASMUtils.desc(object)));
                            methodVisitor.visitLdcInsn(fieldInfo.serialzeFeatures);
                            methodVisitor.visitMethodInsn(182, JavaBeanSerializer, "writeAsArrayNonContext", "(L" + JSONSerializer + ";Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V");
                            methodVisitor.visitJumpInsn(167, label7);
                            methodVisitor.visitLabel(label6);
                        }
                        methodVisitor.visitVarInsn(25, context.var("list_item_desc"));
                        methodVisitor.visitVarInsn(25, 1);
                        methodVisitor.visitVarInsn(25, context.var("list_item"));
                        if (context.nonContext) {
                            methodVisitor.visitInsn(1);
                        } else {
                            methodVisitor.visitVarInsn(21, context.var("i"));
                            methodVisitor.visitMethodInsn(184, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;");
                        }
                        methodVisitor.visitLdcInsn(Type.getType(ASMUtils.desc(object)));
                        methodVisitor.visitLdcInsn(fieldInfo.serialzeFeatures);
                        methodVisitor.visitMethodInsn(185, ObjectSerializer, "write", "(L" + JSONSerializer + ";Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V");
                        methodVisitor.visitLabel(label7);
                        methodVisitor.visitJumpInsn(167, label4);
                    }
                    methodVisitor.visitLabel(label5);
                    methodVisitor.visitVarInsn(25, 1);
                    methodVisitor.visitVarInsn(25, context.var("list_item"));
                    if (context.nonContext) {
                        methodVisitor.visitInsn(1);
                    } else {
                        methodVisitor.visitVarInsn(21, context.var("i"));
                        methodVisitor.visitMethodInsn(184, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;");
                    }
                    if (object != null && Modifier.isPublic(((Class)object).getModifiers())) {
                        methodVisitor.visitLdcInsn(Type.getType(ASMUtils.desc((Class)object3)));
                        methodVisitor.visitLdcInsn(fieldInfo.serialzeFeatures);
                        methodVisitor.visitMethodInsn(182, JSONSerializer, "writeWithFieldName", "(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V");
                    } else {
                        methodVisitor.visitMethodInsn(182, JSONSerializer, "writeWithFieldName", "(Ljava/lang/Object;Ljava/lang/Object;)V");
                    }
                    methodVisitor.visitLabel(label4);
                    methodVisitor.visitLabel(label2);
                    methodVisitor.visitIincInsn(context.var("i"), 1);
                    methodVisitor.visitJumpInsn(167, label3);
                    methodVisitor.visitLabel(label);
                    methodVisitor.visitVarInsn(25, context.var("out"));
                    methodVisitor.visitVarInsn(16, 93);
                    methodVisitor.visitMethodInsn(182, SerializeWriter, "write", "(I)V");
                    methodVisitor.visitLabel((Label)object2);
                }
                methodVisitor.visitVarInsn(25, context.var("out"));
                methodVisitor.visitVarInsn(16, n4);
                methodVisitor.visitMethodInsn(182, SerializeWriter, "write", "(I)V");
            } else {
                object = new Label();
                object2 = new Label();
                this._get(methodVisitor, context, fieldInfo);
                methodVisitor.visitInsn(89);
                methodVisitor.visitVarInsn(58, context.var("field_" + fieldInfo.fieldClass.getName()));
                methodVisitor.visitJumpInsn(199, (Label)object2);
                methodVisitor.visitVarInsn(25, context.var("out"));
                methodVisitor.visitMethodInsn(182, SerializeWriter, "writeNull", "()V");
                methodVisitor.visitJumpInsn(167, (Label)object);
                methodVisitor.visitLabel((Label)object2);
                object2 = new Label();
                label3 = new Label();
                methodVisitor.visitVarInsn(25, context.var("field_" + fieldInfo.fieldClass.getName()));
                methodVisitor.visitMethodInsn(182, "java/lang/Object", "getClass", "()Ljava/lang/Class;");
                methodVisitor.visitLdcInsn(Type.getType(ASMUtils.desc(object3)));
                methodVisitor.visitJumpInsn(166, label3);
                this._getFieldSer(context, methodVisitor, fieldInfo);
                methodVisitor.visitVarInsn(58, context.var("fied_ser"));
                label = new Label();
                label2 = new Label();
                if (context.writeDirect && Modifier.isPublic(((Class)object3).getModifiers())) {
                    methodVisitor.visitVarInsn(25, context.var("fied_ser"));
                    methodVisitor.visitTypeInsn(193, JavaBeanSerializer);
                    methodVisitor.visitJumpInsn(153, label);
                    methodVisitor.visitVarInsn(25, context.var("fied_ser"));
                    methodVisitor.visitTypeInsn(192, JavaBeanSerializer);
                    methodVisitor.visitVarInsn(25, 1);
                    methodVisitor.visitVarInsn(25, context.var("field_" + fieldInfo.fieldClass.getName()));
                    methodVisitor.visitVarInsn(25, Context.fieldName);
                    methodVisitor.visitLdcInsn(Type.getType(ASMUtils.desc(object3)));
                    methodVisitor.visitLdcInsn(fieldInfo.serialzeFeatures);
                    methodVisitor.visitMethodInsn(182, JavaBeanSerializer, "writeAsArrayNonContext", "(L" + JSONSerializer + ";Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V");
                    methodVisitor.visitJumpInsn(167, label2);
                    methodVisitor.visitLabel(label);
                }
                methodVisitor.visitVarInsn(25, context.var("fied_ser"));
                methodVisitor.visitVarInsn(25, 1);
                methodVisitor.visitVarInsn(25, context.var("field_" + fieldInfo.fieldClass.getName()));
                methodVisitor.visitVarInsn(25, Context.fieldName);
                methodVisitor.visitLdcInsn(Type.getType(ASMUtils.desc(object3)));
                methodVisitor.visitLdcInsn(fieldInfo.serialzeFeatures);
                methodVisitor.visitMethodInsn(185, ObjectSerializer, "write", "(L" + JSONSerializer + ";Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V");
                methodVisitor.visitLabel(label2);
                methodVisitor.visitJumpInsn(167, (Label)object2);
                methodVisitor.visitLabel(label3);
                object3 = fieldInfo.getFormat();
                methodVisitor.visitVarInsn(25, 1);
                methodVisitor.visitVarInsn(25, context.var("field_" + fieldInfo.fieldClass.getName()));
                if (object3 != null) {
                    methodVisitor.visitLdcInsn(object3);
                    methodVisitor.visitMethodInsn(182, JSONSerializer, "writeWithFormat", "(Ljava/lang/Object;Ljava/lang/String;)V");
                } else {
                    methodVisitor.visitVarInsn(25, Context.fieldName);
                    if (fieldInfo.fieldType instanceof Class && ((Class)fieldInfo.fieldType).isPrimitive()) {
                        methodVisitor.visitMethodInsn(182, JSONSerializer, "writeWithFieldName", "(Ljava/lang/Object;Ljava/lang/Object;)V");
                    } else {
                        methodVisitor.visitVarInsn(25, 0);
                        methodVisitor.visitFieldInsn(180, context.className, fieldInfo.name + "_asm_fieldType", "Ljava/lang/reflect/Type;");
                        methodVisitor.visitLdcInsn(fieldInfo.serialzeFeatures);
                        methodVisitor.visitMethodInsn(182, JSONSerializer, "writeWithFieldName", "(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V");
                    }
                }
                methodVisitor.visitLabel((Label)object2);
                methodVisitor.visitLabel((Label)object);
                methodVisitor.visitVarInsn(25, context.var("out"));
                methodVisitor.visitVarInsn(16, n4);
                methodVisitor.visitMethodInsn(182, SerializeWriter, "write", "(I)V");
            }
            ++n3;
        }
        return;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void generateWriteMethod(Class<?> object, MethodVisitor methodVisitor, List<FieldInfo> object2, Context context) throws Exception {
        int n2;
        Object object3;
        Object object4;
        int n3;
        Label label;
        block30: {
            block27: {
                Label label2;
                Object object5;
                block29: {
                    block28: {
                        label = new Label();
                        n3 = object2.size();
                        if (!context.writeDirect) {
                            object4 = new Label();
                            object3 = new Label();
                            methodVisitor.visitVarInsn(25, context.var("out"));
                            methodVisitor.visitLdcInsn(SerializerFeature.PrettyFormat.mask);
                            methodVisitor.visitMethodInsn(182, SerializeWriter, "isEnabled", "(I)Z");
                            methodVisitor.visitJumpInsn(154, (Label)object3);
                            n2 = 0;
                            object5 = object2.iterator();
                            while (object5.hasNext()) {
                                if (((FieldInfo)object5.next()).method == null) continue;
                                n2 = 1;
                            }
                            if (n2 != 0) {
                                methodVisitor.visitVarInsn(25, context.var("out"));
                                methodVisitor.visitLdcInsn(SerializerFeature.IgnoreErrorGetter.mask);
                                methodVisitor.visitMethodInsn(182, SerializeWriter, "isEnabled", "(I)Z");
                                methodVisitor.visitJumpInsn(153, (Label)object4);
                            } else {
                                methodVisitor.visitJumpInsn(167, (Label)object4);
                            }
                            methodVisitor.visitLabel((Label)object3);
                            methodVisitor.visitVarInsn(25, 0);
                            methodVisitor.visitVarInsn(25, 1);
                            methodVisitor.visitVarInsn(25, 2);
                            methodVisitor.visitVarInsn(25, 3);
                            methodVisitor.visitVarInsn(25, 4);
                            methodVisitor.visitVarInsn(21, 5);
                            methodVisitor.visitMethodInsn(183, JavaBeanSerializer, "write", "(L" + JSONSerializer + ";Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V");
                            methodVisitor.visitInsn(177);
                            methodVisitor.visitLabel((Label)object4);
                        }
                        if (!context.nonContext) {
                            object4 = new Label();
                            methodVisitor.visitVarInsn(25, 0);
                            methodVisitor.visitVarInsn(25, 1);
                            methodVisitor.visitVarInsn(25, 2);
                            methodVisitor.visitVarInsn(21, 5);
                            methodVisitor.visitMethodInsn(182, JavaBeanSerializer, "writeReference", "(L" + JSONSerializer + ";Ljava/lang/Object;I)Z");
                            methodVisitor.visitJumpInsn(153, (Label)object4);
                            methodVisitor.visitInsn(177);
                            methodVisitor.visitLabel((Label)object4);
                        }
                        object4 = context.writeDirect ? (context.nonContext ? "writeAsArrayNonContext" : "writeAsArray") : "writeAsArrayNormal";
                        if ((context.beanFeatures & SerializerFeature.BeanToArray.mask) == 0) {
                            object3 = new Label();
                            methodVisitor.visitVarInsn(25, context.var("out"));
                            methodVisitor.visitLdcInsn(SerializerFeature.BeanToArray.mask);
                            methodVisitor.visitMethodInsn(182, SerializeWriter, "isEnabled", "(I)Z");
                            methodVisitor.visitJumpInsn(153, (Label)object3);
                            methodVisitor.visitVarInsn(25, 0);
                            methodVisitor.visitVarInsn(25, 1);
                            methodVisitor.visitVarInsn(25, 2);
                            methodVisitor.visitVarInsn(25, 3);
                            methodVisitor.visitVarInsn(25, 4);
                            methodVisitor.visitVarInsn(21, 5);
                            methodVisitor.visitMethodInsn(182, context.className, (String)object4, "(L" + JSONSerializer + ";Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V");
                            methodVisitor.visitInsn(177);
                            methodVisitor.visitLabel((Label)object3);
                        } else {
                            methodVisitor.visitVarInsn(25, 0);
                            methodVisitor.visitVarInsn(25, 1);
                            methodVisitor.visitVarInsn(25, 2);
                            methodVisitor.visitVarInsn(25, 3);
                            methodVisitor.visitVarInsn(25, 4);
                            methodVisitor.visitVarInsn(21, 5);
                            methodVisitor.visitMethodInsn(182, context.className, (String)object4, "(L" + JSONSerializer + ";Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V");
                            methodVisitor.visitInsn(177);
                        }
                        if (!context.nonContext) {
                            methodVisitor.visitVarInsn(25, 1);
                            methodVisitor.visitMethodInsn(182, JSONSerializer, "getContext", "()" + SerialContext_desc);
                            methodVisitor.visitVarInsn(58, context.var("parent"));
                            methodVisitor.visitVarInsn(25, 1);
                            methodVisitor.visitVarInsn(25, context.var("parent"));
                            methodVisitor.visitVarInsn(25, 2);
                            methodVisitor.visitVarInsn(25, 3);
                            methodVisitor.visitLdcInsn(context.beanSerializeFeatures);
                            methodVisitor.visitMethodInsn(182, JSONSerializer, "setContext", "(" + SerialContext_desc + "Ljava/lang/Object;Ljava/lang/Object;I)V");
                        }
                        if (context.writeDirect) break block27;
                        object5 = new Label();
                        label2 = new Label();
                        object4 = new Label();
                        methodVisitor.visitVarInsn(25, 1);
                        methodVisitor.visitVarInsn(25, 4);
                        methodVisitor.visitVarInsn(25, 2);
                        methodVisitor.visitMethodInsn(182, JSONSerializer, "isWriteClassName", "(Ljava/lang/reflect/Type;Ljava/lang/Object;)Z");
                        methodVisitor.visitJumpInsn(153, label2);
                        methodVisitor.visitVarInsn(25, 4);
                        methodVisitor.visitVarInsn(25, 2);
                        methodVisitor.visitMethodInsn(182, "java/lang/Object", "getClass", "()Ljava/lang/Class;");
                        methodVisitor.visitJumpInsn(165, label2);
                        methodVisitor.visitLabel((Label)object4);
                        methodVisitor.visitVarInsn(25, context.var("out"));
                        object4 = null;
                        if (context.jsonType != null) {
                            object4 = context.jsonType.typeName();
                        }
                        if (object4 == null) break block28;
                        object3 = object4;
                        if (((String)object4).length() != 0) break block29;
                    }
                    object3 = ((Class)object).getName();
                }
                methodVisitor.visitLdcInsn("{\"" + JSON.DEFAULT_TYPE_KEY + "\":\"" + (String)object3 + "\"");
                methodVisitor.visitMethodInsn(182, SerializeWriter, "write", "(Ljava/lang/String;)V");
                methodVisitor.visitVarInsn(16, 44);
                methodVisitor.visitJumpInsn(167, (Label)object5);
                methodVisitor.visitLabel(label2);
                methodVisitor.visitVarInsn(16, 123);
                methodVisitor.visitLabel((Label)object5);
                break block30;
            }
            methodVisitor.visitVarInsn(16, 123);
        }
        methodVisitor.visitVarInsn(54, context.var("seperator"));
        if (!context.writeDirect) {
            this._before(methodVisitor, context);
        }
        if (!context.writeDirect) {
            methodVisitor.visitVarInsn(25, context.var("out"));
            methodVisitor.visitMethodInsn(182, SerializeWriter, "isNotWriteDefaultValue", "()Z");
            methodVisitor.visitVarInsn(54, context.var("notWriteDefaultValue"));
            methodVisitor.visitVarInsn(25, 1);
            methodVisitor.visitVarInsn(25, 0);
            methodVisitor.visitMethodInsn(182, JSONSerializer, "checkValue", "(" + SerializeFilterable_desc + ")Z");
            methodVisitor.visitVarInsn(54, context.var("checkValue"));
            methodVisitor.visitVarInsn(25, 1);
            methodVisitor.visitVarInsn(25, 0);
            methodVisitor.visitMethodInsn(182, JSONSerializer, "hasNameFilters", "(" + SerializeFilterable_desc + ")Z");
            methodVisitor.visitVarInsn(54, context.var("hasNameFilters"));
        }
        for (n2 = 0; n2 < n3; ++n2) {
            object4 = (FieldInfo)object2.get(n2);
            object3 = ((FieldInfo)object4).fieldClass;
            methodVisitor.visitLdcInsn(((FieldInfo)object4).name);
            methodVisitor.visitVarInsn(58, Context.fieldName);
            if (object3 == Byte.TYPE || object3 == Short.TYPE || object3 == Integer.TYPE) {
                this._int((Class<?>)object, methodVisitor, (FieldInfo)object4, context, context.var(((Class)object3).getName()), 'I');
                continue;
            }
            if (object3 == Long.TYPE) {
                this._long((Class<?>)object, methodVisitor, (FieldInfo)object4, context);
                continue;
            }
            if (object3 == Float.TYPE) {
                this._float((Class<?>)object, methodVisitor, (FieldInfo)object4, context);
                continue;
            }
            if (object3 == Double.TYPE) {
                this._double((Class<?>)object, methodVisitor, (FieldInfo)object4, context);
                continue;
            }
            if (object3 == Boolean.TYPE) {
                this._int((Class<?>)object, methodVisitor, (FieldInfo)object4, context, context.var("boolean"), 'Z');
                continue;
            }
            if (object3 == Character.TYPE) {
                this._int((Class<?>)object, methodVisitor, (FieldInfo)object4, context, context.var("char"), 'C');
                continue;
            }
            if (object3 == String.class) {
                this._string((Class<?>)object, methodVisitor, (FieldInfo)object4, context);
                continue;
            }
            if (object3 == BigDecimal.class) {
                this._decimal((Class<?>)object, methodVisitor, (FieldInfo)object4, context);
                continue;
            }
            if (List.class.isAssignableFrom((Class<?>)object3)) {
                this._list((Class<?>)object, methodVisitor, (FieldInfo)object4, context);
                continue;
            }
            if (((Class)object3).isEnum()) {
                this._enum((Class<?>)object, methodVisitor, (FieldInfo)object4, context);
                continue;
            }
            this._object((Class<?>)object, methodVisitor, (FieldInfo)object4, context);
        }
        if (!context.writeDirect) {
            this._after(methodVisitor, context);
        }
        object = new Label();
        object2 = new Label();
        methodVisitor.visitVarInsn(21, context.var("seperator"));
        methodVisitor.visitIntInsn(16, 123);
        methodVisitor.visitJumpInsn(160, (Label)object);
        methodVisitor.visitVarInsn(25, context.var("out"));
        methodVisitor.visitVarInsn(16, 123);
        methodVisitor.visitMethodInsn(182, SerializeWriter, "write", "(I)V");
        methodVisitor.visitLabel((Label)object);
        methodVisitor.visitVarInsn(25, context.var("out"));
        methodVisitor.visitVarInsn(16, 125);
        methodVisitor.visitMethodInsn(182, SerializeWriter, "write", "(I)V");
        methodVisitor.visitLabel((Label)object2);
        methodVisitor.visitLabel(label);
        if (!context.nonContext) {
            methodVisitor.visitVarInsn(25, 1);
            methodVisitor.visitVarInsn(25, context.var("parent"));
            methodVisitor.visitMethodInsn(182, JSONSerializer, "setContext", "(" + SerialContext_desc + ")V");
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public ObjectSerializer createJavaBeanSerializer(Class<?> object, Map<String, String> object2) throws Exception {
        boolean bl2;
        boolean bl3;
        Object object3;
        int n2;
        Object object4;
        if (((Class)object).isPrimitive()) {
            throw new JSONException("unsupportd class " + ((Class)object).getName());
        }
        JSONType jSONType = ((Class)object).getAnnotation(JSONType.class);
        Object object5 = TypeUtils.computeGetters(object, jSONType, (Map<String, String>)object2, false);
        Object object6 = object5.iterator();
        while (object6.hasNext()) {
            object4 = object6.next();
            if (((FieldInfo)object4).field != null || ((FieldInfo)object4).method == null || !((FieldInfo)object4).method.getDeclaringClass().isInterface()) continue;
            return new JavaBeanSerializer((Class<?>)object);
        }
        object6 = null;
        if (jSONType != null) {
            object6 = jSONType.orders();
        }
        if (object6 != null && ((String[])object6).length != 0) {
            object6 = TypeUtils.computeGetters(object, jSONType, (Map<String, String>)object2, true);
        } else {
            object6 = new ArrayList<FieldInfo>((Collection<FieldInfo>)object5);
            Collections.sort(object6);
        }
        int n3 = 1;
        int n4 = 0;
        int n5 = object5.size();
        while (true) {
            block34: {
                block33: {
                    n2 = n3;
                    if (n4 >= n5) break block33;
                    if (object5.get(n4).equals(object6.get(n4))) break block34;
                    n2 = 0;
                }
                if (object6.size() <= 256) break;
                return null;
            }
            ++n4;
        }
        object2 = object6.iterator();
        while (object2.hasNext()) {
            if (ASMUtils.checkName(((FieldInfo)object2.next()).getMember().getName())) continue;
            return null;
        }
        object2 = "ASMSerializer_" + this.seed.incrementAndGet() + "_" + ((Class)object).getSimpleName();
        String string2 = ASMSerializerFactory.class.getPackage().getName();
        object4 = string2.replace('.', '/') + "/" + (String)object2;
        string2 = string2 + "." + (String)object2;
        n3 = TypeUtils.getSerializeFeatures(object);
        ClassWriter classWriter = new ClassWriter();
        classWriter.visit(49, 33, (String)object4, JavaBeanSerializer, new String[]{ObjectSerializer});
        object2 = object6.iterator();
        while (object2.hasNext()) {
            object3 = (FieldInfo)object2.next();
            if (((FieldInfo)object3).fieldClass.isPrimitive() || ((FieldInfo)object3).fieldClass.isEnum() || ((FieldInfo)object3).fieldClass == String.class) continue;
            new FieldWriter(classWriter, 1, ((FieldInfo)object3).name + "_asm_fieldType", "Ljava/lang/reflect/Type;").visitEnd();
            if (List.class.isAssignableFrom(((FieldInfo)object3).fieldClass)) {
                new FieldWriter(classWriter, 1, ((FieldInfo)object3).name + "_asm_list_item_ser_", ObjectSerializer_desc).visitEnd();
            }
            new FieldWriter(classWriter, 1, ((FieldInfo)object3).name + "_asm_ser_", ObjectSerializer_desc).visitEnd();
        }
        object2 = new MethodWriter(classWriter, 1, "<init>", "()V", null, null);
        object2.visitVarInsn(25, 0);
        object2.visitLdcInsn(Type.getType(ASMUtils.desc(object)));
        object2.visitMethodInsn(183, JavaBeanSerializer, "<init>", "(Ljava/lang/Class;)V");
        for (n4 = 0; n4 < object6.size(); ++n4) {
            object3 = (FieldInfo)object6.get(n4);
            if (((FieldInfo)object3).fieldClass.isPrimitive() || ((FieldInfo)object3).fieldClass.isEnum() || ((FieldInfo)object3).fieldClass == String.class) continue;
            object2.visitVarInsn(25, 0);
            if (((FieldInfo)object3).method != null) {
                object2.visitLdcInsn(Type.getType(ASMUtils.desc(((FieldInfo)object3).declaringClass)));
                object2.visitLdcInsn(((FieldInfo)object3).method.getName());
                object2.visitMethodInsn(184, ASMUtils.type(ASMUtils.class), "getMethodType", "(Ljava/lang/Class;Ljava/lang/String;)Ljava/lang/reflect/Type;");
            } else {
                object2.visitVarInsn(25, 0);
                object2.visitLdcInsn(n4);
                object2.visitMethodInsn(183, JavaBeanSerializer, "getFieldType", "(I)Ljava/lang/reflect/Type;");
            }
            object2.visitFieldInsn(181, (String)object4, ((FieldInfo)object3).name + "_asm_fieldType", "Ljava/lang/reflect/Type;");
        }
        object2.visitInsn(177);
        object2.visitMaxs(4, 4);
        object2.visitEnd();
        boolean bl4 = bl3 = false;
        if (jSONType != null) {
            object2 = jSONType.serialzeFeatures();
            n5 = ((Object)object2).length;
            n4 = 0;
            while (true) {
                bl4 = bl3;
                if (n4 >= n5) break;
                if (object2[n4] == SerializerFeature.DisableCircularReferenceDetect) {
                    bl4 = true;
                    break;
                }
                ++n4;
            }
        }
        for (n4 = 0; n4 < 3; ++n4) {
            Label label;
            bl2 = bl4;
            bl3 = false;
            if (n4 == 0) {
                object2 = "write";
                bl3 = true;
            } else if (n4 == 1) {
                object2 = "writeNormal";
            } else {
                bl3 = true;
                bl2 = true;
                object2 = "writeDirectNonContext";
            }
            object3 = new Context((List<FieldInfo>)object6, jSONType, (String)object4, n3, bl3, bl2);
            object2 = new MethodWriter(classWriter, 1, (String)object2, "(L" + JSONSerializer + ";Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V", null, new String[]{"java/io/IOException"});
            object2.visitVarInsn(25, 1);
            object2.visitFieldInsn(180, JSONSerializer, "out", SerializeWriter_desc);
            object2.visitVarInsn(58, ((Context)object3).var("out"));
            if (n2 == 0 && !((Context)object3).writeDirect && (jSONType == null || jSONType.alphabetic())) {
                label = new Label();
                object2.visitVarInsn(25, ((Context)object3).var("out"));
                object2.visitMethodInsn(182, SerializeWriter, "isSortField", "()Z");
                object2.visitJumpInsn(154, label);
                object2.visitVarInsn(25, 0);
                object2.visitVarInsn(25, 1);
                object2.visitVarInsn(25, 2);
                object2.visitVarInsn(25, 3);
                object2.visitVarInsn(25, 4);
                object2.visitVarInsn(21, 5);
                object2.visitMethodInsn(182, (String)object4, "writeUnsorted", "(L" + JSONSerializer + ";Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V");
                object2.visitInsn(177);
                object2.visitLabel(label);
            }
            if (((Context)object3).writeDirect && !bl2) {
                label = new Label();
                Label label2 = new Label();
                object2.visitVarInsn(25, 1);
                object2.visitVarInsn(25, 0);
                object2.visitMethodInsn(182, JSONSerializer, "writeDirect", "(" + JavaBeanSerializer_desc + ")Z");
                object2.visitJumpInsn(154, label2);
                object2.visitVarInsn(25, 0);
                object2.visitVarInsn(25, 1);
                object2.visitVarInsn(25, 2);
                object2.visitVarInsn(25, 3);
                object2.visitVarInsn(25, 4);
                object2.visitVarInsn(21, 5);
                object2.visitMethodInsn(182, (String)object4, "writeNormal", "(L" + JSONSerializer + ";Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V");
                object2.visitInsn(177);
                object2.visitLabel(label2);
                object2.visitVarInsn(25, ((Context)object3).var("out"));
                object2.visitLdcInsn(SerializerFeature.DisableCircularReferenceDetect.mask);
                object2.visitMethodInsn(182, SerializeWriter, "isEnabled", "(I)Z");
                object2.visitJumpInsn(153, label);
                object2.visitVarInsn(25, 0);
                object2.visitVarInsn(25, 1);
                object2.visitVarInsn(25, 2);
                object2.visitVarInsn(25, 3);
                object2.visitVarInsn(25, 4);
                object2.visitVarInsn(21, 5);
                object2.visitMethodInsn(182, (String)object4, "writeDirectNonContext", "(L" + JSONSerializer + ";Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V");
                object2.visitInsn(177);
                object2.visitLabel(label);
            }
            object2.visitVarInsn(25, 2);
            object2.visitTypeInsn(192, ASMUtils.type(object));
            object2.visitVarInsn(58, ((Context)object3).var("entity"));
            this.generateWriteMethod((Class<?>)object, (MethodVisitor)object2, (List<FieldInfo>)object6, (Context)object3);
            object2.visitInsn(177);
            object2.visitMaxs(7, ((Context)object3).variantIndex + 2);
            object2.visitEnd();
        }
        if (n2 == 0) {
            object2 = new Context((List<FieldInfo>)object6, jSONType, (String)object4, n3, false, bl4);
            object3 = new MethodWriter(classWriter, 1, "writeUnsorted", "(L" + JSONSerializer + ";Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V", null, new String[]{"java/io/IOException"});
            object3.visitVarInsn(25, 1);
            object3.visitFieldInsn(180, JSONSerializer, "out", SerializeWriter_desc);
            object3.visitVarInsn(58, ((Context)object2).var("out"));
            object3.visitVarInsn(25, 2);
            object3.visitTypeInsn(192, ASMUtils.type(object));
            object3.visitVarInsn(58, ((Context)object2).var("entity"));
            this.generateWriteMethod((Class<?>)object, (MethodVisitor)object3, (List<FieldInfo>)object5, (Context)object2);
            object3.visitInsn(177);
            object3.visitMaxs(7, ((Context)object2).variantIndex + 2);
            object3.visitEnd();
        }
        n2 = 0;
        while (true) {
            if (n2 >= 3) {
                object = classWriter.toByteArray();
                return (ObjectSerializer)this.classLoader.defineClassPublic(string2, (byte[])object, 0, ((Object)object).length).newInstance();
            }
            bl2 = bl4;
            bl3 = false;
            if (n2 == 0) {
                object2 = "writeAsArray";
                bl3 = true;
            } else if (n2 == 1) {
                object2 = "writeAsArrayNormal";
            } else {
                bl3 = true;
                bl2 = true;
                object2 = "writeAsArrayNonContext";
            }
            object5 = new Context((List<FieldInfo>)object6, jSONType, (String)object4, n3, bl3, bl2);
            object2 = new MethodWriter(classWriter, 1, (String)object2, "(L" + JSONSerializer + ";Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V", null, new String[]{"java/io/IOException"});
            object2.visitVarInsn(25, 1);
            object2.visitFieldInsn(180, JSONSerializer, "out", SerializeWriter_desc);
            object2.visitVarInsn(58, ((Context)object5).var("out"));
            object2.visitVarInsn(25, 2);
            object2.visitTypeInsn(192, ASMUtils.type(object));
            object2.visitVarInsn(58, ((Context)object5).var("entity"));
            this.generateWriteAsArray((Class<?>)object, (MethodVisitor)object2, (List<FieldInfo>)object6, (Context)object5);
            object2.visitInsn(177);
            object2.visitMaxs(7, ((Context)object5).variantIndex + 2);
            object2.visitEnd();
            ++n2;
        }
    }

    static class Context {
        static final int features = 5;
        static int fieldName = 0;
        static final int obj = 2;
        static int original = 0;
        static final int paramFieldName = 3;
        static final int paramFieldType = 4;
        static int processValue = 0;
        static final int serializer = 1;
        private final int beanFeatures;
        private final int beanSerializeFeatures;
        private final String className;
        private final List<FieldInfo> getters;
        private final JSONType jsonType;
        private boolean nonContext;
        private int variantIndex = 9;
        private Map<String, Integer> variants = new HashMap<String, Integer>();
        private final boolean writeDirect;

        static {
            fieldName = 6;
            original = 7;
            processValue = 8;
        }

        /*
         * Enabled aggressive block sorting
         */
        public Context(List<FieldInfo> list, JSONType jSONType, String string2, int n2, boolean bl2, boolean bl3) {
            this.getters = list;
            this.jsonType = jSONType;
            this.className = string2;
            this.beanSerializeFeatures = n2;
            this.writeDirect = bl2;
            this.nonContext = bl3;
            if (this.writeDirect) {
                processValue = 8;
            }
            n2 = jSONType != null ? SerializerFeature.of(jSONType.serialzeFeatures()) : 0;
            this.beanFeatures = n2;
        }

        public int getFieldOrinal(String string2) {
            int n2 = -1;
            int n3 = 0;
            int n4 = this.getters.size();
            while (true) {
                block4: {
                    int n5;
                    block3: {
                        n5 = n2;
                        if (n3 >= n4) break block3;
                        if (!this.getters.get((int)n3).name.equals(string2)) break block4;
                        n5 = n3;
                    }
                    return n5;
                }
                ++n3;
            }
        }

        public int var(String string2) {
            if (this.variants.get(string2) == null) {
                Map<String, Integer> map = this.variants;
                int n2 = this.variantIndex;
                this.variantIndex = n2 + 1;
                map.put(string2, n2);
            }
            return this.variants.get(string2);
        }

        public int var(String string2, int n2) {
            if (this.variants.get(string2) == null) {
                this.variants.put(string2, this.variantIndex);
                this.variantIndex += n2;
            }
            return this.variants.get(string2);
        }
    }
}

