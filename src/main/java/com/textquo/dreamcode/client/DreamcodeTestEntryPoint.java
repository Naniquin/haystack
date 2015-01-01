package com.textquo.dreamcode.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Window;

public class DreamcodeTestEntryPoint implements EntryPoint {
    public void onModuleLoad() {
        Dreamcode dreamcode = new Dreamcode();
        dreamcode.globalStore().find("", "", new DreamcodeCallback() {
            public void success(Object result) {
                Window.alert("Success");
            }
            public void failure(Throwable e) {
                Window.alert("Fail");
            }
        });
    }
}
