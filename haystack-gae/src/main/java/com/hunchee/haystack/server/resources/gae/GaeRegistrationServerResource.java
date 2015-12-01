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

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.inject.Inject;
import com.hunchee.haystack.client.Registration;
import com.hunchee.haystack.client.User;
import com.hunchee.haystack.server.guice.SelfInjectingServerResource;
import com.hunchee.haystack.server.resources.RegistrationResource;
import com.hunchee.haystack.server.services.*;
import org.restlet.data.Status;

import java.util.logging.Logger;

/**
 * @author <a href="mailto:kerby@gmail.com">Kerby Martino</a>
 * @author <a href="mailto:loucar.mendoza@gmail.com">Loucar Mendoza</a>
 * @version 1.0
 * @since 1.0
 */
public class GaeRegistrationServerResource extends SelfInjectingServerResource
    implements RegistrationResource {

    private static final Logger LOG = Logger.getLogger(GaeRegistrationServerResource.class.getName());

    @Inject
    RegistrationService service;

    @Inject
    UserService userService;

    @Inject
    EmailService emailService;

    String registrationString;
    String isForgot;

    @Override
    protected void doInit() {
        super.doInit();
        registrationString = (String) getRequest().getAttributes().get("token");
    }

    @Override
    public void validate() {
        LOG.info("Registration string: " + registrationString);
        if(registrationString == null || registrationString.isEmpty()){
            setStatus(Status.CLIENT_ERROR_BAD_REQUEST, "Invalid registration token");
            return;
        }
        Key key = KeyFactory.stringToKey(registrationString);
        if(key == null){
            setStatus(Status.CLIENT_ERROR_BAD_REQUEST, "Invalid registration token");
            return;
        }
        Registration registration = service.read(key);
        LOG.info("Registration: " + registration);
        if(Boolean.parseBoolean(isForgot) && registration != null){
            User user = userService.read(registration.getEmail());
            if(user == null){
                setStatus(Status.CLIENT_ERROR_BAD_REQUEST, "User doesn't exist");
            } else {
                user.setPasswordHash(registration.getPassword());
                userService.update(user);
                service.delete(registration.getEmail());
            }
        }else if(registration != null && service.validate(registrationString) != null){
            setStatus(Status.SUCCESS_OK, "Validation complete");
            // OR redirect
        } else {
            setStatus(Status.CLIENT_ERROR_BAD_REQUEST, "Invalid token");
        }
    }

}
