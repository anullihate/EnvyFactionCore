package dev.anullihate.envyfactioncore.datamanagers;

import dev.anullihate.envyfactioncore.dataobjects.users.UserProfile;

import java.util.HashMap;
import java.util.Map;

public class UserManager {

    public static Map<String, UserProfile> users = new HashMap<>();

    private UserManager() {}

    public static void track() {}

    public static void saveAll() {
        for (UserProfile userProfile : users.values()) {
            userProfile.saveUserProfile();
        }
    }

    public static Map<String, UserProfile> getUsers() {
        return users;
    }

    public static UserProfile getUser(String playerName) {
        return users.get(playerName);
    }
}
