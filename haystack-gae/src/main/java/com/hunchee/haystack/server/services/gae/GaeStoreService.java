package com.hunchee.haystack.server.services.gae;

import com.hunchee.haystack.client.GenericJson;
import com.hunchee.haystack.server.services.StoreService;

import static com.hunchee.twist.ObjectStoreService.store;

/**
 * Created by K on 11/30/2015.
 */
public class GaeStoreService implements StoreService {
    @Override
    public void add(GenericJson json) {
        store().put(json);
    }

    @Override
    public void update(GenericJson json) {
        store().put(json);
    }

    @Override
    public GenericJson get(String id) {
        return null;
    }
}
