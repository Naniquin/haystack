package com.textquo.dreamcode.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Window;

public class DreamcodeTestEntryPoint implements EntryPoint {
    public void onModuleLoad() {
        Dreamcode dreamcode = new Dreamcode();
        dreamcode.globalStore().add("Test", "null", "{'content' : 'test'}", new DreamcodeCallback() {
            public void success(Object result) {
                log("Globalstore add() Callback Success: " + result);

            }
            public void failure(Throwable e) {
                log("Globalstore add() Callback Fail: " + e.getMessage());
            }
        });
        dreamcode.globalStore().find("", "", new DreamcodeCallback() {
            public void success(Object result) {
                log("Globalstore find() Callback Success: " + result);
            }
            public void failure(Throwable e) {
                log("Globalstore find() Callback Fail: " + e.getMessage());
            }
        });
    }
    public static native void log(String message)/*-{
        $wnd.console.log(message);
    }-*/;
}
