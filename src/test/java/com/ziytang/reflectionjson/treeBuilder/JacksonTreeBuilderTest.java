package com.ziytang.reflectionjson.treeBuilder;

import com.fasterxml.jackson.databind.JsonNode;

class JacksonTreeBuilderTest {
    public void printTreeTest() {
        JacksonTreeBuilder builder = new JacksonTreeBuilder();
        JsonNode node = builder.buildTree("[name=zell,sex=male,age=25]");
        System.out.println(builder.printTree(node));
    }
    public static void main(String[] args) {
        JacksonTreeBuilderTest test = new JacksonTreeBuilderTest();
        test.printTreeTest();
    }
}