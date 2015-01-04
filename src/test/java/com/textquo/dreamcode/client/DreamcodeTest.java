package com.textquo.dreamcode.client;

import com.google.gwt.junit.client.GWTTestCase;
import junit.framework.Assert;

public class DreamcodeTest extends GWTTestCase {
    @Override
    public String getModuleName() {
        return "com.textquo.dreamcode.Dreamcode";
    }
    @Override
    protected void gwtSetUp() throws Exception {
    }
    @Override
    protected void gwtTearDown() throws Exception {
    }
    public void test(){
        Assert.assertTrue(true);
    }
}
