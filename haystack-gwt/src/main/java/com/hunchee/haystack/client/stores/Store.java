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
package com.hunchee.haystack.client.stores;

import com.google.gwt.core.client.GWT;
//import com.google.gwt.json.client.JSONObject;
import com.hunchee.haystack.client.HaystackCallback;
import com.hunchee.haystack.client.proxy.StoreResourceProxy;
import org.restlet.client.data.ChallengeResponse;
import org.restlet.client.data.ChallengeScheme;
import org.restlet.client.ext.json.JsonRepresentation;
import org.restlet.client.resource.Result;

import java.util.Map;

/**
 * @author <a href="mailto:kerby@dotweblabs.com">Kerby Martino</a>
 * @version 1.0
 * @since 1.0
 */
public class Store extends AbstractStore {
    @Override
    public void add(String type, String id, JsonRepresentation properties, final HaystackCallback<JsonRepresentation> callback) {
        StoreResourceProxy resourceProxy = GWT.create(StoreResourceProxy.class);
        resourceProxy.getClientResource().setReference(getServerRoot() + "accounts");
        resourceProxy.getClientResource().setQueryValue("type", type);
        resourceProxy.getClientResource().setQueryValue("id", id);
        ChallengeResponse cr = new ChallengeResponse(ChallengeScheme.HTTP_OAUTH_BEARER);
        cr.setRawValue(getClientToken());
        resourceProxy.getClientResource().setChallengeResponse(cr);
        resourceProxy.create(new JsonRepresentation(properties.toString()), new Result<JsonRepresentation>() {
            @Override
            public void onFailure(Throwable caught) {
                callback.failure(caught);
            }
            @Override
            public void onSuccess(JsonRepresentation result) {
                try {
                    callback.success(result);
                } catch (Exception e){

                }
            }
        });
    }

    @Override
    public void find(String type, String id, HaystackCallback<JsonRepresentation> callback) {

    }

    @Override
    public void findOrAdd(String type, String id, JsonRepresentation properties, HaystackCallback<Map<String, Object>> callback) {

    }

    @Override
    public void findAll(String type) {

    }

    @Override
    public void update(String type, String id, JsonRepresentation changedProperties, HaystackCallback<Map<String, Object>> callback) {

    }

    @Override
    public void updateAll(String type, String id, JsonRepresentation updateObject, HaystackCallback<Map<String, Object>> callback) {

    }

    @Override
    public void remove(String type, String id, HaystackCallback<Boolean> callback) {

    }

    @Override
    public void removeAll(String type, HaystackCallback<Boolean> callback) {

    }
}
