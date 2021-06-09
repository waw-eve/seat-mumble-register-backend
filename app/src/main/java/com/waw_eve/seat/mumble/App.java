/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package com.waw_eve.seat.mumble;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.waw_eve.seat.mumble.http.*;
import com.waw_eve.seat.mumble.model.Configuration;
import com.waw_eve.seat.mumble.utils.CertUtil;
import com.waw_eve.seat.mumble.utils.CryptUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import Murmur.InvalidSecretException;

public class App {
    private static final Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        var configPath = "config.json";
        var configFile = new File(configPath);
        var gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
        var configuration = new Configuration();
        if (configFile.exists()) {
            try {
                var reader = new JsonReader(new FileReader(configFile));
                configuration = gson.fromJson(reader, Configuration.class);
            } catch (FileNotFoundException e) {
                logger.error("Failed to read config file.", e);
            }
        } else {
            logger.warn("Config file is not exist, load default config.");
            try {
                if (configFile.createNewFile()) {
                    try (var writer = new FileWriter(configFile)) {
                        writer.write(gson.toJson(configuration));
                    }
                }
            } catch (IOException e) {
                logger.error("Failed to save config.", e);
            }
        }
        Config.init(configuration);
        CertUtil.init();
        CryptUtil.init();
        try {
            MumbleClient.getInstance().init();
        } catch (InvalidSecretException e) {
            logger.error("Failed to init mumble client.", e);
        }
        var server = new HttpServer(80);
        try {
            server.start();
        } catch (InterruptedException e) {
            logger.error("Failed to start server.", e);
            Thread.currentThread().interrupt();
        }
    }
}
