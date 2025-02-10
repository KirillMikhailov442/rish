package dev.parser;

import dev.ast.values.NumberValue;
import dev.ast.values.Value;

import java.util.HashMap;
import java.util.Map;

public class Variable {
    static Map<String, Value> variables;

    static  {
        variables = new HashMap<>();
        variables.put("PI", new NumberValue(Math.PI));
        variables.put("E", new NumberValue(Math.E));
    }

    public static boolean isExists(String key){
        return variables.containsKey(key);
    }

    public static Value get(String key){
        if(!isExists(key)) return new NumberValue(0);
        return variables.get(key);
    }

    public static void set(String key, Value value){
        if(isExists(key)) throw new RuntimeException(String.format("Variable #%s# already exists", key));
        variables.put(key, value);
    }
}
