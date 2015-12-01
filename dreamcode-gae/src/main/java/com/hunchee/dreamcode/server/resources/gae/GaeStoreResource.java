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
import com.google.inject.Inject;
import com.hunchee.dreamcode.client.GenericJson;
import com.hunchee.dreamcode.server.JSONHelper;
import com.hunchee.dreamcode.server.guice.SelfInjectingServerResource;
import com.hunchee.dreamcode.server.resources.StoreResource;
import com.hunchee.dreamcode.server.services.ShardedCounterService;
import com.hunchee.dreamcode.server.services.StoreService;
import com.hunchee.dreamcode.server.services.UserService;
import com.hunchee.dreamcode.server.services.WebTokenService;
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
public class GaeStoreResource extends SelfInjectingServerResource
    implements StoreResource {

    private static final Logger LOG
            = Logger.getLogger(GaeStoreResource.class.getName());

    ShardedCounterService shardCounterService;

    @Inject
    StoreService storeService;

    @Inject
    WebTokenService webTokenService;

    @Inject
    UserService userService;

    String clientToken;

    String storeId;

    @Override
    public GenericJson retrieve() {
        return null;
    }
}
