package com.waw_eve.seat.mumble;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class ConfigurationTests {

    @Test
    public void configurationTest() {
        var configuration = new Configuration();
        assertNotNull(configuration);
    }
}
