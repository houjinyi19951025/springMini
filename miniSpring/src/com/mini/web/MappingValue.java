package com.mini.web;

public class MappingValue {
    String clz;

    String uri;

    String method;

    public String getClz() {
        return clz;
    }

    public void setClz(String clz) {
        this.clz = clz;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public MappingValue(String clz, String uri, String method) {
        this.clz = clz;
        this.uri = uri;
        this.method = method;
    }
}
