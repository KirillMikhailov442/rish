package dev.parser;

import java.util.HashMap;
import java.util.Map;

public class KeyWords {
    final static Map<String, String> keyWords;

    static  {
        keyWords = new HashMap<>();
        keyWords.put("INPUT", "input");
        keyWords.put("PRINT", "print");
        keyWords.put("IF", "if");
        keyWords.put("ELSE", "else");
    }

    public static boolean isExists(String key){
        return keyWords.containsKey(key);
    }

    public static String get(String key) {
        if(!isExists(key)) return null;
        return keyWords.get(key);
    }
}
