package dev.anullihate.envygamescore.tasks;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.scheduler.PluginTask;
import cn.nukkit.utils.TextFormat;
import de.theamychan.scoreboard.network.Scoreboard;
import dev.anullihate.envygamescore.EnvyGamesCore;
import dev.anullihate.envygamescore.datamanagers.UserManager;

import java.util.List;

public class UpdaterTask extends PluginTask<EnvyGamesCore> {

    private int seq = 0;

    public UpdaterTask(EnvyGamesCore core) {
        super(core);
    }

    @Override
    public void onRun(int i) {
        Server server = this.getOwner().getServer();
        List<String> titles = this.getOwner().getConfig().getStringList("scoreboard.titles");
        while (true) {
            if (this.seq < titles.size()) {
                for (Player player : server.getOnlinePlayers().values()) {
                    if (UserManager.getUser(player.getName()) == null) {
                        break;
                    }
                    String nameTagFormat = this.getOwner().getConfig().getString("player-tag.name-tag");
                    String scoreTagFormat = (this.getOwner().getConfig().getString("player-tag.score-tag")
                            .replace("%device_os%", EnvyGamesCore.generalAPI.getOS(player))
                            .replace("{division}", EnvyGamesCore.generalAPI.getDivision(player))
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
                    Scoreboard scoreboard = EnvyGamesCore.generalAPI.processPlayerScoreboard(player, titles.get(this.seq));

                    scoreboard.showFor(player);
                    EnvyGamesCore.generalAPI.scoreboardMap.put(player, scoreboard);
                }
                this.seq++;
                break;
            } else {
                this.seq = 0;
            }
        }
    }
}
