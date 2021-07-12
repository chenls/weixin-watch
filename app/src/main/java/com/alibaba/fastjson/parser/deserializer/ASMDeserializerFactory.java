/*
 * Decompiled with CFR 0.151.
 */
package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.asm.ClassWriter;
import com.alibaba.fastjson.asm.FieldWriter;
import com.alibaba.fastjson.asm.Label;
import com.alibaba.fastjson.asm.MethodVisitor;
import com.alibaba.fastjson.asm.MethodWriter;
import com.alibaba.fastjson.asm.Opcodes;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.parser.JSONLexerBase;
import com.alibaba.fastjson.parser.ParseContext;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.parser.SymbolTable;
import com.alibaba.fastjson.parser.deserializer.EnumDeserializer;
import com.alibaba.fastjson.parser.deserializer.FieldDeserializer;
import com.alibaba.fastjson.parser.deserializer.JavaBeanDeserializer;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.util.ASMClassLoader;
import com.alibaba.fastjson.util.ASMUtils;
import com.alibaba.fastjson.util.FieldInfo;
import com.alibaba.fastjson.util.JavaBeanInfo;
import com.alibaba.fastjson.util.TypeUtils;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicLong;

public class ASMDeserializerFactory
implements Opcodes {
    static final String DefaultJSONParser = ASMUtils.type(DefaultJSONParser.class);
    static final String JSONLexerBase = ASMUtils.type(JSONLexerBase.class);
    public final ASMClassLoader classLoader;
    protected final AtomicLong seed = new AtomicLong();

    /*
     * Enabled aggressive block sorting
     */
    public ASMDeserializerFactory(ClassLoader classLoader) {
        classLoader = classLoader instanceof ASMClassLoader ? (ASMClassLoader)classLoader : new ASMClassLoader(classLoader);
        this.classLoader = classLoader;
    }

    private void _batchSet(Context context, MethodVisitor methodVisitor) {
        this._batchSet(context, methodVisitor, true);
    }

    private void _batchSet(Context context, MethodVisitor methodVisitor, boolean bl2) {
        int n2 = context.fieldInfoList.length;
        for (int i2 = 0; i2 < n2; ++i2) {
            Label label = new Label();
            if (bl2) {
                this._isFlag(methodVisitor, context, i2, label);
            }
            this._loadAndSet(context, methodVisitor, context.fieldInfoList[i2]);
            if (!bl2) continue;
            methodVisitor.visitLabel(label);
        }
    }

    private void _createInstance(ClassWriter object, Context context) {
        object = new MethodWriter((ClassWriter)object, 1, "createInstance", "(L" + DefaultJSONParser + ";Ljava/lang/reflect/Type;)Ljava/lang/Object;", null, null);
        object.visitTypeInsn(187, ASMUtils.type(context.getInstClass()));
        object.visitInsn(89);
        object.visitMethodInsn(183, ASMUtils.type(context.getInstClass()), "<init>", "()V");
        object.visitInsn(176);
        object.visitMaxs(3, 3);
        object.visitEnd();
    }

    private void _createInstance(Context context, MethodVisitor methodVisitor) {
        Constructor<?> constructor = ((Context)context).beanInfo.defaultConstructor;
        if (Modifier.isPublic(constructor.getModifiers())) {
            methodVisitor.visitTypeInsn(187, ASMUtils.type(context.getInstClass()));
            methodVisitor.visitInsn(89);
            methodVisitor.visitMethodInsn(183, ASMUtils.type(constructor.getDeclaringClass()), "<init>", "()V");
            methodVisitor.visitVarInsn(58, context.var("instance"));
            return;
        }
        methodVisitor.visitVarInsn(25, 0);
        methodVisitor.visitVarInsn(25, 1);
        methodVisitor.visitVarInsn(25, 0);
        methodVisitor.visitFieldInsn(180, ASMUtils.type(JavaBeanDeserializer.class), "clazz", "Ljava/lang/Class;");
        methodVisitor.visitMethodInsn(183, ASMUtils.type(JavaBeanDeserializer.class), "createInstance", "(L" + DefaultJSONParser + ";Ljava/lang/reflect/Type;)Ljava/lang/Object;");
        methodVisitor.visitTypeInsn(192, ASMUtils.type(context.getInstClass()));
        methodVisitor.visitVarInsn(58, context.var("instance"));
    }

    /*
     * Enabled aggressive block sorting
     */
    private void _deserObject(Context context, MethodVisitor methodVisitor, FieldInfo fieldInfo, Class<?> clazz, int n2) {
        this._getFieldDeser(context, methodVisitor, fieldInfo);
        methodVisitor.visitVarInsn(25, 1);
        if (fieldInfo.fieldType instanceof Class) {
            methodVisitor.visitLdcInsn(com.alibaba.fastjson.asm.Type.getType(ASMUtils.desc(fieldInfo.fieldClass)));
        } else {
            methodVisitor.visitVarInsn(25, 0);
            methodVisitor.visitLdcInsn(n2);
            methodVisitor.visitMethodInsn(182, ASMUtils.type(JavaBeanDeserializer.class), "getFieldType", "(I)Ljava/lang/reflect/Type;");
        }
        methodVisitor.visitLdcInsn(fieldInfo.name);
        methodVisitor.visitMethodInsn(185, ASMUtils.type(ObjectDeserializer.class), "deserialze", "(L" + DefaultJSONParser + ";Ljava/lang/reflect/Type;Ljava/lang/Object;)Ljava/lang/Object;");
        methodVisitor.visitTypeInsn(192, ASMUtils.type(clazz));
        methodVisitor.visitVarInsn(58, context.var(fieldInfo.name + "_asm"));
    }

    private void _deserialize_endCheck(Context context, MethodVisitor methodVisitor, Label label) {
        methodVisitor.visitIntInsn(21, context.var("matchedCount"));
        methodVisitor.visitJumpInsn(158, label);
        methodVisitor.visitVarInsn(25, context.var("lexer"));
        methodVisitor.visitMethodInsn(182, JSONLexerBase, "token", "()I");
        methodVisitor.visitLdcInsn(13);
        methodVisitor.visitJumpInsn(160, label);
        this._quickNextTokenComma(context, methodVisitor);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void _deserialze(ClassWriter object, Context context) {
        Class<?> clazz;
        Object object2;
        int n2;
        Object object3;
        if (context.fieldInfoList.length == 0) return;
        for (FieldInfo fieldInfo : context.fieldInfoList) {
            object3 = fieldInfo.fieldClass;
            Type type = fieldInfo.fieldType;
            if (object3 == Character.TYPE || Collection.class.isAssignableFrom((Class<?>)object3) && (!(type instanceof ParameterizedType) || !(((ParameterizedType)type).getActualTypeArguments()[0] instanceof Class))) return;
        }
        Object object4 = context.beanInfo;
        Context.access$202(context, ((JavaBeanInfo)object4).sortedFields);
        object = new MethodWriter((ClassWriter)object, 1, "deserialze", "(L" + DefaultJSONParser + ";Ljava/lang/reflect/Type;Ljava/lang/Object;)Ljava/lang/Object;", null, null);
        Label label = new Label();
        object3 = new Label();
        Label label2 = new Label();
        Label label3 = new Label();
        this.defineVarLexer(context, (MethodVisitor)object);
        Class<?> clazz2 = new Label();
        object.visitVarInsn(25, context.var("lexer"));
        object.visitMethodInsn(182, JSONLexerBase, "token", "()I");
        object.visitLdcInsn(14);
        object.visitJumpInsn(160, (Label)((Object)clazz2));
        if ((((JavaBeanInfo)object4).parserFeatures & Feature.SupportArrayToBean.mask) == 0) {
            object.visitVarInsn(25, context.var("lexer"));
            object.visitLdcInsn(Feature.SupportArrayToBean.mask);
            object.visitMethodInsn(182, JSONLexerBase, "isEnabled", "(I)Z");
            object.visitJumpInsn(153, (Label)((Object)clazz2));
        }
        object.visitVarInsn(25, 0);
        object.visitVarInsn(25, 1);
        object.visitVarInsn(25, 2);
        object.visitVarInsn(25, 3);
        object.visitVarInsn(25, 4);
        object.visitMethodInsn(183, context.className, "deserialzeArrayMapping", "(L" + DefaultJSONParser + ";Ljava/lang/reflect/Type;Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;");
        object.visitInsn(176);
        object.visitLabel((Label)((Object)clazz2));
        object.visitVarInsn(25, context.var("lexer"));
        object.visitLdcInsn(Feature.SortFeidFastMatch.mask);
        object.visitMethodInsn(182, JSONLexerBase, "isEnabled", "(I)Z");
        object.visitJumpInsn(153, (Label)object3);
        object.visitVarInsn(25, context.var("lexer"));
        object.visitLdcInsn(context.clazz.getName());
        object.visitMethodInsn(182, JSONLexerBase, "scanType", "(Ljava/lang/String;)I");
        object.visitLdcInsn(-1);
        object.visitJumpInsn(159, (Label)object3);
        object.visitVarInsn(25, 1);
        object.visitMethodInsn(182, DefaultJSONParser, "getContext", "()" + ASMUtils.desc(ParseContext.class));
        object.visitVarInsn(58, context.var("mark_context"));
        object.visitInsn(3);
        object.visitVarInsn(54, context.var("matchedCount"));
        this._createInstance(context, (MethodVisitor)object);
        object.visitVarInsn(25, 1);
        object.visitMethodInsn(182, DefaultJSONParser, "getContext", "()" + ASMUtils.desc(ParseContext.class));
        object.visitVarInsn(58, context.var("context"));
        object.visitVarInsn(25, 1);
        object.visitVarInsn(25, context.var("context"));
        object.visitVarInsn(25, context.var("instance"));
        object.visitVarInsn(25, 3);
        object.visitMethodInsn(182, DefaultJSONParser, "setContext", "(" + ASMUtils.desc(ParseContext.class) + "Ljava/lang/Object;Ljava/lang/Object;)" + ASMUtils.desc(ParseContext.class));
        object.visitVarInsn(58, context.var("childContext"));
        object.visitVarInsn(25, context.var("lexer"));
        object.visitFieldInsn(180, JSONLexerBase, "matchStat", "I");
        object.visitLdcInsn(4);
        object.visitJumpInsn(159, label2);
        object.visitInsn(3);
        object.visitIntInsn(54, context.var("matchStat"));
        int n3 = context.fieldInfoList.length;
        for (n2 = 0; n2 < n3; n2 += 32) {
            object.visitInsn(3);
            object.visitVarInsn(54, context.var("_asm_flag_" + n2 / 32));
        }
        object.visitVarInsn(25, context.var("lexer"));
        object.visitLdcInsn(Feature.InitStringFieldAsEmpty.mask);
        object.visitMethodInsn(182, JSONLexerBase, "isEnabled", "(I)Z");
        object.visitIntInsn(54, context.var("initStringFieldAsEmpty"));
        for (n2 = 0; n2 < n3; ++n2) {
            object4 = context.fieldInfoList[n2];
            clazz2 = ((FieldInfo)object4).fieldClass;
            if (clazz2 == Boolean.TYPE || clazz2 == Byte.TYPE || clazz2 == Short.TYPE || clazz2 == Integer.TYPE) {
                object.visitInsn(3);
                object.visitVarInsn(54, context.var(((FieldInfo)object4).name + "_asm"));
                continue;
            }
            if (clazz2 == Long.TYPE) {
                object.visitInsn(9);
                object.visitVarInsn(55, context.var(((FieldInfo)object4).name + "_asm", 2));
                continue;
            }
            if (clazz2 == Float.TYPE) {
                object.visitInsn(11);
                object.visitVarInsn(56, context.var(((FieldInfo)object4).name + "_asm"));
                continue;
            }
            if (clazz2 == Double.TYPE) {
                object.visitInsn(14);
                object.visitVarInsn(57, context.var(((FieldInfo)object4).name + "_asm", 2));
                continue;
            }
            if (clazz2 == String.class) {
                object2 = new Label();
                clazz = new Label();
                object.visitVarInsn(21, context.var("initStringFieldAsEmpty"));
                object.visitJumpInsn(153, (Label)((Object)clazz));
                this._setFlag((MethodVisitor)object, context, n2);
                object.visitVarInsn(25, context.var("lexer"));
                object.visitMethodInsn(182, JSONLexerBase, "stringDefaultValue", "()Ljava/lang/String;");
                object.visitJumpInsn(167, (Label)object2);
                object.visitLabel((Label)((Object)clazz));
                object.visitInsn(1);
                object.visitLabel((Label)object2);
            } else {
                object.visitInsn(1);
            }
            object.visitTypeInsn(192, ASMUtils.type(clazz2));
            object.visitVarInsn(58, context.var(((FieldInfo)object4).name + "_asm"));
        }
        for (n2 = 0; n2 < n3; ++n2) {
            block28: {
                block29: {
                    block19: {
                        block27: {
                            block26: {
                                block25: {
                                    block24: {
                                        block23: {
                                            block22: {
                                                block21: {
                                                    block20: {
                                                        block18: {
                                                            clazz2 = context.fieldInfoList[n2];
                                                            object2 = ((FieldInfo)clazz2).fieldClass;
                                                            clazz = ((FieldInfo)clazz2).fieldType;
                                                            object4 = new Label();
                                                            if (object2 != Boolean.TYPE) break block18;
                                                            object.visitVarInsn(25, context.var("lexer"));
                                                            object.visitVarInsn(25, 0);
                                                            object.visitFieldInsn(180, context.className, ((FieldInfo)clazz2).name + "_asm_prefix__", "[C");
                                                            object.visitMethodInsn(182, JSONLexerBase, "scanFieldBoolean", "([C)Z");
                                                            object.visitVarInsn(54, context.var(((FieldInfo)clazz2).name + "_asm"));
                                                            break block19;
                                                        }
                                                        if (object2 != Byte.TYPE) break block20;
                                                        object.visitVarInsn(25, context.var("lexer"));
                                                        object.visitVarInsn(25, 0);
                                                        object.visitFieldInsn(180, context.className, ((FieldInfo)clazz2).name + "_asm_prefix__", "[C");
                                                        object.visitMethodInsn(182, JSONLexerBase, "scanFieldInt", "([C)I");
                                                        object.visitVarInsn(54, context.var(((FieldInfo)clazz2).name + "_asm"));
                                                        break block19;
                                                    }
                                                    if (object2 != Short.TYPE) break block21;
                                                    object.visitVarInsn(25, context.var("lexer"));
                                                    object.visitVarInsn(25, 0);
                                                    object.visitFieldInsn(180, context.className, ((FieldInfo)clazz2).name + "_asm_prefix__", "[C");
                                                    object.visitMethodInsn(182, JSONLexerBase, "scanFieldInt", "([C)I");
                                                    object.visitVarInsn(54, context.var(((FieldInfo)clazz2).name + "_asm"));
                                                    break block19;
                                                }
                                                if (object2 != Integer.TYPE) break block22;
                                                object.visitVarInsn(25, context.var("lexer"));
                                                object.visitVarInsn(25, 0);
                                                object.visitFieldInsn(180, context.className, ((FieldInfo)clazz2).name + "_asm_prefix__", "[C");
                                                object.visitMethodInsn(182, JSONLexerBase, "scanFieldInt", "([C)I");
                                                object.visitVarInsn(54, context.var(((FieldInfo)clazz2).name + "_asm"));
                                                break block19;
                                            }
                                            if (object2 != Long.TYPE) break block23;
                                            object.visitVarInsn(25, context.var("lexer"));
                                            object.visitVarInsn(25, 0);
                                            object.visitFieldInsn(180, context.className, ((FieldInfo)clazz2).name + "_asm_prefix__", "[C");
                                            object.visitMethodInsn(182, JSONLexerBase, "scanFieldLong", "([C)J");
                                            object.visitVarInsn(55, context.var(((FieldInfo)clazz2).name + "_asm", 2));
                                            break block19;
                                        }
                                        if (object2 != Float.TYPE) break block24;
                                        object.visitVarInsn(25, context.var("lexer"));
                                        object.visitVarInsn(25, 0);
                                        object.visitFieldInsn(180, context.className, ((FieldInfo)clazz2).name + "_asm_prefix__", "[C");
                                        object.visitMethodInsn(182, JSONLexerBase, "scanFieldFloat", "([C)F");
                                        object.visitVarInsn(56, context.var(((FieldInfo)clazz2).name + "_asm"));
                                        break block19;
                                    }
                                    if (object2 != Double.TYPE) break block25;
                                    object.visitVarInsn(25, context.var("lexer"));
                                    object.visitVarInsn(25, 0);
                                    object.visitFieldInsn(180, context.className, ((FieldInfo)clazz2).name + "_asm_prefix__", "[C");
                                    object.visitMethodInsn(182, JSONLexerBase, "scanFieldDouble", "([C)D");
                                    object.visitVarInsn(57, context.var(((FieldInfo)clazz2).name + "_asm", 2));
                                    break block19;
                                }
                                if (object2 != String.class) break block26;
                                object.visitVarInsn(25, context.var("lexer"));
                                object.visitVarInsn(25, 0);
                                object.visitFieldInsn(180, context.className, ((FieldInfo)clazz2).name + "_asm_prefix__", "[C");
                                object.visitMethodInsn(182, JSONLexerBase, "scanFieldString", "([C)Ljava/lang/String;");
                                object.visitVarInsn(58, context.var(((FieldInfo)clazz2).name + "_asm"));
                                break block19;
                            }
                            if (!((Class)object2).isEnum()) break block27;
                            object.visitVarInsn(25, context.var("lexer"));
                            object.visitVarInsn(25, 0);
                            object.visitFieldInsn(180, context.className, ((FieldInfo)clazz2).name + "_asm_prefix__", "[C");
                            clazz = new Label();
                            object.visitInsn(1);
                            object.visitTypeInsn(192, ASMUtils.type(object2));
                            object.visitVarInsn(58, context.var(((FieldInfo)clazz2).name + "_asm"));
                            object.visitVarInsn(25, 1);
                            object.visitMethodInsn(182, DefaultJSONParser, "getSymbolTable", "()" + ASMUtils.desc(SymbolTable.class));
                            object.visitMethodInsn(182, JSONLexerBase, "scanFieldSymbol", "([C" + ASMUtils.desc(SymbolTable.class) + ")Ljava/lang/String;");
                            object.visitInsn(89);
                            object.visitVarInsn(58, context.var(((FieldInfo)clazz2).name + "_asm_enumName"));
                            object.visitJumpInsn(198, (Label)((Object)clazz));
                            object.visitVarInsn(25, context.var(((FieldInfo)clazz2).name + "_asm_enumName"));
                            object.visitMethodInsn(184, ASMUtils.type(object2), "valueOf", "(Ljava/lang/String;)" + ASMUtils.desc(object2));
                            object.visitVarInsn(58, context.var(((FieldInfo)clazz2).name + "_asm"));
                            object.visitLabel((Label)((Object)clazz));
                            break block19;
                        }
                        if (!Collection.class.isAssignableFrom((Class<?>)object2)) break block28;
                        object.visitVarInsn(25, context.var("lexer"));
                        object.visitVarInsn(25, 0);
                        object.visitFieldInsn(180, context.className, ((FieldInfo)clazz2).name + "_asm_prefix__", "[C");
                        clazz = TypeUtils.getCollectionItemClass(clazz);
                        if (clazz != String.class) break block29;
                        object.visitLdcInsn(com.alibaba.fastjson.asm.Type.getType(ASMUtils.desc(object2)));
                        object.visitMethodInsn(182, JSONLexerBase, "scanFieldStringArray", "([CLjava/lang/Class;)" + ASMUtils.desc(Collection.class));
                        object.visitVarInsn(58, context.var(((FieldInfo)clazz2).name + "_asm"));
                    }
                    object.visitVarInsn(25, context.var("lexer"));
                    object.visitFieldInsn(180, JSONLexerBase, "matchStat", "I");
                    clazz2 = new Label();
                    object.visitJumpInsn(158, (Label)((Object)clazz2));
                    this._setFlag((MethodVisitor)object, context, n2);
                    object.visitLabel((Label)((Object)clazz2));
                    object.visitVarInsn(25, context.var("lexer"));
                    object.visitFieldInsn(180, JSONLexerBase, "matchStat", "I");
                    object.visitInsn(89);
                    object.visitVarInsn(54, context.var("matchStat"));
                    object.visitLdcInsn(-1);
                    object.visitJumpInsn(159, label);
                    object.visitVarInsn(25, context.var("lexer"));
                    object.visitFieldInsn(180, JSONLexerBase, "matchStat", "I");
                    object.visitJumpInsn(158, (Label)object4);
                    object.visitVarInsn(21, context.var("matchedCount"));
                    object.visitInsn(4);
                    object.visitInsn(96);
                    object.visitVarInsn(54, context.var("matchedCount"));
                    object.visitVarInsn(25, context.var("lexer"));
                    object.visitFieldInsn(180, JSONLexerBase, "matchStat", "I");
                    object.visitLdcInsn(4);
                    object.visitJumpInsn(159, label3);
                    object.visitLabel((Label)object4);
                    if (n2 != n3 - 1) continue;
                    object.visitVarInsn(25, context.var("lexer"));
                    object.visitFieldInsn(180, JSONLexerBase, "matchStat", "I");
                    object.visitLdcInsn(4);
                    object.visitJumpInsn(160, label);
                    continue;
                }
                this._deserialze_list_obj(context, (MethodVisitor)object, label, (FieldInfo)((Object)clazz2), (Class<?>)object2, clazz, n2);
                if (n2 != n3 - 1) continue;
                this._deserialize_endCheck(context, (MethodVisitor)object, label);
                continue;
            }
            this._deserialze_obj(context, (MethodVisitor)object, label, (FieldInfo)((Object)clazz2), (Class<?>)object2, n2);
            if (n2 != n3 - 1) continue;
            this._deserialize_endCheck(context, (MethodVisitor)object, label);
        }
        object.visitLabel(label3);
        if (!context.clazz.isInterface() && !Modifier.isAbstract(context.clazz.getModifiers())) {
            this._batchSet(context, (MethodVisitor)object);
        }
        object.visitLabel(label2);
        this._setContext(context, (MethodVisitor)object);
        object.visitVarInsn(25, context.var("instance"));
        Method method = ((Context)context).beanInfo.buildMethod;
        if (method != null) {
            object.visitMethodInsn(182, ASMUtils.type(context.getInstClass()), method.getName(), "()" + ASMUtils.desc(method.getReturnType()));
        }
        object.visitInsn(176);
        object.visitLabel(label);
        this._batchSet(context, (MethodVisitor)object);
        object.visitVarInsn(25, 0);
        object.visitVarInsn(25, 1);
        object.visitVarInsn(25, 2);
        object.visitVarInsn(25, 3);
        object.visitVarInsn(25, context.var("instance"));
        object.visitMethodInsn(182, ASMUtils.type(JavaBeanDeserializer.class), "parseRest", "(L" + DefaultJSONParser + ";Ljava/lang/reflect/Type;Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;");
        object.visitTypeInsn(192, ASMUtils.type(context.clazz));
        object.visitInsn(176);
        object.visitLabel((Label)object3);
        object.visitVarInsn(25, 0);
        object.visitVarInsn(25, 1);
        object.visitVarInsn(25, 2);
        object.visitVarInsn(25, 3);
        object.visitMethodInsn(183, ASMUtils.type(JavaBeanDeserializer.class), "deserialze", "(L" + DefaultJSONParser + ";Ljava/lang/reflect/Type;Ljava/lang/Object;)Ljava/lang/Object;");
        object.visitInsn(176);
        object.visitMaxs(5, context.variantIndex);
        object.visitEnd();
    }

    /*
     * Enabled aggressive block sorting
     */
    private void _deserialzeArrayMapping(ClassWriter object, Context context) {
        object = new MethodWriter((ClassWriter)object, 1, "deserialzeArrayMapping", "(L" + DefaultJSONParser + ";Ljava/lang/reflect/Type;Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", null, null);
        this.defineVarLexer(context, (MethodVisitor)object);
        this._createInstance(context, (MethodVisitor)object);
        FieldInfo[] fieldInfoArray = ((Context)context).beanInfo.sortedFields;
        int n2 = fieldInfoArray.length;
        int n3 = 0;
        while (true) {
            Label label;
            Label label2;
            Object object2;
            Object object3;
            Object object4;
            if (n3 >= n2) {
                this._batchSet(context, (MethodVisitor)object, false);
                Label label3 = new Label();
                object4 = new Label();
                object3 = new Label();
                object2 = new Label();
                object.visitVarInsn(25, context.var("lexer"));
                object.visitMethodInsn(182, JSONLexerBase, "getCurrent", "()C");
                object.visitInsn(89);
                object.visitVarInsn(54, context.var("ch"));
                object.visitVarInsn(16, 44);
                object.visitJumpInsn(160, (Label)object4);
                object.visitVarInsn(25, context.var("lexer"));
                object.visitMethodInsn(182, JSONLexerBase, "next", "()C");
                object.visitInsn(87);
                object.visitVarInsn(25, context.var("lexer"));
                object.visitLdcInsn(16);
                object.visitMethodInsn(182, JSONLexerBase, "setToken", "(I)V");
                object.visitJumpInsn(167, (Label)object2);
                object.visitLabel((Label)object4);
                object.visitVarInsn(21, context.var("ch"));
                object.visitVarInsn(16, 93);
                object.visitJumpInsn(160, (Label)object3);
                object.visitVarInsn(25, context.var("lexer"));
                object.visitMethodInsn(182, JSONLexerBase, "next", "()C");
                object.visitInsn(87);
                object.visitVarInsn(25, context.var("lexer"));
                object.visitLdcInsn(15);
                object.visitMethodInsn(182, JSONLexerBase, "setToken", "(I)V");
                object.visitJumpInsn(167, (Label)object2);
                object.visitLabel((Label)object3);
                object.visitVarInsn(21, context.var("ch"));
                object.visitVarInsn(16, 26);
                object.visitJumpInsn(160, label3);
                object.visitVarInsn(25, context.var("lexer"));
                object.visitMethodInsn(182, JSONLexerBase, "next", "()C");
                object.visitInsn(87);
                object.visitVarInsn(25, context.var("lexer"));
                object.visitLdcInsn(20);
                object.visitMethodInsn(182, JSONLexerBase, "setToken", "(I)V");
                object.visitJumpInsn(167, (Label)object2);
                object.visitLabel(label3);
                object.visitVarInsn(25, context.var("lexer"));
                object.visitLdcInsn(16);
                object.visitMethodInsn(182, JSONLexerBase, "nextToken", "(I)V");
                object.visitLabel((Label)object2);
                object.visitVarInsn(25, context.var("instance"));
                object.visitInsn(176);
                object.visitMaxs(5, context.variantIndex);
                object.visitEnd();
                return;
            }
            int n4 = n3 == n2 - 1 ? 1 : 0;
            int n5 = n4 != 0 ? 93 : 44;
            object4 = fieldInfoArray[n3];
            object3 = ((FieldInfo)object4).fieldClass;
            object2 = ((FieldInfo)object4).fieldType;
            if (object3 == Byte.TYPE || object3 == Short.TYPE || object3 == Integer.TYPE) {
                object.visitVarInsn(25, context.var("lexer"));
                object.visitVarInsn(16, n5);
                object.visitMethodInsn(182, JSONLexerBase, "scanInt", "(C)I");
                object.visitVarInsn(54, context.var(((FieldInfo)object4).name + "_asm"));
            } else if (object3 == Long.TYPE) {
                object.visitVarInsn(25, context.var("lexer"));
                object.visitVarInsn(16, n5);
                object.visitMethodInsn(182, JSONLexerBase, "scanLong", "(C)J");
                object.visitVarInsn(55, context.var(((FieldInfo)object4).name + "_asm", 2));
            } else if (object3 == Boolean.TYPE) {
                object.visitVarInsn(25, context.var("lexer"));
                object.visitVarInsn(16, n5);
                object.visitMethodInsn(182, JSONLexerBase, "scanBoolean", "(C)Z");
                object.visitVarInsn(54, context.var(((FieldInfo)object4).name + "_asm"));
            } else if (object3 == Float.TYPE) {
                object.visitVarInsn(25, context.var("lexer"));
                object.visitVarInsn(16, n5);
                object.visitMethodInsn(182, JSONLexerBase, "scanFloat", "(C)F");
                object.visitVarInsn(56, context.var(((FieldInfo)object4).name + "_asm"));
            } else if (object3 == Double.TYPE) {
                object.visitVarInsn(25, context.var("lexer"));
                object.visitVarInsn(16, n5);
                object.visitMethodInsn(182, JSONLexerBase, "scanDouble", "(C)D");
                object.visitVarInsn(57, context.var(((FieldInfo)object4).name + "_asm", 2));
            } else if (object3 == Character.TYPE) {
                object.visitVarInsn(25, context.var("lexer"));
                object.visitVarInsn(16, n5);
                object.visitMethodInsn(182, JSONLexerBase, "scanString", "(C)Ljava/lang/String;");
                object.visitInsn(3);
                object.visitMethodInsn(182, "java/lang/String", "charAt", "(I)C");
                object.visitVarInsn(54, context.var(((FieldInfo)object4).name + "_asm"));
            } else if (object3 == String.class) {
                object.visitVarInsn(25, context.var("lexer"));
                object.visitVarInsn(16, n5);
                object.visitMethodInsn(182, JSONLexerBase, "scanString", "(C)Ljava/lang/String;");
                object.visitVarInsn(58, context.var(((FieldInfo)object4).name + "_asm"));
            } else if (((Class)object3).isEnum()) {
                object2 = new Label();
                label2 = new Label();
                label = new Label();
                Label label4 = new Label();
                object.visitVarInsn(25, context.var("lexer"));
                object.visitMethodInsn(182, JSONLexerBase, "getCurrent", "()C");
                object.visitInsn(89);
                object.visitVarInsn(54, context.var("ch"));
                object.visitLdcInsn(110);
                object.visitJumpInsn(159, label4);
                object.visitVarInsn(21, context.var("ch"));
                object.visitLdcInsn(34);
                object.visitJumpInsn(160, (Label)object2);
                object.visitLabel(label4);
                object.visitVarInsn(25, context.var("lexer"));
                object.visitLdcInsn(com.alibaba.fastjson.asm.Type.getType(ASMUtils.desc(object3)));
                object.visitVarInsn(25, 1);
                object.visitMethodInsn(182, DefaultJSONParser, "getSymbolTable", "()" + ASMUtils.desc(SymbolTable.class));
                object.visitVarInsn(16, n5);
                object.visitMethodInsn(182, JSONLexerBase, "scanEnum", "(Ljava/lang/Class;" + ASMUtils.desc(SymbolTable.class) + "C)Ljava/lang/Enum;");
                object.visitJumpInsn(167, label);
                object.visitLabel((Label)object2);
                object.visitVarInsn(21, context.var("ch"));
                object.visitLdcInsn(48);
                object.visitJumpInsn(161, label2);
                object.visitVarInsn(21, context.var("ch"));
                object.visitLdcInsn(57);
                object.visitJumpInsn(163, label2);
                this._getFieldDeser(context, (MethodVisitor)object, (FieldInfo)object4);
                object.visitTypeInsn(192, ASMUtils.type(EnumDeserializer.class));
                object.visitVarInsn(25, context.var("lexer"));
                object.visitVarInsn(16, n5);
                object.visitMethodInsn(182, JSONLexerBase, "scanInt", "(C)I");
                object.visitMethodInsn(182, ASMUtils.type(EnumDeserializer.class), "valueOf", "(I)Ljava/lang/Enum;");
                object.visitJumpInsn(167, label);
                object.visitLabel(label2);
                object.visitVarInsn(25, 0);
                object.visitVarInsn(25, context.var("lexer"));
                object.visitVarInsn(16, n5);
                object.visitMethodInsn(182, ASMUtils.type(JavaBeanDeserializer.class), "scanEnum", "(L" + JSONLexerBase + ";C)Ljava/lang/Enum;");
                object.visitLabel(label);
                object.visitTypeInsn(192, ASMUtils.type(object3));
                object.visitVarInsn(58, context.var(((FieldInfo)object4).name + "_asm"));
            } else if (Collection.class.isAssignableFrom((Class<?>)object3)) {
                if ((object2 = TypeUtils.getCollectionItemClass((Type)object2)) == String.class) {
                    if (object3 == List.class || object3 == Collections.class || object3 == ArrayList.class) {
                        object.visitTypeInsn(187, ASMUtils.type(ArrayList.class));
                        object.visitInsn(89);
                        object.visitMethodInsn(183, ASMUtils.type(ArrayList.class), "<init>", "()V");
                    } else {
                        object.visitLdcInsn(com.alibaba.fastjson.asm.Type.getType(ASMUtils.desc(object3)));
                        object.visitMethodInsn(184, ASMUtils.type(TypeUtils.class), "createCollection", "(Ljava/lang/Class;)Ljava/util/Collection;");
                    }
                    object.visitVarInsn(58, context.var(((FieldInfo)object4).name + "_asm"));
                    object.visitVarInsn(25, context.var("lexer"));
                    object.visitVarInsn(25, context.var(((FieldInfo)object4).name + "_asm"));
                    object.visitVarInsn(16, n5);
                    object.visitMethodInsn(182, JSONLexerBase, "scanStringArray", "(Ljava/util/Collection;C)V");
                    object3 = new Label();
                    object.visitVarInsn(25, context.var("lexer"));
                    object.visitFieldInsn(180, JSONLexerBase, "matchStat", "I");
                    object.visitLdcInsn(5);
                    object.visitJumpInsn(160, (Label)object3);
                    object.visitInsn(1);
                    object.visitVarInsn(58, context.var(((FieldInfo)object4).name + "_asm"));
                    object.visitLabel((Label)object3);
                } else {
                    label2 = new Label();
                    object.visitVarInsn(25, context.var("lexer"));
                    object.visitMethodInsn(182, JSONLexerBase, "token", "()I");
                    object.visitVarInsn(54, context.var("token"));
                    object.visitVarInsn(21, context.var("token"));
                    n4 = n3 == 0 ? 14 : 16;
                    object.visitLdcInsn(n4);
                    object.visitJumpInsn(159, label2);
                    object.visitVarInsn(25, 1);
                    object.visitVarInsn(21, context.var("token"));
                    object.visitMethodInsn(182, DefaultJSONParser, "throwException", "(I)V");
                    object.visitLabel(label2);
                    label2 = new Label();
                    label = new Label();
                    object.visitVarInsn(25, context.var("lexer"));
                    object.visitMethodInsn(182, JSONLexerBase, "getCurrent", "()C");
                    object.visitVarInsn(16, 91);
                    object.visitJumpInsn(160, label2);
                    object.visitVarInsn(25, context.var("lexer"));
                    object.visitMethodInsn(182, JSONLexerBase, "next", "()C");
                    object.visitInsn(87);
                    object.visitVarInsn(25, context.var("lexer"));
                    object.visitLdcInsn(14);
                    object.visitMethodInsn(182, JSONLexerBase, "setToken", "(I)V");
                    object.visitJumpInsn(167, label);
                    object.visitLabel(label2);
                    object.visitVarInsn(25, context.var("lexer"));
                    object.visitLdcInsn(14);
                    object.visitMethodInsn(182, JSONLexerBase, "nextToken", "(I)V");
                    object.visitLabel(label);
                    this._newCollection((MethodVisitor)object, (Class<?>)object3, n3, false);
                    object.visitInsn(89);
                    object.visitVarInsn(58, context.var(((FieldInfo)object4).name + "_asm"));
                    this._getCollectionFieldItemDeser(context, (MethodVisitor)object, (FieldInfo)object4, (Class<?>)object2);
                    object.visitVarInsn(25, 1);
                    object.visitLdcInsn(com.alibaba.fastjson.asm.Type.getType(ASMUtils.desc(object2)));
                    object.visitVarInsn(25, 3);
                    object.visitMethodInsn(184, ASMUtils.type(JavaBeanDeserializer.class), "parseArray", "(Ljava/util/Collection;" + ASMUtils.desc(ObjectDeserializer.class) + "L" + DefaultJSONParser + ";" + "Ljava/lang/reflect/Type;Ljava/lang/Object;)V");
                }
            } else if (((Class)object3).isArray()) {
                object.visitVarInsn(25, context.var("lexer"));
                object.visitLdcInsn(14);
                object.visitMethodInsn(182, JSONLexerBase, "nextToken", "(I)V");
                object.visitVarInsn(25, 1);
                object.visitVarInsn(25, 0);
                object.visitLdcInsn(n3);
                object.visitMethodInsn(182, ASMUtils.type(JavaBeanDeserializer.class), "getFieldType", "(I)Ljava/lang/reflect/Type;");
                object.visitMethodInsn(182, DefaultJSONParser, "parseObject", "(Ljava/lang/reflect/Type;)Ljava/lang/Object;");
                object.visitTypeInsn(192, ASMUtils.type(object3));
                object.visitVarInsn(58, context.var(((FieldInfo)object4).name + "_asm"));
            } else {
                object2 = new Label();
                label2 = new Label();
                if (object3 == Date.class) {
                    object.visitVarInsn(25, context.var("lexer"));
                    object.visitMethodInsn(182, JSONLexerBase, "getCurrent", "()C");
                    object.visitLdcInsn(49);
                    object.visitJumpInsn(160, (Label)object2);
                    object.visitTypeInsn(187, ASMUtils.type(Date.class));
                    object.visitInsn(89);
                    object.visitVarInsn(25, context.var("lexer"));
                    object.visitVarInsn(16, n5);
                    object.visitMethodInsn(182, JSONLexerBase, "scanLong", "(C)J");
                    object.visitMethodInsn(183, ASMUtils.type(Date.class), "<init>", "(J)V");
                    object.visitVarInsn(58, context.var(((FieldInfo)object4).name + "_asm"));
                    object.visitJumpInsn(167, label2);
                }
                object.visitLabel((Label)object2);
                this._quickNextToken(context, (MethodVisitor)object, 14);
                this._deserObject(context, (MethodVisitor)object, (FieldInfo)object4, (Class<?>)object3, n3);
                object.visitVarInsn(25, 0);
                object.visitVarInsn(25, context.var("lexer"));
                if (n4 == 0) {
                    object.visitLdcInsn(16);
                } else {
                    object.visitLdcInsn(15);
                }
                object.visitMethodInsn(183, ASMUtils.type(JavaBeanDeserializer.class), "check", "(" + ASMUtils.desc(JSONLexer.class) + "I)V");
                object.visitLabel(label2);
            }
            ++n3;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void _deserialze_list_obj(Context context, MethodVisitor methodVisitor, Label label, FieldInfo fieldInfo, Class<?> clazz, Class<?> clazz2, int n2) {
        Label label2 = new Label();
        methodVisitor.visitMethodInsn(182, JSONLexerBase, "matchField", "([C)Z");
        methodVisitor.visitJumpInsn(153, label2);
        this._setFlag(methodVisitor, context, n2);
        Label label3 = new Label();
        methodVisitor.visitVarInsn(25, context.var("lexer"));
        methodVisitor.visitMethodInsn(182, JSONLexerBase, "token", "()I");
        methodVisitor.visitLdcInsn(8);
        methodVisitor.visitJumpInsn(160, label3);
        methodVisitor.visitVarInsn(25, context.var("lexer"));
        methodVisitor.visitLdcInsn(16);
        methodVisitor.visitMethodInsn(182, JSONLexerBase, "nextToken", "(I)V");
        methodVisitor.visitJumpInsn(167, label2);
        methodVisitor.visitLabel(label3);
        label3 = new Label();
        Label label4 = new Label();
        methodVisitor.visitVarInsn(25, context.var("lexer"));
        methodVisitor.visitMethodInsn(182, JSONLexerBase, "token", "()I");
        methodVisitor.visitLdcInsn(21);
        methodVisitor.visitJumpInsn(160, label4);
        methodVisitor.visitVarInsn(25, context.var("lexer"));
        methodVisitor.visitLdcInsn(14);
        methodVisitor.visitMethodInsn(182, JSONLexerBase, "nextToken", "(I)V");
        methodVisitor.visitVarInsn(25, context.var("lexer"));
        methodVisitor.visitMethodInsn(182, JSONLexerBase, "token", "()I");
        methodVisitor.visitLdcInsn(14);
        methodVisitor.visitJumpInsn(160, label);
        this._newCollection(methodVisitor, clazz, n2, true);
        methodVisitor.visitJumpInsn(167, label3);
        methodVisitor.visitLabel(label4);
        methodVisitor.visitVarInsn(25, context.var("lexer"));
        methodVisitor.visitMethodInsn(182, JSONLexerBase, "token", "()I");
        methodVisitor.visitLdcInsn(14);
        methodVisitor.visitJumpInsn(160, label);
        this._newCollection(methodVisitor, clazz, n2, false);
        methodVisitor.visitLabel(label3);
        methodVisitor.visitVarInsn(58, context.var(fieldInfo.name + "_asm"));
        boolean bl2 = ParserConfig.isPrimitive(fieldInfo.fieldClass);
        this._getCollectionFieldItemDeser(context, methodVisitor, fieldInfo, clazz2);
        if (bl2) {
            methodVisitor.visitMethodInsn(185, ASMUtils.type(ObjectDeserializer.class), "getFastMatchToken", "()I");
            methodVisitor.visitVarInsn(54, context.var("fastMatchToken"));
            methodVisitor.visitVarInsn(25, context.var("lexer"));
            methodVisitor.visitVarInsn(21, context.var("fastMatchToken"));
            methodVisitor.visitMethodInsn(182, JSONLexerBase, "nextToken", "(I)V");
        } else {
            methodVisitor.visitInsn(87);
            methodVisitor.visitLdcInsn(12);
            methodVisitor.visitVarInsn(54, context.var("fastMatchToken"));
            this._quickNextToken(context, methodVisitor, 12);
        }
        methodVisitor.visitVarInsn(25, 1);
        methodVisitor.visitMethodInsn(182, DefaultJSONParser, "getContext", "()" + ASMUtils.desc(ParseContext.class));
        methodVisitor.visitVarInsn(58, context.var("listContext"));
        methodVisitor.visitVarInsn(25, 1);
        methodVisitor.visitVarInsn(25, context.var(fieldInfo.name + "_asm"));
        methodVisitor.visitLdcInsn(fieldInfo.name);
        methodVisitor.visitMethodInsn(182, DefaultJSONParser, "setContext", "(Ljava/lang/Object;Ljava/lang/Object;)" + ASMUtils.desc(ParseContext.class));
        methodVisitor.visitInsn(87);
        label3 = new Label();
        label4 = new Label();
        methodVisitor.visitInsn(3);
        methodVisitor.visitVarInsn(54, context.var("i"));
        methodVisitor.visitLabel(label3);
        methodVisitor.visitVarInsn(25, context.var("lexer"));
        methodVisitor.visitMethodInsn(182, JSONLexerBase, "token", "()I");
        methodVisitor.visitLdcInsn(15);
        methodVisitor.visitJumpInsn(159, label4);
        methodVisitor.visitVarInsn(25, 0);
        methodVisitor.visitFieldInsn(180, context.className, fieldInfo.name + "_asm_list_item_deser__", ASMUtils.desc(ObjectDeserializer.class));
        methodVisitor.visitVarInsn(25, 1);
        methodVisitor.visitLdcInsn(com.alibaba.fastjson.asm.Type.getType(ASMUtils.desc(clazz2)));
        methodVisitor.visitVarInsn(21, context.var("i"));
        methodVisitor.visitMethodInsn(184, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;");
        methodVisitor.visitMethodInsn(185, ASMUtils.type(ObjectDeserializer.class), "deserialze", "(L" + DefaultJSONParser + ";Ljava/lang/reflect/Type;Ljava/lang/Object;)Ljava/lang/Object;");
        methodVisitor.visitVarInsn(58, context.var("list_item_value"));
        methodVisitor.visitIincInsn(context.var("i"), 1);
        methodVisitor.visitVarInsn(25, context.var(fieldInfo.name + "_asm"));
        methodVisitor.visitVarInsn(25, context.var("list_item_value"));
        if (clazz.isInterface()) {
            methodVisitor.visitMethodInsn(185, ASMUtils.type(clazz), "add", "(Ljava/lang/Object;)Z");
        } else {
            methodVisitor.visitMethodInsn(182, ASMUtils.type(clazz), "add", "(Ljava/lang/Object;)Z");
        }
        methodVisitor.visitInsn(87);
        methodVisitor.visitVarInsn(25, 1);
        methodVisitor.visitVarInsn(25, context.var(fieldInfo.name + "_asm"));
        methodVisitor.visitMethodInsn(182, DefaultJSONParser, "checkListResolve", "(Ljava/util/Collection;)V");
        methodVisitor.visitVarInsn(25, context.var("lexer"));
        methodVisitor.visitMethodInsn(182, JSONLexerBase, "token", "()I");
        methodVisitor.visitLdcInsn(16);
        methodVisitor.visitJumpInsn(160, label3);
        if (bl2) {
            methodVisitor.visitVarInsn(25, context.var("lexer"));
            methodVisitor.visitVarInsn(21, context.var("fastMatchToken"));
            methodVisitor.visitMethodInsn(182, JSONLexerBase, "nextToken", "(I)V");
        } else {
            this._quickNextToken(context, methodVisitor, 12);
        }
        methodVisitor.visitJumpInsn(167, label3);
        methodVisitor.visitLabel(label4);
        methodVisitor.visitVarInsn(25, 1);
        methodVisitor.visitVarInsn(25, context.var("listContext"));
        methodVisitor.visitMethodInsn(182, DefaultJSONParser, "setContext", "(" + ASMUtils.desc(ParseContext.class) + ")V");
        methodVisitor.visitVarInsn(25, context.var("lexer"));
        methodVisitor.visitMethodInsn(182, JSONLexerBase, "token", "()I");
        methodVisitor.visitLdcInsn(15);
        methodVisitor.visitJumpInsn(160, label);
        this._quickNextTokenComma(context, methodVisitor);
        methodVisitor.visitLabel(label2);
    }

    private void _deserialze_obj(Context context, MethodVisitor methodVisitor, Label label, FieldInfo fieldInfo, Class<?> clazz, int n2) {
        label = new Label();
        Label label2 = new Label();
        methodVisitor.visitVarInsn(25, context.var("lexer"));
        methodVisitor.visitVarInsn(25, 0);
        methodVisitor.visitFieldInsn(180, context.className, fieldInfo.name + "_asm_prefix__", "[C");
        methodVisitor.visitMethodInsn(182, JSONLexerBase, "matchField", "([C)Z");
        methodVisitor.visitJumpInsn(154, label);
        methodVisitor.visitInsn(1);
        methodVisitor.visitVarInsn(58, context.var(fieldInfo.name + "_asm"));
        methodVisitor.visitJumpInsn(167, label2);
        methodVisitor.visitLabel(label);
        this._setFlag(methodVisitor, context, n2);
        methodVisitor.visitVarInsn(21, context.var("matchedCount"));
        methodVisitor.visitInsn(4);
        methodVisitor.visitInsn(96);
        methodVisitor.visitVarInsn(54, context.var("matchedCount"));
        this._deserObject(context, methodVisitor, fieldInfo, clazz, n2);
        methodVisitor.visitVarInsn(25, 1);
        methodVisitor.visitMethodInsn(182, DefaultJSONParser, "getResolveStatus", "()I");
        methodVisitor.visitLdcInsn(1);
        methodVisitor.visitJumpInsn(160, label2);
        methodVisitor.visitVarInsn(25, 1);
        methodVisitor.visitMethodInsn(182, DefaultJSONParser, "getLastResolveTask", "()" + ASMUtils.desc(DefaultJSONParser.ResolveTask.class));
        methodVisitor.visitVarInsn(58, context.var("resolveTask"));
        methodVisitor.visitVarInsn(25, context.var("resolveTask"));
        methodVisitor.visitVarInsn(25, 1);
        methodVisitor.visitMethodInsn(182, DefaultJSONParser, "getContext", "()" + ASMUtils.desc(ParseContext.class));
        methodVisitor.visitFieldInsn(181, ASMUtils.type(DefaultJSONParser.ResolveTask.class), "ownerContext", ASMUtils.desc(ParseContext.class));
        methodVisitor.visitVarInsn(25, context.var("resolveTask"));
        methodVisitor.visitVarInsn(25, 0);
        methodVisitor.visitLdcInsn(fieldInfo.name);
        methodVisitor.visitMethodInsn(182, ASMUtils.type(JavaBeanDeserializer.class), "getFieldDeserializer", "(Ljava/lang/String;)" + ASMUtils.desc(FieldDeserializer.class));
        methodVisitor.visitFieldInsn(181, ASMUtils.type(DefaultJSONParser.ResolveTask.class), "fieldDeserializer", ASMUtils.desc(FieldDeserializer.class));
        methodVisitor.visitVarInsn(25, 1);
        methodVisitor.visitLdcInsn(0);
        methodVisitor.visitMethodInsn(182, DefaultJSONParser, "setResolveStatus", "(I)V");
        methodVisitor.visitLabel(label2);
    }

    private void _getCollectionFieldItemDeser(Context context, MethodVisitor methodVisitor, FieldInfo fieldInfo, Class<?> clazz) {
        Label label = new Label();
        methodVisitor.visitVarInsn(25, 0);
        methodVisitor.visitFieldInsn(180, context.className, fieldInfo.name + "_asm_list_item_deser__", ASMUtils.desc(ObjectDeserializer.class));
        methodVisitor.visitJumpInsn(199, label);
        methodVisitor.visitVarInsn(25, 0);
        methodVisitor.visitVarInsn(25, 1);
        methodVisitor.visitMethodInsn(182, DefaultJSONParser, "getConfig", "()" + ASMUtils.desc(ParserConfig.class));
        methodVisitor.visitLdcInsn(com.alibaba.fastjson.asm.Type.getType(ASMUtils.desc(clazz)));
        methodVisitor.visitMethodInsn(182, ASMUtils.type(ParserConfig.class), "getDeserializer", "(Ljava/lang/reflect/Type;)" + ASMUtils.desc(ObjectDeserializer.class));
        methodVisitor.visitFieldInsn(181, context.className, fieldInfo.name + "_asm_list_item_deser__", ASMUtils.desc(ObjectDeserializer.class));
        methodVisitor.visitLabel(label);
        methodVisitor.visitVarInsn(25, 0);
        methodVisitor.visitFieldInsn(180, context.className, fieldInfo.name + "_asm_list_item_deser__", ASMUtils.desc(ObjectDeserializer.class));
    }

    private void _getFieldDeser(Context context, MethodVisitor methodVisitor, FieldInfo fieldInfo) {
        Label label = new Label();
        methodVisitor.visitVarInsn(25, 0);
        methodVisitor.visitFieldInsn(180, context.className, fieldInfo.name + "_asm_deser__", ASMUtils.desc(ObjectDeserializer.class));
        methodVisitor.visitJumpInsn(199, label);
        methodVisitor.visitVarInsn(25, 0);
        methodVisitor.visitVarInsn(25, 1);
        methodVisitor.visitMethodInsn(182, DefaultJSONParser, "getConfig", "()" + ASMUtils.desc(ParserConfig.class));
        methodVisitor.visitLdcInsn(com.alibaba.fastjson.asm.Type.getType(ASMUtils.desc(fieldInfo.fieldClass)));
        methodVisitor.visitMethodInsn(182, ASMUtils.type(ParserConfig.class), "getDeserializer", "(Ljava/lang/reflect/Type;)" + ASMUtils.desc(ObjectDeserializer.class));
        methodVisitor.visitFieldInsn(181, context.className, fieldInfo.name + "_asm_deser__", ASMUtils.desc(ObjectDeserializer.class));
        methodVisitor.visitLabel(label);
        methodVisitor.visitVarInsn(25, 0);
        methodVisitor.visitFieldInsn(180, context.className, fieldInfo.name + "_asm_deser__", ASMUtils.desc(ObjectDeserializer.class));
    }

    /*
     * Enabled aggressive block sorting
     */
    private void _init(ClassWriter object, Context context) {
        FieldInfo fieldInfo;
        int n2;
        int n3 = context.fieldInfoList.length;
        for (n2 = 0; n2 < n3; ++n2) {
            fieldInfo = context.fieldInfoList[n2];
            new FieldWriter((ClassWriter)object, 1, fieldInfo.name + "_asm_prefix__", "[C").visitEnd();
        }
        n3 = context.fieldInfoList.length;
        for (n2 = 0; n2 < n3; ++n2) {
            fieldInfo = context.fieldInfoList[n2];
            Class<?> clazz = fieldInfo.fieldClass;
            if (clazz.isPrimitive()) continue;
            if (Collection.class.isAssignableFrom(clazz)) {
                new FieldWriter((ClassWriter)object, 1, fieldInfo.name + "_asm_list_item_deser__", ASMUtils.desc(ObjectDeserializer.class)).visitEnd();
                continue;
            }
            new FieldWriter((ClassWriter)object, 1, fieldInfo.name + "_asm_deser__", ASMUtils.desc(ObjectDeserializer.class)).visitEnd();
        }
        object = new MethodWriter((ClassWriter)object, 1, "<init>", "(" + ASMUtils.desc(ParserConfig.class) + "Ljava/lang/Class;)V", null, null);
        object.visitVarInsn(25, 0);
        object.visitVarInsn(25, 1);
        object.visitVarInsn(25, 2);
        object.visitMethodInsn(183, ASMUtils.type(JavaBeanDeserializer.class), "<init>", "(" + ASMUtils.desc(ParserConfig.class) + "Ljava/lang/Class;)V");
        n2 = 0;
        n3 = context.fieldInfoList.length;
        while (true) {
            if (n2 >= n3) {
                object.visitInsn(177);
                object.visitMaxs(4, 4);
                object.visitEnd();
                return;
            }
            fieldInfo = context.fieldInfoList[n2];
            object.visitVarInsn(25, 0);
            object.visitLdcInsn("\"" + fieldInfo.name + "\":");
            object.visitMethodInsn(182, "java/lang/String", "toCharArray", "()[C");
            object.visitFieldInsn(181, context.className, fieldInfo.name + "_asm_prefix__", "[C");
            ++n2;
        }
    }

    private void _isFlag(MethodVisitor methodVisitor, Context context, int n2, Label label) {
        methodVisitor.visitVarInsn(21, context.var("_asm_flag_" + n2 / 32));
        methodVisitor.visitLdcInsn(1 << n2);
        methodVisitor.visitInsn(126);
        methodVisitor.visitJumpInsn(153, label);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void _loadAndSet(Context context, MethodVisitor methodVisitor, FieldInfo fieldInfo) {
        Class<?> clazz = fieldInfo.fieldClass;
        Type type = fieldInfo.fieldType;
        if (clazz == Boolean.TYPE) {
            methodVisitor.visitVarInsn(25, context.var("instance"));
            methodVisitor.visitVarInsn(21, context.var(fieldInfo.name + "_asm"));
            this._set(context, methodVisitor, fieldInfo);
            return;
        }
        if (clazz == Byte.TYPE || clazz == Short.TYPE || clazz == Integer.TYPE || clazz == Character.TYPE) {
            methodVisitor.visitVarInsn(25, context.var("instance"));
            methodVisitor.visitVarInsn(21, context.var(fieldInfo.name + "_asm"));
            this._set(context, methodVisitor, fieldInfo);
            return;
        }
        if (clazz == Long.TYPE) {
            methodVisitor.visitVarInsn(25, context.var("instance"));
            methodVisitor.visitVarInsn(22, context.var(fieldInfo.name + "_asm", 2));
            if (fieldInfo.method == null) {
                methodVisitor.visitFieldInsn(181, ASMUtils.type(fieldInfo.declaringClass), fieldInfo.field.getName(), ASMUtils.desc(fieldInfo.fieldClass));
                return;
            }
            methodVisitor.visitMethodInsn(182, ASMUtils.type(context.getInstClass()), fieldInfo.method.getName(), ASMUtils.desc(fieldInfo.method));
            if (fieldInfo.method.getReturnType().equals(Void.TYPE)) return;
            methodVisitor.visitInsn(87);
            return;
        }
        if (clazz == Float.TYPE) {
            methodVisitor.visitVarInsn(25, context.var("instance"));
            methodVisitor.visitVarInsn(23, context.var(fieldInfo.name + "_asm"));
            this._set(context, methodVisitor, fieldInfo);
            return;
        }
        if (clazz == Double.TYPE) {
            methodVisitor.visitVarInsn(25, context.var("instance"));
            methodVisitor.visitVarInsn(24, context.var(fieldInfo.name + "_asm", 2));
            this._set(context, methodVisitor, fieldInfo);
            return;
        }
        if (clazz == String.class) {
            methodVisitor.visitVarInsn(25, context.var("instance"));
            methodVisitor.visitVarInsn(25, context.var(fieldInfo.name + "_asm"));
            this._set(context, methodVisitor, fieldInfo);
            return;
        }
        if (clazz.isEnum()) {
            methodVisitor.visitVarInsn(25, context.var("instance"));
            methodVisitor.visitVarInsn(25, context.var(fieldInfo.name + "_asm"));
            this._set(context, methodVisitor, fieldInfo);
            return;
        }
        if (!Collection.class.isAssignableFrom(clazz)) {
            methodVisitor.visitVarInsn(25, context.var("instance"));
            methodVisitor.visitVarInsn(25, context.var(fieldInfo.name + "_asm"));
            this._set(context, methodVisitor, fieldInfo);
            return;
        }
        methodVisitor.visitVarInsn(25, context.var("instance"));
        if (TypeUtils.getCollectionItemClass(type) == String.class) {
            methodVisitor.visitVarInsn(25, context.var(fieldInfo.name + "_asm"));
            methodVisitor.visitTypeInsn(192, ASMUtils.type(clazz));
        } else {
            methodVisitor.visitVarInsn(25, context.var(fieldInfo.name + "_asm"));
        }
        this._set(context, methodVisitor, fieldInfo);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void _newCollection(MethodVisitor methodVisitor, Class<?> clazz, int n2, boolean bl2) {
        if (clazz.isAssignableFrom(ArrayList.class) && !bl2) {
            methodVisitor.visitTypeInsn(187, "java/util/ArrayList");
            methodVisitor.visitInsn(89);
            methodVisitor.visitMethodInsn(183, "java/util/ArrayList", "<init>", "()V");
        } else if (clazz.isAssignableFrom(LinkedList.class) && !bl2) {
            methodVisitor.visitTypeInsn(187, ASMUtils.type(LinkedList.class));
            methodVisitor.visitInsn(89);
            methodVisitor.visitMethodInsn(183, ASMUtils.type(LinkedList.class), "<init>", "()V");
        } else if (clazz.isAssignableFrom(HashSet.class)) {
            methodVisitor.visitTypeInsn(187, ASMUtils.type(HashSet.class));
            methodVisitor.visitInsn(89);
            methodVisitor.visitMethodInsn(183, ASMUtils.type(HashSet.class), "<init>", "()V");
        } else if (clazz.isAssignableFrom(TreeSet.class)) {
            methodVisitor.visitTypeInsn(187, ASMUtils.type(TreeSet.class));
            methodVisitor.visitInsn(89);
            methodVisitor.visitMethodInsn(183, ASMUtils.type(TreeSet.class), "<init>", "()V");
        } else if (clazz.isAssignableFrom(LinkedHashSet.class)) {
            methodVisitor.visitTypeInsn(187, ASMUtils.type(LinkedHashSet.class));
            methodVisitor.visitInsn(89);
            methodVisitor.visitMethodInsn(183, ASMUtils.type(LinkedHashSet.class), "<init>", "()V");
        } else if (bl2) {
            methodVisitor.visitTypeInsn(187, ASMUtils.type(HashSet.class));
            methodVisitor.visitInsn(89);
            methodVisitor.visitMethodInsn(183, ASMUtils.type(HashSet.class), "<init>", "()V");
        } else {
            methodVisitor.visitVarInsn(25, 0);
            methodVisitor.visitLdcInsn(n2);
            methodVisitor.visitMethodInsn(182, ASMUtils.type(JavaBeanDeserializer.class), "getFieldType", "(I)Ljava/lang/reflect/Type;");
            methodVisitor.visitMethodInsn(184, ASMUtils.type(TypeUtils.class), "createCollection", "(Ljava/lang/reflect/Type;)Ljava/util/Collection;");
        }
        methodVisitor.visitTypeInsn(192, ASMUtils.type(clazz));
    }

    /*
     * Enabled aggressive block sorting
     */
    private void _quickNextToken(Context context, MethodVisitor methodVisitor, int n2) {
        Label label = new Label();
        Label label2 = new Label();
        methodVisitor.visitVarInsn(25, context.var("lexer"));
        methodVisitor.visitMethodInsn(182, JSONLexerBase, "getCurrent", "()C");
        if (n2 == 12) {
            methodVisitor.visitVarInsn(16, 123);
        } else {
            if (n2 != 14) {
                throw new IllegalStateException();
            }
            methodVisitor.visitVarInsn(16, 91);
        }
        methodVisitor.visitJumpInsn(160, label);
        methodVisitor.visitVarInsn(25, context.var("lexer"));
        methodVisitor.visitMethodInsn(182, JSONLexerBase, "next", "()C");
        methodVisitor.visitInsn(87);
        methodVisitor.visitVarInsn(25, context.var("lexer"));
        methodVisitor.visitLdcInsn(n2);
        methodVisitor.visitMethodInsn(182, JSONLexerBase, "setToken", "(I)V");
        methodVisitor.visitJumpInsn(167, label2);
        methodVisitor.visitLabel(label);
        methodVisitor.visitVarInsn(25, context.var("lexer"));
        methodVisitor.visitLdcInsn(n2);
        methodVisitor.visitMethodInsn(182, JSONLexerBase, "nextToken", "(I)V");
        methodVisitor.visitLabel(label2);
    }

    private void _quickNextTokenComma(Context context, MethodVisitor methodVisitor) {
        Label label = new Label();
        Label label2 = new Label();
        Label label3 = new Label();
        Label label4 = new Label();
        Label label5 = new Label();
        methodVisitor.visitVarInsn(25, context.var("lexer"));
        methodVisitor.visitMethodInsn(182, JSONLexerBase, "getCurrent", "()C");
        methodVisitor.visitInsn(89);
        methodVisitor.visitVarInsn(54, context.var("ch"));
        methodVisitor.visitVarInsn(16, 44);
        methodVisitor.visitJumpInsn(160, label2);
        methodVisitor.visitVarInsn(25, context.var("lexer"));
        methodVisitor.visitMethodInsn(182, JSONLexerBase, "next", "()C");
        methodVisitor.visitInsn(87);
        methodVisitor.visitVarInsn(25, context.var("lexer"));
        methodVisitor.visitLdcInsn(16);
        methodVisitor.visitMethodInsn(182, JSONLexerBase, "setToken", "(I)V");
        methodVisitor.visitJumpInsn(167, label5);
        methodVisitor.visitLabel(label2);
        methodVisitor.visitVarInsn(21, context.var("ch"));
        methodVisitor.visitVarInsn(16, 125);
        methodVisitor.visitJumpInsn(160, label3);
        methodVisitor.visitVarInsn(25, context.var("lexer"));
        methodVisitor.visitMethodInsn(182, JSONLexerBase, "next", "()C");
        methodVisitor.visitInsn(87);
        methodVisitor.visitVarInsn(25, context.var("lexer"));
        methodVisitor.visitLdcInsn(13);
        methodVisitor.visitMethodInsn(182, JSONLexerBase, "setToken", "(I)V");
        methodVisitor.visitJumpInsn(167, label5);
        methodVisitor.visitLabel(label3);
        methodVisitor.visitVarInsn(21, context.var("ch"));
        methodVisitor.visitVarInsn(16, 93);
        methodVisitor.visitJumpInsn(160, label4);
        methodVisitor.visitVarInsn(25, context.var("lexer"));
        methodVisitor.visitMethodInsn(182, JSONLexerBase, "next", "()C");
        methodVisitor.visitInsn(87);
        methodVisitor.visitVarInsn(25, context.var("lexer"));
        methodVisitor.visitLdcInsn(15);
        methodVisitor.visitMethodInsn(182, JSONLexerBase, "setToken", "(I)V");
        methodVisitor.visitJumpInsn(167, label5);
        methodVisitor.visitLabel(label4);
        methodVisitor.visitVarInsn(21, context.var("ch"));
        methodVisitor.visitVarInsn(16, 26);
        methodVisitor.visitJumpInsn(160, label);
        methodVisitor.visitVarInsn(25, context.var("lexer"));
        methodVisitor.visitLdcInsn(20);
        methodVisitor.visitMethodInsn(182, JSONLexerBase, "setToken", "(I)V");
        methodVisitor.visitJumpInsn(167, label5);
        methodVisitor.visitLabel(label);
        methodVisitor.visitVarInsn(25, context.var("lexer"));
        methodVisitor.visitMethodInsn(182, JSONLexerBase, "nextToken", "()V");
        methodVisitor.visitLabel(label5);
    }

    private void _set(Context context, MethodVisitor methodVisitor, FieldInfo fieldInfo) {
        if (fieldInfo.method != null) {
            methodVisitor.visitMethodInsn(182, ASMUtils.type(fieldInfo.declaringClass), fieldInfo.method.getName(), ASMUtils.desc(fieldInfo.method));
            if (!fieldInfo.method.getReturnType().equals(Void.TYPE)) {
                methodVisitor.visitInsn(87);
            }
            return;
        }
        methodVisitor.visitFieldInsn(181, ASMUtils.type(fieldInfo.declaringClass), fieldInfo.field.getName(), ASMUtils.desc(fieldInfo.fieldClass));
    }

    private void _setContext(Context context, MethodVisitor methodVisitor) {
        methodVisitor.visitVarInsn(25, 1);
        methodVisitor.visitVarInsn(25, context.var("context"));
        methodVisitor.visitMethodInsn(182, DefaultJSONParser, "setContext", "(" + ASMUtils.desc(ParseContext.class) + ")V");
        Label label = new Label();
        methodVisitor.visitVarInsn(25, context.var("childContext"));
        methodVisitor.visitJumpInsn(198, label);
        methodVisitor.visitVarInsn(25, context.var("childContext"));
        methodVisitor.visitVarInsn(25, context.var("instance"));
        methodVisitor.visitFieldInsn(181, ASMUtils.type(ParseContext.class), "object", "Ljava/lang/Object;");
        methodVisitor.visitLabel(label);
    }

    private void _setFlag(MethodVisitor methodVisitor, Context context, int n2) {
        String string2 = "_asm_flag_" + n2 / 32;
        methodVisitor.visitVarInsn(21, context.var(string2));
        methodVisitor.visitLdcInsn(1 << n2);
        methodVisitor.visitInsn(128);
        methodVisitor.visitVarInsn(54, context.var(string2));
    }

    private Class<?> defineClassPublic(String string2, byte[] byArray, int n2, int n3) {
        return this.classLoader.defineClassPublic(string2, byArray, n2, n3);
    }

    private void defineVarLexer(Context context, MethodVisitor methodVisitor) {
        methodVisitor.visitVarInsn(25, 1);
        methodVisitor.visitFieldInsn(180, DefaultJSONParser, "lexer", ASMUtils.desc(JSONLexer.class));
        methodVisitor.visitTypeInsn(192, JSONLexerBase);
        methodVisitor.visitVarInsn(58, context.var("lexer"));
    }

    public ObjectDeserializer createJavaBeanDeserializer(ParserConfig parserConfig, Class<?> clazz, Type object) throws Exception {
        if (clazz.isPrimitive()) {
            throw new IllegalArgumentException("not support type :" + clazz.getName());
        }
        String string2 = "FastjsonASMDeserializer_" + this.seed.incrementAndGet() + "_" + clazz.getSimpleName();
        Object object2 = ASMDeserializerFactory.class.getPackage().getName();
        String string3 = ((String)object2).replace('.', '/') + "/" + string2;
        string2 = (String)object2 + "." + string2;
        object2 = new ClassWriter();
        ((ClassWriter)object2).visit(49, 33, string3, ASMUtils.type(JavaBeanDeserializer.class), null);
        object = JavaBeanInfo.build(clazz, (Type)object);
        this._init((ClassWriter)object2, new Context(string3, parserConfig, (JavaBeanInfo)object, 3));
        this._createInstance((ClassWriter)object2, new Context(string3, parserConfig, (JavaBeanInfo)object, 3));
        this._deserialze((ClassWriter)object2, new Context(string3, parserConfig, (JavaBeanInfo)object, 4));
        this._deserialzeArrayMapping((ClassWriter)object2, new Context(string3, parserConfig, (JavaBeanInfo)object, 4));
        object = ((ClassWriter)object2).toByteArray();
        return (ObjectDeserializer)this.defineClassPublic(string2, (byte[])object, 0, ((Object)object).length).getConstructor(ParserConfig.class, Class.class).newInstance(parserConfig, clazz);
    }

    static class Context {
        private final JavaBeanInfo beanInfo;
        private final String className;
        private final Class<?> clazz;
        private FieldInfo[] fieldInfoList;
        private int variantIndex = 5;
        private final Map<String, Integer> variants = new HashMap<String, Integer>();

        public Context(String string2, ParserConfig parserConfig, JavaBeanInfo javaBeanInfo, int n2) {
            this.className = string2;
            this.clazz = javaBeanInfo.clazz;
            this.variantIndex = n2;
            this.beanInfo = javaBeanInfo;
            this.fieldInfoList = javaBeanInfo.fields;
        }

        static /* synthetic */ FieldInfo[] access$202(Context context, FieldInfo[] fieldInfoArray) {
            context.fieldInfoList = fieldInfoArray;
            return fieldInfoArray;
        }

        public Class<?> getInstClass() {
            Class<?> clazz;
            Class<?> clazz2 = clazz = this.beanInfo.builderClass;
            if (clazz == null) {
                clazz2 = this.clazz;
            }
            return clazz2;
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

