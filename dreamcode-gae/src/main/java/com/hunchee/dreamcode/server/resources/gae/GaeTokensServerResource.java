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

import com.google.inject.Inject;
import com.hunchee.dreamcode.client.User;
import com.hunchee.dreamcode.server.guice.SelfInjectingServerResource;
import com.hunchee.dreamcode.server.resources.TokensResource;
import com.hunchee.dreamcode.server.services.UserService;
import com.hunchee.dreamcode.server.services.WebTokenService;
import com.hunchee.dreamcode.server.utils.Base64;
import org.mindrot.jbcrypt.BCrypt;
import org.restlet.data.Status;
import org.restlet.util.Series;

import java.util.logging.Logger;

/**
 * @author <a href="mailto:kerby@dotweblabs.com">Kerby Martino</a>
 * @version 1.0
 * @since 1.0
 */
public class GaeTokensServerResource extends SelfInjectingServerResource
    implements TokensResource {

    private static final Logger LOG
            = Logger.getLogger(GaeTokensServerResource.class.getName());


    @Inject
    UserService userService;

    @Inject
    WebTokenService webTokenService;

    String email;
    String password;
    String authValue;

    @Override
    protected void doInit() {
        super.doInit();
        email = getQueryValue("email");
        password = getQueryValue("password");
        Series headers = (Series) getRequestAttributes().get("org.restlet.http.headers");
        authValue = headers.getFirstValue("Authorization").replaceFirst("Basic ","");
        String decoded = Base64.decode(authValue);
        String[] emailPasswordPair = decoded.split(":");
        email = (email == null) ? emailPasswordPair[0] : email;
        password = (password == null) ? emailPasswordPair[1] : password;
    }

    @Override
    public User verify() {
        //LOG.info("Authorization: " + authValue);
        if(email != null
                && password != null
                && !email.isEmpty()
                && !password.isEmpty()){
            User user = userService.read(email);
            if(user != null){
                String passwordHash = user.getPasswordHash();
                LOG.info("Password: " + password);
                if(BCrypt.checkpw(password, passwordHash)){
                    //user.setClientToken(webTokenService.createToken(user.getId()));
                    return user;
                } else {
                    setStatus(Status.CLIENT_ERROR_UNAUTHORIZED, "Email and/or password is invalid");
                }
            } else {
                setStatus(Status.CLIENT_ERROR_NOT_FOUND, "User with email " + email + " not found");
                return null;
            }
        } else {
            setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
        }
        return null;
    }
}
