package com.hunchee.haystack.client;

import com.google.gwt.junit.tools.GWTTestSuite;
import com.hunchee.haystack.client.utils.TestJsonConverter;
import junit.framework.Test;
import junit.framework.TestSuite;

public class HaystackTestSuite extends GWTTestSuite {
    public static Test suite() {
        TestSuite suite = new TestSuite("Test for a haystack GWT Library");
        suite.addTestSuite(HaystackTest.class);
        suite.addTestSuite(TestJsonConverter.class);
        return suite;
    }
}