package dev.anullihate.envygamescore.apis;

import cn.nukkit.Player;
import de.theamychan.scoreboard.api.ScoreboardAPI;
import de.theamychan.scoreboard.network.DisplaySlot;
import de.theamychan.scoreboard.network.Scoreboard;
import de.theamychan.scoreboard.network.ScoreboardDisplay;
import dev.anullihate.envygamescore.EnvyGamesCore;
import dev.anullihate.envygamescore.datamanagers.UserManager;
import dev.anullihate.envygamescore.dataobjects.users.UserProfile;
import dev.anullihate.envygamescore.datatables.UserTable;

import java.util.HashMap;
import java.util.Map;

public class GeneralAPI {

    private EnvyGamesCore core;

    public Map<Player, Scoreboard> scoreboardMap = new HashMap<>();
    private int line = 0;

    public GeneralAPI(EnvyGamesCore core) {
        this.core = core;
    }

    public Scoreboard processPlayerScoreboard(Player player, String titleSequence) {
        Scoreboard scoreboard = ScoreboardAPI.createScoreboard();


        ScoreboardDisplay scoreboardDisplay = scoreboard.addDisplay(DisplaySlot.SIDEBAR, "dummy", titleSequence);

        for (String text : this.core.getConfig().getStringList("scoreboard.lines")) {
            String txt = text
                    .replace("{ping}", String.valueOf(player.getPing()))
                    .replace("{division}", String.valueOf(UserManager.getUser(player.getName()).division))
                    .replace("{divisionExp}", String.valueOf(UserManager.getUser(player.getName()).divisionExp))
                    .replace("{divisionRank}", "Division Rank")
                    .replace("{level}", String.valueOf(UserManager.getUser(player.getName()).level))
                    .replace("{exp}", String.valueOf(UserManager.getUser(player.getName()).exp))
                    .replace("{expTLU}", String.valueOf(UserManager.getUser(player.getName()).expTLU));
            scoreboardDisplay.addLine(EnvyGamesCore.placeholderAPI.translateString(txt, player), this.line++);
        }

        this.line = 0;
        return scoreboard;
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

    public String getDivision(Player player) {
        try {
            UserProfile user = UserManager.users.get(player.getName());
            String division = user.getDivision();
            if (division != null) {
                return division;
            }
        } catch (Exception ex) {
            return "None";
        }
        return "None";
    }
}
