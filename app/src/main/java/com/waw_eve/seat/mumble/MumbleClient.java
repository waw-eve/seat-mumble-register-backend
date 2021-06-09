package com.waw_eve.seat.mumble;

import java.util.EnumMap;
import java.util.Map;

import com.zeroc.Ice.InitializationData;
import com.zeroc.Ice.Util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import Murmur.InvalidSecretException;
import Murmur.InvalidUserException;
import Murmur.MetaPrx;
import Murmur.ServerBootedException;
import Murmur.ServerPrx;
import Murmur.UserInfo;

public class MumbleClient {
    private static final Logger logger = LoggerFactory.getLogger(MumbleClient.class);
    private static MumbleClient instance;

    private ServerPrx[] servers;

    private MumbleClient() {
    }

    public void init() throws InvalidSecretException {
        logger.info("Initializing mumble client...");
        var configuration = Config.getGlobalConfig();
        var properties = Util.createProperties();
        properties.setProperty("Ice.Default.EncodingVersion", "1.0");
        properties.setProperty("Ice.ImplicitContext", "Shared");
        var initData = new InitializationData();
        initData.properties = properties;
        var communicator = Util.initialize(initData);
        communicator.getImplicitContext().put("secret", configuration.getMumbleIceSecret());
        var proxy = communicator.stringToProxy("Meta:" + configuration.getMumbleIceAddr());
        var meta = MetaPrx.checkedCast(proxy);
        servers = meta.getAllServers();
        logger.info("The mumble client is initialized.");
    }

    public static MumbleClient getInstance() {
        if (instance == null) {
            instance = new MumbleClient();
        }
        return instance;
    }

    public boolean updateUser(String userName, String userEmail, String certHash) {
        var successForAll = true;
        for (ServerPrx serverPrx : servers) {
            if (!updateUser(userName, userEmail, certHash, serverPrx)) {
                successForAll = false;
            }
        }
        return successForAll;
    }

    private boolean updateUser(String userName, String userEmail, String certHash, ServerPrx serverPrx) {
        try {
            var userMap = serverPrx.getRegisteredUsers(userName);
            var userId = -2;
            for (var user : userMap.entrySet()) {
                if (user.getValue().equals(userName)) {
                    userId = user.getKey();
                    break;
                }
            }
            Map<UserInfo, String> userInfo = new EnumMap<>(UserInfo.class);
            if (userId == -2) {
                userInfo.put(UserInfo.UserName, userName);
                userInfo.put(UserInfo.UserEmail, userEmail);
                userInfo.put(UserInfo.UserComment, "registed by SeAT");
                userInfo.put(UserInfo.UserHash, certHash);
                return serverPrx.registerUser(userInfo) > 0;
            } else {
                userInfo.put(UserInfo.UserHash, certHash);
                serverPrx.updateRegistration(userId, userInfo);
                return true;
            }
        } catch (InvalidSecretException | InvalidUserException | ServerBootedException e) {
            logger.error("Update user failed", e);
            return false;
        }
    }

}
