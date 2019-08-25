package dev.anullihate.envygamescore.tasks;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.scheduler.PluginTask;
import cn.nukkit.utils.TextFormat;
import dev.anullihate.envygamescore.EnvyGamesCore;

public class UpdaterTask extends PluginTask<EnvyGamesCore> {

    public UpdaterTask(EnvyGamesCore core) {
        super(core);
    }

    @Override
    public void onRun(int i) {
        Server server = this.getOwner().getServer();
        for (Player player : server.getOnlinePlayers().values()) {
            String nameTagFormat = this.getOwner().getConfig().getString("player-tag.name-tag");
            String scoreTagFormat = (this.getOwner().getConfig().getString("player-tag.score-tag")
                    .replace("%device_os%", EnvyGamesCore.generalAPI.getOS(player))
            );

            String nameTag = EnvyGamesCore.placeholderAPI.translateString(nameTagFormat, player);
            String scoreTag = EnvyGamesCore.placeholderAPI.translateString(scoreTagFormat, player);

            player.setNameTag(TextFormat.colorize(nameTag));
            if (!player.getScoreTag().equals(scoreTag)) {
                player.setScoreTag(TextFormat.colorize(scoreTag));
            }

            try {
                EnvyGamesCore.generalAPI.scoreboardMap.get(player).hideFor(player);
            } catch (Exception e) {}
            EnvyGamesCore.generalAPI.processPlayerScoreboard(player);
        }
    }
}
