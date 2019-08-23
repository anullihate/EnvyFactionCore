package dev.anullihate.envygamescore.listeners;

import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.event.player.PlayerInvalidMoveEvent;
import dev.anullihate.envygamescore.EnvyGamesCore;
import dev.anullihate.envygamescore.guis.Gui;

public class PlayerEventListener implements Listener {

    private EnvyGamesCore core;

    public PlayerEventListener(EnvyGamesCore core) {
        this.core = core;
    }

    @EventHandler
    public void onPlayerInvalidMoveEvent(PlayerInvalidMoveEvent event) {
        event.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerFormResponse(PlayerFormRespondedEvent event) {
        if (event.getResponse() == null) return;
        if (!(event.getWindow() instanceof Gui)) return;

        ((Gui) event.getWindow()).onPlayerFormResponse(event);
    }
}
