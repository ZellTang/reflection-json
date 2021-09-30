package com.ziytang.reflectionjson.treeBuilder;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.ziytang.reflectionjson.utils.ParenthesesUtil;

public class JacksonTreeBuilder implements TreeBuilder{
    private static final ObjectMapper mapper = new ObjectMapper();
    @Override
    public JsonNode buildTree(String str) {
        String s = str.substring(1, str.length() - 1);
        if (str.startsWith("[")) {
            return buildObjectTree(s);
        }
        if (str.startsWith("{")) {
            return buildListTree(s);
        }
        throw new RuntimeException("String should starts with '[' or '{");
    }
    private JsonNode buildObjectTree(String str) {
        ObjectNode node = mapper.createObjectNode();
        int pre = 0, cur = 0;
        while (cur < str.length()) {
            if (str.charAt(cur) == '=') {
                String key = str.substring(pre, cur);
                key = ParenthesesUtil.camelToUnderscore(key);
                char c = str.charAt(cur + 1);
                if (c == '[' || c == '{') {
                    int end = ParenthesesUtil.indexOfEndParentheses(str, cur + 1);
                    JsonNode value = buildTree(str.substring(cur + 1, end + 1));
                    node.set(key, value);
                    pre = end + 2;
                    cur = end + 1;
                } else {
                    int p = cur + 1;
                    while (p < str.length() && str.charAt(p) != ',') p++;
                    String value = str.substring(cur + 1, p);
                    ParenthesesUtil.addPrimaryInTree(key, value, node);
                    pre = p + 1;
                    cur = p;
                }
            }
            cur++;
        }
        return node;
    }
    private JsonNode buildListTree(String str) {
        ArrayNode node = mapper.createArrayNode();
        char first = str.charAt(0);
        if (first == '[' || first == '{') {
            int start = 0;
            while (start < str.length()) {
                int end = ParenthesesUtil.indexOfEndParentheses(str, start);
                node.add(buildTree(str.substring(start, end + 1)));
                start = end + 2;
            }
        } else {
            int pre = 0, cur = 0;
            while (cur < str.length()) {
                if (str.charAt(cur) == ',') {
                    ParenthesesUtil.addPrimaryInTree(str.substring(pre, cur), node);
                    pre = cur + 1;
                }
                cur++;
            }
            ParenthesesUtil.addPrimaryInTree(str.substring(pre, cur), node);
        }
        return node;
    }


    @Override
    public String printTree(Object tree) {
        String res = null;
        try {
            return  mapper.writeValueAsString(tree);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Jackson tree to json string failed");
        }
    }
}
