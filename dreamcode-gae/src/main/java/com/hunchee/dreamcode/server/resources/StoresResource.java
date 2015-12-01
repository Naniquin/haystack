package com.hunchee.dreamcode.server.resources;

import org.restlet.ext.json.JsonRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;

import java.util.List;

/**
 * Created by K on 11/30/2015.
 */
public interface StoresResource {
    @Post
    public JsonRepresentation create(JsonRepresentation json);
    public JsonRepresentation list();
}
