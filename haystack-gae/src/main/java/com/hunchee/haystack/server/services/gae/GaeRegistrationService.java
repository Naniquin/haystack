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
package com.hunchee.haystack.server.services.gae;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.inject.Inject;
import com.hunchee.haystack.client.Registration;
import com.hunchee.haystack.client.Registrations;
import com.hunchee.haystack.client.User;
import com.hunchee.twist.types.ListResult;
import com.hunchee.haystack.server.services.RegistrationService;
import com.hunchee.haystack.server.services.UserService;

import java.util.Date;
import java.util.logging.Logger;

import static com.hunchee.twist.ObjectStoreService.store;

/**
 * @author <a href="mailto:kerby@dotweblabs.com">Kerby Martino</a>
 * @version 1.0
 * @since 1.0
 */
public class GaeRegistrationService implements RegistrationService {

    private static final Logger LOG
            = Logger.getLogger(GaeRegistrationService.class.getName());

    @Inject
    UserService userService;

    @Override
    public Key create(Registration registration) {
        registration.setCreated(new Date());
        registration.setModified(new Date());
        return store().put(registration);
    }

    @Override
    public Registration read(Long id) {
        return store().get(Registration.class, id);
    }

    @Override
    public Registration read(Key key) {
        return store().get(Registration.class, key);
    }

    @Override
    public Registration read(String email) {
        Registration registration = store().get(Registration.class, email);
        if(registration != null){
            Key key = store().put(registration);
            registration.setToken(KeyFactory.keyToString(key));
        }
        return registration;
    }

    @Override
    public Key update(Registration registration) {
        registration.setModified(new Date());
        return store().put(registration);
    }

    @Override
    public void delete(String email) {
        store().delete(Registration.class, email);
    }

    @Override
    public Registrations list() {
        ListResult<Registration> result = store().find(Registration.class)
                .limit(100)
                .sortDescending("created")
                .asList();
        Registrations registrations = new Registrations();
        registrations.setRegistrations(result.getList());
        registrations.setCursor(result.getWebsafeCursor());
        registrations.setCount(Long.valueOf(result.getList().size()));
        return registrations;
    }

    @Override
    public String readToken(String email) {
        Registration registration = read(email);
        if(registration != null){
            return registration.getToken();
        }
        return null;
    }

    @Override
    public User validate(String registrationString) {
        LOG.info("Validating registration: " + registrationString);
        Key key = KeyFactory.stringToKey(registrationString);
        final Registration registration = store().get(Registration.class, key);
        if(registration != null && !checkExpired(registration)){
            User existing = userService.read(registration.getEmail());
            LOG.info("User for " + registration.getEmail() + " does not exist. Creating new User.");
            if(existing == null){
                User user = User.createFrom(registration);
                Key userKey = store().put(user);
                if(userKey != null){
                    delete(registration.getEmail());
                    return user;
                }
            }
        }
        LOG.info("Validation error");
        return null;
    }

    public boolean checkExpired(Registration registration){
        Long expiration = registration.getExpiration().getTime();
        Long now = new Date().getTime();
        return expiration < now;
    }
}
