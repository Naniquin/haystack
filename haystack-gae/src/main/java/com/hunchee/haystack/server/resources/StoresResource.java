package com.hunchee.haystack.server.resources;

import org.restlet.ext.json.JsonRepresentation;
import org.restlet.resource.Post;

/**
 * Created by K on 11/30/2015.
 */
public interface StoresResource {
    @Post
    public JsonRepresentation create(JsonRepresentation json);
    public JsonRepresentation list();
}
