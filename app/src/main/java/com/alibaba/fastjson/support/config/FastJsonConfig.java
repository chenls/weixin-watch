/*
 * Decompiled with CFR 0.151.
 */
package com.alibaba.fastjson.support.config;

import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.util.IOUtils;
import java.nio.charset.Charset;
import java.util.Map;

public class FastJsonConfig {
    private Charset charset = IOUtils.UTF8;
    private Map<Class<?>, SerializeFilter> classSerializeFilters;
    private String dateFormat;
    private Feature[] features;
    private ParserConfig parserConfig;
    private SerializeConfig serializeConfig = SerializeConfig.getGlobalInstance();
    private SerializeFilter[] serializeFilters;
    private SerializerFeature[] serializerFeatures;

    public FastJsonConfig() {
        this.parserConfig = ParserConfig.getGlobalInstance();
        this.serializerFeatures = new SerializerFeature[0];
        this.serializeFilters = new SerializeFilter[0];
        this.features = new Feature[0];
    }

    public Charset getCharset() {
        return this.charset;
    }

    public Map<Class<?>, SerializeFilter> getClassSerializeFilters() {
        return this.classSerializeFilters;
    }

    public String getDateFormat() {
        return this.dateFormat;
    }

    public Feature[] getFeatures() {
        return this.features;
    }

    public ParserConfig getParserConfig() {
        return this.parserConfig;
    }

    public SerializeConfig getSerializeConfig() {
        return this.serializeConfig;
    }

    public SerializeFilter[] getSerializeFilters() {
        return this.serializeFilters;
    }

    public SerializerFeature[] getSerializerFeatures() {
        return this.serializerFeatures;
    }

    public void setCharset(Charset charset) {
        this.charset = charset;
    }

    public void setClassSerializeFilters(Map<Class<?>, SerializeFilter> map) {
        if (map == null) {
            return;
        }
        for (Map.Entry<Class<?>, SerializeFilter> entry : map.entrySet()) {
            this.serializeConfig.addFilter(entry.getKey(), entry.getValue());
        }
        this.classSerializeFilters = map;
    }

    public void setDateFormat(String string2) {
        this.dateFormat = string2;
    }

    public void setFeatures(Feature ... featureArray) {
        this.features = featureArray;
    }

    public void setParserConfig(ParserConfig parserConfig) {
        this.parserConfig = parserConfig;
    }

    public void setSerializeConfig(SerializeConfig serializeConfig) {
        this.serializeConfig = serializeConfig;
    }

    public void setSerializeFilters(SerializeFilter ... serializeFilterArray) {
        this.serializeFilters = serializeFilterArray;
    }

    public void setSerializerFeatures(SerializerFeature ... serializerFeatureArray) {
        this.serializerFeatures = serializerFeatureArray;
    }
}

