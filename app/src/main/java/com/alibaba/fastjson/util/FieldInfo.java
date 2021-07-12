/*
 * Decompiled with CFR 0.151.
 */
package com.alibaba.fastjson.util;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.util.ParameterizedTypeImpl;
import com.alibaba.fastjson.util.TypeUtils;
import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

public class FieldInfo
implements Comparable<FieldInfo> {
    public final Class<?> declaringClass;
    public final Field field;
    public final boolean fieldAccess;
    private final JSONField fieldAnnotation;
    public final Class<?> fieldClass;
    public final boolean fieldTransient;
    public final Type fieldType;
    public final boolean getOnly;
    public final boolean isEnum;
    public final String label;
    public final Method method;
    private final JSONField methodAnnotation;
    public final String name;
    public final char[] name_chars;
    private int ordinal = 0;
    public final int serialzeFeatures;

    /*
     * Enabled aggressive block sorting
     */
    public FieldInfo(String string2, Class<?> clazz, Class<?> clazz2, Type type, Field field, int n2, int n3) {
        this.name = string2;
        this.declaringClass = clazz;
        this.fieldClass = clazz2;
        this.fieldType = type;
        this.method = null;
        this.field = field;
        this.ordinal = n2;
        this.serialzeFeatures = n3;
        this.isEnum = clazz2.isEnum();
        if (field != null) {
            n2 = field.getModifiers();
            boolean bl2 = (n2 & 1) != 0 || this.method == null;
            this.fieldAccess = bl2;
            this.fieldTransient = Modifier.isTransient(n2);
        } else {
            this.fieldTransient = false;
            this.fieldAccess = false;
        }
        this.name_chars = this.genFieldNameChars();
        if (field != null) {
            TypeUtils.setAccessible(field);
        }
        this.label = "";
        this.fieldAnnotation = null;
        this.methodAnnotation = null;
        this.getOnly = false;
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    public FieldInfo(String object, Method object2, Field object3, Class<?> type, Type type2, int n2, int n3, JSONField type3, JSONField jSONField, String string2) {
        void var8_13;
        void var10_17;
        boolean bl2;
        void var9_16;
        void var8_9;
        void var7_8;
        int n4;
        Object object4 = object;
        if (object3 != null) {
            String string3 = ((Field)object3).getName();
            object4 = object;
            if (string3.equals(object)) {
                object4 = string3;
            }
        }
        this.name = object4;
        this.method = object2;
        this.field = object3;
        this.ordinal = n4;
        this.serialzeFeatures = var7_8;
        this.fieldAnnotation = var8_9;
        this.methodAnnotation = var9_16;
        if (object3 != null) {
            n4 = ((Field)object3).getModifiers();
            bl2 = (n4 & 1) != 0 || object2 == null;
            this.fieldAccess = bl2;
            this.fieldTransient = Modifier.isTransient(n4);
        } else {
            this.fieldAccess = false;
            this.fieldTransient = false;
        }
        this.label = var10_17 != null && var10_17.length() > 0 ? var10_17 : "";
        this.name_chars = this.genFieldNameChars();
        if (object2 != null) {
            TypeUtils.setAccessible((AccessibleObject)object2);
        }
        if (object3 != null) {
            TypeUtils.setAccessible((AccessibleObject)object3);
        }
        bl2 = false;
        if (object2 != null) {
            object = ((Method)object2).getParameterTypes();
            if (((Object)object).length == 1) {
                object = object[0];
                object3 = ((Method)object2).getGenericParameterTypes()[0];
            } else {
                object = ((Method)object2).getReturnType();
                object3 = ((Method)object2).getGenericReturnType();
                bl2 = true;
            }
            this.declaringClass = ((Method)object2).getDeclaringClass();
            object2 = object3;
        } else {
            object = ((Field)object3).getType();
            object2 = ((Field)object3).getGenericType();
            this.declaringClass = ((Field)object3).getDeclaringClass();
            bl2 = Modifier.isFinal(((Field)object3).getModifiers());
        }
        this.getOnly = bl2;
        if (type != null && object == Object.class && object2 instanceof TypeVariable && (object3 = FieldInfo.getInheritGenericType(type, (TypeVariable)object2)) != null) {
            this.fieldClass = TypeUtils.getClass((Type)object3);
            this.fieldType = object3;
            this.isEnum = ((Class)object).isEnum();
            return;
        }
        Object object5 = object2;
        object3 = object;
        if (!(object2 instanceof Class)) {
            void var5_6;
            Type type4 = FieldInfo.getFieldType(type, (Type)var5_6, (Type)object2);
            object3 = object;
            Type type5 = type4;
            if (type4 != object2) {
                if (type4 instanceof ParameterizedType) {
                    object3 = TypeUtils.getClass(type4);
                    Type type6 = type4;
                } else {
                    object3 = object;
                    Type type7 = type4;
                    if (type4 instanceof Class) {
                        object3 = TypeUtils.getClass(type4);
                        Type type8 = type4;
                    }
                }
            }
        }
        this.fieldType = var8_13;
        this.fieldClass = object3;
        this.isEnum = ((Class)object3).isEnum();
    }

    public static Type getFieldType(Class<?> type, Type type2, Type type3) {
        int n2;
        TypeVariable<Class<Object>>[] typeVariableArray;
        Object object;
        Object object2;
        if (type == null || type2 == null) {
            return type3;
        }
        if (type3 instanceof GenericArrayType) {
            Type type4 = ((GenericArrayType)type3).getGenericComponentType();
            if (type4 != (type = FieldInfo.getFieldType(type, type2, type4))) {
                return Array.newInstance(TypeUtils.getClass(type), 0).getClass();
            }
            return type3;
        }
        if (!TypeUtils.isGenericParamType(type2)) {
            return type3;
        }
        if (type3 instanceof TypeVariable) {
            object2 = (ParameterizedType)TypeUtils.getGenericParamType(type2);
            object = TypeUtils.getClass((Type)object2);
            typeVariableArray = (TypeVariable)type3;
            object = object.getTypeParameters();
            for (n2 = 0; n2 < ((TypeVariable<Class<?>>[])object).length; ++n2) {
                if (!object[n2].getName().equals(typeVariableArray.getName())) continue;
                return object2.getActualTypeArguments()[n2];
            }
        }
        if (type3 instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType)type3;
            Type[] typeArray = parameterizedType.getActualTypeArguments();
            n2 = 0;
            object = null;
            object2 = null;
            for (int i2 = 0; i2 < typeArray.length; ++i2) {
                typeVariableArray = typeArray[i2];
                ParameterizedType parameterizedType2 = object2;
                int n3 = n2;
                TypeVariable<Class<Object>>[] typeVariableArray2 = object;
                if (typeVariableArray instanceof TypeVariable) {
                    Type type5 = typeVariableArray;
                    parameterizedType2 = object2;
                    n3 = n2;
                    typeVariableArray2 = object;
                    if (type2 instanceof ParameterizedType) {
                        typeVariableArray = object;
                        if (object == null) {
                            typeVariableArray = ((Class)type).getTypeParameters();
                        }
                        int n4 = 0;
                        while (true) {
                            parameterizedType2 = object2;
                            n3 = n2;
                            typeVariableArray2 = typeVariableArray;
                            if (n4 >= typeVariableArray.length) break;
                            object = object2;
                            if (typeVariableArray[n4].getName().equals(type5.getName())) {
                                object = object2;
                                if (object2 == null) {
                                    object = ((ParameterizedType)type2).getActualTypeArguments();
                                }
                                typeArray[i2] = object[n4];
                                n2 = 1;
                            }
                            ++n4;
                            object2 = object;
                        }
                    }
                }
                object2 = parameterizedType2;
                n2 = n3;
                object = typeVariableArray2;
            }
            if (n2 != 0) {
                return new ParameterizedTypeImpl(typeArray, parameterizedType.getOwnerType(), parameterizedType.getRawType());
            }
        }
        return type3;
    }

    /*
     * Enabled aggressive block sorting
     */
    public static Type getInheritGenericType(Class<?> type, TypeVariable<?> typeVariable) {
        Type type2;
        Object obj = typeVariable.getGenericDeclaration();
        while ((type2 = ((Class)type).getGenericSuperclass()) != null) {
            TypeVariable<?>[] typeVariableArray;
            if (type2 instanceof ParameterizedType && (type = (ParameterizedType)type2).getRawType() == obj) {
                typeVariableArray = obj.getTypeParameters();
                type = type.getActualTypeArguments();
            } else {
                type = TypeUtils.getClass(type2);
                if (type2 != null) continue;
                return null;
            }
            for (int i2 = 0; i2 < typeVariableArray.length; ++i2) {
                if (typeVariableArray[i2] != typeVariable) continue;
                return type[i2];
            }
        }
        return null;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public int compareTo(FieldInfo fieldInfo) {
        int n2 = 1;
        if (this.ordinal < fieldInfo.ordinal) {
            return -1;
        }
        int n3 = n2;
        if (this.ordinal > fieldInfo.ordinal) return n3;
        int n4 = this.name.compareTo(fieldInfo.name);
        if (n4 != 0) {
            return n4;
        }
        Class<?> clazz = this.getDeclaredClass();
        Class<?> clazz2 = fieldInfo.getDeclaredClass();
        if (clazz != null && clazz2 != null && clazz != clazz2) {
            if (clazz.isAssignableFrom(clazz2)) {
                return -1;
            }
            n3 = n2;
            if (clazz2.isAssignableFrom(clazz)) return n3;
        }
        n4 = this.field != null && this.field.getType() == this.fieldClass ? 1 : 0;
        boolean bl2 = fieldInfo.field != null && fieldInfo.field.getType() == fieldInfo.fieldClass;
        if (n4 != 0) {
            n3 = n2;
            if (!bl2) return n3;
        }
        if (bl2 && n4 == 0) {
            return -1;
        }
        if (fieldInfo.fieldClass.isPrimitive()) {
            n3 = n2;
            if (!this.fieldClass.isPrimitive()) return n3;
        }
        if (this.fieldClass.isPrimitive() && !fieldInfo.fieldClass.isPrimitive()) {
            return -1;
        }
        if (fieldInfo.fieldClass.getName().startsWith("java.")) {
            n3 = n2;
            if (!this.fieldClass.getName().startsWith("java.")) return n3;
        }
        if (!this.fieldClass.getName().startsWith("java.")) return this.fieldClass.getName().compareTo(fieldInfo.fieldClass.getName());
        if (fieldInfo.fieldClass.getName().startsWith("java.")) return this.fieldClass.getName().compareTo(fieldInfo.fieldClass.getName());
        return -1;
    }

    protected char[] genFieldNameChars() {
        int n2 = this.name.length();
        char[] cArray = new char[n2 + 3];
        this.name.getChars(0, this.name.length(), cArray, 1);
        cArray[0] = 34;
        cArray[n2 + 1] = 34;
        cArray[n2 + 2] = 58;
        return cArray;
    }

    public Object get(Object object) throws IllegalAccessException, InvocationTargetException {
        if (this.method != null) {
            return this.method.invoke(object, new Object[0]);
        }
        return this.field.get(object);
    }

    /*
     * Enabled aggressive block sorting
     */
    public <T extends Annotation> T getAnnation(Class<T> clazz) {
        JSONField jSONField;
        if (clazz == JSONField.class) {
            jSONField = this.getAnnotation();
            return (T)jSONField;
        } else {
            Object var2_3 = null;
            if (this.method != null) {
                var2_3 = this.method.getAnnotation(clazz);
            }
            jSONField = var2_3;
            if (var2_3 != null) return (T)jSONField;
            jSONField = var2_3;
            if (this.field == null) return (T)jSONField;
            return this.field.getAnnotation(clazz);
        }
    }

    public JSONField getAnnotation() {
        if (this.fieldAnnotation != null) {
            return this.fieldAnnotation;
        }
        return this.methodAnnotation;
    }

    protected Class<?> getDeclaredClass() {
        if (this.method != null) {
            return this.method.getDeclaringClass();
        }
        if (this.field != null) {
            return this.field.getDeclaringClass();
        }
        return null;
    }

    public String getFormat() {
        Object object = null;
        Object object2 = this.getAnnotation();
        if (object2 != null) {
            object = object2 = object2.format();
            if (((String)object2).trim().length() == 0) {
                object = null;
            }
        }
        return object;
    }

    public Member getMember() {
        if (this.method != null) {
            return this.method;
        }
        return this.field;
    }

    public void set(Object object, Object object2) throws IllegalAccessException, InvocationTargetException {
        if (this.method != null) {
            this.method.invoke(object, object2);
            return;
        }
        this.field.set(object, object2);
    }

    public void setAccessible() throws SecurityException {
        if (this.method != null) {
            TypeUtils.setAccessible(this.method);
            return;
        }
        TypeUtils.setAccessible(this.field);
    }

    public String toString() {
        return this.name;
    }
}

