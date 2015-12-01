package com.hunchee.haystack.server.utils;

import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.Map;

/**
 * Created by K on 12/1/2015.
 */
public class JSONUtil {
    public static Map parse(JSONObject object){
        org.json.simple.JSONObject jsonObject = null;
        JSONParser parser=new JSONParser(); // this needs the "json-simple" library
        try {
            Object obj = parser.parse(object.toString());
            jsonObject=(org.json.simple.JSONObject)obj;
            return jsonObject;
        } catch(Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
