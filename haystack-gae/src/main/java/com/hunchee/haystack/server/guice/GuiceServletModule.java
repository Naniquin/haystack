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
package com.hunchee.haystack.server.guice;

import com.google.apphosting.utils.remoteapi.RemoteApiServlet;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.google.inject.Scopes;
import com.google.inject.servlet.ServletModule;
import org.restlet.ext.servlet.ServerServlet;

import java.util.Map;

/**
 * @author <a href="mailto:kerby@dotweblabs.com">Kerby Martino</a>
 * @version 1.0
 * @since 1.0
 */
public class GuiceServletModule extends ServletModule {
    @Override
    protected void configureServlets() {
        bind(RemoteApiServlet.class).in(Scopes.SINGLETON);
        bind(ServerServlet.class).in(Scopes.SINGLETON);
        serve("/_ah/remote_api").with(RemoteApiServlet.class);
        serve("/*").with(ServerServlet.class, map("org.restlet.application", "haystackApplication"));
    }
    private static Map<String,String> map(String... params) {
        Preconditions.checkArgument(params.length % 2 == 0, "You have to have a n even number of map params");
        Map<String,String> map = Maps.newHashMap();
        for (int i = 0; i < params.length; i+=2) {
            map.put(params[i], params[i+1]);
        }
        return map;
    }
}
