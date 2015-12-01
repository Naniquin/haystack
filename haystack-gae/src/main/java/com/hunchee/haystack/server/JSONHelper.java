/**
 *
 * Copyright (c) 2015 Dotweblabs Web Technologies and others. All rights reserved.
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
package com.hunchee.haystack.server;

import org.json.simple.parser.ContainerFactory;
import org.json.simple.parser.ParseException;

import java.util.*;
import java.util.logging.Logger;

/**
 * @author <a href="mailto:kerby@dotweblabs.com">Kerby Martino</a>
 * @version 1.0
 * @since 1.0
 */
public class JSONHelper {
    private static final Logger LOG
            = Logger.getLogger(JSONHelper.class.getName());
    public static Map<String,Object> parseJson(String jsonText) throws ParseException {
        Map<String,Object> json = null;
        org.json.simple.parser.JSONParser parser = new org.json.simple.parser.JSONParser();
        ContainerFactory containerFactory = new ContainerFactory(){
            public List creatArrayContainer() {
                return new LinkedList();
            }
            public Map createObjectContainer() {
                return new LinkedHashMap();
            }
        };
        json = (Map<String,Object> )parser.parse(jsonText, containerFactory);
        Iterator iter = json.entrySet().iterator();
        LOG.info("==iterate result==");
        while(iter.hasNext()){
            Map.Entry entry = (Map.Entry)iter.next();
            LOG.info(entry.getKey() + "=>" + entry.getValue());
        }
        LOG.info("==toJSONString()==");
        LOG.info(org.json.simple.JSONValue.toJSONString(json));
        return json;
    }

}
