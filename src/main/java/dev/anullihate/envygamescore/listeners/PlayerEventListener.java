package dev.anullihate.envygamescore.listeners;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.event.player.PlayerInvalidMoveEvent;
import cn.nukkit.event.player.PlayerJoinEvent;
import dev.anullihate.envygamescore.EnvyGamesCore;
import dev.anullihate.envygamescore.datatables.Account;
import dev.anullihate.envygamescore.guis.Gui;

import java.sql.SQLException;

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

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        try {
            Account account = EnvyGamesCore.accountDao.queryForId(player.getName());
            System.out.println(account);

            if (account == null) {
                // create account
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
