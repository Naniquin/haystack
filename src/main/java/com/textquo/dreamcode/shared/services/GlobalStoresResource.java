package com.textquo.dreamcode.shared.services;

import com.textquo.dreamcode.shared.entities.DreamObject;
import org.restlet.client.resource.Get;

public interface GlobalStoresResource {
    @Get
    DreamObject findAll(String type, String id);
}
