package dev.anullihate.envygamescore.apis;

import cn.nukkit.Player;
import de.theamychan.scoreboard.api.ScoreboardAPI;
import de.theamychan.scoreboard.network.DisplaySlot;
import de.theamychan.scoreboard.network.Scoreboard;
import de.theamychan.scoreboard.network.ScoreboardDisplay;
import dev.anullihate.envygamescore.EnvyGamesCore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GeneralAPI {

    private EnvyGamesCore core;

    public Map<Player, Scoreboard> scoreboardMap = new HashMap<>();
    private int seq = 0;
    private int line = 0;

    public GeneralAPI(EnvyGamesCore core) {
        this.core = core;
    }

    public void processPlayerScoreboard(Player player) {
        Scoreboard scoreboard = ScoreboardAPI.createScoreboard();
        List<String> titles = this.core.getConfig().getStringList("scoreboard.titles");

        while (true) {
            if (this.seq < titles.size()) {
                ScoreboardDisplay scoreboardDisplay = scoreboard.addDisplay(DisplaySlot.SIDEBAR, "dummy", titles.get(this.seq));
                this.core.getConfig().getStringList("scoreboard.lines").forEach(text -> {
                    scoreboardDisplay.addLine(EnvyGamesCore.placeholderAPI.translateString(text, player), this.line++);
                });
                this.seq++;
                break;
            } else {
                this.seq = 0;
            }
        }

        scoreboard.showFor(player);
        scoreboardMap.put(player, scoreboard);

        this.line = 0;
    }
}
