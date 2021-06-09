package com.waw_eve.seat.mumble;

import com.waw_eve.seat.mumble.model.Configuration;

public class Config {
    private static Configuration global;

    private Config() {
    }

    public static void init(Configuration config) {
        global = config;
    }

    public static Configuration getGlobalConfig() {
        return global;
    }
}
