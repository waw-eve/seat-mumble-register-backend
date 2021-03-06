package com.waw_eve.seat.mumble;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Random;

import com.waw_eve.seat.mumble.model.Configuration;

import org.junit.Test;

import Murmur.InvalidSecretException;

public class MumbleClientTests {

    @Test
    public void mumbleClientTest() {
        var mumbleClient = MumbleClient.getInstance();
        Configuration configuration = new Configuration();
        Config.init(configuration);
        try {
            mumbleClient.init();
        } catch (InvalidSecretException e) {
            e.printStackTrace();
        }
        Random random = new Random();
        assertNotNull(mumbleClient);
        assertTrue(mumbleClient.updateUser("test", "test@example.com", "89ec92bbfcbcf74204ba0775ff09bf399b2c19bd"));
        assertTrue(mumbleClient.updateUser("test" + random.nextInt(), "test" + random.nextInt() + "@example.com",
                "89ec92bbfcbcf74204ba0775ff09bf399b2c19bd"));
    }
}
