/*
 * Decompiled with CFR 0.151.
 */
package com.alibaba.fastjson.parser;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.parser.JSONLexerBase;
import com.alibaba.fastjson.parser.JSONScanner;
import com.alibaba.fastjson.parser.JSONToken;
import com.alibaba.fastjson.parser.ParseContext;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.parser.SymbolTable;
import com.alibaba.fastjson.parser.deserializer.ExtraProcessable;
import com.alibaba.fastjson.parser.deserializer.ExtraProcessor;
import com.alibaba.fastjson.parser.deserializer.ExtraTypeProvider;
import com.alibaba.fastjson.parser.deserializer.FieldDeserializer;
import com.alibaba.fastjson.parser.deserializer.FieldTypeResolver;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.parser.deserializer.ResolveFieldDeserializer;
import com.alibaba.fastjson.serializer.IntegerCodec;
import com.alibaba.fastjson.serializer.StringCodec;
import com.alibaba.fastjson.util.TypeUtils;
import java.io.Closeable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class DefaultJSONParser
implements Closeable {
    public static final int NONE = 0;
    public static final int NeedToResolve = 1;
    public static final int TypeNameRedirect = 2;
    private static final Set<Class<?>> primitiveClasses = new HashSet();
    protected ParserConfig config;
    protected ParseContext context;
    private ParseContext[] contextArray;
    private int contextArrayIndex = 0;
    private DateFormat dateFormat;
    private String dateFormatPattern = JSON.DEFFAULT_DATE_FORMAT;
    private List<ExtraProcessor> extraProcessors = null;
    private List<ExtraTypeProvider> extraTypeProviders = null;
    protected FieldTypeResolver fieldTypeResolver = null;
    public final Object input;
    public final JSONLexer lexer;
    public int resolveStatus = 0;
    private List<ResolveTask> resolveTaskList;
    public final SymbolTable symbolTable;

    static {
        primitiveClasses.add(Boolean.TYPE);
        primitiveClasses.add(Byte.TYPE);
        primitiveClasses.add(Short.TYPE);
        primitiveClasses.add(Integer.TYPE);
        primitiveClasses.add(Long.TYPE);
        primitiveClasses.add(Float.TYPE);
        primitiveClasses.add(Double.TYPE);
        primitiveClasses.add(Boolean.class);
        primitiveClasses.add(Byte.class);
        primitiveClasses.add(Short.class);
        primitiveClasses.add(Integer.class);
        primitiveClasses.add(Long.class);
        primitiveClasses.add(Float.class);
        primitiveClasses.add(Double.class);
        primitiveClasses.add(BigInteger.class);
        primitiveClasses.add(BigDecimal.class);
        primitiveClasses.add(String.class);
    }

    public DefaultJSONParser(JSONLexer jSONLexer) {
        this(jSONLexer, ParserConfig.getGlobalInstance());
    }

    public DefaultJSONParser(JSONLexer jSONLexer, ParserConfig parserConfig) {
        this(null, jSONLexer, parserConfig);
    }

    public DefaultJSONParser(Object object, JSONLexer jSONLexer, ParserConfig parserConfig) {
        this.lexer = jSONLexer;
        this.input = object;
        this.config = parserConfig;
        this.symbolTable = parserConfig.symbolTable;
        char c2 = jSONLexer.getCurrent();
        if (c2 == '{') {
            jSONLexer.next();
            ((JSONLexerBase)jSONLexer).token = 12;
            return;
        }
        if (c2 == '[') {
            jSONLexer.next();
            ((JSONLexerBase)jSONLexer).token = 14;
            return;
        }
        jSONLexer.nextToken();
    }

    public DefaultJSONParser(String string2) {
        this(string2, ParserConfig.getGlobalInstance(), JSON.DEFAULT_PARSER_FEATURE);
    }

    public DefaultJSONParser(String string2, ParserConfig parserConfig) {
        this((Object)string2, new JSONScanner(string2, JSON.DEFAULT_PARSER_FEATURE), parserConfig);
    }

    public DefaultJSONParser(String string2, ParserConfig parserConfig, int n2) {
        this((Object)string2, new JSONScanner(string2, n2), parserConfig);
    }

    public DefaultJSONParser(char[] cArray, int n2, ParserConfig parserConfig, int n3) {
        this(cArray, new JSONScanner(cArray, n2, n3), parserConfig);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void addContext(ParseContext parseContext) {
        int n2 = this.contextArrayIndex;
        this.contextArrayIndex = n2 + 1;
        if (this.contextArray == null) {
            this.contextArray = new ParseContext[8];
        } else if (n2 >= this.contextArray.length) {
            ParseContext[] parseContextArray = new ParseContext[this.contextArray.length * 3 / 2];
            System.arraycopy(this.contextArray, 0, parseContextArray, 0, this.contextArray.length);
            this.contextArray = parseContextArray;
        }
        this.contextArray[n2] = parseContext;
    }

    public final void accept(int n2) {
        JSONLexer jSONLexer = this.lexer;
        if (jSONLexer.token() == n2) {
            jSONLexer.nextToken();
            return;
        }
        throw new JSONException("syntax error, expect " + JSONToken.name(n2) + ", actual " + JSONToken.name(jSONLexer.token()));
    }

    public final void accept(int n2, int n3) {
        JSONLexer jSONLexer = this.lexer;
        if (jSONLexer.token() == n2) {
            jSONLexer.nextToken(n3);
            return;
        }
        this.throwException(n2);
    }

    public void acceptType(String string2) {
        JSONLexer jSONLexer = this.lexer;
        jSONLexer.nextTokenWithColon();
        if (jSONLexer.token() != 4) {
            throw new JSONException("type not match error");
        }
        if (string2.equals(jSONLexer.stringVal())) {
            jSONLexer.nextToken();
            if (jSONLexer.token() == 16) {
                jSONLexer.nextToken();
            }
            return;
        }
        throw new JSONException("type not match error");
    }

    public void addResolveTask(ResolveTask resolveTask) {
        if (this.resolveTaskList == null) {
            this.resolveTaskList = new ArrayList<ResolveTask>(2);
        }
        this.resolveTaskList.add(resolveTask);
    }

    public void checkListResolve(Collection collection) {
        block3: {
            block2: {
                if (this.resolveStatus != 1) break block2;
                if (!(collection instanceof List)) break block3;
                int n2 = collection.size();
                collection = (List)collection;
                ResolveTask resolveTask = this.getLastResolveTask();
                resolveTask.fieldDeserializer = new ResolveFieldDeserializer(this, (List)collection, n2 - 1);
                resolveTask.ownerContext = this.context;
                this.setResolveStatus(0);
            }
            return;
        }
        ResolveTask resolveTask = this.getLastResolveTask();
        resolveTask.fieldDeserializer = new ResolveFieldDeserializer(collection);
        resolveTask.ownerContext = this.context;
        this.setResolveStatus(0);
    }

    public void checkMapResolve(Map object, Object object2) {
        if (this.resolveStatus == 1) {
            object = new ResolveFieldDeserializer((Map)object, object2);
            object2 = this.getLastResolveTask();
            ((ResolveTask)object2).fieldDeserializer = object;
            ((ResolveTask)object2).ownerContext = this.context;
            this.setResolveStatus(0);
        }
    }

    @Override
    public void close() {
        JSONLexer jSONLexer = this.lexer;
        try {
            if (jSONLexer.isEnabled(Feature.AutoCloseSource) && jSONLexer.token() != 20) {
                throw new JSONException("not close json text, token : " + JSONToken.name(jSONLexer.token()));
            }
        }
        finally {
            jSONLexer.close();
        }
    }

    public void config(Feature feature, boolean bl2) {
        this.lexer.config(feature, bl2);
    }

    public ParserConfig getConfig() {
        return this.config;
    }

    public ParseContext getContext() {
        return this.context;
    }

    public String getDateFomartPattern() {
        return this.dateFormatPattern;
    }

    public DateFormat getDateFormat() {
        if (this.dateFormat == null) {
            this.dateFormat = new SimpleDateFormat(this.dateFormatPattern, this.lexer.getLocale());
            this.dateFormat.setTimeZone(this.lexer.getTimeZone());
        }
        return this.dateFormat;
    }

    public List<ExtraProcessor> getExtraProcessors() {
        if (this.extraProcessors == null) {
            this.extraProcessors = new ArrayList<ExtraProcessor>(2);
        }
        return this.extraProcessors;
    }

    public List<ExtraTypeProvider> getExtraTypeProviders() {
        if (this.extraTypeProviders == null) {
            this.extraTypeProviders = new ArrayList<ExtraTypeProvider>(2);
        }
        return this.extraTypeProviders;
    }

    public FieldTypeResolver getFieldTypeResolver() {
        return this.fieldTypeResolver;
    }

    public String getInput() {
        if (this.input instanceof char[]) {
            return new String((char[])this.input);
        }
        return this.input.toString();
    }

    public ResolveTask getLastResolveTask() {
        return this.resolveTaskList.get(this.resolveTaskList.size() - 1);
    }

    public JSONLexer getLexer() {
        return this.lexer;
    }

    public Object getObject(String string2) {
        for (int i2 = 0; i2 < this.contextArrayIndex; ++i2) {
            if (!string2.equals(this.contextArray[i2].toString())) continue;
            return this.contextArray[i2].object;
        }
        return null;
    }

    public int getResolveStatus() {
        return this.resolveStatus;
    }

    public List<ResolveTask> getResolveTaskList() {
        if (this.resolveTaskList == null) {
            this.resolveTaskList = new ArrayList<ResolveTask>(2);
        }
        return this.resolveTaskList;
    }

    public SymbolTable getSymbolTable() {
        return this.symbolTable;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void handleResovleTask(Object object) {
        if (this.resolveTaskList != null) {
            int n2 = this.resolveTaskList.size();
            for (int i2 = 0; i2 < n2; ++i2) {
                Object object2 = this.resolveTaskList.get(i2);
                Object object3 = ((ResolveTask)object2).referenceValue;
                object = null;
                if (((ResolveTask)object2).ownerContext != null) {
                    object = object2.ownerContext.object;
                }
                object3 = ((String)object3).startsWith("$") ? this.getObject((String)object3) : object2.context.object;
                object2 = ((ResolveTask)object2).fieldDeserializer;
                if (object2 == null) continue;
                ((FieldDeserializer)object2).setValue(object, object3);
            }
        }
    }

    public boolean isEnabled(Feature feature) {
        return this.lexer.isEnabled(feature);
    }

    public Object parse() {
        return this.parse(null);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public Object parse(Object object) {
        Object var4_2 = null;
        JSONLexer jSONLexer = this.lexer;
        switch (jSONLexer.token()) {
            default: {
                throw new JSONException("syntax error, " + jSONLexer.info());
            }
            case 21: {
                jSONLexer.nextToken();
                HashSet hashSet = new HashSet();
                this.parseArray(hashSet, object);
                return hashSet;
            }
            case 22: {
                jSONLexer.nextToken();
                TreeSet treeSet = new TreeSet();
                this.parseArray(treeSet, object);
                return treeSet;
            }
            case 14: {
                JSONArray jSONArray = new JSONArray();
                this.parseArray(jSONArray, object);
                if (!jSONLexer.isEnabled(Feature.UseObjectArray)) return jSONArray;
                return jSONArray.toArray();
            }
            case 12: {
                return this.parseObject(new JSONObject(jSONLexer.isEnabled(Feature.OrderedField)), object);
            }
            case 2: {
                object = jSONLexer.integerValue();
                jSONLexer.nextToken();
                return object;
            }
            case 3: {
                object = jSONLexer.decimalValue(jSONLexer.isEnabled(Feature.UseBigDecimal));
                jSONLexer.nextToken();
                return object;
            }
            case 4: {
                String string2 = jSONLexer.stringVal();
                jSONLexer.nextToken(16);
                if (!jSONLexer.isEnabled(Feature.AllowISO8601DateFormat)) return string2;
                object = new JSONScanner(string2);
                try {
                    if (!((JSONScanner)object).scanISO8601DateIfMatch()) return string2;
                    Date date = ((JSONLexerBase)object).getCalendar().getTime();
                    return date;
                }
                finally {
                    ((JSONLexerBase)object).close();
                }
            }
            case 8: {
                jSONLexer.nextToken();
                return null;
            }
            case 23: {
                jSONLexer.nextToken();
                return null;
            }
            case 6: {
                jSONLexer.nextToken();
                return Boolean.TRUE;
            }
            case 7: {
                jSONLexer.nextToken();
                return Boolean.FALSE;
            }
            case 9: {
                jSONLexer.nextToken(18);
                if (jSONLexer.token() != 18) {
                    throw new JSONException("syntax error");
                }
                jSONLexer.nextToken(10);
                this.accept(10);
                long l2 = jSONLexer.integerValue().longValue();
                this.accept(2);
                this.accept(11);
                return new Date(l2);
            }
            case 20: 
        }
        object = var4_2;
        if (jSONLexer.isBlankInput()) return object;
        throw new JSONException("unterminated json string, " + jSONLexer.info());
    }

    public <T> List<T> parseArray(Class<T> clazz) {
        ArrayList arrayList = new ArrayList();
        this.parseArray(clazz, arrayList);
        return arrayList;
    }

    public void parseArray(Class<?> clazz, Collection collection) {
        this.parseArray((Type)clazz, collection);
    }

    public void parseArray(Type type, Collection collection) {
        this.parseArray(type, collection, null);
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void parseArray(Type var1_1, Collection var2_3, Object var3_4) {
        block16: {
            block18: {
                block17: {
                    if (this.lexer.token() == 21 || this.lexer.token() == 22) {
                        this.lexer.nextToken();
                    }
                    if (this.lexer.token() != 14) {
                        throw new JSONException("exepct '[', but " + JSONToken.name(this.lexer.token()) + ", " + this.lexer.info());
                    }
                    if (Integer.TYPE != var1_1) break block17;
                    var6_5 = IntegerCodec.instance;
                    this.lexer.nextToken(2);
lbl8:
                    // 3 sources

                    while (true) {
                        var7_6 = this.context;
                        this.setContext(var2_3, var3_4);
                        var4_7 = 0;
lbl13:
                        // 2 sources

                        while (true) {
                            try {
                                if (this.lexer.isEnabled(Feature.AllowArbitraryCommas)) {
                                    while (this.lexer.token() == 16) {
                                        this.lexer.nextToken();
                                    }
                                }
                                ** GOTO lbl-1000
                            }
                            catch (Throwable var1_2) {
                                this.setContext(var7_6);
                                throw var1_2;
                            }
                            break;
                        }
                        break;
                    }
                }
                if (String.class != var1_1) break block18;
                var6_5 = StringCodec.instance;
                this.lexer.nextToken(4);
                ** GOTO lbl8
            }
            var6_5 = this.config.getDeserializer(var1_1);
            this.lexer.nextToken(var6_5.getFastMatchToken());
            ** while (true)
lbl-1000:
            // 1 sources

            {
                var5_8 = this.lexer.token();
                if (var5_8 != 15) ** GOTO lbl-1000
                this.setContext(var7_6);
                this.lexer.nextToken(16);
                return;
            }
lbl-1000:
            // 1 sources

            {
                if (Integer.TYPE == var1_1) {
                    var2_3.add(IntegerCodec.instance.deserialze(this, null, null));
lbl40:
                    // 3 sources

                    while (this.lexer.token() == 16) {
                        this.lexer.nextToken(var6_5.getFastMatchToken());
                        break block16;
                    }
                    break block16;
                }
                if (String.class != var1_1) ** GOTO lbl60
            }
            {
                block20: {
                    block19: {
                        if (this.lexer.token() != 4) break block19;
                        var3_4 = this.lexer.stringVal();
                        this.lexer.nextToken(16);
lbl48:
                        // 3 sources

                        while (true) {
                            var2_3.add(var3_4);
                            ** GOTO lbl40
                            break;
                        }
                    }
                    var3_4 = this.parse();
                    if (var3_4 != null) break block20;
                    var3_4 = null;
                    ** GOTO lbl48
                }
                var3_4 = var3_4.toString();
                ** continue;
lbl60:
                // 1 sources

                if (this.lexer.token() == 8) {
                    this.lexer.nextToken();
                    var3_4 = null;
                } else {
                    var3_4 = var6_5.deserialze(this, var1_1, var4_7);
                }
                var2_3.add(var3_4);
                this.checkListResolve(var2_3);
                ** GOTO lbl40
            }
        }
        ++var4_7;
        ** while (true)
    }

    public final void parseArray(Collection collection) {
        this.parseArray(collection, null);
    }

    /*
     * Exception decompiling
     */
    public final void parseArray(Collection var1_1, Object var2_3) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Back jump on a try block [egrp 1[TRYBLOCK] [2 : 169->553)] java.lang.Throwable
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op02WithProcessedDataAndRefs.insertExceptionBlocks(Op02WithProcessedDataAndRefs.java:2289)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:414)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1042)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:929)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:73)
         *     at org.benf.cfr.reader.Main.main(Main.java:49)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    /*
     * Enabled aggressive block sorting
     */
    public Object[] parseArray(Type[] typeArray) {
        if (this.lexer.token() == 8) {
            this.lexer.nextToken(16);
            return null;
        }
        if (this.lexer.token() != 14) {
            throw new JSONException("syntax error : " + this.lexer.tokenName());
        }
        Object[] objectArray = new Object[typeArray.length];
        if (typeArray.length == 0) {
            this.lexer.nextToken(15);
            if (this.lexer.token() != 15) {
                throw new JSONException("syntax error");
            }
            this.lexer.nextToken(16);
            return new Object[0];
        }
        this.lexer.nextToken(2);
        for (int i2 = 0; i2 < typeArray.length; ++i2) {
            Object object;
            if (this.lexer.token() == 8) {
                object = null;
                this.lexer.nextToken(16);
            } else {
                Type type = typeArray[i2];
                if (type == Integer.TYPE || type == Integer.class) {
                    if (this.lexer.token() == 2) {
                        object = this.lexer.intValue();
                        this.lexer.nextToken(16);
                    } else {
                        object = TypeUtils.cast(this.parse(), type, this.config);
                    }
                } else if (type == String.class) {
                    if (this.lexer.token() == 4) {
                        object = this.lexer.stringVal();
                        this.lexer.nextToken(16);
                    } else {
                        object = TypeUtils.cast(this.parse(), type, this.config);
                    }
                } else {
                    boolean bl2 = false;
                    ArrayList arrayList = null;
                    object = arrayList;
                    boolean bl3 = bl2;
                    if (i2 == typeArray.length - 1) {
                        object = arrayList;
                        bl3 = bl2;
                        if (type instanceof Class) {
                            object = (Class)type;
                            bl3 = ((Class)object).isArray();
                            object = ((Class)object).getComponentType();
                        }
                    }
                    if (bl3 && this.lexer.token() != 14) {
                        arrayList = new ArrayList();
                        object = this.config.getDeserializer((Type)object);
                        int n2 = object.getFastMatchToken();
                        if (this.lexer.token() != 15) {
                            while (true) {
                                arrayList.add(object.deserialze(this, type, null));
                                if (this.lexer.token() != 16) break;
                                this.lexer.nextToken(n2);
                            }
                            if (this.lexer.token() != 15) {
                                throw new JSONException("syntax error :" + JSONToken.name(this.lexer.token()));
                            }
                        }
                        object = TypeUtils.cast(arrayList, type, this.config);
                    } else {
                        object = this.config.getDeserializer(type).deserialze(this, type, null);
                    }
                }
            }
            objectArray[i2] = object;
            if (this.lexer.token() == 15) break;
            if (this.lexer.token() != 16) {
                throw new JSONException("syntax error :" + JSONToken.name(this.lexer.token()));
            }
            if (i2 == typeArray.length - 1) {
                this.lexer.nextToken(15);
                continue;
            }
            this.lexer.nextToken(2);
        }
        if (this.lexer.token() != 15) {
            throw new JSONException("syntax error");
        }
        this.lexer.nextToken(16);
        return objectArray;
    }

    public Object parseArrayWithType(Type arrayList) {
        if (this.lexer.token() == 8) {
            this.lexer.nextToken();
            return null;
        }
        Object object = ((ParameterizedType)((Object)arrayList)).getActualTypeArguments();
        if (((Type[])object).length != 1) {
            throw new JSONException("not support type " + arrayList);
        }
        if ((object = object[0]) instanceof Class) {
            arrayList = new ArrayList();
            this.parseArray((Class)object, (Collection)arrayList);
            return arrayList;
        }
        if (object instanceof WildcardType) {
            WildcardType wildcardType = (WildcardType)object;
            if (Object.class.equals(object = wildcardType.getUpperBounds()[0])) {
                if (wildcardType.getLowerBounds().length == 0) {
                    return this.parse();
                }
                throw new JSONException("not support type : " + arrayList);
            }
            arrayList = new ArrayList();
            this.parseArray((Class)object, arrayList);
            return arrayList;
        }
        if (object instanceof TypeVariable) {
            Type type = (TypeVariable)object;
            Type[] typeArray = type.getBounds();
            if (typeArray.length != 1) {
                throw new JSONException("not support : " + type);
            }
            type = typeArray[0];
            if (type instanceof Class) {
                arrayList = new ArrayList();
                this.parseArray((Class)type, arrayList);
                return arrayList;
            }
        }
        if (object instanceof ParameterizedType) {
            arrayList = (ParameterizedType)object;
            object = new ArrayList();
            this.parseArray((Type)((Object)arrayList), (Collection)object);
            return object;
        }
        throw new JSONException("TODO : " + arrayList);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void parseExtra(Object object, String string2) {
        this.lexer.nextTokenWithColon();
        Object object2 = null;
        Iterator<ExtraProcessor> iterator = null;
        if (this.extraTypeProviders != null) {
            Iterator<ExtraTypeProvider> iterator2 = this.extraTypeProviders.iterator();
            object2 = iterator;
            while (iterator2.hasNext()) {
                object2 = iterator2.next().getExtraType(object, string2);
            }
        }
        object2 = object2 == null ? this.parse() : this.parseObject((Type)object2);
        if (object instanceof ExtraProcessable) {
            ((ExtraProcessable)object).processExtra(string2, object2);
            return;
        } else {
            if (this.extraProcessors == null) return;
            iterator = this.extraProcessors.iterator();
            while (iterator.hasNext()) {
                iterator.next().processExtra(object, string2, object2);
            }
        }
    }

    public Object parseKey() {
        if (this.lexer.token() == 18) {
            String string2 = this.lexer.stringVal();
            this.lexer.nextToken(16);
            return string2;
        }
        return this.parse(null);
    }

    public JSONObject parseObject() {
        return (JSONObject)this.parseObject(new JSONObject(this.lexer.isEnabled(Feature.OrderedField)));
    }

    public <T> T parseObject(Class<T> clazz) {
        return this.parseObject(clazz, null);
    }

    public <T> T parseObject(Type type) {
        return this.parseObject(type, null);
    }

    public <T> T parseObject(Type object, Object object2) {
        int n2 = this.lexer.token();
        if (n2 == 8) {
            this.lexer.nextToken();
            return null;
        }
        if (n2 == 4) {
            if (object == byte[].class) {
                object = this.lexer.bytesValue();
                this.lexer.nextToken();
                return (T)object;
            }
            if (object == char[].class) {
                object = this.lexer.stringVal();
                this.lexer.nextToken();
                return (T)((String)object).toCharArray();
            }
        }
        ObjectDeserializer objectDeserializer = this.config.getDeserializer((Type)object);
        try {
            object = objectDeserializer.deserialze(this, (Type)object, object2);
        }
        catch (JSONException jSONException) {
            throw jSONException;
        }
        catch (Throwable throwable) {
            throw new JSONException(throwable.getMessage(), throwable);
        }
        return (T)object;
    }

    public Object parseObject(Map map) {
        return this.parseObject(map, null);
    }

    /*
     * Exception decompiling
     */
    public final Object parseObject(Map var1_1, Object var2_5) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Back jump on a try block [egrp 10[TRYBLOCK] [10 : 355->363)] java.lang.Throwable
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op02WithProcessedDataAndRefs.insertExceptionBlocks(Op02WithProcessedDataAndRefs.java:2289)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:414)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1042)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:929)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:73)
         *     at org.benf.cfr.reader.Main.main(Main.java:49)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    /*
     * Exception decompiling
     */
    public void parseObject(Object var1_1) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * java.lang.UnsupportedOperationException
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.LoopIdentifier.considerAsDoLoopStart(LoopIdentifier.java:383)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.LoopIdentifier.identifyLoops1(LoopIdentifier.java:65)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:676)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1042)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:929)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:73)
         *     at org.benf.cfr.reader.Main.main(Main.java:49)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    public void popContext() {
        if (this.lexer.isEnabled(Feature.DisableCircularReferenceDetect)) {
            return;
        }
        this.context = this.context.parent;
        this.contextArray[this.contextArrayIndex - 1] = null;
        --this.contextArrayIndex;
    }

    public void setConfig(ParserConfig parserConfig) {
        this.config = parserConfig;
    }

    public ParseContext setContext(ParseContext parseContext, Object object, Object object2) {
        if (this.lexer.isEnabled(Feature.DisableCircularReferenceDetect)) {
            return null;
        }
        this.context = new ParseContext(parseContext, object, object2);
        this.addContext(this.context);
        return this.context;
    }

    public ParseContext setContext(Object object, Object object2) {
        if (this.lexer.isEnabled(Feature.DisableCircularReferenceDetect)) {
            return null;
        }
        return this.setContext(this.context, object, object2);
    }

    public void setContext(ParseContext parseContext) {
        if (this.lexer.isEnabled(Feature.DisableCircularReferenceDetect)) {
            return;
        }
        this.context = parseContext;
    }

    public void setDateFomrat(DateFormat dateFormat) {
        this.dateFormat = dateFormat;
    }

    public void setDateFormat(String string2) {
        this.dateFormatPattern = string2;
        this.dateFormat = null;
    }

    public void setFieldTypeResolver(FieldTypeResolver fieldTypeResolver) {
        this.fieldTypeResolver = fieldTypeResolver;
    }

    public void setResolveStatus(int n2) {
        this.resolveStatus = n2;
    }

    public void throwException(int n2) {
        throw new JSONException("syntax error, expect " + JSONToken.name(n2) + ", actual " + JSONToken.name(this.lexer.token()));
    }

    public static class ResolveTask {
        public final ParseContext context;
        public FieldDeserializer fieldDeserializer;
        public ParseContext ownerContext;
        public final String referenceValue;

        public ResolveTask(ParseContext parseContext, String string2) {
            this.context = parseContext;
            this.referenceValue = string2;
        }
    }
}

