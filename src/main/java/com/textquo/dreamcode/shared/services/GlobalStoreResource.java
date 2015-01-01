package com.textquo.dreamcode.shared.services;

import com.textquo.dreamcode.shared.entities.DreamObject;
import org.restlet.client.resource.Get;
import org.restlet.client.resource.Post;
import org.restlet.client.resource.Put;
import org.restlet.resource.Delete;

import java.util.Map;
import java.util.Objects;

public interface GlobalStoreResource {

    @Post
    void add(DreamObject obj);

    @Get
    DreamObject find(String type, String id);

    @Put
    void update(DreamObject obj);

    @Delete
    void remove(String type, String id);

}
