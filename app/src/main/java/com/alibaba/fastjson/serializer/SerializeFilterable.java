/*
 * Decompiled with CFR 0.151.
 */
package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.serializer.AfterFilter;
import com.alibaba.fastjson.serializer.BeforeFilter;
import com.alibaba.fastjson.serializer.ContextValueFilter;
import com.alibaba.fastjson.serializer.LabelFilter;
import com.alibaba.fastjson.serializer.NameFilter;
import com.alibaba.fastjson.serializer.PropertyFilter;
import com.alibaba.fastjson.serializer.PropertyPreFilter;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.alibaba.fastjson.serializer.ValueFilter;
import java.util.ArrayList;
import java.util.List;

public abstract class SerializeFilterable {
    protected List<AfterFilter> afterFilters = null;
    protected List<BeforeFilter> beforeFilters = null;
    protected List<ContextValueFilter> contextValueFilters = null;
    protected List<LabelFilter> labelFilters = null;
    protected List<NameFilter> nameFilters = null;
    protected List<PropertyFilter> propertyFilters = null;
    protected List<PropertyPreFilter> propertyPreFilters = null;
    protected List<ValueFilter> valueFilters = null;
    protected boolean writeDirect = true;

    /*
     * Enabled aggressive block sorting
     */
    public void addFilter(SerializeFilter serializeFilter) {
        block11: {
            block10: {
                if (serializeFilter == null) break block10;
                if (serializeFilter instanceof PropertyPreFilter) {
                    this.getPropertyPreFilters().add((PropertyPreFilter)serializeFilter);
                }
                if (serializeFilter instanceof NameFilter) {
                    this.getNameFilters().add((NameFilter)serializeFilter);
                }
                if (serializeFilter instanceof ValueFilter) {
                    this.getValueFilters().add((ValueFilter)serializeFilter);
                }
                if (serializeFilter instanceof ContextValueFilter) {
                    this.getContextValueFilters().add((ContextValueFilter)serializeFilter);
                }
                if (serializeFilter instanceof PropertyFilter) {
                    this.getPropertyFilters().add((PropertyFilter)serializeFilter);
                }
                if (serializeFilter instanceof BeforeFilter) {
                    this.getBeforeFilters().add((BeforeFilter)serializeFilter);
                }
                if (serializeFilter instanceof AfterFilter) {
                    this.getAfterFilters().add((AfterFilter)serializeFilter);
                }
                if (serializeFilter instanceof LabelFilter) break block11;
            }
            return;
        }
        this.getLabelFilters().add((LabelFilter)serializeFilter);
    }

    public List<AfterFilter> getAfterFilters() {
        if (this.afterFilters == null) {
            this.afterFilters = new ArrayList<AfterFilter>();
            this.writeDirect = false;
        }
        return this.afterFilters;
    }

    public List<BeforeFilter> getBeforeFilters() {
        if (this.beforeFilters == null) {
            this.beforeFilters = new ArrayList<BeforeFilter>();
            this.writeDirect = false;
        }
        return this.beforeFilters;
    }

    public List<ContextValueFilter> getContextValueFilters() {
        if (this.contextValueFilters == null) {
            this.contextValueFilters = new ArrayList<ContextValueFilter>();
            this.writeDirect = false;
        }
        return this.contextValueFilters;
    }

    public List<LabelFilter> getLabelFilters() {
        if (this.labelFilters == null) {
            this.labelFilters = new ArrayList<LabelFilter>();
            this.writeDirect = false;
        }
        return this.labelFilters;
    }

    public List<NameFilter> getNameFilters() {
        if (this.nameFilters == null) {
            this.nameFilters = new ArrayList<NameFilter>();
            this.writeDirect = false;
        }
        return this.nameFilters;
    }

    public List<PropertyFilter> getPropertyFilters() {
        if (this.propertyFilters == null) {
            this.propertyFilters = new ArrayList<PropertyFilter>();
            this.writeDirect = false;
        }
        return this.propertyFilters;
    }

    public List<PropertyPreFilter> getPropertyPreFilters() {
        if (this.propertyPreFilters == null) {
            this.propertyPreFilters = new ArrayList<PropertyPreFilter>();
            this.writeDirect = false;
        }
        return this.propertyPreFilters;
    }

    public List<ValueFilter> getValueFilters() {
        if (this.valueFilters == null) {
            this.valueFilters = new ArrayList<ValueFilter>();
            this.writeDirect = false;
        }
        return this.valueFilters;
    }
}

