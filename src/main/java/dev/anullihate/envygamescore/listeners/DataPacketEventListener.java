package dev.anullihate.envygamescore.listeners;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.server.DataPacketReceiveEvent;
import cn.nukkit.network.protocol.SetLocalPlayerAsInitializedPacket;
import cn.nukkit.scheduler.NukkitRunnable;
import dev.anullihate.envygamescore.EnvyGamesCore;
import dev.anullihate.envygamescore.datamanagers.UserManager;
import dev.anullihate.envygamescore.datatables.User;
import dev.anullihate.envygamescore.guis.divisions.DivisionsIntroGui;
import dev.anullihate.envygamescore.guis.server.IntroductionGui;

public class DataPacketEventListener implements Listener {

    EnvyGamesCore core;

    public DataPacketEventListener(EnvyGamesCore core) {
        this.core = core;
    }

    @EventHandler
    public void onDataPacketReceive(DataPacketReceiveEvent event) {
        Player player = event.getPlayer();
        if (event.getPacket() instanceof SetLocalPlayerAsInitializedPacket) {
            (new NukkitRunnable() {
                @Override
                public void run() {
                    User user = UserManager.users.get(player.getName());
                    if (user.getDivision() == null) {
                        player.showFormWindow(new DivisionsIntroGui());
                    } else {
                        player.showFormWindow(new IntroductionGui());
                    }
                }
            }).runTaskLater(this.core, 40);
        }
    }
}
