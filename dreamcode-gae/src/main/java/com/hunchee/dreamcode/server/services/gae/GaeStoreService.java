package com.hunchee.dreamcode.server.services.gae;

import com.hunchee.dreamcode.client.GenericJson;
import com.hunchee.dreamcode.server.services.StoreService;

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
