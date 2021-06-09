package com.waw_eve.seat.mumble;

import static org.junit.Assert.assertNotNull;

import com.waw_eve.seat.mumble.model.Configuration;

import org.junit.Test;

public class ConfigTests {

    @Test
    public void configurationTest() {
        var configuration = new Configuration();
        Config.init(configuration);
        assertNotNull(Config.getGlobalConfig());
    }
}
