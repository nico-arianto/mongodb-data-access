package com.mongodb.app.app.resource;

import com.mongodb.MongoClientException;
import com.mongodb.app.resource.MongoDatabaseUtil;
import mockit.Deencapsulation;
import mockit.Expectations;
import mockit.Mocked;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static org.testng.AssertJUnit.assertNotNull;

/**
 * Mongo database utility - integration test.
 *
 * @author nico.arianto
 */
public class MongoDatabaseUtilIT {

    /**
     * Test getDatabase() method.
     */
    @Test
    public void testGetDatabase() {
        assertNotNull(MongoDatabaseUtil.getDatabase());
    }

    /**
     * Test getDatabase() method throw {@link java.io.IOException}.
     *
     * @param properties properties
     * @throws IOException
     */
    @Test(expectedExceptions = MongoClientException.class, expectedExceptionsMessageRegExp = "Failed to load .* file.")
    public void testGetDatabaseThrowIOException(@Mocked final Properties properties) throws IOException {
        new Expectations() {
            {
                properties.load(withInstanceOf(InputStream.class));
                result = new IOException();
            }
        };
        MongoDatabaseUtil.getDatabase();
    }

    /**
     * Test private constructor.
     */
    @Test
    public void testConstructor() {
        Deencapsulation.newInstance(MongoDatabaseUtil.class);
    }
}