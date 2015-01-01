package com.textquo.dreamcode.client;

import com.google.gwt.junit.tools.GWTTestSuite;
import junit.framework.Test;
import junit.framework.TestSuite;

public class DreamcodeTestSuite extends GWTTestSuite {
    public static Test suite() {
        TestSuite suite = new TestSuite("Test for a Dreamcode GWT Library");
        suite.addTestSuite(DreamcodeTest.class);
        return suite;
    }
}