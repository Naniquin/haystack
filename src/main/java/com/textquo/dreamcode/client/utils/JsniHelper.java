package com.textquo.dreamcode.client.utils;

public class JsniHelper {
    public static native void consoleLog(String message)/*-{
        $wnd.console.log(message);
    }-*/;
}
