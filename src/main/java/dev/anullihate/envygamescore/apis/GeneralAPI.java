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

    public String getOS(Player p) {
        switch(p.getLoginChainData().getDeviceOS()) {
            case 1:
                return "Android";
            case 2:
                return "iOS";
            case 3:
                return "Mac";
            case 4:
                return "Fire";
            case 5:
                return "Gear VR";
            case 6:
                return "HoloLens";
            case 7:
                return "Windows 10";
            case 8:
                return "Windows";
            case 9:
                return "Dedicated";
            case 10:
                return "tvOS";
            case 11:
                return "PlayStation";
            case 12:
                return "NX";
            case 13:
                return "Xbox";
            default:
                return "Unknown";
        }
    }

    public String getFactionTag(Player p) {
        String factionTag = EnvyGamesCore.p.getPlayerFactionTag(p);
        String trimmedFactionTag = factionTag.replace("*", "");

        if (factionTag.contains("**")) {
            return String.format("&c%s", trimmedFactionTag);
        } else if (factionTag.contains("*")) {
            return String.format("&6%s", trimmedFactionTag);
        } else {
            return String.format("&a%s", factionTag);
        }
    }
}
