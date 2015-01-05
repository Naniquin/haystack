package com.textquo.dreamcode;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import junit.framework.TestCase;

public class LocalDatastoreTest extends TestCase {
    private final LocalServiceTestHelper helper =
            new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());

    @Override
    public void setUp() throws Exception {
        helper.setUp();
    }

    @Override
    public void tearDown() throws Exception {
        helper.tearDown();
    }

    //@Test
    public void testGWTLibrary() {

    }
}