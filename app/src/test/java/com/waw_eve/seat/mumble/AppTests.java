package com.waw_eve.seat.mumble;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class AppTests {

    @Test
    public void appTest() {
        var app = new App();
        assertNotNull(app);
    }
}
