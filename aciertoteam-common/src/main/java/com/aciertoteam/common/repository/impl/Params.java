package com.aciertoteam.common.repository.impl;

import org.hibernate.Query;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ishestiporov
 */
public class Params {

    private final Map<String, Object> params;

    public Params() {
        params = new HashMap<String, Object>();
    }

    public Params(String param, Object value) {
        this();
        add(param, value);
    }

    public Params add(String param, Object value) {
        params.put(param, value);
        return this;
    }

    void addToQuery(Query query) {
        for (Map.Entry<String, Object> param : params.entrySet()) {
            query.setParameter(param.getKey(), param.getValue());
        }
    }

}
