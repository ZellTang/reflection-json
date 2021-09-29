package com.ziytang.reflectionjson.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.Stack;

public class ParenthesesUtil {
    public static int indexOfEndParentheses(String s, int start) {
        Stack<Character> stack = new Stack<>();
        stack.push(s.charAt(start));
        for (int i = start + 1; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '{' || c == '[') {
                stack.push(s.charAt(i));
            }
            if (c == '}' || c == ']') {
                stack.pop();
            }
            if (stack.isEmpty()) {
                return i;
            }
        }
        throw new RuntimeException("Parentheses Error");
    }
    public static boolean isInteger(String s) {
        if (s == null || s.length() == 0) {
            return false;
        }
        int index = 0;
        if (s.charAt(0) == '-') {
            if (s.length() == 1) {
                return false;
            }
            index = 1;
        }
        while (index < s.length()) {
            char c = s.charAt(index);
            if (c < '0' || c > '9') {
                return false;
            }
            index++;
        }
        return true;
    }
    public static String addQuote(String s) {
        return "\"" + s + "\"";
    }

    public static void appendPrimary(String value, StringBuilder sb) {
        if (value.equals("<null>")) {
            sb.append("null");
        } else if (value.equals("true") || value.equals("false") || ParenthesesUtil.isInteger((String) value)) {
            sb.append(value);
        } else {
            sb.append(ParenthesesUtil.addQuote((String) value));
        }
    }
    public static void addPrimaryInTree(String key, String value, ObjectNode node) {
        if (value.equals("<null>")) {
            node.putNull(key);
        } else if (value.equals("true")) {
            node.put(key, true);
        } else if (value.equals("false")) {
            node.put(key, false);
        } else if (isInteger(value)) {
            node.put(key, Long.parseLong(value));
        } else {
            node.put(key, value);
        }
    }
    public static void addPrimaryInTree(String value, ArrayNode node) {
        if (value.equals("<null>")) {
            node.addNull();
        } else if (value.equals("true")) {
            node.add(true);
        } else if (value.equals("false")) {
            node.add(false);
        } else if (isInteger(value)) {
            node.add(Integer.parseInt(value));
        } else {
            node.add(value);
        }
    }
}
