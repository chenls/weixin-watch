/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  javax.ws.rs.Consumes
 *  javax.ws.rs.Produces
 *  javax.ws.rs.WebApplicationException
 *  javax.ws.rs.core.Context
 *  javax.ws.rs.core.MediaType
 *  javax.ws.rs.core.MultivaluedMap
 *  javax.ws.rs.core.UriInfo
 *  javax.ws.rs.ext.MessageBodyReader
 *  javax.ws.rs.ext.MessageBodyWriter
 *  javax.ws.rs.ext.Provider
 */
package com.alibaba.fastjson.support.jaxrs;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.util.IOUtils;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

@Consumes(value={"*/*"})
@Produces(value={"*/*"})
@Provider
public class FastJsonProvider
implements MessageBodyReader<Object>,
MessageBodyWriter<Object> {
    @Deprecated
    protected Charset charset = IOUtils.UTF8;
    private Class<?>[] clazzes = null;
    @Deprecated
    protected String dateFormat;
    private FastJsonConfig fastJsonConfig;
    @Deprecated
    protected SerializerFeature[] features = new SerializerFeature[0];
    @Deprecated
    protected SerializeFilter[] filters = new SerializeFilter[0];
    @Context
    UriInfo uriInfo;

    public FastJsonProvider() {
        this.fastJsonConfig = new FastJsonConfig();
    }

    @Deprecated
    public FastJsonProvider(String string2) {
        this.fastJsonConfig = new FastJsonConfig();
        this.fastJsonConfig.setCharset(Charset.forName(string2));
    }

    public FastJsonProvider(Class<?>[] classArray) {
        this.fastJsonConfig = new FastJsonConfig();
        this.clazzes = classArray;
    }

    @Deprecated
    public Charset getCharset() {
        return this.fastJsonConfig.getCharset();
    }

    @Deprecated
    public String getDateFormat() {
        return this.fastJsonConfig.getDateFormat();
    }

    public FastJsonConfig getFastJsonConfig() {
        return this.fastJsonConfig;
    }

    @Deprecated
    public SerializerFeature[] getFeatures() {
        return this.fastJsonConfig.getSerializerFeatures();
    }

    @Deprecated
    public SerializeFilter[] getFilters() {
        return this.fastJsonConfig.getSerializeFilters();
    }

    public long getSize(Object object, Class<?> clazz, Type type, Annotation[] annotationArray, MediaType mediaType) {
        return -1L;
    }

    protected boolean hasMatchingMediaType(MediaType object) {
        return object == null || "json".equalsIgnoreCase((String)(object = object.getSubtype())) || ((String)object).endsWith("+json") || "javascript".equals(object) || "x-javascript".equals(object) || "x-json".equals(object) || "x-www-form-urlencoded".equalsIgnoreCase((String)object) || ((String)object).endsWith("x-www-form-urlencoded");
    }

    public boolean isReadable(Class<?> clazz, Type type, Annotation[] annotationArray, MediaType mediaType) {
        if (!this.hasMatchingMediaType(mediaType)) {
            return false;
        }
        return this.isValidType(clazz, annotationArray);
    }

    /*
     * Enabled aggressive block sorting
     */
    protected boolean isValidType(Class<?> clazz, Annotation[] objectArray) {
        if (clazz != null) {
            if (this.clazzes == null) {
                return true;
            }
            objectArray = this.clazzes;
            int n2 = objectArray.length;
            for (int i2 = 0; i2 < n2; ++i2) {
                if (objectArray[i2] != clazz) continue;
                return true;
            }
        }
        return false;
    }

    public boolean isWriteable(Class<?> clazz, Type type, Annotation[] annotationArray, MediaType mediaType) {
        if (!this.hasMatchingMediaType(mediaType)) {
            return false;
        }
        return this.isValidType(clazz, annotationArray);
    }

    public Object readFrom(Class<Object> clazz, Type type, Annotation[] annotationArray, MediaType mediaType, MultivaluedMap<String, String> multivaluedMap, InputStream inputStream) throws IOException, WebApplicationException {
        return JSON.parseObject(inputStream, this.fastJsonConfig.getCharset(), type, this.fastJsonConfig.getFeatures());
    }

    @Deprecated
    public void setCharset(Charset charset) {
        this.fastJsonConfig.setCharset(charset);
    }

    @Deprecated
    public void setDateFormat(String string2) {
        this.fastJsonConfig.setDateFormat(string2);
    }

    public void setFastJsonConfig(FastJsonConfig fastJsonConfig) {
        this.fastJsonConfig = fastJsonConfig;
    }

    @Deprecated
    public void setFeatures(SerializerFeature ... serializerFeatureArray) {
        this.fastJsonConfig.setSerializerFeatures(serializerFeatureArray);
    }

    @Deprecated
    public void setFilters(SerializeFilter ... serializeFilterArray) {
        this.fastJsonConfig.setSerializeFilters(serializeFilterArray);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void writeTo(Object object, Class<?> serializerFeatureArray, Type arrayList, Annotation[] annotationArray, MediaType mediaType, MultivaluedMap<String, Object> multivaluedMap, OutputStream outputStream) throws IOException, WebApplicationException {
        serializerFeatureArray = this.fastJsonConfig.getSerializerFeatures();
        if (this.uriInfo != null && this.uriInfo.getQueryParameters().containsKey((Object)"pretty")) {
            if (serializerFeatureArray == null) {
                serializerFeatureArray = new SerializerFeature[]{SerializerFeature.PrettyFormat};
            } else {
                arrayList = new ArrayList<SerializerFeature>(Arrays.asList(serializerFeatureArray));
                arrayList.add(SerializerFeature.PrettyFormat);
                serializerFeatureArray = arrayList.toArray(serializerFeatureArray);
            }
            this.fastJsonConfig.setSerializerFeatures(serializerFeatureArray);
        }
        JSON.writeJSONString(outputStream, this.fastJsonConfig.getCharset(), object, this.fastJsonConfig.getSerializeConfig(), this.fastJsonConfig.getSerializeFilters(), this.fastJsonConfig.getDateFormat(), JSON.DEFAULT_GENERATE_FEATURE, this.fastJsonConfig.getSerializerFeatures());
        outputStream.flush();
    }
}

