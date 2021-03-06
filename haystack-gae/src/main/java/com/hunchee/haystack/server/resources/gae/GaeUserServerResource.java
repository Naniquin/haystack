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

import com.google.inject.Inject;
import com.hunchee.haystack.client.User;
import com.hunchee.haystack.server.guice.SelfInjectingServerResource;
import com.hunchee.haystack.server.resources.UserResource;
import com.hunchee.haystack.server.services.UserService;
import com.hunchee.haystack.server.services.WebTokenService;
import org.restlet.data.Status;

import java.util.Date;
import java.util.logging.Level;

import static com.hunchee.twist.ObjectStoreService.store;

/**
 * @author <a href="mailto:kerby@dotweblabs.com">Kerby Martino</a>
 * @version 1.0
 * @since 1.0
 */
public class GaeUserServerResource extends SelfInjectingServerResource
        implements UserResource {

    @Inject
    UserService service;

    @Inject
    WebTokenService webTokenService;

    String clientToken;
    String userId;

    @Override
    protected void doInit() {
        super.doInit();
        clientToken = getQueryValue("client_token");
        userId = (String) getRequest().getAttributes().get("user_id");
        getLogger().log(Level.INFO,"userid sent: " + userId);
        getLogger().log(Level.INFO,"clientToken sent: " + clientToken);
    }


    @Override
    public User retrieve() {
        Long userId = this.userId == null || this.userId.isEmpty() ? webTokenService.readUserIdFromToken(clientToken) : Long.valueOf(this.userId);
        if(userId != null){
            if(this.userId != null){
                if(!userId.equals(Long.valueOf(this.userId))){
                    setStatus(Status.CLIENT_ERROR_UNAUTHORIZED, "Cannot access other user");
                    return null;
                }
            }
            User user = service.read(userId);
            //user.setClientToken(webTokenService.createToken(user.getId()));
            return user;
        }else {
            setStatus(Status.CLIENT_ERROR_UNAUTHORIZED);
        }
        return null;
    }

    @Override
    public void create(User user) {
        Long userId = webTokenService.readUserIdFromToken(clientToken);
        if(userId != null){
            user.setId(userId);
            user.setCreated(new Date());
            user.setModified(new Date());
            store().put(user);
        }else {
            setStatus(Status.CLIENT_ERROR_UNAUTHORIZED);
        }
    }

    @Override
    public void remove() {
        if(!isAdmin()){
            setStatus(Status.CLIENT_ERROR_UNAUTHORIZED);
            return;
        }
    }

    private boolean isAdmin(){
        return false;
    }
}
