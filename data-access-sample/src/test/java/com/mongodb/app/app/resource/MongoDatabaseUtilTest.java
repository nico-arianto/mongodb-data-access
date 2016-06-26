package com.mongodb.app.app.resource;

import com.mongodb.app.resource.MongoDatabaseUtil;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertNotNull;

/**
 * Mongo database utility - unit test.
 *
 * @author nico.arianto
 */
public class MongoDatabaseUtilTest {

    /**
     * Test getDatabase() method.
     */
    @Test
    public void testGetDatabase() {
        assertNotNull(MongoDatabaseUtil.getDatabase());
    }
}