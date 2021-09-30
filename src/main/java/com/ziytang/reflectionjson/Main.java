package com.ziytang.reflectionjson;

import com.ziytang.reflectionjson.enums.BuilderType;
import com.ziytang.reflectionjson.factory.JsonTreeFactory;
import com.ziytang.reflectionjson.model.Node;
import com.ziytang.reflectionjson.treeBuilder.TreeBuilder;

public class Main {
    private static final String str = "{123,456}";
    public static void main(String[] args) {
        String res1 = test(0);
        String res2 = test(1);
        System.out.println(res1.equals(res2));
        System.out.println(res2);
    }
    private static String test(int code) {
        TreeBuilder builder = JsonTreeFactory.getBuilder(BuilderType.getType(code));
        Object tree = builder.buildTree(str);
        String jsonString = builder.printTree(tree);
        return jsonString;
    }
}
