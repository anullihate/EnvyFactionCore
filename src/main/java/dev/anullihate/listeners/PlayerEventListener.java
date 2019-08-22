package dev.anullihate.listeners;

import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.event.player.PlayerJoinEvent;
import dev.anullihate.EnvyGamesCore;
import dev.anullihate.guis.Gui;

public class PlayerEventListener implements Listener {

    private EnvyGamesCore plugin;

    public PlayerEventListener(EnvyGamesCore plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {

    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerFormResponse(PlayerFormRespondedEvent event) {
        if (event.getResponse() == null) return;
        if (!(event.getWindow() instanceof Gui)) return;

        ((Gui)event.getWindow()).onPlayerFormResponse(event);
    }
}
