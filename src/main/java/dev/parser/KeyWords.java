package dev.parser;

import java.util.HashMap;
import java.util.Map;

public class KeyWords {
    final static Map<String, String> keywords;

    static  {
        keywords = new HashMap<>();
        keywords.put("INPUT", "input");
        keywords.put("PRINT", "print");
        keywords.put("IF", "if");
        keywords.put("ELSE", "else");
    }

    public static boolean isExists(String key){
        return keywords.containsKey(key);
    }

    public static String get(String key) {
        if(!isExists(key)) return null;
        return keywords.get(key);
    }
}
