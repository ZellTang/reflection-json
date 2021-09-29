package com.ziytang.reflectionjson.factory;

import com.fasterxml.jackson.databind.JsonNode;
import com.ziytang.reflectionjson.enums.BuilderType;
import com.ziytang.reflectionjson.model.Node;
import com.ziytang.reflectionjson.treeBuilder.JacksonTreeBuilder;
import com.ziytang.reflectionjson.treeBuilder.MyTreeBuilder;
import com.ziytang.reflectionjson.treeBuilder.TreeBuilder;

public class JsonTreeFactory {
    private static TreeBuilder myTreeBuilder = new MyTreeBuilder();
    private static TreeBuilder jacksonTreeBuilder = new JacksonTreeBuilder();
    public static TreeBuilder getBuilder(BuilderType type) {
        TreeBuilder res = null;
        switch (type) {
            case MyType:
                res = myTreeBuilder;
                break;
            case JacksonType:
                res = jacksonTreeBuilder;
                break;
            default:
                break;
        }
        if (res == null) {
            throw new RuntimeException(String.format("No such TreeBuilder for given BuilderType: %s", type));
        }
        return res;
    }
}
