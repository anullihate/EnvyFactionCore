package dev.anullihate.envyfactioncore.runnables;

import cn.nukkit.Player;
import cn.nukkit.scheduler.NukkitRunnable;
import dev.anullihate.envyfactioncore.EnvyFactionCore;
import dev.anullihate.envyfactioncore.datamanagers.UserManager;
import dev.anullihate.envyfactioncore.dataobjects.users.UserProfile;
import dev.anullihate.envyfactioncore.datatables.UserTable;

import java.sql.SQLException;

public class UserProfileLoadingRunnable extends NukkitRunnable {

    private final Player player;

    public UserProfileLoadingRunnable(Player player) {
        this.player = player;
    }

    @Override
    public void run() {
        //
        if (!player.isOnline()) {
            EnvyFactionCore.getInstance().getLogger().info("Aborting profile loading recovery for " + player.getName() + " - player logged out");
            return;
        }

        // load profile
        try {
            UserTable user = EnvyFactionCore.userDao.queryForId(player.getName());

            if (user == null) {
                // create player

                // initial data
                UserProfile userProfileCreation = new UserProfile(player.getName(), 100);
                userProfileCreation.initial = true;
                UserManager.users.put(player.getName(), userProfileCreation);
            } else {
                // load player
                UserProfile loadUserProfile = UserProfile.loadUserProfile(player.getName());
                UserManager.users.put(player.getName(), loadUserProfile);
            }

            EnvyFactionCore.getInstance().getLogger().info(player.getName() + " data has been loaded!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
