/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.XmlResourceParser
 *  android.util.AttributeSet
 *  android.util.Xml
 *  android.view.InflateException
 *  org.xmlpull.v1.XmlPullParser
 *  org.xmlpull.v1.XmlPullParserException
 */
package ticwear.design.preference;

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.util.AttributeSet;
import android.util.Xml;
import android.view.InflateException;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

abstract class GenericInflater<T, P extends Parent> {
    private static final Class[] mConstructorSignature = new Class[]{Context.class, AttributeSet.class};
    private static final HashMap sConstructorMap = new HashMap();
    private final boolean DEBUG;
    private final Object[] mConstructorArgs = new Object[2];
    protected final Context mContext;
    private String mDefaultPackage;
    private Factory<T> mFactory;
    private boolean mFactorySet;

    protected GenericInflater(Context context) {
        this.DEBUG = false;
        this.mContext = context;
    }

    protected GenericInflater(GenericInflater<T, P> genericInflater, Context context) {
        this.DEBUG = false;
        this.mContext = context;
        this.mFactory = genericInflater.mFactory;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private final T createItemFromTag(XmlPullParser object, String object2, AttributeSet attributeSet) {
        block6: {
            object = null;
            try {
                if (this.mFactory != null) {
                    object = this.mFactory.onCreateItem((String)object2, this.mContext, attributeSet);
                }
                if (object != null) break block6;
                if (-1 == object2.indexOf(46)) {
                    return this.onCreateItem((String)object2, attributeSet);
                }
                object = this.createItem((String)object2, null, attributeSet);
            }
            catch (InflateException inflateException) {
                throw inflateException;
            }
            catch (ClassNotFoundException classNotFoundException) {
                object2 = new InflateException(attributeSet.getPositionDescription() + ": Error inflating class " + object2);
                object2.initCause((Throwable)classNotFoundException);
                throw object2;
            }
            catch (Exception exception) {
                object2 = new InflateException(attributeSet.getPositionDescription() + ": Error inflating class " + object2);
                object2.initCause((Throwable)exception);
                throw object2;
            }
            return (T)object;
        }
        return (T)object;
    }

    private void rInflate(XmlPullParser xmlPullParser, T t2, AttributeSet attributeSet) throws XmlPullParserException, IOException {
        int n2;
        int n3 = xmlPullParser.getDepth();
        while (((n2 = xmlPullParser.next()) != 3 || xmlPullParser.getDepth() > n3) && n2 != 1) {
            if (n2 != 2 || this.onCreateCustomFromTag(xmlPullParser, t2, attributeSet)) continue;
            T t3 = this.createItemFromTag(xmlPullParser, xmlPullParser.getName(), attributeSet);
            ((Parent)t2).addItemFromInflater(t3);
            this.rInflate(xmlPullParser, t3, attributeSet);
        }
    }

    public abstract GenericInflater cloneInContext(Context var1);

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    public final T createItem(String var1_1, String var2_4, AttributeSet var3_5) throws ClassNotFoundException, InflateException {
        block11: {
            var6_6 = (Object[])GenericInflater.sConstructorMap.get(var1_1 /* !! */ );
            var5_7 = var6_6;
            if (var6_6 != null) ** GOTO lbl19
            var4_8 = var6_6;
            var7_10 = this.mContext.getClassLoader();
            if (var2_4 /* !! */  == null) break block11;
            var4_8 = var6_6;
            try {
                var5_7 = var2_4 /* !! */  + var1_1 /* !! */ ;
            }
            catch (NoSuchMethodException var4_9) {
                var5_7 = new StringBuilder().append(var3_5 /* !! */ .getPositionDescription()).append(": Error inflating class ");
                var3_5 /* !! */  = var1_1 /* !! */ ;
                if (var2_4 /* !! */  != null) {
                    var3_5 /* !! */  = var2_4 /* !! */  + var1_1 /* !! */ ;
                }
                var1_1 /* !! */  = new InflateException(var5_7.append((String)var3_5 /* !! */ ).toString());
                var1_1 /* !! */ .initCause((Throwable)var4_9);
                throw var1_1 /* !! */ ;
            }
            catch (ClassNotFoundException var1_2) {
                throw var1_2;
            }
            catch (Exception var1_3) {
                var2_4 /* !! */  = new InflateException(var3_5 /* !! */ .getPositionDescription() + ": Error inflating class " + var4_8.getClass().getName());
                var2_4 /* !! */ .initCause((Throwable)var1_3);
                throw var2_4 /* !! */ ;
            }
lbl11:
            // 2 sources

            while (true) {
                var4_8 = var6_6;
                var5_7 = var7_10.loadClass((String)var5_7).getConstructor(GenericInflater.mConstructorSignature);
                var4_8 = var5_7;
                GenericInflater.sConstructorMap.put(var1_1 /* !! */ , var5_7);
lbl19:
                // 2 sources

                var4_8 = var5_7;
                var6_6 = this.mConstructorArgs;
                var6_6[1] = var3_5 /* !! */ ;
                var4_8 = var5_7;
                var5_7 = var5_7.newInstance(var6_6);
                return (T)var5_7;
            }
        }
        var5_7 = var1_1 /* !! */ ;
        ** while (true)
    }

    public Context getContext() {
        return this.mContext;
    }

    public String getDefaultPackage() {
        return this.mDefaultPackage;
    }

    public final Factory<T> getFactory() {
        return this.mFactory;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public T inflate(int n2, P p2) {
        boolean bl2;
        if (p2 != null) {
            bl2 = true;
            return this.inflate(n2, p2, bl2);
        }
        bl2 = false;
        return this.inflate(n2, p2, bl2);
    }

    public T inflate(int n2, P object, boolean bl2) {
        XmlResourceParser xmlResourceParser = this.getContext().getResources().getXml(n2);
        try {
            object = this.inflate((XmlPullParser)xmlResourceParser, object, bl2);
            return (T)object;
        }
        finally {
            xmlResourceParser.close();
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public T inflate(XmlPullParser xmlPullParser, P p2) {
        boolean bl2;
        if (p2 != null) {
            bl2 = true;
            return this.inflate(xmlPullParser, p2, bl2);
        }
        bl2 = false;
        return this.inflate(xmlPullParser, p2, bl2);
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public T inflate(XmlPullParser object, P object2, boolean bl2) {
        Object[] objectArray = this.mConstructorArgs;
        synchronized (objectArray) {
            AttributeSet attributeSet = Xml.asAttributeSet((XmlPullParser)object);
            this.mConstructorArgs[0] = this.mContext;
            try {
                int n2;
                while ((n2 = object.next()) != 2 && n2 != 1) {
                }
                if (n2 != 2) {
                    throw new InflateException(object.getPositionDescription() + ": No start tag found!");
                }
            }
            catch (InflateException inflateException) {
                throw inflateException;
            }
            catch (XmlPullParserException xmlPullParserException) {
                object2 = new InflateException(xmlPullParserException.getMessage());
                object2.initCause((Throwable)xmlPullParserException);
                throw object2;
            }
            catch (IOException iOException) {
                object = new InflateException(object.getPositionDescription() + ": " + iOException.getMessage());
                object.initCause((Throwable)iOException);
                throw object;
            }
            {
                object2 = this.onMergeRoots(object2, bl2, (Parent)this.createItemFromTag((XmlPullParser)object, object.getName(), attributeSet));
                this.rInflate((XmlPullParser)object, (T)object2, attributeSet);
            }
            return (T)object2;
        }
    }

    protected boolean onCreateCustomFromTag(XmlPullParser xmlPullParser, T t2, AttributeSet attributeSet) throws XmlPullParserException {
        return false;
    }

    protected T onCreateItem(String string2, AttributeSet attributeSet) throws ClassNotFoundException {
        return this.createItem(string2, this.mDefaultPackage, attributeSet);
    }

    protected P onMergeRoots(P p2, boolean bl2, P p3) {
        return p3;
    }

    public void setDefaultPackage(String string2) {
        this.mDefaultPackage = string2;
    }

    public void setFactory(Factory<T> factory) {
        if (this.mFactorySet) {
            throw new IllegalStateException("A factory has already been set on this inflater");
        }
        if (factory == null) {
            throw new NullPointerException("Given factory can not be null");
        }
        this.mFactorySet = true;
        if (this.mFactory == null) {
            this.mFactory = factory;
            return;
        }
        this.mFactory = new FactoryMerger<T>(factory, this.mFactory);
    }

    public static interface Factory<T> {
        public T onCreateItem(String var1, Context var2, AttributeSet var3);
    }

    private static class FactoryMerger<T>
    implements Factory<T> {
        private final Factory<T> mF1;
        private final Factory<T> mF2;

        FactoryMerger(Factory<T> factory, Factory<T> factory2) {
            this.mF1 = factory;
            this.mF2 = factory2;
        }

        @Override
        public T onCreateItem(String string2, Context context, AttributeSet attributeSet) {
            T t2 = this.mF1.onCreateItem(string2, context, attributeSet);
            if (t2 != null) {
                return t2;
            }
            return this.mF2.onCreateItem(string2, context, attributeSet);
        }
    }

    public static interface Parent<T> {
        public void addItemFromInflater(T var1);
    }
}

