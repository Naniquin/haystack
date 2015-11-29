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
package com.hunchee.dreamcode.server.resources.admin.gae;

import com.google.inject.Inject;
import com.hunchee.dreamcode.client.entity.Registration;
import com.hunchee.dreamcode.client.entity.User;
import com.hunchee.dreamcode.server.guice.SelfInjectingServerResource;
import com.hunchee.dreamcode.server.resources.admin.TestDataResource;
import com.hunchee.dreamcode.server.services.RegistrationService;
import com.hunchee.dreamcode.server.services.UserService;
import org.fluttercode.datafactory.impl.DataFactory;

/**
 * @author <a href="mailto:kerby@dotweblabs.com">Kerby Martino</a>
 * @version 1.0
 * @since 1.0
 */
public class GaeTestDataServerResource extends SelfInjectingServerResource
    implements TestDataResource {

    DataFactory df = new DataFactory();

    @Inject
    RegistrationService registrationService;

    @Inject
    UserService userService;

    @Override
    public void create() {
        registrationService.create(createTestRegistration());
        userService.create(createTestUser());
    }

    private Registration createTestRegistration(){
        Registration registration = new Registration();
        registration.setFirstName(df.getFirstName());
        registration.setLastName(df.getLastName());
        registration.setEmail(df.getEmailAddress());
        registration.setBirthDate(df.getBirthDate());
        registration.setPassword("$2a$10$rq/OQVs9sbG6.tiTdIyTZOdR8oPcIMHUxLRrUfEbddgXXMt6x3vUm"); // 12345678
        return registration;
    }

    private User createTestUser(){
        User user = User.createFrom(createTestRegistration());
        return user;
    }
}
