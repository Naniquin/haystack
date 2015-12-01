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

import com.hunchee.twist.types.ListResult;
import com.hunchee.haystack.client.User;
import com.hunchee.haystack.client.Users;
import com.hunchee.haystack.server.services.UserService;

import java.util.Date;
import java.util.NoSuchElementException;

import static com.hunchee.twist.ObjectStoreService.store;

/**
 * @author <a href="mailto:kerby@dotweblabs.com">Kerby Martino</a>
 * @version 1.0
 * @since 1.0
 */
public class GaeUserService implements UserService {

    @Override
    public void create(User user) {
        store().put(user);
    }

    @Override
    public User read(Long id) {
        return store().get(User.class, id);
    }

    @Override
    public User read(String username) {
        try {
            User user = (User) store().find(User.class)
                    .equal("username", username).now().next();
            return user;
        } catch (NoSuchElementException e){
            //e.printStackTrace();
            // do nothing just return null
        }
        return null;
    }

    @Override
    public void update(User user) {
        User storedUser = (User) store().get(User.class,user.getId());
        storedUser.setPasswordHash(user.getPasswordHash());
        storedUser.setModified(new Date());
        store().put(storedUser);
    }

    @Override
    public void delete(Long id) {
        store().delete(User.class, id);
    }

    @Override
    public Users list() {
        ListResult<User> result = store().find(User.class)
                .limit(100)
                .sortDescending("created")
                .asList();
        Users users = new Users();
        users.setUsers(result.getList());
        users.setCursor(result.getWebsafeCursor());
        users.setCount(Long.valueOf(result.getList().size()));
        return users;
    }
}
