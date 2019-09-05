package dev.anullihate.envyfactioncore.listeners;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.server.DataPacketReceiveEvent;
import cn.nukkit.network.protocol.SetLocalPlayerAsInitializedPacket;
import cn.nukkit.scheduler.NukkitRunnable;
import dev.anullihate.envyfactioncore.EnvyFactionCore;
import dev.anullihate.envyfactioncore.datamanagers.UserManager;
import dev.anullihate.envyfactioncore.dataobjects.users.UserProfile;
import dev.anullihate.envyfactioncore.guis.server.IntroductionGui;

public class DataPacketEventListener implements Listener {

    EnvyFactionCore core;

    public DataPacketEventListener(EnvyFactionCore core) {
        this.core = core;
    }

    @EventHandler
    public void onDataPacketReceive(DataPacketReceiveEvent event) {
        Player player = event.getPlayer();
        if (event.getPacket() instanceof SetLocalPlayerAsInitializedPacket) {
            (new NukkitRunnable() {
                @Override
                public void run() {
                    UserProfile user = UserManager.users.get(player.getName());
                    if (user.initial) {
                        player.showFormWindow(new IntroductionGui());
                    }
                }
            }).runTaskLater(this.core, 40);
        }
    }
}
