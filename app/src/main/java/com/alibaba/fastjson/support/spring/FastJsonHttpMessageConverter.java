/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  org.springframework.http.HttpHeaders
 *  org.springframework.http.HttpInputMessage
 *  org.springframework.http.HttpOutputMessage
 *  org.springframework.http.MediaType
 *  org.springframework.http.converter.AbstractHttpMessageConverter
 *  org.springframework.http.converter.GenericHttpMessageConverter
 *  org.springframework.http.converter.HttpMessageNotReadableException
 *  org.springframework.http.converter.HttpMessageNotWritableException
 */
package com.alibaba.fastjson.support.spring;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.util.IOUtils;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.GenericHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

public class FastJsonHttpMessageConverter
extends AbstractHttpMessageConverter<Object>
implements GenericHttpMessageConverter<Object> {
    @Deprecated
    protected Charset charset = IOUtils.UTF8;
    @Deprecated
    protected String dateFormat;
    private FastJsonConfig fastJsonConfig;
    @Deprecated
    protected SerializerFeature[] features = new SerializerFeature[0];
    @Deprecated
    protected SerializeFilter[] filters = new SerializeFilter[0];

    public FastJsonHttpMessageConverter() {
        super(MediaType.ALL);
        this.fastJsonConfig = new FastJsonConfig();
    }

    @Deprecated
    public void addSerializeFilter(SerializeFilter serializeFilter) {
        if (serializeFilter == null) {
            return;
        }
        int n2 = this.fastJsonConfig.getSerializeFilters().length;
        SerializeFilter[] serializeFilterArray = new SerializeFilter[n2 + 1];
        System.arraycopy(this.fastJsonConfig.getSerializeFilters(), 0, serializeFilterArray, 0, n2);
        serializeFilterArray[serializeFilterArray.length - 1] = serializeFilter;
        this.fastJsonConfig.setSerializeFilters(serializeFilterArray);
    }

    public boolean canRead(Type type, Class<?> clazz, MediaType mediaType) {
        return super.canRead(clazz, mediaType);
    }

    public boolean canWrite(Type type, Class<?> clazz, MediaType mediaType) {
        return super.canWrite(clazz, mediaType);
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

    public Object read(Type type, Class<?> clazz, HttpInputMessage httpInputMessage) throws IOException, HttpMessageNotReadableException {
        return JSON.parseObject(httpInputMessage.getBody(), this.fastJsonConfig.getCharset(), type, this.fastJsonConfig.getFeatures());
    }

    protected Object readInternal(Class<? extends Object> clazz, HttpInputMessage httpInputMessage) throws IOException, HttpMessageNotReadableException {
        return JSON.parseObject(httpInputMessage.getBody(), this.fastJsonConfig.getCharset(), clazz, this.fastJsonConfig.getFeatures());
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

    protected boolean supports(Class<?> clazz) {
        return true;
    }

    public void write(Object object, Type object2, MediaType mediaType, HttpOutputMessage httpOutputMessage) throws IOException, HttpMessageNotWritableException {
        HttpHeaders httpHeaders;
        block5: {
            block7: {
                block6: {
                    httpHeaders = httpOutputMessage.getHeaders();
                    if (httpHeaders.getContentType() != null) break block5;
                    if (mediaType == null || mediaType.isWildcardType()) break block6;
                    object2 = mediaType;
                    if (!mediaType.isWildcardSubtype()) break block7;
                }
                object2 = this.getDefaultContentType(object);
            }
            if (object2 != null) {
                httpHeaders.setContentType((MediaType)object2);
            }
        }
        if (httpHeaders.getContentLength() == -1L && (object2 = this.getContentLength(object, httpHeaders.getContentType())) != null) {
            httpHeaders.setContentLength(((Long)object2).longValue());
        }
        this.writeInternal(object, httpOutputMessage);
        httpOutputMessage.getBody().flush();
    }

    protected void writeInternal(Object object, HttpOutputMessage httpOutputMessage) throws IOException, HttpMessageNotWritableException {
        httpOutputMessage.getHeaders().setContentLength((long)JSON.writeJSONString(httpOutputMessage.getBody(), this.fastJsonConfig.getCharset(), object, this.fastJsonConfig.getSerializeConfig(), this.fastJsonConfig.getSerializeFilters(), this.fastJsonConfig.getDateFormat(), JSON.DEFAULT_GENERATE_FEATURE, this.fastJsonConfig.getSerializerFeatures()));
    }
}

