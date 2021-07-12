/*
 * Decompiled with CFR 0.151.
 */
package com.alibaba.fastjson.util;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.annotation.JSONCreator;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONPOJOBuilder;
import com.alibaba.fastjson.annotation.JSONType;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.util.FieldInfo;
import com.alibaba.fastjson.util.TypeUtils;
import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.Field;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class JavaBeanInfo {
    public final Method buildMethod;
    public final Class<?> builderClass;
    public final Class<?> clazz;
    public final Constructor<?> creatorConstructor;
    public final Constructor<?> defaultConstructor;
    public final int defaultConstructorParameterSize;
    public final Method factoryMethod;
    public final FieldInfo[] fields;
    public final JSONType jsonType;
    public final int parserFeatures;
    public final FieldInfo[] sortedFields;
    public final String typeName;

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    public JavaBeanInfo(Class<?> objectArray, Class<?> object, Constructor<?> constructor, Constructor<?> constructor2, Method method, Method method2, JSONType jSONType, List<FieldInfo> list) {
        void var8_11;
        String string2;
        void var7_10;
        void var6_9;
        void var5_8;
        void var4_7;
        void var3_6;
        int n2 = 0;
        this.clazz = objectArray;
        this.builderClass = object;
        this.defaultConstructor = var3_6;
        this.creatorConstructor = var4_7;
        this.factoryMethod = var5_8;
        this.parserFeatures = TypeUtils.getParserFeatures(objectArray);
        this.buildMethod = var6_9;
        this.jsonType = var7_10;
        this.typeName = var7_10 != null ? ((string2 = var7_10.typeName()).length() != 0 ? string2 : objectArray.getName()) : objectArray.getName();
        this.fields = new FieldInfo[var8_11.size()];
        var8_11.toArray(this.fields);
        Object[] objectArray2 = new FieldInfo[this.fields.length];
        System.arraycopy(this.fields, 0, objectArray2, 0, this.fields.length);
        Arrays.sort(objectArray2);
        objectArray = objectArray2;
        if (Arrays.equals(this.fields, objectArray2)) {
            objectArray = this.fields;
        }
        this.sortedFields = objectArray;
        if (var3_6 != null) {
            n2 = var3_6.getParameterTypes().length;
        }
        this.defaultConstructorParameterSize = n2;
    }

    /*
     * Enabled aggressive block sorting
     */
    static boolean add(List<FieldInfo> list, FieldInfo fieldInfo) {
        for (int i2 = list.size() - 1; i2 >= 0; --i2) {
            FieldInfo fieldInfo2 = list.get(i2);
            if (!fieldInfo2.name.equals(fieldInfo.name) || fieldInfo2.getOnly && !fieldInfo.getOnly) {
                continue;
            }
            if (fieldInfo2.fieldClass.isAssignableFrom(fieldInfo.fieldClass)) {
                list.remove(i2);
                break;
            }
            if (fieldInfo2.compareTo(fieldInfo) >= 0) {
                return false;
            }
            list.remove(i2);
            break;
        }
        list.add(fieldInfo);
        return true;
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static JavaBeanInfo build(Class<?> clazz, Type object) {
        char c2;
        int n2;
        int n3;
        Object object22;
        int n4;
        int n5;
        int n6;
        Object object3;
        Object object4;
        ArrayList<FieldInfo> arrayList;
        Object object5;
        Constructor<?> constructor;
        Object object6;
        Method[] methodArray;
        Field[] fieldArray;
        Class<?> clazz2;
        JSONType jSONType;
        block65: {
            block74: {
                block73: {
                    int n7;
                    JSONPOJOBuilder jSONPOJOBuilder;
                    block68: {
                        Executable executable;
                        block63: {
                            Annotation[][] annotationArray;
                            block64: {
                                block61: {
                                    Annotation[][] annotationArray2;
                                    block62: {
                                        block67: {
                                            block66: {
                                                block59: {
                                                    block60: {
                                                        jSONType = clazz.getAnnotation(JSONType.class);
                                                        clazz2 = JavaBeanInfo.getBuilderClass(jSONType);
                                                        fieldArray = clazz.getDeclaredFields();
                                                        methodArray = clazz.getMethods();
                                                        object6 = clazz2 == null ? clazz : clazz2;
                                                        constructor = JavaBeanInfo.getDefaultConstructor(object6);
                                                        jSONPOJOBuilder = null;
                                                        object5 = null;
                                                        arrayList = new ArrayList<FieldInfo>();
                                                        if (constructor != null || clazz.isInterface() || Modifier.isAbstract(clazz.getModifiers())) break block59;
                                                        executable = JavaBeanInfo.getCreatorConstructor(clazz);
                                                        if (executable == null) break block60;
                                                        TypeUtils.setAccessible(executable);
                                                        object5 = ((Constructor)executable).getParameterTypes();
                                                        if (((Class<?>[])object5).length <= 0) break block61;
                                                        annotationArray2 = ((Constructor)executable).getParameterAnnotations();
                                                        break block62;
                                                    }
                                                    executable = JavaBeanInfo.getFactoryMethod(clazz, methodArray);
                                                    if (executable == null) {
                                                        throw new JSONException("default constructor not found. " + clazz);
                                                    }
                                                    TypeUtils.setAccessible(executable);
                                                    object5 = ((Method)executable).getParameterTypes();
                                                    if (((Class<?>[])object5).length <= 0) break block63;
                                                    annotationArray = ((Method)executable).getParameterAnnotations();
                                                    break block64;
                                                }
                                                if (constructor != null) {
                                                    TypeUtils.setAccessible(constructor);
                                                }
                                                object4 = jSONPOJOBuilder;
                                                if (clazz2 == null) break block65;
                                                object6 = null;
                                                object4 = clazz2.getAnnotation(JSONPOJOBuilder.class);
                                                if (object4 != null) {
                                                    object6 = object4.withPrefix();
                                                }
                                                if (object6 == null) break block66;
                                                object4 = object6;
                                                if (((String)object6).length() != 0) break block67;
                                            }
                                            object4 = "with";
                                        }
                                        object3 = clazz2.getMethods();
                                        n7 = ((Method[])object3).length;
                                        break block68;
                                    }
                                    for (int i2 = 0; i2 < ((Class<?>[])object5).length; ++i2) {
                                        Object object7 = annotationArray2[i2];
                                        object6 = null;
                                        int n8 = ((Annotation[])object7).length;
                                        int n9 = 0;
                                        while (true) {
                                            block70: {
                                                block69: {
                                                    object = object6;
                                                    if (n9 >= n8) break block69;
                                                    object = object7[n9];
                                                    if (!(object instanceof JSONField)) break block70;
                                                    object = (JSONField)object;
                                                }
                                                if (object != null) break;
                                                throw new JSONException("illegal json creator");
                                            }
                                            ++n9;
                                        }
                                        object6 = object5[i2];
                                        object7 = ((Constructor)executable).getGenericParameterTypes()[i2];
                                        Field field = TypeUtils.getField(clazz, object.name(), fieldArray);
                                        n9 = object.ordinal();
                                        n8 = SerializerFeature.of(object.serialzeFeatures());
                                        JavaBeanInfo.add(arrayList, new FieldInfo(object.name(), clazz, (Class<?>)object6, (Type)object7, field, n9, n8));
                                    }
                                }
                                return new JavaBeanInfo(clazz, clazz2, null, (Constructor<?>)executable, null, null, jSONType, (List<FieldInfo>)arrayList);
                            }
                            for (int i3 = 0; i3 < ((GenericDeclaration[])object5).length; ++i3) {
                                Object object8 = annotationArray[i3];
                                object6 = null;
                                int n10 = ((Annotation[])object8).length;
                                int n11 = 0;
                                while (true) {
                                    block72: {
                                        block71: {
                                            object = object6;
                                            if (n11 >= n10) break block71;
                                            object = object8[n11];
                                            if (!(object instanceof JSONField)) break block72;
                                            object = (JSONField)object;
                                        }
                                        if (object != null) break;
                                        throw new JSONException("illegal json creator");
                                    }
                                    ++n11;
                                }
                                object6 = object5[i3];
                                object8 = ((Method)executable).getGenericParameterTypes()[i3];
                                Field field = TypeUtils.getField(clazz, object.name(), fieldArray);
                                n11 = object.ordinal();
                                n10 = SerializerFeature.of(object.serialzeFeatures());
                                JavaBeanInfo.add(arrayList, new FieldInfo(object.name(), clazz, (Class<?>)object6, (Type)object8, field, n11, n10));
                            }
                        }
                        return new JavaBeanInfo(clazz, clazz2, null, null, (Method)executable, null, jSONType, arrayList);
                    }
                    for (n6 = 0; n6 < n7; ++n6) {
                        Method method = object3[n6];
                        if (Modifier.isStatic(method.getModifiers()) || !method.getReturnType().equals(clazz2)) continue;
                        n5 = 0;
                        n4 = 0;
                        object6 = object22 = method.getAnnotation(JSONField.class);
                        if (object22 == null) {
                            object6 = TypeUtils.getSupperMethodAnnotation(clazz, method);
                        }
                        if (object6 != null) {
                            if (!object6.deserialize()) continue;
                            n3 = object6.ordinal();
                            n2 = SerializerFeature.of(object6.serialzeFeatures());
                            n5 = n3;
                            n4 = n2;
                            if (object6.name().length() != 0) {
                                JavaBeanInfo.add(arrayList, new FieldInfo(object6.name(), method, null, clazz, (Type)object, n3, n2, (JSONField)object6, null, null));
                                continue;
                            }
                        }
                        if (!((String)(object22 = method.getName())).startsWith((String)object4) || ((String)object22).length() <= ((String)object4).length() || !Character.isUpperCase(c2 = ((String)object22).charAt(((String)object4).length()))) continue;
                        object22 = new StringBuilder(((String)object22).substring(((String)object4).length()));
                        ((StringBuilder)object22).setCharAt(0, Character.toLowerCase(c2));
                        JavaBeanInfo.add(arrayList, new FieldInfo(((StringBuilder)object22).toString(), method, null, clazz, (Type)object, n5, n4, (JSONField)object6, null, null));
                    }
                    object4 = jSONPOJOBuilder;
                    if (clazz2 == null) break block65;
                    object4 = clazz2.getAnnotation(JSONPOJOBuilder.class);
                    object6 = null;
                    if (object4 != null) {
                        object6 = object4.buildMethod();
                    }
                    if (object6 == null) break block73;
                    object4 = object6;
                    if (((String)object6).length() != 0) break block74;
                }
                object4 = "build";
            }
            try {
                object6 = clazz2.getMethod((String)object4, new Class[0]);
            }
            catch (SecurityException securityException) {
                object6 = object5;
            }
            catch (NoSuchMethodException noSuchMethodException) {
                object6 = object5;
            }
            object4 = object6;
            if (object6 == null) {
                try {
                    object4 = clazz2.getMethod("create", new Class[0]);
                }
                catch (SecurityException securityException) {
                    object4 = object6;
                }
                catch (NoSuchMethodException noSuchMethodException) {
                    object4 = object6;
                }
            }
            if (object4 == null) {
                throw new JSONException("buildMethod not found.");
            }
            TypeUtils.setAccessible((AccessibleObject)object4);
        }
        for (Method method : methodArray) {
            void var12_19;
            n6 = 0;
            n5 = 0;
            String string2 = method.getName();
            if (string2.length() < 4 || Modifier.isStatic(method.getModifiers()) || !method.getReturnType().equals(Void.TYPE) && !method.getReturnType().equals(clazz) || ((GenericDeclaration[])(object3 = method.getParameterTypes())).length != 1) continue;
            object5 = object6 = method.getAnnotation(JSONField.class);
            if (object6 == null) {
                object5 = TypeUtils.getSupperMethodAnnotation(clazz, method);
            }
            if (object5 != null) {
                if (!object5.deserialize()) continue;
                n3 = object5.ordinal();
                n2 = SerializerFeature.of(object5.serialzeFeatures());
                n6 = n3;
                n5 = n2;
                if (object5.name().length() != 0) {
                    JavaBeanInfo.add(arrayList, new FieldInfo(object5.name(), method, null, clazz, (Type)object, n3, n2, (JSONField)object5, null, null));
                    continue;
                }
            }
            if (!string2.startsWith("set")) continue;
            c2 = string2.charAt(3);
            if (Character.isUpperCase(c2) || c2 > '\u0200') {
                object6 = TypeUtils.compatibleWithJavaBean ? TypeUtils.decapitalize(string2.substring(3)) : Character.toLowerCase(string2.charAt(3)) + string2.substring(4);
            } else if (c2 == '_') {
                object6 = string2.substring(4);
            } else if (c2 == 'f') {
                object6 = string2.substring(3);
            } else {
                if (string2.length() < 5 || !Character.isUpperCase(string2.charAt(4))) continue;
                object6 = TypeUtils.decapitalize(string2.substring(3));
            }
            Object object9 = object22 = TypeUtils.getField(clazz, (String)object6, fieldArray);
            if (object22 == null) {
                Object object10 = object22;
                if (object3[0] == Boolean.TYPE) {
                    Field field = TypeUtils.getField(clazz, "is" + Character.toUpperCase(((String)object6).charAt(0)) + ((String)object6).substring(1), fieldArray);
                }
            }
            object22 = null;
            n3 = n6;
            n2 = n5;
            if (var12_19 != null) {
                object3 = var12_19.getAnnotation(JSONField.class);
                n3 = n6;
                n2 = n5;
                object22 = object3;
                if (object3 != null) {
                    n6 = object3.ordinal();
                    n5 = SerializerFeature.of(object3.serialzeFeatures());
                    n3 = n6;
                    n2 = n5;
                    object22 = object3;
                    if (object3.name().length() != 0) {
                        JavaBeanInfo.add(arrayList, new FieldInfo(object3.name(), method, (Field)var12_19, clazz, (Type)object, n6, n5, (JSONField)object5, (JSONField)object3, null));
                        continue;
                    }
                }
            }
            JavaBeanInfo.add(arrayList, new FieldInfo((String)object6, method, (Field)var12_19, clazz, (Type)object, n3, n2, (JSONField)object5, (JSONField)object22, null));
        }
        for (Object object22 : clazz.getFields()) {
            block58: {
                if (Modifier.isStatic(((Field)object22).getModifiers())) continue;
                n4 = 0;
                object6 = arrayList.iterator();
                do {
                    n5 = n4;
                    if (!object6.hasNext()) break block58;
                } while (!((FieldInfo)object6.next()).name.equals(((Field)object22).getName()));
                n5 = 1;
            }
            if (n5 != 0) continue;
            n5 = 0;
            n4 = 0;
            object5 = ((Field)object22).getName();
            object3 = ((Field)object22).getAnnotation(JSONField.class);
            object6 = object5;
            if (object3 != null) {
                n3 = object3.ordinal();
                n2 = SerializerFeature.of(object3.serialzeFeatures());
                n5 = n3;
                n4 = n2;
                object6 = object5;
                if (object3.name().length() != 0) {
                    object6 = object3.name();
                    n4 = n2;
                    n5 = n3;
                }
            }
            JavaBeanInfo.add(arrayList, new FieldInfo((String)object6, null, (Field)object22, clazz, (Type)object, n5, n4, null, (JSONField)object3, null));
        }
        object5 = clazz.getMethods();
        n5 = ((GenericDeclaration[])object5).length;
        n6 = 0;
        while (n6 < n5) {
            GenericDeclaration genericDeclaration = object5[n6];
            object6 = ((Method)genericDeclaration).getName();
            if (((String)object6).length() >= 4 && !Modifier.isStatic(((Method)genericDeclaration).getModifiers()) && ((String)object6).startsWith("get") && Character.isUpperCase(((String)object6).charAt(3)) && ((Method)genericDeclaration).getParameterTypes().length == 0 && (Collection.class.isAssignableFrom(((Method)genericDeclaration).getReturnType()) || Map.class.isAssignableFrom(((Method)genericDeclaration).getReturnType()) || AtomicBoolean.class == ((Method)genericDeclaration).getReturnType() || AtomicInteger.class == ((Method)genericDeclaration).getReturnType() || AtomicLong.class == ((Method)genericDeclaration).getReturnType()) && JavaBeanInfo.getField(arrayList, (String)(object6 = (object22 = ((Method)genericDeclaration).getAnnotation(JSONField.class)) != null && object22.name().length() > 0 ? object22.name() : Character.toLowerCase(((String)object6).charAt(3)) + ((String)object6).substring(4))) == null) {
                JavaBeanInfo.add(arrayList, new FieldInfo((String)object6, (Method)genericDeclaration, null, clazz, (Type)object, 0, 0, (JSONField)object22, null, null));
            }
            ++n6;
        }
        return new JavaBeanInfo(clazz, clazz2, constructor, null, null, (Method)object4, jSONType, arrayList);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static Class<?> getBuilderClass(JSONType clazz) {
        if (clazz == null) {
            return null;
        }
        Class<?> clazz2 = clazz.builder();
        clazz = clazz2;
        if (clazz2 != Void.class) return clazz;
        return null;
    }

    public static Constructor<?> getCreatorConstructor(Class<?> clazz) {
        GenericDeclaration genericDeclaration = null;
        Constructor<?>[] constructorArray = clazz.getDeclaredConstructors();
        int n2 = constructorArray.length;
        clazz = genericDeclaration;
        for (int i2 = 0; i2 < n2; ++i2) {
            Constructor<?> constructor = constructorArray[i2];
            genericDeclaration = clazz;
            if (constructor.getAnnotation(JSONCreator.class) != null) {
                if (clazz != null) {
                    throw new JSONException("multi-JSONCreator");
                }
                genericDeclaration = constructor;
            }
            clazz = genericDeclaration;
        }
        return clazz;
    }

    /*
     * Enabled aggressive block sorting
     */
    static Constructor<?> getDefaultConstructor(Class<?> clazz) {
        Constructor<?> constructor;
        if (Modifier.isAbstract(clazz.getModifiers())) {
            return null;
        }
        Constructor<?> constructor2 = null;
        Constructor<?>[] constructorArray = clazz.getDeclaredConstructors();
        int n2 = constructorArray.length;
        int n3 = 0;
        while (true) {
            constructor = constructor2;
            if (n3 >= n2 || (constructor = constructorArray[n3]).getParameterTypes().length == 0) break;
            ++n3;
        }
        constructor2 = constructor;
        if (constructor != null) return constructor2;
        constructor2 = constructor;
        if (!clazz.isMemberClass()) return constructor2;
        constructor2 = constructor;
        if (Modifier.isStatic(clazz.getModifiers())) return constructor2;
        n2 = constructorArray.length;
        n3 = 0;
        while (true) {
            constructor2 = constructor;
            if (n3 >= n2) return constructor2;
            constructor2 = constructorArray[n3];
            Class<?>[] classArray = constructor2.getParameterTypes();
            if (classArray.length == 1 && classArray[0].equals(clazz.getDeclaringClass())) {
                return constructor2;
            }
            ++n3;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private static Method getFactoryMethod(Class<?> clazz, Method[] methodArray) {
        Method method = null;
        int n2 = methodArray.length;
        int n3 = 0;
        while (n3 < n2) {
            Method method2;
            Method method3 = methodArray[n3];
            if (!Modifier.isStatic(method3.getModifiers())) {
                method2 = method;
            } else {
                method2 = method;
                if (clazz.isAssignableFrom(method3.getReturnType())) {
                    method2 = method;
                    if (method3.getAnnotation(JSONCreator.class) != null) {
                        if (method != null) {
                            throw new JSONException("multi-JSONCreator");
                        }
                        method2 = method3;
                    }
                }
            }
            ++n3;
            method = method2;
        }
        return method;
    }

    private static FieldInfo getField(List<FieldInfo> object, String string2) {
        object = object.iterator();
        while (object.hasNext()) {
            FieldInfo fieldInfo = (FieldInfo)object.next();
            if (!fieldInfo.name.equals(string2)) continue;
            return fieldInfo;
        }
        return null;
    }
}

