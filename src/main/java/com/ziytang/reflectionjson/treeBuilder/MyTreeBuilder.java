package com.ziytang.reflectionjson.treeBuilder;

import com.ziytang.reflectionjson.model.ListNode;
import com.ziytang.reflectionjson.model.Node;
import com.ziytang.reflectionjson.model.ObjectNode;
import com.ziytang.reflectionjson.utils.ParenthesesUtil;

import java.util.List;
import java.util.Map;

public class MyTreeBuilder implements TreeBuilder{
    @Override
    public Node buildTree(String str) {
        if (str.startsWith("{")) {
            return buildListTree(str.substring(1, str.length() - 1));
        }
        if (str.startsWith("[")) {
            return buildObjectTree(str.substring(1, str.length() - 1));
        }
        throw new RuntimeException("String should starts with '[' or '{");
    }
    private Node buildObjectTree(String str) {
        ObjectNode node = new ObjectNode();
        Map<String, Object> map = node.getMap();
        int pre = 0, cur = 0;
        while (cur < str.length()) {
            if (str.charAt(cur) == '=') {
                Object value = null;
                String key = str.substring(pre, cur);
                key = ParenthesesUtil.camelToUnderscore(key);
                char next = str.charAt(cur + 1);
                if (next == '[' || next == '{') {
                    int end = ParenthesesUtil.indexOfEndParentheses(str, cur + 1);
                    value = buildTree(str.substring(cur + 1, end + 1));
                    pre = end + 2;
                    cur = end + 1;
                } else {
                    int p = cur + 1;
                    while (p < str.length() && str.charAt(p) != ',') p++;
                    value = str.substring(cur + 1, p);
                    pre = p + 1;
                    cur = p;
                }
                map.put(key, value);
            }
            cur++;
        }
        return node;
    }
    private Node buildListTree(String str) {
        ListNode node = new ListNode();
        List<Object> list = node.getList();
        char first = str.charAt(0);
        if (first == '[' || first == '{') {
            int start = 0;
            while (start < str.length()) {
                int end = ParenthesesUtil.indexOfEndParentheses(str, start);
                list.add(buildTree(str.substring(start, end + 1)));
                start = end + 2;
            }
        } else {
            int pre = 0, cur = 0;
            while (cur < str.length()) {
                if (str.charAt(cur) == ',') {
                    list.add(str.substring(pre, cur));
                    pre = cur + 1;
                }
                cur++;
            }
            list.add(str.substring(pre, cur));
        }
        return node;
    }

    @Override
    public String printTree(Object node) {
        if (node instanceof ObjectNode) {
            return printObjectNode((ObjectNode) node);
        }
        if (node instanceof ListNode) {
            return printListNode((ListNode) node);
        }
        throw new RuntimeException(String.format("Can not print Node with given type: %s", node.getClass().toString()));
    }
    private String printObjectNode(ObjectNode node) {
        Map<String, Object> map = node.getMap();
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            sb.append(ParenthesesUtil.addQuote(entry.getKey()));
            sb.append(":");
            Object value = entry.getValue();
            if (value instanceof String) {
                ParenthesesUtil.appendPrimary((String) value, sb);
            } else {
                sb.append(printTree((Node) value));
            }
            sb.append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append("}");
        return sb.toString();
    }

    private String printListNode(ListNode node) {
        List<Object> list = node.getList();
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (Object n : list) {
            if (n instanceof ListNode || n instanceof ObjectNode) {
                sb.append(printTree(n));
            } else {
                ParenthesesUtil.appendPrimary((String) n, sb);
            }
            sb.append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append("]");
        return sb.toString();
    }
}
