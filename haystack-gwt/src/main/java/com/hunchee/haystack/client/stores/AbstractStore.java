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
package com.hunchee.haystack.client.stores;

import com.hunchee.haystack.client.AbstractHaystack;
import com.hunchee.haystack.client.HaystackCallback;
import org.restlet.client.ext.json.JsonRepresentation;

import java.util.Map;

/**
 * @author <a href="mailto:kerby@dotweblabs.com">Kerby Martino</a>
 * @version 1.0
 * @since 1.0
 */
public abstract class AbstractStore extends AbstractHaystack {
    public abstract void add(String type, String id, JsonRepresentation properties, final HaystackCallback<JsonRepresentation> callback);
    public abstract void find(String type, String id, final HaystackCallback<JsonRepresentation> callback);
    public abstract void findOrAdd(String type, String id, JsonRepresentation properties, final HaystackCallback<Map<String,Object>> callback);
    public abstract void findAll(String type);
    public abstract void update(String type, String id, JsonRepresentation changedProperties, final HaystackCallback<Map<String,Object>> callback);
    public abstract void updateAll(String type, String id, JsonRepresentation updateObject, final HaystackCallback<Map<String,Object>> callback);
    public abstract void remove(String type, String id, final HaystackCallback<Boolean> callback);
    public abstract void removeAll(String type, final HaystackCallback<Boolean> callback);
}
