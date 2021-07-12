/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.util.CollectionUtils
 *  org.springframework.validation.BindingResult
 *  org.springframework.web.servlet.view.AbstractView
 */
package com.alibaba.fastjson.support.spring;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.util.IOUtils;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.view.AbstractView;

public class FastJsonJsonView
extends AbstractView {
    public static final String DEFAULT_CONTENT_TYPE = "application/json;charset=UTF-8";
    @Deprecated
    protected Charset charset = IOUtils.UTF8;
    @Deprecated
    protected String dateFormat;
    private boolean disableCaching = true;
    private boolean extractValueFromSingleKeyModel = false;
    private FastJsonConfig fastJsonConfig;
    @Deprecated
    protected SerializerFeature[] features = new SerializerFeature[0];
    @Deprecated
    protected SerializeFilter[] filters = new SerializeFilter[0];
    private Set<String> renderedAttributes;
    private boolean updateContentLength = false;

    public FastJsonJsonView() {
        this.fastJsonConfig = new FastJsonConfig();
        this.setContentType(DEFAULT_CONTENT_TYPE);
        this.setExposePathVariables(false);
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    protected Object filterModel(Map<String, Object> hashMap) {
        void var1_7;
        HashMap hashMap2 = new HashMap(hashMap.size());
        Set<String> set = !CollectionUtils.isEmpty(this.renderedAttributes) ? this.renderedAttributes : hashMap.keySet();
        for (Map.Entry entry : hashMap.entrySet()) {
            if (entry.getValue() instanceof BindingResult || !set.contains(entry.getKey())) continue;
            hashMap2.put(entry.getKey(), entry.getValue());
        }
        HashMap hashMap3 = hashMap2;
        if (this.extractValueFromSingleKeyModel) {
            HashMap hashMap4 = hashMap2;
            if (hashMap2.size() == 1) {
                set = hashMap2.entrySet().iterator();
                HashMap hashMap5 = hashMap2;
                if (set.hasNext()) {
                    Object v2 = ((Map.Entry)set.next()).getValue();
                }
            }
        }
        return var1_7;
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

    public boolean isExtractValueFromSingleKeyModel() {
        return this.extractValueFromSingleKeyModel;
    }

    protected void prepareResponse(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        this.setResponseContentType(httpServletRequest, httpServletResponse);
        httpServletResponse.setCharacterEncoding(this.fastJsonConfig.getCharset().name());
        if (this.disableCaching) {
            httpServletResponse.addHeader("Pragma", "no-cache");
            httpServletResponse.addHeader("Cache-Control", "no-cache, no-store, max-age=0");
            httpServletResponse.addDateHeader("Expires", 1L);
        }
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    protected void renderMergedOutputModel(Map<String, Object> object, HttpServletRequest object2, HttpServletResponse httpServletResponse) throws Exception {
        void var3_4;
        Object object3 = this.filterModel((Map<String, Object>)object);
        object = this.updateContentLength ? this.createTemporaryOutputStream() : var3_4.getOutputStream();
        JSON.writeJSONString((OutputStream)object, this.fastJsonConfig.getCharset(), object3, this.fastJsonConfig.getSerializeConfig(), this.fastJsonConfig.getSerializeFilters(), this.fastJsonConfig.getDateFormat(), JSON.DEFAULT_GENERATE_FEATURE, this.fastJsonConfig.getSerializerFeatures());
        ((OutputStream)object).flush();
        if (this.updateContentLength) {
            this.writeToResponse((HttpServletResponse)var3_4, (ByteArrayOutputStream)object);
        }
    }

    @Deprecated
    public void setCharset(Charset charset) {
        this.fastJsonConfig.setCharset(charset);
    }

    @Deprecated
    public void setDateFormat(String string2) {
        this.fastJsonConfig.setDateFormat(string2);
    }

    public void setDisableCaching(boolean bl2) {
        this.disableCaching = bl2;
    }

    public void setExtractValueFromSingleKeyModel(boolean bl2) {
        this.extractValueFromSingleKeyModel = bl2;
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

    public void setRenderedAttributes(Set<String> set) {
        this.renderedAttributes = set;
    }

    @Deprecated
    public void setSerializerFeature(SerializerFeature ... serializerFeatureArray) {
        this.fastJsonConfig.setSerializerFeatures(serializerFeatureArray);
    }

    public void setUpdateContentLength(boolean bl2) {
        this.updateContentLength = bl2;
    }
}

