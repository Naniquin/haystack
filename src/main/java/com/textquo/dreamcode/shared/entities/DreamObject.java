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
package com.textquo.dreamcode.shared.entities;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.IsSerializable;
import com.textquo.dreamcode.client.utils.JsonHelper;
import com.textquo.twist.annotations.Entity;
import com.textquo.twist.annotations.Id;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

@Entity(name = "Document")
public class DreamObject implements Serializable, IsSerializable {

    @Id
    private Long id;
    private String type;
    private String obj;

    public DreamObject(){

    }

    public DreamObject(Long id, String type, String obj){
        this.id = id;
        this.type = type;
        this.obj = obj;
    }

    public DreamObject(String type, String obj){
        this.type = type;
        this.obj = obj;
    }

//    public Object get(String key) {
//        return properties.get(key);
//    }
//    public void set(String key, Object value) {
//        properties.put(key, value);
//    }

    public static DreamObject parse(Object obj){
        // TODO: Implementation
        return new DreamObject();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Map<String, Object> getProperties(){
        Map<String, Object>  properties = new LinkedHashMap<String, Object>();
        return properties;
    }

    public static DreamObject fromJson(String json){
        Map<String,String> map = JsonHelper.toMap(json);
        String id = map.get("id");
        String type = map.get("type");
        String obj = map.get("obj");
        DreamObject dreamObject = new DreamObject(Long.valueOf(id), type, obj);
        return dreamObject;
    }

    public static String toJson(DreamObject dreamObject){
        String json = "{}";
        Map<String,String> map = new LinkedHashMap<String, String>();
        map.put("id", String.valueOf(dreamObject.getId()));
        map.put("type", dreamObject.getType());
        json = JsonHelper.toJson(map);
        return json;
    }

}
