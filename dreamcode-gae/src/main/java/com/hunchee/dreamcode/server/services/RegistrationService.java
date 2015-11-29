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
package com.hunchee.dreamcode.server.services;

import com.google.appengine.api.datastore.Key;
import com.hunchee.dreamcode.client.Registration;
import com.hunchee.dreamcode.client.Registrations;
import com.hunchee.dreamcode.client.User;

/**
 * @author <a href="mailto:kerby@dotweblabs.com">Kerby Martino</a>
 * @version 1.0
 * @since 1.0
 */
public interface RegistrationService {
    public Key create(Registration registration);
    public Registration read(Long id);
    public Registration read(Key key);
    public Registration read(String email);
    public Key update(Registration registration);
    public void delete(String email);
    public Registrations list();
    public String readToken(String email);
    public User validate(String token);
}
