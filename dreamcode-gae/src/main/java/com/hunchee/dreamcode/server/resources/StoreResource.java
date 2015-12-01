package com.hunchee.dreamcode.server.resources;

import org.restlet.ext.json.JsonRepresentation;
import org.restlet.resource.Get;

/**
 * Created by K on 11/30/2015.
 */
public interface StoreResource {
    @Get
    public JsonRepresentation retrieve();
}
