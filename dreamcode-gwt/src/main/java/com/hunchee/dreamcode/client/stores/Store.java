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
package com.hunchee.dreamcode.client.stores;

import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.hunchee.dreamcode.client.Routes;
import com.hunchee.dreamcode.client.DreamcodeCallback;
import org.restlet.client.Request;
import org.restlet.client.Response;
import org.restlet.client.Uniform;
import org.restlet.client.data.MediaType;
import org.restlet.client.data.Status;
import org.restlet.client.resource.ClientResource;
import java.util.Map;

/**
 * @author <a href="mailto:kerby@dotweblabs.com">Kerby Martino</a>
 * @version 1.0
 * @since 1.0
 */
public class Store extends AbstractStore {
    @Override
    public void add(String type, String id, Map<String, Object> properties, final DreamcodeCallback<Boolean> callback) {
        String url = Routes.DREAMCODE_API + Routes.PUBLIC_STORES_API + "?type=" + type + "&id=" + id;
        ClientResource resource = new ClientResource(url);
        resource.setOnResponse(new Uniform() {
            public void handle(Request request, Response response) {
                try {
                    Status status = response.getStatus();
                    if (!Status.isError(status.getCode())) {
                        String jsonResponse = response.getEntity().getText();
                        callback.success(false);
                    } else {
                        callback.failure(new Throwable("Error: " + status.getCode()));
                    }
                } catch (Exception e) {
                    callback.failure(new Throwable(e.getMessage()));
                }
            }
        });
    }

    @Override
    public void find(String type, String id, DreamcodeCallback<Map<String, Object>> callback) {

    }

    @Override
    public void findOrAdd(String type, String id, Map<String, Object> properties, DreamcodeCallback<Map<String, Object>> callback) {

    }

    @Override
    public void findAll(String type) {

    }

    @Override
    public void update(String type, String id, Map<String, Object> changedProperties, DreamcodeCallback<Map<String, Object>> callback) {

    }

    @Override
    public void updateAll(String type, String id, Map<String, Object> updateObject, DreamcodeCallback<Map<String, Object>> callback) {

    }

    @Override
    public void remove(String type, String id, DreamcodeCallback<Boolean> callback) {

    }

    @Override
    public void removeAll(String type, DreamcodeCallback<Boolean> callback) {

    }
}
