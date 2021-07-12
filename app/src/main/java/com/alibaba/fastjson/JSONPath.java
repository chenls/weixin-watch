/*
 * Decompiled with CFR 0.151.
 */
package com.alibaba.fastjson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONAware;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONPathException;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.parser.deserializer.FieldDeserializer;
import com.alibaba.fastjson.parser.deserializer.JavaBeanDeserializer;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.serializer.FieldSerializer;
import com.alibaba.fastjson.serializer.JavaBeanSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.util.IOUtils;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.regex.Pattern;

public class JSONPath
implements JSONAware {
    private static int CACHE_SIZE = 1024;
    private static ConcurrentMap<String, JSONPath> pathCache = new ConcurrentHashMap<String, JSONPath>(128, 0.75f, 1);
    private ParserConfig parserConfig;
    private final String path;
    private Segement[] segments;
    private SerializeConfig serializeConfig;

    public JSONPath(String string2) {
        this(string2, SerializeConfig.getGlobalInstance(), ParserConfig.getGlobalInstance());
    }

    public JSONPath(String string2, SerializeConfig serializeConfig, ParserConfig parserConfig) {
        if (string2 == null || string2.isEmpty()) {
            throw new JSONPathException("json-path can not be null or empty");
        }
        this.path = string2;
        this.serializeConfig = serializeConfig;
        this.parserConfig = parserConfig;
    }

    public static void arrayAdd(Object object, String string2, Object ... objectArray) {
        JSONPath.compile(string2).arrayAdd(object, objectArray);
    }

    public static JSONPath compile(String string2) {
        JSONPath jSONPath;
        if (string2 == null) {
            throw new JSONPathException("jsonpath can not be null");
        }
        JSONPath jSONPath2 = jSONPath = (JSONPath)pathCache.get(string2);
        if (jSONPath == null) {
            jSONPath2 = jSONPath = new JSONPath(string2);
            if (pathCache.size() < CACHE_SIZE) {
                pathCache.putIfAbsent(string2, jSONPath);
                jSONPath2 = (JSONPath)pathCache.get(string2);
            }
        }
        return jSONPath2;
    }

    public static boolean contains(Object object, String string2) {
        if (object == null) {
            return false;
        }
        return JSONPath.compile(string2).contains(object);
    }

    public static boolean containsValue(Object object, String string2, Object object2) {
        return JSONPath.compile(string2).containsValue(object, object2);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    static boolean eq(Object object, Object object2) {
        boolean bl2 = false;
        if (object == object2) {
            return true;
        }
        boolean bl3 = bl2;
        if (object == null) return bl3;
        bl3 = bl2;
        if (object2 == null) return bl3;
        if (object.getClass() == object2.getClass()) {
            return object.equals(object2);
        }
        if (!(object instanceof Number)) return object.equals(object2);
        bl3 = bl2;
        if (!(object2 instanceof Number)) return bl3;
        return JSONPath.eqNotNull((Number)object, (Number)object2);
    }

    static boolean eqNotNull(Number number, Number number2) {
        Class<?> clazz = number.getClass();
        boolean bl2 = JSONPath.isInt(clazz);
        Class<?> clazz2 = number2.getClass();
        boolean bl3 = JSONPath.isInt(clazz2);
        if (number instanceof BigDecimal) {
            BigDecimal bigDecimal = (BigDecimal)number;
            if (bl3) {
                return bigDecimal.equals(BigDecimal.valueOf(number2.longValue()));
            }
        }
        if (bl2) {
            if (bl3) {
                return number.longValue() == number2.longValue();
            }
            if (number2 instanceof BigInteger) {
                number2 = (BigInteger)number;
                return BigInteger.valueOf(number.longValue()).equals(number2);
            }
        }
        if (bl3 && number instanceof BigInteger) {
            return ((BigInteger)number).equals(BigInteger.valueOf(number2.longValue()));
        }
        boolean bl4 = JSONPath.isDouble(clazz);
        boolean bl5 = JSONPath.isDouble(clazz2);
        if (bl4 && bl5 || bl4 && bl3 || bl5 && bl2) {
            return number.doubleValue() == number2.doubleValue();
        }
        return false;
    }

    public static Object eval(Object object, String string2) {
        return JSONPath.compile(string2).eval(object);
    }

    protected static boolean isDouble(Class<?> clazz) {
        return clazz == Float.class || clazz == Double.class;
    }

    protected static boolean isInt(Class<?> clazz) {
        return clazz == Byte.class || clazz == Short.class || clazz == Integer.class || clazz == Long.class;
    }

    public static Object read(String object, String string2) {
        object = JSON.parse((String)object);
        return JSONPath.compile(string2).eval(object);
    }

    public static boolean set(Object object, String string2, Object object2) {
        return JSONPath.compile(string2).set(object, object2);
    }

    public static int size(Object object, String object2) {
        object2 = JSONPath.compile((String)object2);
        return ((JSONPath)object2).evalSize(((JSONPath)object2).eval(object));
    }

    /*
     * Enabled aggressive block sorting
     */
    public void arrayAdd(Object clazz, Object ... object) {
        if (object != null && ((Object[])object).length != 0 && clazz != null) {
            int n2;
            int n3;
            this.init();
            Object object2 = clazz;
            Class<?> clazz2 = null;
            for (n3 = 0; n3 < this.segments.length; ++n3) {
                if (n3 == this.segments.length - 1) {
                    clazz2 = object2;
                }
                object2 = this.segments[n3].eval(this, clazz, object2);
            }
            if (object2 == null) {
                throw new JSONPathException("value not found in path " + this.path);
            }
            if (object2 instanceof Collection) {
                clazz = (Collection)object2;
                n2 = ((Object[])object).length;
            } else {
                clazz = object2.getClass();
                if (!clazz.isArray()) {
                    throw new JSONException("unsupported array put operation. " + clazz);
                }
                int n4 = Array.getLength(object2);
                clazz = Array.newInstance(clazz.getComponentType(), ((Object[])object).length + n4);
                System.arraycopy(object2, 0, clazz, 0, n4);
                for (n3 = 0; n3 < ((Object[])object).length; ++n3) {
                    Array.set(clazz, n4 + n3, object[n3]);
                }
                Segement segement = this.segments[this.segments.length - 1];
                if (segement instanceof PropertySegement) {
                    ((PropertySegement)segement).setValue(this, clazz2, clazz);
                    return;
                }
                if (segement instanceof ArrayAccessSegement) {
                    ((ArrayAccessSegement)segement).setValue(this, clazz2, clazz);
                    return;
                }
                throw new UnsupportedOperationException();
            }
            for (n3 = 0; n3 < n2; ++n3) {
                clazz.add((Object)object[n3]);
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean contains(Object object) {
        if (object != null) {
            this.init();
            Object object2 = object;
            int n2 = 0;
            while (true) {
                if (n2 >= this.segments.length) {
                    return true;
                }
                if ((object2 = this.segments[n2].eval(this, object, object2)) == null) break;
                ++n2;
            }
        }
        return false;
    }

    public boolean containsValue(Object iterator, Object object) {
        if ((iterator = this.eval(iterator)) == object) {
            return true;
        }
        if (iterator == null) {
            return false;
        }
        if (iterator instanceof Iterable) {
            iterator = ((Iterable)((Object)iterator)).iterator();
            while (iterator.hasNext()) {
                if (!JSONPath.eq(iterator.next(), object)) continue;
                return true;
            }
            return false;
        }
        return JSONPath.eq(iterator, object);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public Object eval(Object object) {
        if (object == null) {
            return null;
        }
        this.init();
        Object object2 = object;
        int n2 = 0;
        while (true) {
            Object object3 = object2;
            if (n2 >= this.segments.length) return object3;
            object2 = this.segments[n2].eval(this, object, object2);
            ++n2;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    int evalSize(Object iterator) {
        int n2 = -1;
        if (iterator == null) {
            return n2;
        }
        if (iterator instanceof Collection) {
            return ((Collection)((Object)iterator)).size();
        }
        if (iterator instanceof Object[]) {
            return ((Object[])iterator).length;
        }
        if (iterator.getClass().isArray()) {
            return Array.getLength(iterator);
        }
        if (iterator instanceof Map) {
            int n3 = 0;
            iterator = ((Map)((Object)iterator)).values().iterator();
            while (true) {
                n2 = n3++;
                if (!iterator.hasNext()) return n2;
                if (iterator.next() == null) continue;
            }
        }
        JavaBeanSerializer javaBeanSerializer = this.getJavaBeanSerializer(iterator.getClass());
        if (javaBeanSerializer == null) return n2;
        try {
            return javaBeanSerializer.getSize(iterator);
        }
        catch (Exception exception) {
            throw new JSONPathException("evalSize error : " + this.path, exception);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    protected Object getArrayItem(Object object, int n2) {
        if (object == null) return null;
        if (object instanceof List) {
            object = (List)object;
            if (n2 >= 0) {
                if (n2 >= object.size()) return null;
                return object.get(n2);
            } else {
                if (Math.abs(n2) > object.size()) return null;
                return object.get(object.size() + n2);
            }
        }
        if (!object.getClass().isArray()) {
            throw new UnsupportedOperationException();
        }
        int n3 = Array.getLength(object);
        if (n2 >= 0) {
            if (n2 >= n3) return null;
            return Array.get(object, n2);
        }
        if (Math.abs(n2) <= n3) return Array.get(object, n3 + n2);
        return null;
    }

    protected JavaBeanSerializer getJavaBeanSerializer(Class<?> object) {
        Object var2_2 = null;
        ObjectSerializer objectSerializer = this.serializeConfig.getObjectWriter((Class<?>)object);
        object = var2_2;
        if (objectSerializer instanceof JavaBeanSerializer) {
            object = (JavaBeanSerializer)objectSerializer;
        }
        return object;
    }

    public String getPath() {
        return this.path;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    protected Object getPropertyValue(Object arrayList, String string2, boolean bl2) {
        if (arrayList == null) {
            return null;
        }
        if (arrayList instanceof Map) {
            return ((Map)((Object)arrayList)).get(string2);
        }
        Object object = this.getJavaBeanSerializer(arrayList.getClass());
        if (object != null) {
            block7: {
                try {
                    object = ((JavaBeanSerializer)object).getFieldSerializer(string2);
                    if (object != null) break block7;
                    return null;
                }
                catch (Exception exception) {
                    throw new JSONPathException("jsonpath error, path " + this.path + ", segement " + string2, exception);
                }
            }
            return ((FieldSerializer)object).getPropertyValue(arrayList);
        }
        if (!(arrayList instanceof List)) throw new JSONPathException("jsonpath error, path " + this.path + ", segement " + string2);
        List list = arrayList;
        object = new ArrayList<Object>(list.size());
        int n2 = 0;
        while (true) {
            arrayList = object;
            if (n2 >= list.size()) return arrayList;
            object.add(this.getPropertyValue(list.get(n2), string2, bl2));
            ++n2;
        }
    }

    protected Collection<Object> getPropertyValues(Object list) {
        JavaBeanSerializer javaBeanSerializer = this.getJavaBeanSerializer(list.getClass());
        if (javaBeanSerializer != null) {
            try {
                list = javaBeanSerializer.getFieldValues(list);
                return list;
            }
            catch (Exception exception) {
                throw new JSONPathException("jsonpath error, path " + this.path, exception);
            }
        }
        if (list instanceof Map) {
            return ((Map)((Object)list)).values();
        }
        throw new UnsupportedOperationException();
    }

    protected void init() {
        if (this.segments != null) {
            return;
        }
        if ("*".equals(this.path)) {
            this.segments = new Segement[]{WildCardSegement.instance};
            return;
        }
        this.segments = new JSONPathParser(this.path).explain();
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean set(Object object, Object object2) {
        Object object3;
        block8: {
            block7: {
                if (object == null) break block7;
                this.init();
                Object object4 = object;
                Object object5 = null;
                int n2 = 0;
                while (true) {
                    object3 = object5;
                    if (n2 >= this.segments.length) break;
                    if (n2 == this.segments.length - 1) {
                        object3 = object4;
                        break;
                    }
                    object4 = this.segments[n2].eval(this, object, object4);
                    object3 = object5;
                    if (object4 == null) break;
                    ++n2;
                }
                if (object3 != null) break block8;
            }
            return false;
        }
        object = this.segments[this.segments.length - 1];
        if (object instanceof PropertySegement) {
            ((PropertySegement)object).setValue(this, object3, object2);
            return true;
        }
        if (object instanceof ArrayAccessSegement) {
            return ((ArrayAccessSegement)object).setValue(this, object3, object2);
        }
        throw new UnsupportedOperationException();
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean setArrayItem(JSONPath clazz, Object object, int n2, Object object2) {
        if (object instanceof List) {
            clazz = (List)object;
            if (n2 < 0) {
                clazz.set(clazz.size() + n2, (Object)object2);
                return true;
            }
            clazz.set(n2, (Object)object2);
            return true;
        } else {
            clazz = object.getClass();
            if (!clazz.isArray()) {
                throw new JSONPathException("unsupported set operation." + clazz);
            }
            int n3 = Array.getLength(object);
            if (n2 >= 0) {
                if (n2 >= n3) return true;
                Array.set(object, n2, object2);
                return true;
            } else {
                if (Math.abs(n2) > n3) return true;
                Array.set(object, n3 + n2, object2);
                return true;
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    protected boolean setPropertyValue(Object iterator, String object, Object object2) {
        block6: {
            if (iterator instanceof Map) {
                ((Map)((Object)iterator)).put(object, object2);
                return true;
            }
            if (iterator instanceof List) {
                for (Object e2 : (List)((Object)iterator)) {
                    if (e2 == null) continue;
                    this.setPropertyValue(e2, (String)object, object2);
                }
            }
            break block6;
            return true;
        }
        ObjectDeserializer objectDeserializer = this.parserConfig.getDeserializer(iterator.getClass());
        JavaBeanDeserializer javaBeanDeserializer = null;
        if (objectDeserializer instanceof JavaBeanDeserializer) {
            javaBeanDeserializer = (JavaBeanDeserializer)objectDeserializer;
        }
        if (javaBeanDeserializer == null) {
            throw new UnsupportedOperationException();
        }
        if ((object = javaBeanDeserializer.getFieldDeserializer((String)object)) == null) {
            return false;
        }
        ((FieldDeserializer)object).setValue(iterator, object2);
        return true;
    }

    public int size(Object object) {
        if (object == null) {
            return -1;
        }
        this.init();
        Object object2 = object;
        for (int i2 = 0; i2 < this.segments.length; ++i2) {
            object2 = this.segments[i2].eval(this, object, object2);
        }
        return this.evalSize(object2);
    }

    @Override
    public String toJSONString() {
        return JSON.toJSONString(this.path);
    }

    static class ArrayAccessSegement
    implements Segement {
        private final int index;

        public ArrayAccessSegement(int n2) {
            this.index = n2;
        }

        @Override
        public Object eval(JSONPath jSONPath, Object object, Object object2) {
            return jSONPath.getArrayItem(object2, this.index);
        }

        public boolean setValue(JSONPath jSONPath, Object object, Object object2) {
            return jSONPath.setArrayItem(jSONPath, object, this.index, object2);
        }
    }

    static interface Filter {
        public boolean apply(JSONPath var1, Object var2, Object var3, Object var4);
    }

    public static class FilterSegement
    implements Segement {
        private final Filter filter;

        public FilterSegement(Filter filter) {
            this.filter = filter;
        }

        /*
         * WARNING - void declaration
         * Enabled aggressive block sorting
         */
        @Override
        public Object eval(JSONPath jSONPath, Object object, Object object2) {
            void var4_5;
            if (object2 == null) {
                return var4_5;
            }
            ArrayList arrayList = new ArrayList();
            if (object2 instanceof Iterable) {
                Iterator iterator = ((Iterable)object2).iterator();
                while (true) {
                    ArrayList arrayList2 = arrayList;
                    if (!iterator.hasNext()) {
                        return var4_5;
                    }
                    Object t2 = iterator.next();
                    if (!this.filter.apply(jSONPath, object, object2, t2)) continue;
                    arrayList.add(t2);
                }
            }
            if (this.filter.apply(jSONPath, object, object2, object2)) {
                return object2;
            }
            return null;
        }
    }

    static class IntBetweenSegement
    implements Filter {
        private final long endValue;
        private final boolean not;
        private final String propertyName;
        private final long startValue;

        public IntBetweenSegement(String string2, long l2, long l3, boolean bl2) {
            this.propertyName = string2;
            this.startValue = l2;
            this.endValue = l3;
            this.not = bl2;
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public boolean apply(JSONPath object, Object object2, Object object3, Object object4) {
            block3: {
                block4: {
                    block2: {
                        long l2;
                        if ((object = ((JSONPath)object).getPropertyValue(object4, this.propertyName, false)) == null) break block2;
                        if (!(object instanceof Number) || (l2 = ((Number)object).longValue()) < this.startValue || l2 > this.endValue) break block3;
                        if (!this.not) break block4;
                    }
                    return false;
                }
                return true;
            }
            return this.not;
        }
    }

    static class IntInSegement
    implements Filter {
        private final boolean not;
        private final String propertyName;
        private final long[] values;

        public IntInSegement(String string2, long[] lArray, boolean bl2) {
            this.propertyName = string2;
            this.values = lArray;
            this.not = bl2;
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public boolean apply(JSONPath object, Object object2, Object object3, Object object4) {
            if ((object = ((JSONPath)object).getPropertyValue(object4, this.propertyName, false)) == null) return false;
            if (!(object instanceof Number)) return this.not;
            long l2 = ((Number)object).longValue();
            object = this.values;
            int n2 = ((Object)object).length;
            for (int i2 = 0; i2 < n2; ++i2) {
                if (object[i2] != l2) continue;
                if (this.not) return false;
                return true;
            }
            return this.not;
        }
    }

    static class IntObjInSegement
    implements Filter {
        private final boolean not;
        private final String propertyName;
        private final Long[] values;

        public IntObjInSegement(String string2, Long[] longArray, boolean bl2) {
            this.propertyName = string2;
            this.values = longArray;
            this.not = bl2;
        }

        /*
         * WARNING - void declaration
         * Enabled aggressive block sorting
         */
        @Override
        public boolean apply(JSONPath object, Object object2, Object object3, Object object4) {
            block5: {
                int n2;
                long l2;
                block6: {
                    block4: {
                        void var4_6;
                        if ((object = ((JSONPath)object).getPropertyValue(var4_6, this.propertyName, false)) == null) break block4;
                        if (!(object instanceof Number)) break block5;
                        l2 = ((Number)object).longValue();
                        object = this.values;
                        n2 = ((Object)object).length;
                        break block6;
                    }
                    object = this.values;
                    int n3 = ((Object)object).length;
                    int n4 = 0;
                    while (true) {
                        if (n4 >= n3) {
                            return this.not;
                        }
                        if (object[n4] == null) {
                            return !this.not;
                        }
                        ++n4;
                    }
                }
                for (int i2 = 0; i2 < n2; ++i2) {
                    Object object5 = object[i2];
                    if (object5 == null || (Long)object5 != l2) continue;
                    return !this.not;
                }
            }
            return this.not;
        }
    }

    static class IntOpSegement
    implements Filter {
        private final Operator op;
        private final String propertyName;
        private final long value;

        public IntOpSegement(String string2, long l2, Operator operator) {
            this.propertyName = string2;
            this.value = l2;
            this.op = operator;
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        public boolean apply(JSONPath object, Object object2, Object object3, Object object4) {
            boolean bl2 = true;
            if ((object = ((JSONPath)object).getPropertyValue(object4, this.propertyName, false)) == null) {
                return false;
            }
            if (!(object instanceof Number)) {
                return false;
            }
            long l2 = ((Number)object).longValue();
            if (this.op == Operator.EQ) {
                if (l2 == this.value) return bl2;
                return false;
            }
            if (this.op == Operator.NE) {
                if (l2 != this.value) return bl2;
                return false;
            }
            if (this.op == Operator.GE) {
                if (l2 >= this.value) return bl2;
                return false;
            }
            if (this.op == Operator.GT) {
                if (l2 > this.value) return bl2;
                return false;
            }
            if (this.op == Operator.LE) {
                if (l2 <= this.value) return bl2;
                return false;
            }
            if (this.op != Operator.LT) return false;
            if (l2 < this.value) return bl2;
            return false;
        }
    }

    static class JSONPathParser {
        private char ch;
        private int level;
        private final String path;
        private int pos;

        public JSONPathParser(String string2) {
            this.path = string2;
            this.next();
        }

        static boolean isDigitFirst(char c2) {
            return c2 == '-' || c2 == '+' || c2 >= '0' && c2 <= '9';
        }

        void accept(char c2) {
            if (this.ch != c2) {
                throw new JSONPathException("expect '" + c2 + ", but '" + this.ch + "'");
            }
            if (!this.isEOF()) {
                this.next();
            }
        }

        /*
         * Enabled aggressive block sorting
         */
        Segement buildArraySegement(String stringArray) {
            int n2 = stringArray.length();
            int n3 = stringArray.charAt(0);
            char c2 = stringArray.charAt(n2 - 1);
            int n4 = stringArray.indexOf(44);
            if (stringArray.length() > 2 && n3 == 39 && c2 == '\'') {
                if (n4 == -1) {
                    return new PropertySegement(stringArray.substring(1, n2 - 1));
                }
                stringArray = stringArray.split(",");
                String[] stringArray2 = new String[stringArray.length];
                n4 = 0;
                while (true) {
                    if (n4 >= stringArray.length) {
                        return new MultiPropertySegement(stringArray2);
                    }
                    String string2 = stringArray[n4];
                    stringArray2[n4] = string2.substring(1, string2.length() - 1);
                    ++n4;
                }
            }
            n2 = stringArray.indexOf(58);
            if (n4 == -1 && n2 == -1) {
                return new ArrayAccessSegement(Integer.parseInt((String)stringArray));
            }
            if (n4 != -1) {
                stringArray = stringArray.split(",");
                int[] nArray = new int[stringArray.length];
                n4 = 0;
                while (true) {
                    if (n4 >= stringArray.length) {
                        return new MultiIndexSegement(nArray);
                    }
                    nArray[n4] = Integer.parseInt(stringArray[n4]);
                    ++n4;
                }
            }
            if (n2 == -1) {
                throw new UnsupportedOperationException();
            }
            stringArray = stringArray.split(":");
            int[] nArray = new int[stringArray.length];
            for (n4 = 0; n4 < stringArray.length; ++n4) {
                String string3 = stringArray[n4];
                if (string3.isEmpty()) {
                    if (n4 != 0) {
                        throw new UnsupportedOperationException();
                    }
                    nArray[n4] = 0;
                    continue;
                }
                nArray[n4] = Integer.parseInt(string3);
            }
            n3 = nArray[0];
            n4 = nArray.length > 1 ? nArray[1] : -1;
            n2 = nArray.length == 3 ? nArray[2] : 1;
            if (n4 >= 0 && n4 < n3) {
                throw new UnsupportedOperationException("end must greater than or equals start. start " + n3 + ",  end " + n4);
            }
            if (n2 <= 0) {
                throw new UnsupportedOperationException("step must greater than zero : " + n2);
            }
            return new RangeSegement(n3, n4, n2);
        }

        public Segement[] explain() {
            Segement[] segementArray;
            if (this.path == null || this.path.isEmpty()) {
                throw new IllegalArgumentException();
            }
            Segement[] segementArray2 = new Segement[8];
            while (true) {
                if ((segementArray = this.readSegement()) == null) {
                    if (this.level != segementArray2.length) break;
                    return segementArray2;
                }
                int n2 = this.level;
                this.level = n2 + 1;
                segementArray2[n2] = segementArray;
            }
            segementArray = new Segement[this.level];
            System.arraycopy(segementArray2, 0, segementArray, 0, this.level);
            return segementArray;
        }

        boolean isEOF() {
            return this.pos >= this.path.length();
        }

        void next() {
            String string2 = this.path;
            int n2 = this.pos;
            this.pos = n2 + 1;
            this.ch = string2.charAt(n2);
        }

        /*
         * WARNING - void declaration
         * Enabled aggressive block sorting
         */
        Segement parseArrayAccess(boolean bl2) {
            Iterator iterator;
            boolean bl3;
            boolean bl4;
            String[] stringArray;
            void var10_5;
            String string2;
            if (bl2) {
                this.accept('[');
            }
            int n2 = 0;
            if (this.ch == '?') {
                this.next();
                this.accept('(');
                if (this.ch == '@') {
                    this.next();
                    this.accept('.');
                }
                n2 = 1;
            }
            if (n2 != 0 || IOUtils.firstIdentifier(this.ch)) {
                string2 = this.readName();
                this.skipWhitespace();
                if (n2 != 0 && this.ch == ')') {
                    this.next();
                    if (bl2) {
                        this.accept(']');
                    }
                    FilterSegement filterSegement = new FilterSegement(new NotNullSegement(string2));
                    return var10_5;
                }
                if (bl2 && this.ch == ']') {
                    this.next();
                    return new FilterSegement(new NotNullSegement(string2));
                }
                stringArray = this.readOp();
                this.skipWhitespace();
                if (stringArray == Operator.BETWEEN || stringArray == Operator.NOT_BETWEEN) {
                    bl2 = stringArray == Operator.NOT_BETWEEN;
                    Object object = this.readValue();
                    if (!"and".equalsIgnoreCase(this.readName())) {
                        throw new JSONPathException(this.path);
                    }
                    Object object2 = this.readValue();
                    if (object == null) throw new JSONPathException(this.path);
                    if (object2 == null) {
                        throw new JSONPathException(this.path);
                    }
                    if (!JSONPath.isInt(object.getClass())) throw new JSONPathException(this.path);
                    if (!JSONPath.isInt(object2.getClass())) throw new JSONPathException(this.path);
                    return new FilterSegement(new IntBetweenSegement(string2, ((Number)object).longValue(), ((Number)object2).longValue(), bl2));
                }
                if (stringArray != Operator.IN && stringArray != Operator.NOT_IN) {
                    if (this.ch == '\'' || this.ch == '\"') {
                        void var10_21;
                        void var12_63;
                        void var11_42;
                        void var10_17;
                        void var11_39;
                        void var10_19;
                        String string3 = this.readString();
                        if (n2 != 0) {
                            this.accept(')');
                        }
                        if (bl2) {
                            this.accept(']');
                        }
                        if (stringArray == Operator.RLIKE) {
                            return new FilterSegement(new RlikeSegement(string2, string3, false));
                        }
                        if (stringArray == Operator.NOT_RLIKE) {
                            return new FilterSegement(new RlikeSegement(string2, string3, true));
                        }
                        String string4 = string3;
                        if (stringArray != Operator.LIKE) {
                            String[] stringArray2 = stringArray;
                            String string5 = string3;
                            if (stringArray != Operator.NOT_LIKE) return new FilterSegement(new StringOpSegement(string2, (String)var10_19, (Operator)var11_39));
                            String string6 = string3;
                        }
                        while (var10_17.indexOf("%%") != -1) {
                            String string7 = var10_17.replaceAll("%%", "%");
                        }
                        bl2 = stringArray == Operator.NOT_LIKE;
                        n2 = var10_17.indexOf(37);
                        if (n2 == -1) {
                            if (stringArray == Operator.LIKE) {
                                Operator operator = Operator.EQ;
                                return new FilterSegement(new StringOpSegement(string2, (String)var10_19, (Operator)var11_39));
                            }
                            Operator operator = Operator.NE;
                            return new FilterSegement(new StringOpSegement(string2, (String)var10_19, (Operator)var11_39));
                        }
                        stringArray = var10_17.split("%");
                        Object var14_70 = null;
                        Object var12_62 = null;
                        Object var15_71 = null;
                        if (n2 == 0) {
                            if (var10_17.charAt(var10_17.length() - 1) == '%') {
                                String[] stringArray3 = new String[stringArray.length - 1];
                                System.arraycopy(stringArray, 1, stringArray3, 0, stringArray3.length);
                                Object var11_41 = var14_70;
                                return new FilterSegement(new MatchSegement(string2, (String)var11_42, (String)var12_63, (String[])var10_21, bl2));
                            }
                            String string8 = stringArray[stringArray.length - 1];
                            Object var11_43 = var14_70;
                            String string9 = string8;
                            Object var10_22 = var15_71;
                            if (stringArray.length <= 2) return new FilterSegement(new MatchSegement(string2, (String)var11_42, (String)var12_63, (String[])var10_21, bl2));
                            String[] stringArray4 = new String[stringArray.length - 2];
                            System.arraycopy(stringArray, 1, stringArray4, 0, stringArray4.length);
                            Object var11_44 = var14_70;
                            String string10 = string8;
                            return new FilterSegement(new MatchSegement(string2, (String)var11_42, (String)var12_63, (String[])var10_21, bl2));
                        }
                        if (var10_17.charAt(var10_17.length() - 1) == '%') {
                            String[] stringArray5 = stringArray;
                            Object var11_45 = var14_70;
                            return new FilterSegement(new MatchSegement(string2, (String)var11_42, (String)var12_63, (String[])var10_21, bl2));
                        }
                        if (stringArray.length == 1) {
                            String string11 = stringArray[0];
                            Object var10_25 = var15_71;
                            return new FilterSegement(new MatchSegement(string2, (String)var11_42, (String)var12_63, (String[])var10_21, bl2));
                        }
                        if (stringArray.length == 2) {
                            String string12 = stringArray[0];
                            String string13 = stringArray[1];
                            Object var10_26 = var15_71;
                            return new FilterSegement(new MatchSegement(string2, (String)var11_42, (String)var12_63, (String[])var10_21, bl2));
                        }
                        String string14 = stringArray[0];
                        String string15 = stringArray[stringArray.length - 1];
                        String[] stringArray6 = new String[stringArray.length - 2];
                        System.arraycopy(stringArray, 1, stringArray6, 0, stringArray6.length);
                        return new FilterSegement(new MatchSegement(string2, (String)var11_42, (String)var12_63, (String[])var10_21, bl2));
                    }
                    if (JSONPathParser.isDigitFirst(this.ch)) {
                        long l2 = this.readLongValue();
                        if (n2 != 0) {
                            this.accept(')');
                        }
                        if (!bl2) return new FilterSegement(new IntOpSegement(string2, l2, (Operator)stringArray));
                        this.accept(']');
                        return new FilterSegement(new IntOpSegement(string2, l2, (Operator)stringArray));
                    }
                    if (this.ch != 'n') throw new UnsupportedOperationException();
                    if (!"null".equals(this.readName())) throw new UnsupportedOperationException();
                    if (n2 != 0) {
                        this.accept(')');
                    }
                    this.accept(']');
                    if (stringArray == Operator.EQ) {
                        return new FilterSegement(new NullSegement(string2));
                    }
                    if (stringArray != Operator.NE) throw new UnsupportedOperationException();
                    return new FilterSegement(new NotNullSegement(string2));
                }
            } else {
                Segement segement;
                int n3 = this.pos;
                while (this.ch != ']' && this.ch != '/' && !this.isEOF()) {
                    this.next();
                }
                n2 = bl2 ? this.pos - 1 : (this.ch == '/' ? this.pos - 1 : this.pos);
                Segement segement2 = segement = this.buildArraySegement(this.path.substring(n3 - 1, n2));
                if (!bl2) return var10_5;
                Segement segement3 = segement;
                if (this.isEOF()) return var10_5;
                this.accept(']');
                return segement;
            }
            boolean bl5 = stringArray == Operator.NOT_IN;
            this.accept('(');
            ArrayList<Object> arrayList = new ArrayList<Object>();
            arrayList.add(this.readValue());
            while (true) {
                this.skipWhitespace();
                if (this.ch != ',') {
                    this.accept(')');
                    if (n2 != 0) {
                        this.accept(')');
                    }
                    if (bl2) {
                        this.accept(']');
                    }
                    n2 = 1;
                    bl4 = true;
                    bl3 = true;
                    iterator = arrayList.iterator();
                    break;
                }
                this.next();
                arrayList.add(this.readValue());
            }
            while (iterator.hasNext()) {
                Object e2 = iterator.next();
                if (e2 == null) {
                    if (n2 == 0) continue;
                    n2 = 0;
                    continue;
                }
                Class<?> clazz = e2.getClass();
                int n4 = n2;
                boolean bl6 = bl4;
                if (n2 != 0) {
                    n4 = n2;
                    bl6 = bl4;
                    if (clazz != Byte.class) {
                        n4 = n2;
                        bl6 = bl4;
                        if (clazz != Short.class) {
                            n4 = n2;
                            bl6 = bl4;
                            if (clazz != Integer.class) {
                                n4 = n2;
                                bl6 = bl4;
                                if (clazz != Long.class) {
                                    n4 = 0;
                                    bl6 = false;
                                }
                            }
                        }
                    }
                }
                n2 = n4;
                bl4 = bl6;
                if (!bl3) continue;
                n2 = n4;
                bl4 = bl6;
                if (clazz == String.class) continue;
                bl3 = false;
                n2 = n4;
                bl4 = bl6;
            }
            if (arrayList.size() == 1 && arrayList.get(0) == null) {
                if (!bl5) return new FilterSegement(new NullSegement(string2));
                return new FilterSegement(new NotNullSegement(string2));
            }
            if (n2 != 0) {
                if (arrayList.size() == 1) {
                    void var10_9;
                    long l3 = ((Number)arrayList.get(0)).longValue();
                    if (bl5) {
                        Operator operator = Operator.NE;
                        return new FilterSegement(new IntOpSegement(string2, l3, (Operator)var10_9));
                    }
                    Operator operator = Operator.EQ;
                    return new FilterSegement(new IntOpSegement(string2, l3, (Operator)var10_9));
                }
                long[] lArray = new long[arrayList.size()];
                n2 = 0;
                while (n2 < lArray.length) {
                    lArray[n2] = ((Number)arrayList.get(n2)).longValue();
                    ++n2;
                }
                return new FilterSegement(new IntInSegement(string2, lArray, bl5));
            }
            if (bl3) {
                void var10_12;
                if (arrayList.size() != 1) {
                    String[] stringArray7 = new String[arrayList.size()];
                    arrayList.toArray(stringArray7);
                    return new FilterSegement(new StringInSegement(string2, stringArray7, bl5));
                }
                String string16 = (String)arrayList.get(0);
                if (bl5) {
                    Operator operator = Operator.NE;
                    return new FilterSegement(new StringOpSegement(string2, string16, (Operator)var10_12));
                }
                Operator operator = Operator.EQ;
                return new FilterSegement(new StringOpSegement(string2, string16, (Operator)var10_12));
            }
            if (!bl4) throw new UnsupportedOperationException();
            Long[] longArray = new Long[arrayList.size()];
            n2 = 0;
            while (n2 < longArray.length) {
                Number number = (Number)arrayList.get(n2);
                if (number != null) {
                    longArray[n2] = number.longValue();
                }
                ++n2;
            }
            return new FilterSegement(new IntObjInSegement(string2, longArray, bl5));
        }

        protected long readLongValue() {
            int n2 = this.pos;
            if (this.ch == '+' || this.ch == '-') {
                this.next();
            }
            while (this.ch >= '0' && this.ch <= '9') {
                this.next();
            }
            int n3 = this.pos;
            return Long.parseLong(this.path.substring(n2 - 1, n3 - 1));
        }

        /*
         * Enabled aggressive block sorting
         */
        String readName() {
            this.skipWhitespace();
            if (this.ch != '\\' && !IOUtils.firstIdentifier(this.ch)) {
                throw new JSONPathException("illeal jsonpath syntax. " + this.path);
            }
            StringBuilder stringBuilder = new StringBuilder();
            while (!this.isEOF()) {
                if (this.ch == '\\') {
                    this.next();
                    stringBuilder.append(this.ch);
                    if (this.isEOF()) break;
                    this.next();
                    continue;
                }
                if (!IOUtils.isIdent(this.ch)) break;
                stringBuilder.append(this.ch);
                this.next();
            }
            if (this.isEOF() && IOUtils.isIdent(this.ch)) {
                stringBuilder.append(this.ch);
            }
            return stringBuilder.toString();
        }

        /*
         * WARNING - void declaration
         * Enabled aggressive block sorting
         */
        protected Operator readOp() {
            void var1_3;
            Object var1_1 = null;
            if (this.ch == '=') {
                this.next();
                Operator operator = Operator.EQ;
            } else if (this.ch == '!') {
                this.next();
                this.accept('=');
                Operator operator = Operator.NE;
            } else if (this.ch == '<') {
                this.next();
                if (this.ch == '=') {
                    this.next();
                    Operator operator = Operator.LE;
                } else {
                    Operator operator = Operator.LT;
                }
            } else if (this.ch == '>') {
                this.next();
                if (this.ch == '=') {
                    this.next();
                    Operator operator = Operator.GE;
                } else {
                    Operator operator = Operator.GT;
                }
            }
            Operator operator = var1_3;
            if (var1_3 != null) return operator;
            String string2 = this.readName();
            if ("not".equalsIgnoreCase(string2)) {
                this.skipWhitespace();
                String string3 = this.readName();
                if ("like".equalsIgnoreCase(string3)) {
                    return Operator.NOT_LIKE;
                }
                if ("rlike".equalsIgnoreCase(string3)) {
                    return Operator.NOT_RLIKE;
                }
                if ("in".equalsIgnoreCase(string3)) {
                    return Operator.NOT_IN;
                }
                if (!"between".equalsIgnoreCase(string3)) throw new UnsupportedOperationException();
                return Operator.NOT_BETWEEN;
            }
            if ("like".equalsIgnoreCase(string2)) {
                return Operator.LIKE;
            }
            if ("rlike".equalsIgnoreCase(string2)) {
                return Operator.RLIKE;
            }
            if ("in".equalsIgnoreCase(string2)) {
                return Operator.IN;
            }
            if (!"between".equalsIgnoreCase(string2)) throw new UnsupportedOperationException();
            return Operator.BETWEEN;
        }

        Segement readSegement() {
            if (this.level == 0 && this.path.length() == 1) {
                if (JSONPathParser.isDigitFirst(this.ch)) {
                    return new ArrayAccessSegement(this.ch - 48);
                }
                if (this.ch >= 'a' && this.ch <= 'z' || this.ch >= 'A' && this.ch <= 'Z') {
                    return new PropertySegement(Character.toString(this.ch));
                }
            }
            while (!this.isEOF()) {
                this.skipWhitespace();
                if (this.ch == '$') {
                    this.next();
                    continue;
                }
                if (this.ch == '.' || this.ch == '/') {
                    this.next();
                    if (this.ch == '*') {
                        if (!this.isEOF()) {
                            this.next();
                        }
                        return WildCardSegement.instance;
                    }
                    if (JSONPathParser.isDigitFirst(this.ch)) {
                        return this.parseArrayAccess(false);
                    }
                    String string2 = this.readName();
                    if (this.ch == '(') {
                        this.next();
                        if (this.ch == ')') {
                            if (!this.isEOF()) {
                                this.next();
                            }
                            if ("size".equals(string2)) {
                                return SizeSegement.instance;
                            }
                            throw new UnsupportedOperationException();
                        }
                        throw new UnsupportedOperationException();
                    }
                    return new PropertySegement(string2);
                }
                if (this.ch == '[') {
                    return this.parseArrayAccess(true);
                }
                if (this.level == 0) {
                    return new PropertySegement(this.readName());
                }
                throw new UnsupportedOperationException();
            }
            return null;
        }

        /*
         * Enabled aggressive block sorting
         */
        String readString() {
            char c2 = this.ch;
            this.next();
            int n2 = this.pos;
            while (this.ch != c2 && !this.isEOF()) {
                this.next();
            }
            String string2 = this.path;
            int n3 = this.isEOF() ? this.pos : this.pos - 1;
            string2 = string2.substring(n2 - 1, n3);
            this.accept(c2);
            return string2;
        }

        protected Object readValue() {
            this.skipWhitespace();
            if (JSONPathParser.isDigitFirst(this.ch)) {
                return this.readLongValue();
            }
            if (this.ch == '\"' || this.ch == '\'') {
                return this.readString();
            }
            if (this.ch == 'n') {
                if ("null".equals(this.readName())) {
                    return null;
                }
                throw new JSONPathException(this.path);
            }
            throw new UnsupportedOperationException();
        }

        public final void skipWhitespace() {
            while (this.ch <= ' ' && (this.ch == ' ' || this.ch == '\r' || this.ch == '\n' || this.ch == '\t' || this.ch == '\f' || this.ch == '\b')) {
                this.next();
            }
        }
    }

    static class MatchSegement
    implements Filter {
        private final String[] containsValues;
        private final String endsWithValue;
        private final int minLength;
        private final boolean not;
        private final String propertyName;
        private final String startsWithValue;

        public MatchSegement(String string2, String string3, String string4, String[] stringArray, boolean bl2) {
            this.propertyName = string2;
            this.startsWithValue = string3;
            this.endsWithValue = string4;
            this.containsValues = stringArray;
            this.not = bl2;
            int n2 = 0;
            if (string3 != null) {
                n2 = 0 + string3.length();
            }
            int n3 = n2;
            if (string4 != null) {
                n3 = n2 + string4.length();
            }
            int n4 = n3;
            if (stringArray != null) {
                int n5 = stringArray.length;
                n2 = 0;
                while (true) {
                    n4 = n3;
                    if (n2 >= n5) break;
                    n3 += stringArray[n2].length();
                    ++n2;
                }
            }
            this.minLength = n4;
        }

        /*
         * WARNING - void declaration
         * Enabled aggressive block sorting
         */
        @Override
        public boolean apply(JSONPath object, Object stringArray, Object object2, Object object3) {
            block10: {
                block9: {
                    void var4_6;
                    if ((object = ((JSONPath)object).getPropertyValue(var4_6, this.propertyName, false)) == null) break block9;
                    if (((String)(object = object.toString())).length() < this.minLength) {
                        return this.not;
                    }
                    int n2 = 0;
                    if (this.startsWithValue != null) {
                        if (!((String)object).startsWith(this.startsWithValue)) {
                            return this.not;
                        }
                        n2 = 0 + this.startsWithValue.length();
                    }
                    if (this.containsValues != null) {
                        String string2;
                        stringArray = this.containsValues;
                        int n3 = stringArray.length;
                        for (int i2 = 0; i2 < n3; n2 += string2.length(), ++i2) {
                            string2 = stringArray[i2];
                            if ((n2 = ((String)object).indexOf(string2, n2)) != -1) continue;
                            return this.not;
                        }
                    }
                    if (this.endsWithValue != null && !((String)object).endsWith(this.endsWithValue)) {
                        return this.not;
                    }
                    if (!this.not) break block10;
                }
                return false;
            }
            return true;
        }
    }

    static class MultiIndexSegement
    implements Segement {
        private final int[] indexes;

        public MultiIndexSegement(int[] nArray) {
            this.indexes = nArray;
        }

        @Override
        public Object eval(JSONPath jSONPath, Object arrayList, Object object) {
            arrayList = new ArrayList<Object>(this.indexes.length);
            for (int i2 = 0; i2 < this.indexes.length; ++i2) {
                arrayList.add(jSONPath.getArrayItem(object, this.indexes[i2]));
            }
            return arrayList;
        }
    }

    static class MultiPropertySegement
    implements Segement {
        private final String[] propertyNames;

        public MultiPropertySegement(String[] stringArray) {
            this.propertyNames = stringArray;
        }

        @Override
        public Object eval(JSONPath jSONPath, Object arrayList, Object object) {
            arrayList = new ArrayList<Object>(this.propertyNames.length);
            String[] stringArray = this.propertyNames;
            int n2 = stringArray.length;
            for (int i2 = 0; i2 < n2; ++i2) {
                arrayList.add(jSONPath.getPropertyValue(object, stringArray[i2], true));
            }
            return arrayList;
        }
    }

    static class NotNullSegement
    implements Filter {
        private final String propertyName;

        public NotNullSegement(String string2) {
            this.propertyName = string2;
        }

        @Override
        public boolean apply(JSONPath jSONPath, Object object, Object object2, Object object3) {
            boolean bl2 = false;
            if (jSONPath.getPropertyValue(object3, this.propertyName, false) != null) {
                bl2 = true;
            }
            return bl2;
        }
    }

    static class NullSegement
    implements Filter {
        private final String propertyName;

        public NullSegement(String string2) {
            this.propertyName = string2;
        }

        @Override
        public boolean apply(JSONPath jSONPath, Object object, Object object2, Object object3) {
            boolean bl2 = false;
            if (jSONPath.getPropertyValue(object3, this.propertyName, false) == null) {
                bl2 = true;
            }
            return bl2;
        }
    }

    static enum Operator {
        EQ,
        NE,
        GT,
        GE,
        LT,
        LE,
        LIKE,
        NOT_LIKE,
        RLIKE,
        NOT_RLIKE,
        IN,
        NOT_IN,
        BETWEEN,
        NOT_BETWEEN;

    }

    static class PropertySegement
    implements Segement {
        private final String propertyName;

        public PropertySegement(String string2) {
            this.propertyName = string2;
        }

        @Override
        public Object eval(JSONPath jSONPath, Object object, Object object2) {
            return jSONPath.getPropertyValue(object2, this.propertyName, true);
        }

        public void setValue(JSONPath jSONPath, Object object, Object object2) {
            jSONPath.setPropertyValue(object, this.propertyName, object2);
        }
    }

    static class RangeSegement
    implements Segement {
        private final int end;
        private final int start;
        private final int step;

        public RangeSegement(int n2, int n3, int n4) {
            this.start = n2;
            this.end = n3;
            this.step = n4;
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public Object eval(JSONPath jSONPath, Object arrayList, Object object) {
            int n2;
            int n3 = SizeSegement.instance.eval(jSONPath, arrayList, object);
            int n4 = this.end >= 0 ? this.end : this.end + n3;
            arrayList = new ArrayList((n4 - n2) / this.step + 1);
            for (n2 = this.start >= 0 ? this.start : this.start + n3; n2 <= n4 && n2 < n3; n2 += this.step) {
                arrayList.add(jSONPath.getArrayItem(object, n2));
            }
            return arrayList;
        }
    }

    static class RlikeSegement
    implements Filter {
        private final boolean not;
        private final Pattern pattern;
        private final String propertyName;

        public RlikeSegement(String string2, String string3, boolean bl2) {
            this.propertyName = string2;
            this.pattern = Pattern.compile(string3);
            this.not = bl2;
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        public boolean apply(JSONPath object, Object object2, Object object3, Object object4) {
            boolean bl2;
            if ((object = ((JSONPath)object).getPropertyValue(object4, this.propertyName, false)) == null) {
                return false;
            }
            object = object.toString();
            boolean bl3 = bl2 = this.pattern.matcher((CharSequence)object).matches();
            if (!this.not) return bl3;
            if (bl2) return false;
            return true;
        }
    }

    static interface Segement {
        public Object eval(JSONPath var1, Object var2, Object var3);
    }

    static class SizeSegement
    implements Segement {
        public static final SizeSegement instance = new SizeSegement();

        SizeSegement() {
        }

        @Override
        public Integer eval(JSONPath jSONPath, Object object, Object object2) {
            return jSONPath.evalSize(object2);
        }
    }

    static class StringInSegement
    implements Filter {
        private final boolean not;
        private final String propertyName;
        private final String[] values;

        public StringInSegement(String string2, String[] stringArray, boolean bl2) {
            this.propertyName = string2;
            this.values = stringArray;
            this.not = bl2;
        }

        /*
         * WARNING - void declaration
         * Enabled aggressive block sorting
         */
        @Override
        public boolean apply(JSONPath object, Object stringArray, Object object2, Object object3) {
            void var4_6;
            object = ((JSONPath)object).getPropertyValue(var4_6, this.propertyName, false);
            stringArray = this.values;
            int n2 = stringArray.length;
            int n3 = 0;
            while (n3 < n2) {
                String string2 = stringArray[n3];
                if (string2 == object) {
                    if (!this.not) return true;
                    return false;
                }
                if (string2 != null && string2.equals(object)) {
                    if (this.not) return false;
                    return true;
                }
                ++n3;
            }
            return this.not;
        }
    }

    static class StringOpSegement
    implements Filter {
        private final Operator op;
        private final String propertyName;
        private final String value;

        public StringOpSegement(String string2, String string3, Operator operator) {
            this.propertyName = string2;
            this.value = string3;
            this.op = operator;
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        public boolean apply(JSONPath object, Object object2, Object object3, Object object4) {
            boolean bl2 = true;
            object = ((JSONPath)object).getPropertyValue(object4, this.propertyName, false);
            if (this.op == Operator.EQ) {
                return this.value.equals(object);
            }
            if (this.op == Operator.NE) {
                if (!this.value.equals(object)) return bl2;
                return false;
            }
            if (object == null) {
                return false;
            }
            int n2 = this.value.compareTo(object.toString());
            if (this.op == Operator.GE) {
                if (n2 <= 0) return bl2;
                return false;
            }
            if (this.op == Operator.GT) {
                if (n2 < 0) return bl2;
                return false;
            }
            if (this.op == Operator.LE) {
                if (n2 >= 0) return bl2;
                return false;
            }
            if (this.op != Operator.LT) return false;
            if (n2 > 0) return bl2;
            return false;
        }
    }

    static class WildCardSegement
    implements Segement {
        public static WildCardSegement instance = new WildCardSegement();

        WildCardSegement() {
        }

        @Override
        public Object eval(JSONPath jSONPath, Object object, Object object2) {
            return jSONPath.getPropertyValues(object2);
        }
    }
}

