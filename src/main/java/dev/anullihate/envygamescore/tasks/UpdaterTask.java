package dev.anullihate.envygamescore.tasks;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.scheduler.PluginTask;
import dev.anullihate.envygamescore.EnvyGamesCore;

public class UpdaterTask extends PluginTask<EnvyGamesCore> {

    public UpdaterTask(EnvyGamesCore core) {
        super(core);
    }

    @Override
    public void onRun(int i) {
        Server server = this.getOwner().getServer();
        for (Player player : server.getOnlinePlayers().values()) {
            try {
                EnvyGamesCore.generalAPI.scoreboardMap.get(player).hideFor(player);
            } catch (Exception e) {}
            EnvyGamesCore.generalAPI.processPlayerScoreboard(player);
        }
    }
}
