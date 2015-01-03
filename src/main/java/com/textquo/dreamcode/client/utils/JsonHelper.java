/**
 *
 * Copyright (c) 2014 Kerby Martino and others. All rights reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *     __                                         __
 * .--|  .----.-----.---.-.--------.----.-----.--|  .-----.
 * |  _  |   _|  -__|  _  |        |  __|  _  |  _  |  -__|
 * |_____|__| |_____|___._|__|__|__|____|_____|_____|_____|
 *
 */
package com.textquo.dreamcode.client.utils;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.json.client.JSONException;

import java.util.HashMap;
import java.util.Map;

public class JsonHelper {
    public static String toJson(Map<String, String> map) {
        String json = "";
        if (map != null && !map.isEmpty()) {
            JSONObject jsonObj = new JSONObject();
            for (Map.Entry<String, String> entry: map.entrySet()) {
                jsonObj.put(entry.getKey(), new JSONString(entry.getValue()));
            }
            json = jsonObj.toString();
        }
        return json;
    }

    public static Map<String, String> toMap(String jsonStr) {
        Map<String, String> map = new HashMap<String, String>();

        JSONValue parsed = JSONParser.parseStrict(jsonStr);
        JSONObject jsonObj = parsed.isObject();
        if (jsonObj != null) {
            for (String key : jsonObj.keySet()) {
                map.put(key, jsonObj.get(key).toString());
            }
        }

        return map;
    }

    public static boolean isValid(String json){
        try {
            JSONParser.parseStrict(json);
        } catch (JSONException e) {
            return false;
        } catch (Exception e){
            return false;
        }
        return true;
    }

}
