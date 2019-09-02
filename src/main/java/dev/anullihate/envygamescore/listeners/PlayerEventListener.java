package dev.anullihate.envygamescore.listeners;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.*;
import cn.nukkit.utils.TextFormat;
import dev.anullihate.envygamescore.EnvyGamesCore;
import dev.anullihate.envygamescore.datamanagers.UserManager;
import dev.anullihate.envygamescore.dataobjects.users.UserProfile;
import dev.anullihate.envygamescore.datatables.UserTable;
import dev.anullihate.envygamescore.guis.Gui;
import dev.anullihate.envygamescore.runnables.UserProfileLoadingRunnable;

import java.sql.SQLException;

public class PlayerEventListener implements Listener {

    private EnvyGamesCore core;

    public PlayerEventListener(EnvyGamesCore core) {
        this.core = core;
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerChat(PlayerChatEvent event) {
        Player player = event.getPlayer();
//        PlaySoundPacket pk = new PlaySoundPacket();
//        pk.name = Sound.NOTE_HARP.getSound();
//        pk.volume = 1;
//        pk.pitch = 1;
//        pk.x = (int) player.x;
//        pk.y = (int) player.y;
//        pk.z = (int) player.z;
//
//        player.dataPacket(pk);

        String name = player.getName();
        String message = event.getMessage();

        String chatFormat = (this.core.getConfig().getString("chat.format")
                .replace("%envy_faction%", EnvyGamesCore.generalAPI.getFactionTag(player))
                .replace("%msg%", message)
        );
        String msg = EnvyGamesCore.placeholderAPI.translateString(chatFormat, player);

        event.setFormat(TextFormat.colorize('&', msg));
    }

    @EventHandler
    public void onPlayerInvalidMoveEvent(PlayerInvalidMoveEvent event) {
        event.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerFormResponse(PlayerFormRespondedEvent event) {
        if (!(event.getWindow() instanceof Gui)) return;

        ((Gui) event.getWindow()).onPlayerFormResponse(event);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        new UserProfileLoadingRunnable(player).runTaskAsynchronously(this.core);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        UserProfile userProfile = UserManager.getUser(player.getName());
        if (userProfile.isLoaded()) {
            userProfile.saveUserProfile();
        }

        UserManager.users.remove(player.getName());
    }
}
