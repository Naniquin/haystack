package com.textquo.dreamcode.client;

import com.google.gwt.junit.client.GWTTestCase;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DreamcodeTest extends GWTTestCase {

    @Before
    public void prepareTests(){

    }

    @After
    public void afterTests() {

    }

    @Override
    public String getModuleName() {
        return "com.textquo.dreamcode.DreamcodeTestEntryPoint";
    }

    @Test
    public void test(){
        Assert.assertTrue(true);
    }
}
