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

import com.hunchee.haystack.server.guice.SelfInjectingServerResource;
import org.restlet.ext.servlet.ServletUtils;
import org.restlet.resource.Get;

import javax.servlet.http.HttpServletRequest;

/**
 * @author <a href="mailto:kerby@dotweblabs.com">Kerby Martino</a>
 * @version 1.0
 * @since 1.0
 */
public class GaeRootServerResource extends SelfInjectingServerResource {
    @Get("json")
    public String represent() {
        String client = getRequest().getClientInfo().getAddress();
        org.restlet.Request restletRequest = getRequest();
        HttpServletRequest servletRequest = ServletUtils.getRequest(restletRequest);
        String localName = servletRequest.getLocalName();
        String path = getRequest().getResourceRef().getHostIdentifier() +
                getRequest().getResourceRef().getPath();
        return "All system is up, your client IP: " + client;
    }
}