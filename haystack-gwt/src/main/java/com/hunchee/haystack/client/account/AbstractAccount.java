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
package com.hunchee.haystack.client.account;

import com.hunchee.haystack.client.AbstractHaystack;
import com.hunchee.haystack.client.HaystackCallback;

/**
 * @author <a href="mailto:kerby@dotweblabs.com">Kerby Martino</a>
 * @version 1.0
 * @since 1.0
 */
public abstract class AbstractAccount extends AbstractHaystack {
    public abstract void signUp(String username, String password, final HaystackCallback<Boolean> callback);
    public abstract void signIn(String username, String password, final HaystackCallback<String> callback);
    public abstract void signOut(HaystackCallback<Void> callback);
    public abstract void changePassword(String newPassword, String currentPassword, HaystackCallback<Boolean> callback);
    public abstract void changeUsername(String newUsername, String currentPassword, HaystackCallback<Boolean> callback);
    public abstract void resetPassword(String currentUsername, HaystackCallback<Boolean> callback);
    public abstract void destroy(HaystackCallback<Boolean> callback);
    public abstract void get(String field, HaystackCallback<String> callback);
    public abstract void change(String field, String newValue, HaystackCallback<Boolean> callback);
}
