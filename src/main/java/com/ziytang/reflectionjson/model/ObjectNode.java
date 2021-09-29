package com.ziytang.reflectionjson.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class ObjectNode implements Node{
    private Map<String, Object> map = new LinkedHashMap<>();

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }
}
