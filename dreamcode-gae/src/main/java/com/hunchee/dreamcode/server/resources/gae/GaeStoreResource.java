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
package com.hunchee.dreamcode.server.resources.gae;

import com.google.appengine.repackaged.com.google.common.base.Preconditions;
import com.hunchee.dreamcode.server.JSONHelper;
import com.hunchee.dreamcode.server.guice.SelfInjectingServerResource;
import com.hunchee.dreamcode.server.services.ShardedCounterService;
import org.json.JSONObject;
import org.json.simple.parser.ParseException;
import org.restlet.data.Header;
import org.restlet.data.MediaType;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.*;
import org.restlet.data.Status;
import org.restlet.engine.header.HeaderConstants;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.util.Series;

import java.util.*;
import java.util.logging.Logger;

/**
 * @author <a href="mailto:kerby@dotweblabs.com">Kerby Martino</a>
 * @version 1.0
 * @since 1.0
 */
public class GaeStoreResource extends SelfInjectingServerResource {

    private static final Logger LOG
            = Logger.getLogger(GaeStoreResource.class.getName());

    ShardedCounterService shardCounterService;

    @Post("json")
    public Representation add(Representation entity){
        String jsonString = "{}";
        Series<Header> responseHeaders = (Series<Header>)
                getResponseAttributes().get(HeaderConstants.ATTRIBUTE_HEADERS);
        if (responseHeaders == null) {
            responseHeaders = new Series(Header.class);
            getResponseAttributes().put(HeaderConstants.ATTRIBUTE_HEADERS,
                    responseHeaders);
        }
        String id = String.valueOf(getQueryValue("id"));
        String type = String.valueOf(getQueryValue("type"));
        Preconditions.checkNotNull(type, "Object type cannot be null");
        Preconditions.checkNotNull(entity, "Object cannot be null");
        LOG.info("Object type=" + type);
        LOG.info("Object id=" + id);
        //String namespace = getQueryValue("namespace");
        try{
            JsonRepresentation represent = new JsonRepresentation(entity);
            JSONObject jsonobject = represent.getJsonObject();
            String jsonText = jsonobject.toString();

            if(id==null || id.isEmpty() || id.equals("null") || id.equals("NULL")){
                shardCounterService = new ShardedCounterService();
                shardCounterService.incrementCounter(type);
                long count = shardCounterService.getCount(type);
                LOG.info("Generated from sharded counter: " + count);
                id = String.valueOf(count);
            }
            Map<String,Object> dreamObject = JSONHelper.parseJson(jsonText);
            dreamObject.put("__key__", id);
            dreamObject.put("__kind__", type);
            //store().put(dreamObject);
            setStatus(Status.SUCCESS_OK);
        } catch (ParseException e){
            setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
            e.printStackTrace();
        } catch (Exception e){
            setStatus(Status.SERVER_ERROR_INTERNAL);
            e.printStackTrace();
        } finally {

        }
        responseHeaders.add(new Header("Access-Control-Allow-Origin", "*"));
        return new StringRepresentation(jsonString, MediaType.APPLICATION_JSON);
    };
    @Put("json")
    public Representation update(String type, String id, String jsonObject){
        return new StringRepresentation("{ 'response' : 'dummy' }");
    };
    @Get("json")
    public Representation find(Representation entity){
        String jsonString = "{}";
        Series<Header> responseHeaders = (Series<Header>)
                getResponseAttributes().get(HeaderConstants.ATTRIBUTE_HEADERS);
        if (responseHeaders == null) {
            responseHeaders = new Series(Header.class);
            getResponseAttributes().put(HeaderConstants.ATTRIBUTE_HEADERS,
                    responseHeaders);
        }
        String id = String.valueOf(getAttribute("id"));
        String type = String.valueOf(getAttribute("type"));
        try{
//            Iterator<DreamObject> it
//                    = store().find(DreamObject.class, type).equal("id", id).now();
            setStatus(Status.SUCCESS_OK);
        } catch (Exception e){
            setStatus(Status.SERVER_ERROR_INTERNAL);
            e.printStackTrace();
        } finally {

        }
        return new StringRepresentation("{ 'response' : 'dummy' }");
    };
    @Delete("json")
    public Representation remove(String id){
        return new StringRepresentation("{ 'response' : 'dummy' }");
    };


}
