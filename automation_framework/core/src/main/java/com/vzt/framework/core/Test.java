package com.vzt.framework.core;

import com.google.gson.Gson;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

public class Test {

    public static void main(String[] args) {
        String json = "{ \"store\":{ \"book\":[ { \"category\":\"reference\", \"author\":\"Nigel Rees\", \"title\":\"Sayings of the Century\", \"price\":8.95 }, { \"category\":\"fiction\", \"author\":\"Evelyn Waugh\", \"title\":\"Sword of Honour\", \"price\":12.99, \"isbn\":\"0-553-21311-3\" }, { \"category\":\"fiction\", \"author\":\"Evelyn Waugh1\", \"title\":\"Sword of Honour1\", \"price\":12.99, \"isbn\":\"0-553-21311-3\" } ], \"bicycle\":{ \"color\":\"red\", \"price\":19.95 } } }";
        System.out.println(json);
        
        DocumentContext doc = JsonPath.parse(json);
            doc.set("$.store.bicycle.color", "green").
            set("$.store.book[*].price", 9.51);
        String newJson = new Gson().toJson(doc.read("$"));
        System.out.println(newJson);
    }
}
