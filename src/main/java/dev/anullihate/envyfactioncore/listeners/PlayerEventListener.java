package dev.anullihate.envyfactioncore.listeners;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.*;
import cn.nukkit.utils.TextFormat;
import dev.anullihate.envyfactioncore.EnvyFactionCore;
import dev.anullihate.envyfactioncore.datamanagers.UserManager;
import dev.anullihate.envyfactioncore.dataobjects.users.UserProfile;
import dev.anullihate.envyfactioncore.guis.Gui;
import dev.anullihate.envyfactioncore.runnables.UserProfileLoadingRunnable;

public class PlayerEventListener implements Listener {

    private EnvyFactionCore core;

    public PlayerEventListener(EnvyFactionCore core) {
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
                .replace("%envy_faction%", EnvyFactionCore.generalAPI.getFactionTag(player))
                .replace("%msg%", message)
        );
        String msg = EnvyFactionCore.placeholderAPI.translateString(chatFormat, player);

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
        player.setMovementSpeed(0.1f);
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
