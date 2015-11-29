package com.hunchee.dreamcode.client;

import com.google.gwt.junit.tools.GWTTestSuite;
import com.hunchee.dreamcode.client.utils.TestJsonConverter;
import junit.framework.Test;
import junit.framework.TestSuite;

public class DreamcodeTestSuite extends GWTTestSuite {
    public static Test suite() {
        TestSuite suite = new TestSuite("Test for a Dreamcode GWT Library");
        suite.addTestSuite(DreamcodeTest.class);
        suite.addTestSuite(TestJsonConverter.class);
        return suite;
    }
}