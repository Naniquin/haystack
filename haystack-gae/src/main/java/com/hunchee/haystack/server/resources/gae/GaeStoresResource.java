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
package com.hunchee.haystack.server.resources.gae;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;
import com.hunchee.haystack.client.GenericJson;
import com.hunchee.haystack.client.User;
import com.hunchee.haystack.server.guice.SelfInjectingServerResource;
import com.hunchee.haystack.server.resources.StoresResource;
import com.hunchee.haystack.server.services.StoreService;
import com.hunchee.haystack.server.services.UserService;
import com.hunchee.haystack.server.services.WebTokenService;
import com.hunchee.haystack.server.utils.JSONUtil;
import org.json.JSONObject;
import org.restlet.data.Status;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.util.Series;

import java.util.Map;

/**
 * @author <a href="mailto:kerby@dotweblabs.com">Kerby Martino</a>
 * @version 1.0
 * @since 1.0
 */
public class GaeStoresResource extends SelfInjectingServerResource
    implements StoresResource {

    @Inject
    StoreService storeService;

    @Inject
    WebTokenService webTokenService;

    @Inject
    UserService userService;

    String clientToken;

    String type;

    String id;

    @Override
    protected void doInit() {
        super.doInit();
        Series headers = (Series) getRequestAttributes().get("org.restlet.http.headers");
        clientToken = headers.getFirstValue("Authorization").replaceFirst("Bearer ","");
    }

    @Override
    public JsonRepresentation create(JsonRepresentation representation) {
        String id = getQueryValue("id");
        String type = getQueryValue("type");
        try {
            Preconditions.checkNotNull(id, "Id cannot be null");
            Preconditions.checkNotNull(id, "Type cannot be null");
            Long longId = Long.valueOf(id);
            Long userId = webTokenService.readUserIdFromToken(clientToken);
            User user = userService.read(userId);
            Preconditions.checkNotNull(user, "No associated with token given: " + clientToken);
            JSONObject object = representation.getJsonObject();
            Map map = JSONUtil.parse(object);
            GenericJson persistence  = new GenericJson(longId, type, map, user);
            storeService.add(persistence);
        } catch (NumberFormatException e){
            setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
        } catch (NullPointerException e){
            setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
            e.printStackTrace();
        } catch (Exception e){
            setStatus(Status.SERVER_ERROR_INTERNAL);
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public JsonRepresentation list() {
        return null;
    }

}
