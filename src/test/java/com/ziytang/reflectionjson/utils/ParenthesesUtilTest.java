package com.ziytang.reflectionjson.utils;

class ParenthesesUtilTest {

    void camelToUnderscore() {
        String res = ParenthesesUtil.camelToUnderscore("userId");
        System.out.println(res);
    }

    public static void main(String[] args) {
        ParenthesesUtilTest test = new ParenthesesUtilTest();
        test.camelToUnderscore();
    }
}