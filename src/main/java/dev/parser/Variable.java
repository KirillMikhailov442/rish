package dev.parser;

import java.util.HashMap;
import java.util.Map;

public class Variable {
    static Map<String, Double> variables;

    static  {
        variables = new HashMap<>();
        variables.put("PI", Math.PI);
        variables.put("E", Math.E);
    }

    public static boolean isExists(String key){
        return variables.containsKey(key);
    }

    public static double get(String key){
        if(!isExists(key)) return 0;
        return variables.get(key);
    }

    public static void set(String key, double value){
        if(isExists(key)) throw new RuntimeException(String.format("Variable #%s# already exists", key));
        variables.put(key, value);
    }
}
