package com.hunchee.haystack.client.utils;

import com.google.gwt.junit.client.GWTTestCase;

import java.util.LinkedHashMap;
import java.util.Map;

public class TestJsonConverter extends GWTTestCase {
    @Override
    public String getModuleName() {
        return "com.textquo.haystack.haystack";
    }
    @Override
    protected void gwtSetUp() throws Exception {
    }
    @Override
    protected void gwtTearDown() throws Exception {
    }

    public void testEncode() {
        Map<String,Object> toEncode = new LinkedHashMap<String,Object>();
        toEncode.put("key1", null);
        toEncode.put("key2", true);
        toEncode.put("key3", 1);
        toEncode.put("key4", "2");
        String json = JsonConverter.encode(toEncode).toString();
        assertEquals("{\"key1\":null, \"key2\":true, \"key3\":1, \"key4\":\"2\"}", json);
    }

    public void testDecode() {
        Map<String,Object> decoded = JsonConverter.decode("{\"key1\":null, \"key2\":true, \"key3\":1, \"key4\":\"2\"}");
        assertNull(decoded.get("key1"));
        assertEquals(true, decoded.get("key2"));
        assertEquals(1.0, decoded.get("key3"));
        assertEquals("2", decoded.get("key4"));
    }
}
